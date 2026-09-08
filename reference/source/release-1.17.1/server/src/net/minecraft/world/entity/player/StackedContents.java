package net.minecraft.world.entity.player;

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
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class StackedContents {
   private static final int EMPTY = 0;
   public final Int2IntMap contents = new Int2IntOpenHashMap();

   public void accountSimpleStack(ItemStack var1) {
      if (!â˜ƒ.isDamaged() && !â˜ƒ.isEnchanted() && !â˜ƒ.hasCustomHoverName()) {
         this.accountStack(â˜ƒ);
      }
   }

   public void accountStack(ItemStack var1) {
      this.accountStack(â˜ƒ, 64);
   }

   public void accountStack(ItemStack var1, int var2) {
      if (!â˜ƒ.isEmpty()) {
         int â˜ƒ = getStackingIndex(â˜ƒ);
         int â˜ƒx = Math.min(â˜ƒ, â˜ƒ.getCount());
         this.put(â˜ƒ, â˜ƒx);
      }
   }

   public static int getStackingIndex(ItemStack var0) {
      return Registry.ITEM.getId(â˜ƒ.getItem());
   }

   boolean has(int var1) {
      return this.contents.get(â˜ƒ) > 0;
   }

   int take(int var1, int var2) {
      int â˜ƒ = this.contents.get(â˜ƒ);
      if (â˜ƒ >= â˜ƒ) {
         this.contents.put(â˜ƒ, â˜ƒ - â˜ƒ);
         return â˜ƒ;
      } else {
         return 0;
      }
   }

   void put(int var1, int var2) {
      this.contents.put(â˜ƒ, this.contents.get(â˜ƒ) + â˜ƒ);
   }

   public boolean canCraft(Recipe<?> var1, @Nullable IntList var2) {
      return this.canCraft(â˜ƒ, â˜ƒ, 1);
   }

   public boolean canCraft(Recipe<?> var1, @Nullable IntList var2, int var3) {
      return new StackedContents.RecipePicker(â˜ƒ).tryPick(â˜ƒ, â˜ƒ);
   }

   public int getBiggestCraftableStack(Recipe<?> var1, @Nullable IntList var2) {
      return this.getBiggestCraftableStack(â˜ƒ, Integer.MAX_VALUE, â˜ƒ);
   }

   public int getBiggestCraftableStack(Recipe<?> var1, int var2, @Nullable IntList var3) {
      return new StackedContents.RecipePicker(â˜ƒ).tryPickAll(â˜ƒ, â˜ƒ);
   }

   public static ItemStack fromStackingIndex(int var0) {
      return â˜ƒ == 0 ? ItemStack.EMPTY : new ItemStack(Item.byId(â˜ƒ));
   }

   public void clear() {
      this.contents.clear();
   }

   class RecipePicker {
      private final Recipe<?> recipe;
      private final List<Ingredient> ingredients = Lists.<Ingredient>newArrayList();
      private final int ingredientCount;
      private final int[] items;
      private final int itemCount;
      private final BitSet data;
      private final IntList path = new IntArrayList();

      public RecipePicker(Recipe<?> var2) {
         this.recipe = â˜ƒ;
         this.ingredients.addAll(â˜ƒ.getIngredients());
         this.ingredients.removeIf(Ingredient::isEmpty);
         this.ingredientCount = this.ingredients.size();
         this.items = this.getUniqueAvailableIngredientItems();
         this.itemCount = this.items.length;
         this.data = new BitSet(this.ingredientCount + this.itemCount + this.ingredientCount + this.ingredientCount * this.itemCount);

         for(int â˜ƒ = 0; â˜ƒ < this.ingredients.size(); ++â˜ƒ) {
            IntList â˜ƒx = ((Ingredient)this.ingredients.get(â˜ƒ)).getStackingIds();

            for(int â˜ƒxx = 0; â˜ƒxx < this.itemCount; ++â˜ƒxx) {
               if (â˜ƒx.contains(this.items[â˜ƒxx])) {
                  this.data.set(this.getIndex(true, â˜ƒxx, â˜ƒ));
               }
            }
         }
      }

      public boolean tryPick(int var1, @Nullable IntList var2) {
         if (â˜ƒ <= 0) {
            return true;
         } else {
            int â˜ƒ;
            for(â˜ƒ = 0; this.dfs(â˜ƒ); ++â˜ƒ) {
               StackedContents.this.take(this.items[this.path.getInt(0)], â˜ƒ);
               int â˜ƒ = this.path.size() - 1;
               this.setSatisfied(this.path.getInt(â˜ƒ));

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
                  this.toggleResidual((â˜ƒx & 1) == 0, this.path.get(â˜ƒx), this.path.get(â˜ƒx + 1));
               }

               this.path.clear();
               this.data.clear(0, this.ingredientCount + this.itemCount);
            }

            boolean â˜ƒ = â˜ƒ == this.ingredientCount;
            boolean â˜ƒx = â˜ƒ && â˜ƒ != null;
            if (â˜ƒx) {
               â˜ƒ.clear();
            }

            this.data.clear(0, this.ingredientCount + this.itemCount + this.ingredientCount);
            int â˜ƒ = 0;
            List<Ingredient> â˜ƒx = this.recipe.getIngredients();

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
               if (â˜ƒx && ((Ingredient)â˜ƒx.get(â˜ƒxx)).isEmpty()) {
                  â˜ƒ.add(0);
               } else {
                  for(int â˜ƒxxx = 0; â˜ƒxxx < this.itemCount; ++â˜ƒxxx) {
                     if (this.hasResidual(false, â˜ƒ, â˜ƒxxx)) {
                        this.toggleResidual(true, â˜ƒxxx, â˜ƒ);
                        StackedContents.this.put(this.items[â˜ƒxxx], â˜ƒ);
                        if (â˜ƒx) {
                           â˜ƒ.add(this.items[â˜ƒxxx]);
                        }
                     }
                  }

                  ++â˜ƒ;
               }
            }

            return â˜ƒ;
         }
      }

      private int[] getUniqueAvailableIngredientItems() {
         IntCollection â˜ƒ = new IntAVLTreeSet();

         for(Ingredient â˜ƒx : this.ingredients) {
            â˜ƒ.addAll(â˜ƒx.getStackingIds());
         }

         IntIterator â˜ƒx = â˜ƒ.iterator();

         while(â˜ƒx.hasNext()) {
            if (!StackedContents.this.has(â˜ƒx.nextInt())) {
               â˜ƒx.remove();
            }
         }

         return â˜ƒ.toIntArray();
      }

      private boolean dfs(int var1) {
         int â˜ƒ = this.itemCount;

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            if (StackedContents.this.contents.get(this.items[â˜ƒx]) >= â˜ƒ) {
               this.visit(false, â˜ƒx);

               while(!this.path.isEmpty()) {
                  int â˜ƒxx = this.path.size();
                  boolean â˜ƒxxx = (â˜ƒxx & 1) == 1;
                  int â˜ƒxxxx = this.path.getInt(â˜ƒxx - 1);
                  if (!â˜ƒxxx && !this.isSatisfied(â˜ƒxxxx)) {
                     break;
                  }

                  int â˜ƒxx = â˜ƒxxx ? this.ingredientCount : â˜ƒ;
                  int â˜ƒxxx = 0;

                  while(true) {
                     if (â˜ƒxxx < â˜ƒxx) {
                        if (this.hasVisited(â˜ƒxxx, â˜ƒxxx) || !this.hasConnection(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxx) || !this.hasResidual(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxx)) {
                           ++â˜ƒxxx;
                           continue;
                        }

                        this.visit(â˜ƒxxx, â˜ƒxxx);
                     }

                     â˜ƒxxx = this.path.size();
                     if (â˜ƒxxx == â˜ƒxx) {
                        this.path.removeInt(â˜ƒxxx - 1);
                     }
                     break;
                  }
               }

               if (!this.path.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean isSatisfied(int var1) {
         return this.data.get(this.getSatisfiedIndex(â˜ƒ));
      }

      private void setSatisfied(int var1) {
         this.data.set(this.getSatisfiedIndex(â˜ƒ));
      }

      private int getSatisfiedIndex(int var1) {
         return this.ingredientCount + this.itemCount + â˜ƒ;
      }

      private boolean hasConnection(boolean var1, int var2, int var3) {
         return this.data.get(this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
      }

      private boolean hasResidual(boolean var1, int var2, int var3) {
         return â˜ƒ != this.data.get(1 + this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
      }

      private void toggleResidual(boolean var1, int var2, int var3) {
         this.data.flip(1 + this.getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
      }

      private int getIndex(boolean var1, int var2, int var3) {
         int â˜ƒ = â˜ƒ ? â˜ƒ * this.ingredientCount + â˜ƒ : â˜ƒ * this.ingredientCount + â˜ƒ;
         return this.ingredientCount + this.itemCount + this.ingredientCount + 2 * â˜ƒ;
      }

      private void visit(boolean var1, int var2) {
         this.data.set(this.getVisitedIndex(â˜ƒ, â˜ƒ));
         this.path.add(â˜ƒ);
      }

      private boolean hasVisited(boolean var1, int var2) {
         return this.data.get(this.getVisitedIndex(â˜ƒ, â˜ƒ));
      }

      private int getVisitedIndex(boolean var1, int var2) {
         return (â˜ƒ ? 0 : this.ingredientCount) + â˜ƒ;
      }

      public int tryPickAll(int var1, @Nullable IntList var2) {
         int â˜ƒ = 0;
         int â˜ƒx = Math.min(â˜ƒ, this.getMinIngredientCount()) + 1;

         while(true) {
            int â˜ƒxx = (â˜ƒ + â˜ƒx) / 2;
            if (this.tryPick(â˜ƒxx, null)) {
               if (â˜ƒx - â˜ƒ <= 1) {
                  if (â˜ƒxx > 0) {
                     this.tryPick(â˜ƒxx, â˜ƒ);
                  }

                  return â˜ƒxx;
               }

               â˜ƒ = â˜ƒxx;
            } else {
               â˜ƒx = â˜ƒxx;
            }
         }
      }

      private int getMinIngredientCount() {
         int â˜ƒ = Integer.MAX_VALUE;

         for(Ingredient â˜ƒx : this.ingredients) {
            int â˜ƒxx = 0;

            for(int â˜ƒxxx : â˜ƒx.getStackingIds()) {
               â˜ƒxx = Math.max(â˜ƒxx, StackedContents.this.contents.get(â˜ƒxxx));
            }

            if (â˜ƒ > 0) {
               â˜ƒ = Math.min(â˜ƒ, â˜ƒxx);
            }
         }

         return â˜ƒ;
      }
   }
}
