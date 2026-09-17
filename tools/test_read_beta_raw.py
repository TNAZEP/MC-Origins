import struct
import unittest

from compare_beta_terrain import compare
from read_beta_raw import FORMAT, decode_raw


def record(dimension=0, x=-33, z=32, block=1, biome=3, temperature=0.75):
    return (struct.pack('>iii', dimension, x, z) + bytes([block]) + bytes(32767)
            + bytes(16384) + bytes([biome]) * 256
            + struct.pack('>d', temperature) * 256 + struct.pack('>d', 0.5) * 256)


def stream(*records):
    return struct.pack('>4sII', b'ORAW', 1, len(records)) + b''.join(records)


class RawTests(unittest.TestCase):
    def test_negative_coordinates_and_both_dimensions(self):
        chunks = decode_raw(stream(record(), record(-1)))
        self.assertEqual(set(chunks), {'overworld/-33,32', 'nether/-33,32'})

    def test_block_biome_and_single_climate_bit_changes_are_detected(self):
        original = {'format': FORMAT, 'seed': 0, 'spawn': [0, 64, 0], 'chunks': decode_raw(stream(record()))}
        for change, field in [({'block': 2}, 'Blocks'), ({'biome': 4}, 'Biomes'),
                              ({'temperature': 0.7500000000000001}, 'TemperatureBits')]:
            candidate = dict(original, chunks=decode_raw(stream(record(**change))))
            self.assertEqual(compare(original, candidate)['differences'][0]['fields'], [field])

    def test_corruption_rejected(self):
        good = stream(record())
        for data in (good[:-1], good + b'\0', b'BAD!' + good[4:], stream(record(), record()),
                     stream(record(1)), stream(record(biome=13)), stream(record(temperature=float('nan')))):
            with self.assertRaises(ValueError):
                decode_raw(data)


if __name__ == '__main__':
    unittest.main()
