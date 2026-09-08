#!/usr/bin/env python3
"""Read local references without modifying them; emit deterministic JSON evidence."""

import argparse
import hashlib
import json
from pathlib import Path


def sha256(path):
    with path.open('rb') as stream:
        return hashlib.file_digest(stream, 'sha256').hexdigest()


def tree(root, pattern='*'):
    files = {p.relative_to(root).as_posix(): sha256(p)
             for p in sorted(root.rglob(pattern)) if p.is_file()}
    # Hash sorted UTF-8 records: relative POSIX path, NUL, hex digest, LF.
    digest = hashlib.sha256(''.join(
        f'{name}\0{value}\n' for name, value in files.items()).encode()).hexdigest()
    return {'file_count': len(files), 'sha256': digest}, files


def compare(left, right):
    return [{'path': name, 'client_sha256': left.get(name),
             'server_sha256': right.get(name),
             'classification': ('server_only' if name not in left else
                                'client_only' if name not in right else
                                'byte_identical' if left[name] == right[name] else
                                'different_unreviewed')}
            for name in sorted(left.keys() | right.keys())]


def inventory(reference):
    manifest = json.loads((reference / 'manifest.json').read_text())
    artifacts = []
    for entry in manifest['artifacts']:
        root = reference / entry['source_directory']
        observed, _ = tree(root)
        java, _ = tree(root, '*.java')
        artifacts.append({
            'version': entry['version'], 'side': entry['side'],
            'source_directory': entry['source_directory'],
            'directory_exists': root.is_dir(), 'observed_tree': observed,
            'java_tree': java,
            'original_files': [p.relative_to(reference).as_posix()
                               for p in sorted((root / 'original').rglob('*'))
                               if p.is_file()],
            'jars': [{'path': p.relative_to(reference).as_posix(),
                      'sha256': sha256(p), 'size_bytes': p.stat().st_size}
                     for p in sorted(root.rglob('*.jar'))],
        })
    beta = reference / 'source/beta-1.7.3'
    sources = {}
    for side in ('client', 'server'):
        for name in ('src', 'src_original'):
            root = beta / side / 'decompiled' / name
            summary, files = tree(root, '*.java')
            sources[f'{side}/{name}'] = {'directory_exists': root.is_dir(),
                                        **summary, 'files': files}
    return {'schema_version': 1,
            'hash_algorithm': 'SHA-256; trees: sorted UTF-8 path + NUL + hex file digest + LF',
            'scope': 'Observed bytes only; labels, provenance and behavior are unverified.',
            'artifacts': artifacts, 'beta_source_trees': sources,
            'beta_same_path_candidates': compare(sources['client/src']['files'],
                                                  sources['server/src']['files'])}


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--reference', type=Path,
                        default=Path(__file__).resolve().parents[1] / 'reference')
    args = parser.parse_args()
    print(json.dumps(inventory(args.reference), indent=2, sort_keys=True))
