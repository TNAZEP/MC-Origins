package net.minecraft.core;

import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;

public class Rotations {
   protected final float x;
   protected final float y;
   protected final float z;

   public Rotations(float var1, float var2, float var3) {
      this.x = !Float.isInfinite(â˜ƒ) && !Float.isNaN(â˜ƒ) ? â˜ƒ % 360.0F : 0.0F;
      this.y = !Float.isInfinite(â˜ƒ) && !Float.isNaN(â˜ƒ) ? â˜ƒ % 360.0F : 0.0F;
      this.z = !Float.isInfinite(â˜ƒ) && !Float.isNaN(â˜ƒ) ? â˜ƒ % 360.0F : 0.0F;
   }

   public Rotations(ListTag var1) {
      this(â˜ƒ.getFloat(0), â˜ƒ.getFloat(1), â˜ƒ.getFloat(2));
   }

   public ListTag save() {
      ListTag â˜ƒ = new ListTag();
      â˜ƒ.add(FloatTag.valueOf(this.x));
      â˜ƒ.add(FloatTag.valueOf(this.y));
      â˜ƒ.add(FloatTag.valueOf(this.z));
      return â˜ƒ;
   }

   public boolean equals(Object var1) {
      if (!(â˜ƒ instanceof Rotations)) {
         return false;
      } else {
         Rotations â˜ƒ = (Rotations)â˜ƒ;
         return this.x == â˜ƒ.x && this.y == â˜ƒ.y && this.z == â˜ƒ.z;
      }
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getZ() {
      return this.z;
   }

   public float getWrappedX() {
      return Mth.wrapDegrees(this.x);
   }

   public float getWrappedY() {
      return Mth.wrapDegrees(this.y);
   }

   public float getWrappedZ() {
      return Mth.wrapDegrees(this.z);
   }
}
