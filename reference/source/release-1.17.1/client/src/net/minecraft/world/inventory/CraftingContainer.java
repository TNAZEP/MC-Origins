package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;

public class CraftingContainer implements Container, StackedContentsCompatible {
   private final NonNullList<ItemStack> items;
   private final int width;
   private final int height;
   private final AbstractContainerMenu menu;

   public CraftingContainer(AbstractContainerMenu var1, int var2, int var3) {
      this.items = NonNullList.withSize(â˜ƒ * â˜ƒ, ItemStack.EMPTY);
      this.menu = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
   }

   @Override
   public int getContainerSize() {
      return this.items.size();
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.items) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getItem(int var1) {
      return â˜ƒ >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(â˜ƒ);
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      return ContainerHelper.takeItem(this.items, â˜ƒ);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      ItemStack â˜ƒ = ContainerHelper.removeItem(this.items, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         this.menu.slotsChanged(this);
      }

      return â˜ƒ;
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.items.set(â˜ƒ, â˜ƒ);
      this.menu.slotsChanged(this);
   }

   @Override
   public void setChanged() {
   }

   @Override
   public boolean stillValid(Player var1) {
      return true;
   }

   @Override
   public void clearContent() {
      this.items.clear();
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   @Override
   public void fillStackedContents(StackedContents var1) {
      for(ItemStack â˜ƒ : this.items) {
         â˜ƒ.accountSimpleStack(â˜ƒ);
      }
   }
}
