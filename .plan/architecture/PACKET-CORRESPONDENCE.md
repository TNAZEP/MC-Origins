# Beta packet/source correspondence — a1 review

Status: bounded source review completed 2026-09-09; no classes moved. Runtime codec parity and full shared-source acceptance remain NOT RUN. Base e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus existing work. Beta sides are authoritative; 1.3.2 is organization study only.

[Per-class inventory](../status/evidence/2026-09-09-a1-packet-ledger/inventory.md), [method bodies/signatures, hashes and original names](../status/evidence/2026-09-09-a1-packet-ledger/ledger.json), [handler correspondence](../status/evidence/2026-09-09-a1-packet-ledger/handler-correspondence.json), [verification and limits](../status/evidence/2026-09-09-a1-packet-ledger/README.md).

## Correspondence established

Both Packet registries declare the same 57 packet IDs, concrete classes and receive-direction flags. Packet and PacketCounter are byte-identical. Eight concrete packets are also byte-identical: 0 KeepAlive, 2 Handshake, 3 Chat, 13 PlayerLookMove, 18 Animation, 20 NamedEntitySpawn, 21 PickupSpawn and 255 KickDisconnect. Equality of a class does not imply that its dependencies can already compile in main.

Across 57 × 4 core method slots (read, write, dispatch, size): 170 bodies have equal tokens, 51 differ only in verified mapped field/handler names, six dispatch slots inherit from Packet10Flying or Packet30Entity. One remaining reader difference is Packet25EntityPainting's EnumArt.maxArtTitleLength versus field_27096_z; both Beta fields initialize to "SkullAndRoses".length(), 13. This resolves the source-level difference without treating it as a general name substitution. Runtime wire equality still needs fixtures.

NetHandler has 53 mapped method correspondences, including abstract isServerHandler; 25 method names differ. The 52 concrete handler bodies match. Most forward to registerPacket, but handleMapChunk, registerPacket and handleErrorMessage are empty; retain those defaults. Packet0KeepAlive.processPacket is also empty. Renaming a callback requires updating every override and dispatch call together.

## Boundaries to retain

| Area | Classification | Required treatment |
|---|---|---|
| Packet registration/read/write/string helpers | Equivalent source; protocol policy | Preserve IDs, direction flags, EOF handling, counters, Java UTF-16 char encoding and signed-short length validation |
| NetHandler callback names | Mapping difference; matching base bodies | Choose a consistent shared callback vocabulary only with complete override/call-site coverage |
| NetClientHandler vs NetServerHandler/NetLoginHandler | Intentional host behavior | Keep client world/presentation application, server validation/authority and login/auth lifecycle on their own sides |
| Login packet constructors | Side producer API | Client constructor supplies username/protocol; server also supplies map seed/dimension. Preserve both data meanings |
| Movement 10–13 and 27 | Shared field codec; side semantics | Preserve moving/rotating flags, sentinel values, Y/stance ordering and producer/consumer behavior |
| Scalar constructor-only differences | Producer convenience | A union can be considered after codec tests; absence on one side is not evidence of changed wire format |
| Entity/player/painting/velocity constructors | Dependencies and producer conversion | Preserve entity IDs, floor/scale/angle conversions and velocity clamps; keep host-to-payload conversion outside common codec where appropriate |
| ItemStack packets 5/15/102–104 | Shared payload shape, unresolved shared item graph | Preserve null item representation, item damage/count and constructor copy behavior; review ItemStack before introducing common dependency |
| DataWatcher packets 24/40 | Metadata codec/dependency | Preserve encoding and receiver accessors; Packet24.getMetadata exists only on client, so do not delete it during union |
| Chunk packets 51–53 | Server producer dependency | Separate World/Chunk snapshot extraction from common payload/serialization; retain Deflater behavior, chunk-data scheduling flag and array layout |
| Explosion 60 | Collection/value dependency | Preserve copied set construction, ChunkPosition encoding and iteration effects |
| Vehicle 23 / map data 131 getPacketSize | Existing accounting quirks | Preserve current formulas pending original-bytecode/runtime investigation; do not replace with guessed wire length |

Movement example: server correction constructs Packet13 with y + (double)1.62F followed by y; client handler applies incoming yPosition, then acknowledges with boundingBox.minY as yPosition and posY as stance. Server movement validates stance minus yPosition. The byte-identical Packet13 class cannot justify collapsing these call-site policies. NetworkManager passes isServerHandler into Packet.readPacket: client=false, server/login=true; preserve receive-direction rejection.

The two size formulas particularly need tests: Packet23 currently parses as `(21 + value > 0) ? 6 : 0`; Packet131 returns `4 + payload.length` while write emits two shorts, a length byte and payload. These are source observations, not authorization to fix historical behavior or claims of original-bytecode semantic verification. Packet counters consume reported sizes.

## Build boundary and next change

Moving Packet alone is not a viable source-set change: its static registry names all concrete packets, NetHandler names the packet types, and some codecs/constructors depend on ItemStack, DataWatcher, Entity/World and other unmerged classes. The generated game_type_mentions lists are lexical leads, not a proven dependency closure. Do not add side source roots or reference jars to main's classpath to hide this cycle.

Next bounded task: build an isolated original-client/original-server/built-client/built-server byte-level oracle for the small control/string packet group (0, 2, 3, 255) and Packet string helpers. Cover normal/empty/Unicode strings, lengths, truncation and invalid IDs/directions. Use its evidence for the first small shared primitive/helper extraction, then expand the packet graph deliberately. Preserve real two-player and SP/visual acceptance requirements; codec fixtures do not replace those scenarios.

## 1.3.2 organization study

Both supplied named study trees expose net.minecraft.network.Packet with readFrom/writeTo/apply(PacketListener)/getSize, and an abstract network.listener.PacketListener with typed packet callbacks. Direction-oriented packet packages show one possible organization for shared codec plus listener dispatch. Their larger packet registry, login/encryption, item/NBT helpers, modern behavior and loopback/integrated-server paths are not adopted. Input hashes for both Packet/PacketListener files are retained in the evidence. These are local CFR study sources with previously recorded provenance limits, not a substitute for Beta parity.

## First implementation checkpoint — 2026-09-09

Both Packet string helpers now delegate to one main-source BetaStringCodec with verbatim Beta method bodies. Four-way original/built codec reports match across 175 observations before and after extraction; both artifacts package identical shared class bytes and headless login smoke passes. [Evidence](../status/evidence/2026-09-09-a1-shared-strings/README.md). Other packet classes/handlers remain unmerged. Next expand movement byte fixtures and preserve Y/stance producer/handler policy before addressing the registry dependency cycle.

## Movement checkpoint and bounded sharing boundary — 2026-09-09

[Movement evidence](../status/evidence/2026-09-09-a1-movement-codecs/README.md): 553 four-way original/built observations PASS, including 378 new movement cases; common Packet13 constructor preserves source-informed correction/acknowledgement ordering. Final-ground-byte EOF becomes true in Beta and is explicitly preserved. Actual handlers/physics/two-player movement remain untested by these vectors.

The next closed dependency slice is PacketCounter plus Empty1: both files are byte-identical across Beta sides, and a repository source search finds Empty1 referenced only by PacketCounter's synthetic constructor and Packet's counter allocation. They can be compiled in main without importing Packet, NetHandler, World, items or side roots. Before moving them, extend the oracle to counter totals/size accounting and qualify same-side original/built results; preserve existing packet-size quirks. This is a concrete small class-move candidate, not permission to flatten the larger packet graph or discard the Empty1 compatibility constructor. Registry population and typed handler dependencies stay in the side Packet graph until a separately verified boundary is implemented.

## Shared accounting implementation — 2026-09-09–10

PacketCounter/Empty1 moved verbatim from both sides into main; old side copies removed. 566 four-way observations match before/after, clean builds package one common copy, dedicated login/stop passes, and shared-aware inventory still verifies 998 descriptors. [Evidence](../status/evidence/2026-09-09-a1-shared-accounting/README.md). Packet 23/131 size quirks are now runtime-confirmed in both originals for the tested vectors. Next: a concrete registry/handler boundary for shared control packets; the larger packet graph remains unmerged.

## Registry/handler boundary disposition — 2026-09-10

Implemented BetaPacketRegistry owns shared lookup/direction/duplicate rules; side Packet classes supply their unchanged class-literal registration tables during existing static initialization. Both sides compile against a JDK-only common registry. 1,338 observations match both originals before/after; build/archive and login smoke PASS. [Evidence](../status/evidence/2026-09-10-a1-shared-registry/README.md).

Typed NetHandler remains a shared-packet prerequisite because it references the full packet family, whose ItemStack/DataWatcher fields and entity/world constructors still depend on unmerged model types. The small control-packet move therefore cannot be completed just by extracting the registry. Do not hide that remaining dependency by adding side source roots to main, introducing reflective class-name bootstrap, or routing typed callbacks through unchecked casts merely to make a four-class move compile. Those alternatives would add new initialization/dispatch behavior without removing the model dependency.

The migration order is now concrete: retain explicit side registration while sharing registry rules (done); review payload field/constructor dependency closure and extract host-to-payload construction where evidence supports it; reconcile shared payload/model types; then move Packet, typed NetHandler and the compatible leaf group together, with descriptor/dispatch/runtime checks. Callback aliases and producer semantics remain mandatory ledger items. Next review ItemStack/DataWatcher and entity/world producer dependencies to choose a buildable slice; no integrated-server or gameplay changes follow from this engineering order.

## Payload dependency review and WatchableObject union — 2026-09-10

[Evidence](../status/evidence/2026-09-10-a1-shared-watchable/README.md) records reviewed source hashes, original mapped bytecode and 1,392 before/after observations. This is a bounded dependency review, not a compiler-proven closure of all entity/world classes.

| Type/group | Observed dependency or difference | Disposition |
|---|---|---|
| WatchableObject | Only Object and primitives; server adds getWatching, all other source bytes match | Shared verbatim server API union; direct original state/identity/flag parity PASS, no caller changes |
| ChunkCoordinates | JDK-only; client x/y/z vs server posX/posY/posZ; other bodies match after those aliases | Next value-type candidate after original arithmetic/equality/order fixtures and complete call-site rename review; do not add duplicate mutable coordinate fields |
| ChunkPosition | Byte-identical, primitive final fields, JDK-only; used by explosion sets and world code | Independently buildable candidate after equality/hash and collection-order qualification; not moved this session |
| DataWatcher | WatchableObject, ItemStack -> Item, ChunkCoordinates and Packet string helpers | WatchableObject now common; other dependencies still side-local. Client integer getter name differs; server has hasObjectChanged/getChangedObjects, client has updateWatchedObjectsFromList. Preserve these separate lifecycle methods |
| ItemStack | Block, Item, NBTTagCompound, Entity, EntityPlayer, EntityLiving, World, StatList | Not a closed value-only type. Client icon/name accessors and mapped stat/item callback names need reconciliation with their target APIs; no wholesale union yet |
| Packets 15/102/103/104 | ItemStack fields/arrays; primitive ID/count/damage serialization and null marker | Keep signed reads, short/byte narrowing and constructor copying. 103 copies a non-null input; 104 allocates a new array and copies each non-null element. No replacement item model introduced |
| Packet24 | EntityLiving/EntityList/MathHelper producer; retained DataWatcher reference; separate received list | Preserve floor(position*32), angle casts, retained watcher timing and client getMetadata. Reading then writing is not a generic round-trip fixture: writer uses metaData, while reader fills receivedMetadata |
| Packet40 | Server producer calls getChangedObjects; client exposes received list | Preserve dirty-list consumption at construction and receiver accessor; no eager recomputation |
| Packets51–53 | World/Chunk access confined to producing constructors; codec uses scalar/array data | Future side producer extraction must preserve read timing, scheduling flag, coordinate packing and snapshot layout. Packet51 also compresses in constructor with a fixed buffer and one deflate call; do not replace it with a different compression loop |

DataWatcher review specifics: addObject rejects IDs above 31 but has no lower-bound check; WatchableObject itself validates neither ID nor type. Dirty state is controlled by DataWatcher.updateObject, not WatchableObject.setObject. Server getChangedObjects clears per-object flags and the global flag; client updateWatchedObjectsFromList directly replaces existing object references. Retain HashMap iteration order. Metadata type 5 writes getItem().shiftedIndex rather than simply itemID, so item lookup/failure semantics remain part of qualification. The decoder returns null for a bare terminator and appends a null object for unsupported type 7; these source observations require runtime fixtures before any rewrite.

Next: original/built ChunkCoordinates and metadata state/wire fixtures, then the smallest qualified model move. Full Packet/NetHandler and producer graph remains side-local. No unchecked dispatch or reference/side compile dependencies added to main.

## Shared coordinate/NBT group — 2026-09-10

Implemented the coordinate and NBT dependencies as one 15-class group, bringing main to 20 classes. ChunkCoordinates adopts client x/y/z names; all server accesses reconciled using compiler-resolved receiver types. ChunkPosition and eleven NBT tag classes plus NBTBase retain common logic. NBTTagCompound collection accessor becomes getTags; CompressedStreamTools methods become readCompressed/read/write with both caller sets updated. NBT serialization/stream closure and coordinate arithmetic unchanged. Source provenance in EVOLUTION.json; [build/runtime evidence](../status/evidence/2026-09-10-a1-shared-values/README.md).

Earlier fixture-first prerequisites above are superseded by ADR-0012. Next implementation dependency is ItemStack's Item/Block/entity/world coupling; DataWatcher still retains side lifecycle methods and ItemStack dependency. Shared NBT is still Beta persistence, not the future 1.17.1 format.

## Shared DataWatcher — 2026-09-10

DataWatcher now lives in main. An explicit ItemCodec supplies item type identification and type-5 read/write; side ItemStackMetadataCodec preserves getItem().shiftedIndex, signed count/damage and construction. Entity owns a watcher bound to its side adapter; Packet24/40 pass the same adapter for static codecs. No global mutable registration or side build dependency. Server getChangedObjects and client updateWatchedObjectsFromList retained in the shared API; integer getter unified as getWatchableObjectInteger. ItemStack adapters are temporary until that model is shared. [Build/metadata/login evidence](../status/evidence/2026-09-10-a1-metadata-version/README.md).

## Shared stack data — 2026-09-10

ItemStackData<T> is the common storage/copy/split/NBT/equality base; each side ItemStack supplies createStack returning its concrete type. It depends only on common NBT, not Item/Block/World/Entity. Side item/gameplay methods remain in ItemStack. Integer/damage field visibility and renamed stack methods are recorded in [evidence](../status/evidence/2026-09-10-a1-stack-data/README.md). This removes duplicated value logic without introducing generic Object-based gameplay dispatch. Next reconcile Item callback/subclass APIs and concrete model dependencies. Packets still expose concrete side ItemStacks; adapters are not yet removed.

## Item callback reconciliation — 2026-09-10

Server Item callback aliases and overrides/callers now use the client names (onBlockDestroyed, onUpdate, onCreated, getPlacedBlockMetadata, getStatName, isDamagable); map/shears fields unified. Server-only isMap discriminator and client-only rendering accessors retain their ownership. Tool-material enum is shared. This is API reconciliation, not a full Item graph move. [Evidence](../status/evidence/2026-09-10-a1-item-api/README.md). Next registration/statistics and concrete stack host dependencies.

## 2026-09-11 implemented disposition

All registered packets and base NetHandler are shared. Core methods checked equal after packet/handler aliases; union retains side constructor/accessor APIs. Entity/world constructors are preserved in each side PacketFactory, item fields use shared value data and receipt restores gameplay stacks. NetworkManager and four worker/close helpers are shared; extra server APIs retained. [Aliases, provenance and functional evidence](../status/evidence/2026-09-11-a1-packets/README.md). Full gameplay model merge remains pending.
