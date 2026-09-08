package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;

public abstract class HitResult {
   protected final Vec3 location;

   protected HitResult(Vec3 var1) {
      this.location = â˜ƒ;
   }

   public double distanceTo(Entity var1) {
      double â˜ƒ = this.location.x - â˜ƒ.getX();
      double â˜ƒx = this.location.y - â˜ƒ.getY();
      double â˜ƒxx = this.location.z - â˜ƒ.getZ();
      return â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
   }

   public abstract HitResult.Type getType();

   public Vec3 getLocation() {
      return this.location;
   }

   public static enum Type {
      MISS,
      BLOCK,
      ENTITY;
   }
}
