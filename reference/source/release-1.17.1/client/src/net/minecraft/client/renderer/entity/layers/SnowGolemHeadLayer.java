package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowGolemHeadLayer extends RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>> {
   public SnowGolemHeadLayer(RenderLayerParent<SnowGolem, SnowGolemModel<SnowGolem>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, SnowGolem var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (â˜ƒ.hasPumpkin()) {
         Minecraft â˜ƒ = Minecraft.getInstance();
         boolean â˜ƒx = â˜ƒ.shouldEntityAppearGlowing(â˜ƒ) && â˜ƒ.isInvisible();
         if (!â˜ƒ.isInvisible() || â˜ƒx) {
            â˜ƒ.pushPose();
            this.getParentModel().getHead().translateAndRotate(â˜ƒ);
            float â˜ƒxx = 0.625F;
            â˜ƒ.translate(0.0, -0.34375, 0.0);
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            â˜ƒ.scale(0.625F, -0.625F, -0.625F);
            ItemStack â˜ƒxxx = new ItemStack(Blocks.CARVED_PUMPKIN);
            if (â˜ƒx) {
               BlockState â˜ƒxxxx = Blocks.CARVED_PUMPKIN.defaultBlockState();
               BlockRenderDispatcher â˜ƒxxxxx = â˜ƒ.getBlockRenderer();
               BakedModel â˜ƒxxxxxx = â˜ƒxxxxx.getBlockModel(â˜ƒxxxx);
               int â˜ƒxxxxxxx = LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F);
               â˜ƒ.translate(-0.5, -0.5, -0.5);
               â˜ƒxxxxx.getModelRenderer()
                  .renderModel(
                     â˜ƒ.last(), â˜ƒ.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), â˜ƒxxxx, â˜ƒxxxxxx, 0.0F, 0.0F, 0.0F, â˜ƒ, â˜ƒxxxxxxx
                  );
            } else {
               â˜ƒ.getItemRenderer()
                  .renderStatic(
                     â˜ƒ,
                     â˜ƒxxx,
                     ItemTransforms.TransformType.HEAD,
                     false,
                     â˜ƒ,
                     â˜ƒ,
                     â˜ƒ.level,
                     â˜ƒ,
                     LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F),
                     â˜ƒ.getId()
                  );
            }

            â˜ƒ.popPose();
         }
      }
   }
}
