package net.minecraft.stats;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBook {
   protected final Set<ResourceLocation> known = Sets.<ResourceLocation>newHashSet();
   protected final Set<ResourceLocation> highlight = Sets.<ResourceLocation>newHashSet();
   private final RecipeBookSettings bookSettings = new RecipeBookSettings();

   public void copyOverData(RecipeBook var1) {
      this.known.clear();
      this.highlight.clear();
      this.bookSettings.replaceFrom(â˜ƒ.bookSettings);
      this.known.addAll(â˜ƒ.known);
      this.highlight.addAll(â˜ƒ.highlight);
   }

   public void add(Recipe<?> var1) {
      if (!â˜ƒ.isSpecial()) {
         this.add(â˜ƒ.getId());
      }
   }

   protected void add(ResourceLocation var1) {
      this.known.add(â˜ƒ);
   }

   public boolean contains(@Nullable Recipe<?> var1) {
      return â˜ƒ == null ? false : this.known.contains(â˜ƒ.getId());
   }

   public boolean contains(ResourceLocation var1) {
      return this.known.contains(â˜ƒ);
   }

   public void remove(Recipe<?> var1) {
      this.remove(â˜ƒ.getId());
   }

   protected void remove(ResourceLocation var1) {
      this.known.remove(â˜ƒ);
      this.highlight.remove(â˜ƒ);
   }

   public boolean willHighlight(Recipe<?> var1) {
      return this.highlight.contains(â˜ƒ.getId());
   }

   public void removeHighlight(Recipe<?> var1) {
      this.highlight.remove(â˜ƒ.getId());
   }

   public void addHighlight(Recipe<?> var1) {
      this.addHighlight(â˜ƒ.getId());
   }

   protected void addHighlight(ResourceLocation var1) {
      this.highlight.add(â˜ƒ);
   }

   public boolean isOpen(RecipeBookType var1) {
      return this.bookSettings.isOpen(â˜ƒ);
   }

   public void setOpen(RecipeBookType var1, boolean var2) {
      this.bookSettings.setOpen(â˜ƒ, â˜ƒ);
   }

   public boolean isFiltering(RecipeBookMenu<?> var1) {
      return this.isFiltering(â˜ƒ.getRecipeBookType());
   }

   public boolean isFiltering(RecipeBookType var1) {
      return this.bookSettings.isFiltering(â˜ƒ);
   }

   public void setFiltering(RecipeBookType var1, boolean var2) {
      this.bookSettings.setFiltering(â˜ƒ, â˜ƒ);
   }

   public void setBookSettings(RecipeBookSettings var1) {
      this.bookSettings.replaceFrom(â˜ƒ);
   }

   public RecipeBookSettings getBookSettings() {
      return this.bookSettings.copy();
   }

   public void setBookSetting(RecipeBookType var1, boolean var2, boolean var3) {
      this.bookSettings.setOpen(â˜ƒ, â˜ƒ);
      this.bookSettings.setFiltering(â˜ƒ, â˜ƒ);
   }
}
