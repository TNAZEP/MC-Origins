package net.minecraft.world.phys;

import net.minecraft.util.Mth;

public class Vec2 {
   public static final Vec2 ZERO = new Vec2(0.0F, 0.0F);
   public static final Vec2 ONE = new Vec2(1.0F, 1.0F);
   public static final Vec2 UNIT_X = new Vec2(1.0F, 0.0F);
   public static final Vec2 NEG_UNIT_X = new Vec2(-1.0F, 0.0F);
   public static final Vec2 UNIT_Y = new Vec2(0.0F, 1.0F);
   public static final Vec2 NEG_UNIT_Y = new Vec2(0.0F, -1.0F);
   public static final Vec2 MAX = new Vec2(Float.MAX_VALUE, Float.MAX_VALUE);
   public static final Vec2 MIN = new Vec2(Float.MIN_VALUE, Float.MIN_VALUE);
   public final float x;
   public final float y;

   public Vec2(float var1, float var2) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   public Vec2 scale(float var1) {
      return new Vec2(this.x * â˜ƒ, this.y * â˜ƒ);
   }

   public float dot(Vec2 var1) {
      return this.x * â˜ƒ.x + this.y * â˜ƒ.y;
   }

   public Vec2 add(Vec2 var1) {
      return new Vec2(this.x + â˜ƒ.x, this.y + â˜ƒ.y);
   }

   public Vec2 add(float var1) {
      return new Vec2(this.x + â˜ƒ, this.y + â˜ƒ);
   }

   public boolean equals(Vec2 var1) {
      return this.x == â˜ƒ.x && this.y == â˜ƒ.y;
   }

   public Vec2 normalized() {
      float â˜ƒ = Mth.sqrt(this.x * this.x + this.y * this.y);
      return â˜ƒ < 1.0E-4F ? ZERO : new Vec2(this.x / â˜ƒ, this.y / â˜ƒ);
   }

   public float length() {
      return Mth.sqrt(this.x * this.x + this.y * this.y);
   }

   public float lengthSquared() {
      return this.x * this.x + this.y * this.y;
   }

   public float distanceToSqr(Vec2 var1) {
      float â˜ƒ = â˜ƒ.x - this.x;
      float â˜ƒx = â˜ƒ.y - this.y;
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx;
   }

   public Vec2 negated() {
      return new Vec2(-this.x, -this.y);
   }
}
