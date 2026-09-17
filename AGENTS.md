# Minecraft: Origins — repository instructions

Minecraft Beta 1.7.3 is the behavioral specification for 1.0. Modernize the engine while preserving its gameplay, world generation, visuals, singleplayer semantics, and multiplayer behavior. Do not introduce continuation content, worldgen changes, an integrated server, or Vulkan in 1.0.

At the start of every session read `.plan/README.md`, `.plan/status/CURRENT.md`, `.plan/status/NEXT.md`, the current milestone in `.plan/versions/`, and relevant architecture/ADRs. Inspect the working tree and verify the handoff against reality before editing. Continue the active milestone in bounded steps.

The final target is Java 25, LWJGL 3, shader OpenGL behind an explicit rendering API, unified shared/client/server sources, namespaced registries, real BlockStates, 256-height-capable storage, and 1.17.1-compatible persistence. Storage capacity must not change Beta gameplay or generation bounds. Source unification does not mean integrated singleplayer.

Use immutable references only as evidence. Preserve meaningful client/server differences; do not import modern game behavior while adopting modern data structures. Record source provenance. Do not edit pristine references or use them as hidden build dependencies.

Follow `.plan/codex/WORKFLOW.md`. Every session, including planning-only or blocked sessions, updates `.plan/status/CURRENT.md`, `NEXT.md`, and appends to `HISTORY.md`; update the active milestone and relevant issue/decision records as needed. Distinguish PASS, FAIL, NOT RUN, and BLOCKED. Record actual commands, outcomes, revision, environment, and evidence paths. Unrun checks are not passes.

Follow existing repository commands; task names in the plan are target contracts until implemented. Preserve unrelated changes. Keep completed milestones buildable, launchable, and testable. Never mark one complete based only on compilation. Routine implementation work within the plan can proceed; changes to locked product decisions require an explicit owner decision and updated ADR. Record an unresolved question and continue independent work instead of guessing or repeatedly requesting confirmation.

Verification follows `.plan/decisions/0012-practical-verification.md`: prioritize a working, buildable, maintainable game and coherent implementation progress. Use builds, affected runtime smoke checks and focused tests for concrete risks. Byte-for-byte replication and exhaustive original-jar matrices are not routine requirements. Preserve existing diagnostic tests without expanding them as a prerequisite to every source move.

At the start of work on every new alpha, beta or release (including patch releases), update `src/main/java/net/minecraft/src/OriginsVersion.java` so the in-game label is exactly `Minecraft: Origins VERSION`. Use the active milestone version immediately, not only at completion. Menu, debug overlay, window titles and diagnostics use the shared DISPLAY_NAME; protocol/save version numbers stay separate.
