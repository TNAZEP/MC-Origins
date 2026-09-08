package net.minecraft.client.renderer.texture;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture {
   private static final Logger field_174947_f = LogManager.getLogger();
   private final ResourceLocation field_174948_g;
   private final List<String> field_174949_h;
   private final List<EnumDyeColor> field_174950_i;

   public LayeredColorMaskTexture(ResourceLocation var1, List<String> var2, List<EnumDyeColor> var3) {
      this.field_174948_g = ☃;
      this.field_174949_h = ☃;
      this.field_174950_i = ☃;
   }

   @Override
   public void func_195413_a(IResourceManager var1) throws IOException {
      try {
         IResource ☃ = ☃.func_199002_a(this.field_174948_g);
         Throwable var3 = null;

         try (
            NativeImage ☃x = NativeImage.func_195713_a(☃.func_199027_b());
            NativeImage ☃xx = new NativeImage(☃x.func_195702_a(), ☃x.func_195714_b(), false);
         ) {
            ☃xx.func_195703_a(☃x);

            for(int ☃xxx = 0; ☃xxx < 17 && ☃xxx < this.field_174949_h.size() && ☃xxx < this.field_174950_i.size(); ++☃xxx) {
               String ☃xxxx = (String)this.field_174949_h.get(☃xxx);
               if (☃xxxx != null) {
                  IResource ☃xxxxx = ☃.func_199002_a(new ResourceLocation(☃xxxx));
                  Throwable var11 = null;

                  try (NativeImage ☃xxxxxx = NativeImage.func_195713_a(☃xxxxx.func_199027_b())) {
                     int ☃xxxxxxx = ((EnumDyeColor)this.field_174950_i.get(☃xxx)).func_196057_c();
                     if (☃xxxxxx.func_195702_a() == ☃xx.func_195702_a() && ☃xxxxxx.func_195714_b() == ☃xx.func_195714_b()) {
                        for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxxxxx.func_195714_b(); ++☃xxxxxxxx) {
                           for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxxxx.func_195702_a(); ++☃xxxxxxxxx) {
                              int ☃xxxxxxxxxx = ☃xxxxxx.func_195709_a(☃xxxxxxxxx, ☃xxxxxxxx);
                              if ((☃xxxxxxxxxx & 0xFF000000) != 0) {
                                 int ☃xxxxxxxxxxx = (☃xxxxxxxxxx & 0xFF) << 24 & 0xFF000000;
                                 int ☃xxxxxxxxxxxx = ☃x.func_195709_a(☃xxxxxxxxx, ☃xxxxxxxx);
                                 int ☃xxxxxxxxxxxxx = MathHelper.func_180188_d(☃xxxxxxxxxxxx, ☃xxxxxxx) & 16777215;
                                 ☃xx.func_195718_b(☃xxxxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxxx | ☃xxxxxxxxxxxxx);
                              }
                           }
                        }
                     }
                  } catch (Throwable var142) {
                     var11 = var142;
                     throw var142;
                  } finally {
                     if (☃xxxxx != null) {
                        if (var11 != null) {
                           try {
                              ☃xxxxx.close();
                           } catch (Throwable var138) {
                              var11.addSuppressed(var138);
                           }
                        } else {
                           ☃xxxxx.close();
                        }
                     }
                  }
               }
            }

            TextureUtil.func_110991_a(this.func_110552_b(), ☃xx.func_195702_a(), ☃xx.func_195714_b());
            GlStateManager.func_199297_b(3357, Float.MAX_VALUE);
            ☃xx.func_195697_a(0, 0, 0, false);
            GlStateManager.func_199297_b(3357, 0.0F);
         } catch (Throwable var148) {
            var3 = var148;
            throw var148;
         } finally {
            if (☃ != null) {
               if (var3 != null) {
                  try {
                     ☃.close();
                  } catch (Throwable var135) {
                     var3.addSuppressed(var135);
                  }
               } else {
                  ☃.close();
               }
            }
         }
      } catch (IOException var150) {
         field_174947_f.error("Couldn't load layered color mask image", var150);
      }
   }
}
