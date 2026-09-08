package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TradeWithVillager extends Behavior<Villager> {
   private static final int INTERACT_DIST_SQR = 5;
   private static final float SPEED_MODIFIER = 0.5F;
   private Set<Item> trades = ImmutableSet.of();

   public TradeWithVillager() {
      super(
         ImmutableMap.of(
            MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT
         )
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      return BehaviorUtils.targetIsValid(â˜ƒ.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      Villager â˜ƒ = (Villager)â˜ƒ.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
      BehaviorUtils.lockGazeAndWalkToEachOther(â˜ƒ, â˜ƒ, 0.5F);
      this.trades = figureOutWhatIAmWillingToTrade(â˜ƒ, â˜ƒ);
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      Villager â˜ƒ = (Villager)â˜ƒ.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
      if (!(â˜ƒ.distanceToSqr(â˜ƒ) > 5.0)) {
         BehaviorUtils.lockGazeAndWalkToEachOther(â˜ƒ, â˜ƒ, 0.5F);
         â˜ƒ.gossip(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.hasExcessFood() && (â˜ƒ.getVillagerData().getProfession() == VillagerProfession.FARMER || â˜ƒ.wantsMoreFood())) {
            throwHalfStack(â˜ƒ, Villager.FOOD_POINTS.keySet(), â˜ƒ);
         }

         if (â˜ƒ.getVillagerData().getProfession() == VillagerProfession.FARMER
            && â˜ƒ.getInventory().countItem(Items.WHEAT) > Items.WHEAT.getMaxStackSize() / 2) {
            throwHalfStack(â˜ƒ, ImmutableSet.of(Items.WHEAT), â˜ƒ);
         }

         if (!this.trades.isEmpty() && â˜ƒ.getInventory().hasAnyOf(this.trades)) {
            throwHalfStack(â˜ƒ, this.trades, â˜ƒ);
         }
      }
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
   }

   private static Set<Item> figureOutWhatIAmWillingToTrade(Villager var0, Villager var1) {
      ImmutableSet<Item> â˜ƒ = â˜ƒ.getVillagerData().getProfession().getRequestedItems();
      ImmutableSet<Item> â˜ƒx = â˜ƒ.getVillagerData().getProfession().getRequestedItems();
      return (Set<Item>)â˜ƒ.stream().filter(var1x -> !â˜ƒ.contains(var1x)).collect(Collectors.toSet());
   }

   private static void throwHalfStack(Villager var0, Set<Item> var1, LivingEntity var2) {
      SimpleContainer â˜ƒ = â˜ƒ.getInventory();
      ItemStack â˜ƒx = ItemStack.EMPTY;
      int â˜ƒxx = 0;

      while(â˜ƒxx < â˜ƒ.getContainerSize()) {
         ItemStack â˜ƒ;
         Item â˜ƒ;
         int â˜ƒ;
         label28: {
            â˜ƒ = â˜ƒ.getItem(â˜ƒxx);
            if (!â˜ƒ.isEmpty()) {
               â˜ƒ = â˜ƒ.getItem();
               if (â˜ƒ.contains(â˜ƒ)) {
                  if (â˜ƒ.getCount() > â˜ƒ.getMaxStackSize() / 2) {
                     â˜ƒ = â˜ƒ.getCount() / 2;
                     break label28;
                  }

                  if (â˜ƒ.getCount() > 24) {
                     â˜ƒ = â˜ƒ.getCount() - 24;
                     break label28;
                  }
               }
            }

            ++â˜ƒxx;
            continue;
         }

         â˜ƒ.shrink(â˜ƒ);
         â˜ƒx = new ItemStack(â˜ƒ, â˜ƒ);
         break;
      }

      if (!â˜ƒx.isEmpty()) {
         BehaviorUtils.throwItem(â˜ƒ, â˜ƒx, â˜ƒ.position());
      }
   }
}
