package net.minecraft.command;

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
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;

public interface ISuggestionProvider {
   Collection<String> func_197011_j();

   default Collection<String> func_211270_p() {
      return Collections.emptyList();
   }

   Collection<String> func_197012_k();

   Collection<ResourceLocation> func_197010_l();

   Collection<ResourceLocation> func_199612_m();

   CompletableFuture<Suggestions> func_197009_a(CommandContext<ISuggestionProvider> var1, SuggestionsBuilder var2);

   Collection<ISuggestionProvider.Coordinates> func_199613_a(boolean var1);

   boolean func_197034_c(int var1);

   static <T> void func_210512_a(Iterable<T> var0, String var1, Function<T, ResourceLocation> var2, Consumer<T> var3) {
      boolean ☃ = ☃.indexOf(58) > -1;

      for(T ☃x : ☃) {
         ResourceLocation ☃xx = (ResourceLocation)☃.apply(☃x);
         if (☃) {
            String ☃xxx = ☃xx.toString();
            if (☃xxx.startsWith(☃)) {
               ☃.accept(☃x);
            }
         } else if (☃xx.func_110624_b().startsWith(☃) || ☃xx.func_110624_b().equals("minecraft") && ☃xx.func_110623_a().startsWith(☃)) {
            ☃.accept(☃x);
         }
      }
   }

   static <T> void func_210511_a(Iterable<T> var0, String var1, String var2, Function<T, ResourceLocation> var3, Consumer<T> var4) {
      if (☃.isEmpty()) {
         ☃.forEach(☃);
      } else {
         String ☃ = Strings.commonPrefix(☃, ☃);
         if (!☃.isEmpty()) {
            String ☃x = ☃.substring(☃.length());
            func_210512_a(☃, ☃x, ☃, ☃);
         }
      }
   }

   static CompletableFuture<Suggestions> func_197006_a(Iterable<ResourceLocation> var0, SuggestionsBuilder var1, String var2) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);
      func_210511_a(☃, ☃, ☃, var0x -> var0x, var2x -> ☃.suggest(☃ + var2x));
      return ☃.buildFuture();
   }

   static CompletableFuture<Suggestions> func_197014_a(Iterable<ResourceLocation> var0, SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);
      func_210512_a(☃, ☃, var0x -> var0x, var1x -> ☃.suggest(var1x.toString()));
      return ☃.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> func_210514_a(
      Iterable<T> var0, SuggestionsBuilder var1, Function<T, ResourceLocation> var2, Function<T, Message> var3
   ) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);
      func_210512_a(☃, ☃, ☃, var3x -> ☃.suggest(((ResourceLocation)☃.apply(var3x)).toString(), (Message)☃.apply(var3x)));
      return ☃.buildFuture();
   }

   static CompletableFuture<Suggestions> func_212476_a(Stream<ResourceLocation> var0, SuggestionsBuilder var1) {
      return func_197014_a(☃::iterator, ☃);
   }

   static <T> CompletableFuture<Suggestions> func_201725_a(
      Stream<T> var0, SuggestionsBuilder var1, Function<T, ResourceLocation> var2, Function<T, Message> var3
   ) {
      return func_210514_a(☃::iterator, ☃, ☃, ☃);
   }

   static CompletableFuture<Suggestions> func_209000_a(
      String var0, Collection<ISuggestionProvider.Coordinates> var1, SuggestionsBuilder var2, Predicate<String> var3
   ) {
      List<String> ☃ = Lists.newArrayList();
      if (Strings.isNullOrEmpty(☃)) {
         for(ISuggestionProvider.Coordinates ☃x : ☃) {
            String ☃xx = ☃x.field_209006_c + " " + ☃x.field_209007_d + " " + ☃x.field_209008_e;
            if (☃.test(☃xx)) {
               ☃.add(☃x.field_209006_c);
               ☃.add(☃x.field_209006_c + " " + ☃x.field_209007_d);
               ☃.add(☃xx);
            }
         }
      } else {
         String[] ☃ = ☃.split(" ");
         if (☃.length == 1) {
            for(ISuggestionProvider.Coordinates ☃x : ☃) {
               String ☃xx = ☃[0] + " " + ☃x.field_209007_d + " " + ☃x.field_209008_e;
               if (☃.test(☃xx)) {
                  ☃.add(☃[0] + " " + ☃x.field_209007_d);
                  ☃.add(☃xx);
               }
            }
         } else if (☃.length == 2) {
            for(ISuggestionProvider.Coordinates ☃ : ☃) {
               String ☃x = ☃[0] + " " + ☃[1] + " " + ☃.field_209008_e;
               if (☃.test(☃x)) {
                  ☃.add(☃x);
               }
            }
         }
      }

      return func_197005_b(☃, ☃);
   }

   static CompletableFuture<Suggestions> func_211269_a(
      String var0, Collection<ISuggestionProvider.Coordinates> var1, SuggestionsBuilder var2, Predicate<String> var3
   ) {
      List<String> ☃ = Lists.newArrayList();
      if (Strings.isNullOrEmpty(☃)) {
         for(ISuggestionProvider.Coordinates ☃x : ☃) {
            String ☃xx = ☃x.field_209006_c + " " + ☃x.field_209008_e;
            if (☃.test(☃xx)) {
               ☃.add(☃x.field_209006_c);
               ☃.add(☃xx);
            }
         }
      } else {
         String[] ☃ = ☃.split(" ");
         if (☃.length == 1) {
            for(ISuggestionProvider.Coordinates ☃x : ☃) {
               String ☃xx = ☃[0] + " " + ☃x.field_209008_e;
               if (☃.test(☃xx)) {
                  ☃.add(☃xx);
               }
            }
         }
      }

      return func_197005_b(☃, ☃);
   }

   static CompletableFuture<Suggestions> func_197005_b(Iterable<String> var0, SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);

      for(String ☃x : ☃) {
         if (☃x.toLowerCase(Locale.ROOT).startsWith(☃)) {
            ☃.suggest(☃x);
         }
      }

      return ☃.buildFuture();
   }

   static CompletableFuture<Suggestions> func_197013_a(Stream<String> var0, SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);
      ☃.filter(var1x -> var1x.toLowerCase(Locale.ROOT).startsWith(☃)).forEach(☃::suggest);
      return ☃.buildFuture();
   }

   static CompletableFuture<Suggestions> func_197008_a(String[] var0, SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);

      for(String ☃x : ☃) {
         if (☃x.toLowerCase(Locale.ROOT).startsWith(☃)) {
            ☃.suggest(☃x);
         }
      }

      return ☃.buildFuture();
   }

   public static class Coordinates {
      public static final ISuggestionProvider.Coordinates field_209004_a = new ISuggestionProvider.Coordinates("^", "^", "^");
      public static final ISuggestionProvider.Coordinates field_209005_b = new ISuggestionProvider.Coordinates("~", "~", "~");
      public final String field_209006_c;
      public final String field_209007_d;
      public final String field_209008_e;

      public Coordinates(String var1, String var2, String var3) {
         this.field_209006_c = ☃;
         this.field_209007_d = ☃;
         this.field_209008_e = ☃;
      }
   }
}
