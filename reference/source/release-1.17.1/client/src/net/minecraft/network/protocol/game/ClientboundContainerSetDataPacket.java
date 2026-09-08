package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundContainerSetDataPacket implements Packet<ClientGamePacketListener> {
   private final int containerId;
   private final int id;
   private final int value;

   public ClientboundContainerSetDataPacket(int var1, int var2, int var3) {
      this.containerId = â˜ƒ;
      this.id = â˜ƒ;
      this.value = â˜ƒ;
   }

   public ClientboundContainerSetDataPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readUnsignedByte();
      this.id = â˜ƒ.readShort();
      this.value = â˜ƒ.readShort();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeShort(this.id);
      â˜ƒ.writeShort(this.value);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleContainerSetData(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public int getId() {
      return this.id;
   }

   public int getValue() {
      return this.value;
   }
}
