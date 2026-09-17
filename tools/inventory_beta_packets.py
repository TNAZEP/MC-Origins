#!/usr/bin/env python3
"""Inventory imported Beta packet correspondence; textual matches are not wire parity tests."""
import argparse
import hashlib
import json
from pathlib import Path
import re
import subprocess
import zipfile

CORE = ('readPacketData', 'writePacketData', 'processPacket', 'getPacketSize')
PREFIX = 'net/minecraft/src/'
TOKENS = re.compile(r'"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|[A-Za-z_$][\w$]*|\S')


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def mappings(path):
    classes = {}
    with zipfile.ZipFile(path) as archive:
        lines = archive.read('mappings.tiny').decode().splitlines()
    if lines[0] != 'tiny\t2\t0\tnamed\tclient\tserver':
        raise ValueError('Unexpected mapping namespaces')
    for line in lines[1:]:
        parts = line.split('\t')
        if parts[0] == 'c':
            current = {'names': parts[2:4], 'members': []}
            classes[parts[1]] = current
        elif parts[:2] in (['', 'm'], ['', 'f']):
            current['members'].append({'kind': parts[1], 'descriptor': parts[2],
                                       'named': parts[3], 'names': parts[4:6]})
    return classes


def methods(source):
    # Restricted to the verified imported decompiler layout, not arbitrary Java parsing.
    found = {}
    for match in re.finditer(r'^\t(?:public|protected|private) [^\n=]*?\b(\w+)\(([^\n]*)\)(?: throws [^{;]+)? ([{;])', source, re.M):
        if match[3] == ';':
            body = ''
        else:
            end = source.index('\n\t}', match.end())
            body = source[match.end():end]
        found.setdefault(match[1], []).append({'signature': match[0].strip(), 'body': body,
                                               'line': source.count('\n', 0, match.start()) + 1})
    return found


def tokens(body, aliases=None):
    return [(aliases or {}).get(value, value) for value in TOKENS.findall(body)]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    root = Path(__file__).resolve().parent.parent
    output = args.output.resolve()
    if output.is_relative_to(root / 'reference'):
        parser.error('Output must be outside references')
    output.mkdir(parents=True, exist_ok=False)
    mapping_path = root / 'reference/tools/bin/retromcp-b1.7-resources-2026-09-08.zip'
    mapping = mappings(mapping_path)
    roots = [root / 'src' / side / 'java/net/minecraft/src' for side in ('client', 'server')]
    shared = root / 'src/main/java/net/minecraft/src'
    source_paths = []
    for folder in roots:
        paths = list(shared.glob('*.java')) + list(folder.glob('*.java'))
        if len({path.stem for path in paths}) != len(paths):
            raise ValueError('Duplicate shared/side class in source inventory')
        source_paths.append({path.stem: path for path in paths})
    sources = [{name: path.read_text() for name, path in paths.items()} for paths in source_paths]
    registrations = [re.findall(r'addIdClassMapping\((\d+), (true|false), (true|false), (\w+)\.class\)', side['Packet']) for side in sources]
    assert registrations[0] == registrations[1]
    classes = ['Packet', 'PacketCounter', 'NetHandler'] + [row[3] for row in registrations[0]]
    handler_aliases = []
    for side in range(2):
        handler_aliases.append({m['named']: 'handler_' + m['descriptor'] for m in mapping[PREFIX + 'NetHandler']['members']
                                if m['kind'] == 'm' and m['names'][side]})
    original_members = []
    commands = []
    for side, label in enumerate(('client', 'server')):
        jar = root / f'reference/source/beta-1.7.3/{label}/original/{"minecraft.jar" if side == 0 else "minecraft_server.jar"}'
        names = [mapping[PREFIX + name]['names'][side] for name in classes]
        with zipfile.ZipFile(jar) as archive:
            assert all(name + '.class' in archive.namelist() for name in names)
        command = ['/usr/lib/jvm/java-8-openjdk/bin/javap', '-p', '-s', '-classpath', str(jar)] + names
        commands.append(command)
        result = subprocess.run(command, check=True, capture_output=True, text=True)
        (output / f'{label}-original-descriptors.txt').write_text(result.stdout)
        members = {}
        current = None
        previous = ''
        for line in result.stdout.splitlines():
            match = re.search(r'\bclass ([\w.$]+)', line)
            if match:
                current = match[1]
                members[current] = set()
            elif 'descriptor:' in line:
                member = previous.split('(')[0].split()[-1].rstrip(';')
                members[current].add((member, line.split('descriptor:')[1].strip()))
            previous = line.strip()
        original_members.append(members)
    rows = []
    verified = 0
    for name in classes:
        row = {'class': name, 'original_names': mapping[PREFIX + name]['names'],
               'source_sha256': [sha(paths[name]) for paths in source_paths],
               'source_paths': [str(paths[name].relative_to(root)) for paths in source_paths],
               'byte_identical': sources[0][name] == sources[1][name], 'core_methods': {}, 'constructors': [],
               'game_type_mentions': []}
        parsed = [methods(side[name]) for side in sources]
        aliases = []
        for side in range(2):
            aliases.append(dict(handler_aliases[side]))
            for member in mapping[PREFIX + name]['members']:
                if member['kind'] == 'f' and member['names'][side]:
                    aliases[side][member['named']] = 'field_' + member['names'][side]
            row['constructors'].append([m['signature'] for m in parsed[side].get(name, [])])
            row['game_type_mentions'].append(sorted(set(TOKENS.findall(sources[side][name])) & sources[side].keys() - {name}))
            # Verify mapped declared fields/methods in scope, plus common overrides declared by this packet.
            checks = list(mapping[PREFIX + name]['members'])
            if name not in ('Packet', 'PacketCounter', 'NetHandler'):
                checks += [m for m in mapping[PREFIX + 'Packet']['members'] if m['kind'] == 'm' and m['named'] in parsed[side] and m['named'] in CORE]
            for member in checks:
                original = member['names'][side]
                if not original:
                    continue
                descriptor = re.sub(r'L([^;]+);', lambda m: 'L' + mapping.get(m[1], {'names': [m[1], m[1]]})['names'][side] + ';', member['descriptor'])
                assert (original, descriptor) in original_members[side][row['original_names'][side]], (name, side, member)
                verified += 1
        for method in CORE:
            bodies = [p.get(method) for p in parsed]
            if not all(bodies):
                status = 'inherited' if not any(bodies) else 'side-only'
            elif tokens(bodies[0][0]['body']) == tokens(bodies[1][0]['body']):
                status = 'text-equal'
            elif tokens(bodies[0][0]['body'], aliases[0]) == tokens(bodies[1][0]['body'], aliases[1]):
                status = 'equal-after-mapped-names'
            else:
                status = 'REVIEW'
            row['core_methods'][method] = {'status': status, 'source': bodies}
        if name == 'NetHandler':
            row['handler_methods'] = [p for p in parsed]
        rows.append(row)
    report = {'format': 'origins-beta-packet-ledger-v1', 'revision': subprocess.check_output(['git', 'rev-parse', 'HEAD'], cwd=root, text=True).strip(),
              'mapping_sha256': sha(mapping_path), 'tool_sha256': sha(Path(__file__)), 'commands': commands,
              'registrations': registrations[0], 'verified_original_member_descriptors': verified, 'classes': rows,
              'limit': 'Source-text and descriptor correspondence only; no runtime codec equivalence or merge approval inferred'}
    (output / 'ledger.json').write_text(json.dumps(report, indent=2) + '\n')
    lines = ['# Beta packet source inventory', '', '| Class | Original client/server | Byte-identical | Read / write / dispatch / size |', '|---|---|---|---|']
    for row in rows:
        lines.append(f"| {row['class']} | {' / '.join(row['original_names'])} | {row['byte_identical']} | " + ' / '.join(row['core_methods'][m]['status'] for m in CORE) + ' |')
    (output / 'inventory.md').write_text('\n'.join(lines) + '\n')
    print(json.dumps({'registered_packets': len(registrations[0]), 'classes': len(rows), 'verified_original_member_descriptors': verified,
                      'review_methods': [(r['class'], m) for r in rows for m in CORE if r['core_methods'][m]['status'] == 'REVIEW']}, indent=2))


if __name__ == '__main__':
    main()
