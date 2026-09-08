package net.minecraft.world.item;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BedItem extends BlockItem {
   public BedItem(Block var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean placeBlock(BlockPlaceContext var1, BlockState var2) {
      return â˜ƒ.getLevel().setBlock(â˜ƒ.getClickedPos(), â˜ƒ, 26);
   }
}
