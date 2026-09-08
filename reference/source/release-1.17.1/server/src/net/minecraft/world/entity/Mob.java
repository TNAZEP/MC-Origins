package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootContext;

public abstract class Mob extends LivingEntity {
   private static final EntityDataAccessor<Byte> DATA_MOB_FLAGS_ID = SynchedEntityData.defineId(Mob.class, EntityDataSerializers.BYTE);
   private static final int MOB_FLAG_NO_AI = 1;
   private static final int MOB_FLAG_LEFTHANDED = 2;
   private static final int MOB_FLAG_AGGRESSIVE = 4;
   public static final float MAX_WEARING_ARMOR_CHANCE = 0.15F;
   public static final float MAX_PICKUP_LOOT_CHANCE = 0.55F;
   public static final float MAX_ENCHANTED_ARMOR_CHANCE = 0.5F;
   public static final float MAX_ENCHANTED_WEAPON_CHANCE = 0.25F;
   public static final String LEASH_TAG = "Leash";
   private static final int PICKUP_REACH = 1;
   public static final float DEFAULT_EQUIPMENT_DROP_CHANCE = 0.085F;
   public int ambientSoundTime;
   protected int xpReward;
   protected LookControl lookControl;
   protected MoveControl moveControl;
   protected JumpControl jumpControl;
   private final BodyRotationControl bodyRotationControl;
   protected PathNavigation navigation;
   protected final GoalSelector goalSelector;
   protected final GoalSelector targetSelector;
   private LivingEntity target;
   private final Sensing sensing;
   private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
   protected final float[] handDropChances = new float[2];
   private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
   protected final float[] armorDropChances = new float[4];
   private boolean canPickUpLoot;
   private boolean persistenceRequired;
   private final Map<BlockPathTypes, Float> pathfindingMalus = Maps.newEnumMap(BlockPathTypes.class);
   private ResourceLocation lootTable;
   private long lootTableSeed;
   @Nullable
   private Entity leashHolder;
   private int delayedLeashHolderId;
   @Nullable
   private CompoundTag leashInfoTag;
   private BlockPos restrictCenter = BlockPos.ZERO;
   private float restrictRadius = -1.0F;

   protected Mob(EntityType<? extends Mob> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.goalSelector = new GoalSelector(â˜ƒ.getProfilerSupplier());
      this.targetSelector = new GoalSelector(â˜ƒ.getProfilerSupplier());
      this.lookControl = new LookControl(this);
      this.moveControl = new MoveControl(this);
      this.jumpControl = new JumpControl(this);
      this.bodyRotationControl = this.createBodyControl();
      this.navigation = this.createNavigation(â˜ƒ);
      this.sensing = new Sensing(this);
      Arrays.fill(this.armorDropChances, 0.085F);
      Arrays.fill(this.handDropChances, 0.085F);
      if (â˜ƒ != null && !â˜ƒ.isClientSide) {
         this.registerGoals();
      }
   }

   protected void registerGoals() {
   }

   public static AttributeSupplier.Builder createMobAttributes() {
      return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 16.0).add(Attributes.ATTACK_KNOCKBACK);
   }

   protected PathNavigation createNavigation(Level var1) {
      return new GroundPathNavigation(this, â˜ƒ);
   }

   protected boolean shouldPassengersInheritMalus() {
      return false;
   }

   public float getPathfindingMalus(BlockPathTypes var1) {
      Mob â˜ƒ;
      if (this.getVehicle() instanceof Mob && ((Mob)this.getVehicle()).shouldPassengersInheritMalus()) {
         â˜ƒ = (Mob)this.getVehicle();
      } else {
         â˜ƒ = this;
      }

      Float â˜ƒ = (Float)â˜ƒ.pathfindingMalus.get(â˜ƒ);
      return â˜ƒ == null ? â˜ƒ.getMalus() : â˜ƒ;
   }

   public void setPathfindingMalus(BlockPathTypes var1, float var2) {
      this.pathfindingMalus.put(â˜ƒ, â˜ƒ);
   }

   public boolean canCutCorner(BlockPathTypes var1) {
      return â˜ƒ != BlockPathTypes.DANGER_FIRE
         && â˜ƒ != BlockPathTypes.DANGER_CACTUS
         && â˜ƒ != BlockPathTypes.DANGER_OTHER
         && â˜ƒ != BlockPathTypes.WALKABLE_DOOR;
   }

   protected BodyRotationControl createBodyControl() {
      return new BodyRotationControl(this);
   }

   public LookControl getLookControl() {
      return this.lookControl;
   }

   public MoveControl getMoveControl() {
      return this.isPassenger() && this.getVehicle() instanceof Mob â˜ƒ ? â˜ƒ.getMoveControl() : this.moveControl;
   }

   public JumpControl getJumpControl() {
      return this.jumpControl;
   }

   public PathNavigation getNavigation() {
      return this.isPassenger() && this.getVehicle() instanceof Mob â˜ƒ ? â˜ƒ.getNavigation() : this.navigation;
   }

   public Sensing getSensing() {
      return this.sensing;
   }

   @Nullable
   public LivingEntity getTarget() {
      return this.target;
   }

   public void setTarget(@Nullable LivingEntity var1) {
      this.target = â˜ƒ;
   }

   @Override
   public boolean canAttackType(EntityType<?> var1) {
      return â˜ƒ != EntityType.GHAST;
   }

   public boolean canFireProjectileWeapon(ProjectileWeaponItem var1) {
      return false;
   }

   public void ate() {
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_MOB_FLAGS_ID, (byte)0);
   }

   public int getAmbientSoundInterval() {
      return 80;
   }

   public void playAmbientSound() {
      SoundEvent â˜ƒ = this.getAmbientSound();
      if (â˜ƒ != null) {
         this.playSound(â˜ƒ, this.getSoundVolume(), this.getVoicePitch());
      }
   }

   @Override
   public void baseTick() {
      super.baseTick();
      this.level.getProfiler().push("mobBaseTick");
      if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundTime++) {
         this.resetAmbientSoundTime();
         this.playAmbientSound();
      }

      this.level.getProfiler().pop();
   }

   @Override
   protected void playHurtSound(DamageSource var1) {
      this.resetAmbientSoundTime();
      super.playHurtSound(â˜ƒ);
   }

   private void resetAmbientSoundTime() {
      this.ambientSoundTime = -this.getAmbientSoundInterval();
   }

   @Override
   protected int getExperienceReward(Player var1) {
      if (this.xpReward > 0) {
         int â˜ƒ = this.xpReward;

         for(int â˜ƒx = 0; â˜ƒx < this.armorItems.size(); ++â˜ƒx) {
            if (!this.armorItems.get(â˜ƒx).isEmpty() && this.armorDropChances[â˜ƒx] <= 1.0F) {
               â˜ƒ += 1 + this.random.nextInt(3);
            }
         }

         for(int â˜ƒx = 0; â˜ƒx < this.handItems.size(); ++â˜ƒx) {
            if (!this.handItems.get(â˜ƒx).isEmpty() && this.handDropChances[â˜ƒx] <= 1.0F) {
               â˜ƒ += 1 + this.random.nextInt(3);
            }
         }

         return â˜ƒ;
      } else {
         return this.xpReward;
      }
   }

   public void spawnAnim() {
      if (this.level.isClientSide) {
         for(int â˜ƒ = 0; â˜ƒ < 20; ++â˜ƒ) {
            double â˜ƒx = this.random.nextGaussian() * 0.02;
            double â˜ƒxx = this.random.nextGaussian() * 0.02;
            double â˜ƒxxx = this.random.nextGaussian() * 0.02;
            double â˜ƒxxxx = 10.0;
            this.level
               .addParticle(
                  ParticleTypes.POOF, this.getX(1.0) - â˜ƒx * 10.0, this.getRandomY() - â˜ƒxx * 10.0, this.getRandomZ(1.0) - â˜ƒxxx * 10.0, â˜ƒx, â˜ƒxx, â˜ƒxxx
               );
         }
      } else {
         this.level.broadcastEntityEvent(this, (byte)20);
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 20) {
         this.spawnAnim();
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level.isClientSide) {
         this.tickLeash();
         if (this.tickCount % 5 == 0) {
            this.updateControlFlags();
         }
      }
   }

   protected void updateControlFlags() {
      boolean â˜ƒ = !(this.getControllingPassenger() instanceof Mob);
      boolean â˜ƒx = !(this.getVehicle() instanceof Boat);
      this.goalSelector.setControlFlag(Goal.Flag.MOVE, â˜ƒ);
      this.goalSelector.setControlFlag(Goal.Flag.JUMP, â˜ƒ && â˜ƒx);
      this.goalSelector.setControlFlag(Goal.Flag.LOOK, â˜ƒ);
   }

   @Override
   protected float tickHeadTurn(float var1, float var2) {
      this.bodyRotationControl.clientTick();
      return â˜ƒ;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("CanPickUpLoot", this.canPickUpLoot());
      â˜ƒ.putBoolean("PersistenceRequired", this.persistenceRequired);
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
      ListTag â˜ƒxx = new ListTag();

      for(float â˜ƒxxx : this.armorDropChances) {
         â˜ƒxx.add(FloatTag.valueOf(â˜ƒxxx));
      }

      â˜ƒ.put("ArmorDropChances", â˜ƒxx);
      ListTag â˜ƒxxx = new ListTag();

      for(float â˜ƒxxxx : this.handDropChances) {
         â˜ƒxxx.add(FloatTag.valueOf(â˜ƒxxxx));
      }

      â˜ƒ.put("HandDropChances", â˜ƒxxx);
      if (this.leashHolder != null) {
         CompoundTag â˜ƒxxxx = new CompoundTag();
         if (this.leashHolder instanceof LivingEntity) {
            UUID â˜ƒxxxxx = this.leashHolder.getUUID();
            â˜ƒxxxx.putUUID("UUID", â˜ƒxxxxx);
         } else if (this.leashHolder instanceof HangingEntity) {
            BlockPos â˜ƒxxxx = ((HangingEntity)this.leashHolder).getPos();
            â˜ƒxxxx.putInt("X", â˜ƒxxxx.getX());
            â˜ƒxxxx.putInt("Y", â˜ƒxxxx.getY());
            â˜ƒxxxx.putInt("Z", â˜ƒxxxx.getZ());
         }

         â˜ƒ.put("Leash", â˜ƒxxxx);
      } else if (this.leashInfoTag != null) {
         â˜ƒ.put("Leash", this.leashInfoTag.copy());
      }

      â˜ƒ.putBoolean("LeftHanded", this.isLeftHanded());
      if (this.lootTable != null) {
         â˜ƒ.putString("DeathLootTable", this.lootTable.toString());
         if (this.lootTableSeed != 0L) {
            â˜ƒ.putLong("DeathLootTableSeed", this.lootTableSeed);
         }
      }

      if (this.isNoAi()) {
         â˜ƒ.putBoolean("NoAI", this.isNoAi());
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("CanPickUpLoot", 1)) {
         this.setCanPickUpLoot(â˜ƒ.getBoolean("CanPickUpLoot"));
      }

      this.persistenceRequired = â˜ƒ.getBoolean("PersistenceRequired");
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

      if (â˜ƒ.contains("ArmorDropChances", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("ArmorDropChances", 5);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            this.armorDropChances[â˜ƒx] = â˜ƒ.getFloat(â˜ƒx);
         }
      }

      if (â˜ƒ.contains("HandDropChances", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("HandDropChances", 5);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            this.handDropChances[â˜ƒx] = â˜ƒ.getFloat(â˜ƒx);
         }
      }

      if (â˜ƒ.contains("Leash", 10)) {
         this.leashInfoTag = â˜ƒ.getCompound("Leash");
      }

      this.setLeftHanded(â˜ƒ.getBoolean("LeftHanded"));
      if (â˜ƒ.contains("DeathLootTable", 8)) {
         this.lootTable = new ResourceLocation(â˜ƒ.getString("DeathLootTable"));
         this.lootTableSeed = â˜ƒ.getLong("DeathLootTableSeed");
      }

      this.setNoAi(â˜ƒ.getBoolean("NoAI"));
   }

   @Override
   protected void dropFromLootTable(DamageSource var1, boolean var2) {
      super.dropFromLootTable(â˜ƒ, â˜ƒ);
      this.lootTable = null;
   }

   @Override
   protected LootContext.Builder createLootContext(boolean var1, DamageSource var2) {
      return super.createLootContext(â˜ƒ, â˜ƒ).withOptionalRandomSeed(this.lootTableSeed, this.random);
   }

   @Override
   public final ResourceLocation getLootTable() {
      return this.lootTable == null ? this.getDefaultLootTable() : this.lootTable;
   }

   protected ResourceLocation getDefaultLootTable() {
      return super.getLootTable();
   }

   public void setZza(float var1) {
      this.zza = â˜ƒ;
   }

   public void setYya(float var1) {
      this.yya = â˜ƒ;
   }

   public void setXxa(float var1) {
      this.xxa = â˜ƒ;
   }

   @Override
   public void setSpeed(float var1) {
      super.setSpeed(â˜ƒ);
      this.setZza(â˜ƒ);
   }

   @Override
   public void aiStep() {
      super.aiStep();
      this.level.getProfiler().push("looting");
      if (!this.level.isClientSide && this.canPickUpLoot() && this.isAlive() && !this.dead && this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         for(ItemEntity â˜ƒ : this.level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(1.0, 0.0, 1.0))) {
            if (!â˜ƒ.isRemoved() && !â˜ƒ.getItem().isEmpty() && !â˜ƒ.hasPickUpDelay() && this.wantsToPickUp(â˜ƒ.getItem())) {
               this.pickUpItem(â˜ƒ);
            }
         }
      }

      this.level.getProfiler().pop();
   }

   protected void pickUpItem(ItemEntity var1) {
      ItemStack â˜ƒ = â˜ƒ.getItem();
      if (this.equipItemIfPossible(â˜ƒ)) {
         this.onItemPickup(â˜ƒ);
         this.take(â˜ƒ, â˜ƒ.getCount());
         â˜ƒ.discard();
      }
   }

   public boolean equipItemIfPossible(ItemStack var1) {
      EquipmentSlot â˜ƒ = getEquipmentSlotForItem(â˜ƒ);
      ItemStack â˜ƒx = this.getItemBySlot(â˜ƒ);
      boolean â˜ƒxx = this.canReplaceCurrentItem(â˜ƒ, â˜ƒx);
      if (â˜ƒxx && this.canHoldItem(â˜ƒ)) {
         double â˜ƒxxx = (double)this.getEquipmentDropChance(â˜ƒ);
         if (!â˜ƒx.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < â˜ƒxxx) {
            this.spawnAtLocation(â˜ƒx);
         }

         this.setItemSlotAndDropWhenKilled(â˜ƒ, â˜ƒ);
         this.equipEventAndSound(â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   protected void setItemSlotAndDropWhenKilled(EquipmentSlot var1, ItemStack var2) {
      this.setItemSlot(â˜ƒ, â˜ƒ);
      this.setGuaranteedDrop(â˜ƒ);
      this.persistenceRequired = true;
   }

   public void setGuaranteedDrop(EquipmentSlot var1) {
      switch(â˜ƒ.getType()) {
         case HAND:
            this.handDropChances[â˜ƒ.getIndex()] = 2.0F;
            break;
         case ARMOR:
            this.armorDropChances[â˜ƒ.getIndex()] = 2.0F;
      }
   }

   protected boolean canReplaceCurrentItem(ItemStack var1, ItemStack var2) {
      if (â˜ƒ.isEmpty()) {
         return true;
      } else if (â˜ƒ.getItem() instanceof SwordItem) {
         if (!(â˜ƒ.getItem() instanceof SwordItem)) {
            return true;
         } else {
            SwordItem â˜ƒ = (SwordItem)â˜ƒ.getItem();
            SwordItem â˜ƒx = (SwordItem)â˜ƒ.getItem();
            if (â˜ƒ.getDamage() != â˜ƒx.getDamage()) {
               return â˜ƒ.getDamage() > â˜ƒx.getDamage();
            } else {
               return this.canReplaceEqualItem(â˜ƒ, â˜ƒ);
            }
         }
      } else if (â˜ƒ.getItem() instanceof BowItem && â˜ƒ.getItem() instanceof BowItem) {
         return this.canReplaceEqualItem(â˜ƒ, â˜ƒ);
      } else if (â˜ƒ.getItem() instanceof CrossbowItem && â˜ƒ.getItem() instanceof CrossbowItem) {
         return this.canReplaceEqualItem(â˜ƒ, â˜ƒ);
      } else if (â˜ƒ.getItem() instanceof ArmorItem) {
         if (EnchantmentHelper.hasBindingCurse(â˜ƒ)) {
            return false;
         } else if (!(â˜ƒ.getItem() instanceof ArmorItem)) {
            return true;
         } else {
            ArmorItem â˜ƒ = (ArmorItem)â˜ƒ.getItem();
            ArmorItem â˜ƒx = (ArmorItem)â˜ƒ.getItem();
            if (â˜ƒ.getDefense() != â˜ƒx.getDefense()) {
               return â˜ƒ.getDefense() > â˜ƒx.getDefense();
            } else if (â˜ƒ.getToughness() != â˜ƒx.getToughness()) {
               return â˜ƒ.getToughness() > â˜ƒx.getToughness();
            } else {
               return this.canReplaceEqualItem(â˜ƒ, â˜ƒ);
            }
         }
      } else {
         if (â˜ƒ.getItem() instanceof DiggerItem) {
            if (â˜ƒ.getItem() instanceof BlockItem) {
               return true;
            }

            if (â˜ƒ.getItem() instanceof DiggerItem) {
               DiggerItem â˜ƒ = (DiggerItem)â˜ƒ.getItem();
               DiggerItem â˜ƒx = (DiggerItem)â˜ƒ.getItem();
               if (â˜ƒ.getAttackDamage() != â˜ƒx.getAttackDamage()) {
                  return â˜ƒ.getAttackDamage() > â˜ƒx.getAttackDamage();
               }

               return this.canReplaceEqualItem(â˜ƒ, â˜ƒ);
            }
         }

         return false;
      }
   }

   public boolean canReplaceEqualItem(ItemStack var1, ItemStack var2) {
      if (â˜ƒ.getDamageValue() >= â˜ƒ.getDamageValue() && (!â˜ƒ.hasTag() || â˜ƒ.hasTag())) {
         if (â˜ƒ.hasTag() && â˜ƒ.hasTag()) {
            return â˜ƒ.getTag().getAllKeys().stream().anyMatch(var0 -> !var0.equals("Damage"))
               && !â˜ƒ.getTag().getAllKeys().stream().anyMatch(var0 -> !var0.equals("Damage"));
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public boolean canHoldItem(ItemStack var1) {
      return true;
   }

   public boolean wantsToPickUp(ItemStack var1) {
      return this.canHoldItem(â˜ƒ);
   }

   public boolean removeWhenFarAway(double var1) {
      return true;
   }

   public boolean requiresCustomPersistence() {
      return this.isPassenger();
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   @Override
   public void checkDespawn() {
      if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
         this.discard();
      } else if (!this.isPersistenceRequired() && !this.requiresCustomPersistence()) {
         Entity â˜ƒ = this.level.getNearestPlayer(this, -1.0);
         if (â˜ƒ != null) {
            double â˜ƒx = â˜ƒ.distanceToSqr(this);
            int â˜ƒxx = this.getType().getCategory().getDespawnDistance();
            int â˜ƒxxx = â˜ƒxx * â˜ƒxx;
            if (â˜ƒx > (double)â˜ƒxxx && this.removeWhenFarAway(â˜ƒx)) {
               this.discard();
            }

            int â˜ƒx = this.getType().getCategory().getNoDespawnDistance();
            int â˜ƒxx = â˜ƒx * â˜ƒx;
            if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && â˜ƒx > (double)â˜ƒxx && this.removeWhenFarAway(â˜ƒx)) {
               this.discard();
            } else if (â˜ƒx < (double)â˜ƒxx) {
               this.noActionTime = 0;
            }
         }
      } else {
         this.noActionTime = 0;
      }
   }

   @Override
   protected final void serverAiStep() {
      ++this.noActionTime;
      this.level.getProfiler().push("sensing");
      this.sensing.tick();
      this.level.getProfiler().pop();
      this.level.getProfiler().push("targetSelector");
      this.targetSelector.tick();
      this.level.getProfiler().pop();
      this.level.getProfiler().push("goalSelector");
      this.goalSelector.tick();
      this.level.getProfiler().pop();
      this.level.getProfiler().push("navigation");
      this.navigation.tick();
      this.level.getProfiler().pop();
      this.level.getProfiler().push("mob tick");
      this.customServerAiStep();
      this.level.getProfiler().pop();
      this.level.getProfiler().push("controls");
      this.level.getProfiler().push("move");
      this.moveControl.tick();
      this.level.getProfiler().popPush("look");
      this.lookControl.tick();
      this.level.getProfiler().popPush("jump");
      this.jumpControl.tick();
      this.level.getProfiler().pop();
      this.level.getProfiler().pop();
      this.sendDebugPackets();
   }

   protected void sendDebugPackets() {
      DebugPackets.sendGoalSelector(this.level, this, this.goalSelector);
   }

   protected void customServerAiStep() {
   }

   public int getMaxHeadXRot() {
      return 40;
   }

   public int getMaxHeadYRot() {
      return 75;
   }

   public int getHeadRotSpeed() {
      return 10;
   }

   public void lookAt(Entity var1, float var2, float var3) {
      double â˜ƒxx = â˜ƒ.getX() - this.getX();
      double â˜ƒxxx = â˜ƒ.getZ() - this.getZ();
      double â˜ƒx;
      if (â˜ƒ instanceof LivingEntity â˜ƒ) {
         â˜ƒx = â˜ƒ.getEyeY() - this.getEyeY();
      } else {
         â˜ƒx = (â˜ƒ.getBoundingBox().minY + â˜ƒ.getBoundingBox().maxY) / 2.0 - this.getEyeY();
      }

      double â˜ƒ = Math.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
      float â˜ƒx = (float)(Mth.atan2(â˜ƒxxx, â˜ƒxx) * 180.0F / (float)Math.PI) - 90.0F;
      float â˜ƒxx = (float)(-(Mth.atan2(â˜ƒx, â˜ƒ) * 180.0F / (float)Math.PI));
      this.setXRot(this.rotlerp(this.getXRot(), â˜ƒxx, â˜ƒ));
      this.setYRot(this.rotlerp(this.getYRot(), â˜ƒx, â˜ƒ));
   }

   private float rotlerp(float var1, float var2, float var3) {
      float â˜ƒ = Mth.wrapDegrees(â˜ƒ - â˜ƒ);
      if (â˜ƒ > â˜ƒ) {
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ < -â˜ƒ) {
         â˜ƒ = -â˜ƒ;
      }

      return â˜ƒ + â˜ƒ;
   }

   public static boolean checkMobSpawnRules(EntityType<? extends Mob> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      BlockPos â˜ƒ = â˜ƒ.below();
      return â˜ƒ == MobSpawnType.SPAWNER || â˜ƒ.getBlockState(â˜ƒ).isValidSpawn(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean checkSpawnRules(LevelAccessor var1, MobSpawnType var2) {
      return true;
   }

   public boolean checkSpawnObstruction(LevelReader var1) {
      return !â˜ƒ.containsAnyLiquid(this.getBoundingBox()) && â˜ƒ.isUnobstructed(this);
   }

   public int getMaxSpawnClusterSize() {
      return 4;
   }

   public boolean isMaxGroupSizeReached(int var1) {
      return false;
   }

   @Override
   public int getMaxFallDistance() {
      if (this.getTarget() == null) {
         return 3;
      } else {
         int â˜ƒ = (int)(this.getHealth() - this.getMaxHealth() * 0.33F);
         â˜ƒ -= (3 - this.level.getDifficulty().getId()) * 4;
         if (â˜ƒ < 0) {
            â˜ƒ = 0;
         }

         return â˜ƒ + 3;
      }
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
            this.handItems.set(â˜ƒ.getIndex(), â˜ƒ);
            break;
         case ARMOR:
            this.armorItems.set(â˜ƒ.getIndex(), â˜ƒ);
      }
   }

   @Override
   protected void dropCustomDeathLoot(DamageSource var1, int var2, boolean var3) {
      super.dropCustomDeathLoot(â˜ƒ, â˜ƒ, â˜ƒ);

      for(EquipmentSlot â˜ƒ : EquipmentSlot.values()) {
         ItemStack â˜ƒx = this.getItemBySlot(â˜ƒ);
         float â˜ƒxx = this.getEquipmentDropChance(â˜ƒ);
         boolean â˜ƒxxx = â˜ƒxx > 1.0F;
         if (!â˜ƒx.isEmpty()
            && !EnchantmentHelper.hasVanishingCurse(â˜ƒx)
            && (â˜ƒ || â˜ƒxxx)
            && Math.max(this.random.nextFloat() - (float)â˜ƒ * 0.01F, 0.0F) < â˜ƒxx) {
            if (!â˜ƒxxx && â˜ƒx.isDamageableItem()) {
               â˜ƒx.setDamageValue(â˜ƒx.getMaxDamage() - this.random.nextInt(1 + this.random.nextInt(Math.max(â˜ƒx.getMaxDamage() - 3, 1))));
            }

            this.spawnAtLocation(â˜ƒx);
            this.setItemSlot(â˜ƒ, ItemStack.EMPTY);
         }
      }
   }

   protected float getEquipmentDropChance(EquipmentSlot var1) {
      return switch(â˜ƒ.getType()) {
         case HAND -> this.handDropChances[â˜ƒ.getIndex()];
         case ARMOR -> this.armorDropChances[â˜ƒ.getIndex()];
         default -> 0.0F;
      };
   }

   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      if (this.random.nextFloat() < 0.15F * â˜ƒ.getSpecialMultiplier()) {
         int â˜ƒ = this.random.nextInt(2);
         float â˜ƒx = this.level.getDifficulty() == Difficulty.HARD ? 0.1F : 0.25F;
         if (this.random.nextFloat() < 0.095F) {
            ++â˜ƒ;
         }

         if (this.random.nextFloat() < 0.095F) {
            ++â˜ƒ;
         }

         if (this.random.nextFloat() < 0.095F) {
            ++â˜ƒ;
         }

         boolean â˜ƒ = true;

         for(EquipmentSlot â˜ƒx : EquipmentSlot.values()) {
            if (â˜ƒx.getType() == EquipmentSlot.Type.ARMOR) {
               ItemStack â˜ƒxx = this.getItemBySlot(â˜ƒx);
               if (!â˜ƒ && this.random.nextFloat() < â˜ƒx) {
                  break;
               }

               â˜ƒ = false;
               if (â˜ƒxx.isEmpty()) {
                  Item â˜ƒxx = getEquipmentForSlot(â˜ƒx, â˜ƒ);
                  if (â˜ƒxx != null) {
                     this.setItemSlot(â˜ƒx, new ItemStack(â˜ƒxx));
                  }
               }
            }
         }
      }
   }

   @Nullable
   public static Item getEquipmentForSlot(EquipmentSlot var0, int var1) {
      switch(â˜ƒ) {
         case HEAD:
            if (â˜ƒ == 0) {
               return Items.LEATHER_HELMET;
            } else if (â˜ƒ == 1) {
               return Items.GOLDEN_HELMET;
            } else if (â˜ƒ == 2) {
               return Items.CHAINMAIL_HELMET;
            } else if (â˜ƒ == 3) {
               return Items.IRON_HELMET;
            } else if (â˜ƒ == 4) {
               return Items.DIAMOND_HELMET;
            }
         case CHEST:
            if (â˜ƒ == 0) {
               return Items.LEATHER_CHESTPLATE;
            } else if (â˜ƒ == 1) {
               return Items.GOLDEN_CHESTPLATE;
            } else if (â˜ƒ == 2) {
               return Items.CHAINMAIL_CHESTPLATE;
            } else if (â˜ƒ == 3) {
               return Items.IRON_CHESTPLATE;
            } else if (â˜ƒ == 4) {
               return Items.DIAMOND_CHESTPLATE;
            }
         case LEGS:
            if (â˜ƒ == 0) {
               return Items.LEATHER_LEGGINGS;
            } else if (â˜ƒ == 1) {
               return Items.GOLDEN_LEGGINGS;
            } else if (â˜ƒ == 2) {
               return Items.CHAINMAIL_LEGGINGS;
            } else if (â˜ƒ == 3) {
               return Items.IRON_LEGGINGS;
            } else if (â˜ƒ == 4) {
               return Items.DIAMOND_LEGGINGS;
            }
         case FEET:
            if (â˜ƒ == 0) {
               return Items.LEATHER_BOOTS;
            } else if (â˜ƒ == 1) {
               return Items.GOLDEN_BOOTS;
            } else if (â˜ƒ == 2) {
               return Items.CHAINMAIL_BOOTS;
            } else if (â˜ƒ == 3) {
               return Items.IRON_BOOTS;
            } else if (â˜ƒ == 4) {
               return Items.DIAMOND_BOOTS;
            }
         default:
            return null;
      }
   }

   protected void populateDefaultEquipmentEnchantments(DifficultyInstance var1) {
      float â˜ƒ = â˜ƒ.getSpecialMultiplier();
      this.enchantSpawnedWeapon(â˜ƒ);

      for(EquipmentSlot â˜ƒx : EquipmentSlot.values()) {
         if (â˜ƒx.getType() == EquipmentSlot.Type.ARMOR) {
            this.enchantSpawnedArmor(â˜ƒ, â˜ƒx);
         }
      }
   }

   protected void enchantSpawnedWeapon(float var1) {
      if (!this.getMainHandItem().isEmpty() && this.random.nextFloat() < 0.25F * â˜ƒ) {
         this.setItemSlot(
            EquipmentSlot.MAINHAND,
            EnchantmentHelper.enchantItem(this.random, this.getMainHandItem(), (int)(5.0F + â˜ƒ * (float)this.random.nextInt(18)), false)
         );
      }
   }

   protected void enchantSpawnedArmor(float var1, EquipmentSlot var2) {
      ItemStack â˜ƒ = this.getItemBySlot(â˜ƒ);
      if (!â˜ƒ.isEmpty() && this.random.nextFloat() < 0.5F * â˜ƒ) {
         this.setItemSlot(â˜ƒ, EnchantmentHelper.enchantItem(this.random, â˜ƒ, (int)(5.0F + â˜ƒ * (float)this.random.nextInt(18)), false));
      }
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.getAttribute(Attributes.FOLLOW_RANGE)
         .addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
      if (this.random.nextFloat() < 0.05F) {
         this.setLeftHanded(true);
      } else {
         this.setLeftHanded(false);
      }

      return â˜ƒ;
   }

   public boolean canBeControlledByRider() {
      return false;
   }

   public void setPersistenceRequired() {
      this.persistenceRequired = true;
   }

   public void setDropChance(EquipmentSlot var1, float var2) {
      switch(â˜ƒ.getType()) {
         case HAND:
            this.handDropChances[â˜ƒ.getIndex()] = â˜ƒ;
            break;
         case ARMOR:
            this.armorDropChances[â˜ƒ.getIndex()] = â˜ƒ;
      }
   }

   public boolean canPickUpLoot() {
      return this.canPickUpLoot;
   }

   public void setCanPickUpLoot(boolean var1) {
      this.canPickUpLoot = â˜ƒ;
   }

   @Override
   public boolean canTakeItem(ItemStack var1) {
      EquipmentSlot â˜ƒ = getEquipmentSlotForItem(â˜ƒ);
      return this.getItemBySlot(â˜ƒ).isEmpty() && this.canPickUpLoot();
   }

   public boolean isPersistenceRequired() {
      return this.persistenceRequired;
   }

   @Override
   public final InteractionResult interact(Player var1, InteractionHand var2) {
      if (!this.isAlive()) {
         return InteractionResult.PASS;
      } else if (this.getLeashHolder() == â˜ƒ) {
         this.dropLeash(true, !â˜ƒ.getAbilities().instabuild);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         InteractionResult â˜ƒ = this.checkAndHandleImportantInteractions(â˜ƒ, â˜ƒ);
         if (â˜ƒ.consumesAction()) {
            return â˜ƒ;
         } else {
            â˜ƒ = this.mobInteract(â˜ƒ, â˜ƒ);
            return â˜ƒ.consumesAction() ? â˜ƒ : super.interact(â˜ƒ, â˜ƒ);
         }
      }
   }

   private InteractionResult checkAndHandleImportantInteractions(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.LEAD) && this.canBeLeashed(â˜ƒ)) {
         this.setLeashedTo(â˜ƒ, true);
         â˜ƒ.shrink(1);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         if (â˜ƒ.is(Items.NAME_TAG)) {
            InteractionResult â˜ƒ = â˜ƒ.interactLivingEntity(â˜ƒ, this, â˜ƒ);
            if (â˜ƒ.consumesAction()) {
               return â˜ƒ;
            }
         }

         if (â˜ƒ.getItem() instanceof SpawnEggItem) {
            if (this.level instanceof ServerLevel) {
               SpawnEggItem â˜ƒ = (SpawnEggItem)â˜ƒ.getItem();
               Optional<Mob> â˜ƒx = â˜ƒ.spawnOffspringFromSpawnEgg(â˜ƒ, this, this.getType(), (ServerLevel)this.level, this.position(), â˜ƒ);
               â˜ƒx.ifPresent(var2x -> this.onOffspringSpawnedFromEgg(â˜ƒ, var2x));
               return â˜ƒx.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else {
               return InteractionResult.CONSUME;
            }
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   protected void onOffspringSpawnedFromEgg(Player var1, Mob var2) {
   }

   protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
      return InteractionResult.PASS;
   }

   public boolean isWithinRestriction() {
      return this.isWithinRestriction(this.blockPosition());
   }

   public boolean isWithinRestriction(BlockPos var1) {
      if (this.restrictRadius == -1.0F) {
         return true;
      } else {
         return this.restrictCenter.distSqr(â˜ƒ) < (double)(this.restrictRadius * this.restrictRadius);
      }
   }

   public void restrictTo(BlockPos var1, int var2) {
      this.restrictCenter = â˜ƒ;
      this.restrictRadius = (float)â˜ƒ;
   }

   public BlockPos getRestrictCenter() {
      return this.restrictCenter;
   }

   public float getRestrictRadius() {
      return this.restrictRadius;
   }

   public void clearRestriction() {
      this.restrictRadius = -1.0F;
   }

   public boolean hasRestriction() {
      return this.restrictRadius != -1.0F;
   }

   @Nullable
   public <T extends Mob> T convertTo(EntityType<T> var1, boolean var2) {
      if (this.isRemoved()) {
         return null;
      } else {
         T â˜ƒ = â˜ƒ.create(this.level);
         â˜ƒ.copyPosition(this);
         â˜ƒ.setBaby(this.isBaby());
         â˜ƒ.setNoAi(this.isNoAi());
         if (this.hasCustomName()) {
            â˜ƒ.setCustomName(this.getCustomName());
            â˜ƒ.setCustomNameVisible(this.isCustomNameVisible());
         }

         if (this.isPersistenceRequired()) {
            â˜ƒ.setPersistenceRequired();
         }

         â˜ƒ.setInvulnerable(this.isInvulnerable());
         if (â˜ƒ) {
            â˜ƒ.setCanPickUpLoot(this.canPickUpLoot());

            for(EquipmentSlot â˜ƒ : EquipmentSlot.values()) {
               ItemStack â˜ƒx = this.getItemBySlot(â˜ƒ);
               if (!â˜ƒx.isEmpty()) {
                  â˜ƒ.setItemSlot(â˜ƒ, â˜ƒx.copy());
                  â˜ƒ.setDropChance(â˜ƒ, this.getEquipmentDropChance(â˜ƒ));
                  â˜ƒx.setCount(0);
               }
            }
         }

         this.level.addFreshEntity(â˜ƒ);
         if (this.isPassenger()) {
            Entity â˜ƒ = this.getVehicle();
            this.stopRiding();
            â˜ƒ.startRiding(â˜ƒ, true);
         }

         this.discard();
         return â˜ƒ;
      }
   }

   protected void tickLeash() {
      if (this.leashInfoTag != null) {
         this.restoreLeashFromSave();
      }

      if (this.leashHolder != null) {
         if (!this.isAlive() || !this.leashHolder.isAlive()) {
            this.dropLeash(true, true);
         }
      }
   }

   public void dropLeash(boolean var1, boolean var2) {
      if (this.leashHolder != null) {
         this.leashHolder = null;
         this.leashInfoTag = null;
         if (!this.level.isClientSide && â˜ƒ) {
            this.spawnAtLocation(Items.LEAD);
         }

         if (!this.level.isClientSide && â˜ƒ && this.level instanceof ServerLevel) {
            ((ServerLevel)this.level).getChunkSource().broadcast(this, new ClientboundSetEntityLinkPacket(this, null));
         }
      }
   }

   public boolean canBeLeashed(Player var1) {
      return !this.isLeashed() && !(this instanceof Enemy);
   }

   public boolean isLeashed() {
      return this.leashHolder != null;
   }

   @Nullable
   public Entity getLeashHolder() {
      if (this.leashHolder == null && this.delayedLeashHolderId != 0 && this.level.isClientSide) {
         this.leashHolder = this.level.getEntity(this.delayedLeashHolderId);
      }

      return this.leashHolder;
   }

   public void setLeashedTo(Entity var1, boolean var2) {
      this.leashHolder = â˜ƒ;
      this.leashInfoTag = null;
      if (!this.level.isClientSide && â˜ƒ && this.level instanceof ServerLevel) {
         ((ServerLevel)this.level).getChunkSource().broadcast(this, new ClientboundSetEntityLinkPacket(this, this.leashHolder));
      }

      if (this.isPassenger()) {
         this.stopRiding();
      }
   }

   public void setDelayedLeashHolderId(int var1) {
      this.delayedLeashHolderId = â˜ƒ;
      this.dropLeash(false, false);
   }

   @Override
   public boolean startRiding(Entity var1, boolean var2) {
      boolean â˜ƒ = super.startRiding(â˜ƒ, â˜ƒ);
      if (â˜ƒ && this.isLeashed()) {
         this.dropLeash(true, true);
      }

      return â˜ƒ;
   }

   private void restoreLeashFromSave() {
      if (this.leashInfoTag != null && this.level instanceof ServerLevel) {
         if (this.leashInfoTag.hasUUID("UUID")) {
            UUID â˜ƒ = this.leashInfoTag.getUUID("UUID");
            Entity â˜ƒx = ((ServerLevel)this.level).getEntity(â˜ƒ);
            if (â˜ƒx != null) {
               this.setLeashedTo(â˜ƒx, true);
               return;
            }
         } else if (this.leashInfoTag.contains("X", 99) && this.leashInfoTag.contains("Y", 99) && this.leashInfoTag.contains("Z", 99)) {
            BlockPos â˜ƒ = new BlockPos(this.leashInfoTag.getInt("X"), this.leashInfoTag.getInt("Y"), this.leashInfoTag.getInt("Z"));
            this.setLeashedTo(LeashFenceKnotEntity.getOrCreateKnot(this.level, â˜ƒ), true);
            return;
         }

         if (this.tickCount > 100) {
            this.spawnAtLocation(Items.LEAD);
            this.leashInfoTag = null;
         }
      }
   }

   @Override
   public boolean isControlledByLocalInstance() {
      return this.canBeControlledByRider() && super.isControlledByLocalInstance();
   }

   @Override
   public boolean isEffectiveAi() {
      return super.isEffectiveAi() && !this.isNoAi();
   }

   public void setNoAi(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_MOB_FLAGS_ID);
      this.entityData.set(DATA_MOB_FLAGS_ID, â˜ƒ ? (byte)(â˜ƒ | 1) : (byte)(â˜ƒ & -2));
   }

   public void setLeftHanded(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_MOB_FLAGS_ID);
      this.entityData.set(DATA_MOB_FLAGS_ID, â˜ƒ ? (byte)(â˜ƒ | 2) : (byte)(â˜ƒ & -3));
   }

   public void setAggressive(boolean var1) {
      byte â˜ƒ = this.entityData.get(DATA_MOB_FLAGS_ID);
      this.entityData.set(DATA_MOB_FLAGS_ID, â˜ƒ ? (byte)(â˜ƒ | 4) : (byte)(â˜ƒ & -5));
   }

   public boolean isNoAi() {
      return (this.entityData.get(DATA_MOB_FLAGS_ID) & 1) != 0;
   }

   public boolean isLeftHanded() {
      return (this.entityData.get(DATA_MOB_FLAGS_ID) & 2) != 0;
   }

   public boolean isAggressive() {
      return (this.entityData.get(DATA_MOB_FLAGS_ID) & 4) != 0;
   }

   public void setBaby(boolean var1) {
   }

   @Override
   public HumanoidArm getMainArm() {
      return this.isLeftHanded() ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   public double getMeleeAttackRangeSqr(LivingEntity var1) {
      return (double)(this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + â˜ƒ.getBbWidth());
   }

   @Override
   public boolean doHurtTarget(Entity var1) {
      float â˜ƒ = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
      float â˜ƒx = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
      if (â˜ƒ instanceof LivingEntity) {
         â˜ƒ += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)â˜ƒ).getMobType());
         â˜ƒx += (float)EnchantmentHelper.getKnockbackBonus(this);
      }

      int â˜ƒ = EnchantmentHelper.getFireAspect(this);
      if (â˜ƒ > 0) {
         â˜ƒ.setSecondsOnFire(â˜ƒ * 4);
      }

      boolean â˜ƒ = â˜ƒ.hurt(DamageSource.mobAttack(this), â˜ƒ);
      if (â˜ƒ) {
         if (â˜ƒx > 0.0F && â˜ƒ instanceof LivingEntity) {
            ((LivingEntity)â˜ƒ)
               .knockback(
                  (double)(â˜ƒx * 0.5F),
                  (double)Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)),
                  (double)(-Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)))
               );
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
         }

         if (â˜ƒ instanceof Player â˜ƒx) {
            this.maybeDisableShield(â˜ƒx, this.getMainHandItem(), â˜ƒx.isUsingItem() ? â˜ƒx.getUseItem() : ItemStack.EMPTY);
         }

         this.doEnchantDamageEffects(this, â˜ƒ);
         this.setLastHurtMob(â˜ƒ);
      }

      return â˜ƒ;
   }

   private void maybeDisableShield(Player var1, ItemStack var2, ItemStack var3) {
      if (!â˜ƒ.isEmpty() && !â˜ƒ.isEmpty() && â˜ƒ.getItem() instanceof AxeItem && â˜ƒ.is(Items.SHIELD)) {
         float â˜ƒ = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
         if (this.random.nextFloat() < â˜ƒ) {
            â˜ƒ.getCooldowns().addCooldown(Items.SHIELD, 100);
            this.level.broadcastEntityEvent(â˜ƒ, (byte)30);
         }
      }
   }

   protected boolean isSunBurnTick() {
      if (this.level.isDay() && !this.level.isClientSide) {
         float â˜ƒ = this.getBrightness();
         BlockPos â˜ƒx = new BlockPos(this.getX(), this.getEyeY(), this.getZ());
         boolean â˜ƒxx = this.isInWaterRainOrBubble() || this.isInPowderSnow || this.wasInPowderSnow;
         if (â˜ƒ > 0.5F && this.random.nextFloat() * 30.0F < (â˜ƒ - 0.4F) * 2.0F && !â˜ƒxx && this.level.canSeeSky(â˜ƒx)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void jumpInLiquid(Tag<Fluid> var1) {
      if (this.getNavigation().canFloat()) {
         super.jumpInLiquid(â˜ƒ);
      } else {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.3, 0.0));
      }
   }

   public void removeFreeWill() {
      this.goalSelector.removeAllGoals();
      this.getBrain().removeAllBehaviors();
   }

   @Override
   protected void removeAfterChangingDimensions() {
      super.removeAfterChangingDimensions();
      this.dropLeash(true, false);
      this.getAllSlots().forEach(var0 -> var0.setCount(0));
   }

   @Nullable
   @Override
   public ItemStack getPickResult() {
      SpawnEggItem â˜ƒ = SpawnEggItem.byId(this.getType());
      return â˜ƒ == null ? null : new ItemStack(â˜ƒ);
   }
}
