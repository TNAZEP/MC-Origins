package net.minecraft.util;

import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public enum EnumFacing implements IStringSerializable {
   DOWN(0, 1, -1, "down", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Y, new Vec3i(0, -1, 0)),
   UP(1, 0, -1, "up", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.Y, new Vec3i(0, 1, 0)),
   NORTH(2, 3, 2, "north", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH(3, 2, 0, "south", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.Z, new Vec3i(0, 0, 1)),
   WEST(4, 5, 1, "west", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.X, new Vec3i(-1, 0, 0)),
   EAST(5, 4, 3, "east", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.X, new Vec3i(1, 0, 0));

   private final int field_176748_g;
   private final int field_176759_h;
   private final int field_176760_i;
   private final String field_176757_j;
   private final EnumFacing.Axis field_176758_k;
   private final EnumFacing.AxisDirection field_176755_l;
   private final Vec3i field_176756_m;
   private static final EnumFacing[] field_199792_n = values();
   private static final Map<String, EnumFacing> field_176761_p = (Map<String, EnumFacing>)Arrays.stream(field_199792_n)
      .collect(Collectors.toMap(EnumFacing::func_176742_j, var0 -> var0));
   private static final EnumFacing[] field_82609_l = (EnumFacing[])Arrays.stream(field_199792_n)
      .sorted(Comparator.comparingInt(var0 -> var0.field_176748_g))
      .toArray(var0 -> new EnumFacing[var0]);
   private static final EnumFacing[] field_176754_o = (EnumFacing[])Arrays.stream(field_199792_n)
      .filter(var0 -> var0.func_176740_k().func_176722_c())
      .sorted(Comparator.comparingInt(var0 -> var0.field_176760_i))
      .toArray(var0 -> new EnumFacing[var0]);

   private EnumFacing(int var3, int var4, int var5, String var6, EnumFacing.AxisDirection var7, EnumFacing.Axis var8, Vec3i var9) {
      this.field_176748_g = ☃;
      this.field_176760_i = ☃;
      this.field_176759_h = ☃;
      this.field_176757_j = ☃;
      this.field_176758_k = ☃;
      this.field_176755_l = ☃;
      this.field_176756_m = ☃;
   }

   public static EnumFacing[] func_196054_a(Entity var0) {
      float ☃ = ☃.func_195050_f(1.0F) * (float) (Math.PI / 180.0);
      float ☃x = -☃.func_195046_g(1.0F) * (float) (Math.PI / 180.0);
      float ☃xx = MathHelper.func_76126_a(☃);
      float ☃xxx = MathHelper.func_76134_b(☃);
      float ☃xxxx = MathHelper.func_76126_a(☃x);
      float ☃xxxxx = MathHelper.func_76134_b(☃x);
      boolean ☃xxxxxx = ☃xxxx > 0.0F;
      boolean ☃xxxxxxx = ☃xx < 0.0F;
      boolean ☃xxxxxxxx = ☃xxxxx > 0.0F;
      float ☃xxxxxxxxx = ☃xxxxxx ? ☃xxxx : -☃xxxx;
      float ☃xxxxxxxxxx = ☃xxxxxxx ? -☃xx : ☃xx;
      float ☃xxxxxxxxxxx = ☃xxxxxxxx ? ☃xxxxx : -☃xxxxx;
      float ☃xxxxxxxxxxxx = ☃xxxxxxxxx * ☃xxx;
      float ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxx * ☃xxx;
      EnumFacing ☃xxxxxxxxxxxxxx = ☃xxxxxx ? EAST : WEST;
      EnumFacing ☃xxxxxxxxxxxxxxx = ☃xxxxxxx ? UP : DOWN;
      EnumFacing ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxx ? SOUTH : NORTH;
      if (☃xxxxxxxxx > ☃xxxxxxxxxxx) {
         if (☃xxxxxxxxxx > ☃xxxxxxxxxxxx) {
            return func_196053_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx);
         } else {
            return ☃xxxxxxxxxxxxx > ☃xxxxxxxxxx
               ? func_196053_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
               : func_196053_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx);
         }
      } else if (☃xxxxxxxxxx > ☃xxxxxxxxxxxxx) {
         return func_196053_a(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
      } else {
         return ☃xxxxxxxxxxxx > ☃xxxxxxxxxx
            ? func_196053_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
            : func_196053_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
      }
   }

   private static EnumFacing[] func_196053_a(EnumFacing var0, EnumFacing var1, EnumFacing var2) {
      return new EnumFacing[]{☃, ☃, ☃, ☃.func_176734_d(), ☃.func_176734_d(), ☃.func_176734_d()};
   }

   public int func_176745_a() {
      return this.field_176748_g;
   }

   public int func_176736_b() {
      return this.field_176760_i;
   }

   public EnumFacing.AxisDirection func_176743_c() {
      return this.field_176755_l;
   }

   public EnumFacing func_176734_d() {
      return func_82600_a(this.field_176759_h);
   }

   public EnumFacing func_176746_e() {
      switch(this) {
         case NORTH:
            return EAST;
         case EAST:
            return SOUTH;
         case SOUTH:
            return WEST;
         case WEST:
            return NORTH;
         default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   public EnumFacing func_176735_f() {
      switch(this) {
         case NORTH:
            return WEST;
         case EAST:
            return NORTH;
         case SOUTH:
            return EAST;
         case WEST:
            return SOUTH;
         default:
            throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int func_82601_c() {
      return this.field_176758_k == EnumFacing.Axis.X ? this.field_176755_l.func_179524_a() : 0;
   }

   public int func_96559_d() {
      return this.field_176758_k == EnumFacing.Axis.Y ? this.field_176755_l.func_179524_a() : 0;
   }

   public int func_82599_e() {
      return this.field_176758_k == EnumFacing.Axis.Z ? this.field_176755_l.func_179524_a() : 0;
   }

   public String func_176742_j() {
      return this.field_176757_j;
   }

   public EnumFacing.Axis func_176740_k() {
      return this.field_176758_k;
   }

   public static EnumFacing func_82600_a(int var0) {
      return field_82609_l[MathHelper.func_76130_a(☃ % field_82609_l.length)];
   }

   public static EnumFacing func_176731_b(int var0) {
      return field_176754_o[MathHelper.func_76130_a(☃ % field_176754_o.length)];
   }

   public static EnumFacing func_176733_a(double var0) {
      return func_176731_b(MathHelper.func_76128_c(☃ / 90.0 + 0.5) & 3);
   }

   public static EnumFacing func_211699_a(EnumFacing.Axis var0, EnumFacing.AxisDirection var1) {
      switch(☃) {
         case X:
            return ☃ == EnumFacing.AxisDirection.POSITIVE ? EAST : WEST;
         case Y:
            return ☃ == EnumFacing.AxisDirection.POSITIVE ? UP : DOWN;
         case Z:
         default:
            return ☃ == EnumFacing.AxisDirection.POSITIVE ? SOUTH : NORTH;
      }
   }

   public float func_185119_l() {
      return (float)((this.field_176760_i & 3) * 90);
   }

   public static EnumFacing func_176741_a(Random var0) {
      return values()[☃.nextInt(values().length)];
   }

   public static EnumFacing func_210769_a(double var0, double var2, double var4) {
      return func_176737_a((float)☃, (float)☃, (float)☃);
   }

   public static EnumFacing func_176737_a(float var0, float var1, float var2) {
      EnumFacing ☃ = NORTH;
      float ☃x = Float.MIN_VALUE;

      for(EnumFacing ☃xx : field_199792_n) {
         float ☃xxx = ☃ * (float)☃xx.field_176756_m.func_177958_n()
            + ☃ * (float)☃xx.field_176756_m.func_177956_o()
            + ☃ * (float)☃xx.field_176756_m.func_177952_p();
         if (☃xxx > ☃x) {
            ☃x = ☃xxx;
            ☃ = ☃xx;
         }
      }

      return ☃;
   }

   public String toString() {
      return this.field_176757_j;
   }

   @Override
   public String func_176610_l() {
      return this.field_176757_j;
   }

   public static EnumFacing func_181076_a(EnumFacing.AxisDirection var0, EnumFacing.Axis var1) {
      for(EnumFacing ☃ : values()) {
         if (☃.func_176743_c() == ☃ && ☃.func_176740_k() == ☃) {
            return ☃;
         }
      }

      throw new IllegalArgumentException("No such direction: " + ☃ + " " + ☃);
   }

   public static enum Axis implements Predicate<EnumFacing>, IStringSerializable {
      X("x") {
         @Override
         public int func_196052_a(int var1, int var2, int var3) {
            return ☃;
         }

         @Override
         public double func_196051_a(double var1, double var3, double var5) {
            return ☃;
         }
      },
      Y("y") {
         @Override
         public int func_196052_a(int var1, int var2, int var3) {
            return ☃;
         }

         @Override
         public double func_196051_a(double var1, double var3, double var5) {
            return ☃;
         }
      },
      Z("z") {
         @Override
         public int func_196052_a(int var1, int var2, int var3) {
            return ☃;
         }

         @Override
         public double func_196051_a(double var1, double var3, double var5) {
            return ☃;
         }
      };

      private static final Map<String, EnumFacing.Axis> field_176725_d = (Map<String, EnumFacing.Axis>)Arrays.stream(values())
         .collect(Collectors.toMap(EnumFacing.Axis::func_176719_a, var0 -> var0));
      private final String field_176726_e;

      private Axis(String var3) {
         this.field_176726_e = ☃;
      }

      public String func_176719_a() {
         return this.field_176726_e;
      }

      public boolean func_200128_b() {
         return this == Y;
      }

      public boolean func_176722_c() {
         return this == X || this == Z;
      }

      public String toString() {
         return this.field_176726_e;
      }

      public boolean test(@Nullable EnumFacing var1) {
         return ☃ != null && ☃.func_176740_k() == this;
      }

      public EnumFacing.Plane func_176716_d() {
         switch(this) {
            case X:
            case Z:
               return EnumFacing.Plane.HORIZONTAL;
            case Y:
               return EnumFacing.Plane.VERTICAL;
            default:
               throw new Error("Someone's been tampering with the universe!");
         }
      }

      @Override
      public String func_176610_l() {
         return this.field_176726_e;
      }

      public abstract int func_196052_a(int var1, int var2, int var3);

      public abstract double func_196051_a(double var1, double var3, double var5);
   }

   public static enum AxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int field_179528_c;
      private final String field_179525_d;

      private AxisDirection(int var3, String var4) {
         this.field_179528_c = ☃;
         this.field_179525_d = ☃;
      }

      public int func_179524_a() {
         return this.field_179528_c;
      }

      public String toString() {
         return this.field_179525_d;
      }
   }

   public static enum Plane implements Iterable<EnumFacing>, Predicate<EnumFacing> {
      HORIZONTAL(
         new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}, new EnumFacing.Axis[]{EnumFacing.Axis.X, EnumFacing.Axis.Z}
      ),
      VERTICAL(new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN}, new EnumFacing.Axis[]{EnumFacing.Axis.Y});

      private final EnumFacing[] field_209387_c;
      private final EnumFacing.Axis[] field_209388_d;

      private Plane(EnumFacing[] var3, EnumFacing.Axis[] var4) {
         this.field_209387_c = ☃;
         this.field_209388_d = ☃;
      }

      public EnumFacing func_179518_a(Random var1) {
         return this.field_209387_c[☃.nextInt(this.field_209387_c.length)];
      }

      public boolean test(@Nullable EnumFacing var1) {
         return ☃ != null && ☃.func_176740_k().func_176716_d() == this;
      }

      public Iterator<EnumFacing> iterator() {
         return Iterators.forArray(this.field_209387_c);
      }
   }
}
