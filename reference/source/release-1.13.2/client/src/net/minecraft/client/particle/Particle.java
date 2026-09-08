package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

public class Particle {
   private static final AxisAlignedBB field_187121_a = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   protected World field_187122_b;
   protected double field_187123_c;
   protected double field_187124_d;
   protected double field_187125_e;
   protected double field_187126_f;
   protected double field_187127_g;
   protected double field_187128_h;
   protected double field_187129_i;
   protected double field_187130_j;
   protected double field_187131_k;
   private AxisAlignedBB field_187120_G = field_187121_a;
   protected boolean field_187132_l;
   protected boolean field_190017_n;
   protected boolean field_187133_m;
   protected float field_187134_n = 0.6F;
   protected float field_187135_o = 1.8F;
   protected Random field_187136_p = new Random();
   protected int field_94054_b;
   protected int field_94055_c;
   protected float field_70548_b;
   protected float field_70549_c;
   protected int field_70546_d;
   protected int field_70547_e;
   protected float field_70544_f;
   protected float field_70545_g;
   protected float field_70552_h;
   protected float field_70553_i;
   protected float field_70551_j;
   protected float field_82339_as = 1.0F;
   protected TextureAtlasSprite field_187119_C;
   protected float field_190014_F;
   protected float field_190015_G;
   public static double field_70556_an;
   public static double field_70554_ao;
   public static double field_70555_ap;
   public static Vec3d field_190016_K;

   protected Particle(World var1, double var2, double var4, double var6) {
      this.field_187122_b = ☃;
      this.func_187115_a(0.2F, 0.2F);
      this.func_187109_b(☃, ☃, ☃);
      this.field_187123_c = ☃;
      this.field_187124_d = ☃;
      this.field_187125_e = ☃;
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_70548_b = this.field_187136_p.nextFloat() * 3.0F;
      this.field_70549_c = this.field_187136_p.nextFloat() * 3.0F;
      this.field_70544_f = (this.field_187136_p.nextFloat() * 0.5F + 0.5F) * 2.0F;
      this.field_70547_e = (int)(4.0F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
      this.field_70546_d = 0;
      this.field_190017_n = true;
   }

   public Particle(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(☃, ☃, ☃, ☃);
      this.field_187129_i = ☃ + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.field_187130_j = ☃ + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.field_187131_k = ☃ + (Math.random() * 2.0 - 1.0) * 0.4F;
      float ☃ = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
      float ☃x = MathHelper.func_76133_a(
         this.field_187129_i * this.field_187129_i + this.field_187130_j * this.field_187130_j + this.field_187131_k * this.field_187131_k
      );
      this.field_187129_i = this.field_187129_i / (double)☃x * (double)☃ * 0.4F;
      this.field_187130_j = this.field_187130_j / (double)☃x * (double)☃ * 0.4F + 0.1F;
      this.field_187131_k = this.field_187131_k / (double)☃x * (double)☃ * 0.4F;
   }

   public Particle func_70543_e(float var1) {
      this.field_187129_i *= (double)☃;
      this.field_187130_j = (this.field_187130_j - 0.1F) * (double)☃ + 0.1F;
      this.field_187131_k *= (double)☃;
      return this;
   }

   public Particle func_70541_f(float var1) {
      this.func_187115_a(0.2F * ☃, 0.2F * ☃);
      this.field_70544_f *= ☃;
      return this;
   }

   public void func_70538_b(float var1, float var2, float var3) {
      this.field_70552_h = ☃;
      this.field_70553_i = ☃;
      this.field_70551_j = ☃;
   }

   public void func_82338_g(float var1) {
      this.field_82339_as = ☃;
   }

   public boolean func_187111_c() {
      return false;
   }

   public float func_70534_d() {
      return this.field_70552_h;
   }

   public float func_70542_f() {
      return this.field_70553_i;
   }

   public float func_70535_g() {
      return this.field_70551_j;
   }

   public void func_187114_a(int var1) {
      this.field_70547_e = ☃;
   }

   public int func_206254_h() {
      return this.field_70547_e;
   }

   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      this.field_187130_j -= 0.04 * (double)this.field_70545_g;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.98F;
      this.field_187130_j *= 0.98F;
      this.field_187131_k *= 0.98F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = (float)this.field_94054_b / 32.0F;
      float ☃x = ☃ + 0.03121875F;
      float ☃xx = (float)this.field_94055_c / 32.0F;
      float ☃xxx = ☃xx + 0.03121875F;
      float ☃xxxx = 0.1F * this.field_70544_f;
      if (this.field_187119_C != null) {
         ☃ = this.field_187119_C.func_94209_e();
         ☃x = this.field_187119_C.func_94212_f();
         ☃xx = this.field_187119_C.func_94206_g();
         ☃xxx = this.field_187119_C.func_94210_h();
      }

      float ☃ = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * (double)☃ - field_70556_an);
      float ☃x = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * (double)☃ - field_70554_ao);
      float ☃xx = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * (double)☃ - field_70555_ap);
      int ☃xxx = this.func_189214_a(☃);
      int ☃xxxx = ☃xxx >> 16 & 65535;
      int ☃xxxxx = ☃xxx & 65535;
      Vec3d[] ☃xxxxxx = new Vec3d[]{
         new Vec3d((double)(-☃ * ☃xxxx - ☃ * ☃xxxx), (double)(-☃ * ☃xxxx), (double)(-☃ * ☃xxxx - ☃ * ☃xxxx)),
         new Vec3d((double)(-☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃ * ☃xxxx), (double)(-☃ * ☃xxxx + ☃ * ☃xxxx)),
         new Vec3d((double)(☃ * ☃xxxx + ☃ * ☃xxxx), (double)(☃ * ☃xxxx), (double)(☃ * ☃xxxx + ☃ * ☃xxxx)),
         new Vec3d((double)(☃ * ☃xxxx - ☃ * ☃xxxx), (double)(-☃ * ☃xxxx), (double)(☃ * ☃xxxx - ☃ * ☃xxxx))
      };
      if (this.field_190014_F != 0.0F) {
         float ☃xxxxxxx = this.field_190014_F + (this.field_190014_F - this.field_190015_G) * ☃;
         float ☃xxxxxxxx = MathHelper.func_76134_b(☃xxxxxxx * 0.5F);
         float ☃xxxxxxxxx = MathHelper.func_76126_a(☃xxxxxxx * 0.5F) * (float)field_190016_K.field_72450_a;
         float ☃xxxxxxxxxx = MathHelper.func_76126_a(☃xxxxxxx * 0.5F) * (float)field_190016_K.field_72448_b;
         float ☃xxxxxxxxxxx = MathHelper.func_76126_a(☃xxxxxxx * 0.5F) * (float)field_190016_K.field_72449_c;
         Vec3d ☃xxxxxxxxxxxx = new Vec3d((double)☃xxxxxxxxx, (double)☃xxxxxxxxxx, (double)☃xxxxxxxxxxx);

         for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxx) {
            ☃xxxxxx[☃xxxxxxxxxxxxx] = ☃xxxxxxxxxxxx.func_186678_a(2.0 * ☃xxxxxx[☃xxxxxxxxxxxxx].func_72430_b(☃xxxxxxxxxxxx))
               .func_178787_e(☃xxxxxx[☃xxxxxxxxxxxxx].func_186678_a((double)(☃xxxxxxxx * ☃xxxxxxxx) - ☃xxxxxxxxxxxx.func_72430_b(☃xxxxxxxxxxxx)))
               .func_178787_e(☃xxxxxxxxxxxx.func_72431_c(☃xxxxxx[☃xxxxxxxxxxxxx]).func_186678_a((double)(2.0F * ☃xxxxxxxx)));
         }
      }

      ☃.func_181662_b((double)☃ + ☃xxxxxx[0].field_72450_a, (double)☃x + ☃xxxxxx[0].field_72448_b, (double)☃xx + ☃xxxxxx[0].field_72449_c)
         .func_187315_a((double)☃x, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)☃ + ☃xxxxxx[1].field_72450_a, (double)☃x + ☃xxxxxx[1].field_72448_b, (double)☃xx + ☃xxxxxx[1].field_72449_c)
         .func_187315_a((double)☃x, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)☃ + ☃xxxxxx[2].field_72450_a, (double)☃x + ☃xxxxxx[2].field_72448_b, (double)☃xx + ☃xxxxxx[2].field_72449_c)
         .func_187315_a((double)☃, (double)☃xx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
      ☃.func_181662_b((double)☃ + ☃xxxxxx[3].field_72450_a, (double)☃x + ☃xxxxxx[3].field_72448_b, (double)☃xx + ☃xxxxxx[3].field_72449_c)
         .func_187315_a((double)☃, (double)☃xxx)
         .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
         .func_187314_a(☃xxxx, ☃xxxxx)
         .func_181675_d();
   }

   public int func_70537_b() {
      return 0;
   }

   public void func_187117_a(TextureAtlasSprite var1) {
      int ☃ = this.func_70537_b();
      if (☃ == 1) {
         this.field_187119_C = ☃;
      } else {
         throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
      }
   }

   public void func_70536_a(int var1) {
      if (this.func_70537_b() != 0) {
         throw new RuntimeException("Invalid call to Particle.setMiscTex");
      } else {
         this.field_94054_b = ☃ % 16;
         this.field_94055_c = ☃ / 16;
      }
   }

   public void func_94053_h() {
      ++this.field_94054_b;
   }

   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.field_187126_f
         + ","
         + this.field_187127_g
         + ","
         + this.field_187128_h
         + "), RGBA ("
         + this.field_70552_h
         + ","
         + this.field_70553_i
         + ","
         + this.field_70551_j
         + ","
         + this.field_82339_as
         + "), Age "
         + this.field_70546_d;
   }

   public void func_187112_i() {
      this.field_187133_m = true;
   }

   protected void func_187115_a(float var1, float var2) {
      if (☃ != this.field_187134_n || ☃ != this.field_187135_o) {
         this.field_187134_n = ☃;
         this.field_187135_o = ☃;
         AxisAlignedBB ☃ = this.func_187116_l();
         double ☃x = (☃.field_72340_a + ☃.field_72336_d - (double)☃) / 2.0;
         double ☃xx = (☃.field_72339_c + ☃.field_72334_f - (double)☃) / 2.0;
         this.func_187108_a(
            new AxisAlignedBB(
               ☃x, ☃.field_72338_b, ☃xx, ☃x + (double)this.field_187134_n, ☃.field_72338_b + (double)this.field_187135_o, ☃xx + (double)this.field_187134_n
            )
         );
      }
   }

   public void func_187109_b(double var1, double var3, double var5) {
      this.field_187126_f = ☃;
      this.field_187127_g = ☃;
      this.field_187128_h = ☃;
      float ☃ = this.field_187134_n / 2.0F;
      float ☃x = this.field_187135_o;
      this.func_187108_a(new AxisAlignedBB(☃ - (double)☃, ☃, ☃ - (double)☃, ☃ + (double)☃, ☃ + (double)☃x, ☃ + (double)☃));
   }

   public void func_187110_a(double var1, double var3, double var5) {
      double ☃ = ☃;
      double ☃x = ☃;
      double ☃xx = ☃;
      if (this.field_190017_n && (☃ != 0.0 || ☃ != 0.0 || ☃ != 0.0)) {
         ReuseableStream<VoxelShape> ☃xxx = new ReuseableStream<>(this.field_187122_b.func_199406_a(null, this.func_187116_l(), ☃, ☃, ☃));
         ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.Y, this.func_187116_l(), ☃xxx.func_212761_a(), ☃);
         this.func_187108_a(this.func_187116_l().func_72317_d(0.0, ☃, 0.0));
         ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.X, this.func_187116_l(), ☃xxx.func_212761_a(), ☃);
         if (☃ != 0.0) {
            this.func_187108_a(this.func_187116_l().func_72317_d(☃, 0.0, 0.0));
         }

         ☃ = VoxelShapes.func_212437_a(EnumFacing.Axis.Z, this.func_187116_l(), ☃xxx.func_212761_a(), ☃);
         if (☃ != 0.0) {
            this.func_187108_a(this.func_187116_l().func_72317_d(0.0, 0.0, ☃));
         }
      } else {
         this.func_187108_a(this.func_187116_l().func_72317_d(☃, ☃, ☃));
      }

      this.func_187118_j();
      this.field_187132_l = ☃x != ☃ && ☃x < 0.0;
      if (☃ != ☃) {
         this.field_187129_i = 0.0;
      }

      if (☃xx != ☃) {
         this.field_187131_k = 0.0;
      }
   }

   protected void func_187118_j() {
      AxisAlignedBB ☃ = this.func_187116_l();
      this.field_187126_f = (☃.field_72340_a + ☃.field_72336_d) / 2.0;
      this.field_187127_g = ☃.field_72338_b;
      this.field_187128_h = (☃.field_72339_c + ☃.field_72334_f) / 2.0;
   }

   public int func_189214_a(float var1) {
      BlockPos ☃ = new BlockPos(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      return this.field_187122_b.func_175667_e(☃) ? this.field_187122_b.func_175626_b(☃, 0) : 0;
   }

   public boolean func_187113_k() {
      return !this.field_187133_m;
   }

   public AxisAlignedBB func_187116_l() {
      return this.field_187120_G;
   }

   public void func_187108_a(AxisAlignedBB var1) {
      this.field_187120_G = ☃;
   }
}
