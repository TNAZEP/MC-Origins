package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundHorseScreenOpenPacket implements Packet<ClientGamePacketListener> {
   private final int containerId;
   private final int size;
   private final int entityId;

   public ClientboundHorseScreenOpenPacket(int var1, int var2, int var3) {
      this.containerId = â˜ƒ;
      this.size = â˜ƒ;
      this.entityId = â˜ƒ;
   }

   public ClientboundHorseScreenOpenPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readUnsignedByte();
      this.size = â˜ƒ.readVarInt();
      this.entityId = â˜ƒ.readInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeVarInt(this.size);
      â˜ƒ.writeInt(this.entityId);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleHorseScreenOpen(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public int getSize() {
      return this.size;
   }

   public int getEntityId() {
      return this.entityId;
   }
}
