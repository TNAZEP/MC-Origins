package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.BitSet;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

public class ClientboundLevelChunkPacket implements Packet<ClientGamePacketListener> {
   public static final int TWO_MEGABYTES = 2097152;
   private final int x;
   private final int z;
   private final BitSet availableSections;
   private final CompoundTag heightmaps;
   private final int[] biomes;
   private final byte[] buffer;
   private final List<CompoundTag> blockEntitiesTags;

   public ClientboundLevelChunkPacket(LevelChunk var1) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      this.x = â˜ƒ.x;
      this.z = â˜ƒ.z;
      this.heightmaps = new CompoundTag();

      for(Entry<Heightmap.Types, Heightmap> â˜ƒx : â˜ƒ.getHeightmaps()) {
         if (((Heightmap.Types)â˜ƒx.getKey()).sendToClient()) {
            this.heightmaps.put(((Heightmap.Types)â˜ƒx.getKey()).getSerializationKey(), new LongArrayTag(((Heightmap)â˜ƒx.getValue()).getRawData()));
         }
      }

      this.biomes = â˜ƒ.getBiomes().writeBiomes();
      this.buffer = new byte[this.calculateChunkSize(â˜ƒ)];
      this.availableSections = this.extractChunkData(new FriendlyByteBuf(this.getWriteBuffer()), â˜ƒ);
      this.blockEntitiesTags = Lists.<CompoundTag>newArrayList();

      for(Entry<BlockPos, BlockEntity> â˜ƒx : â˜ƒ.getBlockEntities().entrySet()) {
         BlockEntity â˜ƒxx = (BlockEntity)â˜ƒx.getValue();
         CompoundTag â˜ƒxxx = â˜ƒxx.getUpdateTag();
         this.blockEntitiesTags.add(â˜ƒxxx);
      }
   }

   public ClientboundLevelChunkPacket(FriendlyByteBuf var1) {
      this.x = â˜ƒ.readInt();
      this.z = â˜ƒ.readInt();
      this.availableSections = â˜ƒ.readBitSet();
      this.heightmaps = â˜ƒ.readNbt();
      if (this.heightmaps == null) {
         throw new RuntimeException("Can't read heightmap in packet for [" + this.x + ", " + this.z + "]");
      } else {
         this.biomes = â˜ƒ.readVarIntArray(ChunkBiomeContainer.MAX_SIZE);
         int â˜ƒ = â˜ƒ.readVarInt();
         if (â˜ƒ > 2097152) {
            throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
         } else {
            this.buffer = new byte[â˜ƒ];
            â˜ƒ.readBytes(this.buffer);
            this.blockEntitiesTags = â˜ƒ.readList(FriendlyByteBuf::readNbt);
         }
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.x);
      â˜ƒ.writeInt(this.z);
      â˜ƒ.writeBitSet(this.availableSections);
      â˜ƒ.writeNbt(this.heightmaps);
      â˜ƒ.writeVarIntArray(this.biomes);
      â˜ƒ.writeVarInt(this.buffer.length);
      â˜ƒ.writeBytes(this.buffer);
      â˜ƒ.writeCollection(this.blockEntitiesTags, FriendlyByteBuf::writeNbt);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleLevelChunk(this);
   }

   public FriendlyByteBuf getReadBuffer() {
      return new FriendlyByteBuf(Unpooled.wrappedBuffer(this.buffer));
   }

   private ByteBuf getWriteBuffer() {
      ByteBuf â˜ƒ = Unpooled.wrappedBuffer(this.buffer);
      â˜ƒ.writerIndex(0);
      return â˜ƒ;
   }

   public BitSet extractChunkData(FriendlyByteBuf var1, LevelChunk var2) {
      BitSet â˜ƒ = new BitSet();
      LevelChunkSection[] â˜ƒx = â˜ƒ.getSections();
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = â˜ƒx.length; â˜ƒxx < â˜ƒxxx; ++â˜ƒxx) {
         LevelChunkSection â˜ƒxxxx = â˜ƒx[â˜ƒxx];
         if (â˜ƒxxxx != LevelChunk.EMPTY_SECTION && !â˜ƒxxxx.isEmpty()) {
            â˜ƒ.set(â˜ƒxx);
            â˜ƒxxxx.write(â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   protected int calculateChunkSize(LevelChunk var1) {
      int â˜ƒ = 0;
      LevelChunkSection[] â˜ƒx = â˜ƒ.getSections();
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = â˜ƒx.length; â˜ƒxx < â˜ƒxxx; ++â˜ƒxx) {
         LevelChunkSection â˜ƒxxxx = â˜ƒx[â˜ƒxx];
         if (â˜ƒxxxx != LevelChunk.EMPTY_SECTION && !â˜ƒxxxx.isEmpty()) {
            â˜ƒ += â˜ƒxxxx.getSerializedSize();
         }
      }

      return â˜ƒ;
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public BitSet getAvailableSections() {
      return this.availableSections;
   }

   public CompoundTag getHeightmaps() {
      return this.heightmaps;
   }

   public List<CompoundTag> getBlockEntitiesTags() {
      return this.blockEntitiesTags;
   }

   public int[] getBiomes() {
      return this.biomes;
   }
}
