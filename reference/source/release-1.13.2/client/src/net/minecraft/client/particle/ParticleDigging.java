package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleDigging extends Particle {
   private final IBlockState field_174847_a;
   private BlockPos field_181019_az;

   protected ParticleDigging(World var1, double var2, double var4, double var6, double var8, double var10, double var12, IBlockState var14) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_174847_a = ☃;
      this.func_187117_a(Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178122_a(☃));
      this.field_70545_g = 1.0F;
      this.field_70552_h = 0.6F;
      this.field_70553_i = 0.6F;
      this.field_70551_j = 0.6F;
      this.field_70544_f /= 2.0F;
   }

   public ParticleDigging func_174846_a(BlockPos var1) {
      this.field_181019_az = ☃;
      if (this.field_174847_a.func_177230_c() == Blocks.field_196658_i) {
         return this;
      } else {
         this.func_187154_b(☃);
         return this;
      }
   }

   public ParticleDigging func_174845_l() {
      this.field_181019_az = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      Block ☃ = this.field_174847_a.func_177230_c();
      if (☃ == Blocks.field_196658_i) {
         return this;
      } else {
         this.func_187154_b(this.field_181019_az);
         return this;
      }
   }

   protected void func_187154_b(@Nullable BlockPos var1) {
      int ☃ = Minecraft.func_71410_x().func_184125_al().func_186724_a(this.field_174847_a, this.field_187122_b, ☃, 0);
      this.field_70552_h *= (float)(☃ >> 16 & 0xFF) / 255.0F;
      this.field_70553_i *= (float)(☃ >> 8 & 0xFF) / 255.0F;
      this.field_70551_j *= (float)(☃ & 0xFF) / 255.0F;
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

   @Override
   public int func_189214_a(float var1) {
      int ☃ = super.func_189214_a(☃);
      int ☃x = 0;
      if (this.field_187122_b.func_175667_e(this.field_181019_az)) {
         ☃x = this.field_187122_b.func_175626_b(this.field_181019_az, 0);
      }

      return ☃ == 0 ? ☃x : ☃;
   }

   public static class Factory implements IParticleFactory<BlockParticleData> {
      public Particle func_199234_a(BlockParticleData var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         IBlockState ☃ = ☃.func_197584_c();
         return !☃.func_196958_f() && ☃.func_177230_c() != Blocks.field_196603_bb ? new ParticleDigging(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃).func_174845_l() : null;
      }
   }
}
