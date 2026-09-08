package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WitherSkull extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Boolean> DATA_DANGEROUS = SynchedEntityData.defineId(WitherSkull.class, EntityDataSerializers.BOOLEAN);

   public WitherSkull(EntityType<? extends WitherSkull> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public WitherSkull(Level var1, LivingEntity var2, double var3, double var5, double var7) {
      super(EntityType.WITHER_SKULL, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected float getInertia() {
      return this.isDangerous() ? 0.73F : super.getInertia();
   }

   @Override
   public boolean isOnFire() {
      return false;
   }

   @Override
   public float getBlockExplosionResistance(Explosion var1, BlockGetter var2, BlockPos var3, BlockState var4, FluidState var5, float var6) {
      return this.isDangerous() && WitherBoss.canDestroy(â˜ƒ) ? Math.min(0.8F, â˜ƒ) : â˜ƒ;
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      if (!this.level.isClientSide) {
         Entity â˜ƒxx = â˜ƒ.getEntity();
         Entity â˜ƒxxx = this.getOwner();
         boolean â˜ƒx;
         if (â˜ƒxxx instanceof LivingEntity â˜ƒ) {
            â˜ƒx = â˜ƒxx.hurt(DamageSource.witherSkull(this, â˜ƒ), 8.0F);
            if (â˜ƒx) {
               if (â˜ƒxx.isAlive()) {
                  this.doEnchantDamageEffects(â˜ƒ, â˜ƒxx);
               } else {
                  â˜ƒ.heal(5.0F);
               }
            }
         } else {
            â˜ƒx = â˜ƒxx.hurt(DamageSource.MAGIC, 5.0F);
         }

         if (â˜ƒx && â˜ƒxx instanceof LivingEntity) {
            int â˜ƒ = 0;
            if (this.level.getDifficulty() == Difficulty.NORMAL) {
               â˜ƒ = 10;
            } else if (this.level.getDifficulty() == Difficulty.HARD) {
               â˜ƒ = 40;
            }

            if (â˜ƒ > 0) {
               ((LivingEntity)â˜ƒxx).addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * â˜ƒ, 1), this.getEffectSource());
            }
         }
      }
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (!this.level.isClientSide) {
         Explosion.BlockInteraction â˜ƒ = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
            ? Explosion.BlockInteraction.DESTROY
            : Explosion.BlockInteraction.NONE;
         this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, â˜ƒ);
         this.discard();
      }
   }

   @Override
   public boolean isPickable() {
      return false;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      return false;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_DANGEROUS, false);
   }

   public boolean isDangerous() {
      return this.entityData.get(DATA_DANGEROUS);
   }

   public void setDangerous(boolean var1) {
      this.entityData.set(DATA_DANGEROUS, â˜ƒ);
   }

   @Override
   protected boolean shouldBurn() {
      return false;
   }
}
