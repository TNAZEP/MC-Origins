package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownTrident extends AbstractArrow {
   private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ThrownTrident.class, EntityDataSerializers.BYTE);
   private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownTrident.class, EntityDataSerializers.BOOLEAN);
   private ItemStack tridentItem = new ItemStack(Items.TRIDENT);
   private boolean dealtDamage;
   public int clientSideReturnTridentTickCount;

   public ThrownTrident(EntityType<? extends ThrownTrident> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ThrownTrident(Level var1, LivingEntity var2, ItemStack var3) {
      super(EntityType.TRIDENT, â˜ƒ, â˜ƒ);
      this.tridentItem = â˜ƒ.copy();
      this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(â˜ƒ));
      this.entityData.set(ID_FOIL, â˜ƒ.hasFoil());
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(ID_LOYALTY, (byte)0);
      this.entityData.define(ID_FOIL, false);
   }

   @Override
   public void tick() {
      if (this.inGroundTime > 4) {
         this.dealtDamage = true;
      }

      Entity â˜ƒ = this.getOwner();
      int â˜ƒx = this.entityData.get(ID_LOYALTY);
      if (â˜ƒx > 0 && (this.dealtDamage || this.isNoPhysics()) && â˜ƒ != null) {
         if (!this.isAcceptibleReturnOwner()) {
            if (!this.level.isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
               this.spawnAtLocation(this.getPickupItem(), 0.1F);
            }

            this.discard();
         } else {
            this.setNoPhysics(true);
            Vec3 â˜ƒxx = â˜ƒ.getEyePosition().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + â˜ƒxx.y * 0.015 * (double)â˜ƒx, this.getZ());
            if (this.level.isClientSide) {
               this.yOld = this.getY();
            }

            double â˜ƒxx = 0.05 * (double)â˜ƒx;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(â˜ƒxx.normalize().scale(â˜ƒxx)));
            if (this.clientSideReturnTridentTickCount == 0) {
               this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
            }

            ++this.clientSideReturnTridentTickCount;
         }
      }

      super.tick();
   }

   private boolean isAcceptibleReturnOwner() {
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ == null || !â˜ƒ.isAlive()) {
         return false;
      } else {
         return !(â˜ƒ instanceof ServerPlayer) || !â˜ƒ.isSpectator();
      }
   }

   @Override
   protected ItemStack getPickupItem() {
      return this.tridentItem.copy();
   }

   public boolean isFoil() {
      return this.entityData.get(ID_FOIL);
   }

   @Nullable
   @Override
   protected EntityHitResult findHitEntity(Vec3 var1, Vec3 var2) {
      return this.dealtDamage ? null : super.findHitEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      Entity â˜ƒx = â˜ƒ.getEntity();
      float â˜ƒxx = 8.0F;
      if (â˜ƒx instanceof LivingEntity â˜ƒ) {
         â˜ƒxx += EnchantmentHelper.getDamageBonus(this.tridentItem, â˜ƒ.getMobType());
      }

      Entity â˜ƒ = this.getOwner();
      DamageSource â˜ƒx = DamageSource.trident(this, (Entity)(â˜ƒ == null ? this : â˜ƒ));
      this.dealtDamage = true;
      SoundEvent â˜ƒxx = SoundEvents.TRIDENT_HIT;
      if (â˜ƒx.hurt(â˜ƒx, â˜ƒxx)) {
         if (â˜ƒx.getType() == EntityType.ENDERMAN) {
            return;
         }

         if (â˜ƒx instanceof LivingEntity â˜ƒxxx) {
            if (â˜ƒ instanceof LivingEntity) {
               EnchantmentHelper.doPostHurtEffects(â˜ƒxxx, â˜ƒ);
               EnchantmentHelper.doPostDamageEffects((LivingEntity)â˜ƒ, â˜ƒxxx);
            }

            this.doPostHurtEffects(â˜ƒxxx);
         }
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
      float â˜ƒ = 1.0F;
      if (this.level instanceof ServerLevel && this.level.isThundering() && this.isChanneling()) {
         BlockPos â˜ƒx = â˜ƒx.blockPosition();
         if (this.level.canSeeSky(â˜ƒx)) {
            LightningBolt â˜ƒxx = EntityType.LIGHTNING_BOLT.create(this.level);
            â˜ƒxx.moveTo(Vec3.atBottomCenterOf(â˜ƒx));
            â˜ƒxx.setCause(â˜ƒ instanceof ServerPlayer ? (ServerPlayer)â˜ƒ : null);
            this.level.addFreshEntity(â˜ƒxx);
            â˜ƒxx = SoundEvents.TRIDENT_THUNDER;
            â˜ƒ = 5.0F;
         }
      }

      this.playSound(â˜ƒxx, â˜ƒ, 1.0F);
   }

   public boolean isChanneling() {
      return EnchantmentHelper.hasChanneling(this.tridentItem);
   }

   @Override
   protected boolean tryPickup(Player var1) {
      return super.tryPickup(â˜ƒ) || this.isNoPhysics() && this.ownedBy(â˜ƒ) && â˜ƒ.getInventory().add(this.getPickupItem());
   }

   @Override
   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return SoundEvents.TRIDENT_HIT_GROUND;
   }

   @Override
   public void playerTouch(Player var1) {
      if (this.ownedBy(â˜ƒ) || this.getOwner() == null) {
         super.playerTouch(â˜ƒ);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("Trident", 10)) {
         this.tridentItem = ItemStack.of(â˜ƒ.getCompound("Trident"));
      }

      this.dealtDamage = â˜ƒ.getBoolean("DealtDamage");
      this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.tridentItem));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.put("Trident", this.tridentItem.save(new CompoundTag()));
      â˜ƒ.putBoolean("DealtDamage", this.dealtDamage);
   }

   @Override
   public void tickDespawn() {
      int â˜ƒ = this.entityData.get(ID_LOYALTY);
      if (this.pickup != AbstractArrow.Pickup.ALLOWED || â˜ƒ <= 0) {
         super.tickDespawn();
      }
   }

   @Override
   protected float getWaterInertia() {
      return 0.99F;
   }

   @Override
   public boolean shouldRender(double var1, double var3, double var5) {
      return true;
   }
}
