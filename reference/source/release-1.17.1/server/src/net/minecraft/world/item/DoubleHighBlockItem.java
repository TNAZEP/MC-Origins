package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DoubleHighBlockItem extends BlockItem {
   public DoubleHighBlockItem(Block var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean placeBlock(BlockPlaceContext var1, BlockState var2) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos().above();
      BlockState â˜ƒxx = â˜ƒ.isWaterAt(â˜ƒx) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
      â˜ƒ.setBlock(â˜ƒx, â˜ƒxx, 27);
      return super.placeBlock(â˜ƒ, â˜ƒ);
   }
}
