package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TrappedChestBlockEntity extends ChestBlockEntity {
   public TrappedChestBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.TRAPPED_CHEST, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void signalOpenCount(Level var1, BlockPos var2, BlockState var3, int var4, int var5) {
      super.signalOpenCount(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ != â˜ƒ) {
         Block â˜ƒ = â˜ƒ.getBlock();
         â˜ƒ.updateNeighborsAt(â˜ƒ, â˜ƒ);
         â˜ƒ.updateNeighborsAt(â˜ƒ.below(), â˜ƒ);
      }
   }
}
