package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ShulkerBullet extends Projectile {
   private static final double SPEED = 0.15;
   @Nullable
   private Entity finalTarget;
   @Nullable
   private Direction currentMoveDirection;
   private int flightSteps;
   private double targetDeltaX;
   private double targetDeltaY;
   private double targetDeltaZ;
   @Nullable
   private UUID targetId;

   public ShulkerBullet(EntityType<? extends ShulkerBullet> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.noPhysics = true;
   }

   public ShulkerBullet(Level var1, LivingEntity var2, Entity var3, Direction.Axis var4) {
      this(EntityType.SHULKER_BULLET, â˜ƒ);
      this.setOwner(â˜ƒ);
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      double â˜ƒx = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒxx = (double)â˜ƒ.getY() + 0.5;
      double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5;
      this.moveTo(â˜ƒx, â˜ƒxx, â˜ƒxxx, this.getYRot(), this.getXRot());
      this.finalTarget = â˜ƒ;
      this.currentMoveDirection = Direction.UP;
      this.selectNextMoveDirection(â˜ƒ);
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.HOSTILE;
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.finalTarget != null) {
         â˜ƒ.putUUID("Target", this.finalTarget.getUUID());
      }

      if (this.currentMoveDirection != null) {
         â˜ƒ.putInt("Dir", this.currentMoveDirection.get3DDataValue());
      }

      â˜ƒ.putInt("Steps", this.flightSteps);
      â˜ƒ.putDouble("TXD", this.targetDeltaX);
      â˜ƒ.putDouble("TYD", this.targetDeltaY);
      â˜ƒ.putDouble("TZD", this.targetDeltaZ);
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.flightSteps = â˜ƒ.getInt("Steps");
      this.targetDeltaX = â˜ƒ.getDouble("TXD");
      this.targetDeltaY = â˜ƒ.getDouble("TYD");
      this.targetDeltaZ = â˜ƒ.getDouble("TZD");
      if (â˜ƒ.contains("Dir", 99)) {
         this.currentMoveDirection = Direction.from3DDataValue(â˜ƒ.getInt("Dir"));
      }

      if (â˜ƒ.hasUUID("Target")) {
         this.targetId = â˜ƒ.getUUID("Target");
      }
   }

   @Override
   protected void defineSynchedData() {
   }

   @Nullable
   private Direction getMoveDirection() {
      return this.currentMoveDirection;
   }

   private void setMoveDirection(@Nullable Direction var1) {
      this.currentMoveDirection = â˜ƒ;
   }

   private void selectNextMoveDirection(@Nullable Direction.Axis var1) {
      double â˜ƒx = 0.5;
      BlockPos â˜ƒ;
      if (this.finalTarget == null) {
         â˜ƒ = this.blockPosition().below();
      } else {
         â˜ƒx = (double)this.finalTarget.getBbHeight() * 0.5;
         â˜ƒ = new BlockPos(this.finalTarget.getX(), this.finalTarget.getY() + â˜ƒx, this.finalTarget.getZ());
      }

      double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒx = (double)â˜ƒ.getY() + â˜ƒx;
      double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5;
      Direction â˜ƒxxx = null;
      if (!â˜ƒ.closerThan(this.position(), 2.0)) {
         BlockPos â˜ƒxxxx = this.blockPosition();
         List<Direction> â˜ƒxxxxx = Lists.<Direction>newArrayList();
         if (â˜ƒ != Direction.Axis.X) {
            if (â˜ƒxxxx.getX() < â˜ƒ.getX() && this.level.isEmptyBlock(â˜ƒxxxx.east())) {
               â˜ƒxxxxx.add(Direction.EAST);
            } else if (â˜ƒxxxx.getX() > â˜ƒ.getX() && this.level.isEmptyBlock(â˜ƒxxxx.west())) {
               â˜ƒxxxxx.add(Direction.WEST);
            }
         }

         if (â˜ƒ != Direction.Axis.Y) {
            if (â˜ƒxxxx.getY() < â˜ƒ.getY() && this.level.isEmptyBlock(â˜ƒxxxx.above())) {
               â˜ƒxxxxx.add(Direction.UP);
            } else if (â˜ƒxxxx.getY() > â˜ƒ.getY() && this.level.isEmptyBlock(â˜ƒxxxx.below())) {
               â˜ƒxxxxx.add(Direction.DOWN);
            }
         }

         if (â˜ƒ != Direction.Axis.Z) {
            if (â˜ƒxxxx.getZ() < â˜ƒ.getZ() && this.level.isEmptyBlock(â˜ƒxxxx.south())) {
               â˜ƒxxxxx.add(Direction.SOUTH);
            } else if (â˜ƒxxxx.getZ() > â˜ƒ.getZ() && this.level.isEmptyBlock(â˜ƒxxxx.north())) {
               â˜ƒxxxxx.add(Direction.NORTH);
            }
         }

         â˜ƒxxx = Direction.getRandom(this.random);
         if (â˜ƒxxxxx.isEmpty()) {
            for(int â˜ƒxxxx = 5; !this.level.isEmptyBlock(â˜ƒxxxx.relative(â˜ƒxxx)) && â˜ƒxxxx > 0; --â˜ƒxxxx) {
               â˜ƒxxx = Direction.getRandom(this.random);
            }
         } else {
            â˜ƒxxx = (Direction)â˜ƒxxxxx.get(this.random.nextInt(â˜ƒxxxxx.size()));
         }

         â˜ƒ = this.getX() + (double)â˜ƒxxx.getStepX();
         â˜ƒx = this.getY() + (double)â˜ƒxxx.getStepY();
         â˜ƒxx = this.getZ() + (double)â˜ƒxxx.getStepZ();
      }

      this.setMoveDirection(â˜ƒxxx);
      double â˜ƒ = â˜ƒ - this.getX();
      double â˜ƒx = â˜ƒx - this.getY();
      double â˜ƒxx = â˜ƒxx - this.getZ();
      double â˜ƒxxx = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
      if (â˜ƒxxx == 0.0) {
         this.targetDeltaX = 0.0;
         this.targetDeltaY = 0.0;
         this.targetDeltaZ = 0.0;
      } else {
         this.targetDeltaX = â˜ƒ / â˜ƒxxx * 0.15;
         this.targetDeltaY = â˜ƒx / â˜ƒxxx * 0.15;
         this.targetDeltaZ = â˜ƒxx / â˜ƒxxx * 0.15;
      }

      this.hasImpulse = true;
      this.flightSteps = 10 + this.random.nextInt(5) * 10;
   }

   @Override
   public void checkDespawn() {
      if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
         this.discard();
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level.isClientSide) {
         if (this.finalTarget == null && this.targetId != null) {
            this.finalTarget = ((ServerLevel)this.level).getEntity(this.targetId);
            if (this.finalTarget == null) {
               this.targetId = null;
            }
         }

         if (this.finalTarget == null || !this.finalTarget.isAlive() || this.finalTarget instanceof Player && this.finalTarget.isSpectator()) {
            if (!this.isNoGravity()) {
               this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
            }
         } else {
            this.targetDeltaX = Mth.clamp(this.targetDeltaX * 1.025, -1.0, 1.0);
            this.targetDeltaY = Mth.clamp(this.targetDeltaY * 1.025, -1.0, 1.0);
            this.targetDeltaZ = Mth.clamp(this.targetDeltaZ * 1.025, -1.0, 1.0);
            Vec3 â˜ƒ = this.getDeltaMovement();
            this.setDeltaMovement(â˜ƒ.add((this.targetDeltaX - â˜ƒ.x) * 0.2, (this.targetDeltaY - â˜ƒ.y) * 0.2, (this.targetDeltaZ - â˜ƒ.z) * 0.2));
         }

         HitResult â˜ƒ = ProjectileUtil.getHitResult(this, this::canHitEntity);
         if (â˜ƒ.getType() != HitResult.Type.MISS) {
            this.onHit(â˜ƒ);
         }
      }

      this.checkInsideBlocks();
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setPos(this.getX() + â˜ƒ.x, this.getY() + â˜ƒ.y, this.getZ() + â˜ƒ.z);
      ProjectileUtil.rotateTowardsMovement(this, 0.5F);
      if (this.level.isClientSide) {
         this.level.addParticle(ParticleTypes.END_ROD, this.getX() - â˜ƒ.x, this.getY() - â˜ƒ.y + 0.15, this.getZ() - â˜ƒ.z, 0.0, 0.0, 0.0);
      } else if (this.finalTarget != null && !this.finalTarget.isRemoved()) {
         if (this.flightSteps > 0) {
            --this.flightSteps;
            if (this.flightSteps == 0) {
               this.selectNextMoveDirection(this.currentMoveDirection == null ? null : this.currentMoveDirection.getAxis());
            }
         }

         if (this.currentMoveDirection != null) {
            BlockPos â˜ƒ = this.blockPosition();
            Direction.Axis â˜ƒx = this.currentMoveDirection.getAxis();
            if (this.level.loadedAndEntityCanStandOn(â˜ƒ.relative(this.currentMoveDirection), this)) {
               this.selectNextMoveDirection(â˜ƒx);
            } else {
               BlockPos â˜ƒ = this.finalTarget.blockPosition();
               if (â˜ƒx == Direction.Axis.X && â˜ƒ.getX() == â˜ƒ.getX()
                  || â˜ƒx == Direction.Axis.Z && â˜ƒ.getZ() == â˜ƒ.getZ()
                  || â˜ƒx == Direction.Axis.Y && â˜ƒ.getY() == â˜ƒ.getY()) {
                  this.selectNextMoveDirection(â˜ƒx);
               }
            }
         }
      }
   }

   @Override
   protected boolean canHitEntity(Entity var1) {
      return super.canHitEntity(â˜ƒ) && !â˜ƒ.noPhysics;
   }

   @Override
   public boolean isOnFire() {
      return false;
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      return â˜ƒ < 16384.0;
   }

   @Override
   public float getBrightness() {
      return 1.0F;
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      Entity â˜ƒ = â˜ƒ.getEntity();
      Entity â˜ƒx = this.getOwner();
      LivingEntity â˜ƒxx = â˜ƒx instanceof LivingEntity ? (LivingEntity)â˜ƒx : null;
      boolean â˜ƒxxx = â˜ƒ.hurt(DamageSource.indirectMobAttack(this, â˜ƒxx).setProjectile(), 4.0F);
      if (â˜ƒxxx) {
         this.doEnchantDamageEffects(â˜ƒxx, â˜ƒ);
         if (â˜ƒ instanceof LivingEntity) {
            ((LivingEntity)â˜ƒ).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200), MoreObjects.firstNonNull(â˜ƒx, this));
         }
      }
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      super.onHitBlock(â˜ƒ);
      ((ServerLevel)this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
      this.playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.0F);
   }

   @Override
   protected void onHit(HitResult var1) {
      super.onHit(â˜ƒ);
      this.discard();
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (!this.level.isClientSide) {
         this.playSound(SoundEvents.SHULKER_BULLET_HURT, 1.0F, 1.0F);
         ((ServerLevel)this.level).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
         this.discard();
      }

      return true;
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      double â˜ƒ = â˜ƒ.getXa();
      double â˜ƒx = â˜ƒ.getYa();
      double â˜ƒxx = â˜ƒ.getZa();
      this.setDeltaMovement(â˜ƒ, â˜ƒx, â˜ƒxx);
   }
}
