package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

public class ParrotOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
   private final ParrotModel model;

   public ParrotOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.model = new ParrotModel(â˜ƒ.bakeLayer(ModelLayers.PARROT));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      this.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
      this.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   private void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, boolean var9) {
      CompoundTag â˜ƒ = â˜ƒ ? â˜ƒ.getShoulderEntityLeft() : â˜ƒ.getShoulderEntityRight();
      EntityType.byString(â˜ƒ.getString("id")).filter(var0 -> var0 == EntityType.PARROT).ifPresent(var11 -> {
         â˜ƒ.pushPose();
         â˜ƒ.translate(â˜ƒ ? 0.4F : -0.4F, â˜ƒ.isCrouching() ? -1.3F : -1.5, 0.0);
         VertexConsumer â˜ƒ = â˜ƒ.getBuffer(this.model.renderType(ParrotRenderer.PARROT_LOCATIONS[â˜ƒ.getInt("Variant")]));
         this.model.renderOnShoulder(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.tickCount);
         â˜ƒ.popPose();
      });
   }
}
