# Source symbol crosswalk

Classify equivalent logic, intentional side behavior, lifecycle-only code and decompiler artifacts. Do not erase meaningful SP/MP differences.

| Subsystem | Beta client symbol | Beta server symbol | Shared candidate symbol | Difference classification | Evidence |
|---|---|---|---|---|---|

## 2026-09-08 initial discovery (no source merge)

Source base: `source/beta-1.7.3/{client,server}/decompiled/src/net/minecraft/src/`. Exact file hashes and all 720 same-path candidate rows are in [inventory](../../.plan/status/evidence/2026-09-08-a1/inventory.json). Same filenames are candidate correspondence only; renamed classes are not matched. 106 byte-identical, 296 different/unreviewed, 276 client-only, 42 server-only. Byte equality does not prove equivalence of dependencies or runtime behavior.

| Subsystem | Beta client symbol | Beta server symbol | Shared candidate symbol | Difference classification | Evidence |
|---|---|---|---|---|---|
| Keepalive | Packet0KeepAlive: readPacketData, writePacketData, processPacket, getPacketSize | Same | Packet0KeepAlive | Byte-identical source: empty read/write/dispatch, size 0; candidate only | Both files read; hashes in inventory |
| Login | Packet1Login(String,int) | Packet1Login(String,int,long,byte) | Packet1Login with both construction paths | Intentional side construction: client request vs server response; retain seed/dimension initialization | Both files read; NetClientHandler sends protocol 14, NetLoginHandler checks 14 and constructs response |
| Login codec | Packet1Login: readPacketData, writePacketData, processPacket, getPacketSize | Same | Shared codec candidate with side-specific NetHandler | Method bodies textually identical; dependent Packet string codec/handlers not yet reviewed | Both Packet1Login.java files read; no executable codec parity test |

Original obfuscated symbols, mappings and decompiler identity remain unknown. Protocol 14 and the server source's Beta 1.7.3 banner support the supplied label but do not authenticate an original binary pair. Do not import shared code until baseline evidence is available.
