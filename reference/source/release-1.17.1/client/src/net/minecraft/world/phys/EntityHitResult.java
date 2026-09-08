package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;

public class EntityHitResult extends HitResult {
   private final Entity entity;

   public EntityHitResult(Entity var1) {
      this(â˜ƒ, â˜ƒ.position());
   }

   public EntityHitResult(Entity var1, Vec3 var2) {
      super(â˜ƒ);
      this.entity = â˜ƒ;
   }

   public Entity getEntity() {
      return this.entity;
   }

   @Override
   public HitResult.Type getType() {
      return HitResult.Type.ENTITY;
   }
}
