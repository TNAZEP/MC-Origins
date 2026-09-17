# Local development login — 2026-09-09

Base revision `e1eaaac7d06a41452762eb92fe4bf3fec5670968` / main plus existing uncommitted build work, preserved. Owner reports that both client and server launch successfully, but connection fails. User launch results are reported evidence, not independently repeated graphical tests.

## Diagnosis and adjustment

Observed run/server/server.properties had online-mode=true and an empty server-ip (wildcard bind). build.gradle launches the client with username Player and session `-`. Server log showed three 127.0.0.1 connections lost before login at 11:18:58, 11:19:03 and 11:19:12, then clean shutdown at 11:19:15. The exact client-side error was not captured, so authentication mismatch is a supported diagnosis rather than a reproduced client exception.

Source inspection: server NetLoginHandler.handleHandshake sends a random challenge in online mode, `-` otherwise. Client NetClientHandler.handleHandshake contacts the legacy joinserver.jsp service for a random challenge, but immediately sends protocol-14 login for `-`. Server handleLogin invokes ThreadLoginVerifier only in online mode. The development client has no authenticated session. No live authentication endpoint availability claim or service probe was made.

Changed ONLY the existing local run/server/server.properties keys to online-mode=false and server-ip=127.0.0.1. Port 25565, world and all other settings preserved. Backup: run/server/server.properties.before-local-offline. Loopback scopes unauthenticated development login to this computer. The original game authentication code/defaults and build launch arguments were not changed. Server restart is required to load the file; graphical-client retest pending.

## Verification

Command:

```sh
python3 tools/smoke_server.py --java /usr/lib/jvm/java-8-temurin/bin/java --jar build/libs/Minecraft-server.jar --output run/smoke/offline-login --probe-login
```

PASS on Temurin 1.8.0_492-b09/Linux x86_64: isolated loopback server at transient port with seed 8675309; synthetic username OriginsProbe receives offline `-` handshake, sends protocol 14, receives Packet1Login with entity id, world seed and dimension. Server then stops, exits 0, and save exists. [result.json](result.json) records exact arguments, jar/log hashes, protocol and limits; process log retained. This tests the same offline settings in a disposable world, not the user's world or graphical client.

PASS: `python3 tools/import_beta_sources.py --verify` (all 1,211 working inputs unchanged), `git diff --check`. Build NOT RUN again: no game or build logic changes. Client/server launch PASS (owner reported); graphical reconnect after config change NOT RUN. Two-player/gameplay/worldgen parity NOT RUN. Milestone remains incomplete.

Harness change: optional --probe-login exercises Beta's existing handshake/login wire format using Python stdlib sockets/struct. Default startup-only mode remains available; a protocol/connection failure records FAIL and cleans up the child process. No authentication service contacted.

Next: restart ./gradlew runServer and connect from ./gradlew runClient to 127.0.0.1:25565. Confirm world entry and reconnect, then proceed to planned multiplayer fixtures. To restore previous local authentication/bind settings, stop the server and restore the saved properties backup after inspecting subsequent changes. No saves or prior edits overwritten.
