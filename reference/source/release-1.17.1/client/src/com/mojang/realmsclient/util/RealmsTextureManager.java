package com.mojang.realmsclient.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsTextureManager {
   private static final Map<String, RealmsTextureManager.RealmsTexture> TEXTURES = Maps.newHashMap();
   static final Map<String, Boolean> SKIN_FETCH_STATUS = Maps.newHashMap();
   static final Map<String, String> FETCHED_SKINS = Maps.newHashMap();
   static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation TEMPLATE_ICON_LOCATION = new ResourceLocation("textures/gui/presets/isles.png");

   public static void bindWorldTemplate(String var0, @Nullable String var1) {
      if (â˜ƒ == null) {
         RenderSystem.setShaderTexture(0, TEMPLATE_ICON_LOCATION);
      } else {
         int â˜ƒ = getTextureId(â˜ƒ, â˜ƒ);
         RenderSystem.setShaderTexture(0, â˜ƒ);
      }
   }

   public static void withBoundFace(String var0, Runnable var1) {
      bindFace(â˜ƒ);
      â˜ƒ.run();
   }

   private static void bindDefaultFace(UUID var0) {
      RenderSystem.setShaderTexture(0, DefaultPlayerSkin.getDefaultSkin(â˜ƒ));
   }

   private static void bindFace(final String var0) {
      UUID â˜ƒ = UUIDTypeAdapter.fromString(â˜ƒ);
      if (TEXTURES.containsKey(â˜ƒ)) {
         int â˜ƒx = ((RealmsTextureManager.RealmsTexture)TEXTURES.get(â˜ƒ)).textureId;
         RenderSystem.setShaderTexture(0, â˜ƒx);
      } else if (SKIN_FETCH_STATUS.containsKey(â˜ƒ)) {
         if (!SKIN_FETCH_STATUS.get(â˜ƒ)) {
            bindDefaultFace(â˜ƒ);
         } else if (FETCHED_SKINS.containsKey(â˜ƒ)) {
            int â˜ƒ = getTextureId(â˜ƒ, (String)FETCHED_SKINS.get(â˜ƒ));
            RenderSystem.setShaderTexture(0, â˜ƒ);
         } else {
            bindDefaultFace(â˜ƒ);
         }
      } else {
         SKIN_FETCH_STATUS.put(â˜ƒ, false);
         bindDefaultFace(â˜ƒ);
         Thread â˜ƒ = new Thread("Realms Texture Downloader") {
            public void run() {
               Map<Type, MinecraftProfileTexture> â˜ƒ = RealmsUtil.getTextures(â˜ƒ);
               if (â˜ƒ.containsKey(Type.SKIN)) {
                  MinecraftProfileTexture â˜ƒx = (MinecraftProfileTexture)â˜ƒ.get(Type.SKIN);
                  String â˜ƒxx = â˜ƒx.getUrl();
                  HttpURLConnection â˜ƒxxx = null;
                  RealmsTextureManager.LOGGER.debug("Downloading http texture from {}", â˜ƒxx);

                  try {
                     try {
                        â˜ƒxxx = (HttpURLConnection)new URL(â˜ƒxx).openConnection(Minecraft.getInstance().getProxy());
                        â˜ƒxxx.setDoInput(true);
                        â˜ƒxxx.setDoOutput(false);
                        â˜ƒxxx.connect();
                        if (â˜ƒxxx.getResponseCode() / 100 != 2) {
                           RealmsTextureManager.SKIN_FETCH_STATUS.remove(â˜ƒ);
                           return;
                        }

                        BufferedImage â˜ƒ;
                        try {
                           â˜ƒ = ImageIO.read(â˜ƒxxx.getInputStream());
                        } catch (Exception var17) {
                           RealmsTextureManager.SKIN_FETCH_STATUS.remove(â˜ƒ);
                           return;
                        } finally {
                           IOUtils.closeQuietly(â˜ƒxxx.getInputStream());
                        }

                        â˜ƒ = new SkinProcessor().process(â˜ƒ);
                        ByteArrayOutputStream â˜ƒxxxx = new ByteArrayOutputStream();
                        ImageIO.write(â˜ƒ, "png", â˜ƒxxxx);
                        RealmsTextureManager.FETCHED_SKINS.put(â˜ƒ, new Base64().encodeToString(â˜ƒxxxx.toByteArray()));
                        RealmsTextureManager.SKIN_FETCH_STATUS.put(â˜ƒ, true);
                     } catch (Exception var19) {
                        RealmsTextureManager.LOGGER.error("Couldn't download http texture", var19);
                        RealmsTextureManager.SKIN_FETCH_STATUS.remove(â˜ƒ);
                     }
                  } finally {
                     if (â˜ƒxxx != null) {
                        â˜ƒxxx.disconnect();
                     }
                  }
               } else {
                  RealmsTextureManager.SKIN_FETCH_STATUS.put(â˜ƒ, true);
               }
            }
         };
         â˜ƒ.setDaemon(true);
         â˜ƒ.start();
      }
   }

   private static int getTextureId(String var0, String var1) {
      RealmsTextureManager.RealmsTexture â˜ƒ = (RealmsTextureManager.RealmsTexture)TEXTURES.get(â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.image.equals(â˜ƒ)) {
         return â˜ƒ.textureId;
      } else {
         int â˜ƒ;
         if (â˜ƒ != null) {
            â˜ƒ = â˜ƒ.textureId;
         } else {
            â˜ƒ = GlStateManager._genTexture();
         }

         IntBuffer â˜ƒ = null;
         int â˜ƒx = 0;
         int â˜ƒxx = 0;

         try {
            InputStream â˜ƒxxx = new ByteArrayInputStream(new Base64().decode(â˜ƒ));

            BufferedImage â˜ƒ;
            try {
               â˜ƒ = ImageIO.read(â˜ƒxxx);
            } finally {
               IOUtils.closeQuietly(â˜ƒxxx);
            }

            â˜ƒx = â˜ƒ.getWidth();
            â˜ƒxx = â˜ƒ.getHeight();
            int[] var9 = new int[â˜ƒx * â˜ƒxx];
            â˜ƒ.getRGB(0, 0, â˜ƒx, â˜ƒxx, var9, 0, â˜ƒx);
            â˜ƒ = ByteBuffer.allocateDirect(4 * â˜ƒx * â˜ƒxx).order(ByteOrder.nativeOrder()).asIntBuffer();
            â˜ƒ.put(var9);
            â˜ƒ.flip();
         } catch (IOException var13) {
            var13.printStackTrace();
         }

         RenderSystem.activeTexture(33984);
         RenderSystem.bindTextureForSetup(â˜ƒ);
         TextureUtil.initTexture(â˜ƒ, â˜ƒx, â˜ƒxx);
         TEXTURES.put(â˜ƒ, new RealmsTextureManager.RealmsTexture(â˜ƒ, â˜ƒ));
         return â˜ƒ;
      }
   }

   public static class RealmsTexture {
      final String image;
      final int textureId;

      public RealmsTexture(String var1, int var2) {
         this.image = â˜ƒ;
         this.textureId = â˜ƒ;
      }
   }
}
