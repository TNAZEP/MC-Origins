package net.minecraft.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Eula {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Path file;
   private final boolean agreed;

   public Eula(Path var1) {
      this.file = â˜ƒ;
      this.agreed = SharedConstants.IS_RUNNING_IN_IDE || this.readFile();
   }

   private boolean readFile() {
      try {
         InputStream â˜ƒ = Files.newInputStream(this.file);

         boolean var3;
         try {
            Properties â˜ƒx = new Properties();
            â˜ƒx.load(â˜ƒ);
            var3 = Boolean.parseBoolean(â˜ƒx.getProperty("eula", "false"));
         } catch (Throwable var5) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var4) {
                  var5.addSuppressed(var4);
               }
            }

            throw var5;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }

         return var3;
      } catch (Exception var6) {
         LOGGER.warn("Failed to load {}", this.file);
         this.saveDefaults();
         return false;
      }
   }

   public boolean hasAgreedToEULA() {
      return this.agreed;
   }

   private void saveDefaults() {
      if (!SharedConstants.IS_RUNNING_IN_IDE) {
         try {
            OutputStream â˜ƒ = Files.newOutputStream(this.file);

            try {
               Properties â˜ƒx = new Properties();
               â˜ƒx.setProperty("eula", "false");
               â˜ƒx.store(
                  â˜ƒ,
                  "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula)."
               );
            } catch (Throwable var5) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var4) {
                     var5.addSuppressed(var4);
                  }
               }

               throw var5;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }
         } catch (Exception var6) {
            LOGGER.warn("Failed to save {}", this.file, var6);
         }
      }
   }
}
