package net.minecraft.client.renderer;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ViewFrustum {
   protected final WorldRenderer field_178169_a;
   protected final World field_178167_b;
   protected int field_178168_c;
   protected int field_178165_d;
   protected int field_178166_e;
   public RenderChunk[] field_178164_f;

   public ViewFrustum(World var1, int var2, WorldRenderer var3, IRenderChunkFactory var4) {
      this.field_178169_a = ☃;
      this.field_178167_b = ☃;
      this.func_178159_a(☃);
      this.func_178158_a(☃);
   }

   protected void func_178158_a(IRenderChunkFactory var1) {
      int ☃ = this.field_178165_d * this.field_178168_c * this.field_178166_e;
      this.field_178164_f = new RenderChunk[☃];

      for(int ☃x = 0; ☃x < this.field_178165_d; ++☃x) {
         for(int ☃xx = 0; ☃xx < this.field_178168_c; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < this.field_178166_e; ++☃xxx) {
               int ☃xxxx = this.func_212478_a(☃x, ☃xx, ☃xxx);
               this.field_178164_f[☃xxxx] = ☃.create(this.field_178167_b, this.field_178169_a);
               this.field_178164_f[☃xxxx].func_189562_a(☃x * 16, ☃xx * 16, ☃xxx * 16);
            }
         }
      }
   }

   public void func_178160_a() {
      for(RenderChunk ☃ : this.field_178164_f) {
         ☃.func_178566_a();
      }
   }

   private int func_212478_a(int var1, int var2, int var3) {
      return (☃ * this.field_178168_c + ☃) * this.field_178165_d + ☃;
   }

   protected void func_178159_a(int var1) {
      int ☃ = ☃ * 2 + 1;
      this.field_178165_d = ☃;
      this.field_178168_c = 16;
      this.field_178166_e = ☃;
   }

   public void func_178163_a(double var1, double var3) {
      int ☃ = MathHelper.func_76128_c(☃) - 8;
      int ☃x = MathHelper.func_76128_c(☃) - 8;
      int ☃xx = this.field_178165_d * 16;

      for(int ☃xxx = 0; ☃xxx < this.field_178165_d; ++☃xxx) {
         int ☃xxxx = this.func_178157_a(☃, ☃xx, ☃xxx);

         for(int ☃xxxxx = 0; ☃xxxxx < this.field_178166_e; ++☃xxxxx) {
            int ☃xxxxxx = this.func_178157_a(☃x, ☃xx, ☃xxxxx);

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < this.field_178168_c; ++☃xxxxxxx) {
               int ☃xxxxxxxx = ☃xxxxxxx * 16;
               RenderChunk ☃xxxxxxxxx = this.field_178164_f[this.func_212478_a(☃xxx, ☃xxxxxxx, ☃xxxxx)];
               ☃xxxxxxxxx.func_189562_a(☃xxxx, ☃xxxxxxxx, ☃xxxxxx);
            }
         }
      }
   }

   private int func_178157_a(int var1, int var2, int var3) {
      int ☃ = ☃ * 16;
      int ☃x = ☃ - ☃ + ☃ / 2;
      if (☃x < 0) {
         ☃x -= ☃ - 1;
      }

      return ☃ - ☃x / ☃ * ☃;
   }

   public void func_187474_a(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      int ☃ = MathHelper.func_76137_a(☃, 16);
      int ☃x = MathHelper.func_76137_a(☃, 16);
      int ☃xx = MathHelper.func_76137_a(☃, 16);
      int ☃xxx = MathHelper.func_76137_a(☃, 16);
      int ☃xxxx = MathHelper.func_76137_a(☃, 16);
      int ☃xxxxx = MathHelper.func_76137_a(☃, 16);

      for(int ☃xxxxxx = ☃; ☃xxxxxx <= ☃xxx; ++☃xxxxxx) {
         int ☃xxxxxxx = MathHelper.func_180184_b(☃xxxxxx, this.field_178165_d);

         for(int ☃xxxxxxxx = ☃x; ☃xxxxxxxx <= ☃xxxx; ++☃xxxxxxxx) {
            int ☃xxxxxxxxx = MathHelper.func_180184_b(☃xxxxxxxx, this.field_178168_c);

            for(int ☃xxxxxxxxxx = ☃xx; ☃xxxxxxxxxx <= ☃xxxxx; ++☃xxxxxxxxxx) {
               int ☃xxxxxxxxxxx = MathHelper.func_180184_b(☃xxxxxxxxxx, this.field_178166_e);
               RenderChunk ☃xxxxxxxxxxxx = this.field_178164_f[this.func_212478_a(☃xxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxxx)];
               ☃xxxxxxxxxxxx.func_178575_a(☃);
            }
         }
      }
   }

   @Nullable
   protected RenderChunk func_178161_a(BlockPos var1) {
      int ☃ = MathHelper.func_76137_a(☃.func_177958_n(), 16);
      int ☃x = MathHelper.func_76137_a(☃.func_177956_o(), 16);
      int ☃xx = MathHelper.func_76137_a(☃.func_177952_p(), 16);
      if (☃x >= 0 && ☃x < this.field_178168_c) {
         ☃ = MathHelper.func_180184_b(☃, this.field_178165_d);
         ☃xx = MathHelper.func_180184_b(☃xx, this.field_178166_e);
         return this.field_178164_f[this.func_212478_a(☃, ☃x, ☃xx)];
      } else {
         return null;
      }
   }
}
