package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResourceManager;

public class Shader implements AutoCloseable {
   private final ShaderManager field_148051_c;
   public final Framebuffer field_148052_a;
   public final Framebuffer field_148050_b;
   private final List<Object> field_148048_d = Lists.<Object>newArrayList();
   private final List<String> field_148049_e = Lists.newArrayList();
   private final List<Integer> field_148046_f = Lists.newArrayList();
   private final List<Integer> field_148047_g = Lists.newArrayList();
   private Matrix4f field_148053_h;

   public Shader(IResourceManager var1, String var2, Framebuffer var3, Framebuffer var4) throws IOException {
      this.field_148051_c = new ShaderManager(☃, ☃);
      this.field_148052_a = ☃;
      this.field_148050_b = ☃;
   }

   public void close() {
      this.field_148051_c.close();
   }

   public void func_148041_a(String var1, Object var2, int var3, int var4) {
      this.field_148049_e.add(this.field_148049_e.size(), ☃);
      this.field_148048_d.add(this.field_148048_d.size(), ☃);
      this.field_148046_f.add(this.field_148046_f.size(), ☃);
      this.field_148047_g.add(this.field_148047_g.size(), ☃);
   }

   private void func_148040_d() {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179097_i();
      GlStateManager.func_179118_c();
      GlStateManager.func_179106_n();
      GlStateManager.func_179140_f();
      GlStateManager.func_179119_h();
      GlStateManager.func_179098_w();
      GlStateManager.func_179144_i(0);
   }

   public void func_195654_a(Matrix4f var1) {
      this.field_148053_h = ☃;
   }

   public void func_148042_a(float var1) {
      this.func_148040_d();
      this.field_148052_a.func_147609_e();
      float ☃ = (float)this.field_148050_b.field_147622_a;
      float ☃x = (float)this.field_148050_b.field_147620_b;
      GlStateManager.func_179083_b(0, 0, (int)☃, (int)☃x);
      this.field_148051_c.func_147992_a("DiffuseSampler", this.field_148052_a);

      for(int ☃xx = 0; ☃xx < this.field_148048_d.size(); ++☃xx) {
         this.field_148051_c.func_147992_a((String)this.field_148049_e.get(☃xx), this.field_148048_d.get(☃xx));
         this.field_148051_c
            .func_195653_b("AuxSize" + ☃xx)
            .func_148087_a((float)((Integer)this.field_148046_f.get(☃xx)).intValue(), (float)((Integer)this.field_148047_g.get(☃xx)).intValue());
      }

      this.field_148051_c.func_195653_b("ProjMat").func_195652_a(this.field_148053_h);
      this.field_148051_c.func_195653_b("InSize").func_148087_a((float)this.field_148052_a.field_147622_a, (float)this.field_148052_a.field_147620_b);
      this.field_148051_c.func_195653_b("OutSize").func_148087_a(☃, ☃x);
      this.field_148051_c.func_195653_b("Time").func_148090_a(☃);
      Minecraft ☃xx = Minecraft.func_71410_x();
      this.field_148051_c.func_195653_b("ScreenSize").func_148087_a((float)☃xx.field_195558_d.func_198109_k(), (float)☃xx.field_195558_d.func_198091_l());
      this.field_148051_c.func_147995_c();
      this.field_148050_b.func_147614_f();
      this.field_148050_b.func_147610_a(false);
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179135_a(true, true, true, true);
      Tessellator ☃xxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxx = ☃xxx.func_178180_c();
      ☃xxxx.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      ☃xxxx.func_181662_b(0.0, 0.0, 500.0).func_181669_b(255, 255, 255, 255).func_181675_d();
      ☃xxxx.func_181662_b((double)☃, 0.0, 500.0).func_181669_b(255, 255, 255, 255).func_181675_d();
      ☃xxxx.func_181662_b((double)☃, (double)☃x, 500.0).func_181669_b(255, 255, 255, 255).func_181675_d();
      ☃xxxx.func_181662_b(0.0, (double)☃x, 500.0).func_181669_b(255, 255, 255, 255).func_181675_d();
      ☃xxx.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179135_a(true, true, true, true);
      this.field_148051_c.func_147993_b();
      this.field_148050_b.func_147609_e();
      this.field_148052_a.func_147606_d();

      for(Object ☃xxxxx : this.field_148048_d) {
         if (☃xxxxx instanceof Framebuffer) {
            ((Framebuffer)☃xxxxx).func_147606_d();
         }
      }
   }

   public ShaderManager func_148043_c() {
      return this.field_148051_c;
   }
}
