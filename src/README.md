# Working game sources

Develop Origins here; `reference/` is immutable evidence, never a build input.

| Root | Ownership |
|---|---|
| main/java | 420 shared classes: simulation, entities, blocks/items, chunks, generation, inventory, saves, packets and transport |
| client/java | 270 classes: client entry point, local SP host, remote world, presentation/input/audio |
| server/java | 39 classes: dedicated entry point, lifecycle, connections and server authority |
| client/resources | 84 original client resources |
| server/resources | 5 original server resources |

Start at [World](main/java/net/minecraft/src/World.java), [client Minecraft](client/java/net/minecraft/client/Minecraft.java), and [MinecraftServer](server/java/net/minecraft/server/MinecraftServer.java). Main compiles independently, once, into both artifacts. No duplicate client/server class names remain. Shared implementation preserves separate Beta singleplayer and multiplayer semantics; it does not introduce an integrated server.

[IMPORT.json](IMPORT.json) is the immutable original source/resource snapshot. [EVOLUTION.json](EVOLUTION.json) records moves, aliases, changes and source origins. Verify current files with `python3 tools/verify_working_sources.py`. The original importer verification intentionally reports differences after development; never reimport over working code.

[Full-model ledger](../.plan/status/evidence/2026-09-16-a1-full-model/merge-ledger.json), scoped mapping files beside it, and [final merge evidence](../.plan/status/evidence/2026-09-17-a1-unified/README.md) explain the union. Common algorithms are shared; unique client APIs and server producers/persistence helpers are retained. Shared code has no graphics/audio or client singleton dependency. ClientSleepThread and ServerSleepThread retain their distinct host lifetimes.

Use the Gradle source sets for IDE import and [BUILDING.md](../BUILDING.md) for launches. Java 8/LWJGL 2 are the a1 bootstrap; a2 owns platform modernization. a1 source merge and bounded practical acceptance are complete; see [CURRENT](../.plan/status/CURRENT.md). Owner confirmation is sufficient for a1 playability; the optional graphical harness is not a routine prerequisite.
