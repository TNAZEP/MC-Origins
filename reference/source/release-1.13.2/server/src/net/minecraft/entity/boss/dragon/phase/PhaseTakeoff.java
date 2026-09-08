package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;

public class PhaseTakeoff extends PhaseBase {
   private boolean field_188697_b;
   private Path field_188698_c;
   private Vec3d field_188699_d;

   public PhaseTakeoff(EntityDragon var1) {
      super(☃);
   }

   @Override
   public void func_188659_c() {
      if (!this.field_188697_b && this.field_188698_c != null) {
         BlockPos ☃ = this.field_188661_a.field_70170_p.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.field_186139_a);
         double ☃x = this.field_188661_a.func_174831_c(☃);
         if (☃x > 100.0) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188741_a);
         }
      } else {
         this.field_188697_b = false;
         this.func_188695_j();
      }
   }

   @Override
   public void func_188660_d() {
      this.field_188697_b = true;
      this.field_188698_c = null;
      this.field_188699_d = null;
   }

   private void func_188695_j() {
      int ☃ = this.field_188661_a.func_184671_o();
      Vec3d ☃x = this.field_188661_a.func_184665_a(1.0F);
      int ☃xx = this.field_188661_a.func_184663_l(-☃x.field_72450_a * 40.0, 105.0, -☃x.field_72449_c * 40.0);
      if (this.field_188661_a.func_184664_cU() != null && this.field_188661_a.func_184664_cU().func_186092_c() > 0) {
         ☃xx %= 12;
         if (☃xx < 0) {
            ☃xx += 12;
         }
      } else {
         ☃xx -= 12;
         ☃xx &= 7;
         ☃xx += 12;
      }

      this.field_188698_c = this.field_188661_a.func_184666_a(☃, ☃xx, null);
      if (this.field_188698_c != null) {
         this.field_188698_c.func_75875_a();
         this.func_188696_k();
      }
   }

   private void func_188696_k() {
      Vec3d ☃ = this.field_188698_c.func_186310_f();
      this.field_188698_c.func_75875_a();

      double ☃;
      do {
         ☃ = ☃.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
      } while(☃ < ☃.field_72448_b);

      this.field_188699_d = new Vec3d(☃.field_72450_a, ☃, ☃.field_72449_c);
   }

   @Nullable
   @Override
   public Vec3d func_188650_g() {
      return this.field_188699_d;
   }

   @Override
   public PhaseType<PhaseTakeoff> func_188652_i() {
      return PhaseType.field_188745_e;
   }
}
