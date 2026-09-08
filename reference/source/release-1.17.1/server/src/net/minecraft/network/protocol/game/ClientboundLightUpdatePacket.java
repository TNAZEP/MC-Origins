package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ClientboundLightUpdatePacket implements Packet<ClientGamePacketListener> {
   private final int x;
   private final int z;
   private final BitSet skyYMask;
   private final BitSet blockYMask;
   private final BitSet emptySkyYMask;
   private final BitSet emptyBlockYMask;
   private final List<byte[]> skyUpdates;
   private final List<byte[]> blockUpdates;
   private final boolean trustEdges;

   public ClientboundLightUpdatePacket(ChunkPos var1, LevelLightEngine var2, @Nullable BitSet var3, @Nullable BitSet var4, boolean var5) {
      this.x = â˜ƒ.x;
      this.z = â˜ƒ.z;
      this.trustEdges = â˜ƒ;
      this.skyYMask = new BitSet();
      this.blockYMask = new BitSet();
      this.emptySkyYMask = new BitSet();
      this.emptyBlockYMask = new BitSet();
      this.skyUpdates = Lists.<byte[]>newArrayList();
      this.blockUpdates = Lists.<byte[]>newArrayList();

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getLightSectionCount(); ++â˜ƒ) {
         if (â˜ƒ == null || â˜ƒ.get(â˜ƒ)) {
            prepareSectionData(â˜ƒ, â˜ƒ, LightLayer.SKY, â˜ƒ, this.skyYMask, this.emptySkyYMask, this.skyUpdates);
         }

         if (â˜ƒ == null || â˜ƒ.get(â˜ƒ)) {
            prepareSectionData(â˜ƒ, â˜ƒ, LightLayer.BLOCK, â˜ƒ, this.blockYMask, this.emptyBlockYMask, this.blockUpdates);
         }
      }
   }

   private static void prepareSectionData(ChunkPos var0, LevelLightEngine var1, LightLayer var2, int var3, BitSet var4, BitSet var5, List<byte[]> var6) {
      DataLayer â˜ƒ = â˜ƒ.getLayerListener(â˜ƒ).getDataLayerData(SectionPos.of(â˜ƒ, â˜ƒ.getMinLightSection() + â˜ƒ));
      if (â˜ƒ != null) {
         if (â˜ƒ.isEmpty()) {
            â˜ƒ.set(â˜ƒ);
         } else {
            â˜ƒ.set(â˜ƒ);
            â˜ƒ.add((byte[])â˜ƒ.getData().clone());
         }
      }
   }

   public ClientboundLightUpdatePacket(FriendlyByteBuf var1) {
      this.x = â˜ƒ.readVarInt();
      this.z = â˜ƒ.readVarInt();
      this.trustEdges = â˜ƒ.readBoolean();
      this.skyYMask = â˜ƒ.readBitSet();
      this.blockYMask = â˜ƒ.readBitSet();
      this.emptySkyYMask = â˜ƒ.readBitSet();
      this.emptyBlockYMask = â˜ƒ.readBitSet();
      this.skyUpdates = â˜ƒ.readList(var0 -> var0.readByteArray(2048));
      this.blockUpdates = â˜ƒ.readList(var0 -> var0.readByteArray(2048));
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.x);
      â˜ƒ.writeVarInt(this.z);
      â˜ƒ.writeBoolean(this.trustEdges);
      â˜ƒ.writeBitSet(this.skyYMask);
      â˜ƒ.writeBitSet(this.blockYMask);
      â˜ƒ.writeBitSet(this.emptySkyYMask);
      â˜ƒ.writeBitSet(this.emptyBlockYMask);
      â˜ƒ.writeCollection(this.skyUpdates, FriendlyByteBuf::writeByteArray);
      â˜ƒ.writeCollection(this.blockUpdates, FriendlyByteBuf::writeByteArray);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleLightUpdatePacked(this);
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public BitSet getSkyYMask() {
      return this.skyYMask;
   }

   public BitSet getEmptySkyYMask() {
      return this.emptySkyYMask;
   }

   public List<byte[]> getSkyUpdates() {
      return this.skyUpdates;
   }

   public BitSet getBlockYMask() {
      return this.blockYMask;
   }

   public BitSet getEmptyBlockYMask() {
      return this.emptyBlockYMask;
   }

   public List<byte[]> getBlockUpdates() {
      return this.blockUpdates;
   }

   public boolean getTrustEdges() {
      return this.trustEdges;
   }
}
