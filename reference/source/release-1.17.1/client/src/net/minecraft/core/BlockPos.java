package net.minecraft.core;

import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Immutable
public class BlockPos extends Vec3i {
   public static final Codec<BlockPos> CODEC = Codec.INT_STREAM
      .<BlockPos>comapFlatMap(
         var0 -> Util.fixedSize(var0, 3).map(var0x -> new BlockPos(var0x[0], var0x[1], var0x[2])),
         var0 -> IntStream.of(new int[]{var0.getX(), var0.getY(), var0.getZ()})
      )
      .stable();
   private static final Logger LOGGER = LogManager.getLogger();
   public static final BlockPos ZERO = new BlockPos(0, 0, 0);
   private static final int PACKED_X_LENGTH = 1 + Mth.log2(Mth.smallestEncompassingPowerOfTwo(30000000));
   private static final int PACKED_Z_LENGTH = PACKED_X_LENGTH;
   public static final int PACKED_Y_LENGTH = 64 - PACKED_X_LENGTH - PACKED_Z_LENGTH;
   private static final long PACKED_X_MASK = (1L << PACKED_X_LENGTH) - 1L;
   private static final long PACKED_Y_MASK = (1L << PACKED_Y_LENGTH) - 1L;
   private static final long PACKED_Z_MASK = (1L << PACKED_Z_LENGTH) - 1L;
   private static final int Y_OFFSET = 0;
   private static final int Z_OFFSET = PACKED_Y_LENGTH;
   private static final int X_OFFSET = PACKED_Y_LENGTH + PACKED_Z_LENGTH;

   public BlockPos(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public BlockPos(double var1, double var3, double var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public BlockPos(Vec3 var1) {
      this(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public BlockPos(Position var1) {
      this(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }

   public BlockPos(Vec3i var1) {
      this(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public static long offset(long var0, Direction var2) {
      return offset(â˜ƒ, â˜ƒ.getStepX(), â˜ƒ.getStepY(), â˜ƒ.getStepZ());
   }

   public static long offset(long var0, int var2, int var3, int var4) {
      return asLong(getX(â˜ƒ) + â˜ƒ, getY(â˜ƒ) + â˜ƒ, getZ(â˜ƒ) + â˜ƒ);
   }

   public static int getX(long var0) {
      return (int)(â˜ƒ << 64 - X_OFFSET - PACKED_X_LENGTH >> 64 - PACKED_X_LENGTH);
   }

   public static int getY(long var0) {
      return (int)(â˜ƒ << 64 - PACKED_Y_LENGTH >> 64 - PACKED_Y_LENGTH);
   }

   public static int getZ(long var0) {
      return (int)(â˜ƒ << 64 - Z_OFFSET - PACKED_Z_LENGTH >> 64 - PACKED_Z_LENGTH);
   }

   public static BlockPos of(long var0) {
      return new BlockPos(getX(â˜ƒ), getY(â˜ƒ), getZ(â˜ƒ));
   }

   public long asLong() {
      return asLong(this.getX(), this.getY(), this.getZ());
   }

   public static long asLong(int var0, int var1, int var2) {
      long â˜ƒ = 0L;
      â˜ƒ |= ((long)â˜ƒ & PACKED_X_MASK) << X_OFFSET;
      â˜ƒ |= ((long)â˜ƒ & PACKED_Y_MASK) << 0;
      return â˜ƒ | ((long)â˜ƒ & PACKED_Z_MASK) << Z_OFFSET;
   }

   public static long getFlatIndex(long var0) {
      return â˜ƒ & -16L;
   }

   public BlockPos offset(double var1, double var3, double var5) {
      return â˜ƒ == 0.0 && â˜ƒ == 0.0 && â˜ƒ == 0.0 ? this : new BlockPos((double)this.getX() + â˜ƒ, (double)this.getY() + â˜ƒ, (double)this.getZ() + â˜ƒ);
   }

   public BlockPos offset(int var1, int var2, int var3) {
      return â˜ƒ == 0 && â˜ƒ == 0 && â˜ƒ == 0 ? this : new BlockPos(this.getX() + â˜ƒ, this.getY() + â˜ƒ, this.getZ() + â˜ƒ);
   }

   public BlockPos offset(Vec3i var1) {
      return this.offset(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public BlockPos subtract(Vec3i var1) {
      return this.offset(-â˜ƒ.getX(), -â˜ƒ.getY(), -â˜ƒ.getZ());
   }

   public BlockPos multiply(int var1) {
      if (â˜ƒ == 1) {
         return this;
      } else {
         return â˜ƒ == 0 ? ZERO : new BlockPos(this.getX() * â˜ƒ, this.getY() * â˜ƒ, this.getZ() * â˜ƒ);
      }
   }

   public BlockPos above() {
      return this.relative(Direction.UP);
   }

   public BlockPos above(int var1) {
      return this.relative(Direction.UP, â˜ƒ);
   }

   public BlockPos below() {
      return this.relative(Direction.DOWN);
   }

   public BlockPos below(int var1) {
      return this.relative(Direction.DOWN, â˜ƒ);
   }

   public BlockPos north() {
      return this.relative(Direction.NORTH);
   }

   public BlockPos north(int var1) {
      return this.relative(Direction.NORTH, â˜ƒ);
   }

   public BlockPos south() {
      return this.relative(Direction.SOUTH);
   }

   public BlockPos south(int var1) {
      return this.relative(Direction.SOUTH, â˜ƒ);
   }

   public BlockPos west() {
      return this.relative(Direction.WEST);
   }

   public BlockPos west(int var1) {
      return this.relative(Direction.WEST, â˜ƒ);
   }

   public BlockPos east() {
      return this.relative(Direction.EAST);
   }

   public BlockPos east(int var1) {
      return this.relative(Direction.EAST, â˜ƒ);
   }

   public BlockPos relative(Direction var1) {
      return new BlockPos(this.getX() + â˜ƒ.getStepX(), this.getY() + â˜ƒ.getStepY(), this.getZ() + â˜ƒ.getStepZ());
   }

   public BlockPos relative(Direction var1, int var2) {
      return â˜ƒ == 0 ? this : new BlockPos(this.getX() + â˜ƒ.getStepX() * â˜ƒ, this.getY() + â˜ƒ.getStepY() * â˜ƒ, this.getZ() + â˜ƒ.getStepZ() * â˜ƒ);
   }

   public BlockPos relative(Direction.Axis var1, int var2) {
      if (â˜ƒ == 0) {
         return this;
      } else {
         int â˜ƒ = â˜ƒ == Direction.Axis.X ? â˜ƒ : 0;
         int â˜ƒx = â˜ƒ == Direction.Axis.Y ? â˜ƒ : 0;
         int â˜ƒxx = â˜ƒ == Direction.Axis.Z ? â˜ƒ : 0;
         return new BlockPos(this.getX() + â˜ƒ, this.getY() + â˜ƒx, this.getZ() + â˜ƒxx);
      }
   }

   public BlockPos rotate(Rotation var1) {
      switch(â˜ƒ) {
         case NONE:
         default:
            return this;
         case CLOCKWISE_90:
            return new BlockPos(-this.getZ(), this.getY(), this.getX());
         case CLOCKWISE_180:
            return new BlockPos(-this.getX(), this.getY(), -this.getZ());
         case COUNTERCLOCKWISE_90:
            return new BlockPos(this.getZ(), this.getY(), -this.getX());
      }
   }

   public BlockPos cross(Vec3i var1) {
      return new BlockPos(
         this.getY() * â˜ƒ.getZ() - this.getZ() * â˜ƒ.getY(),
         this.getZ() * â˜ƒ.getX() - this.getX() * â˜ƒ.getZ(),
         this.getX() * â˜ƒ.getY() - this.getY() * â˜ƒ.getX()
      );
   }

   public BlockPos atY(int var1) {
      return new BlockPos(this.getX(), â˜ƒ, this.getZ());
   }

   public BlockPos immutable() {
      return this;
   }

   public BlockPos.MutableBlockPos mutable() {
      return new BlockPos.MutableBlockPos(this.getX(), this.getY(), this.getZ());
   }

   public static Iterable<BlockPos> randomInCube(Random var0, int var1, BlockPos var2, int var3) {
      return randomBetweenClosed(â˜ƒ, â˜ƒ, â˜ƒ.getX() - â˜ƒ, â˜ƒ.getY() - â˜ƒ, â˜ƒ.getZ() - â˜ƒ, â˜ƒ.getX() + â˜ƒ, â˜ƒ.getY() + â˜ƒ, â˜ƒ.getZ() + â˜ƒ);
   }

   public static Iterable<BlockPos> randomBetweenClosed(Random var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int â˜ƒ = â˜ƒ - â˜ƒ + 1;
      int â˜ƒx = â˜ƒ - â˜ƒ + 1;
      int â˜ƒxx = â˜ƒ - â˜ƒ + 1;
      return () -> new AbstractIterator<BlockPos>() {
            final BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
            int counter = â˜ƒ;

            protected BlockPos computeNext() {
               if (this.counter <= 0) {
                  return this.endOfData();
               } else {
                  BlockPos â˜ƒ = this.nextPos.set(â˜ƒ + â˜ƒ.nextInt(â˜ƒ), â˜ƒ + â˜ƒ.nextInt(â˜ƒ), â˜ƒ + â˜ƒ.nextInt(â˜ƒ));
                  --this.counter;
                  return â˜ƒ;
               }
            }
         };
   }

   public static Iterable<BlockPos> withinManhattan(BlockPos var0, int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ + â˜ƒ + â˜ƒ;
      int â˜ƒx = â˜ƒ.getX();
      int â˜ƒxx = â˜ƒ.getY();
      int â˜ƒxxx = â˜ƒ.getZ();
      return () -> new AbstractIterator<BlockPos>() {
            private final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
            private int currentDepth;
            private int maxX;
            private int maxY;
            private int x;
            private int y;
            private boolean zMirror;

            protected BlockPos computeNext() {
               if (this.zMirror) {
                  this.zMirror = false;
                  this.cursor.setZ(â˜ƒ - (this.cursor.getZ() - â˜ƒ));
                  return this.cursor;
               } else {
                  BlockPos â˜ƒ;
                  for(â˜ƒ = null; â˜ƒ == null; ++this.y) {
                     if (this.y > this.maxY) {
                        ++this.x;
                        if (this.x > this.maxX) {
                           ++this.currentDepth;
                           if (this.currentDepth > â˜ƒ) {
                              return this.endOfData();
                           }

                           this.maxX = Math.min(â˜ƒ, this.currentDepth);
                           this.x = -this.maxX;
                        }

                        this.maxY = Math.min(â˜ƒ, this.currentDepth - Math.abs(this.x));
                        this.y = -this.maxY;
                     }

                     int â˜ƒ = this.x;
                     int â˜ƒx = this.y;
                     int â˜ƒxx = this.currentDepth - Math.abs(â˜ƒ) - Math.abs(â˜ƒx);
                     if (â˜ƒxx <= â˜ƒ) {
                        this.zMirror = â˜ƒxx != 0;
                        â˜ƒ = this.cursor.set(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒx, â˜ƒ + â˜ƒxx);
                     }
                  }

                  return â˜ƒ;
               }
            }
         };
   }

   public static Optional<BlockPos> findClosestMatch(BlockPos var0, int var1, int var2, Predicate<BlockPos> var3) {
      return withinManhattanStream(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).filter(â˜ƒ).findFirst();
   }

   public static Stream<BlockPos> withinManhattanStream(BlockPos var0, int var1, int var2, int var3) {
      return StreamSupport.stream(withinManhattan(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).spliterator(), false);
   }

   public static Iterable<BlockPos> betweenClosed(BlockPos var0, BlockPos var1) {
      return betweenClosed(
         Math.min(â˜ƒ.getX(), â˜ƒ.getX()),
         Math.min(â˜ƒ.getY(), â˜ƒ.getY()),
         Math.min(â˜ƒ.getZ(), â˜ƒ.getZ()),
         Math.max(â˜ƒ.getX(), â˜ƒ.getX()),
         Math.max(â˜ƒ.getY(), â˜ƒ.getY()),
         Math.max(â˜ƒ.getZ(), â˜ƒ.getZ())
      );
   }

   public static Stream<BlockPos> betweenClosedStream(BlockPos var0, BlockPos var1) {
      return StreamSupport.stream(betweenClosed(â˜ƒ, â˜ƒ).spliterator(), false);
   }

   public static Stream<BlockPos> betweenClosedStream(BoundingBox var0) {
      return betweenClosedStream(
         Math.min(â˜ƒ.minX(), â˜ƒ.maxX()),
         Math.min(â˜ƒ.minY(), â˜ƒ.maxY()),
         Math.min(â˜ƒ.minZ(), â˜ƒ.maxZ()),
         Math.max(â˜ƒ.minX(), â˜ƒ.maxX()),
         Math.max(â˜ƒ.minY(), â˜ƒ.maxY()),
         Math.max(â˜ƒ.minZ(), â˜ƒ.maxZ())
      );
   }

   public static Stream<BlockPos> betweenClosedStream(AABB var0) {
      return betweenClosedStream(Mth.floor(â˜ƒ.minX), Mth.floor(â˜ƒ.minY), Mth.floor(â˜ƒ.minZ), Mth.floor(â˜ƒ.maxX), Mth.floor(â˜ƒ.maxY), Mth.floor(â˜ƒ.maxZ));
   }

   public static Stream<BlockPos> betweenClosedStream(int var0, int var1, int var2, int var3, int var4, int var5) {
      return StreamSupport.stream(betweenClosed(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).spliterator(), false);
   }

   public static Iterable<BlockPos> betweenClosed(int var0, int var1, int var2, int var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ - â˜ƒ + 1;
      int â˜ƒx = â˜ƒ - â˜ƒ + 1;
      int â˜ƒxx = â˜ƒ - â˜ƒ + 1;
      int â˜ƒxxx = â˜ƒ * â˜ƒx * â˜ƒxx;
      return () -> new AbstractIterator<BlockPos>() {
            private final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
            private int index;

            protected BlockPos computeNext() {
               if (this.index == â˜ƒ) {
                  return this.endOfData();
               } else {
                  int â˜ƒ = this.index % â˜ƒ;
                  int â˜ƒx = this.index / â˜ƒ;
                  int â˜ƒxx = â˜ƒx % â˜ƒ;
                  int â˜ƒxxx = â˜ƒx / â˜ƒ;
                  ++this.index;
                  return this.cursor.set(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒxx, â˜ƒ + â˜ƒxxx);
               }
            }
         };
   }

   public static Iterable<BlockPos.MutableBlockPos> spiralAround(BlockPos var0, int var1, Direction var2, Direction var3) {
      Validate.validState(â˜ƒ.getAxis() != â˜ƒ.getAxis(), "The two directions cannot be on the same axis");
      return () -> new AbstractIterator<BlockPos.MutableBlockPos>() {
            private final Direction[] directions = new Direction[]{â˜ƒ, â˜ƒ, â˜ƒ.getOpposite(), â˜ƒ.getOpposite()};
            private final BlockPos.MutableBlockPos cursor = â˜ƒ.mutable().move(â˜ƒ);
            private final int legs = 4 * â˜ƒ;
            private int leg = -1;
            private int legSize;
            private int legIndex;
            private int lastX = this.cursor.getX();
            private int lastY = this.cursor.getY();
            private int lastZ = this.cursor.getZ();

            protected BlockPos.MutableBlockPos computeNext() {
               this.cursor.set(this.lastX, this.lastY, this.lastZ).move(this.directions[(this.leg + 4) % 4]);
               this.lastX = this.cursor.getX();
               this.lastY = this.cursor.getY();
               this.lastZ = this.cursor.getZ();
               if (this.legIndex >= this.legSize) {
                  if (this.leg >= this.legs) {
                     return this.endOfData();
                  }

                  ++this.leg;
                  this.legIndex = 0;
                  this.legSize = this.leg / 2 + 1;
               }

               ++this.legIndex;
               return this.cursor;
            }
         };
   }

   public static class MutableBlockPos extends BlockPos {
      public MutableBlockPos() {
         this(0, 0, 0);
      }

      public MutableBlockPos(int var1, int var2, int var3) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public MutableBlockPos(double var1, double var3, double var5) {
         this(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ), Mth.floor(â˜ƒ));
      }

      @Override
      public BlockPos offset(double var1, double var3, double var5) {
         return super.offset(â˜ƒ, â˜ƒ, â˜ƒ).immutable();
      }

      @Override
      public BlockPos offset(int var1, int var2, int var3) {
         return super.offset(â˜ƒ, â˜ƒ, â˜ƒ).immutable();
      }

      @Override
      public BlockPos multiply(int var1) {
         return super.multiply(â˜ƒ).immutable();
      }

      @Override
      public BlockPos relative(Direction var1, int var2) {
         return super.relative(â˜ƒ, â˜ƒ).immutable();
      }

      @Override
      public BlockPos relative(Direction.Axis var1, int var2) {
         return super.relative(â˜ƒ, â˜ƒ).immutable();
      }

      @Override
      public BlockPos rotate(Rotation var1) {
         return super.rotate(â˜ƒ).immutable();
      }

      public BlockPos.MutableBlockPos set(int var1, int var2, int var3) {
         this.setX(â˜ƒ);
         this.setY(â˜ƒ);
         this.setZ(â˜ƒ);
         return this;
      }

      public BlockPos.MutableBlockPos set(double var1, double var3, double var5) {
         return this.set(Mth.floor(â˜ƒ), Mth.floor(â˜ƒ), Mth.floor(â˜ƒ));
      }

      public BlockPos.MutableBlockPos set(Vec3i var1) {
         return this.set(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      }

      public BlockPos.MutableBlockPos set(long var1) {
         return this.set(getX(â˜ƒ), getY(â˜ƒ), getZ(â˜ƒ));
      }

      public BlockPos.MutableBlockPos set(AxisCycle var1, int var2, int var3, int var4) {
         return this.set(â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.X), â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.Y), â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.Z));
      }

      public BlockPos.MutableBlockPos setWithOffset(Vec3i var1, Direction var2) {
         return this.set(â˜ƒ.getX() + â˜ƒ.getStepX(), â˜ƒ.getY() + â˜ƒ.getStepY(), â˜ƒ.getZ() + â˜ƒ.getStepZ());
      }

      public BlockPos.MutableBlockPos setWithOffset(Vec3i var1, int var2, int var3, int var4) {
         return this.set(â˜ƒ.getX() + â˜ƒ, â˜ƒ.getY() + â˜ƒ, â˜ƒ.getZ() + â˜ƒ);
      }

      public BlockPos.MutableBlockPos setWithOffset(Vec3i var1, Vec3i var2) {
         return this.set(â˜ƒ.getX() + â˜ƒ.getX(), â˜ƒ.getY() + â˜ƒ.getY(), â˜ƒ.getZ() + â˜ƒ.getZ());
      }

      public BlockPos.MutableBlockPos move(Direction var1) {
         return this.move(â˜ƒ, 1);
      }

      public BlockPos.MutableBlockPos move(Direction var1, int var2) {
         return this.set(this.getX() + â˜ƒ.getStepX() * â˜ƒ, this.getY() + â˜ƒ.getStepY() * â˜ƒ, this.getZ() + â˜ƒ.getStepZ() * â˜ƒ);
      }

      public BlockPos.MutableBlockPos move(int var1, int var2, int var3) {
         return this.set(this.getX() + â˜ƒ, this.getY() + â˜ƒ, this.getZ() + â˜ƒ);
      }

      public BlockPos.MutableBlockPos move(Vec3i var1) {
         return this.set(this.getX() + â˜ƒ.getX(), this.getY() + â˜ƒ.getY(), this.getZ() + â˜ƒ.getZ());
      }

      public BlockPos.MutableBlockPos clamp(Direction.Axis var1, int var2, int var3) {
         switch(â˜ƒ) {
            case X:
               return this.set(Mth.clamp(this.getX(), â˜ƒ, â˜ƒ), this.getY(), this.getZ());
            case Y:
               return this.set(this.getX(), Mth.clamp(this.getY(), â˜ƒ, â˜ƒ), this.getZ());
            case Z:
               return this.set(this.getX(), this.getY(), Mth.clamp(this.getZ(), â˜ƒ, â˜ƒ));
            default:
               throw new IllegalStateException("Unable to clamp axis " + â˜ƒ);
         }
      }

      public BlockPos.MutableBlockPos setX(int var1) {
         super.setX(â˜ƒ);
         return this;
      }

      public BlockPos.MutableBlockPos setY(int var1) {
         super.setY(â˜ƒ);
         return this;
      }

      public BlockPos.MutableBlockPos setZ(int var1) {
         super.setZ(â˜ƒ);
         return this;
      }

      @Override
      public BlockPos immutable() {
         return new BlockPos(this);
      }
   }
}
