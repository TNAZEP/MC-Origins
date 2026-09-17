#!/usr/bin/env python3
"""Explicitly materialize verified local Beta asset objects for a development run."""
import argparse
import hashlib
import json
from pathlib import Path, PurePosixPath


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--index', type=Path, required=True)
    parser.add_argument('--objects', type=Path, required=True)
    parser.add_argument('--destination', type=Path, required=True)
    args = parser.parse_args()
    destination = args.destination.resolve()
    reference = Path(__file__).resolve().parents[1] / 'reference'
    if destination.is_relative_to(reference):
        parser.error('Destination must be outside immutable references')
    index = json.loads(args.index.read_text())
    files = []
    for name, entry in index['objects'].items():
        path = PurePosixPath(name)
        digest = entry['hash']
        if path.is_absolute() or '..' in path.parts or '\\' in name:
            raise ValueError('Invalid asset path: ' + name)
        if len(digest) != 40 or any(c not in '0123456789abcdef' for c in digest):
            raise ValueError('Invalid asset digest')
        data = (args.objects / digest[:2] / digest).read_bytes()
        if len(data) != entry['size'] or hashlib.sha1(data).hexdigest() != digest:
            raise ValueError('Asset hash/size mismatch: ' + name)
        target = destination.joinpath(*path.parts)
        if not target.resolve().is_relative_to(destination):
            raise ValueError('Asset escapes destination: ' + name)
        if target.exists() and target.read_bytes() != data:
            raise ValueError('Refusing to overwrite differing resource: ' + str(target))
        files.append((target, data))
    for target, data in files:
        target.parent.mkdir(parents=True, exist_ok=True)
        if not target.exists():
            target.write_bytes(data)
    report = {'result': 'PASS', 'files': len(files), 'index': str(args.index),
              'index_sha256': hashlib.sha256(args.index.read_bytes()).hexdigest(),
              'objects': str(args.objects), 'destination': str(destination)}
    print(json.dumps(report, indent=2))


if __name__ == '__main__':
    main()
