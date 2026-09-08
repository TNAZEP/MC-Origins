package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ZombieHorse extends AbstractHorse {
   public ZombieHorse(EntityType<? extends ZombieHorse> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static AttributeSupplier.Builder createAttributes() {
      return createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.MOVEMENT_SPEED, 0.2F);
   }

   @Override
   protected void randomizeAttributes() {
      this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
   }

   @Override
   public MobType getMobType() {
      return MobType.UNDEAD;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      super.getAmbientSound();
      return SoundEvents.ZOMBIE_HORSE_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      super.getDeathSound();
      return SoundEvents.ZOMBIE_HORSE_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      super.getHurtSound(â˜ƒ);
      return SoundEvents.ZOMBIE_HORSE_HURT;
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return EntityType.ZOMBIE_HORSE.create(â˜ƒ);
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!this.isTamed()) {
         return InteractionResult.PASS;
      } else if (this.isBaby()) {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      } else if (â˜ƒ.isSecondaryUseActive()) {
         this.openInventory(â˜ƒ);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else if (this.isVehicle()) {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      } else {
         if (!â˜ƒ.isEmpty()) {
            if (â˜ƒ.is(Items.SADDLE) && !this.isSaddled()) {
               this.openInventory(â˜ƒ);
               return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            InteractionResult â˜ƒ = â˜ƒ.interactLivingEntity(â˜ƒ, this, â˜ƒ);
            if (â˜ƒ.consumesAction()) {
               return â˜ƒ;
            }
         }

         this.doPlayerRide(â˜ƒ);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      }
   }

   @Override
   protected void addBehaviourGoals() {
   }
}
