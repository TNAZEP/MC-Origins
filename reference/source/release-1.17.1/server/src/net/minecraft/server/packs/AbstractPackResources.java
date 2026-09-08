package net.minecraft.server.packs;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPackResources implements PackResources {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final File file;

   public AbstractPackResources(File var1) {
      this.file = â˜ƒ;
   }

   private static String getPathFromLocation(PackType var0, ResourceLocation var1) {
      return String.format("%s/%s/%s", â˜ƒ.getDirectory(), â˜ƒ.getNamespace(), â˜ƒ.getPath());
   }

   protected static String getRelativePath(File var0, File var1) {
      return â˜ƒ.toURI().relativize(â˜ƒ.toURI()).getPath();
   }

   @Override
   public InputStream getResource(PackType var1, ResourceLocation var2) throws IOException {
      return this.getResource(getPathFromLocation(â˜ƒ, â˜ƒ));
   }

   @Override
   public boolean hasResource(PackType var1, ResourceLocation var2) {
      return this.hasResource(getPathFromLocation(â˜ƒ, â˜ƒ));
   }

   protected abstract InputStream getResource(String var1) throws IOException;

   @Override
   public InputStream getRootResource(String var1) throws IOException {
      if (!â˜ƒ.contains("/") && !â˜ƒ.contains("\\")) {
         return this.getResource(â˜ƒ);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   protected abstract boolean hasResource(String var1);

   protected void logWarning(String var1) {
      LOGGER.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", â˜ƒ, this.file);
   }

   @Nullable
   @Override
   public <T> T getMetadataSection(MetadataSectionSerializer<T> var1) throws IOException {
      InputStream â˜ƒ = this.getResource("pack.mcmeta");

      Object var3;
      try {
         var3 = getMetadataFromStream(â˜ƒ, â˜ƒ);
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

      return (T)var3;
   }

   @Nullable
   public static <T> T getMetadataFromStream(MetadataSectionSerializer<T> var0, InputStream var1) {
      JsonObject â˜ƒ;
      try {
         BufferedReader â˜ƒ = new BufferedReader(new InputStreamReader(â˜ƒ, StandardCharsets.UTF_8));

         try {
            â˜ƒ = GsonHelper.parse(â˜ƒ);
         } catch (Throwable var8) {
            try {
               â˜ƒ.close();
            } catch (Throwable var6) {
               var8.addSuppressed(var6);
            }

            throw var8;
         }

         â˜ƒ.close();
      } catch (JsonParseException | IOException var9) {
         LOGGER.error("Couldn't load {} metadata", â˜ƒ.getMetadataSectionName(), var9);
         return null;
      }

      if (!â˜ƒ.has(â˜ƒ.getMetadataSectionName())) {
         return null;
      } else {
         try {
            return â˜ƒ.fromJson(GsonHelper.getAsJsonObject(â˜ƒ, â˜ƒ.getMetadataSectionName()));
         } catch (JsonParseException var7) {
            LOGGER.error("Couldn't load {} metadata", â˜ƒ.getMetadataSectionName(), var7);
            return null;
         }
      }
   }

   @Override
   public String getName() {
      return this.file.getName();
   }
}
