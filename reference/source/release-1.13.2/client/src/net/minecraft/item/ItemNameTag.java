package net.minecraft.item;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class ItemNameTag extends Item {
   public ItemNameTag(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_111207_a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3, EnumHand var4) {
      if (☃.func_82837_s() && !(☃ instanceof EntityPlayer)) {
         ☃.func_200203_b(☃.func_200301_q());
         if (☃ instanceof EntityLiving) {
            ((EntityLiving)☃).func_110163_bv();
         }

         ☃.func_190918_g(1);
         return true;
      } else {
         return false;
      }
   }
}
