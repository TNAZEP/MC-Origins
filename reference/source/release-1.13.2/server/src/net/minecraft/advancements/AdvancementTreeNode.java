package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class AdvancementTreeNode {
   private final Advancement field_192328_a;
   private final AdvancementTreeNode field_192329_b;
   private final AdvancementTreeNode field_192330_c;
   private final int field_192331_d;
   private final List<AdvancementTreeNode> field_192332_e = Lists.<AdvancementTreeNode>newArrayList();
   private AdvancementTreeNode field_192333_f;
   private AdvancementTreeNode field_192334_g;
   private int field_192335_h;
   private float field_192336_i;
   private float field_192337_j;
   private float field_192338_k;
   private float field_192339_l;

   public AdvancementTreeNode(Advancement var1, @Nullable AdvancementTreeNode var2, @Nullable AdvancementTreeNode var3, int var4, int var5) {
      if (☃.func_192068_c() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.field_192328_a = ☃;
         this.field_192329_b = ☃;
         this.field_192330_c = ☃;
         this.field_192331_d = ☃;
         this.field_192333_f = this;
         this.field_192335_h = ☃;
         this.field_192336_i = -1.0F;
         AdvancementTreeNode ☃ = null;

         for(Advancement ☃x : ☃.func_192069_e()) {
            ☃ = this.func_192322_a(☃x, ☃);
         }
      }
   }

   @Nullable
   private AdvancementTreeNode func_192322_a(Advancement var1, @Nullable AdvancementTreeNode var2) {
      if (☃.func_192068_c() != null) {
         ☃ = new AdvancementTreeNode(☃, this, ☃, this.field_192332_e.size() + 1, this.field_192335_h + 1);
         this.field_192332_e.add(☃);
      } else {
         for(Advancement ☃ : ☃.func_192069_e()) {
            ☃ = this.func_192322_a(☃, ☃);
         }
      }

      return ☃;
   }

   private void func_192320_a() {
      if (this.field_192332_e.isEmpty()) {
         if (this.field_192330_c != null) {
            this.field_192336_i = this.field_192330_c.field_192336_i + 1.0F;
         } else {
            this.field_192336_i = 0.0F;
         }
      } else {
         AdvancementTreeNode ☃ = null;

         for(AdvancementTreeNode ☃x : this.field_192332_e) {
            ☃x.func_192320_a();
            ☃ = ☃x.func_192324_a(☃ == null ? ☃x : ☃);
         }

         this.func_192325_b();
         float ☃x = (
               ((AdvancementTreeNode)this.field_192332_e.get(0)).field_192336_i
                  + ((AdvancementTreeNode)this.field_192332_e.get(this.field_192332_e.size() - 1)).field_192336_i
            )
            / 2.0F;
         if (this.field_192330_c != null) {
            this.field_192336_i = this.field_192330_c.field_192336_i + 1.0F;
            this.field_192337_j = this.field_192336_i - ☃x;
         } else {
            this.field_192336_i = ☃x;
         }
      }
   }

   private float func_192319_a(float var1, int var2, float var3) {
      this.field_192336_i += ☃;
      this.field_192335_h = ☃;
      if (this.field_192336_i < ☃) {
         ☃ = this.field_192336_i;
      }

      for(AdvancementTreeNode ☃ : this.field_192332_e) {
         ☃ = ☃.func_192319_a(☃ + this.field_192337_j, ☃ + 1, ☃);
      }

      return ☃;
   }

   private void func_192318_a(float var1) {
      this.field_192336_i += ☃;

      for(AdvancementTreeNode ☃ : this.field_192332_e) {
         ☃.func_192318_a(☃);
      }
   }

   private void func_192325_b() {
      float ☃ = 0.0F;
      float ☃x = 0.0F;

      for(int ☃xx = this.field_192332_e.size() - 1; ☃xx >= 0; --☃xx) {
         AdvancementTreeNode ☃xxx = (AdvancementTreeNode)this.field_192332_e.get(☃xx);
         ☃xxx.field_192336_i += ☃;
         ☃xxx.field_192337_j += ☃;
         ☃x += ☃xxx.field_192338_k;
         ☃ += ☃xxx.field_192339_l + ☃x;
      }
   }

   @Nullable
   private AdvancementTreeNode func_192321_c() {
      if (this.field_192334_g != null) {
         return this.field_192334_g;
      } else {
         return !this.field_192332_e.isEmpty() ? (AdvancementTreeNode)this.field_192332_e.get(0) : null;
      }
   }

   @Nullable
   private AdvancementTreeNode func_192317_d() {
      if (this.field_192334_g != null) {
         return this.field_192334_g;
      } else {
         return !this.field_192332_e.isEmpty() ? (AdvancementTreeNode)this.field_192332_e.get(this.field_192332_e.size() - 1) : null;
      }
   }

   private AdvancementTreeNode func_192324_a(AdvancementTreeNode var1) {
      if (this.field_192330_c == null) {
         return ☃;
      } else {
         AdvancementTreeNode ☃ = this;
         AdvancementTreeNode ☃x = this;
         AdvancementTreeNode ☃xx = this.field_192330_c;
         AdvancementTreeNode ☃xxx = (AdvancementTreeNode)this.field_192329_b.field_192332_e.get(0);
         float ☃xxxx = this.field_192337_j;
         float ☃xxxxx = this.field_192337_j;
         float ☃xxxxxx = ☃xx.field_192337_j;

         float ☃;
         for(☃ = ☃xxx.field_192337_j; ☃xx.func_192317_d() != null && ☃.func_192321_c() != null; ☃xxxxx += ☃x.field_192337_j) {
            ☃xx = ☃xx.func_192317_d();
            ☃ = ☃.func_192321_c();
            ☃xxx = ☃xxx.func_192321_c();
            ☃x = ☃x.func_192317_d();
            ☃x.field_192333_f = this;
            float ☃xxxxxxx = ☃xx.field_192336_i + ☃xxxxxx - (☃.field_192336_i + ☃xxxx) + 1.0F;
            if (☃xxxxxxx > 0.0F) {
               ☃xx.func_192326_a(this, ☃).func_192316_a(this, ☃xxxxxxx);
               ☃xxxx += ☃xxxxxxx;
               ☃xxxxx += ☃xxxxxxx;
            }

            ☃xxxxxx += ☃xx.field_192337_j;
            ☃xxxx += ☃.field_192337_j;
            ☃ += ☃xxx.field_192337_j;
         }

         if (☃xx.func_192317_d() != null && ☃x.func_192317_d() == null) {
            ☃x.field_192334_g = ☃xx.func_192317_d();
            ☃x.field_192337_j += ☃xxxxxx - ☃xxxxx;
         } else {
            if (☃.func_192321_c() != null && ☃xxx.func_192321_c() == null) {
               ☃xxx.field_192334_g = ☃.func_192321_c();
               ☃xxx.field_192337_j += ☃xxxx - ☃;
            }

            ☃ = this;
         }

         return ☃;
      }
   }

   private void func_192316_a(AdvancementTreeNode var1, float var2) {
      float ☃ = (float)(☃.field_192331_d - this.field_192331_d);
      if (☃ != 0.0F) {
         ☃.field_192338_k -= ☃ / ☃;
         this.field_192338_k += ☃ / ☃;
      }

      ☃.field_192339_l += ☃;
      ☃.field_192336_i += ☃;
      ☃.field_192337_j += ☃;
   }

   private AdvancementTreeNode func_192326_a(AdvancementTreeNode var1, AdvancementTreeNode var2) {
      return this.field_192333_f != null && ☃.field_192329_b.field_192332_e.contains(this.field_192333_f) ? this.field_192333_f : ☃;
   }

   private void func_192327_e() {
      if (this.field_192328_a.func_192068_c() != null) {
         this.field_192328_a.func_192068_c().func_192292_a((float)this.field_192335_h, this.field_192336_i);
      }

      if (!this.field_192332_e.isEmpty()) {
         for(AdvancementTreeNode ☃ : this.field_192332_e) {
            ☃.func_192327_e();
         }
      }
   }

   public static void func_192323_a(Advancement var0) {
      if (☃.func_192068_c() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         AdvancementTreeNode ☃ = new AdvancementTreeNode(☃, null, null, 1, 0);
         ☃.func_192320_a();
         float ☃x = ☃.func_192319_a(0.0F, 0, ☃.field_192336_i);
         if (☃x < 0.0F) {
            ☃.func_192318_a(-☃x);
         }

         ☃.func_192327_e();
      }
   }
}
