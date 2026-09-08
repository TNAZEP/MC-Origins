# Minecraft: Origins — repository instructions

Minecraft Beta 1.7.3 is the behavioral specification for 1.0. Modernize the engine while preserving its gameplay, world generation, visuals, singleplayer semantics, and multiplayer behavior. Do not introduce continuation content, worldgen changes, an integrated server, or Vulkan in 1.0.

At the start of every session read `.plan/README.md`, `.plan/status/CURRENT.md`, `.plan/status/NEXT.md`, the current milestone in `.plan/versions/`, and relevant architecture/ADRs. Inspect the working tree and verify the handoff against reality before editing. Continue the active milestone in bounded steps.

The final target is Java 25, LWJGL 3, shader OpenGL behind an explicit rendering API, unified shared/client/server sources, namespaced registries, real BlockStates, 256-height-capable storage, and 1.17.1-compatible persistence. Storage capacity must not change Beta gameplay or generation bounds. Source unification does not mean integrated singleplayer.

Use immutable references only as evidence. Preserve meaningful client/server differences; do not import modern game behavior while adopting modern data structures. Record source provenance. Do not edit pristine references or use them as hidden build dependencies.

Follow `.plan/codex/WORKFLOW.md`. Every session, including planning-only or blocked sessions, updates `.plan/status/CURRENT.md`, `NEXT.md`, and appends to `HISTORY.md`; update the active milestone and relevant issue/decision records as needed. Distinguish PASS, FAIL, NOT RUN, and BLOCKED. Record actual commands, outcomes, revision, environment, and evidence paths. Unrun checks are not passes.

Follow existing repository commands; task names in the plan are target contracts until implemented. Preserve unrelated changes. Keep completed milestones buildable, launchable, and testable. Never mark one complete based only on compilation. Routine implementation work within the plan can proceed; changes to locked product decisions require an explicit owner decision and updated ADR. Record an unresolved question and continue independent work instead of guessing or repeatedly requesting confirmation.
