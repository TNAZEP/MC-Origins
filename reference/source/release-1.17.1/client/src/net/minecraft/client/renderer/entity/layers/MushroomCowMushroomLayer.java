package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.block.state.BlockState;

public class MushroomCowMushroomLayer<T extends MushroomCow> extends RenderLayer<T, CowModel<T>> {
   public MushroomCowMushroomLayer(RenderLayerParent<T, CowModel<T>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (!â˜ƒ.isBaby()) {
         Minecraft â˜ƒ = Minecraft.getInstance();
         boolean â˜ƒx = â˜ƒ.shouldEntityAppearGlowing(â˜ƒ) && â˜ƒ.isInvisible();
         if (!â˜ƒ.isInvisible() || â˜ƒx) {
            BlockRenderDispatcher â˜ƒxx = â˜ƒ.getBlockRenderer();
            BlockState â˜ƒxxx = â˜ƒ.getMushroomType().getBlockState();
            int â˜ƒxxxx = LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F);
            BakedModel â˜ƒxxxxx = â˜ƒxx.getBlockModel(â˜ƒxxx);
            â˜ƒ.pushPose();
            â˜ƒ.translate(0.2F, -0.35F, 0.5);
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-48.0F));
            â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
            â˜ƒ.translate(-0.5, -0.5, -0.5);
            this.renderMushroomBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            â˜ƒ.popPose();
            â˜ƒ.pushPose();
            â˜ƒ.translate(0.2F, -0.35F, 0.5);
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(42.0F));
            â˜ƒ.translate(0.1F, 0.0, -0.6F);
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-48.0F));
            â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
            â˜ƒ.translate(-0.5, -0.5, -0.5);
            this.renderMushroomBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            â˜ƒ.popPose();
            â˜ƒ.pushPose();
            this.getParentModel().getHead().translateAndRotate(â˜ƒ);
            â˜ƒ.translate(0.0, -0.7F, -0.2F);
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-78.0F));
            â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
            â˜ƒ.translate(-0.5, -0.5, -0.5);
            this.renderMushroomBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            â˜ƒ.popPose();
         }
      }
   }

   private void renderMushroomBlock(
      PoseStack var1, MultiBufferSource var2, int var3, boolean var4, BlockRenderDispatcher var5, BlockState var6, int var7, BakedModel var8
   ) {
      if (â˜ƒ) {
         â˜ƒ.getModelRenderer().renderModel(â˜ƒ.last(), â˜ƒ.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), â˜ƒ, â˜ƒ, 0.0F, 0.0F, 0.0F, â˜ƒ, â˜ƒ);
      } else {
         â˜ƒ.renderSingleBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
