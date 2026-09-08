# ADR-0007: Native 1.17.1-compatible persistence and direct vanilla play

**Status:** Accepted — owner decision  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

The owner preferred continued vanilla play, comparable to a stepwise vanilla Beta upgrade, over merely recognizing an Anvil container.

## Decision

Target exact Java 1.17.1 normal saves including all meaningful world/player/entity data. Validate by direct unmodified client/server play and a paired upgrade control.

## Alternatives and rejected approaches

Supersedes 1.2.5 as the native format. Reject exporter-only compliance, arbitrary DataVersion labeling, and a required vanilla-side Origins mod/datapack.

## Consequences

Exact schemas/mappings must be verified. Vanilla behavior after migration is expected. Arbitrary reverse imports and later-version guarantees are not implied.

## Verification and delivery

1.0a6/a7; F-06/F-07, P-01 through P-06.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
