# Raw server generation and biome/climate checkpoint — 2026-09-09

Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus existing/session changes. Linux x86_64; Python 3.14.7; OpenJDK Java/javac 8u504. Game sources/resources, build configuration and reference inputs unchanged. No game rebuild, graphical launch, SP or multiplayer scenario run.

## Verification

| Check | Result | Evidence |
|---|---|---|
| Raw original repeat / candidate matrix | PASS | 18 cases, 54 captures, 36 comparisons with zero differences; summary.json |
| Raw block/metadata arrays | PASS | 150 chunks per case, 2,700 original/candidate chunk samples total |
| Biome identities and exact climate bits | PASS | 691,200 columns per field across candidate cases; coverage.json |
| Request and world-RNG traces | PASS | All three traces/process logs byte-identical within each case; zero World.rand calls and zero loaded world chunks |
| Existing saved-terrain path | PASS | saved-regression: 1,248 chunks and trace still match earlier original |
| Python tests | PASS | 14 tests; tests.txt includes three new raw reader/mutation/corruption checks |
| Java RNG observer fidelity | PASS | Six seeds, 60,000 mixed iterations; rng-test.txt |
| Source/reference preservation | PASS | 1,211 import hashes; previous reference inventory unchanged |
| SP, all biomes, far-distance, active simulation, full gameplay/persistence | NOT RUN | Scope limitations below |

Each case uses one of the six planned signed seeds and one of x/z, reverse or spiral order, across three 5x5 chunk neighborhoods centered at (-32,-32), (0,0), (32,32). Both server dimensions are sampled. Each original is repeated in a separate fresh JVM/world before the corresponding candidate run. Exact schedules, input/tool hashes, commands, canonical original field hashes and comparison results are retained per case. Raw binary dumps and all three canonical snapshots remain in ignored run/raw-terrain-matrix. There are 2,700 samples summed across cases, not 2,700 unique coordinates. Different orders are compared with their matching original controls.

## Adapter and format

The raw mode is separate from the saved mode: direct serverChunkGenerator.provideChunk(x,z), bypassing ChunkProviderServer.loadChunk and its population callbacks. It asserts returned x/z, isTerrainPopulated=false and that the server provider's loaded-chunk list remains empty after every request. No population, world ticks, lighting drain, chunk registration or chunk saving is invoked in raw mode. World seed and unchanged time are asserted, and World.rand observation remains enabled. The Overworld generator's own height/skylight calculation still executes as part of its provideChunk; the harness does not replace it.

After each generation call, a separate explicit WorldChunkManager.loadBlockGeneratorData(null,x*16,z*16,16,16) query records biome singleton identities and the manager's temperature/humidity arrays in their original order (x outer, z inner). These are Beta's climate fields, including humidity used for biome selection, not a modern rainfall API. Nether values come from its constant biome manager, not a claim that its terrain uses Overworld climate noise. No additional query is injected into the saved-generation path.

Correspondences were read from the current candidate RetroMCP mapping bundle and checked against original javap descriptors (raw-adapter-descriptors.txt): ChunkProviderServer.serverChunkGenerator = he.d; IChunkProvider.provideChunk(int,int) = bl.b; Chunk = hi with blocks=b, data=e, xPosition=j, zPosition=k, isTerrainPopulated=n; NibbleArray.data = ob.a; World.getWorldChunkManager = dj.a; WorldChunkManager.loadBlockGeneratorData = ph.a(gs[],int,int,int,int), temperature=ph.a, humidity=ph.b. BiomeGenBase=gs, singleton fields a through m correspond to the adapter identity table in read_beta_raw.py. These bounded mappings do not resolve historical decompiler provenance or complete the source correspondence ledger.

Raw binary format `ORAW`, version 1: four-byte ASCII magic, big-endian 32-bit version/count, followed by count records. Each record is dimension (0 or -1), chunk x and z as big-endian signed ints; 32,768 block bytes; 16,384 metadata nibble bytes; 256 biome identity bytes; 256 temperature and 256 humidity values stored as big-endian raw IEEE-754 double bits. Identities 0..12 follow the explicit Beta singleton table, not game registry IDs. Each record is 53,516 bytes. No float tolerance, decimal conversion or hash of Java object identity is used. The reader rejects malformed dimensions/counts/lengths, duplicate coordinates, unknown biome identities, non-finite/out-of-range climate samples and incomplete request coverage. Canonical report format is origins-beta-raw-terrain-v1, deliberately different from saved terrain.

The mutation tests detect a changed block, biome and a single temperature bit. Original jar remains pinned to SHA-256 033a127e4a25a60b038f15369c89305a3d53752242a1cff11ae964954e79ba4d; candidate is dde3a5627a65c6310977e202efddaffd52c02f515e129e87918899348dc3e7b7. Each result JSON records driver, Java harness, raw reader and input hashes. Existing seed-copy and request controls remain as qualified in the prior [matrix evidence](../2026-09-09-a1-terrain-matrix/README.md).

## Actual commands

```sh
python3 tools/check_beta_terrain_matrix.py --java-home /usr/lib/jvm/java-8-openjdk --original reference/source/beta-1.7.3/server/original/minecraft_server.jar --candidate build/libs/Minecraft-server.jar --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/raw-terrain-matrix --evidence .plan/status/evidence/2026-09-09-a1-raw-terrain --mode raw
python3 -m unittest discover -s tools -p 'test_*.py' -v
/usr/lib/jvm/java-8-openjdk/bin/javap -classpath reference/source/beta-1.7.3/server/original/minecraft_server.jar -p he bl hi ob ph gs
python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar build/libs/Minecraft-server.jar --namespace named --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-control/raw-extension-saved-regression
python3 tools/compare_beta_terrain.py --original run/terrain-control/original-1/world --candidate run/terrain-control/raw-extension-saved-regression/world --output .plan/status/evidence/2026-09-09-a1-raw-terrain/saved-regression
/usr/lib/jvm/java-8-openjdk/bin/javac -cp run/raw-terrain-matrix/0-xz/original/classes -d run/raw-terrain-matrix/0-xz/original/classes tools/BetaTerrainHarnessTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp run/raw-terrain-matrix/0-xz/original/classes BetaTerrainHarnessTest
python3 tools/import_beta_sources.py --verify
```

All commands exit 0. Per-case command JSONs and driver/result logs include exact child invocations. Python assertions verified inventory(Path('reference')) equals the acquisition inventory, and the current saved request trace equals run/terrain-control/original-1/requests.tsv; PASS. Final git diff --check PASS.

## Limits and continuation

The sampled biome identities are seasonalForest, forest, savanna, shrubland, desert, plains and hell. Temperature range 0.5178284793437942..1, humidity 0..0.8012852625835946. These samples do not qualify every biome, cold/snow/ice cases or explicit sea-level threshold assertions. Raw lighting/height is not included here; saved lighting/height remains covered by the prior saved fixture matrix. Client/SP generation, long-distance arithmetic, active entity/tick behavior, save/reload and two-player/fixed-camera/audio fixtures remain pending. No full worldgen or a1 promotion is claimed.

Next bounded independent work: produce a Packet/NetHandler class/method correspondence ledger from both Beta sides with original-name mappings and dependency/side-policy classification before moving code. Continue remaining runtime fixture qualification before behavior-sensitive merges. Recovery: rerun in fresh output/evidence directories; do not overwrite worlds/templates or reuse saved chunks. Prior PASS and failed startup evidence is preserved.
