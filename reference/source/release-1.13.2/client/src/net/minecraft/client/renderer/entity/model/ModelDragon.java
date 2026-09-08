package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;

public class ModelDragon extends ModelBase {
   private final ModelRenderer field_78221_a;
   private final ModelRenderer field_78219_b;
   private final ModelRenderer field_78220_c;
   private final ModelRenderer field_78217_d;
   private final ModelRenderer field_78218_e;
   private final ModelRenderer field_78215_f;
   private final ModelRenderer field_78216_g;
   private final ModelRenderer field_78226_h;
   private final ModelRenderer field_78227_i;
   private final ModelRenderer field_78224_j;
   private final ModelRenderer field_78225_k;
   private final ModelRenderer field_78222_l;
   private float field_78223_m;

   public ModelDragon(float var1) {
      this.field_78090_t = 256;
      this.field_78089_u = 256;
      this.func_78085_a("body.body", 0, 0);
      this.func_78085_a("wing.skin", -56, 88);
      this.func_78085_a("wingtip.skin", -56, 144);
      this.func_78085_a("rearleg.main", 0, 0);
      this.func_78085_a("rearfoot.main", 112, 0);
      this.func_78085_a("rearlegtip.main", 196, 0);
      this.func_78085_a("head.upperhead", 112, 30);
      this.func_78085_a("wing.bone", 112, 88);
      this.func_78085_a("head.upperlip", 176, 44);
      this.func_78085_a("jaw.jaw", 176, 65);
      this.func_78085_a("frontleg.main", 112, 104);
      this.func_78085_a("wingtip.bone", 112, 136);
      this.func_78085_a("frontfoot.main", 144, 104);
      this.func_78085_a("neck.box", 192, 104);
      this.func_78085_a("frontlegtip.main", 226, 138);
      this.func_78085_a("body.scale", 220, 53);
      this.func_78085_a("head.scale", 0, 0);
      this.func_78085_a("neck.scale", 48, 0);
      this.func_78085_a("head.nostril", 112, 0);
      float ☃ = -16.0F;
      this.field_78221_a = new ModelRenderer(this, "head");
      this.field_78221_a.func_78786_a("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16);
      this.field_78221_a.func_78786_a("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16);
      this.field_78221_a.field_78809_i = true;
      this.field_78221_a.func_78786_a("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6);
      this.field_78221_a.func_78786_a("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4);
      this.field_78221_a.field_78809_i = false;
      this.field_78221_a.func_78786_a("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6);
      this.field_78221_a.func_78786_a("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4);
      this.field_78220_c = new ModelRenderer(this, "jaw");
      this.field_78220_c.func_78793_a(0.0F, 4.0F, -8.0F);
      this.field_78220_c.func_78786_a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.field_78221_a.func_78792_a(this.field_78220_c);
      this.field_78219_b = new ModelRenderer(this, "neck");
      this.field_78219_b.func_78786_a("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.field_78219_b.func_78786_a("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
      this.field_78217_d = new ModelRenderer(this, "body");
      this.field_78217_d.func_78793_a(0.0F, 4.0F, 8.0F);
      this.field_78217_d.func_78786_a("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
      this.field_78217_d.func_78786_a("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
      this.field_78217_d.func_78786_a("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
      this.field_78217_d.func_78786_a("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
      this.field_78225_k = new ModelRenderer(this, "wing");
      this.field_78225_k.func_78793_a(-12.0F, 5.0F, 2.0F);
      this.field_78225_k.func_78786_a("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.field_78225_k.func_78786_a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.field_78222_l = new ModelRenderer(this, "wingtip");
      this.field_78222_l.func_78793_a(-56.0F, 0.0F, 0.0F);
      this.field_78222_l.func_78786_a("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.field_78222_l.func_78786_a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.field_78225_k.func_78792_a(this.field_78222_l);
      this.field_78215_f = new ModelRenderer(this, "frontleg");
      this.field_78215_f.func_78793_a(-12.0F, 20.0F, 2.0F);
      this.field_78215_f.func_78786_a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
      this.field_78226_h = new ModelRenderer(this, "frontlegtip");
      this.field_78226_h.func_78793_a(0.0F, 20.0F, -1.0F);
      this.field_78226_h.func_78786_a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
      this.field_78215_f.func_78792_a(this.field_78226_h);
      this.field_78224_j = new ModelRenderer(this, "frontfoot");
      this.field_78224_j.func_78793_a(0.0F, 23.0F, 0.0F);
      this.field_78224_j.func_78786_a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
      this.field_78226_h.func_78792_a(this.field_78224_j);
      this.field_78218_e = new ModelRenderer(this, "rearleg");
      this.field_78218_e.func_78793_a(-16.0F, 16.0F, 42.0F);
      this.field_78218_e.func_78786_a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
      this.field_78216_g = new ModelRenderer(this, "rearlegtip");
      this.field_78216_g.func_78793_a(0.0F, 32.0F, -4.0F);
      this.field_78216_g.func_78786_a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
      this.field_78218_e.func_78792_a(this.field_78216_g);
      this.field_78227_i = new ModelRenderer(this, "rearfoot");
      this.field_78227_i.func_78793_a(0.0F, 31.0F, 4.0F);
      this.field_78227_i.func_78786_a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
      this.field_78216_g.func_78792_a(this.field_78227_i);
   }

   @Override
   public void func_78086_a(EntityLivingBase var1, float var2, float var3, float var4) {
      this.field_78223_m = ☃;
   }

   @Override
   public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.func_179094_E();
      EntityDragon ☃ = (EntityDragon)☃;
      float ☃x = ☃.field_70991_bC + (☃.field_70988_bD - ☃.field_70991_bC) * this.field_78223_m;
      this.field_78220_c.field_78795_f = (float)(Math.sin((double)(☃x * (float) (Math.PI * 2))) + 1.0) * 0.2F;
      float ☃xx = (float)(Math.sin((double)(☃x * (float) (Math.PI * 2) - 1.0F)) + 1.0);
      ☃xx = (☃xx * ☃xx + ☃xx * 2.0F) * 0.05F;
      GlStateManager.func_179109_b(0.0F, ☃xx - 2.0F, -3.0F);
      GlStateManager.func_179114_b(☃xx * 2.0F, 1.0F, 0.0F, 0.0F);
      float ☃xxx = 0.0F;
      float ☃xxxx = 20.0F;
      float ☃xxxxx = -12.0F;
      float ☃xxxxxx = 1.5F;
      double[] ☃xxxxxxx = ☃.func_70974_a(6, this.field_78223_m);
      float ☃xxxxxxxx = this.func_78214_a(☃.func_70974_a(5, this.field_78223_m)[0] - ☃.func_70974_a(10, this.field_78223_m)[0]);
      float ☃xxxxxxxxx = this.func_78214_a(☃.func_70974_a(5, this.field_78223_m)[0] + (double)(☃xxxxxxxx / 2.0F));
      float ☃xxxxxxxxxx = ☃x * (float) (Math.PI * 2);

      for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < 5; ++☃xxxxxxxxxxx) {
         double[] ☃xxxxxxxxxxxx = ☃.func_70974_a(5 - ☃xxxxxxxxxxx, this.field_78223_m);
         float ☃xxxxxxxxxxxxx = (float)Math.cos((double)((float)☃xxxxxxxxxxx * 0.45F + ☃xxxxxxxxxx)) * 0.15F;
         this.field_78219_b.field_78796_g = this.func_78214_a(☃xxxxxxxxxxxx[0] - ☃xxxxxxx[0]) * (float) (Math.PI / 180.0) * 1.5F;
         this.field_78219_b.field_78795_f = ☃xxxxxxxxxxxxx + ☃.func_184667_a(☃xxxxxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
         this.field_78219_b.field_78808_h = -this.func_78214_a(☃xxxxxxxxxxxx[0] - (double)☃xxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
         this.field_78219_b.field_78797_d = ☃xxxx;
         this.field_78219_b.field_78798_e = ☃xxxxx;
         this.field_78219_b.field_78800_c = ☃xxx;
         ☃xxxx = (float)((double)☃xxxx + Math.sin((double)this.field_78219_b.field_78795_f) * 10.0);
         ☃xxxxx = (float)((double)☃xxxxx - Math.cos((double)this.field_78219_b.field_78796_g) * Math.cos((double)this.field_78219_b.field_78795_f) * 10.0);
         ☃xxx = (float)((double)☃xxx - Math.sin((double)this.field_78219_b.field_78796_g) * Math.cos((double)this.field_78219_b.field_78795_f) * 10.0);
         this.field_78219_b.func_78785_a(☃);
      }

      this.field_78221_a.field_78797_d = ☃xxxx;
      this.field_78221_a.field_78798_e = ☃xxxxx;
      this.field_78221_a.field_78800_c = ☃xxx;
      double[] ☃xxxxxxxxxxx = ☃.func_70974_a(0, this.field_78223_m);
      this.field_78221_a.field_78796_g = this.func_78214_a(☃xxxxxxxxxxx[0] - ☃xxxxxxx[0]) * (float) (Math.PI / 180.0);
      this.field_78221_a.field_78795_f = this.func_78214_a((double)☃.func_184667_a(6, ☃xxxxxxx, ☃xxxxxxxxxxx)) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
      this.field_78221_a.field_78808_h = -this.func_78214_a(☃xxxxxxxxxxx[0] - (double)☃xxxxxxxxx) * (float) (Math.PI / 180.0);
      this.field_78221_a.func_78785_a(☃);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-☃xxxxxxxx * 1.5F, 0.0F, 0.0F, 1.0F);
      GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F);
      this.field_78217_d.field_78808_h = 0.0F;
      this.field_78217_d.func_78785_a(☃);

      for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < 2; ++☃xxxxxxxxxxxx) {
         GlStateManager.func_179089_o();
         float ☃xxxxxxxxxxxxx = ☃x * (float) (Math.PI * 2);
         this.field_78225_k.field_78795_f = 0.125F - (float)Math.cos((double)☃xxxxxxxxxxxxx) * 0.2F;
         this.field_78225_k.field_78796_g = 0.25F;
         this.field_78225_k.field_78808_h = (float)(Math.sin((double)☃xxxxxxxxxxxxx) + 0.125) * 0.8F;
         this.field_78222_l.field_78808_h = -((float)(Math.sin((double)(☃xxxxxxxxxxxxx + 2.0F)) + 0.5)) * 0.75F;
         this.field_78218_e.field_78795_f = 1.0F + ☃xx * 0.1F;
         this.field_78216_g.field_78795_f = 0.5F + ☃xx * 0.1F;
         this.field_78227_i.field_78795_f = 0.75F + ☃xx * 0.1F;
         this.field_78215_f.field_78795_f = 1.3F + ☃xx * 0.1F;
         this.field_78226_h.field_78795_f = -0.5F - ☃xx * 0.1F;
         this.field_78224_j.field_78795_f = 0.75F + ☃xx * 0.1F;
         this.field_78225_k.func_78785_a(☃);
         this.field_78215_f.func_78785_a(☃);
         this.field_78218_e.func_78785_a(☃);
         GlStateManager.func_179152_a(-1.0F, 1.0F, 1.0F);
         if (☃xxxxxxxxxxxx == 0) {
            GlStateManager.func_187407_a(GlStateManager.CullFace.FRONT);
         }
      }

      GlStateManager.func_179121_F();
      GlStateManager.func_187407_a(GlStateManager.CullFace.BACK);
      GlStateManager.func_179129_p();
      float ☃xxxxxxxxxxxx = -((float)Math.sin((double)(☃x * (float) (Math.PI * 2)))) * 0.0F;
      ☃xxxxxxxxxx = ☃x * (float) (Math.PI * 2);
      ☃xxxx = 10.0F;
      ☃xxxxx = 60.0F;
      ☃xxx = 0.0F;
      ☃xxxxxxx = ☃.func_70974_a(11, this.field_78223_m);

      for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < 12; ++☃xxxxxxxxxxxxx) {
         ☃xxxxxxxxxxx = ☃.func_70974_a(12 + ☃xxxxxxxxxxxxx, this.field_78223_m);
         ☃xxxxxxxxxxxx = (float)((double)☃xxxxxxxxxxxx + Math.sin((double)((float)☃xxxxxxxxxxxxx * 0.45F + ☃xxxxxxxxxx)) * 0.05F);
         this.field_78219_b.field_78796_g = (this.func_78214_a(☃xxxxxxxxxxx[0] - ☃xxxxxxx[0]) * 1.5F + 180.0F) * (float) (Math.PI / 180.0);
         this.field_78219_b.field_78795_f = ☃xxxxxxxxxxxx + (float)(☃xxxxxxxxxxx[1] - ☃xxxxxxx[1]) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
         this.field_78219_b.field_78808_h = this.func_78214_a(☃xxxxxxxxxxx[0] - (double)☃xxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
         this.field_78219_b.field_78797_d = ☃xxxx;
         this.field_78219_b.field_78798_e = ☃xxxxx;
         this.field_78219_b.field_78800_c = ☃xxx;
         ☃xxxx = (float)((double)☃xxxx + Math.sin((double)this.field_78219_b.field_78795_f) * 10.0);
         ☃xxxxx = (float)((double)☃xxxxx - Math.cos((double)this.field_78219_b.field_78796_g) * Math.cos((double)this.field_78219_b.field_78795_f) * 10.0);
         ☃xxx = (float)((double)☃xxx - Math.sin((double)this.field_78219_b.field_78796_g) * Math.cos((double)this.field_78219_b.field_78795_f) * 10.0);
         this.field_78219_b.func_78785_a(☃);
      }

      GlStateManager.func_179121_F();
   }

   private float func_78214_a(double var1) {
      while(☃ >= 180.0) {
         ☃ -= 360.0;
      }

      while(☃ < -180.0) {
         ☃ += 360.0;
      }

      return (float)☃;
   }
}
