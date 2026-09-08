package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateFlying extends PathNavigate {
   public PathNavigateFlying(EntityLiving var1, World var2) {
      super(☃, ☃);
   }

   @Override
   protected PathFinder func_179679_a() {
      this.field_179695_a = new FlyingNodeProcessor();
      this.field_179695_a.func_186317_a(true);
      return new PathFinder(this.field_179695_a);
   }

   @Override
   protected boolean func_75485_k() {
      return this.func_212238_t() && this.func_75506_l() || !this.field_75515_a.func_184218_aH();
   }

   @Override
   protected Vec3d func_75502_i() {
      return new Vec3d(this.field_75515_a.field_70165_t, this.field_75515_a.field_70163_u, this.field_75515_a.field_70161_v);
   }

   @Override
   public Path func_75494_a(Entity var1) {
      return this.func_179680_a(new BlockPos(☃));
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
   protected boolean func_75493_a(Vec3d var1, Vec3d var2, int var3, int var4, int var5) {
      int ☃ = MathHelper.func_76128_c(☃.field_72450_a);
      int ☃x = MathHelper.func_76128_c(☃.field_72448_b);
      int ☃xx = MathHelper.func_76128_c(☃.field_72449_c);
      double ☃xxx = ☃.field_72450_a - ☃.field_72450_a;
      double ☃xxxx = ☃.field_72448_b - ☃.field_72448_b;
      double ☃xxxxx = ☃.field_72449_c - ☃.field_72449_c;
      double ☃xxxxxx = ☃xxx * ☃xxx + ☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx;
      if (☃xxxxxx < 1.0E-8) {
         return false;
      } else {
         double ☃ = 1.0 / Math.sqrt(☃xxxxxx);
         ☃xxx *= ☃;
         ☃xxxx *= ☃;
         ☃xxxxx *= ☃;
         double ☃x = 1.0 / Math.abs(☃xxx);
         double ☃xx = 1.0 / Math.abs(☃xxxx);
         double ☃xxx = 1.0 / Math.abs(☃xxxxx);
         double ☃xxxx = (double)☃ - ☃.field_72450_a;
         double ☃xxxxx = (double)☃x - ☃.field_72448_b;
         double ☃xxxxxx = (double)☃xx - ☃.field_72449_c;
         if (☃xxx >= 0.0) {
            ++☃xxxx;
         }

         if (☃xxxx >= 0.0) {
            ++☃xxxxx;
         }

         if (☃xxxxx >= 0.0) {
            ++☃xxxxxx;
         }

         ☃xxxx /= ☃xxx;
         ☃xxxxx /= ☃xxxx;
         ☃xxxxxx /= ☃xxxxx;
         int ☃ = ☃xxx < 0.0 ? -1 : 1;
         int ☃x = ☃xxxx < 0.0 ? -1 : 1;
         int ☃xx = ☃xxxxx < 0.0 ? -1 : 1;
         int ☃xxx = MathHelper.func_76128_c(☃.field_72450_a);
         int ☃xxxx = MathHelper.func_76128_c(☃.field_72448_b);
         int ☃xxxxx = MathHelper.func_76128_c(☃.field_72449_c);
         int ☃xxxxxx = ☃xxx - ☃;
         int ☃xxxxxxx = ☃xxxx - ☃x;
         int ☃xxxxxxxx = ☃xxxxx - ☃xx;

         while(☃xxxxxx * ☃ > 0 || ☃xxxxxxx * ☃x > 0 || ☃xxxxxxxx * ☃xx > 0) {
            if (☃xxxx < ☃xxxxxx && ☃xxxx <= ☃xxxxx) {
               ☃xxxx += ☃x;
               ☃ += ☃;
               ☃xxxxxx = ☃xxx - ☃;
            } else if (☃xxxxx < ☃xxxx && ☃xxxxx <= ☃xxxxxx) {
               ☃xxxxx += ☃xx;
               ☃x += ☃x;
               ☃xxxxxxx = ☃xxxx - ☃x;
            } else {
               ☃xxxxxx += ☃xxx;
               ☃xx += ☃xx;
               ☃xxxxxxxx = ☃xxxxx - ☃xx;
            }
         }

         return true;
      }
   }

   public void func_192879_a(boolean var1) {
      this.field_179695_a.func_186321_b(☃);
   }

   public void func_192878_b(boolean var1) {
      this.field_179695_a.func_186317_a(☃);
   }

   @Override
   public boolean func_188555_b(BlockPos var1) {
      return this.field_75513_b.func_180495_p(☃).func_185896_q();
   }
}
