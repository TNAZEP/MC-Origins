package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vindicator;

public class VindicatorRenderer extends IllagerRenderer<Vindicator> {
   private static final ResourceLocation VINDICATOR = new ResourceLocation("textures/entity/illager/vindicator.png");

   public VindicatorRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new IllagerModel<>(â˜ƒ.bakeLayer(ModelLayers.VINDICATOR)), 0.5F);
      this.addLayer(
         new ItemInHandLayer<Vindicator, IllagerModel<Vindicator>>(this) {
            public void render(
               PoseStack var1, MultiBufferSource var2, int var3, Vindicator var4, float var5, float var6, float var7, float var8, float var9, float var10
            ) {
               if (â˜ƒ.isAggressive()) {
                  super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               }
            }
         }
      );
   }

   public ResourceLocation getTextureLocation(Vindicator var1) {
      return VINDICATOR;
   }
}
