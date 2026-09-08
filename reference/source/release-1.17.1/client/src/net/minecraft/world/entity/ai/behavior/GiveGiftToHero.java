package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class GiveGiftToHero extends Behavior<Villager> {
   private static final int THROW_GIFT_AT_DISTANCE = 5;
   private static final int MIN_TIME_BETWEEN_GIFTS = 600;
   private static final int MAX_TIME_BETWEEN_GIFTS = 6600;
   private static final int TIME_TO_DELAY_FOR_HEAD_TO_FINISH_TURNING = 20;
   private static final Map<VillagerProfession, ResourceLocation> GIFTS = Util.make(Maps.<VillagerProfession, ResourceLocation>newHashMap(), var0 -> {
      var0.put(VillagerProfession.ARMORER, BuiltInLootTables.ARMORER_GIFT);
      var0.put(VillagerProfession.BUTCHER, BuiltInLootTables.BUTCHER_GIFT);
      var0.put(VillagerProfession.CARTOGRAPHER, BuiltInLootTables.CARTOGRAPHER_GIFT);
      var0.put(VillagerProfession.CLERIC, BuiltInLootTables.CLERIC_GIFT);
      var0.put(VillagerProfession.FARMER, BuiltInLootTables.FARMER_GIFT);
      var0.put(VillagerProfession.FISHERMAN, BuiltInLootTables.FISHERMAN_GIFT);
      var0.put(VillagerProfession.FLETCHER, BuiltInLootTables.FLETCHER_GIFT);
      var0.put(VillagerProfession.LEATHERWORKER, BuiltInLootTables.LEATHERWORKER_GIFT);
      var0.put(VillagerProfession.LIBRARIAN, BuiltInLootTables.LIBRARIAN_GIFT);
      var0.put(VillagerProfession.MASON, BuiltInLootTables.MASON_GIFT);
      var0.put(VillagerProfession.SHEPHERD, BuiltInLootTables.SHEPHERD_GIFT);
      var0.put(VillagerProfession.TOOLSMITH, BuiltInLootTables.TOOLSMITH_GIFT);
      var0.put(VillagerProfession.WEAPONSMITH, BuiltInLootTables.WEAPONSMITH_GIFT);
   });
   private static final float SPEED_MODIFIER = 0.5F;
   private int timeUntilNextGift = 600;
   private boolean giftGivenDuringThisRun;
   private long timeSinceStart;

   public GiveGiftToHero(int var1) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryStatus.VALUE_PRESENT
         ),
         â˜ƒ
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      if (!this.isHeroVisible(â˜ƒ)) {
         return false;
      } else if (this.timeUntilNextGift > 0) {
         --this.timeUntilNextGift;
         return false;
      } else {
         return true;
      }
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      this.giftGivenDuringThisRun = false;
      this.timeSinceStart = â˜ƒ;
      Player â˜ƒ = (Player)this.getNearestTargetableHero(â˜ƒ).get();
      â˜ƒ.getBrain().setMemory(MemoryModuleType.INTERACTION_TARGET, â˜ƒ);
      BehaviorUtils.lookAtEntity(â˜ƒ, â˜ƒ);
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.isHeroVisible(â˜ƒ) && !this.giftGivenDuringThisRun;
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      Player â˜ƒ = (Player)this.getNearestTargetableHero(â˜ƒ).get();
      BehaviorUtils.lookAtEntity(â˜ƒ, â˜ƒ);
      if (this.isWithinThrowingDistance(â˜ƒ, â˜ƒ)) {
         if (â˜ƒ - this.timeSinceStart > 20L) {
            this.throwGift(â˜ƒ, â˜ƒ);
            this.giftGivenDuringThisRun = true;
         }
      } else {
         BehaviorUtils.setWalkAndLookTargetMemories(â˜ƒ, â˜ƒ, 0.5F, 5);
      }
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      this.timeUntilNextGift = calculateTimeUntilNextGift(â˜ƒ);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
   }

   private void throwGift(Villager var1, LivingEntity var2) {
      for(ItemStack â˜ƒ : this.getItemToThrow(â˜ƒ)) {
         BehaviorUtils.throwItem(â˜ƒ, â˜ƒ, â˜ƒ.position());
      }
   }

   private List<ItemStack> getItemToThrow(Villager var1) {
      if (â˜ƒ.isBaby()) {
         return ImmutableList.of(new ItemStack(Items.POPPY));
      } else {
         VillagerProfession â˜ƒ = â˜ƒ.getVillagerData().getProfession();
         if (GIFTS.containsKey(â˜ƒ)) {
            LootTable â˜ƒx = â˜ƒ.level.getServer().getLootTables().get((ResourceLocation)GIFTS.get(â˜ƒ));
            LootContext.Builder â˜ƒxx = new LootContext.Builder((ServerLevel)â˜ƒ.level)
               .withParameter(LootContextParams.ORIGIN, â˜ƒ.position())
               .withParameter(LootContextParams.THIS_ENTITY, â˜ƒ)
               .withRandom(â˜ƒ.getRandom());
            return â˜ƒx.getRandomItems(â˜ƒxx.create(LootContextParamSets.GIFT));
         } else {
            return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
         }
      }
   }

   private boolean isHeroVisible(Villager var1) {
      return this.getNearestTargetableHero(â˜ƒ).isPresent();
   }

   private Optional<Player> getNearestTargetableHero(Villager var1) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).filter(this::isHero);
   }

   private boolean isHero(Player var1) {
      return â˜ƒ.hasEffect(MobEffects.HERO_OF_THE_VILLAGE);
   }

   private boolean isWithinThrowingDistance(Villager var1, Player var2) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      BlockPos â˜ƒx = â˜ƒ.blockPosition();
      return â˜ƒx.closerThan(â˜ƒ, 5.0);
   }

   private static int calculateTimeUntilNextGift(ServerLevel var0) {
      return 600 + â˜ƒ.random.nextInt(6001);
   }
}
