package net.minecraft.world.level.block;

import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class DoubleBlockCombiner {
   public static <S extends BlockEntity> DoubleBlockCombiner.NeighborCombineResult<S> combineWithNeigbour(
      BlockEntityType<S> var0,
      Function<BlockState, DoubleBlockCombiner.BlockType> var1,
      Function<BlockState, Direction> var2,
      DirectionProperty var3,
      BlockState var4,
      LevelAccessor var5,
      BlockPos var6,
      BiPredicate<LevelAccessor, BlockPos> var7
   ) {
      S â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ, â˜ƒ);
      if (â˜ƒ == null) {
         return DoubleBlockCombiner.Combiner::acceptNone;
      } else if (â˜ƒ.test(â˜ƒ, â˜ƒ)) {
         return DoubleBlockCombiner.Combiner::acceptNone;
      } else {
         DoubleBlockCombiner.BlockType â˜ƒ = (DoubleBlockCombiner.BlockType)â˜ƒ.apply(â˜ƒ);
         boolean â˜ƒx = â˜ƒ == DoubleBlockCombiner.BlockType.SINGLE;
         boolean â˜ƒxx = â˜ƒ == DoubleBlockCombiner.BlockType.FIRST;
         if (â˜ƒx) {
            return new DoubleBlockCombiner.NeighborCombineResult.Single<>(â˜ƒ);
         } else {
            BlockPos â˜ƒ = â˜ƒ.relative((Direction)â˜ƒ.apply(â˜ƒ));
            BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒx.is(â˜ƒ.getBlock())) {
               DoubleBlockCombiner.BlockType â˜ƒxx = (DoubleBlockCombiner.BlockType)â˜ƒ.apply(â˜ƒx);
               if (â˜ƒxx != DoubleBlockCombiner.BlockType.SINGLE && â˜ƒ != â˜ƒxx && â˜ƒx.getValue(â˜ƒ) == â˜ƒ.getValue(â˜ƒ)) {
                  if (â˜ƒ.test(â˜ƒ, â˜ƒ)) {
                     return DoubleBlockCombiner.Combiner::acceptNone;
                  }

                  S â˜ƒxxx = â˜ƒ.getBlockEntity(â˜ƒ, â˜ƒ);
                  if (â˜ƒxxx != null) {
                     S â˜ƒxxxx = â˜ƒxx ? â˜ƒ : â˜ƒxxx;
                     S â˜ƒxxxxx = â˜ƒxx ? â˜ƒxxx : â˜ƒ;
                     return new DoubleBlockCombiner.NeighborCombineResult.Double<>(â˜ƒxxxx, â˜ƒxxxxx);
                  }
               }
            }

            return new DoubleBlockCombiner.NeighborCombineResult.Single<>(â˜ƒ);
         }
      }
   }

   public static enum BlockType {
      SINGLE,
      FIRST,
      SECOND;
   }

   public interface Combiner<S, T> {
      T acceptDouble(S var1, S var2);

      T acceptSingle(S var1);

      T acceptNone();
   }

   public interface NeighborCombineResult<S> {
      <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> var1);

      public static final class Double<S> implements DoubleBlockCombiner.NeighborCombineResult<S> {
         private final S first;
         private final S second;

         public Double(S var1, S var2) {
            this.first = â˜ƒ;
            this.second = â˜ƒ;
         }

         @Override
         public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> var1) {
            return â˜ƒ.acceptDouble(this.first, this.second);
         }
      }

      public static final class Single<S> implements DoubleBlockCombiner.NeighborCombineResult<S> {
         private final S single;

         public Single(S var1) {
            this.single = â˜ƒ;
         }

         @Override
         public <T> T apply(DoubleBlockCombiner.Combiner<? super S, T> var1) {
            return â˜ƒ.acceptSingle(this.single);
         }
      }
   }
}
