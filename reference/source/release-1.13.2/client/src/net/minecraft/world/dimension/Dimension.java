package net.minecraft.world.dimension;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class Dimension {
   public static final float[] field_111203_a = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   protected World field_76579_a;
   protected boolean field_76575_d;
   protected boolean field_76576_e;
   protected boolean field_191067_f;
   protected final float[] field_76573_f = new float[16];
   private final float[] field_76580_h = new float[4];

   public final void func_76558_a(World var1) {
      this.field_76579_a = ☃;
      this.func_76572_b();
      this.func_76556_a();
   }

   protected void func_76556_a() {
      float ☃ = 0.0F;

      for(int ☃x = 0; ☃x <= 15; ++☃x) {
         float ☃xx = 1.0F - (float)☃x / 15.0F;
         this.field_76573_f[☃x] = (1.0F - ☃xx) / (☃xx * 3.0F + 1.0F) * 1.0F + 0.0F;
      }
   }

   public int func_76559_b(long var1) {
      return (int)(☃ / 24000L % 8L + 8L) % 8;
   }

   @Nullable
   public float[] func_76560_a(float var1, float var2) {
      float ☃ = 0.4F;
      float ☃x = MathHelper.func_76134_b(☃ * (float) (Math.PI * 2)) - 0.0F;
      float ☃xx = -0.0F;
      if (☃x >= -0.4F && ☃x <= 0.4F) {
         float ☃xxx = (☃x - -0.0F) / 0.4F * 0.5F + 0.5F;
         float ☃xxxx = 1.0F - (1.0F - MathHelper.func_76126_a(☃xxx * (float) Math.PI)) * 0.99F;
         ☃xxxx *= ☃xxxx;
         this.field_76580_h[0] = ☃xxx * 0.3F + 0.7F;
         this.field_76580_h[1] = ☃xxx * ☃xxx * 0.7F + 0.2F;
         this.field_76580_h[2] = ☃xxx * ☃xxx * 0.0F + 0.2F;
         this.field_76580_h[3] = ☃xxxx;
         return this.field_76580_h;
      } else {
         return null;
      }
   }

   public float func_76571_f() {
      return 128.0F;
   }

   public boolean func_76561_g() {
      return true;
   }

   @Nullable
   public BlockPos func_177496_h() {
      return null;
   }

   public double func_76565_k() {
      return this.field_76579_a.func_72912_H().func_76067_t() == WorldType.field_77138_c ? 1.0 : 0.03125;
   }

   public boolean func_177500_n() {
      return this.field_76575_d;
   }

   public boolean func_191066_m() {
      return this.field_191067_f;
   }

   public boolean func_177495_o() {
      return this.field_76576_e;
   }

   public float[] func_177497_p() {
      return this.field_76573_f;
   }

   public WorldBorder func_177501_r() {
      return new WorldBorder();
   }

   public void func_186061_a(EntityPlayerMP var1) {
   }

   public void func_186062_b(EntityPlayerMP var1) {
   }

   public void func_186057_q() {
   }

   public void func_186059_r() {
   }

   public boolean func_186056_c(int var1, int var2) {
      return !this.field_76579_a.func_212416_f(☃, ☃);
   }

   protected abstract void func_76572_b();

   public abstract IChunkGenerator<?> func_186060_c();

   @Nullable
   public abstract BlockPos func_206920_a(ChunkPos var1, boolean var2);

   @Nullable
   public abstract BlockPos func_206921_a(int var1, int var2, boolean var3);

   public abstract float func_76563_a(long var1, float var3);

   public abstract boolean func_76569_d();

   public abstract Vec3d func_76562_b(float var1, float var2);

   public abstract boolean func_76567_e();

   public abstract boolean func_76568_b(int var1, int var2);

   public abstract DimensionType func_186058_p();
}
