package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.block.Blocks;

public class IronGolemFlowerLayer extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
   public IronGolemFlowerLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, IronGolem var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (â˜ƒ.getOfferFlowerTick() != 0) {
         â˜ƒ.pushPose();
         ModelPart â˜ƒ = this.getParentModel().getFlowerHoldingArm();
         â˜ƒ.translateAndRotate(â˜ƒ);
         â˜ƒ.translate(-1.1875, 1.0625, -0.9375);
         â˜ƒ.translate(0.5, 0.5, 0.5);
         float â˜ƒx = 0.5F;
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
         â˜ƒ.translate(-0.5, -0.5, -0.5);
         Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.POPPY.defaultBlockState(), â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY);
         â˜ƒ.popPose();
      }
   }
}
