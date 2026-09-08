# Architecture Decision Records

Accepted owner decisions record explicit project direction. Accepted engineering defaults make the plan actionable but can be refined with evidence within that direction. “Accepted” is a design status, not a claim of tested implementation.

| ADR | Decision | Status |
|---|---|---|
| [0001](0001-beta-behavior.md) | Beta 1.7.3 is the 1.0 behavioral specification | Accepted — owner decision |
| [0002](0002-unified-codebase.md) | One shared client/server source tree from the first alpha | Accepted — owner decision |
| [0003](0003-local-singleplayer.md) | Integrated server is deferred until after 1.0 | Accepted — owner decision |
| [0004](0004-java25-lwjgl3.md) | Java 25 and LWJGL 3 are required platform targets | Accepted — owner decision |
| [0005](0005-shader-rendering.md) | Shader OpenGL behind an explicit future-backend API | Accepted — owner decision; GL 3.3 capability level is a working default |
| [0006](0006-registries-states.md) | Stable names and real internal BlockStates | Accepted — owner decision |
| [0007](0007-native-1171-persistence.md) | Native 1.17.1-compatible persistence and direct vanilla play | Accepted — owner decision |
| [0008](0008-height-worldgen.md) | 256-capable storage with unchanged Beta generation and behavior | Accepted — owner decision; explicit separation of gameplay bounds is its conservative implementation |
| [0009](0009-safe-migration.md) | Immutable-source import and explicit compatibility direction | Accepted — plan engineering default supporting owner compatibility goal |
| [0010](0010-milestone-session-model.md) | Discrete alpha/beta releases and persistent session state | Accepted — owner decision; exact milestone count is a working plan |
| [0011](0011-build-artifacts.md) | Unified reproducible build and verified distribution artifacts | Accepted — owner artifact requirements; Gradle/task details are engineering defaults |

Use [the ADR template](../templates/ADR.md) for new consequential decisions. Link affected requirements, milestone, code and tests. Keep superseded records with a pointer to their successor. The detailed ordering/count of milestones and exact packages are adjustable; Beta scope and the owner-selected technology/compatibility goals are not silently adjustable.
