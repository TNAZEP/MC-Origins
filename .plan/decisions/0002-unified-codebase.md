# ADR-0002: One shared client/server source tree from the first alpha

**Status:** Accepted — owner decision  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

Separately modernizing decompiled client/server trees would duplicate work and create divergence.

## Decision

Merge shared game/model/protocol logic in a1. Produce client and dedicated artifacts from the same shared classes, with small side-specific hosts and policies.

## Alternatives and rejected approaches

Reject keeping two mostly identical World/entity/packet implementations. Also reject flattening genuine side behavior simply to reduce file count.

## Consequences

A merge ledger, dedicated headless launch and real client/server interaction are a1 exit requirements.

## Verification and delivery

1.0a1; G-BUILD/G-RUN, F-01.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
