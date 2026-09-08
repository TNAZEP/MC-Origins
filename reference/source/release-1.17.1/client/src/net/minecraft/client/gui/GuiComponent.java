package net.minecraft.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public abstract class GuiComponent {
   public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
   private int blitOffset;

   protected void hLine(PoseStack var1, int var2, int var3, int var4, int var5) {
      if (â˜ƒ < â˜ƒ) {
         int â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
      }

      fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, â˜ƒ);
   }

   protected void vLine(PoseStack var1, int var2, int var3, int var4, int var5) {
      if (â˜ƒ < â˜ƒ) {
         int â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
      }

      fill(â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, â˜ƒ, â˜ƒ);
   }

   public static void fill(PoseStack var0, int var1, int var2, int var3, int var4, int var5) {
      innerFill(â˜ƒ.last().pose(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void innerFill(Matrix4f var0, int var1, int var2, int var3, int var4, int var5) {
      if (â˜ƒ < â˜ƒ) {
         int â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
      }

      if (â˜ƒ < â˜ƒ) {
         int â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
         â˜ƒ = â˜ƒ;
      }

      float â˜ƒ = (float)(â˜ƒ >> 24 & 0xFF) / 255.0F;
      float â˜ƒx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
      float â˜ƒxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
      float â˜ƒxxx = (float)(â˜ƒ & 0xFF) / 255.0F;
      BufferBuilder â˜ƒxxxx = Tesselator.getInstance().getBuilder();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      â˜ƒxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      â˜ƒxxxx.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.0F).color(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ).endVertex();
      â˜ƒxxxx.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.0F).color(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ).endVertex();
      â˜ƒxxxx.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.0F).color(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ).endVertex();
      â˜ƒxxxx.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.0F).color(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ).endVertex();
      â˜ƒxxxx.end();
      BufferUploader.end(â˜ƒxxxx);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   protected void fillGradient(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      fillGradient(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.blitOffset);
   }

   protected static void fillGradient(PoseStack var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      RenderSystem.disableTexture();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      fillGradient(â˜ƒ.last().pose(), â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.end();
      RenderSystem.disableBlend();
      RenderSystem.enableTexture();
   }

   protected static void fillGradient(Matrix4f var0, BufferBuilder var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      float â˜ƒ = (float)(â˜ƒ >> 24 & 0xFF) / 255.0F;
      float â˜ƒx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
      float â˜ƒxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
      float â˜ƒxxx = (float)(â˜ƒ & 0xFF) / 255.0F;
      float â˜ƒxxxx = (float)(â˜ƒ >> 24 & 0xFF) / 255.0F;
      float â˜ƒxxxxx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
      float â˜ƒxxxxxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
      float â˜ƒxxxxxxx = (float)(â˜ƒ & 0xFF) / 255.0F;
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).color(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).color(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).color(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).color(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxx).endVertex();
   }

   public static void drawCenteredString(PoseStack var0, Font var1, String var2, int var3, int var4, int var5) {
      â˜ƒ.drawShadow(â˜ƒ, â˜ƒ, (float)(â˜ƒ - â˜ƒ.width(â˜ƒ) / 2), (float)â˜ƒ, â˜ƒ);
   }

   public static void drawCenteredString(PoseStack var0, Font var1, Component var2, int var3, int var4, int var5) {
      FormattedCharSequence â˜ƒ = â˜ƒ.getVisualOrderText();
      â˜ƒ.drawShadow(â˜ƒ, â˜ƒ, (float)(â˜ƒ - â˜ƒ.width(â˜ƒ) / 2), (float)â˜ƒ, â˜ƒ);
   }

   public static void drawCenteredString(PoseStack var0, Font var1, FormattedCharSequence var2, int var3, int var4, int var5) {
      â˜ƒ.drawShadow(â˜ƒ, â˜ƒ, (float)(â˜ƒ - â˜ƒ.width(â˜ƒ) / 2), (float)â˜ƒ, â˜ƒ);
   }

   public static void drawString(PoseStack var0, Font var1, String var2, int var3, int var4, int var5) {
      â˜ƒ.drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
   }

   public static void drawString(PoseStack var0, Font var1, FormattedCharSequence var2, int var3, int var4, int var5) {
      â˜ƒ.drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
   }

   public static void drawString(PoseStack var0, Font var1, Component var2, int var3, int var4, int var5) {
      â˜ƒ.drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ);
   }

   public void blitOutlineBlack(int var1, int var2, BiConsumer<Integer, Integer> var3) {
      RenderSystem.blendFuncSeparate(
         GlStateManager.SourceFactor.ZERO,
         GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
         GlStateManager.SourceFactor.SRC_ALPHA,
         GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
      );
      â˜ƒ.accept(â˜ƒ + 1, â˜ƒ);
      â˜ƒ.accept(â˜ƒ - 1, â˜ƒ);
      â˜ƒ.accept(â˜ƒ, â˜ƒ + 1);
      â˜ƒ.accept(â˜ƒ, â˜ƒ - 1);
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      â˜ƒ.accept(â˜ƒ, â˜ƒ);
   }

   public static void blit(PoseStack var0, int var1, int var2, int var3, int var4, int var5, TextureAtlasSprite var6) {
      innerBlit(â˜ƒ.last().pose(), â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ.getU0(), â˜ƒ.getU1(), â˜ƒ.getV0(), â˜ƒ.getV1());
   }

   public void blit(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      blit(â˜ƒ, â˜ƒ, â˜ƒ, this.blitOffset, (float)â˜ƒ, (float)â˜ƒ, â˜ƒ, â˜ƒ, 256, 256);
   }

   public static void blit(PoseStack var0, int var1, int var2, int var3, float var4, float var5, int var6, int var7, int var8, int var9) {
      innerBlit(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void blit(PoseStack var0, int var1, int var2, int var3, int var4, float var5, float var6, int var7, int var8, int var9, int var10) {
      innerBlit(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ, 0, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void blit(PoseStack var0, int var1, int var2, float var3, float var4, int var5, int var6, int var7, int var8) {
      blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void innerBlit(
      PoseStack var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8, float var9, int var10, int var11
   ) {
      innerBlit(
         â˜ƒ.last().pose(),
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         (â˜ƒ + 0.0F) / (float)â˜ƒ,
         (â˜ƒ + (float)â˜ƒ) / (float)â˜ƒ,
         (â˜ƒ + 0.0F) / (float)â˜ƒ,
         (â˜ƒ + (float)â˜ƒ) / (float)â˜ƒ
      );
   }

   private static void innerBlit(Matrix4f var0, int var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, float var9) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      BufferBuilder â˜ƒ = Tesselator.getInstance().getBuilder();
      â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).uv(â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).uv(â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).uv(â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ).uv(â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.end();
      BufferUploader.end(â˜ƒ);
   }

   public int getBlitOffset() {
      return this.blitOffset;
   }

   public void setBlitOffset(int var1) {
      this.blitOffset = â˜ƒ;
   }
}
