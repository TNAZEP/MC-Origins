package net.minecraft.commands;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public interface SharedSuggestionProvider {
   Collection<String> getOnlinePlayerNames();

   default Collection<String> getSelectedEntities() {
      return Collections.emptyList();
   }

   Collection<String> getAllTeams();

   Collection<ResourceLocation> getAvailableSoundEvents();

   Stream<ResourceLocation> getRecipeNames();

   CompletableFuture<Suggestions> customSuggestion(CommandContext<SharedSuggestionProvider> var1, SuggestionsBuilder var2);

   default Collection<SharedSuggestionProvider.TextCoordinates> getRelevantCoordinates() {
      return Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_GLOBAL);
   }

   default Collection<SharedSuggestionProvider.TextCoordinates> getAbsoluteCoordinates() {
      return Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_GLOBAL);
   }

   Set<ResourceKey<Level>> levels();

   RegistryAccess registryAccess();

   boolean hasPermission(int var1);

   static <T> void filterResources(Iterable<T> var0, String var1, Function<T, ResourceLocation> var2, Consumer<T> var3) {
      boolean â˜ƒ = â˜ƒ.indexOf(58) > -1;

      for(T â˜ƒx : â˜ƒ) {
         ResourceLocation â˜ƒxx = (ResourceLocation)â˜ƒ.apply(â˜ƒx);
         if (â˜ƒ) {
            String â˜ƒxxx = â˜ƒxx.toString();
            if (matchesSubStr(â˜ƒ, â˜ƒxxx)) {
               â˜ƒ.accept(â˜ƒx);
            }
         } else if (matchesSubStr(â˜ƒ, â˜ƒxx.getNamespace()) || â˜ƒxx.getNamespace().equals("minecraft") && matchesSubStr(â˜ƒ, â˜ƒxx.getPath())) {
            â˜ƒ.accept(â˜ƒx);
         }
      }
   }

   static <T> void filterResources(Iterable<T> var0, String var1, String var2, Function<T, ResourceLocation> var3, Consumer<T> var4) {
      if (â˜ƒ.isEmpty()) {
         â˜ƒ.forEach(â˜ƒ);
      } else {
         String â˜ƒ = Strings.commonPrefix(â˜ƒ, â˜ƒ);
         if (!â˜ƒ.isEmpty()) {
            String â˜ƒx = â˜ƒ.substring(â˜ƒ.length());
            filterResources(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
         }
      }
   }

   static CompletableFuture<Suggestions> suggestResource(Iterable<ResourceLocation> var0, SuggestionsBuilder var1, String var2) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);
      filterResources(â˜ƒ, â˜ƒ, â˜ƒ, var0x -> var0x, var2x -> â˜ƒ.suggest(â˜ƒ + var2x));
      return â˜ƒ.buildFuture();
   }

   static CompletableFuture<Suggestions> suggestResource(Iterable<ResourceLocation> var0, SuggestionsBuilder var1) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);
      filterResources(â˜ƒ, â˜ƒ, var0x -> var0x, var1x -> â˜ƒ.suggest(var1x.toString()));
      return â˜ƒ.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> suggestResource(
      Iterable<T> var0, SuggestionsBuilder var1, Function<T, ResourceLocation> var2, Function<T, Message> var3
   ) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);
      filterResources(â˜ƒ, â˜ƒ, â˜ƒ, var3x -> â˜ƒ.suggest(((ResourceLocation)â˜ƒ.apply(var3x)).toString(), (Message)â˜ƒ.apply(var3x)));
      return â˜ƒ.buildFuture();
   }

   static CompletableFuture<Suggestions> suggestResource(Stream<ResourceLocation> var0, SuggestionsBuilder var1) {
      return suggestResource(â˜ƒ::iterator, â˜ƒ);
   }

   static <T> CompletableFuture<Suggestions> suggestResource(
      Stream<T> var0, SuggestionsBuilder var1, Function<T, ResourceLocation> var2, Function<T, Message> var3
   ) {
      return suggestResource(â˜ƒ::iterator, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   static CompletableFuture<Suggestions> suggestCoordinates(
      String var0, Collection<SharedSuggestionProvider.TextCoordinates> var1, SuggestionsBuilder var2, Predicate<String> var3
   ) {
      List<String> â˜ƒ = Lists.newArrayList();
      if (Strings.isNullOrEmpty(â˜ƒ)) {
         for(SharedSuggestionProvider.TextCoordinates â˜ƒx : â˜ƒ) {
            String â˜ƒxx = â˜ƒx.x + " " + â˜ƒx.y + " " + â˜ƒx.z;
            if (â˜ƒ.test(â˜ƒxx)) {
               â˜ƒ.add(â˜ƒx.x);
               â˜ƒ.add(â˜ƒx.x + " " + â˜ƒx.y);
               â˜ƒ.add(â˜ƒxx);
            }
         }
      } else {
         String[] â˜ƒ = â˜ƒ.split(" ");
         if (â˜ƒ.length == 1) {
            for(SharedSuggestionProvider.TextCoordinates â˜ƒx : â˜ƒ) {
               String â˜ƒxx = â˜ƒ[0] + " " + â˜ƒx.y + " " + â˜ƒx.z;
               if (â˜ƒ.test(â˜ƒxx)) {
                  â˜ƒ.add(â˜ƒ[0] + " " + â˜ƒx.y);
                  â˜ƒ.add(â˜ƒxx);
               }
            }
         } else if (â˜ƒ.length == 2) {
            for(SharedSuggestionProvider.TextCoordinates â˜ƒ : â˜ƒ) {
               String â˜ƒx = â˜ƒ[0] + " " + â˜ƒ[1] + " " + â˜ƒ.z;
               if (â˜ƒ.test(â˜ƒx)) {
                  â˜ƒ.add(â˜ƒx);
               }
            }
         }
      }

      return suggest(â˜ƒ, â˜ƒ);
   }

   static CompletableFuture<Suggestions> suggest2DCoordinates(
      String var0, Collection<SharedSuggestionProvider.TextCoordinates> var1, SuggestionsBuilder var2, Predicate<String> var3
   ) {
      List<String> â˜ƒ = Lists.newArrayList();
      if (Strings.isNullOrEmpty(â˜ƒ)) {
         for(SharedSuggestionProvider.TextCoordinates â˜ƒx : â˜ƒ) {
            String â˜ƒxx = â˜ƒx.x + " " + â˜ƒx.z;
            if (â˜ƒ.test(â˜ƒxx)) {
               â˜ƒ.add(â˜ƒx.x);
               â˜ƒ.add(â˜ƒxx);
            }
         }
      } else {
         String[] â˜ƒ = â˜ƒ.split(" ");
         if (â˜ƒ.length == 1) {
            for(SharedSuggestionProvider.TextCoordinates â˜ƒx : â˜ƒ) {
               String â˜ƒxx = â˜ƒ[0] + " " + â˜ƒx.z;
               if (â˜ƒ.test(â˜ƒxx)) {
                  â˜ƒ.add(â˜ƒxx);
               }
            }
         }
      }

      return suggest(â˜ƒ, â˜ƒ);
   }

   static CompletableFuture<Suggestions> suggest(Iterable<String> var0, SuggestionsBuilder var1) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);

      for(String â˜ƒx : â˜ƒ) {
         if (matchesSubStr(â˜ƒ, â˜ƒx.toLowerCase(Locale.ROOT))) {
            â˜ƒ.suggest(â˜ƒx);
         }
      }

      return â˜ƒ.buildFuture();
   }

   static CompletableFuture<Suggestions> suggest(Stream<String> var0, SuggestionsBuilder var1) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);
      â˜ƒ.filter(var1x -> matchesSubStr(â˜ƒ, var1x.toLowerCase(Locale.ROOT))).forEach(â˜ƒ::suggest);
      return â˜ƒ.buildFuture();
   }

   static CompletableFuture<Suggestions> suggest(String[] var0, SuggestionsBuilder var1) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);

      for(String â˜ƒx : â˜ƒ) {
         if (matchesSubStr(â˜ƒ, â˜ƒx.toLowerCase(Locale.ROOT))) {
            â˜ƒ.suggest(â˜ƒx);
         }
      }

      return â˜ƒ.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> suggest(Iterable<T> var0, SuggestionsBuilder var1, Function<T, String> var2, Function<T, Message> var3) {
      String â˜ƒ = â˜ƒ.getRemaining().toLowerCase(Locale.ROOT);

      for(T â˜ƒx : â˜ƒ) {
         String â˜ƒxx = (String)â˜ƒ.apply(â˜ƒx);
         if (matchesSubStr(â˜ƒ, â˜ƒxx.toLowerCase(Locale.ROOT))) {
            â˜ƒ.suggest(â˜ƒxx, (Message)â˜ƒ.apply(â˜ƒx));
         }
      }

      return â˜ƒ.buildFuture();
   }

   static boolean matchesSubStr(String var0, String var1) {
      for(int â˜ƒ = 0; !â˜ƒ.startsWith(â˜ƒ, â˜ƒ); ++â˜ƒ) {
         â˜ƒ = â˜ƒ.indexOf(95, â˜ƒ);
         if (â˜ƒ < 0) {
            return false;
         }
      }

      return true;
   }

   public static class TextCoordinates {
      public static final SharedSuggestionProvider.TextCoordinates DEFAULT_LOCAL = new SharedSuggestionProvider.TextCoordinates("^", "^", "^");
      public static final SharedSuggestionProvider.TextCoordinates DEFAULT_GLOBAL = new SharedSuggestionProvider.TextCoordinates("~", "~", "~");
      public final String x;
      public final String y;
      public final String z;

      public TextCoordinates(String var1, String var2, String var3) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
         this.z = â˜ƒ;
      }
   }
}
