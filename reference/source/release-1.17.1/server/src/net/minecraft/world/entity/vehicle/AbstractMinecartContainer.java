package net.minecraft.world.entity.vehicle;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public abstract class AbstractMinecartContainer extends AbstractMinecart implements Container, MenuProvider {
   private NonNullList<ItemStack> itemStacks = NonNullList.withSize(36, ItemStack.EMPTY);
   @Nullable
   private ResourceLocation lootTable;
   private long lootTableSeed;

   protected AbstractMinecartContainer(EntityType<?> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected AbstractMinecartContainer(EntityType<?> var1, double var2, double var4, double var6, Level var8) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void destroy(DamageSource var1) {
      super.destroy(â˜ƒ);
      if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         Containers.dropContents(this.level, this, this);
         if (!this.level.isClientSide) {
            Entity â˜ƒ = â˜ƒ.getDirectEntity();
            if (â˜ƒ != null && â˜ƒ.getType() == EntityType.PLAYER) {
               PiglinAi.angerNearbyPiglins((Player)â˜ƒ, true);
            }
         }
      }
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.itemStacks) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getItem(int var1) {
      this.unpackLootTable(null);
      return this.itemStacks.get(â˜ƒ);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      this.unpackLootTable(null);
      return ContainerHelper.removeItem(this.itemStacks, â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      this.unpackLootTable(null);
      ItemStack â˜ƒ = this.itemStacks.get(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.itemStacks.set(â˜ƒ, ItemStack.EMPTY);
         return â˜ƒ;
      }
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.unpackLootTable(null);
      this.itemStacks.set(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty() && â˜ƒ.getCount() > this.getMaxStackSize()) {
         â˜ƒ.setCount(this.getMaxStackSize());
      }
   }

   @Override
   public SlotAccess getSlot(final int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.getContainerSize() ? new SlotAccess() {
         @Override
         public ItemStack get() {
            return AbstractMinecartContainer.this.getItem(â˜ƒ);
         }

         @Override
         public boolean set(ItemStack var1x) {
            AbstractMinecartContainer.this.setItem(â˜ƒ, â˜ƒ);
            return true;
         }
      } : super.getSlot(â˜ƒ);
   }

   @Override
   public void setChanged() {
   }

   @Override
   public boolean stillValid(Player var1) {
      if (this.isRemoved()) {
         return false;
      } else {
         return !(â˜ƒ.distanceToSqr(this) > 64.0);
      }
   }

   @Override
   public void remove(Entity.RemovalReason var1) {
      if (!this.level.isClientSide && â˜ƒ.shouldDestroy()) {
         Containers.dropContents(this.level, this, this);
      }

      super.remove(â˜ƒ);
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.lootTable != null) {
         â˜ƒ.putString("LootTable", this.lootTable.toString());
         if (this.lootTableSeed != 0L) {
            â˜ƒ.putLong("LootTableSeed", this.lootTableSeed);
         }
      } else {
         ContainerHelper.saveAllItems(â˜ƒ, this.itemStacks);
      }
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (â˜ƒ.contains("LootTable", 8)) {
         this.lootTable = new ResourceLocation(â˜ƒ.getString("LootTable"));
         this.lootTableSeed = â˜ƒ.getLong("LootTableSeed");
      } else {
         ContainerHelper.loadAllItems(â˜ƒ, this.itemStacks);
      }
   }

   @Override
   public InteractionResult interact(Player var1, InteractionHand var2) {
      â˜ƒ.openMenu(this);
      if (!â˜ƒ.level.isClientSide) {
         this.gameEvent(GameEvent.CONTAINER_OPEN, â˜ƒ);
         PiglinAi.angerNearbyPiglins(â˜ƒ, true);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   @Override
   protected void applyNaturalSlowdown() {
      float â˜ƒ = 0.98F;
      if (this.lootTable == null) {
         int â˜ƒx = 15 - AbstractContainerMenu.getRedstoneSignalFromContainer(this);
         â˜ƒ += (float)â˜ƒx * 0.001F;
      }

      if (this.isInWater()) {
         â˜ƒ *= 0.95F;
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply((double)â˜ƒ, 0.0, (double)â˜ƒ));
   }

   public void unpackLootTable(@Nullable Player var1) {
      if (this.lootTable != null && this.level.getServer() != null) {
         LootTable â˜ƒ = this.level.getServer().getLootTables().get(this.lootTable);
         if (â˜ƒ instanceof ServerPlayer) {
            CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)â˜ƒ, this.lootTable);
         }

         this.lootTable = null;
         LootContext.Builder â˜ƒ = new LootContext.Builder((ServerLevel)this.level)
            .withParameter(LootContextParams.ORIGIN, this.position())
            .withOptionalRandomSeed(this.lootTableSeed);
         if (â˜ƒ != null) {
            â˜ƒ.withLuck(â˜ƒ.getLuck()).withParameter(LootContextParams.THIS_ENTITY, â˜ƒ);
         }

         â˜ƒ.fill(this, â˜ƒ.create(LootContextParamSets.CHEST));
      }
   }

   @Override
   public void clearContent() {
      this.unpackLootTable(null);
      this.itemStacks.clear();
   }

   public void setLootTable(ResourceLocation var1, long var2) {
      this.lootTable = â˜ƒ;
      this.lootTableSeed = â˜ƒ;
   }

   @Nullable
   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
      if (this.lootTable != null && â˜ƒ.isSpectator()) {
         return null;
      } else {
         this.unpackLootTable(â˜ƒ.player);
         return this.createMenu(â˜ƒ, â˜ƒ);
      }
   }

   protected abstract AbstractContainerMenu createMenu(int var1, Inventory var2);
}
