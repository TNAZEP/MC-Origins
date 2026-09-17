#!/usr/bin/env python3
"""Compare control-packet/string observations across both original and built Beta sides."""
import argparse
import hashlib
import json
from pathlib import Path
import subprocess


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    root = Path(__file__).resolve().parent.parent
    output = args.output.resolve()
    if output.is_relative_to(root / 'reference'):
        parser.error('Output must be outside references')
    output.mkdir(parents=True, exist_ok=False)
    (output / 'classes').mkdir()
    source = root / 'tools/BetaPacketCodecTest.java'
    java = Path('/usr/lib/jvm/java-8-openjdk/bin')
    commands = [[str(java / 'javac'), '-d', str(output / 'classes'), str(source)]]
    result = {'result': 'FAIL', 'commands': commands, 'harness_sha256': sha(source), 'driver_sha256': sha(Path(__file__)),
              'revision': subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=root, text=True).strip(),
              'java': subprocess.run([str(java / 'java'), '-version'], capture_output=True, text=True, check=True).stderr,
              'inputs': {}}
    try:
        with (output / 'compile.log').open('w') as log:
            subprocess.run(commands[0], check=True, stdout=log, stderr=subprocess.STDOUT)
        for label, jar, namespace in (
            ('original-client', 'reference/source/beta-1.7.3/client/original/minecraft.jar', 'client'),
            ('original-server', 'reference/source/beta-1.7.3/server/original/minecraft_server.jar', 'server'),
            ('built-client', 'build/libs/Minecraft.jar', 'named'),
            ('built-server', 'build/libs/Minecraft-server.jar', 'named')):
            result['inputs'][label] = {'path': jar, 'sha256': sha(root / jar)}
            command = [str(java / 'java'), '-cp', str(output / 'classes'), 'BetaPacketCodecTest',
                       str(root / jar), namespace, str(output / (label + '.tsv'))]
            commands.append(command)
            with (output / (label + '.log')).open('w') as log:
                subprocess.run(command, check=True, stdout=log, stderr=subprocess.STDOUT, timeout=60)
        oracle = (output / 'original-client.tsv').read_bytes()
        matches = {label: (output / (label + '.tsv')).read_bytes() == oracle for label in result['inputs']}
        result.update(result='PASS' if all(matches.values()) else 'FAIL', matches=matches,
                      observations=len(oracle.splitlines()), oracle_sha256=sha(output / 'original-client.tsv'))
    finally:
        (output / 'result.json').write_text(json.dumps(result, indent=2) + '\n')
    print(json.dumps({k: result[k] for k in ('result', 'matches', 'observations')}, indent=2))
    return 0 if result['result'] == 'PASS' else 1


if __name__ == '__main__':
    raise SystemExit(main())
