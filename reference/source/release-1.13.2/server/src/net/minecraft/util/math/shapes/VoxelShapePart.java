package net.minecraft.util.math.shapes;

import net.minecraft.util.AxisRotation;
import net.minecraft.util.EnumFacing;

public abstract class VoxelShapePart {
   private static final EnumFacing.Axis[] field_199626_e = EnumFacing.Axis.values();
   protected final int field_197838_b;
   protected final int field_197839_c;
   protected final int field_197840_d;

   protected VoxelShapePart(int var1, int var2, int var3) {
      this.field_197838_b = ☃;
      this.field_197839_c = ☃;
      this.field_197840_d = ☃;
   }

   public boolean func_197824_a(AxisRotation var1, int var2, int var3, int var4) {
      return this.func_197818_c(
         ☃.func_197517_a(☃, ☃, ☃, EnumFacing.Axis.X), ☃.func_197517_a(☃, ☃, ☃, EnumFacing.Axis.Y), ☃.func_197517_a(☃, ☃, ☃, EnumFacing.Axis.Z)
      );
   }

   public boolean func_197818_c(int var1, int var2, int var3) {
      if (☃ < 0 || ☃ < 0 || ☃ < 0) {
         return false;
      } else {
         return ☃ < this.field_197838_b && ☃ < this.field_197839_c && ☃ < this.field_197840_d ? this.func_197835_b(☃, ☃, ☃) : false;
      }
   }

   public boolean func_197829_b(AxisRotation var1, int var2, int var3, int var4) {
      return this.func_197835_b(
         ☃.func_197517_a(☃, ☃, ☃, EnumFacing.Axis.X), ☃.func_197517_a(☃, ☃, ☃, EnumFacing.Axis.Y), ☃.func_197517_a(☃, ☃, ☃, EnumFacing.Axis.Z)
      );
   }

   public abstract boolean func_197835_b(int var1, int var2, int var3);

   public abstract void func_199625_a(int var1, int var2, int var3, boolean var4, boolean var5);

   public boolean func_197830_a() {
      for(EnumFacing.Axis ☃ : field_199626_e) {
         if (this.func_199623_a(☃) >= this.func_199624_b(☃)) {
            return true;
         }
      }

      return false;
   }

   public abstract int func_199623_a(EnumFacing.Axis var1);

   public abstract int func_199624_b(EnumFacing.Axis var1);

   public int func_197819_a(EnumFacing.Axis var1) {
      return ☃.func_196052_a(this.field_197838_b, this.field_197839_c, this.field_197840_d);
   }

   public int func_197823_b() {
      return this.func_197819_a(EnumFacing.Axis.X);
   }

   public int func_197820_c() {
      return this.func_197819_a(EnumFacing.Axis.Y);
   }

   public int func_197821_d() {
      return this.func_197819_a(EnumFacing.Axis.Z);
   }

   protected boolean func_197833_a(int var1, int var2, int var3, int var4) {
      for(int ☃ = ☃; ☃ < ☃; ++☃) {
         if (!this.func_197818_c(☃, ☃, ☃)) {
            return false;
         }
      }

      return true;
   }

   protected void func_197834_a(int var1, int var2, int var3, int var4, boolean var5) {
      for(int ☃ = ☃; ☃ < ☃; ++☃) {
         this.func_199625_a(☃, ☃, ☃, false, ☃);
      }
   }

   protected boolean func_197827_a(int var1, int var2, int var3, int var4, int var5) {
      for(int ☃ = ☃; ☃ < ☃; ++☃) {
         if (!this.func_197833_a(☃, ☃, ☃, ☃)) {
            return false;
         }
      }

      return true;
   }

   public void func_197831_b(VoxelShapePart.LineConsumer var1, boolean var2) {
      VoxelShapePart ☃ = new VoxelShapePartBitSet(this);

      for(int ☃x = 0; ☃x <= this.field_197838_b; ++☃x) {
         for(int ☃xx = 0; ☃xx <= this.field_197839_c; ++☃xx) {
            int ☃xxx = -1;

            for(int ☃xxxx = 0; ☃xxxx <= this.field_197840_d; ++☃xxxx) {
               if (☃.func_197818_c(☃x, ☃xx, ☃xxxx)) {
                  if (☃) {
                     if (☃xxx == -1) {
                        ☃xxx = ☃xxxx;
                     }
                  } else {
                     ☃.consume(☃x, ☃xx, ☃xxxx, ☃x + 1, ☃xx + 1, ☃xxxx + 1);
                  }
               } else if (☃xxx != -1) {
                  int ☃xxxxx = ☃x;
                  int ☃xxxxxx = ☃x;
                  int ☃xxxxxxx = ☃xx;
                  int ☃xxxxxxxx = ☃xx;
                  ☃.func_197834_a(☃xxx, ☃xxxx, ☃x, ☃xx, false);

                  while(☃.func_197833_a(☃xxx, ☃xxxx, ☃xxxxx - 1, ☃xxxxxxx)) {
                     ☃.func_197834_a(☃xxx, ☃xxxx, ☃xxxxx - 1, ☃xxxxxxx, false);
                     --☃xxxxx;
                  }

                  while(☃.func_197833_a(☃xxx, ☃xxxx, ☃xxxxxx + 1, ☃xxxxxxx)) {
                     ☃.func_197834_a(☃xxx, ☃xxxx, ☃xxxxxx + 1, ☃xxxxxxx, false);
                     ++☃xxxxxx;
                  }

                  while(☃.func_197827_a(☃xxxxx, ☃xxxxxx + 1, ☃xxx, ☃xxxx, ☃xxxxxxx - 1)) {
                     for(int ☃xxxxxxxxx = ☃xxxxx; ☃xxxxxxxxx <= ☃xxxxxx; ++☃xxxxxxxxx) {
                        ☃.func_197834_a(☃xxx, ☃xxxx, ☃xxxxxxxxx, ☃xxxxxxx - 1, false);
                     }

                     --☃xxxxxxx;
                  }

                  while(☃.func_197827_a(☃xxxxx, ☃xxxxxx + 1, ☃xxx, ☃xxxx, ☃xxxxxxxx + 1)) {
                     for(int ☃xxxxxxxxx = ☃xxxxx; ☃xxxxxxxxx <= ☃xxxxxx; ++☃xxxxxxxxx) {
                        ☃.func_197834_a(☃xxx, ☃xxxx, ☃xxxxxxxxx, ☃xxxxxxxx + 1, false);
                     }

                     ++☃xxxxxxxx;
                  }

                  ☃.consume(☃xxxxx, ☃xxxxxxx, ☃xxx, ☃xxxxxx + 1, ☃xxxxxxxx + 1, ☃xxxx);
                  ☃xxx = -1;
               }
            }
         }
      }
   }

   public void func_211540_a(VoxelShapePart.FaceConsumer var1) {
      this.func_211541_a(☃, AxisRotation.NONE);
      this.func_211541_a(☃, AxisRotation.FORWARD);
      this.func_211541_a(☃, AxisRotation.BACKWARD);
   }

   private void func_211541_a(VoxelShapePart.FaceConsumer var1, AxisRotation var2) {
      AxisRotation ☃ = ☃.func_197514_a();
      EnumFacing.Axis ☃x = ☃.func_197513_a(EnumFacing.Axis.Z);
      int ☃xx = this.func_197819_a(☃.func_197513_a(EnumFacing.Axis.X));
      int ☃xxx = this.func_197819_a(☃.func_197513_a(EnumFacing.Axis.Y));
      int ☃xxxx = this.func_197819_a(☃x);
      EnumFacing ☃xxxxx = EnumFacing.func_211699_a(☃x, EnumFacing.AxisDirection.NEGATIVE);
      EnumFacing ☃xxxxxx = EnumFacing.func_211699_a(☃x, EnumFacing.AxisDirection.POSITIVE);

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xx; ++☃xxxxxxx) {
         for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxx; ++☃xxxxxxxx) {
            boolean ☃xxxxxxxxx = false;

            for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx <= ☃xxxx; ++☃xxxxxxxxxx) {
               boolean ☃xxxxxxxxxxx = ☃xxxxxxxxxx != ☃xxxx && this.func_197829_b(☃, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx);
               if (!☃xxxxxxxxx && ☃xxxxxxxxxxx) {
                  ☃.consume(
                     ☃xxxxx,
                     ☃.func_197517_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx, EnumFacing.Axis.X),
                     ☃.func_197517_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx, EnumFacing.Axis.Y),
                     ☃.func_197517_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx, EnumFacing.Axis.Z)
                  );
               }

               if (☃xxxxxxxxx && !☃xxxxxxxxxxx) {
                  ☃.consume(
                     ☃xxxxxx,
                     ☃.func_197517_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx - 1, EnumFacing.Axis.X),
                     ☃.func_197517_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx - 1, EnumFacing.Axis.Y),
                     ☃.func_197517_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxx - 1, EnumFacing.Axis.Z)
                  );
               }

               ☃xxxxxxxxx = ☃xxxxxxxxxxx;
            }
         }
      }
   }

   public interface FaceConsumer {
      void consume(EnumFacing var1, int var2, int var3, int var4);
   }

   public interface LineConsumer {
      void consume(int var1, int var2, int var3, int var4, int var5, int var6);
   }
}
