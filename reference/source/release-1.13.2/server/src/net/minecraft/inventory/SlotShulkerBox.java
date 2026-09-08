package net.minecraft.inventory;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.item.ItemStack;

public class SlotShulkerBox extends Slot {
   public SlotShulkerBox(IInventory var1, int var2, int var3, int var4) {
      super(☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_75214_a(ItemStack var1) {
      return !(Block.func_149634_a(☃.func_77973_b()) instanceof BlockShulkerBox);
   }
}
