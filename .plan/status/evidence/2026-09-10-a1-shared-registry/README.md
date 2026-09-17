# Shared registry rules — 2026-09-10

Base e1eaaac7d06a41452762eb92fe4bf3fec5670968/main plus preserved work. Linux x86_64, Python3.14.7, Gradle9.1.0/JVM21.0.12+8, Temurin8u492 compiler; OpenJDK8u504 codec/smoke runtime.

## Implemented boundary

BetaPacketRegistry in main owns the four maps/sets and the original duplicate-ID/class, class lookup, ID lookup and receive-direction rules. Both Packet implementations delegate these operations. Each side retains the exact static class-literal registration list, sequence and boolean flags; packet creation still uses Class.newInstance in Packet, accounting remains there, and all typed NetHandler callbacks remain unchanged. The common registry depends only on JDK collections/Class, not Packet, a side root, reflection-based registration, service loading, game singletons or reference jars. A new registry instance is initialized at the beginning of each Packet class initialization, preserving the previous map initialization order and lifetime.

This is the bounded first registry boundary, not a move of Packet or the four control leaf classes. Those classes still require the typed handler/remaining packet graph. See the reviewed architecture checkpoint for the migration constraints.

## Verification

| Check | Result | Evidence |
|---|---|---|
| Before/after original-client/original-server/built-client/built-server | PASS | 1,338 identical observations per jar; before/after result JSONs |
| Previous codec/accounting observations | PASS | First 566 rows preserved exactly |
| Registry observations | PASS | 772 added rows: duplicate ID/class failures, ID lookup -1..256, all 256 IDs in both receive directions with empty payload |
| Both builds | PASS | build.log; main and side compile/artifact tasks succeed; test NO-SOURCE |
| Shared artifacts | PASS | verification.json: all four shared classes occur once and equal main output in both jars |
| Dedicated startup/offline login/stop | PASS | smoke/result.json: protocol14, exit0, save presence, no observed client/native class loads |
| Tool/provenance checks | PASS | 16 Python tests, 998 original descriptor inventory checks, 1,211 current files/10 evolution entries; reference inventory unchanged |
| Full packet payload/handler/two-player/SP/graphics/terrain retest | NOT RUN | Empty-payload ID probes do not qualify every decoder; broader gates remain open |

Duplicate class diagnostics contain mapped Java class names, so that single fixture compares exception type instead of namespace-dependent message text. All other observation formatting remains unchanged; duplicate-ID text is compared. The tests perform duplicate failures before the lookup/direction sweep so later results check that those failed registrations did not alter the registry. Invalid direction is rejected at the ID gate; allowed empty payloads retain the decoder's existing EOF/error behavior. These are behavior comparisons, not a claim that all 57 packet payload formats have complete fixtures.

Commands (exit0):

```sh
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-10-a1-shared-registry/before
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-10-a1-shared-registry/after
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/shared-registry --probe-login
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/inventory_beta_packets.py --output .plan/status/evidence/2026-09-10-a1-shared-registry/inventory
python3 tools/verify_working_sources.py
```

Build/smoke ran with approved local socket access. JSONs record actual child arguments, input/tool/artifact hashes and runtimes. Python assertions compared reports to both pre-change and prior accounting evidence, all shared entries against main output, and inventory(reference) against the acquisition inventory; verification.json records PASS. IMPORT.json is unchanged. EVOLUTION.json retains earlier Packet evidence and current delegate hashes, plus the new registry provenance. User worlds/reference inputs are unchanged. git diff --check PASS.

Next: review the ItemStack/DataWatcher and entity/world-constructor dependencies that prevent a complete shared Packet/typed-NetHandler group, then select the next independently buildable slice. Keep class-literal registration and typed dispatch until their replacements are verified; no temporary side-root compile dependency. Milestone remains In progress. Recover by rebuilding and rerunning codec/smoke in fresh directories; no save migration or publication performed.
