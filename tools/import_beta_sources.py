#!/usr/bin/env python3
"""Import editable Beta sources/resources once, or verify the initial copy.

Run from any directory. Import refuses an existing src/ directory.
--verify checks the recorded initial working snapshot, without reading references.
After intentional development edits, a snapshot mismatch is expected.
"""

import argparse
import hashlib
import json
from pathlib import Path, PurePosixPath
import shutil
import zipfile

from inventory_references import tree


ROOT = Path(__file__).resolve().parents[1]
DESTINATION = ROOT / 'src'
EVIDENCE = ROOT / '.plan/status/evidence/2026-09-08-a1-references'


def digest(data):
    return hashlib.sha256(data).hexdigest()


def verify():
    manifest = json.loads((DESTINATION / 'IMPORT.json').read_text())
    expected = {entry['destination']: entry['sha256'] for entry in manifest['files']}
    actual = {}
    for side in ('main', 'client', 'server'):
        for kind in ('java', 'resources'):
            for path in (DESTINATION / side / kind).rglob('*'):
                if path.is_file() and path.name != '.gitkeep':
                    actual[path.relative_to(ROOT).as_posix()] = digest(path.read_bytes())
    differences = [name for name in sorted(actual.keys() | expected.keys())
                   if actual.get(name) != expected.get(name)]
    if differences:
        raise SystemExit('Initial import snapshot differs (expected after development edits):\n'
                         + '\n'.join(differences))
    print(f'PASS: {len(expected)} working files match the initial import snapshot')


def import_sources():
    if DESTINATION.exists():
        raise SystemExit('Refusing to overwrite existing src/. Edit working files directly; '
                         'use --verify to check the initial snapshot.')
    inventory = json.loads((EVIDENCE / 'inventory.json').read_text())
    manifest = json.loads((ROOT / 'reference/manifest.json').read_text())
    files = []
    # Verify ALL inputs and archive paths before creating the working directory.
    copies = []
    for side in ('client', 'server'):
        source = ROOT / 'reference/source/beta-1.7.3' / side / 'decompiled/src'
        expected = inventory['beta_source_trees'][side + '/src']['files']
        if tree(source, '*.java')[1] != expected:
            raise SystemExit(f'Reference source differs from the recorded snapshot: {side}')
        for name, sha in sorted(expected.items()):
            origin = source / name
            target = Path('src') / side / 'java' / name
            copies.append((origin, target, None))
            files.append({'destination': target.as_posix(),
                          'source': origin.relative_to(ROOT).as_posix(), 'sha256': sha})
        entry = next(item for item in manifest['artifacts']
                     if item['version'] == 'b1.7.3' and item['side'] == side)
        jar = ROOT / 'reference' / entry['original_file']
        jar_hash = digest(jar.read_bytes())
        recorded = next(item for item in inventory['artifacts']
                        if item['version'] == 'b1.7.3' and item['side'] == side)
        jar_observation = next(item for item in recorded['jars']
                               if item['path'] == entry['original_file'])
        if jar_hash != jar_observation['sha256'] or jar_hash != entry['sha256']:
            raise SystemExit(f'Original jar differs from the recorded snapshot: {side}')
        with zipfile.ZipFile(jar) as archive:
            seen = set()
            for member in sorted(archive.infolist(), key=lambda item: item.filename):
                name = member.filename
                if member.is_dir() or name.endswith('.class') or name.startswith('META-INF/'):
                    continue
                path = PurePosixPath(name)
                if path.is_absolute() or '..' in path.parts or '\\' in name or name in seen:
                    raise SystemExit(f'Unsafe or duplicate resource path: {name}')
                seen.add(name)
                data = archive.read(member)
                target = Path('src') / side / 'resources' / path
                copies.append((jar, target, data))
                files.append({'destination': target.as_posix(),
                              'source': jar.relative_to(ROOT).as_posix(),
                              'archive_entry': name, 'archive_sha256': jar_hash,
                              'sha256': digest(data)})
    DESTINATION.mkdir()
    for origin, target, data in copies:
        destination = ROOT / target
        destination.parent.mkdir(parents=True, exist_ok=True)
        if data is None:
            shutil.copyfile(origin, destination)
        else:
            destination.write_bytes(data)
    for directory in ('main/java', 'main/resources', 'test/java', 'integrationTest/java'):
        path = DESTINATION / directory
        path.mkdir(parents=True)
        (path / '.gitkeep').touch()
    (DESTINATION / 'IMPORT.json').write_text(json.dumps({
        'schema_version': 1,
        'baseline_revision': '83155adbcddba2f77f7269f68ee949502e054efe',
        'input_evidence': EVIDENCE.relative_to(ROOT).as_posix(),
        'policy': 'Verbatim per-side import; no class merging or behavior edits. '
                  'Jar META-INF metadata/signatures and class files excluded from resources. '
                  'External assets/natives/dependencies not imported.',
        'files': files,
    }, indent=2) + '\n')
    verify()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--verify', action='store_true')
    args = parser.parse_args()
    if args.verify:
        verify()
    else:
        import_sources()
