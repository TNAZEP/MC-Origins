package net.minecraft.world;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class ContainerHelper {
   public static ItemStack removeItem(List<ItemStack> var0, int var1, int var2) {
      return â˜ƒ >= 0 && â˜ƒ < â˜ƒ.size() && !((ItemStack)â˜ƒ.get(â˜ƒ)).isEmpty() && â˜ƒ > 0 ? ((ItemStack)â˜ƒ.get(â˜ƒ)).split(â˜ƒ) : ItemStack.EMPTY;
   }

   public static ItemStack takeItem(List<ItemStack> var0, int var1) {
      return â˜ƒ >= 0 && â˜ƒ < â˜ƒ.size() ? (ItemStack)â˜ƒ.set(â˜ƒ, ItemStack.EMPTY) : ItemStack.EMPTY;
   }

   public static CompoundTag saveAllItems(CompoundTag var0, NonNullList<ItemStack> var1) {
      return saveAllItems(â˜ƒ, â˜ƒ, true);
   }

   public static CompoundTag saveAllItems(CompoundTag var0, NonNullList<ItemStack> var1, boolean var2) {
      ListTag â˜ƒ = new ListTag();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.get(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            CompoundTag â˜ƒxxx = new CompoundTag();
            â˜ƒxxx.putByte("Slot", (byte)â˜ƒx);
            â˜ƒxx.save(â˜ƒxxx);
            â˜ƒ.add(â˜ƒxxx);
         }
      }

      if (!â˜ƒ.isEmpty() || â˜ƒ) {
         â˜ƒ.put("Items", â˜ƒ);
      }

      return â˜ƒ;
   }

   public static void loadAllItems(CompoundTag var0, NonNullList<ItemStack> var1) {
      ListTag â˜ƒ = â˜ƒ.getList("Items", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
         int â˜ƒxxx = â˜ƒxx.getByte("Slot") & 255;
         if (â˜ƒxxx >= 0 && â˜ƒxxx < â˜ƒ.size()) {
            â˜ƒ.set(â˜ƒxxx, ItemStack.of(â˜ƒxx));
         }
      }
   }

   public static int clearOrCountMatchingItems(Container var0, Predicate<ItemStack> var1, int var2, boolean var3) {
      int â˜ƒ = 0;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         int â˜ƒxxx = clearOrCountMatchingItems(â˜ƒxx, â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒ);
         if (â˜ƒxxx > 0 && !â˜ƒ && â˜ƒxx.isEmpty()) {
            â˜ƒ.setItem(â˜ƒx, ItemStack.EMPTY);
         }

         â˜ƒ += â˜ƒxxx;
      }

      return â˜ƒ;
   }

   public static int clearOrCountMatchingItems(ItemStack var0, Predicate<ItemStack> var1, int var2, boolean var3) {
      if (â˜ƒ.isEmpty() || !â˜ƒ.test(â˜ƒ)) {
         return 0;
      } else if (â˜ƒ) {
         return â˜ƒ.getCount();
      } else {
         int â˜ƒ = â˜ƒ < 0 ? â˜ƒ.getCount() : Math.min(â˜ƒ, â˜ƒ.getCount());
         â˜ƒ.shrink(â˜ƒ);
         return â˜ƒ;
      }
   }
}
