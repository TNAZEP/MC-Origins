package net.minecraft.client.renderer.entity.model;

import net.minecraft.util.math.Vec3d;

public class PositionTextureVertex {
   public Vec3d field_78243_a;
   public float field_78241_b;
   public float field_78242_c;

   public PositionTextureVertex(float var1, float var2, float var3, float var4, float var5) {
      this(new Vec3d((double)☃, (double)☃, (double)☃), ☃, ☃);
   }

   public PositionTextureVertex func_78240_a(float var1, float var2) {
      return new PositionTextureVertex(this, ☃, ☃);
   }

   public PositionTextureVertex(PositionTextureVertex var1, float var2, float var3) {
      this.field_78243_a = ☃.field_78243_a;
      this.field_78241_b = ☃;
      this.field_78242_c = ☃;
   }

   public PositionTextureVertex(Vec3d var1, float var2, float var3) {
      this.field_78243_a = ☃;
      this.field_78241_b = ☃;
      this.field_78242_c = ☃;
   }
}
