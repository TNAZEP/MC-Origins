package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

public class VisGraph {
   private static final int field_178616_a = (int)Math.pow(16.0, 0.0);
   private static final int field_178614_b = (int)Math.pow(16.0, 1.0);
   private static final int field_178615_c = (int)Math.pow(16.0, 2.0);
   private static final EnumFacing[] field_200008_d = EnumFacing.values();
   private final BitSet field_178612_d = new BitSet(4096);
   private static final int[] field_178613_e = Util.func_200696_a(new int[1352], var0 -> {
      int ☃ = 0;
      int ☃x = 15;
      int ☃xx = 0;

      for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
         for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
            for(int ☃xxxxx = 0; ☃xxxxx < 16; ++☃xxxxx) {
               if (☃xxx == 0 || ☃xxx == 15 || ☃xxxx == 0 || ☃xxxx == 15 || ☃xxxxx == 0 || ☃xxxxx == 15) {
                  var0[☃xx++] = func_178605_a(☃xxx, ☃xxxx, ☃xxxxx);
               }
            }
         }
      }
   });
   private int field_178611_f = 4096;

   public void func_178606_a(BlockPos var1) {
      this.field_178612_d.set(func_178608_c(☃), true);
      --this.field_178611_f;
   }

   private static int func_178608_c(BlockPos var0) {
      return func_178605_a(☃.func_177958_n() & 15, ☃.func_177956_o() & 15, ☃.func_177952_p() & 15);
   }

   private static int func_178605_a(int var0, int var1, int var2) {
      return ☃ << 0 | ☃ << 8 | ☃ << 4;
   }

   public SetVisibility func_178607_a() {
      SetVisibility ☃ = new SetVisibility();
      if (4096 - this.field_178611_f < 256) {
         ☃.func_178618_a(true);
      } else if (this.field_178611_f == 0) {
         ☃.func_178618_a(false);
      } else {
         for(int ☃ : field_178613_e) {
            if (!this.field_178612_d.get(☃)) {
               ☃.func_178620_a(this.func_178604_a(☃));
            }
         }
      }

      return ☃;
   }

   public Set<EnumFacing> func_178609_b(BlockPos var1) {
      return this.func_178604_a(func_178608_c(☃));
   }

   private Set<EnumFacing> func_178604_a(int var1) {
      Set<EnumFacing> ☃ = EnumSet.noneOf(EnumFacing.class);
      IntPriorityQueue ☃x = new IntArrayFIFOQueue();
      ☃x.enqueue(☃);
      this.field_178612_d.set(☃, true);

      while(!☃x.isEmpty()) {
         int ☃xx = ☃x.dequeueInt();
         this.func_178610_a(☃xx, ☃);

         for(EnumFacing ☃xxx : field_200008_d) {
            int ☃xxxx = this.func_178603_a(☃xx, ☃xxx);
            if (☃xxxx >= 0 && !this.field_178612_d.get(☃xxxx)) {
               this.field_178612_d.set(☃xxxx, true);
               ☃x.enqueue(☃xxxx);
            }
         }
      }

      return ☃;
   }

   private void func_178610_a(int var1, Set<EnumFacing> var2) {
      int ☃ = ☃ >> 0 & 15;
      if (☃ == 0) {
         ☃.add(EnumFacing.WEST);
      } else if (☃ == 15) {
         ☃.add(EnumFacing.EAST);
      }

      int ☃ = ☃ >> 8 & 15;
      if (☃ == 0) {
         ☃.add(EnumFacing.DOWN);
      } else if (☃ == 15) {
         ☃.add(EnumFacing.UP);
      }

      int ☃ = ☃ >> 4 & 15;
      if (☃ == 0) {
         ☃.add(EnumFacing.NORTH);
      } else if (☃ == 15) {
         ☃.add(EnumFacing.SOUTH);
      }
   }

   private int func_178603_a(int var1, EnumFacing var2) {
      switch(☃) {
         case DOWN:
            if ((☃ >> 8 & 15) == 0) {
               return -1;
            }

            return ☃ - field_178615_c;
         case UP:
            if ((☃ >> 8 & 15) == 15) {
               return -1;
            }

            return ☃ + field_178615_c;
         case NORTH:
            if ((☃ >> 4 & 15) == 0) {
               return -1;
            }

            return ☃ - field_178614_b;
         case SOUTH:
            if ((☃ >> 4 & 15) == 15) {
               return -1;
            }

            return ☃ + field_178614_b;
         case WEST:
            if ((☃ >> 0 & 15) == 0) {
               return -1;
            }

            return ☃ - field_178616_a;
         case EAST:
            if ((☃ >> 0 & 15) == 15) {
               return -1;
            }

            return ☃ + field_178616_a;
         default:
            return -1;
      }
   }
}
