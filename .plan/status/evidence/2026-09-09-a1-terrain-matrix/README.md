# Bounded server terrain matrix — 2026-09-09

Base revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus existing and session work. Linux x86_64, Python 3.14.7, OpenJDK Java/javac 8u504. Game sources/resources, original reference inputs and build settings remain unchanged. No game rebuild, graphical launch, SP or two-player test performed this session.

## Scope and controls

The matrix runs seeds 0, 1, -1, 123456789, -9223372036854775808 and 9223372036854775807, each in x-outer/z-inner, reverse and center-out spiral order. Each schedule requests three 5x5 chunk neighborhoods centered at (-32,-32), (0,0) and (32,32), 75 explicit requests in each of Overworld and Nether. It covers the planned (-33,-33), (-32,-32), (-1,-1), (0,0), (31,31), (32,32) boundary points. Each schedule is retained as schedule.tsv, including the exact center/axis order. Reverse reverses the whole x/z sequence; spiral visits centers in -32,0,32 order with local +x,+z,-x,-z expanding legs.

For every seed/order, the original server is captured twice in separate fresh JVMs/worlds, then the built server once. The comparator checks all saved block/metadata/light/height arrays, population flags, exact saved coordinate sets, seed and spawn. Request/lighting/RNG traces and RNG digest logs must also be byte-identical within each case. Different orders are compared only with their corresponding originals; Beta can save different chunk counts by order. No fields are masked.

The existing test adapter now accepts an explicit schedule. Without --requests it reconstructs the previously qualified startup requests. --seed validates a signed Java long and patches only the eight-byte Data.RandomSeed payload in a disposable level.dat copy, then recompresses it. The input template is untouched; both input and derived hashes are recorded. Structured NBT traversal avoids matching unrelated names or strings. Existing seed/spawn metadata prevents random spawn search; spawn remains (32,64,-16) for every fixture, not a claim about newly selected spawn points for these seeds. Test-only World.rand remains seed 8675309, with zero simulation ticks and original loadChunk population/lighting behavior. Seed and request controls do not alter game sources or algorithms.

The original adapter remains constrained to server SHA-256 033a127e4a25a60b038f15369c89305a3d53752242a1cff11ae964954e79ba4d. Candidate SHA-256 dde3a5627a65c6310977e202efddaffd52c02f515e129e87918899348dc3e7b7. Source correspondence/constructor constraints remain as documented in the prior [adapter evidence](../2026-09-09-a1-terrain-control/README.md).

## Commands and artifacts

```sh
python3 tools/check_beta_terrain_matrix.py --java-home /usr/lib/jvm/java-8-openjdk --original reference/source/beta-1.7.3/server/original/minecraft_server.jar --candidate build/libs/Minecraft-server.jar --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-matrix --evidence .plan/status/evidence/2026-09-09-a1-terrain-matrix
python3 -m unittest discover -s tools -p 'test_*.py' -v
/usr/lib/jvm/java-8-openjdk/bin/javac -cp run/terrain-matrix/0-xz/original/classes -d run/terrain-matrix/0-xz/original/classes tools/BetaTerrainHarnessTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp run/terrain-matrix/0-xz/original/classes BetaTerrainHarnessTest
python3 tools/import_beta_sources.py --verify
python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar build/libs/Minecraft-server.jar --namespace named --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-control/default-adapter-regression
python3 tools/compare_beta_terrain.py --original run/terrain-control/original-1/world --candidate run/terrain-control/default-adapter-regression/world --output .plan/status/evidence/2026-09-09-a1-terrain-matrix/default-regression
```

summary.json records the matrix script hash, revision, invocation and each comparison result. Per-case command JSONs record exact capture commands; result JSONs include Java version, child commands and tool/input hashes. Each case retains its original canonical terrain snapshot with region hashes, three process/request logs and comparison results. Full worlds and all canonical snapshots remain under ignored run/terrain-matrix. Default-regression retains the prior large-fixture comparison, current inputs/commands and trace. Python inventory equality assertion and imported file verification are recorded in preservation.txt and source-verification.txt.

## Limits and recovery

This matrix samples saved terrain after normal neighbor-driven population at zero ticks. It does not capture pristine raw generation before population or biome/temperature/rainfall arrays. It does not cover SP generation, long-distance arithmetic edge cases, active simulation, scheduled ticks/entities/tile entities, save/reload stability or the complete persistence contract. A small neighborhood and fixed auxiliary RNG seed cannot certify all Beta behavior. Full worldgen and a1 exit gates remain open.

Next bounded action: add independent raw pre-population and biome/climate sampling to the oracle harness with corresponding original comparisons. Gameplay/SP/two-player/visual/audio scenarios and shared-source reconciliation remain pending. Reproduce in fresh output/evidence directories; captures refuse existing outputs and never rewrite pristine templates. Earlier failed startup evidence remains unchanged.

## Final verification

| Check | Result | Evidence |
|---|---|---|
| Six seeds × three orders; original repeat and candidate | PASS | 18 cases, 54 fresh captures, 36 comparisons; zero differences; summary.json |
| Request/lighting/RNG traces | PASS | All three traces/logs match within each of 18 cases |
| Default adapter regression | PASS | 1,248 saved chunks match earlier original; unchanged request trace |
| Python tests | PASS | 11 tests; tests.txt |
| Java RNG fidelity | PASS | Six seeds, 60,000 mixed iterations; rng-test.txt |
| Input preservation | PASS | 1,211 working hashes and prior reference inventory |
| Raw generation/biomes/SP/full gameplay and milestone gates | NOT RUN | Coverage limits above |

Matrix commands exit 0. Saved counts range 138–149 per case (2598 chunk comparisons summed over the 18 candidate cases; repeated coordinates across seeds/orders are counted separately). coverage.json records populated/saved counts. The variation by order is preserved, not normalized. Final `git diff --check` PASS; existing unrelated changes preserved.
