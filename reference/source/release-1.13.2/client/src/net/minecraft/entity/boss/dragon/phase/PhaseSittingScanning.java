package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseSittingScanning extends PhaseSittingBase {
   private int field_188667_b;

   public PhaseSittingScanning(EntityDragon var1) {
      super(☃);
   }

   @Override
   public void func_188659_c() {
      ++this.field_188667_b;
      EntityLivingBase ☃ = this.field_188661_a.field_70170_p.func_184142_a(this.field_188661_a, 20.0, 10.0);
      if (☃ != null) {
         if (this.field_188667_b > 25) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188748_h);
         } else {
            Vec3d ☃x = new Vec3d(☃.field_70165_t - this.field_188661_a.field_70165_t, 0.0, ☃.field_70161_v - this.field_188661_a.field_70161_v).func_72432_b();
            Vec3d ☃xx = new Vec3d(
                  (double)MathHelper.func_76126_a(this.field_188661_a.field_70177_z * (float) (Math.PI / 180.0)),
                  0.0,
                  (double)(-MathHelper.func_76134_b(this.field_188661_a.field_70177_z * (float) (Math.PI / 180.0)))
               )
               .func_72432_b();
            float ☃xxx = (float)☃xx.func_72430_b(☃x);
            float ☃xxxx = (float)(Math.acos((double)☃xxx) * 180.0F / (float)Math.PI) + 0.5F;
            if (☃xxxx < 0.0F || ☃xxxx > 10.0F) {
               double ☃xxxxx = ☃.field_70165_t - this.field_188661_a.field_70986_h.field_70165_t;
               double ☃xxxxxx = ☃.field_70161_v - this.field_188661_a.field_70986_h.field_70161_v;
               double ☃xxxxxxx = MathHelper.func_151237_a(
                  MathHelper.func_76138_g(
                     180.0 - MathHelper.func_181159_b(☃xxxxx, ☃xxxxxx) * 180.0F / (float)Math.PI - (double)this.field_188661_a.field_70177_z
                  ),
                  -100.0,
                  100.0
               );
               this.field_188661_a.field_70704_bt *= 0.8F;
               float ☃xxxxxxxx = MathHelper.func_76133_a(☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx) + 1.0F;
               float ☃xxxxxxxxx = ☃xxxxxxxx;
               if (☃xxxxxxxx > 40.0F) {
                  ☃xxxxxxxx = 40.0F;
               }

               this.field_188661_a.field_70704_bt = (float)((double)this.field_188661_a.field_70704_bt + ☃xxxxxxx * (double)(0.7F / ☃xxxxxxxx / ☃xxxxxxxxx));
               this.field_188661_a.field_70177_z += this.field_188661_a.field_70704_bt;
            }
         }
      } else if (this.field_188667_b >= 100) {
         ☃ = this.field_188661_a.field_70170_p.func_184142_a(this.field_188661_a, 150.0, 150.0);
         this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188745_e);
         if (☃ != null) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188749_i);
            this.field_188661_a
               .func_184670_cT()
               .func_188757_b(PhaseType.field_188749_i)
               .func_188668_a(new Vec3d(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v));
         }
      }
   }

   @Override
   public void func_188660_d() {
      this.field_188667_b = 0;
   }

   @Override
   public PhaseType<PhaseSittingScanning> func_188652_i() {
      return PhaseType.field_188747_g;
   }
}
