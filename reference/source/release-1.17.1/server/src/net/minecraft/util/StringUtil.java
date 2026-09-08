package net.minecraft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {
   private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
   private static final Pattern LINE_PATTERN = Pattern.compile("\\r\\n|\\v");
   private static final Pattern LINE_END_PATTERN = Pattern.compile("(?:\\r\\n|\\v)$");

   public static String formatTickDuration(int var0) {
      int â˜ƒ = â˜ƒ / 20;
      int â˜ƒx = â˜ƒ / 60;
      â˜ƒ %= 60;
      return â˜ƒ < 10 ? â˜ƒx + ":0" + â˜ƒ : â˜ƒx + ":" + â˜ƒ;
   }

   public static String stripColor(String var0) {
      return STRIP_COLOR_PATTERN.matcher(â˜ƒ).replaceAll("");
   }

   public static boolean isNullOrEmpty(@Nullable String var0) {
      return StringUtils.isEmpty(â˜ƒ);
   }

   public static String truncateStringIfNecessary(String var0, int var1, boolean var2) {
      if (â˜ƒ.length() <= â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ && â˜ƒ > 3 ? â˜ƒ.substring(0, â˜ƒ - 3) + "..." : â˜ƒ.substring(0, â˜ƒ);
      }
   }

   public static int lineCount(String var0) {
      if (â˜ƒ.isEmpty()) {
         return 0;
      } else {
         Matcher â˜ƒ = LINE_PATTERN.matcher(â˜ƒ);
         int â˜ƒx = 1;

         while(â˜ƒ.find()) {
            ++â˜ƒx;
         }

         return â˜ƒx;
      }
   }

   public static boolean endsWithNewLine(String var0) {
      return LINE_END_PATTERN.matcher(â˜ƒ).find();
   }
}
