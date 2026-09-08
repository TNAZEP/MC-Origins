package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ClientboundSetEquipmentPacket implements Packet<ClientGamePacketListener> {
   private static final byte CONTINUE_MASK = -128;
   private final int entity;
   private final List<Pair<EquipmentSlot, ItemStack>> slots;

   public ClientboundSetEquipmentPacket(int var1, List<Pair<EquipmentSlot, ItemStack>> var2) {
      this.entity = â˜ƒ;
      this.slots = â˜ƒ;
   }

   public ClientboundSetEquipmentPacket(FriendlyByteBuf var1) {
      this.entity = â˜ƒ.readVarInt();
      EquipmentSlot[] â˜ƒ = EquipmentSlot.values();
      this.slots = Lists.<Pair<EquipmentSlot, ItemStack>>newArrayList();

      int â˜ƒ;
      do {
         â˜ƒ = â˜ƒ.readByte();
         EquipmentSlot â˜ƒx = â˜ƒ[â˜ƒ & 127];
         ItemStack â˜ƒxx = â˜ƒ.readItem();
         this.slots.add(Pair.of(â˜ƒx, â˜ƒxx));
      } while((â˜ƒ & -128) != 0);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entity);
      int â˜ƒ = this.slots.size();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         Pair<EquipmentSlot, ItemStack> â˜ƒxx = (Pair)this.slots.get(â˜ƒx);
         EquipmentSlot â˜ƒxxx = (EquipmentSlot)â˜ƒxx.getFirst();
         boolean â˜ƒxxxx = â˜ƒx != â˜ƒ - 1;
         int â˜ƒxxxxx = â˜ƒxxx.ordinal();
         â˜ƒ.writeByte(â˜ƒxxxx ? â˜ƒxxxxx | -128 : â˜ƒxxxxx);
         â˜ƒ.writeItem(â˜ƒxx.getSecond());
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetEquipment(this);
   }

   public int getEntity() {
      return this.entity;
   }

   public List<Pair<EquipmentSlot, ItemStack>> getSlots() {
      return this.slots;
   }
}
