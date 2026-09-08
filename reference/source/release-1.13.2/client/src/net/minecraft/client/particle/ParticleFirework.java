package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFireworkRocket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFirework {
   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         ParticleFirework.Spark ☃ = new ParticleFirework.Spark(☃, ☃, ☃, ☃, ☃, ☃, ☃, Minecraft.func_71410_x().field_71452_i);
         ☃.func_82338_g(0.99F);
         return ☃;
      }
   }

   public static class Overlay extends Particle {
      protected Overlay(World var1, double var2, double var4, double var6) {
         super(☃, ☃, ☃, ☃);
         this.field_70547_e = 4;
      }

      @Override
      public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         float ☃ = 0.25F;
         float ☃x = 0.5F;
         float ☃xx = 0.125F;
         float ☃xxx = 0.375F;
         float ☃xxxx = 7.1F * MathHelper.func_76126_a(((float)this.field_70546_d + ☃ - 1.0F) * 0.25F * (float) Math.PI);
         this.func_82338_g(0.6F - ((float)this.field_70546_d + ☃ - 1.0F) * 0.25F * 0.5F);
         float ☃xxxxx = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)☃ - field_70556_an);
         float ☃xxxxxx = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)☃ - field_70554_ao);
         float ☃xxxxxxx = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)☃ - field_70555_ap);
         int ☃xxxxxxxx = this.func_189214_a(☃);
         int ☃xxxxxxxxx = ☃xxxxxxxx >> 16 & 65535;
         int ☃xxxxxxxxxx = ☃xxxxxxxx & 65535;
         ☃.func_181662_b((double)(☃xxxxx - ☃ * ☃xxxx - ☃ * ☃xxxx), (double)(☃xxxxxx - ☃ * ☃xxxx), (double)(☃xxxxxxx - ☃ * ☃xxxx - ☃ * ☃xxxx))
            .func_187315_a(0.5, 0.375)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
            .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
            .func_181675_d();
         ☃.func_181662_b((double)(☃xxxxx - ☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃xxxxxx + ☃ * ☃xxxx), (double)(☃xxxxxxx - ☃ * ☃xxxx + ☃ * ☃xxxx))
            .func_187315_a(0.5, 0.125)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
            .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
            .func_181675_d();
         ☃.func_181662_b((double)(☃xxxxx + ☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃xxxxxx + ☃ * ☃xxxx), (double)(☃xxxxxxx + ☃ * ☃xxxx + ☃ * ☃xxxx))
            .func_187315_a(0.25, 0.125)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
            .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
            .func_181675_d();
         ☃.func_181662_b((double)(☃xxxxx + ☃ * ☃xxxx - ☃ * ☃xxxx), (double)(☃xxxxxx - ☃ * ☃xxxx), (double)(☃xxxxxxx + ☃ * ☃xxxx - ☃ * ☃xxxx))
            .func_187315_a(0.25, 0.375)
            .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
            .func_187314_a(☃xxxxxxxxx, ☃xxxxxxxxxx)
            .func_181675_d();
      }
   }

   public static class Spark extends ParticleSimpleAnimated {
      private boolean field_92054_ax;
      private boolean field_92048_ay;
      private final ParticleManager field_92047_az;
      private float field_92050_aA;
      private float field_92051_aB;
      private float field_92052_aC;
      private boolean field_92053_aD;

      public Spark(World var1, double var2, double var4, double var6, double var8, double var10, double var12, ParticleManager var14) {
         super(☃, ☃, ☃, ☃, 160, 8, -0.004F);
         this.field_187129_i = ☃;
         this.field_187130_j = ☃;
         this.field_187131_k = ☃;
         this.field_92047_az = ☃;
         this.field_70544_f *= 0.75F;
         this.field_70547_e = 48 + this.field_187136_p.nextInt(12);
      }

      public void func_92045_e(boolean var1) {
         this.field_92054_ax = ☃;
      }

      public void func_92043_f(boolean var1) {
         this.field_92048_ay = ☃;
      }

      @Override
      public boolean func_187111_c() {
         return true;
      }

      @Override
      public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         if (!this.field_92048_ay || this.field_70546_d < this.field_70547_e / 3 || (this.field_70546_d + this.field_70547_e) / 3 % 2 == 0) {
            super.func_180434_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
         }
      }

      @Override
      public void func_189213_a() {
         super.func_189213_a();
         if (this.field_92054_ax && this.field_70546_d < this.field_70547_e / 2 && (this.field_70546_d + this.field_70547_e) % 2 == 0) {
            ParticleFirework.Spark ☃ = new ParticleFirework.Spark(
               this.field_187122_b, this.field_187126_f, this.field_187127_g, this.field_187128_h, 0.0, 0.0, 0.0, this.field_92047_az
            );
            ☃.func_82338_g(0.99F);
            ☃.func_70538_b(this.field_70552_h, this.field_70553_i, this.field_70551_j);
            ☃.field_70546_d = ☃.field_70547_e / 2;
            if (this.field_92053_aD) {
               ☃.field_92053_aD = true;
               ☃.field_92050_aA = this.field_92050_aA;
               ☃.field_92051_aB = this.field_92051_aB;
               ☃.field_92052_aC = this.field_92052_aC;
            }

            ☃.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.func_78873_a(☃);
         }
      }
   }

   public static class Starter extends Particle {
      private int field_92042_ax;
      private final ParticleManager field_92040_ay;
      private NBTTagList field_92039_az;
      private boolean field_92041_a;

      public Starter(
         World var1, double var2, double var4, double var6, double var8, double var10, double var12, ParticleManager var14, @Nullable NBTTagCompound var15
      ) {
         super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
         this.field_187129_i = ☃;
         this.field_187130_j = ☃;
         this.field_187131_k = ☃;
         this.field_92040_ay = ☃;
         this.field_70547_e = 8;
         if (☃ != null) {
            this.field_92039_az = ☃.func_150295_c("Explosions", 10);
            if (this.field_92039_az.isEmpty()) {
               this.field_92039_az = null;
            } else {
               this.field_70547_e = this.field_92039_az.size() * 2 - 1;

               for(int ☃ = 0; ☃ < this.field_92039_az.size(); ++☃) {
                  NBTTagCompound ☃x = this.field_92039_az.func_150305_b(☃);
                  if (☃x.func_74767_n("Flicker")) {
                     this.field_92041_a = true;
                     this.field_70547_e += 15;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      }

      @Override
      public void func_189213_a() {
         if (this.field_92042_ax == 0 && this.field_92039_az != null) {
            boolean ☃ = this.func_92037_i();
            boolean ☃x = false;
            if (this.field_92039_az.size() >= 3) {
               ☃x = true;
            } else {
               for(int ☃ = 0; ☃ < this.field_92039_az.size(); ++☃) {
                  NBTTagCompound ☃x = this.field_92039_az.func_150305_b(☃);
                  if (ItemFireworkRocket.Shape.func_196070_a(☃x.func_74771_c("Type")) == ItemFireworkRocket.Shape.LARGE_BALL) {
                     ☃x = true;
                     break;
                  }
               }
            }

            SoundEvent ☃;
            if (☃x) {
               ☃ = ☃ ? SoundEvents.field_187628_bn : SoundEvents.field_187625_bm;
            } else {
               ☃ = ☃ ? SoundEvents.field_187622_bl : SoundEvents.field_187619_bk;
            }

            this.field_187122_b
               .func_184134_a(
                  this.field_187126_f,
                  this.field_187127_g,
                  this.field_187128_h,
                  ☃,
                  SoundCategory.AMBIENT,
                  20.0F,
                  0.95F + this.field_187136_p.nextFloat() * 0.1F,
                  true
               );
         }

         if (this.field_92042_ax % 2 == 0 && this.field_92039_az != null && this.field_92042_ax / 2 < this.field_92039_az.size()) {
            int ☃ = this.field_92042_ax / 2;
            NBTTagCompound ☃x = this.field_92039_az.func_150305_b(☃);
            ItemFireworkRocket.Shape ☃xx = ItemFireworkRocket.Shape.func_196070_a(☃x.func_74771_c("Type"));
            boolean ☃xxx = ☃x.func_74767_n("Trail");
            boolean ☃xxxx = ☃x.func_74767_n("Flicker");
            int[] ☃xxxxx = ☃x.func_74759_k("Colors");
            int[] ☃xxxxxx = ☃x.func_74759_k("FadeColors");
            if (☃xxxxx.length == 0) {
               ☃xxxxx = new int[]{EnumDyeColor.BLACK.func_196060_f()};
            }

            switch(☃xx) {
               case SMALL_BALL:
               default:
                  this.func_92035_a(0.25, 2, ☃xxxxx, ☃xxxxxx, ☃xxx, ☃xxxx);
                  break;
               case LARGE_BALL:
                  this.func_92035_a(0.5, 4, ☃xxxxx, ☃xxxxxx, ☃xxx, ☃xxxx);
                  break;
               case STAR:
                  this.func_92038_a(
                     0.5,
                     new double[][]{
                        {0.0, 1.0},
                        {0.3455, 0.309},
                        {0.9511, 0.309},
                        {0.3795918367346939, -0.12653061224489795},
                        {0.6122448979591837, -0.8040816326530612},
                        {0.0, -0.35918367346938773}
                     },
                     ☃xxxxx,
                     ☃xxxxxx,
                     ☃xxx,
                     ☃xxxx,
                     false
                  );
                  break;
               case CREEPER:
                  this.func_92038_a(
                     0.5,
                     new double[][]{
                        {0.0, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.6},
                        {0.6, 0.6},
                        {0.6, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.0},
                        {0.4, 0.0},
                        {0.4, -0.6},
                        {0.2, -0.6},
                        {0.2, -0.4},
                        {0.0, -0.4}
                     },
                     ☃xxxxx,
                     ☃xxxxxx,
                     ☃xxx,
                     ☃xxxx,
                     true
                  );
                  break;
               case BURST:
                  this.func_92036_a(☃xxxxx, ☃xxxxxx, ☃xxx, ☃xxxx);
            }

            int ☃ = ☃xxxxx[0];
            float ☃x = (float)((☃ & 0xFF0000) >> 16) / 255.0F;
            float ☃xx = (float)((☃ & 0xFF00) >> 8) / 255.0F;
            float ☃xxx = (float)((☃ & 0xFF) >> 0) / 255.0F;
            ParticleFirework.Overlay ☃xxxx = new ParticleFirework.Overlay(this.field_187122_b, this.field_187126_f, this.field_187127_g, this.field_187128_h);
            ☃xxxx.func_70538_b(☃x, ☃xx, ☃xxx);
            this.field_92040_ay.func_78873_a(☃xxxx);
         }

         ++this.field_92042_ax;
         if (this.field_92042_ax > this.field_70547_e) {
            if (this.field_92041_a) {
               boolean ☃ = this.func_92037_i();
               SoundEvent ☃x = ☃ ? SoundEvents.field_187640_br : SoundEvents.field_187637_bq;
               this.field_187122_b
                  .func_184134_a(
                     this.field_187126_f,
                     this.field_187127_g,
                     this.field_187128_h,
                     ☃x,
                     SoundCategory.AMBIENT,
                     20.0F,
                     0.9F + this.field_187136_p.nextFloat() * 0.15F,
                     true
                  );
            }

            this.func_187112_i();
         }
      }

      private boolean func_92037_i() {
         Minecraft ☃ = Minecraft.func_71410_x();
         return ☃.func_175606_aa() == null || !(☃.func_175606_aa().func_70092_e(this.field_187126_f, this.field_187127_g, this.field_187128_h) < 256.0);
      }

      private void func_92034_a(
         double var1, double var3, double var5, double var7, double var9, double var11, int[] var13, int[] var14, boolean var15, boolean var16
      ) {
         ParticleFirework.Spark ☃ = new ParticleFirework.Spark(this.field_187122_b, ☃, ☃, ☃, ☃, ☃, ☃, this.field_92040_ay);
         ☃.func_82338_g(0.99F);
         ☃.func_92045_e(☃);
         ☃.func_92043_f(☃);
         int ☃x = this.field_187136_p.nextInt(☃.length);
         ☃.func_187146_c(☃[☃x]);
         if (☃.length > 0) {
            ☃.func_187145_d(☃[this.field_187136_p.nextInt(☃.length)]);
         }

         this.field_92040_ay.func_78873_a(☃);
      }

      private void func_92035_a(double var1, int var3, int[] var4, int[] var5, boolean var6, boolean var7) {
         double ☃ = this.field_187126_f;
         double ☃x = this.field_187127_g;
         double ☃xx = this.field_187128_h;

         for(int ☃xxx = -☃; ☃xxx <= ☃; ++☃xxx) {
            for(int ☃xxxx = -☃; ☃xxxx <= ☃; ++☃xxxx) {
               for(int ☃xxxxx = -☃; ☃xxxxx <= ☃; ++☃xxxxx) {
                  double ☃xxxxxx = (double)☃xxxx + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 0.5;
                  double ☃xxxxxxx = (double)☃xxx + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 0.5;
                  double ☃xxxxxxxx = (double)☃xxxxx + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 0.5;
                  double ☃xxxxxxxxx = (double)MathHelper.func_76133_a(☃xxxxxx * ☃xxxxxx + ☃xxxxxxx * ☃xxxxxxx + ☃xxxxxxxx * ☃xxxxxxxx) / ☃
                     + this.field_187136_p.nextGaussian() * 0.05;
                  this.func_92034_a(☃, ☃x, ☃xx, ☃xxxxxx / ☃xxxxxxxxx, ☃xxxxxxx / ☃xxxxxxxxx, ☃xxxxxxxx / ☃xxxxxxxxx, ☃, ☃, ☃, ☃);
                  if (☃xxx != -☃ && ☃xxx != ☃ && ☃xxxx != -☃ && ☃xxxx != ☃) {
                     ☃xxxxx += ☃ * 2 - 1;
                  }
               }
            }
         }
      }

      private void func_92038_a(double var1, double[][] var3, int[] var4, int[] var5, boolean var6, boolean var7, boolean var8) {
         double ☃ = ☃[0][0];
         double ☃x = ☃[0][1];
         this.func_92034_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, ☃ * ☃, ☃x * ☃, 0.0, ☃, ☃, ☃, ☃);
         float ☃xx = this.field_187136_p.nextFloat() * (float) Math.PI;
         double ☃xxx = ☃ ? 0.034 : 0.34;

         for(int ☃xxxx = 0; ☃xxxx < 3; ++☃xxxx) {
            double ☃xxxxx = (double)☃xx + (double)((float)☃xxxx * (float) Math.PI) * ☃xxx;
            double ☃xxxxxx = ☃;
            double ☃xxxxxxx = ☃x;

            for(int ☃xxxxxxxx = 1; ☃xxxxxxxx < ☃.length; ++☃xxxxxxxx) {
               double ☃xxxxxxxxx = ☃[☃xxxxxxxx][0];
               double ☃xxxxxxxxxx = ☃[☃xxxxxxxx][1];

               for(double ☃xxxxxxxxxxx = 0.25; ☃xxxxxxxxxxx <= 1.0; ☃xxxxxxxxxxx += 0.25) {
                  double ☃xxxxxxxxxxxx = (☃xxxxxx + (☃xxxxxxxxx - ☃xxxxxx) * ☃xxxxxxxxxxx) * ☃;
                  double ☃xxxxxxxxxxxxx = (☃xxxxxxx + (☃xxxxxxxxxx - ☃xxxxxxx) * ☃xxxxxxxxxxx) * ☃;
                  double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxx * Math.sin(☃xxxxx);
                  ☃xxxxxxxxxxxx *= Math.cos(☃xxxxx);

                  for(double ☃xxxxxxxxxxxxxxx = -1.0; ☃xxxxxxxxxxxxxxx <= 1.0; ☃xxxxxxxxxxxxxxx += 2.0) {
                     this.func_92034_a(
                        this.field_187126_f,
                        this.field_187127_g,
                        this.field_187128_h,
                        ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx,
                        ☃xxxxxxxxxxxxx,
                        ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx,
                        ☃,
                        ☃,
                        ☃,
                        ☃
                     );
                  }
               }

               ☃xxxxxx = ☃xxxxxxxxx;
               ☃xxxxxxx = ☃xxxxxxxxxx;
            }
         }
      }

      private void func_92036_a(int[] var1, int[] var2, boolean var3, boolean var4) {
         double ☃ = this.field_187136_p.nextGaussian() * 0.05;
         double ☃x = this.field_187136_p.nextGaussian() * 0.05;

         for(int ☃xx = 0; ☃xx < 70; ++☃xx) {
            double ☃xxx = this.field_187129_i * 0.5 + this.field_187136_p.nextGaussian() * 0.15 + ☃;
            double ☃xxxx = this.field_187131_k * 0.5 + this.field_187136_p.nextGaussian() * 0.15 + ☃x;
            double ☃xxxxx = this.field_187130_j * 0.5 + this.field_187136_p.nextDouble() * 0.5;
            this.func_92034_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, ☃xxx, ☃xxxxx, ☃xxxx, ☃, ☃, ☃, ☃);
         }
      }

      @Override
      public int func_70537_b() {
         return 0;
      }
   }
}
