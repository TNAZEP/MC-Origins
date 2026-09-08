# ADR-0009: Immutable-source import and explicit compatibility direction

**Status:** Accepted — plan engineering default supporting owner compatibility goal  
**Recorded:** 2026-09-07  
**Superseded by:** None

## Context

Import and multi-file normal saves can lose data; modern vanilla can rewrite Origins-specific assumptions.

## Decision

Import Beta worlds into a separately validated destination, retain originals, and test recovery. Treat vanilla-forward migration as one-way for 1.0 and reject unsupported reverse data before writes.

## Alternatives and rejected approaches

Reject in-place conversion without a recoverable original and silent unknown-to-air mappings. Any minimal sidecar must not be required by vanilla or applied after external edits.

## Consequences

Crash/error tests and content-accounting manifests are required; compatibility losses are classified against the vanilla control.

## Verification and delivery

1.0a6/a7; G-DATA and P-02/P-06.

## Revisit policy

Record new evidence and a superseding ADR rather than erasing history. A change to locked owner scope requires an explicit owner decision; routine implementation details remain within the current authorized plan. No implementation verification is claimed by this ADR.
