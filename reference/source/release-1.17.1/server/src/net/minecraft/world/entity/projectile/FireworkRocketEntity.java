package net.minecraft.world.entity.projectile;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FireworkRocketEntity extends Projectile implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> DATA_ID_FIREWORKS_ITEM = SynchedEntityData.defineId(
      FireworkRocketEntity.class, EntityDataSerializers.ITEM_STACK
   );
   private static final EntityDataAccessor<OptionalInt> DATA_ATTACHED_TO_TARGET = SynchedEntityData.defineId(
      FireworkRocketEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT
   );
   private static final EntityDataAccessor<Boolean> DATA_SHOT_AT_ANGLE = SynchedEntityData.defineId(FireworkRocketEntity.class, EntityDataSerializers.BOOLEAN);
   private int life;
   private int lifetime;
   @Nullable
   private LivingEntity attachedToEntity;

   public FireworkRocketEntity(EntityType<? extends FireworkRocketEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public FireworkRocketEntity(Level var1, double var2, double var4, double var6, ItemStack var8) {
      super(EntityType.FIREWORK_ROCKET, â˜ƒ);
      this.life = 0;
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒ = 1;
      if (!â˜ƒ.isEmpty() && â˜ƒ.hasTag()) {
         this.entityData.set(DATA_ID_FIREWORKS_ITEM, â˜ƒ.copy());
         â˜ƒ += â˜ƒ.getOrCreateTagElement("Fireworks").getByte("Flight");
      }

      this.setDeltaMovement(this.random.nextGaussian() * 0.001, 0.05, this.random.nextGaussian() * 0.001);
      this.lifetime = 10 * â˜ƒ + this.random.nextInt(6) + this.random.nextInt(7);
   }

   public FireworkRocketEntity(Level var1, @Nullable Entity var2, double var3, double var5, double var7, ItemStack var9) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setOwner(â˜ƒ);
   }

   public FireworkRocketEntity(Level var1, ItemStack var2, LivingEntity var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ);
      this.entityData.set(DATA_ATTACHED_TO_TARGET, OptionalInt.of(â˜ƒ.getId()));
      this.attachedToEntity = â˜ƒ;
   }

   public FireworkRocketEntity(Level var1, ItemStack var2, double var3, double var5, double var7, boolean var9) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.entityData.set(DATA_SHOT_AT_ANGLE, â˜ƒ);
   }

   public FireworkRocketEntity(Level var1, ItemStack var2, Entity var3, double var4, double var6, double var8, boolean var10) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setOwner(â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(DATA_ID_FIREWORKS_ITEM, ItemStack.EMPTY);
      this.entityData.define(DATA_ATTACHED_TO_TARGET, OptionalInt.empty());
      this.entityData.define(DATA_SHOT_AT_ANGLE, false);
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      return â˜ƒ < 4096.0 && !this.isAttachedToEntity();
   }

   @Override
   public boolean shouldRender(double var1, double var3, double var5) {
      return super.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ) && !this.isAttachedToEntity();
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isAttachedToEntity()) {
         if (this.attachedToEntity == null) {
            ((OptionalInt)this.entityData.get(DATA_ATTACHED_TO_TARGET)).ifPresent(var1x -> {
               Entity â˜ƒ = this.level.getEntity(var1x);
               if (â˜ƒ instanceof LivingEntity) {
                  this.attachedToEntity = (LivingEntity)â˜ƒ;
               }
            });
         }

         if (this.attachedToEntity != null) {
            if (this.attachedToEntity.isFallFlying()) {
               Vec3 â˜ƒ = this.attachedToEntity.getLookAngle();
               double â˜ƒx = 1.5;
               double â˜ƒxx = 0.1;
               Vec3 â˜ƒxxx = this.attachedToEntity.getDeltaMovement();
               this.attachedToEntity
                  .setDeltaMovement(
                     â˜ƒxxx.add(
                        â˜ƒ.x * 0.1 + (â˜ƒ.x * 1.5 - â˜ƒxxx.x) * 0.5,
                        â˜ƒ.y * 0.1 + (â˜ƒ.y * 1.5 - â˜ƒxxx.y) * 0.5,
                        â˜ƒ.z * 0.1 + (â˜ƒ.z * 1.5 - â˜ƒxxx.z) * 0.5
                     )
                  );
            }

            this.setPos(this.attachedToEntity.getX(), this.attachedToEntity.getY(), this.attachedToEntity.getZ());
            this.setDeltaMovement(this.attachedToEntity.getDeltaMovement());
         }
      } else {
         if (!this.isShotAtAngle()) {
            double â˜ƒ = this.horizontalCollision ? 1.0 : 1.15;
            this.setDeltaMovement(this.getDeltaMovement().multiply(â˜ƒ, 1.0, â˜ƒ).add(0.0, 0.04, 0.0));
         }

         Vec3 â˜ƒ = this.getDeltaMovement();
         this.move(MoverType.SELF, â˜ƒ);
         this.setDeltaMovement(â˜ƒ);
      }

      HitResult â˜ƒ = ProjectileUtil.getHitResult(this, this::canHitEntity);
      if (!this.noPhysics) {
         this.onHit(â˜ƒ);
         this.hasImpulse = true;
      }

      this.updateRotation();
      if (this.life == 0 && !this.isSilent()) {
         this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.AMBIENT, 3.0F, 1.0F);
      }

      ++this.life;
      if (this.level.isClientSide && this.life % 2 < 2) {
         this.level
            .addParticle(
               ParticleTypes.FIREWORK,
               this.getX(),
               this.getY() - 0.3,
               this.getZ(),
               this.random.nextGaussian() * 0.05,
               -this.getDeltaMovement().y * 0.5,
               this.random.nextGaussian() * 0.05
            );
      }

      if (!this.level.isClientSide && this.life > this.lifetime) {
         this.explode();
      }
   }

   private void explode() {
      this.level.broadcastEntityEvent(this, (byte)17);
      this.gameEvent(GameEvent.EXPLODE, this.getOwner());
      this.dealExplosionDamage();
      this.discard();
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      if (!this.level.isClientSide) {
         this.explode();
      }
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getBlockPos());
      this.level.getBlockState(â˜ƒ).entityInside(this.level, â˜ƒ, this);
      if (!this.level.isClientSide() && this.hasExplosion()) {
         this.explode();
      }

      super.onHitBlock(â˜ƒ);
   }

   private boolean hasExplosion() {
      ItemStack â˜ƒ = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
      CompoundTag â˜ƒx = â˜ƒ.isEmpty() ? null : â˜ƒ.getTagElement("Fireworks");
      ListTag â˜ƒxx = â˜ƒx != null ? â˜ƒx.getList("Explosions", 10) : null;
      return â˜ƒxx != null && !â˜ƒxx.isEmpty();
   }

   private void dealExplosionDamage() {
      float â˜ƒ = 0.0F;
      ItemStack â˜ƒx = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
      CompoundTag â˜ƒxx = â˜ƒx.isEmpty() ? null : â˜ƒx.getTagElement("Fireworks");
      ListTag â˜ƒxxx = â˜ƒxx != null ? â˜ƒxx.getList("Explosions", 10) : null;
      if (â˜ƒxxx != null && !â˜ƒxxx.isEmpty()) {
         â˜ƒ = 5.0F + (float)(â˜ƒxxx.size() * 2);
      }

      if (â˜ƒ > 0.0F) {
         if (this.attachedToEntity != null) {
            this.attachedToEntity.hurt(DamageSource.fireworks(this, this.getOwner()), 5.0F + (float)(â˜ƒxxx.size() * 2));
         }

         double â˜ƒ = 5.0;
         Vec3 â˜ƒx = this.position();

         for(LivingEntity â˜ƒxx : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0))) {
            if (â˜ƒxx != this.attachedToEntity && !(this.distanceToSqr(â˜ƒxx) > 25.0)) {
               boolean â˜ƒxxx = false;

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < 2; ++â˜ƒxxxx) {
                  Vec3 â˜ƒxxxxx = new Vec3(â˜ƒxx.getX(), â˜ƒxx.getY(0.5 * (double)â˜ƒxxxx), â˜ƒxx.getZ());
                  HitResult â˜ƒxxxxxx = this.level.clip(new ClipContext(â˜ƒx, â˜ƒxxxxx, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                  if (â˜ƒxxxxxx.getType() == HitResult.Type.MISS) {
                     â˜ƒxxx = true;
                     break;
                  }
               }

               if (â˜ƒxxx) {
                  float â˜ƒxxxx = â˜ƒ * (float)Math.sqrt((5.0 - (double)this.distanceTo(â˜ƒxx)) / 5.0);
                  â˜ƒxx.hurt(DamageSource.fireworks(this, this.getOwner()), â˜ƒxxxx);
               }
            }
         }
      }
   }

   private boolean isAttachedToEntity() {
      return ((OptionalInt)this.entityData.get(DATA_ATTACHED_TO_TARGET)).isPresent();
   }

   public boolean isShotAtAngle() {
      return this.entityData.get(DATA_SHOT_AT_ANGLE);
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 17 && this.level.isClientSide) {
         if (!this.hasExplosion()) {
            for(int â˜ƒ = 0; â˜ƒ < this.random.nextInt(3) + 2; ++â˜ƒ) {
               this.level
                  .addParticle(
                     ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05, 0.005, this.random.nextGaussian() * 0.05
                  );
            }
         } else {
            ItemStack â˜ƒ = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
            CompoundTag â˜ƒx = â˜ƒ.isEmpty() ? null : â˜ƒ.getTagElement("Fireworks");
            Vec3 â˜ƒxx = this.getDeltaMovement();
            this.level.createFireworks(this.getX(), this.getY(), this.getZ(), â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, â˜ƒx);
         }
      }

      super.handleEntityEvent(â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Life", this.life);
      â˜ƒ.putInt("LifeTime", this.lifetime);
      ItemStack â˜ƒ = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.put("FireworksItem", â˜ƒ.save(new CompoundTag()));
      }

      â˜ƒ.putBoolean("ShotAtAngle", this.entityData.get(DATA_SHOT_AT_ANGLE));
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.life = â˜ƒ.getInt("Life");
      this.lifetime = â˜ƒ.getInt("LifeTime");
      ItemStack â˜ƒ = ItemStack.of(â˜ƒ.getCompound("FireworksItem"));
      if (!â˜ƒ.isEmpty()) {
         this.entityData.set(DATA_ID_FIREWORKS_ITEM, â˜ƒ);
      }

      if (â˜ƒ.contains("ShotAtAngle")) {
         this.entityData.set(DATA_SHOT_AT_ANGLE, â˜ƒ.getBoolean("ShotAtAngle"));
      }
   }

   @Override
   public ItemStack getItem() {
      ItemStack â˜ƒ = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
      return â˜ƒ.isEmpty() ? new ItemStack(Items.FIREWORK_ROCKET) : â˜ƒ;
   }

   @Override
   public boolean isAttackable() {
      return false;
   }
}
