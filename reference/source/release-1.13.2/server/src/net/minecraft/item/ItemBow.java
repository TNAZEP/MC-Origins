package net.minecraft.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBow extends Item {
   public ItemBow(Item.Properties var1) {
      super(☃);
      this.func_185043_a(new ResourceLocation("pull"), (var0, var1x, var2) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            return var2.func_184607_cu().func_77973_b() != Items.field_151031_f ? 0.0F : (float)(var0.func_77988_m() - var2.func_184605_cv()) / 20.0F;
         }
      });
      this.func_185043_a(
         new ResourceLocation("pulling"), (var0, var1x, var2) -> var2 != null && var2.func_184587_cr() && var2.func_184607_cu() == var0 ? 1.0F : 0.0F
      );
   }

   private ItemStack func_185060_a(EntityPlayer var1) {
      if (this.func_185058_h_(☃.func_184586_b(EnumHand.OFF_HAND))) {
         return ☃.func_184586_b(EnumHand.OFF_HAND);
      } else if (this.func_185058_h_(☃.func_184586_b(EnumHand.MAIN_HAND))) {
         return ☃.func_184586_b(EnumHand.MAIN_HAND);
      } else {
         for(int ☃ = 0; ☃ < ☃.field_71071_by.func_70302_i_(); ++☃) {
            ItemStack ☃x = ☃.field_71071_by.func_70301_a(☃);
            if (this.func_185058_h_(☃x)) {
               return ☃x;
            }
         }

         return ItemStack.field_190927_a;
      }
   }

   protected boolean func_185058_h_(ItemStack var1) {
      return ☃.func_77973_b() instanceof ItemArrow;
   }

   @Override
   public void func_77615_a(ItemStack var1, World var2, EntityLivingBase var3, int var4) {
      if (☃ instanceof EntityPlayer) {
         EntityPlayer ☃ = (EntityPlayer)☃;
         boolean ☃x = ☃.field_71075_bZ.field_75098_d || EnchantmentHelper.func_77506_a(Enchantments.field_185312_x, ☃) > 0;
         ItemStack ☃xx = this.func_185060_a(☃);
         if (!☃xx.func_190926_b() || ☃x) {
            if (☃xx.func_190926_b()) {
               ☃xx = new ItemStack(Items.field_151032_g);
            }

            int ☃xxx = this.func_77626_a(☃) - ☃;
            float ☃xxxx = func_185059_b(☃xxx);
            if (!((double)☃xxxx < 0.1)) {
               boolean ☃xxxxx = ☃x && ☃xx.func_77973_b() == Items.field_151032_g;
               if (!☃.field_72995_K) {
                  ItemArrow ☃xxxxxx = (ItemArrow)(☃xx.func_77973_b() instanceof ItemArrow ? ☃xx.func_77973_b() : Items.field_151032_g);
                  EntityArrow ☃xxxxxxx = ☃xxxxxx.func_200887_a(☃, ☃xx, ☃);
                  ☃xxxxxxx.func_184547_a(☃, ☃.field_70125_A, ☃.field_70177_z, 0.0F, ☃xxxx * 3.0F, 1.0F);
                  if (☃xxxx == 1.0F) {
                     ☃xxxxxxx.func_70243_d(true);
                  }

                  int ☃xxxxxx = EnchantmentHelper.func_77506_a(Enchantments.field_185309_u, ☃);
                  if (☃xxxxxx > 0) {
                     ☃xxxxxxx.func_70239_b(☃xxxxxxx.func_70242_d() + (double)☃xxxxxx * 0.5 + 0.5);
                  }

                  int ☃xxxxxx = EnchantmentHelper.func_77506_a(Enchantments.field_185310_v, ☃);
                  if (☃xxxxxx > 0) {
                     ☃xxxxxxx.func_70240_a(☃xxxxxx);
                  }

                  if (EnchantmentHelper.func_77506_a(Enchantments.field_185311_w, ☃) > 0) {
                     ☃xxxxxxx.func_70015_d(100);
                  }

                  ☃.func_77972_a(1, ☃);
                  if (☃xxxxx || ☃.field_71075_bZ.field_75098_d && (☃xx.func_77973_b() == Items.field_185166_h || ☃xx.func_77973_b() == Items.field_185167_i)) {
                     ☃xxxxxxx.field_70251_a = EntityArrow.PickupStatus.CREATIVE_ONLY;
                  }

                  ☃.func_72838_d(☃xxxxxxx);
               }

               ☃.func_184148_a(
                  null,
                  ☃.field_70165_t,
                  ☃.field_70163_u,
                  ☃.field_70161_v,
                  SoundEvents.field_187737_v,
                  SoundCategory.PLAYERS,
                  1.0F,
                  1.0F / (field_77697_d.nextFloat() * 0.4F + 1.2F) + ☃xxxx * 0.5F
               );
               if (!☃xxxxx && !☃.field_71075_bZ.field_75098_d) {
                  ☃xx.func_190918_g(1);
                  if (☃xx.func_190926_b()) {
                     ☃.field_71071_by.func_184437_d(☃xx);
                  }
               }

               ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
            }
         }
      }
   }

   public static float func_185059_b(int var0) {
      float ☃ = (float)☃ / 20.0F;
      ☃ = (☃ * ☃ + ☃ * 2.0F) / 3.0F;
      if (☃ > 1.0F) {
         ☃ = 1.0F;
      }

      return ☃;
   }

   @Override
   public int func_77626_a(ItemStack var1) {
      return 72000;
   }

   @Override
   public EnumAction func_77661_b(ItemStack var1) {
      return EnumAction.BOW;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      boolean ☃x = !this.func_185060_a(☃).func_190926_b();
      if (☃.field_71075_bZ.field_75098_d || ☃x) {
         ☃.func_184598_c(☃);
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      } else {
         return ☃x ? new ActionResult<>(EnumActionResult.PASS, ☃) : new ActionResult<>(EnumActionResult.FAIL, ☃);
      }
   }

   @Override
   public int func_77619_b() {
      return 1;
   }
}
