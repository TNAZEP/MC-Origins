package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownEnderpearl extends ThrowableItemProjectile {
   public ThrownEnderpearl(EntityType<? extends ThrownEnderpearl> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ThrownEnderpearl(Level var1, LivingEntity var2) {
      super(EntityType.ENDER_PEARL, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.ENDER_PEARL;
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      â˜ƒ.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < 32; ++â˜ƒ) {
         this.level
            .addParticle(
               ParticleTypes.PORTAL,
               this.getX(),
               this.getY() + this.random.nextDouble() * 2.0,
               this.getZ(),
               this.random.nextGaussian(),
               0.0,
               this.random.nextGaussian()
            );
      }

      if (!this.level.isClientSide && !this.isRemoved()) {
         Entity â˜ƒx = this.getOwner();
         if (â˜ƒx instanceof ServerPlayer â˜ƒ) {
            if (â˜ƒ.connection.getConnection().isConnected() && â˜ƒ.level == this.level && !â˜ƒ.isSleeping()) {
               if (this.random.nextFloat() < 0.05F && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                  Endermite â˜ƒxx = EntityType.ENDERMITE.create(this.level);
                  â˜ƒxx.moveTo(â˜ƒx.getX(), â˜ƒx.getY(), â˜ƒx.getZ(), â˜ƒx.getYRot(), â˜ƒx.getXRot());
                  this.level.addFreshEntity(â˜ƒxx);
               }

               if (â˜ƒx.isPassenger()) {
                  â˜ƒ.dismountTo(this.getX(), this.getY(), this.getZ());
               } else {
                  â˜ƒx.teleportTo(this.getX(), this.getY(), this.getZ());
               }

               â˜ƒx.fallDistance = 0.0F;
               â˜ƒx.hurt(DamageSource.FALL, 5.0F);
            }
         } else if (â˜ƒx != null) {
            â˜ƒx.teleportTo(this.getX(), this.getY(), this.getZ());
            â˜ƒx.fallDistance = 0.0F;
         }

         this.discard();
      }
   }

   @Override
   public void tick() {
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ instanceof Player && !â˜ƒ.isAlive()) {
         this.discard();
      } else {
         super.tick();
      }
   }

   @Nullable
   @Override
   public Entity changeDimension(ServerLevel var1) {
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ != null && â˜ƒ.level.dimension() != â˜ƒ.dimension()) {
         this.setOwner(null);
      }

      return super.changeDimension(â˜ƒ);
   }
}
