package net.minecraft.world.entity;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityDimensions {
   public final float width;
   public final float height;
   public final boolean fixed;

   public EntityDimensions(float var1, float var2, boolean var3) {
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.fixed = â˜ƒ;
   }

   public AABB makeBoundingBox(Vec3 var1) {
      return this.makeBoundingBox(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public AABB makeBoundingBox(double var1, double var3, double var5) {
      float â˜ƒ = this.width / 2.0F;
      float â˜ƒx = this.height;
      return new AABB(â˜ƒ - (double)â˜ƒ, â˜ƒ, â˜ƒ - (double)â˜ƒ, â˜ƒ + (double)â˜ƒ, â˜ƒ + (double)â˜ƒx, â˜ƒ + (double)â˜ƒ);
   }

   public EntityDimensions scale(float var1) {
      return this.scale(â˜ƒ, â˜ƒ);
   }

   public EntityDimensions scale(float var1, float var2) {
      return !this.fixed && (â˜ƒ != 1.0F || â˜ƒ != 1.0F) ? scalable(this.width * â˜ƒ, this.height * â˜ƒ) : this;
   }

   public static EntityDimensions scalable(float var0, float var1) {
      return new EntityDimensions(â˜ƒ, â˜ƒ, false);
   }

   public static EntityDimensions fixed(float var0, float var1) {
      return new EntityDimensions(â˜ƒ, â˜ƒ, true);
   }

   public String toString() {
      return "EntityDimensions w=" + this.width + ", h=" + this.height + ", fixed=" + this.fixed;
   }
}
