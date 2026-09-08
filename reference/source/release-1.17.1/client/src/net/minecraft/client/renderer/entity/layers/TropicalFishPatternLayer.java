package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.animal.TropicalFish;

public class TropicalFishPatternLayer extends RenderLayer<TropicalFish, ColorableHierarchicalModel<TropicalFish>> {
   private final TropicalFishModelA<TropicalFish> modelA;
   private final TropicalFishModelB<TropicalFish> modelB;

   public TropicalFishPatternLayer(RenderLayerParent<TropicalFish, ColorableHierarchicalModel<TropicalFish>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.modelA = new TropicalFishModelA<>(â˜ƒ.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL_PATTERN));
      this.modelB = new TropicalFishModelB<>(â˜ƒ.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE_PATTERN));
   }

   public void render(
      PoseStack var1, MultiBufferSource var2, int var3, TropicalFish var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      EntityModel<TropicalFish> â˜ƒ = (EntityModel<TropicalFish>)(â˜ƒ.getBaseVariant() == 0 ? this.modelA : this.modelB);
      float[] â˜ƒx = â˜ƒ.getPatternColor();
      coloredCutoutModelCopyLayerRender(
         this.getParentModel(), â˜ƒ, â˜ƒ.getPatternTextureLocation(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx[0], â˜ƒx[1], â˜ƒx[2]
      );
   }
}
