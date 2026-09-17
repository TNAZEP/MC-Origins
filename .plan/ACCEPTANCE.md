# Acceptance criteria

## Gates for every completed milestone

- **G-BUILD:** Clean documented build produces the applicable client and dedicated-server artifacts from one canonical shared source tree.
- **G-RUN:** Client loads a local world; dedicated server starts headlessly; client joins it, moves, breaks/places blocks, interacts, saves and reconnects. The active milestone's supported environment is recorded.
- **G-BETA:** Practical checks show the affected Beta gameplay, worldgen and visuals work correctly; meaningful regressions are investigated. Targeted differential checks may resolve uncertainty, but byte-for-byte replication is not a gate (ADR-0012).
- **G-DATA:** Existing supported worlds still save/reload; migration changes protect source copies and include failure/recovery checks.
- **G-SCOPE:** No new gameplay, higher build limit, worldgen changes, integrated server or Vulkan has entered 1.0.
- **G-HANDOFF:** CURRENT/NEXT/HISTORY, milestone checkboxes, decisions, risks and issue records reflect reality and link to evidence.

An early alpha is not expected to have features assigned to later alphas. It must not advertise them or break the working behavior already established. A pre-existing reference defect is documented separately from a new Origins regression.

## Final 1.0 gates

| ID | Required outcome | Primary delivery |
|---|---|---|
| F-01 | Java 25 compile/runtime, clean CLI build, separate artifacts from shared logic | a1/a2 |
| F-02 | LWJGL 3 window/input/audio, platform-qualified launch and cleanup | a2/b2 |
| F-03 | Complete shader OpenGL with Beta visual parity; no final legacy fallback | a3 |
| F-04 | Namespaced registries and real validated BlockStates; no implicit runtime-index persistence | a4 |
| F-05 | 256-capable section storage and preserved Beta behavior/generation bounds | a5 |
| F-06 | Valid native 1.17.1 world schema, full supported Origins round trip | a6 |
| F-07 | Safe Beta world import and direct vanilla 1.17.1 client/server play with documented equivalences | a7 |
| F-08 | Tested standalone client/server, reconstructable patch and Prism import | a8 |
| F-09 | Complete behavioral/worldgen/visual/MP suite, all differences triaged | a9/b1 |
| F-10 | Performance/platform evidence and release documentation | b2/b3 |

No unresolved release blockers: data corruption/loss, invalid native saves, failed supported launch/build, broken required multiplayer, or unapproved scope deviations. Major unexplained behavior/visual regressions also block release. Minor issues may ship only with explicit impact, workaround where available and an owner disposition recorded in release notes.

The forward 1.18.x experiment must be run and reported before 1.0. Its result does not become a blanket promise about all later versions or every terrain boundary. If it reveals malformed 1.17.1 output, that is a required-compatibility blocker; if it reflects the same normal vanilla upgrade issue as the control, record the limitation.

## Evidence rules

PASS requires execution with expected and observed results. NOT RUN means missing execution, not partial success. BLOCKED identifies the missing input/capability and independent work still possible. Benchmarks record hardware/settings/world/revision and repeated runs. A manual check can count when scripted automation is unsuitable, provided steps and observations are reproducible.
