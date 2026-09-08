# a1 reference acquisition — 2026-09-08

Revision: `83155adbcddba2f77f7269f68ee949502e054efe`, branch `main`, plus prior uncommitted inventory work retained. Owner supplied original Beta jars and reported the latest RetroMCP release with no changes; asked Codex to procure missing 1.3.2 sources. Scope: reference acquisition and verification only. No working game source changes or Origins build introduced.

Environment: Linux x86_64 / kernel `7.2.0-1-cachyos`, Python 3.14.7, OpenJDK 21.0.12+8. Exact Java output is in [java-version.txt](java-version.txt). The Java version here qualifies source generation only, not Beta gameplay or the final Java 25 target.

## Outcome

- Beta client `original/minecraft.jar`: SHA-256 `af1fa04b8006d3ef78c7e24f8de4aa56f439a74d7f314827529062d5bab6db4c`; published SHA-1 `43db9b498cb67058d2e12d394e6507722e71bb45` matches Mojang metadata.
- Beta server `original/minecraft_server.jar`: SHA-256 `033a127e4a25a60b038f15369c89305a3d53752242a1cff11ae964954e79ba4d`; published SHA-1 `2f90dc1cb5ca7e9d71786801b307390a67fcf954` matches MCPHackers BetterJSONs. This is a tool-maintainer metadata check, not a Mojang-published Beta server hash. ZIP CRC checks pass for both. Runtime handshake remains NOT RUN.
- GitHub latest RetroMCP release observed: v1.2, published 2026-01-20. Owner's statement supports an inference that v1.2 was used, but the original tool binary/runtime/mapping snapshot was not supplied. Manifest keeps these distinctions explicit. Current upstream b1.7 resource archive was fetched and pinned as a candidate only; existing Beta sources were not regenerated.
- Live RetroMCP v1.2 catalog omits 1.3.2. Procured Mojang client/server 1.3.2 jars, matched published SHA-1 and sizes, remapped separately with Legacy Fabric Yarn 1.3.2+build.604 / Tiny Remapper 0.14.0, decompiled with CFR 0.152. Both pristine jars, side-specific mappings and named outputs are installed locally.
- Client: 1,335 Java files (1,257 net/minecraft classes plus 78 bundled library classes). Server: 902 Java files/classes. Every remapped class has a corresponding Java path. Two independent runs produced identical Java bytes. This does not prove semantic decompilation fidelity.

## Commands and verification

All repository commands run from the root unless stated. Download URLs and observed input SHA-256 are in [input lock](../../../../reference/tools/1.3.2-inputs.json). Metadata JSON snapshots accompany this report.

| Command/check | Result | Evidence |
|---|---|---|
| `git status --short`; `git rev-parse HEAD` | PASS | Revision above; existing uncommitted work preserved |
| `curl -fL --max-time 30 https://api.github.com/repos/MCPHackers/RetroMCP-Java/releases/latest -o /tmp/origins-retromcp-release.json` | Initial FAIL, then PASS with network escalation | Initial sandbox DNS resolution failure; approved network retry downloaded release metadata |
| `java -jar /tmp/origins-retromcp-cli.jar setup 1.3.2` in scratch | FAIL | Sandbox run lacked catalog and input; tool printed NoSuchElementException but returned 0; not accepted as success |
| `java -jar /tmp/origins-retromcp-cli.jar setup` in `/tmp/origins-rmcp-132`, network enabled, then enter `1.3.2` | PASS (catalog discovery) | Live catalog contains no 1.3.2; prompt repeats; stopped with Ctrl-C, exit 130. [Catalog](retromcp-versions.json) retained |
| `curl -fL --max-time 30 URL -o PATH` for locked inputs | PASS | Input lock stores exact URLs/paths; temporary names documented below |
| Python hashlib SHA-1/size comparison to downloaded Mojang/BetterJSONs metadata; zipfile.testzip on Beta originals | PASS | [verification.json](verification.json) |
| Initial per-side remap/decompile commands below | PASS with client decompiler caveats | Both exit 0; summaries retained |
| `python3 tools/prepare_132_references.py --output /tmp/origins-132-repro` | PASS with client decompiler caveats | Offline SHA-256 checks, command arrays in [commands.json](commands.json), remap/decompile logs per side |
| Compare initial/reproduced Java path-to-SHA-256 maps using inventory tool `tree(..., '*.java')`; compare remapped .class paths with .java paths | PASS | [verification.json](verification.json): identical Java bytes and complete path coverage |
| Compare four existing Beta source trees to prior inventory's files map | PASS | All existing Beta source bytes unchanged |
| Recipe attempts with existing output and reference output | PASS (expected refusals) | [guard-checks.json](guard-checks.json); no output overwritten |
| `python3 -m unittest discover -s tools -p 'test_*.py' -v` | PASS | [tests.txt](tests.txt), 3 inventory tests |
| `python3 tools/inventory_references.py > .plan/status/evidence/2026-09-08-a1-references/inventory.json` | PASS | Updated local inventory, prior evidence retained |
| Beta/1.3.2 game launches, Origins build, SP/MP/two-player, worldgen and visuals | NOT RUN | Acquisition session; original Beta inputs now available for launch qualification |
| a1 acceptance | NOT RUN | No milestone promotion |

Initial scratch commands, each run separately for client and server (SIDE denotes the substituted side):

```sh
java -jar /tmp/origins-tiny-remapper.jar /tmp/origins-132-SIDE.jar /tmp/origins-132-SIDE-named.jar /tmp/origins-132.tiny official named
java -Xmx2G -jar /tmp/origins-cfr.jar /tmp/origins-132-SIDE-named.jar --outputdir /tmp/origins-132-SIDE-source --silent true
```

The Tiny mapping was extracted from the downloaded Yarn jar entry `mappings/mappings.tiny` using Python zipfile. Initial download filenames: `/tmp/origins-132-{client,server}.jar`, `/tmp/origins-yarn132.jar`, `/tmp/origins-tiny-remapper.jar`, `/tmp/origins-cfr.jar`, `/tmp/origins-retromcp-cli.jar`, `/tmp/origins-rmcp-beta-resources.zip`. These were copied without overwrite into locked paths, then verified by the offline recipe. Installed decompiled files came from the recipe run after Java-byte comparison with the initial run. Original 1.3.2 metadata is also stored in each side's `original/version.json`.

Exploratory URL probes returned HTTP 404 for Forge's guessed `mcp-1.3.2-srg.zip`, Tiny Remapper's guessed `0.14.0/README.md` GitHub ref and `versionsV2/b1.7.3.zip`. None were used as inputs. The actual RetroMCP catalog points to `versionsV2/b1.7.zip`, which downloaded successfully. Concurrent initial Java processes reported a harmless hsperfdata file-lock warning; the sequential reproduction completed successfully.

## Limitations and handoff

Client CFR summary reports unstructured control flow in bundled `com.jcraft.jorbis.Drft.dradfg` and `dradbg`. Server summary reports no problems. No manual source fixes applied. Names can remain intermediary/obfuscated; no source compilation, game launch or bytecode equivalence is claimed. Output reproducibility covers Java source bytes; CFR summaries contain scratch paths, and jar ZIP metadata is not included in that claim. The inventory records the actual installed bytes separately.

1.3.2 integrated-server and world-height code is study evidence only. It cannot override Beta singleplayer, gameplay or worldgen. References remain excluded from the future Origins build. Original/mapped jars, mappings and source output are ignored locally; only authored tooling/notes/metadata/evidence are trackable.

Next: qualify the original Beta client and server on a suitable runtime in isolated run directories, resolve assets/natives as needed, and capture baseline evidence. Pinned Origins build scaffolding can proceed independently. Historical Beta mapping/tool identity remains a provenance gap rather than a reason to postpone all original-jar launch work. Recovery: no old sources or saves changed; scratch runs are disposable, installed references should not be overwritten. Reproduce into a fresh scratch directory using the documented recipe.

Final review PASS: `git diff --check`; final inventory rerun/cmp matched saved JSON; all previously inventoried jar SHA-256 values unchanged; documented 1.3.2 entry-point files and manifest original paths exist. `START-HERE.md` appeared modified during the session; Codex did not edit it and preserved that concurrent user change.
