package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ModelGuardian extends ModelBase {
   private final ModelRenderer field_178710_a;
   private final ModelRenderer field_178708_b;
   private final ModelRenderer[] field_178709_c;
   private final ModelRenderer[] field_178707_d;

   public ModelGuardian() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_178709_c = new ModelRenderer[12];
      this.field_178710_a = new ModelRenderer(this);
      this.field_178710_a.func_78784_a(0, 0).func_78789_a(-6.0F, 10.0F, -8.0F, 12, 12, 16);
      this.field_178710_a.func_78784_a(0, 28).func_78789_a(-8.0F, 10.0F, -6.0F, 2, 12, 12);
      this.field_178710_a.func_78784_a(0, 28).func_178769_a(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
      this.field_178710_a.func_78784_a(16, 40).func_78789_a(-6.0F, 8.0F, -6.0F, 12, 2, 12);
      this.field_178710_a.func_78784_a(16, 40).func_78789_a(-6.0F, 22.0F, -6.0F, 12, 2, 12);

      for(int ☃ = 0; ☃ < this.field_178709_c.length; ++☃) {
         this.field_178709_c[☃] = new ModelRenderer(this, 0, 0);
         this.field_178709_c[☃].func_78789_a(-1.0F, -4.5F, -1.0F, 2, 9, 2);
         this.field_178710_a.func_78792_a(this.field_178709_c[☃]);
      }

      this.field_178708_b = new ModelRenderer(this, 8, 0);
      this.field_178708_b.func_78789_a(-1.0F, 15.0F, 0.0F, 2, 2, 1);
      this.field_178710_a.func_78792_a(this.field_178708_b);
      this.field_178707_d = new ModelRenderer[3];
      this.field_178707_d[0] = new ModelRenderer(this, 40, 0);
      this.field_178707_d[0].func_78789_a(-2.0F, 14.0F, 7.0F, 4, 4, 8);
      this.field_178707_d[1] = new ModelRenderer(this, 0, 54);
      this.field_178707_d[1].func_78789_a(0.0F, 14.0F, 0.0F, 3, 3, 7);
      this.field_178707_d[2] = new ModelRenderer(this);
      this.field_178707_d[2].func_78784_a(41, 32).func_78789_a(0.0F, 14.0F, 0.0F, 2, 2, 6);
      this.field_178707_d[2].func_78784_a(25, 19).func_78789_a(1.0F, 10.5F, 3.0F, 1, 9, 9);
      this.field_178710_a.func_78792_a(this.field_178707_d[0]);
      this.field_178707_d[0].func_78792_a(this.field_178707_d[1]);
      this.field_178707_d[1].func_78792_a(this.field_178707_d[2]);
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_178710_a.func_78785_a(☃);
   }

   @Override
   public void func_78087_a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      EntityGuardian ☃ = (EntityGuardian)☃;
      float ☃x = ☃ - (float)☃.field_70173_aa;
      this.field_178710_a.field_78796_g = ☃ * (float) (Math.PI / 180.0);
      this.field_178710_a.field_78795_f = ☃ * (float) (Math.PI / 180.0);
      float[] ☃xx = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
      float[] ☃xxx = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
      float[] ☃xxxx = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
      float[] ☃xxxxx = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
      float[] ☃xxxxxx = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
      float[] ☃xxxxxxx = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
      float ☃xxxxxxxx = (1.0F - ☃.func_175469_o(☃x)) * 0.55F;

      for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < 12; ++☃xxxxxxxxx) {
         this.field_178709_c[☃xxxxxxxxx].field_78795_f = (float) Math.PI * ☃xx[☃xxxxxxxxx];
         this.field_178709_c[☃xxxxxxxxx].field_78796_g = (float) Math.PI * ☃xxx[☃xxxxxxxxx];
         this.field_178709_c[☃xxxxxxxxx].field_78808_h = (float) Math.PI * ☃xxxx[☃xxxxxxxxx];
         this.field_178709_c[☃xxxxxxxxx].field_78800_c = ☃xxxxx[☃xxxxxxxxx]
            * (1.0F + MathHelper.func_76134_b(☃ * 1.5F + (float)☃xxxxxxxxx) * 0.01F - ☃xxxxxxxx);
         this.field_178709_c[☃xxxxxxxxx].field_78797_d = 16.0F
            + ☃xxxxxx[☃xxxxxxxxx] * (1.0F + MathHelper.func_76134_b(☃ * 1.5F + (float)☃xxxxxxxxx) * 0.01F - ☃xxxxxxxx);
         this.field_178709_c[☃xxxxxxxxx].field_78798_e = ☃xxxxxxx[☃xxxxxxxxx]
            * (1.0F + MathHelper.func_76134_b(☃ * 1.5F + (float)☃xxxxxxxxx) * 0.01F - ☃xxxxxxxx);
      }

      this.field_178708_b.field_78798_e = -8.25F;
      Entity ☃xxxxxxxxx = Minecraft.func_71410_x().func_175606_aa();
      if (☃.func_175474_cn()) {
         ☃xxxxxxxxx = ☃.func_175466_co();
      }

      if (☃xxxxxxxxx != null) {
         Vec3d ☃xxxxxxxxx = ☃xxxxxxxxx.func_174824_e(0.0F);
         Vec3d ☃xxxxxxxxxx = ☃.func_174824_e(0.0F);
         double ☃xxxxxxxxxxx = ☃xxxxxxxxx.field_72448_b - ☃xxxxxxxxxx.field_72448_b;
         if (☃xxxxxxxxxxx > 0.0) {
            this.field_178708_b.field_78797_d = 0.0F;
         } else {
            this.field_178708_b.field_78797_d = 1.0F;
         }

         Vec3d ☃xxxxxxxxx = ☃.func_70676_i(0.0F);
         ☃xxxxxxxxx = new Vec3d(☃xxxxxxxxx.field_72450_a, 0.0, ☃xxxxxxxxx.field_72449_c);
         Vec3d ☃xxxxxxxxxx = new Vec3d(☃xxxxxxxxxx.field_72450_a - ☃xxxxxxxxx.field_72450_a, 0.0, ☃xxxxxxxxxx.field_72449_c - ☃xxxxxxxxx.field_72449_c)
            .func_72432_b()
            .func_178785_b((float) (Math.PI / 2));
         double ☃xxxxxxxxxxx = ☃xxxxxxxxx.func_72430_b(☃xxxxxxxxxx);
         this.field_178708_b.field_78800_c = MathHelper.func_76129_c((float)Math.abs(☃xxxxxxxxxxx)) * 2.0F * (float)Math.signum(☃xxxxxxxxxxx);
      }

      this.field_178708_b.field_78806_j = true;
      float ☃xxxxxxxxx = ☃.func_175471_a(☃x);
      this.field_178707_d[0].field_78796_g = MathHelper.func_76126_a(☃xxxxxxxxx) * (float) Math.PI * 0.05F;
      this.field_178707_d[1].field_78796_g = MathHelper.func_76126_a(☃xxxxxxxxx) * (float) Math.PI * 0.1F;
      this.field_178707_d[1].field_78800_c = -1.5F;
      this.field_178707_d[1].field_78797_d = 0.5F;
      this.field_178707_d[1].field_78798_e = 14.0F;
      this.field_178707_d[2].field_78796_g = MathHelper.func_76126_a(☃xxxxxxxxx) * (float) Math.PI * 0.15F;
      this.field_178707_d[2].field_78800_c = 0.5F;
      this.field_178707_d[2].field_78797_d = 0.5F;
      this.field_178707_d[2].field_78798_e = 6.0F;
   }
}
