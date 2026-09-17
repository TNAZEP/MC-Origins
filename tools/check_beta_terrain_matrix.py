#!/usr/bin/env python3
"""Run a bounded six-seed, three-order saved-terrain matrix against Beta server."""
import argparse
import json
from pathlib import Path
import shutil
import subprocess
import sys

from compare_beta_terrain import compare, sha

SEEDS = (0, 1, -1, 123456789, -(2**63), 2**63 - 1)
ORDERS = ('xz', 'reverse', 'spiral')


def schedule(order):
    points = [(center + x, center + z) for center in (-32, 0, 32)
              for x in range(-2, 3) for z in range(-2, 3)]
    if order == 'xz':
        return points
    if order == 'reverse':
        return points[::-1]
    if order != 'spiral':
        raise ValueError(order)
    # Center outward: east, north, west, south, increasing each second leg.
    spiral = [(0, 0)]
    x = z = 0
    length = 1
    directions = ((1, 0), (0, 1), (-1, 0), (0, -1))
    leg = 0
    while len(spiral) < 25:
        dx, dz = directions[leg % 4]
        for _ in range(length):
            x, z = x + dx, z + dz
            if -2 <= x <= 2 and -2 <= z <= 2:
                spiral.append((x, z))
        leg += 1
        if leg % 2 == 0:
            length += 1
    return [(center + x, center + z) for center in (-32, 0, 32) for x, z in spiral]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--java-home', type=Path, required=True)
    parser.add_argument('--original', type=Path, required=True)
    parser.add_argument('--candidate', type=Path, required=True)
    parser.add_argument('--level-template', type=Path, required=True)
    parser.add_argument('--mode', choices=['saved', 'raw'], default='saved')
    parser.add_argument('--output', type=Path, required=True, help='Fresh disposable capture directory')
    parser.add_argument('--evidence', type=Path, required=True, help='Fresh persistent report directory')
    args = parser.parse_args()
    root = Path(__file__).resolve().parent.parent
    output, evidence = args.output.resolve(), args.evidence.resolve()
    for destination in (output, evidence):
        if destination.is_relative_to(root / 'reference') or destination.exists():
            parser.error('Output/evidence must be fresh and outside reference')
    if output.is_relative_to(evidence) or evidence.is_relative_to(output):
        parser.error('Keep disposable captures and evidence separate')
    output.mkdir(parents=True)
    evidence.mkdir(parents=True)
    results = []
    summary = {'result': 'FAIL', 'cases': results, 'seeds': SEEDS, 'orders': ORDERS, 'mode': args.mode,
               'script_sha256': sha(Path(__file__).read_bytes()), 'argv': sys.argv,
               'revision': subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=root, text=True).strip()}
    try:
        for seed in SEEDS:
            for order in ORDERS:
                label = f'{seed}-{order}'
                case = evidence / label
                case.mkdir()
                requests = case / 'schedule.tsv'
                requests.write_text(''.join(f'{x}\t{z}\n' for x, z in schedule(order)))
                captures = {}
                for side, jar, namespace in [('original', args.original, 'original'),
                                             ('repeat', args.original, 'original'),
                                             ('candidate', args.candidate, 'named')]:
                    dest = output / label / side
                    command = [sys.executable, str(root / 'tools/capture_beta_terrain.py'),
                               '--java-home', str(args.java_home.resolve()), '--jar', str(jar.resolve()),
                               '--namespace', namespace, '--level-template', str(args.level_template.resolve()),
                               '--seed', str(seed), '--requests', str(requests), '--output', str(dest), '--mode', args.mode]
                    with (case / f'{side}-command.json').open('w') as file:
                        json.dump(command, file, indent=2)
                    with (case / f'{side}-driver.log').open('w') as log:
                        subprocess.run(command, cwd=root, stdout=log, stderr=subprocess.STDOUT, check=True, timeout=150)
                    captures[side] = json.loads((dest / 'terrain.json').read_text())
                    for filename in ('result.json', 'requests.tsv', 'process.log'):
                        shutil.copyfile(dest / filename, case / f'{side}-{filename}')
                # Retain the original canonical oracle; all captures remain in the disposable tree.
                (case / 'original-terrain.json').write_text(json.dumps(captures['original'], sort_keys=True, indent=2) + '\n')
                comparisons = {side: compare(captures['original'], captures[side]) for side in ('repeat', 'candidate')}
                trace_match = all((case / f'original-{filename}').read_bytes() == (case / f'{side}-{filename}').read_bytes()
                                  for filename in ('requests.tsv', 'process.log') for side in ('repeat', 'candidate'))
                passed = trace_match and all(value['result'] == 'PASS' for value in comparisons.values())
                item = {'seed': seed, 'order': order, 'result': 'PASS' if passed else 'FAIL',
                        'traces_match': trace_match, 'comparisons': comparisons}
                (case / 'comparison.json').write_text(json.dumps(item, indent=2) + '\n')
                results.append(item)
                (evidence / 'summary.json').write_text(json.dumps(summary, indent=2) + '\n')
                print(f"{label}: {item['result']}, {comparisons['candidate']['original_chunks']} {args.mode} chunks", flush=True)
        summary['result'] = 'PASS' if all(item['result'] == 'PASS' for item in results) else 'FAIL'
    finally:
        (evidence / 'summary.json').write_text(json.dumps(summary, indent=2) + '\n')
    return 0 if summary['result'] == 'PASS' else 1


if __name__ == '__main__':
    raise SystemExit(main())
