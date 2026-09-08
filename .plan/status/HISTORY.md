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
