package net.minecraft.item.crafting;

import java.util.Iterator;
import net.minecraft.util.math.MathHelper;

public interface IRecipePlacer<T> {
   default void func_201501_a(int var1, int var2, int var3, IRecipe var4, Iterator<T> var5, int var6) {
      int ☃ = ☃;
      int ☃x = ☃;
      if (☃ instanceof ShapedRecipe) {
         ShapedRecipe ☃xx = (ShapedRecipe)☃;
         ☃ = ☃xx.func_192403_f();
         ☃x = ☃xx.func_192404_g();
      }

      int ☃ = 0;

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         if (☃ == ☃) {
            ++☃;
         }

         boolean ☃xx = (float)☃x < (float)☃ / 2.0F;
         int ☃xxx = MathHelper.func_76141_d((float)☃ / 2.0F - (float)☃x / 2.0F);
         if (☃xx && ☃xxx > ☃x) {
            ☃ += ☃;
            ++☃x;
         }

         for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
            if (!☃.hasNext()) {
               return;
            }

            ☃xx = (float)☃ < (float)☃ / 2.0F;
            ☃xxx = MathHelper.func_76141_d((float)☃ / 2.0F - (float)☃ / 2.0F);
            int ☃xxx = ☃;
            boolean ☃xxxx = ☃xx < ☃;
            if (☃xx) {
               ☃xxx = ☃xxx + ☃;
               ☃xxxx = ☃xxx <= ☃xx && ☃xx < ☃xxx + ☃;
            }

            if (☃xxxx) {
               this.func_201500_a(☃, ☃, ☃, ☃x, ☃xx);
            } else if (☃xxx == ☃xx) {
               ☃ += ☃ - ☃xx;
               break;
            }

            ++☃;
         }
      }
   }

   void func_201500_a(Iterator<T> var1, int var2, int var3, int var4, int var5);
}
