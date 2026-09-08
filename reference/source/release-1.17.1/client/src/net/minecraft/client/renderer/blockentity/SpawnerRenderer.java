package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class SpawnerRenderer implements BlockEntityRenderer<SpawnerBlockEntity> {
   public SpawnerRenderer(BlockEntityRendererProvider.Context var1) {
   }

   public void render(SpawnerBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.5, 0.0, 0.5);
      BaseSpawner â˜ƒ = â˜ƒ.getSpawner();
      Entity â˜ƒx = â˜ƒ.getOrCreateDisplayEntity(â˜ƒ.getLevel());
      if (â˜ƒx != null) {
         float â˜ƒxx = 0.53125F;
         float â˜ƒxxx = Math.max(â˜ƒx.getBbWidth(), â˜ƒx.getBbHeight());
         if ((double)â˜ƒxxx > 1.0) {
            â˜ƒxx /= â˜ƒxxx;
         }

         â˜ƒ.translate(0.0, 0.4F, 0.0);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)Mth.lerp((double)â˜ƒ, â˜ƒ.getoSpin(), â˜ƒ.getSpin()) * 10.0F));
         â˜ƒ.translate(0.0, -0.2F, 0.0);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-30.0F));
         â˜ƒ.scale(â˜ƒxx, â˜ƒxx, â˜ƒxx);
         Minecraft.getInstance().getEntityRenderDispatcher().render(â˜ƒx, 0.0, 0.0, 0.0, 0.0F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      â˜ƒ.popPose();
   }
}
