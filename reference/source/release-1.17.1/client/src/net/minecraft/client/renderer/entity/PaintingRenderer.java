package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;

public class PaintingRenderer extends EntityRenderer<Painting> {
   public PaintingRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
   }

   public void render(Painting var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ));
      Motive â˜ƒ = â˜ƒ.motive;
      float â˜ƒx = 0.0625F;
      â˜ƒ.scale(0.0625F, 0.0625F, 0.0625F);
      VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(RenderType.entitySolid(this.getTextureLocation(â˜ƒ)));
      PaintingTextureManager â˜ƒxxx = Minecraft.getInstance().getPaintingTextures();
      this.renderPainting(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ.getWidth(), â˜ƒ.getHeight(), â˜ƒxxx.get(â˜ƒ), â˜ƒxxx.getBackSprite());
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(Painting var1) {
      return Minecraft.getInstance().getPaintingTextures().getBackSprite().atlas().location();
   }

   private void renderPainting(PoseStack var1, VertexConsumer var2, Painting var3, int var4, int var5, TextureAtlasSprite var6, TextureAtlasSprite var7) {
      PoseStack.Pose â˜ƒ = â˜ƒ.last();
      Matrix4f â˜ƒx = â˜ƒ.pose();
      Matrix3f â˜ƒxx = â˜ƒ.normal();
      float â˜ƒxxx = (float)(-â˜ƒ) / 2.0F;
      float â˜ƒxxxx = (float)(-â˜ƒ) / 2.0F;
      float â˜ƒxxxxx = 0.5F;
      float â˜ƒxxxxxx = â˜ƒ.getU0();
      float â˜ƒxxxxxxx = â˜ƒ.getU1();
      float â˜ƒxxxxxxxx = â˜ƒ.getV0();
      float â˜ƒxxxxxxxxx = â˜ƒ.getV1();
      float â˜ƒxxxxxxxxxx = â˜ƒ.getU0();
      float â˜ƒxxxxxxxxxxx = â˜ƒ.getU1();
      float â˜ƒxxxxxxxxxxxx = â˜ƒ.getV0();
      float â˜ƒxxxxxxxxxxxxx = â˜ƒ.getV(1.0);
      float â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getU0();
      float â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.getU(1.0);
      float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.getV0();
      float â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ.getV1();
      int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ / 16;
      int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ / 16;
      double â˜ƒxxxxxxxxxxxxxxxxxxxx = 16.0 / (double)â˜ƒxxxxxxxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxxxxxxxx = 16.0 / (double)â˜ƒxxxxxxxxxxxxxxxxxxx;

      for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxxx) {
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxx + (float)((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1) * 16);
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxx + (float)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx * 16);
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxx + (float)((â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 1) * 16);
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxx + (float)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx * 16);
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockX();
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.floor(
               â˜ƒ.getY() + (double)((â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F)
            );
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockZ();
            Direction â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getDirection();
            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.NORTH) {
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.floor(â˜ƒ.getX() + (double)((â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F));
            }

            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.WEST) {
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.floor(â˜ƒ.getZ() - (double)((â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F));
            }

            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.SOUTH) {
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.floor(â˜ƒ.getX() - (double)((â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F));
            }

            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == Direction.EAST) {
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.floor(â˜ƒ.getZ() + (double)((â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx) / 2.0F / 16.0F));
            }

            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = LevelRenderer.getLightColor(
               â˜ƒ.level, new BlockPos(â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
            );
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getU(â˜ƒxxxxxxxxxxxxxxxxxxxx * (double)(â˜ƒxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxx));
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getU(â˜ƒxxxxxxxxxxxxxxxxxxxx * (double)(â˜ƒxxxxxxxxxxxxxxxxxx - (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1)));
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getV(â˜ƒxxxxxxxxxxxxxxxxxxxxx * (double)(â˜ƒxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxx));
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getV(â˜ƒxxxxxxxxxxxxxxxxxxxxx * (double)(â˜ƒxxxxxxxxxxxxxxxxxxx - (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 1)));
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               -0.5F,
               0,
               0,
               -1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx, 0.5F, 0, 0, 1, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxxxx,
               0.5F,
               0,
               0,
               1,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               -0.5F,
               0,
               1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               -0.5F,
               0,
               1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               0.5F,
               0,
               1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               0.5F,
               0,
               1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               0.5F,
               0,
               -1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               0.5F,
               0,
               -1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               -0.5F,
               0,
               -1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx,
               -0.5F,
               0,
               -1,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               0.5F,
               -1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx,
               0.5F,
               -1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx,
               -0.5F,
               -1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               -0.5F,
               -1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               -0.5F,
               1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx,
               -0.5F,
               1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx,
               0.5F,
               1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒx,
               â˜ƒxx,
               â˜ƒ,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               0.5F,
               1,
               0,
               0,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx
            );
         }
      }
   }

   private void vertex(
      Matrix4f var1, Matrix3f var2, VertexConsumer var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11, int var12
   ) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         .color(255, 255, 255, 255)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(â˜ƒ)
         .normal(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ)
         .endVertex();
   }
}
