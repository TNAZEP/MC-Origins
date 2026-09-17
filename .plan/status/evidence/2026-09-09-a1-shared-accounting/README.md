# Shared packet accounting — 2026-09-09–10

Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved existing/session changes. Session began September 9 and handoff completed September 10, Asia/Tokyo. Linux x86_64; Gradle 9.1.0/JVM21.0.12+8; compiler Temurin 8u492; codec/smoke Java OpenJDK 8u504; Python 3.14.7.

PacketCounter and Empty1 are now in src/main/java/net/minecraft/src, byte-for-byte identical to both side inputs. Removed the two duplicate pairs from client/server. The package-private classes and compatibility constructor remain unchanged. Packet's side registries, handlers, accounting fields and creation path are unchanged. There are now three shared Java classes including BetaStringCodec, 676 client and 442 server Java sources.

## Results

| Check | Result | Evidence |
|---|---|---|
| Four-way original/built accounting baseline | PASS | before/result.json: 566 identical observations |
| Four-way comparison after move | PASS | after/result.json; matches baseline and retains previous 553 codec observations |
| Clean shared/client/server build | PASS | build.log; all nine tasks executed, main compiles three classes |
| Single shared copies in both artifacts | PASS | verification.json: identical to main output; no stale copies under side class outputs |
| Dedicated startup/offline login/save/stop | PASS | smoke/result.json: protocol 14, exit 0, no observed client/native class loading |
| Shared-aware source inventory | PASS | inventory/: 57 packets, 60 reviewed classes, 998 original descriptors |
| Python tools | PASS | python-tests.txt: 16 tests |
| Current provenance/reference preservation | PASS | 1,210 current files, 9 evolution entries; original reference inventory unchanged |
| Graphical/two-player/terrain/full packet parity | NOT RUN | No broader acceptance claimed |

The 13 added observations snapshot all per-ID counts/reported byte totals after existing codec cases, exercise packet 23 values 0/1/-1/INT_MAX and packet 131 payload sizes 0/1/255, verify rejected IDs and truncated packets do not change the observed totals, and exercise detached counter negative-size accumulation plus int/long overflow. Before/after reports are identical across both pristine originals and both builds. Accounting uses getPacketSize, not wire lengths: original packet 23 reports 6/6/6/0 in the tested cases; packet 131 reports 4+payload length. The shared code preserves these quirks.

The original counter classes nv/client and ir/server expose private a(int count)/b(long bytes) fields and a(int) increment method; the harness accesses the recorded mappings through reflection. Detached overflow setup touches only a test-created counter. Real static counters accumulate through the original Packet.readPacket path. Reflection fixtures do not replace live gameplay or concurrency testing.

IMPORT.json remains unchanged. EVOLUTION.json adds four removed side paths (current SHA null) and two shared destinations with original hashes and per-entry evidence. tools/verify_working_sources.py accepts these declared removals and verifies the exact current tree. inventory_beta_packets.py now resolves common sources plus each side and rejects same-side/shared duplicate names; it records actual source paths. The generated inventory still leaves the previously reviewed EnumArt constant alias as mechanical REVIEW, not an unresolved new mismatch.

## Commands

```sh
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-09-a1-shared-accounting/before
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline clean build reportBuildEnvironment
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-09-a1-shared-accounting/after
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/shared-accounting --probe-login
python3 tools/inventory_beta_packets.py --output .plan/status/evidence/2026-09-09-a1-shared-accounting/inventory
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/verify_working_sources.py
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline prepareClientNatives copyClientLibraries
```

Build and smoke use approved execution for local daemon/network sockets. Test directories were fresh. Result JSONs record exact child command arrays, input/tool hashes and runtime. Python assertions verified all three shared class entries equal main output, occur once in each jar, have no side output copies, preserve before/after TSVs, preserve previous 553 rows and leave inventory(reference) equal to the acquisition snapshot. Those results and new artifact hashes are in verification.json. The clean build required restoring client native/library copies; client-runtime-files.log records that task. No user worlds, source references or build dependency declarations were changed.

## Continuation

Next review a concrete registry/handler boundary for the small shared control-packet group; Packet references all 57 leaf types and NetHandler callbacks, so moving a leaf alone still introduces a source-set cycle. Preserve side callback signatures and producer semantics, and use the existing byte oracle as the regression gate. Full packet unification, live movement/SP/two-player/visual/audio and remaining terrain qualification stay open. Milestone 1.0a1 remains In progress.

Recovery: rebuild with wrapper; run optional codec/smoke tests in fresh directories. IMPORT.json and EVOLUTION.json retain original/current paths and hashes. No commit, publication or user-save migration performed. Final git diff --check PASS.
