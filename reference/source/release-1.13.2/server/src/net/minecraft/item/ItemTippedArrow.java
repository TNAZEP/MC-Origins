package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class ItemTippedArrow extends ItemArrow {
   public ItemTippedArrow(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EntityArrow func_200887_a(World var1, ItemStack var2, EntityLivingBase var3) {
      EntityTippedArrow ☃ = new EntityTippedArrow(☃, ☃);
      ☃.func_184555_a(☃);
      return ☃;
   }

   @Override
   public void func_150895_a(ItemGroup var1, NonNullList<ItemStack> var2) {
      if (this.func_194125_a(☃)) {
         for(PotionType ☃ : IRegistry.field_212621_j) {
            if (!☃.func_185170_a().isEmpty()) {
               ☃.add(PotionUtils.func_185188_a(new ItemStack(this), ☃));
            }
         }
      }
   }

   @Override
   public String func_77667_c(ItemStack var1) {
      return PotionUtils.func_185191_c(☃).func_185174_b(this.func_77658_a() + ".effect.");
   }
}
