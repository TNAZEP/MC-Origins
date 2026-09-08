# Compatibility goals

| Direction or domain | 1.0 contract | Evidence |
|---|---|---|
| Beta singleplayer -> Origins singleplayer | Preserve behavior and meaningful save state; safe copy conversion | Golden scenarios and import inventory |
| Beta dedicated save -> Origins server | Preserve player/world/dimension data | Two-player migration and reconnect |
| Origins client <-> Origins server | Required Beta gameplay/network semantics | Repeated SP/MP scenario suite |
| Origins normal save -> vanilla 1.17.1 client | Required direct playability, meaningful equivalents preserved | Copy without edits; play/save/reload |
| Origins normal save -> vanilla 1.17.1 dedicated server | Same target with explicit player-identity mapping | Join, inventories, dimensions, restart |
| Origins -> Origins save/reload | Required supported-state round trip | Semantic dumps and tick continuation |
| Vanilla 1.17.1 edited world -> Origins | Unsupported for 1.0 | Detect unsupported input before writes |
| Origins -> vanilla Beta downgrade | Not promised | Document forward-only migration |
| Origins -> 1.17.1 -> pinned 1.18.x | Secondary forward-upgrade goal; tested and reported | Runtime conversion and boundary exploration |
| Origins <-> vanilla Beta network | Preserve codec compatibility where feasible; qualification target, not assumed universal support | Both cross-pair tests and limitation record |
| Origins <-> vanilla 1.17.1 network | Out of scope | Save format does not imply protocol compatibility |
| Old mods/resource packs | No general binary compatibility promise | Test only explicitly listed artifacts |

## What preserving data means

Terrain/block variants, orientation, inventories and durability, entities, block entities, player state, dimensions, seed and world metadata must survive where meaningful equivalents exist. Enumerate and account for every Beta record type. Losses intrinsic to a vanilla version upgrade must be demonstrated by the control path; do not label unexplained Origins losses as normal vanilla behavior.

Once opened in vanilla 1.17.1, simulation follows 1.17.1: redstone/physics/mobs and newly generated chunks can differ. That is expected. Incorrect block conversion, missing inventories, erased terrain, broken dimension data or malformed chunks are Origins defects when absent from the control upgrade.

## Triage

Classify each difference as exact preservation, equivalent representation, expected vanilla change proven by control, Origins defect, unsupported input, or unresolved. Unknowns remain unresolved. Each exception records the affected data and route, source evidence, fixture, practical impact and decision. See [DEVIATIONS](status/DEVIATIONS.md).

Cross-version networking is useful to preserve, especially during the initial merge, but must not be advertised without running both directions. Modernized bytecode and class packaging do not imply compatibility with historical mod loaders or Java runtimes.
