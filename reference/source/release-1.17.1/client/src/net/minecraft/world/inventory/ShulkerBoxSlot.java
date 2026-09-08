package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxSlot extends Slot {
   public ShulkerBoxSlot(Container var1, int var2, int var3, int var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean mayPlace(ItemStack var1) {
      return â˜ƒ.getItem().canFitInsideContainerItems();
   }
}
