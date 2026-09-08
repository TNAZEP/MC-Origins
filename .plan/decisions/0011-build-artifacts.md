# ADR-0011: Unified reproducible build and verified distribution artifacts

**Status:** Accepted — owner artifact requirements; Gradle/task details are engineering defaults  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

The original request requires a client jar, patch ZIP and Prism instance; source unification also requires a dedicated artifact.

## Decision

Produce all four from a documented pinned build. Prove patch reconstruction including additions/removals/names and launch dependencies, and prove a fresh Prism import.

## Alternatives and rejected approaches

Reject assuming Java 25/LWJGL 3 works in an unchanged historical launcher merely because classes were copied. Keep reference material out of release artifacts.

## Consequences

Patch-name mapping is investigated in a1; distribution qualification is complete in a8. Local full outputs and distributable patch content are explicitly distinguished.

## Verification and delivery

1.0a1/a8/b3; F-08.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
