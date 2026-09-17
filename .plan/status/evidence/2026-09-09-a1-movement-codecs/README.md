# Movement codec checkpoint — 2026-09-09

Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus preserved existing work. OpenJDK Java/javac 8u504, Python 3.14.7, Linux x86_64. Only test harness and documents changed this session; game sources/resources and reference inputs are unchanged.

## Results

| Check | Result | Evidence |
|---|---|---|
| Four-way original/built codecs | PASS | result.json: 553 observations identical across both original jars and both built artifacts |
| New movement observations | PASS | 378 cases for packets 10–13; wire hashes, reported sizes, exact field bits, flags and unread bytes |
| Previous control/string cases | PASS | First 175 report rows unchanged from shared-string checkpoint |
| Constructor coordinate order | PASS | Common Packet13 constructor exercised with source-informed client acknowledgement and server correction vectors |
| Current source/reference preservation | PASS | source-verification.txt: 1,212 files, 3 existing evolution entries; reference inventory unchanged |
| Python tools | PASS | python-tests.txt: 16 tests |
| Actual handlers/player physics/two-player/graphical retest | NOT RUN | Source review and packet vectors do not execute host handlers |
| New game build/server smoke | NOT RUN | Existing artifacts tested; no game changes |

The movement matrix covers ordinary coordinates, negative zero, riding -999 Y/stance sentinels, ±30,000,000 coordinates, subnormal/extreme values, infinities/NaNs, wrapped/extreme angles, ground bytes 0/1/2/127/255, both receive directions, and every truncation length. Successful reads assert packet ID, moving/rotating/onGround flags; all numeric fields are recorded as raw IEEE bits. Ground values other than zero serialize back as one. Constructor tests independently build expected bytes in x/y/stance/z/yaw/pitch/ground order. The constructor vectors use feet=64 and eyes=64+(double)1.62F, preserving the float-to-double expression in the server call site.

A Beta behavior explicitly retained: Packet10Flying.readPacketData uses read()!=0. EOF (-1) for the final ground byte therefore becomes true instead of throwing. When all preceding fields are present but the final byte is missing, Packet.readPacket returns a movement packet; earlier numeric-field truncation produces its existing EOF/null behavior. The test asserts this distinction against both originals. This is not authorization to repair it.

Original movement field mappings (Packet10Flying ig/client, fm/server) are xPosition=a, yPosition=b, zPosition=c, stance=d, yaw=e, pitch=f, onGround=g, moving=h, rotating=i. Reflection resolves them through inherited public fields, and packet class selection uses the original ID registry. The existing Packet method mappings remain those from the reviewed ledger. Exact jar/tool hashes, JVM and child command arrays are in result.json. Complete observations are retained in four TSVs; no normalization of field values is used to hide mismatches.

## Side-policy review and limits

EntityClientPlayerMP sends boundingBox.minY as yPosition and posY as stance; its riding branch sends -999 for both Y fields. NetServerHandler treats that pair specially; otherwise its stance difference check uses >1.65 or <0.1 with the existing player-state condition. Server corrections construct Packet13 with y+(double)1.62F followed by y. NetClientHandler applies correction coordinates, then acknowledges with boundingBox.minY and posY. These call sites and source hashes are recorded in review.json. Neither codec parity nor synthetic constructor vectors establish actual movement acceptance, collision, flying checks, correction timing or two-player behavior. Those remain NOT RUN.

## Commands

```sh
python3 tools/check_beta_packet_codecs.py --output .plan/status/evidence/2026-09-09-a1-movement-codecs
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/verify_working_sources.py
```

All exit 0. A Python assertion compared the first 175 rows to the previous shared-string oracle, and inventory(Path('reference')) to the acquisition inventory; PASS in review.json. `git diff --check` PASS. No new game build, network access or graphical launch was needed. Reproduce in a fresh output directory; original jars remain explicit read-only test inputs, never build dependencies.

Next: qualify packet accounting against originals before moving the byte-identical PacketCounter/Empty1 pair into main. That closed two-class dependency is smaller than the Packet/NetHandler/57-leaf registry cycle. Full shared packet design and host scenario qualification remain open; no milestone promotion.
