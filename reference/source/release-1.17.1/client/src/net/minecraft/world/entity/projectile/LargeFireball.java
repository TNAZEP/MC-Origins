package net.minecraft.world.entity.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class LargeFireball extends Fireball {
   private int explosionPower = 1;

   public LargeFireball(EntityType<? extends LargeFireball> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public LargeFireball(Level var1, LivingEntity var2, double var3, double var5, double var7, int var9) {
      super(EntityType.FIREBALL, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.explosionPower = â˜ƒ;
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (!this.level.isClientSide) {
         boolean â˜ƒ = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
         this.level
            .explode(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               (float)this.explosionPower,
               â˜ƒ,
               â˜ƒ ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE
            );
         this.discard();
      }
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      if (!this.level.isClientSide) {
         Entity â˜ƒ = â˜ƒ.getEntity();
         Entity â˜ƒx = this.getOwner();
         â˜ƒ.hurt(DamageSource.fireball(this, â˜ƒx), 6.0F);
         if (â˜ƒx instanceof LivingEntity) {
            this.doEnchantDamageEffects((LivingEntity)â˜ƒx, â˜ƒ);
         }
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putByte("ExplosionPower", (byte)this.explosionPower);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("ExplosionPower", 99)) {
         this.explosionPower = â˜ƒ.getByte("ExplosionPower");
      }
   }
}
