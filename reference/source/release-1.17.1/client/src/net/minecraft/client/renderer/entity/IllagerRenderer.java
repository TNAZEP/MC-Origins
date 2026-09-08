package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.monster.AbstractIllager;

public abstract class IllagerRenderer<T extends AbstractIllager> extends MobRenderer<T, IllagerModel<T>> {
   protected IllagerRenderer(EntityRendererProvider.Context var1, IllagerModel<T> var2, float var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.addLayer(new CustomHeadLayer<>(this, â˜ƒ.getModelSet()));
   }

   protected void scale(T var1, PoseStack var2, float var3) {
      float â˜ƒ = 0.9375F;
      â˜ƒ.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
