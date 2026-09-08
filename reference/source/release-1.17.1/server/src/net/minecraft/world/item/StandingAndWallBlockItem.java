package net.minecraft.world.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class StandingAndWallBlockItem extends BlockItem {
   protected final Block wallBlock;

   public StandingAndWallBlockItem(Block var1, Block var2, Item.Properties var3) {
      super(â˜ƒ, â˜ƒ);
      this.wallBlock = â˜ƒ;
   }

   @Nullable
   @Override
   protected BlockState getPlacementState(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.wallBlock.getStateForPlacement(â˜ƒ);
      BlockState â˜ƒx = null;
      LevelReader â˜ƒxx = â˜ƒ.getLevel();
      BlockPos â˜ƒxxx = â˜ƒ.getClickedPos();

      for(Direction â˜ƒxxxx : â˜ƒ.getNearestLookingDirections()) {
         if (â˜ƒxxxx != Direction.UP) {
            BlockState â˜ƒxxxxx = â˜ƒxxxx == Direction.DOWN ? this.getBlock().getStateForPlacement(â˜ƒ) : â˜ƒ;
            if (â˜ƒxxxxx != null && â˜ƒxxxxx.canSurvive(â˜ƒxx, â˜ƒxxx)) {
               â˜ƒx = â˜ƒxxxxx;
               break;
            }
         }
      }

      return â˜ƒx != null && â˜ƒxx.isUnobstructed(â˜ƒx, â˜ƒxxx, CollisionContext.empty()) ? â˜ƒx : null;
   }

   @Override
   public void registerBlocks(Map<Block, Item> var1, Item var2) {
      super.registerBlocks(â˜ƒ, â˜ƒ);
      â˜ƒ.put(this.wallBlock, â˜ƒ);
   }
}
