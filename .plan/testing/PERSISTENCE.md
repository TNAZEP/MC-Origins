# Persistence and migration tests

## Fixture inventory

Build small, reproducible Beta fixtures containing every supported block/metadata state, item variant/durability, container and block entity, entity type, player inventory/armor, time/weather/spawn state, maps and dimension data. Include moving/transient blocks and pending updates where Beta actually persists them. Separate legal gameplay fixtures from malformed/invalid-metadata stress fixtures. Store semantic manifests and source hashes, never just a screenshot.

Include empty and dense chunks; negative coordinates; chunk/region boundaries; Overworld and Nether; singleplayer and two-player dedicated worlds; far explored chunks; large palettes; zero/one-entry palettes; packed-long boundaries; full and missing sections; separate entity data and player files. Synthetic Y=128..255 fixtures test codec capacity only, not gameplay.

## P-01: Origins round trip

Snapshot a controlled supported world; save, quit and reload in Origins; compare semantic state and the subsequent tick trace. Repeat with changed registry bootstrap order and fresh runtime. Verify Beta-only state retained by any sidecar. Test that generated chunks are not repopulated after reload.

## P-02: Safe Beta import

Hash an untouched Beta world; convert into a separate destination; validate all dimensions/players/auxiliary files; play and reload. Rehash the source and require equality. Test deterministic/restartable conversion, rejected unknown records, interruption at each stage, full disk and corrupt input. Incomplete destinations must not appear complete or replace the source.

## P-03: Direct vanilla 1.17.1 consumer

Copy the normal Origins save without manual NBT edits or an exporter. Open in unmodified 1.17.1 client; load the known explored areas in both dimensions; inspect terrain, block variants, entities, block entities, inventories, health/spawn/time and maps. Play, save, quit and reopen. Repeat the appropriate dedicated-server route with explicit username/UUID mapping and two players. Record logs, missing-data warnings and semantic diffs. No required Origins datapack/mod may be present.

## P-04: Paired vanilla-upgrade control

1. Create and freeze one Beta fixture B and its explored-chunk list. Preserve an immutable copy.
2. Control C: upgrade a copy using unmodified vanilla releases through a recorded sequence. A **candidate** sequence is b1.7.3 -> 1.2.5 -> 1.12.2 -> 1.13.2 -> 1.16.5 -> 1.17.1. It is a test recipe to qualify, not a proven universal conversion chain.
3. At each hop verify every intended region/chunk/dimension/player was processed. Use supported conversion/optimization facilities where available; merely opening at spawn does not upgrade the whole world. Add intermediate releases if a verified hop cannot consume the previous format, and record why.
4. Candidate O: import the same B into Origins, save without intentional gameplay edits, then open that save directly in unmodified 1.17.1.
5. At a controlled immediate-load checkpoint and after the same short play scenario, compare C and O semantically. Normalize only nondeterministic IDs/timestamps with explicit rules. Do not normalize away missing entities, inventory losses or changed block types.
6. Classify every difference using COMPATIBILITY. Expected vanilla changes require evidence in C. An unexplained O-only failure is an Origins defect.

Player identity and default modern-only metadata need an explicit policy shared by the control and candidate observations. If the control itself loses a field, document that result; still preserve more in Origins where feasible without compromising direct vanilla play.

## P-05: Forward 1.18.x qualification

Use a pinned release, proposed **1.18.2**, on separate copies of C and O after 1.17.1 save/reload. Explore the same old/new chunk boundaries and inspect retained structures, terrain transitions, biome blending and below-zero conversion. Record the exact upgrade result and screenshots/dumps. Do not infer blending success solely from DataVersion or promise perfectly seamless borders. A later vanilla release's behavior is its own test target.

## P-06: Unsupported reverse load and recovery

After vanilla edits a copy, attempt Origins detection in a safe test harness. It must not silently apply stale preservation metadata, erase modern-only content or write an unsupported profile. Include modified sidecars, mixed-version chunks, unknown registry keys and partial saves. Recovery behavior is explicit and preserves a user-restorable copy.

## Passing threshold

All required supported record types are accounted for; no unexplained data loss; source immutability passes; native saves reload and play in both 1.17.1 client/server routes. Maintain a field-by-field mapping ledger with evidence. The extension `.mca`, a successful NBT parse, or an empty-world launch is not enough.
