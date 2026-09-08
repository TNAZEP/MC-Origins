package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;

public class CatCollarLayer extends RenderLayer<Cat, CatModel<Cat>> {
   private static final ResourceLocation CAT_COLLAR_LOCATION = new ResourceLocation("textures/entity/cat/cat_collar.png");
   private final CatModel<Cat> catModel;

   public CatCollarLayer(RenderLayerParent<Cat, CatModel<Cat>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.catModel = new CatModel<>(â˜ƒ.bakeLayer(ModelLayers.CAT_COLLAR));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Cat var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (â˜ƒ.isTame()) {
         float[] â˜ƒ = â˜ƒ.getCollarColor().getTextureDiffuseColors();
         coloredCutoutModelCopyLayerRender(
            this.getParentModel(), this.catModel, CAT_COLLAR_LOCATION, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ[0], â˜ƒ[1], â˜ƒ[2]
         );
      }
   }
}
