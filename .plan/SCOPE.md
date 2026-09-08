# 1.0 scope

## Required

- Reproducible command-line build, one shared/client/server source tree and separately launchable client and dedicated server.
- Java 25 as the final compile/runtime target; LWJGL 3 behind window/input/audio/platform services.
- Shader-based OpenGL, explicit matrices/buffers/pipelines, and an API that can accommodate a later Vulkan backend.
- Namespaced block/item identities and a genuine BlockState-style model; Beta simulation rules and compatibility mappings remain authoritative.
- Sectioned, 256-height-capable world storage with Beta worldgen, sea level, placement limits and simulation semantics preserved.
- Native 1.17.1-compatible persistence; safe Beta McRegion import; direct vanilla 1.17.1 playability as far as meaningful vanilla equivalents permit.
- Behavioral, visual, multiplayer, migration and packaging verification; persistent status and documentation.
- Local `Minecraft.jar`, dedicated-server artifact, changed/added-class patch ZIP plus required installation metadata, and Prism-importable instance ZIP.

## Deferred past 1.0

Worldgen redesign or vertical scaling; new blocks/items/mobs/biomes/recipes; higher gameplay build limit; modern combat, hunger or modern redstone; visual upgrades; integrated server/LAN hosting through one; Vulkan implementation; new dimensions; general mod loader/API guarantees; arbitrary modern world import; vanilla 1.17.1 wire-protocol support.

## Scope discipline

A task must identify the current milestone and acceptance criterion it advances. Cleanup belongs only where needed to make that change safe or maintainable. Future hooks may be small boundaries, not partially implemented features.

Work order and milestone subdivision are adjustable implementation choices. Record dependency changes in ROADMAP and CURRENT; do not mark unfinished work complete merely to move its defects into beta. A new feature during beta requires leaving the frozen scope and an explicit owner decision; fixing a faulty architectural implementation of an existing requirement remains bug work when narrowly documented.

## Reconciliation of earlier discussion

| Earlier idea | Final rule |
|---|---|
| Port 1.2.5 Anvil as the target | Use 1.17.1-compatible persistence; 1.2.5 is excluded from the initial source set and available only for targeted later investigation |
| Scale Beta worldgen for extra space | No worldgen changes in 1.0 |
| Extra 128 blocks available for play | Capacity only; preserve Beta observable limits |
| Shared source implies integrated server | Unified sources first; integrated server after 1.0 |
| Unlimited IDs | Stable names and scalable internal indexing, not literally unbounded memory |
