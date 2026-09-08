package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {
   public ItemBucketMilk(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ItemStack func_77654_b(ItemStack var1, World var2, EntityLivingBase var3) {
      if (☃ instanceof EntityPlayerMP) {
         EntityPlayerMP ☃ = (EntityPlayerMP)☃;
         CriteriaTriggers.field_193138_y.func_193148_a(☃, ☃);
         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      }

      if (☃ instanceof EntityPlayer && !((EntityPlayer)☃).field_71075_bZ.field_75098_d) {
         ☃.func_190918_g(1);
      }

      if (!☃.field_72995_K) {
         ☃.func_195061_cb();
      }

      return ☃.func_190926_b() ? new ItemStack(Items.field_151133_ar) : ☃;
   }

   @Override
   public int func_77626_a(ItemStack var1) {
      return 32;
   }

   @Override
   public EnumAction func_77661_b(ItemStack var1) {
      return EnumAction.DRINK;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ☃.func_184598_c(☃);
      return new ActionResult<>(EnumActionResult.SUCCESS, ☃.func_184586_b(☃));
   }
}
