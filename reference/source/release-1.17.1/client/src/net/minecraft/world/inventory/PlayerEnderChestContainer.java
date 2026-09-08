package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;

public class PlayerEnderChestContainer extends SimpleContainer {
   @Nullable
   private EnderChestBlockEntity activeChest;

   public PlayerEnderChestContainer() {
      super(27);
   }

   public void setActiveChest(EnderChestBlockEntity var1) {
      this.activeChest = â˜ƒ;
   }

   public boolean isActiveChest(EnderChestBlockEntity var1) {
      return this.activeChest == â˜ƒ;
   }

   @Override
   public void fromTag(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.getContainerSize(); ++â˜ƒ) {
         this.setItem(â˜ƒ, ItemStack.EMPTY);
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         int â˜ƒxx = â˜ƒx.getByte("Slot") & 255;
         if (â˜ƒxx >= 0 && â˜ƒxx < this.getContainerSize()) {
            this.setItem(â˜ƒxx, ItemStack.of(â˜ƒx));
         }
      }
   }

   @Override
   public ListTag createTag() {
      ListTag â˜ƒ = new ListTag();

      for(int â˜ƒx = 0; â˜ƒx < this.getContainerSize(); ++â˜ƒx) {
         ItemStack â˜ƒxx = this.getItem(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            CompoundTag â˜ƒxxx = new CompoundTag();
            â˜ƒxxx.putByte("Slot", (byte)â˜ƒx);
            â˜ƒxx.save(â˜ƒxxx);
            â˜ƒ.add(â˜ƒxxx);
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean stillValid(Player var1) {
      return this.activeChest != null && !this.activeChest.stillValid(â˜ƒ) ? false : super.stillValid(â˜ƒ);
   }

   @Override
   public void startOpen(Player var1) {
      if (this.activeChest != null) {
         this.activeChest.startOpen(â˜ƒ);
      }

      super.startOpen(â˜ƒ);
   }

   @Override
   public void stopOpen(Player var1) {
      if (this.activeChest != null) {
         this.activeChest.stopOpen(â˜ƒ);
      }

      super.stopOpen(â˜ƒ);
      this.activeChest = null;
   }
}
