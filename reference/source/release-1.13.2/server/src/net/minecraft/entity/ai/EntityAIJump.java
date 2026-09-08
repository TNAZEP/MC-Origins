package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityDolphin;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EntityAIJump extends EntityAIBase {
   private static final int[] field_211697_a = new int[]{0, 1, 4, 5, 6, 7};
   private final EntityDolphin field_205149_a;
   private final int field_205150_b;
   private boolean field_205151_c;

   public EntityAIJump(EntityDolphin var1, int var2) {
      this.field_205149_a = ☃;
      this.field_205150_b = ☃;
      this.func_75248_a(5);
   }

   @Override
   public boolean func_75250_a() {
      if (this.field_205149_a.func_70681_au().nextInt(this.field_205150_b) != 0) {
         return false;
      } else {
         EnumFacing ☃ = this.field_205149_a.func_184172_bi();
         int ☃x = ☃.func_82601_c();
         int ☃xx = ☃.func_82599_e();
         BlockPos ☃xxx = new BlockPos(this.field_205149_a);

         for(int ☃xxxx : field_211697_a) {
            if (!this.func_211695_a(☃xxx, ☃x, ☃xx, ☃xxxx) || !this.func_211696_b(☃xxx, ☃x, ☃xx, ☃xxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_211695_a(BlockPos var1, int var2, int var3, int var4) {
      BlockPos ☃ = ☃.func_177982_a(☃ * ☃, 0, ☃ * ☃);
      return this.field_205149_a.field_70170_p.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a)
         && !this.field_205149_a.field_70170_p.func_180495_p(☃).func_185904_a().func_76230_c();
   }

   private boolean func_211696_b(BlockPos var1, int var2, int var3, int var4) {
      return this.field_205149_a.field_70170_p.func_180495_p(☃.func_177982_a(☃ * ☃, 1, ☃ * ☃)).func_196958_f()
         && this.field_205149_a.field_70170_p.func_180495_p(☃.func_177982_a(☃ * ☃, 2, ☃ * ☃)).func_196958_f();
   }

   @Override
   public boolean func_75253_b() {
      return (
            !(this.field_205149_a.field_70181_x * this.field_205149_a.field_70181_x < 0.03F)
               || this.field_205149_a.field_70125_A == 0.0F
               || !(Math.abs(this.field_205149_a.field_70125_A) < 10.0F)
               || !this.field_205149_a.func_70090_H()
         )
         && !this.field_205149_a.field_70122_E;
   }

   @Override
   public boolean func_75252_g() {
      return false;
   }

   @Override
   public void func_75249_e() {
      EnumFacing ☃ = this.field_205149_a.func_184172_bi();
      this.field_205149_a.field_70159_w += (double)☃.func_82601_c() * 0.6;
      this.field_205149_a.field_70181_x += 0.7;
      this.field_205149_a.field_70179_y += (double)☃.func_82599_e() * 0.6;
      this.field_205149_a.func_70661_as().func_75499_g();
   }

   @Override
   public void func_75251_c() {
      this.field_205149_a.field_70125_A = 0.0F;
   }

   @Override
   public void func_75246_d() {
      boolean ☃ = this.field_205151_c;
      if (!☃) {
         IFluidState ☃x = this.field_205149_a.field_70170_p.func_204610_c(new BlockPos(this.field_205149_a));
         this.field_205151_c = ☃x.func_206884_a(FluidTags.field_206959_a);
      }

      if (this.field_205151_c && !☃) {
         this.field_205149_a.func_184185_a(SoundEvents.field_205209_aZ, 1.0F, 1.0F);
      }

      if (this.field_205149_a.field_70181_x * this.field_205149_a.field_70181_x < 0.03F && this.field_205149_a.field_70125_A != 0.0F) {
         this.field_205149_a.field_70125_A = this.func_205147_a(this.field_205149_a.field_70125_A, 0.0F, 0.2F);
      } else {
         double ☃ = Math.sqrt(
            this.field_205149_a.field_70159_w * this.field_205149_a.field_70159_w
               + this.field_205149_a.field_70181_x * this.field_205149_a.field_70181_x
               + this.field_205149_a.field_70179_y * this.field_205149_a.field_70179_y
         );
         double ☃x = Math.sqrt(
            this.field_205149_a.field_70159_w * this.field_205149_a.field_70159_w + this.field_205149_a.field_70179_y * this.field_205149_a.field_70179_y
         );
         double ☃xx = Math.signum(-this.field_205149_a.field_70181_x) * Math.acos(☃x / ☃) * 180.0F / (float)Math.PI;
         this.field_205149_a.field_70125_A = (float)☃xx;
      }
   }

   protected float func_205147_a(float var1, float var2, float var3) {
      float ☃ = ☃ - ☃;

      while(☃ < -180.0F) {
         ☃ += 360.0F;
      }

      while(☃ >= 180.0F) {
         ☃ -= 360.0F;
      }

      return ☃ + ☃ * ☃;
   }
}
