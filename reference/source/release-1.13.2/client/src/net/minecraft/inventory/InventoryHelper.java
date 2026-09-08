package net.minecraft.inventory;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper {
   private static final Random field_180177_a = new Random();

   public static void func_180175_a(World var0, BlockPos var1, IInventory var2) {
      func_180174_a(☃, (double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p(), ☃);
   }

   public static void func_180176_a(World var0, Entity var1, IInventory var2) {
      func_180174_a(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃);
   }

   private static void func_180174_a(World var0, double var1, double var3, double var5, IInventory var7) {
      for(int ☃ = 0; ☃ < ☃.func_70302_i_(); ++☃) {
         ItemStack ☃x = ☃.func_70301_a(☃);
         if (!☃x.func_190926_b()) {
            func_180173_a(☃, ☃, ☃, ☃, ☃x);
         }
      }
   }

   public static void func_180173_a(World var0, double var1, double var3, double var5, ItemStack var7) {
      float ☃ = 0.75F;
      float ☃x = 0.125F;
      float ☃xx = field_180177_a.nextFloat() * 0.75F + 0.125F;
      float ☃xxx = field_180177_a.nextFloat() * 0.75F;
      float ☃xxxx = field_180177_a.nextFloat() * 0.75F + 0.125F;

      while(!☃.func_190926_b()) {
         EntityItem ☃xxxxx = new EntityItem(☃, ☃ + (double)☃xx, ☃ + (double)☃xxx, ☃ + (double)☃xxxx, ☃.func_77979_a(field_180177_a.nextInt(21) + 10));
         float ☃xxxxxx = 0.05F;
         ☃xxxxx.field_70159_w = field_180177_a.nextGaussian() * 0.05F;
         ☃xxxxx.field_70181_x = field_180177_a.nextGaussian() * 0.05F + 0.2F;
         ☃xxxxx.field_70179_y = field_180177_a.nextGaussian() * 0.05F;
         ☃.func_72838_d(☃xxxxx);
      }
   }
}
