package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Slot {
   private final int slot;
   public final Container container;
   public int index;
   public final int x;
   public final int y;

   public Slot(Container var1, int var2, int var3, int var4) {
      this.container = â˜ƒ;
      this.slot = â˜ƒ;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   public void onQuickCraft(ItemStack var1, ItemStack var2) {
      int â˜ƒ = â˜ƒ.getCount() - â˜ƒ.getCount();
      if (â˜ƒ > 0) {
         this.onQuickCraft(â˜ƒ, â˜ƒ);
      }
   }

   protected void onQuickCraft(ItemStack var1, int var2) {
   }

   protected void onSwapCraft(int var1) {
   }

   protected void checkTakeAchievements(ItemStack var1) {
   }

   public void onTake(Player var1, ItemStack var2) {
      this.setChanged();
   }

   public boolean mayPlace(ItemStack var1) {
      return true;
   }

   public ItemStack getItem() {
      return this.container.getItem(this.slot);
   }

   public boolean hasItem() {
      return !this.getItem().isEmpty();
   }

   public void set(ItemStack var1) {
      this.container.setItem(this.slot, â˜ƒ);
      this.setChanged();
   }

   public void setChanged() {
      this.container.setChanged();
   }

   public int getMaxStackSize() {
      return this.container.getMaxStackSize();
   }

   public int getMaxStackSize(ItemStack var1) {
      return Math.min(this.getMaxStackSize(), â˜ƒ.getMaxStackSize());
   }

   @Nullable
   public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
      return null;
   }

   public ItemStack remove(int var1) {
      return this.container.removeItem(this.slot, â˜ƒ);
   }

   public boolean mayPickup(Player var1) {
      return true;
   }

   public boolean isActive() {
      return true;
   }

   public Optional<ItemStack> tryRemove(int var1, int var2, Player var3) {
      if (!this.mayPickup(â˜ƒ)) {
         return Optional.empty();
      } else if (!this.allowModification(â˜ƒ) && â˜ƒ < this.getItem().getCount()) {
         return Optional.empty();
      } else {
         â˜ƒ = Math.min(â˜ƒ, â˜ƒ);
         ItemStack â˜ƒ = this.remove(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            return Optional.empty();
         } else {
            if (this.getItem().isEmpty()) {
               this.set(ItemStack.EMPTY);
            }

            return Optional.of(â˜ƒ);
         }
      }
   }

   public ItemStack safeTake(int var1, int var2, Player var3) {
      Optional<ItemStack> â˜ƒ = this.tryRemove(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.ifPresent(var2x -> this.onTake(â˜ƒ, var2x));
      return (ItemStack)â˜ƒ.orElse(ItemStack.EMPTY);
   }

   public ItemStack safeInsert(ItemStack var1) {
      return this.safeInsert(â˜ƒ, â˜ƒ.getCount());
   }

   public ItemStack safeInsert(ItemStack var1, int var2) {
      if (!â˜ƒ.isEmpty() && this.mayPlace(â˜ƒ)) {
         ItemStack â˜ƒ = this.getItem();
         int â˜ƒx = Math.min(Math.min(â˜ƒ, â˜ƒ.getCount()), this.getMaxStackSize(â˜ƒ) - â˜ƒ.getCount());
         if (â˜ƒ.isEmpty()) {
            this.set(â˜ƒ.split(â˜ƒx));
         } else if (ItemStack.isSameItemSameTags(â˜ƒ, â˜ƒ)) {
            â˜ƒ.shrink(â˜ƒx);
            â˜ƒ.grow(â˜ƒx);
            this.set(â˜ƒ);
         }

         return â˜ƒ;
      } else {
         return â˜ƒ;
      }
   }

   public boolean allowModification(Player var1) {
      return this.mayPickup(â˜ƒ) && this.mayPlace(this.getItem());
   }

   public int getContainerSlot() {
      return this.slot;
   }
}
