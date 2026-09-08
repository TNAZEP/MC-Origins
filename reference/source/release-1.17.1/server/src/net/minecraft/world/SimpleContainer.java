package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SimpleContainer implements Container, StackedContentsCompatible {
   private final int size;
   private final NonNullList<ItemStack> items;
   private List<ContainerListener> listeners;

   public SimpleContainer(int var1) {
      this.size = â˜ƒ;
      this.items = NonNullList.withSize(â˜ƒ, ItemStack.EMPTY);
   }

   public SimpleContainer(ItemStack... var1) {
      this.size = â˜ƒ.length;
      this.items = NonNullList.of(ItemStack.EMPTY, â˜ƒ);
   }

   public void addListener(ContainerListener var1) {
      if (this.listeners == null) {
         this.listeners = Lists.<ContainerListener>newArrayList();
      }

      this.listeners.add(â˜ƒ);
   }

   public void removeListener(ContainerListener var1) {
      this.listeners.remove(â˜ƒ);
   }

   @Override
   public ItemStack getItem(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.items.size() ? this.items.get(â˜ƒ) : ItemStack.EMPTY;
   }

   public List<ItemStack> removeAllItems() {
      List<ItemStack> â˜ƒ = (List)this.items.stream().filter(var0 -> !var0.isEmpty()).collect(Collectors.toList());
      this.clearContent();
      return â˜ƒ;
   }

   @Override
   public ItemStack removeItem(int var1, int var2) {
      ItemStack â˜ƒ = ContainerHelper.removeItem(this.items, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         this.setChanged();
      }

      return â˜ƒ;
   }

   public ItemStack removeItemType(Item var1, int var2) {
      ItemStack â˜ƒ = new ItemStack(â˜ƒ, 0);

      for(int â˜ƒx = this.size - 1; â˜ƒx >= 0; --â˜ƒx) {
         ItemStack â˜ƒxx = this.getItem(â˜ƒx);
         if (â˜ƒxx.getItem().equals(â˜ƒ)) {
            int â˜ƒxxx = â˜ƒ - â˜ƒ.getCount();
            ItemStack â˜ƒxxxx = â˜ƒxx.split(â˜ƒxxx);
            â˜ƒ.grow(â˜ƒxxxx.getCount());
            if (â˜ƒ.getCount() == â˜ƒ) {
               break;
            }
         }
      }

      if (!â˜ƒ.isEmpty()) {
         this.setChanged();
      }

      return â˜ƒ;
   }

   public ItemStack addItem(ItemStack var1) {
      ItemStack â˜ƒ = â˜ƒ.copy();
      this.moveItemToOccupiedSlotsWithSameType(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.moveItemToEmptySlots(â˜ƒ);
         return â˜ƒ.isEmpty() ? ItemStack.EMPTY : â˜ƒ;
      }
   }

   public boolean canAddItem(ItemStack var1) {
      boolean â˜ƒ = false;

      for(ItemStack â˜ƒx : this.items) {
         if (â˜ƒx.isEmpty() || ItemStack.isSameItemSameTags(â˜ƒx, â˜ƒ) && â˜ƒx.getCount() < â˜ƒx.getMaxStackSize()) {
            â˜ƒ = true;
            break;
         }
      }

      return â˜ƒ;
   }

   @Override
   public ItemStack removeItemNoUpdate(int var1) {
      ItemStack â˜ƒ = this.items.get(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.items.set(â˜ƒ, ItemStack.EMPTY);
         return â˜ƒ;
      }
   }

   @Override
   public void setItem(int var1, ItemStack var2) {
      this.items.set(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isEmpty() && â˜ƒ.getCount() > this.getMaxStackSize()) {
         â˜ƒ.setCount(this.getMaxStackSize());
      }

      this.setChanged();
   }

   @Override
   public int getContainerSize() {
      return this.size;
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
   public void setChanged() {
      if (this.listeners != null) {
         for(ContainerListener â˜ƒ : this.listeners) {
            â˜ƒ.containerChanged(this);
         }
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return true;
   }

   @Override
   public void clearContent() {
      this.items.clear();
      this.setChanged();
   }

   @Override
   public void fillStackedContents(StackedContents var1) {
      for(ItemStack â˜ƒ : this.items) {
         â˜ƒ.accountStack(â˜ƒ);
      }
   }

   public String toString() {
      return ((List)this.items.stream().filter(var0 -> !var0.isEmpty()).collect(Collectors.toList())).toString();
   }

   private void moveItemToEmptySlots(ItemStack var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.size; ++â˜ƒ) {
         ItemStack â˜ƒx = this.getItem(â˜ƒ);
         if (â˜ƒx.isEmpty()) {
            this.setItem(â˜ƒ, â˜ƒ.copy());
            â˜ƒ.setCount(0);
            return;
         }
      }
   }

   private void moveItemToOccupiedSlotsWithSameType(ItemStack var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.size; ++â˜ƒ) {
         ItemStack â˜ƒx = this.getItem(â˜ƒ);
         if (ItemStack.isSameItemSameTags(â˜ƒx, â˜ƒ)) {
            this.moveItemsBetweenStacks(â˜ƒ, â˜ƒx);
            if (â˜ƒ.isEmpty()) {
               return;
            }
         }
      }
   }

   private void moveItemsBetweenStacks(ItemStack var1, ItemStack var2) {
      int â˜ƒ = Math.min(this.getMaxStackSize(), â˜ƒ.getMaxStackSize());
      int â˜ƒx = Math.min(â˜ƒ.getCount(), â˜ƒ - â˜ƒ.getCount());
      if (â˜ƒx > 0) {
         â˜ƒ.grow(â˜ƒx);
         â˜ƒ.shrink(â˜ƒx);
         this.setChanged();
      }
   }

   public void fromTag(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         ItemStack â˜ƒx = ItemStack.of(â˜ƒ.getCompound(â˜ƒ));
         if (!â˜ƒx.isEmpty()) {
            this.addItem(â˜ƒx);
         }
      }
   }

   public ListTag createTag() {
      ListTag â˜ƒ = new ListTag();

      for(int â˜ƒx = 0; â˜ƒx < this.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = this.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.add(â˜ƒxx.save(new CompoundTag()));
         }
      }

      return â˜ƒ;
   }
}
