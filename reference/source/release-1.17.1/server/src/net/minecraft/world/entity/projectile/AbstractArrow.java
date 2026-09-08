package net.minecraft.world.entity.projectile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractArrow extends Projectile {
   private static final double ARROW_BASE_DAMAGE = 2.0;
   private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);
   private static final int FLAG_CRIT = 1;
   private static final int FLAG_NOPHYSICS = 2;
   private static final int FLAG_CROSSBOW = 4;
   @Nullable
   private BlockState lastState;
   protected boolean inGround;
   protected int inGroundTime;
   public AbstractArrow.Pickup pickup = AbstractArrow.Pickup.DISALLOWED;
   public int shakeTime;
   private int life;
   private double baseDamage = 2.0;
   private int knockback;
   private SoundEvent soundEvent = this.getDefaultHitGroundSoundEvent();
   @Nullable
   private IntOpenHashSet piercingIgnoreEntityIds;
   @Nullable
   private List<Entity> piercedAndKilledEntities;

   protected AbstractArrow(EntityType<? extends AbstractArrow> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected AbstractArrow(EntityType<? extends AbstractArrow> var1, double var2, double var4, double var6, Level var8) {
      this(â˜ƒ, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected AbstractArrow(EntityType<? extends AbstractArrow> var1, LivingEntity var2, Level var3) {
      this(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getEyeY() - 0.1F, â˜ƒ.getZ(), â˜ƒ);
      this.setOwner(â˜ƒ);
      if (â˜ƒ instanceof Player) {
         this.pickup = AbstractArrow.Pickup.ALLOWED;
      }
   }

   public void setSoundEvent(SoundEvent var1) {
      this.soundEvent = â˜ƒ;
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = this.getBoundingBox().getSize() * 10.0;
      if (Double.isNaN(â˜ƒ)) {
         â˜ƒ = 1.0;
      }

      â˜ƒ *= 64.0 * getViewScale();
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
      this.entityData.define(ID_FLAGS, (byte)0);
      this.entityData.define(PIERCE_LEVEL, (byte)0);
   }

   @Override
   public void shoot(double var1, double var3, double var5, float var7, float var8) {
      super.shoot(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.life = 0;
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setRot(â˜ƒ, â˜ƒ);
   }

   @Override
   public void lerpMotion(double var1, double var3, double var5) {
      super.lerpMotion(â˜ƒ, â˜ƒ, â˜ƒ);
      this.life = 0;
   }

   @Override
   public void tick() {
      super.tick();
      boolean â˜ƒ = this.isNoPhysics();
      Vec3 â˜ƒx = this.getDeltaMovement();
      if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
         double â˜ƒxx = â˜ƒx.horizontalDistance();
         this.setYRot((float)(Mth.atan2(â˜ƒx.x, â˜ƒx.z) * 180.0F / (float)Math.PI));
         this.setXRot((float)(Mth.atan2(â˜ƒx.y, â˜ƒxx) * 180.0F / (float)Math.PI));
         this.yRotO = this.getYRot();
         this.xRotO = this.getXRot();
      }

      BlockPos â˜ƒ = this.blockPosition();
      BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
      if (!â˜ƒx.isAir() && !â˜ƒ) {
         VoxelShape â˜ƒxx = â˜ƒx.getCollisionShape(this.level, â˜ƒ);
         if (!â˜ƒxx.isEmpty()) {
            Vec3 â˜ƒxxx = this.position();

            for(AABB â˜ƒxxxx : â˜ƒxx.toAabbs()) {
               if (â˜ƒxxxx.move(â˜ƒ).contains(â˜ƒxxx)) {
                  this.inGround = true;
                  break;
               }
            }
         }
      }

      if (this.shakeTime > 0) {
         --this.shakeTime;
      }

      if (this.isInWaterOrRain() || â˜ƒx.is(Blocks.POWDER_SNOW)) {
         this.clearFire();
      }

      if (this.inGround && !â˜ƒ) {
         if (this.lastState != â˜ƒx && this.shouldFall()) {
            this.startFalling();
         } else if (!this.level.isClientSide) {
            this.tickDespawn();
         }

         ++this.inGroundTime;
      } else {
         this.inGroundTime = 0;
         Vec3 â˜ƒ = this.position();
         Vec3 â˜ƒx = â˜ƒ.add(â˜ƒx);
         HitResult â˜ƒxx = this.level.clip(new ClipContext(â˜ƒ, â˜ƒx, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
         if (â˜ƒxx.getType() != HitResult.Type.MISS) {
            â˜ƒx = â˜ƒxx.getLocation();
         }

         while(!this.isRemoved()) {
            EntityHitResult â˜ƒ = this.findHitEntity(â˜ƒ, â˜ƒx);
            if (â˜ƒ != null) {
               â˜ƒxx = â˜ƒ;
            }

            if (â˜ƒxx != null && â˜ƒxx.getType() == HitResult.Type.ENTITY) {
               Entity â˜ƒ = ((EntityHitResult)â˜ƒxx).getEntity();
               Entity â˜ƒx = this.getOwner();
               if (â˜ƒ instanceof Player && â˜ƒx instanceof Player && !((Player)â˜ƒx).canHarmPlayer((Player)â˜ƒ)) {
                  â˜ƒxx = null;
                  â˜ƒ = null;
               }
            }

            if (â˜ƒxx != null && !â˜ƒ) {
               this.onHit(â˜ƒxx);
               this.hasImpulse = true;
            }

            if (â˜ƒ == null || this.getPierceLevel() <= 0) {
               break;
            }

            â˜ƒxx = null;
         }

         â˜ƒx = this.getDeltaMovement();
         double â˜ƒ = â˜ƒx.x;
         double â˜ƒx = â˜ƒx.y;
         double â˜ƒxx = â˜ƒx.z;
         if (this.isCritArrow()) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
               this.level
                  .addParticle(
                     ParticleTypes.CRIT,
                     this.getX() + â˜ƒ * (double)â˜ƒxxx / 4.0,
                     this.getY() + â˜ƒx * (double)â˜ƒxxx / 4.0,
                     this.getZ() + â˜ƒxx * (double)â˜ƒxxx / 4.0,
                     -â˜ƒ,
                     -â˜ƒx + 0.2,
                     -â˜ƒxx
                  );
            }
         }

         double â˜ƒ = this.getX() + â˜ƒ;
         double â˜ƒx = this.getY() + â˜ƒx;
         double â˜ƒxx = this.getZ() + â˜ƒxx;
         double â˜ƒxxx = â˜ƒx.horizontalDistance();
         if (â˜ƒ) {
            this.setYRot((float)(Mth.atan2(-â˜ƒ, -â˜ƒxx) * 180.0F / (float)Math.PI));
         } else {
            this.setYRot((float)(Mth.atan2(â˜ƒ, â˜ƒxx) * 180.0F / (float)Math.PI));
         }

         this.setXRot((float)(Mth.atan2(â˜ƒx, â˜ƒxxx) * 180.0F / (float)Math.PI));
         this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
         this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
         float â˜ƒ = 0.99F;
         float â˜ƒx = 0.05F;
         if (this.isInWater()) {
            for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
               float â˜ƒxxx = 0.25F;
               this.level.addParticle(ParticleTypes.BUBBLE, â˜ƒ - â˜ƒ * 0.25, â˜ƒx - â˜ƒx * 0.25, â˜ƒxx - â˜ƒxx * 0.25, â˜ƒ, â˜ƒx, â˜ƒxx);
            }

            â˜ƒ = this.getWaterInertia();
         }

         this.setDeltaMovement(â˜ƒx.scale((double)â˜ƒ));
         if (!this.isNoGravity() && !â˜ƒ) {
            Vec3 â˜ƒ = this.getDeltaMovement();
            this.setDeltaMovement(â˜ƒ.x, â˜ƒ.y - 0.05F, â˜ƒ.z);
         }

         this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
         this.checkInsideBlocks();
      }
   }

   private boolean shouldFall() {
      return this.inGround && this.level.noCollision(new AABB(this.position(), this.position()).inflate(0.06));
   }

   private void startFalling() {
      this.inGround = false;
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setDeltaMovement(
         â˜ƒ.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F))
      );
      this.life = 0;
   }

   @Override
   public void move(MoverType var1, Vec3 var2) {
      super.move(â˜ƒ, â˜ƒ);
      if (â˜ƒ != MoverType.SELF && this.shouldFall()) {
         this.startFalling();
      }
   }

   protected void tickDespawn() {
      ++this.life;
      if (this.life >= 1200) {
         this.discard();
      }
   }

   private void resetPiercedEntities() {
      if (this.piercedAndKilledEntities != null) {
         this.piercedAndKilledEntities.clear();
      }

      if (this.piercingIgnoreEntityIds != null) {
         this.piercingIgnoreEntityIds.clear();
      }
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      Entity â˜ƒ = â˜ƒ.getEntity();
      float â˜ƒx = (float)this.getDeltaMovement().length();
      int â˜ƒxx = Mth.ceil(Mth.clamp((double)â˜ƒx * this.baseDamage, 0.0, 2.147483647E9));
      if (this.getPierceLevel() > 0) {
         if (this.piercingIgnoreEntityIds == null) {
            this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
         }

         if (this.piercedAndKilledEntities == null) {
            this.piercedAndKilledEntities = Lists.<Entity>newArrayListWithCapacity(5);
         }

         if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
            this.discard();
            return;
         }

         this.piercingIgnoreEntityIds.add(â˜ƒ.getId());
      }

      if (this.isCritArrow()) {
         long â˜ƒ = (long)this.random.nextInt(â˜ƒxx / 2 + 2);
         â˜ƒxx = (int)Math.min(â˜ƒ + (long)â˜ƒxx, 2147483647L);
      }

      Entity â˜ƒx = this.getOwner();
      DamageSource â˜ƒ;
      if (â˜ƒx == null) {
         â˜ƒ = DamageSource.arrow(this, this);
      } else {
         â˜ƒ = DamageSource.arrow(this, â˜ƒx);
         if (â˜ƒx instanceof LivingEntity) {
            ((LivingEntity)â˜ƒx).setLastHurtMob(â˜ƒ);
         }
      }

      boolean â˜ƒ = â˜ƒ.getType() == EntityType.ENDERMAN;
      int â˜ƒx = â˜ƒ.getRemainingFireTicks();
      if (this.isOnFire() && !â˜ƒ) {
         â˜ƒ.setSecondsOnFire(5);
      }

      if (â˜ƒ.hurt(â˜ƒ, (float)â˜ƒxx)) {
         if (â˜ƒ) {
            return;
         }

         if (â˜ƒ instanceof LivingEntity â˜ƒ) {
            if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
               â˜ƒ.setArrowCount(â˜ƒ.getArrowCount() + 1);
            }

            if (this.knockback > 0) {
               Vec3 â˜ƒx = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale((double)this.knockback * 0.6);
               if (â˜ƒx.lengthSqr() > 0.0) {
                  â˜ƒ.push(â˜ƒx.x, 0.1, â˜ƒx.z);
               }
            }

            if (!this.level.isClientSide && â˜ƒx instanceof LivingEntity) {
               EnchantmentHelper.doPostHurtEffects(â˜ƒ, â˜ƒx);
               EnchantmentHelper.doPostDamageEffects((LivingEntity)â˜ƒx, â˜ƒ);
            }

            this.doPostHurtEffects(â˜ƒ);
            if (â˜ƒx != null && â˜ƒ != â˜ƒx && â˜ƒ instanceof Player && â˜ƒx instanceof ServerPlayer && !this.isSilent()) {
               ((ServerPlayer)â˜ƒx).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }

            if (!â˜ƒ.isAlive() && this.piercedAndKilledEntities != null) {
               this.piercedAndKilledEntities.add(â˜ƒ);
            }

            if (!this.level.isClientSide && â˜ƒx instanceof ServerPlayer â˜ƒx) {
               if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
                  CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(â˜ƒx, this.piercedAndKilledEntities);
               } else if (!â˜ƒ.isAlive() && this.shotFromCrossbow()) {
                  CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(â˜ƒx, Arrays.asList(â˜ƒ));
               }
            }
         }

         this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
         if (this.getPierceLevel() <= 0) {
            this.discard();
         }
      } else {
         â˜ƒ.setRemainingFireTicks(â˜ƒx);
         this.setDeltaMovement(this.getDeltaMovement().scale(-0.1));
         this.setYRot(this.getYRot() + 180.0F);
         this.yRotO += 180.0F;
         if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
            if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
               this.spawnAtLocation(this.getPickupItem(), 0.1F);
            }

            this.discard();
         }
      }
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      this.lastState = this.level.getBlockState(â˜ƒ.getBlockPos());
      super.onHitBlock(â˜ƒ);
      Vec3 â˜ƒ = â˜ƒ.getLocation().subtract(this.getX(), this.getY(), this.getZ());
      this.setDeltaMovement(â˜ƒ);
      Vec3 â˜ƒx = â˜ƒ.normalize().scale(0.05F);
      this.setPosRaw(this.getX() - â˜ƒx.x, this.getY() - â˜ƒx.y, this.getZ() - â˜ƒx.z);
      this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
      this.inGround = true;
      this.shakeTime = 7;
      this.setCritArrow(false);
      this.setPierceLevel((byte)0);
      this.setSoundEvent(SoundEvents.ARROW_HIT);
      this.setShotFromCrossbow(false);
      this.resetPiercedEntities();
   }

   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return SoundEvents.ARROW_HIT;
   }

   protected final SoundEvent getHitGroundSoundEvent() {
      return this.soundEvent;
   }

   protected void doPostHurtEffects(LivingEntity var1) {
   }

   @Nullable
   protected EntityHitResult findHitEntity(Vec3 var1, Vec3 var2) {
      return ProjectileUtil.getEntityHitResult(
         this.level, this, â˜ƒ, â˜ƒ, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), this::canHitEntity
      );
   }

   @Override
   protected boolean canHitEntity(Entity var1) {
      return super.canHitEntity(â˜ƒ) && (this.piercingIgnoreEntityIds == null || !this.piercingIgnoreEntityIds.contains(â˜ƒ.getId()));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putShort("life", (short)this.life);
      if (this.lastState != null) {
         â˜ƒ.put("inBlockState", NbtUtils.writeBlockState(this.lastState));
      }

      â˜ƒ.putByte("shake", (byte)this.shakeTime);
      â˜ƒ.putBoolean("inGround", this.inGround);
      â˜ƒ.putByte("pickup", (byte)this.pickup.ordinal());
      â˜ƒ.putDouble("damage", this.baseDamage);
      â˜ƒ.putBoolean("crit", this.isCritArrow());
      â˜ƒ.putByte("PierceLevel", this.getPierceLevel());
      â˜ƒ.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.soundEvent).toString());
      â˜ƒ.putBoolean("ShotFromCrossbow", this.shotFromCrossbow());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.life = â˜ƒ.getShort("life");
      if (â˜ƒ.contains("inBlockState", 10)) {
         this.lastState = NbtUtils.readBlockState(â˜ƒ.getCompound("inBlockState"));
      }

      this.shakeTime = â˜ƒ.getByte("shake") & 255;
      this.inGround = â˜ƒ.getBoolean("inGround");
      if (â˜ƒ.contains("damage", 99)) {
         this.baseDamage = â˜ƒ.getDouble("damage");
      }

      this.pickup = AbstractArrow.Pickup.byOrdinal(â˜ƒ.getByte("pickup"));
      this.setCritArrow(â˜ƒ.getBoolean("crit"));
      this.setPierceLevel(â˜ƒ.getByte("PierceLevel"));
      if (â˜ƒ.contains("SoundEvent", 8)) {
         this.soundEvent = (SoundEvent)Registry.SOUND_EVENT
            .getOptional(new ResourceLocation(â˜ƒ.getString("SoundEvent")))
            .orElse(this.getDefaultHitGroundSoundEvent());
      }

      this.setShotFromCrossbow(â˜ƒ.getBoolean("ShotFromCrossbow"));
   }

   @Override
   public void setOwner(@Nullable Entity var1) {
      super.setOwner(â˜ƒ);
      if (â˜ƒ instanceof Player) {
         this.pickup = ((Player)â˜ƒ).getAbilities().instabuild ? AbstractArrow.Pickup.CREATIVE_ONLY : AbstractArrow.Pickup.ALLOWED;
      }
   }

   @Override
   public void playerTouch(Player var1) {
      if (!this.level.isClientSide && (this.inGround || this.isNoPhysics()) && this.shakeTime <= 0) {
         if (this.tryPickup(â˜ƒ)) {
            â˜ƒ.take(this, 1);
            this.discard();
         }
      }
   }

   protected boolean tryPickup(Player var1) {
      switch(this.pickup) {
         case ALLOWED:
            return â˜ƒ.getInventory().add(this.getPickupItem());
         case CREATIVE_ONLY:
            return â˜ƒ.getAbilities().instabuild;
         default:
            return false;
      }
   }

   protected abstract ItemStack getPickupItem();

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   public void setBaseDamage(double var1) {
      this.baseDamage = â˜ƒ;
   }

   public double getBaseDamage() {
      return this.baseDamage;
   }

   public void setKnockback(int var1) {
      this.knockback = â˜ƒ;
   }

   public int getKnockback() {
      return this.knockback;
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   @Override
   protected float getEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.13F;
   }

   public void setCritArrow(boolean var1) {
      this.setFlag(1, â˜ƒ);
   }

   public void setPierceLevel(byte var1) {
      this.entityData.set(PIERCE_LEVEL, â˜ƒ);
   }

   private void setFlag(int var1, boolean var2) {
      byte â˜ƒ = this.entityData.get(ID_FLAGS);
      if (â˜ƒ) {
         this.entityData.set(ID_FLAGS, (byte)(â˜ƒ | â˜ƒ));
      } else {
         this.entityData.set(ID_FLAGS, (byte)(â˜ƒ & ~â˜ƒ));
      }
   }

   public boolean isCritArrow() {
      byte â˜ƒ = this.entityData.get(ID_FLAGS);
      return (â˜ƒ & 1) != 0;
   }

   public boolean shotFromCrossbow() {
      byte â˜ƒ = this.entityData.get(ID_FLAGS);
      return (â˜ƒ & 4) != 0;
   }

   public byte getPierceLevel() {
      return this.entityData.get(PIERCE_LEVEL);
   }

   public void setEnchantmentEffectsFromEntity(LivingEntity var1, float var2) {
      int â˜ƒ = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, â˜ƒ);
      int â˜ƒx = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, â˜ƒ);
      this.setBaseDamage((double)(â˜ƒ * 2.0F) + this.random.nextGaussian() * 0.25 + (double)((float)this.level.getDifficulty().getId() * 0.11F));
      if (â˜ƒ > 0) {
         this.setBaseDamage(this.getBaseDamage() + (double)â˜ƒ * 0.5 + 0.5);
      }

      if (â˜ƒx > 0) {
         this.setKnockback(â˜ƒx);
      }

      if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, â˜ƒ) > 0) {
         this.setSecondsOnFire(100);
      }
   }

   protected float getWaterInertia() {
      return 0.6F;
   }

   public void setNoPhysics(boolean var1) {
      this.noPhysics = â˜ƒ;
      this.setFlag(2, â˜ƒ);
   }

   public boolean isNoPhysics() {
      if (!this.level.isClientSide) {
         return this.noPhysics;
      } else {
         return (this.entityData.get(ID_FLAGS) & 2) != 0;
      }
   }

   public void setShotFromCrossbow(boolean var1) {
      this.setFlag(4, â˜ƒ);
   }

   public static enum Pickup {
      DISALLOWED,
      ALLOWED,
      CREATIVE_ONLY;

      public static AbstractArrow.Pickup byOrdinal(int var0) {
         if (â˜ƒ < 0 || â˜ƒ > values().length) {
            â˜ƒ = 0;
         }

         return values()[â˜ƒ];
      }
   }
}
