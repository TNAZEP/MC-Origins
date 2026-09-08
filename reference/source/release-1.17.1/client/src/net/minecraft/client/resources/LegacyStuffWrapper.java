package net.minecraft.client.resources;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

public class LegacyStuffWrapper {
   @Deprecated
   public static int[] getPixels(ResourceManager var0, ResourceLocation var1) throws IOException {
      Resource â˜ƒ = â˜ƒ.getResource(â˜ƒ);

      int[] var4;
      try (NativeImage â˜ƒx = NativeImage.read(â˜ƒ.getInputStream())) {
         var4 = â˜ƒx.makePixelArray();
      } catch (Throwable var9) {
         if (â˜ƒ != null) {
            try {
               â˜ƒ.close();
            } catch (Throwable var6) {
               var9.addSuppressed(var6);
            }
         }

         throw var9;
      }

      if (â˜ƒ != null) {
         â˜ƒ.close();
      }

      return var4;
   }
}
