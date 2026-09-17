# ADR-0001: Beta 1.7.3 is the 1.0 behavioral specification

**Status:** Accepted — owner decision  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

Engine modernization can inadvertently import newer Minecraft mechanics or fix historical quirks.

## Decision

Use pristine Beta 1.7.3 client/server behavior as the oracle, including purposeful SP/MP differences. Modern internals do not authorize gameplay or visual changes.

## Alternatives and rejected approaches

Reject adopting modern behavior with modern APIs and reject treating all Beta quirks as defects. Behavior deviations need concrete evidence and an explicit disposition.

## Consequences

Preserve Beta gameplay through source review and practical functional checks. Verification depth follows [ADR-0012](0012-practical-verification.md): differential fixtures are targeted tools, not mandatory prerequisites for every merge. Historical implementation details may change while the game functions correctly.

## Verification and delivery

Every alpha and beta; G-BETA, F-09.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
