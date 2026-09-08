package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.Level;

public class ServerboundSetCommandMinecartPacket implements Packet<ServerGamePacketListener> {
   private final int entity;
   private final String command;
   private final boolean trackOutput;

   public ServerboundSetCommandMinecartPacket(int var1, String var2, boolean var3) {
      this.entity = â˜ƒ;
      this.command = â˜ƒ;
      this.trackOutput = â˜ƒ;
   }

   public ServerboundSetCommandMinecartPacket(FriendlyByteBuf var1) {
      this.entity = â˜ƒ.readVarInt();
      this.command = â˜ƒ.readUtf();
      this.trackOutput = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entity);
      â˜ƒ.writeUtf(this.command);
      â˜ƒ.writeBoolean(this.trackOutput);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetCommandMinecart(this);
   }

   @Nullable
   public BaseCommandBlock getCommandBlock(Level var1) {
      Entity â˜ƒ = â˜ƒ.getEntity(this.entity);
      return â˜ƒ instanceof MinecartCommandBlock ? ((MinecartCommandBlock)â˜ƒ).getCommandBlock() : null;
   }

   public String getCommand() {
      return this.command;
   }

   public boolean isTrackOutput() {
      return this.trackOutput;
   }
}
