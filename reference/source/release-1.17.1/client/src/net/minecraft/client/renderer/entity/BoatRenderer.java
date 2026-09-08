package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class BoatRenderer extends EntityRenderer<Boat> {
   private final Map<Boat.Type, Pair<ResourceLocation, BoatModel>> boatResources;

   public BoatRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.shadowRadius = 0.8F;
      this.boatResources = (Map)Stream.of(Boat.Type.values())
         .collect(
            ImmutableMap.toImmutableMap(
               var0 -> var0,
               var1x -> Pair.of(
                     new ResourceLocation("textures/entity/boat/" + var1x.getName() + ".png"),
                     new BoatModel(â˜ƒ.bakeLayer(ModelLayers.createBoatModelName(var1x)))
                  )
            )
         );
   }

   public void render(Boat var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.375, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ));
      float â˜ƒ = (float)â˜ƒ.getHurtTime() - â˜ƒ;
      float â˜ƒx = â˜ƒ.getDamage() - â˜ƒ;
      if (â˜ƒx < 0.0F) {
         â˜ƒx = 0.0F;
      }

      if (â˜ƒ > 0.0F) {
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(â˜ƒ) * â˜ƒ * â˜ƒx / 10.0F * (float)â˜ƒ.getHurtDir()));
      }

      float â˜ƒ = â˜ƒ.getBubbleAngle(â˜ƒ);
      if (!Mth.equal(â˜ƒ, 0.0F)) {
         â˜ƒ.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), â˜ƒ.getBubbleAngle(â˜ƒ), true));
      }

      Pair<ResourceLocation, BoatModel> â˜ƒ = (Pair)this.boatResources.get(â˜ƒ.getBoatType());
      ResourceLocation â˜ƒx = â˜ƒ.getFirst();
      BoatModel â˜ƒxx = â˜ƒ.getSecond();
      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(90.0F));
      â˜ƒxx.setupAnim(â˜ƒ, â˜ƒ, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer â˜ƒxxx = â˜ƒ.getBuffer(â˜ƒxx.renderType(â˜ƒx));
      â˜ƒxx.renderToBuffer(â˜ƒ, â˜ƒxxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      if (!â˜ƒ.isUnderWater()) {
         VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(RenderType.waterMask());
         â˜ƒxx.waterPatch().render(â˜ƒ, â˜ƒxxxx, â˜ƒ, OverlayTexture.NO_OVERLAY);
      }

      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(Boat var1) {
      return (ResourceLocation)((Pair)this.boatResources.get(â˜ƒ.getBoatType())).getFirst();
   }
}
