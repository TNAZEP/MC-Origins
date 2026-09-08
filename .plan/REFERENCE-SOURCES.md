# Minecraft Java reference-source guide

## Initial reference set — final conversation decision

Prepare **both client and dedicated-server jars for all four versions: b1.7.3, 1.3.2, 1.13.2 and 1.17.1**. Preserve separate side provenance even where shared classes overlap. Keep readable source for study and untouched binaries for applicable runtime tests.

| Version | Client | Dedicated server | Purpose and authority |
|---|---|---|---|
| **Beta 1.7.3** | Initial set | Initial set | Authoritative gameplay, physics, worldgen, rendering appearance and multiplayer behavior. Both sides are needed for a1 reconciliation. Its architecture is the starting point to modernize. |
| **1.3.2** | Initial set | Initial set | Source-organization and responsibility-boundary reference for the unified client/server architecture, useful during a1. Study how formerly separate concepts were reconciled. Do not port its integrated server into 1.0. |
| **1.13.2** | Initial set | Initial set | Transitional technical reference for namespaced identities, registries/states, legacy-data flattening and the LWJGL 2-to-3 transition. Use during a2/a4 and conversion research; never as the gameplay oracle. |
| **1.17.1** | Initial set | Initial set | Authoritative persistence/output target: chunk/NBT, palettes, blocks/items, players/entities, dimensions and auxiliary saves. Rendering architecture may be studied; gameplay must not be ported. |

**Do not initially include 1.2.5.** Add it only if a concrete McRegion-to-Anvil question requires historical investigation. Likewise, do not initially collect 1.8, 1.12.2, 1.14, 1.16 or every intermediate release. A later 1.18.x source reference is optional diagnostic material, not an initial source dependency.

Intermediate vanilla **runtime installations** may still be needed to build the paired upgrade-control fixtures. That does not require decompiling those releases or adding them to the initial source set. The candidate runtime chain in [save testing](testing/PERSISTENCE.md) must be qualified rather than assumed.

## Authority hierarchy

- Behavior, physics, worldgen, appearance and multiplayer: **Beta 1.7.3**.
- Unified source organization and responsibility boundaries: **1.3.2 plus Origins' design**.
- Registry/state modernization, legacy conversion and LWJGL transition: **1.13.2**.
- Exact persistent representation and direct vanilla compatibility: **1.17.1**.

A newer reference never overrides Beta behavior simply because it is newer. Origins uses Beta piston behavior with its modern internal representation. Studying 1.3.2 during a1 does not authorize implementing integrated singleplayer.

## Tooling and provenance

Use the user's RetroMCP-Java workflow for Beta, pinned to an exact tool version and mapping set. [RetroMCP-Java's upstream repository](https://github.com/MCPHackers/RetroMCP-Java) is the tool source; check its supported-version metadata rather than assuming it supports every modern release. For 1.17.1 use a reproducible decompiler/remapping pipeline appropriate to that version, retaining official mapping provenance where available. Record the actual tool and invocation after selecting it; this kit does not prescribe an unverified one-command decompile recipe.

For every side capture exact version ID/build, original filename, acquisition source, download date, binary SHA-256 (and published hash where provided), mapping name/version/hash, decompiler name/version/hash, Java runtime, command/configuration, output tree hash and any compile-fix patches. A decompiler artifact is not evidence of intended behavior; check bytecode or executable behavior when source output is ambiguous.

Beta server naming may differ from the client label in historical tools. Verify the actual matching server/protocol and record its original label; never choose a server solely because its filename resembles b1.7.3. A missing server reference blocks a fully verified a1 merge, though build scaffolding can proceed.

## Folder layout

```text
reference/
  README.md
  manifest.json
  .gitignore
  source/
    beta-1.7.3/
      README.md           purpose and authority
      client/
        original/         untouched jar and version metadata
        mappings/         mapping files and provenance
        decompiled/       untouched decompiler output
        resources/        optional local extracted resources
        patches/          separate compile-fix patches
        notes/            authored source observations
      server/             same six subdirectories
    release-1.3.2/        README.md plus client/server layout
    release-1.13.2/       README.md plus client/server layout
    release-1.17.1/       README.md plus client/server layout
  fixtures/
    beta-1.7.3/
      worlds/
      screenshots/
    migration/
      beta-control-world/
      vanilla-1.17.1-upgraded-control/
  notes/
    symbol-crosswalk.md
    schema-inventory.md
    vanilla-mapping-ledger.md
    upgrade-chain.md
  tools/                  tool/configuration manifests
```

Source trees are study material; fixtures are regression data. The scaffold contains no game artifacts. Keep working sources outside `reference/`, in the actual project source sets. Never edit pristine decompiled output or put reference trees on the build classpath. Use the README in each version directory to retain its purpose and authority.

Original binaries, decompiled material, mappings, extracted resources and generated worlds/captures remain local under the supplied ignore rules. Commit authored notes/manifests selectively. This organization is not redistribution permission and does not make a decompiled-source project a clean-room implementation.

## Readiness checklist

- [ ] Beta 1.7.3 client and matching dedicated-server provenance, hashes and baseline launches recorded.
- [ ] 1.3.2 client/server sources prepared for source-organization and boundary study in a1.
- [ ] 1.13.2 client/server sources prepared for platform/model/conversion study in a2/a4.
- [ ] 1.17.1 client/server sources and generated SP/server saves prepared for exact schema inspection.
- [ ] Each version README states purpose, authority and prohibited behavioral imports.
- [ ] Working Beta source is separate from pristine references.
- [ ] Fixture recipes and tested runtimes entered in the manifest.
- [ ] No unknown hashes or placeholder statuses are described as verified.
