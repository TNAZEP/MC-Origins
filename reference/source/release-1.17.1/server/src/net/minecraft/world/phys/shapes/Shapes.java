package net.minecraft.world.phys.shapes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public final class Shapes {
   public static final double EPSILON = 1.0E-7;
   public static final double BIG_EPSILON = 1.0E-6;
   private static final VoxelShape BLOCK = Util.make(() -> {
      DiscreteVoxelShape â˜ƒ = new BitSetDiscreteVoxelShape(1, 1, 1);
      â˜ƒ.fill(0, 0, 0);
      return new CubeVoxelShape(â˜ƒ);
   });
   public static final VoxelShape INFINITY = box(
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY
   );
   private static final VoxelShape EMPTY = new ArrayVoxelShape(
      new BitSetDiscreteVoxelShape(0, 0, 0),
      new DoubleArrayList(new double[]{0.0}),
      new DoubleArrayList(new double[]{0.0}),
      new DoubleArrayList(new double[]{0.0})
   );

   public static VoxelShape empty() {
      return EMPTY;
   }

   public static VoxelShape block() {
      return BLOCK;
   }

   public static VoxelShape box(double var0, double var2, double var4, double var6, double var8, double var10) {
      if (!(â˜ƒ > â˜ƒ) && !(â˜ƒ > â˜ƒ) && !(â˜ƒ > â˜ƒ)) {
         return create(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         throw new IllegalArgumentException("The min values need to be smaller or equals to the max values");
      }
   }

   public static VoxelShape create(double var0, double var2, double var4, double var6, double var8, double var10) {
      if (!(â˜ƒ - â˜ƒ < 1.0E-7) && !(â˜ƒ - â˜ƒ < 1.0E-7) && !(â˜ƒ - â˜ƒ < 1.0E-7)) {
         int â˜ƒ = findBits(â˜ƒ, â˜ƒ);
         int â˜ƒx = findBits(â˜ƒ, â˜ƒ);
         int â˜ƒxx = findBits(â˜ƒ, â˜ƒ);
         if (â˜ƒ < 0 || â˜ƒx < 0 || â˜ƒxx < 0) {
            return new ArrayVoxelShape(
               BLOCK.shape,
               DoubleArrayList.wrap(new double[]{â˜ƒ, â˜ƒ}),
               DoubleArrayList.wrap(new double[]{â˜ƒ, â˜ƒ}),
               DoubleArrayList.wrap(new double[]{â˜ƒ, â˜ƒ})
            );
         } else if (â˜ƒ == 0 && â˜ƒx == 0 && â˜ƒxx == 0) {
            return block();
         } else {
            int â˜ƒ = 1 << â˜ƒ;
            int â˜ƒx = 1 << â˜ƒx;
            int â˜ƒxx = 1 << â˜ƒxx;
            BitSetDiscreteVoxelShape â˜ƒxxx = BitSetDiscreteVoxelShape.withFilledBounds(
               â˜ƒ,
               â˜ƒx,
               â˜ƒxx,
               (int)Math.round(â˜ƒ * (double)â˜ƒ),
               (int)Math.round(â˜ƒ * (double)â˜ƒx),
               (int)Math.round(â˜ƒ * (double)â˜ƒxx),
               (int)Math.round(â˜ƒ * (double)â˜ƒ),
               (int)Math.round(â˜ƒ * (double)â˜ƒx),
               (int)Math.round(â˜ƒ * (double)â˜ƒxx)
            );
            return new CubeVoxelShape(â˜ƒxxx);
         }
      } else {
         return empty();
      }
   }

   public static VoxelShape create(AABB var0) {
      return create(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ);
   }

   @VisibleForTesting
   protected static int findBits(double var0, double var2) {
      if (!(â˜ƒ < -1.0E-7) && !(â˜ƒ > 1.0000001)) {
         for(int â˜ƒ = 0; â˜ƒ <= 3; ++â˜ƒ) {
            int â˜ƒx = 1 << â˜ƒ;
            double â˜ƒxx = â˜ƒ * (double)â˜ƒx;
            double â˜ƒxxx = â˜ƒ * (double)â˜ƒx;
            boolean â˜ƒxxxx = Math.abs(â˜ƒxx - (double)Math.round(â˜ƒxx)) < 1.0E-7 * (double)â˜ƒx;
            boolean â˜ƒxxxxx = Math.abs(â˜ƒxxx - (double)Math.round(â˜ƒxxx)) < 1.0E-7 * (double)â˜ƒx;
            if (â˜ƒxxxx && â˜ƒxxxxx) {
               return â˜ƒ;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   protected static long lcm(int var0, int var1) {
      return (long)â˜ƒ * (long)(â˜ƒ / IntMath.gcd(â˜ƒ, â˜ƒ));
   }

   public static VoxelShape or(VoxelShape var0, VoxelShape var1) {
      return join(â˜ƒ, â˜ƒ, BooleanOp.OR);
   }

   public static VoxelShape or(VoxelShape var0, VoxelShape... var1) {
      return (VoxelShape)Arrays.stream(â˜ƒ).reduce(â˜ƒ, Shapes::or);
   }

   public static VoxelShape join(VoxelShape var0, VoxelShape var1, BooleanOp var2) {
      return joinUnoptimized(â˜ƒ, â˜ƒ, â˜ƒ).optimize();
   }

   public static VoxelShape joinUnoptimized(VoxelShape var0, VoxelShape var1, BooleanOp var2) {
      if (â˜ƒ.apply(false, false)) {
         throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException());
      } else if (â˜ƒ == â˜ƒ) {
         return â˜ƒ.apply(true, true) ? â˜ƒ : empty();
      } else {
         boolean â˜ƒ = â˜ƒ.apply(true, false);
         boolean â˜ƒx = â˜ƒ.apply(false, true);
         if (â˜ƒ.isEmpty()) {
            return â˜ƒx ? â˜ƒ : empty();
         } else if (â˜ƒ.isEmpty()) {
            return â˜ƒ ? â˜ƒ : empty();
         } else {
            IndexMerger â˜ƒ = createIndexMerger(1, â˜ƒ.getCoords(Direction.Axis.X), â˜ƒ.getCoords(Direction.Axis.X), â˜ƒ, â˜ƒx);
            IndexMerger â˜ƒx = createIndexMerger(â˜ƒ.size() - 1, â˜ƒ.getCoords(Direction.Axis.Y), â˜ƒ.getCoords(Direction.Axis.Y), â˜ƒ, â˜ƒx);
            IndexMerger â˜ƒxx = createIndexMerger(
               (â˜ƒ.size() - 1) * (â˜ƒx.size() - 1), â˜ƒ.getCoords(Direction.Axis.Z), â˜ƒ.getCoords(Direction.Axis.Z), â˜ƒ, â˜ƒx
            );
            BitSetDiscreteVoxelShape â˜ƒxxx = BitSetDiscreteVoxelShape.join(â˜ƒ.shape, â˜ƒ.shape, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ);
            return (VoxelShape)(â˜ƒ instanceof DiscreteCubeMerger && â˜ƒx instanceof DiscreteCubeMerger && â˜ƒxx instanceof DiscreteCubeMerger
               ? new CubeVoxelShape(â˜ƒxxx)
               : new ArrayVoxelShape(â˜ƒxxx, â˜ƒ.getList(), â˜ƒx.getList(), â˜ƒxx.getList()));
         }
      }
   }

   public static boolean joinIsNotEmpty(VoxelShape var0, VoxelShape var1, BooleanOp var2) {
      if (â˜ƒ.apply(false, false)) {
         throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException());
      } else {
         boolean â˜ƒ = â˜ƒ.isEmpty();
         boolean â˜ƒx = â˜ƒ.isEmpty();
         if (!â˜ƒ && !â˜ƒx) {
            if (â˜ƒ == â˜ƒ) {
               return â˜ƒ.apply(true, true);
            } else {
               boolean â˜ƒxx = â˜ƒ.apply(true, false);
               boolean â˜ƒxxx = â˜ƒ.apply(false, true);

               for(Direction.Axis â˜ƒxxxx : AxisCycle.AXIS_VALUES) {
                  if (â˜ƒ.max(â˜ƒxxxx) < â˜ƒ.min(â˜ƒxxxx) - 1.0E-7) {
                     return â˜ƒxx || â˜ƒxxx;
                  }

                  if (â˜ƒ.max(â˜ƒxxxx) < â˜ƒ.min(â˜ƒxxxx) - 1.0E-7) {
                     return â˜ƒxx || â˜ƒxxx;
                  }
               }

               IndexMerger â˜ƒxxxx = createIndexMerger(1, â˜ƒ.getCoords(Direction.Axis.X), â˜ƒ.getCoords(Direction.Axis.X), â˜ƒxx, â˜ƒxxx);
               IndexMerger â˜ƒxxxxx = createIndexMerger(â˜ƒxxxx.size() - 1, â˜ƒ.getCoords(Direction.Axis.Y), â˜ƒ.getCoords(Direction.Axis.Y), â˜ƒxx, â˜ƒxxx);
               IndexMerger â˜ƒxxxxxx = createIndexMerger(
                  (â˜ƒxxxx.size() - 1) * (â˜ƒxxxxx.size() - 1), â˜ƒ.getCoords(Direction.Axis.Z), â˜ƒ.getCoords(Direction.Axis.Z), â˜ƒxx, â˜ƒxxx
               );
               return joinIsNotEmpty(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ.shape, â˜ƒ.shape, â˜ƒ);
            }
         } else {
            return â˜ƒ.apply(!â˜ƒ, !â˜ƒx);
         }
      }
   }

   private static boolean joinIsNotEmpty(IndexMerger var0, IndexMerger var1, IndexMerger var2, DiscreteVoxelShape var3, DiscreteVoxelShape var4, BooleanOp var5) {
      return !â˜ƒ.forMergedIndexes(
         (var5x, var6, var7) -> â˜ƒ.forMergedIndexes(
               (var6x, var7x, var8) -> â˜ƒ.forMergedIndexes(
                     (var7xx, var8x, var9) -> !â˜ƒ.apply(â˜ƒ.isFullWide(var5x, var6x, var7xx), â˜ƒ.isFullWide(var6, var7x, var8x))
                  )
            )
      );
   }

   public static double collide(Direction.Axis var0, AABB var1, Stream<VoxelShape> var2, double var3) {
      for(Iterator<VoxelShape> â˜ƒ = â˜ƒ.iterator(); â˜ƒ.hasNext(); â˜ƒ = ((VoxelShape)â˜ƒ.next()).collide(â˜ƒ, â˜ƒ, â˜ƒ)) {
         if (Math.abs(â˜ƒ) < 1.0E-7) {
            return 0.0;
         }
      }

      return â˜ƒ;
   }

   public static double collide(Direction.Axis var0, AABB var1, LevelReader var2, double var3, CollisionContext var5, Stream<VoxelShape> var6) {
      return collide(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, AxisCycle.between(â˜ƒ, Direction.Axis.Z), â˜ƒ);
   }

   private static double collide(AABB var0, LevelReader var1, double var2, CollisionContext var4, AxisCycle var5, Stream<VoxelShape> var6) {
      if (â˜ƒ.getXsize() < 1.0E-6 || â˜ƒ.getYsize() < 1.0E-6 || â˜ƒ.getZsize() < 1.0E-6) {
         return â˜ƒ;
      } else if (Math.abs(â˜ƒ) < 1.0E-7) {
         return 0.0;
      } else {
         AxisCycle â˜ƒ = â˜ƒ.inverse();
         Direction.Axis â˜ƒx = â˜ƒ.cycle(Direction.Axis.X);
         Direction.Axis â˜ƒxx = â˜ƒ.cycle(Direction.Axis.Y);
         Direction.Axis â˜ƒxxx = â˜ƒ.cycle(Direction.Axis.Z);
         BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();
         int â˜ƒxxxxx = Mth.floor(â˜ƒ.min(â˜ƒx) - 1.0E-7) - 1;
         int â˜ƒxxxxxx = Mth.floor(â˜ƒ.max(â˜ƒx) + 1.0E-7) + 1;
         int â˜ƒxxxxxxx = Mth.floor(â˜ƒ.min(â˜ƒxx) - 1.0E-7) - 1;
         int â˜ƒxxxxxxxx = Mth.floor(â˜ƒ.max(â˜ƒxx) + 1.0E-7) + 1;
         double â˜ƒxxxxxxxxx = â˜ƒ.min(â˜ƒxxx) - 1.0E-7;
         double â˜ƒxxxxxxxxxx = â˜ƒ.max(â˜ƒxxx) + 1.0E-7;
         boolean â˜ƒxxxxxxxxxxx = â˜ƒ > 0.0;
         int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx ? Mth.floor(â˜ƒ.max(â˜ƒxxx) - 1.0E-7) - 1 : Mth.floor(â˜ƒ.min(â˜ƒxxx) + 1.0E-7) + 1;
         int â˜ƒxxxxxxxxxxxxx = lastC(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
         int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx ? 1 : -1;

         for(int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxx ? â˜ƒxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxxxxxx += â˜ƒxxxxxxxxxxxxxx
         ) {
            for(int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxxxxxxxxxxx <= â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxx) {
                  int â˜ƒxxxxxxxxxxxxxxxxxx = 0;
                  if (â˜ƒxxxxxxxxxxxxxxxx == â˜ƒxxxxx || â˜ƒxxxxxxxxxxxxxxxx == â˜ƒxxxxxx) {
                     ++â˜ƒxxxxxxxxxxxxxxxxxx;
                  }

                  if (â˜ƒxxxxxxxxxxxxxxxxx == â˜ƒxxxxxxx || â˜ƒxxxxxxxxxxxxxxxxx == â˜ƒxxxxxxxx) {
                     ++â˜ƒxxxxxxxxxxxxxxxxxx;
                  }

                  if (â˜ƒxxxxxxxxxxxxxxx == â˜ƒxxxxxxxxxxxx || â˜ƒxxxxxxxxxxxxxxx == â˜ƒxxxxxxxxxxxxx) {
                     ++â˜ƒxxxxxxxxxxxxxxxxxx;
                  }

                  if (â˜ƒxxxxxxxxxxxxxxxxxx < 3) {
                     â˜ƒxxxx.set(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
                     BlockState â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
                     if ((â˜ƒxxxxxxxxxxxxxxxxxx != 1 || â˜ƒxxxxxxxxxxxxxxxxxx.hasLargeCollisionShape())
                        && (â˜ƒxxxxxxxxxxxxxxxxxx != 2 || â˜ƒxxxxxxxxxxxxxxxxxx.is(Blocks.MOVING_PISTON))) {
                        â˜ƒ = â˜ƒxxxxxxxxxxxxxxxxxx.getCollisionShape(â˜ƒ, â˜ƒxxxx, â˜ƒ)
                           .collide(â˜ƒxxx, â˜ƒ.move((double)(-â˜ƒxxxx.getX()), (double)(-â˜ƒxxxx.getY()), (double)(-â˜ƒxxxx.getZ())), â˜ƒ);
                        if (Math.abs(â˜ƒ) < 1.0E-7) {
                           return 0.0;
                        }

                        â˜ƒxxxxxxxxxxxxx = lastC(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
                     }
                  }
               }
            }
         }

         double[] â˜ƒxxxxxxxxxxxxxxx = new double[]{â˜ƒ};
         â˜ƒ.forEach(var3 -> â˜ƒ[0] = var3.collide(â˜ƒ, â˜ƒ, â˜ƒ[0]));
         return â˜ƒxxxxxxxxxxxxxxx[0];
      }
   }

   private static int lastC(double var0, double var2, double var4) {
      return â˜ƒ > 0.0 ? Mth.floor(â˜ƒ + â˜ƒ) + 1 : Mth.floor(â˜ƒ + â˜ƒ) - 1;
   }

   public static boolean blockOccudes(VoxelShape var0, VoxelShape var1, Direction var2) {
      if (â˜ƒ == block() && â˜ƒ == block()) {
         return true;
      } else if (â˜ƒ.isEmpty()) {
         return false;
      } else {
         Direction.Axis â˜ƒ = â˜ƒ.getAxis();
         Direction.AxisDirection â˜ƒx = â˜ƒ.getAxisDirection();
         VoxelShape â˜ƒxx = â˜ƒx == Direction.AxisDirection.POSITIVE ? â˜ƒ : â˜ƒ;
         VoxelShape â˜ƒxxx = â˜ƒx == Direction.AxisDirection.POSITIVE ? â˜ƒ : â˜ƒ;
         BooleanOp â˜ƒxxxx = â˜ƒx == Direction.AxisDirection.POSITIVE ? BooleanOp.ONLY_FIRST : BooleanOp.ONLY_SECOND;
         return DoubleMath.fuzzyEquals(â˜ƒxx.max(â˜ƒ), 1.0, 1.0E-7)
            && DoubleMath.fuzzyEquals(â˜ƒxxx.min(â˜ƒ), 0.0, 1.0E-7)
            && !joinIsNotEmpty(new SliceShape(â˜ƒxx, â˜ƒ, â˜ƒxx.shape.getSize(â˜ƒ) - 1), new SliceShape(â˜ƒxxx, â˜ƒ, 0), â˜ƒxxxx);
      }
   }

   public static VoxelShape getFaceShape(VoxelShape var0, Direction var1) {
      if (â˜ƒ == block()) {
         return block();
      } else {
         Direction.Axis â˜ƒxx = â˜ƒ.getAxis();
         boolean â˜ƒ;
         int â˜ƒx;
         if (â˜ƒ.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
            â˜ƒ = DoubleMath.fuzzyEquals(â˜ƒ.max(â˜ƒxx), 1.0, 1.0E-7);
            â˜ƒx = â˜ƒ.shape.getSize(â˜ƒxx) - 1;
         } else {
            â˜ƒ = DoubleMath.fuzzyEquals(â˜ƒ.min(â˜ƒxx), 0.0, 1.0E-7);
            â˜ƒx = 0;
         }

         return (VoxelShape)(!â˜ƒ ? empty() : new SliceShape(â˜ƒ, â˜ƒxx, â˜ƒx));
      }
   }

   public static boolean mergedFaceOccludes(VoxelShape var0, VoxelShape var1, Direction var2) {
      if (â˜ƒ != block() && â˜ƒ != block()) {
         Direction.Axis â˜ƒ = â˜ƒ.getAxis();
         Direction.AxisDirection â˜ƒx = â˜ƒ.getAxisDirection();
         VoxelShape â˜ƒxx = â˜ƒx == Direction.AxisDirection.POSITIVE ? â˜ƒ : â˜ƒ;
         VoxelShape â˜ƒxxx = â˜ƒx == Direction.AxisDirection.POSITIVE ? â˜ƒ : â˜ƒ;
         if (!DoubleMath.fuzzyEquals(â˜ƒxx.max(â˜ƒ), 1.0, 1.0E-7)) {
            â˜ƒxx = empty();
         }

         if (!DoubleMath.fuzzyEquals(â˜ƒxxx.min(â˜ƒ), 0.0, 1.0E-7)) {
            â˜ƒxxx = empty();
         }

         return !joinIsNotEmpty(
            block(),
            joinUnoptimized(new SliceShape(â˜ƒxx, â˜ƒ, â˜ƒxx.shape.getSize(â˜ƒ) - 1), new SliceShape(â˜ƒxxx, â˜ƒ, 0), BooleanOp.OR),
            BooleanOp.ONLY_FIRST
         );
      } else {
         return true;
      }
   }

   public static boolean faceShapeOccludes(VoxelShape var0, VoxelShape var1) {
      if (â˜ƒ == block() || â˜ƒ == block()) {
         return true;
      } else if (â˜ƒ.isEmpty() && â˜ƒ.isEmpty()) {
         return false;
      } else {
         return !joinIsNotEmpty(block(), joinUnoptimized(â˜ƒ, â˜ƒ, BooleanOp.OR), BooleanOp.ONLY_FIRST);
      }
   }

   @VisibleForTesting
   protected static IndexMerger createIndexMerger(int var0, DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      int â˜ƒ = â˜ƒ.size() - 1;
      int â˜ƒx = â˜ƒ.size() - 1;
      if (â˜ƒ instanceof CubePointRange && â˜ƒ instanceof CubePointRange) {
         long â˜ƒxx = lcm(â˜ƒ, â˜ƒx);
         if ((long)â˜ƒ * â˜ƒxx <= 256L) {
            return new DiscreteCubeMerger(â˜ƒ, â˜ƒx);
         }
      }

      if (â˜ƒ.getDouble(â˜ƒ) < â˜ƒ.getDouble(0) - 1.0E-7) {
         return new NonOverlappingMerger(â˜ƒ, â˜ƒ, false);
      } else if (â˜ƒ.getDouble(â˜ƒx) < â˜ƒ.getDouble(0) - 1.0E-7) {
         return new NonOverlappingMerger(â˜ƒ, â˜ƒ, true);
      } else {
         return (IndexMerger)(â˜ƒ == â˜ƒx && Objects.equals(â˜ƒ, â˜ƒ) ? new IdenticalMerger(â˜ƒ) : new IndirectMerger(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }
   }

   public interface DoubleLineConsumer {
      void consume(double var1, double var3, double var5, double var7, double var9, double var11);
   }
}
