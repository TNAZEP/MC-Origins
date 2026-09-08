package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityDolphin;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateSwimmer extends PathNavigate {
   private boolean field_205155_i;

   public PathNavigateSwimmer(EntityLiving var1, World var2) {
      super(☃, ☃);
   }

   @Override
   protected PathFinder func_179679_a() {
      this.field_205155_i = this.field_75515_a instanceof EntityDolphin;
      this.field_179695_a = new SwimNodeProcessor(this.field_205155_i);
      return new PathFinder(this.field_179695_a);
   }

   @Override
   protected boolean func_75485_k() {
      return this.field_205155_i || this.func_75506_l();
   }

   @Override
   protected Vec3d func_75502_i() {
      return new Vec3d(
         this.field_75515_a.field_70165_t, this.field_75515_a.field_70163_u + (double)this.field_75515_a.field_70131_O * 0.5, this.field_75515_a.field_70161_v
      );
   }

   @Override
   public void func_75501_e() {
      ++this.field_75510_g;
      if (this.field_188562_p) {
         this.func_188554_j();
      }

      if (!this.func_75500_f()) {
         if (this.func_75485_k()) {
            this.func_75508_h();
         } else if (this.field_75514_c != null && this.field_75514_c.func_75873_e() < this.field_75514_c.func_75874_d()) {
            Vec3d ☃ = this.field_75514_c.func_75881_a(this.field_75515_a, this.field_75514_c.func_75873_e());
            if (MathHelper.func_76128_c(this.field_75515_a.field_70165_t) == MathHelper.func_76128_c(☃.field_72450_a)
               && MathHelper.func_76128_c(this.field_75515_a.field_70163_u) == MathHelper.func_76128_c(☃.field_72448_b)
               && MathHelper.func_76128_c(this.field_75515_a.field_70161_v) == MathHelper.func_76128_c(☃.field_72449_c)) {
               this.field_75514_c.func_75872_c(this.field_75514_c.func_75873_e() + 1);
            }
         }

         this.func_192876_m();
         if (!this.func_75500_f()) {
            Vec3d ☃ = this.field_75514_c.func_75878_a(this.field_75515_a);
            this.field_75515_a.func_70605_aq().func_75642_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, this.field_75511_d);
         }
      }
   }

   @Override
   protected void func_75508_h() {
      if (this.field_75514_c != null) {
         Vec3d ☃ = this.func_75502_i();
         float ☃x = this.field_75515_a.field_70130_N > 0.75F ? this.field_75515_a.field_70130_N / 2.0F : 0.75F - this.field_75515_a.field_70130_N / 2.0F;
         if ((double)MathHelper.func_76135_e((float)this.field_75515_a.field_70159_w) > 0.2
            || (double)MathHelper.func_76135_e((float)this.field_75515_a.field_70179_y) > 0.2) {
            ☃x *= MathHelper.func_76133_a(
                  this.field_75515_a.field_70159_w * this.field_75515_a.field_70159_w
                     + this.field_75515_a.field_70181_x * this.field_75515_a.field_70181_x
                     + this.field_75515_a.field_70179_y * this.field_75515_a.field_70179_y
               )
               * 6.0F;
         }

         int ☃ = 6;
         Vec3d ☃x = this.field_75514_c.func_186310_f();
         if (MathHelper.func_76135_e((float)(this.field_75515_a.field_70165_t - (☃x.field_72450_a + 0.5))) < ☃x
            && MathHelper.func_76135_e((float)(this.field_75515_a.field_70161_v - (☃x.field_72449_c + 0.5))) < ☃x
            && Math.abs(this.field_75515_a.field_70163_u - ☃x.field_72448_b) < (double)(☃x * 2.0F)) {
            this.field_75514_c.func_75875_a();
         }

         for(int ☃ = Math.min(this.field_75514_c.func_75873_e() + 6, this.field_75514_c.func_75874_d() - 1); ☃ > this.field_75514_c.func_75873_e(); --☃) {
            ☃x = this.field_75514_c.func_75881_a(this.field_75515_a, ☃);
            if (!(☃x.func_72436_e(☃) > 36.0) && this.func_75493_a(☃, ☃x, 0, 0, 0)) {
               this.field_75514_c.func_75872_c(☃);
               break;
            }
         }

         this.func_179677_a(☃);
      }
   }

   @Override
   protected void func_179677_a(Vec3d var1) {
      if (this.field_75510_g - this.field_75520_h > 100) {
         if (☃.func_72436_e(this.field_75521_i) < 2.25) {
            this.func_75499_g();
         }

         this.field_75520_h = this.field_75510_g;
         this.field_75521_i = ☃;
      }

      if (this.field_75514_c != null && !this.field_75514_c.func_75879_b()) {
         Vec3d ☃ = this.field_75514_c.func_186310_f();
         if (☃.equals(this.field_188557_k)) {
            this.field_188558_l += Util.func_211177_b() - this.field_188559_m;
         } else {
            this.field_188557_k = ☃;
            double ☃ = ☃.func_72438_d(this.field_188557_k);
            this.field_188560_n = this.field_75515_a.func_70689_ay() > 0.0F ? ☃ / (double)this.field_75515_a.func_70689_ay() * 100.0 : 0.0;
         }

         if (this.field_188560_n > 0.0 && (double)this.field_188558_l > this.field_188560_n * 2.0) {
            this.field_188557_k = Vec3d.field_186680_a;
            this.field_188558_l = 0L;
            this.field_188560_n = 0.0;
            this.func_75499_g();
         }

         this.field_188559_m = Util.func_211177_b();
      }
   }

   @Override
   protected boolean func_75493_a(Vec3d var1, Vec3d var2, int var3, int var4, int var5) {
      RayTraceResult ☃ = this.field_75513_b
         .func_200259_a(
            ☃,
            new Vec3d(☃.field_72450_a, ☃.field_72448_b + (double)this.field_75515_a.field_70131_O * 0.5, ☃.field_72449_c),
            RayTraceFluidMode.NEVER,
            true,
            false
         );
      return ☃ == null || ☃.field_72313_a == RayTraceResult.Type.MISS;
   }

   @Override
   public boolean func_188555_b(BlockPos var1) {
      return !this.field_75513_b.func_180495_p(☃).func_200015_d(this.field_75513_b, ☃);
   }

   @Override
   public void func_212239_d(boolean var1) {
   }
}
