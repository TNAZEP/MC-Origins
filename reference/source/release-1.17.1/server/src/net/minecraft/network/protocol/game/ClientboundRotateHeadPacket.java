package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundRotateHeadPacket implements Packet<ClientGamePacketListener> {
   private final int entityId;
   private final byte yHeadRot;

   public ClientboundRotateHeadPacket(Entity var1, byte var2) {
      this.entityId = â˜ƒ.getId();
      this.yHeadRot = â˜ƒ;
   }

   public ClientboundRotateHeadPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readVarInt();
      this.yHeadRot = â˜ƒ.readByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entityId);
      â˜ƒ.writeByte(this.yHeadRot);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleRotateMob(this);
   }

   public Entity getEntity(Level var1) {
      return â˜ƒ.getEntity(this.entityId);
   }

   public byte getYHeadRot() {
      return this.yHeadRot;
   }
}
