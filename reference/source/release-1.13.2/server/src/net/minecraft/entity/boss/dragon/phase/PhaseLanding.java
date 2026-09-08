package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Particles;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class PhaseLanding extends PhaseBase {
   private Vec3d field_188685_b;

   public PhaseLanding(EntityDragon var1) {
      super(☃);
   }

   @Override
   public void func_188657_b() {
      Vec3d ☃ = this.field_188661_a.func_184665_a(1.0F).func_72432_b();
      ☃.func_178785_b((float) (-Math.PI / 4));
      double ☃x = this.field_188661_a.field_70986_h.field_70165_t;
      double ☃xx = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F);
      double ☃xxx = this.field_188661_a.field_70986_h.field_70161_v;

      for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
         double ☃xxxxx = ☃x + this.field_188661_a.func_70681_au().nextGaussian() / 2.0;
         double ☃xxxxxx = ☃xx + this.field_188661_a.func_70681_au().nextGaussian() / 2.0;
         double ☃xxxxxxx = ☃xxx + this.field_188661_a.func_70681_au().nextGaussian() / 2.0;
         this.field_188661_a
            .field_70170_p
            .func_195594_a(
               Particles.field_197616_i,
               ☃xxxxx,
               ☃xxxxxx,
               ☃xxxxxxx,
               -☃.field_72450_a * 0.08F + this.field_188661_a.field_70159_w,
               -☃.field_72448_b * 0.3F + this.field_188661_a.field_70181_x,
               -☃.field_72449_c * 0.08F + this.field_188661_a.field_70179_y
            );
         ☃.func_178785_b((float) (Math.PI / 16));
      }
   }

   @Override
   public void func_188659_c() {
      if (this.field_188685_b == null) {
         this.field_188685_b = new Vec3d(
            this.field_188661_a.field_70170_p.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.field_186139_a)
         );
      }

      if (this.field_188685_b.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v) < 1.0) {
         this.field_188661_a.func_184670_cT().func_188757_b(PhaseType.field_188746_f).func_188663_j();
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188747_g);
      }
   }

   @Override
   public float func_188651_f() {
      return 1.5F;
   }

   @Override
   public float func_188653_h() {
      float ☃ = MathHelper.func_76133_a(
            this.field_188661_a.field_70159_w * this.field_188661_a.field_70159_w + this.field_188661_a.field_70179_y * this.field_188661_a.field_70179_y
         )
         + 1.0F;
      float ☃x = Math.min(☃, 40.0F);
      return ☃x / ☃;
   }

   @Override
   public void func_188660_d() {
      this.field_188685_b = null;
   }

   @Nullable
   @Override
   public Vec3d func_188650_g() {
      return this.field_188685_b;
   }

   @Override
   public PhaseType<PhaseLanding> func_188652_i() {
      return PhaseType.field_188744_d;
   }
}
