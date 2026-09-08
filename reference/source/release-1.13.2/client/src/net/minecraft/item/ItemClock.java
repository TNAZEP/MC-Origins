package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemClock extends Item {
   public ItemClock(Item.Properties var1) {
      super(☃);
      this.func_185043_a(new ResourceLocation("time"), new IItemPropertyGetter() {
         private double field_185088_a;
         private double field_185089_b;
         private long field_185090_c;

         @Override
         public float call(ItemStack var1, @Nullable World var2, @Nullable EntityLivingBase var3) {
            boolean ☃ = ☃ != null;
            Entity ☃x = (Entity)(☃ ? ☃ : ☃.func_82836_z());
            if (☃ == null && ☃x != null) {
               ☃ = ☃x.field_70170_p;
            }

            if (☃ == null) {
               return 0.0F;
            } else {
               double ☃;
               if (☃.field_73011_w.func_76569_d()) {
                  ☃ = (double)☃.func_72826_c(1.0F);
               } else {
                  ☃ = Math.random();
               }

               ☃ = this.func_185087_a(☃, ☃);
               return (float)☃;
            }
         }

         private double func_185087_a(World var1, double var2) {
            if (☃.func_82737_E() != this.field_185090_c) {
               this.field_185090_c = ☃.func_82737_E();
               double ☃ = ☃ - this.field_185088_a;
               ☃ = MathHelper.func_191273_b(☃ + 0.5, 1.0) - 0.5;
               this.field_185089_b += ☃ * 0.1;
               this.field_185089_b *= 0.9;
               this.field_185088_a = MathHelper.func_191273_b(this.field_185088_a + this.field_185089_b, 1.0);
            }

            return this.field_185088_a;
         }
      });
   }
}
