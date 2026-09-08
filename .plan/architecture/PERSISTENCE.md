# Persistence design and implementation contract

## Target

Origins' normal save output targets **Minecraft Java Edition 1.17.1**. Direct playability means copying the saved world into an unmodified vanilla 1.17.1 installation and continuing without an Origins converter, plugin or required datapack. Merely adding an export-only path does not satisfy the native-save target.

Implement a dedicated codec around the canonical model. Do not transplant 1.17 simulation classes into Beta. Use exact 1.17.1 identifiers, property schemas, field types and packing rules derived from that version's source and vanilla-generated fixtures. Record the verified DataVersion and source location in the schema inventory before writing it; a correct version label on an incorrect schema is worse than a clear rejection.

## Schema work packages

| Area | Required investigation and mapping |
|---|---|
| Region container | Sector allocation, headers, compression, length checks, timestamps, truncation/oversize handling |
| Terrain chunks | 1.17.1 nesting/tag names, position, status, section indices, palettes, packed longs, empty sections |
| Light and heightmaps | Correct nibble order, sky/block light and trust/recompute flags; valid packed heightmaps |
| Biomes | Beta temperature/rainfall/runtime biome behavior versus valid 1.17.1 persisted biome data |
| Entities | 1.17.1 entity-region storage, identity/UUIDs, passengers/riding, motion, health and dimension ownership |
| Block entities | Type IDs, positions, inventories, signs, spawners, furnaces, piston/transient data |
| Items | Names, count, damage, durability, metadata variants, nested item NBT |
| Players | Singleplayer level player record, dedicated player files, identity mapping, inventory/armor, position, health, spawn, dimension |
| Level metadata | Seed, time, weather, spawn, difficulty, game mode defaults and 1.17.1 world-generation settings |
| Ticks | Block/fluid scheduled queues, timing units, priorities/ordering, pending events that survive Beta saves |
| Dimensions | Overworld/Nether folder mappings, portals and respawn behavior; valid vanilla defaults for modern-only metadata |
| Auxiliary data | Maps, counters/IDs, any Beta persistent records discovered in the inventory |
| Modern structural fields | Valid absent/empty structures and POI where appropriate; never fabricate generated features |

The inventory must say which fields are mandatory, derived, optional, ignored or unsupported, with fixture evidence. These work packages deliberately do not pretend to be a complete byte-level codec specification before reference inspection.

## Lossless Origins operation and vanilla interpretation

Keep Beta semantics in the canonical model. Vanilla fields use meaningful 1.17.1 equivalents. Where vanilla cannot represent Beta-only transient state, prefer a versioned `origins/` sidecar that vanilla can ignore. Use it only when a concrete round-trip test proves it necessary; record field ownership and collision/version rules in an ADR.

Any sidecar must be unnecessary for vanilla play. Origins must not silently apply stale sidecar state after vanilla has edited the world. Associate preservation records with a canonical content fingerprint/revision excluding volatile storage metadata, detect external changes and refuse unsupported reverse loading before writing. Prove a reliable invalidation policy in a6/a7. A sidecar does not create a promise of vanilla-to-Origins round trips.

Beta runtime biomes/generator identity may need Origins-owned metadata because 1.17's persisted biomes and generator settings mean something different. Write valid vanilla generator settings for what vanilla will do next; keep Origins' Beta generator selection outside that interpretation. Modern terrain will generate when the world is played in modern vanilla. Do not ship a datapack to force Beta generation there.

## Safe import and write lifecycle

1. Identify the Beta McRegion format and inventory all region, player and auxiliary records. Refuse an unknown/corrupt input before modifying it.
2. Read the original world immutably. Create a separate destination/staging world with a manifest recording source hashes and conversion version.
3. Convert all required files, including dimensions and data outside spawn. Record unresolved mappings by coordinate/entity/player; never replace unknown content with air silently.
4. Validate the entire destination and perform a reload. Finalize only after validation, with a completion marker and recovery instructions. The original remains the backup.
5. For normal saves, use temporary files and checked replacement where possible, a world lock and recovery records for multi-file updates. Do not claim all region/entity/player files form an atomic transaction without proving it.
6. Fault-inject interrupted conversion, truncated regions, full disk, permission errors, stale locks and partial normal saves. Recovery must select a known consistent state or stop with a recoverable error.

## Explicit compatibility boundaries

Origins -> Origins must preserve all supported Beta state across ordinary save/reload. Beta -> Origins must preserve the source world and meaningful data. Origins -> vanilla 1.17.1 must match an ordinary vanilla upgrade as closely as practical. Vanilla 1.17.1 -> Origins is unsupported once external editing or unsupported content is detected. Origins -> Beta downgrade is not a release requirement.

The forward path 1.17.1 -> a pinned 1.18.x release is tested using Mojang's runtime upgrader. Do not synthesize 1.18 blending metadata or promise identical modern terrain. [Save tests](../testing/PERSISTENCE.md) define the paired control experiment.
