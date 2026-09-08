package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Shulker;

public class ShulkerHeadLayer extends RenderLayer<Shulker, ShulkerModel<Shulker>> {
   public ShulkerHeadLayer(RenderLayerParent<Shulker, ShulkerModel<Shulker>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Shulker var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ResourceLocation â˜ƒ = ShulkerRenderer.getTextureLocation(â˜ƒ.getColor());
      VertexConsumer â˜ƒx = â˜ƒ.getBuffer(RenderType.entitySolid(â˜ƒ));
      this.getParentModel().getHead().render(â˜ƒ, â˜ƒx, â˜ƒ, LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F));
   }
}
