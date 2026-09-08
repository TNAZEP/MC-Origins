package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ScreenEffectRenderer {
   private static final ResourceLocation UNDERWATER_LOCATION = new ResourceLocation("textures/misc/underwater.png");

   public static void renderScreenEffect(Minecraft var0, PoseStack var1) {
      Player â˜ƒ = â˜ƒ.player;
      if (!â˜ƒ.noPhysics) {
         BlockState â˜ƒx = getViewBlockingState(â˜ƒ);
         if (â˜ƒx != null) {
            renderTex(â˜ƒ.getBlockRenderer().getBlockModelShaper().getParticleIcon(â˜ƒx), â˜ƒ);
         }
      }

      if (!â˜ƒ.player.isSpectator()) {
         if (â˜ƒ.player.isEyeInFluid(FluidTags.WATER)) {
            renderWater(â˜ƒ, â˜ƒ);
         }

         if (â˜ƒ.player.isOnFire()) {
            renderFire(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Nullable
   private static BlockState getViewBlockingState(Player var0) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
         double â˜ƒxx = â˜ƒ.getX() + (double)(((float)((â˜ƒx >> 0) % 2) - 0.5F) * â˜ƒ.getBbWidth() * 0.8F);
         double â˜ƒxxx = â˜ƒ.getEyeY() + (double)(((float)((â˜ƒx >> 1) % 2) - 0.5F) * 0.1F);
         double â˜ƒxxxx = â˜ƒ.getZ() + (double)(((float)((â˜ƒx >> 2) % 2) - 0.5F) * â˜ƒ.getBbWidth() * 0.8F);
         â˜ƒ.set(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         BlockState â˜ƒxxxxx = â˜ƒ.level.getBlockState(â˜ƒ);
         if (â˜ƒxxxxx.getRenderShape() != RenderShape.INVISIBLE && â˜ƒxxxxx.isViewBlocking(â˜ƒ.level, â˜ƒ)) {
            return â˜ƒxxxxx;
         }
      }

      return null;
   }

   private static void renderTex(TextureAtlasSprite var0, PoseStack var1) {
      RenderSystem.setShaderTexture(0, â˜ƒ.atlas().location());
      RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
      BufferBuilder â˜ƒ = Tesselator.getInstance().getBuilder();
      float â˜ƒx = 0.1F;
      float â˜ƒxx = -1.0F;
      float â˜ƒxxx = 1.0F;
      float â˜ƒxxxx = -1.0F;
      float â˜ƒxxxxx = 1.0F;
      float â˜ƒxxxxxx = -0.5F;
      float â˜ƒxxxxxxx = â˜ƒ.getU0();
      float â˜ƒxxxxxxxx = â˜ƒ.getU1();
      float â˜ƒxxxxxxxxx = â˜ƒ.getV0();
      float â˜ƒxxxxxxxxxx = â˜ƒ.getV1();
      Matrix4f â˜ƒxxxxxxxxxxx = â˜ƒ.last().pose();
      â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
      â˜ƒ.vertex(â˜ƒxxxxxxxxxxx, -1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒxxxxxxxxxxx, 1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒxxxxxxxxxxx, 1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(â˜ƒxxxxxxx, â˜ƒxxxxxxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒxxxxxxxxxxx, -1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).endVertex();
      â˜ƒ.end();
      BufferUploader.end(â˜ƒ);
   }

   private static void renderWater(Minecraft var0, PoseStack var1) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.enableTexture();
      RenderSystem.setShaderTexture(0, UNDERWATER_LOCATION);
      BufferBuilder â˜ƒ = Tesselator.getInstance().getBuilder();
      float â˜ƒx = â˜ƒ.player.getBrightness();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(â˜ƒx, â˜ƒx, â˜ƒx, 0.1F);
      float â˜ƒxx = 4.0F;
      float â˜ƒxxx = -1.0F;
      float â˜ƒxxxx = 1.0F;
      float â˜ƒxxxxx = -1.0F;
      float â˜ƒxxxxxx = 1.0F;
      float â˜ƒxxxxxxx = -0.5F;
      float â˜ƒxxxxxxxx = -â˜ƒ.player.getYRot() / 64.0F;
      float â˜ƒxxxxxxxxx = â˜ƒ.player.getXRot() / 64.0F;
      Matrix4f â˜ƒxxxxxxxxxx = â˜ƒ.last().pose();
      â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      â˜ƒ.vertex(â˜ƒxxxxxxxxxx, -1.0F, -1.0F, -0.5F).uv(4.0F + â˜ƒxxxxxxxx, 4.0F + â˜ƒxxxxxxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒxxxxxxxxxx, 1.0F, -1.0F, -0.5F).uv(0.0F + â˜ƒxxxxxxxx, 4.0F + â˜ƒxxxxxxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒxxxxxxxxxx, 1.0F, 1.0F, -0.5F).uv(0.0F + â˜ƒxxxxxxxx, 0.0F + â˜ƒxxxxxxxxx).endVertex();
      â˜ƒ.vertex(â˜ƒxxxxxxxxxx, -1.0F, 1.0F, -0.5F).uv(4.0F + â˜ƒxxxxxxxx, 0.0F + â˜ƒxxxxxxxxx).endVertex();
      â˜ƒ.end();
      BufferUploader.end(â˜ƒ);
      RenderSystem.disableBlend();
   }

   private static void renderFire(Minecraft var0, PoseStack var1) {
      BufferBuilder â˜ƒ = Tesselator.getInstance().getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
      RenderSystem.depthFunc(519);
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableTexture();
      TextureAtlasSprite â˜ƒx = ModelBakery.FIRE_1.sprite();
      RenderSystem.setShaderTexture(0, â˜ƒx.atlas().location());
      float â˜ƒxx = â˜ƒx.getU0();
      float â˜ƒxxx = â˜ƒx.getU1();
      float â˜ƒxxxx = (â˜ƒxx + â˜ƒxxx) / 2.0F;
      float â˜ƒxxxxx = â˜ƒx.getV0();
      float â˜ƒxxxxxx = â˜ƒx.getV1();
      float â˜ƒxxxxxxx = (â˜ƒxxxxx + â˜ƒxxxxxx) / 2.0F;
      float â˜ƒxxxxxxxx = â˜ƒx.uvShrinkRatio();
      float â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, â˜ƒxx, â˜ƒxxxx);
      float â˜ƒxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxx);
      float â˜ƒxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxx);
      float â˜ƒxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
      float â˜ƒxxxxxxxxxxxxx = 1.0F;

      for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < 2; ++â˜ƒxxxxxxxxxxxxxx) {
         â˜ƒ.pushPose();
         float â˜ƒxxxxxxxxxxxxxxx = -0.5F;
         float â˜ƒxxxxxxxxxxxxxxxx = 0.5F;
         float â˜ƒxxxxxxxxxxxxxxxxx = -0.5F;
         float â˜ƒxxxxxxxxxxxxxxxxxx = 0.5F;
         float â˜ƒxxxxxxxxxxxxxxxxxxx = -0.5F;
         â˜ƒ.translate((double)((float)(-(â˜ƒxxxxxxxxxxxxxx * 2 - 1)) * 0.24F), -0.3F, 0.0);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)(â˜ƒxxxxxxxxxxxxxx * 2 - 1) * 10.0F));
         Matrix4f â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒ.last().pose();
         â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
         â˜ƒ.vertex(â˜ƒxxxxxxxxxxxxxxxxxxxx, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).endVertex();
         â˜ƒ.vertex(â˜ƒxxxxxxxxxxxxxxxxxxxx, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxx).endVertex();
         â˜ƒ.vertex(â˜ƒxxxxxxxxxxxxxxxxxxxx, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxx).endVertex();
         â˜ƒ.vertex(â˜ƒxxxxxxxxxxxxxxxxxxxx, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx).endVertex();
         â˜ƒ.end();
         BufferUploader.end(â˜ƒ);
         â˜ƒ.popPose();
      }

      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.depthFunc(515);
   }
}
