package net.minecraft.world.entity.projectile;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownPotion extends ThrowableItemProjectile implements ItemSupplier {
   public static final double SPLASH_RANGE = 4.0;
   private static final double SPLASH_RANGE_SQ = 16.0;
   public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isSensitiveToWater;

   public ThrownPotion(EntityType<? extends ThrownPotion> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ThrownPotion(Level var1, LivingEntity var2) {
      super(EntityType.POTION, â˜ƒ, â˜ƒ);
   }

   public ThrownPotion(Level var1, double var2, double var4, double var6) {
      super(EntityType.POTION, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.SPLASH_POTION;
   }

   @Override
   protected float getGravity() {
      return 0.05F;
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      super.onHitBlock(â˜ƒ);
      if (!this.level.isClientSide) {
         ItemStack â˜ƒ = this.getItem();
         Potion â˜ƒx = PotionUtils.getPotion(â˜ƒ);
         List<MobEffectInstance> â˜ƒxx = PotionUtils.getMobEffects(â˜ƒ);
         boolean â˜ƒxxx = â˜ƒx == Potions.WATER && â˜ƒxx.isEmpty();
         Direction â˜ƒxxxx = â˜ƒ.getDirection();
         BlockPos â˜ƒxxxxx = â˜ƒ.getBlockPos();
         BlockPos â˜ƒxxxxxx = â˜ƒxxxxx.relative(â˜ƒxxxx);
         if (â˜ƒxxx) {
            this.dowseFire(â˜ƒxxxxxx);
            this.dowseFire(â˜ƒxxxxxx.relative(â˜ƒxxxx.getOpposite()));

            for(Direction â˜ƒxxxxxxx : Direction.Plane.HORIZONTAL) {
               this.dowseFire(â˜ƒxxxxxx.relative(â˜ƒxxxxxxx));
            }
         }
      }
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (!this.level.isClientSide) {
         ItemStack â˜ƒ = this.getItem();
         Potion â˜ƒx = PotionUtils.getPotion(â˜ƒ);
         List<MobEffectInstance> â˜ƒxx = PotionUtils.getMobEffects(â˜ƒ);
         boolean â˜ƒxxx = â˜ƒx == Potions.WATER && â˜ƒxx.isEmpty();
         if (â˜ƒxxx) {
            this.applyWater();
         } else if (!â˜ƒxx.isEmpty()) {
            if (this.isLingering()) {
               this.makeAreaOfEffectCloud(â˜ƒ, â˜ƒx);
            } else {
               this.applySplash(â˜ƒxx, â˜ƒ.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)â˜ƒ).getEntity() : null);
            }
         }

         int â˜ƒ = â˜ƒx.hasInstantEffects() ? 2007 : 2002;
         this.level.levelEvent(â˜ƒ, this.blockPosition(), PotionUtils.getColor(â˜ƒ));
         this.discard();
      }
   }

   private void applyWater() {
      AABB â˜ƒ = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
      List<LivingEntity> â˜ƒx = this.level.getEntitiesOfClass(LivingEntity.class, â˜ƒ, WATER_SENSITIVE);
      if (!â˜ƒx.isEmpty()) {
         for(LivingEntity â˜ƒxx : â˜ƒx) {
            double â˜ƒxxx = this.distanceToSqr(â˜ƒxx);
            if (â˜ƒxxx < 16.0 && â˜ƒxx.isSensitiveToWater()) {
               â˜ƒxx.hurt(DamageSource.indirectMagic(â˜ƒxx, this.getOwner()), 1.0F);
            }
         }
      }

      for(Axolotl â˜ƒ : this.level.getEntitiesOfClass(Axolotl.class, â˜ƒ)) {
         â˜ƒ.rehydrate();
      }
   }

   private void applySplash(List<MobEffectInstance> var1, @Nullable Entity var2) {
      AABB â˜ƒ = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
      List<LivingEntity> â˜ƒx = this.level.getEntitiesOfClass(LivingEntity.class, â˜ƒ);
      if (!â˜ƒx.isEmpty()) {
         Entity â˜ƒxx = this.getEffectSource();

         for(LivingEntity â˜ƒxxx : â˜ƒx) {
            if (â˜ƒxxx.isAffectedByPotions()) {
               double â˜ƒxxxx = this.distanceToSqr(â˜ƒxxx);
               if (â˜ƒxxxx < 16.0) {
                  double â˜ƒxxxxx = 1.0 - Math.sqrt(â˜ƒxxxx) / 4.0;
                  if (â˜ƒxxx == â˜ƒ) {
                     â˜ƒxxxxx = 1.0;
                  }

                  for(MobEffectInstance â˜ƒxxxxx : â˜ƒ) {
                     MobEffect â˜ƒxxxxxx = â˜ƒxxxxx.getEffect();
                     if (â˜ƒxxxxxx.isInstantenous()) {
                        â˜ƒxxxxxx.applyInstantenousEffect(this, this.getOwner(), â˜ƒxxx, â˜ƒxxxxx.getAmplifier(), â˜ƒxxxxx);
                     } else {
                        int â˜ƒxxxxxx = (int)(â˜ƒxxxxx * (double)â˜ƒxxxxx.getDuration() + 0.5);
                        if (â˜ƒxxxxxx > 20) {
                           â˜ƒxxx.addEffect(
                              new MobEffectInstance(â˜ƒxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxx.getAmplifier(), â˜ƒxxxxx.isAmbient(), â˜ƒxxxxx.isVisible()), â˜ƒxx
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void makeAreaOfEffectCloud(ItemStack var1, Potion var2) {
      AreaEffectCloud â˜ƒ = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
      Entity â˜ƒx = this.getOwner();
      if (â˜ƒx instanceof LivingEntity) {
         â˜ƒ.setOwner((LivingEntity)â˜ƒx);
      }

      â˜ƒ.setRadius(3.0F);
      â˜ƒ.setRadiusOnUse(-0.5F);
      â˜ƒ.setWaitTime(10);
      â˜ƒ.setRadiusPerTick(-â˜ƒ.getRadius() / (float)â˜ƒ.getDuration());
      â˜ƒ.setPotion(â˜ƒ);

      for(MobEffectInstance â˜ƒ : PotionUtils.getCustomEffects(â˜ƒ)) {
         â˜ƒ.addEffect(new MobEffectInstance(â˜ƒ));
      }

      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null && â˜ƒ.contains("CustomPotionColor", 99)) {
         â˜ƒ.setFixedColor(â˜ƒ.getInt("CustomPotionColor"));
      }

      this.level.addFreshEntity(â˜ƒ);
   }

   private boolean isLingering() {
      return this.getItem().is(Items.LINGERING_POTION);
   }

   private void dowseFire(BlockPos var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (â˜ƒ.is(BlockTags.FIRE)) {
         this.level.removeBlock(â˜ƒ, false);
      } else if (AbstractCandleBlock.isLit(â˜ƒ)) {
         AbstractCandleBlock.extinguish(null, â˜ƒ, this.level, â˜ƒ);
      } else if (CampfireBlock.isLitCampfire(â˜ƒ)) {
         this.level.levelEvent(null, 1009, â˜ƒ, 0);
         CampfireBlock.dowse(this.getOwner(), this.level, â˜ƒ, â˜ƒ);
         this.level.setBlockAndUpdate(â˜ƒ, â˜ƒ.setValue(CampfireBlock.LIT, Boolean.valueOf(false)));
      }
   }
}
