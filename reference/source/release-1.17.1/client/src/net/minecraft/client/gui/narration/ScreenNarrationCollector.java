package net.minecraft.client.gui.narration;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;

public class ScreenNarrationCollector {
   int generation;
   final Map<ScreenNarrationCollector.EntryKey, ScreenNarrationCollector.NarrationEntry> entries = Maps.newTreeMap(
      Comparator.comparing(var0 -> var0.type).thenComparing(var0 -> var0.depth)
   );

   public void update(Consumer<NarrationElementOutput> var1) {
      ++this.generation;
      â˜ƒ.accept(new ScreenNarrationCollector.Output(0));
   }

   public String collectNarrationText(boolean var1) {
      final StringBuilder â˜ƒ = new StringBuilder();
      Consumer<String> â˜ƒx = new Consumer<String>() {
         private boolean firstEntry = true;

         public void accept(String var1) {
            if (!this.firstEntry) {
               â˜ƒ.append(". ");
            }

            this.firstEntry = false;
            â˜ƒ.append(â˜ƒ);
         }
      };
      this.entries.forEach((var3x, var4) -> {
         if (var4.generation == this.generation && (â˜ƒ || !var4.alreadyNarrated)) {
            var4.contents.getText(â˜ƒ);
            var4.alreadyNarrated = true;
         }
      });
      return â˜ƒ.toString();
   }

   static class EntryKey {
      final NarratedElementType type;
      final int depth;

      EntryKey(NarratedElementType var1, int var2) {
         this.type = â˜ƒ;
         this.depth = â˜ƒ;
      }
   }

   static class NarrationEntry {
      NarrationThunk<?> contents = NarrationThunk.EMPTY;
      int generation = -1;
      boolean alreadyNarrated;

      public ScreenNarrationCollector.NarrationEntry update(int var1, NarrationThunk<?> var2) {
         if (!this.contents.equals(â˜ƒ)) {
            this.contents = â˜ƒ;
            this.alreadyNarrated = false;
         } else if (this.generation + 1 != â˜ƒ) {
            this.alreadyNarrated = false;
         }

         this.generation = â˜ƒ;
         return this;
      }
   }

   class Output implements NarrationElementOutput {
      private final int depth;

      Output(int var2) {
         this.depth = â˜ƒ;
      }

      @Override
      public void add(NarratedElementType var1, NarrationThunk<?> var2) {
         ((ScreenNarrationCollector.NarrationEntry)ScreenNarrationCollector.this.entries
               .computeIfAbsent(new ScreenNarrationCollector.EntryKey(â˜ƒ, this.depth), var0 -> new ScreenNarrationCollector.NarrationEntry()))
            .update(ScreenNarrationCollector.this.generation, â˜ƒ);
      }

      @Override
      public NarrationElementOutput nest() {
         return ScreenNarrationCollector.this.new Output(this.depth + 1);
      }
   }
}
