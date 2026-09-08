# a1 input baseline — 2026-09-08

Revision: `83155adbcddba2f77f7269f68ee949502e054efe`, branch `main`, initially clean tracked working tree. This session adds inventory tooling and evidence; no working game implementation was present outside references. Ignored local Beta material was included in the read-only scan. No pristine source, binary or save was modified.

Environment: Linux x86_64, kernel `7.2.0-1-cachyos`; default OpenJDK/javac `21.0.12`; Python `3.14.7`. `/usr/lib/jvm` also contains Java 8 (OpenJDK and Temurin), 17 and 26 directories; those runtimes were not executed or qualified. Java 25 was not found there. `gradle --version` failed with command not found. No wrapper or CLI game build exists. No bootstrap game runtime selected yet.

## Observations

| Supplied label | Client Java files | Server Java files | Original files | State |
|---|---:|---:|---:|---|
| b1.7.3 | 1356 (678 src + 678 src_original) | 888 (444 + 444) | 0 | Decompiled sources/classes and two remapped/source jars per side |
| 1.3.2 | 0 | 0 | 0 | Both directories empty |
| 1.13.2 | 2714 | 2014 | 0 | src and mapped.jar per side |
| 1.17.1 | 4142 | 3100 | 0 | src and deobfuscated jar per side |

All eight manifest original paths are empty. Acquisition sources/dates, original binary hashes, mapping sets, decompiler versions/invocations and prior fix provenance remain unknown. Existing `md5/original.md5` files do not supply the missing SHA-256 binary/tool provenance. Local hashes identify observed bytes only. Do not substitute remapped jars for pristine artifacts.

Beta IDE modules declare JInput 2.0.5, JUtils 1.0.0, LWJGL/LWJGL-util/platform 2.9.4-nightly-20150209; Paulscode codecjorbis 20230120, codecwav 20101023, libraryjavasound 20101123, librarylwjglopenal 20100824, soundsystem 20120107; launchwrapper 1.3.0, JSON 20230311 and ASM/core/tree/commons 9.10.1, plus deobfuscated library. These are observed declarations, not resolved/validated dependencies. Their external binaries/natives were not supplied in the scanned reference trees. Client assets are present under decompiled/game; resource completeness is unverified. Server IDE launch names `net.minecraft.server.MinecraftServer` and a relative game directory. Do not run inside reference directories.

Beta `src` and `src_original` match on each side. The candidate ledger has 106 identical, 296 different/unreviewed, 276 client-only and 42 server-only paths. Initial manual packet observations are in [symbol crosswalk](../../../../reference/notes/symbol-crosswalk.md); no classes or methods have been merged.

## Commands and results

Run from repository root:

| Command/check | Result | Evidence |
|---|---|---|
| `git status --short`, `git rev-parse HEAD`, `git branch --show-current` | PASS | Clean start, revision/branch above |
| `java -version`, `javac -version`, `python3 --version`, `uname -a` | PASS | Environment above; availability only |
| `gradle --version` | FAIL | Command not found; no installed CLI Gradle |
| `python3 tools/inventory_references.py > .plan/status/evidence/2026-09-08-a1/inventory.json` | PASS | Deterministic file/tree hashes; includes ignored local files |
| `python3 tools/inventory_references.py > /tmp/origins-inventory-repeat.json` then `cmp .plan/status/evidence/2026-09-08-a1/inventory.json /tmp/origins-inventory-repeat.json` | PASS | Identical output, exit 0 |
| `python3 -m unittest discover -s tools -p 'test_*.py' -v` | PASS | 3 tests; [tests.txt](tests.txt), mutation/rename detection, missing tree, conservative classification |
| Pristine client/server baseline | BLOCKED | Untouched original jars and provenance missing |
| Origins build, launches, two-player smoke, worldgen/visual fixtures, headless class-loading, patch mapping | NOT RUN | No working import/build/artifacts; pristine baseline prerequisite absent |

Inventory SHA-256: `f68b4b436652b4869cbc20706826b8eaf64ea776dcdca4cc95b6dc3e89baa25d`. Tree hashing uses sorted relative POSIX paths, NUL, each file's SHA-256 hex digest, LF, encoded UTF-8. Empty trees have the empty-input hash; file counts/directory-existence fields distinguish missing/empty input from usable source. Manifest observation fields do not promote provenance fields to verified.

## Next action and recovery

Resolve Q-01/Q-02: acquire untouched Beta client and matching server jars into each side's `original/`, retaining source URL/date, published hash and independently computed SHA-256; supply RetroMCP version, mappings, tool hash, invocation/runtime and fix history. Validate version/protocol correspondence, then capture pristine launches in separate disposable run directories. Obtain 1.3.2 source-organization references. Independent next step: choose/pin a Gradle wrapper and explicit toolchain, create main/client/server boundaries without compiling reference trees, and report empty scaffolding honestly. No game restore needed; this session changed only authored tooling, notes, manifest and evidence.

Final review: initial `git diff --check` reported Markdown trailing spaces in updated status/milestone lines; removed them and reran successfully. A post-edit inventory rerun still matched the saved evidence, confirming scanned reference bytes remained unchanged.
