package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientboundBlockBreakAckPacket implements Packet<ClientGamePacketListener> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final BlockPos pos;
   private final BlockState state;
   private final ServerboundPlayerActionPacket.Action action;
   private final boolean allGood;

   public ClientboundBlockBreakAckPacket(BlockPos var1, BlockState var2, ServerboundPlayerActionPacket.Action var3, boolean var4, String var5) {
      this.pos = â˜ƒ.immutable();
      this.state = â˜ƒ;
      this.action = â˜ƒ;
      this.allGood = â˜ƒ;
   }

   public ClientboundBlockBreakAckPacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.state = Block.BLOCK_STATE_REGISTRY.byId(â˜ƒ.readVarInt());
      this.action = â˜ƒ.readEnum(ServerboundPlayerActionPacket.Action.class);
      this.allGood = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeVarInt(Block.getId(this.state));
      â˜ƒ.writeEnum(this.action);
      â˜ƒ.writeBoolean(this.allGood);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleBlockBreakAck(this);
   }

   public BlockState getState() {
      return this.state;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public boolean allGood() {
      return this.allGood;
   }

   public ServerboundPlayerActionPacket.Action action() {
      return this.action;
   }
}
