package net.minecraft.util;

import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

public class StringDecomposer {
   private static final char REPLACEMENT_CHAR = '\ufffd';
   private static final Optional<Object> STOP_ITERATION = Optional.of(Unit.INSTANCE);

   private static boolean feedChar(Style var0, FormattedCharSink var1, int var2, char var3) {
      return Character.isSurrogate(â˜ƒ) ? â˜ƒ.accept(â˜ƒ, â˜ƒ, 65533) : â˜ƒ.accept(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean iterate(String var0, Style var1, FormattedCharSink var2) {
      int â˜ƒ = â˜ƒ.length();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         char â˜ƒxx = â˜ƒ.charAt(â˜ƒx);
         if (Character.isHighSurrogate(â˜ƒxx)) {
            if (â˜ƒx + 1 >= â˜ƒ) {
               if (!â˜ƒ.accept(â˜ƒx, â˜ƒ, 65533)) {
                  return false;
               }
               break;
            }

            char â˜ƒxxx = â˜ƒ.charAt(â˜ƒx + 1);
            if (Character.isLowSurrogate(â˜ƒxxx)) {
               if (!â˜ƒ.accept(â˜ƒx, â˜ƒ, Character.toCodePoint(â˜ƒxx, â˜ƒxxx))) {
                  return false;
               }

               ++â˜ƒx;
            } else if (!â˜ƒ.accept(â˜ƒx, â˜ƒ, 65533)) {
               return false;
            }
         } else if (!feedChar(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean iterateBackwards(String var0, Style var1, FormattedCharSink var2) {
      int â˜ƒ = â˜ƒ.length();

      for(int â˜ƒx = â˜ƒ - 1; â˜ƒx >= 0; --â˜ƒx) {
         char â˜ƒxx = â˜ƒ.charAt(â˜ƒx);
         if (Character.isLowSurrogate(â˜ƒxx)) {
            if (â˜ƒx - 1 < 0) {
               if (!â˜ƒ.accept(0, â˜ƒ, 65533)) {
                  return false;
               }
               break;
            }

            char â˜ƒxxx = â˜ƒ.charAt(â˜ƒx - 1);
            if (Character.isHighSurrogate(â˜ƒxxx)) {
               if (!â˜ƒ.accept(--â˜ƒx, â˜ƒ, Character.toCodePoint(â˜ƒxxx, â˜ƒxx))) {
                  return false;
               }
            } else if (!â˜ƒ.accept(â˜ƒx, â˜ƒ, 65533)) {
               return false;
            }
         } else if (!feedChar(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean iterateFormatted(String var0, Style var1, FormattedCharSink var2) {
      return iterateFormatted(â˜ƒ, 0, â˜ƒ, â˜ƒ);
   }

   public static boolean iterateFormatted(String var0, int var1, Style var2, FormattedCharSink var3) {
      return iterateFormatted(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static boolean iterateFormatted(String var0, int var1, Style var2, Style var3, FormattedCharSink var4) {
      int â˜ƒ = â˜ƒ.length();
      Style â˜ƒx = â˜ƒ;

      for(int â˜ƒxx = â˜ƒ; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         char â˜ƒxxx = â˜ƒ.charAt(â˜ƒxx);
         if (â˜ƒxxx == 167) {
            if (â˜ƒxx + 1 >= â˜ƒ) {
               break;
            }

            char â˜ƒxxxx = â˜ƒ.charAt(â˜ƒxx + 1);
            ChatFormatting â˜ƒxxxxx = ChatFormatting.getByCode(â˜ƒxxxx);
            if (â˜ƒxxxxx != null) {
               â˜ƒx = â˜ƒxxxxx == ChatFormatting.RESET ? â˜ƒ : â˜ƒx.applyLegacyFormat(â˜ƒxxxxx);
            }

            ++â˜ƒxx;
         } else if (Character.isHighSurrogate(â˜ƒxxx)) {
            if (â˜ƒxx + 1 >= â˜ƒ) {
               if (!â˜ƒ.accept(â˜ƒxx, â˜ƒx, 65533)) {
                  return false;
               }
               break;
            }

            char â˜ƒxxx = â˜ƒ.charAt(â˜ƒxx + 1);
            if (Character.isLowSurrogate(â˜ƒxxx)) {
               if (!â˜ƒ.accept(â˜ƒxx, â˜ƒx, Character.toCodePoint(â˜ƒxxx, â˜ƒxxx))) {
                  return false;
               }

               ++â˜ƒxx;
            } else if (!â˜ƒ.accept(â˜ƒxx, â˜ƒx, 65533)) {
               return false;
            }
         } else if (!feedChar(â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒxxx)) {
            return false;
         }
      }

      return true;
   }

   public static boolean iterateFormatted(FormattedText var0, Style var1, FormattedCharSink var2) {
      return !â˜ƒ.visit((var1x, var2x) -> iterateFormatted(var2x, 0, var1x, â˜ƒ) ? Optional.empty() : STOP_ITERATION, â˜ƒ).isPresent();
   }

   public static String filterBrokenSurrogates(String var0) {
      StringBuilder â˜ƒ = new StringBuilder();
      iterate(â˜ƒ, Style.EMPTY, (var1x, var2, var3) -> {
         â˜ƒ.appendCodePoint(var3);
         return true;
      });
      return â˜ƒ.toString();
   }

   public static String getPlainText(FormattedText var0) {
      StringBuilder â˜ƒ = new StringBuilder();
      iterateFormatted(â˜ƒ, Style.EMPTY, (var1x, var2, var3) -> {
         â˜ƒ.appendCodePoint(var3);
         return true;
      });
      return â˜ƒ.toString();
   }
}
