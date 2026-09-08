package net.minecraft.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;

public class ItemArmorDyeable extends ItemArmor {
   public ItemArmorDyeable(IArmorMaterial var1, EntityEquipmentSlot var2, Item.Properties var3) {
      super(☃, ☃, ☃);
   }

   public boolean func_200883_f_(ItemStack var1) {
      NBTTagCompound ☃ = ☃.func_179543_a("display");
      return ☃ != null && ☃.func_150297_b("color", 99);
   }

   public int func_200886_f(ItemStack var1) {
      NBTTagCompound ☃ = ☃.func_179543_a("display");
      return ☃ != null && ☃.func_150297_b("color", 99) ? ☃.func_74762_e("color") : 10511680;
   }

   public void func_200884_g(ItemStack var1) {
      NBTTagCompound ☃ = ☃.func_179543_a("display");
      if (☃ != null && ☃.func_74764_b("color")) {
         ☃.func_82580_o("color");
      }
   }

   public void func_200885_a(ItemStack var1, int var2) {
      ☃.func_190925_c("display").func_74768_a("color", ☃);
   }
}
