package net.minecraft.world.storage;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   DateTimeFormatter field_197716_d = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .appendLiteral('_')
      .appendValue(ChronoField.HOUR_OF_DAY, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
      .toFormatter();

   ISaveHandler func_197715_a(String var1, @Nullable MinecraftServer var2);

   @Nullable
   WorldInfo func_75803_c(String var1);

   boolean func_75801_b(String var1);

   boolean func_75805_a(String var1, IProgressUpdate var2);

   File func_186352_b(String var1, String var2);
}
