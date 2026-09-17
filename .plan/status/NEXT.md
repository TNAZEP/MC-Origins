# Next tasks

1. Begin 1.0a2 platform modernization when continuing development: read its plan and platform ADRs, inspect the existing build, and update OriginsVersion to `Minecraft: Origins 1.0a2` at the start of implementation.
2. Replace the temporary Java 8/LWJGL 2 bootstrap in coherent, buildable steps while preserving separate SP/MP hosting. Carry forward explicit local asset setup and address obsolete service dependencies where relevant.
3. Use build and affected functional checks. Owner playability confirmation is sufficient for a1; do not make automated graphical testing a recurring prerequisite. Select graphical checks when the later platform/rendering changes warrant them.

1.0a1 full merge and bounded practical acceptance are complete. [Final evidence](evidence/2026-09-17-a1-unified/README.md). a2 has not started; in-game version still 1.0a1. No release or commit has been made.
