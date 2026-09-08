package net.minecraft;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum ChatFormatting {
   BLACK("BLACK", '0', 0, 0),
   DARK_BLUE("DARK_BLUE", '1', 1, 170),
   DARK_GREEN("DARK_GREEN", '2', 2, 43520),
   DARK_AQUA("DARK_AQUA", '3', 3, 43690),
   DARK_RED("DARK_RED", '4', 4, 11141120),
   DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
   GOLD("GOLD", '6', 6, 16755200),
   GRAY("GRAY", '7', 7, 11184810),
   DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
   BLUE("BLUE", '9', 9, 5592575),
   GREEN("GREEN", 'a', 10, 5635925),
   AQUA("AQUA", 'b', 11, 5636095),
   RED("RED", 'c', 12, 16733525),
   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
   YELLOW("YELLOW", 'e', 14, 16777045),
   WHITE("WHITE", 'f', 15, 16777215),
   OBFUSCATED("OBFUSCATED", 'k', true),
   BOLD("BOLD", 'l', true),
   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
   UNDERLINE("UNDERLINE", 'n', true),
   ITALIC("ITALIC", 'o', true),
   RESET("RESET", 'r', -1, null);

   public static final char PREFIX_CODE = '\u00a7';
   private static final Map<String, ChatFormatting> FORMATTING_BY_NAME = (Map<String, ChatFormatting>)Arrays.stream(values())
      .collect(Collectors.toMap(var0 -> cleanName(var0.name), var0 -> var0));
   private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
   private final String name;
   private final char code;
   private final boolean isFormat;
   private final String toString;
   private final int id;
   @Nullable
   private final Integer color;

   private static String cleanName(String var0) {
      return â˜ƒ.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
   }

   private ChatFormatting(String var3, char var4, int var5, @Nullable Integer var6) {
      this(â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ);
   }

   private ChatFormatting(String var3, char var4, boolean var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, -1, null);
   }

   private ChatFormatting(String var3, char var4, boolean var5, int var6, @Nullable Integer var7) {
      this.name = â˜ƒ;
      this.code = â˜ƒ;
      this.isFormat = â˜ƒ;
      this.id = â˜ƒ;
      this.color = â˜ƒ;
      this.toString = "\u00a7" + â˜ƒ;
   }

   public char getChar() {
      return this.code;
   }

   public int getId() {
      return this.id;
   }

   public boolean isFormat() {
      return this.isFormat;
   }

   public boolean isColor() {
      return !this.isFormat && this != RESET;
   }

   @Nullable
   public Integer getColor() {
      return this.color;
   }

   public String getName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public String toString() {
      return this.toString;
   }

   @Nullable
   public static String stripFormatting(@Nullable String var0) {
      return â˜ƒ == null ? null : STRIP_FORMATTING_PATTERN.matcher(â˜ƒ).replaceAll("");
   }

   @Nullable
   public static ChatFormatting getByName(@Nullable String var0) {
      return â˜ƒ == null ? null : (ChatFormatting)FORMATTING_BY_NAME.get(cleanName(â˜ƒ));
   }

   @Nullable
   public static ChatFormatting getById(int var0) {
      if (â˜ƒ < 0) {
         return RESET;
      } else {
         for(ChatFormatting â˜ƒ : values()) {
            if (â˜ƒ.getId() == â˜ƒ) {
               return â˜ƒ;
            }
         }

         return null;
      }
   }

   @Nullable
   public static ChatFormatting getByCode(char var0) {
      char â˜ƒ = Character.toString(â˜ƒ).toLowerCase(Locale.ROOT).charAt(0);

      for(ChatFormatting â˜ƒx : values()) {
         if (â˜ƒx.code == â˜ƒ) {
            return â˜ƒx;
         }
      }

      return null;
   }

   public static Collection<String> getNames(boolean var0, boolean var1) {
      List<String> â˜ƒ = Lists.newArrayList();

      for(ChatFormatting â˜ƒx : values()) {
         if ((!â˜ƒx.isColor() || â˜ƒ) && (!â˜ƒx.isFormat() || â˜ƒ)) {
            â˜ƒ.add(â˜ƒx.getName());
         }
      }

      return â˜ƒ;
   }
}
