package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundEntityEventPacket implements Packet<ClientGamePacketListener> {
   private final int entityId;
   private final byte eventId;

   public ClientboundEntityEventPacket(Entity var1, byte var2) {
      this.entityId = â˜ƒ.getId();
      this.eventId = â˜ƒ;
   }

   public ClientboundEntityEventPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readInt();
      this.eventId = â˜ƒ.readByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.entityId);
      â˜ƒ.writeByte(this.eventId);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleEntityEvent(this);
   }

   @Nullable
   public Entity getEntity(Level var1) {
      return â˜ƒ.getEntity(this.entityId);
   }

   public byte getEventId() {
      return this.eventId;
   }
}
