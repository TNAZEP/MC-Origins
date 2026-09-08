package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.ComponentCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class ComponentRenderUtils {
   private static final FormattedCharSequence INDENT = FormattedCharSequence.codepoint(32, Style.EMPTY);

   private static String stripColor(String var0) {
      return Minecraft.getInstance().options.chatColors ? â˜ƒ : ChatFormatting.stripFormatting(â˜ƒ);
   }

   public static List<FormattedCharSequence> wrapComponents(FormattedText var0, int var1, Font var2) {
      ComponentCollector â˜ƒ = new ComponentCollector();
      â˜ƒ.visit((var1x, var2x) -> {
         â˜ƒ.append(FormattedText.of(stripColor(var2x), var1x));
         return Optional.empty();
      }, Style.EMPTY);
      List<FormattedCharSequence> â˜ƒx = Lists.<FormattedCharSequence>newArrayList();
      â˜ƒ.getSplitter().splitLines(â˜ƒ.getResultOrEmpty(), â˜ƒ, Style.EMPTY, (var1x, var2x) -> {
         FormattedCharSequence â˜ƒ = Language.getInstance().getVisualOrder(var1x);
         â˜ƒ.add(var2x ? FormattedCharSequence.composite(INDENT, â˜ƒ) : â˜ƒ);
      });
      return (List<FormattedCharSequence>)(â˜ƒx.isEmpty() ? Lists.<FormattedCharSequence>newArrayList(FormattedCharSequence.EMPTY) : â˜ƒx);
   }
}
