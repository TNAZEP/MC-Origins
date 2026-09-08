package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemWritableBook extends Item {
   public ItemWritableBook(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      ☃.func_184814_a(☃, ☃);
      ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
   }

   public static boolean func_150930_a(@Nullable NBTTagCompound var0) {
      if (☃ == null) {
         return false;
      } else if (!☃.func_150297_b("pages", 9)) {
         return false;
      } else {
         NBTTagList ☃ = ☃.func_150295_c("pages", 8);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            String ☃xx = ☃.func_150307_f(☃x);
            if (☃xx.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}
