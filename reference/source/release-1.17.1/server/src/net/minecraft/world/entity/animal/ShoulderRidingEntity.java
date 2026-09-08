package net.minecraft.world.entity.animal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

public abstract class ShoulderRidingEntity extends TamableAnimal {
   private static final int RIDE_COOLDOWN = 100;
   private int rideCooldownCounter;

   protected ShoulderRidingEntity(EntityType<? extends ShoulderRidingEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public boolean setEntityOnShoulder(ServerPlayer var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("id", this.getEncodeId());
      this.saveWithoutId(â˜ƒ);
      if (â˜ƒ.setEntityOnShoulder(â˜ƒ)) {
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void tick() {
      ++this.rideCooldownCounter;
      super.tick();
   }

   public boolean canSitOnShoulder() {
      return this.rideCooldownCounter > 100;
   }
}
