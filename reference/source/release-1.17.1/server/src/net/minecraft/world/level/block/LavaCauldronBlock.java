package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class LavaCauldronBlock extends AbstractCauldronBlock {
   public LavaCauldronBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, CauldronInteraction.LAVA);
   }

   @Override
   protected double getContentHeight(BlockState var1) {
      return 0.9375;
   }

   @Override
   public boolean isFull(BlockState var1) {
      return true;
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (this.isEntityInsideContent(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.lavaHurt();
      }
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return 3;
   }
}
