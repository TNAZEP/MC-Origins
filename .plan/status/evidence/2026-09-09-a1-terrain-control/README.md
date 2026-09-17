# Controlled Beta server terrain checkpoint — 2026-09-09

Base revision `e1eaaac7d06a41452762eb92fe4bf3fec5670968`, main plus pre-existing build/tooling changes and this session. Linux x86_64, Python 3.14.7, OpenJDK Java/javac 8u504. No game Java/resources, reference inputs, build configuration or user saves changed. No new game build or graphical launch performed.

## Results

| Check | Result | Evidence |
|---|---|---|
| Original repeat | PASS | oracle-repeat/comparison.json: zero differences across 1,248 saved chunks |
| Original versus built server | PASS | candidate/comparison.json: zero differences across 1,248 saved chunks |
| Request, lighting-drain and RNG traces | PASS | All three requests.tsv and process.log files byte-identical |
| RNG observer fidelity | PASS | rng-test.txt: six seeds, 60,000 mixed iterations against java.util.Random |
| Capture refusal guards | PASS | refusal-checks.json: wrong original hash and existing output rejected; no output overwrite |
| Python decoder/inventory tests | PASS | tests.txt: 8 tests |
| Working-source and pristine input preservation | PASS | source-verification.txt: 1,211 imported hashes; inventory(reference) equals prior acquisition inventory |
| Full seed/order/SP/gameplay/visual/persistence suite | NOT RUN | This is one bounded tick-free server fixture |

Each capture loads 625 chunks per dimension; Beta saves 625 Overworld and 623 Nether chunks. All compared Blocks, Data, SkyLight, BlockLight, HeightMap and TerrainPopulated fields match, with identical seed and spawn. No fields were dropped to obtain the pass. Raw save bytes may differ because session locks and timestamps are excluded from the saved-terrain comparator.

## Harness contract and provenance

`tools/capture_beta_terrain.py` copies the existing original-derived level.dat into a new output directory, compiles the JDK-only reflection adapter, then loads only the explicitly selected game jar in an isolated classloader. The original adapter refuses any original jar hash other than the qualified Beta server. Result JSON records actual compiler/runtime commands, Java version, jar/template/driver/harness hashes and request-trace hash. Worlds and compiled tooling remain under ignored run/terrain-control; reviewable canonical dumps, hashes and logs are retained here.

`tools/BetaTerrainHarness.java` constructs SaveOldDir, WorldServer and WorldServerMulti, with a null server host and no listeners, players, network, normal server loop or simulation ticks. It checks that constructors loaded no chunks before replacing each world's Random with a seed-8675309 Java Random subclass that only records protected next(bits) results. The class does not change the Random algorithm; the separate mixed-operation test covers rejection sampling, power-of-two bounds, long/double/Gaussian/byte outputs and signed seed limits. Constructor randomness outside this controlled stream remains untouched; the existing metadata avoids spawn search, and no ticks use the ambient countdown/distHashCounter.

Both dimensions are constructed before generation. Requests use Beta initWorld's x-outer/z-inner offsets -196 through 196 in steps of 16 around spawn (32,64,-16), Overworld first then Nether. Each request uses the original loadChunk, which retains population's neighbor-availability decisions, followed by the original lighting drain. World time stays 1 and is asserted unchanged; saveWorld(true,null) and save-handler region flush complete the capture. No attempt is made to equate different request orders or to reproduce a wall-clock startup world. Server settings/listeners and active simulation are outside this adapter's coverage.

Current RetroMCP mapping bundle SHA-256 b1903f261dca46e25feb50151102afd11807d446ac49c59dac7a995b497c92a9 supplied candidate symbol names; this does not resolve historical decompilation provenance. Descriptors and constructor/load/save paths were inspected against the pinned original using javap; output is original-adapter-bytecode.txt. The adapter uses these bounded correspondences:

| Named | Original server |
|---|---|
| World / WorldServer / WorldServerMulti | dj / dp / eg |
| SaveOldDir / ISaveHandler / IProgressUpdate | ie / om / pj |
| ChunkProviderServer | he |
| World.rand / WorldServer.chunkProviderServer | dj.r / dp.C |
| ChunkProviderServer.field_727_f / loadChunk(int,int) | he.g / he.c(int,int) |
| World.func_6156_d / getRandomSeed / getWorldTime | dj.f() / dj.l() / dj.m() |
| World.saveWorld(boolean,IProgressUpdate) / ISaveHandler.func_22093_e | dj.a(boolean,pj) / om.e() |

This is a test adapter, not a general source-merge or reobfuscation ledger. Reflection validates runtime descriptors; hashes constrain the original input.

## Actual commands

From repository root (each output was fresh):

```sh
python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar reference/source/beta-1.7.3/server/original/minecraft_server.jar --namespace original --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-control/original-1
python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar reference/source/beta-1.7.3/server/original/minecraft_server.jar --namespace original --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-control/original-2
python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar build/libs/Minecraft-server.jar --namespace named --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-control/built-1
python3 tools/compare_beta_terrain.py --original run/terrain-control/original-1/world --candidate run/terrain-control/original-2/world --output .plan/status/evidence/2026-09-09-a1-terrain-control/oracle-repeat
python3 tools/compare_beta_terrain.py --original run/terrain-control/original-1/world --candidate run/terrain-control/built-1/world --output .plan/status/evidence/2026-09-09-a1-terrain-control/candidate
/usr/lib/jvm/java-8-openjdk/bin/javac -cp run/terrain-control/original-1/classes -d run/terrain-control/original-1/classes tools/BetaTerrainHarnessTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp run/terrain-control/original-1/classes BetaTerrainHarnessTest
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/import_beta_sources.py --verify
/usr/lib/jvm/java-8-openjdk/bin/javap -classpath reference/source/beta-1.7.3/server/original/minecraft_server.jar -c -p dj dp he ie eg
```

All above exit 0. A Python assertion compared inventory(Path('reference')) to .plan/status/evidence/2026-09-08-a1-references/inventory.json, and compared all three request traces/logs byte-for-byte; PASS in summary.json. Each result.json contains the exact child process argument arrays. The original repeat and candidate capture ran concurrently in separate JVMs and directories; there is no shared mutable game state.

## Interpretation and next action

I-02 is resolved for this bounded test fixture: original repeatability and appropriate candidate parity now pass under declared controls. The previous uncontrolled startup failures remain valid diagnostic evidence and are not relabeled. RNG observation confirms generation uses the world RNG even without ticks (487 calls in Overworld, 6 in Nether); it does not alone distinguish every cause of the prior startup differences.

Next extend controlled coverage to the planned seeds, coordinate boundaries and request orders with matching original controls; retain raw/pre-population and biome sampling requirements. SP, two-player, visuals/audio and shared-source reconciliation remain pending. Milestone 1.0a1 stays In progress. Recovery: rerun captures in new directories and compare; do not reuse worlds with existing chunks or mutate reference templates.

Final review: `git diff --check` PASS. Capture refusal-checks.json contains exact commands and expected nonzero exits for wrong original jar hash and existing output.
