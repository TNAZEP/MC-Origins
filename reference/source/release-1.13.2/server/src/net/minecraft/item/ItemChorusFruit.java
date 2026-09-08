package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemChorusFruit extends ItemFood {
   public ItemChorusFruit(int var1, float var2, Item.Properties var3) {
      super(☃, ☃, false, ☃);
   }

   @Override
   public ItemStack func_77654_b(ItemStack var1, World var2, EntityLivingBase var3) {
      ItemStack ☃ = super.func_77654_b(☃, ☃, ☃);
      if (!☃.field_72995_K) {
         double ☃x = ☃.field_70165_t;
         double ☃xx = ☃.field_70163_u;
         double ☃xxx = ☃.field_70161_v;

         for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
            double ☃xxxxx = ☃.field_70165_t + (☃.func_70681_au().nextDouble() - 0.5) * 16.0;
            double ☃xxxxxx = MathHelper.func_151237_a(☃.field_70163_u + (double)(☃.func_70681_au().nextInt(16) - 8), 0.0, (double)(☃.func_72940_L() - 1));
            double ☃xxxxxxx = ☃.field_70161_v + (☃.func_70681_au().nextDouble() - 0.5) * 16.0;
            if (☃.func_184218_aH()) {
               ☃.func_184210_p();
            }

            if (☃.func_184595_k(☃xxxxx, ☃xxxxxx, ☃xxxxxxx)) {
               ☃.func_184148_a(null, ☃x, ☃xx, ☃xxx, SoundEvents.field_187544_ad, SoundCategory.PLAYERS, 1.0F, 1.0F);
               ☃.func_184185_a(SoundEvents.field_187544_ad, 1.0F, 1.0F);
               break;
            }
         }

         if (☃ instanceof EntityPlayer) {
            ((EntityPlayer)☃).func_184811_cZ().func_185145_a(this, 20);
         }
      }

      return ☃;
   }
}
