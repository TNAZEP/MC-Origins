package net.minecraft.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.Registry;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientRecipeBook extends RecipeBook {
   private static final Logger LOGGER = LogManager.getLogger();
   private Map<RecipeBookCategories, List<RecipeCollection>> collectionsByTab = ImmutableMap.of();
   private List<RecipeCollection> allCollections = ImmutableList.of();

   public void setupCollections(Iterable<Recipe<?>> var1) {
      Map<RecipeBookCategories, List<List<Recipe<?>>>> â˜ƒ = categorizeAndGroupRecipes(â˜ƒ);
      Map<RecipeBookCategories, List<RecipeCollection>> â˜ƒx = Maps.newHashMap();
      Builder<RecipeCollection> â˜ƒxx = ImmutableList.builder();
      â˜ƒ.forEach((var2x, var3x) -> â˜ƒ.put(var2x, (List)var3x.stream().map(RecipeCollection::new).peek(â˜ƒ::add).collect(ImmutableList.toImmutableList())));
      RecipeBookCategories.AGGREGATE_CATEGORIES
         .forEach(
            (var1x, var2x) -> â˜ƒ.put(
                  var1x,
                  (List)var2x.stream()
                     .flatMap(var1xx -> ((List)â˜ƒ.getOrDefault(var1xx, ImmutableList.of())).stream())
                     .collect(ImmutableList.toImmutableList())
               )
         );
      this.collectionsByTab = ImmutableMap.copyOf(â˜ƒx);
      this.allCollections = â˜ƒxx.build();
   }

   private static Map<RecipeBookCategories, List<List<Recipe<?>>>> categorizeAndGroupRecipes(Iterable<Recipe<?>> var0) {
      Map<RecipeBookCategories, List<List<Recipe<?>>>> â˜ƒ = Maps.newHashMap();
      Table<RecipeBookCategories, String, List<Recipe<?>>> â˜ƒx = HashBasedTable.create();

      for(Recipe<?> â˜ƒxx : â˜ƒ) {
         if (!â˜ƒxx.isSpecial() && !â˜ƒxx.isIncomplete()) {
            RecipeBookCategories â˜ƒxxx = getCategory(â˜ƒxx);
            String â˜ƒxxxx = â˜ƒxx.getGroup();
            if (â˜ƒxxxx.isEmpty()) {
               ((List)â˜ƒ.computeIfAbsent(â˜ƒxxx, var0x -> Lists.newArrayList())).add(ImmutableList.<Recipe<?>>of(â˜ƒxx));
            } else {
               List<Recipe<?>> â˜ƒxxx = (List)â˜ƒx.get(â˜ƒxxx, â˜ƒxxxx);
               if (â˜ƒxxx == null) {
                  â˜ƒxxx = Lists.<Recipe<?>>newArrayList();
                  â˜ƒx.put(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxx);
                  ((List)â˜ƒ.computeIfAbsent(â˜ƒxxx, var0x -> Lists.newArrayList())).add(â˜ƒxxx);
               }

               â˜ƒxxx.add(â˜ƒxx);
            }
         }
      }

      return â˜ƒ;
   }

   private static RecipeBookCategories getCategory(Recipe<?> var0) {
      RecipeType<?> â˜ƒ = â˜ƒ.getType();
      if (â˜ƒ == RecipeType.CRAFTING) {
         ItemStack â˜ƒx = â˜ƒ.getResultItem();
         CreativeModeTab â˜ƒxx = â˜ƒx.getItem().getItemCategory();
         if (â˜ƒxx == CreativeModeTab.TAB_BUILDING_BLOCKS) {
            return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
         } else if (â˜ƒxx == CreativeModeTab.TAB_TOOLS || â˜ƒxx == CreativeModeTab.TAB_COMBAT) {
            return RecipeBookCategories.CRAFTING_EQUIPMENT;
         } else {
            return â˜ƒxx == CreativeModeTab.TAB_REDSTONE ? RecipeBookCategories.CRAFTING_REDSTONE : RecipeBookCategories.CRAFTING_MISC;
         }
      } else if (â˜ƒ == RecipeType.SMELTING) {
         if (â˜ƒ.getResultItem().getItem().isEdible()) {
            return RecipeBookCategories.FURNACE_FOOD;
         } else {
            return â˜ƒ.getResultItem().getItem() instanceof BlockItem ? RecipeBookCategories.FURNACE_BLOCKS : RecipeBookCategories.FURNACE_MISC;
         }
      } else if (â˜ƒ == RecipeType.BLASTING) {
         return â˜ƒ.getResultItem().getItem() instanceof BlockItem ? RecipeBookCategories.BLAST_FURNACE_BLOCKS : RecipeBookCategories.BLAST_FURNACE_MISC;
      } else if (â˜ƒ == RecipeType.SMOKING) {
         return RecipeBookCategories.SMOKER_FOOD;
      } else if (â˜ƒ == RecipeType.STONECUTTING) {
         return RecipeBookCategories.STONECUTTER;
      } else if (â˜ƒ == RecipeType.CAMPFIRE_COOKING) {
         return RecipeBookCategories.CAMPFIRE;
      } else if (â˜ƒ == RecipeType.SMITHING) {
         return RecipeBookCategories.SMITHING;
      } else {
         LOGGER.warn("Unknown recipe category: {}/{}", () -> Registry.RECIPE_TYPE.getKey(â˜ƒ.getType()), â˜ƒ::getId);
         return RecipeBookCategories.UNKNOWN;
      }
   }

   public List<RecipeCollection> getCollections() {
      return this.allCollections;
   }

   public List<RecipeCollection> getCollection(RecipeBookCategories var1) {
      return (List<RecipeCollection>)this.collectionsByTab.getOrDefault(â˜ƒ, Collections.emptyList());
   }
}
