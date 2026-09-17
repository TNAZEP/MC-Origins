# Shared metadata and version identity — 2026-09-10

Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved changes. Linux x86_64, Gradle 9.1.0/JVM 21, Java 8 bootstrap compiler/runtime.

Shared DataWatcher retains server dirty-list and client receiver methods. A small explicit ItemCodec isolates the remaining side ItemStack graph. Both adapter copies preserve Beta item lookup, count/damage and signed reads. Shared string helpers remove the Packet dependency. Integer getter calls reconciled. ItemStack/model and Packet/NetHandler themselves remain unmerged.

OriginsVersion.VERSION = 1.0a1 supplies Minecraft: Origins 1.0a1 for menu, debug overlay, display/launch frame, server GUI, startup and crash diagnostics. Owner direction recorded in AGENTS.md, VERSIONING.md and WORKFLOW.md: update at the start of each alpha/beta/release. Protocol 14 and save format unchanged; build metadata can still say dev.

PASS: both builds (build.log), functional metadata snapshot/dirty-list/receiver/item/coordinate tests in both artifacts (metadata-client.log, metadata-server.log), dedicated startup/login/stop (server-smoke.json), actual new version banner (version-banner.txt), source provenance and git diff --check. Graphical verification and full multiplayer play NOT RUN. No milestone promotion.

The initial isolated metadata test failed because it initialized Item before normal StatList startup, exposing Beta's static initialization cycle. Fixed the test to initialize StatList as both game entry points do; both tests then passed. No game initialization workaround introduced.

Commands run from repository root (Gradle/server given local socket permission):

```sh
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build
/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/libs/Minecraft-server.jar -d build/smoke-tests tools/MetadataSmokeTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.MetadataSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft.jar net.minecraft.src.MetadataSmokeTest
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/metadata-version --probe-login
python3 tools/verify_working_sources.py
git diff --check
```

Build and metadata output saved in the logs above; server process log remains in run/smoke/metadata-version. No references/user saves changed. Provenance in EVOLUTION.json. Next: continue ItemStack/model reconciliation, then shared packets; remove temporary side adapters when their model is common.
