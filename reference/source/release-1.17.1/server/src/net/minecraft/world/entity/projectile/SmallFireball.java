package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SmallFireball extends Fireball {
   public SmallFireball(EntityType<? extends SmallFireball> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public SmallFireball(Level var1, LivingEntity var2, double var3, double var5, double var7) {
      super(EntityType.SMALL_FIREBALL, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public SmallFireball(Level var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(EntityType.SMALL_FIREBALL, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      if (!this.level.isClientSide) {
         Entity â˜ƒ = â˜ƒ.getEntity();
         if (!â˜ƒ.fireImmune()) {
            Entity â˜ƒx = this.getOwner();
            int â˜ƒxx = â˜ƒ.getRemainingFireTicks();
            â˜ƒ.setSecondsOnFire(5);
            boolean â˜ƒxxx = â˜ƒ.hurt(DamageSource.fireball(this, â˜ƒx), 5.0F);
            if (!â˜ƒxxx) {
               â˜ƒ.setRemainingFireTicks(â˜ƒxx);
            } else if (â˜ƒx instanceof LivingEntity) {
               this.doEnchantDamageEffects((LivingEntity)â˜ƒx, â˜ƒ);
            }
         }
      }
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      super.onHitBlock(â˜ƒ);
      if (!this.level.isClientSide) {
         Entity â˜ƒ = this.getOwner();
         if (!(â˜ƒ instanceof Mob) || this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            BlockPos â˜ƒx = â˜ƒ.getBlockPos().relative(â˜ƒ.getDirection());
            if (this.level.isEmptyBlock(â˜ƒx)) {
               this.level.setBlockAndUpdate(â˜ƒx, BaseFireBlock.getState(this.level, â˜ƒx));
            }
         }
      }
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      if (!this.level.isClientSide) {
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
}
