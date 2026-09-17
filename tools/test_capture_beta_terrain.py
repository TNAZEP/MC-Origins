import gzip
import struct
import unittest

from capture_beta_terrain import parse_requests, seeded_template
from check_beta_terrain_matrix import schedule
from compare_beta_terrain import NBT, field
from test_compare_beta_terrain import named


class CaptureTests(unittest.TestCase):
    def test_seed_patch_changes_only_target_payload(self):
        fake = named(4, 'RandomSeed', struct.pack('>q', 99))
        raw = named(10, 'root', named(10, 'unrelated', fake + b'\0')
                    + named(10, 'Data', named(8, 'Text', b'\0\x0aRandomSeed')
                            + fake + named(9, 'List', b'\x04\0\0\0\x01' + struct.pack('>q', 99)) + b'\0') + b'\0')
        for seed in (0, -1, -(2**63), 2**63 - 1):
            modified = gzip.decompress(seeded_template(gzip.compress(raw), seed))
            data = NBT(modified).root()
            self.assertEqual(field(field(data, 'Data', 10), 'RandomSeed', 4), seed)
            # Independent expected bytes: only the second named long is replaced.
            first = raw.index(fake)
            second = raw.index(fake, first + len(fake)) + len(fake) - 8
            self.assertEqual(modified, raw[:second] + struct.pack('>q', seed) + raw[second + 8:])

    def test_orders_cover_same_boundaries_without_duplicates(self):
        xz, reverse, spiral = [schedule(order) for order in ('xz', 'reverse', 'spiral')]
        self.assertEqual(reverse, xz[::-1])
        self.assertEqual(spiral[25:30], [(0, 0), (1, 0), (1, 1), (0, 1), (-1, 1)])
        for points in (xz, reverse, spiral):
            self.assertEqual(len(points), 75)
            self.assertEqual(set(points), set(xz))
            for coord in (-33, -32, -1, 0, 31, 32):
                self.assertIn((coord, coord), points)

    def test_bad_requests_rejected(self):
        for text in ('', '1 2', '1\t2\n1\t2\n', '1874991\t0', '1\t2\t3'):
            with self.assertRaises(ValueError):
                parse_requests(text)
        self.assertEqual(parse_requests('-33\t32\n0\t0\n'), [(-33, 32), (0, 0)])


if __name__ == '__main__':
    unittest.main()
