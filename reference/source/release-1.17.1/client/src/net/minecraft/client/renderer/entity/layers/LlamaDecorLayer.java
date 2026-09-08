package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.DyeColor;

public class LlamaDecorLayer extends RenderLayer<Llama, LlamaModel<Llama>> {
   private static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
      new ResourceLocation("textures/entity/llama/decor/white.png"),
      new ResourceLocation("textures/entity/llama/decor/orange.png"),
      new ResourceLocation("textures/entity/llama/decor/magenta.png"),
      new ResourceLocation("textures/entity/llama/decor/light_blue.png"),
      new ResourceLocation("textures/entity/llama/decor/yellow.png"),
      new ResourceLocation("textures/entity/llama/decor/lime.png"),
      new ResourceLocation("textures/entity/llama/decor/pink.png"),
      new ResourceLocation("textures/entity/llama/decor/gray.png"),
      new ResourceLocation("textures/entity/llama/decor/light_gray.png"),
      new ResourceLocation("textures/entity/llama/decor/cyan.png"),
      new ResourceLocation("textures/entity/llama/decor/purple.png"),
      new ResourceLocation("textures/entity/llama/decor/blue.png"),
      new ResourceLocation("textures/entity/llama/decor/brown.png"),
      new ResourceLocation("textures/entity/llama/decor/green.png"),
      new ResourceLocation("textures/entity/llama/decor/red.png"),
      new ResourceLocation("textures/entity/llama/decor/black.png")
   };
   private static final ResourceLocation TRADER_LLAMA = new ResourceLocation("textures/entity/llama/decor/trader_llama.png");
   private final LlamaModel<Llama> model;

   public LlamaDecorLayer(RenderLayerParent<Llama, LlamaModel<Llama>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.model = new LlamaModel<>(â˜ƒ.bakeLayer(ModelLayers.LLAMA_DECOR));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Llama var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      DyeColor â˜ƒx = â˜ƒ.getSwag();
      ResourceLocation â˜ƒ;
      if (â˜ƒx != null) {
         â˜ƒ = TEXTURE_LOCATION[â˜ƒx.getId()];
      } else {
         if (!â˜ƒ.isTraderLlama()) {
            return;
         }

         â˜ƒ = TRADER_LLAMA;
      }

      this.getParentModel().copyPropertiesTo(this.model);
      this.model.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RenderType.entityCutoutNoCull(â˜ƒ));
      this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
