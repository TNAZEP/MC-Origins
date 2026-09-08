package net.minecraft.pathfinding;

public class PathHeap {
   private PathPoint[] field_75852_a = new PathPoint[128];
   private int field_75851_b;

   public PathPoint func_75849_a(PathPoint var1) {
      if (☃.field_75835_d >= 0) {
         throw new IllegalStateException("OW KNOWS!");
      } else {
         if (this.field_75851_b == this.field_75852_a.length) {
            PathPoint[] ☃ = new PathPoint[this.field_75851_b << 1];
            System.arraycopy(this.field_75852_a, 0, ☃, 0, this.field_75851_b);
            this.field_75852_a = ☃;
         }

         this.field_75852_a[this.field_75851_b] = ☃;
         ☃.field_75835_d = this.field_75851_b;
         this.func_75847_a(this.field_75851_b++);
         return ☃;
      }
   }

   public void func_75848_a() {
      this.field_75851_b = 0;
   }

   public PathPoint func_75844_c() {
      PathPoint ☃ = this.field_75852_a[0];
      this.field_75852_a[0] = this.field_75852_a[--this.field_75851_b];
      this.field_75852_a[this.field_75851_b] = null;
      if (this.field_75851_b > 0) {
         this.func_75846_b(0);
      }

      ☃.field_75835_d = -1;
      return ☃;
   }

   public void func_75850_a(PathPoint var1, float var2) {
      float ☃ = ☃.field_75834_g;
      ☃.field_75834_g = ☃;
      if (☃ < ☃) {
         this.func_75847_a(☃.field_75835_d);
      } else {
         this.func_75846_b(☃.field_75835_d);
      }
   }

   private void func_75847_a(int var1) {
      PathPoint ☃ = this.field_75852_a[☃];

      int ☃;
      for(float ☃x = ☃.field_75834_g; ☃ > 0; ☃ = ☃) {
         ☃ = ☃ - 1 >> 1;
         PathPoint ☃xx = this.field_75852_a[☃];
         if (!(☃x < ☃xx.field_75834_g)) {
            break;
         }

         this.field_75852_a[☃] = ☃xx;
         ☃xx.field_75835_d = ☃;
      }

      this.field_75852_a[☃] = ☃;
      ☃.field_75835_d = ☃;
   }

   private void func_75846_b(int var1) {
      PathPoint ☃ = this.field_75852_a[☃];
      float ☃x = ☃.field_75834_g;

      while(true) {
         int ☃xx = 1 + (☃ << 1);
         int ☃xxx = ☃xx + 1;
         if (☃xx >= this.field_75851_b) {
            break;
         }

         PathPoint ☃xxxx = this.field_75852_a[☃xx];
         float ☃xxxxx = ☃xxxx.field_75834_g;
         PathPoint ☃xx;
         float ☃xxx;
         if (☃xxx >= this.field_75851_b) {
            ☃xx = null;
            ☃xxx = Float.POSITIVE_INFINITY;
         } else {
            ☃xx = this.field_75852_a[☃xxx];
            ☃xxx = ☃xx.field_75834_g;
         }

         if (☃xxxxx < ☃xxx) {
            if (!(☃xxxxx < ☃x)) {
               break;
            }

            this.field_75852_a[☃] = ☃xxxx;
            ☃xxxx.field_75835_d = ☃;
            ☃ = ☃xx;
         } else {
            if (!(☃xxx < ☃x)) {
               break;
            }

            this.field_75852_a[☃] = ☃xx;
            ☃xx.field_75835_d = ☃;
            ☃ = ☃xxx;
         }
      }

      this.field_75852_a[☃] = ☃;
      ☃.field_75835_d = ☃;
   }

   public boolean func_75845_e() {
      return this.field_75851_b == 0;
   }
}
