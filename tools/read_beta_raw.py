"""Strict reader for the test adapter's raw terrain format (not a game save format)."""
import struct

from compare_beta_terrain import sha

FORMAT = 'origins-beta-raw-terrain-v1'
FIELDS = {'Blocks': 32768, 'Data': 16384, 'Biomes': 256,
          'TemperatureBits': 2048, 'HumidityBits': 2048}
RECORD_SIZE = 12 + sum(FIELDS.values())
BIOMES = ('rainforest', 'swampland', 'seasonalForest', 'forest', 'savanna', 'shrubland',
          'taiga', 'desert', 'plains', 'iceDesert', 'tundra', 'hell', 'field_28054_m')


def decode_raw(raw):
    if len(raw) < 12:
        raise ValueError('Truncated raw header')
    magic, version, count = struct.unpack_from('>4sII', raw)
    if magic != b'ORAW' or version != 1 or not 1 <= count <= 8192:
        raise ValueError('Invalid raw header')
    if len(raw) != 12 + count * RECORD_SIZE:
        raise ValueError('Truncated or trailing raw record data')
    chunks = {}
    offset = 12
    for _ in range(count):
        dimension, x, z = struct.unpack_from('>iii', raw, offset)
        offset += 12
        if dimension not in (0, -1):
            raise ValueError('Unsupported dimension')
        key = f'{"overworld" if dimension == 0 else "nether"}/{x},{z}'
        if key in chunks:
            raise ValueError('Duplicate raw chunk')
        fields = {}
        for name, size in FIELDS.items():
            data = raw[offset:offset + size]
            offset += size
            if name == 'Biomes' and any(value >= len(BIOMES) for value in data):
                raise ValueError('Unknown biome identity')
            if name.endswith('Bits') and any(not 0 <= value <= 1 for (value,) in struct.iter_unpack('>d', data)):
                raise ValueError('Non-finite or out-of-range climate sample')
            fields[name] = sha(data)
        chunks[key] = fields
    return chunks


def snapshot_raw(path, seed, spawn, requests):
    raw = path.read_bytes()
    chunks = decode_raw(raw)
    expected = {f'{dimension}/{x},{z}' for dimension in ('overworld', 'nether') for x, z in requests}
    if chunks.keys() != expected:
        raise ValueError('Raw coverage does not match explicit requests in both dimensions')
    return {'format': FORMAT, 'seed': seed, 'spawn': spawn,
            'input_sha256': {'raw-terrain.bin': sha(raw)}, 'chunks': chunks}
