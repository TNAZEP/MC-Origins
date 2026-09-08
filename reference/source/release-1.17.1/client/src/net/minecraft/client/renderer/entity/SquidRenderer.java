package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.SquidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Squid;

public class SquidRenderer<T extends Squid> extends MobRenderer<T, SquidModel<T>> {
   private static final ResourceLocation SQUID_LOCATION = new ResourceLocation("textures/entity/squid/squid.png");

   public SquidRenderer(EntityRendererProvider.Context var1, SquidModel<T> var2) {
      super(â˜ƒ, â˜ƒ, 0.7F);
   }

   public ResourceLocation getTextureLocation(T var1) {
      return SQUID_LOCATION;
   }

   protected void setupRotations(T var1, PoseStack var2, float var3, float var4, float var5) {
      float â˜ƒ = Mth.lerp(â˜ƒ, â˜ƒ.xBodyRotO, â˜ƒ.xBodyRot);
      float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.zBodyRotO, â˜ƒ.zBodyRot);
      â˜ƒ.translate(0.0, 0.5, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
      â˜ƒ.translate(0.0, -1.2F, 0.0);
   }

   protected float getBob(T var1, float var2) {
      return Mth.lerp(â˜ƒ, â˜ƒ.oldTentacleAngle, â˜ƒ.tentacleAngle);
   }
}
