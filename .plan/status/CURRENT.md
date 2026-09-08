# Current project status

**Target release:** 1.0
**Current milestone:** 1.0a1 — Baseline, build and unified client/server source
**Milestone state:** In progress — editable Beta source import complete; shared-code merge and runtime/build acceptance pending
**Last updated:** 2026-09-08
**Repository revision / branch:** `83155adbcddba2f77f7269f68ee949502e054efe` / `main` + prior and current uncommitted changes
**Active session:** 2026-09-08-a1-working-sources — bounded task finished

## Completed

- Latest: editable working tree created under src/client and src/server (678/444 Java, 84/5 resources), preserving input bytes. Reserved shared/test roots; full import provenance and source guide. No behavioral merge yet.

- Prior session: deterministic reference inventory and initial Beta packet correspondence; inventory integrity tests pass.
- User-supplied original Beta client/server jars verified against published SHA-1/size metadata (Mojang client, MCPHackers BetterJSONs server), SHA-256 recorded, ZIP CRC checks pass.
- Owner's latest-release/no-changes RetroMCP statement recorded without claiming the historical tool binary is verified. GitHub latest currently v1.2; current resource archive pinned as a candidate.
- Obtained original 1.3.2 client/server jars from Mojang and prepared separate named study sources with pinned Legacy Yarn/Tiny Remapper/CFR inputs. Installed 1,335 client and 902 server Java files under reference/source/release-1.3.2/{client,server}/decompiled/src/.
- Offline reproduction recipe produces identical Java bytes to the initial run; complete class-to-source path coverage. Existing Beta sources unchanged. No game implementation changes.

## Verification

[Latest working-source evidence](evidence/2026-09-08-a1-working-sources/README.md) and [acquisition evidence](evidence/2026-09-08-a1-references/README.md) contains commands, metadata, hashes, summaries and limitations. [Prior inventory](evidence/2026-09-08-a1/README.md) remains historical evidence.

| Check | Result | Evidence / reason |
|---|---|---|
| Beta original hashes / ZIP integrity | PASS | verification.json; runtime correspondence still untested |
| 1.3.2 original hashes and source generation | PASS with caveats | Mojang SHA-1/size; CFR warns about two client JOrbis methods |
| Java source reproducibility / coverage | PASS | Both runs byte-identical; every mapped class has a source path |
| Existing Beta source preservation | PASS | Four source-tree maps match prior inventory |
| Inventory tests / recipe output guards | PASS | 3 tests and 2 expected refusals |
| Working-source import / snapshot / overwrite guard | PASS | 1,211 independent files, exact copied hashes, references unchanged |
| Origins CLI build | NOT RUN | Editable source exists; no wrapper/dependency configuration; prior installed Gradle probe failed |
| Game launches / SP/MP / two-player / behavior / worldgen / persistence | NOT RUN | Original Beta jars now available; runtime and asset/native qualification next |
| a1 exit gates | NOT RUN | Milestone incomplete |

## Environment and remaining gaps

Linux x86_64; Java 21.0.12+8 used only for decompilation, Python 3.14.7. No game bootstrap runtime chosen; Java 25 final target unchanged. Historical Beta mapping/tool binary/runtime/automatic-patch provenance is still unverified; no manual changes reported. Client 1.3.2 bundled JOrbis Drft has two unstructured CFR methods; references are for study, not a proven recompilable build. Some mapping names remain incomplete. No original-jar launch has been attempted.

## Next bounded action

Establish a pinned Gradle build for the working source roots and isolated run directories; qualify original Beta launches and capture baseline evidence before behavioral merging. See src/README.md and NEXT. Keep client/server duplicate classes separate until reviewed; main/java remains empty.

## Working tree and recovery

Preserved prior uncommitted inventory work. Added reference preparation script, pinned input manifest, local ignored reference artifacts, authored notes and evidence/status updates. Existing source/binary/save bytes unchanged. No migration or gameplay rollback needed. Keep installed references immutable and reproduce into a fresh scratch directory. No commit or publication performed.

Concurrent user change: START-HERE.md became modified during this session and was left untouched by Codex. Final whitespace/inventory review passed; prior jar hashes unchanged.

Working-source session changes: src/, tools/import_beta_sources.py, root README, architecture clarification and persistent evidence/status. START-HERE.md remains untouched. No compile or game launch attempted. Reference inventory unchanged after import; copies are independent, not symlinks. Prior work remains uncommitted.
