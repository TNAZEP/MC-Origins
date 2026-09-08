package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFallingDust extends Particle {
   private final float field_190018_a;
   private final float field_190019_b;

   protected ParticleFallingDust(World var1, double var2, double var4, double var6, float var8, float var9, float var10) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i = 0.0;
      this.field_187130_j = 0.0;
      this.field_187131_k = 0.0;
      this.field_70552_h = ☃;
      this.field_70553_i = ☃;
      this.field_70551_j = ☃;
      float ☃ = 0.9F;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= 0.9F;
      this.field_190018_a = this.field_70544_f;
      this.field_70547_e = (int)(32.0 / (Math.random() * 0.8 + 0.2));
      this.field_70547_e = (int)((float)this.field_70547_e * 0.9F);
      this.field_70547_e = Math.max(this.field_70547_e, 1);
      this.field_190019_b = ((float)Math.random() - 0.5F) * 0.1F;
      this.field_190014_F = (float)Math.random() * (float) (Math.PI * 2);
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e * 32.0F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      this.field_70544_f = this.field_190018_a * ☃;
      super.func_180434_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.field_190015_G = this.field_190014_F;
      this.field_190014_F += (float) Math.PI * this.field_190019_b * 2.0F;
      if (this.field_187132_l) {
         this.field_190015_G = this.field_190014_F = 0.0F;
      }

      this.func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187130_j -= 0.003F;
      this.field_187130_j = Math.max(this.field_187130_j, -0.14F);
   }

   public static class Factory implements IParticleFactory<BlockParticleData> {
      @Nullable
      public Particle func_199234_a(BlockParticleData var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         IBlockState ☃ = ☃.func_197584_c();
         if (!☃.func_196958_f() && ☃.func_185901_i() == EnumBlockRenderType.INVISIBLE) {
            return null;
         } else {
            int ☃ = Minecraft.func_71410_x().func_184125_al().func_189991_a(☃, ☃, new BlockPos(☃, ☃, ☃));
            if (☃.func_177230_c() instanceof BlockFalling) {
               ☃ = ((BlockFalling)☃.func_177230_c()).func_189876_x(☃);
            }

            float ☃ = (float)(☃ >> 16 & 0xFF) / 255.0F;
            float ☃x = (float)(☃ >> 8 & 0xFF) / 255.0F;
            float ☃xx = (float)(☃ & 0xFF) / 255.0F;
            return new ParticleFallingDust(☃, ☃, ☃, ☃, ☃, ☃x, ☃xx);
         }
      }
   }
}
