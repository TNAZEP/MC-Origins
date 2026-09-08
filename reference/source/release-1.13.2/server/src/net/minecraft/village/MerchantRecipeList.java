package net.minecraft.village;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
   public MerchantRecipeList() {
   }

   public MerchantRecipeList(NBTTagCompound var1) {
      this.func_77201_a(☃);
   }

   @Nullable
   public MerchantRecipe func_77203_a(ItemStack var1, ItemStack var2, int var3) {
      if (☃ > 0 && ☃ < this.size()) {
         MerchantRecipe ☃ = (MerchantRecipe)this.get(☃);
         return !this.func_181078_a(☃, ☃.func_77394_a())
               || (!☃.func_190926_b() || ☃.func_77398_c()) && (!☃.func_77398_c() || !this.func_181078_a(☃, ☃.func_77396_b()))
               || ☃.func_190916_E() < ☃.func_77394_a().func_190916_E()
               || ☃.func_77398_c() && ☃.func_190916_E() < ☃.func_77396_b().func_190916_E()
            ? null
            : ☃;
      } else {
         for(int ☃ = 0; ☃ < this.size(); ++☃) {
            MerchantRecipe ☃x = (MerchantRecipe)this.get(☃);
            if (this.func_181078_a(☃, ☃x.func_77394_a())
               && ☃.func_190916_E() >= ☃x.func_77394_a().func_190916_E()
               && (
                  !☃x.func_77398_c() && ☃.func_190926_b()
                     || ☃x.func_77398_c() && this.func_181078_a(☃, ☃x.func_77396_b()) && ☃.func_190916_E() >= ☃x.func_77396_b().func_190916_E()
               )) {
               return ☃x;
            }
         }

         return null;
      }
   }

   private boolean func_181078_a(ItemStack var1, ItemStack var2) {
      ItemStack ☃ = ☃.func_77946_l();
      if (☃.func_77973_b().func_77645_m()) {
         ☃.func_196085_b(☃.func_77952_i());
      }

      return ItemStack.func_179545_c(☃, ☃) && (!☃.func_77942_o() || ☃.func_77942_o() && NBTUtil.func_181123_a(☃.func_77978_p(), ☃.func_77978_p(), false));
   }

   public void func_151391_a(PacketBuffer var1) {
      ☃.writeByte((byte)(this.size() & 0xFF));

      for(int ☃ = 0; ☃ < this.size(); ++☃) {
         MerchantRecipe ☃x = (MerchantRecipe)this.get(☃);
         ☃.func_150788_a(☃x.func_77394_a());
         ☃.func_150788_a(☃x.func_77397_d());
         ItemStack ☃xx = ☃x.func_77396_b();
         ☃.writeBoolean(!☃xx.func_190926_b());
         if (!☃xx.func_190926_b()) {
            ☃.func_150788_a(☃xx);
         }

         ☃.writeBoolean(☃x.func_82784_g());
         ☃.writeInt(☃x.func_180321_e());
         ☃.writeInt(☃x.func_180320_f());
      }
   }

   public void func_77201_a(NBTTagCompound var1) {
      NBTTagList ☃ = ☃.func_150295_c("Recipes", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
         this.add(new MerchantRecipe(☃xx));
      }
   }

   public NBTTagCompound func_77202_a() {
      NBTTagCompound ☃ = new NBTTagCompound();
      NBTTagList ☃x = new NBTTagList();

      for(int ☃xx = 0; ☃xx < this.size(); ++☃xx) {
         MerchantRecipe ☃xxx = (MerchantRecipe)this.get(☃xx);
         ☃x.add((INBTBase)☃xxx.func_77395_g());
      }

      ☃.func_74782_a("Recipes", ☃x);
      return ☃;
   }
}
