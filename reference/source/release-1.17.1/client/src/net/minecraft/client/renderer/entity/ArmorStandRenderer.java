package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandRenderer extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {
   public static final ResourceLocation DEFAULT_SKIN_LOCATION = new ResourceLocation("textures/entity/armorstand/wood.png");

   public ArmorStandRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new ArmorStandModel(â˜ƒ.bakeLayer(ModelLayers.ARMOR_STAND)), 0.0F);
      this.addLayer(
         new HumanoidArmorLayer<>(
            this,
            new ArmorStandArmorModel(â˜ƒ.bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR)),
            new ArmorStandArmorModel(â˜ƒ.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR))
         )
      );
      this.addLayer(new ItemInHandLayer<>(this));
      this.addLayer(new ElytraLayer<>(this, â˜ƒ.getModelSet()));
      this.addLayer(new CustomHeadLayer<>(this, â˜ƒ.getModelSet()));
   }

   public ResourceLocation getTextureLocation(ArmorStand var1) {
      return DEFAULT_SKIN_LOCATION;
   }

   protected void setupRotations(ArmorStand var1, PoseStack var2, float var3, float var4, float var5) {
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ));
      float â˜ƒ = (float)(â˜ƒ.level.getGameTime() - â˜ƒ.lastHit) + â˜ƒ;
      if (â˜ƒ < 5.0F) {
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(â˜ƒ / 1.5F * (float) Math.PI) * 3.0F));
      }
   }

   protected boolean shouldShowName(ArmorStand var1) {
      double â˜ƒ = this.entityRenderDispatcher.distanceToSqr(â˜ƒ);
      float â˜ƒx = â˜ƒ.isCrouching() ? 32.0F : 64.0F;
      return â˜ƒ >= (double)(â˜ƒx * â˜ƒx) ? false : â˜ƒ.isCustomNameVisible();
   }

   @Nullable
   protected RenderType getRenderType(ArmorStand var1, boolean var2, boolean var3, boolean var4) {
      if (!â˜ƒ.isMarker()) {
         return super.getRenderType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         ResourceLocation â˜ƒ = this.getTextureLocation(â˜ƒ);
         if (â˜ƒ) {
            return RenderType.entityTranslucent(â˜ƒ, false);
         } else {
            return â˜ƒ ? RenderType.entityCutoutNoCull(â˜ƒ, false) : null;
         }
      }
   }
}
