package net.minecraft.entity;

import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class EntityCreature extends EntityLiving {
   private BlockPos field_70775_bC = BlockPos.field_177992_a;
   private float field_70772_bD = -1.0F;

   protected EntityCreature(EntityType<?> var1, World var2) {
      super(☃, ☃);
   }

   public float func_180484_a(BlockPos var1) {
      return this.func_205022_a(☃, this.field_70170_p);
   }

   public float func_205022_a(BlockPos var1, IWorldReaderBase var2) {
      return 0.0F;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return super.func_205020_a(☃, ☃)
         && this.func_205022_a(new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v), ☃) >= 0.0F;
   }

   public boolean func_70781_l() {
      return !this.field_70699_by.func_75500_f();
   }

   public boolean func_110173_bK() {
      return this.func_180485_d(new BlockPos(this));
   }

   public boolean func_180485_d(BlockPos var1) {
      if (this.field_70772_bD == -1.0F) {
         return true;
      } else {
         return this.field_70775_bC.func_177951_i(☃) < (double)(this.field_70772_bD * this.field_70772_bD);
      }
   }

   public void func_175449_a(BlockPos var1, int var2) {
      this.field_70775_bC = ☃;
      this.field_70772_bD = (float)☃;
   }

   public BlockPos func_180486_cf() {
      return this.field_70775_bC;
   }

   public float func_110174_bM() {
      return this.field_70772_bD;
   }

   public void func_110177_bN() {
      this.field_70772_bD = -1.0F;
   }

   public boolean func_110175_bO() {
      return this.field_70772_bD != -1.0F;
   }

   @Override
   protected void func_110159_bB() {
      super.func_110159_bB();
      if (this.func_110167_bD() && this.func_110166_bE() != null && this.func_110166_bE().field_70170_p == this.field_70170_p) {
         Entity ☃ = this.func_110166_bE();
         this.func_175449_a(new BlockPos((int)☃.field_70165_t, (int)☃.field_70163_u, (int)☃.field_70161_v), 5);
         float ☃x = this.func_70032_d(☃);
         if (this instanceof EntityTameable && ((EntityTameable)this).func_70906_o()) {
            if (☃x > 10.0F) {
               this.func_110160_i(true, true);
            }

            return;
         }

         this.func_142017_o(☃x);
         if (☃x > 10.0F) {
            this.func_110160_i(true, true);
            this.field_70714_bg.func_188526_c(1);
         } else if (☃x > 6.0F) {
            double ☃ = (☃.field_70165_t - this.field_70165_t) / (double)☃x;
            double ☃x = (☃.field_70163_u - this.field_70163_u) / (double)☃x;
            double ☃xx = (☃.field_70161_v - this.field_70161_v) / (double)☃x;
            this.field_70159_w += ☃ * Math.abs(☃) * 0.4;
            this.field_70181_x += ☃x * Math.abs(☃x) * 0.4;
            this.field_70179_y += ☃xx * Math.abs(☃xx) * 0.4;
         } else {
            this.field_70714_bg.func_188525_d(1);
            float ☃ = 2.0F;
            Vec3d ☃x = new Vec3d(☃.field_70165_t - this.field_70165_t, ☃.field_70163_u - this.field_70163_u, ☃.field_70161_v - this.field_70161_v)
               .func_72432_b()
               .func_186678_a((double)Math.max(☃x - 2.0F, 0.0F));
            this.func_70661_as()
               .func_75492_a(
                  this.field_70165_t + ☃x.field_72450_a, this.field_70163_u + ☃x.field_72448_b, this.field_70161_v + ☃x.field_72449_c, this.func_190634_dg()
               );
         }
      }
   }

   protected double func_190634_dg() {
      return 1.0;
   }

   protected void func_142017_o(float var1) {
   }
}
