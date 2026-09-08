package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredTexture extends AbstractTexture {
   private static final Logger field_147638_c = LogManager.getLogger();
   public final List<String> field_110567_b;

   public LayeredTexture(String... var1) {
      this.field_110567_b = Lists.newArrayList(☃);
      if (this.field_110567_b.isEmpty()) {
         throw new IllegalStateException("Layered texture with no layers.");
      }
   }

   @Override
   public void func_195413_a(IResourceManager var1) throws IOException {
      Iterator<String> ☃ = this.field_110567_b.iterator();
      String ☃x = (String)☃.next();

      try {
         IResource ☃xx = ☃.func_199002_a(new ResourceLocation(☃x));
         Throwable var5 = null;

         try (NativeImage ☃xxx = NativeImage.func_195713_a(☃xx.func_199027_b())) {
            while(true) {
               if (!☃.hasNext()) {
                  TextureUtil.func_110991_a(this.func_110552_b(), ☃xxx.func_195702_a(), ☃xxx.func_195714_b());
                  ☃xxx.func_195697_a(0, 0, 0, false);
                  break;
               }

               String ☃xxxx = (String)☃.next();
               if (☃xxxx != null) {
                  IResource ☃xxxxx = ☃.func_199002_a(new ResourceLocation(☃xxxx));
                  Throwable var10 = null;

                  try (NativeImage ☃xxxxxx = NativeImage.func_195713_a(☃xxxxx.func_199027_b())) {
                     for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxxx.func_195714_b(); ++☃xxxxxxx) {
                        for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxxxxx.func_195702_a(); ++☃xxxxxxxx) {
                           ☃xxx.func_195718_b(☃xxxxxxxx, ☃xxxxxxx, ☃xxxxxx.func_195709_a(☃xxxxxxxx, ☃xxxxxxx));
                        }
                     }
                  } catch (Throwable var91) {
                     var10 = var91;
                     throw var91;
                  } finally {
                     if (☃xxxxx != null) {
                        if (var10 != null) {
                           try {
                              ☃xxxxx.close();
                           } catch (Throwable var87) {
                              var10.addSuppressed(var87);
                           }
                        } else {
                           ☃xxxxx.close();
                        }
                     }
                  }
               }
            }
         } catch (Throwable var95) {
            var5 = var95;
            throw var95;
         } finally {
            if (☃xx != null) {
               if (var5 != null) {
                  try {
                     ☃xx.close();
                  } catch (Throwable var85) {
                     var5.addSuppressed(var85);
                  }
               } else {
                  ☃xx.close();
               }
            }
         }
      } catch (IOException var97) {
         field_147638_c.error("Couldn't load layered image", var97);
      }
   }
}
