package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;

public class ServerboundContainerClickPacket implements Packet<ServerGamePacketListener> {
   private static final int MAX_SLOT_COUNT = 128;
   private final int containerId;
   private final int stateId;
   private final int slotNum;
   private final int buttonNum;
   private final ClickType clickType;
   private final ItemStack carriedItem;
   private final Int2ObjectMap<ItemStack> changedSlots;

   public ServerboundContainerClickPacket(int var1, int var2, int var3, int var4, ClickType var5, ItemStack var6, Int2ObjectMap<ItemStack> var7) {
      this.containerId = â˜ƒ;
      this.stateId = â˜ƒ;
      this.slotNum = â˜ƒ;
      this.buttonNum = â˜ƒ;
      this.clickType = â˜ƒ;
      this.carriedItem = â˜ƒ;
      this.changedSlots = Int2ObjectMaps.unmodifiable(â˜ƒ);
   }

   public ServerboundContainerClickPacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readByte();
      this.stateId = â˜ƒ.readVarInt();
      this.slotNum = â˜ƒ.readShort();
      this.buttonNum = â˜ƒ.readByte();
      this.clickType = â˜ƒ.readEnum(ClickType.class);
      this.changedSlots = Int2ObjectMaps.unmodifiable(
         â˜ƒ.readMap(FriendlyByteBuf.limitValue(Int2ObjectOpenHashMap::new, 128), var0 -> Integer.valueOf(var0.readShort()), FriendlyByteBuf::readItem)
      );
      this.carriedItem = â˜ƒ.readItem();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeVarInt(this.stateId);
      â˜ƒ.writeShort(this.slotNum);
      â˜ƒ.writeByte(this.buttonNum);
      â˜ƒ.writeEnum(this.clickType);
      â˜ƒ.writeMap(this.changedSlots, FriendlyByteBuf::writeShort, FriendlyByteBuf::writeItem);
      â˜ƒ.writeItem(this.carriedItem);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleContainerClick(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public int getSlotNum() {
      return this.slotNum;
   }

   public int getButtonNum() {
      return this.buttonNum;
   }

   public ItemStack getCarriedItem() {
      return this.carriedItem;
   }

   public Int2ObjectMap<ItemStack> getChangedSlots() {
      return this.changedSlots;
   }

   public ClickType getClickType() {
      return this.clickType;
   }

   public int getStateId() {
      return this.stateId;
   }
}
