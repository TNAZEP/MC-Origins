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

## a1 startup-fixture finding (2026-09-09)

The seed-only server smoke worlds are not golden worldgen fixtures: Beta's spawn search uses unseeded World.rand, altering startup chunk requests. Identical existing level.dat files align spawn/coverage but still leave Overworld lava-related differences, including original-vs-original repeats. [Evidence](../status/evidence/2026-09-09-a1-terrain/README.md). Preserve RNG semantics in the game; add explicit test-only RNG/tick/request controls and verify oracle repeatability before parity claims. tools/compare_beta_terrain.py provides a read-only saved-terrain comparator, not a complete generation harness or persistence verifier.

## Qualified bounded server fixture (2026-09-09)

The separate reflection harness now controls World.rand (seed 8675309), x-outer/z-inner chunk requests and lighting drains with no simulation ticks. Original repeat and original/built match 1,248 saved chunks and identical request/RNG traces. [Evidence](../status/evidence/2026-09-09-a1-terrain-control/README.md). This qualifies one server seed/order, not the full matrix above. Previous startup fixtures remain diagnostic; gameplay RNG is unchanged.

## Saved-terrain matrix checkpoint (2026-09-09)

All six planned seeds now pass original repeat and candidate comparison in x/z, reverse and spiral schedules across three 5x5 neighborhoods centered on chunks -32, 0 and 32 in both server dimensions. 18 cases/54 captures with matching request/RNG traces. [Evidence](../status/evidence/2026-09-09-a1-terrain-matrix/README.md). Saved chunk counts remain order-dependent; each case has 96 saved populated chunks. This covers saved post-request terrain, not independent raw pre-population dumps or biome/climate samples. Those, SP and long-distance arithmetic coverage remain pending.

## Raw server generation and biome/climate checkpoint (2026-09-09)

The same six-seed/three-order/boundary matrix now passes independent direct-generator captures: raw blocks/metadata before population and explicit 16x16 biome/temperature/humidity queries, compared with exact double bits. 18 cases/54 captures, 150 chunks per case, 2,700 original/candidate samples. [Evidence](../status/evidence/2026-09-09-a1-raw-terrain/README.md). The adapter asserts no populated or world-loaded chunks; it runs no simulation ticks. Seven biome identities were observed; cold/missing-biome, explicit sea threshold, far-distance and SP cases remain pending. Full worldgen acceptance is not promoted.
