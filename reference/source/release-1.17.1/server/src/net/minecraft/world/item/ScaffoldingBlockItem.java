package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ScaffoldingBlockItem extends BlockItem {
   public ScaffoldingBlockItem(Block var1, Item.Properties var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockPlaceContext updatePlacementContext(BlockPlaceContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      Level â˜ƒx = â˜ƒ.getLevel();
      BlockState â˜ƒxx = â˜ƒx.getBlockState(â˜ƒ);
      Block â˜ƒxxx = this.getBlock();
      if (!â˜ƒxx.is(â˜ƒxxx)) {
         return ScaffoldingBlock.getDistance(â˜ƒx, â˜ƒ) == 7 ? null : â˜ƒ;
      } else {
         Direction â˜ƒ;
         if (â˜ƒ.isSecondaryUseActive()) {
            â˜ƒ = â˜ƒ.isInside() ? â˜ƒ.getClickedFace().getOpposite() : â˜ƒ.getClickedFace();
         } else {
            â˜ƒ = â˜ƒ.getClickedFace() == Direction.UP ? â˜ƒ.getHorizontalDirection() : Direction.UP;
         }

         int â˜ƒ = 0;
         BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable().move(â˜ƒ);

         while(â˜ƒ < 7) {
            if (!â˜ƒx.isClientSide && !â˜ƒx.isInWorldBounds(â˜ƒx)) {
               Player â˜ƒxx = â˜ƒ.getPlayer();
               int â˜ƒxxx = â˜ƒx.getMaxBuildHeight();
               if (â˜ƒxx instanceof ServerPlayer && â˜ƒx.getY() >= â˜ƒxxx) {
                  ((ServerPlayer)â˜ƒxx)
                     .sendMessage(new TranslatableComponent("build.tooHigh", â˜ƒxxx - 1).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
               }
               break;
            }

            â˜ƒxx = â˜ƒx.getBlockState(â˜ƒx);
            if (!â˜ƒxx.is(this.getBlock())) {
               if (â˜ƒxx.canBeReplaced(â˜ƒ)) {
                  return BlockPlaceContext.at(â˜ƒ, â˜ƒx, â˜ƒ);
               }
               break;
            }

            â˜ƒx.move(â˜ƒ);
            if (â˜ƒ.getAxis().isHorizontal()) {
               ++â˜ƒ;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean mustSurvive() {
      return false;
   }
}
