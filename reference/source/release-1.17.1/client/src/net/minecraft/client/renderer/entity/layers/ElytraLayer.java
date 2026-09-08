package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");
   private final ElytraModel<T> elytraModel;

   public ElytraLayer(RenderLayerParent<T, M> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.elytraModel = new ElytraModel<>(â˜ƒ.bakeLayer(ModelLayers.ELYTRA));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ItemStack â˜ƒ = â˜ƒ.getItemBySlot(EquipmentSlot.CHEST);
      if (â˜ƒ.is(Items.ELYTRA)) {
         ResourceLocation â˜ƒxx;
         if (â˜ƒ instanceof AbstractClientPlayer â˜ƒx) {
            if (â˜ƒx.isElytraLoaded() && â˜ƒx.getElytraTextureLocation() != null) {
               â˜ƒxx = â˜ƒx.getElytraTextureLocation();
            } else if (â˜ƒx.isCapeLoaded() && â˜ƒx.getCloakTextureLocation() != null && â˜ƒx.isModelPartShown(PlayerModelPart.CAPE)) {
               â˜ƒxx = â˜ƒx.getCloakTextureLocation();
            } else {
               â˜ƒxx = WINGS_LOCATION;
            }
         } else {
            â˜ƒxx = WINGS_LOCATION;
         }

         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 0.0, 0.125);
         this.getParentModel().copyPropertiesTo(this.elytraModel);
         this.elytraModel.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         VertexConsumer â˜ƒx = ItemRenderer.getArmorFoilBuffer(â˜ƒ, RenderType.armorCutoutNoCull(â˜ƒxx), false, â˜ƒ.hasFoil());
         this.elytraModel.renderToBuffer(â˜ƒ, â˜ƒx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
         â˜ƒ.popPose();
      }
   }
}
