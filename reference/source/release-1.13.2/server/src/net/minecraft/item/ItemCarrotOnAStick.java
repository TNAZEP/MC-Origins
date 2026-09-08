package net.minecraft.item;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCarrotOnAStick extends Item {
   public ItemCarrotOnAStick(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.field_72995_K) {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      } else {
         if (☃.func_184218_aH() && ☃.func_184187_bx() instanceof EntityPig) {
            EntityPig ☃ = (EntityPig)☃.func_184187_bx();
            if (☃.func_77958_k() - ☃.func_77952_i() >= 7 && ☃.func_184762_da()) {
               ☃.func_77972_a(7, ☃);
               if (☃.func_190926_b()) {
                  ItemStack ☃x = new ItemStack(Items.field_151112_aM);
                  ☃x.func_77982_d(☃.func_77978_p());
                  return new ActionResult<>(EnumActionResult.SUCCESS, ☃x);
               }

               return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
            }
         }

         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      }
   }
}
