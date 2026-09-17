# Model API and map persistence checkpoint

Work continued September 11–16, 2026 (Asia/Tokyo). Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved changes. Same Linux x86_64, Gradle 9.1.0/JVM 21 and Java 8 bootstrap environment as prior checkpoint. No commit or reference/user-world mutation.

147 shared Java classes. MapStorage and MapDataBase share persistence behind MapFileAccess (save hosts retain file locations); progress reporting, chat character values, old chunk-file patterns, bed/piston tables and step-sound descriptors are shared. Client break-sound accessors are retained; server walking-sound dispatch remains unchanged. ThreadSleepForever deliberately stays side-local: its owner and stop conditions differ.

Entity and dependent API names reconciled with overrides/callers. The server's misleading `singleplayerWorld` field is now `multiplayerWorld`, matching the client remote-world flag; values and all conditions are unchanged. All 68 common Entity method bodies match after names; extra client APIs and server inventory/sneak setters await the full model merge.

333 further field/method aliases were inferred from aligned method bodies or unique nonempty body/signature matches (model-aliases.json records each witness), applied as identifier renames, not body replacements. A redstone-ore name collision was caught by compilation: original server private `func_320_h` is the client particle helper `func_319_i`; original server activation helper `func_321_g` maps to `func_320_h`. The file was regenerated from its pre-rename snapshot with both substitutions simultaneously. Final build PASS. Keep class-scoped correspondence when future canonical names overlap old names in other classes.

Remaining-method-differences.json is an investigation queue, not approval to replace server bodies. Many differences are still names; SP/MP policies require explicit review. A conservative client World type closure contains 256 side-source classes and no non-JDK imports, providing a concrete next graph-merging scope. Full entity/world source unification remains incomplete.

## Actual verification

- PASS: `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build` (build.log), with local Gradle lock socket allowed. Initial redstone name-collision compile FAIL was corrected as described above.
- PASS: `/usr/lib/jvm/java-8-openjdk/bin/javac -cp build/classes/java/main -d build/smoke-tests tools/WorldSaveSmokeTest.java` and corresponding `java -cp build/smoke-tests:build/classes/java/main WorldSaveSmokeTest`: world metadata/region reopen, map data and persisted ID sequence. Disposable output `run/smoke/world-save-4258664640665639390`.
- PASS: PacketFlowSmokeTest on both jars after the first model batch. Final alias-only server follow-up does not replace these with a broader play claim.
- PASS: `python3 tools/capture_beta_terrain.py --java-home /usr/lib/jvm/java-8-openjdk --jar build/libs/Minecraft-server.jar --namespace named --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --mode raw --requests .plan/status/evidence/2026-09-10-a1-foundation/requests.tsv --output run/smoke/model-api-terrain`: six generated chunks, both dimensions; execution smoke, not original-byte comparison. Detailed command/environment in terrain-result.json.
- PASS: `python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/model-api --probe-login`; offline protocol-14 login, exit 0, save presence, no forbidden client/native classes observed. See server-smoke.json.
- PASS: final server PacketFlowSmokeTest; source provenance (1,084 files / 631 evolution entries) and `git diff --check`.
- NOT RUN: graphical client, SP play and actual two-player interactions on current build. Full a1 remains in progress.
