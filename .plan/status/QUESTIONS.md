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
