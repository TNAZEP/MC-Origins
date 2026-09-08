package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class StuckInBodyLayer<T extends LivingEntity, M extends PlayerModel<T>> extends RenderLayer<T, M> {
   public StuckInBodyLayer(LivingEntityRenderer<T, M> var1) {
      super(â˜ƒ);
   }

   protected abstract int numStuck(T var1);

   protected abstract void renderStuckItem(PoseStack var1, MultiBufferSource var2, int var3, Entity var4, float var5, float var6, float var7, float var8);

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      int â˜ƒ = this.numStuck(â˜ƒ);
      Random â˜ƒx = new Random((long)â˜ƒ.getId());
      if (â˜ƒ > 0) {
         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            â˜ƒ.pushPose();
            ModelPart â˜ƒxxx = this.getParentModel().getRandomModelPart(â˜ƒx);
            ModelPart.Cube â˜ƒxxxx = â˜ƒxxx.getRandomCube(â˜ƒx);
            â˜ƒxxx.translateAndRotate(â˜ƒ);
            float â˜ƒxxxxx = â˜ƒx.nextFloat();
            float â˜ƒxxxxxx = â˜ƒx.nextFloat();
            float â˜ƒxxxxxxx = â˜ƒx.nextFloat();
            float â˜ƒxxxxxxxx = Mth.lerp(â˜ƒxxxxx, â˜ƒxxxx.minX, â˜ƒxxxx.maxX) / 16.0F;
            float â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒxxxxxx, â˜ƒxxxx.minY, â˜ƒxxxx.maxY) / 16.0F;
            float â˜ƒxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxx, â˜ƒxxxx.minZ, â˜ƒxxxx.maxZ) / 16.0F;
            â˜ƒ.translate((double)â˜ƒxxxxxxxx, (double)â˜ƒxxxxxxxxx, (double)â˜ƒxxxxxxxxxx);
            â˜ƒxxxxx = -1.0F * (â˜ƒxxxxx * 2.0F - 1.0F);
            â˜ƒxxxxxx = -1.0F * (â˜ƒxxxxxx * 2.0F - 1.0F);
            â˜ƒxxxxxxx = -1.0F * (â˜ƒxxxxxxx * 2.0F - 1.0F);
            this.renderStuckItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒ);
            â˜ƒ.popPose();
         }
      }
   }
}
