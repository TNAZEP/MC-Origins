package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Ghast;

public class GhastRenderer extends MobRenderer<Ghast, GhastModel<Ghast>> {
   private static final ResourceLocation GHAST_LOCATION = new ResourceLocation("textures/entity/ghast/ghast.png");
   private static final ResourceLocation GHAST_SHOOTING_LOCATION = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

   public GhastRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new GhastModel<>(â˜ƒ.bakeLayer(ModelLayers.GHAST)), 1.5F);
   }

   public ResourceLocation getTextureLocation(Ghast var1) {
      return â˜ƒ.isCharging() ? GHAST_SHOOTING_LOCATION : GHAST_LOCATION;
   }

   protected void scale(Ghast var1, PoseStack var2, float var3) {
      float â˜ƒ = 1.0F;
      float â˜ƒx = 4.5F;
      float â˜ƒxx = 4.5F;
      â˜ƒ.scale(4.5F, 4.5F, 4.5F);
   }
}
