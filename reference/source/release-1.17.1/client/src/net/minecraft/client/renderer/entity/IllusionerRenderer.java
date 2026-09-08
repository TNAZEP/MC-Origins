package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.phys.Vec3;

public class IllusionerRenderer extends IllagerRenderer<Illusioner> {
   private static final ResourceLocation ILLUSIONER = new ResourceLocation("textures/entity/illager/illusioner.png");

   public IllusionerRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new IllagerModel<>(â˜ƒ.bakeLayer(ModelLayers.ILLUSIONER)), 0.5F);
      this.addLayer(
         new ItemInHandLayer<Illusioner, IllagerModel<Illusioner>>(this) {
            public void render(
               PoseStack var1, MultiBufferSource var2, int var3, Illusioner var4, float var5, float var6, float var7, float var8, float var9, float var10
            ) {
               if (â˜ƒ.isCastingSpell() || â˜ƒ.isAggressive()) {
                  super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               }
            }
         }
      );
      this.model.getHat().visible = true;
   }

   public ResourceLocation getTextureLocation(Illusioner var1) {
      return ILLUSIONER;
   }

   public void render(Illusioner var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      if (â˜ƒ.isInvisible()) {
         Vec3[] â˜ƒ = â˜ƒ.getIllusionOffsets(â˜ƒ);
         float â˜ƒx = this.getBob(â˜ƒ, â˜ƒ);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length; ++â˜ƒxx) {
            â˜ƒ.pushPose();
            â˜ƒ.translate(
               â˜ƒ[â˜ƒxx].x + (double)Mth.cos((float)â˜ƒxx + â˜ƒx * 0.5F) * 0.025,
               â˜ƒ[â˜ƒxx].y + (double)Mth.cos((float)â˜ƒxx + â˜ƒx * 0.75F) * 0.0125,
               â˜ƒ[â˜ƒxx].z + (double)Mth.cos((float)â˜ƒxx + â˜ƒx * 0.7F) * 0.025
            );
            super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.popPose();
         }
      } else {
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected boolean isBodyVisible(Illusioner var1) {
      return true;
   }
}
