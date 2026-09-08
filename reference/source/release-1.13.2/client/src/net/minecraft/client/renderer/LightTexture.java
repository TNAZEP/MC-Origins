package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class LightTexture implements AutoCloseable {
   private final DynamicTexture field_205110_a;
   private final NativeImage field_205111_b;
   private final ResourceLocation field_205112_c;
   private boolean field_205113_d;
   private float field_205114_e;
   private float field_205115_f;
   private final GameRenderer field_205116_g;
   private final Minecraft field_205117_h;

   public LightTexture(GameRenderer var1) {
      this.field_205116_g = ☃;
      this.field_205117_h = ☃.func_205000_l();
      this.field_205110_a = new DynamicTexture(16, 16, false);
      this.field_205112_c = this.field_205117_h.func_110434_K().func_110578_a("light_map", this.field_205110_a);
      this.field_205111_b = this.field_205110_a.func_195414_e();
   }

   public void close() {
      this.field_205110_a.close();
   }

   public void func_205107_a() {
      this.field_205115_f = (float)((double)this.field_205115_f + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.field_205115_f = (float)((double)this.field_205115_f * 0.9);
      this.field_205114_e += this.field_205115_f - this.field_205114_e;
      this.field_205113_d = true;
   }

   public void func_205108_b() {
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179090_x();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   public void func_205109_c() {
      GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      float ☃ = 0.00390625F;
      GlStateManager.func_179152_a(0.00390625F, 0.00390625F, 0.00390625F);
      GlStateManager.func_179109_b(8.0F, 8.0F, 8.0F);
      GlStateManager.func_179128_n(5888);
      this.field_205117_h.func_110434_K().func_110577_a(this.field_205112_c);
      GlStateManager.func_187421_b(3553, 10241, 9729);
      GlStateManager.func_187421_b(3553, 10240, 9729);
      GlStateManager.func_187421_b(3553, 10242, 10496);
      GlStateManager.func_187421_b(3553, 10243, 10496);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179098_w();
      GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
   }

   public void func_205106_a(float var1) {
      if (this.field_205113_d) {
         this.field_205117_h.field_71424_I.func_76320_a("lightTex");
         World ☃ = this.field_205117_h.field_71441_e;
         if (☃ != null) {
            float ☃xx = ☃.func_72971_b(1.0F);
            float ☃xxx = ☃xx * 0.95F + 0.05F;
            float ☃xxxx = this.field_205117_h.field_71439_g.func_203719_J();
            float ☃x;
            if (this.field_205117_h.field_71439_g.func_70644_a(MobEffects.field_76439_r)) {
               ☃x = this.field_205116_g.func_180438_a(this.field_205117_h.field_71439_g, ☃);
            } else if (☃xxxx > 0.0F && this.field_205117_h.field_71439_g.func_70644_a(MobEffects.field_205136_C)) {
               ☃x = ☃xxxx;
            } else {
               ☃x = 0.0F;
            }

            for(int ☃x = 0; ☃x < 16; ++☃x) {
               for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
                  float ☃xxx = ☃.field_73011_w.func_177497_p()[☃x] * ☃xxx;
                  float ☃xxxx = ☃.field_73011_w.func_177497_p()[☃xx] * (this.field_205114_e * 0.1F + 1.5F);
                  if (☃.func_175658_ac() > 0) {
                     ☃xxx = ☃.field_73011_w.func_177497_p()[☃x];
                  }

                  float ☃xxx = ☃xxx * (☃xx * 0.65F + 0.35F);
                  float ☃xxxx = ☃xxx * (☃xx * 0.65F + 0.35F);
                  float ☃xxxxx = ☃xxxx * ((☃xxxx * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float ☃xxxxxx = ☃xxxx * (☃xxxx * ☃xxxx * 0.6F + 0.4F);
                  float ☃xxxxxxx = ☃xxx + ☃xxxx;
                  float ☃xxxxxxxx = ☃xxxx + ☃xxxxx;
                  float ☃xxxxxxxxx = ☃xxx + ☃xxxxxx;
                  ☃xxxxxxx = ☃xxxxxxx * 0.96F + 0.03F;
                  ☃xxxxxxxx = ☃xxxxxxxx * 0.96F + 0.03F;
                  ☃xxxxxxxxx = ☃xxxxxxxxx * 0.96F + 0.03F;
                  if (this.field_205116_g.func_205002_d(☃) > 0.0F) {
                     float ☃xxxxxxxxxx = this.field_205116_g.func_205002_d(☃);
                     ☃xxxxxxx = ☃xxxxxxx * (1.0F - ☃xxxxxxxxxx) + ☃xxxxxxx * 0.7F * ☃xxxxxxxxxx;
                     ☃xxxxxxxx = ☃xxxxxxxx * (1.0F - ☃xxxxxxxxxx) + ☃xxxxxxxx * 0.6F * ☃xxxxxxxxxx;
                     ☃xxxxxxxxx = ☃xxxxxxxxx * (1.0F - ☃xxxxxxxxxx) + ☃xxxxxxxxx * 0.6F * ☃xxxxxxxxxx;
                  }

                  if (☃.field_73011_w.func_186058_p() == DimensionType.THE_END) {
                     ☃xxxxxxx = 0.22F + ☃xxxx * 0.75F;
                     ☃xxxxxxxx = 0.28F + ☃xxxxx * 0.75F;
                     ☃xxxxxxxxx = 0.25F + ☃xxxxxx * 0.75F;
                  }

                  if (☃x > 0.0F) {
                     float ☃xxx = 1.0F / ☃xxxxxxx;
                     if (☃xxx > 1.0F / ☃xxxxxxxx) {
                        ☃xxx = 1.0F / ☃xxxxxxxx;
                     }

                     if (☃xxx > 1.0F / ☃xxxxxxxxx) {
                        ☃xxx = 1.0F / ☃xxxxxxxxx;
                     }

                     ☃xxxxxxx = ☃xxxxxxx * (1.0F - ☃x) + ☃xxxxxxx * ☃xxx * ☃x;
                     ☃xxxxxxxx = ☃xxxxxxxx * (1.0F - ☃x) + ☃xxxxxxxx * ☃xxx * ☃x;
                     ☃xxxxxxxxx = ☃xxxxxxxxx * (1.0F - ☃x) + ☃xxxxxxxxx * ☃xxx * ☃x;
                  }

                  if (☃xxxxxxx > 1.0F) {
                     ☃xxxxxxx = 1.0F;
                  }

                  if (☃xxxxxxxx > 1.0F) {
                     ☃xxxxxxxx = 1.0F;
                  }

                  if (☃xxxxxxxxx > 1.0F) {
                     ☃xxxxxxxxx = 1.0F;
                  }

                  float ☃xxx = (float)this.field_205117_h.field_71474_y.field_74333_Y;
                  float ☃xxxx = 1.0F - ☃xxxxxxx;
                  float ☃xxxxx = 1.0F - ☃xxxxxxxx;
                  float ☃xxxxxx = 1.0F - ☃xxxxxxxxx;
                  ☃xxxx = 1.0F - ☃xxxx * ☃xxxx * ☃xxxx * ☃xxxx;
                  ☃xxxxx = 1.0F - ☃xxxxx * ☃xxxxx * ☃xxxxx * ☃xxxxx;
                  ☃xxxxxx = 1.0F - ☃xxxxxx * ☃xxxxxx * ☃xxxxxx * ☃xxxxxx;
                  ☃xxxxxxx = ☃xxxxxxx * (1.0F - ☃xxx) + ☃xxxx * ☃xxx;
                  ☃xxxxxxxx = ☃xxxxxxxx * (1.0F - ☃xxx) + ☃xxxxx * ☃xxx;
                  ☃xxxxxxxxx = ☃xxxxxxxxx * (1.0F - ☃xxx) + ☃xxxxxx * ☃xxx;
                  ☃xxxxxxx = ☃xxxxxxx * 0.96F + 0.03F;
                  ☃xxxxxxxx = ☃xxxxxxxx * 0.96F + 0.03F;
                  ☃xxxxxxxxx = ☃xxxxxxxxx * 0.96F + 0.03F;
                  if (☃xxxxxxx > 1.0F) {
                     ☃xxxxxxx = 1.0F;
                  }

                  if (☃xxxxxxxx > 1.0F) {
                     ☃xxxxxxxx = 1.0F;
                  }

                  if (☃xxxxxxxxx > 1.0F) {
                     ☃xxxxxxxxx = 1.0F;
                  }

                  if (☃xxxxxxx < 0.0F) {
                     ☃xxxxxxx = 0.0F;
                  }

                  if (☃xxxxxxxx < 0.0F) {
                     ☃xxxxxxxx = 0.0F;
                  }

                  if (☃xxxxxxxxx < 0.0F) {
                     ☃xxxxxxxxx = 0.0F;
                  }

                  int ☃xxx = 255;
                  int ☃xxxx = (int)(☃xxxxxxx * 255.0F);
                  int ☃xxxxx = (int)(☃xxxxxxxx * 255.0F);
                  int ☃xxxxxx = (int)(☃xxxxxxxxx * 255.0F);
                  this.field_205111_b.func_195700_a(☃xx, ☃x, 0xFF000000 | ☃xxxxxx << 16 | ☃xxxxx << 8 | ☃xxxx);
               }
            }

            this.field_205110_a.func_110564_a();
            this.field_205113_d = false;
            this.field_205117_h.field_71424_I.func_76319_b();
         }
      }
   }
}
