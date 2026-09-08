package net.minecraft.world;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CompoundContainer implements Container {
   private final Container container1;
   private final Container container2;

   public CompoundContainer(Container var1, Container var2) {
      if (â˜ƒ == null) {
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ == null) {
         â˜ƒ = â˜ƒ;
      }

      this.container1 = â˜ƒ;
      this.container2 = â˜ƒ;
   }

   @Override
   public int getContainerSize() {
      return this.container1.getContainerSize() + this.container2.getContainerSize();
   }

   @Override
   public boolean isEmpty() {
      return this.container1.isEmpty() && this.container2.isEmpty();
   }

   public boolean contains(Container var1) {
      return this.container1 == â˜ƒ || this.container2 == â˜ƒ;
   }

   @Override
   public ItemStack getItem(int var1) {
      return â˜ƒ >= this.container1.getContainerSize() ? this.container2.getItem(â˜ƒ - this.container1.getContainerSize()) : this.container1.getItem(â˜ƒ);
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      return â˜ƒ >= this.container1.getContainerSize()
         ? this.container2.removeItem(â˜ƒ - this.container1.getContainerSize(), â˜ƒ)
         : this.container1.removeItem(â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      return â˜ƒ >= this.container1.getContainerSize()
         ? this.container2.removeItemNoUpdate(â˜ƒ - this.container1.getContainerSize())
         : this.container1.removeItemNoUpdate(â˜ƒ);
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      if (â˜ƒ >= this.container1.getContainerSize()) {
         this.container2.setItem(â˜ƒ - this.container1.getContainerSize(), â˜ƒ);
      } else {
         this.container1.setItem(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public int getMaxStackSize() {
      return this.container1.getMaxStackSize();
   }

   @Override
   public void setChanged() {
      this.container1.setChanged();
      this.container2.setChanged();
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.container1.stillValid(â˜ƒ) && this.container2.stillValid(â˜ƒ);
   }

   @Override
   public void startOpen(Player var1) {
      this.container1.startOpen(â˜ƒ);
      this.container2.startOpen(â˜ƒ);
   }

   @Override
   public void stopOpen(Player var1) {
      this.container1.stopOpen(â˜ƒ);
      this.container2.stopOpen(â˜ƒ);
   }

   @Override
   public boolean canPlaceItem(int var1, ItemStack var2) {
      return â˜ƒ >= this.container1.getContainerSize()
         ? this.container2.canPlaceItem(â˜ƒ - this.container1.getContainerSize(), â˜ƒ)
         : this.container1.canPlaceItem(â˜ƒ, â˜ƒ);
   }

   @Override
   public void clearContent() {
      this.container1.clearContent();
      this.container2.clearContent();
   }
}
