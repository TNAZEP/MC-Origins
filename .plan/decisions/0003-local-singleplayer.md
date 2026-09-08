# ADR-0003: Integrated server is deferred until after 1.0

**Status:** Accepted — owner decision  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

Source unification and modern references can suggest moving SP onto server simulation immediately.

## Decision

Keep Beta local singleplayer semantics in 1.0. Isolate lifecycle and transport so integrated hosting remains possible later, without implementing it now.

## Alternatives and rejected approaches

Reject a localhost server/thread hidden inside the client as a modernization shortcut. Preserve pause/save/tick behavior.

## Consequences

Shared logic has explicit host context; future integration needs its own milestone and behavior decision.

## Verification and delivery

1.0a1/a2/a9; G-SCOPE and SP pause tests.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
