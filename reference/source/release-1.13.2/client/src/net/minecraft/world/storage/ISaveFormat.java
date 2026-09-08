package net.minecraft.world.storage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
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

   default long func_197713_h(String var1) throws IOException {
      final Path ☃ = this.func_197714_g(☃);
      String ☃x = LocalDateTime.now().format(field_197716_d) + "_" + ☃;
      int ☃xx = 0;
      Path ☃xxx = this.func_197712_e();

      try {
         Files.createDirectories(Files.exists(☃xxx, new LinkOption[0]) ? ☃xxx.toRealPath() : ☃xxx);
      } catch (IOException var19) {
         throw new RuntimeException(var19);
      }

      Path ☃;
      do {
         ☃ = ☃xxx.resolve(☃x + (☃xx++ > 0 ? "_" + ☃xx : "") + ".zip");
      } while(Files.exists(☃, new LinkOption[0]));

      final ZipOutputStream ☃xxxx = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(☃.toFile())));
      Throwable var8 = null;

      try {
         final Path ☃xxxxx = Paths.get(☃);
         Files.walkFileTree(☃, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path var1, BasicFileAttributes var2x) throws IOException {
               String ☃ = ☃.resolve(☃.relativize(☃)).toString();
               ZipEntry ☃x = new ZipEntry(☃);
               ☃.putNextEntry(☃x);
               com.google.common.io.Files.asByteSource(☃.toFile()).copyTo(☃);
               ☃.closeEntry();
               return FileVisitResult.CONTINUE;
            }
         });
         ☃xxxx.close();
      } catch (Throwable var18) {
         var8 = var18;
         throw var18;
      } finally {
         if (☃xxxx != null) {
            if (var8 != null) {
               try {
                  ☃xxxx.close();
               } catch (Throwable var17) {
                  var8.addSuppressed(var17);
               }
            } else {
               ☃xxxx.close();
            }
         }
      }

      return Files.size(☃);
   }

   String func_207741_a();

   ISaveHandler func_197715_a(String var1, @Nullable MinecraftServer var2);

   List<WorldSummary> func_75799_b() throws AnvilConverterException;

   void func_75800_d();

   @Nullable
   WorldInfo func_75803_c(String var1);

   boolean func_207742_d(String var1);

   boolean func_75802_e(String var1);

   void func_75806_a(String var1, String var2);

   boolean func_207743_a(String var1);

   boolean func_75801_b(String var1);

   boolean func_75805_a(String var1, IProgressUpdate var2);

   boolean func_90033_f(String var1);

   File func_186352_b(String var1, String var2);

   Path func_197714_g(String var1);

   Path func_197712_e();
}
