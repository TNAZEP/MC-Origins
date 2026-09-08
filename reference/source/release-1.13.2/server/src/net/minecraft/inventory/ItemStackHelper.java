package net.minecraft.inventory;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class ItemStackHelper {
   public static ItemStack func_188382_a(List<ItemStack> var0, int var1, int var2) {
      return ☃ >= 0 && ☃ < ☃.size() && !((ItemStack)☃.get(☃)).func_190926_b() && ☃ > 0 ? ((ItemStack)☃.get(☃)).func_77979_a(☃) : ItemStack.field_190927_a;
   }

   public static ItemStack func_188383_a(List<ItemStack> var0, int var1) {
      return ☃ >= 0 && ☃ < ☃.size() ? (ItemStack)☃.set(☃, ItemStack.field_190927_a) : ItemStack.field_190927_a;
   }

   public static NBTTagCompound func_191282_a(NBTTagCompound var0, NonNullList<ItemStack> var1) {
      return func_191281_a(☃, ☃, true);
   }

   public static NBTTagCompound func_191281_a(NBTTagCompound var0, NonNullList<ItemStack> var1, boolean var2) {
      NBTTagList ☃ = new NBTTagList();

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ItemStack ☃xx = ☃.get(☃x);
         if (!☃xx.func_190926_b()) {
            NBTTagCompound ☃xxx = new NBTTagCompound();
            ☃xxx.func_74774_a("Slot", (byte)☃x);
            ☃xx.func_77955_b(☃xxx);
            ☃.add((INBTBase)☃xxx);
         }
      }

      if (!☃.isEmpty() || ☃) {
         ☃.func_74782_a("Items", ☃);
      }

      return ☃;
   }

   public static void func_191283_b(NBTTagCompound var0, NonNullList<ItemStack> var1) {
      NBTTagList ☃ = ☃.func_150295_c("Items", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
         int ☃xxx = ☃xx.func_74771_c("Slot") & 255;
         if (☃xxx >= 0 && ☃xxx < ☃.size()) {
            ☃.set(☃xxx, ItemStack.func_199557_a(☃xx));
         }
      }
   }
}
