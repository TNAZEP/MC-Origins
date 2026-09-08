package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.LlamaSpitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.LlamaSpit;

public class LlamaSpitRenderer extends EntityRenderer<LlamaSpit> {
   private static final ResourceLocation LLAMA_SPIT_LOCATION = new ResourceLocation("textures/entity/llama/spit.png");
   private final LlamaSpitModel<LlamaSpit> model;

   public LlamaSpitRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.model = new LlamaSpitModel<>(â˜ƒ.bakeLayer(ModelLayers.LLAMA_SPIT));
   }

   public void render(LlamaSpit var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.15F, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.yRotO, â˜ƒ.getYRot()) - 90.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot())));
      this.model.setupAnim(â˜ƒ, â˜ƒ, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(this.model.renderType(LLAMA_SPIT_LOCATION));
      this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(LlamaSpit var1) {
      return LLAMA_SPIT_LOCATION;
   }
}
