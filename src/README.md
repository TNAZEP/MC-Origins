# Working game sources

Develop Origins here. `reference/` holds immutable evidence; files in this directory are independent, editable copies.

| Directory | Purpose and current state |
|---|---|
| `main/java/` | Future shared implementation; empty until classes and their dependencies are reconciled |
| `main/resources/` | Future shared non-client resources; currently empty |
| `client/java/` | 678 verbatim Beta client Java files, including local singleplayer |
| `client/resources/` | 84 resources copied from the original Beta client jar |
| `server/java/` | 444 verbatim Beta dedicated-server Java files |
| `server/resources/` | 5 resources copied from the original Beta server jar |
| `test/java/` | Future deterministic tests |
| `integrationTest/java/` | Future runtime/scenario tests |

Start exploring at [client Minecraft](client/java/net/minecraft/client/Minecraft.java), [client World](client/java/net/minecraft/src/World.java), [dedicated MinecraftServer](server/java/net/minecraft/server/MinecraftServer.java), and [server World](server/java/net/minecraft/src/World.java).

## Temporary a1 import boundary

Client and server are separate compilation roots. Do not recursively compile all of `src/` or mark it as a single IDE Java source root: 402 class paths overlap. This initial layout preserves the supplied sides until the baseline and merge review are ready. Their Java bytes have not been changed, packages renamed, or shared implementations selected.

The final build will compile `main/java/` once and include those shared classes in both artifacts. During a1, review each class and its dependencies against both Beta sides, record the correspondence decision, then move the approved implementation into `main/java/` and remove the replaced side copies together. Byte-identical files alone do not establish shared dependency or behavior equivalence. Retain genuine side differences in host/policy boundaries. This temporary import is not completed source unification, and modernization must not proceed independently on duplicate simulation implementations.

No Gradle wrapper, dependency configuration or launch command is implemented yet. This is an editable source import, not a verified runnable build. The next build step must declare source sets explicitly and use working files only; reference trees must never become build inputs. External audio assets, dependencies and native libraries are not included here.

## Provenance and initial verification

[IMPORT.json](IMPORT.json) records each working destination, input path or jar entry, and initial SHA-256. Java came from the recorded Beta `decompiled/src` snapshots. Resources came from the hash-verified original jars. Original manifests/signatures (`META-INF/`) and compiled `.class` entries were deliberately excluded from resources; other entries, including the file named `null`, were preserved verbatim.

Run from the repository root:

```sh
python3 tools/import_beta_sources.py --verify
```

This checks the initial working snapshot using only the import manifest and working files. After deliberate development edits, differences are expected; it is not a permanent gameplay test or a command to reset your work. Preserve IMPORT.json as provenance. The import command without `--verify` refuses an existing `src/` and must not be used to overwrite development changes.

Baseline runtime/gameplay checks and a1 acceptance remain pending. See [current status](../.plan/status/CURRENT.md).
