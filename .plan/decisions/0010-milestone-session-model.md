# ADR-0010: Discrete alpha/beta releases and persistent session state

**Status:** Accepted — owner decision; exact milestone count is a working plan  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

The owner wants bounded checkpoints and clean progress continuity between Codex sessions.

## Decision

Use 1.0aN architecture milestones, then 1.0bN frozen-scope polish, then 1.0 and 1.0.x. Update persistent status every session; no milestone completes without acceptance evidence.

## Alternatives and rejected approaches

Reject session numbers as release versions, a giant one-shot implementation prompt, or declaring code written equivalent to a usable milestone.

## Consequences

CURRENT/NEXT/HISTORY plus milestone records are part of the definition of done. Add/split versions when work requires it.

## Verification and delivery

All milestones; G-HANDOFF.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
