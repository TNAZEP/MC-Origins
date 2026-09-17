import unittest

from inventory_beta_packets import methods, tokens


class PacketInventoryTests(unittest.TestCase):
    def test_method_boundaries_include_nested_blocks(self):
        source = ('public class P {\n\tpublic void readPacketData(Input var1) {\n'
                  '\t\tif(true) {\n\t\t\tvar1.readInt();\n\t\t}\n\t}\n'
                  '\tpublic int getPacketSize() {\n\t\treturn 4;\n\t}\n}\n')
        result = methods(source)
        self.assertIn('var1.readInt()', result['readPacketData'][0]['body'])
        self.assertNotIn('return 4', result['readPacketData'][0]['body'])
        self.assertEqual(result['getPacketSize'][0]['line'], 7)

    def test_normalization_preserves_strings_operators_and_values(self):
        aliases = {'oldName': 'field_a'}
        self.assertEqual(tokens('this.oldName + 1', aliases), tokens('this.field_a+1'))
        self.assertNotEqual(tokens('"oldName"', aliases), tokens('"field_a"'))
        self.assertNotEqual(tokens('"a b"'), tokens('"ab"'))
        self.assertNotEqual(tokens('a + 1 > 0 ? 6 : 0'), tokens('a > 0 ? 6 : 0'))


if __name__ == '__main__':
    unittest.main()
