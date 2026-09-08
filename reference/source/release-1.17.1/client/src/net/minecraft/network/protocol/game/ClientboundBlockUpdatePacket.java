package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ClientboundBlockUpdatePacket implements Packet<ClientGamePacketListener> {
   private final BlockPos pos;
   private final BlockState blockState;

   public ClientboundBlockUpdatePacket(BlockPos var1, BlockState var2) {
      this.pos = â˜ƒ;
      this.blockState = â˜ƒ;
   }

   public ClientboundBlockUpdatePacket(BlockGetter var1, BlockPos var2) {
      this(â˜ƒ, â˜ƒ.getBlockState(â˜ƒ));
   }

   public ClientboundBlockUpdatePacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.blockState = Block.BLOCK_STATE_REGISTRY.byId(â˜ƒ.readVarInt());
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeVarInt(Block.getId(this.blockState));
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleBlockUpdate(this);
   }

   public BlockState getBlockState() {
      return this.blockState;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
