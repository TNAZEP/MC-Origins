package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class CatRenderer extends MobRenderer<Cat, CatModel<Cat>> {
   public CatRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new CatModel<>(â˜ƒ.bakeLayer(ModelLayers.CAT)), 0.4F);
      this.addLayer(new CatCollarLayer(this, â˜ƒ.getModelSet()));
   }

   public ResourceLocation getTextureLocation(Cat var1) {
      return â˜ƒ.getResourceLocation();
   }

   protected void scale(Cat var1, PoseStack var2, float var3) {
      super.scale(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.scale(0.8F, 0.8F, 0.8F);
   }

   protected void setupRotations(Cat var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = â˜ƒ.getLieDownAmount(â˜ƒ);
      if (â˜ƒ > 0.0F) {
         â˜ƒ.translate((double)(0.4F * â˜ƒ), (double)(0.15F * â˜ƒ), (double)(0.1F * â˜ƒ));
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(Mth.rotLerp(â˜ƒ, 0.0F, 90.0F)));
         BlockPos â˜ƒx = â˜ƒ.blockPosition();

         for(Player â˜ƒxx : â˜ƒ.level.getEntitiesOfClass(Player.class, new AABB(â˜ƒx).inflate(2.0, 2.0, 2.0))) {
            if (â˜ƒxx.isSleeping()) {
               â˜ƒ.translate((double)(0.15F * â˜ƒ), 0.0, 0.0);
               break;
            }
         }
      }
   }
}
