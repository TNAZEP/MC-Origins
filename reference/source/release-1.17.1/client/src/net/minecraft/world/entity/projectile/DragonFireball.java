package net.minecraft.world.entity.projectile;

import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DragonFireball extends AbstractHurtingProjectile {
   public static final float SPLASH_RANGE = 4.0F;

   public DragonFireball(EntityType<? extends DragonFireball> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public DragonFireball(Level var1, LivingEntity var2, double var3, double var5, double var7) {
      super(EntityType.DRAGON_FIREBALL, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (â˜ƒ.getType() != HitResult.Type.ENTITY || !this.ownedBy(((EntityHitResult)â˜ƒ).getEntity())) {
         if (!this.level.isClientSide) {
            List<LivingEntity> â˜ƒ = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0, 2.0, 4.0));
            AreaEffectCloud â˜ƒx = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
            Entity â˜ƒxx = this.getOwner();
            if (â˜ƒxx instanceof LivingEntity) {
               â˜ƒx.setOwner((LivingEntity)â˜ƒxx);
            }

            â˜ƒx.setParticle(ParticleTypes.DRAGON_BREATH);
            â˜ƒx.setRadius(3.0F);
            â˜ƒx.setDuration(600);
            â˜ƒx.setRadiusPerTick((7.0F - â˜ƒx.getRadius()) / (float)â˜ƒx.getDuration());
            â˜ƒx.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
            if (!â˜ƒ.isEmpty()) {
               for(LivingEntity â˜ƒ : â˜ƒ) {
                  double â˜ƒx = this.distanceToSqr(â˜ƒ);
                  if (â˜ƒx < 16.0) {
                     â˜ƒx.setPos(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
                     break;
                  }
               }
            }

            this.level.levelEvent(2006, this.blockPosition(), this.isSilent() ? -1 : 1);
            this.level.addFreshEntity(â˜ƒx);
            this.discard();
         }
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
   protected ParticleOptions getTrailParticle() {
      return ParticleTypes.DRAGON_BREATH;
   }

   @Override
   protected boolean shouldBurn() {
      return false;
   }
}
