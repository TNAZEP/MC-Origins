package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.Features;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.TwistingVinesFeature;
import net.minecraft.world.level.lighting.LayerLightEngine;

public class NyliumBlock extends Block implements BonemealableBlock {
   protected NyliumBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   private static boolean canBeNylium(BlockState var0, LevelReader var1, BlockPos var2) {
      BlockPos â˜ƒ = â˜ƒ.above();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      int â˜ƒxx = LayerLightEngine.getLightBlockInto(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, Direction.UP, â˜ƒx.getLightBlock(â˜ƒ, â˜ƒ));
      return â˜ƒxx < â˜ƒ.getMaxLightLevel();
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!canBeNylium(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.NETHERRACK.defaultBlockState());
      }
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return â˜ƒ.getBlockState(â˜ƒ.above()).isAir();
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      BlockPos â˜ƒx = â˜ƒ.above();
      if (â˜ƒ.is(Blocks.CRIMSON_NYLIUM)) {
         NetherForestVegetationFeature.place(â˜ƒ, â˜ƒ, â˜ƒx, Features.Configs.CRIMSON_FOREST_CONFIG, 3, 1);
      } else if (â˜ƒ.is(Blocks.WARPED_NYLIUM)) {
         NetherForestVegetationFeature.place(â˜ƒ, â˜ƒ, â˜ƒx, Features.Configs.WARPED_FOREST_CONFIG, 3, 1);
         NetherForestVegetationFeature.place(â˜ƒ, â˜ƒ, â˜ƒx, Features.Configs.NETHER_SPROUTS_CONFIG, 3, 1);
         if (â˜ƒ.nextInt(8) == 0) {
            TwistingVinesFeature.place(â˜ƒ, â˜ƒ, â˜ƒx, 3, 1, 2);
         }
      }
   }
}
