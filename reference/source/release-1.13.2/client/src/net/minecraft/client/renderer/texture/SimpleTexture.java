package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture {
   private static final Logger field_147639_c = LogManager.getLogger();
   protected final ResourceLocation field_110568_b;

   public SimpleTexture(ResourceLocation var1) {
      this.field_110568_b = ☃;
   }

   @Override
   public void func_195413_a(IResourceManager var1) throws IOException {
      IResource ☃ = ☃.func_199002_a(this.field_110568_b);
      Throwable var3 = null;

      try (NativeImage ☃x = NativeImage.func_195713_a(☃.func_199027_b())) {
         boolean ☃xx = false;
         boolean ☃xxx = false;
         if (☃.func_199030_c()) {
            try {
               TextureMetadataSection ☃xxxx = ☃.func_199028_a(TextureMetadataSection.field_195819_a);
               if (☃xxxx != null) {
                  ☃xx = ☃xxxx.func_110479_a();
                  ☃xxx = ☃xxxx.func_110480_b();
               }
            } catch (RuntimeException var32) {
               field_147639_c.warn("Failed reading metadata of: {}", this.field_110568_b, var32);
            }
         }

         this.func_195412_h();
         TextureUtil.func_180600_a(this.func_110552_b(), 0, ☃x.func_195702_a(), ☃x.func_195714_b());
         ☃x.func_195712_a(0, 0, 0, 0, 0, ☃x.func_195702_a(), ☃x.func_195714_b(), ☃xx, ☃xxx, false);
      } catch (Throwable var35) {
         var3 = var35;
         throw var35;
      } finally {
         if (☃ != null) {
            if (var3 != null) {
               try {
                  ☃.close();
               } catch (Throwable var30) {
                  var3.addSuppressed(var30);
               }
            } else {
               ☃.close();
            }
         }
      }
   }
}
