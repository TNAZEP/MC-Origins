# Current project status

**Target release:** 1.0  
**Current milestone:** 1.0a1 — Baseline, build and unified client/server source  
**Milestone state:** Not started  
**Last updated:** 2026-09-07 — planning kit initialization  
**Repository revision / branch:** Not inspected in target project  
**Active session:** None

## Completed

- Planning documents, architecture decisions, milestone definitions, reference scaffold and session templates prepared.
- Final conversation decisions reconciled; worldgen expansion explicitly excluded from 1.0.

## In progress

No game implementation work has started in this kit.

## Not started

All alpha, beta and release implementation/qualification. Target source inventory, baseline artifact hashing, reference decompilation, toolchain selection and build setup.

## Verification

| Check | Result | Evidence / reason |
|---|---|---|
| Target source inventory | NOT RUN | Game sources not supplied to planning workspace |
| Game build | NOT RUN | Build system not yet implemented |
| Client launch | NOT RUN | No game artifact |
| Dedicated server / multiplayer | NOT RUN | No game artifact |
| Behavior / worldgen / persistence | NOT RUN | No runtime fixtures |
| Platform / package qualification | NOT RUN | No release artifacts |

## Important decisions

Beta behavior is the 1.0 spec. Unified sources precede modernization. Java 25/LWJGL 3/shader OpenGL, states and native 1.17.1 persistence are required. Storage supports 256 blocks but normal Beta gameplay/worldgen bounds stay unchanged. Integrated server and Vulkan are deferred.

## Missing inputs and risks

Expected inputs are the working RetroMCP Beta sources plus pristine matching client/server references. Their actual availability must be checked in the destination repository. This is an input requirement, not a claim that the user's project lacks them. See QUESTIONS and RISKS.

## Next bounded action

Inspect the target repository and reference manifest, identify the actual Beta client/server source correspondence, record tool/runtime availability, then establish the pristine launch baseline before editing shared logic. Update [NEXT](NEXT.md) and append the session to [HISTORY](HISTORY.md).

## Working tree and recovery

No target-project edits or save conversions performed by this kit. Preserve existing user work when installing it. Do not overwrite an existing AGENTS.md or status history without merging.

## Planning correction

The final conversation reply has now been incorporated: initial references are b1.7.3, 1.3.2, 1.13.2 and 1.17.1, both sides for each. The guide, source/fixture scaffold, manifest and affected milestones are corrected. No runtime implementation status changed.
