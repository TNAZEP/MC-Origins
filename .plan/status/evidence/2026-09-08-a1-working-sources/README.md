# a1 editable working-source import — 2026-09-08

Revision: `83155adbcddba2f77f7269f68ee949502e054efe` / main, plus previous uncommitted acquisition/inventory work. User explicitly requested an editable Beta source directory. Existing START-HERE.md user changes preserved. Environment inherited and checked in prior session: Linux x86_64, Python 3.14.7; no Java compiler/runtime invoked for this import.

## Scope and result

PASS: imported 678 client and 444 server Java files as independent regular files under `src/{client,server}/java/`. Input Java bytes were verified against the saved reference inventory before copying. Copied 84 client and 5 server resources from the hash-verified original jars into the corresponding `resources/` roots. Jar class files and META-INF manifest/signature entries excluded; all other resources retained byte-for-byte, including the entry named `null`.

Created empty `src/main/{java,resources}/`, `src/test/java/` and `src/integrationTest/java/` roots. `src/IMPORT.json` records every input path/archive entry and initial content hash. `src/README.md` explains editing, side separation, future shared-code movement and current build limitations. Root README now points to the working tree. Import tooling refuses an existing src/ and checks all inputs before starting. No reference tree is an implicit build dependency.

This is a byte-preserving import before baseline launches, explicitly authorized by the source-setup request. It is not a source merge. The 402 overlapping class paths remain in separate roots; even 106 byte-identical candidates are not shared until dependencies and side behavior are reviewed. No locked decision changed. The completed a1 target still requires one compiled shared implementation, separate local SP semantics and runtime parity evidence. No modernization may proceed on duplicated simulation trees independently.

## Commands and evidence

| Command/check | Result | Evidence |
|---|---|---|
| `git status --short`; `git rev-parse HEAD` | PASS | Prior uncommitted work retained; base revision above |
| `python3 tools/import_beta_sources.py > .plan/status/evidence/2026-09-08-a1-working-sources/import.txt` | PASS | 1,211 imported files, [import.txt](import.txt) |
| `python3 tools/import_beta_sources.py --verify` | PASS | All working source/resource bytes match initial provenance; verifier reads working files and IMPORT.json only |
| Repeat `python3 tools/import_beta_sources.py` | PASS (expected refusal, exit 1) | Existing src/ protected, stderr retained in [verification.json](verification.json) |
| Python comparison of `inventory(Path('reference'))` to prior acquisition inventory | PASS | Entire reference-side inventory unchanged; recorded in verification.json |
| Python count/path/symlink and source-tree hash checks | PASS | 678/444 Java, 84/5 resources, actual entry points exist, independent regular files; verification.json |
| `git diff --check` | PASS | Authored tracked-file changes have no whitespace errors; imported untracked source formatting was preserved verbatim |
| Game compilation, launches, source-unification parity, SP/MP/worldgen/visuals | NOT RUN | No wrapper/dependency/launch configuration exists; byte-copy verification is not runtime acceptance |
| a1 milestone exit gates | NOT RUN | Milestone remains In progress |

The import verifier compares original content hashes; after intentional development edits, mismatches are expected and do not mean the game has regressed. It must not become an immutable-source requirement on editable files. Keep IMPORT.json as historical provenance and use appropriate tests for later changes.

## Next and recovery

Set up a pinned Gradle build with separate client/server compilation roots, dependencies and isolated run directories; qualify pristine Beta launches and capture fixtures before behavior-sensitive merge edits. Then reconcile bounded classes into main/java with ledger and parity evidence. No game build or launch command is advertised yet.

No old files or reference inputs overwritten, no package/logic edits, no world/save operation. Import is intentionally one-shot: never delete working development changes to rerun it. Recover individual files from version control or their recorded reference input only after inspecting edits. Existing user and prior-session changes remain uncommitted.
