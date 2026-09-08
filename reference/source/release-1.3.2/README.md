# 1.3.2 reference pair

## Purpose

Unified source organization. Initial reference set: both client and dedicated server. Both original jars and named study sources prepared on 2026-09-08.

## Authority

Architecture is REFERENCE ONLY: study how shared responsibilities and side boundaries are organized during a1. Gameplay is DO NOT PORT. Integrated server implementation remains deferred beyond 1.0.

A newer version never overrides Beta behavior merely because it is newer.

## Provenance

Keep separate client/server originals, mappings, untouched decompiled output, optional resources, separate patches and authored notes. Record actual build labels, hashes, tools/runtime and commands in the top-level manifest. These trees are for study and are excluded from the Origins build.

## Prepared local references

- Original client/server jars verified against Mojang metadata, in each side's `original/`.
- Separate named outputs: `client/decompiled/src/` (1,335 Java files) and `server/decompiled/src/` (902).
- Mappings: Legacy Fabric Yarn 1.3.2+build.604; remapper: Tiny Remapper 0.14.0; decompiler: CFR 0.152. [Reproduction recipe](../../tools/README.md).
- Client JOrbis Drft has two unstructured methods. Sources are for study; compilation and game launch NOT RUN.

Useful entry points relative to either source root: `net/minecraft/server/MinecraftServer.java`, `net/minecraft/server/world/ServerWorld.java`, `net/minecraft/world/World.java`, and `net/minecraft/server/dedicated/MinecraftDedicatedServer.java`. The client also contains `net/minecraft/server/integrated/IntegratedServer.java`. These illustrate responsibility boundaries only; its integrated server, height and generation behavior must not be imported into Origins 1.0.
