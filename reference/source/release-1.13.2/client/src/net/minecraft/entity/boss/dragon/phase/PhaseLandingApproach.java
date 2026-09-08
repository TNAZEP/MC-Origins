package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class PhaseLandingApproach extends PhaseBase {
   private Path field_188683_b;
   private Vec3d field_188684_c;

   public PhaseLandingApproach(EntityDragon var1) {
      super(☃);
   }

   @Override
   public PhaseType<PhaseLandingApproach> func_188652_i() {
      return PhaseType.field_188743_c;
   }

   @Override
   public void func_188660_d() {
      this.field_188683_b = null;
      this.field_188684_c = null;
   }

   @Override
   public void func_188659_c() {
      double ☃ = this.field_188684_c == null
         ? 0.0
         : this.field_188684_c.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
      if (☃ < 100.0 || ☃ > 22500.0 || this.field_188661_a.field_70123_F || this.field_188661_a.field_70124_G) {
         this.func_188681_j();
      }
   }

   @Nullable
   @Override
   public Vec3d func_188650_g() {
      return this.field_188684_c;
   }

   private void func_188681_j() {
      if (this.field_188683_b == null || this.field_188683_b.func_75879_b()) {
         int ☃x = this.field_188661_a.func_184671_o();
         BlockPos ☃xx = this.field_188661_a.field_70170_p.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.field_186139_a);
         EntityPlayer ☃xxx = this.field_188661_a.field_70170_p.func_184139_a(☃xx, 128.0, 128.0);
         int ☃;
         if (☃xxx != null) {
            Vec3d ☃xxxx = new Vec3d(☃xxx.field_70165_t, 0.0, ☃xxx.field_70161_v).func_72432_b();
            ☃ = this.field_188661_a.func_184663_l(-☃xxxx.field_72450_a * 40.0, 105.0, -☃xxxx.field_72449_c * 40.0);
         } else {
            ☃ = this.field_188661_a.func_184663_l(40.0, (double)☃xx.func_177956_o(), 0.0);
         }

         PathPoint ☃ = new PathPoint(☃xx.func_177958_n(), ☃xx.func_177956_o(), ☃xx.func_177952_p());
         this.field_188683_b = this.field_188661_a.func_184666_a(☃x, ☃, ☃);
         if (this.field_188683_b != null) {
            this.field_188683_b.func_75875_a();
         }
      }

      this.func_188682_k();
      if (this.field_188683_b != null && this.field_188683_b.func_75879_b()) {
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188744_d);
      }
   }

   private void func_188682_k() {
      if (this.field_188683_b != null && !this.field_188683_b.func_75879_b()) {
         Vec3d ☃ = this.field_188683_b.func_186310_f();
         this.field_188683_b.func_75875_a();
         double ☃x = ☃.field_72450_a;
         double ☃xx = ☃.field_72449_c;

         double ☃;
         do {
            ☃ = ☃.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
         } while(☃ < ☃.field_72448_b);

         this.field_188684_c = new Vec3d(☃x, ☃, ☃xx);
      }
   }
}
