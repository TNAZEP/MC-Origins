package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WinScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation LOGO_LOCATION = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation EDITION_LOCATION = new ResourceLocation("textures/gui/title/edition.png");
   private static final ResourceLocation VIGNETTE_LOCATION = new ResourceLocation("textures/misc/vignette.png");
   private static final Component SECTION_HEADING = new TextComponent("============").withStyle(ChatFormatting.WHITE);
   private static final String NAME_PREFIX = "           ";
   private static final String OBFUSCATE_TOKEN = "" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + ChatFormatting.GREEN + ChatFormatting.AQUA;
   private static final int LOGO_WIDTH = 274;
   private static final float SPEEDUP_FACTOR = 5.0F;
   private static final float SPEEDUP_FACTOR_FAST = 15.0F;
   private final boolean poem;
   private final Runnable onFinished;
   private float scroll;
   private List<FormattedCharSequence> lines;
   private IntSet centeredLines;
   private int totalScrollLength;
   private boolean speedupActive;
   private final IntSet speedupModifiers = new IntOpenHashSet();
   private float scrollSpeed;
   private final float unmodifiedScrollSpeed;

   public WinScreen(boolean var1, Runnable var2) {
      super(NarratorChatListener.NO_TITLE);
      this.poem = â˜ƒ;
      this.onFinished = â˜ƒ;
      if (!â˜ƒ) {
         this.unmodifiedScrollSpeed = 0.75F;
      } else {
         this.unmodifiedScrollSpeed = 0.5F;
      }

      this.scrollSpeed = this.unmodifiedScrollSpeed;
   }

   private float calculateScrollSpeed() {
      return this.speedupActive ? this.unmodifiedScrollSpeed * (5.0F + (float)this.speedupModifiers.size() * 15.0F) : this.unmodifiedScrollSpeed;
   }

   @Override
   public void tick() {
      this.minecraft.getMusicManager().tick();
      this.minecraft.getSoundManager().tick(false);
      float â˜ƒ = (float)(this.totalScrollLength + this.height + this.height + 24);
      if (this.scroll > â˜ƒ) {
         this.respawn();
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 341 || â˜ƒ == 345) {
         this.speedupModifiers.add(â˜ƒ);
      } else if (â˜ƒ == 32) {
         this.speedupActive = true;
      }

      this.scrollSpeed = this.calculateScrollSpeed();
      return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean keyReleased(int var1, int var2, int var3) {
      if (â˜ƒ == 32) {
         this.speedupActive = false;
      } else if (â˜ƒ == 341 || â˜ƒ == 345) {
         this.speedupModifiers.remove(â˜ƒ);
      }

      this.scrollSpeed = this.calculateScrollSpeed();
      return super.keyReleased(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onClose() {
      this.respawn();
   }

   private void respawn() {
      this.onFinished.run();
      this.minecraft.setScreen(null);
   }

   @Override
   protected void init() {
      if (this.lines == null) {
         this.lines = Lists.<FormattedCharSequence>newArrayList();
         this.centeredLines = new IntOpenHashSet();
         Resource â˜ƒ = null;

         try {
            if (this.poem) {
               â˜ƒ = this.minecraft.getResourceManager().getResource(new ResourceLocation("texts/end.txt"));
               InputStream â˜ƒx = â˜ƒ.getInputStream();
               BufferedReader â˜ƒxx = new BufferedReader(new InputStreamReader(â˜ƒx, StandardCharsets.UTF_8));
               Random â˜ƒxxx = new Random(8124371L);

               String â˜ƒ;
               while((â˜ƒ = â˜ƒxx.readLine()) != null) {
                  int â˜ƒ;
                  String â˜ƒ;
                  String â˜ƒ;
                  for(â˜ƒ = â˜ƒ.replaceAll("PLAYERNAME", this.minecraft.getUser().getName());
                     (â˜ƒ = â˜ƒ.indexOf(OBFUSCATE_TOKEN)) != -1;
                     â˜ƒ = â˜ƒ + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, â˜ƒxxx.nextInt(4) + 3) + â˜ƒ
                  ) {
                     â˜ƒ = â˜ƒ.substring(0, â˜ƒ);
                     â˜ƒ = â˜ƒ.substring(â˜ƒ + OBFUSCATE_TOKEN.length());
                  }

                  this.addPoemLines(â˜ƒ);
                  this.addEmptyLine();
               }

               â˜ƒx.close();

               for(int â˜ƒxxxx = 0; â˜ƒxxxx < 8; ++â˜ƒxxxx) {
                  this.addEmptyLine();
               }
            }

            â˜ƒ = this.minecraft.getResourceManager().getResource(new ResourceLocation("texts/credits.json"));
            JsonArray â˜ƒx = GsonHelper.parseArray(new InputStreamReader(â˜ƒ.getInputStream(), StandardCharsets.UTF_8));

            for(JsonElement â˜ƒxx : â˜ƒx.getAsJsonArray()) {
               JsonObject â˜ƒxxx = â˜ƒxx.getAsJsonObject();
               String â˜ƒxxxx = â˜ƒxxx.get("section").getAsString();
               this.addCreditsLine(SECTION_HEADING, true);
               this.addCreditsLine(new TextComponent(â˜ƒxxxx).withStyle(ChatFormatting.YELLOW), true);
               this.addCreditsLine(SECTION_HEADING, true);
               this.addEmptyLine();
               this.addEmptyLine();

               for(JsonElement â˜ƒxxxxx : â˜ƒxxx.getAsJsonArray("titles")) {
                  JsonObject â˜ƒxxxxxx = â˜ƒxxxxx.getAsJsonObject();
                  String â˜ƒxxxxxxx = â˜ƒxxxxxx.get("title").getAsString();
                  JsonArray â˜ƒxxxxxxxx = â˜ƒxxxxxx.getAsJsonArray("names");
                  this.addCreditsLine(new TextComponent(â˜ƒxxxxxxx).withStyle(ChatFormatting.GRAY), false);

                  for(JsonElement â˜ƒxxxxxxxxx : â˜ƒxxxxxxxx) {
                     String â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.getAsString();
                     this.addCreditsLine(new TextComponent("           ").append(â˜ƒxxxxxxxxxx).withStyle(ChatFormatting.WHITE), false);
                  }

                  this.addEmptyLine();
                  this.addEmptyLine();
               }
            }

            this.totalScrollLength = this.lines.size() * 12;
         } catch (Exception var20) {
            LOGGER.error("Couldn't load credits", var20);
         } finally {
            IOUtils.closeQuietly(â˜ƒ);
         }
      }
   }

   private void addEmptyLine() {
      this.lines.add(FormattedCharSequence.EMPTY);
   }

   private void addPoemLines(String var1) {
      this.lines.addAll(this.minecraft.font.split(new TextComponent(â˜ƒ), 274));
   }

   private void addCreditsLine(Component var1, boolean var2) {
      if (â˜ƒ) {
         this.centeredLines.add(this.lines.size());
      }

      this.lines.add(â˜ƒ.getVisualOrderText());
   }

   private void renderBg() {
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
      int â˜ƒ = this.width;
      float â˜ƒx = -this.scroll * 0.5F;
      float â˜ƒxx = (float)this.height - 0.5F * this.scroll;
      float â˜ƒxxx = 0.015625F;
      float â˜ƒxxxx = this.scroll / this.unmodifiedScrollSpeed;
      float â˜ƒxxxxx = â˜ƒxxxx * 0.02F;
      float â˜ƒxxxxxx = (float)(this.totalScrollLength + this.height + this.height + 24) / this.unmodifiedScrollSpeed;
      float â˜ƒxxxxxxx = (â˜ƒxxxxxx - 20.0F - â˜ƒxxxx) * 0.005F;
      if (â˜ƒxxxxxxx < â˜ƒxxxxx) {
         â˜ƒxxxxx = â˜ƒxxxxxxx;
      }

      if (â˜ƒxxxxx > 1.0F) {
         â˜ƒxxxxx = 1.0F;
      }

      â˜ƒxxxxx *= â˜ƒxxxxx;
      â˜ƒxxxxx = â˜ƒxxxxx * 96.0F / 255.0F;
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      â˜ƒx.vertex(0.0, (double)this.height, (double)this.getBlitOffset()).uv(0.0F, â˜ƒx * 0.015625F).color(â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx, 1.0F).endVertex();
      â˜ƒx.vertex((double)â˜ƒ, (double)this.height, (double)this.getBlitOffset())
         .uv((float)â˜ƒ * 0.015625F, â˜ƒx * 0.015625F)
         .color(â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx, 1.0F)
         .endVertex();
      â˜ƒx.vertex((double)â˜ƒ, 0.0, (double)this.getBlitOffset())
         .uv((float)â˜ƒ * 0.015625F, â˜ƒxx * 0.015625F)
         .color(â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx, 1.0F)
         .endVertex();
      â˜ƒx.vertex(0.0, 0.0, (double)this.getBlitOffset()).uv(0.0F, â˜ƒxx * 0.015625F).color(â˜ƒxxxxx, â˜ƒxxxxx, â˜ƒxxxxx, 1.0F).endVertex();
      â˜ƒ.end();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.scroll += â˜ƒ * this.scrollSpeed;
      this.renderBg();
      int â˜ƒ = this.width / 2 - 137;
      int â˜ƒx = this.height + 50;
      float â˜ƒxx = -this.scroll;
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, (double)â˜ƒxx, 0.0);
      RenderSystem.setShaderTexture(0, LOGO_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      this.blitOutlineBlack(â˜ƒ, â˜ƒx, (var2x, var3x) -> {
         this.blit(â˜ƒ, var2x + 0, var3x, 0, 0, 155, 44);
         this.blit(â˜ƒ, var2x + 155, var3x, 0, 45, 155, 44);
      });
      RenderSystem.disableBlend();
      RenderSystem.setShaderTexture(0, EDITION_LOCATION);
      blit(â˜ƒ, â˜ƒ + 88, â˜ƒx + 37, 0.0F, 0.0F, 98, 14, 128, 16);
      int â˜ƒxxx = â˜ƒx + 100;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < this.lines.size(); ++â˜ƒxxxx) {
         if (â˜ƒxxxx == this.lines.size() - 1) {
            float â˜ƒxxxxx = (float)â˜ƒxxx + â˜ƒxx - (float)(this.height / 2 - 6);
            if (â˜ƒxxxxx < 0.0F) {
               â˜ƒ.translate(0.0, (double)(-â˜ƒxxxxx), 0.0);
            }
         }

         if ((float)â˜ƒxxx + â˜ƒxx + 12.0F + 8.0F > 0.0F && (float)â˜ƒxxx + â˜ƒxx < (float)this.height) {
            FormattedCharSequence â˜ƒxxxxx = (FormattedCharSequence)this.lines.get(â˜ƒxxxx);
            if (this.centeredLines.contains(â˜ƒxxxx)) {
               this.font.drawShadow(â˜ƒ, â˜ƒxxxxx, (float)(â˜ƒ + (274 - this.font.width(â˜ƒxxxxx)) / 2), (float)â˜ƒxxx, 16777215);
            } else {
               this.font.drawShadow(â˜ƒ, â˜ƒxxxxx, (float)â˜ƒ, (float)â˜ƒxxx, 16777215);
            }
         }

         â˜ƒxxx += 12;
      }

      â˜ƒ.popPose();
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      RenderSystem.setShaderTexture(0, VIGNETTE_LOCATION);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
      int â˜ƒxxxx = this.width;
      int â˜ƒxxxxx = this.height;
      Tesselator â˜ƒxxxxxx = Tesselator.getInstance();
      BufferBuilder â˜ƒxxxxxxx = â˜ƒxxxxxx.getBuilder();
      â˜ƒxxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      â˜ƒxxxxxxx.vertex(0.0, (double)â˜ƒxxxxx, (double)this.getBlitOffset()).uv(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      â˜ƒxxxxxxx.vertex((double)â˜ƒxxxx, (double)â˜ƒxxxxx, (double)this.getBlitOffset()).uv(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      â˜ƒxxxxxxx.vertex((double)â˜ƒxxxx, 0.0, (double)this.getBlitOffset()).uv(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      â˜ƒxxxxxxx.vertex(0.0, 0.0, (double)this.getBlitOffset()).uv(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      â˜ƒxxxxxx.end();
      RenderSystem.disableBlend();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
