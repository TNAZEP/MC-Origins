package net.minecraft.world.phys.shapes;

import java.util.BitSet;
import net.minecraft.core.Direction;

public final class BitSetDiscreteVoxelShape extends DiscreteVoxelShape {
   private final BitSet storage;
   private int xMin;
   private int yMin;
   private int zMin;
   private int xMax;
   private int yMax;
   private int zMax;

   public BitSetDiscreteVoxelShape(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.storage = new BitSet(â˜ƒ * â˜ƒ * â˜ƒ);
      this.xMin = â˜ƒ;
      this.yMin = â˜ƒ;
      this.zMin = â˜ƒ;
   }

   public static BitSetDiscreteVoxelShape withFilledBounds(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      BitSetDiscreteVoxelShape â˜ƒ = new BitSetDiscreteVoxelShape(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.xMin = â˜ƒ;
      â˜ƒ.yMin = â˜ƒ;
      â˜ƒ.zMin = â˜ƒ;
      â˜ƒ.xMax = â˜ƒ;
      â˜ƒ.yMax = â˜ƒ;
      â˜ƒ.zMax = â˜ƒ;

      for(int â˜ƒx = â˜ƒ; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         for(int â˜ƒxx = â˜ƒ; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            for(int â˜ƒxxx = â˜ƒ; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
               â˜ƒ.fillUpdateBounds(â˜ƒx, â˜ƒxx, â˜ƒxxx, false);
            }
         }
      }

      return â˜ƒ;
   }

   public BitSetDiscreteVoxelShape(DiscreteVoxelShape var1) {
      super(â˜ƒ.xSize, â˜ƒ.ySize, â˜ƒ.zSize);
      if (â˜ƒ instanceof BitSetDiscreteVoxelShape) {
         this.storage = (BitSet)((BitSetDiscreteVoxelShape)â˜ƒ).storage.clone();
      } else {
         this.storage = new BitSet(this.xSize * this.ySize * this.zSize);

         for(int â˜ƒ = 0; â˜ƒ < this.xSize; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < this.ySize; ++â˜ƒx) {
               for(int â˜ƒxx = 0; â˜ƒxx < this.zSize; ++â˜ƒxx) {
                  if (â˜ƒ.isFull(â˜ƒ, â˜ƒx, â˜ƒxx)) {
                     this.storage.set(this.getIndex(â˜ƒ, â˜ƒx, â˜ƒxx));
                  }
               }
            }
         }
      }

      this.xMin = â˜ƒ.firstFull(Direction.Axis.X);
      this.yMin = â˜ƒ.firstFull(Direction.Axis.Y);
      this.zMin = â˜ƒ.firstFull(Direction.Axis.Z);
      this.xMax = â˜ƒ.lastFull(Direction.Axis.X);
      this.yMax = â˜ƒ.lastFull(Direction.Axis.Y);
      this.zMax = â˜ƒ.lastFull(Direction.Axis.Z);
   }

   protected int getIndex(int var1, int var2, int var3) {
      return (â˜ƒ * this.ySize + â˜ƒ) * this.zSize + â˜ƒ;
   }

   @Override
   public boolean isFull(int var1, int var2, int var3) {
      return this.storage.get(this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   private void fillUpdateBounds(int var1, int var2, int var3, boolean var4) {
      this.storage.set(this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
      if (â˜ƒ) {
         this.xMin = Math.min(this.xMin, â˜ƒ);
         this.yMin = Math.min(this.yMin, â˜ƒ);
         this.zMin = Math.min(this.zMin, â˜ƒ);
         this.xMax = Math.max(this.xMax, â˜ƒ + 1);
         this.yMax = Math.max(this.yMax, â˜ƒ + 1);
         this.zMax = Math.max(this.zMax, â˜ƒ + 1);
      }
   }

   @Override
   public void fill(int var1, int var2, int var3) {
      this.fillUpdateBounds(â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   @Override
   public boolean isEmpty() {
      return this.storage.isEmpty();
   }

   @Override
   public int firstFull(Direction.Axis var1) {
      return â˜ƒ.choose(this.xMin, this.yMin, this.zMin);
   }

   @Override
   public int lastFull(Direction.Axis var1) {
      return â˜ƒ.choose(this.xMax, this.yMax, this.zMax);
   }

   static BitSetDiscreteVoxelShape join(DiscreteVoxelShape var0, DiscreteVoxelShape var1, IndexMerger var2, IndexMerger var3, IndexMerger var4, BooleanOp var5) {
      BitSetDiscreteVoxelShape â˜ƒ = new BitSetDiscreteVoxelShape(â˜ƒ.size() - 1, â˜ƒ.size() - 1, â˜ƒ.size() - 1);
      int[] â˜ƒx = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      â˜ƒ.forMergedIndexes((var7x, var8, var9) -> {
         boolean[] â˜ƒ = new boolean[]{false};
         â˜ƒ.forMergedIndexes((var10x, var11, var12) -> {
            boolean[] â˜ƒ = new boolean[]{false};
            â˜ƒ.forMergedIndexes((var12x, var13x, var14) -> {
               if (â˜ƒ.apply(â˜ƒ.isFullWide(var7x, var10x, var12x), â˜ƒ.isFullWide(var8, var11, var13x))) {
                  â˜ƒ.storage.set(â˜ƒ.getIndex(var9, var12, var14));
                  â˜ƒ[2] = Math.min(â˜ƒ[2], var14);
                  â˜ƒ[5] = Math.max(â˜ƒ[5], var14);
                  â˜ƒ[0] = true;
               }

               return true;
            });
            if (â˜ƒ[0]) {
               â˜ƒ[1] = Math.min(â˜ƒ[1], var12);
               â˜ƒ[4] = Math.max(â˜ƒ[4], var12);
               â˜ƒ[0] = true;
            }

            return true;
         });
         if (â˜ƒ[0]) {
            â˜ƒ[0] = Math.min(â˜ƒ[0], var9);
            â˜ƒ[3] = Math.max(â˜ƒ[3], var9);
         }

         return true;
      });
      â˜ƒ.xMin = â˜ƒx[0];
      â˜ƒ.yMin = â˜ƒx[1];
      â˜ƒ.zMin = â˜ƒx[2];
      â˜ƒ.xMax = â˜ƒx[3] + 1;
      â˜ƒ.yMax = â˜ƒx[4] + 1;
      â˜ƒ.zMax = â˜ƒx[5] + 1;
      return â˜ƒ;
   }

   protected static void forAllBoxes(DiscreteVoxelShape var0, DiscreteVoxelShape.IntLineConsumer var1, boolean var2) {
      BitSetDiscreteVoxelShape â˜ƒ = new BitSetDiscreteVoxelShape(â˜ƒ);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.xSize; ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.ySize; ++â˜ƒxx) {
            int â˜ƒxxx = -1;

            for(int â˜ƒxxxx = 0; â˜ƒxxxx <= â˜ƒ.zSize; ++â˜ƒxxxx) {
               if (â˜ƒ.isFullWide(â˜ƒx, â˜ƒxx, â˜ƒxxxx)) {
                  if (â˜ƒ) {
                     if (â˜ƒxxx == -1) {
                        â˜ƒxxx = â˜ƒxxxx;
                     }
                  } else {
                     â˜ƒ.consume(â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒx + 1, â˜ƒxx + 1, â˜ƒxxxx + 1);
                  }
               } else if (â˜ƒxxx != -1) {
                  int â˜ƒxxxxx = â˜ƒx;
                  int â˜ƒxxxxxx = â˜ƒxx;
                  â˜ƒ.clearZStrip(â˜ƒxxx, â˜ƒxxxx, â˜ƒx, â˜ƒxx);

                  while(â˜ƒ.isZStripFull(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx + 1, â˜ƒxx)) {
                     â˜ƒ.clearZStrip(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx + 1, â˜ƒxx);
                     ++â˜ƒxxxxx;
                  }

                  while(â˜ƒ.isXZRectangleFull(â˜ƒx, â˜ƒxxxxx + 1, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxx + 1)) {
                     for(int â˜ƒxxxxxxx = â˜ƒx; â˜ƒxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
                        â˜ƒ.clearZStrip(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx + 1);
                     }

                     ++â˜ƒxxxxxx;
                  }

                  â˜ƒ.consume(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxxx + 1, â˜ƒxxxxxx + 1, â˜ƒxxxx);
                  â˜ƒxxx = -1;
               }
            }
         }
      }
   }

   private boolean isZStripFull(int var1, int var2, int var3, int var4) {
      if (â˜ƒ < this.xSize && â˜ƒ < this.ySize) {
         return this.storage.nextClearBit(this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ)) >= this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return false;
      }
   }

   private boolean isXZRectangleFull(int var1, int var2, int var3, int var4, int var5) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         if (!this.isZStripFull(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return false;
         }
      }

      return true;
   }

   private void clearZStrip(int var1, int var2, int var3, int var4) {
      this.storage.clear(this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ), this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
   }
}
