#!/usr/bin/env python3
"""Reproduce 1.3.2 study sources offline from hash-pinned local inputs.

Creates a NEW output directory. Never writes to the installed reference tree.
This is a decompilation recipe, not an Origins game build.
"""

import argparse
import hashlib
import json
from pathlib import Path
import subprocess
import zipfile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--output', required=True, type=Path)
    parser.add_argument('--java', default='java')
    args = parser.parse_args()
    root = Path(__file__).resolve().parents[1]
    output = args.output.resolve()
    if output.is_relative_to(root / 'reference'):
        parser.error('Use a fresh scratch directory outside reference/.')
    lock = json.loads((root / 'reference/tools/1.3.2-inputs.json').read_text())
    required = [entry for entry in lock['files']
                if 'RetroMCP' not in entry['path'] and 'retromcp-' not in entry['path']]
    paths = {}
    for entry in required:
        path = root / entry['path']
        with path.open('rb') as stream:
            actual = hashlib.file_digest(stream, 'sha256').hexdigest()
        if actual != entry['sha256']:
            raise ValueError(f'Input SHA-256 mismatch: {path}')
        paths[path.name] = path
    # Refuse all existing destinations, including partially completed runs.
    output.mkdir(parents=True, exist_ok=False)
    (output / 'java-version.txt').write_text(subprocess.check_output(
        [args.java, '-version'], stderr=subprocess.STDOUT, text=True))
    mapping = output / 'mappings.tiny'
    with zipfile.ZipFile(paths['yarn-1.3.2+build.604-mergedv2.jar']) as archive:
        mapping.write_bytes(archive.read('mappings/mappings.tiny'))
    commands = []
    for side in ('client', 'server'):
        destination = output / side
        destination.mkdir()
        mapped = destination / 'named.jar'
        steps = [
            ('remap', [args.java, '-jar', str(paths['tiny-remapper-0.14.0-fat.jar']),
                       str(paths[side + '.jar']), str(mapped), str(mapping),
                       'official', 'named']),
            ('decompile', [args.java, '-Xmx2G', '-jar', str(paths['cfr-0.152.jar']),
                           str(mapped), '--outputdir', str(destination / 'src'),
                           '--silent', 'true']),
        ]
        for name, command in steps:
            commands.append({'side': side, 'step': name, 'argv': command})
            (output / 'commands.json').write_text(json.dumps(commands, indent=2) + '\n')
            with (destination / (name + '.log')).open('w') as log:
                subprocess.run(command, stdout=log, stderr=subprocess.STDOUT, check=True)
        print(f'{side}: {len(list((destination / "src").rglob("*.java")))} Java files; '
              f'inspect {destination / "src/summary.txt"} for decompiler warnings')


if __name__ == '__main__':
    main()
