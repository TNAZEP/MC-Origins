# Reference tooling manifests

Record the decompiler/remapper, mappings, runtime and exact configuration per artifact in the top-level manifest. Keep local binaries under bin if needed. Do not make reference tooling an undeclared dependency of the Origins release build.

RetroMCP-Java is the expected Beta workflow. Choose a separately verified pipeline for 1.17.1. Decompilation tool Java requirements and reference-game Java requirements need not match Origins' Java 25 target.
