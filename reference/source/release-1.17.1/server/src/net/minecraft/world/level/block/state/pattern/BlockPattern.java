package net.minecraft.world.level.block.state.pattern;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelReader;

public class BlockPattern {
   private final Predicate<BlockInWorld>[][][] pattern;
   private final int depth;
   private final int height;
   private final int width;

   public BlockPattern(Predicate<BlockInWorld>[][][] var1) {
      this.pattern = â˜ƒ;
      this.depth = â˜ƒ.length;
      if (this.depth > 0) {
         this.height = â˜ƒ[0].length;
         if (this.height > 0) {
            this.width = â˜ƒ[0][0].length;
         } else {
            this.width = 0;
         }
      } else {
         this.height = 0;
         this.width = 0;
      }
   }

   public int getDepth() {
      return this.depth;
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   @VisibleForTesting
   public Predicate<BlockInWorld>[][][] getPattern() {
      return this.pattern;
   }

   @Nullable
   @VisibleForTesting
   public BlockPattern.BlockPatternMatch matches(LevelReader var1, BlockPos var2, Direction var3, Direction var4) {
      LoadingCache<BlockPos, BlockInWorld> â˜ƒ = createLevelCache(â˜ƒ, false);
      return this.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   private BlockPattern.BlockPatternMatch matches(BlockPos var1, Direction var2, Direction var3, LoadingCache<BlockPos, BlockInWorld> var4) {
      for(int â˜ƒ = 0; â˜ƒ < this.width; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < this.height; ++â˜ƒx) {
            for(int â˜ƒxx = 0; â˜ƒxx < this.depth; ++â˜ƒxx) {
               if (!this.pattern[â˜ƒxx][â˜ƒx][â˜ƒ].test(â˜ƒ.getUnchecked(translateAndRotate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.BlockPatternMatch(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.width, this.height, this.depth);
   }

   @Nullable
   public BlockPattern.BlockPatternMatch find(LevelReader var1, BlockPos var2) {
      LoadingCache<BlockPos, BlockInWorld> â˜ƒ = createLevelCache(â˜ƒ, false);
      int â˜ƒx = Math.max(Math.max(this.width, this.height), this.depth);

      for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒ, â˜ƒ.offset(â˜ƒx - 1, â˜ƒx - 1, â˜ƒx - 1))) {
         for(Direction â˜ƒxxx : Direction.values()) {
            for(Direction â˜ƒxxxx : Direction.values()) {
               if (â˜ƒxxxx != â˜ƒxxx && â˜ƒxxxx != â˜ƒxxx.getOpposite()) {
                  BlockPattern.BlockPatternMatch â˜ƒxxxxx = this.matches(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒ);
                  if (â˜ƒxxxxx != null) {
                     return â˜ƒxxxxx;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, BlockInWorld> createLevelCache(LevelReader var0, boolean var1) {
      return CacheBuilder.newBuilder().build(new BlockPattern.BlockCacheLoader(â˜ƒ, â˜ƒ));
   }

   protected static BlockPos translateAndRotate(BlockPos var0, Direction var1, Direction var2, int var3, int var4, int var5) {
      if (â˜ƒ != â˜ƒ && â˜ƒ != â˜ƒ.getOpposite()) {
         Vec3i â˜ƒ = new Vec3i(â˜ƒ.getStepX(), â˜ƒ.getStepY(), â˜ƒ.getStepZ());
         Vec3i â˜ƒx = new Vec3i(â˜ƒ.getStepX(), â˜ƒ.getStepY(), â˜ƒ.getStepZ());
         Vec3i â˜ƒxx = â˜ƒ.cross(â˜ƒx);
         return â˜ƒ.offset(
            â˜ƒx.getX() * -â˜ƒ + â˜ƒxx.getX() * â˜ƒ + â˜ƒ.getX() * â˜ƒ,
            â˜ƒx.getY() * -â˜ƒ + â˜ƒxx.getY() * â˜ƒ + â˜ƒ.getY() * â˜ƒ,
            â˜ƒx.getZ() * -â˜ƒ + â˜ƒxx.getZ() * â˜ƒ + â˜ƒ.getZ() * â˜ƒ
         );
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class BlockCacheLoader extends CacheLoader<BlockPos, BlockInWorld> {
      private final LevelReader level;
      private final boolean loadChunks;

      public BlockCacheLoader(LevelReader var1, boolean var2) {
         this.level = â˜ƒ;
         this.loadChunks = â˜ƒ;
      }

      public BlockInWorld load(BlockPos var1) {
         return new BlockInWorld(this.level, â˜ƒ, this.loadChunks);
      }
   }

   public static class BlockPatternMatch {
      private final BlockPos frontTopLeft;
      private final Direction forwards;
      private final Direction up;
      private final LoadingCache<BlockPos, BlockInWorld> cache;
      private final int width;
      private final int height;
      private final int depth;

      public BlockPatternMatch(BlockPos var1, Direction var2, Direction var3, LoadingCache<BlockPos, BlockInWorld> var4, int var5, int var6, int var7) {
         this.frontTopLeft = â˜ƒ;
         this.forwards = â˜ƒ;
         this.up = â˜ƒ;
         this.cache = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.depth = â˜ƒ;
      }

      public BlockPos getFrontTopLeft() {
         return this.frontTopLeft;
      }

      public Direction getForwards() {
         return this.forwards;
      }

      public Direction getUp() {
         return this.up;
      }

      public int getWidth() {
         return this.width;
      }

      public int getHeight() {
         return this.height;
      }

      public int getDepth() {
         return this.depth;
      }

      public BlockInWorld getBlock(int var1, int var2, int var3) {
         return this.cache.getUnchecked(BlockPattern.translateAndRotate(this.frontTopLeft, this.getForwards(), this.getUp(), â˜ƒ, â˜ƒ, â˜ƒ));
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.up).add("forwards", this.forwards).add("frontTopLeft", this.frontTopLeft).toString();
      }
   }
}
