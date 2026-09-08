package net.minecraft.world.level.block.entity;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DispenserBlockEntity extends RandomizableContainerBlockEntity {
   private static final Random RANDOM = new Random();
   public static final int CONTAINER_SIZE = 9;
   private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

   protected DispenserBlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public DispenserBlockEntity(BlockPos var1, BlockState var2) {
      this(BlockEntityType.DISPENSER, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getContainerSize() {
      return 9;
   }

   public int getRandomSlot() {
      this.unpackLootTable(null);
      int â˜ƒ = -1;
      int â˜ƒx = 1;

      for(int â˜ƒxx = 0; â˜ƒxx < this.items.size(); ++â˜ƒxx) {
         if (!this.items.get(â˜ƒxx).isEmpty() && RANDOM.nextInt(â˜ƒx++) == 0) {
            â˜ƒ = â˜ƒxx;
         }
      }

      return â˜ƒ;
   }

   public int addItem(ItemStack var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.items.size(); ++â˜ƒ) {
         if (this.items.get(â˜ƒ).isEmpty()) {
            this.setItem(â˜ƒ, â˜ƒ);
            return â˜ƒ;
         }
      }

      return -1;
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.dispenser");
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(â˜ƒ)) {
         ContainerHelper.loadAllItems(â˜ƒ, this.items);
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (!this.trySaveLootTable(â˜ƒ)) {
         ContainerHelper.saveAllItems(â˜ƒ, this.items);
      }

      return â˜ƒ;
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> var1) {
      this.items = â˜ƒ;
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return new DispenserMenu(â˜ƒ, â˜ƒ, this);
   }
}
