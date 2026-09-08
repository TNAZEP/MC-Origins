package net.minecraft.world.level.block.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public abstract class RandomizableContainerBlockEntity extends BaseContainerBlockEntity {
   public static final String LOOT_TABLE_TAG = "LootTable";
   public static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
   @Nullable
   protected ResourceLocation lootTable;
   protected long lootTableSeed;

   protected RandomizableContainerBlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void setLootTable(BlockGetter var0, Random var1, BlockPos var2, ResourceLocation var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof RandomizableContainerBlockEntity) {
         ((RandomizableContainerBlockEntity)â˜ƒ).setLootTable(â˜ƒ, â˜ƒ.nextLong());
      }
   }

   protected boolean tryLoadLootTable(CompoundTag var1) {
      if (â˜ƒ.contains("LootTable", 8)) {
         this.lootTable = new ResourceLocation(â˜ƒ.getString("LootTable"));
         this.lootTableSeed = â˜ƒ.getLong("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   protected boolean trySaveLootTable(CompoundTag var1) {
      if (this.lootTable == null) {
         return false;
      } else {
         â˜ƒ.putString("LootTable", this.lootTable.toString());
         if (this.lootTableSeed != 0L) {
            â˜ƒ.putLong("LootTableSeed", this.lootTableSeed);
         }

         return true;
      }
   }

   public void unpackLootTable(@Nullable Player var1) {
      if (this.lootTable != null && this.level.getServer() != null) {
         LootTable â˜ƒ = this.level.getServer().getLootTables().get(this.lootTable);
         if (â˜ƒ instanceof ServerPlayer) {
            CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)â˜ƒ, this.lootTable);
         }

         this.lootTable = null;
         LootContext.Builder â˜ƒ = new LootContext.Builder((ServerLevel)this.level)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition))
            .withOptionalRandomSeed(this.lootTableSeed);
         if (â˜ƒ != null) {
            â˜ƒ.withLuck(â˜ƒ.getLuck()).withParameter(LootContextParams.THIS_ENTITY, â˜ƒ);
         }

         â˜ƒ.fill(this, â˜ƒ.create(LootContextParamSets.CHEST));
      }
   }

   public void setLootTable(ResourceLocation var1, long var2) {
      this.lootTable = â˜ƒ;
      this.lootTableSeed = â˜ƒ;
   }

   @Override
   public boolean isEmpty() {
      this.unpackLootTable(null);
      return this.getItems().stream().allMatch(ItemStack::isEmpty);
   }

   @Override
   public ItemStack getItem(int var1) {
      this.unpackLootTable(null);
      return this.getItems().get(â˜ƒ);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      this.unpackLootTable(null);
      ItemStack â˜ƒ = ContainerHelper.removeItem(this.getItems(), â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         this.setChanged();
      }

      return â˜ƒ;
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      this.unpackLootTable(null);
      return ContainerHelper.takeItem(this.getItems(), â˜ƒ);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.unpackLootTable(null);
      this.getItems().set(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getCount() > this.getMaxStackSize()) {
         â˜ƒ.setCount(this.getMaxStackSize());
      }

      this.setChanged();
   }

   @Override
   public boolean stillValid(Player var1) {
      if (this.level.getBlockEntity(this.worldPosition) != this) {
         return false;
      } else {
         return !(
            â˜ƒ.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) > 64.0
         );
      }
   }

   @Override
   public void clearContent() {
      this.getItems().clear();
   }

   protected abstract NonNullList<ItemStack> getItems();

   protected abstract void setItems(NonNullList<ItemStack> var1);

   @Override
   public boolean canOpen(Player var1) {
      return super.canOpen(â˜ƒ) && (this.lootTable == null || !â˜ƒ.isSpectator());
   }

   @Nullable
   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
      if (this.canOpen(â˜ƒ)) {
         this.unpackLootTable(â˜ƒ.player);
         return this.createMenu(â˜ƒ, â˜ƒ);
      } else {
         return null;
      }
   }
}
