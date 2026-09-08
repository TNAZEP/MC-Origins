package net.minecraft.item;

import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemExpBottle extends Item {
   public ItemExpBottle(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_77636_d(ItemStack var1) {
      return true;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (!☃.field_71075_bZ.field_75098_d) {
         ☃.func_190918_g(1);
      }

      ☃.func_184148_a(
         null,
         ☃.field_70165_t,
         ☃.field_70163_u,
         ☃.field_70161_v,
         SoundEvents.field_187601_be,
         SoundCategory.NEUTRAL,
         0.5F,
         0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F)
      );
      if (!☃.field_72995_K) {
         EntityExpBottle ☃ = new EntityExpBottle(☃, ☃);
         ☃.func_184538_a(☃, ☃.field_70125_A, ☃.field_70177_z, -20.0F, 0.7F, 1.0F);
         ☃.func_72838_d(☃);
      }

      ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
   }
}
