# Saved-terrain baseline investigation — 2026-09-09

Base revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus prior uncommitted build/login work, preserved. Scope: independent read-only Beta McRegion/NBT comparison and baseline reproducibility investigation. No game Java/resource changes or source merging.

## Outcome

Overall terrain parity is **FAIL**, and the current startup fixture is **not a qualified golden baseline**. The unchanged original server also differs from itself on repeat. Do not infer an Origins regression from these results, mask lava differences, or change Beta RNG/gameplay to make the comparison pass.

| Comparison | Result | Observations |
|---|---|---|
| Prior original/built startup smoke worlds | FAIL | Same seed 8675309, different spawn: original (32,64,-16), candidate (88,64,106). 1,248 vs 1,243 chunks; 1,042 context/missing/field differences |
| Identical level.dat template, original vs built | FAIL | Same seed/spawn and 1,248 chunk coordinates; 32 Overworld chunks differ. All 623 Nether chunks match |
| Identical template, original vs original repeat | FAIL | Same original jar/runtime/metadata; same 1,248 coordinates, 32 Overworld chunks differ. All 623 Nether chunks match |
| Decoder/comparison and existing inventory tests | PASS | 8 tests, including single-block mutation, malformed input, negative region coordinates, gzip/zlib and missing chunks |
| Original/built/repeat server startup-stop | PASS | Three new headless loopback runs, no players; recorded JSON/logs retained |

Controlled original/built changed fields: Blocks in 29 chunks, Data in 29, BlockLight in 12. HeightMap, SkyLight and TerrainPopulated match across this controlled pair. The 79 differing block bytes are transitions only among air (0), flowing lava (10), and still lava (11); see block-differences.json. Original repeat differs in Blocks/Data in 27 chunks and BlockLight in 17. These are diagnostic observations, not permission to exclude those fields.

## Why the first fixture was insufficient

Source evidence: World.rand is initialized with new Random() and the new-world spawn search consumes it (src/server/java/net/minecraft/src/World.java, generateSpawnPoint). The terrain seed does not fix spawn. MinecraftServer startup requests chunks relative to spawn with x-outer/z-inner offsets -196..196 in steps of 16, first Overworld then Nether, draining lighting work after each request. Different spawns therefore alter requests and population neighbor availability.

The controlled runs use an identical existing level.dat and no region files, avoiding the new-world spawn search while preserving normal startup generation. This is a synthetic startup fixture, not the full new-world scenario. The level.dat came from the original server's first smoke run; it was copied untouched into reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat. SHA-256: 9b35eadf49402b56f2f06a6b4ede4639d5df7733083b2471ddeed5b2db03e049.

Source-informed hypothesis for remaining variation: lava flow consumes random choices in BlockFlowing.updateTick; World.scheduleUpdateTick may call updateTick with the unseeded World.rand while scheduledUpdatesAreImmediate is set during WorldGenLiquids generation. The normal tick loop also uses wall-clock time. Repeat variability is measured; the exact causal call sequence has not been instrumented. No RNG call count, tick count or chunk-request trace was captured, so this does not satisfy the controlled WORLDGEN gate.

## Tool and canonical format

`tools/compare_beta_terrain.py` reads both input worlds without writing to them; refuses an existing output and output nested inside either world. Supports Beta tags 1..10, gzip/zlib chunk records, raw modified-UTF string/name bytes and known ASCII field names. Validates compound types, chunk coordinates against region slots, allocations, compression, nesting/lengths and the fixed Beta array sizes. It requires nonempty Overworld and Nether inputs; it is not a modern persistence codec.

`origins-beta-saved-terrain-v1` compares seed/spawn, dimension/chunk coordinate set, TerrainPopulated and SHA-256 of full Blocks (32768), Data/SkyLight/BlockLight (16384 each), HeightMap (256) byte arrays. It does not normalize differing terrain. JSON key order is canonicalized; world-file hashes are retained separately and do not participate in terrain equality. Excluded: file timestamps/compression layout, LastUpdate, entities, tile entities, scheduled ticks, world time, weather, player data and other NBT fields. Therefore even PASS would mean only equality of these saved terrain fields, not full world/persistence/gameplay parity.

## Actual commands

```sh
python3 tools/compare_beta_terrain.py --original run/smoke/original-server-network/smoke-world --candidate run/smoke/built-server/smoke-world --output .plan/status/evidence/2026-09-09-a1-terrain/comparison
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar reference/source/beta-1.7.3/server/original/minecraft_server.jar --output run/smoke/terrain-original --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/terrain-built --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat
python3 tools/compare_beta_terrain.py --original run/smoke/terrain-original/smoke-world --candidate run/smoke/terrain-built/smoke-world --output .plan/status/evidence/2026-09-09-a1-terrain/controlled
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar reference/source/beta-1.7.3/server/original/minecraft_server.jar --output run/smoke/terrain-original-repeat --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat
python3 tools/compare_beta_terrain.py --original run/smoke/terrain-original/smoke-world --candidate run/smoke/terrain-original-repeat/smoke-world --output .plan/status/evidence/2026-09-09-a1-terrain/oracle-repeat
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/import_beta_sources.py --verify
```

Comparison commands exit 1 (FAIL), smoke runs exit 0 (startup/stop PASS), 8 tool tests pass. Socket access for fresh servers required approved escalation. No game build rerun: game/build inputs unchanged. Tests/read-only checks run with Python 3.14.7 on Linux x86_64. All fresh servers use OpenJDK 1.8.0_504-b01, -Xmx512M, headless mode, console stop on readiness and identical template hash. Original jar SHA-256 033a127e4a25a60b038f15369c89305a3d53752242a1cff11ae964954e79ba4d; built jar dde3a5627a65c6310977e202efddaffd52c02f515e129e87918899348dc3e7b7. Per-run result.json retains complete argv, port, runtime and process-log hashes.

Snapshots and differences are in comparison/, controlled/, oracle-repeat/. summary.json summarizes results; tests.txt retains test output; terrain-* directories retain fresh server logs/results/properties. Each snapshot hashes its input level.dat and .mcr files. The original and candidate must be compared on the same request/population/tick/RNG schedule before interpreting differences as implementation defects.

## Handoff and recovery

I-02 tracks insufficient startup fixture control. Next: a bounded generation harness that fixes non-terrain RNG state and tick/request/population schedule for both executable oracles, with those controls explicit and outside game source. Qualify original-vs-original repeat before using it as the candidate oracle; retain pre/post-population stages. Expand to the planned signed-seed/order/coordinate suite only after that harness is stable. No seed changes or exclusion of lava/lighting to force PASS.

Two-player, SP pause, visual/audio and full persistence fixtures remain NOT RUN. Shared source root remains empty. Milestone stays In progress. Existing worlds and reference source/binaries were not modified; new disposable outputs are under run/smoke and an original-derived metadata fixture is under the ignored reference fixture directory. Keep hashes and failed comparisons; no game rollback needed.
