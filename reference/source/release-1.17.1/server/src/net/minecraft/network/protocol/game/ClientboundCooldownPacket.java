package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.Item;

public class ClientboundCooldownPacket implements Packet<ClientGamePacketListener> {
   private final Item item;
   private final int duration;

   public ClientboundCooldownPacket(Item var1, int var2) {
      this.item = â˜ƒ;
      this.duration = â˜ƒ;
   }

   public ClientboundCooldownPacket(FriendlyByteBuf var1) {
      this.item = Item.byId(â˜ƒ.readVarInt());
      this.duration = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(Item.getId(this.item));
      â˜ƒ.writeVarInt(this.duration);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleItemCooldown(this);
   }

   public Item getItem() {
      return this.item;
   }

   public int getDuration() {
      return this.duration;
   }
}
