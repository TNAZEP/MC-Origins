# Shared item-stack data — 2026-09-10

Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved work. Linux x86_64, Gradle 9.1.0/JVM 21, Java 8 bootstrap compiler and OpenJDK 8u504 smoke runtime.

ItemStackData now owns ID/count/animation/damage storage, splitting, copying, NBT persistence, damage access and item/stack equality. Both concrete side ItemStacks extend it and supply a typed createStack factory. Copies/splits still construct the appropriate side ItemStack and reset transient animation state. Gameplay callbacks, item lookup, durability rules, stats, rendering helpers and world/entity dependencies remain side-local. This is shared stack data, not completion of the full ItemStack or packet merge.

Server stack names reconciled with the client: func_20117_a -> copyItemStack, func_28144_c -> isStackEqual, func_21132_c -> isStackable, func_25124_a -> onDestroyBlock, func_28143_a -> updateAnimation, func_28142_b -> onCrafting. Empty destruction hook func_577_a/client func_1097_a -> onItemDestroyed. Updated call sites; callback bodies and policies retained. Previous private itemDamage becomes protected in the shared base for side durability code. Immutable import retained; EVOLUTION records changes and origins.

PASS: both builds (build.log); stack split/copy independence, damage, NBT restoration, null/equality and stackability checks in both artifacts (stack-client.log, stack-server.log); existing metadata synchronization on both artifacts (metadata-client.log, metadata-server.log); server startup/login/stop/save presence (server-smoke.json); provenance (1,198 files/108 entries); git diff --check. GUI/real multiplayer and gameplay callback execution NOT RUN. a1 remains In progress. Version remains Minecraft: Origins 1.0a1.

Commands from repository root; Gradle/server permitted to use local sockets:

```sh
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build
/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/libs/Minecraft-server.jar -d build/smoke-tests tools/ItemStackSmokeTest.java tools/MetadataSmokeTest.java
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.ItemStackSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft.jar net.minecraft.src.ItemStackSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.MetadataSmokeTest
/usr/lib/jvm/java-8-openjdk/bin/java -cp build/smoke-tests:build/libs/Minecraft.jar net.minecraft.src.MetadataSmokeTest
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/stack-data --probe-login
python3 tools/verify_working_sources.py
git diff --check
```

Output redirected to named logs; detailed server output in run/smoke/stack-data/process.log. References/user worlds unchanged. Next: reconcile Item callback/subclass APIs and the remaining concrete ItemStack dependencies towards the full packet/model merge. No blanket differential matrix prerequisite.
