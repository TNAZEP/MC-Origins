# Shared packets and transport

September 10–11, 2026 (Asia/Tokyo). Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968, main plus preserved changes. Linux x86_64; Gradle 9.1.0/JVM 21.0.12+8; existing Java 8 bootstrap compiler; smoke OpenJDK 8u504.

## Implementation and correspondence

All 57 registered packets, Packet, NetHandler and socket transport now compile once in main. Client/server codec bodies match after recorded aliases; constructor/method unions retain both APIs. The original packet order, direction checks, numeric layouts, send queues, reader/writer scheduling and close delays are retained. Login/application handlers remain side-owned. NetworkManager includes the server's handler replacement/address/queue APIs. Shutdown thread differences were names only. Painting enum is also shared without value changes.

PacketFactory on each side contains its original entity/world constructors, including coordinate conversion and chunk compression. These are producer adapters, not alternate wire implementations. Packet item fields accept ItemStackData; decoding creates PacketItemStack with no gameplay dependencies. ItemStack.fromPacket restores the receiving host's concrete stack and animation state. Metadata conversion happens through the receiving DataWatcher item codec; no global side initialization or reflection hook was added. Original copy-versus-reference producer semantics are retained. Shared metadata writes valid item IDs directly; matching incidental invalid-ID exception behavior is not an acceptance objective.

134 main / 553 client / 319 server Java files. Original import retained, current provenance updated separately. No reference sources or user worlds changed. Full entity/world merge remains outstanding.

## Verification

- PASS: `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build` after packets and after transport (packets-build.log/build.log). Main has no side/reference classpath. Gradle requires its local lock socket outside the sandbox.
- PASS: `/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/libs/Minecraft-server.jar -d build/smoke-tests tools/PacketFlowSmokeTest.java tools/ItemStackSmokeTest.java tools/MetadataSmokeTest.java`; `java -cp build/smoke-tests:build/libs/Minecraft-server.jar net.minecraft.src.PacketFlowSmokeTest`, repeated with Minecraft.jar. Actual framing, slot snapshots, inventory nulls, place/click payloads, metadata host conversion, mob producer and typed dispatch verified. Java uses the same JDK path above.
- PASS: packet-stage `python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/shared-packets --probe-login`. Offline protocol-14 login, save presence, exit 0; no forbidden client/native loading observed.
- PASS: same smoke command with `--output run/smoke/shared-network` after transport merge; offline login, shutdown/save presence, exit 0 and no forbidden classes observed. Dedicated artifact SHA-256 `b0cf44f4f6dc2ea9ceb783edf8da322897ce6cb9a8672415706b5ad9a7cc2814`. See server-smoke.json.
- PASS: `python3 tools/verify_working_sources.py` (1,095 files / 485 evolution entries), `git diff --check`.
- NOT RUN: graphical client, actual two-player gameplay and SP play/save acceptance on this build. Older owner reports apply to earlier builds only. Full a1 is not complete.

Existing exhaustive original-jar diagnostics were not rerun. Their reflective constructor assumptions may require an adapter update after producer extraction; no diagnostic PASS is claimed. Functional checks above exercise the new supported boundary.
