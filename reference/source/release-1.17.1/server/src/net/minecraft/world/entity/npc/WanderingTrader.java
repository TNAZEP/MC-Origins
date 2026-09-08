package net.minecraft.world.entity.npc;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.InteractGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.ai.goal.UseItemGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WanderingTrader extends AbstractVillager {
   private static final int NUMBER_OF_TRADE_OFFERS = 5;
   @Nullable
   private BlockPos wanderTarget;
   private int despawnDelay;

   public WanderingTrader(EntityType<? extends WanderingTrader> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector
         .addGoal(
            0,
            new UseItemGoal<>(
               this,
               PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY),
               SoundEvents.WANDERING_TRADER_DISAPPEARED,
               var1 -> this.level.isNight() && !var1.isInvisible()
            )
         );
      this.goalSelector
         .addGoal(
            0,
            new UseItemGoal<>(this, new ItemStack(Items.MILK_BUCKET), SoundEvents.WANDERING_TRADER_REAPPEARED, var1 -> this.level.isDay() && var1.isInvisible())
         );
      this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Zombie.class, 8.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Evoker.class, 12.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Vindicator.class, 8.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Vex.class, 8.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Pillager.class, 15.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Illusioner.class, 12.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new AvoidEntityGoal(this, Zoglin.class, 10.0F, 0.5, 0.5));
      this.goalSelector.addGoal(1, new PanicGoal(this, 0.5));
      this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
      this.goalSelector.addGoal(2, new WanderingTrader.WanderToPositionGoal(this, 2.0, 0.35));
      this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35));
      this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35));
      this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
   }

   @Nullable
   @Override
   public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      return null;
   }

   @Override
   public boolean showProgressBar() {
      return false;
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!â˜ƒ.is(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
         if (â˜ƒ == InteractionHand.MAIN_HAND) {
            â˜ƒ.awardStat(Stats.TALKED_TO_VILLAGER);
         }

         if (this.getOffers().isEmpty()) {
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         } else {
            if (!this.level.isClientSide) {
               this.setTradingPlayer(â˜ƒ);
               this.openTradingScreen(â˜ƒ, this.getDisplayName(), 1);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }
      } else {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void updateTrades() {
      VillagerTrades.ItemListing[] â˜ƒ = (VillagerTrades.ItemListing[])VillagerTrades.WANDERING_TRADER_TRADES.get(1);
      VillagerTrades.ItemListing[] â˜ƒx = (VillagerTrades.ItemListing[])VillagerTrades.WANDERING_TRADER_TRADES.get(2);
      if (â˜ƒ != null && â˜ƒx != null) {
         MerchantOffers â˜ƒxx = this.getOffers();
         this.addOffersFromItemListings(â˜ƒxx, â˜ƒ, 5);
         int â˜ƒxxx = this.random.nextInt(â˜ƒx.length);
         VillagerTrades.ItemListing â˜ƒxxxx = â˜ƒx[â˜ƒxxx];
         MerchantOffer â˜ƒxxxxx = â˜ƒxxxx.getOffer(this, this.random);
         if (â˜ƒxxxxx != null) {
            â˜ƒxx.add(â˜ƒxxxxx);
         }
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("DespawnDelay", this.despawnDelay);
      if (this.wanderTarget != null) {
         â˜ƒ.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("DespawnDelay", 99)) {
         this.despawnDelay = â˜ƒ.getInt("DespawnDelay");
      }

      if (â˜ƒ.contains("WanderTarget")) {
         this.wanderTarget = NbtUtils.readBlockPos(â˜ƒ.getCompound("WanderTarget"));
      }

      this.setAge(Math.max(0, this.getAge()));
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return false;
   }

   @Override
   protected void rewardTradeXp(MerchantOffer var1) {
      if (â˜ƒ.shouldRewardExp()) {
         int â˜ƒ = 3 + this.random.nextInt(4);
         this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY() + 0.5, this.getZ(), â˜ƒ));
      }
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : SoundEvents.WANDERING_TRADER_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.WANDERING_TRADER_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.WANDERING_TRADER_DEATH;
   }

   @Override
   protected SoundEvent getDrinkingSound(ItemStack var1) {
      return â˜ƒ.is(Items.MILK_BUCKET) ? SoundEvents.WANDERING_TRADER_DRINK_MILK : SoundEvents.WANDERING_TRADER_DRINK_POTION;
   }

   @Override
   protected SoundEvent getTradeUpdatedSound(boolean var1) {
      return â˜ƒ ? SoundEvents.WANDERING_TRADER_YES : SoundEvents.WANDERING_TRADER_NO;
   }

   @Override
   public SoundEvent getNotifyTradeSound() {
      return SoundEvents.WANDERING_TRADER_YES;
   }

   public void setDespawnDelay(int var1) {
      this.despawnDelay = â˜ƒ;
   }

   public int getDespawnDelay() {
      return this.despawnDelay;
   }

   @Override
   public void aiStep() {
      super.aiStep();
      if (!this.level.isClientSide) {
         this.maybeDespawn();
      }
   }

   private void maybeDespawn() {
      if (this.despawnDelay > 0 && !this.isTrading() && --this.despawnDelay == 0) {
         this.discard();
      }
   }

   public void setWanderTarget(@Nullable BlockPos var1) {
      this.wanderTarget = â˜ƒ;
   }

   @Nullable
   BlockPos getWanderTarget() {
      return this.wanderTarget;
   }

   class WanderToPositionGoal extends Goal {
      final WanderingTrader trader;
      final double stopDistance;
      final double speedModifier;

      WanderToPositionGoal(WanderingTrader var2, double var3, double var5) {
         this.trader = â˜ƒ;
         this.stopDistance = â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      @Override
      public void stop() {
         this.trader.setWanderTarget(null);
         WanderingTrader.this.navigation.stop();
      }

      @Override
      public boolean canUse() {
         BlockPos â˜ƒ = this.trader.getWanderTarget();
         return â˜ƒ != null && this.isTooFarAway(â˜ƒ, this.stopDistance);
      }

      @Override
      public void tick() {
         BlockPos â˜ƒ = this.trader.getWanderTarget();
         if (â˜ƒ != null && WanderingTrader.this.navigation.isDone()) {
            if (this.isTooFarAway(â˜ƒ, 10.0)) {
               Vec3 â˜ƒx = new Vec3((double)â˜ƒ.getX() - this.trader.getX(), (double)â˜ƒ.getY() - this.trader.getY(), (double)â˜ƒ.getZ() - this.trader.getZ())
                  .normalize();
               Vec3 â˜ƒxx = â˜ƒx.scale(10.0).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
               WanderingTrader.this.navigation.moveTo(â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, this.speedModifier);
            } else {
               WanderingTrader.this.navigation.moveTo((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), this.speedModifier);
            }
         }
      }

      private boolean isTooFarAway(BlockPos var1, double var2) {
         return !â˜ƒ.closerThan(this.trader.position(), â˜ƒ);
      }
   }
}
