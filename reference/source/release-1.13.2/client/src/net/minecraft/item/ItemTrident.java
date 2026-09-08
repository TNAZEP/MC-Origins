package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTrident;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemTrident extends Item {
   public ItemTrident(Item.Properties var1) {
      super(☃);
      this.func_185043_a(
         new ResourceLocation("throwing"), (var0, var1x, var2) -> var2 != null && var2.func_184587_cr() && var2.func_184607_cu() == var0 ? 1.0F : 0.0F
      );
   }

   @Override
   public boolean func_195938_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      return !☃.func_184812_l_();
   }

   @Override
   public EnumAction func_77661_b(ItemStack var1) {
      return EnumAction.SPEAR;
   }

   @Override
   public int func_77626_a(ItemStack var1) {
      return 72000;
   }

   @Override
   public boolean func_77636_d(ItemStack var1) {
      return false;
   }

   @Override
   public void func_77615_a(ItemStack var1, World var2, EntityLivingBase var3, int var4) {
      if (☃ instanceof EntityPlayer) {
         EntityPlayer ☃ = (EntityPlayer)☃;
         int ☃x = this.func_77626_a(☃) - ☃;
         if (☃x >= 10) {
            int ☃xx = EnchantmentHelper.func_203190_g(☃);
            if (☃xx <= 0 || ☃.func_70026_G()) {
               if (!☃.field_72995_K) {
                  ☃.func_77972_a(1, ☃);
                  if (☃xx == 0) {
                     EntityTrident ☃xxx = new EntityTrident(☃, ☃, ☃);
                     ☃xxx.func_184547_a(☃, ☃.field_70125_A, ☃.field_70177_z, 0.0F, 2.5F + (float)☃xx * 0.5F, 1.0F);
                     if (☃.field_71075_bZ.field_75098_d) {
                        ☃xxx.field_70251_a = EntityArrow.PickupStatus.CREATIVE_ONLY;
                     }

                     ☃.func_72838_d(☃xxx);
                     if (!☃.field_71075_bZ.field_75098_d) {
                        ☃.field_71071_by.func_184437_d(☃);
                     }
                  }
               }

               ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
               SoundEvent ☃xxx = SoundEvents.field_203274_ip;
               if (☃xx > 0) {
                  float ☃xxxx = ☃.field_70177_z;
                  float ☃xxxxx = ☃.field_70125_A;
                  float ☃xxxxxx = -MathHelper.func_76126_a(☃xxxx * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃xxxxx * (float) (Math.PI / 180.0));
                  float ☃xxxxxxx = -MathHelper.func_76126_a(☃xxxxx * (float) (Math.PI / 180.0));
                  float ☃xxxxxxxx = MathHelper.func_76134_b(☃xxxx * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃xxxxx * (float) (Math.PI / 180.0));
                  float ☃xxxxxxxxx = MathHelper.func_76129_c(☃xxxxxx * ☃xxxxxx + ☃xxxxxxx * ☃xxxxxxx + ☃xxxxxxxx * ☃xxxxxxxx);
                  float ☃xxxxxxxxxx = 3.0F * ((1.0F + (float)☃xx) / 4.0F);
                  ☃xxxxxx *= ☃xxxxxxxxxx / ☃xxxxxxxxx;
                  ☃xxxxxxx *= ☃xxxxxxxxxx / ☃xxxxxxxxx;
                  ☃xxxxxxxx *= ☃xxxxxxxxxx / ☃xxxxxxxxx;
                  ☃.func_70024_g((double)☃xxxxxx, (double)☃xxxxxxx, (double)☃xxxxxxxx);
                  if (☃xx >= 3) {
                     ☃xxx = SoundEvents.field_203273_io;
                  } else if (☃xx == 2) {
                     ☃xxx = SoundEvents.field_203272_in;
                  } else {
                     ☃xxx = SoundEvents.field_203271_im;
                  }

                  ☃.func_204803_n(20);
                  if (☃.field_70122_E) {
                     float ☃xxxx = 1.1999999F;
                     ☃.func_70091_d(MoverType.SELF, 0.0, 1.1999999F, 0.0);
                  }
               }

               ☃.func_184148_a(null, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃xxx, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
         }
      }
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77952_i() >= ☃.func_77958_k()) {
         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      } else if (EnchantmentHelper.func_203190_g(☃) > 0 && !☃.func_70026_G()) {
         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      } else {
         ☃.func_184598_c(☃);
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      }
   }

   @Override
   public boolean func_77644_a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      ☃.func_77972_a(1, ☃);
      return true;
   }

   @Override
   public boolean func_179218_a(ItemStack var1, World var2, IBlockState var3, BlockPos var4, EntityLivingBase var5) {
      if ((double)☃.func_185887_b(☃, ☃) != 0.0) {
         ☃.func_77972_a(2, ☃);
      }

      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot var1) {
      Multimap<String, AttributeModifier> ☃ = super.func_111205_h(☃);
      if (☃ == EntityEquipmentSlot.MAINHAND) {
         ☃.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Tool modifier", 8.0, 0));
         ☃.put(SharedMonsterAttributes.field_188790_f.func_111108_a(), new AttributeModifier(field_185050_h, "Tool modifier", -2.9F, 0));
      }

      return ☃;
   }

   @Override
   public int func_77619_b() {
      return 1;
   }
}
