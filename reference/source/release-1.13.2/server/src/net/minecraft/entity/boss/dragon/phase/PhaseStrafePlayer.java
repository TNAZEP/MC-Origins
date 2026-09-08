package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseStrafePlayer extends PhaseBase {
   private static final Logger field_188689_b = LogManager.getLogger();
   private int field_188690_c;
   private Path field_188691_d;
   private Vec3d field_188692_e;
   private EntityLivingBase field_188693_f;
   private boolean field_188694_g;

   public PhaseStrafePlayer(EntityDragon var1) {
      super(☃);
   }

   @Override
   public void func_188659_c() {
      if (this.field_188693_f == null) {
         field_188689_b.warn("Skipping player strafe phase because no player was found");
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188741_a);
      } else {
         if (this.field_188691_d != null && this.field_188691_d.func_75879_b()) {
            double ☃ = this.field_188693_f.field_70165_t;
            double ☃x = this.field_188693_f.field_70161_v;
            double ☃xx = ☃ - this.field_188661_a.field_70165_t;
            double ☃xxx = ☃x - this.field_188661_a.field_70161_v;
            double ☃xxxx = (double)MathHelper.func_76133_a(☃xx * ☃xx + ☃xxx * ☃xxx);
            double ☃xxxxx = Math.min(0.4F + ☃xxxx / 80.0 - 1.0, 10.0);
            this.field_188692_e = new Vec3d(☃, this.field_188693_f.field_70163_u + ☃xxxxx, ☃x);
         }

         double ☃ = this.field_188692_e == null
            ? 0.0
            : this.field_188692_e.func_186679_c(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
         if (☃ < 100.0 || ☃ > 22500.0) {
            this.func_188687_j();
         }

         double ☃ = 64.0;
         if (this.field_188693_f.func_70068_e(this.field_188661_a) < 4096.0) {
            if (this.field_188661_a.func_70685_l(this.field_188693_f)) {
               ++this.field_188690_c;
               Vec3d ☃x = new Vec3d(
                     this.field_188693_f.field_70165_t - this.field_188661_a.field_70165_t,
                     0.0,
                     this.field_188693_f.field_70161_v - this.field_188661_a.field_70161_v
                  )
                  .func_72432_b();
               Vec3d ☃xx = new Vec3d(
                     (double)MathHelper.func_76126_a(this.field_188661_a.field_70177_z * (float) (Math.PI / 180.0)),
                     0.0,
                     (double)(-MathHelper.func_76134_b(this.field_188661_a.field_70177_z * (float) (Math.PI / 180.0)))
                  )
                  .func_72432_b();
               float ☃xxx = (float)☃xx.func_72430_b(☃x);
               float ☃xxxx = (float)(Math.acos((double)☃xxx) * 180.0F / (float)Math.PI);
               ☃xxxx += 0.5F;
               if (this.field_188690_c >= 5 && ☃xxxx >= 0.0F && ☃xxxx < 10.0F) {
                  double ☃xxxxx = 1.0;
                  Vec3d ☃xxxxxx = this.field_188661_a.func_70676_i(1.0F);
                  double ☃xxxxxxx = this.field_188661_a.field_70986_h.field_70165_t - ☃xxxxxx.field_72450_a * 1.0;
                  double ☃xxxxxxxx = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F) + 0.5;
                  double ☃xxxxxxxxx = this.field_188661_a.field_70986_h.field_70161_v - ☃xxxxxx.field_72449_c * 1.0;
                  double ☃xxxxxxxxxx = this.field_188693_f.field_70165_t - ☃xxxxxxx;
                  double ☃xxxxxxxxxxx = this.field_188693_f.field_70163_u
                     + (double)(this.field_188693_f.field_70131_O / 2.0F)
                     - (☃xxxxxxxx + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F));
                  double ☃xxxxxxxxxxxx = this.field_188693_f.field_70161_v - ☃xxxxxxxxx;
                  this.field_188661_a.field_70170_p.func_180498_a(null, 1017, new BlockPos(this.field_188661_a), 0);
                  EntityDragonFireball ☃xxxxxxxxxxxxx = new EntityDragonFireball(
                     this.field_188661_a.field_70170_p, this.field_188661_a, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx
                  );
                  ☃xxxxxxxxxxxxx.func_70012_b(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.0F, 0.0F);
                  this.field_188661_a.field_70170_p.func_72838_d(☃xxxxxxxxxxxxx);
                  this.field_188690_c = 0;
                  if (this.field_188691_d != null) {
                     while(!this.field_188691_d.func_75879_b()) {
                        this.field_188691_d.func_75875_a();
                     }
                  }

                  this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188741_a);
               }
            } else if (this.field_188690_c > 0) {
               --this.field_188690_c;
            }
         } else if (this.field_188690_c > 0) {
            --this.field_188690_c;
         }
      }
   }

   private void func_188687_j() {
      if (this.field_188691_d == null || this.field_188691_d.func_75879_b()) {
         int ☃ = this.field_188661_a.func_184671_o();
         int ☃x = ☃;
         if (this.field_188661_a.func_70681_au().nextInt(8) == 0) {
            this.field_188694_g = !this.field_188694_g;
            ☃x = ☃ + 6;
         }

         if (this.field_188694_g) {
            ++☃x;
         } else {
            --☃x;
         }

         if (this.field_188661_a.func_184664_cU() != null && this.field_188661_a.func_184664_cU().func_186092_c() > 0) {
            ☃x %= 12;
            if (☃x < 0) {
               ☃x += 12;
            }
         } else {
            ☃x -= 12;
            ☃x &= 7;
            ☃x += 12;
         }

         this.field_188691_d = this.field_188661_a.func_184666_a(☃, ☃x, null);
         if (this.field_188691_d != null) {
            this.field_188691_d.func_75875_a();
         }
      }

      this.func_188688_k();
   }

   private void func_188688_k() {
      if (this.field_188691_d != null && !this.field_188691_d.func_75879_b()) {
         Vec3d ☃ = this.field_188691_d.func_186310_f();
         this.field_188691_d.func_75875_a();
         double ☃x = ☃.field_72450_a;
         double ☃xx = ☃.field_72449_c;

         double ☃;
         do {
            ☃ = ☃.field_72448_b + (double)(this.field_188661_a.func_70681_au().nextFloat() * 20.0F);
         } while(☃ < ☃.field_72448_b);

         this.field_188692_e = new Vec3d(☃x, ☃, ☃xx);
      }
   }

   @Override
   public void func_188660_d() {
      this.field_188690_c = 0;
      this.field_188692_e = null;
      this.field_188691_d = null;
      this.field_188693_f = null;
   }

   public void func_188686_a(EntityLivingBase var1) {
      this.field_188693_f = ☃;
      int ☃ = this.field_188661_a.func_184671_o();
      int ☃x = this.field_188661_a.func_184663_l(this.field_188693_f.field_70165_t, this.field_188693_f.field_70163_u, this.field_188693_f.field_70161_v);
      int ☃xx = MathHelper.func_76128_c(this.field_188693_f.field_70165_t);
      int ☃xxx = MathHelper.func_76128_c(this.field_188693_f.field_70161_v);
      double ☃xxxx = (double)☃xx - this.field_188661_a.field_70165_t;
      double ☃xxxxx = (double)☃xxx - this.field_188661_a.field_70161_v;
      double ☃xxxxxx = (double)MathHelper.func_76133_a(☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx);
      double ☃xxxxxxx = Math.min(0.4F + ☃xxxxxx / 80.0 - 1.0, 10.0);
      int ☃xxxxxxxx = MathHelper.func_76128_c(this.field_188693_f.field_70163_u + ☃xxxxxxx);
      PathPoint ☃xxxxxxxxx = new PathPoint(☃xx, ☃xxxxxxxx, ☃xxx);
      this.field_188691_d = this.field_188661_a.func_184666_a(☃, ☃x, ☃xxxxxxxxx);
      if (this.field_188691_d != null) {
         this.field_188691_d.func_75875_a();
         this.func_188688_k();
      }
   }

   @Nullable
   @Override
   public Vec3d func_188650_g() {
      return this.field_188692_e;
   }

   @Override
   public PhaseType<PhaseStrafePlayer> func_188652_i() {
      return PhaseType.field_188742_b;
   }
}
