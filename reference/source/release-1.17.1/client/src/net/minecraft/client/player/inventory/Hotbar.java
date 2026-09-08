package net.minecraft.client.player.inventory;

import com.google.common.collect.ForwardingList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class Hotbar extends ForwardingList<ItemStack> {
   private final NonNullList<ItemStack> items = NonNullList.withSize(Inventory.getSelectionSize(), ItemStack.EMPTY);

   @Override
   protected List<ItemStack> delegate() {
      return this.items;
   }

   public ListTag createTag() {
      ListTag â˜ƒ = new ListTag();

      for(ItemStack â˜ƒx : this.delegate()) {
         â˜ƒ.add(â˜ƒx.save(new CompoundTag()));
      }

      return â˜ƒ;
   }

   public void fromTag(ListTag var1) {
      List<ItemStack> â˜ƒ = this.delegate();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         â˜ƒ.set(â˜ƒx, ItemStack.of(â˜ƒ.getCompound(â˜ƒx)));
      }
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack â˜ƒ : this.delegate()) {
         if (!â˜ƒ.isEmpty()) {
            return false;
         }
      }

      return true;
   }
}
