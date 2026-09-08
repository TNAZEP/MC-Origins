package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BarrelBlockEntity extends RandomizableContainerBlockEntity {
   private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
   private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      @Override
      protected void onOpen(Level var1, BlockPos var2, BlockState var3) {
         BarrelBlockEntity.this.playSound(â˜ƒ, SoundEvents.BARREL_OPEN);
         BarrelBlockEntity.this.updateBlockState(â˜ƒ, true);
      }

      @Override
      protected void onClose(Level var1, BlockPos var2, BlockState var3) {
         BarrelBlockEntity.this.playSound(â˜ƒ, SoundEvents.BARREL_CLOSE);
         BarrelBlockEntity.this.updateBlockState(â˜ƒ, false);
      }

      @Override
      protected void openerCountChanged(Level var1, BlockPos var2, BlockState var3, int var4, int var5) {
      }

      @Override
      protected boolean isOwnContainer(Player var1) {
         if (â˜ƒ.containerMenu instanceof ChestMenu) {
            Container â˜ƒ = ((ChestMenu)â˜ƒ.containerMenu).getContainer();
            return â˜ƒ == BarrelBlockEntity.this;
         } else {
            return false;
         }
      }
   };

   public BarrelBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.BARREL, â˜ƒ, â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (!this.trySaveLootTable(â˜ƒ)) {
         ContainerHelper.saveAllItems(â˜ƒ, this.items);
      }

      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(â˜ƒ)) {
         ContainerHelper.loadAllItems(â˜ƒ, this.items);
      }
   }

   @Override
   public int getContainerSize() {
      return 27;
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> var1) {
      this.items = â˜ƒ;
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.barrel");
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return ChestMenu.threeRows(â˜ƒ, â˜ƒ, this);
   }

   @Override
   public void startOpen(Player var1) {
      if (!this.remove && !â˜ƒ.isSpectator()) {
         this.openersCounter.incrementOpeners(â˜ƒ, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   @Override
   public void stopOpen(Player var1) {
      if (!this.remove && !â˜ƒ.isSpectator()) {
         this.openersCounter.decrementOpeners(â˜ƒ, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   public void recheckOpen() {
      if (!this.remove) {
         this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   void updateBlockState(BlockState var1, boolean var2) {
      this.level.setBlock(this.getBlockPos(), â˜ƒ.setValue(BarrelBlock.OPEN, Boolean.valueOf(â˜ƒ)), 3);
   }

   void playSound(BlockState var1, SoundEvent var2) {
      Vec3i â˜ƒ = ((Direction)â˜ƒ.getValue(BarrelBlock.FACING)).getNormal();
      double â˜ƒx = (double)this.worldPosition.getX() + 0.5 + (double)â˜ƒ.getX() / 2.0;
      double â˜ƒxx = (double)this.worldPosition.getY() + 0.5 + (double)â˜ƒ.getY() / 2.0;
      double â˜ƒxxx = (double)this.worldPosition.getZ() + 0.5 + (double)â˜ƒ.getZ() / 2.0;
      this.level.playSound(null, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
   }
}
