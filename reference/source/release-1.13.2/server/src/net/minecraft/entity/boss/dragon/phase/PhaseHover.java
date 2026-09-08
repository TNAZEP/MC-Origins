package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.Vec3d;

public class PhaseHover extends PhaseBase {
   private Vec3d field_188680_b;

   public PhaseHover(EntityDragon var1) {
      super(☃);
   }

   @Override
   public void func_188659_c() {
      if (this.field_188680_b == null) {
         this.field_188680_b = new Vec3d(this.field_188661_a.field_70165_t, this.field_188661_a.field_70163_u, this.field_188661_a.field_70161_v);
      }
   }

   @Override
   public boolean func_188654_a() {
      return true;
   }

   @Override
   public void func_188660_d() {
      this.field_188680_b = null;
   }

   @Override
   public float func_188651_f() {
      return 1.0F;
   }

   @Nullable
   @Override
   public Vec3d func_188650_g() {
      return this.field_188680_b;
   }

   @Override
   public PhaseType<PhaseHover> func_188652_i() {
      return PhaseType.field_188751_k;
   }
}
