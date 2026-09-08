package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.Player;

public class ClientboundAddPlayerPacket implements Packet<ClientGamePacketListener> {
   private final int entityId;
   private final UUID playerId;
   private final double x;
   private final double y;
   private final double z;
   private final byte yRot;
   private final byte xRot;

   public ClientboundAddPlayerPacket(Player var1) {
      this.entityId = â˜ƒ.getId();
      this.playerId = â˜ƒ.getGameProfile().getId();
      this.x = â˜ƒ.getX();
      this.y = â˜ƒ.getY();
      this.z = â˜ƒ.getZ();
      this.yRot = (byte)((int)(â˜ƒ.getYRot() * 256.0F / 360.0F));
      this.xRot = (byte)((int)(â˜ƒ.getXRot() * 256.0F / 360.0F));
   }

   public ClientboundAddPlayerPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readVarInt();
      this.playerId = â˜ƒ.readUUID();
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.yRot = â˜ƒ.readByte();
      this.xRot = â˜ƒ.readByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entityId);
      â˜ƒ.writeUUID(this.playerId);
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeByte(this.yRot);
      â˜ƒ.writeByte(this.xRot);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddPlayer(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public UUID getPlayerId() {
      return this.playerId;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public byte getyRot() {
      return this.yRot;
   }

   public byte getxRot() {
      return this.xRot;
   }
}
