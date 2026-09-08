package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;

public final class TextColor {
   private static final String CUSTOM_COLOR_PREFIX = "#";
   private static final Map<ChatFormatting, TextColor> LEGACY_FORMAT_TO_COLOR = (Map<ChatFormatting, TextColor>)Stream.of(ChatFormatting.values())
      .filter(ChatFormatting::isColor)
      .collect(ImmutableMap.toImmutableMap(Function.identity(), var0 -> new TextColor(var0.getColor(), var0.getName())));
   private static final Map<String, TextColor> NAMED_COLORS = (Map<String, TextColor>)LEGACY_FORMAT_TO_COLOR.values()
      .stream()
      .collect(ImmutableMap.toImmutableMap(var0 -> var0.name, Function.identity()));
   private final int value;
   @Nullable
   private final String name;

   private TextColor(int var1, String var2) {
      this.value = â˜ƒ;
      this.name = â˜ƒ;
   }

   private TextColor(int var1) {
      this.value = â˜ƒ;
      this.name = null;
   }

   public int getValue() {
      return this.value;
   }

   public String serialize() {
      return this.name != null ? this.name : this.formatValue();
   }

   private String formatValue() {
      return String.format("#%06X", this.value);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         TextColor â˜ƒ = (TextColor)â˜ƒ;
         return this.value == â˜ƒ.value;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.value, this.name});
   }

   public String toString() {
      return this.name != null ? this.name : this.formatValue();
   }

   @Nullable
   public static TextColor fromLegacyFormat(ChatFormatting var0) {
      return (TextColor)LEGACY_FORMAT_TO_COLOR.get(â˜ƒ);
   }

   public static TextColor fromRgb(int var0) {
      return new TextColor(â˜ƒ);
   }

   @Nullable
   public static TextColor parseColor(String var0) {
      if (â˜ƒ.startsWith("#")) {
         try {
            int â˜ƒ = Integer.parseInt(â˜ƒ.substring(1), 16);
            return fromRgb(â˜ƒ);
         } catch (NumberFormatException var2) {
            return null;
         }
      } else {
         return (TextColor)NAMED_COLORS.get(â˜ƒ);
      }
   }
}
