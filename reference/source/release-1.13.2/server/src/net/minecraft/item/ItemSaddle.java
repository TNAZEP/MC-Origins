package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

public class ItemSaddle extends Item {
   public ItemSaddle(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_111207_a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3, EnumHand var4) {
      if (☃ instanceof EntityPig) {
         EntityPig ☃ = (EntityPig)☃;
         if (!☃.func_70901_n() && !☃.func_70631_g_()) {
            ☃.func_70900_e(true);
            ☃.field_70170_p.func_184148_a(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, SoundEvents.field_187706_dO, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            ☃.func_190918_g(1);
         }

         return true;
      } else {
         return false;
      }
   }
}
