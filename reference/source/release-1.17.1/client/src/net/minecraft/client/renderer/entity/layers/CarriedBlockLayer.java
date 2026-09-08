package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;

public class CarriedBlockLayer extends RenderLayer<EnderMan, EndermanModel<EnderMan>> {
   public CarriedBlockLayer(RenderLayerParent<EnderMan, EndermanModel<EnderMan>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, EnderMan var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      BlockState â˜ƒ = â˜ƒ.getCarriedBlock();
      if (â˜ƒ != null) {
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 0.6875, -0.75);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(20.0F));
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(45.0F));
         â˜ƒ.translate(0.25, 0.1875, 0.25);
         float â˜ƒx = 0.5F;
         â˜ƒ.scale(-0.5F, -0.5F, 0.5F);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(90.0F));
         Minecraft.getInstance().getBlockRenderer().renderSingleBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY);
         â˜ƒ.popPose();
      }
   }
}
