package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import net.minecraft.network.chat.Style;

@FunctionalInterface
public interface FormattedCharSequence {
   FormattedCharSequence EMPTY = var0 -> true;

   boolean accept(FormattedCharSink var1);

   static FormattedCharSequence codepoint(int var0, Style var1) {
      return var2 -> var2.accept(0, â˜ƒ, â˜ƒ);
   }

   static FormattedCharSequence forward(String var0, Style var1) {
      return â˜ƒ.isEmpty() ? EMPTY : var2 -> StringDecomposer.iterate(â˜ƒ, â˜ƒ, var2);
   }

   static FormattedCharSequence forward(String var0, Style var1, Int2IntFunction var2) {
      return â˜ƒ.isEmpty() ? EMPTY : var3 -> StringDecomposer.iterate(â˜ƒ, â˜ƒ, decorateOutput(var3, â˜ƒ));
   }

   static FormattedCharSequence backward(String var0, Style var1) {
      return â˜ƒ.isEmpty() ? EMPTY : var2 -> StringDecomposer.iterateBackwards(â˜ƒ, â˜ƒ, var2);
   }

   static FormattedCharSequence backward(String var0, Style var1, Int2IntFunction var2) {
      return â˜ƒ.isEmpty() ? EMPTY : var3 -> StringDecomposer.iterateBackwards(â˜ƒ, â˜ƒ, decorateOutput(var3, â˜ƒ));
   }

   static FormattedCharSink decorateOutput(FormattedCharSink var0, Int2IntFunction var1) {
      return (var2, var3, var4) -> â˜ƒ.accept(var2, var3, â˜ƒ.apply(Integer.valueOf(var4)));
   }

   static FormattedCharSequence composite() {
      return EMPTY;
   }

   static FormattedCharSequence composite(FormattedCharSequence var0) {
      return â˜ƒ;
   }

   static FormattedCharSequence composite(FormattedCharSequence var0, FormattedCharSequence var1) {
      return fromPair(â˜ƒ, â˜ƒ);
   }

   static FormattedCharSequence composite(FormattedCharSequence... var0) {
      return fromList(ImmutableList.copyOf(â˜ƒ));
   }

   static FormattedCharSequence composite(List<FormattedCharSequence> var0) {
      int â˜ƒ = â˜ƒ.size();
      switch(â˜ƒ) {
         case 0:
            return EMPTY;
         case 1:
            return (FormattedCharSequence)â˜ƒ.get(0);
         case 2:
            return fromPair((FormattedCharSequence)â˜ƒ.get(0), (FormattedCharSequence)â˜ƒ.get(1));
         default:
            return fromList(ImmutableList.copyOf(â˜ƒ));
      }
   }

   static FormattedCharSequence fromPair(FormattedCharSequence var0, FormattedCharSequence var1) {
      return var2 -> â˜ƒ.accept(var2) && â˜ƒ.accept(var2);
   }

   static FormattedCharSequence fromList(List<FormattedCharSequence> var0) {
      return var1 -> {
         for(FormattedCharSequence â˜ƒ : â˜ƒ) {
            if (!â˜ƒ.accept(var1)) {
               return false;
            }
         }

         return true;
      };
   }
}
