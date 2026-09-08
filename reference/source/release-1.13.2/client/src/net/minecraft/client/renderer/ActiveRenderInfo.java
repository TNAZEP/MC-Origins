package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;

public class ActiveRenderInfo {
   private static final FloatBuffer field_178812_b = GLAllocation.func_74529_h(16);
   private static Vec3d field_178811_e = new Vec3d(0.0, 0.0, 0.0);
   private static float field_74588_d;
   private static float field_74589_e;
   private static float field_74586_f;
   private static float field_74587_g;
   private static float field_74596_h;

   public static void func_197924_a(EntityPlayer var0, boolean var1, float var2) {
      field_178812_b.clear();
      GlStateManager.func_179111_a(2982, field_178812_b);
      Matrix4f ☃ = new Matrix4f();
      ☃.func_195874_a(field_178812_b);
      ☃.func_195887_c();
      float ☃x = 0.05F;
      float ☃xx = ☃ * MathHelper.field_180189_a;
      Vector4f ☃xxx = new Vector4f(0.0F, 0.0F, -2.0F * ☃xx * 0.05F / (☃xx + 0.05F), 1.0F);
      ☃xxx.func_195908_a(☃);
      field_178811_e = new Vec3d((double)☃xxx.func_195910_a(), (double)☃xxx.func_195913_b(), (double)☃xxx.func_195914_c());
      float ☃xxxx = ☃.field_70125_A;
      float ☃xxxxx = ☃.field_70177_z;
      int ☃xxxxxx = ☃ ? -1 : 1;
      field_74588_d = MathHelper.func_76134_b(☃xxxxx * (float) (Math.PI / 180.0)) * (float)☃xxxxxx;
      field_74586_f = MathHelper.func_76126_a(☃xxxxx * (float) (Math.PI / 180.0)) * (float)☃xxxxxx;
      field_74587_g = -field_74586_f * MathHelper.func_76126_a(☃xxxx * (float) (Math.PI / 180.0)) * (float)☃xxxxxx;
      field_74596_h = field_74588_d * MathHelper.func_76126_a(☃xxxx * (float) (Math.PI / 180.0)) * (float)☃xxxxxx;
      field_74589_e = MathHelper.func_76134_b(☃xxxx * (float) (Math.PI / 180.0));
   }

   public static Vec3d func_178806_a(Entity var0, double var1) {
      double ☃ = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * ☃;
      double ☃x = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * ☃;
      double ☃xx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * ☃;
      double ☃xxx = ☃ + field_178811_e.field_72450_a;
      double ☃xxxx = ☃x + field_178811_e.field_72448_b;
      double ☃xxxxx = ☃xx + field_178811_e.field_72449_c;
      return new Vec3d(☃xxx, ☃xxxx, ☃xxxxx);
   }

   public static IBlockState func_186703_a(IBlockReader var0, Entity var1, float var2) {
      Vec3d ☃ = func_178806_a(☃, (double)☃);
      BlockPos ☃x = new BlockPos(☃);
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      IFluidState ☃xxx = ☃.func_204610_c(☃x);
      if (!☃xxx.func_206888_e()) {
         float ☃xxxx = (float)☃x.func_177956_o() + ☃xxx.func_206885_f() + 0.11111111F;
         if (☃.field_72448_b >= (double)☃xxxx) {
            ☃xx = ☃.func_180495_p(☃x.func_177984_a());
         }
      }

      return ☃xx;
   }

   public static IFluidState func_206243_b(IBlockReader var0, Entity var1, float var2) {
      Vec3d ☃ = func_178806_a(☃, (double)☃);
      BlockPos ☃x = new BlockPos(☃);
      IFluidState ☃xx = ☃.func_204610_c(☃x);
      if (!☃xx.func_206888_e()) {
         float ☃xxx = (float)☃x.func_177956_o() + ☃xx.func_206885_f() + 0.11111111F;
         if (☃.field_72448_b >= (double)☃xxx) {
            ☃xx = ☃.func_204610_c(☃x.func_177984_a());
         }
      }

      return ☃xx;
   }

   public static float func_178808_b() {
      return field_74588_d;
   }

   public static float func_178809_c() {
      return field_74589_e;
   }

   public static float func_178803_d() {
      return field_74586_f;
   }

   public static float func_178805_e() {
      return field_74587_g;
   }

   public static float func_178807_f() {
      return field_74596_h;
   }
}
