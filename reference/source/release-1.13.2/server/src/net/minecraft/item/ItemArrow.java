package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.world.World;

public class ItemArrow extends Item {
   public ItemArrow(Item.Properties var1) {
      super(☃);
   }

   public EntityArrow func_200887_a(World var1, ItemStack var2, EntityLivingBase var3) {
      EntityTippedArrow ☃ = new EntityTippedArrow(☃, ☃);
      ☃.func_184555_a(☃);
      return ☃;
   }
}
