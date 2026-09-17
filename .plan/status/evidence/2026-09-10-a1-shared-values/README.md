# Shared coordinate and NBT group — 2026-09-10

Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved uncommitted changes.
Linux x86_64; Gradle 9.1.0/JVM 21.0.12+8, Java 8 compiler and OpenJDK 8u504 smoke runtime.

Merged 15 classes into main: ChunkCoordinates, ChunkPosition, NBTBase, all eleven NBT tag classes, and CompressedStreamTools. Removed their 30 side copies. Both source implementations match after the recorded naming reconciliation; existing data formats, arithmetic and stream ownership remain unchanged.

- ChunkCoordinates uses x/y/z. Updated 70 compiler-resolved server accesses across ten files; entity position fields remain posX/posY/posZ.
- NBTTagCompound.func_28110_c / func_28107_c becomes getTags.
- CompressedStreamTools.func_1138_a / func_770_a becomes readCompressed; func_1141_a / func_774_a becomes read; func_1139_a / func_771_a becomes write. Updated both-side callers. Existing descriptive gzip writer name retained.
- ItemStack, DataWatcher and typed packet graph still side-local; no host/gameplay policy change.

## Results

PASS: final client/server build (build.log), headless startup/offline login/stop/save presence (server-smoke.json), focused NBT compressed/raw read/write including world seed/spawn and nested inventory (nbt-smoke.log; also run successfully on the server's new level.dat), current-source verification (1,195 files / 78 evolution entries), git diff --check.

Intermediate build FAIL: build-first.log captures the 70 old coordinate field references; all updated before the final build. No unresolved compile errors. Graphical client, two-player gameplay and full world restart NOT RUN this session. Existing exhaustive parity suites NOT RUN; this follows ADR-0012. No milestone promotion.

## Commands

```sh
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build
mkdir -p build/smoke-tests
/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/classes/java/main -d build/smoke-tests tools/SharedNbtSmokeTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/classes/java/main SharedNbtSmokeTest reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/shared-values --probe-login
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/classes/java/main SharedNbtSmokeTest run/smoke/shared-values/smoke-world/level.dat
python3 tools/verify_working_sources.py
git diff --check
```

Gradle and server smoke ran with local socket permission. Build output redirected to build-first.log then build.log; first NBT run to nbt-smoke.log. Detailed server log remains in run/smoke/shared-values/process.log. Test reads the supplied world and writes only in-memory buffers. References and user worlds not edited. src/IMPORT.json preserved; src/EVOLUTION.json records moves, both origins and updated caller hashes. changed-files.txt lists this session's 65 changed game-source paths.

Next: continue ItemStack/model reconciliation towards common DataWatcher and packet definitions. Build and check affected paths; no new original-jar matrix prerequisite.
