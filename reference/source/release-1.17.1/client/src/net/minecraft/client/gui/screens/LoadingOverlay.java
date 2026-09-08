package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class LoadingOverlay extends Overlay {
   static final ResourceLocation MOJANG_STUDIOS_LOGO_LOCATION = new ResourceLocation("textures/gui/title/mojangstudios.png");
   private static final int LOGO_BACKGROUND_COLOR = FastColor.ARGB32.color(255, 239, 50, 61);
   private static final int LOGO_BACKGROUND_COLOR_DARK = FastColor.ARGB32.color(255, 0, 0, 0);
   private static final IntSupplier BRAND_BACKGROUND = () -> Minecraft.getInstance().options.darkMojangStudiosBackground
         ? LOGO_BACKGROUND_COLOR_DARK
         : LOGO_BACKGROUND_COLOR;
   private static final int LOGO_SCALE = 240;
   private static final float LOGO_QUARTER_FLOAT = 60.0F;
   private static final int LOGO_QUARTER = 60;
   private static final int LOGO_HALF = 120;
   private static final float LOGO_OVERLAP = 0.0625F;
   private static final float SMOOTHING = 0.95F;
   public static final long FADE_OUT_TIME = 1000L;
   public static final long FADE_IN_TIME = 500L;
   private final Minecraft minecraft;
   private final ReloadInstance reload;
   private final Consumer<Optional<Throwable>> onFinish;
   private final boolean fadeIn;
   private float currentProgress;
   private long fadeOutStart = -1L;
   private long fadeInStart = -1L;

   public LoadingOverlay(Minecraft var1, ReloadInstance var2, Consumer<Optional<Throwable>> var3, boolean var4) {
      this.minecraft = â˜ƒ;
      this.reload = â˜ƒ;
      this.onFinish = â˜ƒ;
      this.fadeIn = â˜ƒ;
   }

   public static void registerTextures(Minecraft var0) {
      â˜ƒ.getTextureManager().register(MOJANG_STUDIOS_LOGO_LOCATION, new LoadingOverlay.LogoTexture());
   }

   private static int replaceAlpha(int var0, int var1) {
      return â˜ƒ & 16777215 | â˜ƒ << 24;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      int â˜ƒ = this.minecraft.getWindow().getGuiScaledWidth();
      int â˜ƒx = this.minecraft.getWindow().getGuiScaledHeight();
      long â˜ƒxx = Util.getMillis();
      if (this.fadeIn && this.fadeInStart == -1L) {
         this.fadeInStart = â˜ƒxx;
      }

      float â˜ƒx = this.fadeOutStart > -1L ? (float)(â˜ƒxx - this.fadeOutStart) / 1000.0F : -1.0F;
      float â˜ƒxx = this.fadeInStart > -1L ? (float)(â˜ƒxx - this.fadeInStart) / 500.0F : -1.0F;
      float â˜ƒ;
      if (â˜ƒx >= 1.0F) {
         if (this.minecraft.screen != null) {
            this.minecraft.screen.render(â˜ƒ, 0, 0, â˜ƒ);
         }

         int â˜ƒxxx = Mth.ceil((1.0F - Mth.clamp(â˜ƒx - 1.0F, 0.0F, 1.0F)) * 255.0F);
         fill(â˜ƒ, 0, 0, â˜ƒ, â˜ƒx, replaceAlpha(BRAND_BACKGROUND.getAsInt(), â˜ƒxxx));
         â˜ƒ = 1.0F - Mth.clamp(â˜ƒx - 1.0F, 0.0F, 1.0F);
      } else if (this.fadeIn) {
         if (this.minecraft.screen != null && â˜ƒxx < 1.0F) {
            this.minecraft.screen.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         int â˜ƒ = Mth.ceil(Mth.clamp((double)â˜ƒxx, 0.15, 1.0) * 255.0);
         fill(â˜ƒ, 0, 0, â˜ƒ, â˜ƒx, replaceAlpha(BRAND_BACKGROUND.getAsInt(), â˜ƒ));
         â˜ƒ = Mth.clamp(â˜ƒxx, 0.0F, 1.0F);
      } else {
         int â˜ƒ = BRAND_BACKGROUND.getAsInt();
         float â˜ƒx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
         float â˜ƒxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
         float â˜ƒxxx = (float)(â˜ƒ & 0xFF) / 255.0F;
         GlStateManager._clearColor(â˜ƒx, â˜ƒxx, â˜ƒxxx, 1.0F);
         GlStateManager._clear(16384, Minecraft.ON_OSX);
         â˜ƒ = 1.0F;
      }

      int â˜ƒ = (int)((double)this.minecraft.getWindow().getGuiScaledWidth() * 0.5);
      int â˜ƒx = (int)((double)this.minecraft.getWindow().getGuiScaledHeight() * 0.5);
      double â˜ƒxx = Math.min((double)this.minecraft.getWindow().getGuiScaledWidth() * 0.75, (double)this.minecraft.getWindow().getGuiScaledHeight()) * 0.25;
      int â˜ƒxxx = (int)(â˜ƒxx * 0.5);
      double â˜ƒxxxx = â˜ƒxx * 4.0;
      int â˜ƒxxxxx = (int)(â˜ƒxxxx * 0.5);
      RenderSystem.setShaderTexture(0, MOJANG_STUDIOS_LOGO_LOCATION);
      RenderSystem.enableBlend();
      RenderSystem.blendEquation(32774);
      RenderSystem.blendFunc(770, 1);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒ);
      blit(â˜ƒ, â˜ƒ - â˜ƒxxxxx, â˜ƒx - â˜ƒxxx, â˜ƒxxxxx, (int)â˜ƒxx, -0.0625F, 0.0F, 120, 60, 120, 120);
      blit(â˜ƒ, â˜ƒ, â˜ƒx - â˜ƒxxx, â˜ƒxxxxx, (int)â˜ƒxx, 0.0625F, 60.0F, 120, 60, 120, 120);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      int â˜ƒxxxxxx = (int)((double)this.minecraft.getWindow().getGuiScaledHeight() * 0.8325);
      float â˜ƒxxxxxxx = this.reload.getActualProgress();
      this.currentProgress = Mth.clamp(this.currentProgress * 0.95F + â˜ƒxxxxxxx * 0.050000012F, 0.0F, 1.0F);
      if (â˜ƒx < 1.0F) {
         this.drawProgressBar(â˜ƒ, â˜ƒ / 2 - â˜ƒxxxxx, â˜ƒxxxxxx - 5, â˜ƒ / 2 + â˜ƒxxxxx, â˜ƒxxxxxx + 5, 1.0F - Mth.clamp(â˜ƒx, 0.0F, 1.0F));
      }

      if (â˜ƒx >= 2.0F) {
         this.minecraft.setOverlay(null);
      }

      if (this.fadeOutStart == -1L && this.reload.isDone() && (!this.fadeIn || â˜ƒxx >= 2.0F)) {
         try {
            this.reload.checkExceptions();
            this.onFinish.accept(Optional.empty());
         } catch (Throwable var23) {
            this.onFinish.accept(Optional.of(var23));
         }

         this.fadeOutStart = Util.getMillis();
         if (this.minecraft.screen != null) {
            this.minecraft.screen.init(this.minecraft, this.minecraft.getWindow().getGuiScaledWidth(), this.minecraft.getWindow().getGuiScaledHeight());
         }
      }
   }

   private void drawProgressBar(PoseStack var1, int var2, int var3, int var4, int var5, float var6) {
      int â˜ƒ = Mth.ceil((float)(â˜ƒ - â˜ƒ - 2) * this.currentProgress);
      int â˜ƒx = Math.round(â˜ƒ * 255.0F);
      int â˜ƒxx = FastColor.ARGB32.color(â˜ƒx, 255, 255, 255);
      fill(â˜ƒ, â˜ƒ + 2, â˜ƒ + 2, â˜ƒ + â˜ƒ, â˜ƒ - 2, â˜ƒxx);
      fill(â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ - 1, â˜ƒ + 1, â˜ƒxx);
      fill(â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ - 1, â˜ƒ - 1, â˜ƒxx);
      fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒxx);
      fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ - 1, â˜ƒ, â˜ƒxx);
   }

   @Override
   public boolean isPauseScreen() {
      return true;
   }

   static class LogoTexture extends SimpleTexture {
      public LogoTexture() {
         super(LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION);
      }

      @Override
      protected SimpleTexture.TextureImage getTextureImage(ResourceManager var1) {
         Minecraft â˜ƒ = Minecraft.getInstance();
         VanillaPackResources â˜ƒx = â˜ƒ.getClientPackSource().getVanillaPack();

         try {
            InputStream â˜ƒxx = â˜ƒx.getResource(PackType.CLIENT_RESOURCES, LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION);

            SimpleTexture.TextureImage var5;
            try {
               var5 = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), NativeImage.read(â˜ƒxx));
            } catch (Throwable var8) {
               if (â˜ƒxx != null) {
                  try {
                     â˜ƒxx.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (â˜ƒxx != null) {
               â˜ƒxx.close();
            }

            return var5;
         } catch (IOException var9) {
            return new SimpleTexture.TextureImage(var9);
         }
      }
   }
}
