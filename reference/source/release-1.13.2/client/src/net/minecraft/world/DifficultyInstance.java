package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.math.MathHelper;

@Immutable
public class DifficultyInstance {
   private final EnumDifficulty field_180172_a;
   private final float field_180171_b;

   public DifficultyInstance(EnumDifficulty var1, long var2, long var4, float var6) {
      this.field_180172_a = ☃;
      this.field_180171_b = this.func_180169_a(☃, ☃, ☃, ☃);
   }

   public EnumDifficulty func_203095_a() {
      return this.field_180172_a;
   }

   public float func_180168_b() {
      return this.field_180171_b;
   }

   public boolean func_193845_a(float var1) {
      return this.field_180171_b > ☃;
   }

   public float func_180170_c() {
      if (this.field_180171_b < 2.0F) {
         return 0.0F;
      } else {
         return this.field_180171_b > 4.0F ? 1.0F : (this.field_180171_b - 2.0F) / 2.0F;
      }
   }

   private float func_180169_a(EnumDifficulty var1, long var2, long var4, float var6) {
      if (☃ == EnumDifficulty.PEACEFUL) {
         return 0.0F;
      } else {
         boolean ☃ = ☃ == EnumDifficulty.HARD;
         float ☃x = 0.75F;
         float ☃xx = MathHelper.func_76131_a(((float)☃ + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         ☃x += ☃xx;
         float ☃xxx = 0.0F;
         ☃xxx += MathHelper.func_76131_a((float)☃ / 3600000.0F, 0.0F, 1.0F) * (☃ ? 1.0F : 0.75F);
         ☃xxx += MathHelper.func_76131_a(☃ * 0.25F, 0.0F, ☃xx);
         if (☃ == EnumDifficulty.EASY) {
            ☃xxx *= 0.5F;
         }

         ☃x += ☃xxx;
         return (float)☃.func_151525_a() * ☃x;
      }
   }
}
