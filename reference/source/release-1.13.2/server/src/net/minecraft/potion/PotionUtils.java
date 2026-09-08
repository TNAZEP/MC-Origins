package net.minecraft.potion;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class PotionUtils {
   public static List<PotionEffect> func_185189_a(ItemStack var0) {
      return func_185185_a(☃.func_77978_p());
   }

   public static List<PotionEffect> func_185186_a(PotionType var0, Collection<PotionEffect> var1) {
      List<PotionEffect> ☃ = Lists.<PotionEffect>newArrayList();
      ☃.addAll(☃.func_185170_a());
      ☃.addAll(☃);
      return ☃;
   }

   public static List<PotionEffect> func_185185_a(@Nullable NBTTagCompound var0) {
      List<PotionEffect> ☃ = Lists.<PotionEffect>newArrayList();
      ☃.addAll(func_185187_c(☃).func_185170_a());
      func_185193_a(☃, ☃);
      return ☃;
   }

   public static List<PotionEffect> func_185190_b(ItemStack var0) {
      return func_185192_b(☃.func_77978_p());
   }

   public static List<PotionEffect> func_185192_b(@Nullable NBTTagCompound var0) {
      List<PotionEffect> ☃ = Lists.<PotionEffect>newArrayList();
      func_185193_a(☃, ☃);
      return ☃;
   }

   public static void func_185193_a(@Nullable NBTTagCompound var0, List<PotionEffect> var1) {
      if (☃ != null && ☃.func_150297_b("CustomPotionEffects", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("CustomPotionEffects", 10);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
            PotionEffect ☃xxx = PotionEffect.func_82722_b(☃xx);
            if (☃xxx != null) {
               ☃.add(☃xxx);
            }
         }
      }
   }

   public static int func_190932_c(ItemStack var0) {
      NBTTagCompound ☃ = ☃.func_77978_p();
      if (☃ != null && ☃.func_150297_b("CustomPotionColor", 99)) {
         return ☃.func_74762_e("CustomPotionColor");
      } else {
         return func_185191_c(☃) == PotionTypes.field_185229_a ? 16253176 : func_185181_a(func_185189_a(☃));
      }
   }

   public static int func_185183_a(PotionType var0) {
      return ☃ == PotionTypes.field_185229_a ? 16253176 : func_185181_a(☃.func_185170_a());
   }

   public static int func_185181_a(Collection<PotionEffect> var0) {
      int ☃ = 3694022;
      if (☃.isEmpty()) {
         return 3694022;
      } else {
         float ☃ = 0.0F;
         float ☃x = 0.0F;
         float ☃xx = 0.0F;
         int ☃xxx = 0;

         for(PotionEffect ☃xxxx : ☃) {
            if (☃xxxx.func_188418_e()) {
               int ☃xxxxx = ☃xxxx.func_188419_a().func_76401_j();
               int ☃xxxxxx = ☃xxxx.func_76458_c() + 1;
               ☃ += (float)(☃xxxxxx * (☃xxxxx >> 16 & 0xFF)) / 255.0F;
               ☃x += (float)(☃xxxxxx * (☃xxxxx >> 8 & 0xFF)) / 255.0F;
               ☃xx += (float)(☃xxxxxx * (☃xxxxx >> 0 & 0xFF)) / 255.0F;
               ☃xxx += ☃xxxxxx;
            }
         }

         if (☃xxx == 0) {
            return 0;
         } else {
            ☃ = ☃ / (float)☃xxx * 255.0F;
            ☃x = ☃x / (float)☃xxx * 255.0F;
            ☃xx = ☃xx / (float)☃xxx * 255.0F;
            return (int)☃ << 16 | (int)☃x << 8 | (int)☃xx;
         }
      }
   }

   public static PotionType func_185191_c(ItemStack var0) {
      return func_185187_c(☃.func_77978_p());
   }

   public static PotionType func_185187_c(@Nullable NBTTagCompound var0) {
      return ☃ == null ? PotionTypes.field_185229_a : PotionType.func_185168_a(☃.func_74779_i("Potion"));
   }

   public static ItemStack func_185188_a(ItemStack var0, PotionType var1) {
      ResourceLocation ☃ = IRegistry.field_212621_j.func_177774_c(☃);
      if (☃ == PotionTypes.field_185229_a) {
         ☃.func_196083_e("Potion");
      } else {
         ☃.func_196082_o().func_74778_a("Potion", ☃.toString());
      }

      return ☃;
   }

   public static ItemStack func_185184_a(ItemStack var0, Collection<PotionEffect> var1) {
      if (☃.isEmpty()) {
         return ☃;
      } else {
         NBTTagCompound ☃ = ☃.func_196082_o();
         NBTTagList ☃x = ☃.func_150295_c("CustomPotionEffects", 9);

         for(PotionEffect ☃xx : ☃) {
            ☃x.add((INBTBase)☃xx.func_82719_a(new NBTTagCompound()));
         }

         ☃.func_74782_a("CustomPotionEffects", ☃x);
         return ☃;
      }
   }
}
