# Session history

Append entries; do not replace earlier evidence. Use the session template and link durable reports.

## 2026-09-07 — Planning kit initialization

- Target: 1.0 / next milestone 1.0a1.
- Work: Created design, scope, architecture, ADRs, alpha/beta/release milestones, testing/acceptance, build/reference guidance and persistent handoff templates.
- Decisions: Preserved the owner's final direction; earlier expanded worldgen and 1.2.5-native-format suggestions are superseded.
- Verification: Game build, launch and compatibility checks NOT RUN; this is a planning-only deliverable without game sources.
- Target repository revision: Not available in this planning kit.
- Blockers: None to writing the plan. Source/reference availability must be checked in the destination project before implementation gates.
- Next action: Inspect actual a1 inputs and capture the pristine Beta baseline.

## 2026-09-07 — Reference recommendation correction

- Re-read the completed final reply after the owner flagged the mismatch. The earlier retrieval had ended before that reply.
- Replaced the initial reference recommendation with b1.7.3, 1.3.2, 1.13.2 and 1.17.1, client/server for all four. Removed 1.2.5 from the initial scaffold.
- Adopted reference/source/ with beta-/release- labels and separate Beta/migration fixtures; added purpose/authority READMEs and updated manifest, guide and affected alpha evidence.
- No game implementation performed; all runtime gates remain NOT RUN.

## 2026-09-08 — a1 local reference inventory and initial correspondence

- Revision: `83155adbcddba2f77f7269f68ee949502e054efe` / main, clean at entry; session changes uncommitted.
- Completed deterministic local hashes, source inventory, initial keepalive/login observations and manifest reconciliation. No source merge or gameplay modification.
- PASS: repeat inventory byte comparison and 3 tool tests. FAIL: installed Gradle probe (not found). BLOCKED: pristine baseline, absent original jars/provenance. NOT RUN: game build, client/server/two-player, behavioral/worldgen/persistence and a1 exit gates.
- Environment: Linux x86_64, Java/javac 21.0.12, Python 3.14.7. Exact commands, input hashes, missing inputs and recovery: [evidence](evidence/2026-09-08-a1/README.md).
- Next: pinned build scaffold independently; acquire authenticated pristine Beta inputs before baseline and behavioral merge. Milestone remains In progress, not complete.

## 2026-09-08 — Original Beta verification and 1.3.2 source acquisition

- Revision `83155adbcddba2f77f7269f68ee949502e054efe` / main, prior uncommitted inventory work preserved. User supplied Beta originals and reported latest RetroMCP/no changes; requested 1.3.2 procurement.
- PASS: Beta hash/CRC checks (Mojang client, MCPHackers server metadata); original 1.3.2 Mojang hashes; separate remap/decompile; repeat Java-byte comparison and full class/source path coverage; Beta source preservation; 3 inventory tests and 2 output refusal guards.
- Installed 1,335 client and 902 server 1.3.2 Java files. CFR reports two unstructured bundled client JOrbis methods; no server summary warnings. No compilation/behavior equivalence claim.
- Initial sandbox network request FAIL; approved retry PASS. RetroMCP catalog excludes 1.3.2; selected pinned Legacy Yarn 1.3.2+build.604, Tiny Remapper 0.14.0, CFR 0.152 on OpenJDK 21.0.12+8. Historical Beta v1.2 usage is inferred from latest release, not exact-binary verified.
- NOT RUN: game launches, Origins build, SP/MP/two-player, worldgen/visuals and a1 acceptance. Originals are now available for runtime qualification.
- Evidence, commands, inputs, limits and recovery: [session report](evidence/2026-09-08-a1-references/README.md). Next: isolated original Beta baseline and independent pinned build scaffold. Milestone remains In progress.

## 2026-09-08 — Editable Beta working-source setup

- Owner requested actual development sources and structure. Imported Java into src/client/java (678) and src/server/java (444), plus original jar resources (84/5). Reserved main/test/integrationTest roots. No source bytes changed or classes merged.
- Added per-file provenance, a guarded one-time importer and initial snapshot verifier, root/source guides and architecture clarification. Separate source roots are temporary a1 staging; shared-code/runtime acceptance remains required.
- PASS: all 1,211 working file hashes, independent copies, complete reference inventory unchanged, existing-destination refusal and authored whitespace check. NOT RUN: compilation, game launches, behavior/worldgen/visuals and a1 acceptance.
- Revision `83155adbcddba2f77f7269f68ee949502e054efe` / main + existing/current uncommitted work. Python 3.14.7/Linux x86_64; no game runtime selected. User START-HERE.md changes preserved.
- Commands, hashes and recovery: [working-source evidence](evidence/2026-09-08-a1-working-sources/README.md). Next: pinned build for actual roots and isolated pristine launches before behavioral reconciliation.

## 2026-09-08–09 — Pinned a1 build and dedicated-server qualification

- Base revision `e1eaaac7d06a41452762eb92fe4bf3fec5670968` / main, clean on entry; prior work had been committed. Added Gradle 9.1.0 wrapper, Java 8 bootstrap source sets, dependency locks/checksums, artifact/run/native tasks and server smoke harness. No game Java/resource edits.
- PASS: both sides compile; byte-identical clean rebuilds; package/resources/class verification; nine client dependency SHA-1 comparisons; original/built headless startup/stop/save-presence/class-loading smoke on OpenJDK 8u504; built smoke also on selected Temurin 8u492; final launch task dry-run and source/reference preservation.
- Initial FAIL: Gradle/smoke sandbox socket restrictions (approved retries passed); JOrbis repository location (corrected using upstream metadata, rebuild passed). Gradle test NO-SOURCE, recorded as NOT RUN.
- NOT RUN: client play/audio, multiplayer/two-player, visual/worldgen/persistence parity and source-unification acceptance. Main Java root still empty. No milestone promotion.
- Environment: Linux x86_64/Python 3.14.7, Gradle JVM 21.0.12+8, compiler Temurin 1.8.0_492-b09. Java 8/LWJGL 2 are a1 bootstrap only; final Java 25/LWJGL 3 unchanged.
- Commands, logs, artifact/runtime hashes and recovery: [evidence](evidence/2026-09-08-a1-build/README.md). Next: client/original local-SP qualification and paired fixtures before shared merge. Session edits uncommitted.

## 2026-09-09 — Local development authentication mismatch

- Owner reports client/server launch success and failed local connections. Inspected server logs/properties and both handshake implementations: local server online-mode=true, development client session `-`; exact graphical-client error not captured.
- Backed up and changed existing local properties to online-mode=false and server-ip=127.0.0.1, preserving world/port and other settings. No game authentication code/default changes. Added BUILDING.md guidance and optional protocol login probe.
- PASS: synthetic offline protocol-14 handshake/login on built jar in disposable loopback server, clean stop/save presence, all working input hashes preserved, whitespace review. NOT RUN: graphical connection retest, two-player/fixture parity, new build (no game/build logic changes).
- Base revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus prior uncommitted build work; Temurin 8u492/Linux x86_64. [Evidence](evidence/2026-09-09-a1-login/README.md). Next: restart server and connect to 127.0.0.1:25565. Milestone remains In progress.

## 2026-09-09 — Owner confirms working local client/server

- Owner confirms both compile and work after offline-mode fix. PASS: local connection, supported by log at 11:22:18, normal disconnect at 11:22:23 and save/shutdown at 11:22:27–28. Closed I-01.
- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus existing uncommitted work; documentation-only session. Runtime/commands for owner test not independently supplied. No new build or game test run; whitespace check PASS.
- Updated CURRENT/NEXT/milestone/issue and launch docs. [Evidence](evidence/2026-09-09-a1-confirmation/README.md). Next: bounded baseline fixtures then shared reconciliation; full two-player/parity acceptance remains incomplete.

## 2026-09-09 — Saved-terrain comparator and startup nondeterminism

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 + existing uncommitted work. Implemented read-only Beta NBT/McRegion decoder/comparator; 5 new tests, 8 total PASS. No game code changes.
- Initial seed-only old smoke comparison FAIL (different spawn/coverage, 1,042 differences). Identical original-derived level.dat aligned 1,248 coordinates; original/built comparison FAIL on 32 Overworld chunks. Original self-repeat also FAIL on 32 Overworld chunks; all 623 Nether chunks match. I-02 tracks fixture-control insufficiency, not a proven game regression.
- Added --level-template to smoke harness, recorded template/region/artifact hashes and three fresh server logs. Headless startup-stop PASS on OpenJDK 8u504. New fixtures remain diagnostic, not golden. Python 3.14.7/Linux x86_64.
- Commands, raw differences and source hypotheses: [evidence](evidence/2026-09-09-a1-terrain/README.md). Game rebuild/two-player/visual fixtures NOT RUN. Next: explicit test-only RNG/tick/request control and original repeatability. No milestone promotion.

## 2026-09-09 — Repeatable bounded terrain oracle

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus prior uncommitted work. Added JDK-only reflection capture adapter and Python driver; controls World.rand, explicit requests/light drains and zero simulation ticks outside game sources.
- PASS: original repeat and original/built saved-terrain comparison (1,248 chunks, zero differences), identical request/RNG traces; eight Python tests and six-seed/60,000-iteration Java RNG fidelity test. Working import and pristine inventory unchanged. Closed I-02 for this bounded fixture; retained previous FAIL evidence.
- OpenJDK Java/javac 8u504, Python 3.14.7, Linux x86_64. No new build, graphical launch or full parity suite run. [Commands/hashes/logs/evidence](evidence/2026-09-09-a1-terrain-control/README.md).
- Next: expand controlled seed/order/boundary and raw/biome coverage; remaining SP/two-player/visual/shared-merge gates incomplete. No milestone promotion.

## 2026-09-09 — Bounded seed/order/boundary terrain matrix

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus prior work. Extended test-only capture to explicit schedules and signed-seed overrides of disposable metadata; original template and game sources unchanged. Added matrix runner and three seed-patch/schedule tests.
- PASS: 18 cases (six seeds × three orders), 54 fresh captures, 18 original self-repeats and 18 candidate comparisons, all terrain/request/RNG traces match. Each case requests 75 chunks per dimension across three 5x5 boundary neighborhoods; saved counts vary 138–149 by seed/order. Initial 1,248-chunk default fixture still matches.
- PASS: 11 Python tests; Java Random observer fidelity for six seeds/60,000 mixed iterations; 1,211 working hashes and pristine inventory. OpenJDK 8u504, Python 3.14.7, Linux x86_64. No new game build, graphical launch or full acceptance run.
- [Commands/hashes/reports](evidence/2026-09-09-a1-terrain-matrix/README.md). Next: raw pre-population and biome/climate sampling; SP/gameplay/two-player/visual/shared-source gates pending. Milestone remains In progress.

## 2026-09-09 — Raw generation and biome/climate matrix

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus prior work. Added separate raw capture mode, exact binary decoder and three corruption/mutation tests. No game source/resource or pristine reference changes.
- PASS: 18 six-seed/three-order cases, 54 captures, original repeats and candidate comparisons. 2,700 raw chunk samples and 691,200 biome/climate columns per field; exact blocks/metadata/biomes/double bits match, traces match, zero world ticks/loaded chunks/population. Saved 1,248-chunk regression PASS.
- PASS: 14 Python tests; Java RNG observer fidelity; 1,211 imported hashes and pristine inventory. OpenJDK 8u504/Python 3.14.7/Linux x86_64. No game rebuild, GUI/SP/two-player/full acceptance run. [Evidence and exact commands](evidence/2026-09-09-a1-raw-terrain/README.md).
- Seven biome identities sampled; cold/all-biome and far-distance coverage remain open. Next independent bounded work: Packet/NetHandler correspondence/dependency ledger; remaining runtime fixtures before behavior-sensitive merge. Milestone remains In progress.

## 2026-09-09 — Packet/source correspondence review

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus existing work. Added reproducible inventory and reviewed protocol ledger: 57 registered packets + 3 infrastructure classes, 998 mapped original descriptor checks, 53 handler correspondences (25 names differ).
- Source classification: 170 equal core bodies, 51 mapped-name matches, six inherited dispatches, one painting-title constant difference manually resolved. Preserve movement Y/stance call-site policies, constructors, world/item/metadata dependencies and packet-size accounting quirks. Studied both 1.3.2 Packet/PacketListener source pairs for organization only.
- PASS: 16 Python tool tests, 1,211 import hashes, reference inventory unchanged. Python 3.14.7/OpenJDK javap 8u504/Linux x86_64. No game source edits/build/launch/wire tests or milestone promotion. [Evidence](evidence/2026-09-09-a1-packet-ledger/README.md).
- Next: original/built control-packet and string byte oracle before a small shared helper extraction. Full runtime/SP/two-player/visual/shared-graph gates remain open.

## 2026-09-09 — First shared packet-string implementation

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus preserved work. Added isolated four-way control/string oracle; 175 observations match both originals and builds before extraction. Moved verbatim string method bodies into main BetaStringCodec; both side Packet methods delegate.
- PASS: rebuilt shared/client/server artifacts, identical shared class in both jars; all 175 observations still match originals/previous baseline; built headless offline login/stop/save-presence/no observed client-native classes. 16 Python tests PASS. No graphical/two-player/terrain rerun or full milestone promotion.
- IMPORT.json preserved; EVOLUTION.json records two changed delegates and new helper; current-source verifier PASS on 1,212 files. Initial import check EXPECTED_DIFFERENCE on exactly three paths. Reference inventory unchanged. Initial harness compile directory error and sandboxed Gradle socket failure recorded; fixed driver/approved build retry pass.
- Java compile Temurin 8u492, codec/smoke OpenJDK 8u504, Gradle 9.1.0/JVM21.0.12+8, Python 3.14.7/Linux x86_64. [Evidence/commands](evidence/2026-09-09-a1-shared-strings/README.md). Next: movement codec/side-policy fixtures and registry/handler boundary. a1 In progress.

## 2026-09-09 — Movement packet byte oracle

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus existing work. Extended codec harness to packets 10–13: flags, coordinates/angles as exact bits, -999 sentinels, noncanonical ground bytes, all truncations and common Packet13 constructor role vectors.
- PASS: 553 observations match both originals and both artifacts; 378 new movement cases, earlier 175 rows unchanged. Preserved final-ground-byte EOF=>true quirk. Side producer/handler policies reviewed with source hashes; actual live handlers/physics/two-player NOT RUN.
- PASS: 16 Python tests, current-source verification (1,212 files/3 existing changes), unchanged reference inventory. OpenJDK 8u504/Python 3.14.7/Linux x86_64. No game changes/build/smoke rerun. [Evidence](evidence/2026-09-09-a1-movement-codecs/README.md).
- Next closed dependency slice: qualify accounting totals then share byte-identical PacketCounter/Empty1; larger registry/handler graph and runtime gates remain pending. No milestone promotion.

## 2026-09-09–10 — Shared PacketCounter and Empty1

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus preserved work. Added 13 accounting observations (566 total) and moved byte-identical PacketCounter/Empty1 into main, removing four side copies. No behavior/body changes. Inventory now resolves shared sources and detects duplicate side/common classes.
- PASS: 566 four-way original/built observations before/after, including per-ID totals, packet 23/131 size quirks, rejection non-counting and overflow; prior 553 rows retained. Clean build, three identical shared class entries in both artifacts, no stale side outputs, protocol-14 offline login/stop/save-presence and no observed client/native loading. Client natives/library copies restored after clean.
- PASS: 16 Python tests, 998 original descriptor checks, 1,210 current files/9 evolution entries, unchanged reference inventory. Compile Temurin 8u492; codec/smoke OpenJDK 8u504; Gradle 9.1.0/JVM21, Python3.14.7/Linux. No GUI/two-player/terrain rerun or milestone promotion.
- [Evidence/commands](evidence/2026-09-09-a1-shared-accounting/README.md). Next: concrete registry/handler boundary for shared control packets; full runtime/shared-graph gates remain open.

## 2026-09-10 — Shared packet registry rules

- Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus preserved work. Added JDK-only BetaPacketRegistry for the original maps/sets, duplicate checks, ID/class lookup and direction policy. Both Packet classes delegate; static class-literal registrations/order, creation and typed handlers remain unchanged.
- PASS: 1,338 four-way before/after observations (772 new registry probes, prior 566 retained); both builds, four identical single common entries, dedicated protocol14 login/stop/save-presence/no client-native loads. 16 Python tests and 998 original descriptor checks PASS.
- PASS: 1,211 current files/10 evolution entries and unchanged reference inventory. Compile Temurin8u492, tests OpenJDK8u504, Gradle9.1.0/JVM21, Python3.14.7/Linux. No GUI/two-player/terrain rerun. [Evidence](evidence/2026-09-10-a1-shared-registry/README.md).
- Recorded remaining typed-handler/model dependency cycle; rejected side-root/unchecked-dispatch/reflective-bootstrap shortcuts for a small leaf move. Next: ItemStack/DataWatcher and producer dependency review to select a buildable shared slice. No full packet move or milestone promotion.

## 2026-09-10 — Shared WatchableObject and payload dependency review

Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved uncommitted work. Reviewed both ItemStack/DataWatcher/value/selected producer packet sides; recorded remaining model/lifecycle dependencies. WatchableObject moved as a verbatim server API union (client adds only dirty getter), duplicate side files removed; EVOLUTION has 13 entries, IMPORT untouched.

PASS: 1,392 four-way original/built observations before/after (54 new identity/null/type/ID/dirty-state cases); both builds; five identical shared archive classes; protocol-14 headless offline login/stop; 16 Python tests; 998 original packet descriptors; source/reference preservation; diff whitespace check. Initial sandbox Gradle lock socket BLOCKED, permitted rerun PASS. Server artifact hash unchanged. Graphical/two-player, full metadata codec/lifecycle and terrain retests NOT RUN. No milestone promotion.

[Evidence and commands](evidence/2026-09-10-a1-shared-watchable/README.md). Next: qualify coordinate/metadata behavior, then reconcile remaining metadata value dependencies; keep ItemStack and typed handler/producer graph side-local until reviewed. No references, user worlds, gameplay policy or modern formats changed.

## 2026-09-10 — Owner directs practical verification

Owner clarified that working gameplay, correct compilation and a good development foundation matter more than byte-for-byte replication. Accepted ADR-0012 and aligned repository/workflow/testing/acceptance instructions and a1 handoff. Next work is implementation of a coherent shared dependency group, not another mandatory differential matrix. Existing tests/evidence retained. Documentation only; `git diff --check` PASS, builds/runtime NOT RUN because unchanged. Revision e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved changes; Linux workspace. No milestone promotion.

## 2026-09-10 — Shared coordinate and NBT dependency group

Merged 15 classes (20 common total), removed 30 duplicate side copies, reconciled coordinate fields and NBT method names/callers. Both builds PASS after resolving 70 old server coordinate accesses (intermediate FAIL retained). Headless login/stop/save presence and focused compressed/raw NBT world/inventory read/write PASS. Source verification: 1,195 files / 78 evolution entries; diff check PASS. GUI/two-player/full restart and exhaustive parity suites NOT RUN. No milestone promotion. [Evidence, revision/environment and commands](evidence/2026-09-10-a1-shared-values/README.md). Next: ItemStack/model and metadata reconciliation. Existing references/user worlds and unrelated work preserved.

## 2026-09-10 — Shared metadata and in-game version identity

Shared DataWatcher using explicit side item adapters, retained server/client lifecycle methods, and unified integer getter. Added OriginsVersion; menu/debug/title/crash/server labels now Minecraft: Origins 1.0a1. Recorded owner's rule to update at the start of every alpha/beta/release. Both builds, metadata synchronization in both jars and headless login/stop PASS. Initial isolated metadata test FAIL due to missing normal StatList initialization; test startup corrected, game logic unchanged. GUI retest NOT RUN. [Evidence/commands](evidence/2026-09-10-a1-metadata-version/README.md). a1 remains in progress; next shared ItemStack/packet dependencies.

## 2026-09-10 — Shared stack data and reconciled APIs

Extracted ItemStackData for fields/copy/split/NBT/damage access/equality with typed concrete factory; both side ItemStacks retain gameplay callbacks. Reconciled stack API names and callers. Both builds, stack and metadata smoke on both artifacts, dedicated login/stop/save presence, provenance and diff check PASS. GUI/real interaction NOT RUN. Version stays Minecraft: Origins 1.0a1; milestone not promoted. [Evidence/commands/environment](evidence/2026-09-10-a1-stack-data/README.md). Next: Item callback/subclass and concrete stack dependency reconciliation.

## 2026-09-10 — Item API and shared tool materials

Reconciled item callback/subclass/caller names and map/shears fields; retained client rendering and server map scheduling distinctions. EnumToolMaterial shared unchanged. Both builds, two-side stack/tool/shears/placement checks, server login/stop and provenance/diff checks PASS. GUI/live crafting/maps NOT RUN; a1 unpromoted. [Evidence and commands](evidence/2026-09-10-a1-item-api/README.md). Next: Item/StatList registration and concrete stack host dependencies. Minecraft: Origins 1.0a1 remains current.

## 2026-09-10 — Ongoing full-merge foundation batch

Owner requested continuous progress through the full merge/a1. Shared foundation, material, noise, region, world metadata and statistics groups; 64 common classes. Both builds, item/metadata, NBT/region reopen, small terrain generation and server login/stop PASS. Full graph and GUI/MP gates still incomplete. [Evidence/commands](evidence/2026-09-10-a1-foundation/README.md). Continuing without routine confirmation; no milestone promotion.

### 2026-09-10 shared path search

68 shared classes; path algorithm and values merged with side world/entity adapters. Both builds and obstacle-routing smoke PASS; mob gameplay NOT RUN. Continuing packet merge. Evidence: status/evidence/2026-09-10-a1-pathfinding/README.md.

### 2026-09-11 packet/transport implementation

134 shared classes. All 57 packet definitions, typed dispatch and transport merged. Both builds, two-artifact packet flow and packet-stage login/stop PASS; transport smoke pending at checkpoint. Graphical/SP/two-player acceptance NOT RUN. Full a1 remains active. Evidence: [packet checkpoint](evidence/2026-09-11-a1-packets/README.md). No commit; preserved references and unrelated changes.

Transport follow-up: dedicated offline login, clean stop/save presence and no observed client/native loading PASS after the transport merge; final artifact/evidence recorded in packet checkpoint.

### 2026-09-16 map persistence and model APIs

147 shared classes; map save and value groups merged. Base Entity common bodies match after name reconciliation; broader model aliases applied without body replacement. Build and map/region reopen plus six-chunk generation smoke PASS; dedicated follow-up pending. [Evidence](evidence/2026-09-16-a1-model-api/README.md). Full graph and practical play gates remain incomplete; work continues.

Model follow-up: dedicated login/stop, final server packet-flow check, source provenance and whitespace PASS; evidence updated.

## 2026-09-17 — Full source merge

Completed World/entity dependency graph and remaining container/save-format union; retained separate client/server sleep-thread lifecycles with distinct names. 420 shared classes; clean client/server build, packet checks on both artifacts, region/map save-reopen and provenance verification PASS. Full model ledger and scoped mappings retained. Practical graphical/two-player acceptance remains open; a1 not promoted. Evidence: [unified checkpoint](evidence/2026-09-17-a1-unified/README.md).

## 2026-09-17 — a1 completion and owner verification clarification

Full merge complete: 420 shared classes and no duplicate side source paths. Clean build, local world edits/inventory/position save-reload, two-player network movement/place/dig/chest/save/reconnect, dedicated headless isolation and source provenance PASS. Real graphical SP/save-reload/server join/reconnect also passed before the owner clarified graphical tests are unnecessary for a1 and their playability confirmation is sufficient. ADR-0012 updated; no further graphical checks performed. Explicit local asset import verifies 220 resources; obsolete download endpoint and audio/platform limitations documented. Named full-replacement patch strategy discovered, reconstruction deferred to a8. a1 accepted as Complete under authorized scope with manifest/artifact evidence; no commit/tag/publication. a2 not started. [Evidence](evidence/2026-09-17-a1-unified/README.md).
