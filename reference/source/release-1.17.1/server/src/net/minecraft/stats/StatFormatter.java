package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.Util;

public interface StatFormatter {
   DecimalFormat DECIMAL_FORMAT = Util.make(
      new DecimalFormat("########0.00"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   StatFormatter DEFAULT = NumberFormat.getIntegerInstance(Locale.US)::format;
   StatFormatter DIVIDE_BY_TEN = var0 -> DECIMAL_FORMAT.format((double)var0 * 0.1);
   StatFormatter DISTANCE = var0 -> {
      double â˜ƒ = (double)var0 / 100.0;
      double â˜ƒx = â˜ƒ / 1000.0;
      if (â˜ƒx > 0.5) {
         return DECIMAL_FORMAT.format(â˜ƒx) + " km";
      } else {
         return â˜ƒ > 0.5 ? DECIMAL_FORMAT.format(â˜ƒ) + " m" : var0 + " cm";
      }
   };
   StatFormatter TIME = var0 -> {
      double â˜ƒ = (double)var0 / 20.0;
      double â˜ƒx = â˜ƒ / 60.0;
      double â˜ƒxx = â˜ƒx / 60.0;
      double â˜ƒxxx = â˜ƒxx / 24.0;
      double â˜ƒxxxx = â˜ƒxxx / 365.0;
      if (â˜ƒxxxx > 0.5) {
         return DECIMAL_FORMAT.format(â˜ƒxxxx) + " y";
      } else if (â˜ƒxxx > 0.5) {
         return DECIMAL_FORMAT.format(â˜ƒxxx) + " d";
      } else if (â˜ƒxx > 0.5) {
         return DECIMAL_FORMAT.format(â˜ƒxx) + " h";
      } else {
         return â˜ƒx > 0.5 ? DECIMAL_FORMAT.format(â˜ƒx) + " m" : â˜ƒ + " s";
      }
   };

   String format(int var1);
}
