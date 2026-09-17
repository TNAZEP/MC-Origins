import copy
from pathlib import Path
import struct
import tempfile
import unittest
import zlib

from compare_beta_terrain import ARRAYS, FORMAT, NBT, compare, region_chunks


def named(tag, name, payload):
    name = name.encode('ascii')
    return bytes([tag]) + struct.pack('>H', len(name)) + name + payload


def chunk(x=-1, z=-1, changed=False):
    tags = named(3, 'xPos', struct.pack('>i', x)) + named(3, 'zPos', struct.pack('>i', z))
    for name, size in ARRAYS.items():
        data = bytes([1 if changed and name == 'Blocks' else 0]) + bytes(size - 1)
        tags += named(7, name, struct.pack('>i', size) + data)
    tags += named(1, 'TerrainPopulated', b'\x01')
    return named(10, '', named(10, 'Level', tags + b'\x00') + b'\x00')


def region(payload, codec=2):
    compressor = zlib.compressobj(wbits=31 if codec == 1 else 15)
    compressed = compressor.compress(payload) + compressor.flush()
    record = struct.pack('>iB', len(compressed) + 1, codec) + compressed
    sectors = (len(record) + 4095) // 4096
    header = bytearray(8192)
    header[1023 * 4:1024 * 4] = ((2 << 8) | sectors).to_bytes(4, 'big')
    return bytes(header) + record + bytes(sectors * 4096 - len(record))


class TerrainTests(unittest.TestCase):
    def test_negative_coordinates_and_compression_variants(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'r.-1.-1.mcr'
            dumps = []
            for codec in (1, 2):
                path.write_bytes(region(chunk(), codec))
                dumps.append(region_chunks(path)[1])
            self.assertEqual(dumps[0], dumps[1])
            self.assertEqual(set(dumps[0]), {'-1,-1'})
            self.assertEqual(dumps[0]['-1,-1']['TerrainPopulated'], 1)

    def test_one_block_mutation_is_detected(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'r.-1.-1.mcr'
            path.write_bytes(region(chunk()))
            a = {'format': FORMAT, 'seed': -1, 'spawn': [0, 64, 0], 'chunks': region_chunks(path)[1]}
            path.write_bytes(region(chunk(changed=True)))
            b = dict(a, chunks=region_chunks(path)[1])
            self.assertEqual(compare(a, b)['differences'], [{'chunk': '-1,-1', 'fields': ['Blocks']}])
            self.assertEqual(compare(a, a)['result'], 'PASS')

    def test_missing_chunk_or_spawn_difference_is_not_a_pass(self):
        a = {'format': FORMAT, 'seed': 0, 'spawn': [0, 64, 0], 'chunks': {'0,0': {}}}
        b = copy.deepcopy(a)
        b['chunks'] = {}
        b['spawn'][0] = 1
        self.assertEqual(len(compare(a, b)['differences']), 2)

    def test_corrupt_regions_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'r.-1.-1.mcr'
            wrong_location = bytearray(region(chunk()))
            wrong_location[0:4] = wrong_location[1023 * 4:1024 * 4]
            for data in (b'\x00', region(chunk(x=0)), bytes(wrong_location)):
                path.write_bytes(data)
                with self.assertRaises(ValueError):
                    region_chunks(path)

    def test_truncation_duplicate_keys_and_bad_array_length_rejected(self):
        fixtures = [chunk()[:-1], chunk() + b'\x00',
                    named(10, '', named(1, 'x', b'\x00') * 2 + b'\x00'),
                    named(10, '', named(7, 'x', struct.pack('>i', -1)) + b'\x00')]
        for data in fixtures:
            with self.assertRaises(ValueError):
                NBT(data).root()


if __name__ == '__main__':
    unittest.main()
