package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FurnaceFuelSlot extends Slot {
   private final AbstractFurnaceMenu menu;

   public FurnaceFuelSlot(AbstractFurnaceMenu var1, Container var2, int var3, int var4, int var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.menu = â˜ƒ;
   }

   @Override
   public boolean mayPlace(ItemStack var1) {
      return this.menu.isFuel(â˜ƒ) || isBucket(â˜ƒ);
   }

   @Override
   public int getMaxStackSize(ItemStack var1) {
      return isBucket(â˜ƒ) ? 1 : super.getMaxStackSize(â˜ƒ);
   }

   public static boolean isBucket(ItemStack var0) {
      return â˜ƒ.is(Items.BUCKET);
   }
}
