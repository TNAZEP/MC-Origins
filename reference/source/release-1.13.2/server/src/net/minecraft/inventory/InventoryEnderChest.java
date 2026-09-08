package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryEnderChest extends InventoryBasic {
   private TileEntityEnderChest field_70488_a;

   public InventoryEnderChest() {
      super(new TextComponentTranslation("container.enderchest"), 27);
   }

   public void func_146031_a(TileEntityEnderChest var1) {
      this.field_70488_a = ☃;
   }

   public void func_70486_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < this.func_70302_i_(); ++☃) {
         this.func_70299_a(☃, ItemStack.field_190927_a);
      }

      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         int ☃xx = ☃x.func_74771_c("Slot") & 255;
         if (☃xx >= 0 && ☃xx < this.func_70302_i_()) {
            this.func_70299_a(☃xx, ItemStack.func_199557_a(☃x));
         }
      }
   }

   public NBTTagList func_70487_g() {
      NBTTagList ☃ = new NBTTagList();

      for(int ☃x = 0; ☃x < this.func_70302_i_(); ++☃x) {
         ItemStack ☃xx = this.func_70301_a(☃x);
         if (!☃xx.func_190926_b()) {
            NBTTagCompound ☃xxx = new NBTTagCompound();
            ☃xxx.func_74774_a("Slot", (byte)☃x);
            ☃xx.func_77955_b(☃xxx);
            ☃.add((INBTBase)☃xxx);
         }
      }

      return ☃;
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      return this.field_70488_a != null && !this.field_70488_a.func_145971_a(☃) ? false : super.func_70300_a(☃);
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
      if (this.field_70488_a != null) {
         this.field_70488_a.func_145969_a();
      }

      super.func_174889_b(☃);
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
      if (this.field_70488_a != null) {
         this.field_70488_a.func_145970_b();
      }

      super.func_174886_c(☃);
      this.field_70488_a = null;
   }
}
