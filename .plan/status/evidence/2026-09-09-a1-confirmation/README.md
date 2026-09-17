# Owner-confirmed local client/server test — 2026-09-09

Revision: e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus existing uncommitted build/login work. Documentation-only confirmation session; no gameplay/build/configuration edits or fresh runtime launches.

Owner reports both client and server compile and work properly after the local offline configuration fix. PASS: local graphical-client/server connection (owner confirmed), supported by the server log: readiness at 11:21:44, Player login at 11:22:18, disconnect.quitting at 11:22:23, console stop at 11:22:27 and saving chunks through 11:22:28. Exact owner commands, runtime and graphical actions were not supplied; do not infer additional gameplay coverage. Prior automated build/server/protocol evidence remains separate.

Evidence: server-excerpt.log, selected lines copied from run/server/server.log. Source excerpt SHA-256: 47d9eb1a0919f17eb4d3a54b24a386a05fb56b6e5c49d8b1650fe224d4331ae7. Configuration remains loopback/offline for same-computer development. I-01 closed based on successful owner retest plus login log.

Checks performed: git status --short, git rev-parse HEAD, tail -30 run/server/server.log, and git diff --check (PASS). No build/test rerun necessary for documentation-only changes. Two-player interactions, reconnect, SP pause, audio/visual/worldgen/persistence parity and shared-source reconciliation remain NOT RUN/uncompleted. 1.0a1 is not complete.

Next: capture bounded baseline scenarios and expected results before changing shared logic. Preserve the now-working client/server build and original references. No recovery steps needed; all prior uncommitted work preserved.
