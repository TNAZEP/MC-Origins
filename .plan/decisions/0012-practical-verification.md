# ADR-0012: Practical verification and implementation progress

**Status:** Accepted — owner direction  
**Recorded:** 2026-09-10  
**Supersedes:** Mandatory exhaustive/differential-before-every-merge interpretation of ADR-0001 and testing plans

## Context

The owner clarified that byte-for-byte replication is not the objective: the game should work correctly, compile correctly, and provide a good foundation for further development. Small source moves had accumulated disproportionate differential testing and evidence work.

## Decision

Prioritize useful implementation in coherent dependency groups. Preserve recognizable Beta gameplay, world generation and SP/MP semantics, while allowing maintainable internal implementations. Binary identity, exact exception text and exhaustive reproduction of incidental implementation details are not acceptance goals.

For routine source unification, review the relevant side differences, compile both artifacts and run a smoke check when the affected runtime path warrants it. Add focused tests for meaningful risks such as save corruption, protocol incompatibility, inventory loss or altered generation. Existing fast tests can be reused; do not expand original-jar matrices as a prerequisite to every class move. Use differential tools when they help investigate a specific uncertainty or regression.

Retain existing tests and historical evidence as optional diagnostic assets. A failing test must be assessed for user-visible or compatibility impact; do not silently change expectations or call failures passes. Internal-only mismatches can be documented and the test revised when exact identity is not part of the supported contract.

## Consequences

Builds, practical play/connection/save checks and a maintainable shared codebase determine progress. Compilation alone still does not establish a playable milestone. Manual owner reports count within their stated scope. Record commands/results and remaining issues concisely; avoid creating large evidence packages for routine moves.

No new gameplay, integrated server, modern generation or change to the chosen platform/storage targets is implied. Wire-format and save-format correctness remain necessary for supported functionality; this decision removes blanket byte-identity demands, not functional compatibility.

## Verification

Apply to subsequent implementation sessions and milestone acceptance. This is a workflow decision, not a new runtime PASS.

## Owner clarification — 2026-09-17

For a1, owner confirmation that the game works is sufficient practical playability evidence within its stated scope. Automated graphical tests are not a prerequisite; graphical qualification becomes more important during later rendering/platform work. Do not repeat or expand graphical testing to gate routine source unification. Previously executed graphical evidence can remain, but it does not establish a recurring test requirement. A conditional statement about accepting owner confirmation is not itself a new playtest report.
