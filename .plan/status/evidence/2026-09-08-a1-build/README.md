# a1 pinned build and dedicated-server smoke

Session began 2026-09-08; checkpoint finalized 2026-09-09 (Asia/Tokyo). Revision: `e1eaaac7d06a41452762eb92fe4bf3fec5670968` / main, clean on entry. Prior inventory/import work had been committed by the user since the previous handoff; the source snapshot was reverified before editing. This session's changes remain uncommitted.

## Result

PASS: Gradle wrapper 9.1.0, explicit Java 8 bootstrap toolchain, separate main/client/server source sets, pinned direct dependencies, dependency locks/SHA-256 verification, client/server artifact tasks, isolated run tasks, Linux client native preparation. No game Java/resource bytes changed. `main/java` still contains zero Java files; compilation of both sides is not unified-source acceptance.

Two clean builds produce identical jar bytes. The server artifact contains no client/native library classes and has no external runtime dependencies. All packaged compiled classes and source resources were compared byte-for-byte with build/source inputs. No source/reference tree content leaked into the artifacts. Nine client jars additionally match the SHA-1 values in the recorded upstream RetroMCP metadata.

Original Beta server and built server both passed loopback startup/shutdown on OpenJDK 8u504. The built server also passed on the Temurin 8u492 runtime selected by Gradle. Each reached Done, accepted console stop, exited 0 and produced smoke-world/level.dat. Verbose class loading showed no net.minecraft.client, LWJGL or Paulscode classes. No player connected; no save-content or worldgen comparison was made.

Client launch, local SP, audio assets, multiplayer/two-player, visual/worldgen parity and remaining milestone gates are NOT RUN. Gradle `test` reports NO-SOURCE, which is not a game-test pass. Compilation emitted existing deprecation/unchecked-operation notes; no compile-fix patches were needed.

## Environment and pins

Linux x86_64, kernel 7.2.0-1-cachyos, Python 3.14.7. Gradle ran on OpenJDK 21.0.12+8. Its toolchain selector chose Eclipse Temurin 1.8.0_492-b09 at /usr/lib/jvm/java-8-temurin. Server comparison also used /usr/lib/jvm/java-8-openjdk (1.8.0_504-b01). See [environment.log](environment.log). Java 8/LWJGL 2 remain an a1-only bootstrap; final Java 25/LWJGL 3 requirements are unchanged.

Gradle distribution SHA-256: `a17ddd85a26b6a7f5ddb71ff8b05fc5104c0202c6e64782429790c933686c806`, checked against https://services.gradle.org/distributions/gradle-9.1.0-bin.zip.sha256 before extraction and pinned in wrapper properties. Gradle 9.1 supports Java 25 per [upstream release notes](https://docs.gradle.org/9.1.0/release-notes.html), allowing the same wrapper baseline for later migration. Core Java plugin only; no third-party build plugins.

Direct client dependencies match supplied RetroMCP declarations: LWJGL/lwjgl_util 2.9.4-nightly-20150209, JInput 2.0.5, JUtils 1.0.0, codecjorbis 20230120, codecwav 20101023, libraryjavasound 20101123, librarylwjglopenal 20100824, soundsystem 20120107. Linux LWJGL/JInput classifiers are pinned too. JOrbis comes from the exact MCPHackers repository declared by upstream artifact metadata; other libraries come from libraries.minecraft.net. No Maven transitive dependency inference: artifact-only historical repositories and all required coordinates declared explicitly. Locks in gradle.lockfile and checksums in gradle/verification-metadata.xml.

## Actual commands and outcomes

All repository commands below ran at the root. Network and Gradle/process socket access required escalation; no game artifacts were published.

| Command/check | Result | Evidence |
|---|---|---|
| `git status --short`; `git rev-parse HEAD`; `python3 tools/import_beta_sources.py --verify` | PASS | Clean start at revision above; all 1,211 working inputs unchanged |
| `curl -fL --max-time 120 https://services.gradle.org/distributions/gradle-9.1.0-bin.zip -o /tmp/origins-gradle-9.1.0-bin.zip` and SHA-256 sidecar download | PASS | Distribution checked with Python hashlib against sidecar before extracting |
| `/tmp/origins-gradle/gradle-9.1.0/bin/gradle --gradle-user-home /tmp/origins-gradle-cache --no-daemon wrapper --gradle-version 9.1.0 --gradle-distribution-sha256-sum a17ddd85a26b6a7f5ddb71ff8b05fc5104c0202c6e64782429790c933686c806` | Initial FAIL, approved retry PASS | Sandbox wildcard-IP/socket denial; retry generated actual wrapper scripts/jar/properties |
| `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon clean build prepareClientNatives copyClientLibraries --write-locks --write-verification-metadata sha256` | Initial FAIL, corrected retry PASS | JOrbis 20230120 absent from Mojang repo; corrected to MCPHackers artifact URL from reference metadata, then all classes/dependencies built. One sandbox retry also failed before socket-enabled execution |
| `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline clean build prepareClientNatives copyClientLibraries` | PASS | [build.log](build.log), ordinary strict checksum verification enabled; test NO-SOURCE |
| Python hashlib comparison to first-artifacts.json, ZIP CRC/content checks, dependency SHA-1 checks, reference inventory equality | PASS | [verification.json](verification.json); identical jar bytes and preserved source/reference inputs |
| `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline reportBuildEnvironment` | PASS | [environment.log](environment.log); server libraries empty, shared Java files zero |
| `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline runClient runServer --dry-run` | PASS | [launch-tasks.log](launch-tasks.log); task graph only, not game launch evidence |
| Server smoke commands below | PASS | Per-run JSON/properties/process logs, retained here |
| `git diff --check` | PASS | Final authored-file whitespace review |
| Client play, two-player, fixture parity, a1 promotion | NOT RUN | Further qualification required |

Server smoke commands (the original command first failed at sandbox socket creation in run/smoke/original-server; reran in a fresh directory with approved socket access):

```sh
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar reference/source/beta-1.7.3/server/original/minecraft_server.jar --output run/smoke/original-server-network
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/built-server
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-temurin/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/built-server-temurin
```

Each JSON records exact Java argv/runtime, jar/log hashes, port, seed, readiness, stop, save presence and forbidden-class scan. `original-*`, `built-*`, and `built-temurin-*` files distinguish the three runs. Generated worlds remain ignored under run/smoke, outside reference directories. The no-client-class observation covers this startup path; it is not a comprehensive future class-loading proof.

## Artifacts and limits

- Minecraft.jar SHA-256: `4332144c3869f46afc217847b5ab3c77163633d156ec90759261b70b6cc8bfd8` (678 classes).
- Minecraft-server.jar SHA-256: `dde3a5627a65c6310977e202efddaffd52c02f515e129e87918899348dc3e7b7` (444 classes).

Reproducibility is same-environment only. Java toolchains are selected at major version 8; a different vendor/patch may produce different bytes. Distribution/third-party dependency artifacts are checksum-pinned. The client jar requires adjacent libraries/natives or the runClient task; it is not a self-contained executable jar. Launch tasks preserve normal developer worlds under run/ across clean builds. Linux x86_64 client launch is configured but untested; other client platforms deliberately fail before launch pending native configuration. Source unification and patch-name mapping remain unresolved.

## Next and recovery

Next bounded work: qualify the client launch and local SP on the original and built artifacts, acquire/verify required audio assets, then paired multiplayer/fixed-camera/seed-order fixtures before shared-code reconciliation. Keep reference binaries and original source immutable. Inspect [BUILDING.md](../../../../BUILDING.md) for runnable commands and bootstrap requirements.

No game source/resource changes, migrations, existing worlds or reference bytes modified. New disposable smoke worlds live under ignored run/smoke. `clean` removes build outputs only; it does not remove run saves. Rebuild artifacts with the wrapper; use a fresh --output for each smoke run. Do not clear user work or rewrite input checksums to bypass failures. Milestone remains In progress.
