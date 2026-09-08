package net.minecraft.world.entity.decoration;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArmorStand extends LivingEntity {
   public static final int WOBBLE_TIME = 5;
   private static final boolean ENABLE_ARMS = true;
   private static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0F, 0.0F, -10.0F);
   private static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-15.0F, 0.0F, 10.0F);
   private static final Rotations DEFAULT_LEFT_LEG_POSE = new Rotations(-1.0F, 0.0F, -1.0F);
   private static final Rotations DEFAULT_RIGHT_LEG_POSE = new Rotations(1.0F, 0.0F, 1.0F);
   private static final EntityDimensions MARKER_DIMENSIONS = new EntityDimensions(0.0F, 0.0F, true);
   private static final EntityDimensions BABY_DIMENSIONS = EntityType.ARMOR_STAND.getDimensions().scale(0.5F);
   private static final double FEET_OFFSET = 0.1;
   private static final double CHEST_OFFSET = 0.9;
   private static final double LEGS_OFFSET = 0.4;
   private static final double HEAD_OFFSET = 1.6;
   public static final int DISABLE_TAKING_OFFSET = 8;
   public static final int DISABLE_PUTTING_OFFSET = 16;
   public static final int CLIENT_FLAG_SMALL = 1;
   public static final int CLIENT_FLAG_SHOW_ARMS = 4;
   public static final int CLIENT_FLAG_NO_BASEPLATE = 8;
   public static final int CLIENT_FLAG_MARKER = 16;
   public static final EntityDataAccessor<Byte> DATA_CLIENT_FLAGS = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.BYTE);
   public static final EntityDataAccessor<Rotations> DATA_HEAD_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
   public static final EntityDataAccessor<Rotations> DATA_BODY_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
   public static final EntityDataAccessor<Rotations> DATA_LEFT_ARM_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
   public static final EntityDataAccessor<Rotations> DATA_RIGHT_ARM_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
   public static final EntityDataAccessor<Rotations> DATA_LEFT_LEG_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
   public static final EntityDataAccessor<Rotations> DATA_RIGHT_LEG_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
   private static final Predicate<Entity> RIDABLE_MINECARTS = var0 -> var0 instanceof AbstractMinecart
         && ((AbstractMinecart)var0).getMinecartType() == AbstractMinecart.Type.RIDEABLE;
   private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
   private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
   private boolean invisible;
   public long lastHit;
   private int disabledSlots;
   private Rotations headPose = DEFAULT_HEAD_POSE;
   private Rotations bodyPose = DEFAULT_BODY_POSE;
   private Rotations leftArmPose = DEFAULT_LEFT_ARM_POSE;
   private Rotations rightArmPose = DEFAULT_RIGHT_ARM_POSE;
   private Rotations leftLegPose = DEFAULT_LEFT_LEG_POSE;
   private Rotations rightLegPose = DEFAULT_RIGHT_LEG_POSE;

   public ArmorStand(EntityType<? extends ArmorStand> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.maxUpStep = 0.0F;
   }

   public ArmorStand(Level var1, double var2, double var4, double var6) {
      this(EntityType.ARMOR_STAND, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void refreshDimensions() {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      super.refreshDimensions();
      this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   private boolean hasPhysics() {
      return !this.isMarker() && !this.isNoGravity();
   }

   @Override
   public boolean isEffectiveAi() {
      return super.isEffectiveAi() && this.hasPhysics();
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_CLIENT_FLAGS, (byte)0);
      this.entityData.define(DATA_HEAD_POSE, DEFAULT_HEAD_POSE);
      this.entityData.define(DATA_BODY_POSE, DEFAULT_BODY_POSE);
      this.entityData.define(DATA_LEFT_ARM_POSE, DEFAULT_LEFT_ARM_POSE);
      this.entityData.define(DATA_RIGHT_ARM_POSE, DEFAULT_RIGHT_ARM_POSE);
      this.entityData.define(DATA_LEFT_LEG_POSE, DEFAULT_LEFT_LEG_POSE);
      this.entityData.define(DATA_RIGHT_LEG_POSE, DEFAULT_RIGHT_LEG_POSE);
   }

   @Override
   public Iterable<ItemStack> getHandSlots() {
      return this.handItems;
   }

   @Override
   public Iterable<ItemStack> getArmorSlots() {
      return this.armorItems;
   }

   @Override
   public ItemStack getItemBySlot(EquipmentSlot var1) {
      switch(â˜ƒ.getType()) {
         case HAND:
            return this.handItems.get(â˜ƒ.getIndex());
         case ARMOR:
            return this.armorItems.get(â˜ƒ.getIndex());
         default:
            return ItemStack.EMPTY;
      }
   }

   @Override
   public void setItemSlot(EquipmentSlot var1, ItemStack var2) {
      this.verifyEquippedItem(â˜ƒ);
      switch(â˜ƒ.getType()) {
         case HAND:
            this.equipEventAndSound(â˜ƒ);
            this.handItems.set(â˜ƒ.getIndex(), â˜ƒ);
            break;
         case ARMOR:
            this.equipEventAndSound(â˜ƒ);
            this.armorItems.set(â˜ƒ.getIndex(), â˜ƒ);
      }
   }

   @Override
   public boolean canTakeItem(ItemStack var1) {
      EquipmentSlot â˜ƒ = Mob.getEquipmentSlotForItem(â˜ƒ);
      return this.getItemBySlot(â˜ƒ).isEmpty() && !this.isDisabled(â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      ListTag â˜ƒ = new ListTag();

      for(ItemStack â˜ƒx : this.armorItems) {
         CompoundTag â˜ƒxx = new CompoundTag();
         if (!â˜ƒx.isEmpty()) {
            â˜ƒx.save(â˜ƒxx);
         }

         â˜ƒ.add(â˜ƒxx);
      }

      â˜ƒ.put("ArmorItems", â˜ƒ);
      ListTag â˜ƒx = new ListTag();

      for(ItemStack â˜ƒxx : this.handItems) {
         CompoundTag â˜ƒxxx = new CompoundTag();
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒxx.save(â˜ƒxxx);
         }

         â˜ƒx.add(â˜ƒxxx);
      }

      â˜ƒ.put("HandItems", â˜ƒx);
      â˜ƒ.putBoolean("Invisible", this.isInvisible());
      â˜ƒ.putBoolean("Small", this.isSmall());
      â˜ƒ.putBoolean("ShowArms", this.isShowArms());
      â˜ƒ.putInt("DisabledSlots", this.disabledSlots);
      â˜ƒ.putBoolean("NoBasePlate", this.isNoBasePlate());
      if (this.isMarker()) {
         â˜ƒ.putBoolean("Marker", this.isMarker());
      }

      â˜ƒ.put("Pose", this.writePose());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("ArmorItems", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("ArmorItems", 10);

         for(int â˜ƒx = 0; â˜ƒx < this.armorItems.size(); ++â˜ƒx) {
            this.armorItems.set(â˜ƒx, ItemStack.of(â˜ƒ.getCompound(â˜ƒx)));
         }
      }

      if (â˜ƒ.contains("HandItems", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("HandItems", 10);

         for(int â˜ƒx = 0; â˜ƒx < this.handItems.size(); ++â˜ƒx) {
            this.handItems.set(â˜ƒx, ItemStack.of(â˜ƒ.getCompound(â˜ƒx)));
         }
      }

      this.setInvisible(â˜ƒ.getBoolean("Invisible"));
      this.setSmall(â˜ƒ.getBoolean("Small"));
      this.setShowArms(â˜ƒ.getBoolean("ShowArms"));
      this.disabledSlots = â˜ƒ.getInt("DisabledSlots");
      this.setNoBasePlate(â˜ƒ.getBoolean("NoBasePlate"));
      this.setMarker(â˜ƒ.getBoolean("Marker"));
      this.noPhysics = !this.hasPhysics();
      CompoundTag â˜ƒ = â˜ƒ.getCompound("Pose");
      this.readPose(â˜ƒ);
   }

   private void readPose(CompoundTag var1) {
      ListTag â˜ƒ = â˜ƒ.getList("Head", 5);
      this.setHeadPose(â˜ƒ.isEmpty() ? DEFAULT_HEAD_POSE : new Rotations(â˜ƒ));
      ListTag â˜ƒx = â˜ƒ.getList("Body", 5);
      this.setBodyPose(â˜ƒx.isEmpty() ? DEFAULT_BODY_POSE : new Rotations(â˜ƒx));
      ListTag â˜ƒxx = â˜ƒ.getList("LeftArm", 5);
      this.setLeftArmPose(â˜ƒxx.isEmpty() ? DEFAULT_LEFT_ARM_POSE : new Rotations(â˜ƒxx));
      ListTag â˜ƒxxx = â˜ƒ.getList("RightArm", 5);
      this.setRightArmPose(â˜ƒxxx.isEmpty() ? DEFAULT_RIGHT_ARM_POSE : new Rotations(â˜ƒxxx));
      ListTag â˜ƒxxxx = â˜ƒ.getList("LeftLeg", 5);
      this.setLeftLegPose(â˜ƒxxxx.isEmpty() ? DEFAULT_LEFT_LEG_POSE : new Rotations(â˜ƒxxxx));
      ListTag â˜ƒxxxxx = â˜ƒ.getList("RightLeg", 5);
      this.setRightLegPose(â˜ƒxxxxx.isEmpty() ? DEFAULT_RIGHT_LEG_POSE : new Rotations(â˜ƒxxxxx));
   }

   private CompoundTag writePose() {
      CompoundTag â˜ƒ = new CompoundTag();
      if (!DEFAULT_HEAD_POSE.equals(this.headPose)) {
         â˜ƒ.put("Head", this.headPose.save());
      }

      if (!DEFAULT_BODY_POSE.equals(this.bodyPose)) {
         â˜ƒ.put("Body", this.bodyPose.save());
      }

      if (!DEFAULT_LEFT_ARM_POSE.equals(this.leftArmPose)) {
         â˜ƒ.put("LeftArm", this.leftArmPose.save());
      }

      if (!DEFAULT_RIGHT_ARM_POSE.equals(this.rightArmPose)) {
         â˜ƒ.put("RightArm", this.rightArmPose.save());
      }

      if (!DEFAULT_LEFT_LEG_POSE.equals(this.leftLegPose)) {
         â˜ƒ.put("LeftLeg", this.leftLegPose.save());
      }

      if (!DEFAULT_RIGHT_LEG_POSE.equals(this.rightLegPose)) {
         â˜ƒ.put("RightLeg", this.rightLegPose.save());
      }

      return â˜ƒ;
   }

   @Override
   public boolean isPushable() {
      return false;
   }

   @Override
   protected void doPush(Entity var1) {
   }

   @Override
   protected void pushEntities() {
      List<Entity> â˜ƒ = this.level.getEntities(this, this.getBoundingBox(), RIDABLE_MINECARTS);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         Entity â˜ƒxx = (Entity)â˜ƒ.get(â˜ƒx);
         if (this.distanceToSqr(â˜ƒxx) <= 0.2) {
            â˜ƒxx.push(this);
         }
      }
   }

   @Override
   public InteractionResult interactAt(Player var1, Vec3 var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (this.isMarker() || â˜ƒ.is(Items.NAME_TAG)) {
         return InteractionResult.PASS;
      } else if (â˜ƒ.isSpectator()) {
         return InteractionResult.SUCCESS;
      } else if (â˜ƒ.level.isClientSide) {
         return InteractionResult.CONSUME;
      } else {
         EquipmentSlot â˜ƒ = Mob.getEquipmentSlotForItem(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            EquipmentSlot â˜ƒx = this.getClickedSlot(â˜ƒ);
            EquipmentSlot â˜ƒxx = this.isDisabled(â˜ƒx) ? â˜ƒ : â˜ƒx;
            if (this.hasItemInSlot(â˜ƒxx) && this.swapItem(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ)) {
               return InteractionResult.SUCCESS;
            }
         } else {
            if (this.isDisabled(â˜ƒ)) {
               return InteractionResult.FAIL;
            }

            if (â˜ƒ.getType() == EquipmentSlot.Type.HAND && !this.isShowArms()) {
               return InteractionResult.FAIL;
            }

            if (this.swapItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.PASS;
      }
   }

   private EquipmentSlot getClickedSlot(Vec3 var1) {
      EquipmentSlot â˜ƒ = EquipmentSlot.MAINHAND;
      boolean â˜ƒx = this.isSmall();
      double â˜ƒxx = â˜ƒx ? â˜ƒ.y * 2.0 : â˜ƒ.y;
      EquipmentSlot â˜ƒxxx = EquipmentSlot.FEET;
      if (â˜ƒxx >= 0.1 && â˜ƒxx < 0.1 + (â˜ƒx ? 0.8 : 0.45) && this.hasItemInSlot(â˜ƒxxx)) {
         â˜ƒ = EquipmentSlot.FEET;
      } else if (â˜ƒxx >= 0.9 + (â˜ƒx ? 0.3 : 0.0) && â˜ƒxx < 0.9 + (â˜ƒx ? 1.0 : 0.7) && this.hasItemInSlot(EquipmentSlot.CHEST)) {
         â˜ƒ = EquipmentSlot.CHEST;
      } else if (â˜ƒxx >= 0.4 && â˜ƒxx < 0.4 + (â˜ƒx ? 1.0 : 0.8) && this.hasItemInSlot(EquipmentSlot.LEGS)) {
         â˜ƒ = EquipmentSlot.LEGS;
      } else if (â˜ƒxx >= 1.6 && this.hasItemInSlot(EquipmentSlot.HEAD)) {
         â˜ƒ = EquipmentSlot.HEAD;
      } else if (!this.hasItemInSlot(EquipmentSlot.MAINHAND) && this.hasItemInSlot(EquipmentSlot.OFFHAND)) {
         â˜ƒ = EquipmentSlot.OFFHAND;
      }

      return â˜ƒ;
   }

   private boolean isDisabled(EquipmentSlot var1) {
      return (this.disabledSlots & 1 << â˜ƒ.getFilterFlag()) != 0 || â˜ƒ.getType() == EquipmentSlot.Type.HAND && !this.isShowArms();
   }

   private boolean swapItem(Player var1, EquipmentSlot var2, ItemStack var3, InteractionHand var4) {
      ItemStack â˜ƒ = this.getItemBySlot(â˜ƒ);
      if (!â˜ƒ.isEmpty() && (this.disabledSlots & 1 << â˜ƒ.getFilterFlag() + 8) != 0) {
         return false;
      } else if (â˜ƒ.isEmpty() && (this.disabledSlots & 1 << â˜ƒ.getFilterFlag() + 16) != 0) {
         return false;
      } else if (â˜ƒ.getAbilities().instabuild && â˜ƒ.isEmpty() && !â˜ƒ.isEmpty()) {
         ItemStack â˜ƒ = â˜ƒ.copy();
         â˜ƒ.setCount(1);
         this.setItemSlot(â˜ƒ, â˜ƒ);
         return true;
      } else if (â˜ƒ.isEmpty() || â˜ƒ.getCount() <= 1) {
         this.setItemSlot(â˜ƒ, â˜ƒ);
         â˜ƒ.setItemInHand(â˜ƒ, â˜ƒ);
         return true;
      } else if (!â˜ƒ.isEmpty()) {
         return false;
      } else {
         ItemStack â˜ƒ = â˜ƒ.copy();
         â˜ƒ.setCount(1);
         this.setItemSlot(â˜ƒ, â˜ƒ);
         â˜ƒ.shrink(1);
         return true;
      }
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.level.isClientSide || this.isRemoved()) {
         return false;
      } else if (DamageSource.OUT_OF_WORLD.equals(â˜ƒ)) {
         this.kill();
         return false;
      } else if (this.isInvulnerableTo(â˜ƒ) || this.invisible || this.isMarker()) {
         return false;
      } else if (â˜ƒ.isExplosion()) {
         this.brokenByAnything(â˜ƒ);
         this.kill();
         return false;
      } else if (DamageSource.IN_FIRE.equals(â˜ƒ)) {
         if (this.isOnFire()) {
            this.causeDamage(â˜ƒ, 0.15F);
         } else {
            this.setSecondsOnFire(5);
         }

         return false;
      } else if (DamageSource.ON_FIRE.equals(â˜ƒ) && this.getHealth() > 0.5F) {
         this.causeDamage(â˜ƒ, 4.0F);
         return false;
      } else {
         boolean â˜ƒ = â˜ƒ.getDirectEntity() instanceof AbstractArrow;
         boolean â˜ƒx = â˜ƒ && ((AbstractArrow)â˜ƒ.getDirectEntity()).getPierceLevel() > 0;
         boolean â˜ƒxx = "player".equals(â˜ƒ.getMsgId());
         if (!â˜ƒxx && !â˜ƒ) {
            return false;
         } else if (â˜ƒ.getEntity() instanceof Player && !((Player)â˜ƒ.getEntity()).getAbilities().mayBuild) {
            return false;
         } else if (â˜ƒ.isCreativePlayer()) {
            this.playBrokenSound();
            this.showBreakingParticles();
            this.kill();
            return â˜ƒx;
         } else {
            long â˜ƒ = this.level.getGameTime();
            if (â˜ƒ - this.lastHit > 5L && !â˜ƒ) {
               this.level.broadcastEntityEvent(this, (byte)32);
               this.gameEvent(GameEvent.ENTITY_DAMAGED, â˜ƒ.getEntity());
               this.lastHit = â˜ƒ;
            } else {
               this.brokenByPlayer(â˜ƒ);
               this.showBreakingParticles();
               this.kill();
            }

            return true;
         }
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 32) {
         if (this.level.isClientSide) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
            this.lastHit = this.level.getGameTime();
         }
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = this.getBoundingBox().getSize() * 4.0;
      if (Double.isNaN(â˜ƒ) || â˜ƒ == 0.0) {
         â˜ƒ = 4.0;
      }

      â˜ƒ *= 64.0;
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   private void showBreakingParticles() {
      if (this.level instanceof ServerLevel) {
         ((ServerLevel)this.level)
            .sendParticles(
               new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()),
               this.getX(),
               this.getY(0.6666666666666666),
               this.getZ(),
               10,
               (double)(this.getBbWidth() / 4.0F),
               (double)(this.getBbHeight() / 4.0F),
               (double)(this.getBbWidth() / 4.0F),
               0.05
            );
      }
   }

   private void causeDamage(DamageSource var1, float var2) {
      float â˜ƒ = this.getHealth();
      â˜ƒ -= â˜ƒ;
      if (â˜ƒ <= 0.5F) {
         this.brokenByAnything(â˜ƒ);
         this.kill();
      } else {
         this.setHealth(â˜ƒ);
         this.gameEvent(GameEvent.ENTITY_DAMAGED, â˜ƒ.getEntity());
      }
   }

   private void brokenByPlayer(DamageSource var1) {
      Block.popResource(this.level, this.blockPosition(), new ItemStack(Items.ARMOR_STAND));
      this.brokenByAnything(â˜ƒ);
   }

   private void brokenByAnything(DamageSource var1) {
      this.playBrokenSound();
      this.dropAllDeathLoot(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < this.handItems.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.handItems.get(â˜ƒ);
         if (!â˜ƒx.isEmpty()) {
            Block.popResource(this.level, this.blockPosition().above(), â˜ƒx);
            this.handItems.set(â˜ƒ, ItemStack.EMPTY);
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < this.armorItems.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.armorItems.get(â˜ƒ);
         if (!â˜ƒx.isEmpty()) {
            Block.popResource(this.level, this.blockPosition().above(), â˜ƒx);
            this.armorItems.set(â˜ƒ, ItemStack.EMPTY);
         }
      }
   }

   private void playBrokenSound() {
      this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
   }

   @Override
   protected float tickHeadTurn(float var1, float var2) {
      this.yBodyRotO = this.yRotO;
      this.yBodyRot = this.getYRot();
      return 0.0F;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return â˜ƒ.height * (this.isBaby() ? 0.5F : 0.9F);
   }

   @Override
   public double getMyRidingOffset() {
      return this.isMarker() ? 0.0 : 0.1F;
   }

   @Override
   public void travel(Vec3 var1) {
      if (this.hasPhysics()) {
         super.travel(â˜ƒ);
      }
   }

   @Override
   public void setYBodyRot(float var1) {
      this.yBodyRotO = this.yRotO = â˜ƒ;
      this.yHeadRotO = this.yHeadRot = â˜ƒ;
   }

   @Override
   public void setYHeadRot(float var1) {
      this.yBodyRotO = this.yRotO = â˜ƒ;
      this.yHeadRotO = this.yHeadRot = â˜ƒ;
   }

   @Override
   public void tick() {
      super.tick();
      Rotations â˜ƒ = this.entityData.get(DATA_HEAD_POSE);
      if (!this.headPose.equals(â˜ƒ)) {
         this.setHeadPose(â˜ƒ);
      }

      Rotations â˜ƒ = this.entityData.get(DATA_BODY_POSE);
      if (!this.bodyPose.equals(â˜ƒ)) {
         this.setBodyPose(â˜ƒ);
      }

      Rotations â˜ƒ = this.entityData.get(DATA_LEFT_ARM_POSE);
      if (!this.leftArmPose.equals(â˜ƒ)) {
         this.setLeftArmPose(â˜ƒ);
      }

      Rotations â˜ƒ = this.entityData.get(DATA_RIGHT_ARM_POSE);
      if (!this.rightArmPose.equals(â˜ƒ)) {
         this.setRightArmPose(â˜ƒ);
      }

      Rotations â˜ƒ = this.entityData.get(DATA_LEFT_LEG_POSE);
      if (!this.leftLegPose.equals(â˜ƒ)) {
         this.setLeftLegPose(â˜ƒ);
      }

      Rotations â˜ƒ = this.entityData.get(DATA_RIGHT_LEG_POSE);
      if (!this.rightLegPose.equals(â˜ƒ)) {
         this.setRightLegPose(â˜ƒ);
      }
   }

   @Override
   protected void updateInvisibilityStatus() {
      this.setInvisible(this.invisible);
   }

   @Override
   public void setInvisible(boolean var1) {
      this.invisible = â˜ƒ;
      super.setInvisible(â˜ƒ);
   }

   @Override
   public boolean isBaby() {
      return this.isSmall();
   }

   @Override
   public void kill() {
      this.remove(Entity.RemovalReason.KILLED);
   }

   @Override
   public boolean ignoreExplosion() {
      return this.isInvisible();
   }

   @Override
   public PushReaction getPistonPushReaction() {
      return this.isMarker() ? PushReaction.IGNORE : super.getPistonPushReaction();
   }

   private void setSmall(boolean var1) {
      this.entityData.set(DATA_CLIENT_FLAGS, this.setBit(this.entityData.get(DATA_CLIENT_FLAGS), 1, â˜ƒ));
   }

   public boolean isSmall() {
      return (this.entityData.get(DATA_CLIENT_FLAGS) & 1) != 0;
   }

   private void setShowArms(boolean var1) {
      this.entityData.set(DATA_CLIENT_FLAGS, this.setBit(this.entityData.get(DATA_CLIENT_FLAGS), 4, â˜ƒ));
   }

   public boolean isShowArms() {
      return (this.entityData.get(DATA_CLIENT_FLAGS) & 4) != 0;
   }

   private void setNoBasePlate(boolean var1) {
      this.entityData.set(DATA_CLIENT_FLAGS, this.setBit(this.entityData.get(DATA_CLIENT_FLAGS), 8, â˜ƒ));
   }

   public boolean isNoBasePlate() {
      return (this.entityData.get(DATA_CLIENT_FLAGS) & 8) != 0;
   }

   private void setMarker(boolean var1) {
      this.entityData.set(DATA_CLIENT_FLAGS, this.setBit(this.entityData.get(DATA_CLIENT_FLAGS), 16, â˜ƒ));
   }

   public boolean isMarker() {
      return (this.entityData.get(DATA_CLIENT_FLAGS) & 16) != 0;
   }

   private byte setBit(byte var1, int var2, boolean var3) {
      if (â˜ƒ) {
         â˜ƒ = (byte)(â˜ƒ | â˜ƒ);
      } else {
         â˜ƒ = (byte)(â˜ƒ & ~â˜ƒ);
      }

      return â˜ƒ;
   }

   public void setHeadPose(Rotations var1) {
      this.headPose = â˜ƒ;
      this.entityData.set(DATA_HEAD_POSE, â˜ƒ);
   }

   public void setBodyPose(Rotations var1) {
      this.bodyPose = â˜ƒ;
      this.entityData.set(DATA_BODY_POSE, â˜ƒ);
   }

   public void setLeftArmPose(Rotations var1) {
      this.leftArmPose = â˜ƒ;
      this.entityData.set(DATA_LEFT_ARM_POSE, â˜ƒ);
   }

   public void setRightArmPose(Rotations var1) {
      this.rightArmPose = â˜ƒ;
      this.entityData.set(DATA_RIGHT_ARM_POSE, â˜ƒ);
   }

   public void setLeftLegPose(Rotations var1) {
      this.leftLegPose = â˜ƒ;
      this.entityData.set(DATA_LEFT_LEG_POSE, â˜ƒ);
   }

   public void setRightLegPose(Rotations var1) {
      this.rightLegPose = â˜ƒ;
      this.entityData.set(DATA_RIGHT_LEG_POSE, â˜ƒ);
   }

   public Rotations getHeadPose() {
      return this.headPose;
   }

   public Rotations getBodyPose() {
      return this.bodyPose;
   }

   public Rotations getLeftArmPose() {
      return this.leftArmPose;
   }

   public Rotations getRightArmPose() {
      return this.rightArmPose;
   }

   public Rotations getLeftLegPose() {
      return this.leftLegPose;
   }

   public Rotations getRightLegPose() {
      return this.rightLegPose;
   }

   @Override
   public boolean isPickable() {
      return super.isPickable() && !this.isMarker();
   }

   @Override
   public boolean skipAttackInteraction(Entity var1) {
      return â˜ƒ instanceof Player && !this.level.mayInteract((Player)â˜ƒ, this.blockPosition());
   }

   @Override
   public HumanoidArm getMainArm() {
      return HumanoidArm.RIGHT;
   }

   @Override
   protected SoundEvent getFallDamageSound(int var1) {
      return SoundEvents.ARMOR_STAND_FALL;
   }

   @Nullable
   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.ARMOR_STAND_HIT;
   }

   @Nullable
   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ARMOR_STAND_BREAK;
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
   }

   @Override
   public boolean isAffectedByPotions() {
      return false;
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_CLIENT_FLAGS.equals(â˜ƒ)) {
         this.refreshDimensions();
         this.blocksBuilding = !this.isMarker();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   public boolean attackable() {
      return false;
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return this.getDimensionsMarker(this.isMarker());
   }

   private EntityDimensions getDimensionsMarker(boolean var1) {
      if (â˜ƒ) {
         return MARKER_DIMENSIONS;
      } else {
         return this.isBaby() ? BABY_DIMENSIONS : this.getType().getDimensions();
      }
   }

   @Override
   public Vec3 getLightProbePosition(float var1) {
      if (this.isMarker()) {
         AABB â˜ƒ = this.getDimensionsMarker(false).makeBoundingBox(this.position());
         BlockPos â˜ƒx = this.blockPosition();
         int â˜ƒxx = Integer.MIN_VALUE;

         for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(new BlockPos(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ), new BlockPos(â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ))) {
            int â˜ƒxxxx = Math.max(this.level.getBrightness(LightLayer.BLOCK, â˜ƒxxx), this.level.getBrightness(LightLayer.SKY, â˜ƒxxx));
            if (â˜ƒxxxx == 15) {
               return Vec3.atCenterOf(â˜ƒxxx);
            }

            if (â˜ƒxxxx > â˜ƒxx) {
               â˜ƒxx = â˜ƒxxxx;
               â˜ƒx = â˜ƒxxx.immutable();
            }
         }

         return Vec3.atCenterOf(â˜ƒx);
      } else {
         return super.getLightProbePosition(â˜ƒ);
      }
   }

   @Override
   public ItemStack getPickResult() {
      return new ItemStack(Items.ARMOR_STAND);
   }

   @Override
   public boolean canBeSeenByAnyone() {
      return !this.isInvisible() && !this.isMarker();
   }
}
