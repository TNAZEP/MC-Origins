# Full source merge — 2026-09-17

Base revision `e1eaaac7d06a41452762eb92fe4bf3fec5670968`, main, plus preserved uncommitted implementation. Linux x86_64; Gradle 9.1.0; Java 8 game toolchain/runtime. Artifact hashes and candidate mapping archive identity are in [inputs.json](inputs.json). Display remains `Minecraft: Origins 1.0a1`.

## Implementation and review

420 shared / 270 client / 39 server Java classes. No duplicate side class names remain. Shared World/entity/block/item/chunk/generation/inventory/save graph compiles without either side or reference inputs. Both artifacts include the same main output. Client local/remote worlds and dedicated connection/lifecycle policies remain side-local; no integrated server or modern gameplay was imported.

The [263-class model union ledger](../2026-09-16-a1-full-model/merge-ledger.json), scoped alias mappings beside it and [final container/save ledger](final-host-ledger.json) record correspondence. Common method bodies were compared after scoped symbol reconciliation. World/EntityFish initializer placement and World lighting early-return/finally arrangements were reviewed as equivalent forms. Client-only APIs and server-only producer/player-save helpers are retained in the union. SaveHandler includes dedicated player files; remote SaveHandlerMP supplies no-op persistence hooks. Furnace containers retain both progress producer and receiver. Distinct Item.isMap/render-rotation and solid/solid-or-liquid world queries remain distinct. ClientSleepThread and ServerSleepThread retain their separate lifetimes.

IMPORT.json remains immutable; EVOLUTION.json records new paths, removed duplicates, caller reconciliation and original side origins. Verification PASS: 818 files / 1269 evolution entries. Old /tmp recovery snapshots have expired; no claim that they remain recoverable.

## Executed checks

| Check | Result | Evidence |
|---|---|---|
| Clean client/server build | PASS | build.log |
| Both artifact packet flow | PASS | packets-client.log, packets-server.log |
| Item operations | PASS | ItemStackSmokeTest.log |
| Region/map metadata save/reopen | PASS | WorldSaveSmokeTest.log |
| Local generated world, block edits, chest/player inventory and position save/reload; Beta height bound | PASS | LocalWorldSmokeTest.log |
| Two protocol players: login/chat/move/place/dig/chest/save/disconnect/reconnect and persisted consumption/block edits | PASS | multiplayer.log |
| Dedicated launch/login/stop, no observed client/native classes | PASS | server-result.json |
| Prepared original sound resources | PASS | resources.json: 220 hash/size-verified assets |
| Graphical client local movement/controller interaction/save-reload/dedicated join/reconnect | PASS | graphical-result.json, graphical-pass.log, graphical-server.log, screenshots/ |

Commands from the repository root:

```sh
./gradlew --no-daemon --offline clean build
python3 tools/verify_working_sources.py
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-openjdk/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/unified-final --probe-login
./gradlew --no-daemon --offline prepareClientNatives copyClientLibraries
python3 tools/run_game_smoke.py --java-home /usr/lib/jvm/java-8-openjdk --graphical
python3 tools/run_game_smoke.py --java-home /usr/lib/jvm/java-8-openjdk --graphical-only --resources run/client/.minecraft/resources
```

The runner records exact argv, exit codes, hashes and fresh disposable world paths. Initial graphical harness attempts FAIL: relative LWJGL native path, omitted SP controller/menu transition, null AWT canvas, and desktop focus pause. Corrected the harness to use the normal controller/menu and AWT window setup; automatic focus-loss menu is suppressed only in the test subclass. Production pause/input code is unchanged. These failures are retained, not presented as game regressions or passes.

Beta’s obsolete resource-listing URL fails. Explicit hash-verified local resources enable the existing fallback without build dependencies on references; see BUILDING.md. Audio listening, other operating systems/GPUs, online authentication services and exhaustive original-jar parity are NOT RUN. Current practical qualification is the Linux bootstrap environment only. No patch/release packaging or publication is claimed. [Patch strategy discovery](../../../architecture/PATCH-STRATEGY.md) completes a1 investigation; reconstruction remains a8 work.

## Acceptance review and promotion

2026-09-17: a1 implementation checkpoint accepted as Complete under the owner’s instruction to finish the full merge and a1. No release, tag, commit or publication was performed. Revision is the base commit above plus the current IMPORT/EVOLUTION hashes and artifact identities; the work remains uncommitted.

G-BUILD PASS (clean build, canonical main output); G-RUN PASS (real graphical SP and server join/reconnect plus two-player protocol gameplay); G-BETA PASS for the affected practical scenarios and screenshot review; G-DATA PASS (region/map, actual world edits, chest/player data and reconnect persistence); G-SCOPE PASS (no new gameplay or integrated server); G-HANDOFF PASS (status/milestone/strategy/docs updated). This is bounded bootstrap acceptance, not a claim that every Beta interaction or platform has been exhaustively tested.

Reviewed menu, local-play and multiplayer-reconnect images: expected version label, textures, terrain/water, tree/cow rendering, held block/hand and HUD are visible without obvious missing textures or rendering corruption. OpenAL initialization and shutdown are logged. Audible fidelity is NOT RUN. The graphical harness uses the production render/tick/controller/network paths with scripted actions; test setup supplies inventory/support blocks and suppresses focus-loss menus only in its subclass. It is not a human keyboard/mouse or pause-menu qualification.

Known service limitation: the obsolete asset-listing request logs an error; 220 explicitly imported verified resources support the existing local fallback. Online account/skin/stat services and broader platform qualification remain outside this bounded a1 acceptance and are carried into the next platform work. The historical exact Beta decompiler build remains unknown.

Owner clarification after the completed graphical run: graphical tests are not required for a1; owner confirmation that the game works is sufficient within its stated scope. No additional graphical checks were run. The optional harness remains available for later platform/rendering work; it is not a mandatory routine gate. ADR-0012 records this direction.
