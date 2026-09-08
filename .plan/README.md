# Minecraft: Origins — 1.0 foundation plan

> Minecraft Beta 1.7.3 is the behavioral specification for version 1.0. Its purpose is to replace obsolete internal architecture with a maintainable modern foundation while preserving the observable game as faithfully as practical.

**Preserve the origins on the outside. Modernize everything underneath.**

This is an executable planning contract, not evidence of completed implementation. The supplied status is deliberately unstarted. The project source and binaries were not present while this kit was written.

## Start here each session

Read [current status](status/CURRENT.md), [next tasks](status/NEXT.md), the current [milestone](versions/1.0a1.md), and [workflow](codex/WORKFLOW.md). Confirm the handoff against the repository. The root `AGENTS.md` points Codex here; `.plan` itself is an ordinary documentation folder, not an automatic execution mechanism.

## Design and delivery index

| Document | Purpose |
|---|---|
| [Identity](IDENTITY.md) | Project name, purpose, audience |
| [Principles](PRINCIPLES.md) | Locked behavioral and engineering rules |
| [Scope](SCOPE.md) | Included work, exclusions, change control |
| [Architecture](ARCHITECTURE.md) | Source sets, runtime ownership, boundaries |
| [Rendering](architecture/RENDERING.md) | Shader migration and future backend boundary |
| [Data model](architecture/DATA-MODEL.md) | Registries, states, sections, height policy |
| [Persistence](architecture/PERSISTENCE.md) | Native save contract, import, mapping, safety |
| [Compatibility](COMPATIBILITY.md) | Claims, directions, limitations |
| [Versioning](VERSIONING.md) | aN/bN/release model and promotion |
| [Roadmap](ROADMAP.md) | Dependencies and milestone navigation |
| [Acceptance](ACCEPTANCE.md) | Cross-cutting release gates |
| [Testing strategy](testing/STRATEGY.md) | Oracles, coverage, evidence |
| [Behavior tests](testing/BEHAVIOR.md) | Gameplay, rendering, SP/MP checks |
| [Worldgen tests](testing/WORLDGEN.md) | Deterministic seed and order controls |
| [Save tests](testing/PERSISTENCE.md) | Conversion, vanilla play, upgrade comparison |
| [Platform tests](testing/PLATFORMS.md) | OS/GPU/runtime qualification |
| [Build/distribution](BUILD-DISTRIBUTION.md) | Artifacts, patches, launcher packaging |
| [Reference guide](REFERENCE-SOURCES.md) | Versions to decompile and layout |
| [Research sources](SOURCES.md) | Provenance and unresolved verification |
| [ADRs](decisions/README.md) | Accepted decisions and working defaults |
| [Risk register](status/RISKS.md) | Known project risks and resolution owners |
| [Open questions](status/QUESTIONS.md) | Bounded discovery tasks |
| [Templates](templates/README.md) | Repeatable session/milestone records |

## Authority and interpretation

The owner's explicit current direction takes precedence. Within this kit, locked decisions in principles/scope and accepted ADRs constrain implementation; architecture and milestones explain how to deliver them. Status reports describe reality and cannot redefine scope. Newer explicit decisions supersede conflicting older suggestions, with the supersession recorded.

The source conversation initially proposed 1.2.5 persistence and expanded terrain. These were superseded by 1.17.1 persistence and **no worldgen changes for 1.0**. Sea level stays at Beta's generator threshold; verify exact boundary comparisons from the source, rather than rewriting them from a prose approximation. Additional storage does not grant a higher player build limit.

The milestone count, detailed module names, proposed OpenGL baseline, platform matrix, and test fixture recipes are engineering defaults introduced by this plan. They are not claims that the owner previously chose every detail. Adjust them with evidence and a decision record while retaining all locked outcomes.

The final conversation specifies four initial reference pairs: **b1.7.3, 1.3.2, 1.13.2 and 1.17.1**, both sides for each, under `reference/source/`. Use 1.3.2 during early source-unification study and 1.13.2 during platform/model modernization. 1.2.5 is not part of the initial set. See the reference guide for authority boundaries.
