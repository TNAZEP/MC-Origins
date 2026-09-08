# 1.17.1 reference pair

## Purpose

Persistence and output target. Initial reference set: both client and dedicated server. No artifacts populated yet.

## Authority

Serialization is AUTHORITATIVE for the target format: chunks, palettes/states, items, entities, players, level metadata, dimensions and auxiliary data. Architecture/rendering are REFERENCE ONLY. Gameplay and worldgen are DO NOT PORT.

A newer version never overrides Beta behavior merely because it is newer.

## Provenance

Keep separate client/server originals, mappings, untouched decompiled output, optional resources, separate patches and authored notes. Record actual build labels, hashes, tools/runtime and commands in the top-level manifest. These trees are for study and are excluded from the Origins build.
