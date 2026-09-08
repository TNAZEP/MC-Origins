package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentHelper {
   public static int func_77506_a(Enchantment var0, ItemStack var1) {
      if (☃.func_190926_b()) {
         return 0;
      } else {
         ResourceLocation ☃ = IRegistry.field_212628_q.func_177774_c(☃);
         NBTTagList ☃x = ☃.func_77986_q();

         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            NBTTagCompound ☃xxx = ☃x.func_150305_b(☃xx);
            ResourceLocation ☃xxxx = ResourceLocation.func_208304_a(☃xxx.func_74779_i("id"));
            if (☃xxxx != null && ☃xxxx.equals(☃)) {
               return ☃xxx.func_74762_e("lvl");
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> func_82781_a(ItemStack var0) {
      Map<Enchantment, Integer> ☃ = Maps.newLinkedHashMap();
      NBTTagList ☃x = ☃.func_77973_b() == Items.field_151134_bR ? ItemEnchantedBook.func_92110_g(☃) : ☃.func_77986_q();

      for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
         NBTTagCompound ☃xxx = ☃x.func_150305_b(☃xx);
         Enchantment ☃xxxx = IRegistry.field_212628_q.func_212608_b(ResourceLocation.func_208304_a(☃xxx.func_74779_i("id")));
         if (☃xxxx != null) {
            ☃.put(☃xxxx, ☃xxx.func_74762_e("lvl"));
         }
      }

      return ☃;
   }

   public static void func_82782_a(Map<Enchantment, Integer> var0, ItemStack var1) {
      NBTTagList ☃ = new NBTTagList();

      for(Entry<Enchantment, Integer> ☃x : ☃.entrySet()) {
         Enchantment ☃xx = (Enchantment)☃x.getKey();
         if (☃xx != null) {
            int ☃xxx = ☃x.getValue();
            NBTTagCompound ☃xxxx = new NBTTagCompound();
            ☃xxxx.func_74778_a("id", String.valueOf(IRegistry.field_212628_q.func_177774_c(☃xx)));
            ☃xxxx.func_74777_a("lvl", (short)☃xxx);
            ☃.add((INBTBase)☃xxxx);
            if (☃.func_77973_b() == Items.field_151134_bR) {
               ItemEnchantedBook.func_92115_a(☃, new EnchantmentData(☃xx, ☃xxx));
            }
         }
      }

      if (☃.isEmpty()) {
         ☃.func_196083_e("Enchantments");
      } else if (☃.func_77973_b() != Items.field_151134_bR) {
         ☃.func_77983_a("Enchantments", ☃);
      }
   }

   private static void func_77518_a(EnchantmentHelper.IEnchantmentVisitor var0, ItemStack var1) {
      if (!☃.func_190926_b()) {
         NBTTagList ☃ = ☃.func_77986_q();

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            String ☃xx = ☃.func_150305_b(☃x).func_74779_i("id");
            int ☃xxx = ☃.func_150305_b(☃x).func_74762_e("lvl");
            Enchantment ☃xxxx = IRegistry.field_212628_q.func_212608_b(ResourceLocation.func_208304_a(☃xx));
            if (☃xxxx != null) {
               ☃.accept(☃xxxx, ☃xxx);
            }
         }
      }
   }

   private static void func_77516_a(EnchantmentHelper.IEnchantmentVisitor var0, Iterable<ItemStack> var1) {
      for(ItemStack ☃ : ☃) {
         func_77518_a(☃, ☃);
      }
   }

   public static int func_77508_a(Iterable<ItemStack> var0, DamageSource var1) {
      MutableInt ☃ = new MutableInt();
      func_77516_a((var2x, var3) -> ☃.add(var2x.func_77318_a(var3, ☃)), ☃);
      return ☃.intValue();
   }

   public static float func_152377_a(ItemStack var0, CreatureAttribute var1) {
      MutableFloat ☃ = new MutableFloat();
      func_77518_a((var2x, var3) -> ☃.add(var2x.func_152376_a(var3, ☃)), ☃);
      return ☃.floatValue();
   }

   public static float func_191527_a(EntityLivingBase var0) {
      int ☃ = func_185284_a(Enchantments.field_191530_r, ☃);
      return ☃ > 0 ? EnchantmentSweepingEdge.func_191526_e(☃) : 0.0F;
   }

   public static void func_151384_a(EntityLivingBase var0, Entity var1) {
      EnchantmentHelper.IEnchantmentVisitor ☃ = (var2x, var3) -> var2x.func_151367_b(☃, ☃, var3);
      if (☃ != null) {
         func_77516_a(☃, ☃.func_184209_aF());
      }

      if (☃ instanceof EntityPlayer) {
         func_77518_a(☃, ☃.func_184614_ca());
      }
   }

   public static void func_151385_b(EntityLivingBase var0, Entity var1) {
      EnchantmentHelper.IEnchantmentVisitor ☃ = (var2x, var3) -> var2x.func_151368_a(☃, ☃, var3);
      if (☃ != null) {
         func_77516_a(☃, ☃.func_184209_aF());
      }

      if (☃ instanceof EntityPlayer) {
         func_77518_a(☃, ☃.func_184614_ca());
      }
   }

   public static int func_185284_a(Enchantment var0, EntityLivingBase var1) {
      Iterable<ItemStack> ☃ = ☃.func_185260_a(☃);
      if (☃ == null) {
         return 0;
      } else {
         int ☃ = 0;

         for(ItemStack ☃x : ☃) {
            int ☃xx = func_77506_a(☃, ☃x);
            if (☃xx > ☃) {
               ☃ = ☃xx;
            }
         }

         return ☃;
      }
   }

   public static int func_77501_a(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_180313_o, ☃);
   }

   public static int func_90036_a(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_77334_n, ☃);
   }

   public static int func_185292_c(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_185298_f, ☃);
   }

   public static int func_185294_d(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_185300_i, ☃);
   }

   public static int func_185293_e(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_185305_q, ☃);
   }

   public static int func_191529_b(ItemStack var0) {
      return func_77506_a(Enchantments.field_151370_z, ☃);
   }

   public static int func_191528_c(ItemStack var0) {
      return func_77506_a(Enchantments.field_151369_A, ☃);
   }

   public static int func_185283_h(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_185304_p, ☃);
   }

   public static boolean func_185287_i(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_185299_g, ☃) > 0;
   }

   public static boolean func_189869_j(EntityLivingBase var0) {
      return func_185284_a(Enchantments.field_185301_j, ☃) > 0;
   }

   public static boolean func_190938_b(ItemStack var0) {
      return func_77506_a(Enchantments.field_190941_k, ☃) > 0;
   }

   public static boolean func_190939_c(ItemStack var0) {
      return func_77506_a(Enchantments.field_190940_C, ☃) > 0;
   }

   public static int func_203191_f(ItemStack var0) {
      return func_77506_a(Enchantments.field_203193_C, ☃);
   }

   public static int func_203190_g(ItemStack var0) {
      return func_77506_a(Enchantments.field_203195_E, ☃);
   }

   public static boolean func_203192_h(ItemStack var0) {
      return func_77506_a(Enchantments.field_203196_F, ☃) > 0;
   }

   public static ItemStack func_92099_a(Enchantment var0, EntityLivingBase var1) {
      List<ItemStack> ☃ = ☃.func_185260_a(☃);
      if (☃.isEmpty()) {
         return ItemStack.field_190927_a;
      } else {
         List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();

         for(ItemStack ☃x : ☃) {
            if (!☃x.func_190926_b() && func_77506_a(☃, ☃x) > 0) {
               ☃.add(☃x);
            }
         }

         return ☃.isEmpty() ? ItemStack.field_190927_a : (ItemStack)☃.get(☃.func_70681_au().nextInt(☃.size()));
      }
   }

   public static int func_77514_a(Random var0, int var1, int var2, ItemStack var3) {
      Item ☃ = ☃.func_77973_b();
      int ☃x = ☃.func_77619_b();
      if (☃x <= 0) {
         return 0;
      } else {
         if (☃ > 15) {
            ☃ = 15;
         }

         int ☃ = ☃.nextInt(8) + 1 + (☃ >> 1) + ☃.nextInt(☃ + 1);
         if (☃ == 0) {
            return Math.max(☃ / 3, 1);
         } else {
            return ☃ == 1 ? ☃ * 2 / 3 + 1 : Math.max(☃, ☃ * 2);
         }
      }
   }

   public static ItemStack func_77504_a(Random var0, ItemStack var1, int var2, boolean var3) {
      List<EnchantmentData> ☃ = func_77513_b(☃, ☃, ☃, ☃);
      boolean ☃x = ☃.func_77973_b() == Items.field_151122_aG;
      if (☃x) {
         ☃ = new ItemStack(Items.field_151134_bR);
      }

      for(EnchantmentData ☃ : ☃) {
         if (☃x) {
            ItemEnchantedBook.func_92115_a(☃, ☃);
         } else {
            ☃.func_77966_a(☃.field_76302_b, ☃.field_76303_c);
         }
      }

      return ☃;
   }

   public static List<EnchantmentData> func_77513_b(Random var0, ItemStack var1, int var2, boolean var3) {
      List<EnchantmentData> ☃ = Lists.<EnchantmentData>newArrayList();
      Item ☃x = ☃.func_77973_b();
      int ☃xx = ☃x.func_77619_b();
      if (☃xx <= 0) {
         return ☃;
      } else {
         ☃ += 1 + ☃.nextInt(☃xx / 4 + 1) + ☃.nextInt(☃xx / 4 + 1);
         float ☃ = (☃.nextFloat() + ☃.nextFloat() - 1.0F) * 0.15F;
         ☃ = MathHelper.func_76125_a(Math.round((float)☃ + (float)☃ * ☃), 1, Integer.MAX_VALUE);
         List<EnchantmentData> ☃x = func_185291_a(☃, ☃, ☃);
         if (!☃x.isEmpty()) {
            ☃.add(WeightedRandom.func_76271_a(☃, ☃x));

            while(☃.nextInt(50) <= ☃) {
               func_185282_a(☃x, Util.func_184878_a(☃));
               if (☃x.isEmpty()) {
                  break;
               }

               ☃.add(WeightedRandom.func_76271_a(☃, ☃x));
               ☃ /= 2;
            }
         }

         return ☃;
      }
   }

   public static void func_185282_a(List<EnchantmentData> var0, EnchantmentData var1) {
      Iterator<EnchantmentData> ☃ = ☃.iterator();

      while(☃.hasNext()) {
         if (!☃.field_76302_b.func_191560_c(((EnchantmentData)☃.next()).field_76302_b)) {
            ☃.remove();
         }
      }
   }

   public static boolean func_201840_a(Collection<Enchantment> var0, Enchantment var1) {
      for(Enchantment ☃ : ☃) {
         if (!☃.func_191560_c(☃)) {
            return false;
         }
      }

      return true;
   }

   public static List<EnchantmentData> func_185291_a(int var0, ItemStack var1, boolean var2) {
      List<EnchantmentData> ☃ = Lists.<EnchantmentData>newArrayList();
      Item ☃x = ☃.func_77973_b();
      boolean ☃xx = ☃.func_77973_b() == Items.field_151122_aG;

      for(Enchantment ☃xxx : IRegistry.field_212628_q) {
         if ((!☃xxx.func_185261_e() || ☃) && (☃xxx.field_77351_y.func_77557_a(☃x) || ☃xx)) {
            for(int ☃xxxx = ☃xxx.func_77325_b(); ☃xxxx > ☃xxx.func_77319_d() - 1; --☃xxxx) {
               if (☃ >= ☃xxx.func_77321_a(☃xxxx) && ☃ <= ☃xxx.func_77317_b(☃xxxx)) {
                  ☃.add(new EnchantmentData(☃xxx, ☃xxxx));
                  break;
               }
            }
         }
      }

      return ☃;
   }

   @FunctionalInterface
   interface IEnchantmentVisitor {
      void accept(Enchantment var1, int var2);
   }
}
