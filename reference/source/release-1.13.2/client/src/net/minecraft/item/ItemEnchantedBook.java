package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemEnchantedBook extends Item {
   public ItemEnchantedBook(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_77636_d(ItemStack var1) {
      return true;
   }

   @Override
   public boolean func_77616_k(ItemStack var1) {
      return false;
   }

   public static NBTTagList func_92110_g(ItemStack var0) {
      NBTTagCompound ☃ = ☃.func_77978_p();
      return ☃ != null ? ☃.func_150295_c("StoredEnchantments", 10) : new NBTTagList();
   }

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      super.func_77624_a(☃, ☃, ☃, ☃);
      NBTTagList ☃ = func_92110_g(☃);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
         Enchantment ☃xxx = IRegistry.field_212628_q.func_212608_b(ResourceLocation.func_208304_a(☃xx.func_74779_i("id")));
         if (☃xxx != null) {
            ☃.add(☃xxx.func_200305_d(☃xx.func_74762_e("lvl")));
         }
      }
   }

   public static void func_92115_a(ItemStack var0, EnchantmentData var1) {
      NBTTagList ☃ = func_92110_g(☃);
      boolean ☃x = true;
      ResourceLocation ☃xx = IRegistry.field_212628_q.func_177774_c(☃.field_76302_b);

      for(int ☃xxx = 0; ☃xxx < ☃.size(); ++☃xxx) {
         NBTTagCompound ☃xxxx = ☃.func_150305_b(☃xxx);
         ResourceLocation ☃xxxxx = ResourceLocation.func_208304_a(☃xxxx.func_74779_i("id"));
         if (☃xxxxx != null && ☃xxxxx.equals(☃xx)) {
            if (☃xxxx.func_74762_e("lvl") < ☃.field_76303_c) {
               ☃xxxx.func_74777_a("lvl", (short)☃.field_76303_c);
            }

            ☃x = false;
            break;
         }
      }

      if (☃x) {
         NBTTagCompound ☃xxx = new NBTTagCompound();
         ☃xxx.func_74778_a("id", String.valueOf(☃xx));
         ☃xxx.func_74777_a("lvl", (short)☃.field_76303_c);
         ☃.add((INBTBase)☃xxx);
      }

      ☃.func_196082_o().func_74782_a("StoredEnchantments", ☃);
   }

   public static ItemStack func_92111_a(EnchantmentData var0) {
      ItemStack ☃ = new ItemStack(Items.field_151134_bR);
      func_92115_a(☃, ☃);
      return ☃;
   }

   @Override
   public void func_150895_a(ItemGroup var1, NonNullList<ItemStack> var2) {
      if (☃ == ItemGroup.field_78027_g) {
         for(Enchantment ☃ : IRegistry.field_212628_q) {
            if (☃.field_77351_y != null) {
               for(int ☃x = ☃.func_77319_d(); ☃x <= ☃.func_77325_b(); ++☃x) {
                  ☃.add(func_92111_a(new EnchantmentData(☃, ☃x)));
               }
            }
         }
      } else if (☃.func_111225_m().length != 0) {
         for(Enchantment ☃ : IRegistry.field_212628_q) {
            if (☃.func_111226_a(☃.field_77351_y)) {
               ☃.add(func_92111_a(new EnchantmentData(☃, ☃.func_77325_b())));
            }
         }
      }
   }
}
