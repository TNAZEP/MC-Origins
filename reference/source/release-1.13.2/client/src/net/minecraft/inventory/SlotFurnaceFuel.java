package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   public SlotFurnaceFuel(IInventory var1, int var2, int var3, int var4) {
      super(☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_75214_a(ItemStack var1) {
      return TileEntityFurnace.func_145954_b(☃) || func_178173_c_(☃);
   }

   @Override
   public int func_178170_b(ItemStack var1) {
      return func_178173_c_(☃) ? 1 : super.func_178170_b(☃);
   }

   public static boolean func_178173_c_(ItemStack var0) {
      return ☃.func_77973_b() == Items.field_151133_ar;
   }
}
