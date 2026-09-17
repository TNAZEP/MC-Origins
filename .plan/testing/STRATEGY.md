# Testing strategy

## Practical default

[ADR-0012](../decisions/0012-practical-verification.md) records the owner’s priority: a working, buildable, maintainable game. Implement coherent changes, build both artifacts, and smoke-test affected paths. Add tests in proportion to concrete risk. Exact binary identity and exhaustive original-jar comparisons are not routine acceptance requirements. Existing matrices remain available for targeted diagnostics; expanding them must not displace source-unification work.

## Independent oracles

When a specific behavior needs comparison, use pristine Beta 1.7.3 binaries and sources as independent evidence. Capture the inputs needed to reproduce that check; do not require a new original-jar baseline for every refactor.

Use unmodified vanilla 1.17.1 as the persistence-consumer oracle. Use paired control-world upgrades to distinguish normal vanilla changes from Origins conversion defects. Decompiled modern source explains field layouts; it does not override Beta behavior.

## Layers and cadence

| Layer | Examples | Cadence |
|---|---|---|
| Unit/property tests | State validation, palette packing, coordinates, Beta adapters | Affected changes and CI |
| Differential scenarios | Movement, redstone, updates, RNG, inventories | Specific uncertainty or regression requiring an original comparison |
| World fixtures | Seed/order-controlled chunk dumps, import/save/reload | World/model/codec changes |
| Process tests | Dedicated classpath, connect/reconnect, pause and shutdown | Runtime/build/network changes |
| Visual/audio/manual | Fixed scenes, input, sound, fullscreen, high-DPI | Platform/rendering changes |
| Consumer tests | Unmodified vanilla play and upgrade-control comparison | Persistence milestone and release |
| Package tests | Fresh direct launch, patch reconstruction, Prism import | Packaging and release |
| Performance/soak | Frame/tick distribution, memory growth, save latency | a9/b2 and relevant regression |

Do not rerun unrelated expensive suites for every tiny edit. Promotion requires the whole applicable milestone gate. When a test fails, minimize the fixture, classify the failure and record the changed behavior before changing code or the expected baseline.

## Evidence format

Use `build/reports/origins/<revision>/<suite>/` for generated reports (working default), with hashes and a durable summary in `.plan/status/`. Large private worlds/captures belong in local fixture storage or an explicitly chosen artifact store, not automatically in public source control. Keep the fixture manifest and reproducible recipes tracked.

Record the command or manual steps, revision/environment, expected and observed result, and PASS/FAIL/NOT RUN/BLOCKED concisely. Include reference hashes and detailed fixture metadata when a comparison actually uses them. A CI skip must remain NOT RUN. Repeat unstable checks and identify nondeterminism instead of masking it with arbitrary tolerances.

Performance measurements use the same hardware, JVM settings, world, view distance and workload. Capture median and tail frame/tick times, memory trend, chunk throughput and save latency. Establish numeric budgets in a1/a2 after baseline measurement, then lock them before optimization. A release cannot claim a speedup or universal smoothness without comparable measurements.

See [behavior](BEHAVIOR.md), [worldgen](WORLDGEN.md), [persistence](PERSISTENCE.md) and [platforms](PLATFORMS.md).
