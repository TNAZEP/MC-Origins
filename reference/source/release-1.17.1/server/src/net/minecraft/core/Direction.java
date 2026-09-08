package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;

public enum Direction implements StringRepresentable {
   DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
   UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
   NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
   WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
   EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));

   public static final Codec<Direction> CODEC = StringRepresentable.fromEnum(Direction::values, Direction::byName);
   private final int data3d;
   private final int oppositeIndex;
   private final int data2d;
   private final String name;
   private final Direction.Axis axis;
   private final Direction.AxisDirection axisDirection;
   private final Vec3i normal;
   private static final Direction[] VALUES = values();
   private static final Map<String, Direction> BY_NAME = (Map<String, Direction>)Arrays.stream(VALUES)
      .collect(Collectors.toMap(Direction::getName, var0 -> var0));
   private static final Direction[] BY_3D_DATA = (Direction[])Arrays.stream(VALUES)
      .sorted(Comparator.comparingInt(var0 -> var0.data3d))
      .toArray(var0 -> new Direction[var0]);
   private static final Direction[] BY_2D_DATA = (Direction[])Arrays.stream(VALUES)
      .filter(var0 -> var0.getAxis().isHorizontal())
      .sorted(Comparator.comparingInt(var0 -> var0.data2d))
      .toArray(var0 -> new Direction[var0]);
   private static final Long2ObjectMap<Direction> BY_NORMAL = (Long2ObjectMap<Direction>)Arrays.stream(VALUES)
      .collect(Collectors.toMap(var0 -> new BlockPos(var0.getNormal()).asLong(), var0 -> var0, (var0, var1) -> {
         throw new IllegalArgumentException("Duplicate keys");
      }, Long2ObjectOpenHashMap::new));

   private Direction(int var3, int var4, int var5, String var6, Direction.AxisDirection var7, Direction.Axis var8, Vec3i var9) {
      this.data3d = â˜ƒ;
      this.data2d = â˜ƒ;
      this.oppositeIndex = â˜ƒ;
      this.name = â˜ƒ;
      this.axis = â˜ƒ;
      this.axisDirection = â˜ƒ;
      this.normal = â˜ƒ;
   }

   public static Direction[] orderedByNearest(Entity var0) {
      float â˜ƒ = â˜ƒ.getViewXRot(1.0F) * (float) (Math.PI / 180.0);
      float â˜ƒx = -â˜ƒ.getViewYRot(1.0F) * (float) (Math.PI / 180.0);
      float â˜ƒxx = Mth.sin(â˜ƒ);
      float â˜ƒxxx = Mth.cos(â˜ƒ);
      float â˜ƒxxxx = Mth.sin(â˜ƒx);
      float â˜ƒxxxxx = Mth.cos(â˜ƒx);
      boolean â˜ƒxxxxxx = â˜ƒxxxx > 0.0F;
      boolean â˜ƒxxxxxxx = â˜ƒxx < 0.0F;
      boolean â˜ƒxxxxxxxx = â˜ƒxxxxx > 0.0F;
      float â˜ƒxxxxxxxxx = â˜ƒxxxxxx ? â˜ƒxxxx : -â˜ƒxxxx;
      float â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx ? -â˜ƒxx : â˜ƒxx;
      float â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx ? â˜ƒxxxxx : -â˜ƒxxxxx;
      float â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx * â˜ƒxxx;
      float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx * â˜ƒxxx;
      Direction â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxx ? EAST : WEST;
      Direction â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxx ? UP : DOWN;
      Direction â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx ? SOUTH : NORTH;
      if (â˜ƒxxxxxxxxx > â˜ƒxxxxxxxxxxx) {
         if (â˜ƒxxxxxxxxxx > â˜ƒxxxxxxxxxxxx) {
            return makeDirectionArray(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
         } else {
            return â˜ƒxxxxxxxxxxxxx > â˜ƒxxxxxxxxxx
               ? makeDirectionArray(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)
               : makeDirectionArray(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
         }
      } else if (â˜ƒxxxxxxxxxx > â˜ƒxxxxxxxxxxxxx) {
         return makeDirectionArray(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
      } else {
         return â˜ƒxxxxxxxxxxxx > â˜ƒxxxxxxxxxx
            ? makeDirectionArray(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)
            : makeDirectionArray(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
      }
   }

   private static Direction[] makeDirectionArray(Direction var0, Direction var1, Direction var2) {
      return new Direction[]{â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getOpposite(), â˜ƒ.getOpposite(), â˜ƒ.getOpposite()};
   }

   public static Direction rotate(Matrix4f var0, Direction var1) {
      Vec3i â˜ƒ = â˜ƒ.getNormal();
      Vector4f â˜ƒx = new Vector4f((float)â˜ƒ.getX(), (float)â˜ƒ.getY(), (float)â˜ƒ.getZ(), 0.0F);
      â˜ƒx.transform(â˜ƒ);
      return getNearest(â˜ƒx.x(), â˜ƒx.y(), â˜ƒx.z());
   }

   public Quaternion getRotation() {
      Quaternion â˜ƒ = Vector3f.XP.rotationDegrees(90.0F);
      switch(this) {
         case DOWN:
            return Vector3f.XP.rotationDegrees(180.0F);
         case UP:
            return Quaternion.ONE.copy();
         case NORTH:
            â˜ƒ.mul(Vector3f.ZP.rotationDegrees(180.0F));
            return â˜ƒ;
         case SOUTH:
            return â˜ƒ;
         case WEST:
            â˜ƒ.mul(Vector3f.ZP.rotationDegrees(90.0F));
            return â˜ƒ;
         case EAST:
         default:
            â˜ƒ.mul(Vector3f.ZP.rotationDegrees(-90.0F));
            return â˜ƒ;
      }
   }

   public int get3DDataValue() {
      return this.data3d;
   }

   public int get2DDataValue() {
      return this.data2d;
   }

   public Direction.AxisDirection getAxisDirection() {
      return this.axisDirection;
   }

   public static Direction getFacingAxis(Entity var0, Direction.Axis var1) {
      switch(â˜ƒ) {
         case X:
            return EAST.isFacingAngle(â˜ƒ.getViewYRot(1.0F)) ? EAST : WEST;
         case Z:
            return SOUTH.isFacingAngle(â˜ƒ.getViewYRot(1.0F)) ? SOUTH : NORTH;
         case Y:
         default:
            return â˜ƒ.getViewXRot(1.0F) < 0.0F ? UP : DOWN;
      }
   }

   public Direction getOpposite() {
      return from3DDataValue(this.oppositeIndex);
   }

   public Direction getClockWise(Direction.Axis var1) {
      switch(â˜ƒ) {
         case X:
            if (this != WEST && this != EAST) {
               return this.getClockWiseX();
            }

            return this;
         case Z:
            if (this != NORTH && this != SOUTH) {
               return this.getClockWiseZ();
            }

            return this;
         case Y:
            if (this != UP && this != DOWN) {
               return this.getClockWise();
            }

            return this;
         default:
            throw new IllegalStateException("Unable to get CW facing for axis " + â˜ƒ);
      }
   }

   public Direction getCounterClockWise(Direction.Axis var1) {
      switch(â˜ƒ) {
         case X:
            if (this != WEST && this != EAST) {
               return this.getCounterClockWiseX();
            }

            return this;
         case Z:
            if (this != NORTH && this != SOUTH) {
               return this.getCounterClockWiseZ();
            }

            return this;
         case Y:
            if (this != UP && this != DOWN) {
               return this.getCounterClockWise();
            }

            return this;
         default:
            throw new IllegalStateException("Unable to get CW facing for axis " + â˜ƒ);
      }
   }

   public Direction getClockWise() {
      switch(this) {
         case NORTH:
            return EAST;
         case SOUTH:
            return WEST;
         case WEST:
            return NORTH;
         case EAST:
            return SOUTH;
         default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   private Direction getClockWiseX() {
      switch(this) {
         case DOWN:
            return SOUTH;
         case UP:
            return NORTH;
         case NORTH:
            return DOWN;
         case SOUTH:
            return UP;
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      }
   }

   private Direction getCounterClockWiseX() {
      switch(this) {
         case DOWN:
            return NORTH;
         case UP:
            return SOUTH;
         case NORTH:
            return UP;
         case SOUTH:
            return DOWN;
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      }
   }

   private Direction getClockWiseZ() {
      switch(this) {
         case DOWN:
            return WEST;
         case UP:
            return EAST;
         case NORTH:
         case SOUTH:
         default:
            throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case WEST:
            return UP;
         case EAST:
            return DOWN;
      }
   }

   private Direction getCounterClockWiseZ() {
      switch(this) {
         case DOWN:
            return EAST;
         case UP:
            return WEST;
         case NORTH:
         case SOUTH:
         default:
            throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case WEST:
            return DOWN;
         case EAST:
            return UP;
      }
   }

   public Direction getCounterClockWise() {
      switch(this) {
         case NORTH:
            return WEST;
         case SOUTH:
            return EAST;
         case WEST:
            return SOUTH;
         case EAST:
            return NORTH;
         default:
            throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int getStepX() {
      return this.normal.getX();
   }

   public int getStepY() {
      return this.normal.getY();
   }

   public int getStepZ() {
      return this.normal.getZ();
   }

   public Vector3f step() {
      return new Vector3f((float)this.getStepX(), (float)this.getStepY(), (float)this.getStepZ());
   }

   public String getName() {
      return this.name;
   }

   public Direction.Axis getAxis() {
      return this.axis;
   }

   @Nullable
   public static Direction byName(@Nullable String var0) {
      return â˜ƒ == null ? null : (Direction)BY_NAME.get(â˜ƒ.toLowerCase(Locale.ROOT));
   }

   public static Direction from3DDataValue(int var0) {
      return BY_3D_DATA[Mth.abs(â˜ƒ % BY_3D_DATA.length)];
   }

   public static Direction from2DDataValue(int var0) {
      return BY_2D_DATA[Mth.abs(â˜ƒ % BY_2D_DATA.length)];
   }

   @Nullable
   public static Direction fromNormal(BlockPos var0) {
      return BY_NORMAL.get(â˜ƒ.asLong());
   }

   @Nullable
   public static Direction fromNormal(int var0, int var1, int var2) {
      return BY_NORMAL.get(BlockPos.asLong(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static Direction fromYRot(double var0) {
      return from2DDataValue(Mth.floor(â˜ƒ / 90.0 + 0.5) & 3);
   }

   public static Direction fromAxisAndDirection(Direction.Axis var0, Direction.AxisDirection var1) {
      switch(â˜ƒ) {
         case X:
            return â˜ƒ == Direction.AxisDirection.POSITIVE ? EAST : WEST;
         case Z:
         default:
            return â˜ƒ == Direction.AxisDirection.POSITIVE ? SOUTH : NORTH;
         case Y:
            return â˜ƒ == Direction.AxisDirection.POSITIVE ? UP : DOWN;
      }
   }

   public float toYRot() {
      return (float)((this.data2d & 3) * 90);
   }

   public static Direction getRandom(Random var0) {
      return Util.getRandom(VALUES, â˜ƒ);
   }

   public static Direction getNearest(double var0, double var2, double var4) {
      return getNearest((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ);
   }

   public static Direction getNearest(float var0, float var1, float var2) {
      Direction â˜ƒ = NORTH;
      float â˜ƒx = Float.MIN_VALUE;

      for(Direction â˜ƒxx : VALUES) {
         float â˜ƒxxx = â˜ƒ * (float)â˜ƒxx.normal.getX() + â˜ƒ * (float)â˜ƒxx.normal.getY() + â˜ƒ * (float)â˜ƒxx.normal.getZ();
         if (â˜ƒxxx > â˜ƒx) {
            â˜ƒx = â˜ƒxxx;
            â˜ƒ = â˜ƒxx;
         }
      }

      return â˜ƒ;
   }

   public String toString() {
      return this.name;
   }

   @Override
   public String getSerializedName() {
      return this.name;
   }

   public static Direction get(Direction.AxisDirection var0, Direction.Axis var1) {
      for(Direction â˜ƒ : VALUES) {
         if (â˜ƒ.getAxisDirection() == â˜ƒ && â˜ƒ.getAxis() == â˜ƒ) {
            return â˜ƒ;
         }
      }

      throw new IllegalArgumentException("No such direction: " + â˜ƒ + " " + â˜ƒ);
   }

   public Vec3i getNormal() {
      return this.normal;
   }

   public boolean isFacingAngle(float var1) {
      float â˜ƒ = â˜ƒ * (float) (Math.PI / 180.0);
      float â˜ƒx = -Mth.sin(â˜ƒ);
      float â˜ƒxx = Mth.cos(â˜ƒ);
      return (float)this.normal.getX() * â˜ƒx + (float)this.normal.getZ() * â˜ƒxx > 0.0F;
   }

   public static enum Axis implements StringRepresentable, Predicate<Direction> {
      X("x") {
         @Override
         public int choose(int var1, int var2, int var3) {
            return â˜ƒ;
         }

         @Override
         public double choose(double var1, double var3, double var5) {
            return â˜ƒ;
         }
      },
      Y("y") {
         @Override
         public int choose(int var1, int var2, int var3) {
            return â˜ƒ;
         }

         @Override
         public double choose(double var1, double var3, double var5) {
            return â˜ƒ;
         }
      },
      Z("z") {
         @Override
         public int choose(int var1, int var2, int var3) {
            return â˜ƒ;
         }

         @Override
         public double choose(double var1, double var3, double var5) {
            return â˜ƒ;
         }
      };

      public static final Direction.Axis[] VALUES = values();
      public static final Codec<Direction.Axis> CODEC = StringRepresentable.fromEnum(Direction.Axis::values, Direction.Axis::byName);
      private static final Map<String, Direction.Axis> BY_NAME = (Map<String, Direction.Axis>)Arrays.stream(VALUES)
         .collect(Collectors.toMap(Direction.Axis::getName, var0 -> var0));
      private final String name;

      Axis(String var3) {
         this.name = â˜ƒ;
      }

      @Nullable
      public static Direction.Axis byName(String var0) {
         return (Direction.Axis)BY_NAME.get(â˜ƒ.toLowerCase(Locale.ROOT));
      }

      public String getName() {
         return this.name;
      }

      public boolean isVertical() {
         return this == Y;
      }

      public boolean isHorizontal() {
         return this == X || this == Z;
      }

      public String toString() {
         return this.name;
      }

      public static Direction.Axis getRandom(Random var0) {
         return Util.getRandom(VALUES, â˜ƒ);
      }

      public boolean test(@Nullable Direction var1) {
         return â˜ƒ != null && â˜ƒ.getAxis() == this;
      }

      public Direction.Plane getPlane() {
         switch(this) {
            case X:
            case Z:
               return Direction.Plane.HORIZONTAL;
            case Y:
               return Direction.Plane.VERTICAL;
            default:
               throw new Error("Someone's been tampering with the universe!");
         }
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }

      public abstract int choose(int var1, int var2, int var3);

      public abstract double choose(double var1, double var3, double var5);
   }

   public static enum AxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int step;
      private final String name;

      private AxisDirection(int var3, String var4) {
         this.step = â˜ƒ;
         this.name = â˜ƒ;
      }

      public int getStep() {
         return this.step;
      }

      public String getName() {
         return this.name;
      }

      public String toString() {
         return this.name;
      }

      public Direction.AxisDirection opposite() {
         return this == POSITIVE ? NEGATIVE : POSITIVE;
      }
   }

   public static enum Plane implements Iterable<Direction>, Predicate<Direction> {
      HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}),
      VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Direction.Axis[]{Direction.Axis.Y});

      private final Direction[] faces;
      private final Direction.Axis[] axis;

      private Plane(Direction[] var3, Direction.Axis[] var4) {
         this.faces = â˜ƒ;
         this.axis = â˜ƒ;
      }

      public Direction getRandomDirection(Random var1) {
         return Util.getRandom(this.faces, â˜ƒ);
      }

      public Direction.Axis getRandomAxis(Random var1) {
         return Util.getRandom(this.axis, â˜ƒ);
      }

      public boolean test(@Nullable Direction var1) {
         return â˜ƒ != null && â˜ƒ.getAxis().getPlane() == this;
      }

      public Iterator<Direction> iterator() {
         return Iterators.forArray(this.faces);
      }

      public Stream<Direction> stream() {
         return Arrays.stream(this.faces);
      }
   }
}
