package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtil {
   public static BlockUtil.FoundRectangle getLargestRectangleAround(
      BlockPos var0, Direction.Axis var1, int var2, Direction.Axis var3, int var4, Predicate<BlockPos> var5
   ) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      Direction â˜ƒx = Direction.get(Direction.AxisDirection.NEGATIVE, â˜ƒ);
      Direction â˜ƒxx = â˜ƒx.getOpposite();
      Direction â˜ƒxxx = Direction.get(Direction.AxisDirection.NEGATIVE, â˜ƒ);
      Direction â˜ƒxxxx = â˜ƒxxx.getOpposite();
      int â˜ƒxxxxx = getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ), â˜ƒx, â˜ƒ);
      int â˜ƒxxxxxx = getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ), â˜ƒxx, â˜ƒ);
      int â˜ƒxxxxxxx = â˜ƒxxxxx;
      BlockUtil.IntBounds[] â˜ƒxxxxxxxx = new BlockUtil.IntBounds[â˜ƒxxxxx + 1 + â˜ƒxxxxxx];
      â˜ƒxxxxxxxx[â˜ƒxxxxx] = new BlockUtil.IntBounds(getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ), â˜ƒxxx, â˜ƒ), getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ), â˜ƒxxxx, â˜ƒ));
      int â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx[â˜ƒxxxxx].min;

      for(int â˜ƒxxxxxxxxxx = 1; â˜ƒxxxxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxxxxx) {
         BlockUtil.IntBounds â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx[â˜ƒxxxxxxx - (â˜ƒxxxxxxxxxx - 1)];
         â˜ƒxxxxxxxx[â˜ƒxxxxxxx - â˜ƒxxxxxxxxxx] = new BlockUtil.IntBounds(
            getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ).move(â˜ƒx, â˜ƒxxxxxxxxxx), â˜ƒxxx, â˜ƒxxxxxxxxxxx.min),
            getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ).move(â˜ƒx, â˜ƒxxxxxxxxxx), â˜ƒxxxx, â˜ƒxxxxxxxxxxx.max)
         );
      }

      for(int â˜ƒxxxxxxxxxx = 1; â˜ƒxxxxxxxxxx <= â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxx) {
         BlockUtil.IntBounds â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx[â˜ƒxxxxxxx + â˜ƒxxxxxxxxxx - 1];
         â˜ƒxxxxxxxx[â˜ƒxxxxxxx + â˜ƒxxxxxxxxxx] = new BlockUtil.IntBounds(
            getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ).move(â˜ƒxx, â˜ƒxxxxxxxxxx), â˜ƒxxx, â˜ƒxxxxxxxxxxx.min),
            getLimit(â˜ƒ, â˜ƒ.set(â˜ƒ).move(â˜ƒxx, â˜ƒxxxxxxxxxx), â˜ƒxxxx, â˜ƒxxxxxxxxxxx.max)
         );
      }

      int â˜ƒxxxxxxxxxx = 0;
      int â˜ƒxxxxxxxxxxx = 0;
      int â˜ƒxxxxxxxxxxxx = 0;
      int â˜ƒxxxxxxxxxxxxx = 0;
      int[] â˜ƒxxxxxxxxxxxxxx = new int[â˜ƒxxxxxxxx.length];

      for(int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxx.length; ++â˜ƒxxxxxxxxxxxxxxxx) {
            BlockUtil.IntBounds â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx[â˜ƒxxxxxxxxxxxxxxxx];
            int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxx.min;
            int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxx.max;
            â˜ƒxxxxxxxxxxxxxx[â˜ƒxxxxxxxxxxxxxxxx] = â˜ƒxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxxx && â˜ƒxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxxxxxxx
               ? â˜ƒxxxxxxxxxxxxxxxxxxx + 1 - â˜ƒxxxxxxxxxxxxxxx
               : 0;
         }

         Pair<BlockUtil.IntBounds, Integer> â˜ƒxxxxxxxxxxxxxxxx = getMaxRectangleLocation(â˜ƒxxxxxxxxxxxxxx);
         BlockUtil.IntBounds â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.getFirst();
         int â˜ƒxxxxxxxxxxxxxxxxxx = 1 + â˜ƒxxxxxxxxxxxxxxxxx.max - â˜ƒxxxxxxxxxxxxxxxxx.min;
         int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.getSecond();
         if (â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxx > â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxx) {
            â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx.min;
            â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx;
         }
      }

      return new BlockUtil.FoundRectangle(
         â˜ƒ.relative(â˜ƒ, â˜ƒxxxxxxxxxx - â˜ƒxxxxxxx).relative(â˜ƒ, â˜ƒxxxxxxxxxxx - â˜ƒxxxxxxxxx), â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx
      );
   }

   private static int getLimit(Predicate<BlockPos> var0, BlockPos.MutableBlockPos var1, Direction var2, int var3) {
      int â˜ƒ = 0;

      while(â˜ƒ < â˜ƒ && â˜ƒ.test(â˜ƒ.move(â˜ƒ))) {
         ++â˜ƒ;
      }

      return â˜ƒ;
   }

   @VisibleForTesting
   static Pair<BlockUtil.IntBounds, Integer> getMaxRectangleLocation(int[] var0) {
      int â˜ƒ = 0;
      int â˜ƒx = 0;
      int â˜ƒxx = 0;
      IntStack â˜ƒxxx = new IntArrayList();
      â˜ƒxxx.push(0);

      for(int â˜ƒxxxx = 1; â˜ƒxxxx <= â˜ƒ.length; ++â˜ƒxxxx) {
         int â˜ƒxxxxx = â˜ƒxxxx == â˜ƒ.length ? 0 : â˜ƒ[â˜ƒxxxx];

         while(!â˜ƒxxx.isEmpty()) {
            int â˜ƒxxxxxx = â˜ƒ[â˜ƒxxx.topInt()];
            if (â˜ƒxxxxx >= â˜ƒxxxxxx) {
               â˜ƒxxx.push(â˜ƒxxxx);
               break;
            }

            â˜ƒxxx.popInt();
            int â˜ƒxxxxxx = â˜ƒxxx.isEmpty() ? 0 : â˜ƒxxx.topInt() + 1;
            if (â˜ƒxxxxxx * (â˜ƒxxxx - â˜ƒxxxxxx) > â˜ƒxx * (â˜ƒx - â˜ƒ)) {
               â˜ƒx = â˜ƒxxxx;
               â˜ƒ = â˜ƒxxxxxx;
               â˜ƒxx = â˜ƒxxxxxx;
            }
         }

         if (â˜ƒxxx.isEmpty()) {
            â˜ƒxxx.push(â˜ƒxxxx);
         }
      }

      return new Pair<>(new BlockUtil.IntBounds(â˜ƒ, â˜ƒx - 1), â˜ƒxx);
   }

   public static Optional<BlockPos> getTopConnectedBlock(BlockGetter var0, BlockPos var1, Block var2, Direction var3, Block var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      BlockState â˜ƒ;
      do {
         â˜ƒ.move(â˜ƒ);
         â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      } while(â˜ƒ.is(â˜ƒ));

      return â˜ƒ.is(â˜ƒ) ? Optional.of(â˜ƒ) : Optional.empty();
   }

   public static class FoundRectangle {
      public final BlockPos minCorner;
      public final int axis1Size;
      public final int axis2Size;

      public FoundRectangle(BlockPos var1, int var2, int var3) {
         this.minCorner = â˜ƒ;
         this.axis1Size = â˜ƒ;
         this.axis2Size = â˜ƒ;
      }
   }

   public static class IntBounds {
      public final int min;
      public final int max;

      public IntBounds(int var1, int var2) {
         this.min = â˜ƒ;
         this.max = â˜ƒ;
      }

      public String toString() {
         return "IntBounds{min=" + this.min + ", max=" + this.max + "}";
      }
   }
}
