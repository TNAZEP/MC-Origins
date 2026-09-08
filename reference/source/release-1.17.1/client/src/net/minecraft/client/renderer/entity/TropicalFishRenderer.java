package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.TropicalFish;

public class TropicalFishRenderer extends MobRenderer<TropicalFish, ColorableHierarchicalModel<TropicalFish>> {
   private final ColorableHierarchicalModel<TropicalFish> modelA = this.getModel();
   private final ColorableHierarchicalModel<TropicalFish> modelB;

   public TropicalFishRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new TropicalFishModelA<>(â˜ƒ.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL)), 0.15F);
      this.modelB = new TropicalFishModelB<>(â˜ƒ.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE));
      this.addLayer(new TropicalFishPatternLayer(this, â˜ƒ.getModelSet()));
   }

   public ResourceLocation getTextureLocation(TropicalFish var1) {
      return â˜ƒ.getBaseTextureLocation();
   }

   public void render(TropicalFish var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      ColorableHierarchicalModel<TropicalFish> â˜ƒ = â˜ƒ.getBaseVariant() == 0 ? this.modelA : this.modelB;
      this.model = â˜ƒ;
      float[] â˜ƒx = â˜ƒ.getBaseColor();
      â˜ƒ.setColor(â˜ƒx[0], â˜ƒx[1], â˜ƒx[2]);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.setColor(1.0F, 1.0F, 1.0F);
   }

   protected void setupRotations(TropicalFish var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = 4.3F * Mth.sin(0.6F * â˜ƒ);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ));
      if (!â˜ƒ.isInWater()) {
         â˜ƒ.translate(0.2F, 0.1F, 0.0);
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
      }
   }
}
