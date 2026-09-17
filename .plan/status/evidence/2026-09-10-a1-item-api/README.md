# Item API reconciliation — 2026-09-10

Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved uncommitted work. Linux x86_64; Gradle 9.1.0/JVM 21, Java 8 bootstrap compiler and OpenJDK 8u504 smoke runtime.

Reconciled server Item and subclass/caller names: field_28021_bb -> mapItem, field_31022_bc -> shears, func_25005_e -> isDamagable, func_25007_a -> onBlockDestroyed, func_25006_i -> getStatName, func_28018_a -> onUpdate, func_28020_c -> onCreated. Item placement getMetadata -> getPlacedBlockMetadata in the item hierarchy. Server map discriminator func_28019_b -> isMap; its EntityPlayerMP scheduling policy remains server-only. Rendering accessors remain client-only. All method bodies retained. Moved identical EnumToolMaterial to main and removed both copies. Full Item/ItemStack merge remains pending, including static registration, statistics and world/entity dependencies.

PASS: both builds (build.log); stack/inventory and tool/shears/placement callback checks in both built jars (item-client.log, item-server.log); server startup/login/stop/save presence (server-smoke.json); current provenance (1,197 files/129 entries); git diff --check. GUI, live crafting/map updates and full multiplayer actions NOT RUN. No milestone promotion. Version remains Minecraft: Origins 1.0a1. No reference or user-world edits.

Commands from repository root; Gradle/server use permitted local sockets:

```sh
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build
/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/libs/Minecraft-server.jar -d build/smoke-tests tools/ItemStackSmokeTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.ItemStackSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft.jar net.minecraft.src.ItemStackSmokeTest
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/item-api --probe-login
python3 tools/verify_working_sources.py
git diff --check
```

Build/item outputs redirected to named logs; detailed server log in run/smoke/item-api/process.log. changed-files.txt lists 23 changed game paths; EVOLUTION.json records hashes and shared origins. Next: reconcile the Item/StatList registration dependency and remaining concrete stack host APIs; do not merge client rendering or server map scheduling policy blindly.
