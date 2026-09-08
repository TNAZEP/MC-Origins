package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.IRegistry;

public class RecipeItemHelper {
   public final Int2IntMap field_194124_a = new Int2IntOpenHashMap();

   public void func_195932_a(ItemStack var1) {
      if (!☃.func_77951_h() && !☃.func_77948_v() && !☃.func_82837_s()) {
         this.func_194112_a(☃);
      }
   }

   public void func_194112_a(ItemStack var1) {
      if (!☃.func_190926_b()) {
         int ☃ = func_194113_b(☃);
         int ☃x = ☃.func_190916_E();
         this.func_194117_b(☃, ☃x);
      }
   }

   public static int func_194113_b(ItemStack var0) {
      return IRegistry.field_212630_s.func_148757_b(☃.func_77973_b());
   }

   public boolean func_194120_a(int var1) {
      return this.field_194124_a.get(☃) > 0;
   }

   public int func_194122_a(int var1, int var2) {
      int ☃ = this.field_194124_a.get(☃);
      if (☃ >= ☃) {
         this.field_194124_a.put(☃, ☃ - ☃);
         return ☃;
      } else {
         return 0;
      }
   }

   private void func_194117_b(int var1, int var2) {
      this.field_194124_a.put(☃, this.field_194124_a.get(☃) + ☃);
   }

   public boolean func_194116_a(IRecipe var1, @Nullable IntList var2) {
      return this.func_194118_a(☃, ☃, 1);
   }

   public boolean func_194118_a(IRecipe var1, @Nullable IntList var2, int var3) {
      return new RecipeItemHelper.RecipePicker(☃).func_194092_a(☃, ☃);
   }

   public int func_194114_b(IRecipe var1, @Nullable IntList var2) {
      return this.func_194121_a(☃, Integer.MAX_VALUE, ☃);
   }

   public int func_194121_a(IRecipe var1, int var2, @Nullable IntList var3) {
      return new RecipeItemHelper.RecipePicker(☃).func_194102_b(☃, ☃);
   }

   public static ItemStack func_194115_b(int var0) {
      return ☃ == 0 ? ItemStack.field_190927_a : new ItemStack(Item.func_150899_d(☃));
   }

   public void func_194119_a() {
      this.field_194124_a.clear();
   }

   class RecipePicker {
      private final IRecipe field_194105_b;
      private final List<Ingredient> field_194106_c = Lists.<Ingredient>newArrayList();
      private final int field_194107_d;
      private final int[] field_194108_e;
      private final int field_194109_f;
      private final BitSet field_194110_g;
      private final IntList field_194111_h = new IntArrayList();

      public RecipePicker(IRecipe var2) {
         this.field_194105_b = ☃;
         this.field_194106_c.addAll(☃.func_192400_c());
         this.field_194106_c.removeIf(Ingredient::func_203189_d);
         this.field_194107_d = this.field_194106_c.size();
         this.field_194108_e = this.func_194097_a();
         this.field_194109_f = this.field_194108_e.length;
         this.field_194110_g = new BitSet(this.field_194107_d + this.field_194109_f + this.field_194107_d + this.field_194107_d * this.field_194109_f);

         for(int ☃ = 0; ☃ < this.field_194106_c.size(); ++☃) {
            IntList ☃x = ((Ingredient)this.field_194106_c.get(☃)).func_194139_b();

            for(int ☃xx = 0; ☃xx < this.field_194109_f; ++☃xx) {
               if (☃x.contains(this.field_194108_e[☃xx])) {
                  this.field_194110_g.set(this.func_194095_d(true, ☃xx, ☃));
               }
            }
         }
      }

      public boolean func_194092_a(int var1, @Nullable IntList var2) {
         if (☃ <= 0) {
            return true;
         } else {
            int ☃;
            for(☃ = 0; this.func_194098_a(☃); ++☃) {
               RecipeItemHelper.this.func_194122_a(this.field_194108_e[this.field_194111_h.getInt(0)], ☃);
               int ☃ = this.field_194111_h.size() - 1;
               this.func_194096_c(this.field_194111_h.getInt(☃));

               for(int ☃x = 0; ☃x < ☃; ++☃x) {
                  this.func_194089_c((☃x & 1) == 0, this.field_194111_h.get(☃x), this.field_194111_h.get(☃x + 1));
               }

               this.field_194111_h.clear();
               this.field_194110_g.clear(0, this.field_194107_d + this.field_194109_f);
            }

            boolean ☃ = ☃ == this.field_194107_d;
            boolean ☃x = ☃ && ☃ != null;
            if (☃x) {
               ☃.clear();
            }

            this.field_194110_g.clear(0, this.field_194107_d + this.field_194109_f + this.field_194107_d);
            int ☃ = 0;
            List<Ingredient> ☃x = this.field_194105_b.func_192400_c();

            for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
               if (☃x && ((Ingredient)☃x.get(☃xx)).func_203189_d()) {
                  ☃.add(0);
               } else {
                  for(int ☃xxx = 0; ☃xxx < this.field_194109_f; ++☃xxx) {
                     if (this.func_194100_b(false, ☃, ☃xxx)) {
                        this.func_194089_c(true, ☃xxx, ☃);
                        RecipeItemHelper.this.func_194117_b(this.field_194108_e[☃xxx], ☃);
                        if (☃x) {
                           ☃.add(this.field_194108_e[☃xxx]);
                        }
                     }
                  }

                  ++☃;
               }
            }

            return ☃;
         }
      }

      private int[] func_194097_a() {
         IntCollection ☃ = new IntAVLTreeSet();

         for(Ingredient ☃x : this.field_194106_c) {
            ☃.addAll(☃x.func_194139_b());
         }

         IntIterator ☃x = ☃.iterator();

         while(☃x.hasNext()) {
            if (!RecipeItemHelper.this.func_194120_a(☃x.nextInt())) {
               ☃x.remove();
            }
         }

         return ☃.toIntArray();
      }

      private boolean func_194098_a(int var1) {
         int ☃ = this.field_194109_f;

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            if (RecipeItemHelper.this.field_194124_a.get(this.field_194108_e[☃x]) >= ☃) {
               this.func_194088_a(false, ☃x);

               while(!this.field_194111_h.isEmpty()) {
                  int ☃xx = this.field_194111_h.size();
                  boolean ☃xxx = (☃xx & 1) == 1;
                  int ☃xxxx = this.field_194111_h.getInt(☃xx - 1);
                  if (!☃xxx && !this.func_194091_b(☃xxxx)) {
                     break;
                  }

                  int ☃xx = ☃xxx ? this.field_194107_d : ☃;
                  int ☃xxx = 0;

                  while(true) {
                     if (☃xxx < ☃xx) {
                        if (this.func_194101_b(☃xxx, ☃xxx) || !this.func_194093_a(☃xxx, ☃xxxx, ☃xxx) || !this.func_194100_b(☃xxx, ☃xxxx, ☃xxx)) {
                           ++☃xxx;
                           continue;
                        }

                        this.func_194088_a(☃xxx, ☃xxx);
                     }

                     ☃xxx = this.field_194111_h.size();
                     if (☃xxx == ☃xx) {
                        this.field_194111_h.removeInt(☃xxx - 1);
                     }
                     break;
                  }
               }

               if (!this.field_194111_h.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean func_194091_b(int var1) {
         return this.field_194110_g.get(this.func_194094_d(☃));
      }

      private void func_194096_c(int var1) {
         this.field_194110_g.set(this.func_194094_d(☃));
      }

      private int func_194094_d(int var1) {
         return this.field_194107_d + this.field_194109_f + ☃;
      }

      private boolean func_194093_a(boolean var1, int var2, int var3) {
         return this.field_194110_g.get(this.func_194095_d(☃, ☃, ☃));
      }

      private boolean func_194100_b(boolean var1, int var2, int var3) {
         return ☃ != this.field_194110_g.get(1 + this.func_194095_d(☃, ☃, ☃));
      }

      private void func_194089_c(boolean var1, int var2, int var3) {
         this.field_194110_g.flip(1 + this.func_194095_d(☃, ☃, ☃));
      }

      private int func_194095_d(boolean var1, int var2, int var3) {
         int ☃ = ☃ ? ☃ * this.field_194107_d + ☃ : ☃ * this.field_194107_d + ☃;
         return this.field_194107_d + this.field_194109_f + this.field_194107_d + 2 * ☃;
      }

      private void func_194088_a(boolean var1, int var2) {
         this.field_194110_g.set(this.func_194099_c(☃, ☃));
         this.field_194111_h.add(☃);
      }

      private boolean func_194101_b(boolean var1, int var2) {
         return this.field_194110_g.get(this.func_194099_c(☃, ☃));
      }

      private int func_194099_c(boolean var1, int var2) {
         return (☃ ? 0 : this.field_194107_d) + ☃;
      }

      public int func_194102_b(int var1, @Nullable IntList var2) {
         int ☃ = 0;
         int ☃x = Math.min(☃, this.func_194090_b()) + 1;

         while(true) {
            int ☃xx = (☃ + ☃x) / 2;
            if (this.func_194092_a(☃xx, null)) {
               if (☃x - ☃ <= 1) {
                  if (☃xx > 0) {
                     this.func_194092_a(☃xx, ☃);
                  }

                  return ☃xx;
               }

               ☃ = ☃xx;
            } else {
               ☃x = ☃xx;
            }
         }
      }

      private int func_194090_b() {
         int ☃ = Integer.MAX_VALUE;

         for(Ingredient ☃x : this.field_194106_c) {
            int ☃xx = 0;

            for(int ☃xxx : ☃x.func_194139_b()) {
               ☃xx = Math.max(☃xx, RecipeItemHelper.this.field_194124_a.get(☃xxx));
            }

            if (☃ > 0) {
               ☃ = Math.min(☃, ☃xx);
            }
         }

         return ☃;
      }
   }
}
