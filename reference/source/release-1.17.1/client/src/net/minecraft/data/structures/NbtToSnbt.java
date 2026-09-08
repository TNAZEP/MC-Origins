package net.minecraft.data.structures;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NbtToSnbt implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private final DataGenerator generator;

   public NbtToSnbt(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) throws IOException {
      Path â˜ƒ = this.generator.getOutputFolder();

      for(Path â˜ƒx : this.generator.getInputFolders()) {
         Files.walk(â˜ƒx).filter(var0 -> var0.toString().endsWith(".nbt")).forEach(var3 -> convertStructure(var3, this.getName(â˜ƒ, var3), â˜ƒ));
      }
   }

   @Override
   public String getName() {
      return "NBT to SNBT";
   }

   private String getName(Path var1, Path var2) {
      String â˜ƒ = â˜ƒ.relativize(â˜ƒ).toString().replaceAll("\\\\", "/");
      return â˜ƒ.substring(0, â˜ƒ.length() - ".nbt".length());
   }

   @Nullable
   public static Path convertStructure(Path var0, String var1, Path var2) {
      try {
         writeSnbt(â˜ƒ.resolve(â˜ƒ + ".snbt"), NbtUtils.structureToSnbt(NbtIo.readCompressed(Files.newInputStream(â˜ƒ))));
         LOGGER.info("Converted {} from NBT to SNBT", â˜ƒ);
         return â˜ƒ.resolve(â˜ƒ + ".snbt");
      } catch (IOException var4) {
         LOGGER.error("Couldn't convert {} from NBT to SNBT at {}", â˜ƒ, â˜ƒ, var4);
         return null;
      }
   }

   public static void writeSnbt(Path var0, String var1) throws IOException {
      Files.createDirectories(â˜ƒ.getParent());
      BufferedWriter â˜ƒ = Files.newBufferedWriter(â˜ƒ);

      try {
         â˜ƒ.write(â˜ƒ);
         â˜ƒ.write(10);
      } catch (Throwable var6) {
         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }
         }

         throw var6;
      }

      if (â˜ƒ != null) {
         â˜ƒ.close();
      }
   }
}
