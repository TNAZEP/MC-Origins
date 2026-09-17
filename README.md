# Minecraft: Origins

Modernize Minecraft Beta 1.7.3's engine while preserving its gameplay and appearance.

The editable game code is under [src/](src/README.md). The pinned build compiles both Beta sides and produces client/server jars. Headless dedicated-server startup/shutdown is verified; shared-source reconciliation, client play and multiplayer parity remain in progress.

- [Working source layout and provenance](src/README.md)
- [Build and launch instructions](BUILDING.md)
- [Current implementation status](.plan/status/CURRENT.md)
- [Next tasks](.plan/status/NEXT.md)
- [Architecture and development plan](.plan/README.md)
- [Immutable reference material](reference/README.md)

Edit working files in `src/`; keep `reference/` unchanged. Start with `./gradlew clean build` using a Gradle-compatible JDK plus a Java 8 toolchain; see BUILDING.md for requirements and verification limits.
