package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EnderChestBlockEntity extends BlockEntity implements LidBlockEntity {
   private final ChestLidController chestLidController = new ChestLidController();
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
      @Override
      protected void onOpen(Level var1, BlockPos var2, BlockState var3) {
         â˜ƒ.playSound(
            null,
            (double)â˜ƒ.getX() + 0.5,
            (double)â˜ƒ.getY() + 0.5,
            (double)â˜ƒ.getZ() + 0.5,
            SoundEvents.ENDER_CHEST_OPEN,
            SoundSource.BLOCKS,
            0.5F,
            â˜ƒ.random.nextFloat() * 0.1F + 0.9F
         );
      }

      @Override
      protected void onClose(Level var1, BlockPos var2, BlockState var3) {
         â˜ƒ.playSound(
            null,
            (double)â˜ƒ.getX() + 0.5,
            (double)â˜ƒ.getY() + 0.5,
            (double)â˜ƒ.getZ() + 0.5,
            SoundEvents.ENDER_CHEST_CLOSE,
            SoundSource.BLOCKS,
            0.5F,
            â˜ƒ.random.nextFloat() * 0.1F + 0.9F
         );
      }

      @Override
      protected void openerCountChanged(Level var1, BlockPos var2, BlockState var3, int var4, int var5) {
         â˜ƒ.blockEvent(EnderChestBlockEntity.this.worldPosition, Blocks.ENDER_CHEST, 1, â˜ƒ);
      }

      @Override
      protected boolean isOwnContainer(Player var1) {
         return â˜ƒ.getEnderChestInventory().isActiveChest(EnderChestBlockEntity.this);
      }
   };

   public EnderChestBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.ENDER_CHEST, â˜ƒ, â˜ƒ);
   }

   public static void lidAnimateTick(Level var0, BlockPos var1, BlockState var2, EnderChestBlockEntity var3) {
      â˜ƒ.chestLidController.tickLid();
   }

   @Override
   public boolean triggerEvent(int var1, int var2) {
      if (â˜ƒ == 1) {
         this.chestLidController.shouldBeOpen(â˜ƒ > 0);
         return true;
      } else {
         return super.triggerEvent(â˜ƒ, â˜ƒ);
      }
   }

   public void startOpen(Player var1) {
      if (!this.remove && !â˜ƒ.isSpectator()) {
         this.openersCounter.incrementOpeners(â˜ƒ, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   public void stopOpen(Player var1) {
      if (!this.remove && !â˜ƒ.isSpectator()) {
         this.openersCounter.decrementOpeners(â˜ƒ, this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   public boolean stillValid(Player var1) {
      if (this.level.getBlockEntity(this.worldPosition) != this) {
         return false;
      } else {
         return !(
            â˜ƒ.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) > 64.0
         );
      }
   }

   public void recheckOpen() {
      if (!this.remove) {
         this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
      }
   }

   @Override
   public float getOpenNess(float var1) {
      return this.chestLidController.getOpenness(â˜ƒ);
   }
}
