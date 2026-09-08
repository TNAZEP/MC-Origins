package net.minecraft.world.phys;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class AABB {
   private static final double EPSILON = 1.0E-7;
   public final double minX;
   public final double minY;
   public final double minZ;
   public final double maxX;
   public final double maxY;
   public final double maxZ;

   public AABB(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.minX = Math.min(â˜ƒ, â˜ƒ);
      this.minY = Math.min(â˜ƒ, â˜ƒ);
      this.minZ = Math.min(â˜ƒ, â˜ƒ);
      this.maxX = Math.max(â˜ƒ, â˜ƒ);
      this.maxY = Math.max(â˜ƒ, â˜ƒ);
      this.maxZ = Math.max(â˜ƒ, â˜ƒ);
   }

   public AABB(BlockPos var1) {
      this((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), (double)(â˜ƒ.getX() + 1), (double)(â˜ƒ.getY() + 1), (double)(â˜ƒ.getZ() + 1));
   }

   public AABB(BlockPos var1, BlockPos var2) {
      this((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
   }

   public AABB(Vec3 var1, Vec3 var2) {
      this(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public static AABB of(BoundingBox var0) {
      return new AABB((double)â˜ƒ.minX(), (double)â˜ƒ.minY(), (double)â˜ƒ.minZ(), (double)(â˜ƒ.maxX() + 1), (double)(â˜ƒ.maxY() + 1), (double)(â˜ƒ.maxZ() + 1));
   }

   public static AABB unitCubeFromLowerCorner(Vec3 var0) {
      return new AABB(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ.x + 1.0, â˜ƒ.y + 1.0, â˜ƒ.z + 1.0);
   }

   public AABB setMinX(double var1) {
      return new AABB(â˜ƒ, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
   }

   public AABB setMinY(double var1) {
      return new AABB(this.minX, â˜ƒ, this.minZ, this.maxX, this.maxY, this.maxZ);
   }

   public AABB setMinZ(double var1) {
      return new AABB(this.minX, this.minY, â˜ƒ, this.maxX, this.maxY, this.maxZ);
   }

   public AABB setMaxX(double var1) {
      return new AABB(this.minX, this.minY, this.minZ, â˜ƒ, this.maxY, this.maxZ);
   }

   public AABB setMaxY(double var1) {
      return new AABB(this.minX, this.minY, this.minZ, this.maxX, â˜ƒ, this.maxZ);
   }

   public AABB setMaxZ(double var1) {
      return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, â˜ƒ);
   }

   public double min(Direction.Axis var1) {
      return â˜ƒ.choose(this.minX, this.minY, this.minZ);
   }

   public double max(Direction.Axis var1) {
      return â˜ƒ.choose(this.maxX, this.maxY, this.maxZ);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof AABB)) {
         return false;
      } else {
         AABB â˜ƒ = (AABB)â˜ƒ;
         if (Double.compare(â˜ƒ.minX, this.minX) != 0) {
            return false;
         } else if (Double.compare(â˜ƒ.minY, this.minY) != 0) {
            return false;
         } else if (Double.compare(â˜ƒ.minZ, this.minZ) != 0) {
            return false;
         } else if (Double.compare(â˜ƒ.maxX, this.maxX) != 0) {
            return false;
         } else if (Double.compare(â˜ƒ.maxY, this.maxY) != 0) {
            return false;
         } else {
            return Double.compare(â˜ƒ.maxZ, this.maxZ) == 0;
         }
      }
   }

   public int hashCode() {
      long â˜ƒ = Double.doubleToLongBits(this.minX);
      int â˜ƒx = (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.minY);
      â˜ƒx = 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.minZ);
      â˜ƒx = 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.maxX);
      â˜ƒx = 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.maxY);
      â˜ƒx = 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
      â˜ƒ = Double.doubleToLongBits(this.maxZ);
      return 31 * â˜ƒx + (int)(â˜ƒ ^ â˜ƒ >>> 32);
   }

   public AABB contract(double var1, double var3, double var5) {
      double â˜ƒ = this.minX;
      double â˜ƒx = this.minY;
      double â˜ƒxx = this.minZ;
      double â˜ƒxxx = this.maxX;
      double â˜ƒxxxx = this.maxY;
      double â˜ƒxxxxx = this.maxZ;
      if (â˜ƒ < 0.0) {
         â˜ƒ -= â˜ƒ;
      } else if (â˜ƒ > 0.0) {
         â˜ƒxxx -= â˜ƒ;
      }

      if (â˜ƒ < 0.0) {
         â˜ƒx -= â˜ƒ;
      } else if (â˜ƒ > 0.0) {
         â˜ƒxxxx -= â˜ƒ;
      }

      if (â˜ƒ < 0.0) {
         â˜ƒxx -= â˜ƒ;
      } else if (â˜ƒ > 0.0) {
         â˜ƒxxxxx -= â˜ƒ;
      }

      return new AABB(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   public AABB expandTowards(Vec3 var1) {
      return this.expandTowards(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public AABB expandTowards(double var1, double var3, double var5) {
      double â˜ƒ = this.minX;
      double â˜ƒx = this.minY;
      double â˜ƒxx = this.minZ;
      double â˜ƒxxx = this.maxX;
      double â˜ƒxxxx = this.maxY;
      double â˜ƒxxxxx = this.maxZ;
      if (â˜ƒ < 0.0) {
         â˜ƒ += â˜ƒ;
      } else if (â˜ƒ > 0.0) {
         â˜ƒxxx += â˜ƒ;
      }

      if (â˜ƒ < 0.0) {
         â˜ƒx += â˜ƒ;
      } else if (â˜ƒ > 0.0) {
         â˜ƒxxxx += â˜ƒ;
      }

      if (â˜ƒ < 0.0) {
         â˜ƒxx += â˜ƒ;
      } else if (â˜ƒ > 0.0) {
         â˜ƒxxxxx += â˜ƒ;
      }

      return new AABB(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   public AABB inflate(double var1, double var3, double var5) {
      double â˜ƒ = this.minX - â˜ƒ;
      double â˜ƒx = this.minY - â˜ƒ;
      double â˜ƒxx = this.minZ - â˜ƒ;
      double â˜ƒxxx = this.maxX + â˜ƒ;
      double â˜ƒxxxx = this.maxY + â˜ƒ;
      double â˜ƒxxxxx = this.maxZ + â˜ƒ;
      return new AABB(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   public AABB inflate(double var1) {
      return this.inflate(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public AABB intersect(AABB var1) {
      double â˜ƒ = Math.max(this.minX, â˜ƒ.minX);
      double â˜ƒx = Math.max(this.minY, â˜ƒ.minY);
      double â˜ƒxx = Math.max(this.minZ, â˜ƒ.minZ);
      double â˜ƒxxx = Math.min(this.maxX, â˜ƒ.maxX);
      double â˜ƒxxxx = Math.min(this.maxY, â˜ƒ.maxY);
      double â˜ƒxxxxx = Math.min(this.maxZ, â˜ƒ.maxZ);
      return new AABB(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   public AABB minmax(AABB var1) {
      double â˜ƒ = Math.min(this.minX, â˜ƒ.minX);
      double â˜ƒx = Math.min(this.minY, â˜ƒ.minY);
      double â˜ƒxx = Math.min(this.minZ, â˜ƒ.minZ);
      double â˜ƒxxx = Math.max(this.maxX, â˜ƒ.maxX);
      double â˜ƒxxxx = Math.max(this.maxY, â˜ƒ.maxY);
      double â˜ƒxxxxx = Math.max(this.maxZ, â˜ƒ.maxZ);
      return new AABB(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   public AABB move(double var1, double var3, double var5) {
      return new AABB(this.minX + â˜ƒ, this.minY + â˜ƒ, this.minZ + â˜ƒ, this.maxX + â˜ƒ, this.maxY + â˜ƒ, this.maxZ + â˜ƒ);
   }

   public AABB move(BlockPos var1) {
      return new AABB(
         this.minX + (double)â˜ƒ.getX(),
         this.minY + (double)â˜ƒ.getY(),
         this.minZ + (double)â˜ƒ.getZ(),
         this.maxX + (double)â˜ƒ.getX(),
         this.maxY + (double)â˜ƒ.getY(),
         this.maxZ + (double)â˜ƒ.getZ()
      );
   }

   public AABB move(Vec3 var1) {
      return this.move(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public boolean intersects(AABB var1) {
      return this.intersects(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ);
   }

   public boolean intersects(double var1, double var3, double var5, double var7, double var9, double var11) {
      return this.minX < â˜ƒ && this.maxX > â˜ƒ && this.minY < â˜ƒ && this.maxY > â˜ƒ && this.minZ < â˜ƒ && this.maxZ > â˜ƒ;
   }

   public boolean intersects(Vec3 var1, Vec3 var2) {
      return this.intersects(
         Math.min(â˜ƒ.x, â˜ƒ.x), Math.min(â˜ƒ.y, â˜ƒ.y), Math.min(â˜ƒ.z, â˜ƒ.z), Math.max(â˜ƒ.x, â˜ƒ.x), Math.max(â˜ƒ.y, â˜ƒ.y), Math.max(â˜ƒ.z, â˜ƒ.z)
      );
   }

   public boolean contains(Vec3 var1) {
      return this.contains(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public boolean contains(double var1, double var3, double var5) {
      return â˜ƒ >= this.minX && â˜ƒ < this.maxX && â˜ƒ >= this.minY && â˜ƒ < this.maxY && â˜ƒ >= this.minZ && â˜ƒ < this.maxZ;
   }

   public double getSize() {
      double â˜ƒ = this.getXsize();
      double â˜ƒx = this.getYsize();
      double â˜ƒxx = this.getZsize();
      return (â˜ƒ + â˜ƒx + â˜ƒxx) / 3.0;
   }

   public double getXsize() {
      return this.maxX - this.minX;
   }

   public double getYsize() {
      return this.maxY - this.minY;
   }

   public double getZsize() {
      return this.maxZ - this.minZ;
   }

   public AABB deflate(double var1, double var3, double var5) {
      return this.inflate(-â˜ƒ, -â˜ƒ, -â˜ƒ);
   }

   public AABB deflate(double var1) {
      return this.inflate(-â˜ƒ);
   }

   public Optional<Vec3> clip(Vec3 var1, Vec3 var2) {
      double[] â˜ƒ = new double[]{1.0};
      double â˜ƒx = â˜ƒ.x - â˜ƒ.x;
      double â˜ƒxx = â˜ƒ.y - â˜ƒ.y;
      double â˜ƒxxx = â˜ƒ.z - â˜ƒ.z;
      Direction â˜ƒxxxx = getDirection(this, â˜ƒ, â˜ƒ, null, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      if (â˜ƒxxxx == null) {
         return Optional.empty();
      } else {
         double â˜ƒ = â˜ƒ[0];
         return Optional.of(â˜ƒ.add(â˜ƒ * â˜ƒx, â˜ƒ * â˜ƒxx, â˜ƒ * â˜ƒxxx));
      }
   }

   @Nullable
   public static BlockHitResult clip(Iterable<AABB> var0, Vec3 var1, Vec3 var2, BlockPos var3) {
      double[] â˜ƒ = new double[]{1.0};
      Direction â˜ƒx = null;
      double â˜ƒxx = â˜ƒ.x - â˜ƒ.x;
      double â˜ƒxxx = â˜ƒ.y - â˜ƒ.y;
      double â˜ƒxxxx = â˜ƒ.z - â˜ƒ.z;

      for(AABB â˜ƒxxxxx : â˜ƒ) {
         â˜ƒx = getDirection(â˜ƒxxxxx.move(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }

      if (â˜ƒx == null) {
         return null;
      } else {
         double â˜ƒxxxxx = â˜ƒ[0];
         return new BlockHitResult(â˜ƒ.add(â˜ƒxxxxx * â˜ƒxx, â˜ƒxxxxx * â˜ƒxxx, â˜ƒxxxxx * â˜ƒxxxx), â˜ƒx, â˜ƒ, false);
      }
   }

   @Nullable
   private static Direction getDirection(AABB var0, Vec3 var1, double[] var2, @Nullable Direction var3, double var4, double var6, double var8) {
      if (â˜ƒ > 1.0E-7) {
         â˜ƒ = clipPoint(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.maxY, â˜ƒ.minZ, â˜ƒ.maxZ, Direction.WEST, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      } else if (â˜ƒ < -1.0E-7) {
         â˜ƒ = clipPoint(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.maxX, â˜ƒ.minY, â˜ƒ.maxY, â˜ƒ.minZ, â˜ƒ.maxZ, Direction.EAST, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      }

      if (â˜ƒ > 1.0E-7) {
         â˜ƒ = clipPoint(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxZ, â˜ƒ.minX, â˜ƒ.maxX, Direction.DOWN, â˜ƒ.y, â˜ƒ.z, â˜ƒ.x);
      } else if (â˜ƒ < -1.0E-7) {
         â˜ƒ = clipPoint(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.maxY, â˜ƒ.minZ, â˜ƒ.maxZ, â˜ƒ.minX, â˜ƒ.maxX, Direction.UP, â˜ƒ.y, â˜ƒ.z, â˜ƒ.x);
      }

      if (â˜ƒ > 1.0E-7) {
         â˜ƒ = clipPoint(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.minZ, â˜ƒ.minX, â˜ƒ.maxX, â˜ƒ.minY, â˜ƒ.maxY, Direction.NORTH, â˜ƒ.z, â˜ƒ.x, â˜ƒ.y);
      } else if (â˜ƒ < -1.0E-7) {
         â˜ƒ = clipPoint(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.maxZ, â˜ƒ.minX, â˜ƒ.maxX, â˜ƒ.minY, â˜ƒ.maxY, Direction.SOUTH, â˜ƒ.z, â˜ƒ.x, â˜ƒ.y);
      }

      return â˜ƒ;
   }

   @Nullable
   private static Direction clipPoint(
      double[] var0,
      @Nullable Direction var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      Direction var18,
      double var19,
      double var21,
      double var23
   ) {
      double â˜ƒ = (â˜ƒ - â˜ƒ) / â˜ƒ;
      double â˜ƒx = â˜ƒ + â˜ƒ * â˜ƒ;
      double â˜ƒxx = â˜ƒ + â˜ƒ * â˜ƒ;
      if (0.0 < â˜ƒ && â˜ƒ < â˜ƒ[0] && â˜ƒ - 1.0E-7 < â˜ƒx && â˜ƒx < â˜ƒ + 1.0E-7 && â˜ƒ - 1.0E-7 < â˜ƒxx && â˜ƒxx < â˜ƒ + 1.0E-7) {
         â˜ƒ[0] = â˜ƒ;
         return â˜ƒ;
      } else {
         return â˜ƒ;
      }
   }

   public String toString() {
      return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
   }

   public boolean hasNaN() {
      return Double.isNaN(this.minX)
         || Double.isNaN(this.minY)
         || Double.isNaN(this.minZ)
         || Double.isNaN(this.maxX)
         || Double.isNaN(this.maxY)
         || Double.isNaN(this.maxZ);
   }

   public Vec3 getCenter() {
      return new Vec3(Mth.lerp(0.5, this.minX, this.maxX), Mth.lerp(0.5, this.minY, this.maxY), Mth.lerp(0.5, this.minZ, this.maxZ));
   }

   public static AABB ofSize(Vec3 var0, double var1, double var3, double var5) {
      return new AABB(â˜ƒ.x - â˜ƒ / 2.0, â˜ƒ.y - â˜ƒ / 2.0, â˜ƒ.z - â˜ƒ / 2.0, â˜ƒ.x + â˜ƒ / 2.0, â˜ƒ.y + â˜ƒ / 2.0, â˜ƒ.z + â˜ƒ / 2.0);
   }
}
