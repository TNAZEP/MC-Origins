#!/usr/bin/env python3
"""Capture one test-controlled, tick-free Beta server terrain sample in a fresh directory."""
import argparse
import gzip
import json
from pathlib import Path
import subprocess
import struct

from compare_beta_terrain import NBT, field, inflate, sha, snapshot
from read_beta_raw import snapshot_raw

ORIGINAL_SHA256 = '033a127e4a25a60b038f15369c89305a3d53752242a1cff11ae964954e79ba4d'


def seeded_template(template, seed):
    """Patch only Data.RandomSeed's eight payload bytes in a disposable copy."""
    raw = inflate(template, True)
    field(field(NBT(raw).root(), 'Data', 10), 'RandomSeed', 4)
    reader = NBT(raw)
    reader.number('B')
    reader.string()
    while (tag := reader.number('B')):
        name = reader.string()
        if name == b'Data':
            while (child := reader.number('B')):
                key = reader.string()
                if key == b'RandomSeed':
                    offset = reader.position
                    return gzip.compress(raw[:offset] + struct.pack('>q', seed) + raw[offset + 8:], mtime=0)
                reader.payload(child)
        else:
            reader.payload(tag)
    raise ValueError('Missing Data.RandomSeed')


def parse_requests(text):
    result = []
    for line in text.splitlines():
        parts = line.split('\t')
        if len(parts) != 2:
            raise ValueError('Requests must contain tab-separated x/z pairs')
        point = tuple(map(int, parts))
        if any(abs(value) > 1874990 for value in point):
            raise ValueError('Request outside bounded Beta coordinate range')
        result.append(point)
    if not result or len(result) > 4096 or len(set(result)) != len(result):
        raise ValueError('Expected 1..4096 unique requests')
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-home', type=Path, required=True)
    parser.add_argument('--jar', type=Path, required=True)
    parser.add_argument('--namespace', choices=['original', 'named'], required=True)
    parser.add_argument('--level-template', type=Path, required=True)
    parser.add_argument('--rng-seed', type=int, default=8675309)
    parser.add_argument('--mode', choices=['saved', 'raw'], default='saved')
    parser.add_argument('--seed', type=int, help='Override only the copied level.dat RandomSeed')
    parser.add_argument('--requests', type=Path, help='Explicit ordered chunk x/z pairs, tab-separated, used in both dimensions')
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    jar, output = args.jar.resolve(), args.output.resolve()
    source = Path(__file__).resolve().with_name('BetaTerrainHarness.java')
    reference = source.parent.parent / 'reference'
    if output == reference or reference in output.parents:
        parser.error('Output must be outside pristine references')
    if any(not -(2**63) <= value < 2**63 for value in (args.rng_seed, args.seed) if value is not None):
        parser.error('Seeds must be signed Java longs')
    jar_hash = sha(jar.read_bytes())
    if args.namespace == 'original' and jar_hash != ORIGINAL_SHA256:
        parser.error('Original adapter is qualified only for the recorded Beta server SHA-256')
    template = args.level_template.read_bytes()
    input_template_hash = sha(template)
    if args.seed is not None:
        template = seeded_template(template, args.seed)
    data = field(NBT(inflate(template, True)).root(), 'Data', 10)
    seed = field(data, 'RandomSeed', 4)
    spawn = [field(data, key, 3) for key in ('SpawnX', 'SpawnY', 'SpawnZ')]
    requests = parse_requests(args.requests.read_text()) if args.requests else [
        ((spawn[0] + dx) >> 4, (spawn[2] + dz) >> 4)
        for dx in range(-196, 197, 16) for dz in range(-196, 197, 16)]
    output.mkdir(parents=True, exist_ok=False)
    (output / 'world').mkdir()
    (output / 'world/level.dat').write_bytes(template)
    (output / 'classes').mkdir()
    schedule = ''.join(f'{x}\t{z}\n' for x, z in requests)
    (output / 'schedule.tsv').write_text(schedule)
    java = args.java_home.resolve() / 'bin/java'
    commands = [
        [str(args.java_home.resolve() / 'bin/javac'), '-d', str(output / 'classes'), str(source)],
        [str(java), '-Xmx512M', '-Djava.awt.headless=true', '-cp', str(output / 'classes'),
         'BetaTerrainHarness', str(jar), args.namespace, str(output), str(seed), str(args.rng_seed), str(output / 'schedule.tsv'), args.mode],
    ]
    result = {'result': 'FAIL', 'jar_sha256': jar_hash, 'template_sha256': sha(template),
              'input_template_sha256': input_template_hash, 'schedule_sha256': sha(schedule.encode()),
              'harness_sha256': sha(source.read_bytes()), 'driver_sha256': sha(Path(__file__).read_bytes()),
              'java': subprocess.run([str(java), '-version'], capture_output=True, text=True, check=True).stderr,
              'commands': commands, 'seed': seed, 'spawn': spawn, 'world_rng_seed': args.rng_seed,
              'ticks': 0, 'mode': args.mode, 'order': 'overworld then nether; explicit schedule.tsv',
              'raw_reader_sha256': sha(Path(__file__).with_name('read_beta_raw.py').read_bytes()),
              'requested_chunks_per_dimension': len(requests)}
    try:
        with (output / 'process.log').open('w') as log:
            for command in commands:
                subprocess.run(command, cwd=output, stdout=log, stderr=subprocess.STDOUT, check=True, timeout=120)
        terrain = (snapshot_raw(output / 'raw-terrain.bin', seed, spawn, requests)
                   if args.mode == 'raw' else snapshot(output / 'world'))
        (output / 'terrain.json').write_text(json.dumps(terrain, indent=2) + '\n')
        result.update(result='PASS', chunks=len(terrain['chunks']),
                      requests_sha256=sha((output / 'requests.tsv').read_bytes()))
    finally:
        (output / 'result.json').write_text(json.dumps(result, indent=2) + '\n')
    print(json.dumps(result, indent=2))


if __name__ == '__main__':
    main()
