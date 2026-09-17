#!/usr/bin/env python3
"""Read-only comparison of saved Beta McRegion terrain, not full world parity."""
import argparse
import hashlib
import json
from pathlib import Path
import re
import struct
import zlib

FORMAT = 'origins-beta-saved-terrain-v1'
ARRAYS = {'Blocks': 32768, 'Data': 16384, 'SkyLight': 16384,
          'BlockLight': 16384, 'HeightMap': 256}


def sha(data):
    return hashlib.sha256(data).hexdigest()


def inflate(data, gzip=False):
    decoder = zlib.decompressobj(31 if gzip else 15)
    result = decoder.decompress(data, 4 * 1024 * 1024 + 1)
    if len(result) > 4 * 1024 * 1024 or not decoder.eof or decoder.unused_data:
        raise ValueError('Oversized, truncated or trailing compressed NBT')
    return result


class NBT:
    """Beta tags 1..10. Names/strings stay raw modified-UTF-8 bytes.

    Only known ASCII tag names are interpreted by the terrain extractor.
    """
    def __init__(self, data):
        self.data, self.position = data, 0

    def read(self, count):
        if count < 0 or self.position + count > len(self.data):
            raise ValueError('Truncated NBT or negative length')
        start = self.position
        self.position += count
        return self.data[start:self.position]

    def number(self, fmt):
        return struct.unpack('>' + fmt, self.read(struct.calcsize('>' + fmt)))[0]

    def string(self):
        return self.read(self.number('H'))

    def payload(self, tag, depth=0):
        if depth > 64:
            raise ValueError('NBT nesting limit')
        if tag in range(1, 7):
            return self.number({1: 'b', 2: 'h', 3: 'i', 4: 'q', 5: 'f', 6: 'd'}[tag])
        if tag == 7:
            return self.read(self.number('i'))
        if tag == 8:
            return self.string()
        if tag == 9:
            child, size = self.number('B'), self.number('i')
            if not 0 <= size <= 1000000 or not 0 <= child <= 10 or (child == 0 and size):
                raise ValueError('Invalid NBT list')
            return [self.payload(child, depth + 1) for _ in range(size)]
        if tag == 10:
            result = {}
            while True:
                child = self.number('B')
                if child == 0:
                    return result
                name = self.string()
                if name in result:
                    raise ValueError('Duplicate NBT compound key')
                result[name] = (child, self.payload(child, depth + 1))
        raise ValueError(f'Unsupported Beta NBT tag: {tag}')

    def root(self):
        if self.number('B') != 10:
            raise ValueError('Expected root compound')
        self.string()
        result = self.payload(10)
        if self.position != len(self.data):
            raise ValueError('Trailing NBT bytes')
        return result


def field(compound, name, tag):
    actual, value = compound[name.encode('ascii')]
    if actual != tag:
        raise ValueError(f'Wrong NBT type for {name}')
    return value


def region_chunks(path):
    match = re.fullmatch(r'r\.(-?\d+)\.(-?\d+)\.mcr', path.name)
    if not match:
        raise ValueError(f'Invalid region filename: {path}')
    rx, rz = map(int, match.groups())
    data = path.read_bytes()
    if len(data) < 8192 or len(data) % 4096:
        raise ValueError('Invalid region file size')
    occupied = {0, 1}
    chunks = {}
    for slot in range(1024):
        location = int.from_bytes(data[slot * 4:slot * 4 + 4], 'big')
        if not location:
            continue
        offset, count = location >> 8, location & 255
        sectors = set(range(offset, offset + count))
        if not count or sectors & occupied or (offset + count) * 4096 > len(data):
            raise ValueError('Overlapping/out-of-range region allocation')
        occupied.update(sectors)
        start = offset * 4096
        length = int.from_bytes(data[start:start + 4], 'big')
        if not 1 < length <= count * 4096 - 4:
            raise ValueError('Invalid chunk compressed length')
        codec = data[start + 4]
        if codec not in (1, 2):
            raise ValueError('Unsupported McRegion compression')
        level = field(NBT(inflate(data[start + 5:start + 4 + length], codec == 1)).root(), 'Level', 10)
        x, z = field(level, 'xPos', 3), field(level, 'zPos', 3)
        if (x, z) != (rx * 32 + slot % 32, rz * 32 + slot // 32):
            raise ValueError('Chunk coordinates do not match region slot')
        terrain = {}
        for name, size in ARRAYS.items():
            value = field(level, name, 7)
            if len(value) != size:
                raise ValueError(f'Invalid Beta array size: {name}')
            terrain[name] = sha(value)
        populated = field(level, 'TerrainPopulated', 1)
        if populated not in (0, 1):
            raise ValueError('Invalid TerrainPopulated flag')
        terrain['TerrainPopulated'] = populated
        chunks[f'{x},{z}'] = terrain
    return sha(data), chunks


def snapshot(world):
    raw_level = (world / 'level.dat').read_bytes()
    data = field(NBT(inflate(raw_level, True)).root(), 'Data', 10)
    result = {'format': FORMAT, 'seed': field(data, 'RandomSeed', 4),
              'spawn': [field(data, name, 3) for name in ('SpawnX', 'SpawnY', 'SpawnZ')],
              'input_sha256': {'level.dat': sha(raw_level)}, 'chunks': {}}
    for dimension, folder in [('overworld', world / 'region'), ('nether', world / 'DIM-1/region')]:
        count = 0
        for path in sorted(folder.glob('*.mcr')):
            digest, chunks = region_chunks(path)
            result['input_sha256'][path.relative_to(world).as_posix()] = digest
            for key, value in chunks.items():
                full = f'{dimension}/{key}'
                if full in result['chunks']:
                    raise ValueError('Duplicate chunk coordinates')
                result['chunks'][full] = value
                count += 1
        if not count:
            raise ValueError(f'No chunks found in required dimension {dimension}')
    return result


def compare(original, candidate):
    differences = []
    for key in ('format', 'seed', 'spawn'):
        if original[key] != candidate[key]:
            differences.append({'context': key, 'original': original[key], 'candidate': candidate[key]})
    a, b = original['chunks'], candidate['chunks']
    for key in sorted(a.keys() | b.keys()):
        if key not in a or key not in b:
            differences.append({'chunk': key, 'missing_from': 'original' if key not in a else 'candidate'})
        elif a[key] != b[key]:
            differences.append({'chunk': key, 'fields': [name for name in a[key] if a[key][name] != b[key][name]]})
    return {'format': FORMAT, 'result': 'FAIL' if differences else 'PASS',
            'original_chunks': len(a), 'candidate_chunks': len(b), 'differences': differences}


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--original', required=True, type=Path)
    parser.add_argument('--candidate', required=True, type=Path)
    parser.add_argument('--output', required=True, type=Path)
    args = parser.parse_args()
    if any(args.output.resolve().is_relative_to(world.resolve())
           for world in (args.original, args.candidate)):
        parser.error('Comparison output must be outside both input worlds')
    original, candidate = snapshot(args.original), snapshot(args.candidate)
    result = compare(original, candidate)
    args.output.mkdir(parents=True, exist_ok=False)
    for name, value in [('original', original), ('candidate', candidate), ('comparison', result)]:
        (args.output / (name + '.json')).write_text(json.dumps(value, sort_keys=True, indent=2) + '\n')
    print(json.dumps({key: value for key, value in result.items() if key != 'differences'}
                     | {'difference_count': len(result['differences']),
                        'details': str(args.output / 'comparison.json')}, indent=2))
    raise SystemExit(0 if result['result'] == 'PASS' else 1)
