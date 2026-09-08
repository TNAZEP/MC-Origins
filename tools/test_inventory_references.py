"""Checks for inventory integrity and conservative correspondence classification."""

import tempfile
import unittest
from pathlib import Path

from inventory_references import compare, tree


class InventoryTests(unittest.TestCase):
    def test_tree_tracks_content_and_paths(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            file = root / 'A.java'
            file.write_bytes(b'original')
            before, _ = tree(root)
            file.write_bytes(b'changed')
            changed, _ = tree(root)
            self.assertNotEqual(before['sha256'], changed['sha256'])
            file.rename(root / 'B.java')
            renamed, _ = tree(root)
            self.assertNotEqual(changed['sha256'], renamed['sha256'])
            self.assertEqual(renamed, tree(root)[0])

    def test_missing_tree_has_no_files(self):
        with tempfile.TemporaryDirectory() as directory:
            summary, files = tree(Path(directory) / 'missing')
            self.assertEqual(summary['file_count'], 0)
            self.assertEqual(files, {})

    def test_correspondence_does_not_infer_equivalence(self):
        rows = compare({'A': 'a', 'B': 'b', 'C': 'c'},
                       {'A': 'a', 'B': 'different', 'D': 'd'})
        self.assertEqual([row['classification'] for row in rows],
                         ['byte_identical', 'different_unreviewed',
                          'client_only', 'server_only'])


if __name__ == '__main__':
    unittest.main()
