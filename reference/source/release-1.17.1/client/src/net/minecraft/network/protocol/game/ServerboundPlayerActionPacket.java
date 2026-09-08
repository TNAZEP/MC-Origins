package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPlayerActionPacket implements Packet<ServerGamePacketListener> {
   private final BlockPos pos;
   private final Direction direction;
   private final ServerboundPlayerActionPacket.Action action;

   public ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action var1, BlockPos var2, Direction var3) {
      this.action = â˜ƒ;
      this.pos = â˜ƒ.immutable();
      this.direction = â˜ƒ;
   }

   public ServerboundPlayerActionPacket(FriendlyByteBuf var1) {
      this.action = â˜ƒ.readEnum(ServerboundPlayerActionPacket.Action.class);
      this.pos = â˜ƒ.readBlockPos();
      this.direction = Direction.from3DDataValue(â˜ƒ.readUnsignedByte());
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.action);
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeByte(this.direction.get3DDataValue());
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePlayerAction(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public ServerboundPlayerActionPacket.Action getAction() {
      return this.action;
   }

   public static enum Action {
      START_DESTROY_BLOCK,
      ABORT_DESTROY_BLOCK,
      STOP_DESTROY_BLOCK,
      DROP_ALL_ITEMS,
      DROP_ITEM,
      RELEASE_USE_ITEM,
      SWAP_ITEM_WITH_OFFHAND;
   }
}
