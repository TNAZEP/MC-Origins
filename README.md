# Minecraft: Origins

Modernize Minecraft Beta 1.7.3's engine while preserving its gameplay and appearance.

The editable game code is under [src/](src/README.md): client and dedicated-server sources/resources are imported, with a shared source root reserved for the a1 reconciliation. The initial import is byte-preserving; source unification, build configuration and runtime verification are still in progress.

- [Working source layout and provenance](src/README.md)
- [Current implementation status](.plan/status/CURRENT.md)
- [Next tasks](.plan/status/NEXT.md)
- [Architecture and development plan](.plan/README.md)
- [Immutable reference material](reference/README.md)

Edit working files in `src/`; keep `reference/` unchanged. No game build or launch command is available yet.
