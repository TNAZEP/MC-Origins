package net.minecraft.server.packs.resources;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements Resource {
   private final String sourceName;
   private final ResourceLocation location;
   private final InputStream resourceStream;
   private final InputStream metadataStream;
   private boolean triedMetadata;
   private JsonObject metadata;

   public SimpleResource(String var1, ResourceLocation var2, InputStream var3, @Nullable InputStream var4) {
      this.sourceName = â˜ƒ;
      this.location = â˜ƒ;
      this.resourceStream = â˜ƒ;
      this.metadataStream = â˜ƒ;
   }

   @Override
   public ResourceLocation getLocation() {
      return this.location;
   }

   @Override
   public InputStream getInputStream() {
      return this.resourceStream;
   }

   @Override
   public boolean hasMetadata() {
      return this.metadataStream != null;
   }

   @Nullable
   @Override
   public <T> T getMetadata(MetadataSectionSerializer<T> var1) {
      if (!this.hasMetadata()) {
         return null;
      } else {
         if (this.metadata == null && !this.triedMetadata) {
            this.triedMetadata = true;
            BufferedReader â˜ƒ = null;

            try {
               â˜ƒ = new BufferedReader(new InputStreamReader(this.metadataStream, StandardCharsets.UTF_8));
               this.metadata = GsonHelper.parse(â˜ƒ);
            } finally {
               IOUtils.closeQuietly(â˜ƒ);
            }
         }

         if (this.metadata == null) {
            return null;
         } else {
            String â˜ƒ = â˜ƒ.getMetadataSectionName();
            return this.metadata.has(â˜ƒ) ? â˜ƒ.fromJson(GsonHelper.getAsJsonObject(this.metadata, â˜ƒ)) : null;
         }
      }
   }

   @Override
   public String getSourceName() {
      return this.sourceName;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource â˜ƒ = (SimpleResource)â˜ƒ;
         if (this.location != null ? this.location.equals(â˜ƒ.location) : â˜ƒ.location == null) {
            return this.sourceName != null ? this.sourceName.equals(â˜ƒ.sourceName) : â˜ƒ.sourceName == null;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int â˜ƒ = this.sourceName != null ? this.sourceName.hashCode() : 0;
      return 31 * â˜ƒ + (this.location != null ? this.location.hashCode() : 0);
   }

   public void close() throws IOException {
      this.resourceStream.close();
      if (this.metadataStream != null) {
         this.metadataStream.close();
      }
   }
}
