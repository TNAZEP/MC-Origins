package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class SuggestionProviders {
   private static final Map<ResourceLocation, SuggestionProvider<SharedSuggestionProvider>> PROVIDERS_BY_NAME = Maps.<ResourceLocation, SuggestionProvider<SharedSuggestionProvider>>newHashMap(
      
   );
   private static final ResourceLocation DEFAULT_NAME = new ResourceLocation("ask_server");
   public static final SuggestionProvider<SharedSuggestionProvider> ASK_SERVER = register(
      DEFAULT_NAME, (var0, var1) -> var0.getSource().customSuggestion(var0, var1)
   );
   public static final SuggestionProvider<CommandSourceStack> ALL_RECIPES = register(
      new ResourceLocation("all_recipes"), (var0, var1) -> SharedSuggestionProvider.suggestResource(var0.getSource().getRecipeNames(), var1)
   );
   public static final SuggestionProvider<CommandSourceStack> AVAILABLE_SOUNDS = register(
      new ResourceLocation("available_sounds"), (var0, var1) -> SharedSuggestionProvider.suggestResource(var0.getSource().getAvailableSoundEvents(), var1)
   );
   public static final SuggestionProvider<CommandSourceStack> AVAILABLE_BIOMES = register(
      new ResourceLocation("available_biomes"),
      (var0, var1) -> SharedSuggestionProvider.suggestResource(var0.getSource().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).keySet(), var1)
   );
   public static final SuggestionProvider<CommandSourceStack> SUMMONABLE_ENTITIES = register(
      new ResourceLocation("summonable_entities"),
      (var0, var1) -> SharedSuggestionProvider.suggestResource(
            Registry.ENTITY_TYPE.stream().filter(EntityType::canSummon),
            var1,
            EntityType::getKey,
            var0x -> new TranslatableComponent(Util.makeDescriptionId("entity", EntityType.getKey(var0x)))
         )
   );

   public static <S extends SharedSuggestionProvider> SuggestionProvider<S> register(ResourceLocation var0, SuggestionProvider<SharedSuggestionProvider> var1) {
      if (PROVIDERS_BY_NAME.containsKey(â˜ƒ)) {
         throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + â˜ƒ);
      } else {
         PROVIDERS_BY_NAME.put(â˜ƒ, â˜ƒ);
         return new SuggestionProviders.Wrapper(â˜ƒ, â˜ƒ);
      }
   }

   public static SuggestionProvider<SharedSuggestionProvider> getProvider(ResourceLocation var0) {
      return (SuggestionProvider<SharedSuggestionProvider>)PROVIDERS_BY_NAME.getOrDefault(â˜ƒ, ASK_SERVER);
   }

   public static ResourceLocation getName(SuggestionProvider<SharedSuggestionProvider> var0) {
      return â˜ƒ instanceof SuggestionProviders.Wrapper ? ((SuggestionProviders.Wrapper)â˜ƒ).name : DEFAULT_NAME;
   }

   public static SuggestionProvider<SharedSuggestionProvider> safelySwap(SuggestionProvider<SharedSuggestionProvider> var0) {
      return â˜ƒ instanceof SuggestionProviders.Wrapper ? â˜ƒ : ASK_SERVER;
   }

   protected static class Wrapper implements SuggestionProvider<SharedSuggestionProvider> {
      private final SuggestionProvider<SharedSuggestionProvider> delegate;
      final ResourceLocation name;

      public Wrapper(ResourceLocation var1, SuggestionProvider<SharedSuggestionProvider> var2) {
         this.delegate = â˜ƒ;
         this.name = â˜ƒ;
      }

      @Override
      public CompletableFuture<Suggestions> getSuggestions(CommandContext<SharedSuggestionProvider> var1, SuggestionsBuilder var2) throws CommandSyntaxException {
         return this.delegate.getSuggestions(â˜ƒ, â˜ƒ);
      }
   }
}
