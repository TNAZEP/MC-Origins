# ADR-0005: Shader OpenGL behind an explicit future-backend API

**Status:** Accepted — owner decision; GL 3.3 capability level is a working default  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

Fixed-function OpenGL must be replaced while a later Vulkan backend remains feasible.

## Decision

Implement shader OpenGL with explicit resources, transforms, pipelines and draw/pass contracts. Keep GL internals inside the backend. Vulkan itself is post-1.0.

## Alternatives and rejected approaches

Reject GL-function wrappers as the public abstraction and reject visual upgrades as a side effect of shaders. Do not overbuild a general graphics engine.

## Consequences

Beta visual captures, full-pass coverage and a written explicit-backend mapping review are required. Confirm the proposed GL capability level in a2.

## Verification and delivery

1.0a2/a3; F-03.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
