# Build and distribution

Use a pinned Gradle wrapper with dependency locks/checksums and explicit Java toolchains. Gradle 9.1.0 is the minimum documented Java-25-compatible baseline; choose and pin an appropriate version after validating the build plugins. Avoid dynamic versions. Gradle's [compatibility matrix](https://docs.gradle.org/current/userguide/compatibility.html) lists Java 25 support from 9.1.0.

a1 may retain a named legacy game compilation/runtime toolchain while source unification is verified. a2 removes that runtime dependency for Origins. Reference games and decompilers keep their own version-appropriate runtimes; do not force them onto Java 25.

## Target build task contract

These names are planned interfaces, not commands that already exist. Record actual implemented commands in status.

| Proposed task | Outcome |
|---|---|
| `build`, `test` | Compile and fast verification |
| `runClient`, `runServer` | Separate dev launches with isolated run directories |
| `buildMinecraftJar` | Local client `Minecraft.jar`, entry point and dependency/native instructions |
| `buildServerJar` | Headless dedicated-server artifact |
| `buildVanillaPatch` | Changed/added entries plus mapping/deletion/install metadata |
| `verifyVanillaPatch` | Apply patch to exact baseline; compare reconstructed runnable content |
| `buildPrismInstance` | Importable instance ZIP with correct launch metadata |
| `integrationTest` | Scenario/artifact tests with separately supplied local references |

The client artifact may use adjacent dependency libraries/native extraction rather than a fat jar. Document precisely how to run it. The user-facing `Minecraft.jar` target must still be produced. The dedicated artifact must not initialize client native libraries.

## Patch reality

A Java 25/LWJGL 3 port needs more than dropping a few classes into an old launcher. Produce the requested changed-class/resource ZIP, plus exact baseline hash, added/replaced/deleted entry lists, class-name/reobfuscation mapping policy, updated entry point, Java requirement and dependency/native launch configuration. Full-class replacement is acceptable; falsely claiming a tiny overlay is not.

a1 must investigate whether the decompiled/recompiled naming scheme can map unchanged entries back to the original jar. a8 must prove reconstruction: known untouched Beta jar + patch + declared dependency bundle/configuration yields the same effective application content as the direct build. Normalize only documented archive metadata/signatures, not differing class bytes or resources. Include removed entries so old classes do not shadow new ones. If preserving original names is impractical, document the remapped output and complete replacement set; do not compare only filenames.

## Prism and clean installation

Pin the tested Prism release in the evidence record. Validate the actual instance format, component metadata, entry point/classpath, Java 25 selection, natives, OS-specific flags and asset resolution against a freshly imported instance. Successful ZIP creation does not prove launcher compatibility. Test an environment without development caches. Do not embed accounts, tokens, private paths, user saves or reference sources.

Keep local full-game outputs separate from release patch artifacts. Record provenance and dependency notices. The kit grants no right to redistribute upstream game code/assets; publication packaging must explicitly account for what it contains rather than accidentally bundling the reference tree.

## Reproducibility

Record compiler/Gradle/plugin/dependency/native versions, source revision and source baseline hashes. Compare two clean builds after stable timestamps/order are configured. Report reproducibility at the content level if platform signatures prevent byte-identical archives. Build from declared inputs; no IDE-only steps or hidden reference classpaths.
