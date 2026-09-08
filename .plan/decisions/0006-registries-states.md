# ADR-0006: Stable names and real internal BlockStates

**Status:** Accepted — owner decision  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

Legacy IDs/metadata constrain future development and do not naturally express the target persistence representation.

## Decision

Use namespaced registries and real validated state definitions, keeping explicit Beta numeric and 1.17.1 persistence mappings at boundaries.

## Alternatives and rejected approaches

Reject a larger numeric ID alone, a metadata-only wrapper, and copying modern behavioral algorithms with modern state definitions.

## Consequences

Many-to-one mappings and transient metadata need accounting. Compact runtime IDs remain implementation details.

## Verification and delivery

1.0a4/a6; F-04/F-06.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
