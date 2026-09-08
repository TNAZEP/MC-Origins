package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;

public class ReplaceBlobsFeature extends Feature<ReplaceSphereConfiguration> {
   public ReplaceBlobsFeature(Codec<ReplaceSphereConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<ReplaceSphereConfiguration> var1) {
      ReplaceSphereConfiguration â˜ƒ = â˜ƒ.config();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();
      Block â˜ƒxxx = â˜ƒ.targetState.getBlock();
      BlockPos â˜ƒxxxx = findTarget(â˜ƒx, â˜ƒ.origin().mutable().clamp(Direction.Axis.Y, â˜ƒx.getMinBuildHeight() + 1, â˜ƒx.getMaxBuildHeight() - 1), â˜ƒxxx);
      if (â˜ƒxxxx == null) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.radius().sample(â˜ƒxx);
         int â˜ƒx = â˜ƒ.radius().sample(â˜ƒxx);
         int â˜ƒxx = â˜ƒ.radius().sample(â˜ƒxx);
         int â˜ƒxxx = Math.max(â˜ƒ, Math.max(â˜ƒx, â˜ƒxx));
         boolean â˜ƒxxxx = false;

         for(BlockPos â˜ƒxxxxx : BlockPos.withinManhattan(â˜ƒxxxx, â˜ƒ, â˜ƒx, â˜ƒxx)) {
            if (â˜ƒxxxxx.distManhattan(â˜ƒxxxx) > â˜ƒxxx) {
               break;
            }

            BlockState â˜ƒxxxxxx = â˜ƒx.getBlockState(â˜ƒxxxxx);
            if (â˜ƒxxxxxx.is(â˜ƒxxx)) {
               this.setBlock(â˜ƒx, â˜ƒxxxxx, â˜ƒ.replaceState);
               â˜ƒxxxx = true;
            }
         }

         return â˜ƒxxxx;
      }
   }

   @Nullable
   private static BlockPos findTarget(LevelAccessor var0, BlockPos.MutableBlockPos var1, Block var2) {
      while(â˜ƒ.getY() > â˜ƒ.getMinBuildHeight() + 1) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒ.is(â˜ƒ)) {
            return â˜ƒ;
         }

         â˜ƒ.move(Direction.DOWN);
      }

      return null;
   }
}
