package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class Donkey extends AbstractChestedHorse {
   public Donkey(EntityType<? extends Donkey> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      super.getAmbientSound();
      return SoundEvents.DONKEY_AMBIENT;
   }

   @Override
   protected SoundEvent getAngrySound() {
      super.getAngrySound();
      return SoundEvents.DONKEY_ANGRY;
   }

   @Override
   protected SoundEvent getDeathSound() {
      super.getDeathSound();
      return SoundEvents.DONKEY_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getEatingSound() {
      return SoundEvents.DONKEY_EAT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      super.getHurtSound(â˜ƒ);
      return SoundEvents.DONKEY_HURT;
   }

   @Override
   public boolean canMate(Animal var1) {
      if (â˜ƒ == this) {
         return false;
      } else if (!(â˜ƒ instanceof Donkey) && !(â˜ƒ instanceof Horse)) {
         return false;
      } else {
         return this.canParent() && ((AbstractHorse)â˜ƒ).canParent();
      }
   }

   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      EntityType<? extends AbstractHorse> â˜ƒ = â˜ƒ instanceof Horse ? EntityType.MULE : EntityType.DONKEY;
      AbstractHorse â˜ƒx = â˜ƒ.create(â˜ƒ);
      this.setOffspringAttributes(â˜ƒ, â˜ƒx);
      return â˜ƒx;
   }
}
