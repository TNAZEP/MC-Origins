package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateGround extends PathNavigate {
   private boolean field_179694_f;

   public PathNavigateGround(EntityLiving var1, World var2) {
      super(☃, ☃);
   }

   @Override
   protected PathFinder func_179679_a() {
      this.field_179695_a = new WalkNodeProcessor();
      this.field_179695_a.func_186317_a(true);
      return new PathFinder(this.field_179695_a);
   }

   @Override
   protected boolean func_75485_k() {
      return this.field_75515_a.field_70122_E || this.func_75506_l() || this.field_75515_a.func_184218_aH();
   }

   @Override
   protected Vec3d func_75502_i() {
      return new Vec3d(this.field_75515_a.field_70165_t, (double)this.func_179687_p(), this.field_75515_a.field_70161_v);
   }

   @Override
   public Path func_179680_a(BlockPos var1) {
      if (this.field_75513_b.func_180495_p(☃).func_196958_f()) {
         BlockPos ☃ = ☃.func_177977_b();

         while(☃.func_177956_o() > 0 && this.field_75513_b.func_180495_p(☃).func_196958_f()) {
            ☃ = ☃.func_177977_b();
         }

         if (☃.func_177956_o() > 0) {
            return super.func_179680_a(☃.func_177984_a());
         }

         while(☃.func_177956_o() < this.field_75513_b.func_72800_K() && this.field_75513_b.func_180495_p(☃).func_196958_f()) {
            ☃ = ☃.func_177984_a();
         }

         ☃ = ☃;
      }

      if (!this.field_75513_b.func_180495_p(☃).func_185904_a().func_76220_a()) {
         return super.func_179680_a(☃);
      } else {
         BlockPos ☃ = ☃.func_177984_a();

         while(☃.func_177956_o() < this.field_75513_b.func_72800_K() && this.field_75513_b.func_180495_p(☃).func_185904_a().func_76220_a()) {
            ☃ = ☃.func_177984_a();
         }

         return super.func_179680_a(☃);
      }
   }

   @Override
   public Path func_75494_a(Entity var1) {
      return this.func_179680_a(new BlockPos(☃));
   }

   private int func_179687_p() {
      if (this.field_75515_a.func_70090_H() && this.func_212238_t()) {
         int ☃ = (int)this.field_75515_a.func_174813_aQ().field_72338_b;
         Block ☃x = this.field_75513_b
            .func_180495_p(
               new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), ☃, MathHelper.func_76128_c(this.field_75515_a.field_70161_v))
            )
            .func_177230_c();
         int ☃xx = 0;

         while(☃x == Blocks.field_150355_j) {
            ☃x = this.field_75513_b
               .func_180495_p(
                  new BlockPos(MathHelper.func_76128_c(this.field_75515_a.field_70165_t), ++☃, MathHelper.func_76128_c(this.field_75515_a.field_70161_v))
               )
               .func_177230_c();
            if (++☃xx > 16) {
               return (int)this.field_75515_a.func_174813_aQ().field_72338_b;
            }
         }

         return ☃;
      } else {
         return (int)(this.field_75515_a.func_174813_aQ().field_72338_b + 0.5);
      }
   }

   @Override
   protected void func_75487_m() {
      super.func_75487_m();
      if (this.field_179694_f) {
         if (this.field_75513_b
            .func_175678_i(
               new BlockPos(
                  MathHelper.func_76128_c(this.field_75515_a.field_70165_t),
                  (int)(this.field_75515_a.func_174813_aQ().field_72338_b + 0.5),
                  MathHelper.func_76128_c(this.field_75515_a.field_70161_v)
               )
            )) {
            return;
         }

         for(int ☃ = 0; ☃ < this.field_75514_c.func_75874_d(); ++☃) {
            PathPoint ☃x = this.field_75514_c.func_75877_a(☃);
            if (this.field_75513_b.func_175678_i(new BlockPos(☃x.field_75839_a, ☃x.field_75837_b, ☃x.field_75838_c))) {
               this.field_75514_c.func_75871_b(☃ - 1);
               return;
            }
         }
      }
   }

   @Override
   protected boolean func_75493_a(Vec3d var1, Vec3d var2, int var3, int var4, int var5) {
      int ☃ = MathHelper.func_76128_c(☃.field_72450_a);
      int ☃x = MathHelper.func_76128_c(☃.field_72449_c);
      double ☃xx = ☃.field_72450_a - ☃.field_72450_a;
      double ☃xxx = ☃.field_72449_c - ☃.field_72449_c;
      double ☃xxxx = ☃xx * ☃xx + ☃xxx * ☃xxx;
      if (☃xxxx < 1.0E-8) {
         return false;
      } else {
         double ☃ = 1.0 / Math.sqrt(☃xxxx);
         ☃xx *= ☃;
         ☃xxx *= ☃;
         ☃ += 2;
         ☃ += 2;
         if (!this.func_179683_a(☃, (int)☃.field_72448_b, ☃x, ☃, ☃, ☃, ☃, ☃xx, ☃xxx)) {
            return false;
         } else {
            ☃ -= 2;
            ☃ -= 2;
            double ☃ = 1.0 / Math.abs(☃xx);
            double ☃x = 1.0 / Math.abs(☃xxx);
            double ☃xx = (double)☃ - ☃.field_72450_a;
            double ☃xxx = (double)☃x - ☃.field_72449_c;
            if (☃xx >= 0.0) {
               ++☃xx;
            }

            if (☃xxx >= 0.0) {
               ++☃xxx;
            }

            ☃xx /= ☃xx;
            ☃xxx /= ☃xxx;
            int ☃ = ☃xx < 0.0 ? -1 : 1;
            int ☃x = ☃xxx < 0.0 ? -1 : 1;
            int ☃xx = MathHelper.func_76128_c(☃.field_72450_a);
            int ☃xxx = MathHelper.func_76128_c(☃.field_72449_c);
            int ☃xxxx = ☃xx - ☃;
            int ☃xxxxx = ☃xxx - ☃x;

            while(☃xxxx * ☃ > 0 || ☃xxxxx * ☃x > 0) {
               if (☃xx < ☃xxx) {
                  ☃xx += ☃;
                  ☃ += ☃;
                  ☃xxxx = ☃xx - ☃;
               } else {
                  ☃xxx += ☃x;
                  ☃x += ☃x;
                  ☃xxxxx = ☃xxx - ☃x;
               }

               if (!this.func_179683_a(☃, (int)☃.field_72448_b, ☃x, ☃, ☃, ☃, ☃, ☃xx, ☃xxx)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean func_179683_a(int var1, int var2, int var3, int var4, int var5, int var6, Vec3d var7, double var8, double var10) {
      int ☃ = ☃ - ☃ / 2;
      int ☃x = ☃ - ☃ / 2;
      if (!this.func_179692_b(☃, ☃, ☃x, ☃, ☃, ☃, ☃, ☃, ☃)) {
         return false;
      } else {
         for(int ☃ = ☃; ☃ < ☃ + ☃; ++☃) {
            for(int ☃x = ☃x; ☃x < ☃x + ☃; ++☃x) {
               double ☃xx = (double)☃ + 0.5 - ☃.field_72450_a;
               double ☃xxx = (double)☃x + 0.5 - ☃.field_72449_c;
               if (!(☃xx * ☃ + ☃xxx * ☃ < 0.0)) {
                  PathNodeType ☃xxxx = this.field_179695_a.func_186319_a(this.field_75513_b, ☃, ☃ - 1, ☃x, this.field_75515_a, ☃, ☃, ☃, true, true);
                  if (☃xxxx == PathNodeType.WATER) {
                     return false;
                  }

                  if (☃xxxx == PathNodeType.LAVA) {
                     return false;
                  }

                  if (☃xxxx == PathNodeType.OPEN) {
                     return false;
                  }

                  ☃xxxx = this.field_179695_a.func_186319_a(this.field_75513_b, ☃, ☃, ☃x, this.field_75515_a, ☃, ☃, ☃, true, true);
                  float ☃xxxx = this.field_75515_a.func_184643_a(☃xxxx);
                  if (☃xxxx < 0.0F || ☃xxxx >= 8.0F) {
                     return false;
                  }

                  if (☃xxxx == PathNodeType.DAMAGE_FIRE || ☃xxxx == PathNodeType.DANGER_FIRE || ☃xxxx == PathNodeType.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean func_179692_b(int var1, int var2, int var3, int var4, int var5, int var6, Vec3d var7, double var8, double var10) {
      for(BlockPos ☃ : BlockPos.func_177980_a(new BlockPos(☃, ☃, ☃), new BlockPos(☃ + ☃ - 1, ☃ + ☃ - 1, ☃ + ☃ - 1))) {
         double ☃x = (double)☃.func_177958_n() + 0.5 - ☃.field_72450_a;
         double ☃xx = (double)☃.func_177952_p() + 0.5 - ☃.field_72449_c;
         if (!(☃x * ☃ + ☃xx * ☃ < 0.0) && !this.field_75513_b.func_180495_p(☃).func_196957_g(this.field_75513_b, ☃, PathType.LAND)) {
            return false;
         }
      }

      return true;
   }

   public void func_179688_b(boolean var1) {
      this.field_179695_a.func_186321_b(☃);
   }

   public void func_179691_c(boolean var1) {
      this.field_179695_a.func_186317_a(☃);
   }

   public boolean func_179686_g() {
      return this.field_179695_a.func_186323_c();
   }

   public void func_179685_e(boolean var1) {
      this.field_179694_f = ☃;
   }
}
