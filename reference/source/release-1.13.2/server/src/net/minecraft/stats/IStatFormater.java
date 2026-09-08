package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.util.Util;

public interface IStatFormater {
   DecimalFormat DECIMAL_FORMAT = Util.func_200696_a(
      new DecimalFormat("########0.00"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   IStatFormater DEFAULT = NumberFormat.getIntegerInstance(Locale.US)::format;
   IStatFormater DIVIDE_BY_TEN = var0 -> DECIMAL_FORMAT.format((double)var0 * 0.1);
   IStatFormater DISTANCE = var0 -> {
      double ☃ = (double)var0 / 100.0;
      double ☃x = ☃ / 1000.0;
      if (☃x > 0.5) {
         return DECIMAL_FORMAT.format(☃x) + " km";
      } else {
         return ☃ > 0.5 ? DECIMAL_FORMAT.format(☃) + " m" : var0 + " cm";
      }
   };
   IStatFormater TIME = var0 -> {
      double ☃ = (double)var0 / 20.0;
      double ☃x = ☃ / 60.0;
      double ☃xx = ☃x / 60.0;
      double ☃xxx = ☃xx / 24.0;
      double ☃xxxx = ☃xxx / 365.0;
      if (☃xxxx > 0.5) {
         return DECIMAL_FORMAT.format(☃xxxx) + " y";
      } else if (☃xxx > 0.5) {
         return DECIMAL_FORMAT.format(☃xxx) + " d";
      } else if (☃xx > 0.5) {
         return DECIMAL_FORMAT.format(☃xx) + " h";
      } else {
         return ☃x > 0.5 ? DECIMAL_FORMAT.format(☃x) + " m" : ☃ + " s";
      }
   };
}
