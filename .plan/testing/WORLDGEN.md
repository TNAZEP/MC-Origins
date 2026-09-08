# World-generation invariance

The requirement is unchanged Beta generation, not similar-looking terrain. Baseline and candidate must use the same signed seed, dimension, coordinates, chunk-request order, population/neighbour availability, settings and reference runtime. Order-dependent Beta results remain order-dependent; do not sort requests to make the result prettier.

## Initial fixture set

Use signed-long seeds `0`, `1`, `-1`, `123456789`, `-9223372036854775808`, and `9223372036854775807` as initial test inputs, not precomputed claims. For each, sample origin and positive/negative chunk neighborhoods around section/region boundaries, including chunks (-33,-33), (-32,-32), (-1,-1), (0,0), (31,31), (32,32). Add source-informed long-distance coordinates for arithmetic/noise edge cases after baseline investigation.

Exercise row-major, reverse and spiral request orders independently. Compare each order against its corresponding Beta result, not against another order. Capture raw generation before population and after population with the same neighbor availability. Run Overworld and Nether baseline paths, distinguishing any SP/MP generation differences.

## Capture and compare

- Full block ID/metadata equivalents over original Beta Y coordinates, not merely aggregate heightmaps.
- Biome/temperature/rainfall samples used by Beta, plus sea-level threshold behavior.
- Surface, caves, ores, trees, vegetation, lakes, bedrock and population boundary effects.
- Heights and lighting where affected by generation; RNG state/call trace where feasible without perturbation.
- Chunk-coordinate and population flags on save/reload; no regeneration of existing imported terrain.

For section-storage tests, verify the normal generator produces no new terrain in Y=128..255. Verify storage changes do not increase random-tick/spawn sample range or alter ceiling behavior. Preserve the original generator water threshold (commonly described as sea level 64); record the actual source condition and top water coordinate before assigning an exact test assertion.

## Gate

Discrete generated state matches the pristine Beta oracle for the fixture suite under each controlled schedule. Any mismatch is investigated and classified; “same seed, visually similar” is insufficient. A golden hash includes the canonical dump format version so serializer changes cannot masquerade as terrain changes.

New terrain in vanilla 1.17.1 or 1.18.x belongs to the migration test suite and is expected to use that vanilla release's generator. It must not change Origins' own generator selection.
