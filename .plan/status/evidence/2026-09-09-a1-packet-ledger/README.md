# Packet correspondence checkpoint — 2026-09-09

Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 / main plus pre-existing and this session's tooling/docs. Linux x86_64, Python 3.14.7, OpenJDK javap 8u504. No game Java/resources, build configuration, reference inputs or user worlds changed. No compile/launch/codec runtime/terrain suite rerun; the change is inventory tooling and review.

## Results

| Check | Result | Evidence |
|---|---|---|
| Registry correspondence | PASS | 57 IDs/classes/direction flags identical in both source trees |
| Class and original mapped members | PASS | 60 classes present in both originals; 998 mapped field/method descriptor checks against javap |
| Core source method classification | PASS | 170 token-equal, 51 equal after mapped names, 6 inherited; painting title limit manually resolved |
| NetHandler correspondence | PASS | 53 mapped methods, 25 renamed; 52 concrete bodies equal, one abstract side policy |
| Tool tests | PASS | tests.txt: 16 tests total; two new method-boundary/token-preservation checks |
| Input preservation | PASS | 1,211 working hashes; reference inventory equals previous acquisition inventory |
| Runtime codec/merge/client-MP acceptance | NOT RUN | No wire or shared-code implementation claim |

[Reviewed ledger and boundaries](../../../architecture/PACKET-CORRESPONDENCE.md) is the human disposition. inventory.md summarizes all 60 classes (57 registered concrete packets plus Packet, PacketCounter and NetHandler). ledger.json contains core method bodies and source lines, constructor signatures, lexical game-type mentions, original class names and both source hashes. The six inherited slots are concrete packet dispatches; helper/base inventory rows that lack a core method are not additional inheritance claims. Constructors omitted from source may be implicit; this tool does not invent their bodies.

Original class names come from the current candidate RetroMCP tiny mapping bundle, SHA-256 b1903f261dca46e25feb50151102afd11807d446ac49c59dac7a995b497c92a9. Each declared mapped member in scope, plus concrete overrides of the four Packet methods, is checked for its original name and translated JVM descriptor in the pinned original jars. The 998 checks are descriptor correspondence, not semantic bytecode equality. Constructor correspondence is recorded at source-signature level; constructor bytecode was not compared. Historical exact decompiler/mapping provenance remains unresolved.

Source normalization tokenizes strings/characters intact, ignoring inter-token whitespace. Only the current class's mapped fields and mapped NetHandler callbacks receive aliases. Arithmetic, constants, strings and call order remain unchanged. This is intentionally bounded to imported decompiler formatting, not a general Java parser or equivalence prover. The sole REVIEW entry remains in the mechanical report: Packet25EntityPainting reads the same limit via a side-renamed EnumArt constant; source inspection finds both initialize to the same 13-character title length. Original EnumArt descriptor dumps are retained separately. Tests ensure literal whitespace/operators and mutations are not normalized away.

All source differences remain in source-differences.patch. handler-correspondence.json matches callbacks by mapped named parameter descriptor, retaining 25 differing names and the abstract side-policy method. review-summary.json records original jars, reviewed extra host/EnumArt files, both 1.3.2 study pairs and tool hashes, actual environment and preservation result. No runtime codec parity is inferred from these comparisons.

## Commands and recovery

```sh
python3 tools/inventory_beta_packets.py --output .plan/status/evidence/2026-09-09-a1-packet-ledger
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/import_beta_sources.py --verify
```

All exit 0. ledger.json stores exact original javap command arrays; client-original-descriptors.txt and server-original-descriptors.txt retain outputs. Additional original EnumArt descriptors were read with `/usr/lib/jvm/java-8-openjdk/bin/javap -p -s -classpath JAR ORIGINAL_ENUMART_NAME`; mapped classes and supporting hashes are in the retained evidence. A Python assertion compared inventory(Path('reference')) with .plan/status/evidence/2026-09-08-a1-references/inventory.json; PASS. Source diffs and both Beta handlers/constructors were inspected, with conclusions and exact file names recorded in the reviewed ledger. 1.3.2 sources were located under release-1.3.2 after an initial lookup at 1.3.2 found no directory; no source input is missing for that study.

Reproduce the inventory in a fresh output directory. Do not treat a token match or the PASS review status as authorization to bypass runtime fixtures. Next: byte-level original/built control-packet and string-helper oracle before a small shared-helper extraction. Full shared packet graph, real two-player/SP/visual checks and a1 promotion remain incomplete. Final git diff --check PASS.
