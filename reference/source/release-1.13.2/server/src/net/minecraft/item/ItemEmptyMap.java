package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemEmptyMap extends ItemMapBase {
   public ItemEmptyMap(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ItemMap.func_195952_a(☃, MathHelper.func_76128_c(☃.field_70165_t), MathHelper.func_76128_c(☃.field_70161_v), (byte)0, true, false);
      ItemStack ☃x = ☃.func_184586_b(☃);
      ☃x.func_190918_g(1);
      if (☃x.func_190926_b()) {
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      } else {
         if (!☃.field_71071_by.func_70441_a(☃.func_77946_l())) {
            ☃.func_71019_a(☃, false);
         }

         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃x);
      }
   }
}
