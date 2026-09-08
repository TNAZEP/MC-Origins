package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;

public class ChunkBorderRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;

   public ChunkBorderRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      RenderSystem.enableDepthTest();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Entity â˜ƒ = this.minecraft.gameRenderer.getMainCamera().getEntity();
      Tesselator â˜ƒx = Tesselator.getInstance();
      BufferBuilder â˜ƒxx = â˜ƒx.getBuilder();
      double â˜ƒxxx = (double)this.minecraft.level.getMinBuildHeight() - â˜ƒ;
      double â˜ƒxxxx = (double)this.minecraft.level.getMaxBuildHeight() - â˜ƒ;
      RenderSystem.disableTexture();
      RenderSystem.disableBlend();
      ChunkPos â˜ƒxxxxx = â˜ƒ.chunkPosition();
      double â˜ƒxxxxxx = (double)â˜ƒxxxxx.getMinBlockX() - â˜ƒ;
      double â˜ƒxxxxxxx = (double)â˜ƒxxxxx.getMinBlockZ() - â˜ƒ;
      RenderSystem.lineWidth(1.0F);
      â˜ƒxx.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

      for(int â˜ƒxxxxxxxx = -16; â˜ƒxxxxxxxx <= 32; â˜ƒxxxxxxxx += 16) {
         for(int â˜ƒxxxxxxxxx = -16; â˜ƒxxxxxxxxx <= 32; â˜ƒxxxxxxxxx += 16) {
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
         }
      }

      for(int â˜ƒxxxxxxxx = 2; â˜ƒxxxxxxxx < 16; â˜ƒxxxxxxxx += 2) {
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
      }

      for(int â˜ƒxxxxxxxx = 2; â˜ƒxxxxxxxx < 16; â˜ƒxxxxxxxx += 2) {
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
      }

      for(int â˜ƒxxxxxxxx = this.minecraft.level.getMinBuildHeight(); â˜ƒxxxxxxxx <= this.minecraft.level.getMaxBuildHeight(); â˜ƒxxxxxxxx += 2) {
         double â˜ƒxxxxxxxxx = (double)â˜ƒxxxxxxxx - â˜ƒ;
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx + 16.0).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
      }

      â˜ƒx.end();
      RenderSystem.lineWidth(2.0F);
      â˜ƒxx.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

      for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx <= 16; â˜ƒxxxxxxxx += 16) {
         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx <= 16; â˜ƒxxxxxxxxx += 16) {
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
            â˜ƒxx.vertex(â˜ƒxxxxxx + (double)â˜ƒxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxx + (double)â˜ƒxxxxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
         }
      }

      for(int â˜ƒxxxxxxxx = this.minecraft.level.getMinBuildHeight(); â˜ƒxxxxxxxx <= this.minecraft.level.getMaxBuildHeight(); â˜ƒxxxxxxxx += 16) {
         double â˜ƒxxxxxxxxx = (double)â˜ƒxxxxxxxx - â˜ƒ;
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx + 16.0).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx + 16.0).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx + 16.0, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxx).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
      }

      â˜ƒx.end();
      RenderSystem.lineWidth(1.0F);
      RenderSystem.enableBlend();
      RenderSystem.enableTexture();
   }
}
