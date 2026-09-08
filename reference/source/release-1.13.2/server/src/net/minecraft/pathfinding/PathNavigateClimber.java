package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
   private BlockPos field_179696_f;

   public PathNavigateClimber(EntityLiving var1, World var2) {
      super(☃, ☃);
   }

   @Override
   public Path func_179680_a(BlockPos var1) {
      this.field_179696_f = ☃;
      return super.func_179680_a(☃);
   }

   @Override
   public Path func_75494_a(Entity var1) {
      this.field_179696_f = new BlockPos(☃);
      return super.func_75494_a(☃);
   }

   @Override
   public boolean func_75497_a(Entity var1, double var2) {
      Path ☃ = this.func_75494_a(☃);
      if (☃ != null) {
         return this.func_75484_a(☃, ☃);
      } else {
         this.field_179696_f = new BlockPos(☃);
         this.field_75511_d = ☃;
         return true;
      }
   }

   @Override
   public void func_75501_e() {
      if (!this.func_75500_f()) {
         super.func_75501_e();
      } else {
         if (this.field_179696_f != null) {
            double ☃ = (double)(this.field_75515_a.field_70130_N * this.field_75515_a.field_70130_N);
            if (!(this.field_75515_a.func_174831_c(this.field_179696_f) < ☃)
               && (
                  !(this.field_75515_a.field_70163_u > (double)this.field_179696_f.func_177956_o())
                     || !(
                        this.field_75515_a
                              .func_174831_c(
                                 new BlockPos(
                                    this.field_179696_f.func_177958_n(),
                                    MathHelper.func_76128_c(this.field_75515_a.field_70163_u),
                                    this.field_179696_f.func_177952_p()
                                 )
                              )
                           < ☃
                     )
               )) {
               this.field_75515_a
                  .func_70605_aq()
                  .func_75642_a(
                     (double)this.field_179696_f.func_177958_n(),
                     (double)this.field_179696_f.func_177956_o(),
                     (double)this.field_179696_f.func_177952_p(),
                     this.field_75511_d
                  );
            } else {
               this.field_179696_f = null;
            }
         }
      }
   }
}
