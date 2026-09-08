package net.minecraft.world.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EvokerFangs extends Entity {
   public static final int ATTACK_DURATION = 20;
   public static final int LIFE_OFFSET = 2;
   public static final int ATTACK_TRIGGER_TICKS = 14;
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 22;
   private boolean clientSideAttackStarted;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public EvokerFangs(EntityType<? extends EvokerFangs> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public EvokerFangs(Level var1, double var2, double var4, double var6, float var8, int var9, LivingEntity var10) {
      this(EntityType.EVOKER_FANGS, â˜ƒ);
      this.warmupDelayTicks = â˜ƒ;
      this.setOwner(â˜ƒ);
      this.setYRot(â˜ƒ * (180.0F / (float)Math.PI));
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
   }

   public void setOwner(@Nullable LivingEntity var1) {
      this.owner = â˜ƒ;
      this.ownerUUID = â˜ƒ == null ? null : â˜ƒ.getUUID();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel) {
         Entity â˜ƒ = ((ServerLevel)this.level).getEntity(this.ownerUUID);
         if (â˜ƒ instanceof LivingEntity) {
            this.owner = (LivingEntity)â˜ƒ;
         }
      }

      return this.owner;
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      this.warmupDelayTicks = â˜ƒ.getInt("Warmup");
      if (â˜ƒ.hasUUID("Owner")) {
         this.ownerUUID = â˜ƒ.getUUID("Owner");
      }
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putInt("Warmup", this.warmupDelayTicks);
      if (this.ownerUUID != null) {
         â˜ƒ.putUUID("Owner", this.ownerUUID);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level.isClientSide) {
         if (this.clientSideAttackStarted) {
            --this.lifeTicks;
            if (this.lifeTicks == 14) {
               for(int â˜ƒ = 0; â˜ƒ < 12; ++â˜ƒ) {
                  double â˜ƒx = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                  double â˜ƒxx = this.getY() + 0.05 + this.random.nextDouble();
                  double â˜ƒxxx = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                  double â˜ƒxxxx = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                  double â˜ƒxxxxx = 0.3 + this.random.nextDouble() * 0.3;
                  double â˜ƒxxxxxx = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                  this.level.addParticle(ParticleTypes.CRIT, â˜ƒx, â˜ƒxx + 1.0, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
               }
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -8) {
            for(LivingEntity â˜ƒ : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
               this.dealDamageTo(â˜ƒ);
            }
         }

         if (!this.sentSpikeEvent) {
            this.level.broadcastEntityEvent(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (--this.lifeTicks < 0) {
            this.discard();
         }
      }
   }

   private void dealDamageTo(LivingEntity var1) {
      LivingEntity â˜ƒ = this.getOwner();
      if (â˜ƒ.isAlive() && !â˜ƒ.isInvulnerable() && â˜ƒ != â˜ƒ) {
         if (â˜ƒ == null) {
            â˜ƒ.hurt(DamageSource.MAGIC, 6.0F);
         } else {
            if (â˜ƒ.isAlliedTo(â˜ƒ)) {
               return;
            }

            â˜ƒ.hurt(DamageSource.indirectMagic(this, â˜ƒ), 6.0F);
         }
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      super.handleEntityEvent(â˜ƒ);
      if (â˜ƒ == 4) {
         this.clientSideAttackStarted = true;
         if (!this.isSilent()) {
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.EVOKER_FANGS_ATTACK,
                  this.getSoundSource(),
                  1.0F,
                  this.random.nextFloat() * 0.2F + 0.85F,
                  false
               );
         }
      }
   }

   public float getAnimationProgress(float var1) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      } else {
         int â˜ƒ = this.lifeTicks - 2;
         return â˜ƒ <= 0 ? 1.0F : 1.0F - ((float)â˜ƒ - â˜ƒ) / 20.0F;
      }
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }
}
