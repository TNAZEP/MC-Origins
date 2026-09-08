package net.minecraft.world.entity.animal.horse;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;

public class Horse extends AbstractHorse {
   private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
   private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(Horse.class, EntityDataSerializers.INT);

   public Horse(EntityType<? extends Horse> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void randomizeAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
      this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
      this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Variant", this.getTypeVariant());
      if (!this.inventory.getItem(1).isEmpty()) {
         â˜ƒ.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
      }
   }

   public ItemStack getArmor() {
      return this.getItemBySlot(EquipmentSlot.CHEST);
   }

   private void setArmor(ItemStack var1) {
      this.setItemSlot(EquipmentSlot.CHEST, â˜ƒ);
      this.setDropChance(EquipmentSlot.CHEST, 0.0F);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setTypeVariant(â˜ƒ.getInt("Variant"));
      if (â˜ƒ.contains("ArmorItem", 10)) {
         ItemStack â˜ƒ = ItemStack.of(â˜ƒ.getCompound("ArmorItem"));
         if (!â˜ƒ.isEmpty() && this.isArmor(â˜ƒ)) {
            this.inventory.setItem(1, â˜ƒ);
         }
      }

      this.updateContainerEquipment();
   }

   private void setTypeVariant(int var1) {
      this.entityData.set(DATA_ID_TYPE_VARIANT, â˜ƒ);
   }

   private int getTypeVariant() {
      return this.entityData.get(DATA_ID_TYPE_VARIANT);
   }

   private void setVariantAndMarkings(Variant var1, Markings var2) {
      this.setTypeVariant(â˜ƒ.getId() & 0xFF | â˜ƒ.getId() << 8 & 0xFF00);
   }

   public Variant getVariant() {
      return Variant.byId(this.getTypeVariant() & 0xFF);
   }

   public Markings getMarkings() {
      return Markings.byId((this.getTypeVariant() & 0xFF00) >> 8);
   }

   @Override
   protected void updateContainerEquipment() {
      if (!this.level.isClientSide) {
         super.updateContainerEquipment();
         this.setArmorEquipment(this.inventory.getItem(1));
         this.setDropChance(EquipmentSlot.CHEST, 0.0F);
      }
   }

   private void setArmorEquipment(ItemStack var1) {
      this.setArmor(â˜ƒ);
      if (!this.level.isClientSide) {
         this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER_UUID);
         if (this.isArmor(â˜ƒ)) {
            int â˜ƒ = ((HorseArmorItem)â˜ƒ.getItem()).getProtection();
            if (â˜ƒ != 0) {
               this.getAttribute(Attributes.ARMOR)
                  .addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Horse armor bonus", (double)â˜ƒ, AttributeModifier.Operation.ADDITION));
            }
         }
      }
   }

   @Override
   public void containerChanged(Container var1) {
      ItemStack â˜ƒ = this.getArmor();
      super.containerChanged(â˜ƒ);
      ItemStack â˜ƒx = this.getArmor();
      if (this.tickCount > 20 && this.isArmor(â˜ƒx) && â˜ƒ != â˜ƒx) {
         this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
      }
   }

   @Override
   protected void playGallopSound(SoundType var1) {
      super.playGallopSound(â˜ƒ);
      if (this.random.nextInt(10) == 0) {
         this.playSound(SoundEvents.HORSE_BREATHE, â˜ƒ.getVolume() * 0.6F, â˜ƒ.getPitch());
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      super.getAmbientSound();
      return SoundEvents.HORSE_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      super.getDeathSound();
      return SoundEvents.HORSE_DEATH;
   }

   @Nullable
   @Override
   protected SoundEvent getEatingSound() {
      return SoundEvents.HORSE_EAT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      super.getHurtSound(â˜ƒ);
      return SoundEvents.HORSE_HURT;
   }

   @Override
   protected SoundEvent getAngrySound() {
      super.getAngrySound();
      return SoundEvents.HORSE_ANGRY;
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!this.isBaby()) {
         if (this.isTamed() && â˜ƒ.isSecondaryUseActive()) {
            this.openInventory(â˜ƒ);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }

         if (this.isVehicle()) {
            return super.mobInteract(â˜ƒ, â˜ƒ);
         }
      }

      if (!â˜ƒ.isEmpty()) {
         if (this.isFood(â˜ƒ)) {
            return this.fedFood(â˜ƒ, â˜ƒ);
         }

         InteractionResult â˜ƒ = â˜ƒ.interactLivingEntity(â˜ƒ, this, â˜ƒ);
         if (â˜ƒ.consumesAction()) {
            return â˜ƒ;
         }

         if (!this.isTamed()) {
            this.makeMad();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }

         boolean â˜ƒ = !this.isBaby() && !this.isSaddled() && â˜ƒ.is(Items.SADDLE);
         if (this.isArmor(â˜ƒ) || â˜ƒ) {
            this.openInventory(â˜ƒ);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }
      }

      if (this.isBaby()) {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      } else {
         this.doPlayerRide(â˜ƒ);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      }
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
      AbstractHorse â˜ƒ;
      if (â˜ƒ instanceof Donkey) {
         â˜ƒ = EntityType.MULE.create(â˜ƒ);
      } else {
         Horse â˜ƒx = (Horse)â˜ƒ;
         â˜ƒ = EntityType.HORSE.create(â˜ƒ);
         int â˜ƒxx = this.random.nextInt(9);
         Variant â˜ƒ;
         if (â˜ƒxx < 4) {
            â˜ƒ = this.getVariant();
         } else if (â˜ƒxx < 8) {
            â˜ƒ = â˜ƒx.getVariant();
         } else {
            â˜ƒ = Util.getRandom((Variant[])Variant.values(), this.random);
         }

         int â˜ƒx = this.random.nextInt(5);
         Markings â˜ƒ;
         if (â˜ƒx < 2) {
            â˜ƒ = this.getMarkings();
         } else if (â˜ƒx < 4) {
            â˜ƒ = â˜ƒx.getMarkings();
         } else {
            â˜ƒ = Util.getRandom((Markings[])Markings.values(), this.random);
         }

         ((Horse)â˜ƒ).setVariantAndMarkings(â˜ƒ, â˜ƒ);
      }

      this.setOffspringAttributes(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public boolean canWearArmor() {
      return true;
   }

   @Override
   public boolean isArmor(ItemStack var1) {
      return â˜ƒ.getItem() instanceof HorseArmorItem;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      Variant â˜ƒ;
      if (â˜ƒ instanceof Horse.HorseGroupData) {
         â˜ƒ = ((Horse.HorseGroupData)â˜ƒ).variant;
      } else {
         â˜ƒ = Util.getRandom((Variant[])Variant.values(), this.random);
         â˜ƒ = new Horse.HorseGroupData(â˜ƒ);
      }

      this.setVariantAndMarkings(â˜ƒ, Util.getRandom((Markings[])Markings.values(), this.random));
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static class HorseGroupData extends AgeableMob.AgeableMobGroupData {
      public final Variant variant;

      public HorseGroupData(Variant var1) {
         super(true);
         this.variant = â˜ƒ;
      }
   }
}
