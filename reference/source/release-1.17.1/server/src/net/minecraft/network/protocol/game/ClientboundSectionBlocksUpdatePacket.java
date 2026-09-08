package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class ClientboundSectionBlocksUpdatePacket implements Packet<ClientGamePacketListener> {
   private static final int POS_IN_SECTION_BITS = 12;
   private final SectionPos sectionPos;
   private final short[] positions;
   private final BlockState[] states;
   private final boolean suppressLightUpdates;

   public ClientboundSectionBlocksUpdatePacket(SectionPos var1, ShortSet var2, LevelChunkSection var3, boolean var4) {
      this.sectionPos = â˜ƒ;
      this.suppressLightUpdates = â˜ƒ;
      int â˜ƒ = â˜ƒ.size();
      this.positions = new short[â˜ƒ];
      this.states = new BlockState[â˜ƒ];
      int â˜ƒx = 0;

      for(short â˜ƒxx : â˜ƒ) {
         this.positions[â˜ƒx] = â˜ƒxx;
         this.states[â˜ƒx] = â˜ƒ.getBlockState(SectionPos.sectionRelativeX(â˜ƒxx), SectionPos.sectionRelativeY(â˜ƒxx), SectionPos.sectionRelativeZ(â˜ƒxx));
         ++â˜ƒx;
      }
   }

   public ClientboundSectionBlocksUpdatePacket(FriendlyByteBuf var1) {
      this.sectionPos = SectionPos.of(â˜ƒ.readLong());
      this.suppressLightUpdates = â˜ƒ.readBoolean();
      int â˜ƒ = â˜ƒ.readVarInt();
      this.positions = new short[â˜ƒ];
      this.states = new BlockState[â˜ƒ];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         long â˜ƒxx = â˜ƒ.readVarLong();
         this.positions[â˜ƒx] = (short)((int)(â˜ƒxx & 4095L));
         this.states[â˜ƒx] = Block.BLOCK_STATE_REGISTRY.byId((int)(â˜ƒxx >>> 12));
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeLong(this.sectionPos.asLong());
      â˜ƒ.writeBoolean(this.suppressLightUpdates);
      â˜ƒ.writeVarInt(this.positions.length);

      for(int â˜ƒ = 0; â˜ƒ < this.positions.length; ++â˜ƒ) {
         â˜ƒ.writeVarLong((long)(Block.getId(this.states[â˜ƒ]) << 12 | this.positions[â˜ƒ]));
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleChunkBlocksUpdate(this);
   }

   public void runUpdates(BiConsumer<BlockPos, BlockState> var1) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = 0; â˜ƒx < this.positions.length; ++â˜ƒx) {
         short â˜ƒxx = this.positions[â˜ƒx];
         â˜ƒ.set(this.sectionPos.relativeToBlockX(â˜ƒxx), this.sectionPos.relativeToBlockY(â˜ƒxx), this.sectionPos.relativeToBlockZ(â˜ƒxx));
         â˜ƒ.accept(â˜ƒ, this.states[â˜ƒx]);
      }
   }

   public boolean shouldSuppressLightUpdates() {
      return this.suppressLightUpdates;
   }
}
