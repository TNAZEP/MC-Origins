package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ItemCompass extends Item {
   public ItemCompass(Item.Properties var1) {
      super(☃);
      this.func_185043_a(new ResourceLocation("angle"), new IItemPropertyGetter() {
         private double field_185095_a;
         private double field_185096_b;
         private long field_185097_c;

         @Override
         public float call(ItemStack var1, @Nullable World var2, @Nullable EntityLivingBase var3) {
            if (☃ == null && !☃.func_82839_y()) {
               return 0.0F;
            } else {
               boolean ☃ = ☃ != null;
               Entity ☃x = (Entity)(☃ ? ☃ : ☃.func_82836_z());
               if (☃ == null) {
                  ☃ = ☃x.field_70170_p;
               }

               double ☃;
               if (☃.field_73011_w.func_76569_d()) {
                  double ☃x = ☃ ? (double)☃x.field_70177_z : this.func_185094_a((EntityItemFrame)☃x);
                  ☃x = MathHelper.func_191273_b(☃x / 360.0, 1.0);
                  double ☃xx = this.func_185092_a(☃, ☃x) / (float) (Math.PI * 2);
                  ☃ = 0.5 - (☃x - 0.25 - ☃xx);
               } else {
                  ☃ = Math.random();
               }

               if (☃) {
                  ☃ = this.func_185093_a(☃, ☃);
               }

               return MathHelper.func_188207_b((float)☃, 1.0F);
            }
         }

         private double func_185093_a(World var1, double var2) {
            if (☃.func_82737_E() != this.field_185097_c) {
               this.field_185097_c = ☃.func_82737_E();
               double ☃ = ☃ - this.field_185095_a;
               ☃ = MathHelper.func_191273_b(☃ + 0.5, 1.0) - 0.5;
               this.field_185096_b += ☃ * 0.1;
               this.field_185096_b *= 0.8;
               this.field_185095_a = MathHelper.func_191273_b(this.field_185095_a + this.field_185096_b, 1.0);
            }

            return this.field_185095_a;
         }

         private double func_185094_a(EntityItemFrame var1) {
            return (double)MathHelper.func_188209_b(180 + ☃.field_174860_b.func_176736_b() * 90);
         }

         private double func_185092_a(IWorld var1, Entity var2) {
            BlockPos ☃ = ☃.func_175694_M();
            return Math.atan2((double)☃.func_177952_p() - ☃.field_70161_v, (double)☃.func_177958_n() - ☃.field_70165_t);
         }
      });
   }
}
