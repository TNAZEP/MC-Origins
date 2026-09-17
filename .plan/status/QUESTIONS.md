# Open implementation questions

These are discovery tasks, not reasons to re-ask settled product decisions. Resolve ordinary details from the source and evidence; involve the owner only if a locked requirement must change or a material preference cannot be inferred.

| ID | Question | Default / investigation | Due |
|---|---|---|---|
| Q-01 | What working source/mapping/tool versions are actually supplied? | Inventory before edits; preserve exact provenance | a1 entry |
| Q-02 | Which Beta dedicated artifact matches the client baseline? | Validate original metadata and protocol behavior, record hash | a1 |
| Q-03 | Which Gradle/plugins/bootstrap runtime work with the source? | Java 25 final; Gradle 9.1+ compatible baseline, pin after validation | a1/a2 |
| Q-04 | Which core GL context/native options work on intended platforms? | Proposed 3.3 feature level; measured platform spike | a2 |
| Q-05 | How will working names map into the patch output? | Inspect reobfuscation/correspondence; prove reconstruction | a1 discovery / a8 gate |
| Q-06 | Which Beta states need preservation beyond vanilla fields? | Enumerate many-to-one mappings; sidecar only if necessary | a4/a6 |
| Q-07 | What are exact 1.17.1 schema fields/DataVersion/packing rules? | Read exact source plus SP/server generated fixtures | before a6 writer |
| Q-08 | How will Beta usernames/player files map to vanilla UUID data? | Explicit offline/online identity policy; no silent reassignment | a6/a7 |
| Q-09 | Which vanilla control hops and full-world conversion steps work? | Qualify candidate chain and inventory each hop | a7 |
| Q-10 | What are measured visual/performance tolerances and release platform list? | Establish before optimization; no invented pass budgets | a2 baseline / b2 gate |

Resolved answers should link an ADR or evidence record and remain in history. None of these questions reopens the no-worldgen-change, native 1.17.1, Java 25, or integrated-server deferral decisions.

## 2026-09-08 discovery update

- Q-01 remains open for provenance: local Beta src/src_original (678 client, 444 server, identical per side), remapped jars/classes and client assets exist. No original jars, mapping files or tool manifests in expected locations. All 1.3.2 material missing; modern sources exist outside scaffold decompiled directories. [Evidence](evidence/2026-09-08-a1/README.md).
- Q-02 remains open: source banner says Beta 1.7.3 and both sides use protocol 14; this does not authenticate original artifacts. Obtain originals with acquisition metadata/hashes and runtime handshake evidence.
- Q-03 remains open: default Java 21.0.12, no installed Gradle/wrapper; JDK 8/17/26 directories also exist but are unqualified. No bootstrap selection made.
- Q-05 remains open: no mapping/reobfuscation provenance; a same-path candidate ledger cannot recover original obfuscated symbols.

## 2026-09-08 acquisition update (supersedes missing-original/1.3.2 observations above)

- Q-01 partly resolved: owner supplied Beta original jars and reports latest RetroMCP release/no manual changes. GitHub latest v1.2 checked; exact originally used binary, mappings, runtime and automatic patches remain unverified. Current upstream b1.7 resource archive is pinned as a candidate only. Existing source bytes preserved.
- Q-02 artifact hash correspondence established: client matches Mojang, server matches MCPHackers BetterJSONs b1.7.3 metadata. Original runtime handshake/behavior still NOT RUN. User's historical download date/source is unknown; hash verification does not invent it.
- 1.3.2 missing-input gap resolved for source study: Mojang originals and independently generated named client/server sources installed. Two client JOrbis methods have CFR warnings; no recompilation or behavior certification.
- Q-03/Q-05 remain open for Origins bootstrap build and Beta original-name mapping. Source-generation Java 21 is not a game runtime selection. See [acquisition evidence](evidence/2026-09-08-a1-references/README.md).

## 2026-09-09 build checkpoint

- Q-03 a1 bootstrap selected and compiled: Gradle 9.1.0, Java 8 toolchain (Temurin 8u492 selected), declared legacy LWJGL 2/Paulscode dependencies, locks/checksums. Built server smoke passes on Temurin 8u492 and OpenJDK 8u504. Final Java 25 migration remains a2; client runtime qualification pending.
- Q-02 original server startup now verified with recorded original hash and runtime. This is not a client/server handshake or gameplay-parity result.
- Q-01/Q-05 historical Beta source-tool mapping and original-name patch strategy remain open. Builds no longer depend on reference inputs; no automatic compile fixes applied.
- [Evidence](evidence/2026-09-08-a1-build/README.md) records artifacts and limitations.

## 2026-09-17 unified source discovery

- Q-05 a1 investigation resolved: candidate Tiny mappings have different client/server obfuscated names for the same now-shared class. Keep named output and use a complete game implementation replacement with explicit original-entry deletions; see [patch strategy](../architecture/PATCH-STRATEGY.md). Actual patch construction/reconstruction remains the a8 gate.
- Q-01 historical exact decompiler provenance remains unknown; recorded immutable source hashes, supplied original jars and per-file evolution are sufficient to continue development without inventing it.
