package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseSittingFlaming extends PhaseSittingBase {
   private int field_188664_b;
   private int field_188665_c;
   private EntityAreaEffectCloud field_188666_d;

   public PhaseSittingFlaming(EntityDragon var1) {
      super(☃);
   }

   @Override
   public void func_188657_b() {
      ++this.field_188664_b;
      if (this.field_188664_b % 2 == 0 && this.field_188664_b < 10) {
         Vec3d ☃ = this.field_188661_a.func_184665_a(1.0F).func_72432_b();
         ☃.func_178785_b((float) (-Math.PI / 4));
         double ☃x = this.field_188661_a.field_70986_h.field_70165_t;
         double ☃xx = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F);
         double ☃xxx = this.field_188661_a.field_70986_h.field_70161_v;

         for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
            double ☃xxxxx = ☃x + this.field_188661_a.func_70681_au().nextGaussian() / 2.0;
            double ☃xxxxxx = ☃xx + this.field_188661_a.func_70681_au().nextGaussian() / 2.0;
            double ☃xxxxxxx = ☃xxx + this.field_188661_a.func_70681_au().nextGaussian() / 2.0;

            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 6; ++☃xxxxxxxx) {
               this.field_188661_a
                  .field_70170_p
                  .func_195594_a(
                     Particles.field_197616_i,
                     ☃xxxxx,
                     ☃xxxxxx,
                     ☃xxxxxxx,
                     -☃.field_72450_a * 0.08F * (double)☃xxxxxxxx,
                     -☃.field_72448_b * 0.6F,
                     -☃.field_72449_c * 0.08F * (double)☃xxxxxxxx
                  );
            }

            ☃.func_178785_b((float) (Math.PI / 16));
         }
      }
   }

   @Override
   public void func_188659_c() {
      ++this.field_188664_b;
      if (this.field_188664_b >= 200) {
         if (this.field_188665_c >= 4) {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188745_e);
         } else {
            this.field_188661_a.func_184670_cT().func_188758_a(PhaseType.field_188747_g);
         }
      } else if (this.field_188664_b == 10) {
         Vec3d ☃ = new Vec3d(
               this.field_188661_a.field_70986_h.field_70165_t - this.field_188661_a.field_70165_t,
               0.0,
               this.field_188661_a.field_70986_h.field_70161_v - this.field_188661_a.field_70161_v
            )
            .func_72432_b();
         float ☃x = 5.0F;
         double ☃xx = this.field_188661_a.field_70986_h.field_70165_t + ☃.field_72450_a * 5.0 / 2.0;
         double ☃xxx = this.field_188661_a.field_70986_h.field_70161_v + ☃.field_72449_c * 5.0 / 2.0;
         double ☃xxxx = this.field_188661_a.field_70986_h.field_70163_u + (double)(this.field_188661_a.field_70986_h.field_70131_O / 2.0F);
         BlockPos.MutableBlockPos ☃xxxxx = new BlockPos.MutableBlockPos(
            MathHelper.func_76128_c(☃xx), MathHelper.func_76128_c(☃xxxx), MathHelper.func_76128_c(☃xxx)
         );

         while(this.field_188661_a.field_70170_p.func_175623_d(☃xxxxx)) {
            ☃xxxxx.func_181079_c(MathHelper.func_76128_c(☃xx), MathHelper.func_76128_c(--☃xxxx), MathHelper.func_76128_c(☃xxx));
         }

         ☃xxxx = (double)(MathHelper.func_76128_c(☃xxxx) + 1);
         this.field_188666_d = new EntityAreaEffectCloud(this.field_188661_a.field_70170_p, ☃xx, ☃xxxx, ☃xxx);
         this.field_188666_d.func_184481_a(this.field_188661_a);
         this.field_188666_d.func_184483_a(5.0F);
         this.field_188666_d.func_184486_b(200);
         this.field_188666_d.func_195059_a(Particles.field_197616_i);
         this.field_188666_d.func_184496_a(new PotionEffect(MobEffects.field_76433_i));
         this.field_188661_a.field_70170_p.func_72838_d(this.field_188666_d);
      }
   }

   @Override
   public void func_188660_d() {
      this.field_188664_b = 0;
      ++this.field_188665_c;
   }

   @Override
   public void func_188658_e() {
      if (this.field_188666_d != null) {
         this.field_188666_d.func_70106_y();
         this.field_188666_d = null;
      }
   }

   @Override
   public PhaseType<PhaseSittingFlaming> func_188652_i() {
      return PhaseType.field_188746_f;
   }

   public void func_188663_j() {
      this.field_188665_c = 0;
   }
}
