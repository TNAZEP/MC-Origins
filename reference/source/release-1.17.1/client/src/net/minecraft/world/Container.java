package net.minecraft.world;

import java.util.Set;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface Container extends Clearable {
   int LARGE_MAX_STACK_SIZE = 64;

   int getContainerSize();

   boolean isEmpty();

   ItemStack getItem(int var1);

   ItemStack removeItem(int var1, int var2);

   ItemStack removeItemNoUpdate(int var1);

   void setItem(int var1, ItemStack var2);

   default int getMaxStackSize() {
      return 64;
   }

   void setChanged();

   boolean stillValid(Player var1);

   default void startOpen(Player var1) {
   }

   default void stopOpen(Player var1) {
   }

   default boolean canPlaceItem(int var1, ItemStack var2) {
      return true;
   }

   default int countItem(Item var1) {
      int â˜ƒ = 0;

      for(int â˜ƒx = 0; â˜ƒx < this.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = this.getItem(â˜ƒx);
         if (â˜ƒxx.getItem().equals(â˜ƒ)) {
            â˜ƒ += â˜ƒxx.getCount();
         }
      }

      return â˜ƒ;
   }

   default boolean hasAnyOf(Set<Item> var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.getContainerSize(); ++â˜ƒ) {
         ItemStack â˜ƒx = this.getItem(â˜ƒ);
         if (â˜ƒ.contains(â˜ƒx.getItem()) && â˜ƒx.getCount() > 0) {
            return true;
         }
      }

      return false;
   }
}
