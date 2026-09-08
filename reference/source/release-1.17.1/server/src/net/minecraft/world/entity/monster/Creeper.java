package net.minecraft.world.entity.monster;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class Creeper extends Monster implements PowerableMob {
   private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.BOOLEAN);
   private int oldSwell;
   private int swell;
   private int maxSwell = 30;
   private int explosionRadius = 3;
   private int droppedSkulls;

   public Creeper(EntityType<? extends Creeper> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new SwellGoal(this));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Ocelot.class, 6.0F, 1.0, 1.2));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Cat.class, 6.0F, 1.0, 1.2));
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   @Override
   public int getMaxFallDistance() {
      return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
   }

   @Override
   public boolean causeFallDamage(float var1, float var2, DamageSource var3) {
      boolean â˜ƒ = super.causeFallDamage(â˜ƒ, â˜ƒ, â˜ƒ);
      this.swell = (int)((float)this.swell + â˜ƒ * 1.5F);
      if (this.swell > this.maxSwell - 5) {
         this.swell = this.maxSwell - 5;
      }

      return â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_SWELL_DIR, -1);
      this.entityData.define(DATA_IS_POWERED, false);
      this.entityData.define(DATA_IS_IGNITED, false);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.entityData.get(DATA_IS_POWERED)) {
         â˜ƒ.putBoolean("powered", true);
      }

      â˜ƒ.putShort("Fuse", (short)this.maxSwell);
      â˜ƒ.putByte("ExplosionRadius", (byte)this.explosionRadius);
      â˜ƒ.putBoolean("ignited", this.isIgnited());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.entityData.set(DATA_IS_POWERED, â˜ƒ.getBoolean("powered"));
      if (â˜ƒ.contains("Fuse", 99)) {
         this.maxSwell = â˜ƒ.getShort("Fuse");
      }

      if (â˜ƒ.contains("ExplosionRadius", 99)) {
         this.explosionRadius = â˜ƒ.getByte("ExplosionRadius");
      }

      if (â˜ƒ.getBoolean("ignited")) {
         this.ignite();
      }
   }

   @Override
   public void tick() {
      if (this.isAlive()) {
         this.oldSwell = this.swell;
         if (this.isIgnited()) {
            this.setSwellDir(1);
         }

         int â˜ƒ = this.getSwellDir();
         if (â˜ƒ > 0 && this.swell == 0) {
            this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
            this.gameEvent(GameEvent.PRIME_FUSE);
         }

         this.swell += â˜ƒ;
         if (this.swell < 0) {
            this.swell = 0;
         }

         if (this.swell >= this.maxSwell) {
            this.swell = this.maxSwell;
            this.explodeCreeper();
         }
      }

      super.tick();
   }

   @Override
   public void setTarget(@Nullable LivingEntity var1) {
      if (!(â˜ƒ instanceof Goat)) {
         super.setTarget(â˜ƒ);
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.CREEPER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.CREEPER_DEATH;
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);
      Entity â˜ƒx = â˜ƒ.getEntity();
      if (â˜ƒx != this && â˜ƒx instanceof Creeper â˜ƒ && â˜ƒ.canDropMobsSkull()) {
         â˜ƒ.increaseDroppedSkulls();
         this.spawnAtLocation(Items.CREEPER_HEAD);
      }
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      return true;
   }

   @Override
   public boolean isPowered() {
      return this.entityData.get(DATA_IS_POWERED);
   }

   public float getSwelling(float var1) {
      return Mth.lerp(â˜ƒ, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
   }

   public int getSwellDir() {
      return this.entityData.get(DATA_SWELL_DIR);
   }

   public void setSwellDir(int var1) {
      this.entityData.set(DATA_SWELL_DIR, â˜ƒ);
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
      super.thunderHit(â˜ƒ, â˜ƒ);
      this.entityData.set(DATA_IS_POWERED, true);
   }

   @Override
   protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.FLINT_AND_STEEL)) {
         this.level
            .playSound(
               â˜ƒ, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F
            );
         if (!this.level.isClientSide) {
            this.ignite();
            â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
         }

         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      }
   }

   private void explodeCreeper() {
      if (!this.level.isClientSide) {
         Explosion.BlockInteraction â˜ƒ = this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
            ? Explosion.BlockInteraction.DESTROY
            : Explosion.BlockInteraction.NONE;
         float â˜ƒx = this.isPowered() ? 2.0F : 1.0F;
         this.dead = true;
         this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * â˜ƒx, â˜ƒ);
         this.discard();
         this.spawnLingeringCloud();
      }
   }

   private void spawnLingeringCloud() {
      Collection<MobEffectInstance> â˜ƒ = this.getActiveEffects();
      if (!â˜ƒ.isEmpty()) {
         AreaEffectCloud â˜ƒx = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
         â˜ƒx.setRadius(2.5F);
         â˜ƒx.setRadiusOnUse(-0.5F);
         â˜ƒx.setWaitTime(10);
         â˜ƒx.setDuration(â˜ƒx.getDuration() / 2);
         â˜ƒx.setRadiusPerTick(-â˜ƒx.getRadius() / (float)â˜ƒx.getDuration());

         for(MobEffectInstance â˜ƒxx : â˜ƒ) {
            â˜ƒx.addEffect(new MobEffectInstance(â˜ƒxx));
         }

         this.level.addFreshEntity(â˜ƒx);
      }
   }

   public boolean isIgnited() {
      return this.entityData.get(DATA_IS_IGNITED);
   }

   public void ignite() {
      this.entityData.set(DATA_IS_IGNITED, true);
   }

   public boolean canDropMobsSkull() {
      return this.isPowered() && this.droppedSkulls < 1;
   }

   public void increaseDroppedSkulls() {
      ++this.droppedSkulls;
   }
}
