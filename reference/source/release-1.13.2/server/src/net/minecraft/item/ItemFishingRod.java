package net.minecraft.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemFishingRod extends Item {
   public ItemFishingRod(Item.Properties var1) {
      super(☃);
      this.func_185043_a(new ResourceLocation("cast"), (var0, var1x, var2) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            boolean ☃ = var2.func_184614_ca() == var0;
            boolean ☃x = var2.func_184592_cb() == var0;
            if (var2.func_184614_ca().func_77973_b() instanceof ItemFishingRod) {
               ☃x = false;
            }

            return (☃ || ☃x) && var2 instanceof EntityPlayer && ((EntityPlayer)var2).field_71104_cf != null ? 1.0F : 0.0F;
         }
      });
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.field_71104_cf != null) {
         int ☃x = ☃.field_71104_cf.func_146034_e(☃);
         ☃.func_77972_a(☃x, ☃);
         ☃.func_184609_a(☃);
         ☃.func_184148_a(
            null,
            ☃.field_70165_t,
            ☃.field_70163_u,
            ☃.field_70161_v,
            SoundEvents.field_193780_J,
            SoundCategory.NEUTRAL,
            1.0F,
            0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F)
         );
      } else {
         ☃.func_184148_a(
            null,
            ☃.field_70165_t,
            ☃.field_70163_u,
            ☃.field_70161_v,
            SoundEvents.field_187612_G,
            SoundCategory.NEUTRAL,
            0.5F,
            0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F)
         );
         if (!☃.field_72995_K) {
            EntityFishHook ☃ = new EntityFishHook(☃, ☃);
            int ☃x = EnchantmentHelper.func_191528_c(☃);
            if (☃x > 0) {
               ☃.func_191516_a(☃x);
            }

            int ☃ = EnchantmentHelper.func_191529_b(☃);
            if (☃ > 0) {
               ☃.func_191517_b(☃);
            }

            ☃.func_72838_d(☃);
         }

         ☃.func_184609_a(☃);
         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      }

      return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
   }

   @Override
   public int func_77619_b() {
      return 1;
   }
}
