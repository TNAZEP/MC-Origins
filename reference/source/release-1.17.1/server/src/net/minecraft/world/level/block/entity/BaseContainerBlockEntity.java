package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseContainerBlockEntity extends BlockEntity implements Container, MenuProvider, Nameable {
   private LockCode lockKey = LockCode.NO_LOCK;
   private Component name;

   protected BaseContainerBlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.lockKey = LockCode.fromTag(â˜ƒ);
      if (â˜ƒ.contains("CustomName", 8)) {
         this.name = Component.Serializer.fromJson(â˜ƒ.getString("CustomName"));
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      this.lockKey.addToTag(â˜ƒ);
      if (this.name != null) {
         â˜ƒ.putString("CustomName", Component.Serializer.toJson(this.name));
      }

      return â˜ƒ;
   }

   public void setCustomName(Component var1) {
      this.name = â˜ƒ;
   }

   @Override
   public Component getName() {
      return this.name != null ? this.name : this.getDefaultName();
   }

   @Override
   public Component getDisplayName() {
      return this.getName();
   }

   @Nullable
   @Override
   public Component getCustomName() {
      return this.name;
   }

   protected abstract Component getDefaultName();

   public boolean canOpen(Player var1) {
      return canUnlock(â˜ƒ, this.lockKey, this.getDisplayName());
   }

   public static boolean canUnlock(Player var0, LockCode var1, Component var2) {
      if (!â˜ƒ.isSpectator() && !â˜ƒ.unlocksWith(â˜ƒ.getMainHandItem())) {
         â˜ƒ.displayClientMessage(new TranslatableComponent("container.isLocked", â˜ƒ), true);
         â˜ƒ.playNotifySound(SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
      return this.canOpen(â˜ƒ) ? this.createMenu(â˜ƒ, â˜ƒ) : null;
   }

   protected abstract AbstractContainerMenu createMenu(int var1, Inventory var2);
}
