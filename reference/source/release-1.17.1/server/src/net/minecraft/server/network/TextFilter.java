package net.minecraft.server.network;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TextFilter {
   TextFilter DUMMY = new TextFilter() {
      @Override
      public void join() {
      }

      @Override
      public void leave() {
      }

      @Override
      public CompletableFuture<TextFilter.FilteredText> processStreamMessage(String var1) {
         return CompletableFuture.completedFuture(TextFilter.FilteredText.passThrough(â˜ƒ));
      }

      @Override
      public CompletableFuture<List<TextFilter.FilteredText>> processMessageBundle(List<String> var1) {
         return CompletableFuture.completedFuture((List)â˜ƒ.stream().map(TextFilter.FilteredText::passThrough).collect(ImmutableList.toImmutableList()));
      }
   };

   void join();

   void leave();

   CompletableFuture<TextFilter.FilteredText> processStreamMessage(String var1);

   CompletableFuture<List<TextFilter.FilteredText>> processMessageBundle(List<String> var1);

   public static class FilteredText {
      public static final TextFilter.FilteredText EMPTY = new TextFilter.FilteredText("", "");
      private final String raw;
      private final String filtered;

      public FilteredText(String var1, String var2) {
         this.raw = â˜ƒ;
         this.filtered = â˜ƒ;
      }

      public String getRaw() {
         return this.raw;
      }

      public String getFiltered() {
         return this.filtered;
      }

      public static TextFilter.FilteredText passThrough(String var0) {
         return new TextFilter.FilteredText(â˜ƒ, â˜ƒ);
      }

      public static TextFilter.FilteredText fullyFiltered(String var0) {
         return new TextFilter.FilteredText(â˜ƒ, "");
      }
   }
}
