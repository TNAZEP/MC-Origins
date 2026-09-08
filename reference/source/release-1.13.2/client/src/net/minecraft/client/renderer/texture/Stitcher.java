package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.math.MathHelper;

public class Stitcher {
   private final int field_147971_a;
   private final Set<Stitcher.Holder> field_94319_a = Sets.<Stitcher.Holder>newHashSetWithExpectedSize(256);
   private final List<Stitcher.Slot> field_94317_b = Lists.<Stitcher.Slot>newArrayListWithCapacity(256);
   private int field_94318_c;
   private int field_94315_d;
   private final int field_94316_e;
   private final int field_94313_f;
   private final int field_94323_h;

   public Stitcher(int var1, int var2, int var3, int var4) {
      this.field_147971_a = ☃;
      this.field_94316_e = ☃;
      this.field_94313_f = ☃;
      this.field_94323_h = ☃;
   }

   public int func_110935_a() {
      return this.field_94318_c;
   }

   public int func_110936_b() {
      return this.field_94315_d;
   }

   public void func_110934_a(TextureAtlasSprite var1) {
      Stitcher.Holder ☃ = new Stitcher.Holder(☃, this.field_147971_a);
      if (this.field_94323_h > 0) {
         ☃.func_94196_a(this.field_94323_h);
      }

      this.field_94319_a.add(☃);
   }

   public void func_94305_f() {
      Stitcher.Holder[] ☃ = (Stitcher.Holder[])this.field_94319_a.toArray(new Stitcher.Holder[this.field_94319_a.size()]);
      Arrays.sort(☃);

      for(Stitcher.Holder ☃x : ☃) {
         if (!this.func_94310_b(☃x)) {
            String ☃xx = String.format(
               "Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution resourcepack?",
               ☃x.func_98150_a().func_195668_m(),
               ☃x.func_98150_a().func_94211_a(),
               ☃x.func_98150_a().func_94216_b()
            );
            throw new StitcherException(☃x, ☃xx);
         }
      }

      this.field_94318_c = MathHelper.func_151236_b(this.field_94318_c);
      this.field_94315_d = MathHelper.func_151236_b(this.field_94315_d);
   }

   public List<TextureAtlasSprite> func_94309_g() {
      List<Stitcher.Slot> ☃ = Lists.<Stitcher.Slot>newArrayList();

      for(Stitcher.Slot ☃x : this.field_94317_b) {
         ☃x.func_94184_a(☃);
      }

      List<TextureAtlasSprite> ☃x = Lists.<TextureAtlasSprite>newArrayList();

      for(Stitcher.Slot ☃xx : ☃) {
         Stitcher.Holder ☃xxx = ☃xx.func_94183_a();
         TextureAtlasSprite ☃xxxx = ☃xxx.func_98150_a();
         ☃xxxx.func_110971_a(this.field_94318_c, this.field_94315_d, ☃xx.func_94186_b(), ☃xx.func_94185_c(), ☃xxx.func_94195_e());
         ☃x.add(☃xxxx);
      }

      return ☃x;
   }

   private static int func_147969_b(int var0, int var1) {
      return (☃ >> ☃) + ((☃ & (1 << ☃) - 1) == 0 ? 0 : 1) << ☃;
   }

   private boolean func_94310_b(Stitcher.Holder var1) {
      TextureAtlasSprite ☃ = ☃.func_98150_a();
      boolean ☃x = ☃.func_94211_a() != ☃.func_94216_b();

      for(int ☃xx = 0; ☃xx < this.field_94317_b.size(); ++☃xx) {
         if (((Stitcher.Slot)this.field_94317_b.get(☃xx)).func_94182_a(☃)) {
            return true;
         }

         if (☃x) {
            ☃.func_94194_d();
            if (((Stitcher.Slot)this.field_94317_b.get(☃xx)).func_94182_a(☃)) {
               return true;
            }

            ☃.func_94194_d();
         }
      }

      return this.func_94311_c(☃);
   }

   private boolean func_94311_c(Stitcher.Holder var1) {
      int ☃ = Math.min(☃.func_94197_a(), ☃.func_94199_b());
      int ☃x = Math.max(☃.func_94197_a(), ☃.func_94199_b());
      int ☃xx = MathHelper.func_151236_b(this.field_94318_c);
      int ☃xxx = MathHelper.func_151236_b(this.field_94315_d);
      int ☃xxxx = MathHelper.func_151236_b(this.field_94318_c + ☃);
      int ☃xxxxx = MathHelper.func_151236_b(this.field_94315_d + ☃);
      boolean ☃xxxxxx = ☃xxxx <= this.field_94316_e;
      boolean ☃xxxxxxx = ☃xxxxx <= this.field_94313_f;
      if (!☃xxxxxx && !☃xxxxxxx) {
         return false;
      } else {
         boolean ☃x = ☃xxxxxx && ☃xx != ☃xxxx;
         boolean ☃xx = ☃xxxxxxx && ☃xxx != ☃xxxxx;
         boolean ☃;
         if (☃x ^ ☃xx) {
            ☃ = ☃x;
         } else {
            ☃ = ☃xxxxxx && ☃xx <= ☃xxx;
         }

         Stitcher.Slot ☃;
         if (☃) {
            if (☃.func_94197_a() > ☃.func_94199_b()) {
               ☃.func_94194_d();
            }

            if (this.field_94315_d == 0) {
               this.field_94315_d = ☃.func_94199_b();
            }

            ☃ = new Stitcher.Slot(this.field_94318_c, 0, ☃.func_94197_a(), this.field_94315_d);
            this.field_94318_c += ☃.func_94197_a();
         } else {
            ☃ = new Stitcher.Slot(0, this.field_94315_d, this.field_94318_c, ☃.func_94199_b());
            this.field_94315_d += ☃.func_94199_b();
         }

         ☃.func_94182_a(☃);
         this.field_94317_b.add(☃);
         return true;
      }
   }

   public static class Holder implements Comparable<Stitcher.Holder> {
      private final TextureAtlasSprite field_98151_a;
      private final int field_94204_c;
      private final int field_94201_d;
      private final int field_147968_d;
      private boolean field_94202_e;
      private float field_94205_a = 1.0F;

      public Holder(TextureAtlasSprite var1, int var2) {
         this.field_98151_a = ☃;
         this.field_94204_c = ☃.func_94211_a();
         this.field_94201_d = ☃.func_94216_b();
         this.field_147968_d = ☃;
         this.field_94202_e = Stitcher.func_147969_b(this.field_94201_d, ☃) > Stitcher.func_147969_b(this.field_94204_c, ☃);
      }

      public TextureAtlasSprite func_98150_a() {
         return this.field_98151_a;
      }

      public int func_94197_a() {
         int ☃ = this.field_94202_e ? this.field_94201_d : this.field_94204_c;
         return Stitcher.func_147969_b((int)((float)☃ * this.field_94205_a), this.field_147968_d);
      }

      public int func_94199_b() {
         int ☃ = this.field_94202_e ? this.field_94204_c : this.field_94201_d;
         return Stitcher.func_147969_b((int)((float)☃ * this.field_94205_a), this.field_147968_d);
      }

      public void func_94194_d() {
         this.field_94202_e = !this.field_94202_e;
      }

      public boolean func_94195_e() {
         return this.field_94202_e;
      }

      public void func_94196_a(int var1) {
         if (this.field_94204_c > ☃ && this.field_94201_d > ☃) {
            this.field_94205_a = (float)☃ / (float)Math.min(this.field_94204_c, this.field_94201_d);
         }
      }

      public String toString() {
         return "Holder{width=" + this.field_94204_c + ", height=" + this.field_94201_d + '}';
      }

      public int compareTo(Stitcher.Holder var1) {
         int ☃;
         if (this.func_94199_b() == ☃.func_94199_b()) {
            if (this.func_94197_a() == ☃.func_94197_a()) {
               return this.field_98151_a.func_195668_m().toString().compareTo(☃.field_98151_a.func_195668_m().toString());
            }

            ☃ = this.func_94197_a() < ☃.func_94197_a() ? 1 : -1;
         } else {
            ☃ = this.func_94199_b() < ☃.func_94199_b() ? 1 : -1;
         }

         return ☃;
      }
   }

   public static class Slot {
      private final int field_94192_a;
      private final int field_94190_b;
      private final int field_94191_c;
      private final int field_94188_d;
      private List<Stitcher.Slot> field_94189_e;
      private Stitcher.Holder field_94187_f;

      public Slot(int var1, int var2, int var3, int var4) {
         this.field_94192_a = ☃;
         this.field_94190_b = ☃;
         this.field_94191_c = ☃;
         this.field_94188_d = ☃;
      }

      public Stitcher.Holder func_94183_a() {
         return this.field_94187_f;
      }

      public int func_94186_b() {
         return this.field_94192_a;
      }

      public int func_94185_c() {
         return this.field_94190_b;
      }

      public boolean func_94182_a(Stitcher.Holder var1) {
         if (this.field_94187_f != null) {
            return false;
         } else {
            int ☃ = ☃.func_94197_a();
            int ☃x = ☃.func_94199_b();
            if (☃ <= this.field_94191_c && ☃x <= this.field_94188_d) {
               if (☃ == this.field_94191_c && ☃x == this.field_94188_d) {
                  this.field_94187_f = ☃;
                  return true;
               } else {
                  if (this.field_94189_e == null) {
                     this.field_94189_e = Lists.<Stitcher.Slot>newArrayListWithCapacity(1);
                     this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b, ☃, ☃x));
                     int ☃xx = this.field_94191_c - ☃;
                     int ☃xxx = this.field_94188_d - ☃x;
                     if (☃xxx > 0 && ☃xx > 0) {
                        int ☃xxxx = Math.max(this.field_94188_d, ☃xx);
                        int ☃xxxxx = Math.max(this.field_94191_c, ☃xxx);
                        if (☃xxxx >= ☃xxxxx) {
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b + ☃x, ☃, ☃xxx));
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a + ☃, this.field_94190_b, ☃xx, this.field_94188_d));
                        } else {
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a + ☃, this.field_94190_b, ☃xx, ☃x));
                           this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b + ☃x, this.field_94191_c, ☃xxx));
                        }
                     } else if (☃xx == 0) {
                        this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a, this.field_94190_b + ☃x, ☃, ☃xxx));
                     } else if (☃xxx == 0) {
                        this.field_94189_e.add(new Stitcher.Slot(this.field_94192_a + ☃, this.field_94190_b, ☃xx, ☃x));
                     }
                  }

                  for(Stitcher.Slot ☃xx : this.field_94189_e) {
                     if (☃xx.func_94182_a(☃)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void func_94184_a(List<Stitcher.Slot> var1) {
         if (this.field_94187_f != null) {
            ☃.add(this);
         } else if (this.field_94189_e != null) {
            for(Stitcher.Slot ☃ : this.field_94189_e) {
               ☃.func_94184_a(☃);
            }
         }
      }

      public String toString() {
         return "Slot{originX="
            + this.field_94192_a
            + ", originY="
            + this.field_94190_b
            + ", width="
            + this.field_94191_c
            + ", height="
            + this.field_94188_d
            + ", texture="
            + this.field_94187_f
            + ", subSlots="
            + this.field_94189_e
            + '}';
      }
   }
}
