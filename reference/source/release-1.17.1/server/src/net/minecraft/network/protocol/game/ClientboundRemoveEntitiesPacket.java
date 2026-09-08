package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundRemoveEntitiesPacket implements Packet<ClientGamePacketListener> {
   private final IntList entityIds;

   public ClientboundRemoveEntitiesPacket(IntList var1) {
      this.entityIds = new IntArrayList(â˜ƒ);
   }

   public ClientboundRemoveEntitiesPacket(int... var1) {
      this.entityIds = new IntArrayList(â˜ƒ);
   }

   public ClientboundRemoveEntitiesPacket(FriendlyByteBuf var1) {
      this.entityIds = â˜ƒ.readIntIdList();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeIntIdList(this.entityIds);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleRemoveEntities(this);
   }

   public IntList getEntityIds() {
      return this.entityIds;
   }
}
