package net.minecraft.util.math;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.concurrent.Immutable;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Immutable
public class BlockPos extends Vec3i {
   private static final Logger field_185335_c = LogManager.getLogger();
   public static final BlockPos field_177992_a = new BlockPos(0, 0, 0);
   private static final int field_177990_b = 1 + MathHelper.func_151239_c(MathHelper.func_151236_b(30000000));
   private static final int field_177991_c = field_177990_b;
   private static final int field_177989_d = 64 - field_177990_b - field_177991_c;
   private static final int field_177987_f = 0 + field_177991_c;
   private static final int field_177988_g = field_177987_f + field_177989_d;
   private static final long field_177994_h = (1L << field_177990_b) - 1L;
   private static final long field_177995_i = (1L << field_177989_d) - 1L;
   private static final long field_177993_j = (1L << field_177991_c) - 1L;

   public BlockPos(int var1, int var2, int var3) {
      super(☃, ☃, ☃);
   }

   public BlockPos(double var1, double var3, double var5) {
      super(☃, ☃, ☃);
   }

   public BlockPos(Entity var1) {
      this(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
   }

   public BlockPos(Vec3d var1) {
      this(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   public BlockPos(Vec3i var1) {
      this(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public BlockPos func_177963_a(double var1, double var3, double var5) {
      return ☃ == 0.0 && ☃ == 0.0 && ☃ == 0.0
         ? this
         : new BlockPos((double)this.func_177958_n() + ☃, (double)this.func_177956_o() + ☃, (double)this.func_177952_p() + ☃);
   }

   public BlockPos func_177982_a(int var1, int var2, int var3) {
      return ☃ == 0 && ☃ == 0 && ☃ == 0 ? this : new BlockPos(this.func_177958_n() + ☃, this.func_177956_o() + ☃, this.func_177952_p() + ☃);
   }

   public BlockPos func_177971_a(Vec3i var1) {
      return this.func_177982_a(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public BlockPos func_177973_b(Vec3i var1) {
      return this.func_177982_a(-☃.func_177958_n(), -☃.func_177956_o(), -☃.func_177952_p());
   }

   public BlockPos func_177984_a() {
      return this.func_177981_b(1);
   }

   public BlockPos func_177981_b(int var1) {
      return this.func_177967_a(EnumFacing.UP, ☃);
   }

   public BlockPos func_177977_b() {
      return this.func_177979_c(1);
   }

   public BlockPos func_177979_c(int var1) {
      return this.func_177967_a(EnumFacing.DOWN, ☃);
   }

   public BlockPos func_177978_c() {
      return this.func_177964_d(1);
   }

   public BlockPos func_177964_d(int var1) {
      return this.func_177967_a(EnumFacing.NORTH, ☃);
   }

   public BlockPos func_177968_d() {
      return this.func_177970_e(1);
   }

   public BlockPos func_177970_e(int var1) {
      return this.func_177967_a(EnumFacing.SOUTH, ☃);
   }

   public BlockPos func_177976_e() {
      return this.func_177985_f(1);
   }

   public BlockPos func_177985_f(int var1) {
      return this.func_177967_a(EnumFacing.WEST, ☃);
   }

   public BlockPos func_177974_f() {
      return this.func_177965_g(1);
   }

   public BlockPos func_177965_g(int var1) {
      return this.func_177967_a(EnumFacing.EAST, ☃);
   }

   public BlockPos func_177972_a(EnumFacing var1) {
      return this.func_177967_a(☃, 1);
   }

   public BlockPos func_177967_a(EnumFacing var1, int var2) {
      return ☃ == 0
         ? this
         : new BlockPos(this.func_177958_n() + ☃.func_82601_c() * ☃, this.func_177956_o() + ☃.func_96559_d() * ☃, this.func_177952_p() + ☃.func_82599_e() * ☃);
   }

   public BlockPos func_190942_a(Rotation var1) {
      switch(☃) {
         case NONE:
         default:
            return this;
         case CLOCKWISE_90:
            return new BlockPos(-this.func_177952_p(), this.func_177956_o(), this.func_177958_n());
         case CLOCKWISE_180:
            return new BlockPos(-this.func_177958_n(), this.func_177956_o(), -this.func_177952_p());
         case COUNTERCLOCKWISE_90:
            return new BlockPos(this.func_177952_p(), this.func_177956_o(), -this.func_177958_n());
      }
   }

   public BlockPos func_177955_d(Vec3i var1) {
      return new BlockPos(
         this.func_177956_o() * ☃.func_177952_p() - this.func_177952_p() * ☃.func_177956_o(),
         this.func_177952_p() * ☃.func_177958_n() - this.func_177958_n() * ☃.func_177952_p(),
         this.func_177958_n() * ☃.func_177956_o() - this.func_177956_o() * ☃.func_177958_n()
      );
   }

   public long func_177986_g() {
      return ((long)this.func_177958_n() & field_177994_h) << field_177988_g
         | ((long)this.func_177956_o() & field_177995_i) << field_177987_f
         | ((long)this.func_177952_p() & field_177993_j) << 0;
   }

   public static BlockPos func_177969_a(long var0) {
      int ☃ = (int)(☃ << 64 - field_177988_g - field_177990_b >> 64 - field_177990_b);
      int ☃x = (int)(☃ << 64 - field_177987_f - field_177989_d >> 64 - field_177989_d);
      int ☃xx = (int)(☃ << 64 - field_177991_c >> 64 - field_177991_c);
      return new BlockPos(☃, ☃x, ☃xx);
   }

   public static Iterable<BlockPos> func_177980_a(BlockPos var0, BlockPos var1) {
      return func_191532_a(
         Math.min(☃.func_177958_n(), ☃.func_177958_n()),
         Math.min(☃.func_177956_o(), ☃.func_177956_o()),
         Math.min(☃.func_177952_p(), ☃.func_177952_p()),
         Math.max(☃.func_177958_n(), ☃.func_177958_n()),
         Math.max(☃.func_177956_o(), ☃.func_177956_o()),
         Math.max(☃.func_177952_p(), ☃.func_177952_p())
      );
   }

   public static Iterable<BlockPos> func_191532_a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return () -> new AbstractIterator<BlockPos>() {
            private boolean field_210330_g = true;
            private int field_210331_h;
            private int field_210332_i;
            private int field_210333_j;

            protected BlockPos computeNext() {
               if (this.field_210330_g) {
                  this.field_210330_g = false;
                  this.field_210331_h = ☃;
                  this.field_210332_i = ☃;
                  this.field_210333_j = ☃;
                  return new BlockPos(☃, ☃, ☃);
               } else if (this.field_210331_h == ☃ && this.field_210332_i == ☃ && this.field_210333_j == ☃) {
                  return this.endOfData();
               } else {
                  if (this.field_210331_h < ☃) {
                     ++this.field_210331_h;
                  } else if (this.field_210332_i < ☃) {
                     this.field_210331_h = ☃;
                     ++this.field_210332_i;
                  } else if (this.field_210333_j < ☃) {
                     this.field_210331_h = ☃;
                     this.field_210332_i = ☃;
                     ++this.field_210333_j;
                  }

                  return new BlockPos(this.field_210331_h, this.field_210332_i, this.field_210333_j);
               }
            }
         };
   }

   public BlockPos func_185334_h() {
      return this;
   }

   public static Iterable<BlockPos.MutableBlockPos> func_177975_b(BlockPos var0, BlockPos var1) {
      return func_191531_b(
         Math.min(☃.func_177958_n(), ☃.func_177958_n()),
         Math.min(☃.func_177956_o(), ☃.func_177956_o()),
         Math.min(☃.func_177952_p(), ☃.func_177952_p()),
         Math.max(☃.func_177958_n(), ☃.func_177958_n()),
         Math.max(☃.func_177956_o(), ☃.func_177956_o()),
         Math.max(☃.func_177952_p(), ☃.func_177952_p())
      );
   }

   public static Iterable<BlockPos.MutableBlockPos> func_191531_b(int var0, int var1, int var2, int var3, int var4, int var5) {
      return () -> new AbstractIterator<BlockPos.MutableBlockPos>() {
            private BlockPos.MutableBlockPos field_210334_g;

            protected BlockPos.MutableBlockPos computeNext() {
               if (this.field_210334_g == null) {
                  this.field_210334_g = new BlockPos.MutableBlockPos(☃, ☃, ☃);
                  return this.field_210334_g;
               } else if (this.field_210334_g.field_177997_b == ☃ && this.field_210334_g.field_177998_c == ☃ && this.field_210334_g.field_177996_d == ☃) {
                  return this.endOfData();
               } else {
                  if (this.field_210334_g.field_177997_b < ☃) {
                     ++this.field_210334_g.field_177997_b;
                  } else if (this.field_210334_g.field_177998_c < ☃) {
                     this.field_210334_g.field_177997_b = ☃;
                     ++this.field_210334_g.field_177998_c;
                  } else if (this.field_210334_g.field_177996_d < ☃) {
                     this.field_210334_g.field_177997_b = ☃;
                     this.field_210334_g.field_177998_c = ☃;
                     ++this.field_210334_g.field_177996_d;
                  }

                  return this.field_210334_g;
               }
            }
         };
   }

   public static class MutableBlockPos extends BlockPos {
      protected int field_177997_b;
      protected int field_177998_c;
      protected int field_177996_d;

      public MutableBlockPos() {
         this(0, 0, 0);
      }

      public MutableBlockPos(BlockPos var1) {
         this(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
      }

      public MutableBlockPos(int var1, int var2, int var3) {
         super(0, 0, 0);
         this.field_177997_b = ☃;
         this.field_177998_c = ☃;
         this.field_177996_d = ☃;
      }

      @Override
      public BlockPos func_177963_a(double var1, double var3, double var5) {
         return super.func_177963_a(☃, ☃, ☃).func_185334_h();
      }

      @Override
      public BlockPos func_177982_a(int var1, int var2, int var3) {
         return super.func_177982_a(☃, ☃, ☃).func_185334_h();
      }

      @Override
      public BlockPos func_177967_a(EnumFacing var1, int var2) {
         return super.func_177967_a(☃, ☃).func_185334_h();
      }

      @Override
      public BlockPos func_190942_a(Rotation var1) {
         return super.func_190942_a(☃).func_185334_h();
      }

      @Override
      public int func_177958_n() {
         return this.field_177997_b;
      }

      @Override
      public int func_177956_o() {
         return this.field_177998_c;
      }

      @Override
      public int func_177952_p() {
         return this.field_177996_d;
      }

      public BlockPos.MutableBlockPos func_181079_c(int var1, int var2, int var3) {
         this.field_177997_b = ☃;
         this.field_177998_c = ☃;
         this.field_177996_d = ☃;
         return this;
      }

      public BlockPos.MutableBlockPos func_189532_c(double var1, double var3, double var5) {
         return this.func_181079_c(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃));
      }

      public BlockPos.MutableBlockPos func_189533_g(Vec3i var1) {
         return this.func_181079_c(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
      }

      public BlockPos.MutableBlockPos func_189536_c(EnumFacing var1) {
         return this.func_189534_c(☃, 1);
      }

      public BlockPos.MutableBlockPos func_189534_c(EnumFacing var1, int var2) {
         return this.func_181079_c(
            this.field_177997_b + ☃.func_82601_c() * ☃, this.field_177998_c + ☃.func_96559_d() * ☃, this.field_177996_d + ☃.func_82599_e() * ☃
         );
      }

      public BlockPos.MutableBlockPos func_196234_d(int var1, int var2, int var3) {
         return this.func_181079_c(this.field_177997_b + ☃, this.field_177998_c + ☃, this.field_177996_d + ☃);
      }

      public void func_185336_p(int var1) {
         this.field_177998_c = ☃;
      }

      @Override
      public BlockPos func_185334_h() {
         return new BlockPos(this);
      }
   }

   public static final class PooledMutableBlockPos extends BlockPos.MutableBlockPos implements AutoCloseable {
      private boolean field_185350_f;
      private static final List<BlockPos.PooledMutableBlockPos> field_185351_g = Lists.<BlockPos.PooledMutableBlockPos>newArrayList();

      private PooledMutableBlockPos(int var1, int var2, int var3) {
         super(☃, ☃, ☃);
      }

      public static BlockPos.PooledMutableBlockPos func_185346_s() {
         return func_185339_c(0, 0, 0);
      }

      public static BlockPos.PooledMutableBlockPos func_209907_b(Entity var0) {
         return func_185345_c(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
      }

      public static BlockPos.PooledMutableBlockPos func_185345_c(double var0, double var2, double var4) {
         return func_185339_c(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃));
      }

      public static BlockPos.PooledMutableBlockPos func_185339_c(int var0, int var1, int var2) {
         synchronized(field_185351_g) {
            if (!field_185351_g.isEmpty()) {
               BlockPos.PooledMutableBlockPos ☃ = (BlockPos.PooledMutableBlockPos)field_185351_g.remove(field_185351_g.size() - 1);
               if (☃ != null && ☃.field_185350_f) {
                  ☃.field_185350_f = false;
                  ☃.func_181079_c(☃, ☃, ☃);
                  return ☃;
               }
            }
         }

         return new BlockPos.PooledMutableBlockPos(☃, ☃, ☃);
      }

      public BlockPos.PooledMutableBlockPos func_181079_c(int var1, int var2, int var3) {
         return (BlockPos.PooledMutableBlockPos)super.func_181079_c(☃, ☃, ☃);
      }

      public BlockPos.PooledMutableBlockPos func_189532_c(double var1, double var3, double var5) {
         return (BlockPos.PooledMutableBlockPos)super.func_189532_c(☃, ☃, ☃);
      }

      public BlockPos.PooledMutableBlockPos func_189533_g(Vec3i var1) {
         return (BlockPos.PooledMutableBlockPos)super.func_189533_g(☃);
      }

      public BlockPos.PooledMutableBlockPos func_189536_c(EnumFacing var1) {
         return (BlockPos.PooledMutableBlockPos)super.func_189536_c(☃);
      }

      public BlockPos.PooledMutableBlockPos func_189534_c(EnumFacing var1, int var2) {
         return (BlockPos.PooledMutableBlockPos)super.func_189534_c(☃, ☃);
      }

      public BlockPos.PooledMutableBlockPos func_196234_d(int var1, int var2, int var3) {
         return (BlockPos.PooledMutableBlockPos)super.func_196234_d(☃, ☃, ☃);
      }

      public void close() {
         synchronized(field_185351_g) {
            if (field_185351_g.size() < 100) {
               field_185351_g.add(this);
            }

            this.field_185350_f = true;
         }
      }
   }
}
