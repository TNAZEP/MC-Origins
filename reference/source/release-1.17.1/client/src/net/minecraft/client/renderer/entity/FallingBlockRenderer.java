package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FallingBlockRenderer extends EntityRenderer<FallingBlockEntity> {
   public FallingBlockRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.shadowRadius = 0.5F;
   }

   public void render(FallingBlockEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      BlockState â˜ƒ = â˜ƒ.getBlockState();
      if (â˜ƒ.getRenderShape() == RenderShape.MODEL) {
         Level â˜ƒx = â˜ƒ.getLevel();
         if (â˜ƒ != â˜ƒx.getBlockState(â˜ƒ.blockPosition()) && â˜ƒ.getRenderShape() != RenderShape.INVISIBLE) {
            â˜ƒ.pushPose();
            BlockPos â˜ƒxx = new BlockPos(â˜ƒ.getX(), â˜ƒ.getBoundingBox().maxY, â˜ƒ.getZ());
            â˜ƒ.translate(-0.5, 0.0, -0.5);
            BlockRenderDispatcher â˜ƒxxx = Minecraft.getInstance().getBlockRenderer();
            â˜ƒxxx.getModelRenderer()
               .tesselateBlock(
                  â˜ƒx,
                  â˜ƒxxx.getBlockModel(â˜ƒ),
                  â˜ƒ,
                  â˜ƒxx,
                  â˜ƒ,
                  â˜ƒ.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(â˜ƒ)),
                  false,
                  new Random(),
                  â˜ƒ.getSeed(â˜ƒ.getStartPos()),
                  OverlayTexture.NO_OVERLAY
               );
            â˜ƒ.popPose();
            super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   public ResourceLocation getTextureLocation(FallingBlockEntity var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
