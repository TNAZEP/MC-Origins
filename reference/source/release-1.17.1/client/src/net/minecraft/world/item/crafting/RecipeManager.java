package net.minecraft.world.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeManager extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final Logger LOGGER = LogManager.getLogger();
   private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes = ImmutableMap.of();
   private boolean hasErrors;

   public RecipeManager() {
      super(GSON, "recipes");
   }

   protected void apply(Map<ResourceLocation, JsonElement> var1, ResourceManager var2, ProfilerFiller var3) {
      this.hasErrors = false;
      Map<RecipeType<?>, Builder<ResourceLocation, Recipe<?>>> â˜ƒ = Maps.<RecipeType<?>, Builder<ResourceLocation, Recipe<?>>>newHashMap();

      for(Entry<ResourceLocation, JsonElement> â˜ƒx : â˜ƒ.entrySet()) {
         ResourceLocation â˜ƒxx = (ResourceLocation)â˜ƒx.getKey();

         try {
            Recipe<?> â˜ƒxxx = fromJson(â˜ƒxx, GsonHelper.convertToJsonObject((JsonElement)â˜ƒx.getValue(), "top element"));
            ((Builder)â˜ƒ.computeIfAbsent(â˜ƒxxx.getType(), var0 -> ImmutableMap.builder())).put(â˜ƒxx, â˜ƒxxx);
         } catch (IllegalArgumentException | JsonParseException var9) {
            LOGGER.error("Parsing error loading recipe {}", â˜ƒxx, var9);
         }
      }

      this.recipes = (Map)â˜ƒ.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ((Builder)var0.getValue()).build()));
      LOGGER.info("Loaded {} recipes", â˜ƒ.size());
   }

   public boolean hadErrorsLoading() {
      return this.hasErrors;
   }

   public <C extends Container, T extends Recipe<C>> Optional<T> getRecipeFor(RecipeType<T> var1, C var2, Level var3) {
      return this.byType(â˜ƒ).values().stream().flatMap(var3x -> Util.toStream(â˜ƒ.tryMatch(var3x, â˜ƒ, â˜ƒ))).findFirst();
   }

   public <C extends Container, T extends Recipe<C>> List<T> getAllRecipesFor(RecipeType<T> var1) {
      return (List<T>)this.byType(â˜ƒ).values().stream().map(var0 -> var0).collect(Collectors.toList());
   }

   public <C extends Container, T extends Recipe<C>> List<T> getRecipesFor(RecipeType<T> var1, C var2, Level var3) {
      return (List<T>)this.byType(â˜ƒ)
         .values()
         .stream()
         .flatMap(var3x -> Util.toStream(â˜ƒ.tryMatch(var3x, â˜ƒ, â˜ƒ)))
         .sorted(Comparator.comparing(var0 -> var0.getResultItem().getDescriptionId()))
         .collect(Collectors.toList());
   }

   private <C extends Container, T extends Recipe<C>> Map<ResourceLocation, Recipe<C>> byType(RecipeType<T> var1) {
      return (Map<ResourceLocation, Recipe<C>>)this.recipes.getOrDefault(â˜ƒ, Collections.emptyMap());
   }

   public <C extends Container, T extends Recipe<C>> NonNullList<ItemStack> getRemainingItemsFor(RecipeType<T> var1, C var2, Level var3) {
      Optional<T> â˜ƒ = this.getRecipeFor(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPresent()) {
         return ((Recipe)â˜ƒ.get()).getRemainingItems(â˜ƒ);
      } else {
         NonNullList<ItemStack> â˜ƒ = NonNullList.withSize(â˜ƒ.getContainerSize(), ItemStack.EMPTY);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            â˜ƒ.set(â˜ƒx, â˜ƒ.getItem(â˜ƒx));
         }

         return â˜ƒ;
      }
   }

   public Optional<? extends Recipe<?>> byKey(ResourceLocation var1) {
      return this.recipes.values().stream().map(var1x -> (Recipe)var1x.get(â˜ƒ)).filter(Objects::nonNull).findFirst();
   }

   public Collection<Recipe<?>> getRecipes() {
      return (Collection<Recipe<?>>)this.recipes.values().stream().flatMap(var0 -> var0.values().stream()).collect(Collectors.toSet());
   }

   public Stream<ResourceLocation> getRecipeIds() {
      return this.recipes.values().stream().flatMap(var0 -> var0.keySet().stream());
   }

   public static Recipe<?> fromJson(ResourceLocation var0, JsonObject var1) {
      String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "type");
      return ((RecipeSerializer)Registry.RECIPE_SERIALIZER
            .getOptional(new ResourceLocation(â˜ƒ))
            .orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + â˜ƒ + "'")))
         .fromJson(â˜ƒ, â˜ƒ);
   }

   public void replaceRecipes(Iterable<Recipe<?>> var1) {
      this.hasErrors = false;
      Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> â˜ƒ = Maps.newHashMap();
      â˜ƒ.forEach(var1x -> {
         Map<ResourceLocation, Recipe<?>> â˜ƒ = (Map)â˜ƒ.computeIfAbsent(var1x.getType(), var0x -> Maps.newHashMap());
         Recipe<?> â˜ƒx = (Recipe)â˜ƒ.put(var1x.getId(), var1x);
         if (â˜ƒx != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + var1x.getId());
         }
      });
      this.recipes = ImmutableMap.copyOf(â˜ƒ);
   }
}
