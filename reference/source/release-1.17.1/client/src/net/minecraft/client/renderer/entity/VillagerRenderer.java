package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

public class VillagerRenderer extends MobRenderer<Villager, VillagerModel<Villager>> {
   private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

   public VillagerRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new VillagerModel<>(â˜ƒ.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
      this.addLayer(new CustomHeadLayer<>(this, â˜ƒ.getModelSet()));
      this.addLayer(new VillagerProfessionLayer<>(this, â˜ƒ.getResourceManager(), "villager"));
      this.addLayer(new CrossedArmsItemLayer<>(this));
   }

   public ResourceLocation getTextureLocation(Villager var1) {
      return VILLAGER_BASE_SKIN;
   }

   protected void scale(Villager var1, PoseStack var2, float var3) {
      float â˜ƒ = 0.9375F;
      if (â˜ƒ.isBaby()) {
         â˜ƒ = (float)((double)â˜ƒ * 0.5);
         this.shadowRadius = 0.25F;
      } else {
         this.shadowRadius = 0.5F;
      }

      â˜ƒ.scale(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
