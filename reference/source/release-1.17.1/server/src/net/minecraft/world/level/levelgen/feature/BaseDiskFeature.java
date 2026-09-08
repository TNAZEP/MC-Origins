package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class BaseDiskFeature extends Feature<DiskConfiguration> {
   public BaseDiskFeature(Codec<DiskConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<DiskConfiguration> var1) {
      DiskConfiguration â˜ƒ = â˜ƒ.config();
      BlockPos â˜ƒx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      boolean â˜ƒxxx = false;
      int â˜ƒxxxx = â˜ƒx.getY();
      int â˜ƒxxxxx = â˜ƒxxxx + â˜ƒ.halfHeight;
      int â˜ƒxxxxxx = â˜ƒxxxx - â˜ƒ.halfHeight - 1;
      boolean â˜ƒxxxxxxx = â˜ƒ.state.getBlock() instanceof FallingBlock;
      int â˜ƒxxxxxxxx = â˜ƒ.radius.sample(â˜ƒ.random());

      for(int â˜ƒxxxxxxxxx = â˜ƒx.getX() - â˜ƒxxxxxxxx; â˜ƒxxxxxxxxx <= â˜ƒx.getX() + â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxx = â˜ƒx.getZ() - â˜ƒxxxxxxxx; â˜ƒxxxxxxxxxx <= â˜ƒx.getZ() + â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxxx) {
            int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx - â˜ƒx.getX();
            int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx - â˜ƒx.getZ();
            if (â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx <= â˜ƒxxxxxxxx * â˜ƒxxxxxxxx) {
               boolean â˜ƒxxxxxxxxxxxxx = false;

               for(int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxxxxxxxxx >= â˜ƒxxxxxx; --â˜ƒxxxxxxxxxxxxxx) {
                  BlockPos â˜ƒxxxxxxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx);
                  BlockState â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxx.getBlockState(â˜ƒxxxxxxxxxxxxxxx);
                  Block â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.getBlock();
                  boolean â˜ƒxxxxxxxxxxxxxxxxxx = false;
                  if (â˜ƒxxxxxxxxxxxxxx > â˜ƒxxxxxx) {
                     for(BlockState â˜ƒxxxxxxxxxxxxxxxxxxx : â˜ƒ.targets) {
                        if (â˜ƒxxxxxxxxxxxxxxxxxxx.is(â˜ƒxxxxxxxxxxxxxxxxx)) {
                           â˜ƒxx.setBlock(â˜ƒxxxxxxxxxxxxxxx, â˜ƒ.state, 2);
                           this.markAboveForPostProcessing(â˜ƒxx, â˜ƒxxxxxxxxxxxxxxx);
                           â˜ƒxxx = true;
                           â˜ƒxxxxxxxxxxxxxxxxxx = true;
                           break;
                        }
                     }
                  }

                  if (â˜ƒxxxxxxx && â˜ƒxxxxxxxxxxxxx && â˜ƒxxxxxxxxxxxxxxxx.isAir()) {
                     BlockState â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.state.is(Blocks.RED_SAND)
                        ? Blocks.RED_SANDSTONE.defaultBlockState()
                        : Blocks.SANDSTONE.defaultBlockState();
                     â˜ƒxx.setBlock(new BlockPos(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxxxxx), â˜ƒxxxxxxxxxxxxxxx, 2);
                  }

                  â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx;
               }
            }
         }
      }

      return â˜ƒxxx;
   }
}
