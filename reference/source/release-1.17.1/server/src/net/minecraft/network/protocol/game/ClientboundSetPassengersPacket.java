package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class ClientboundSetPassengersPacket implements Packet<ClientGamePacketListener> {
   private final int vehicle;
   private final int[] passengers;

   public ClientboundSetPassengersPacket(Entity var1) {
      this.vehicle = â˜ƒ.getId();
      List<Entity> â˜ƒ = â˜ƒ.getPassengers();
      this.passengers = new int[â˜ƒ.size()];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         this.passengers[â˜ƒx] = ((Entity)â˜ƒ.get(â˜ƒx)).getId();
      }
   }

   public ClientboundSetPassengersPacket(FriendlyByteBuf var1) {
      this.vehicle = â˜ƒ.readVarInt();
      this.passengers = â˜ƒ.readVarIntArray();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.vehicle);
      â˜ƒ.writeVarIntArray(this.passengers);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetEntityPassengersPacket(this);
   }

   public int[] getPassengers() {
      return this.passengers;
   }

   public int getVehicle() {
      return this.vehicle;
   }
}
