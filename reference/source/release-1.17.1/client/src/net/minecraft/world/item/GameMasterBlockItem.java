package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GameMasterBlockItem extends BlockItem {
   public GameMasterBlockItem(Block var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   protected BlockState getPlacementState(BlockPlaceContext var1) {
      Player â˜ƒ = â˜ƒ.getPlayer();
      return â˜ƒ != null && !â˜ƒ.canUseGameMasterBlocks() ? null : super.getPlacementState(â˜ƒ);
   }
}
