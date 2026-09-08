package net.minecraft.data.structures;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnbtToNbt implements DataProvider {
   @Nullable
   private static final Path DUMP_SNBT_TO = null;
   private static final Logger LOGGER = LogManager.getLogger();
   private final DataGenerator generator;
   private final List<SnbtToNbt.Filter> filters = Lists.<SnbtToNbt.Filter>newArrayList();

   public SnbtToNbt(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   public SnbtToNbt addFilter(SnbtToNbt.Filter var1) {
      this.filters.add(â˜ƒ);
      return this;
   }

   private CompoundTag applyFilters(String var1, CompoundTag var2) {
      CompoundTag â˜ƒ = â˜ƒ;

      for(SnbtToNbt.Filter â˜ƒx : this.filters) {
         â˜ƒ = â˜ƒx.apply(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   @Override
   public void run(HashCache var1) throws IOException {
      Path â˜ƒ = this.generator.getOutputFolder();
      List<CompletableFuture<SnbtToNbt.TaskResult>> â˜ƒx = Lists.newArrayList();

      for(Path â˜ƒxx : this.generator.getInputFolders()) {
         Files.walk(â˜ƒxx)
            .filter(var0 -> var0.toString().endsWith(".snbt"))
            .forEach(var3x -> â˜ƒ.add(CompletableFuture.supplyAsync(() -> this.readStructure(var3x, this.getName(â˜ƒ, var3x)), Util.backgroundExecutor())));
      }

      boolean â˜ƒxx = false;

      for(CompletableFuture<SnbtToNbt.TaskResult> â˜ƒxxx : â˜ƒx) {
         try {
            this.storeStructureIfChanged(â˜ƒ, (SnbtToNbt.TaskResult)â˜ƒxxx.get(), â˜ƒ);
         } catch (Exception var8) {
            LOGGER.error("Failed to process structure", var8);
            â˜ƒxx = true;
         }
      }

      if (â˜ƒxx) {
         throw new IllegalStateException("Failed to convert all structures, aborting");
      }
   }

   @Override
   public String getName() {
      return "SNBT -> NBT";
   }

   private String getName(Path var1, Path var2) {
      String â˜ƒ = â˜ƒ.relativize(â˜ƒ).toString().replaceAll("\\\\", "/");
      return â˜ƒ.substring(0, â˜ƒ.length() - ".snbt".length());
   }

   private SnbtToNbt.TaskResult readStructure(Path var1, String var2) {
      try {
         BufferedReader â˜ƒ = Files.newBufferedReader(â˜ƒ);

         SnbtToNbt.TaskResult var10;
         try {
            String â˜ƒxx = IOUtils.toString(â˜ƒ);
            CompoundTag â˜ƒxxx = this.applyFilters(â˜ƒ, NbtUtils.snbtToStructure(â˜ƒxx));
            ByteArrayOutputStream â˜ƒxxxx = new ByteArrayOutputStream();
            NbtIo.writeCompressed(â˜ƒxxx, â˜ƒxxxx);
            byte[] â˜ƒxxxxx = â˜ƒxxxx.toByteArray();
            String â˜ƒxxxxxx = SHA1.hashBytes(â˜ƒxxxxx).toString();
            String â˜ƒx;
            if (DUMP_SNBT_TO != null) {
               â˜ƒx = NbtUtils.structureToSnbt(â˜ƒxxx);
            } else {
               â˜ƒx = null;
            }

            var10 = new SnbtToNbt.TaskResult(â˜ƒ, â˜ƒxxxxx, â˜ƒx, â˜ƒxxxxxx);
         } catch (Throwable var12) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }
            }

            throw var12;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }

         return var10;
      } catch (Throwable var13) {
         throw new SnbtToNbt.StructureConversionException(â˜ƒ, var13);
      }
   }

   private void storeStructureIfChanged(HashCache var1, SnbtToNbt.TaskResult var2, Path var3) {
      if (â˜ƒ.snbtPayload != null) {
         Path â˜ƒ = DUMP_SNBT_TO.resolve(â˜ƒ.name + ".snbt");

         try {
            NbtToSnbt.writeSnbt(â˜ƒ, â˜ƒ.snbtPayload);
         } catch (IOException var9) {
            LOGGER.error("Couldn't write structure SNBT {} at {}", â˜ƒ.name, â˜ƒ, var9);
         }
      }

      Path â˜ƒ = â˜ƒ.resolve(â˜ƒ.name + ".nbt");

      try {
         if (!Objects.equals(â˜ƒ.getHash(â˜ƒ), â˜ƒ.hash) || !Files.exists(â˜ƒ, new LinkOption[0])) {
            Files.createDirectories(â˜ƒ.getParent());
            OutputStream â˜ƒx = Files.newOutputStream(â˜ƒ);

            try {
               â˜ƒx.write(â˜ƒ.payload);
            } catch (Throwable var10) {
               if (â˜ƒx != null) {
                  try {
                     â˜ƒx.close();
                  } catch (Throwable var8) {
                     var10.addSuppressed(var8);
                  }
               }

               throw var10;
            }

            if (â˜ƒx != null) {
               â˜ƒx.close();
            }
         }

         â˜ƒ.putNew(â˜ƒ, â˜ƒ.hash);
      } catch (IOException var11) {
         LOGGER.error("Couldn't write structure {} at {}", â˜ƒ.name, â˜ƒ, var11);
      }
   }

   @FunctionalInterface
   public interface Filter {
      CompoundTag apply(String var1, CompoundTag var2);
   }

   static class StructureConversionException extends RuntimeException {
      public StructureConversionException(Path var1, Throwable var2) {
         super(â˜ƒ.toAbsolutePath().toString(), â˜ƒ);
      }
   }

   static class TaskResult {
      final String name;
      final byte[] payload;
      @Nullable
      final String snbtPayload;
      final String hash;

      public TaskResult(String var1, byte[] var2, @Nullable String var3, String var4) {
         this.name = â˜ƒ;
         this.payload = â˜ƒ;
         this.snbtPayload = â˜ƒ;
         this.hash = â˜ƒ;
      }
   }
}
