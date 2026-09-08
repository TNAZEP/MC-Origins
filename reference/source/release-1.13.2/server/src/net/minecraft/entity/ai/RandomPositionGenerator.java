package net.minecraft.entity.ai;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RandomPositionGenerator {
   @Nullable
   public static Vec3d func_75463_a(EntityCreature var0, int var1, int var2) {
      return func_75462_c(☃, ☃, ☃, null);
   }

   @Nullable
   public static Vec3d func_191377_b(EntityCreature var0, int var1, int var2) {
      return func_191379_a(☃, ☃, ☃, null, false, 0.0);
   }

   @Nullable
   public static Vec3d func_75464_a(EntityCreature var0, int var1, int var2, Vec3d var3) {
      Vec3d ☃ = ☃.func_178786_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
      return func_75462_c(☃, ☃, ☃, ☃);
   }

   @Nullable
   public static Vec3d func_203155_a(EntityCreature var0, int var1, int var2, Vec3d var3, double var4) {
      Vec3d ☃ = ☃.func_178786_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
      return func_191379_a(☃, ☃, ☃, ☃, true, ☃);
   }

   @Nullable
   public static Vec3d func_75461_b(EntityCreature var0, int var1, int var2, Vec3d var3) {
      Vec3d ☃ = new Vec3d(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v).func_178788_d(☃);
      return func_75462_c(☃, ☃, ☃, ☃);
   }

   @Nullable
   private static Vec3d func_75462_c(EntityCreature var0, int var1, int var2, @Nullable Vec3d var3) {
      return func_191379_a(☃, ☃, ☃, ☃, true, (float) (Math.PI / 2));
   }

   @Nullable
   private static Vec3d func_191379_a(EntityCreature var0, int var1, int var2, @Nullable Vec3d var3, boolean var4, double var5) {
      PathNavigate ☃x = ☃.func_70661_as();
      Random ☃xx = ☃.func_70681_au();
      boolean ☃;
      if (☃.func_110175_bO()) {
         double ☃xxx = ☃.func_180486_cf()
               .func_177954_c(
                  (double)MathHelper.func_76128_c(☃.field_70165_t),
                  (double)MathHelper.func_76128_c(☃.field_70163_u),
                  (double)MathHelper.func_76128_c(☃.field_70161_v)
               )
            + 4.0;
         double ☃xxxx = (double)(☃.func_110174_bM() + (float)☃);
         ☃ = ☃xxx < ☃xxxx * ☃xxxx;
      } else {
         ☃ = false;
      }

      boolean ☃ = false;
      float ☃x = -99999.0F;
      int ☃xx = 0;
      int ☃xxx = 0;
      int ☃xxxx = 0;

      for(int ☃xxxxx = 0; ☃xxxxx < 10; ++☃xxxxx) {
         BlockPos ☃xxxxxx = func_203156_a(☃xx, ☃, ☃, ☃, ☃);
         if (☃xxxxxx != null) {
            int ☃xxxxxxx = ☃xxxxxx.func_177958_n();
            int ☃xxxxxxxx = ☃xxxxxx.func_177956_o();
            int ☃xxxxxxxxx = ☃xxxxxx.func_177952_p();
            if (☃.func_110175_bO() && ☃ > 1) {
               BlockPos ☃xxxxxxxxxx = ☃.func_180486_cf();
               if (☃.field_70165_t > (double)☃xxxxxxxxxx.func_177958_n()) {
                  ☃xxxxxxx -= ☃xx.nextInt(☃ / 2);
               } else {
                  ☃xxxxxxx += ☃xx.nextInt(☃ / 2);
               }

               if (☃.field_70161_v > (double)☃xxxxxxxxxx.func_177952_p()) {
                  ☃xxxxxxxxx -= ☃xx.nextInt(☃ / 2);
               } else {
                  ☃xxxxxxxxx += ☃xx.nextInt(☃ / 2);
               }
            }

            BlockPos ☃xxxxxxx = new BlockPos((double)☃xxxxxxx + ☃.field_70165_t, (double)☃xxxxxxxx + ☃.field_70163_u, (double)☃xxxxxxxxx + ☃.field_70161_v);
            if ((!☃ || ☃.func_180485_d(☃xxxxxxx)) && ☃x.func_188555_b(☃xxxxxxx)) {
               if (!☃) {
                  ☃xxxxxxx = func_191378_a(☃xxxxxxx, ☃);
                  if (func_191380_b(☃xxxxxxx, ☃)) {
                     continue;
                  }
               }

               float ☃xxxxxxxx = ☃.func_180484_a(☃xxxxxxx);
               if (☃xxxxxxxx > ☃x) {
                  ☃x = ☃xxxxxxxx;
                  ☃xx = ☃xxxxxxx;
                  ☃xxx = ☃xxxxxxxx;
                  ☃xxxx = ☃xxxxxxxxx;
                  ☃ = true;
               }
            }
         }
      }

      return ☃ ? new Vec3d((double)☃xx + ☃.field_70165_t, (double)☃xxx + ☃.field_70163_u, (double)☃xxxx + ☃.field_70161_v) : null;
   }

   @Nullable
   private static BlockPos func_203156_a(Random var0, int var1, int var2, @Nullable Vec3d var3, double var4) {
      if (☃ != null && !(☃ >= Math.PI)) {
         double ☃ = MathHelper.func_181159_b(☃.field_72449_c, ☃.field_72450_a) - (float) (Math.PI / 2);
         double ☃x = ☃ + (double)(2.0F * ☃.nextFloat() - 1.0F) * ☃;
         double ☃xx = Math.sqrt(☃.nextDouble()) * (double)MathHelper.field_180189_a * (double)☃;
         double ☃xxx = -☃xx * Math.sin(☃x);
         double ☃xxxx = ☃xx * Math.cos(☃x);
         if (!(Math.abs(☃xxx) > (double)☃) && !(Math.abs(☃xxxx) > (double)☃)) {
            int ☃xxxxx = ☃.nextInt(2 * ☃ + 1) - ☃;
            return new BlockPos(☃xxx, (double)☃xxxxx, ☃xxxx);
         } else {
            return null;
         }
      } else {
         int ☃ = ☃.nextInt(2 * ☃ + 1) - ☃;
         int ☃x = ☃.nextInt(2 * ☃ + 1) - ☃;
         int ☃xx = ☃.nextInt(2 * ☃ + 1) - ☃;
         return new BlockPos(☃, ☃x, ☃xx);
      }
   }

   private static BlockPos func_191378_a(BlockPos var0, EntityCreature var1) {
      if (!☃.field_70170_p.func_180495_p(☃).func_185904_a().func_76220_a()) {
         return ☃;
      } else {
         BlockPos ☃ = ☃.func_177984_a();

         while(☃.func_177956_o() < ☃.field_70170_p.func_72800_K() && ☃.field_70170_p.func_180495_p(☃).func_185904_a().func_76220_a()) {
            ☃ = ☃.func_177984_a();
         }

         return ☃;
      }
   }

   private static boolean func_191380_b(BlockPos var0, EntityCreature var1) {
      return ☃.field_70170_p.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a);
   }
}
