package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class VisGraph {
   private static final int SIZE_IN_BITS = 4;
   private static final int LEN = 16;
   private static final int MASK = 15;
   private static final int SIZE = 4096;
   private static final int X_SHIFT = 0;
   private static final int Z_SHIFT = 4;
   private static final int Y_SHIFT = 8;
   private static final int DX = (int)Math.pow(16.0, 0.0);
   private static final int DZ = (int)Math.pow(16.0, 1.0);
   private static final int DY = (int)Math.pow(16.0, 2.0);
   private static final int INVALID_INDEX = -1;
   private static final Direction[] DIRECTIONS = Direction.values();
   private final BitSet bitSet = new BitSet(4096);
   private static final int[] INDEX_OF_EDGES = Util.make(new int[1352], var0 -> {
      int â˜ƒ = 0;
      int â˜ƒx = 15;
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 16; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
               if (â˜ƒxxx == 0 || â˜ƒxxx == 15 || â˜ƒxxxx == 0 || â˜ƒxxxx == 15 || â˜ƒxxxxx == 0 || â˜ƒxxxxx == 15) {
                  var0[â˜ƒxx++] = getIndex(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
               }
            }
         }
      }
   });
   private int empty = 4096;

   public void setOpaque(BlockPos var1) {
      this.bitSet.set(getIndex(â˜ƒ), true);
      --this.empty;
   }

   private static int getIndex(BlockPos var0) {
      return getIndex(â˜ƒ.getX() & 15, â˜ƒ.getY() & 15, â˜ƒ.getZ() & 15);
   }

   private static int getIndex(int var0, int var1, int var2) {
      return â˜ƒ << 0 | â˜ƒ << 8 | â˜ƒ << 4;
   }

   public VisibilitySet resolve() {
      VisibilitySet â˜ƒ = new VisibilitySet();
      if (4096 - this.empty < 256) {
         â˜ƒ.setAll(true);
      } else if (this.empty == 0) {
         â˜ƒ.setAll(false);
      } else {
         for(int â˜ƒ : INDEX_OF_EDGES) {
            if (!this.bitSet.get(â˜ƒ)) {
               â˜ƒ.add(this.floodFill(â˜ƒ));
            }
         }
      }

      return â˜ƒ;
   }

   private Set<Direction> floodFill(int var1) {
      Set<Direction> â˜ƒ = EnumSet.noneOf(Direction.class);
      IntPriorityQueue â˜ƒx = new IntArrayFIFOQueue();
      â˜ƒx.enqueue(â˜ƒ);
      this.bitSet.set(â˜ƒ, true);

      while(!â˜ƒx.isEmpty()) {
         int â˜ƒxx = â˜ƒx.dequeueInt();
         this.addEdges(â˜ƒxx, â˜ƒ);

         for(Direction â˜ƒxxx : DIRECTIONS) {
            int â˜ƒxxxx = this.getNeighborIndexAtFace(â˜ƒxx, â˜ƒxxx);
            if (â˜ƒxxxx >= 0 && !this.bitSet.get(â˜ƒxxxx)) {
               this.bitSet.set(â˜ƒxxxx, true);
               â˜ƒx.enqueue(â˜ƒxxxx);
            }
         }
      }

      return â˜ƒ;
   }

   private void addEdges(int var1, Set<Direction> var2) {
      int â˜ƒ = â˜ƒ >> 0 & 15;
      if (â˜ƒ == 0) {
         â˜ƒ.add(Direction.WEST);
      } else if (â˜ƒ == 15) {
         â˜ƒ.add(Direction.EAST);
      }

      int â˜ƒ = â˜ƒ >> 8 & 15;
      if (â˜ƒ == 0) {
         â˜ƒ.add(Direction.DOWN);
      } else if (â˜ƒ == 15) {
         â˜ƒ.add(Direction.UP);
      }

      int â˜ƒ = â˜ƒ >> 4 & 15;
      if (â˜ƒ == 0) {
         â˜ƒ.add(Direction.NORTH);
      } else if (â˜ƒ == 15) {
         â˜ƒ.add(Direction.SOUTH);
      }
   }

   private int getNeighborIndexAtFace(int var1, Direction var2) {
      switch(â˜ƒ) {
         case DOWN:
            if ((â˜ƒ >> 8 & 15) == 0) {
               return -1;
            }

            return â˜ƒ - DY;
         case UP:
            if ((â˜ƒ >> 8 & 15) == 15) {
               return -1;
            }

            return â˜ƒ + DY;
         case NORTH:
            if ((â˜ƒ >> 4 & 15) == 0) {
               return -1;
            }

            return â˜ƒ - DZ;
         case SOUTH:
            if ((â˜ƒ >> 4 & 15) == 15) {
               return -1;
            }

            return â˜ƒ + DZ;
         case WEST:
            if ((â˜ƒ >> 0 & 15) == 0) {
               return -1;
            }

            return â˜ƒ - DX;
         case EAST:
            if ((â˜ƒ >> 0 & 15) == 15) {
               return -1;
            }

            return â˜ƒ + DX;
         default:
            return -1;
      }
   }
}
