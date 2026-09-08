package net.minecraft.util.text;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum TextFormatting {
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

   private static final Map<String, TextFormatting> field_96331_x = (Map<String, TextFormatting>)Arrays.stream(values())
      .collect(Collectors.toMap(var0 -> func_175745_c(var0.field_175748_y), var0 -> var0));
   private static final Pattern field_96330_y = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
   private final String field_175748_y;
   private final char field_96329_z;
   private final boolean field_96303_A;
   private final String field_96304_B;
   private final int field_175747_C;
   @Nullable
   private final Integer field_211167_D;

   private static String func_175745_c(String var0) {
      return ☃.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
   }

   private TextFormatting(String var3, char var4, int var5, @Nullable Integer var6) {
      this(☃, ☃, false, ☃, ☃);
   }

   private TextFormatting(String var3, char var4, boolean var5) {
      this(☃, ☃, ☃, -1, null);
   }

   private TextFormatting(String var3, char var4, boolean var5, int var6, @Nullable Integer var7) {
      this.field_175748_y = ☃;
      this.field_96329_z = ☃;
      this.field_96303_A = ☃;
      this.field_175747_C = ☃;
      this.field_211167_D = ☃;
      this.field_96304_B = "\u00a7" + ☃;
   }

   public static String func_211164_a(String var0) {
      StringBuilder ☃ = new StringBuilder();
      int ☃x = -1;
      int ☃xx = ☃.length();

      while((☃x = ☃.indexOf(167, ☃x + 1)) != -1) {
         if (☃x < ☃xx - 1) {
            TextFormatting ☃xxx = func_211165_a(☃.charAt(☃x + 1));
            if (☃xxx != null) {
               if (☃xxx.func_211166_f()) {
                  ☃.setLength(0);
               }

               if (☃xxx != RESET) {
                  ☃.append(☃xxx);
               }
            }
         }
      }

      return ☃.toString();
   }

   public int func_175746_b() {
      return this.field_175747_C;
   }

   public boolean func_96301_b() {
      return this.field_96303_A;
   }

   public boolean func_96302_c() {
      return !this.field_96303_A && this != RESET;
   }

   @Nullable
   public Integer func_211163_e() {
      return this.field_211167_D;
   }

   public boolean func_211166_f() {
      return !this.field_96303_A;
   }

   public String func_96297_d() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   public String toString() {
      return this.field_96304_B;
   }

   @Nullable
   public static String func_110646_a(@Nullable String var0) {
      return ☃ == null ? null : field_96330_y.matcher(☃).replaceAll("");
   }

   @Nullable
   public static TextFormatting func_96300_b(@Nullable String var0) {
      return ☃ == null ? null : (TextFormatting)field_96331_x.get(func_175745_c(☃));
   }

   @Nullable
   public static TextFormatting func_175744_a(int var0) {
      if (☃ < 0) {
         return RESET;
      } else {
         for(TextFormatting ☃ : values()) {
            if (☃.func_175746_b() == ☃) {
               return ☃;
            }
         }

         return null;
      }
   }

   @Nullable
   public static TextFormatting func_211165_a(char var0) {
      char ☃ = Character.toString(☃).toLowerCase(Locale.ROOT).charAt(0);

      for(TextFormatting ☃x : values()) {
         if (☃x.field_96329_z == ☃) {
            return ☃x;
         }
      }

      return null;
   }

   public static Collection<String> func_96296_a(boolean var0, boolean var1) {
      List<String> ☃ = Lists.newArrayList();

      for(TextFormatting ☃x : values()) {
         if ((!☃x.func_96302_c() || ☃) && (!☃x.func_96301_b() || ☃)) {
            ☃.add(☃x.func_96297_d());
         }
      }

      return ☃;
   }
}
