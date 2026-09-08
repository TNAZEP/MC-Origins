package net.minecraft.world.phys.shapes;

import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;

public abstract class DiscreteVoxelShape {
   private static final Direction.Axis[] AXIS_VALUES = Direction.Axis.values();
   protected final int xSize;
   protected final int ySize;
   protected final int zSize;

   protected DiscreteVoxelShape(int var1, int var2, int var3) {
      if (â˜ƒ >= 0 && â˜ƒ >= 0 && â˜ƒ >= 0) {
         this.xSize = â˜ƒ;
         this.ySize = â˜ƒ;
         this.zSize = â˜ƒ;
      } else {
         throw new IllegalArgumentException("Need all positive sizes: x: " + â˜ƒ + ", y: " + â˜ƒ + ", z: " + â˜ƒ);
      }
   }

   public boolean isFullWide(AxisCycle var1, int var2, int var3, int var4) {
      return this.isFullWide(â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.X), â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.Y), â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.Z));
   }

   public boolean isFullWide(int var1, int var2, int var3) {
      if (â˜ƒ < 0 || â˜ƒ < 0 || â˜ƒ < 0) {
         return false;
      } else {
         return â˜ƒ < this.xSize && â˜ƒ < this.ySize && â˜ƒ < this.zSize ? this.isFull(â˜ƒ, â˜ƒ, â˜ƒ) : false;
      }
   }

   public boolean isFull(AxisCycle var1, int var2, int var3, int var4) {
      return this.isFull(â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.X), â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.Y), â˜ƒ.cycle(â˜ƒ, â˜ƒ, â˜ƒ, Direction.Axis.Z));
   }

   public abstract boolean isFull(int var1, int var2, int var3);

   public abstract void fill(int var1, int var2, int var3);

   public boolean isEmpty() {
      for(Direction.Axis â˜ƒ : AXIS_VALUES) {
         if (this.firstFull(â˜ƒ) >= this.lastFull(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }

   public abstract int firstFull(Direction.Axis var1);

   public abstract int lastFull(Direction.Axis var1);

   public int firstFull(Direction.Axis var1, int var2, int var3) {
      int â˜ƒ = this.getSize(â˜ƒ);
      if (â˜ƒ >= 0 && â˜ƒ >= 0) {
         Direction.Axis â˜ƒx = AxisCycle.FORWARD.cycle(â˜ƒ);
         Direction.Axis â˜ƒxx = AxisCycle.BACKWARD.cycle(â˜ƒ);
         if (â˜ƒ < this.getSize(â˜ƒx) && â˜ƒ < this.getSize(â˜ƒxx)) {
            AxisCycle â˜ƒxxx = AxisCycle.between(Direction.Axis.X, â˜ƒ);

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ; ++â˜ƒxxxx) {
               if (this.isFull(â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒ)) {
                  return â˜ƒxxxx;
               }
            }

            return â˜ƒ;
         } else {
            return â˜ƒ;
         }
      } else {
         return â˜ƒ;
      }
   }

   public int lastFull(Direction.Axis var1, int var2, int var3) {
      if (â˜ƒ >= 0 && â˜ƒ >= 0) {
         Direction.Axis â˜ƒ = AxisCycle.FORWARD.cycle(â˜ƒ);
         Direction.Axis â˜ƒx = AxisCycle.BACKWARD.cycle(â˜ƒ);
         if (â˜ƒ < this.getSize(â˜ƒ) && â˜ƒ < this.getSize(â˜ƒx)) {
            int â˜ƒxx = this.getSize(â˜ƒ);
            AxisCycle â˜ƒxxx = AxisCycle.between(Direction.Axis.X, â˜ƒ);

            for(int â˜ƒxxxx = â˜ƒxx - 1; â˜ƒxxxx >= 0; --â˜ƒxxxx) {
               if (this.isFull(â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒ)) {
                  return â˜ƒxxxx + 1;
               }
            }

            return 0;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public int getSize(Direction.Axis var1) {
      return â˜ƒ.choose(this.xSize, this.ySize, this.zSize);
   }

   public int getXSize() {
      return this.getSize(Direction.Axis.X);
   }

   public int getYSize() {
      return this.getSize(Direction.Axis.Y);
   }

   public int getZSize() {
      return this.getSize(Direction.Axis.Z);
   }

   public void forAllEdges(DiscreteVoxelShape.IntLineConsumer var1, boolean var2) {
      this.forAllAxisEdges(â˜ƒ, AxisCycle.NONE, â˜ƒ);
      this.forAllAxisEdges(â˜ƒ, AxisCycle.FORWARD, â˜ƒ);
      this.forAllAxisEdges(â˜ƒ, AxisCycle.BACKWARD, â˜ƒ);
   }

   private void forAllAxisEdges(DiscreteVoxelShape.IntLineConsumer var1, AxisCycle var2, boolean var3) {
      AxisCycle â˜ƒ = â˜ƒ.inverse();
      int â˜ƒx = this.getSize(â˜ƒ.cycle(Direction.Axis.X));
      int â˜ƒxx = this.getSize(â˜ƒ.cycle(Direction.Axis.Y));
      int â˜ƒxxx = this.getSize(â˜ƒ.cycle(Direction.Axis.Z));

      for(int â˜ƒxxxx = 0; â˜ƒxxxx <= â˜ƒx; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= â˜ƒxx; ++â˜ƒxxxxx) {
            int â˜ƒxxxxxx = -1;

            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxxx) {
               int â˜ƒxxxxxxxx = 0;
               int â˜ƒxxxxxxxxx = 0;

               for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx <= 1; ++â˜ƒxxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx <= 1; ++â˜ƒxxxxxxxxxxx) {
                     if (this.isFullWide(â˜ƒ, â˜ƒxxxx + â˜ƒxxxxxxxxxx - 1, â˜ƒxxxxx + â˜ƒxxxxxxxxxxx - 1, â˜ƒxxxxxxx)) {
                        ++â˜ƒxxxxxxxx;
                        â˜ƒxxxxxxxxx ^= â˜ƒxxxxxxxxxx ^ â˜ƒxxxxxxxxxxx;
                     }
                  }
               }

               if (â˜ƒxxxxxxxx == 1 || â˜ƒxxxxxxxx == 3 || â˜ƒxxxxxxxx == 2 && (â˜ƒxxxxxxxxx & 1) == 0) {
                  if (â˜ƒ) {
                     if (â˜ƒxxxxxx == -1) {
                        â˜ƒxxxxxx = â˜ƒxxxxxxx;
                     }
                  } else {
                     â˜ƒ.consume(
                        â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, Direction.Axis.X),
                        â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, Direction.Axis.Y),
                        â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, Direction.Axis.Z),
                        â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx + 1, Direction.Axis.X),
                        â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx + 1, Direction.Axis.Y),
                        â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx + 1, Direction.Axis.Z)
                     );
                  }
               } else if (â˜ƒxxxxxx != -1) {
                  â˜ƒ.consume(
                     â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, Direction.Axis.X),
                     â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, Direction.Axis.Y),
                     â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, Direction.Axis.Z),
                     â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, Direction.Axis.X),
                     â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, Direction.Axis.Y),
                     â˜ƒ.cycle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx, Direction.Axis.Z)
                  );
                  â˜ƒxxxxxx = -1;
               }
            }
         }
      }
   }

   public void forAllBoxes(DiscreteVoxelShape.IntLineConsumer var1, boolean var2) {
      BitSetDiscreteVoxelShape.forAllBoxes(this, â˜ƒ, â˜ƒ);
   }

   public void forAllFaces(DiscreteVoxelShape.IntFaceConsumer var1) {
      this.forAllAxisFaces(â˜ƒ, AxisCycle.NONE);
      this.forAllAxisFaces(â˜ƒ, AxisCycle.FORWARD);
      this.forAllAxisFaces(â˜ƒ, AxisCycle.BACKWARD);
   }

   private void forAllAxisFaces(DiscreteVoxelShape.IntFaceConsumer var1, AxisCycle var2) {
      AxisCycle â˜ƒ = â˜ƒ.inverse();
      Direction.Axis â˜ƒx = â˜ƒ.cycle(Direction.Axis.Z);
      int â˜ƒxx = this.getSize(â˜ƒ.cycle(Direction.Axis.X));
      int â˜ƒxxx = this.getSize(â˜ƒ.cycle(Direction.Axis.Y));
      int â˜ƒxxxx = this.getSize(â˜ƒx);
      Direction â˜ƒxxxxx = Direction.fromAxisAndDirection(â˜ƒx, Direction.AxisDirection.NEGATIVE);
      Direction â˜ƒxxxxxx = Direction.fromAxisAndDirection(â˜ƒx, Direction.AxisDirection.POSITIVE);

      for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxx; ++â˜ƒxxxxxxx) {
         for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxxxx) {
            boolean â˜ƒxxxxxxxxx = false;

            for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx <= â˜ƒxxxx; ++â˜ƒxxxxxxxxxx) {
               boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx != â˜ƒxxxx && this.isFull(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx);
               if (!â˜ƒxxxxxxxxx && â˜ƒxxxxxxxxxxx) {
                  â˜ƒ.consume(
                     â˜ƒxxxxx,
                     â˜ƒ.cycle(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, Direction.Axis.X),
                     â˜ƒ.cycle(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, Direction.Axis.Y),
                     â˜ƒ.cycle(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, Direction.Axis.Z)
                  );
               }

               if (â˜ƒxxxxxxxxx && !â˜ƒxxxxxxxxxxx) {
                  â˜ƒ.consume(
                     â˜ƒxxxxxx,
                     â˜ƒ.cycle(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx - 1, Direction.Axis.X),
                     â˜ƒ.cycle(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx - 1, Direction.Axis.Y),
                     â˜ƒ.cycle(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx - 1, Direction.Axis.Z)
                  );
               }

               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxx;
            }
         }
      }
   }

   public interface IntFaceConsumer {
      void consume(Direction var1, int var2, int var3, int var4);
   }

   public interface IntLineConsumer {
      void consume(int var1, int var2, int var3, int var4, int var5, int var6);
   }
}
