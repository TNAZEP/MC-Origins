package net.minecraft.client.resources.language;

import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.List;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.SubStringSource;
import net.minecraft.util.FormattedCharSequence;

public class FormattedBidiReorder {
   public static FormattedCharSequence reorder(FormattedText var0, boolean var1) {
      SubStringSource â˜ƒ = SubStringSource.create(â˜ƒ, UCharacter::getMirror, FormattedBidiReorder::shape);
      Bidi â˜ƒx = new Bidi(â˜ƒ.getPlainText(), â˜ƒ ? 127 : 126);
      â˜ƒx.setReorderingMode(0);
      List<FormattedCharSequence> â˜ƒxx = Lists.<FormattedCharSequence>newArrayList();
      int â˜ƒxxx = â˜ƒx.countRuns();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
         BidiRun â˜ƒxxxxx = â˜ƒx.getVisualRun(â˜ƒxxxx);
         â˜ƒxx.addAll(â˜ƒ.substring(â˜ƒxxxxx.getStart(), â˜ƒxxxxx.getLength(), â˜ƒxxxxx.isOddRun()));
      }

      return FormattedCharSequence.composite(â˜ƒxx);
   }

   private static String shape(String var0) {
      try {
         return new ArabicShaping(8).shape(â˜ƒ);
      } catch (Exception var2) {
         return â˜ƒ;
      }
   }
}
