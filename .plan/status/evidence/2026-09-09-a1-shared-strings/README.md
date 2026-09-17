# First shared packet-string codec — 2026-09-09

Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus existing/session work. Linux x86_64; Gradle 9.1.0 on Java 21.0.12+8; Temurin 8u492 compilation; OpenJDK 8u504 codec tests and server smoke. No reference inputs or user worlds changed.

## Change and provenance

Both Packet.writeString/readString implementations were byte-identical. Their method bodies now live verbatim in src/main/java/net/minecraft/src/BetaStringCodec.java. Both side Packet classes retain their public signatures and delegate to that class. IDs, direction rules, constructors, handlers, packet-size accounting and gameplay remain untouched. One shared class is compiled once and packaged byte-identically into both artifacts (verification.json).

src/IMPORT.json remains the immutable import baseline. src/EVOLUTION.json records baseline/current hashes for the two changed Packet files and the new shared helper, with this evidence link. tools/verify_working_sources.py verifies all 1,212 current Java/resource files against that combination. The original importer verifier correctly reports the three deliberate differences; its output is retained as initial-snapshot-expected-differences.txt. Do not overwrite original provenance to make an old snapshot check pass.

## Verification

| Check | Result | Evidence |
|---|---|---|
| Original-client/original-server/built-client/built-server codecs before edit | PASS | baseline: 175 identical observations per jar |
| Same four-way comparison after extraction | PASS | after: 175 identical observations, also identical to pre-edit baseline |
| Independent valid UTF-16 wire expectation | PASS | Harness asserts writeShort(char count) + writeChars bytes for successful writes |
| Shared and both side builds | PASS | build-success.txt: compileJava and both artifacts succeed; Gradle test NO-SOURCE |
| Same shared class in both jars | PASS | verification.json: one entry in each, bytes equal to main compile output |
| Built headless startup/offline login/stop | PASS | smoke/result.json and process.log; protocol 14 login, exit 0, save presence, no client/native classes observed |
| Python tooling | PASS | python-tests.txt: 16 tests |
| Source provenance/reference preservation | PASS | 1,212 current files / 3 declared changes; reference inventory unchanged |
| Graphical retest/two-player/full packet/gameplay/terrain parity | NOT RUN | Narrow string extraction; previous terrain evidence applies to unchanged generation sources but was not rerun |

The Java harness uses one isolated classloader per invocation and no reference compilation dependencies. Original Packet mappings ki (client) and gt (server), overloaded methods and ID getters are the reviewed mappings from the packet ledger. Both actual input hashes, command arrays, Java version, harness/driver hashes and report digest appear in baseline/result.json and after/result.json. TSVs compare outputs and exception class/messages, not stack traces.

Cases include empty/ASCII/NUL/Japanese text, surrogate pairs and lone surrogates, length boundaries 32/33, 100/101, 119/120, 32767/32768, negative lengths/maximum, every short-string truncation, control packet construction/read/write/size, EOF, unknown ID 254, allowed/rejected directions using IDs 4/7, and truncated chat packets. Packet3 constructor's 119-char truncation remains observable. Exceptions are expected observations; a matching ERROR row is not a failed test. No network handlers or simulation are executed by the codec harness. Runtime networking is separately exercised by the synthetic login smoke.

## Actual commands and initial failures

```sh
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-09-a1-shared-strings/baseline
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build reportBuildEnvironment
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-09-a1-shared-strings/after
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/shared-strings --probe-login
python3 tools/verify_working_sources.py
python3 tools/import_beta_sources.py --verify
```

All exit 0 except the last command, which intentionally exits 1 with the three approved snapshot differences. Initial capture to the sibling before/ directory failed before tests because javac's destination directory was missing; before/result.json records FAIL. The driver now creates that directory and logs compiler output. No codec outcome is claimed for that failed attempt. The initial sandboxed Gradle run failed creating its network/lock service (build-sandbox-failure.log); the approved retry outside the socket restriction succeeded, summarized from observed output in build-success.txt. Server smoke similarly ran with approved loopback access. No dependency download was required.

Python assertions verified the shared archive entries against main's class output, before/after TSV equality and inventory(reference) equality with the previous acquisition inventory. Hashes/results are in verification.json. Generated Java test classes are excluded from version control under evidence classes/ directories. Final git diff --check PASS.

## Continuation and recovery

This is a completed small shared primitive extraction, not completed packet/source unification or a1. Next expand byte fixtures to movement packets 10–13 with side-specific producer/handler cases, then design the registry/handler dependency boundary before moving packet classes into main. Two-player, SP, fixed-camera/audio and remaining worldgen coverage remain open.

Rebuild with the existing wrapper and rerun codec checks in a fresh output directory. Smoke worlds are disposable under run/smoke/shared-strings. Review future edits in EVOLUTION.json rather than altering IMPORT.json. No user save migration or external publication performed.
