package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleSweepAttack extends Particle {
   private static final ResourceLocation field_187137_a = new ResourceLocation("textures/entity/sweep.png");
   private static final VertexFormat field_187138_G = new VertexFormat()
      .func_181721_a(DefaultVertexFormats.field_181713_m)
      .func_181721_a(DefaultVertexFormats.field_181715_o)
      .func_181721_a(DefaultVertexFormats.field_181714_n)
      .func_181721_a(DefaultVertexFormats.field_181716_p)
      .func_181721_a(DefaultVertexFormats.field_181717_q)
      .func_181721_a(DefaultVertexFormats.field_181718_r);
   private int field_187139_H;
   private final int field_187140_I;
   private final TextureManager field_187141_J;
   private final float field_187142_K;

   protected ParticleSweepAttack(TextureManager var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187141_J = ☃;
      this.field_187140_I = 4;
      float ☃ = this.field_187136_p.nextFloat() * 0.6F + 0.4F;
      this.field_70552_h = ☃;
      this.field_70553_i = ☃;
      this.field_70551_j = ☃;
      this.field_187142_K = 1.0F - (float)☃ * 0.5F;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      int ☃ = (int)(((float)this.field_187139_H + ☃) * 3.0F / (float)this.field_187140_I);
      if (☃ <= 7) {
         this.field_187141_J.func_110577_a(field_187137_a);
         float ☃x = (float)(☃ % 4) / 4.0F;
         float ☃xx = ☃x + 0.24975F;
         float ☃xxx = (float)(☃ / 2) / 2.0F;
         float ☃xxxx = ☃xxx + 0.4995F;
         float ☃xxxxx = 1.0F * this.field_187142_K;
         float ☃xxxxxx = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)☃ - field_70556_an);
         float ☃xxxxxxx = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)☃ - field_70554_ao);
         float ☃xxxxxxxx = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)☃ - field_70555_ap);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179140_f();
         RenderHelper.func_74518_a();
         ☃.func_181668_a(7, field_187138_G);
         ☃.func_181662_b((double)(☃xxxxxx - ☃ * ☃xxxxx - ☃ * ☃xxxxx), (double)(☃xxxxxxx - ☃ * ☃xxxxx * 0.5F), (double)(☃xxxxxxxx - ☃ * ☃xxxxx - ☃ * ☃xxxxx))
            .func_187315_a((double)☃xx, (double)☃xxxx)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
            .func_187314_a(0, 240)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃.func_181662_b((double)(☃xxxxxx - ☃ * ☃xxxxx + ☃ * ☃xxxxx), (double)(☃xxxxxxx + ☃ * ☃xxxxx * 0.5F), (double)(☃xxxxxxxx - ☃ * ☃xxxxx + ☃ * ☃xxxxx))
            .func_187315_a((double)☃xx, (double)☃xxx)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
            .func_187314_a(0, 240)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃.func_181662_b((double)(☃xxxxxx + ☃ * ☃xxxxx + ☃ * ☃xxxxx), (double)(☃xxxxxxx + ☃ * ☃xxxxx * 0.5F), (double)(☃xxxxxxxx + ☃ * ☃xxxxx + ☃ * ☃xxxxx))
            .func_187315_a((double)☃x, (double)☃xxx)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
            .func_187314_a(0, 240)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃.func_181662_b((double)(☃xxxxxx + ☃ * ☃xxxxx - ☃ * ☃xxxxx), (double)(☃xxxxxxx - ☃ * ☃xxxxx * 0.5F), (double)(☃xxxxxxxx + ☃ * ☃xxxxx - ☃ * ☃xxxxx))
            .func_187315_a((double)☃x, (double)☃xxxx)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
            .func_187314_a(0, 240)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         Tessellator.func_178181_a().func_78381_a();
         GlStateManager.func_179145_e();
      }
   }

   @Override
   public int func_189214_a(float var1) {
      return 61680;
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      ++this.field_187139_H;
      if (this.field_187139_H == this.field_187140_I) {
         this.func_187112_i();
      }
   }

   @Override
   public int func_70537_b() {
      return 3;
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleSweepAttack(Minecraft.func_71410_x().func_110434_K(), ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
