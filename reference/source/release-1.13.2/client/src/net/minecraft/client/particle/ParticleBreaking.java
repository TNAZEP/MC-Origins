package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.world.World;

public class ParticleBreaking extends Particle {
   protected ParticleBreaking(World var1, double var2, double var4, double var6, double var8, double var10, double var12, ItemStack var14) {
      this(☃, ☃, ☃, ☃, ☃);
      this.field_187129_i *= 0.1F;
      this.field_187130_j *= 0.1F;
      this.field_187131_k *= 0.1F;
      this.field_187129_i += ☃;
      this.field_187130_j += ☃;
      this.field_187131_k += ☃;
   }

   protected ParticleBreaking(World var1, double var2, double var4, double var6, ItemStack var8) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.func_187117_a(Minecraft.func_71410_x().func_175599_af().func_175037_a().func_199309_a(☃));
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_70545_g = 1.0F;
      this.field_70544_f /= 2.0F;
   }

   @Override
   public int func_70537_b() {
      return 1;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_94054_b + this.field_70548_b / 4.0F) / 16.0F;
      float ☃x = ☃ + 0.015609375F;
      float ☃xx = ((float)this.field_94055_c + this.field_70549_c / 4.0F) / 16.0F;
      float ☃xxx = ☃xx + 0.015609375F;
      float ☃xxxx = 0.1F * this.field_70544_f;
      if (this.field_187119_C != null) {
         ☃ = this.field_187119_C.func_94214_a((double)(this.field_70548_b / 4.0F * 16.0F));
         ☃x = this.field_187119_C.func_94214_a((double)((this.field_70548_b + 1.0F) / 4.0F * 16.0F));
         ☃xx = this.field_187119_C.func_94207_b((double)(this.field_70549_c / 4.0F * 16.0F));
         ☃xxx = this.field_187119_C.func_94207_b((double)((this.field_70549_c + 1.0F) / 4.0F * 16.0F));
      }

      float ☃ = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)☃ - field_70556_an);
      float ☃x = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)☃ - field_70554_ao);
      float ☃xx = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)☃ - field_70555_ap);
      int ☃xxx = this.func_189214_a(☃);
      int ☃xxxx = ☃xxx >> 16 & 65535;
      int ☃xxxxx = ☃xxx & 65535;
      ☃.func_181662_b((double)(☃ - ☃ * ☃xxxx - ☃ * ☃xxxx), (double)(☃x - ☃ * ☃xxxx), (double)(☃xx - ☃ * ☃xxxx - ☃ * ☃xxxx))
         .func_187315_a((double)☃, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)(☃ - ☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃x + ☃ * ☃xxxx), (double)(☃xx - ☃ * ☃xxxx + ☃ * ☃xxxx))
         .func_187315_a((double)☃, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)(☃ + ☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃x + ☃ * ☃xxxx), (double)(☃xx + ☃ * ☃xxxx + ☃ * ☃xxxx))
         .func_187315_a((double)☃x, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)(☃ + ☃ * ☃xxxx - ☃ * ☃xxxx), (double)(☃x - ☃ * ☃xxxx), (double)(☃xx + ☃ * ☃xxxx - ☃ * ☃xxxx))
         .func_187315_a((double)☃x, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0F)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
   }

   public static class Factory implements IParticleFactory<ItemParticleData> {
      public Particle func_199234_a(ItemParticleData var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleBreaking(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃.func_197556_c());
      }
   }

   public static class SlimeFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleBreaking(☃, ☃, ☃, ☃, new ItemStack(Items.field_151123_aH));
      }
   }

   public static class SnowballFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleBreaking(☃, ☃, ☃, ☃, new ItemStack(Items.field_151126_ay));
      }
   }
}
