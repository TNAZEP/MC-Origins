package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class ShowTradesToPlayer extends Behavior<Villager> {
   private static final int MAX_LOOK_TIME = 900;
   private static final int STARTING_LOOK_TIME = 40;
   @Nullable
   private ItemStack playerItemStack;
   private final List<ItemStack> displayItems = Lists.<ItemStack>newArrayList();
   private int cycleCounter;
   private int displayIndex;
   private int lookTime;

   public ShowTradesToPlayer(int var1, int var2) {
      super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT), â˜ƒ, â˜ƒ);
   }

   public boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      if (!â˜ƒ.getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent()) {
         return false;
      } else {
         LivingEntity â˜ƒ = (LivingEntity)â˜ƒ.getMemory(MemoryModuleType.INTERACTION_TARGET).get();
         return â˜ƒ.getType() == EntityType.PLAYER && â˜ƒ.isAlive() && â˜ƒ.isAlive() && !â˜ƒ.isBaby() && â˜ƒ.distanceToSqr(â˜ƒ) <= 17.0;
      }
   }

   public boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return this.checkExtraStartConditions(â˜ƒ, â˜ƒ) && this.lookTime > 0 && â˜ƒ.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
   }

   public void start(ServerLevel var1, Villager var2, long var3) {
      super.start(â˜ƒ, â˜ƒ, â˜ƒ);
      this.lookAtTarget(â˜ƒ);
      this.cycleCounter = 0;
      this.displayIndex = 0;
      this.lookTime = 40;
   }

   public void tick(ServerLevel var1, Villager var2, long var3) {
      LivingEntity â˜ƒ = this.lookAtTarget(â˜ƒ);
      this.findItemsToDisplay(â˜ƒ, â˜ƒ);
      if (!this.displayItems.isEmpty()) {
         this.displayCyclingItems(â˜ƒ);
      } else {
         clearHeldItem(â˜ƒ);
         this.lookTime = Math.min(this.lookTime, 40);
      }

      --this.lookTime;
   }

   public void stop(ServerLevel var1, Villager var2, long var3) {
      super.stop(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
      clearHeldItem(â˜ƒ);
      this.playerItemStack = null;
   }

   private void findItemsToDisplay(LivingEntity var1, Villager var2) {
      boolean â˜ƒ = false;
      ItemStack â˜ƒx = â˜ƒ.getMainHandItem();
      if (this.playerItemStack == null || !ItemStack.isSame(this.playerItemStack, â˜ƒx)) {
         this.playerItemStack = â˜ƒx;
         â˜ƒ = true;
         this.displayItems.clear();
      }

      if (â˜ƒ && !this.playerItemStack.isEmpty()) {
         this.updateDisplayItems(â˜ƒ);
         if (!this.displayItems.isEmpty()) {
            this.lookTime = 900;
            this.displayFirstItem(â˜ƒ);
         }
      }
   }

   private void displayFirstItem(Villager var1) {
      displayAsHeldItem(â˜ƒ, (ItemStack)this.displayItems.get(0));
   }

   private void updateDisplayItems(Villager var1) {
      for(MerchantOffer â˜ƒ : â˜ƒ.getOffers()) {
         if (!â˜ƒ.isOutOfStock() && this.playerItemStackMatchesCostOfOffer(â˜ƒ)) {
            this.displayItems.add(â˜ƒ.getResult());
         }
      }
   }

   private boolean playerItemStackMatchesCostOfOffer(MerchantOffer var1) {
      return ItemStack.isSame(this.playerItemStack, â˜ƒ.getCostA()) || ItemStack.isSame(this.playerItemStack, â˜ƒ.getCostB());
   }

   private static void clearHeldItem(Villager var0) {
      â˜ƒ.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      â˜ƒ.setDropChance(EquipmentSlot.MAINHAND, 0.085F);
   }

   private static void displayAsHeldItem(Villager var0, ItemStack var1) {
      â˜ƒ.setItemSlot(EquipmentSlot.MAINHAND, â˜ƒ);
      â˜ƒ.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
   }

   private LivingEntity lookAtTarget(Villager var1) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      LivingEntity â˜ƒx = (LivingEntity)â˜ƒ.getMemory(MemoryModuleType.INTERACTION_TARGET).get();
      â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒx, true));
      return â˜ƒx;
   }

   private void displayCyclingItems(Villager var1) {
      if (this.displayItems.size() >= 2 && ++this.cycleCounter >= 40) {
         ++this.displayIndex;
         this.cycleCounter = 0;
         if (this.displayIndex > this.displayItems.size() - 1) {
            this.displayIndex = 0;
         }

         displayAsHeldItem(â˜ƒ, (ItemStack)this.displayItems.get(this.displayIndex));
      }
   }
}
