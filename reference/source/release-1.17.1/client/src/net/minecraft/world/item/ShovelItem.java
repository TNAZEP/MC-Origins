package net.minecraft.world.item;

import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ShovelItem extends DiggerItem {
   protected static final Map<Block, BlockState> FLATTENABLES = Maps.<Block, BlockState>newHashMap(
      new Builder()
         .put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState())
         .put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState())
         .put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState())
         .put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState())
         .put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState())
         .put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState())
         .build()
   );

   public ShovelItem(Tier var1, float var2, float var3, Item.Properties var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, BlockTags.MINEABLE_WITH_SHOVEL, â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (â˜ƒ.getClickedFace() == Direction.DOWN) {
         return InteractionResult.PASS;
      } else {
         Player â˜ƒ = â˜ƒ.getPlayer();
         BlockState â˜ƒx = (BlockState)FLATTENABLES.get(â˜ƒxx.getBlock());
         BlockState â˜ƒxx = null;
         if (â˜ƒx != null && â˜ƒ.getBlockState(â˜ƒx.above()).isAir()) {
            â˜ƒ.playSound(â˜ƒ, â˜ƒx, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            â˜ƒxx = â˜ƒx;
         } else if (â˜ƒxx.getBlock() instanceof CampfireBlock && â˜ƒxx.getValue(CampfireBlock.LIT)) {
            if (!â˜ƒ.isClientSide()) {
               â˜ƒ.levelEvent(null, 1009, â˜ƒx, 0);
            }

            CampfireBlock.dowse(â˜ƒ.getPlayer(), â˜ƒ, â˜ƒx, â˜ƒxx);
            â˜ƒxx = â˜ƒxx.setValue(CampfireBlock.LIT, Boolean.valueOf(false));
         }

         if (â˜ƒxx != null) {
            if (!â˜ƒ.isClientSide) {
               â˜ƒ.setBlock(â˜ƒx, â˜ƒxx, 11);
               if (â˜ƒ != null) {
                  â˜ƒ.getItemInHand().hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getHand()));
               }
            }

            return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
         } else {
            return InteractionResult.PASS;
         }
      }
   }
}
