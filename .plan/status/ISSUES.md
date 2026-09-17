# Implementation issue register

Local development configuration findings are tracked below; game behavior remains only partially qualified.

Severity: Blocker = required launch/build/multiplayer failure or data loss/corruption; Major = materially wrong behavior/visuals/compatibility; Minor = bounded low-impact defect; Observation = needs reproduction/classification.

| ID | Severity | Reproducer / expected / observed | Affected revision | Status / owner | Milestone / evidence |
|---|---|---|---|---|---|

For each issue add environment, pristine Beta or vanilla-control comparison, affected saves/platforms, fix revision and regression test. Close only with verified evidence. Keep research risks in RISKS and planned product exceptions in DEVIATIONS.

## I-01 — Development session rejected by online-mode server

Observation, a1. Base e1eaaac + uncommitted build: owner launches runClient/runServer, client cannot connect. Local properties require online authentication while runClient has placeholder session `-`; server logs lost connections before login. Adjusted only local config to offline mode with loopback binding, backup retained. Closed 2026-09-09: synthetic login PASS, owner confirms successful graphical retest, and server log records Player login at 11:22:18 followed by normal quitting and server save/shutdown. [Confirmation](evidence/2026-09-09-a1-confirmation/README.md). [Evidence](evidence/2026-09-09-a1-login/README.md).

## I-02 — Startup worlds are not repeatable terrain oracles

Observation; closed 2026-09-09 for the bounded a1 test harness. On e1eaaac + uncommitted tooling, same seed alone yields different spawn/coverage. Supplying identical original-derived level.dat aligns 1,248 chunks but original/built and original/original repeats both differ in 32 Overworld chunks. The unchanged original repeats establish insufficient fixture control; no Origins regression proven. Source points to unseeded World.rand during spawn/flow and wall-clock ticks; exact trace remains to be captured. Fix the test harness with explicit RNG/tick/request/population controls, preserving game behavior. Close after reference repeats and appropriate candidate comparison pass. [Evidence](evidence/2026-09-09-a1-terrain/README.md).

I-02 resolution: a separate reflection harness controls World.rand, explicit requests/lighting and zero ticks without changing game sources. Original repeat and original/built each PASS with zero differences across 1,248 saved chunks; RNG/request traces match. Previous startup worlds remain non-golden. Wider seed/order and gameplay acceptance remain open. [Resolution evidence](evidence/2026-09-09-a1-terrain-control/README.md).

## I-03 — Obsolete Beta asset-listing service

Observation, a1 Linux bootstrap: the original HTTP resource-listing URL returns FileNotFoundException. Existing local-resource fallback still works. Mitigation implemented 2026-09-17: explicit `tools/prepare_beta_resources.py` validates all 220 supplied asset objects and copies them to the development resource directory without modifying references or introducing reference build dependencies. Graphical run with copied assets initializes/shuts down OpenAL; audible fidelity NOT RUN. Legacy downloader still logs the obsolete-service error. Track service replacement with a2 platform/asset work; no gameplay/source-merge regression claimed. [Evidence](evidence/2026-09-17-a1-unified/README.md).
