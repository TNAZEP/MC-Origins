# ADR-0004: Java 25 and LWJGL 3 are required platform targets

**Status:** Accepted — owner decision  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

The old Java/native stack limits maintenance and platform support.

## Decision

Use Java 25 for final build/runtime and LWJGL 3 behind platform/input/audio services. Permit a documented a1 bootstrap runtime only until the bounded platform port.

## Alternatives and rejected approaches

Reject wholesale idiomatic Java rewrites or replacing scattered LWJGL 2 calls with scattered GLFW calls.

## Consequences

Pin compatible tooling/natives, preserve input/timing/audio behavior and qualify actual platforms.

## Verification and delivery

1.0a2/b2; F-01/F-02.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
