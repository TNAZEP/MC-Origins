# Building and running the a1 bootstrap

Install a JDK to run Gradle (tested: Java 21) and a Java 8 JDK for the a1 Beta bootstrap code. Java 8/LWJGL 2 are temporary a1 bootstrap choices; the final Java 25/LWJGL 3 target is unchanged. Gradle 9.1.0 is pinned in the wrapper and its distribution SHA-256 is verified.

Gradle discovers installed Java 8 toolchains. For a nonstandard installation, set `ORIGINS_JAVA8_HOME` to the JDK directory. Automatic JDK downloads are disabled. Run `./gradlew reportBuildEnvironment` to see the selected compiler/runtime and side dependencies. This session compiled with Temurin 1.8.0_492-b09 and ran server smoke checks on OpenJDK 1.8.0_504 as well as the selected Temurin runtime. Toolchain discovery can choose another Java 8 patch release; record it when comparing builds.

From the repository root (`gradlew.bat` on Windows):

```sh
./gradlew clean build
```

Outputs:

- `build/libs/Minecraft.jar` — client classes/resources; requires external libraries/natives.
- `build/libs/Minecraft-server.jar` — self-contained dedicated server, no client libraries.

The `main`, `client` and `server` source sets are explicit. The 420 shared Java classes compile once; both artifacts include that output. Client presentation/SP hosting and dedicated lifecycle remain separate. There are no duplicate side class names. `test` currently reports NO-SOURCE; functional smoke commands below are separate from that task. Reference trees and jars are not on any build classpath.

## Development launches

```sh
./gradlew runServer
./gradlew runClient
```

Server working directory: `run/server/`. The server runs with `nogui`; type `stop` in its console to save and exit. All run directories are ignored by Git and survive `clean`.

Client launch is configured for **Linux x86_64 only**. It extracts pinned LWJGL/JInput natives into `build/natives/linux/`, uses the client-only library classpath and sets `user.home` to `run/client/`, so Beta stores its data under `run/client/.minecraft/` rather than your normal game directory. It uses the unmodified client main method and the development username `Player`. A graphical session is required. Client launch and local server connection are **PASS (owner confirmed)**; detailed gameplay/audio asset and old network-service qualification remain pending. Original jar resources are already in the client artifact. Other platform native configurations are not implemented yet.

The client jar is not a fat jar and cannot be launched successfully with `java -jar` alone. Use `runClient` during development. `./gradlew copyClientLibraries prepareClientNatives` materializes its external libraries/natives for later packaging work. Patch/Prism/release packaging is not implemented.

## Connecting the development client locally

`runClient` uses a placeholder session, so it cannot satisfy the server's default `online-mode=true` authentication requirement. For same-computer development, stop the server and set these existing Beta properties in `run/server/server.properties`:

```properties
online-mode=false
server-ip=127.0.0.1
```

Restart `./gradlew runServer`, launch `./gradlew runClient`, and connect to `127.0.0.1:25565` (or your configured server-port). The current local properties were updated this way on 2026-09-09, with a backup beside them. This uses Beta's existing offline mode; authentication is unchanged in game code and in newly generated server defaults. The loopback binding limits this configuration to clients on the same computer.

The protocol login exchange passed a synthetic test; the owner confirmed the graphical-client connection after this configuration change, corroborated by the server login log. An optional `--probe-login` flag on the smoke harness checks protocol-14 login before shutdown. It does not simulate player movement or certify multiplayer parity.

## Dependency and artifact verification

Versions are locked in `gradle.lockfile`; SHA-256 values are enforced by `gradle/verification-metadata.xml`. The client uses the versions declared by the supplied RetroMCP metadata. JOrbis comes from MCPHackers' artifact repository; other client artifacts come from Mojang's library repository. Repositories use explicit artifact metadata because those historical coordinates do not consistently supply Maven POMs. All dependencies are therefore declared directly, including JInput/JUtils and the Paulscode libraries. The nine library jars were also compared to upstream metadata SHA-1 values.

After the first download, the tested verification command is:

```sh
./gradlew --offline clean build prepareClientNatives copyClientLibraries
```

Do not routinely regenerate verification metadata to bypass a mismatch. A deliberate dependency change requires reviewing its provenance and updating the lock/checksum files. Two clean builds in this session produced byte-identical client and server jars; this is a same-environment result, not a cross-JDK reproducibility claim.

## Dedicated-server smoke recipe

The optional Python harness runs an explicit jar on loopback in a fresh directory, using seed 8675309 and offline mode, waits for readiness, sends `stop`, and records exit/save/class-loading evidence:

```sh
python3 tools/smoke_server.py --java /path/to/java8/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/new-built-run
```

The output directory must not exist. The server uses a transient port; an allocation race or failed bind must fail the test. `result.json`, `process.log`, properties and the disposable world remain in the output directory. Startup/shutdown, save presence and absence of observed client/native class loading are checked. No player joins, world-content comparisons or gameplay parity are asserted. The harness reads reference jars only when explicitly passed one; they are never build dependencies.

[Session evidence](.plan/status/evidence/2026-09-08-a1-build/README.md) records actual commands, environments, artifact hashes and incomplete gates.

## Saved-terrain diagnostics

`tools/compare_beta_terrain.py --original WORLD --candidate WORLD --output NEW_DIRECTORY` compares Beta chunk block/metadata/light/height arrays and population flags, retaining input hashes and a versioned report. It exits 1 on differences and rejects malformed/empty required inputs. A `--level-template PATH` option on smoke_server.py copies existing level.dat into a fresh disposable world before launch; use only a template with the intended seed (existing world metadata overrides server.properties).

The initial startup fixtures are **not golden baselines**: even unchanged original repeats differ in lava-related saved state. See [terrain investigation](.plan/status/evidence/2026-09-09-a1-terrain/README.md). Do not change gameplay or ignore fields to force this diagnostic to pass. The decoder tests run with `python3 -m unittest discover -s tools -p 'test_*.py' -v`.

## Controlled terrain capture

A separate test-only adapter can capture repeatable server terrain without launching the normal tick loop:

```sh
python3 tools/capture_beta_terrain.py --java-home /path/to/jdk8 --jar build/libs/Minecraft-server.jar --namespace named --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/terrain-control/new-built-run
```

For the recorded original Beta server jar, use `--namespace original` and pass its path explicitly. The original adapter checks its SHA-256. Output must be new; the driver compiles JDK-only tooling, copies the template, fixes test-only World.rand to seed 8675309 and requests both dimensions in a declared order with lighting drains and zero ticks. Compare the resulting `world` directories using the saved-terrain comparator above. Result JSON records inputs/commands; requests.tsv and process.log record RNG/request evidence.

Original repeat and built comparison PASS across 1,248 saved chunks for this one fixture. [Evidence and exact commands](.plan/status/evidence/2026-09-09-a1-terrain-control/README.md). This does not qualify the full seed/order matrix, active gameplay or full persistence. No game RNG/source changes are involved.

The bounded matrix command repeats each original case before comparing the build:

```sh
python3 tools/check_beta_terrain_matrix.py --java-home /path/to/jdk8 --original reference/source/beta-1.7.3/server/original/minecraft_server.jar --candidate build/libs/Minecraft-server.jar --level-template reference/fixtures/beta-1.7.3/worlds/startup-seed-8675309/level.dat --output run/new-terrain-matrix --evidence .plan/status/evidence/new-terrain-matrix
```

Both directories must be fresh. It runs 54 captures covering six signed seeds, three request orders and neighborhoods around chunk -32, 0 and 32 in both dimensions. A capture failure or terrain/trace mismatch returns nonzero and preserves evidence. `capture_beta_terrain.py --seed SIGNED_LONG --requests SCHEDULE.tsv` supports individual cases; schedules contain unique tab-separated chunk x/z pairs. Seed overrides affect only copied metadata. Without these options the original startup schedule/template seed is retained. [Recorded matrix](.plan/status/evidence/2026-09-09-a1-terrain-matrix/README.md): all 18 cases PASS; raw/biome/SP/full parity remain pending.

For independent raw pre-population terrain and biome/climate comparison, add `--mode raw` to the matrix command and choose fresh output/evidence paths. Raw captures call the server generator directly in separate JVM/worlds and record exact block/metadata arrays, biome identities and IEEE-754 temperature/humidity bits. The strict `read_beta_raw.py` reader produces versioned canonical hashes; the matrix automatically compares them. Raw binaries stay under the capture directory. `compare_beta_terrain.py` remains a saved-world CLI; use the matrix for raw comparisons.

[Raw matrix evidence](.plan/status/evidence/2026-09-09-a1-raw-terrain/README.md): all 18 cases PASS, and saved mode's earlier 1,248-chunk fixture still passes. This covers server generation; SP, all-biome/cold cases, far-distance and gameplay qualification remain pending.

## Packet correspondence inventory

`python3 tools/inventory_beta_packets.py --output NEW_DIRECTORY` records source hashes, packet registrations, core method comparisons and original mapped descriptor checks using the recorded local mapping bundle and `/usr/lib/jvm/java-8-openjdk/bin/javap`. This is optional review tooling, not a build dependency or runtime codec test. See the [reviewed packet ledger](.plan/architecture/PACKET-CORRESPONDENCE.md) for side policies and remaining shared-source boundaries.

## Control packet/string parity

`python3 tools/check_beta_packet_codecs.py --output NEW_DIRECTORY` compiles a JDK-only harness and compares 175 observations across both original jars and both built artifacts. It currently uses `/usr/lib/jvm/java-8-openjdk/bin` and the recorded local originals. Run it after building; it returns nonzero for failures or mismatches. This optional reference test is not a build dependency.

`python3 tools/verify_working_sources.py` verifies current source/resource hashes using immutable IMPORT.json plus reviewed EVOLUTION.json. Initial-import verification now intentionally differs at the two Packet delegates and new shared codec. [Extraction evidence](.plan/status/evidence/2026-09-09-a1-shared-strings/README.md): build, four-way codec comparison and dedicated login smoke PASS; graphical/two-player/full parity retests pending.

The codec harness now includes movement packets 10–13: 553 observations total, including flags, exact numeric fields, sentinels, truncation and source-informed correction/acknowledgement constructor vectors. [Movement evidence](.plan/status/evidence/2026-09-09-a1-movement-codecs/README.md). It does not execute real movement handlers or replace gameplay/two-player tests.

The codec/accounting suite now contains 566 observations, including per-ID counters and historical reported-size quirks. PacketCounter/Empty1 compile once from main and pass before/after comparison and dedicated login smoke. [Accounting checkpoint](.plan/status/evidence/2026-09-09-a1-shared-accounting/README.md).

Registry coverage raises the codec/accounting suite to 1,338 observations: all ID lookups, duplicate-registration failures and every receive-direction/ID gate are compared across both originals/artifacts. [Shared registry evidence](.plan/status/evidence/2026-09-10-a1-shared-registry/README.md). Empty-payload probes do not certify every packet decoder or real handler behavior.

WatchableObject state coverage raises the differential suite to 1,392 observations (54 object-state cases in addition to prior codec/accounting/registry cases). Five classes now compile once in main. [Metadata value evidence](.plan/status/evidence/2026-09-10-a1-shared-watchable/README.md). Full DataWatcher wire/lifecycle qualification remains pending.

The coordinate/NBT merge brings main to 20 classes. For a focused save-data check, compile tools/SharedNbtSmokeTest.java against build/classes/java/main and run it with a level.dat path; it reads that file and performs writes only in memory. [Commands and results](.plan/status/evidence/2026-09-10-a1-shared-values/README.md).

## Practical unified-game checks

After building both artifacts, run:

```sh
python3 tools/run_game_smoke.py --java-home /path/to/jdk8
```

This checks packet/item operations, local-world save/reload, and two protocol clients moving, placing/digging, opening a chest, saving and reconnecting to a separate dedicated process. It creates fresh worlds under `run/smoke/`; it never uses your development saves. Commands, artifact hashes and logs are recorded there. This is a functional scenario, not an original-jar byte comparison.

For later platform/rendering work, an optional graphical scenario is available. It is not required for a1; owner confirmation is sufficient. To run it, prepare dependencies and explicitly request the windowed scenario:

```sh
./gradlew prepareClientNatives copyClientLibraries
python3 tools/run_game_smoke.py --java-home /path/to/jdk8 --graphical
```

The graphical scenario opens a temporary game window, runs local movement/block interaction/save/reload, joins a separate local server, reconnects, captures screenshots and exits. It requires the supported Linux graphical/native environment. Headless checks alone do not certify rendering or audio. The normal `build` remains independent of a display or available network ports.

The future vanilla patch uses a complete named implementation replacement; see [a1 strategy discovery](.plan/architecture/PATCH-STRATEGY.md). Patch packaging and reconstruction are deferred to a8.

### Offline Beta sound resources

The original resource-listing URL now returns an error. Beta falls back to existing local resources. If you have the supplied RetroMCP asset cache, explicitly verify and materialize it once:

```sh
python3 tools/prepare_beta_resources.py \
  --index reference/source/beta-1.7.3/client/decompiled/game/assets/indexes/b1.7.json \
  --objects reference/source/beta-1.7.3/client/decompiled/game/assets/objects \
  --destination run/client/.minecraft/resources
```

The tool validates every object’s SHA-1 and size before writing, preserves differing existing files, and does not change references. This is an explicit local asset import, not a build dependency or automatic download. Normal `runClient` then finds the copied resources through Beta’s fallback. The obsolete URL may still log an error. To use these assets in the disposable graphical scenario, add `--resources run/client/.minecraft/resources`. Audible output still requires a functioning audio device; asset verification alone is not a listening test.
