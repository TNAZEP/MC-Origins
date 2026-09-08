package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class DebugRendererPathfinding implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_188290_a;
   private final Map<Integer, Path> field_188291_b = Maps.newHashMap();
   private final Map<Integer, Float> field_188292_c = Maps.newHashMap();
   private final Map<Integer, Long> field_188293_d = Maps.newHashMap();
   private EntityPlayer field_190068_e;
   private double field_190069_f;
   private double field_190070_g;
   private double field_190071_h;

   public DebugRendererPathfinding(Minecraft var1) {
      this.field_188290_a = ☃;
   }

   public void func_188289_a(int var1, Path var2, float var3) {
      this.field_188291_b.put(☃, ☃);
      this.field_188293_d.put(☃, Util.func_211177_b());
      this.field_188292_c.put(☃, ☃);
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      if (!this.field_188291_b.isEmpty()) {
         long ☃ = Util.func_211177_b();
         this.field_190068_e = this.field_188290_a.field_71439_g;
         this.field_190069_f = this.field_190068_e.field_70142_S + (this.field_190068_e.field_70165_t - this.field_190068_e.field_70142_S) * (double)☃;
         this.field_190070_g = this.field_190068_e.field_70137_T + (this.field_190068_e.field_70163_u - this.field_190068_e.field_70137_T) * (double)☃;
         this.field_190071_h = this.field_190068_e.field_70136_U + (this.field_190068_e.field_70161_v - this.field_190068_e.field_70136_U) * (double)☃;
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_179131_c(0.0F, 1.0F, 0.0F, 0.75F);
         GlStateManager.func_179090_x();
         GlStateManager.func_187441_d(6.0F);

         for(Integer ☃x : this.field_188291_b.keySet()) {
            Path ☃xx = (Path)this.field_188291_b.get(☃x);
            float ☃xxx = this.field_188292_c.get(☃x);
            this.func_190067_a(☃, ☃xx);
            PathPoint ☃xxxx = ☃xx.func_189964_i();
            if (!(this.func_190066_a(☃xxxx) > 40.0F)) {
               WorldRenderer.func_189696_b(
                  new AxisAlignedBB(
                        (double)((float)☃xxxx.field_75839_a + 0.25F),
                        (double)((float)☃xxxx.field_75837_b + 0.25F),
                        (double)☃xxxx.field_75838_c + 0.25,
                        (double)((float)☃xxxx.field_75839_a + 0.75F),
                        (double)((float)☃xxxx.field_75837_b + 0.75F),
                        (double)((float)☃xxxx.field_75838_c + 0.75F)
                     )
                     .func_72317_d(-this.field_190069_f, -this.field_190070_g, -this.field_190071_h),
                  0.0F,
                  1.0F,
                  0.0F,
                  0.5F
               );

               for(int ☃xxxxx = 0; ☃xxxxx < ☃xx.func_75874_d(); ++☃xxxxx) {
                  PathPoint ☃xxxxxx = ☃xx.func_75877_a(☃xxxxx);
                  if (!(this.func_190066_a(☃xxxxxx) > 40.0F)) {
                     float ☃xxxxxxx = ☃xxxxx == ☃xx.func_75873_e() ? 1.0F : 0.0F;
                     float ☃xxxxxxxx = ☃xxxxx == ☃xx.func_75873_e() ? 0.0F : 1.0F;
                     WorldRenderer.func_189696_b(
                        new AxisAlignedBB(
                              (double)((float)☃xxxxxx.field_75839_a + 0.5F - ☃xxx),
                              (double)((float)☃xxxxxx.field_75837_b + 0.01F * (float)☃xxxxx),
                              (double)((float)☃xxxxxx.field_75838_c + 0.5F - ☃xxx),
                              (double)((float)☃xxxxxx.field_75839_a + 0.5F + ☃xxx),
                              (double)((float)☃xxxxxx.field_75837_b + 0.25F + 0.01F * (float)☃xxxxx),
                              (double)((float)☃xxxxxx.field_75838_c + 0.5F + ☃xxx)
                           )
                           .func_72317_d(-this.field_190069_f, -this.field_190070_g, -this.field_190071_h),
                        ☃xxxxxxx,
                        0.0F,
                        ☃xxxxxxxx,
                        0.5F
                     );
                  }
               }
            }
         }

         for(Integer ☃x : this.field_188291_b.keySet()) {
            Path ☃xx = (Path)this.field_188291_b.get(☃x);

            for(PathPoint ☃xxx : ☃xx.func_189965_h()) {
               if (!(this.func_190066_a(☃xxx) > 40.0F)) {
                  DebugRenderer.func_190076_a(
                     String.format("%s", ☃xxx.field_186287_m),
                     (double)☃xxx.field_75839_a + 0.5,
                     (double)☃xxx.field_75837_b + 0.75,
                     (double)☃xxx.field_75838_c + 0.5,
                     ☃,
                     -65536
                  );
                  DebugRenderer.func_190076_a(
                     String.format(Locale.ROOT, "%.2f", ☃xxx.field_186286_l),
                     (double)☃xxx.field_75839_a + 0.5,
                     (double)☃xxx.field_75837_b + 0.25,
                     (double)☃xxx.field_75838_c + 0.5,
                     ☃,
                     -65536
                  );
               }
            }

            for(PathPoint ☃xxx : ☃xx.func_189966_g()) {
               if (!(this.func_190066_a(☃xxx) > 40.0F)) {
                  DebugRenderer.func_190076_a(
                     String.format("%s", ☃xxx.field_186287_m),
                     (double)☃xxx.field_75839_a + 0.5,
                     (double)☃xxx.field_75837_b + 0.75,
                     (double)☃xxx.field_75838_c + 0.5,
                     ☃,
                     -16776961
                  );
                  DebugRenderer.func_190076_a(
                     String.format(Locale.ROOT, "%.2f", ☃xxx.field_186286_l),
                     (double)☃xxx.field_75839_a + 0.5,
                     (double)☃xxx.field_75837_b + 0.25,
                     (double)☃xxx.field_75838_c + 0.5,
                     ☃,
                     -16776961
                  );
               }
            }

            for(int ☃xxx = 0; ☃xxx < ☃xx.func_75874_d(); ++☃xxx) {
               PathPoint ☃xxxx = ☃xx.func_75877_a(☃xxx);
               if (!(this.func_190066_a(☃xxxx) > 40.0F)) {
                  DebugRenderer.func_190076_a(
                     String.format("%s", ☃xxxx.field_186287_m),
                     (double)☃xxxx.field_75839_a + 0.5,
                     (double)☃xxxx.field_75837_b + 0.75,
                     (double)☃xxxx.field_75838_c + 0.5,
                     ☃,
                     -1
                  );
                  DebugRenderer.func_190076_a(
                     String.format(Locale.ROOT, "%.2f", ☃xxxx.field_186286_l),
                     (double)☃xxxx.field_75839_a + 0.5,
                     (double)☃xxxx.field_75837_b + 0.25,
                     (double)☃xxxx.field_75838_c + 0.5,
                     ☃,
                     -1
                  );
               }
            }
         }

         for(Integer ☃x : (Integer[])this.field_188293_d.keySet().toArray(new Integer[0])) {
            if (☃ - this.field_188293_d.get(☃x) > 20000L) {
               this.field_188291_b.remove(☃x);
               this.field_188293_d.remove(☃x);
            }
         }

         GlStateManager.func_179098_w();
         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   public void func_190067_a(float var1, Path var2) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int ☃xx = 0; ☃xx < ☃.func_75874_d(); ++☃xx) {
         PathPoint ☃xxx = ☃.func_75877_a(☃xx);
         if (!(this.func_190066_a(☃xxx) > 40.0F)) {
            float ☃xxxx = (float)☃xx / (float)☃.func_75874_d() * 0.33F;
            int ☃xxxxx = ☃xx == 0 ? 0 : MathHelper.func_181758_c(☃xxxx, 0.9F, 0.9F);
            int ☃xxxxxx = ☃xxxxx >> 16 & 0xFF;
            int ☃xxxxxxx = ☃xxxxx >> 8 & 0xFF;
            int ☃xxxxxxxx = ☃xxxxx & 0xFF;
            ☃x.func_181662_b(
                  (double)☃xxx.field_75839_a - this.field_190069_f + 0.5,
                  (double)☃xxx.field_75837_b - this.field_190070_g + 0.5,
                  (double)☃xxx.field_75838_c - this.field_190071_h + 0.5
               )
               .func_181669_b(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 255)
               .func_181675_d();
         }
      }

      ☃.func_78381_a();
   }

   private float func_190066_a(PathPoint var1) {
      return (float)(
         Math.abs((double)☃.field_75839_a - this.field_190068_e.field_70165_t)
            + Math.abs((double)☃.field_75837_b - this.field_190068_e.field_70163_u)
            + Math.abs((double)☃.field_75838_c - this.field_190068_e.field_70161_v)
      );
   }
}
