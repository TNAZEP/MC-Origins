package net.minecraft.world.entity;

import java.util.function.Predicate;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface SlotAccess {
   SlotAccess NULL = new SlotAccess() {
      @Override
      public ItemStack get() {
         return ItemStack.EMPTY;
      }

      @Override
      public boolean set(ItemStack var1) {
         return false;
      }
   };

   static SlotAccess forContainer(final Container var0, final int var1, final Predicate<ItemStack> var2) {
      return new SlotAccess() {
         @Override
         public ItemStack get() {
            return â˜ƒ.getItem(â˜ƒ);
         }

         @Override
         public boolean set(ItemStack var1x) {
            if (!â˜ƒ.test(â˜ƒ)) {
               return false;
            } else {
               â˜ƒ.setItem(â˜ƒ, â˜ƒ);
               return true;
            }
         }
      };
   }

   static SlotAccess forContainer(Container var0, int var1) {
      return forContainer(â˜ƒ, â˜ƒ, var0x -> true);
   }

   static SlotAccess forEquipmentSlot(final LivingEntity var0, final EquipmentSlot var1, final Predicate<ItemStack> var2) {
      return new SlotAccess() {
         @Override
         public ItemStack get() {
            return â˜ƒ.getItemBySlot(â˜ƒ);
         }

         @Override
         public boolean set(ItemStack var1x) {
            if (!â˜ƒ.test(â˜ƒ)) {
               return false;
            } else {
               â˜ƒ.setItemSlot(â˜ƒ, â˜ƒ);
               return true;
            }
         }
      };
   }

   static SlotAccess forEquipmentSlot(LivingEntity var0, EquipmentSlot var1) {
      return forEquipmentSlot(â˜ƒ, â˜ƒ, var0x -> true);
   }

   ItemStack get();

   boolean set(ItemStack var1);
}
