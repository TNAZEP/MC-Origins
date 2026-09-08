package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleItemPickup extends Particle {
   private final Entity field_174840_a;
   private final Entity field_174843_ax;
   private int field_70594_ar;
   private final int field_70593_as;
   private final float field_174841_aA;
   private final RenderManager field_174842_aB = Minecraft.func_71410_x().func_175598_ae();

   public ParticleItemPickup(World var1, Entity var2, Entity var3, float var4) {
      super(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃.field_70159_w, ☃.field_70181_x, ☃.field_70179_y);
      this.field_174840_a = ☃;
      this.field_174843_ax = ☃;
      this.field_70593_as = 3;
      this.field_174841_aA = ☃;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70594_ar + ☃) / (float)this.field_70593_as;
      ☃ *= ☃;
      double ☃x = this.field_174840_a.field_70165_t;
      double ☃xx = this.field_174840_a.field_70163_u;
      double ☃xxx = this.field_174840_a.field_70161_v;
      double ☃xxxx = this.field_174843_ax.field_70142_S + (this.field_174843_ax.field_70165_t - this.field_174843_ax.field_70142_S) * (double)☃;
      double ☃xxxxx = this.field_174843_ax.field_70137_T
         + (this.field_174843_ax.field_70163_u - this.field_174843_ax.field_70137_T) * (double)☃
         + (double)this.field_174841_aA;
      double ☃xxxxxx = this.field_174843_ax.field_70136_U + (this.field_174843_ax.field_70161_v - this.field_174843_ax.field_70136_U) * (double)☃;
      double ☃xxxxxxx = ☃x + (☃xxxx - ☃x) * (double)☃;
      double ☃xxxxxxxx = ☃xx + (☃xxxxx - ☃xx) * (double)☃;
      double ☃xxxxxxxxx = ☃xxx + (☃xxxxxx - ☃xxx) * (double)☃;
      int ☃xxxxxxxxxx = this.func_189214_a(☃);
      int ☃xxxxxxxxxxx = ☃xxxxxxxxxx % 65536;
      int ☃xxxxxxxxxxxx = ☃xxxxxxxxxx / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)☃xxxxxxxxxxx, (float)☃xxxxxxxxxxxx);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      ☃xxxxxxx -= field_70556_an;
      ☃xxxxxxxx -= field_70554_ao;
      ☃xxxxxxxxx -= field_70555_ap;
      GlStateManager.func_179145_e();
      this.field_174842_aB.func_188391_a(this.field_174840_a, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, this.field_174840_a.field_70177_z, ☃, false);
   }

   @Override
   public void func_189213_a() {
      ++this.field_70594_ar;
      if (this.field_70594_ar == this.field_70593_as) {
         this.func_187112_i();
      }
   }

   @Override
   public int func_70537_b() {
      return 3;
   }
}
