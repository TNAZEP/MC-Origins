package net.minecraft.world.entity.monster;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ZombieVillager extends Zombie implements VillagerDataHolder {
   private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(ZombieVillager.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(
      ZombieVillager.class, EntityDataSerializers.VILLAGER_DATA
   );
   private static final int VILLAGER_CONVERSION_WAIT_MIN = 3600;
   private static final int VILLAGER_CONVERSION_WAIT_MAX = 6000;
   private static final int MAX_SPECIAL_BLOCKS_COUNT = 14;
   private static final int SPECIAL_BLOCK_RADIUS = 4;
   private int villagerConversionTime;
   private UUID conversionStarter;
   private Tag gossips;
   private CompoundTag tradeOffers;
   private int villagerXp;

   public ZombieVillager(EntityType<? extends ZombieVillager> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.setVillagerData(this.getVillagerData().setProfession(Registry.VILLAGER_PROFESSION.getRandom(this.random)));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_CONVERTING_ID, false);
      this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent(var1x -> â˜ƒ.put("VillagerData", var1x));
      if (this.tradeOffers != null) {
         â˜ƒ.put("Offers", this.tradeOffers);
      }

      if (this.gossips != null) {
         â˜ƒ.put("Gossips", this.gossips);
      }

      â˜ƒ.putInt("ConversionTime", this.isConverting() ? this.villagerConversionTime : -1);
      if (this.conversionStarter != null) {
         â˜ƒ.putUUID("ConversionPlayer", this.conversionStarter);
      }

      â˜ƒ.putInt("Xp", this.villagerXp);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("VillagerData", 10)) {
         DataResult<VillagerData> â˜ƒ = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ.get("VillagerData")));
         â˜ƒ.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
      }

      if (â˜ƒ.contains("Offers", 10)) {
         this.tradeOffers = â˜ƒ.getCompound("Offers");
      }

      if (â˜ƒ.contains("Gossips", 10)) {
         this.gossips = â˜ƒ.getList("Gossips", 10);
      }

      if (â˜ƒ.contains("ConversionTime", 99) && â˜ƒ.getInt("ConversionTime") > -1) {
         this.startConverting(â˜ƒ.hasUUID("ConversionPlayer") ? â˜ƒ.getUUID("ConversionPlayer") : null, â˜ƒ.getInt("ConversionTime"));
      }

      if (â˜ƒ.contains("Xp", 3)) {
         this.villagerXp = â˜ƒ.getInt("Xp");
      }
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.isAlive() && this.isConverting()) {
         int â˜ƒ = this.getConversionProgress();
         this.villagerConversionTime -= â˜ƒ;
         if (this.villagerConversionTime <= 0) {
            this.finishConversion((ServerLevel)this.level);
         }
      }

      super.tick();
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.GOLDEN_APPLE)) {
         if (this.hasEffect(MobEffects.WEAKNESS)) {
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒ.shrink(1);
            }

            if (!this.level.isClientSide) {
               this.startConverting(â˜ƒ.getUUID(), this.random.nextInt(2401) + 3600);
            }

            this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.CONSUME;
         }
      } else {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected boolean convertsInWater() {
      return false;
   }

   @Override
   public boolean removeWhenFarAway(double var1) {
      return !this.isConverting() && this.villagerXp == 0;
   }

   public boolean isConverting() {
      return this.getEntityData().get(DATA_CONVERTING_ID);
   }

   private void startConverting(@Nullable UUID var1, int var2) {
      this.conversionStarter = â˜ƒ;
      this.villagerConversionTime = â˜ƒ;
      this.getEntityData().set(DATA_CONVERTING_ID, true);
      this.removeEffect(MobEffects.WEAKNESS);
      this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, â˜ƒ, Math.min(this.level.getDifficulty().getId() - 1, 0)));
      this.level.broadcastEntityEvent(this, (byte)16);
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 16) {
         if (!this.isSilent()) {
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getEyeY(),
                  this.getZ(),
                  SoundEvents.ZOMBIE_VILLAGER_CURE,
                  this.getSoundSource(),
                  1.0F + this.random.nextFloat(),
                  this.random.nextFloat() * 0.7F + 0.3F,
                  false
               );
         }
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }

   private void finishConversion(ServerLevel var1) {
      Villager â˜ƒ = this.convertTo(EntityType.VILLAGER, false);

      for(EquipmentSlot â˜ƒx : EquipmentSlot.values()) {
         ItemStack â˜ƒxx = this.getItemBySlot(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            if (EnchantmentHelper.hasBindingCurse(â˜ƒxx)) {
               â˜ƒ.getSlot(â˜ƒx.getIndex() + 300).set(â˜ƒxx);
            } else {
               double â˜ƒxxx = (double)this.getEquipmentDropChance(â˜ƒx);
               if (â˜ƒxxx > 1.0) {
                  this.spawnAtLocation(â˜ƒxx);
               }
            }
         }
      }

      â˜ƒ.setVillagerData(this.getVillagerData());
      if (this.gossips != null) {
         â˜ƒ.setGossips(this.gossips);
      }

      if (this.tradeOffers != null) {
         â˜ƒ.setOffers(new MerchantOffers(this.tradeOffers));
      }

      â˜ƒ.setVillagerXp(this.villagerXp);
      â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ.blockPosition()), MobSpawnType.CONVERSION, null, null);
      if (this.conversionStarter != null) {
         Player â˜ƒx = â˜ƒ.getPlayerByUUID(this.conversionStarter);
         if (â˜ƒx instanceof ServerPlayer) {
            CriteriaTriggers.CURED_ZOMBIE_VILLAGER.trigger((ServerPlayer)â˜ƒx, this, â˜ƒ);
            â˜ƒ.onReputationEvent(ReputationEventType.ZOMBIE_VILLAGER_CURED, â˜ƒx, â˜ƒ);
         }
      }

      â˜ƒ.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
      if (!this.isSilent()) {
         â˜ƒ.levelEvent(null, 1027, this.blockPosition(), 0);
      }
   }

   private int getConversionProgress() {
      int â˜ƒ = 1;
      if (this.random.nextFloat() < 0.01F) {
         int â˜ƒx = 0;
         BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxx = (int)this.getX() - 4; â˜ƒxxx < (int)this.getX() + 4 && â˜ƒx < 14; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = (int)this.getY() - 4; â˜ƒxxxx < (int)this.getY() + 4 && â˜ƒx < 14; ++â˜ƒxxxx) {
               for(int â˜ƒxxxxx = (int)this.getZ() - 4; â˜ƒxxxxx < (int)this.getZ() + 4 && â˜ƒx < 14; ++â˜ƒxxxxx) {
                  BlockState â˜ƒxxxxxx = this.level.getBlockState(â˜ƒxx.set(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx));
                  if (â˜ƒxxxxxx.is(Blocks.IRON_BARS) || â˜ƒxxxxxx.getBlock() instanceof BedBlock) {
                     if (this.random.nextFloat() < 0.3F) {
                        ++â˜ƒ;
                     }

                     ++â˜ƒx;
                  }
               }
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   public float getVoicePitch() {
      return this.isBaby()
         ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F
         : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
   }

   @Override
   public SoundEvent getAmbientSound() {
      return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
   }

   @Override
   public SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.ZOMBIE_VILLAGER_HURT;
   }

   @Override
   public SoundEvent getDeathSound() {
      return SoundEvents.ZOMBIE_VILLAGER_DEATH;
   }

   @Override
   public SoundEvent getStepSound() {
      return SoundEvents.ZOMBIE_VILLAGER_STEP;
   }

   @Override
   protected ItemStack getSkull() {
      return ItemStack.EMPTY;
   }

   public void setTradeOffers(CompoundTag var1) {
      this.tradeOffers = â˜ƒ;
   }

   public void setGossips(Tag var1) {
      this.gossips = â˜ƒ;
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      this.setVillagerData(this.getVillagerData().setType(VillagerType.byBiome(â˜ƒ.getBiomeName(this.blockPosition()))));
      return super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void setVillagerData(VillagerData var1) {
      VillagerData â˜ƒ = this.getVillagerData();
      if (â˜ƒ.getProfession() != â˜ƒ.getProfession()) {
         this.tradeOffers = null;
      }

      this.entityData.set(DATA_VILLAGER_DATA, â˜ƒ);
   }

   @Override
   public VillagerData getVillagerData() {
      return this.entityData.get(DATA_VILLAGER_DATA);
   }

   public int getVillagerXp() {
      return this.villagerXp;
   }

   public void setVillagerXp(int var1) {
      this.villagerXp = â˜ƒ;
   }
}
