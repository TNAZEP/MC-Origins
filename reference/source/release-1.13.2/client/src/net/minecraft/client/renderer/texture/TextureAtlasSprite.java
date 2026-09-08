package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class TextureAtlasSprite {
   private final ResourceLocation field_110984_i;
   protected final int field_130223_c;
   protected final int field_130224_d;
   protected NativeImage[] field_195670_c;
   @Nullable
   protected int[] field_195671_d;
   @Nullable
   protected int[] field_195672_e;
   protected NativeImage[] field_176605_b;
   private AnimationMetadataSection field_110982_k;
   protected boolean field_130222_e;
   protected int field_110975_c;
   protected int field_110974_d;
   private float field_110979_l;
   private float field_110980_m;
   private float field_110977_n;
   private float field_110978_o;
   protected int field_110973_g;
   protected int field_110983_h;
   private static final int[] field_195673_r = new int[4];
   private static final float[] field_195674_s = Util.func_200696_a(new float[256], var0 -> {
      for(int ☃ = 0; ☃ < var0.length; ++☃) {
         var0[☃] = (float)Math.pow((double)((float)☃ / 255.0F), 2.2);
      }
   });

   protected TextureAtlasSprite(ResourceLocation var1, int var2, int var3) {
      this.field_110984_i = ☃;
      this.field_130223_c = ☃;
      this.field_130224_d = ☃;
   }

   protected TextureAtlasSprite(ResourceLocation var1, PngSizeInfo var2, @Nullable AnimationMetadataSection var3) {
      this.field_110984_i = ☃;
      if (☃ != null) {
         int ☃ = Math.min(☃.field_188533_a, ☃.field_188534_b);
         this.field_130224_d = this.field_130223_c = ☃;
      } else {
         if (☃.field_188534_b != ☃.field_188533_a) {
            throw new RuntimeException("broken aspect ratio and not an animation");
         }

         this.field_130223_c = ☃.field_188533_a;
         this.field_130224_d = ☃.field_188534_b;
      }

      this.field_110982_k = ☃;
   }

   private void func_195666_b(int var1) {
      NativeImage[] ☃ = new NativeImage[☃ + 1];
      ☃[0] = this.field_195670_c[0];
      if (☃ > 0) {
         boolean ☃x = false;

         label71:
         for(int ☃xx = 0; ☃xx < this.field_195670_c[0].func_195702_a(); ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < this.field_195670_c[0].func_195714_b(); ++☃xxx) {
               if (this.field_195670_c[0].func_195709_a(☃xx, ☃xxx) >> 24 == 0) {
                  ☃x = true;
                  break label71;
               }
            }
         }

         for(int ☃xx = 1; ☃xx <= ☃; ++☃xx) {
            if (this.field_195670_c.length > ☃xx && this.field_195670_c[☃xx] != null) {
               ☃[☃xx] = this.field_195670_c[☃xx];
            } else {
               NativeImage ☃xxx = ☃[☃xx - 1];
               NativeImage ☃xxxx = new NativeImage(☃xxx.func_195702_a() >> 1, ☃xxx.func_195714_b() >> 1, false);
               int ☃xxxxx = ☃xxxx.func_195702_a();
               int ☃xxxxxx = ☃xxxx.func_195714_b();

               for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxx; ++☃xxxxxxx) {
                  for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxxxxx; ++☃xxxxxxxx) {
                     ☃xxxx.func_195700_a(
                        ☃xxxxxxx,
                        ☃xxxxxxxx,
                        func_195661_b(
                           ☃xxx.func_195709_a(☃xxxxxxx * 2 + 0, ☃xxxxxxxx * 2 + 0),
                           ☃xxx.func_195709_a(☃xxxxxxx * 2 + 1, ☃xxxxxxxx * 2 + 0),
                           ☃xxx.func_195709_a(☃xxxxxxx * 2 + 0, ☃xxxxxxxx * 2 + 1),
                           ☃xxx.func_195709_a(☃xxxxxxx * 2 + 1, ☃xxxxxxxx * 2 + 1),
                           ☃x
                        )
                     );
                  }
               }

               ☃[☃xx] = ☃xxxx;
            }
         }

         for(int ☃xx = ☃ + 1; ☃xx < this.field_195670_c.length; ++☃xx) {
            if (this.field_195670_c[☃xx] != null) {
               this.field_195670_c[☃xx].close();
            }
         }
      }

      this.field_195670_c = ☃;
   }

   private static int func_195661_b(int var0, int var1, int var2, int var3, boolean var4) {
      if (☃) {
         field_195673_r[0] = ☃;
         field_195673_r[1] = ☃;
         field_195673_r[2] = ☃;
         field_195673_r[3] = ☃;
         float ☃ = 0.0F;
         float ☃x = 0.0F;
         float ☃xx = 0.0F;
         float ☃xxx = 0.0F;

         for(int ☃xxxx = 0; ☃xxxx < 4; ++☃xxxx) {
            if (field_195673_r[☃xxxx] >> 24 != 0) {
               ☃ += func_195660_c(field_195673_r[☃xxxx] >> 24);
               ☃x += func_195660_c(field_195673_r[☃xxxx] >> 16);
               ☃xx += func_195660_c(field_195673_r[☃xxxx] >> 8);
               ☃xxx += func_195660_c(field_195673_r[☃xxxx] >> 0);
            }
         }

         ☃ /= 4.0F;
         ☃x /= 4.0F;
         ☃xx /= 4.0F;
         ☃xxx /= 4.0F;
         int ☃xxxx = (int)(Math.pow((double)☃, 0.45454545454545453) * 255.0);
         int ☃xxxxx = (int)(Math.pow((double)☃x, 0.45454545454545453) * 255.0);
         int ☃xxxxxx = (int)(Math.pow((double)☃xx, 0.45454545454545453) * 255.0);
         int ☃xxxxxxx = (int)(Math.pow((double)☃xxx, 0.45454545454545453) * 255.0);
         if (☃xxxx < 96) {
            ☃xxxx = 0;
         }

         return ☃xxxx << 24 | ☃xxxxx << 16 | ☃xxxxxx << 8 | ☃xxxxxxx;
      } else {
         int ☃ = func_195669_a(☃, ☃, ☃, ☃, 24);
         int ☃x = func_195669_a(☃, ☃, ☃, ☃, 16);
         int ☃xx = func_195669_a(☃, ☃, ☃, ☃, 8);
         int ☃xxx = func_195669_a(☃, ☃, ☃, ☃, 0);
         return ☃ << 24 | ☃x << 16 | ☃xx << 8 | ☃xxx;
      }
   }

   private static int func_195669_a(int var0, int var1, int var2, int var3, int var4) {
      float ☃ = func_195660_c(☃ >> ☃);
      float ☃x = func_195660_c(☃ >> ☃);
      float ☃xx = func_195660_c(☃ >> ☃);
      float ☃xxx = func_195660_c(☃ >> ☃);
      float ☃xxxx = (float)((double)((float)Math.pow((double)(☃ + ☃x + ☃xx + ☃xxx) * 0.25, 0.45454545454545453)));
      return (int)((double)☃xxxx * 255.0);
   }

   private static float func_195660_c(int var0) {
      return field_195674_s[☃ & 0xFF];
   }

   private void func_195659_d(int var1) {
      int ☃ = 0;
      int ☃x = 0;
      if (this.field_195671_d != null) {
         ☃ = this.field_195671_d[☃] * this.field_130223_c;
         ☃x = this.field_195672_e[☃] * this.field_130224_d;
      }

      this.func_195667_a(☃, ☃x, this.field_195670_c);
   }

   private void func_195667_a(int var1, int var2, NativeImage[] var3) {
      for(int ☃ = 0; ☃ < this.field_195670_c.length; ++☃) {
         ☃[☃]
            .func_195706_a(
               ☃,
               this.field_110975_c >> ☃,
               this.field_110974_d >> ☃,
               ☃ >> ☃,
               ☃ >> ☃,
               this.field_130223_c >> ☃,
               this.field_130224_d >> ☃,
               this.field_195670_c.length > 1
            );
      }
   }

   public void func_110971_a(int var1, int var2, int var3, int var4, boolean var5) {
      this.field_110975_c = ☃;
      this.field_110974_d = ☃;
      this.field_130222_e = ☃;
      float ☃ = (float)(0.01F / (double)☃);
      float ☃x = (float)(0.01F / (double)☃);
      this.field_110979_l = (float)☃ / (float)((double)☃) + ☃;
      this.field_110980_m = (float)(☃ + this.field_130223_c) / (float)((double)☃) - ☃;
      this.field_110977_n = (float)☃ / (float)☃ + ☃x;
      this.field_110978_o = (float)(☃ + this.field_130224_d) / (float)☃ - ☃x;
   }

   public int func_94211_a() {
      return this.field_130223_c;
   }

   public int func_94216_b() {
      return this.field_130224_d;
   }

   public float func_94209_e() {
      return this.field_110979_l;
   }

   public float func_94212_f() {
      return this.field_110980_m;
   }

   public float func_94214_a(double var1) {
      float ☃ = this.field_110980_m - this.field_110979_l;
      return this.field_110979_l + ☃ * (float)☃ / 16.0F;
   }

   public float func_188537_a(float var1) {
      float ☃ = this.field_110980_m - this.field_110979_l;
      return (☃ - this.field_110979_l) / ☃ * 16.0F;
   }

   public float func_94206_g() {
      return this.field_110977_n;
   }

   public float func_94210_h() {
      return this.field_110978_o;
   }

   public float func_94207_b(double var1) {
      float ☃ = this.field_110978_o - this.field_110977_n;
      return this.field_110977_n + ☃ * (float)☃ / 16.0F;
   }

   public float func_188536_b(float var1) {
      float ☃ = this.field_110978_o - this.field_110977_n;
      return (☃ - this.field_110977_n) / ☃ * 16.0F;
   }

   public ResourceLocation func_195668_m() {
      return this.field_110984_i;
   }

   public void func_94219_l() {
      ++this.field_110983_h;
      if (this.field_110983_h >= this.field_110982_k.func_110472_a(this.field_110973_g)) {
         int ☃ = this.field_110982_k.func_110468_c(this.field_110973_g);
         int ☃x = this.field_110982_k.func_110473_c() == 0 ? this.func_110970_k() : this.field_110982_k.func_110473_c();
         this.field_110973_g = (this.field_110973_g + 1) % ☃x;
         this.field_110983_h = 0;
         int ☃xx = this.field_110982_k.func_110468_c(this.field_110973_g);
         if (☃ != ☃xx && ☃xx >= 0 && ☃xx < this.func_110970_k()) {
            this.func_195659_d(☃xx);
         }
      } else if (this.field_110982_k.func_177219_e()) {
         this.func_180599_n();
      }
   }

   private void func_180599_n() {
      double ☃ = 1.0 - (double)this.field_110983_h / (double)this.field_110982_k.func_110472_a(this.field_110973_g);
      int ☃x = this.field_110982_k.func_110468_c(this.field_110973_g);
      int ☃xx = this.field_110982_k.func_110473_c() == 0 ? this.func_110970_k() : this.field_110982_k.func_110473_c();
      int ☃xxx = this.field_110982_k.func_110468_c((this.field_110973_g + 1) % ☃xx);
      if (☃x != ☃xxx && ☃xxx >= 0 && ☃xxx < this.func_110970_k()) {
         if (this.field_176605_b == null || this.field_176605_b.length != this.field_195670_c.length) {
            if (this.field_176605_b != null) {
               for(NativeImage ☃xxxx : this.field_176605_b) {
                  if (☃xxxx != null) {
                     ☃xxxx.close();
                  }
               }
            }

            this.field_176605_b = new NativeImage[this.field_195670_c.length];
         }

         for(int ☃xxxx = 0; ☃xxxx < this.field_195670_c.length; ++☃xxxx) {
            int ☃xxxxx = this.field_130223_c >> ☃xxxx;
            int ☃xxxxxx = this.field_130224_d >> ☃xxxx;
            if (this.field_176605_b[☃xxxx] == null) {
               this.field_176605_b[☃xxxx] = new NativeImage(☃xxxxx, ☃xxxxxx, false);
            }

            for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxxxx; ++☃xxxxx) {
               for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxxx; ++☃xxxxxx) {
                  int ☃xxxxxxx = this.func_195665_a(☃x, ☃xxxx, ☃xxxxxx, ☃xxxxx);
                  int ☃xxxxxxxx = this.func_195665_a(☃xxx, ☃xxxx, ☃xxxxxx, ☃xxxxx);
                  int ☃xxxxxxxxx = this.func_188535_a(☃, ☃xxxxxxx >> 16 & 0xFF, ☃xxxxxxxx >> 16 & 0xFF);
                  int ☃xxxxxxxxxx = this.func_188535_a(☃, ☃xxxxxxx >> 8 & 0xFF, ☃xxxxxxxx >> 8 & 0xFF);
                  int ☃xxxxxxxxxxx = this.func_188535_a(☃, ☃xxxxxxx & 0xFF, ☃xxxxxxxx & 0xFF);
                  this.field_176605_b[☃xxxx].func_195700_a(☃xxxxxx, ☃xxxxx, ☃xxxxxxx & 0xFF000000 | ☃xxxxxxxxx << 16 | ☃xxxxxxxxxx << 8 | ☃xxxxxxxxxxx);
               }
            }
         }

         this.func_195667_a(0, 0, this.field_176605_b);
      }
   }

   private int func_188535_a(double var1, int var3, int var4) {
      return (int)(☃ * (double)☃ + (1.0 - ☃) * (double)☃);
   }

   public int func_110970_k() {
      return this.field_195671_d == null ? 0 : this.field_195671_d.length;
   }

   public void func_195664_a(IResource var1, int var2) throws IOException {
      NativeImage ☃x = NativeImage.func_195713_a(☃.func_199027_b());
      this.field_195670_c = new NativeImage[☃];
      this.field_195670_c[0] = ☃x;
      int ☃;
      if (this.field_110982_k != null && this.field_110982_k.func_110474_b() != -1) {
         ☃ = ☃x.func_195702_a() / this.field_110982_k.func_110474_b();
      } else {
         ☃ = ☃x.func_195702_a() / this.field_130223_c;
      }

      int ☃;
      if (this.field_110982_k != null && this.field_110982_k.func_110471_a() != -1) {
         ☃ = ☃x.func_195714_b() / this.field_110982_k.func_110471_a();
      } else {
         ☃ = ☃x.func_195714_b() / this.field_130224_d;
      }

      if (this.field_110982_k != null && this.field_110982_k.func_110473_c() > 0) {
         int ☃ = this.field_110982_k.func_130073_e().stream().max(Integer::compareTo).get() + 1;
         this.field_195671_d = new int[☃];
         this.field_195672_e = new int[☃];
         Arrays.fill(this.field_195671_d, -1);
         Arrays.fill(this.field_195672_e, -1);

         for(int ☃x : this.field_110982_k.func_130073_e()) {
            if (☃x >= ☃ * ☃) {
               throw new RuntimeException("invalid frameindex " + ☃x);
            }

            int ☃xx = ☃x / ☃;
            int ☃xxx = ☃x % ☃;
            this.field_195671_d[☃x] = ☃xxx;
            this.field_195672_e[☃x] = ☃xx;
         }
      } else {
         List<AnimationFrame> ☃ = Lists.<AnimationFrame>newArrayList();
         int ☃x = ☃ * ☃;
         this.field_195671_d = new int[☃x];
         this.field_195672_e = new int[☃x];

         for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
               int ☃xxxx = ☃xx * ☃ + ☃xxx;
               this.field_195671_d[☃xxxx] = ☃xxx;
               this.field_195672_e[☃xxxx] = ☃xx;
               ☃.add(new AnimationFrame(☃xxxx, -1));
            }
         }

         int ☃xx = 1;
         boolean ☃xxx = false;
         if (this.field_110982_k != null) {
            ☃xx = this.field_110982_k.func_110469_d();
            ☃xxx = this.field_110982_k.func_177219_e();
         }

         this.field_110982_k = new AnimationMetadataSection(☃, this.field_130223_c, this.field_130224_d, ☃xx, ☃xxx);
      }
   }

   public void func_147963_d(int var1) {
      try {
         this.func_195666_b(☃);
      } catch (Throwable var5) {
         CrashReport ☃ = CrashReport.func_85055_a(var5, "Generating mipmaps for frame");
         CrashReportCategory ☃x = ☃.func_85058_a("Frame being iterated");
         ☃x.func_189529_a("Frame sizes", () -> {
            StringBuilder ☃ = new StringBuilder();

            for(NativeImage ☃x : this.field_195670_c) {
               if (☃.length() > 0) {
                  ☃.append(", ");
               }

               ☃.append(☃x == null ? "null" : ☃x.func_195702_a() + "x" + ☃x.func_195714_b());
            }

            return ☃.toString();
         });
         throw new ReportedException(☃);
      }
   }

   public void func_130103_l() {
      if (this.field_195670_c != null) {
         for(NativeImage ☃ : this.field_195670_c) {
            if (☃ != null) {
               ☃.close();
            }
         }
      }

      this.field_195670_c = null;
      if (this.field_176605_b != null) {
         for(NativeImage ☃ : this.field_176605_b) {
            if (☃ != null) {
               ☃.close();
            }
         }
      }

      this.field_176605_b = null;
   }

   public boolean func_130098_m() {
      return this.field_110982_k != null && this.field_110982_k.func_110473_c() > 1;
   }

   public String toString() {
      int ☃ = this.field_195671_d == null ? 0 : this.field_195671_d.length;
      return "TextureAtlasSprite{name='"
         + this.field_110984_i
         + '\''
         + ", frameCount="
         + ☃
         + ", rotated="
         + this.field_130222_e
         + ", x="
         + this.field_110975_c
         + ", y="
         + this.field_110974_d
         + ", height="
         + this.field_130224_d
         + ", width="
         + this.field_130223_c
         + ", u0="
         + this.field_110979_l
         + ", u1="
         + this.field_110980_m
         + ", v0="
         + this.field_110977_n
         + ", v1="
         + this.field_110978_o
         + '}';
   }

   private int func_195665_a(int var1, int var2, int var3, int var4) {
      return this.field_195670_c[☃]
         .func_195709_a(☃ + (this.field_195671_d[☃] * this.field_130223_c >> ☃), ☃ + (this.field_195672_e[☃] * this.field_130224_d >> ☃));
   }

   public boolean func_195662_a(int var1, int var2, int var3) {
      return (
            this.field_195670_c[0].func_195709_a(☃ + this.field_195671_d[☃] * this.field_130223_c, ☃ + this.field_195672_e[☃] * this.field_130224_d) >> 24
               & 0xFF
         )
         == 0;
   }

   public void func_195663_q() {
      this.func_195659_d(0);
   }
}
