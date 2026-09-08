package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.layers.ShulkerHeadLayer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShulkerRenderer extends MobRenderer<Shulker, ShulkerModel<Shulker>> {
   private static final ResourceLocation DEFAULT_TEXTURE_LOCATION = new ResourceLocation(
      "textures/" + Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION.texture().getPath() + ".png"
   );
   private static final ResourceLocation[] TEXTURE_LOCATION = (ResourceLocation[])Sheets.SHULKER_TEXTURE_LOCATION
      .stream()
      .map(var0 -> new ResourceLocation("textures/" + var0.texture().getPath() + ".png"))
      .toArray(var0 -> new ResourceLocation[var0]);

   public ShulkerRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new ShulkerModel<>(â˜ƒ.bakeLayer(ModelLayers.SHULKER)), 0.0F);
      this.addLayer(new ShulkerHeadLayer(this));
   }

   public Vec3 getRenderOffset(Shulker var1, float var2) {
      return (Vec3)â˜ƒ.getRenderPosition(â˜ƒ).orElse(super.getRenderOffset(â˜ƒ, â˜ƒ));
   }

   public boolean shouldRender(Shulker var1, Frustum var2, double var3, double var5, double var7) {
      return super.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         ? true
         : â˜ƒ.getRenderPosition(0.0F)
            .filter(
               var2x -> {
                  EntityType<?> â˜ƒ = â˜ƒ.getType();
                  float â˜ƒx = â˜ƒ.getHeight() / 2.0F;
                  float â˜ƒxx = â˜ƒ.getWidth() / 2.0F;
                  Vec3 â˜ƒxxx = Vec3.atBottomCenterOf(â˜ƒ.blockPosition());
                  return â˜ƒ.isVisible(
                     new AABB(var2x.x, var2x.y + (double)â˜ƒx, var2x.z, â˜ƒxxx.x, â˜ƒxxx.y + (double)â˜ƒx, â˜ƒxxx.z)
                        .inflate((double)â˜ƒxx, (double)â˜ƒx, (double)â˜ƒxx)
                  );
               }
            )
            .isPresent();
   }

   public ResourceLocation getTextureLocation(Shulker var1) {
      return getTextureLocation(â˜ƒ.getColor());
   }

   public static ResourceLocation getTextureLocation(@Nullable DyeColor var0) {
      return â˜ƒ == null ? DEFAULT_TEXTURE_LOCATION : TEXTURE_LOCATION[â˜ƒ.getId()];
   }

   protected void setupRotations(Shulker var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 180.0F, â˜ƒ);
      â˜ƒ.translate(0.0, 0.5, 0.0);
      â˜ƒ.mulPose(â˜ƒ.getAttachFace().getOpposite().getRotation());
      â˜ƒ.translate(0.0, -0.5, 0.0);
   }
}
