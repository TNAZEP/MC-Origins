package net.minecraft.util;

import com.google.common.collect.ImmutableMap;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileZipper implements Closeable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Path outputFile;
   private final Path tempFile;
   private final FileSystem fs;

   public FileZipper(Path var1) {
      this.outputFile = â˜ƒ;
      this.tempFile = â˜ƒ.resolveSibling(â˜ƒ.getFileName().toString() + "_tmp");

      try {
         this.fs = Util.ZIP_FILE_SYSTEM_PROVIDER.newFileSystem(this.tempFile, ImmutableMap.of("create", "true"));
      } catch (IOException var3) {
         throw new UncheckedIOException(var3);
      }
   }

   public void add(Path var1, String var2) {
      try {
         Path â˜ƒ = this.fs.getPath(File.separator);
         Path â˜ƒx = â˜ƒ.resolve(â˜ƒ.toString());
         Files.createDirectories(â˜ƒx.getParent());
         Files.write(â˜ƒx, â˜ƒ.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
      } catch (IOException var5) {
         throw new UncheckedIOException(var5);
      }
   }

   public void add(Path var1, File var2) {
      try {
         Path â˜ƒ = this.fs.getPath(File.separator);
         Path â˜ƒx = â˜ƒ.resolve(â˜ƒ.toString());
         Files.createDirectories(â˜ƒx.getParent());
         Files.copy(â˜ƒ.toPath(), â˜ƒx);
      } catch (IOException var5) {
         throw new UncheckedIOException(var5);
      }
   }

   public void add(Path var1) {
      try {
         Path â˜ƒ = this.fs.getPath(File.separator);
         if (Files.isRegularFile(â˜ƒ, new LinkOption[0])) {
            Path â˜ƒx = â˜ƒ.resolve(â˜ƒ.getParent().relativize(â˜ƒ).toString());
            Files.copy(â˜ƒx, â˜ƒ);
         } else {
            Stream<Path> â˜ƒ = Files.find(â˜ƒ, Integer.MAX_VALUE, (var0, var1x) -> var1x.isRegularFile(), new FileVisitOption[0]);

            try {
               for(Path â˜ƒx : (List)â˜ƒ.collect(Collectors.toList())) {
                  Path â˜ƒxx = â˜ƒ.resolve(â˜ƒ.relativize(â˜ƒx).toString());
                  Files.createDirectories(â˜ƒxx.getParent());
                  Files.copy(â˜ƒx, â˜ƒxx);
               }
            } catch (Throwable var8) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }
         }
      } catch (IOException var9) {
         throw new UncheckedIOException(var9);
      }
   }

   public void close() {
      try {
         this.fs.close();
         Files.move(this.tempFile, this.outputFile);
         LOGGER.info("Compressed to {}", this.outputFile);
      } catch (IOException var2) {
         throw new UncheckedIOException(var2);
      }
   }
}
