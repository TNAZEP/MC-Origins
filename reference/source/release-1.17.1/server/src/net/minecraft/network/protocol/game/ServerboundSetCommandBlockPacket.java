package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.entity.CommandBlockEntity;

public class ServerboundSetCommandBlockPacket implements Packet<ServerGamePacketListener> {
   private static final int FLAG_TRACK_OUTPUT = 1;
   private static final int FLAG_CONDITIONAL = 2;
   private static final int FLAG_AUTOMATIC = 4;
   private final BlockPos pos;
   private final String command;
   private final boolean trackOutput;
   private final boolean conditional;
   private final boolean automatic;
   private final CommandBlockEntity.Mode mode;

   public ServerboundSetCommandBlockPacket(BlockPos var1, String var2, CommandBlockEntity.Mode var3, boolean var4, boolean var5, boolean var6) {
      this.pos = â˜ƒ;
      this.command = â˜ƒ;
      this.trackOutput = â˜ƒ;
      this.conditional = â˜ƒ;
      this.automatic = â˜ƒ;
      this.mode = â˜ƒ;
   }

   public ServerboundSetCommandBlockPacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.command = â˜ƒ.readUtf();
      this.mode = â˜ƒ.readEnum(CommandBlockEntity.Mode.class);
      int â˜ƒ = â˜ƒ.readByte();
      this.trackOutput = (â˜ƒ & 1) != 0;
      this.conditional = (â˜ƒ & 2) != 0;
      this.automatic = (â˜ƒ & 4) != 0;
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeUtf(this.command);
      â˜ƒ.writeEnum(this.mode);
      int â˜ƒ = 0;
      if (this.trackOutput) {
         â˜ƒ |= 1;
      }

      if (this.conditional) {
         â˜ƒ |= 2;
      }

      if (this.automatic) {
         â˜ƒ |= 4;
      }

      â˜ƒ.writeByte(â˜ƒ);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetCommandBlock(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public String getCommand() {
      return this.command;
   }

   public boolean isTrackOutput() {
      return this.trackOutput;
   }

   public boolean isConditional() {
      return this.conditional;
   }

   public boolean isAutomatic() {
      return this.automatic;
   }

   public CommandBlockEntity.Mode getMode() {
      return this.mode;
   }
}
