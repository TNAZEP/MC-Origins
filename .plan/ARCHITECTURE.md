# Architecture

## Source ownership

Start with source sets rather than a speculative multi-module framework:

```text
src/main/java/        shared game, states, chunks, worldgen, codecs, packet definitions
src/main/resources/  shared non-client data
src/client/java/      client entry point, local SP host, remote-world view, UI, render, platform
src/client/resources/ shaders and presentation resources
src/server/java/      dedicated entry point, console, connections, server lifecycle
src/test/java/        fast deterministic tests
src/integrationTest/ scenario and artifact tests (working layout)
reference/           immutable study material, never an implicit compile source root
```

Both artifacts use the same compiled shared classes. The dedicated server must load without client classes, a display, OpenGL, GLFW or OpenAL initialization. Shared simulation must not reach into a client singleton. Pass small interfaces for sound/particle notifications and environment services; a headless host may discard presentation events without discarding gameplay events.

The initial a1 working import temporarily keeps all supplied client/server classes in their respective Java roots, including overlapping class names. `main/java/` is reserved until baseline evidence and dependency review support moving implementations into it. This is an intermediate source layout, not an exception to unified-source acceptance or permission to modernize duplicated simulation separately. See [working source guide](../src/README.md) and its per-file import provenance. Compile each side separately until the corresponding duplicates are reconciled; never compile all roots as one flat tree.

Classify each client/server difference before merging: naming/decompiler artifact, equivalent logic, intentional side behavior, or lifecycle-only code. Record the decision in a merge ledger with original class/method names. Never flatten genuine SP/MP differences simply because modern Minecraft unifies them.

## 1.0 runtime model

```text
Singleplayer: client host -> local Beta world simulation -> shared world systems
Multiplayer:  client remote view <-> shared Beta packet codecs <-> dedicated server simulation
Presentation: world snapshot/events -> scene renderer -> render API -> OpenGL backend
Persistence:  Beta import -> canonical model <-> 1.17.1 codec -> region/world files
```

The local SP world preserves its original pause, save, tick, inventory and dimension behavior. The remote view preserves Beta client prediction and server authority. Shared classes can implement common algorithms while explicit world/host policies preserve differing entry paths. Future integration should be possible by introducing another host/transport; do not create it now.

## Timing, threading and determinism

Initially keep simulation ownership and ordering faithful to Beta. Do not add parallel worldgen, asynchronous block ticks, sorted entity iteration, new RNG streams, or a different fixed-timestep accumulator without differential evidence. Treat Java collection iteration, signed arithmetic, float/double precision, RNG call counts, event timing and chunk population order as possible behavior.

Rendering resources belong to the graphics context thread. Any future background mesh construction takes immutable snapshots and publishes results with a chunk revision check; it must not mutate simulation state or advance simulation RNG. Persistence snapshots must be coherent with the tick state; choose a simple synchronous implementation before introducing save concurrency.

## Boundary contracts

| Boundary | Allowed data | Forbidden coupling |
|---|---|---|
| Platform -> input | Events, key/button state, timing, focus | GLFW handles in gameplay |
| Simulation -> presentation | State snapshots, events | Direct GL calls or GPU resources |
| Game -> renderer | Meshes, material/pipeline descriptions, camera | Legacy GL state/matrix stack |
| Model -> persistence | Canonical world/state data and explicit mappings | NBT tags controlling behavior |
| Model -> network | Packet adapters and Beta numeric mappings | Runtime registry indices as wire identity |
| Host -> shared logic | Context/side policy and owned services | Global client assumption |

Cross-reference [rendering](architecture/RENDERING.md), [data model](architecture/DATA-MODEL.md), [persistence](architecture/PERSISTENCE.md) and [build](BUILD-DISTRIBUTION.md). Module and type names here are design vocabulary; confirm them against the actual source before introducing packages.
