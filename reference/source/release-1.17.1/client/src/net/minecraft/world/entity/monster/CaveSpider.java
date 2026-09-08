package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class CaveSpider extends Spider {
   public CaveSpider(EntityType<? extends CaveSpider> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static AttributeSupplier.Builder createCaveSpider() {
      return Spider.createAttributes().add(Attributes.MAX_HEALTH, 12.0);
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      if (super.doHurtTarget(â˜ƒ)) {
         if (â˜ƒ instanceof LivingEntity) {
            int â˜ƒ = 0;
            if (this.level.getDifficulty() == Difficulty.NORMAL) {
               â˜ƒ = 7;
            } else if (this.level.getDifficulty() == Difficulty.HARD) {
               â˜ƒ = 15;
            }

            if (â˜ƒ > 0) {
               ((LivingEntity)â˜ƒ).addEffect(new MobEffectInstance(MobEffects.POISON, â˜ƒ * 20, 0), this);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      return â˜ƒ;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.45F;
   }
}
