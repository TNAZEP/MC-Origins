#!/usr/bin/env python3
"""Verify current source/resources against immutable import plus reviewed evolution hashes."""
import hashlib
import json
from pathlib import Path


def main():
    root = Path(__file__).resolve().parent.parent
    baseline = json.loads((root / 'src/IMPORT.json').read_text())
    expected = {item['destination']: item['sha256'] for item in baseline['files']}
    changes = json.loads((root / 'src/EVOLUTION.json').read_text())['changes']
    seen = set()
    for change in changes:
        path = change['path']
        if path in seen or expected.get(path) != change['baseline_sha256']:
            raise SystemExit('Invalid/duplicate evolution baseline: ' + path)
        seen.add(path)
        expected[path] = change['sha256']
    actual = {}
    for side in ('main', 'client', 'server'):
        for kind in ('java', 'resources'):
            for path in (root / 'src' / side / kind).rglob('*'):
                if path.is_file() and path.name != '.gitkeep':
                    actual[path.relative_to(root).as_posix()] = hashlib.sha256(path.read_bytes()).hexdigest()
    differences = [key for key in sorted(expected.keys() | actual.keys()) if expected.get(key) != actual.get(key)]
    if differences:
        raise SystemExit('Unrecorded working-source differences:\n' + '\n'.join(differences))
    print(f'PASS: {len(actual)} working files; {len(changes)} reviewed evolution entries; import manifest unchanged')


if __name__ == '__main__':
    main()
