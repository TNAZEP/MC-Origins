package net.minecraft.entity.projectile;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class ProjectileHelper {
   public static RayTraceResult func_188802_a(Entity var0, boolean var1, boolean var2, @Nullable Entity var3) {
      double ☃ = ☃.field_70165_t;
      double ☃x = ☃.field_70163_u;
      double ☃xx = ☃.field_70161_v;
      double ☃xxx = ☃.field_70159_w;
      double ☃xxxx = ☃.field_70181_x;
      double ☃xxxxx = ☃.field_70179_y;
      World ☃xxxxxx = ☃.field_70170_p;
      Vec3d ☃xxxxxxx = new Vec3d(☃, ☃x, ☃xx);
      if (!☃xxxxxx.func_211156_a(☃, ☃.func_174813_aQ(), (Set<Entity>)(!☃ && ☃ != null ? func_211325_a(☃) : ImmutableSet.of()))) {
         return new RayTraceResult(RayTraceResult.Type.BLOCK, ☃xxxxxxx, EnumFacing.func_210769_a(☃xxx, ☃xxxx, ☃xxxxx), new BlockPos(☃));
      } else {
         Vec3d ☃ = new Vec3d(☃ + ☃xxx, ☃x + ☃xxxx, ☃xx + ☃xxxxx);
         RayTraceResult ☃x = ☃xxxxxx.func_200259_a(☃xxxxxxx, ☃, RayTraceFluidMode.NEVER, true, false);
         if (☃) {
            if (☃x != null) {
               ☃ = new Vec3d(☃x.field_72307_f.field_72450_a, ☃x.field_72307_f.field_72448_b, ☃x.field_72307_f.field_72449_c);
            }

            Entity ☃xx = null;
            List<Entity> ☃xxx = ☃xxxxxx.func_72839_b(☃, ☃.func_174813_aQ().func_72321_a(☃xxx, ☃xxxx, ☃xxxxx).func_186662_g(1.0));
            double ☃xxxx = 0.0;

            for(int ☃xxxxx = 0; ☃xxxxx < ☃xxx.size(); ++☃xxxxx) {
               Entity ☃xxxxxx = (Entity)☃xxx.get(☃xxxxx);
               if (☃xxxxxx.func_70067_L() && (☃ || !☃xxxxxx.func_70028_i(☃)) && !☃xxxxxx.field_70145_X) {
                  AxisAlignedBB ☃xxxxxxx = ☃xxxxxx.func_174813_aQ().func_186662_g(0.3F);
                  RayTraceResult ☃xxxxxxxx = ☃xxxxxxx.func_72327_a(☃xxxxxxx, ☃);
                  if (☃xxxxxxxx != null) {
                     double ☃xxxxxxxxx = ☃xxxxxxx.func_72436_e(☃xxxxxxxx.field_72307_f);
                     if (☃xxxxxxxxx < ☃xxxx || ☃xxxx == 0.0) {
                        ☃xx = ☃xxxxxx;
                        ☃xxxx = ☃xxxxxxxxx;
                     }
                  }
               }
            }

            if (☃xx != null) {
               ☃x = new RayTraceResult(☃xx);
            }
         }

         return ☃x;
      }
   }

   private static Set<Entity> func_211325_a(Entity var0) {
      Entity ☃ = ☃.func_184187_bx();
      return ☃ != null ? ImmutableSet.of(☃, ☃) : ImmutableSet.of(☃);
   }

   public static final void func_188803_a(Entity var0, float var1) {
      double ☃ = ☃.field_70159_w;
      double ☃x = ☃.field_70181_x;
      double ☃xx = ☃.field_70179_y;
      float ☃xxx = MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx);
      ☃.field_70177_z = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) + 90.0F;
      ☃.field_70125_A = (float)(MathHelper.func_181159_b((double)☃xxx, ☃x) * 180.0F / (float)Math.PI) - 90.0F;

      while(☃.field_70125_A - ☃.field_70127_C < -180.0F) {
         ☃.field_70127_C -= 360.0F;
      }

      while(☃.field_70125_A - ☃.field_70127_C >= 180.0F) {
         ☃.field_70127_C += 360.0F;
      }

      while(☃.field_70177_z - ☃.field_70126_B < -180.0F) {
         ☃.field_70126_B -= 360.0F;
      }

      while(☃.field_70177_z - ☃.field_70126_B >= 180.0F) {
         ☃.field_70126_B += 360.0F;
      }

      ☃.field_70125_A = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      ☃.field_70177_z = ☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃;
   }
}
