package net.minecraft.util;

import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class StringUtils {
   private static final Pattern field_76339_a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String func_76337_a(int var0) {
      int ☃ = ☃ / 20;
      int ☃x = ☃ / 60;
      ☃ %= 60;
      return ☃ < 10 ? ☃x + ":0" + ☃ : ☃x + ":" + ☃;
   }

   public static String func_76338_a(String var0) {
      return field_76339_a.matcher(☃).replaceAll("");
   }

   public static boolean func_151246_b(@Nullable String var0) {
      return org.apache.commons.lang3.StringUtils.isEmpty(☃);
   }
}
