package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class NetherrackBlock extends Block implements BonemealableBlock {
   public NetherrackBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      if (!â˜ƒ.getBlockState(â˜ƒ.above()).propagatesSkylightDown(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         for(BlockPos â˜ƒ : BlockPos.betweenClosed(â˜ƒ.offset(-1, -1, -1), â˜ƒ.offset(1, 1, 1))) {
            if (â˜ƒ.getBlockState(â˜ƒ).is(BlockTags.NYLIUM)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      boolean â˜ƒ = false;
      boolean â˜ƒx = false;

      for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒ.offset(-1, -1, -1), â˜ƒ.offset(1, 1, 1))) {
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
         if (â˜ƒxxx.is(Blocks.WARPED_NYLIUM)) {
            â˜ƒx = true;
         }

         if (â˜ƒxxx.is(Blocks.CRIMSON_NYLIUM)) {
            â˜ƒ = true;
         }

         if (â˜ƒx && â˜ƒ) {
            break;
         }
      }

      if (â˜ƒx && â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.nextBoolean() ? Blocks.WARPED_NYLIUM.defaultBlockState() : Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
      } else if (â˜ƒx) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.WARPED_NYLIUM.defaultBlockState(), 3);
      } else if (â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
      }
   }
}
