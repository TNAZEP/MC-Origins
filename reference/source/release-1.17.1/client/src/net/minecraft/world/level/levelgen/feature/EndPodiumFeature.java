package net.minecraft.world.level.levelgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndPodiumFeature extends Feature<NoneFeatureConfiguration> {
   public static final int PODIUM_RADIUS = 4;
   public static final int PODIUM_PILLAR_HEIGHT = 4;
   public static final int RIM_RADIUS = 1;
   public static final float CORNER_ROUNDING = 0.5F;
   public static final BlockPos END_PODIUM_LOCATION = BlockPos.ZERO;
   private final boolean active;

   public EndPodiumFeature(boolean var1) {
      super(NoneFeatureConfiguration.CODEC);
      this.active = â˜ƒ;
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();

      for(BlockPos â˜ƒxx : BlockPos.betweenClosed(
         new BlockPos(â˜ƒ.getX() - 4, â˜ƒ.getY() - 1, â˜ƒ.getZ() - 4), new BlockPos(â˜ƒ.getX() + 4, â˜ƒ.getY() + 32, â˜ƒ.getZ() + 4)
      )) {
         boolean â˜ƒxxx = â˜ƒxx.closerThan(â˜ƒ, 2.5);
         if (â˜ƒxxx || â˜ƒxx.closerThan(â˜ƒ, 3.5)) {
            if (â˜ƒxx.getY() < â˜ƒ.getY()) {
               if (â˜ƒxxx) {
                  this.setBlock(â˜ƒx, â˜ƒxx, Blocks.BEDROCK.defaultBlockState());
               } else if (â˜ƒxx.getY() < â˜ƒ.getY()) {
                  this.setBlock(â˜ƒx, â˜ƒxx, Blocks.END_STONE.defaultBlockState());
               }
            } else if (â˜ƒxx.getY() > â˜ƒ.getY()) {
               this.setBlock(â˜ƒx, â˜ƒxx, Blocks.AIR.defaultBlockState());
            } else if (!â˜ƒxxx) {
               this.setBlock(â˜ƒx, â˜ƒxx, Blocks.BEDROCK.defaultBlockState());
            } else if (this.active) {
               this.setBlock(â˜ƒx, new BlockPos(â˜ƒxx), Blocks.END_PORTAL.defaultBlockState());
            } else {
               this.setBlock(â˜ƒx, new BlockPos(â˜ƒxx), Blocks.AIR.defaultBlockState());
            }
         }
      }

      for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
         this.setBlock(â˜ƒx, â˜ƒ.above(â˜ƒxx), Blocks.BEDROCK.defaultBlockState());
      }

      BlockPos â˜ƒxx = â˜ƒ.above(2);

      for(Direction â˜ƒxxx : Direction.Plane.HORIZONTAL) {
         this.setBlock(â˜ƒx, â˜ƒxx.relative(â˜ƒxxx), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, â˜ƒxxx));
      }

      return true;
   }
}
