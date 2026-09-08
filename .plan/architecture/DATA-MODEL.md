# Registries, states and world storage

## Identity and behavior

Use validated `namespace:path` keys and a deterministic registry bootstrap. Reject duplicates and unresolved required entries. Freeze registries before world loading. Runtime compact integers may index tables and palettes, but are neither persistent identities nor automatically Beta packet IDs.

A block has an explicit state definition with named, typed, finite properties, validated combinations and a canonical default. A BlockState is immutable and canonicalizable; equality must be stable. Transition operations validate properties instead of manipulating a universal metadata nibble. Avoid creating modern properties with modern mechanics just because 1.17 uses them.

Keep three distinct mappings:

```text
Beta ID + metadata <-> internal Beta-behaving BlockState
internal BlockState <-> 1.17.1 name/properties (+ any Origins-only preservation data)
internal BlockState <-> Beta packet encoding for supported Beta content
```

Not every mapping is one-to-one. Doors, stairs, leaves, fluids, redstone, slabs, pistons, orientation and transient update flags need focused tests. If multiple Beta states collapse to a single vanilla representation, preserve the distinction for Origins reloads in a versioned sidecar or explicit internal property. Never silently lose it. Item damage/durability is separate from block metadata and must not be flattened into the wrong identifier.

Enumerate all valid Beta blocks/items/metadata with source-backed expected meaning. Record invalid metadata observed in real saves separately: either preserve it safely or report a precise incompatibility; do not normalize it without evidence. Test reversed registry insertion order so accidental index persistence cannot pass unnoticed.

## Sections and bounds

Storage is 16 x 16 x 16 sections, with capacity for Y=0..255 (section indices 0..15). Use sparse empty sections and palettes where useful. Handle negative X/Z with floor division/modulo and region boundaries independently of local coordinates.

**Behavior policy:** Beta worldgen and gameplay retain their original 128-high world assumptions where externally observable. Do not let the new allocation size become a new loop bound for generation, random ticks, spawn attempts, lighting behavior, placement checks or packet encoding. Original air/out-of-range read semantics must be measured and retained. Normal 1.0 worlds do not gain new buildable space at Y=128..255.

Test the storage API at Y=0,15,16,127,128,255 and reject invalid storage coordinates. Separately test player placement/collision/world reads around the Beta ceiling. A low-level synthetic storage fixture above the Beta ceiling proves capacity; it does not enable gameplay there.

Keep light data, heightmaps, biome information, entities, block entities and scheduled ticks separate from block palette identity. Changes to containers must retain iteration/update order where observable. Do not infer a 256-high Nether generator from the save dimension's capacity.

Arbitrary modern worlds, modern-only blocks and tall builds are not a supported 1.0 import path. Detect them before edits and explain the unsupported profile rather than loading partial data and dropping the rest on save.
