package net.minecraft.world.entity.item;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ItemEntity extends Entity {
   private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.ITEM_STACK);
   private static final int LIFETIME = 6000;
   private static final int INFINITE_PICKUP_DELAY = 32767;
   private static final int INFINITE_LIFETIME = -32768;
   private int age;
   private int pickupDelay;
   private int health = 5;
   private UUID thrower;
   private UUID owner;
   public final float bobOffs;

   public ItemEntity(EntityType<? extends ItemEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.bobOffs = this.random.nextFloat() * (float) Math.PI * 2.0F;
      this.setYRot(this.random.nextFloat() * 360.0F);
   }

   public ItemEntity(Level var1, double var2, double var4, double var6, ItemStack var8) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.random.nextDouble() * 0.2 - 0.1, 0.2, â˜ƒ.random.nextDouble() * 0.2 - 0.1);
   }

   public ItemEntity(Level var1, double var2, double var4, double var6, ItemStack var8, double var9, double var11, double var13) {
      this(EntityType.ITEM, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setDeltaMovement(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setItem(â˜ƒ);
   }

   private ItemEntity(ItemEntity var1) {
      super(â˜ƒ.getType(), â˜ƒ.level);
      this.setItem(â˜ƒ.getItem().copy());
      this.copyPosition(â˜ƒ);
      this.age = â˜ƒ.age;
      this.bobOffs = â˜ƒ.bobOffs;
   }

   @Override
   public boolean occludesVibrations() {
      return ItemTags.OCCLUDES_VIBRATION_SIGNALS.contains(this.getItem().getItem());
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_ITEM, ItemStack.EMPTY);
   }

   @Override
   public void tick() {
      if (this.getItem().isEmpty()) {
         this.discard();
      } else {
         super.tick();
         if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
            --this.pickupDelay;
         }

         this.xo = this.getX();
         this.yo = this.getY();
         this.zo = this.getZ();
         Vec3 â˜ƒ = this.getDeltaMovement();
         float â˜ƒx = this.getEyeHeight() - 0.11111111F;
         if (this.isInWater() && this.getFluidHeight(FluidTags.WATER) > (double)â˜ƒx) {
            this.setUnderwaterMovement();
         } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double)â˜ƒx) {
            this.setUnderLavaMovement();
         } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
         }

         if (this.level.isClientSide) {
            this.noPhysics = false;
         } else {
            this.noPhysics = !this.level.noCollision(this, this.getBoundingBox().deflate(1.0E-7), var0 -> true);
            if (this.noPhysics) {
               this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
            }
         }

         if (!this.onGround || this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5F || (this.tickCount + this.getId()) % 4 == 0) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            float â˜ƒ = 0.98F;
            if (this.onGround) {
               â˜ƒ = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getFriction() * 0.98F;
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply((double)â˜ƒ, 0.98, (double)â˜ƒ));
            if (this.onGround) {
               Vec3 â˜ƒ = this.getDeltaMovement();
               if (â˜ƒ.y < 0.0) {
                  this.setDeltaMovement(â˜ƒ.multiply(1.0, -0.5, 1.0));
               }
            }
         }

         boolean â˜ƒ = Mth.floor(this.xo) != Mth.floor(this.getX())
            || Mth.floor(this.yo) != Mth.floor(this.getY())
            || Mth.floor(this.zo) != Mth.floor(this.getZ());
         int â˜ƒx = â˜ƒ ? 2 : 40;
         if (this.tickCount % â˜ƒx == 0 && !this.level.isClientSide && this.isMergable()) {
            this.mergeWithNeighbours();
         }

         if (this.age != -32768) {
            ++this.age;
         }

         this.hasImpulse |= this.updateInWaterStateAndDoFluidPushing();
         if (!this.level.isClientSide) {
            double â˜ƒ = this.getDeltaMovement().subtract(â˜ƒ).lengthSqr();
            if (â˜ƒ > 0.01) {
               this.hasImpulse = true;
            }
         }

         if (!this.level.isClientSide && this.age >= 6000) {
            this.discard();
         }
      }
   }

   private void setUnderwaterMovement() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setDeltaMovement(â˜ƒ.x * 0.99F, â˜ƒ.y + (double)(â˜ƒ.y < 0.06F ? 5.0E-4F : 0.0F), â˜ƒ.z * 0.99F);
   }

   private void setUnderLavaMovement() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setDeltaMovement(â˜ƒ.x * 0.95F, â˜ƒ.y + (double)(â˜ƒ.y < 0.06F ? 5.0E-4F : 0.0F), â˜ƒ.z * 0.95F);
   }

   private void mergeWithNeighbours() {
      if (this.isMergable()) {
         for(ItemEntity â˜ƒ : this.level
            .getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.5, 0.0, 0.5), var1 -> var1 != this && var1.isMergable())) {
            if (â˜ƒ.isMergable()) {
               this.tryToMerge(â˜ƒ);
               if (this.isRemoved()) {
                  break;
               }
            }
         }
      }
   }

   private boolean isMergable() {
      ItemStack â˜ƒ = this.getItem();
      return this.isAlive() && this.pickupDelay != 32767 && this.age != -32768 && this.age < 6000 && â˜ƒ.getCount() < â˜ƒ.getMaxStackSize();
   }

   private void tryToMerge(ItemEntity var1) {
      ItemStack â˜ƒ = this.getItem();
      ItemStack â˜ƒx = â˜ƒ.getItem();
      if (Objects.equals(this.getOwner(), â˜ƒ.getOwner()) && areMergable(â˜ƒ, â˜ƒx)) {
         if (â˜ƒx.getCount() < â˜ƒ.getCount()) {
            merge(this, â˜ƒ, â˜ƒ, â˜ƒx);
         } else {
            merge(â˜ƒ, â˜ƒx, this, â˜ƒ);
         }
      }
   }

   public static boolean areMergable(ItemStack var0, ItemStack var1) {
      if (!â˜ƒ.is(â˜ƒ.getItem())) {
         return false;
      } else if (â˜ƒ.getCount() + â˜ƒ.getCount() > â˜ƒ.getMaxStackSize()) {
         return false;
      } else if (â˜ƒ.hasTag() ^ â˜ƒ.hasTag()) {
         return false;
      } else {
         return !â˜ƒ.hasTag() || â˜ƒ.getTag().equals(â˜ƒ.getTag());
      }
   }

   public static ItemStack merge(ItemStack var0, ItemStack var1, int var2) {
      int â˜ƒ = Math.min(Math.min(â˜ƒ.getMaxStackSize(), â˜ƒ) - â˜ƒ.getCount(), â˜ƒ.getCount());
      ItemStack â˜ƒx = â˜ƒ.copy();
      â˜ƒx.grow(â˜ƒ);
      â˜ƒ.shrink(â˜ƒ);
      return â˜ƒx;
   }

   private static void merge(ItemEntity var0, ItemStack var1, ItemStack var2) {
      ItemStack â˜ƒ = merge(â˜ƒ, â˜ƒ, 64);
      â˜ƒ.setItem(â˜ƒ);
   }

   private static void merge(ItemEntity var0, ItemStack var1, ItemEntity var2, ItemStack var3) {
      merge(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.pickupDelay = Math.max(â˜ƒ.pickupDelay, â˜ƒ.pickupDelay);
      â˜ƒ.age = Math.min(â˜ƒ.age, â˜ƒ.age);
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.discard();
      }
   }

   @Override
   public boolean fireImmune() {
      return this.getItem().getItem().isFireResistant() || super.fireImmune();
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (!this.getItem().isEmpty() && this.getItem().is(Items.NETHER_STAR) && â˜ƒ.isExplosion()) {
         return false;
      } else if (!this.getItem().getItem().canBeHurtBy(â˜ƒ)) {
         return false;
      } else {
         this.markHurt();
         this.health = (int)((float)this.health - â˜ƒ);
         this.gameEvent(GameEvent.ENTITY_DAMAGED, â˜ƒ.getEntity());
         if (this.health <= 0) {
            this.getItem().onDestroyed(this);
            this.discard();
         }

         return true;
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putShort("Health", (short)this.health);
      â˜ƒ.putShort("Age", (short)this.age);
      â˜ƒ.putShort("PickupDelay", (short)this.pickupDelay);
      if (this.getThrower() != null) {
         â˜ƒ.putUUID("Thrower", this.getThrower());
      }

      if (this.getOwner() != null) {
         â˜ƒ.putUUID("Owner", this.getOwner());
      }

      if (!this.getItem().isEmpty()) {
         â˜ƒ.put("Item", this.getItem().save(new CompoundTag()));
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.health = â˜ƒ.getShort("Health");
      this.age = â˜ƒ.getShort("Age");
      if (â˜ƒ.contains("PickupDelay")) {
         this.pickupDelay = â˜ƒ.getShort("PickupDelay");
      }

      if (â˜ƒ.hasUUID("Owner")) {
         this.owner = â˜ƒ.getUUID("Owner");
      }

      if (â˜ƒ.hasUUID("Thrower")) {
         this.thrower = â˜ƒ.getUUID("Thrower");
      }

      CompoundTag â˜ƒ = â˜ƒ.getCompound("Item");
      this.setItem(ItemStack.of(â˜ƒ));
      if (this.getItem().isEmpty()) {
         this.discard();
      }
   }

   @Override
   public void playerTouch(Player var1) {
      if (!this.level.isClientSide) {
         ItemStack â˜ƒ = this.getItem();
         Item â˜ƒx = â˜ƒ.getItem();
         int â˜ƒxx = â˜ƒ.getCount();
         if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(â˜ƒ.getUUID())) && â˜ƒ.getInventory().add(â˜ƒ)) {
            â˜ƒ.take(this, â˜ƒxx);
            if (â˜ƒ.isEmpty()) {
               this.discard();
               â˜ƒ.setCount(â˜ƒxx);
            }

            â˜ƒ.awardStat(Stats.ITEM_PICKED_UP.get(â˜ƒx), â˜ƒxx);
            â˜ƒ.onItemPickup(this);
         }
      }
   }

   @Override
   public Component getName() {
      Component â˜ƒ = this.getCustomName();
      return (Component)(â˜ƒ != null ? â˜ƒ : new TranslatableComponent(this.getItem().getDescriptionId()));
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   @Nullable
   @Override
   public Entity changeDimension(ServerLevel var1) {
      Entity â˜ƒ = super.changeDimension(â˜ƒ);
      if (!this.level.isClientSide && â˜ƒ instanceof ItemEntity) {
         ((ItemEntity)â˜ƒ).mergeWithNeighbours();
      }

      return â˜ƒ;
   }

   public ItemStack getItem() {
      return this.getEntityData().get(DATA_ITEM);
   }

   public void setItem(ItemStack var1) {
      this.getEntityData().set(DATA_ITEM, â˜ƒ);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      super.onSyncedDataUpdated(â˜ƒ);
      if (DATA_ITEM.equals(â˜ƒ)) {
         this.getItem().setEntityRepresentation(this);
      }
   }

   @Nullable
   public UUID getOwner() {
      return this.owner;
   }

   public void setOwner(@Nullable UUID var1) {
      this.owner = â˜ƒ;
   }

   @Nullable
   public UUID getThrower() {
      return this.thrower;
   }

   public void setThrower(@Nullable UUID var1) {
      this.thrower = â˜ƒ;
   }

   public int getAge() {
      return this.age;
   }

   public void setDefaultPickUpDelay() {
      this.pickupDelay = 10;
   }

   public void setNoPickUpDelay() {
      this.pickupDelay = 0;
   }

   public void setNeverPickUp() {
      this.pickupDelay = 32767;
   }

   public void setPickUpDelay(int var1) {
      this.pickupDelay = â˜ƒ;
   }

   public boolean hasPickUpDelay() {
      return this.pickupDelay > 0;
   }

   public void setUnlimitedLifetime() {
      this.age = -32768;
   }

   public void setExtendedLifetime() {
      this.age = -6000;
   }

   public void makeFakeItem() {
      this.setNeverPickUp();
      this.age = 5999;
   }

   public float getSpin(float var1) {
      return ((float)this.getAge() + â˜ƒ) / 20.0F + this.bobOffs;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }

   public ItemEntity copy() {
      return new ItemEntity(this);
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.AMBIENT;
   }
}
