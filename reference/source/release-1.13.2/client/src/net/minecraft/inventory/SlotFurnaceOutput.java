package net.minecraft.inventory;

import java.util.Map.Entry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SlotFurnaceOutput extends Slot {
   private final EntityPlayer field_75229_a;
   private int field_75228_b;

   public SlotFurnaceOutput(EntityPlayer var1, IInventory var2, int var3, int var4, int var5) {
      super(☃, ☃, ☃, ☃);
      this.field_75229_a = ☃;
   }

   @Override
   public boolean func_75214_a(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack func_75209_a(int var1) {
      if (this.func_75216_d()) {
         this.field_75228_b += Math.min(☃, this.func_75211_c().func_190916_E());
      }

      return super.func_75209_a(☃);
   }

   @Override
   public ItemStack func_190901_a(EntityPlayer var1, ItemStack var2) {
      this.func_75208_c(☃);
      super.func_190901_a(☃, ☃);
      return ☃;
   }

   @Override
   protected void func_75210_a(ItemStack var1, int var2) {
      this.field_75228_b += ☃;
      this.func_75208_c(☃);
   }

   @Override
   protected void func_75208_c(ItemStack var1) {
      ☃.func_77980_a(this.field_75229_a.field_70170_p, this.field_75229_a, this.field_75228_b);
      if (!this.field_75229_a.field_70170_p.field_72995_K) {
         for(Entry<ResourceLocation, Integer> ☃ : ((TileEntityFurnace)this.field_75224_c).func_203900_q().entrySet()) {
            FurnaceRecipe ☃xx = (FurnaceRecipe)this.field_75229_a.field_70170_p.func_199532_z().func_199517_a((ResourceLocation)☃.getKey());
            float ☃x;
            if (☃xx != null) {
               ☃x = ☃xx.func_201831_g();
            } else {
               ☃x = 0.0F;
            }

            int ☃x = ☃.getValue();
            if (☃x == 0.0F) {
               ☃x = 0;
            } else if (☃x < 1.0F) {
               int ☃x = MathHelper.func_76141_d((float)☃x * ☃x);
               if (☃x < MathHelper.func_76123_f((float)☃x * ☃x) && Math.random() < (double)((float)☃x * ☃x - (float)☃x)) {
                  ++☃x;
               }

               ☃x = ☃x;
            }

            while(☃x > 0) {
               int ☃x = EntityXPOrb.func_70527_a(☃x);
               ☃x -= ☃x;
               this.field_75229_a
                  .field_70170_p
                  .func_72838_d(
                     new EntityXPOrb(
                        this.field_75229_a.field_70170_p,
                        this.field_75229_a.field_70165_t,
                        this.field_75229_a.field_70163_u + 0.5,
                        this.field_75229_a.field_70161_v + 0.5,
                        ☃x
                     )
                  );
            }
         }

         ((IRecipeHolder)this.field_75224_c).func_201560_d(this.field_75229_a);
      }

      this.field_75228_b = 0;
   }
}
