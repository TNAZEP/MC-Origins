# Roadmap

1.0 is the foundation release. All implementation is unstarted in this kit. The sequence is a dependency-aware working plan; each milestone is a usable checkpoint, not an arbitrary deadline.

| Version | Deliverable | Dependency |
|---|---|---|
| [1.0a1](versions/1.0a1.md) | Baseline, build and unified client/server source | Start |
| [1.0a2](versions/1.0a2.md) | Java 25, LWJGL 3 and platform boundary | 1.0a1 |
| [1.0a3](versions/1.0a3.md) | Complete shader OpenGL and render abstraction | 1.0a2 |
| [1.0a4](versions/1.0a4.md) | Namespaced registries and real BlockStates | 1.0a3 |
| [1.0a5](versions/1.0a5.md) | Section storage with unchanged Beta bounds | 1.0a4 |
| [1.0a6](versions/1.0a6.md) | Native 1.17.1 persistence | 1.0a5 |
| [1.0a7](versions/1.0a7.md) | Beta import and vanilla migration qualification | 1.0a6 |
| [1.0a8](versions/1.0a8.md) | Distribution, patch reconstruction and Prism | 1.0a7 |
| [1.0a9](versions/1.0a9.md) | Foundation completion and feature-freeze audit | 1.0a8 |
| [1.0b1](versions/1.0b1.md) | Behavioral regression and compatibility polish | 1.0a9 |
| [1.0b2](versions/1.0b2.md) | Platform, performance and stability polish | 1.0b1 |
| [1.0b3](versions/1.0b3.md) | Release rehearsal and final documentation | 1.0b2 |
| [1.0](versions/1.0.md) | Foundation release | 1.0b3 |

## Why this order

Merge once before porting duplicated code. Resolve the Java/native lifecycle next, including the minimum shader bridge needed for a usable core-context checkpoint. Finish rendering before changing the model. Names/states provide the foundation for palettes; sections then support the exact persistence codec. Native saves precede the full Beta importer and control-upgrade qualification. Package the proven model before the integrated feature-freeze audit. Betas stabilize the complete foundation.

Reference acquisition, baseline capture, schema investigation and packaging feasibility may begin before their delivery milestone as bounded discovery. That does not authorize partially shipping future user-facing features. A blocking dependency remains blocking until resolved; independent documentation/research can still proceed.

## Milestone adjustments

If a step cannot remain buildable/launchable, expand or split it before promotion. Update version files, dependencies, status and a concise planning decision. Do not use the next alpha to justify a broken completed alpha. Add beta milestones when needed. No dates or effort estimates are promised before inspecting the actual source baseline.

## After 1.0

1.0.x maintains the foundation. The 1.1 planning queue may consider an integrated server, deliberate worldgen evolution, use of upper gameplay space, content additions, modding interfaces or a Vulkan backend. These are candidates, not an approved 1.1 scope or dependency of 1.0. Each needs a new behavior/compatibility decision and test plan. Never slip them into a 1.0 cleanup session.
