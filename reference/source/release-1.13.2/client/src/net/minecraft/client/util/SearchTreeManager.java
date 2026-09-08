package net.minecraft.client.util;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;

public class SearchTreeManager implements IResourceManagerReloadListener {
   public static final SearchTreeManager.Key<ItemStack> field_194011_a = new SearchTreeManager.Key<>();
   public static final SearchTreeManager.Key<RecipeList> field_194012_b = new SearchTreeManager.Key<>();
   private final Map<SearchTreeManager.Key<?>, SearchTree<?>> field_194013_c = Maps.<SearchTreeManager.Key<?>, SearchTree<?>>newHashMap();

   @Override
   public void func_195410_a(IResourceManager var1) {
      for(SearchTree<?> ☃ : this.field_194013_c.values()) {
         ☃.func_194040_a();
      }
   }

   public <T> void func_194009_a(SearchTreeManager.Key<T> var1, SearchTree<T> var2) {
      this.field_194013_c.put(☃, ☃);
   }

   public <T> ISearchTree<T> func_194010_a(SearchTreeManager.Key<T> var1) {
      return (ISearchTree<T>)this.field_194013_c.get(☃);
   }

   public static class Key<T> {
   }
}
