package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.phys.AABB;

public class UnderwaterMagmaFeature extends Feature<UnderwaterMagmaConfiguration> {
   public UnderwaterMagmaFeature(Codec<UnderwaterMagmaConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<UnderwaterMagmaConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      UnderwaterMagmaConfiguration â˜ƒxx = â˜ƒ.config();
      Random â˜ƒxxx = â˜ƒ.random();
      OptionalInt â˜ƒxxxx = getFloorY(â˜ƒ, â˜ƒx, â˜ƒxx);
      if (!â˜ƒxxxx.isPresent()) {
         return false;
      } else {
         BlockPos â˜ƒ = â˜ƒx.atY(â˜ƒxxxx.getAsInt());
         Vec3i â˜ƒx = new Vec3i(â˜ƒxx.placementRadiusAroundFloor, â˜ƒxx.placementRadiusAroundFloor, â˜ƒxx.placementRadiusAroundFloor);
         AABB â˜ƒxx = new AABB(â˜ƒ.subtract(â˜ƒx), â˜ƒ.offset(â˜ƒx));
         return BlockPos.betweenClosedStream(â˜ƒxx)
               .filter(var2x -> â˜ƒ.nextFloat() < â˜ƒ.placementProbabilityPerValidPosition)
               .filter(var2x -> this.isValidPlacement(â˜ƒ, var2x))
               .mapToInt(var1x -> {
                  â˜ƒ.setBlock(var1x, Blocks.MAGMA_BLOCK.defaultBlockState(), 2);
                  return 1;
               })
               .sum()
            > 0;
      }
   }

   private static OptionalInt getFloorY(WorldGenLevel var0, BlockPos var1, UnderwaterMagmaConfiguration var2) {
      Predicate<BlockState> â˜ƒ = var0x -> var0x.is(Blocks.WATER);
      Predicate<BlockState> â˜ƒx = var0x -> !var0x.is(Blocks.WATER);
      Optional<Column> â˜ƒxx = Column.scan(â˜ƒ, â˜ƒ, â˜ƒ.floorSearchRange, â˜ƒ, â˜ƒx);
      return (OptionalInt)â˜ƒxx.map(Column::getFloor).orElseGet(OptionalInt::empty);
   }

   private boolean isValidPlacement(WorldGenLevel var1, BlockPos var2) {
      if (!this.isWaterOrAir(â˜ƒ, â˜ƒ) && !this.isWaterOrAir(â˜ƒ, â˜ƒ.below())) {
         for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
            if (this.isWaterOrAir(â˜ƒ, â˜ƒ.relative(â˜ƒ))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isWaterOrAir(LevelAccessor var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.is(Blocks.WATER) || â˜ƒ.isAir();
   }
}
