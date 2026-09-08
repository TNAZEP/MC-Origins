package net.minecraft.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class DefaultClientPackResources extends VanillaPackResources {
   private final AssetIndex assetIndex;

   public DefaultClientPackResources(PackMetadataSection var1, AssetIndex var2) {
      super(â˜ƒ, "minecraft", "realms");
      this.assetIndex = â˜ƒ;
   }

   @Nullable
   @Override
   protected InputStream getResourceAsStream(PackType var1, ResourceLocation var2) {
      if (â˜ƒ == PackType.CLIENT_RESOURCES) {
         File â˜ƒ = this.assetIndex.getFile(â˜ƒ);
         if (â˜ƒ != null && â˜ƒ.exists()) {
            try {
               return new FileInputStream(â˜ƒ);
            } catch (FileNotFoundException var5) {
            }
         }
      }

      return super.getResourceAsStream(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean hasResource(PackType var1, ResourceLocation var2) {
      if (â˜ƒ == PackType.CLIENT_RESOURCES) {
         File â˜ƒ = this.assetIndex.getFile(â˜ƒ);
         if (â˜ƒ != null && â˜ƒ.exists()) {
            return true;
         }
      }

      return super.hasResource(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   protected InputStream getResourceAsStream(String var1) {
      File â˜ƒ = this.assetIndex.getRootFile(â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.exists()) {
         try {
            return new FileInputStream(â˜ƒ);
         } catch (FileNotFoundException var4) {
         }
      }

      return super.getResourceAsStream(â˜ƒ);
   }

   @Override
   public Collection<ResourceLocation> getResources(PackType var1, String var2, String var3, int var4, Predicate<String> var5) {
      Collection<ResourceLocation> â˜ƒ = super.getResources(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.addAll(this.assetIndex.getFiles(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      return â˜ƒ;
   }
}
