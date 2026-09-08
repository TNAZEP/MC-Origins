# Minecraft: Origins — drop-in planning kit

Preserve the origins on the outside. Modernize everything underneath.

This kit is a development plan, not an implemented game. All implementation milestones start as not started. It includes no Minecraft binaries, assets, mappings, decompiled sources, or fabricated test results.

## Install

1. Extract this archive and copy `.plan/` into your project root beside your working Beta 1.7.3 sources. Enable hidden files if `.plan` is not visible.
2. Copy the supplied `AGENTS.md` into the root. If one already exists, merge this guidance into it rather than replacing project instructions.
3. Merge the supplied `reference/` scaffold with your references. Follow [.plan/REFERENCE-SOURCES.md](.plan/REFERENCE-SOURCES.md) to populate it. Keep pristine sources separate from your working code.
4. Open the project in Codex and use the starter prompt below. Codex should first inventory the actual repository and references, then start `1.0a1`.

## Starter prompt

> Read AGENTS.md, .plan/README.md, .plan/status/CURRENT.md, and .plan/versions/1.0a1.md. Inspect the repository and reference manifest. Begin Minecraft: Origins 1.0a1 with the smallest verifiable baseline/build/source-unification task. Preserve Beta 1.7.3 behavior. Record missing inputs honestly, continue independent work where possible, and update the persistent status documents before ending the session. Do not claim a milestone complete without its acceptance evidence.

## Reference preparation at a glance

- **Initial set: b1.7.3, 1.13.2 and 1.17.1 — both client and dedicated server for all four.**
- Store them under `reference/source/beta-1.7.3/`, `release-1.13.2/` and `release-1.17.1/`. Each directory includes a purpose/authority README.
- **Do not initially include 1.2.5** or other intermediate sources. Add one only for a concrete investigation. integrated server implementation remains post-1.0.
- Intermediate vanilla runtime installations used to build upgrade-control fixtures are separate from the minimum decompilation set.

The full navigation index is [.plan/README.md](.plan/README.md). The roadmap is [.plan/ROADMAP.md](.plan/ROADMAP.md). The proposed sequence contains nine alphas and three betas; add or split milestones when evidence warrants it without expanding 1.0 scope.
