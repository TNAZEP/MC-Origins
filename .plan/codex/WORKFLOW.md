# Codex session workflow

## Start

1. Read repository AGENTS instructions, `.plan/README.md`, CURRENT, NEXT, the active milestone and relevant accepted ADRs.
2. Inspect repository status, pending changes, available references/toolchains and the last evidence. Do not assume a prior PASS still applies after source changes.
3. Reconcile the handoff with actual files. Identify one bounded task tied to an acceptance criterion; record the session start and intended verification in CURRENT.
4. When beginning a new alpha, beta or release, update OriginsVersion.VERSION to that version immediately and verify the shared display label follows `Minecraft: Origins VERSION` (see VERSIONING.md).
5. If a required input is absent, name it precisely and continue any independent useful work. Avoid inventing source layouts or test results.

## Work

Follow [ADR-0012](../decisions/0012-practical-verification.md): prioritize coherent implementation over expanding parity fixtures. Routine merges need relevant source review, both builds and affected runtime checks. Add focused tests for concrete risk; do not require original-jar byte comparisons for every class. Keep session evidence concise.

Keep behavioral intent explicit. Read both Beta sides for shared logic changes; inspect only the needed modern subsystem for format/platform ideas. Use reproducible edits with narrow scope. Do not rewrite unrelated code to modern style. Add a regression scenario when fixing a substantive defect. Keep normal implementation decisions moving; record consequential architecture decisions in an ADR.

Task names in this plan become runnable commands only once the repository implements them. Discover and document actual commands. A failing reference harness is not a reason to silently skip parity gates. Preserve baseline sources, unrelated work and original saves.

## Before ending every session

- Run applicable verification, recording exact commands/manual steps and results.
- Update [CURRENT](../status/CURRENT.md) with completed/in-progress work, changed files, actual environment, issues, evidence and the precise next action.
- Update [NEXT](../status/NEXT.md), keeping a short ordered queue with prerequisites.
- Append a dated session entry to [HISTORY](../status/HISTORY.md), including planning-only or blocked sessions.
- Update active milestone checkboxes and evidence, plus issues/risks/questions/ADRs/deviations when changed.
- State the actual build/launch/test condition and any unrun gates. Leave a recovery recipe for unfinished migrations or broken intermediate builds.

If interrupted, save a compact checkpoint as soon as practical; the next session repairs the handoff before continuing. Status must not be deferred until an entire alpha completes. A session may end with work unfinished; the milestone remains In progress or Blocked.

## Promotion and scope changes

A milestone enters Ready for review only when every required check has evidence. Mark Complete with a revision and promotion record once accepted under the project's review process. Do not start dependent work as though a failed gate passed. Independent discovery can continue without falsely advancing the milestone.

Record routine implementation choices locally without asking for repeated approval. A proposed change to Beta behavior, worldgen, the native persistence target, integrated-server deferral or other locked scope requires an explicit owner decision and an updated ADR. Document the concrete conflict and alternatives first; do not treat every uncertain class name as a product decision.

## Suggested continuation prompt

> Continue the next bounded task in .plan/status/NEXT.md after verifying CURRENT against the repository. Follow the current milestone and accepted ADRs. Preserve Beta 1.7.3 behavior and update the persistent handoff before finishing. Report actual verification and remaining blockers.

Codex recognizes repository AGENTS.md guidance; the root bridge directs it to these project-specific documents. See [official AGENTS.md documentation](https://developers.openai.com/codex/guides/agents-md). This kit does not rely on custom automation or implicit memory to maintain status.
