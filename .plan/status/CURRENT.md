# Current project status

**Target release:** 1.0
**Current milestone:** 1.0a1 — Complete
**Next milestone:** 1.0a2 — Not started
**Last updated:** 2026-09-17 (Asia/Tokyo)
**Revision / branch:** e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved uncommitted implementation; exact source/artifact identities in evidence

Full source merge implemented: 420 shared / 270 client / 39 server Java files, no duplicate side source paths. Main compiles independently once into both artifacts. Shared simulation, generation, inventory, saves, packets and transport preserve separate Beta singleplayer and dedicated multiplayer hosts. Display remains `Minecraft: Origins 1.0a1`.

Clean client/server build PASS. Local world generation, edits, chest/player inventory and position save/reload PASS. Two protocol players login, move, place/dig, open chest, save and reconnect PASS. Dedicated headless startup/login/stop and dependency isolation PASS. Provenance PASS: 818 files, 1269 evolution entries. Real graphical client local play/save-reload/server join/reconnect also executed successfully; no further graphical test requirement is implied.

Owner clarified that a1 playability confirmation is sufficient and graphical testing matters more in later work. Follow ADR-0012; do not expand automated graphical tests as a gate for routine implementation. The clarification is not itself a fresh playtest report.

[Final evidence and promotion](evidence/2026-09-17-a1-unified/README.md), [milestone](../versions/1.0a1.md), [source guide](../../src/README.md), [patch strategy](../architecture/PATCH-STRATEGY.md). a1 accepted as a bounded Linux Java 8/LWJGL 2 development checkpoint, not a published release. No commit/tag/publication. a2 has not started; update OriginsVersion immediately when it does.

Known limitations: obsolete asset-listing endpoint logs an error; 220 verified sound resources explicitly prepared in run/client/.minecraft/resources enable the existing fallback. Audio listening, other OS/GPU environments and online account/skin/stat services are NOT RUN. Exact historical decompiler build remains unknown. Java 25/LWJGL 3 migration is next; patch reconstruction remains a8. User worlds/references/unrelated changes preserved.
