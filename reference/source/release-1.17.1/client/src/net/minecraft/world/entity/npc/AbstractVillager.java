package net.minecraft.world.entity.npc;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractVillager extends AgeableMob implements InventoryCarrier, Npc, Merchant {
   private static final EntityDataAccessor<Integer> DATA_UNHAPPY_COUNTER = SynchedEntityData.defineId(AbstractVillager.class, EntityDataSerializers.INT);
   public static final int VILLAGER_SLOT_OFFSET = 300;
   private static final int VILLAGER_INVENTORY_SIZE = 8;
   @Nullable
   private Player tradingPlayer;
   @Nullable
   protected MerchantOffers offers;
   private final SimpleContainer inventory = new SimpleContainer(8);

   public AbstractVillager(EntityType<? extends AbstractVillager> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
      this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
   }

   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      if (â˜ƒ == null) {
         â˜ƒ = new AgeableMob.AgeableMobGroupData(false);
      }

      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public int getUnhappyCounter() {
      return this.entityData.get(DATA_UNHAPPY_COUNTER);
   }

   public void setUnhappyCounter(int var1) {
      this.entityData.set(DATA_UNHAPPY_COUNTER, â˜ƒ);
   }

   @Override
   public int getVillagerXp() {
      return 0;
   }

   @Override
   protected float getStandingEyeHeight(Pose var1, EntityDimensions var2) {
      return this.isBaby() ? 0.81F : 1.62F;
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_UNHAPPY_COUNTER, 0);
   }

   @Override
   public void setTradingPlayer(@Nullable Player var1) {
      this.tradingPlayer = â˜ƒ;
   }

   @Nullable
   @Override
   public Player getTradingPlayer() {
      return this.tradingPlayer;
   }

   public boolean isTrading() {
      return this.tradingPlayer != null;
   }

   @Override
   public MerchantOffers getOffers() {
      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.updateTrades();
      }

      return this.offers;
   }

   @Override
   public void overrideOffers(@Nullable MerchantOffers var1) {
   }

   @Override
   public void overrideXp(int var1) {
   }

   @Override
   public void notifyTrade(MerchantOffer var1) {
      â˜ƒ.increaseUses();
      this.ambientSoundTime = -this.getAmbientSoundInterval();
      this.rewardTradeXp(â˜ƒ);
      if (this.tradingPlayer instanceof ServerPlayer) {
         CriteriaTriggers.TRADE.trigger((ServerPlayer)this.tradingPlayer, this, â˜ƒ.getResult());
      }
   }

   protected abstract void rewardTradeXp(MerchantOffer var1);

   @Override
   public boolean showProgressBar() {
      return true;
   }

   @Override
   public void notifyTradeUpdated(ItemStack var1) {
      if (!this.level.isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
         this.ambientSoundTime = -this.getAmbientSoundInterval();
         this.playSound(this.getTradeUpdatedSound(!â˜ƒ.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
      }
   }

   @Override
   public SoundEvent getNotifyTradeSound() {
      return SoundEvents.VILLAGER_YES;
   }

   protected SoundEvent getTradeUpdatedSound(boolean var1) {
      return â˜ƒ ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
   }

   public void playCelebrateSound() {
      this.playSound(SoundEvents.VILLAGER_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      MerchantOffers â˜ƒ = this.getOffers();
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.put("Offers", â˜ƒ.createTag());
      }

      â˜ƒ.put("Inventory", this.inventory.createTag());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("Offers", 10)) {
         this.offers = new MerchantOffers(â˜ƒ.getCompound("Offers"));
      }

      this.inventory.fromTag(â˜ƒ.getList("Inventory", 10));
   }

   @Nullable
   @Override
   public Entity changeDimension(ServerLevel var1) {
      this.stopTrading();
      return super.changeDimension(â˜ƒ);
   }

   protected void stopTrading() {
      this.setTradingPlayer(null);
   }

   @Override
   public void die(DamageSource var1) {
      super.die(â˜ƒ);
      this.stopTrading();
   }

   protected void addParticlesAroundSelf(ParticleOptions var1) {
      for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
         double â˜ƒx = this.random.nextGaussian() * 0.02;
         double â˜ƒxx = this.random.nextGaussian() * 0.02;
         double â˜ƒxxx = this.random.nextGaussian() * 0.02;
         this.level.addParticle(â˜ƒ, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   @Override
   public boolean canBeLeashed(Player var1) {
      return false;
   }

   public SimpleContainer getInventory() {
      return this.inventory;
   }

   @Override
   public SlotAccess getSlot(int var1) {
      int â˜ƒ = â˜ƒ - 300;
      return â˜ƒ >= 0 && â˜ƒ < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, â˜ƒ) : super.getSlot(â˜ƒ);
   }

   @Override
   public Level getLevel() {
      return this.level;
   }

   protected abstract void updateTrades();

   protected void addOffersFromItemListings(MerchantOffers var1, VillagerTrades.ItemListing[] var2, int var3) {
      Set<Integer> â˜ƒ = Sets.newHashSet();
      if (â˜ƒ.length > â˜ƒ) {
         while(â˜ƒ.size() < â˜ƒ) {
            â˜ƒ.add(this.random.nextInt(â˜ƒ.length));
         }
      } else {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
            â˜ƒ.add(â˜ƒ);
         }
      }

      for(Integer â˜ƒ : â˜ƒ) {
         VillagerTrades.ItemListing â˜ƒx = â˜ƒ[â˜ƒ];
         MerchantOffer â˜ƒxx = â˜ƒx.getOffer(this, this.random);
         if (â˜ƒxx != null) {
            â˜ƒ.add(â˜ƒxx);
         }
      }
   }

   @Override
   public Vec3 getRopeHoldPosition(float var1) {
      float â˜ƒ = Mth.lerp(â˜ƒ, this.yBodyRotO, this.yBodyRot) * (float) (Math.PI / 180.0);
      Vec3 â˜ƒx = new Vec3(0.0, this.getBoundingBox().getYsize() - 1.0, 0.2);
      return this.getPosition(â˜ƒ).add(â˜ƒx.yRot(-â˜ƒ));
   }
}
