# Continuing a1 foundation merge — 2026-09-10

Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved changes. Linux x86_64; Gradle 9.1.0/JVM21, Java8 bootstrap compiler/OpenJDK8u504 runtime. Owner requested uninterrupted work towards the full merge; this is a recoverable intermediate checkpoint, not milestone completion.

Shared material/map-color family, basic chunk/storage/tick records and enums, MathHelper, vector/hash groups, four noise implementations, translation/achievement GUID helpers, region-file group, WorldInfo and statistics model. Side API aliases are recorded in JSON files here. Pure groups matched after identifier reconciliation; additive unions retain client vector/math/formatting accessors and server hash lookup methods.

WorldInfo accepts a prepared NBT player snapshot; each side save host now serializes the original first player at the existing save call site. Stats use common StatRegistry map/all/basic lists, referenced by existing StatList fields; host StatList still defines built-ins and retains block/item initialization ordering. Client stat formatting methods are part of the shared API; no graphics dependencies. World generation calculations, material values, region format and tick ordering preserved. This is Beta storage, not the future 1.17.1 migration.

PASS: both builds (build.log); existing item/metadata smoke tests in both jars; NBT smoke; focused world metadata/player snapshot plus region save/reopen (world-save.log); tiny raw terrain generation (terrain-result.json: three requested chunks in each dimension; no parity claim); final dedicated startup/login/stop/save presence (server-smoke.json); current source provenance and diff whitespace check. GUI/two-player/full gameplay acceptance NOT RUN. Full packet/entity/world merge remains unfinished. Version stays Minecraft: Origins 1.0a1.

One alias-discovery script initially rejected a named Block accessor; it stopped before mutations. Reviewed getNameLocalizedForStats -> translateBlockName correspondence, then continued. No game compile failures in this batch.

Commands from repository root:

```sh
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build
/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/libs/Minecraft-server.jar -d build/smoke-tests tools/ItemStackSmokeTest.java tools/MetadataSmokeTest.java tools/SharedNbtSmokeTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.ItemStackSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft.jar net.minecraft.src.ItemStackSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.MetadataSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft.jar net.minecraft.src.MetadataSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar SharedNbtSmokeTest reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat
/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/classes/java/main -d build/smoke-tests tools/WorldSaveSmokeTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/classes/java/main WorldSaveSmokeTest
python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar build/libs/Minecraft-server.jar --namespace named --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --mode raw --requests .plan/status/evidence/2026-09-10-a1-foundation/requests.tsv --output run/smoke/foundation-final-terrain
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/foundation-final --probe-login
python3 tools/verify_working_sources.py
git diff --check
```

Gradle/server used permitted local sockets. Initial intermediate smoke paths were run/smoke/foundation and foundation-terrain; final artifact results use foundation-final*. Outputs are in the named logs/results or tool session output. References and user worlds not edited; test files under run/smoke only. IMPORT unchanged; EVOLUTION records current hashes and origins. Continue the shared-model and packet producer/handler boundaries; keep policy-bearing entity/world code separate until reconciled.
