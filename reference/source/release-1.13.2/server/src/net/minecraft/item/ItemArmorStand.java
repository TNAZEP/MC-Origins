package net.minecraft.item;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Rotations;
import net.minecraft.world.World;

public class ItemArmorStand extends Item {
   public ItemArmorStand(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      EnumFacing ☃ = ☃.func_196000_l();
      if (☃ == EnumFacing.DOWN) {
         return EnumActionResult.FAIL;
      } else {
         World ☃ = ☃.func_195991_k();
         BlockItemUseContext ☃x = new BlockItemUseContext(☃);
         BlockPos ☃xx = ☃x.func_195995_a();
         BlockPos ☃xxx = ☃xx.func_177984_a();
         if (☃x.func_196011_b() && ☃.func_180495_p(☃xxx).func_196953_a(☃x)) {
            double ☃xxxx = (double)☃xx.func_177958_n();
            double ☃xxxxx = (double)☃xx.func_177956_o();
            double ☃xxxxxx = (double)☃xx.func_177952_p();
            List<Entity> ☃xxxxxxx = ☃.func_72839_b(null, new AxisAlignedBB(☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxx + 1.0, ☃xxxxx + 2.0, ☃xxxxxx + 1.0));
            if (!☃xxxxxxx.isEmpty()) {
               return EnumActionResult.FAIL;
            } else {
               ItemStack ☃xxxx = ☃.func_195996_i();
               if (!☃.field_72995_K) {
                  ☃.func_175698_g(☃xx);
                  ☃.func_175698_g(☃xxx);
                  EntityArmorStand ☃xxxxx = new EntityArmorStand(☃, ☃xxxx + 0.5, ☃xxxxx, ☃xxxxxx + 0.5);
                  float ☃xxxxxx = (float)MathHelper.func_76141_d((MathHelper.func_76142_g(☃.func_195990_h() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                  ☃xxxxx.func_70012_b(☃xxxx + 0.5, ☃xxxxx, ☃xxxxxx + 0.5, ☃xxxxxx, 0.0F);
                  this.func_179221_a(☃xxxxx, ☃.field_73012_v);
                  EntityType.func_208048_a(☃, ☃.func_195999_j(), ☃xxxxx, ☃xxxx.func_77978_p());
                  ☃.func_72838_d(☃xxxxx);
                  ☃.func_184148_a(
                     null, ☃xxxxx.field_70165_t, ☃xxxxx.field_70163_u, ☃xxxxx.field_70161_v, SoundEvents.field_187710_m, SoundCategory.BLOCKS, 0.75F, 0.8F
                  );
               }

               ☃xxxx.func_190918_g(1);
               return EnumActionResult.SUCCESS;
            }
         } else {
            return EnumActionResult.FAIL;
         }
      }
   }

   private void func_179221_a(EntityArmorStand var1, Random var2) {
      Rotations ☃ = ☃.func_175418_s();
      float ☃x = ☃.nextFloat() * 5.0F;
      float ☃xx = ☃.nextFloat() * 20.0F - 10.0F;
      Rotations ☃xxx = new Rotations(☃.func_179415_b() + ☃x, ☃.func_179416_c() + ☃xx, ☃.func_179413_d());
      ☃.func_175415_a(☃xxx);
      ☃ = ☃.func_175408_t();
      ☃x = ☃.nextFloat() * 10.0F - 5.0F;
      ☃xxx = new Rotations(☃.func_179415_b(), ☃.func_179416_c() + ☃x, ☃.func_179413_d());
      ☃.func_175424_b(☃xxx);
   }
}
