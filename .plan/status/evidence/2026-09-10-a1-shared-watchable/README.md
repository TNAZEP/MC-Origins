# Shared metadata value object — 2026-09-10

Result: PASS for this bounded class union. 1.0a1 remains In progress.
Revision: e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved uncommitted work.
Environment: Linux x86_64, Python 3.14.7; Gradle 9.1.0 / JVM 21.0.12+8,
Temurin 8u492 compiler, OpenJDK 8u504 differential/smoke runtime.

## Change and provenance

WatchableObject now compiles from main once. Both side copies were removed.
The common source is byte-for-byte the former server source; deleting only its
getWatching method produces the former client source exactly. The client gains
that accessor; no caller, field, constructor, setter or dirty-list policy changes.
Five shared Java classes; 675 client / 441 server Java classes. Current source
verification covers 1,210 Java/resource files and 13 EVOLUTION entries. IMPORT.json
and pristine references remain unchanged.

[Review inputs](review-inputs.json) contains both-side hashes for the reviewed
payload/model group and exact original javap commands. [Mapping](watchable-mapping.tiny)
records WatchableObject = original client ma / server ht. Both original
[client](client-watchable-bytecode.txt) and [server](server-watchable-bytecode.txt)
bytecode confirm constructor field assignment, initial dirty=true, plain reference
setter and explicit dirty setter. Only the server exposes the getter. Mapping
source: reference/tools/bin/retromcp-b1.7-resources-2026-09-08.zip, whose SHA-256 is
b1903f261dca46e25feb50151102afd11807d446ac49c59dac7a995b497c92a9.

## Verification

| Check | Result | Evidence |
|---|---|---|
| Expanded original/built oracle before move | PASS | before/result.json; 1,392 observations for each of four jars |
| Build in sandbox | BLOCKED | build.log; Gradle wildcard-IP lock socket unavailable |
| Same build with socket permission | PASS | build-escalated.log; both artifacts |
| Oracle after move | PASS | after/result.json; same 1,392 rows, prior 1,338 rows preserved |
| Shared archive contents | PASS | verification.json; each of five common classes present once and equal to main compiler output in both jars |
| Headless startup/login/stop | PASS | smoke/result.json; protocol 14 offline login, exit 0, save presence, no observed client/native loading |
| Current provenance | PASS | source-verification.txt; 1,210 files / 13 entries |
| Reference inventory equality | PASS | verification.json; compared to 2026-09-08-a1-references/inventory.json |
| Packet descriptor inventory | PASS | inventory/ledger.json; 998 original descriptors, existing reviewed EnumArt alias retained |
| Python tooling | PASS | python-tests.txt; 16 tests |
| Graphical/two-player, metadata wire/dirty-list lifecycle, terrain retests | NOT RUN | Object state tests do not exercise the full DataWatcher or live handlers |
| a1 acceptance | NOT RUN | Complete shared graph/runtime gates remain pending |

The 54 added observations cover nine type/ID vectors, including negative and
out-of-range values accepted by the value object, and six states each: construction,
dirty clear, reference replacement, null replacement, dirty set, original reference
restore. They assert identity (no cloning), type/ID preservation and that setObject
does not change the dirty flag. The original client flag is inspected directly;
the server getter and every available named getter are additionally checked.
API absence on the original client is recorded here, not disguised as a matching
original method. These are object-state observations, not extra wire packet cases.

The server artifact hash is unchanged from the registry checkpoint: moving the
verbatim server class did not change its packaged bytes. Client hash changed due
to the union getter. Exact artifact/tool/runtime hashes and JVM commands are in
before/result.json, after/result.json and verification.json.

## Commands

Run from the repository root; differential output directories must be fresh.

```sh
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-10-a1-shared-watchable/before
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build > .plan/status/evidence/2026-09-10-a1-shared-watchable/build.log 2>&1
./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build > .plan/status/evidence/2026-09-10-a1-shared-watchable/build-escalated.log 2>&1
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-10-a1-shared-watchable/after
python3 -m unittest discover -s tools -p 'test_*.py' > .plan/status/evidence/2026-09-10-a1-shared-watchable/python-tests.txt 2>&1
python3 tools/verify_working_sources.py > .plan/status/evidence/2026-09-10-a1-shared-watchable/source-verification.txt
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/shared-watchable --probe-login
python3 tools/inventory_beta_packets.py --output .plan/status/evidence/2026-09-10-a1-shared-watchable/inventory
git diff --check
```

Additional Python assertions compared before/after TSV bytes and the first 1,338
rows against the prior registry oracle; compared inventory(Path('reference'))
against the acquisition JSON; and checked ZipFile.namelist().count(entry)==1 and
ZipFile.read(entry)==build/classes/java/main/entry for every main Java class.
Their recorded outcomes and archive hashes are in verification.json. Smoke logs
were copied from the disposable run directory. No user worlds were touched.

## Next

Qualify ChunkCoordinates value semantics and DataWatcher metadata/dirty-list
behavior against both originals before reconciling coordinate names and the
remaining metadata model. See the payload dependency review in
[the ledger](../../../architecture/PACKET-CORRESPONDENCE.md). No broad model merge,
modern item representation or gameplay change is justified by this checkpoint.
