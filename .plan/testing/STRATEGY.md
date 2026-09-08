# Testing strategy

## Independent oracles

Use pristine Beta 1.7.3 client and server binaries as behavioral oracles, with decompiled sources as explanatory evidence. A rewritten implementation must not generate its own expected answers. Capture baseline artifacts before refactoring. Record the jar hash, mappings/decompiler version, runtime, OS, seed, settings, tick schedule and scenario inputs.

Use unmodified vanilla 1.17.1 as the persistence-consumer oracle. Use paired control-world upgrades to distinguish normal vanilla changes from Origins conversion defects. Decompiled modern source explains field layouts; it does not override Beta behavior.

## Layers and cadence

| Layer | Examples | Cadence |
|---|---|---|
| Unit/property tests | State validation, palette packing, coordinates, Beta adapters | Affected changes and CI |
| Differential scenarios | Movement, redstone, updates, RNG, inventories | Affected subsystem and milestone promotion |
| World fixtures | Seed/order-controlled chunk dumps, import/save/reload | World/model/codec changes |
| Process tests | Dedicated classpath, connect/reconnect, pause and shutdown | Runtime/build/network changes |
| Visual/audio/manual | Fixed scenes, input, sound, fullscreen, high-DPI | Platform/rendering changes |
| Consumer tests | Unmodified vanilla play and upgrade-control comparison | Persistence milestone and release |
| Package tests | Fresh direct launch, patch reconstruction, Prism import | Packaging and release |
| Performance/soak | Frame/tick distribution, memory growth, save latency | a9/b2 and relevant regression |

Do not rerun unrelated expensive suites for every tiny edit. Promotion requires the whole applicable milestone gate. When a test fails, minimize the fixture, classify the failure and record the changed behavior before changing code or the expected baseline.

## Evidence format

Use `build/reports/origins/<revision>/<suite>/` for generated reports (working default), with hashes and a durable summary in `.plan/status/`. Large private worlds/captures belong in local fixture storage or an explicitly chosen artifact store, not automatically in public source control. Keep the fixture manifest and reproducible recipes tracked.

Each result records test ID, baseline/reference hash, candidate revision, exact command or manual steps, environment, expected result, observed result, PASS/FAIL/NOT RUN/BLOCKED and evidence path/hash. A CI skip must remain NOT RUN. Repeat unstable checks and identify nondeterminism instead of masking it with arbitrary tolerances.

Performance measurements use the same hardware, JVM settings, world, view distance and workload. Capture median and tail frame/tick times, memory trend, chunk throughput and save latency. Establish numeric budgets in a1/a2 after baseline measurement, then lock them before optimization. A release cannot claim a speedup or universal smoothness without comparable measurements.

See [behavior](BEHAVIOR.md), [worldgen](WORLDGEN.md), [persistence](PERSISTENCE.md) and [platforms](PLATFORMS.md).
