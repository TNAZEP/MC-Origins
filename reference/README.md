# Local Minecraft reference library

Follow [the reference guide](../.plan/REFERENCE-SOURCES.md). The initial set is **b1.7.3, 1.3.2, 1.13.2 and 1.17.1, both client and dedicated server for all four**, matching the final source-conversation recommendation. No game artifacts are populated yet.

`source/beta-1.7.3/` is the behavioral authority. `source/release-1.3.2/` informs early source organization and responsibility boundaries. `source/release-1.13.2/` informs registries/states, legacy conversion and the LWJGL transition. `source/release-1.17.1/` is the persistence/output authority. Each version README states its limits. Newer source never overrides Beta behavior, and 1.3.2 study does not authorize integrated singleplayer in 1.0.

Within each client/server directory, keep original binaries/metadata, mappings, untouched decompiled output, optional resources, separate fix patches and authored notes distinct. Never put the reference trees on the build classpath. Populate manifest.json from actual observations; null means unknown.

Keep baseline worlds/screenshots under `fixtures/beta-1.7.3/` and upgrade-control data under `fixtures/migration/`. Run experiments on separate copies. The initial set excludes 1.2.5 and other intermediate source trees; add one only when a concrete question needs it. Runtime installs used in control-upgrade tests are separate from source-reference selection.
