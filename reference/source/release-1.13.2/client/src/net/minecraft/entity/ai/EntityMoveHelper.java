package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityMoveHelper {
   protected final EntityLiving field_75648_a;
   protected double field_75646_b;
   protected double field_75647_c;
   protected double field_75644_d;
   protected double field_75645_e;
   protected float field_188489_f;
   protected float field_188490_g;
   protected EntityMoveHelper.Action field_188491_h = EntityMoveHelper.Action.WAIT;

   public EntityMoveHelper(EntityLiving var1) {
      this.field_75648_a = ☃;
   }

   public boolean func_75640_a() {
      return this.field_188491_h == EntityMoveHelper.Action.MOVE_TO;
   }

   public double func_75638_b() {
      return this.field_75645_e;
   }

   public void func_75642_a(double var1, double var3, double var5, double var7) {
      this.field_75646_b = ☃;
      this.field_75647_c = ☃;
      this.field_75644_d = ☃;
      this.field_75645_e = ☃;
      if (this.field_188491_h != EntityMoveHelper.Action.JUMPING) {
         this.field_188491_h = EntityMoveHelper.Action.MOVE_TO;
      }
   }

   public void func_188488_a(float var1, float var2) {
      this.field_188491_h = EntityMoveHelper.Action.STRAFE;
      this.field_188489_f = ☃;
      this.field_188490_g = ☃;
      this.field_75645_e = 0.25;
   }

   public void func_188487_a(EntityMoveHelper var1) {
      this.field_188491_h = ☃.field_188491_h;
      this.field_75646_b = ☃.field_75646_b;
      this.field_75647_c = ☃.field_75647_c;
      this.field_75644_d = ☃.field_75644_d;
      this.field_75645_e = Math.max(☃.field_75645_e, 1.0);
      this.field_188489_f = ☃.field_188489_f;
      this.field_188490_g = ☃.field_188490_g;
   }

   public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.STRAFE) {
         float ☃ = (float)this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
         float ☃x = (float)this.field_75645_e * ☃;
         float ☃xx = this.field_188489_f;
         float ☃xxx = this.field_188490_g;
         float ☃xxxx = MathHelper.func_76129_c(☃xx * ☃xx + ☃xxx * ☃xxx);
         if (☃xxxx < 1.0F) {
            ☃xxxx = 1.0F;
         }

         ☃xxxx = ☃x / ☃xxxx;
         ☃xx *= ☃xxxx;
         ☃xxx *= ☃xxxx;
         float ☃ = MathHelper.func_76126_a(this.field_75648_a.field_70177_z * (float) (Math.PI / 180.0));
         float ☃x = MathHelper.func_76134_b(this.field_75648_a.field_70177_z * (float) (Math.PI / 180.0));
         float ☃xx = ☃xx * ☃x - ☃xxx * ☃;
         float ☃xxx = ☃xxx * ☃x + ☃xx * ☃;
         PathNavigate ☃xxxx = this.field_75648_a.func_70661_as();
         if (☃xxxx != null) {
            NodeProcessor ☃xxxxx = ☃xxxx.func_189566_q();
            if (☃xxxxx != null
               && ☃xxxxx.func_186330_a(
                     this.field_75648_a.field_70170_p,
                     MathHelper.func_76128_c(this.field_75648_a.field_70165_t + (double)☃xx),
                     MathHelper.func_76128_c(this.field_75648_a.field_70163_u),
                     MathHelper.func_76128_c(this.field_75648_a.field_70161_v + (double)☃xxx)
                  )
                  != PathNodeType.WALKABLE) {
               this.field_188489_f = 1.0F;
               this.field_188490_g = 0.0F;
               ☃x = ☃;
            }
         }

         this.field_75648_a.func_70659_e(☃x);
         this.field_75648_a.func_191989_p(this.field_188489_f);
         this.field_75648_a.func_184646_p(this.field_188490_g);
         this.field_188491_h = EntityMoveHelper.Action.WAIT;
      } else if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
         this.field_188491_h = EntityMoveHelper.Action.WAIT;
         double ☃ = this.field_75646_b - this.field_75648_a.field_70165_t;
         double ☃x = this.field_75644_d - this.field_75648_a.field_70161_v;
         double ☃xx = this.field_75647_c - this.field_75648_a.field_70163_u;
         double ☃xxx = ☃ * ☃ + ☃xx * ☃xx + ☃x * ☃x;
         if (☃xxx < 2.5000003E-7F) {
            this.field_75648_a.func_191989_p(0.0F);
            return;
         }

         float ☃ = (float)(MathHelper.func_181159_b(☃x, ☃) * 180.0F / (float)Math.PI) - 90.0F;
         this.field_75648_a.field_70177_z = this.func_75639_a(this.field_75648_a.field_70177_z, ☃, 90.0F);
         this.field_75648_a
            .func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
         if (☃xx > (double)this.field_75648_a.field_70138_W && ☃ * ☃ + ☃x * ☃x < (double)Math.max(1.0F, this.field_75648_a.field_70130_N)) {
            this.field_75648_a.func_70683_ar().func_75660_a();
            this.field_188491_h = EntityMoveHelper.Action.JUMPING;
         }
      } else if (this.field_188491_h == EntityMoveHelper.Action.JUMPING) {
         this.field_75648_a
            .func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
         if (this.field_75648_a.field_70122_E) {
            this.field_188491_h = EntityMoveHelper.Action.WAIT;
         }
      } else {
         this.field_75648_a.func_191989_p(0.0F);
      }
   }

   protected float func_75639_a(float var1, float var2, float var3) {
      float ☃ = MathHelper.func_76142_g(☃ - ☃);
      if (☃ > ☃) {
         ☃ = ☃;
      }

      if (☃ < -☃) {
         ☃ = -☃;
      }

      float ☃ = ☃ + ☃;
      if (☃ < 0.0F) {
         ☃ += 360.0F;
      } else if (☃ > 360.0F) {
         ☃ -= 360.0F;
      }

      return ☃;
   }

   public double func_179917_d() {
      return this.field_75646_b;
   }

   public double func_179919_e() {
      return this.field_75647_c;
   }

   public double func_179918_f() {
      return this.field_75644_d;
   }

   public static enum Action {
      WAIT,
      MOVE_TO,
      STRAFE,
      JUMPING;
   }
}
