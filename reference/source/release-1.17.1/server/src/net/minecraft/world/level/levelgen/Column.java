package net.minecraft.world.level.levelgen;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;

public abstract class Column {
   public static Column.Range around(int var0, int var1) {
      return new Column.Range(â˜ƒ - 1, â˜ƒ + 1);
   }

   public static Column.Range inside(int var0, int var1) {
      return new Column.Range(â˜ƒ, â˜ƒ);
   }

   public static Column below(int var0) {
      return new Column.Ray(â˜ƒ, false);
   }

   public static Column fromHighest(int var0) {
      return new Column.Ray(â˜ƒ + 1, false);
   }

   public static Column above(int var0) {
      return new Column.Ray(â˜ƒ, true);
   }

   public static Column fromLowest(int var0) {
      return new Column.Ray(â˜ƒ - 1, true);
   }

   public static Column line() {
      return Column.Line.INSTANCE;
   }

   public static Column create(OptionalInt var0, OptionalInt var1) {
      if (â˜ƒ.isPresent() && â˜ƒ.isPresent()) {
         return inside(â˜ƒ.getAsInt(), â˜ƒ.getAsInt());
      } else if (â˜ƒ.isPresent()) {
         return above(â˜ƒ.getAsInt());
      } else {
         return â˜ƒ.isPresent() ? below(â˜ƒ.getAsInt()) : line();
      }
   }

   public abstract OptionalInt getCeiling();

   public abstract OptionalInt getFloor();

   public abstract OptionalInt getHeight();

   public Column withFloor(OptionalInt var1) {
      return create(â˜ƒ, this.getCeiling());
   }

   public Column withCeiling(OptionalInt var1) {
      return create(this.getFloor(), â˜ƒ);
   }

   public static Optional<Column> scan(LevelSimulatedReader var0, BlockPos var1, int var2, Predicate<BlockState> var3, Predicate<BlockState> var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      if (!â˜ƒ.isStateAtPosition(â˜ƒ, â˜ƒ)) {
         return Optional.empty();
      } else {
         int â˜ƒ = â˜ƒ.getY();
         OptionalInt â˜ƒx = scanDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Direction.UP);
         OptionalInt â˜ƒxx = scanDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Direction.DOWN);
         return Optional.of(create(â˜ƒxx, â˜ƒx));
      }
   }

   private static OptionalInt scanDirection(
      LevelSimulatedReader var0, int var1, Predicate<BlockState> var2, Predicate<BlockState> var3, BlockPos.MutableBlockPos var4, int var5, Direction var6
   ) {
      â˜ƒ.setY(â˜ƒ);

      for(int â˜ƒ = 1; â˜ƒ < â˜ƒ && â˜ƒ.isStateAtPosition(â˜ƒ, â˜ƒ); ++â˜ƒ) {
         â˜ƒ.move(â˜ƒ);
      }

      return â˜ƒ.isStateAtPosition(â˜ƒ, â˜ƒ) ? OptionalInt.of(â˜ƒ.getY()) : OptionalInt.empty();
   }

   public static final class Line extends Column {
      static final Column.Line INSTANCE = new Column.Line();

      private Line() {
      }

      @Override
      public OptionalInt getCeiling() {
         return OptionalInt.empty();
      }

      @Override
      public OptionalInt getFloor() {
         return OptionalInt.empty();
      }

      @Override
      public OptionalInt getHeight() {
         return OptionalInt.empty();
      }

      public String toString() {
         return "C(-)";
      }
   }

   public static final class Range extends Column {
      private final int floor;
      private final int ceiling;

      protected Range(int var1, int var2) {
         this.floor = â˜ƒ;
         this.ceiling = â˜ƒ;
         if (this.height() < 0) {
            throw new IllegalArgumentException("Column of negative height: " + this);
         }
      }

      @Override
      public OptionalInt getCeiling() {
         return OptionalInt.of(this.ceiling);
      }

      @Override
      public OptionalInt getFloor() {
         return OptionalInt.of(this.floor);
      }

      @Override
      public OptionalInt getHeight() {
         return OptionalInt.of(this.height());
      }

      public int ceiling() {
         return this.ceiling;
      }

      public int floor() {
         return this.floor;
      }

      public int height() {
         return this.ceiling - this.floor - 1;
      }

      public String toString() {
         return "C(" + this.ceiling + "-" + this.floor + ")";
      }
   }

   public static final class Ray extends Column {
      private final int edge;
      private final boolean pointingUp;

      public Ray(int var1, boolean var2) {
         this.edge = â˜ƒ;
         this.pointingUp = â˜ƒ;
      }

      @Override
      public OptionalInt getCeiling() {
         return this.pointingUp ? OptionalInt.empty() : OptionalInt.of(this.edge);
      }

      @Override
      public OptionalInt getFloor() {
         return this.pointingUp ? OptionalInt.of(this.edge) : OptionalInt.empty();
      }

      @Override
      public OptionalInt getHeight() {
         return OptionalInt.empty();
      }

      public String toString() {
         return this.pointingUp ? "C(" + this.edge + "-)" : "C(-" + this.edge + ")";
      }
   }
}
