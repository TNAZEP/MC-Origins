# ADR-0008: 256-capable storage with unchanged Beta generation and behavior

**Status:** Accepted — owner decision; explicit separation of gameplay bounds is its conservative implementation  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

The conversation originally considered expanded terrain, then explicitly removed every worldgen change from 1.0.

## Decision

Use section storage capable of Y=0..255 while retaining original Beta generation, sea threshold, gameplay placement bounds, tick/spawn behavior and dimension semantics. Upper capacity is unused by normal gameplay.

## Alternatives and rejected approaches

Supersedes terrain scaling and any aN worldgen expansion milestone. Reject deriving generation/simulation loop limits from the new array capacity.

## Consequences

Capacity and behavior tests are separate. Any future playable height/worldgen expansion needs a new post-1.0 decision.

## Verification and delivery

1.0a5; F-05 and the full WORLDGEN suite.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
