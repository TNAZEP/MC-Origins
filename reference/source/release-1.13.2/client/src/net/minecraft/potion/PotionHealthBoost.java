package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;

public class PotionHealthBoost extends Potion {
   public PotionHealthBoost(boolean var1, int var2) {
      super(☃, ☃);
   }

   @Override
   public void func_111187_a(EntityLivingBase var1, AbstractAttributeMap var2, int var3) {
      super.func_111187_a(☃, ☃, ☃);
      if (☃.func_110143_aJ() > ☃.func_110138_aP()) {
         ☃.func_70606_j(☃.func_110138_aP());
      }
   }
}
