package net.minecraft.world.phys.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class VoxelShape {
   protected final DiscreteVoxelShape shape;
   @Nullable
   private VoxelShape[] faces;

   VoxelShape(DiscreteVoxelShape var1) {
      this.shape = â˜ƒ;
   }

   public double min(Direction.Axis var1) {
      int â˜ƒ = this.shape.firstFull(â˜ƒ);
      return â˜ƒ >= this.shape.getSize(â˜ƒ) ? Double.POSITIVE_INFINITY : this.get(â˜ƒ, â˜ƒ);
   }

   public double max(Direction.Axis var1) {
      int â˜ƒ = this.shape.lastFull(â˜ƒ);
      return â˜ƒ <= 0 ? Double.NEGATIVE_INFINITY : this.get(â˜ƒ, â˜ƒ);
   }

   public AABB bounds() {
      if (this.isEmpty()) {
         throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("No bounds for empty shape."));
      } else {
         return new AABB(
            this.min(Direction.Axis.X),
            this.min(Direction.Axis.Y),
            this.min(Direction.Axis.Z),
            this.max(Direction.Axis.X),
            this.max(Direction.Axis.Y),
            this.max(Direction.Axis.Z)
         );
      }
   }

   protected double get(Direction.Axis var1, int var2) {
      return this.getCoords(â˜ƒ).getDouble(â˜ƒ);
   }

   protected abstract DoubleList getCoords(Direction.Axis var1);

   public boolean isEmpty() {
      return this.shape.isEmpty();
   }

   public VoxelShape move(double var1, double var3, double var5) {
      return (VoxelShape)(this.isEmpty()
         ? Shapes.empty()
         : new ArrayVoxelShape(
            this.shape,
            new OffsetDoubleList(this.getCoords(Direction.Axis.X), â˜ƒ),
            new OffsetDoubleList(this.getCoords(Direction.Axis.Y), â˜ƒ),
            new OffsetDoubleList(this.getCoords(Direction.Axis.Z), â˜ƒ)
         ));
   }

   public VoxelShape optimize() {
      VoxelShape[] â˜ƒ = new VoxelShape[]{Shapes.empty()};
      this.forAllBoxes(
         (var1x, var3, var5, var7, var9, var11) -> â˜ƒ[0] = Shapes.joinUnoptimized(â˜ƒ[0], Shapes.box(var1x, var3, var5, var7, var9, var11), BooleanOp.OR)
      );
      return â˜ƒ[0];
   }

   public void forAllEdges(Shapes.DoubleLineConsumer var1) {
      this.shape
         .forAllEdges(
            (var2, var3, var4, var5, var6, var7) -> â˜ƒ.consume(
                  this.get(Direction.Axis.X, var2),
                  this.get(Direction.Axis.Y, var3),
                  this.get(Direction.Axis.Z, var4),
                  this.get(Direction.Axis.X, var5),
                  this.get(Direction.Axis.Y, var6),
                  this.get(Direction.Axis.Z, var7)
               ),
            true
         );
   }

   public void forAllBoxes(Shapes.DoubleLineConsumer var1) {
      DoubleList â˜ƒ = this.getCoords(Direction.Axis.X);
      DoubleList â˜ƒx = this.getCoords(Direction.Axis.Y);
      DoubleList â˜ƒxx = this.getCoords(Direction.Axis.Z);
      this.shape
         .forAllBoxes(
            (var4x, var5, var6, var7, var8, var9) -> â˜ƒ.consume(
                  â˜ƒ.getDouble(var4x), â˜ƒ.getDouble(var5), â˜ƒ.getDouble(var6), â˜ƒ.getDouble(var7), â˜ƒ.getDouble(var8), â˜ƒ.getDouble(var9)
               ),
            true
         );
   }

   public List<AABB> toAabbs() {
      List<AABB> â˜ƒ = Lists.<AABB>newArrayList();
      this.forAllBoxes((var1x, var3, var5, var7, var9, var11) -> â˜ƒ.add(new AABB(var1x, var3, var5, var7, var9, var11)));
      return â˜ƒ;
   }

   public double min(Direction.Axis var1, double var2, double var4) {
      Direction.Axis â˜ƒ = AxisCycle.FORWARD.cycle(â˜ƒ);
      Direction.Axis â˜ƒx = AxisCycle.BACKWARD.cycle(â˜ƒ);
      int â˜ƒxx = this.findIndex(â˜ƒ, â˜ƒ);
      int â˜ƒxxx = this.findIndex(â˜ƒx, â˜ƒ);
      int â˜ƒxxxx = this.shape.firstFull(â˜ƒ, â˜ƒxx, â˜ƒxxx);
      return â˜ƒxxxx >= this.shape.getSize(â˜ƒ) ? Double.POSITIVE_INFINITY : this.get(â˜ƒ, â˜ƒxxxx);
   }

   public double max(Direction.Axis var1, double var2, double var4) {
      Direction.Axis â˜ƒ = AxisCycle.FORWARD.cycle(â˜ƒ);
      Direction.Axis â˜ƒx = AxisCycle.BACKWARD.cycle(â˜ƒ);
      int â˜ƒxx = this.findIndex(â˜ƒ, â˜ƒ);
      int â˜ƒxxx = this.findIndex(â˜ƒx, â˜ƒ);
      int â˜ƒxxxx = this.shape.lastFull(â˜ƒ, â˜ƒxx, â˜ƒxxx);
      return â˜ƒxxxx <= 0 ? Double.NEGATIVE_INFINITY : this.get(â˜ƒ, â˜ƒxxxx);
   }

   protected int findIndex(Direction.Axis var1, double var2) {
      return Mth.binarySearch(0, this.shape.getSize(â˜ƒ) + 1, var4 -> â˜ƒ < this.get(â˜ƒ, var4)) - 1;
   }

   @Nullable
   public BlockHitResult clip(Vec3 var1, Vec3 var2, BlockPos var3) {
      if (this.isEmpty()) {
         return null;
      } else {
         Vec3 â˜ƒ = â˜ƒ.subtract(â˜ƒ);
         if (â˜ƒ.lengthSqr() < 1.0E-7) {
            return null;
         } else {
            Vec3 â˜ƒ = â˜ƒ.add(â˜ƒ.scale(0.001));
            return this.shape
                  .isFullWide(
                     this.findIndex(Direction.Axis.X, â˜ƒ.x - (double)â˜ƒ.getX()),
                     this.findIndex(Direction.Axis.Y, â˜ƒ.y - (double)â˜ƒ.getY()),
                     this.findIndex(Direction.Axis.Z, â˜ƒ.z - (double)â˜ƒ.getZ())
                  )
               ? new BlockHitResult(â˜ƒ, Direction.getNearest(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z).getOpposite(), â˜ƒ, true)
               : AABB.clip(this.toAabbs(), â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   public Optional<Vec3> closestPointTo(Vec3 var1) {
      if (this.isEmpty()) {
         return Optional.empty();
      } else {
         Vec3[] â˜ƒ = new Vec3[1];
         this.forAllBoxes((var2x, var4, var6, var8, var10, var12) -> {
            double â˜ƒ = Mth.clamp(â˜ƒ.x(), var2x, var8);
            double â˜ƒx = Mth.clamp(â˜ƒ.y(), var4, var10);
            double â˜ƒxx = Mth.clamp(â˜ƒ.z(), var6, var12);
            if (â˜ƒ[0] == null || â˜ƒ.distanceToSqr(â˜ƒ, â˜ƒx, â˜ƒxx) < â˜ƒ.distanceToSqr(â˜ƒ[0])) {
               â˜ƒ[0] = new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
            }
         });
         return Optional.of(â˜ƒ[0]);
      }
   }

   public VoxelShape getFaceShape(Direction var1) {
      if (!this.isEmpty() && this != Shapes.block()) {
         if (this.faces != null) {
            VoxelShape â˜ƒ = this.faces[â˜ƒ.ordinal()];
            if (â˜ƒ != null) {
               return â˜ƒ;
            }
         } else {
            this.faces = new VoxelShape[6];
         }

         VoxelShape â˜ƒ = this.calculateFace(â˜ƒ);
         this.faces[â˜ƒ.ordinal()] = â˜ƒ;
         return â˜ƒ;
      } else {
         return this;
      }
   }

   private VoxelShape calculateFace(Direction var1) {
      Direction.Axis â˜ƒ = â˜ƒ.getAxis();
      DoubleList â˜ƒx = this.getCoords(â˜ƒ);
      if (â˜ƒx.size() == 2 && DoubleMath.fuzzyEquals(â˜ƒx.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(â˜ƒx.getDouble(1), 1.0, 1.0E-7)) {
         return this;
      } else {
         Direction.AxisDirection â˜ƒ = â˜ƒ.getAxisDirection();
         int â˜ƒx = this.findIndex(â˜ƒ, â˜ƒ == Direction.AxisDirection.POSITIVE ? 0.9999999 : 1.0E-7);
         return new SliceShape(this, â˜ƒ, â˜ƒx);
      }
   }

   public double collide(Direction.Axis var1, AABB var2, double var3) {
      return this.collideX(AxisCycle.between(â˜ƒ, Direction.Axis.X), â˜ƒ, â˜ƒ);
   }

   protected double collideX(AxisCycle var1, AABB var2, double var3) {
      if (this.isEmpty()) {
         return â˜ƒ;
      } else if (Math.abs(â˜ƒ) < 1.0E-7) {
         return 0.0;
      } else {
         AxisCycle â˜ƒ = â˜ƒ.inverse();
         Direction.Axis â˜ƒx = â˜ƒ.cycle(Direction.Axis.X);
         Direction.Axis â˜ƒxx = â˜ƒ.cycle(Direction.Axis.Y);
         Direction.Axis â˜ƒxxx = â˜ƒ.cycle(Direction.Axis.Z);
         double â˜ƒxxxx = â˜ƒ.max(â˜ƒx);
         double â˜ƒxxxxx = â˜ƒ.min(â˜ƒx);
         int â˜ƒxxxxxx = this.findIndex(â˜ƒx, â˜ƒxxxxx + 1.0E-7);
         int â˜ƒxxxxxxx = this.findIndex(â˜ƒx, â˜ƒxxxx - 1.0E-7);
         int â˜ƒxxxxxxxx = Math.max(0, this.findIndex(â˜ƒxx, â˜ƒ.min(â˜ƒxx) + 1.0E-7));
         int â˜ƒxxxxxxxxx = Math.min(this.shape.getSize(â˜ƒxx), this.findIndex(â˜ƒxx, â˜ƒ.max(â˜ƒxx) - 1.0E-7) + 1);
         int â˜ƒxxxxxxxxxx = Math.max(0, this.findIndex(â˜ƒxxx, â˜ƒ.min(â˜ƒxxx) + 1.0E-7));
         int â˜ƒxxxxxxxxxxx = Math.min(this.shape.getSize(â˜ƒxxx), this.findIndex(â˜ƒxxx, â˜ƒ.max(â˜ƒxxx) - 1.0E-7) + 1);
         int â˜ƒxxxxxxxxxxxx = this.shape.getSize(â˜ƒx);
         if (â˜ƒ > 0.0) {
            for(int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxx + 1; â˜ƒxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxx) {
               for(int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxx; â˜ƒxxxxxxxxxxxxxx < â˜ƒxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxx) {
                     if (this.shape.isFullWide(â˜ƒ, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)) {
                        double â˜ƒxxxxxxxxxxxxxxxx = this.get(â˜ƒx, â˜ƒxxxxxxxxxxxxx) - â˜ƒxxxx;
                        if (â˜ƒxxxxxxxxxxxxxxxx >= -1.0E-7) {
                           â˜ƒ = Math.min(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxx);
                        }

                        return â˜ƒ;
                     }
                  }
               }
            }
         } else if (â˜ƒ < 0.0) {
            for(int â˜ƒ = â˜ƒxxxxxx - 1; â˜ƒ >= 0; --â˜ƒ) {
               for(int â˜ƒx = â˜ƒxxxxxxxx; â˜ƒx < â˜ƒxxxxxxxxx; ++â˜ƒx) {
                  for(int â˜ƒxx = â˜ƒxxxxxxxxxx; â˜ƒxx < â˜ƒxxxxxxxxxxx; ++â˜ƒxx) {
                     if (this.shape.isFullWide(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx)) {
                        double â˜ƒxxx = this.get(â˜ƒx, â˜ƒ + 1) - â˜ƒxxxxx;
                        if (â˜ƒxxx <= 1.0E-7) {
                           â˜ƒ = Math.max(â˜ƒ, â˜ƒxxx);
                        }

                        return â˜ƒ;
                     }
                  }
               }
            }
         }

         return â˜ƒ;
      }
   }

   public String toString() {
      return this.isEmpty() ? "EMPTY" : "VoxelShape[" + this.bounds() + "]";
   }
}
