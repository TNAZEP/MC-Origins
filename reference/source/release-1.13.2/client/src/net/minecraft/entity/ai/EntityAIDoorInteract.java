package net.minecraft.entity.ai;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public abstract class EntityAIDoorInteract extends EntityAIBase {
   protected EntityLiving field_75356_a;
   protected BlockPos field_179507_b = BlockPos.field_177992_a;
   protected boolean field_195923_c;
   private boolean field_75350_f;
   private float field_75351_g;
   private float field_75357_h;

   public EntityAIDoorInteract(EntityLiving var1) {
      this.field_75356_a = ☃;
      if (!(☃.func_70661_as() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   protected boolean func_195922_f() {
      if (!this.field_195923_c) {
         return false;
      } else {
         IBlockState ☃ = this.field_75356_a.field_70170_p.func_180495_p(this.field_179507_b);
         if (!(☃.func_177230_c() instanceof BlockDoor)) {
            this.field_195923_c = false;
            return false;
         } else {
            return ☃.func_177229_b(BlockDoor.field_176519_b);
         }
      }
   }

   protected void func_195921_a(boolean var1) {
      if (this.field_195923_c) {
         IBlockState ☃ = this.field_75356_a.field_70170_p.func_180495_p(this.field_179507_b);
         if (☃.func_177230_c() instanceof BlockDoor) {
            ((BlockDoor)☃.func_177230_c()).func_176512_a(this.field_75356_a.field_70170_p, this.field_179507_b, ☃);
         }
      }
   }

   @Override
   public boolean func_75250_a() {
      if (!this.field_75356_a.field_70123_F) {
         return false;
      } else {
         PathNavigateGround ☃ = (PathNavigateGround)this.field_75356_a.func_70661_as();
         Path ☃x = ☃.func_75505_d();
         if (☃x != null && !☃x.func_75879_b() && ☃.func_179686_g()) {
            for(int ☃xx = 0; ☃xx < Math.min(☃x.func_75873_e() + 2, ☃x.func_75874_d()); ++☃xx) {
               PathPoint ☃xxx = ☃x.func_75877_a(☃xx);
               this.field_179507_b = new BlockPos(☃xxx.field_75839_a, ☃xxx.field_75837_b + 1, ☃xxx.field_75838_c);
               if (!(
                  this.field_75356_a
                        .func_70092_e(
                           (double)this.field_179507_b.func_177958_n(), this.field_75356_a.field_70163_u, (double)this.field_179507_b.func_177952_p()
                        )
                     > 2.25
               )) {
                  this.field_195923_c = this.func_195920_a(this.field_179507_b);
                  if (this.field_195923_c) {
                     return true;
                  }
               }
            }

            this.field_179507_b = new BlockPos(this.field_75356_a).func_177984_a();
            this.field_195923_c = this.func_195920_a(this.field_179507_b);
            return this.field_195923_c;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean func_75253_b() {
      return !this.field_75350_f;
   }

   @Override
   public void func_75249_e() {
      this.field_75350_f = false;
      this.field_75351_g = (float)((double)((float)this.field_179507_b.func_177958_n() + 0.5F) - this.field_75356_a.field_70165_t);
      this.field_75357_h = (float)((double)((float)this.field_179507_b.func_177952_p() + 0.5F) - this.field_75356_a.field_70161_v);
   }

   @Override
   public void func_75246_d() {
      float ☃ = (float)((double)((float)this.field_179507_b.func_177958_n() + 0.5F) - this.field_75356_a.field_70165_t);
      float ☃x = (float)((double)((float)this.field_179507_b.func_177952_p() + 0.5F) - this.field_75356_a.field_70161_v);
      float ☃xx = this.field_75351_g * ☃ + this.field_75357_h * ☃x;
      if (☃xx < 0.0F) {
         this.field_75350_f = true;
      }
   }

   private boolean func_195920_a(BlockPos var1) {
      IBlockState ☃ = this.field_75356_a.field_70170_p.func_180495_p(☃);
      return ☃.func_177230_c() instanceof BlockDoor && ☃.func_185904_a() == Material.field_151575_d;
   }
}
