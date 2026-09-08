package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class CubeMap {
   private static final int SIDES = 6;
   private final ResourceLocation[] images = new ResourceLocation[6];

   public CubeMap(ResourceLocation var1) {
      for(int â˜ƒ = 0; â˜ƒ < 6; ++â˜ƒ) {
         this.images[â˜ƒ] = new ResourceLocation(â˜ƒ.getNamespace(), â˜ƒ.getPath() + "_" + â˜ƒ + ".png");
      }
   }

   public void render(Minecraft var1, float var2, float var3, float var4) {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      Matrix4f â˜ƒxx = Matrix4f.perspective(85.0, (float)â˜ƒ.getWindow().getWidth() / (float)â˜ƒ.getWindow().getHeight(), 0.05F, 10.0F);
      RenderSystem.backupProjectionMatrix();
      RenderSystem.setProjectionMatrix(â˜ƒxx);
      PoseStack â˜ƒxxx = RenderSystem.getModelViewStack();
      â˜ƒxxx.pushPose();
      â˜ƒxxx.setIdentity();
      â˜ƒxxx.mulPose(Vector3f.XP.rotationDegrees(180.0F));
      RenderSystem.applyModelViewMatrix();
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      int â˜ƒxxxx = 2;

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 4; ++â˜ƒxxxxx) {
         â˜ƒxxx.pushPose();
         float â˜ƒxxxxxx = ((float)(â˜ƒxxxxx % 2) / 2.0F - 0.5F) / 256.0F;
         float â˜ƒxxxxxxx = ((float)(â˜ƒxxxxx / 2) / 2.0F - 0.5F) / 256.0F;
         float â˜ƒxxxxxxxx = 0.0F;
         â˜ƒxxx.translate((double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx, 0.0);
         â˜ƒxxx.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ));
         â˜ƒxxx.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ));
         RenderSystem.applyModelViewMatrix();

         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 6; ++â˜ƒxxxxxxxxx) {
            RenderSystem.setShaderTexture(0, this.images[â˜ƒxxxxxxxxx]);
            â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            int â˜ƒxxxxxxxxxx = Math.round(255.0F * â˜ƒ) / (â˜ƒxxxxx + 1);
            if (â˜ƒxxxxxxxxx == 0) {
               â˜ƒx.vertex(-1.0, -1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, 1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, 1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, -1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
            }

            if (â˜ƒxxxxxxxxx == 1) {
               â˜ƒx.vertex(1.0, -1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, 1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
            }

            if (â˜ƒxxxxxxxxx == 2) {
               â˜ƒx.vertex(1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
            }

            if (â˜ƒxxxxxxxxx == 3) {
               â˜ƒx.vertex(-1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, 1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, -1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
            }

            if (â˜ƒxxxxxxxxx == 4) {
               â˜ƒx.vertex(-1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, -1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, -1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
            }

            if (â˜ƒxxxxxxxxx == 5) {
               â˜ƒx.vertex(-1.0, 1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(-1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
               â˜ƒx.vertex(1.0, 1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, â˜ƒxxxxxxxxxx).endVertex();
            }

            â˜ƒ.end();
         }

         â˜ƒxxx.popPose();
         RenderSystem.applyModelViewMatrix();
         RenderSystem.colorMask(true, true, true, false);
      }

      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.restoreProjectionMatrix();
      â˜ƒxxx.popPose();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.enableDepthTest();
   }

   public CompletableFuture<Void> preload(TextureManager var1, Executor var2) {
      CompletableFuture<?>[] â˜ƒ = new CompletableFuture[6];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx] = â˜ƒ.preload(this.images[â˜ƒx], â˜ƒ);
      }

      return CompletableFuture.allOf(â˜ƒ);
   }
}
