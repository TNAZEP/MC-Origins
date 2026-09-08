package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.minecraft.util.SharedConstants;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerEula {
   private static final Logger field_154349_a = LogManager.getLogger();
   private final File field_154350_b;
   private final boolean field_154351_c;

   public ServerEula(File var1) {
      this.field_154350_b = ☃;
      this.field_154351_c = SharedConstants.field_206244_b || this.func_154347_a(☃);
   }

   private boolean func_154347_a(File var1) {
      FileInputStream ☃ = null;
      boolean ☃x = false;

      try {
         Properties ☃xx = new Properties();
         ☃ = new FileInputStream(☃);
         ☃xx.load(☃);
         ☃x = Boolean.parseBoolean(☃xx.getProperty("eula", "false"));
      } catch (Exception var8) {
         field_154349_a.warn("Failed to load {}", ☃);
         this.func_154348_b();
      } finally {
         IOUtils.closeQuietly(☃);
      }

      return ☃x;
   }

   public boolean func_154346_a() {
      return this.field_154351_c;
   }

   public void func_154348_b() {
      if (!SharedConstants.field_206244_b) {
         FileOutputStream ☃ = null;

         try {
            Properties ☃x = new Properties();
            ☃ = new FileOutputStream(this.field_154350_b);
            ☃x.setProperty("eula", "false");
            ☃x.store(
               ☃, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula)."
            );
         } catch (Exception var6) {
            field_154349_a.warn("Failed to save {}", this.field_154350_b, var6);
         } finally {
            IOUtils.closeQuietly(☃);
         }
      }
   }
}
