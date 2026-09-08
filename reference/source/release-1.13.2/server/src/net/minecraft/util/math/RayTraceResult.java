package net.minecraft.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public class RayTraceResult {
   private BlockPos field_178783_e;
   public RayTraceResult.Type field_72313_a;
   public EnumFacing field_178784_b;
   public Vec3d field_72307_f;
   public Entity field_72308_g;

   public RayTraceResult(Vec3d var1, EnumFacing var2, BlockPos var3) {
      this(RayTraceResult.Type.BLOCK, ☃, ☃, ☃);
   }

   public RayTraceResult(Entity var1) {
      this(☃, new Vec3d(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v));
   }

   public RayTraceResult(RayTraceResult.Type var1, Vec3d var2, EnumFacing var3, BlockPos var4) {
      this.field_72313_a = ☃;
      this.field_178783_e = ☃;
      this.field_178784_b = ☃;
      this.field_72307_f = new Vec3d(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public RayTraceResult(Entity var1, Vec3d var2) {
      this.field_72313_a = RayTraceResult.Type.ENTITY;
      this.field_72308_g = ☃;
      this.field_72307_f = ☃;
   }

   public BlockPos func_178782_a() {
      return this.field_178783_e;
   }

   public String toString() {
      return "HitResult{type="
         + this.field_72313_a
         + ", blockpos="
         + this.field_178783_e
         + ", f="
         + this.field_178784_b
         + ", pos="
         + this.field_72307_f
         + ", entity="
         + this.field_72308_g
         + '}';
   }

   public static enum Type {
      MISS,
      BLOCK,
      ENTITY;
   }
}
