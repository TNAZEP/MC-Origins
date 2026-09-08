package net.minecraft.client.resources.language;

import java.util.IllegalFormatException;
import net.minecraft.locale.Language;

public class I18n {
   private static volatile Language language = Language.getInstance();

   private I18n() {
   }

   static void setLanguage(Language var0) {
      language = â˜ƒ;
   }

   public static String get(String var0, Object... var1) {
      String â˜ƒ = language.getOrDefault(â˜ƒ);

      try {
         return String.format(â˜ƒ, â˜ƒ);
      } catch (IllegalFormatException var4) {
         return "Format error: " + â˜ƒ;
      }
   }

   public static boolean exists(String var0) {
      return language.has(â˜ƒ);
   }
}
