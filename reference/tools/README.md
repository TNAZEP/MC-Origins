# Reference tooling manifests

Record the decompiler/remapper, mappings, runtime and exact configuration per artifact in the top-level manifest. Keep local binaries under bin if needed. Do not make reference tooling an undeclared dependency of the Origins release build.

RetroMCP-Java is the expected Beta workflow. Choose a separately verified pipeline for 1.17.1. Decompilation tool Java requirements and reference-game Java requirements need not match Origins' Java 25 target.

## 1.3.2 preparation, 2026-09-08

Prepared both sides from Mojang original downloads, Legacy Fabric Yarn `1.3.2+build.604` merged Tiny v2 mappings, Fabric Tiny Remapper `0.14.0`, CFR `0.152`, and OpenJDK `21.0.12+8`. No game code patches, jar merging or Origins build dependencies were introduced.

[Input lock](1.3.2-inputs.json) records each download URL and observed SHA-256. The RetroMCP entries are provenance research inputs, not requirements of the 1.3.2 recipe. Local tool binaries are ignored under `bin/`; originals, mappings and source output remain under each version/side directory.

To restore missing inputs, download each required URL from the lock to its recorded repository-relative path using `curl -fL --max-time 60 URL -o PATH`, without overwriting differing existing files. Original 1.3.2 jars also match Mojang's SHA-1 and size metadata saved under each original directory. Tool/mapping SHA-256 values pin this session's observed downloads; they are not claimed as publisher signatures.

Run from the repository root with the recorded Java on PATH:

```sh
python3 tools/prepare_132_references.py --output /tmp/origins-132-new
```

This offline recipe verifies all five required input hashes, extracts mappings, remaps each side separately, and runs CFR into a new scratch directory. It refuses an existing output or any destination inside `reference/`. It records command argument arrays, Java version and logs. Review CFR `src/summary.txt` before installing any output. This is study source generation, not a recompilable game build.

The actual session used `/tmp/origins-132-repro`; after verifying it against the independent initial run, its side `src/` and `named.jar` were copied into the previously empty `reference/source/release-1.3.2/{client,server}/decompiled/`. The extracted Tiny file was copied to each side's `mappings/legacy-yarn-1.3.2-build.604.tiny`. Installed sources are immutable; reproduce into a fresh scratch destination instead of replacing them.

CFR reports two unstructured methods in bundled client JOrbis `Drft` (`dradfg`, `dradbg`). The server summary has no warnings. Some symbol names remain intermediary/obfuscated. Neither absence of warnings nor byte-repeatability proves recompilability or behavior. Source tree hashes cover Java files only: summary/log paths and jar ZIP metadata are not normalized or claimed reproducible.

[Full session evidence](../../.plan/status/evidence/2026-09-08-a1-references/README.md) includes commands, verification, metadata snapshots and limitations.
