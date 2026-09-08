package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GrassColors;

public class GrassColorReloadListener implements IResourceManagerReloadListener {
   private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");

   @Override
   public void func_195410_a(IResourceManager var1) {
      try {
         GrassColors.func_77479_a(TextureUtil.func_195725_a(☃, field_130078_a));
      } catch (IOException var3) {
      }
   }
}
