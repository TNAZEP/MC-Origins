package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringDecomposer;

public class SubStringSource {
   private final String plainText;
   private final List<Style> charStyles;
   private final Int2IntFunction reverseCharModifier;

   private SubStringSource(String var1, List<Style> var2, Int2IntFunction var3) {
      this.plainText = â˜ƒ;
      this.charStyles = ImmutableList.copyOf(â˜ƒ);
      this.reverseCharModifier = â˜ƒ;
   }

   public String getPlainText() {
      return this.plainText;
   }

   public List<FormattedCharSequence> substring(int var1, int var2, boolean var3) {
      if (â˜ƒ == 0) {
         return ImmutableList.of();
      } else {
         List<FormattedCharSequence> â˜ƒ = Lists.<FormattedCharSequence>newArrayList();
         Style â˜ƒx = (Style)this.charStyles.get(â˜ƒ);
         int â˜ƒxx = â˜ƒ;

         for(int â˜ƒxxx = 1; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒ + â˜ƒxxx;
            Style â˜ƒxxxxx = (Style)this.charStyles.get(â˜ƒxxxx);
            if (!â˜ƒxxxxx.equals(â˜ƒx)) {
               String â˜ƒxxxxxx = this.plainText.substring(â˜ƒxx, â˜ƒxxxx);
               â˜ƒ.add(â˜ƒ ? FormattedCharSequence.backward(â˜ƒxxxxxx, â˜ƒx, this.reverseCharModifier) : FormattedCharSequence.forward(â˜ƒxxxxxx, â˜ƒx));
               â˜ƒx = â˜ƒxxxxx;
               â˜ƒxx = â˜ƒxxxx;
            }
         }

         if (â˜ƒxx < â˜ƒ + â˜ƒ) {
            String â˜ƒxxx = this.plainText.substring(â˜ƒxx, â˜ƒ + â˜ƒ);
            â˜ƒ.add(â˜ƒ ? FormattedCharSequence.backward(â˜ƒxxx, â˜ƒx, this.reverseCharModifier) : FormattedCharSequence.forward(â˜ƒxxx, â˜ƒx));
         }

         return â˜ƒ ? Lists.reverse(â˜ƒ) : â˜ƒ;
      }
   }

   public static SubStringSource create(FormattedText var0) {
      return create(â˜ƒ, var0x -> var0x, var0x -> var0x);
   }

   public static SubStringSource create(FormattedText var0, Int2IntFunction var1, UnaryOperator<String> var2) {
      StringBuilder â˜ƒ = new StringBuilder();
      List<Style> â˜ƒx = Lists.<Style>newArrayList();
      â˜ƒ.visit((var2x, var3x) -> {
         StringDecomposer.iterateFormatted(var3x, var2x, (var2xx, var3xx, var4x) -> {
            â˜ƒ.appendCodePoint(var4x);
            int â˜ƒ = Character.charCount(var4x);

            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
               â˜ƒ.add(var3xx);
            }

            return true;
         });
         return Optional.empty();
      }, Style.EMPTY);
      return new SubStringSource((String)â˜ƒ.apply(â˜ƒ.toString()), â˜ƒx, â˜ƒ);
   }
}
