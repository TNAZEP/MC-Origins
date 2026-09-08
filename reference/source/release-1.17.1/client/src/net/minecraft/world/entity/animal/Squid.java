package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class Squid extends WaterAnimal {
   public float xBodyRot;
   public float xBodyRotO;
   public float zBodyRot;
   public float zBodyRotO;
   public float tentacleMovement;
   public float oldTentacleMovement;
   public float tentacleAngle;
   public float oldTentacleAngle;
   private float speed;
   private float tentacleSpeed;
   private float rotateSpeed;
   private float tx;
   private float ty;
   private float tz;

   public Squid(EntityType<? extends Squid> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.random.setSeed((long)this.getId());
      this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new Squid.SquidRandomMovementGoal(this));
      this.goalSelector.addGoal(1, new Squid.SquidFleeGoal());
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0);
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * 0.5F;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.SQUID_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.SQUID_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.SQUID_DEATH;
   }

   protected SoundEvent getSquirtSound() {
      return SoundEvents.SQUID_SQUIRT;
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return !this.isLeashed();
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.EVENTS;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      this.xBodyRotO = this.xBodyRot;
      this.zBodyRotO = this.zBodyRot;
      this.oldTentacleMovement = this.tentacleMovement;
      this.oldTentacleAngle = this.tentacleAngle;
      this.tentacleMovement += this.tentacleSpeed;
      if ((double)this.tentacleMovement > Math.PI * 2) {
         if (this.level.isClientSide) {
            this.tentacleMovement = (float) (Math.PI * 2);
         } else {
            this.tentacleMovement = (float)((double)this.tentacleMovement - (Math.PI * 2));
            if (this.random.nextInt(10) == 0) {
               this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
            }

            this.level.broadcastEntityEvent(this, (byte)19);
         }
      }

      if (this.isInWaterOrBubble()) {
         if (this.tentacleMovement < (float) Math.PI) {
            float â˜ƒ = this.tentacleMovement / (float) Math.PI;
            this.tentacleAngle = Mth.sin(â˜ƒ * â˜ƒ * (float) Math.PI) * (float) Math.PI * 0.25F;
            if ((double)â˜ƒ > 0.75) {
               this.speed = 1.0F;
               this.rotateSpeed = 1.0F;
            } else {
               this.rotateSpeed *= 0.8F;
            }
         } else {
            this.tentacleAngle = 0.0F;
            this.speed *= 0.9F;
            this.rotateSpeed *= 0.99F;
         }

         if (!this.level.isClientSide) {
            this.setDeltaMovement((double)(this.tx * this.speed), (double)(this.ty * this.speed), (double)(this.tz * this.speed));
         }

         Vec3 â˜ƒ = this.getDeltaMovement();
         double â˜ƒx = â˜ƒ.horizontalDistance();
         this.yBodyRot += (-((float)Mth.atan2(â˜ƒ.x, â˜ƒ.z)) * (180.0F / (float)Math.PI) - this.yBodyRot) * 0.1F;
         this.setYRot(this.yBodyRot);
         this.zBodyRot = (float)((double)this.zBodyRot + Math.PI * (double)this.rotateSpeed * 1.5);
         this.xBodyRot += (-((float)Mth.atan2(â˜ƒx, â˜ƒ.y)) * (180.0F / (float)Math.PI) - this.xBodyRot) * 0.1F;
      } else {
         this.tentacleAngle = Mth.abs(Mth.sin(this.tentacleMovement)) * (float) Math.PI * 0.25F;
         if (!this.level.isClientSide) {
            double â˜ƒ = this.getDeltaMovement().y;
            if (this.hasEffect(MobEffects.LEVITATION)) {
               â˜ƒ = 0.05 * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1);
            } else if (!this.isNoGravity()) {
               â˜ƒ -= 0.08;
            }

            this.setDeltaMovement(0.0, â˜ƒ * 0.98F, 0.0);
         }

         this.xBodyRot = (float)((double)this.xBodyRot + (double)(-90.0F - this.xBodyRot) * 0.02);
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (super.hurt(â˜ƒ, â˜ƒ) && this.getLastHurtByMob() != null) {
         this.spawnInk();
         return true;
      } else {
         return false;
      }
   }

   private Vec3 rotateVector(Vec3 var1) {
      Vec3 â˜ƒ = â˜ƒ.xRot(this.xBodyRotO * (float) (Math.PI / 180.0));
      return â˜ƒ.yRot(-this.yBodyRotO * (float) (Math.PI / 180.0));
   }

   private void spawnInk() {
      this.playSound(this.getSquirtSound(), this.getSoundVolume(), this.getVoicePitch());
      Vec3 â˜ƒ = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());

      for(int â˜ƒx = 0; â˜ƒx < 30; ++â˜ƒx) {
         Vec3 â˜ƒxx = this.rotateVector(new Vec3((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
         Vec3 â˜ƒxxx = â˜ƒxx.scale(0.3 + (double)(this.random.nextFloat() * 2.0F));
         ((ServerLevel)this.level).sendParticles(this.getInkParticle(), â˜ƒ.x, â˜ƒ.y + 0.5, â˜ƒ.z, 0, â˜ƒxxx.x, â˜ƒxxx.y, â˜ƒxxx.z, 0.1F);
      }
   }

   protected ParticleOptions getInkParticle() {
      return ParticleTypes.SQUID_INK;
   }

   @Override
   public void travel(Vec3 var1) {
      this.move(MoverType.SELF, this.getDeltaMovement());
   }

   public static boolean checkSquidSpawnRules(EntityType<Squid> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getY() > 45 && â˜ƒ.getY() < â˜ƒ.getSeaLevel();
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 19) {
         this.tentacleMovement = 0.0F;
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   public void setMovementVector(float var1, float var2, float var3) {
      this.tx = â˜ƒ;
      this.ty = â˜ƒ;
      this.tz = â˜ƒ;
   }

   public boolean hasMovementVector() {
      return this.tx != 0.0F || this.ty != 0.0F || this.tz != 0.0F;
   }

   class SquidFleeGoal extends Goal {
      private static final float SQUID_FLEE_SPEED = 3.0F;
      private static final float SQUID_FLEE_MIN_DISTANCE = 5.0F;
      private static final float SQUID_FLEE_MAX_DISTANCE = 10.0F;
      private int fleeTicks;

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = Squid.this.getLastHurtByMob();
         if (Squid.this.isInWater() && â˜ƒ != null) {
            return Squid.this.distanceToSqr(â˜ƒ) < 100.0;
         } else {
            return false;
         }
      }

      @Override
      public void start() {
         this.fleeTicks = 0;
      }

      @Override
      public void tick() {
         ++this.fleeTicks;
         LivingEntity â˜ƒ = Squid.this.getLastHurtByMob();
         if (â˜ƒ != null) {
            Vec3 â˜ƒx = new Vec3(Squid.this.getX() - â˜ƒ.getX(), Squid.this.getY() - â˜ƒ.getY(), Squid.this.getZ() - â˜ƒ.getZ());
            BlockState â˜ƒxx = Squid.this.level.getBlockState(new BlockPos(Squid.this.getX() + â˜ƒx.x, Squid.this.getY() + â˜ƒx.y, Squid.this.getZ() + â˜ƒx.z));
            FluidState â˜ƒxxx = Squid.this.level
               .getFluidState(new BlockPos(Squid.this.getX() + â˜ƒx.x, Squid.this.getY() + â˜ƒx.y, Squid.this.getZ() + â˜ƒx.z));
            if (â˜ƒxxx.is(FluidTags.WATER) || â˜ƒxx.isAir()) {
               double â˜ƒxxxx = â˜ƒx.length();
               if (â˜ƒxxxx > 0.0) {
                  â˜ƒx.normalize();
                  float â˜ƒxxxxx = 3.0F;
                  if (â˜ƒxxxx > 5.0) {
                     â˜ƒxxxxx = (float)((double)â˜ƒxxxxx - (â˜ƒxxxx - 5.0) / 5.0);
                  }

                  if (â˜ƒxxxxx > 0.0F) {
                     â˜ƒx = â˜ƒx.scale((double)â˜ƒxxxxx);
                  }
               }

               if (â˜ƒxx.isAir()) {
                  â˜ƒx = â˜ƒx.subtract(0.0, â˜ƒx.y, 0.0);
               }

               Squid.this.setMovementVector((float)â˜ƒx.x / 20.0F, (float)â˜ƒx.y / 20.0F, (float)â˜ƒx.z / 20.0F);
            }

            if (this.fleeTicks % 10 == 5) {
               Squid.this.level.addParticle(ParticleTypes.BUBBLE, Squid.this.getX(), Squid.this.getY(), Squid.this.getZ(), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   class SquidRandomMovementGoal extends Goal {
      private final Squid squid;

      public SquidRandomMovementGoal(Squid var2) {
         this.squid = â˜ƒ;
      }

      @Override
      public boolean canUse() {
         return true;
      }

      @Override
      public void tick() {
         int â˜ƒ = this.squid.getNoActionTime();
         if (â˜ƒ > 100) {
            this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
         } else if (this.squid.getRandom().nextInt(50) == 0 || !this.squid.wasTouchingWater || !this.squid.hasMovementVector()) {
            float â˜ƒ = this.squid.getRandom().nextFloat() * (float) (Math.PI * 2);
            float â˜ƒx = Mth.cos(â˜ƒ) * 0.2F;
            float â˜ƒxx = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
            float â˜ƒxxx = Mth.sin(â˜ƒ) * 0.2F;
            this.squid.setMovementVector(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      }
   }
}
