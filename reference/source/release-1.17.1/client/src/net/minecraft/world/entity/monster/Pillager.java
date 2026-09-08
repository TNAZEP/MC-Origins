package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Pillager extends AbstractIllager implements CrossbowAttackMob, InventoryCarrier {
   private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Pillager.class, EntityDataSerializers.BOOLEAN);
   private static final int INVENTORY_SIZE = 5;
   private static final int SLOT_OFFSET = 300;
   private static final float CROSSBOW_POWER = 1.6F;
   private final SimpleContainer inventory = new SimpleContainer(5);

   public Pillager(EntityType<? extends Pillager> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(2, new Raider.HoldGroundAttackGoal(this, 10.0F));
      this.goalSelector.addGoal(3, new RangedCrossbowAttackGoal<>(this, 1.0, 8.0F));
      this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
      this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MOVEMENT_SPEED, 0.35F)
         .add(Attributes.MAX_HEALTH, 24.0)
         .add(Attributes.ATTACK_DAMAGE, 5.0)
         .add(Attributes.FOLLOW_RANGE, 32.0);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(IS_CHARGING_CROSSBOW, false);
   }

   @Override
   public boolean canFireProjectileWeapon(ProjectileWeaponItem var1) {
      return â˜ƒ == Items.CROSSBOW;
   }

   public boolean isChargingCrossbow() {
      return this.entityData.get(IS_CHARGING_CROSSBOW);
   }

   @Override
   public void setChargingCrossbow(boolean var1) {
      this.entityData.set(IS_CHARGING_CROSSBOW, â˜ƒ);
   }

   @Override
   public void onCrossbowAttackPerformed() {
      this.noActionTime = 0;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      ListTag â˜ƒ = new ListTag();

      for(int â˜ƒx = 0; â˜ƒx < this.inventory.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = this.inventory.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.add(â˜ƒxx.save(new CompoundTag()));
         }
      }

      â˜ƒ.put("Inventory", â˜ƒ);
   }

   @Override
   public AbstractIllager.IllagerArmPose getArmPose() {
      if (this.isChargingCrossbow()) {
         return AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE;
      } else if (this.isHolding(Items.CROSSBOW)) {
         return AbstractIllager.IllagerArmPose.CROSSBOW_HOLD;
      } else {
         return this.isAggressive() ? AbstractIllager.IllagerArmPose.ATTACKING : AbstractIllager.IllagerArmPose.NEUTRAL;
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      ListTag â˜ƒ = â˜ƒ.getList("Inventory", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         ItemStack â˜ƒxx = ItemStack.of(â˜ƒ.getCompound(â˜ƒx));
         if (!â˜ƒxx.isEmpty()) {
            this.inventory.addItem(â˜ƒxx);
         }
      }

      this.setCanPickUpLoot(true);
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      return !â˜ƒ.is(Blocks.GRASS_BLOCK) && !â˜ƒ.is(Blocks.SAND) ? 0.5F - â˜ƒ.getBrightness(â˜ƒ) : 10.0F;
   }

   @Override
   public int getMaxSpawnClusterSize() {
      return 1;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.populateDefaultEquipmentSlots(â˜ƒ);
      this.populateDefaultEquipmentEnchantments(â˜ƒ);
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void populateDefaultEquipmentSlots(DifficultyInstance var1) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
   }

   @Override
   protected void enchantSpawnedWeapon(float var1) {
      super.enchantSpawnedWeapon(â˜ƒ);
      if (this.random.nextInt(300) == 0) {
         ItemStack â˜ƒ = this.getMainHandItem();
         if (â˜ƒ.is(Items.CROSSBOW)) {
            Map<Enchantment, Integer> â˜ƒx = EnchantmentHelper.getEnchantments(â˜ƒ);
            â˜ƒx.putIfAbsent(Enchantments.PIERCING, 1);
            EnchantmentHelper.setEnchantments(â˜ƒx, â˜ƒ);
            this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
         }
      }
   }

   @Override
   public boolean isAlliedTo(Entity var1) {
      if (super.isAlliedTo(â˜ƒ)) {
         return true;
      } else if (â˜ƒ instanceof LivingEntity && ((LivingEntity)â˜ƒ).getMobType() == MobType.ILLAGER) {
         return this.getTeam() == null && â˜ƒ.getTeam() == null;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.PILLAGER_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.PILLAGER_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.PILLAGER_HURT;
   }

   @Override
   public void performRangedAttack(LivingEntity var1, float var2) {
      this.performCrossbowAttack(this, 1.6F);
   }

   @Override
   public void shootCrossbowProjectile(LivingEntity var1, ItemStack var2, Projectile var3, float var4) {
      this.shootCrossbowProjectile(this, â˜ƒ, â˜ƒ, â˜ƒ, 1.6F);
   }

   @Override
   public Container getInventory() {
      return this.inventory;
   }

   @Override
   protected void pickUpItem(ItemEntity var1) {
      ItemStack â˜ƒ = â˜ƒ.getItem();
      if (â˜ƒ.getItem() instanceof BannerItem) {
         super.pickUpItem(â˜ƒ);
      } else if (this.wantsItem(â˜ƒ)) {
         this.onItemPickup(â˜ƒ);
         ItemStack â˜ƒ = this.inventory.addItem(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            â˜ƒ.discard();
         } else {
            â˜ƒ.setCount(â˜ƒ.getCount());
         }
      }
   }

   private boolean wantsItem(ItemStack var1) {
      return this.hasActiveRaid() && â˜ƒ.is(Items.WHITE_BANNER);
   }

   @Override
   public SlotAccess getSlot(int var1) {
      int â˜ƒ = â˜ƒ - 300;
      return â˜ƒ >= 0 && â˜ƒ < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, â˜ƒ) : super.getSlot(â˜ƒ);
   }

   @Override
   public void applyRaidBuffs(int var1, boolean var2) {
      Raid â˜ƒ = this.getCurrentRaid();
      boolean â˜ƒx = this.random.nextFloat() <= â˜ƒ.getEnchantOdds();
      if (â˜ƒx) {
         ItemStack â˜ƒxx = new ItemStack(Items.CROSSBOW);
         Map<Enchantment, Integer> â˜ƒxxx = Maps.newHashMap();
         if (â˜ƒ > â˜ƒ.getNumGroups(Difficulty.NORMAL)) {
            â˜ƒxxx.put(Enchantments.QUICK_CHARGE, 2);
         } else if (â˜ƒ > â˜ƒ.getNumGroups(Difficulty.EASY)) {
            â˜ƒxxx.put(Enchantments.QUICK_CHARGE, 1);
         }

         â˜ƒxxx.put(Enchantments.MULTISHOT, 1);
         EnchantmentHelper.setEnchantments(â˜ƒxxx, â˜ƒxx);
         this.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒxx);
      }
   }

   @Override
   public SoundEvent getCelebrateSound() {
      return SoundEvents.PILLAGER_CELEBRATE;
   }
}
