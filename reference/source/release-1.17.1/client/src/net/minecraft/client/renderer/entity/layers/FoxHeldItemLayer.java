package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;

public class FoxHeldItemLayer extends RenderLayer<Fox, FoxModel<Fox>> {
   public FoxHeldItemLayer(RenderLayerParent<Fox, FoxModel<Fox>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Fox var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      boolean â˜ƒ = â˜ƒ.isSleeping();
      boolean â˜ƒx = â˜ƒ.isBaby();
      â˜ƒ.pushPose();
      if (â˜ƒx) {
         float â˜ƒxx = 0.75F;
         â˜ƒ.scale(0.75F, 0.75F, 0.75F);
         â˜ƒ.translate(0.0, 0.5, 0.209375F);
      }

      â˜ƒ.translate(
         (double)(this.getParentModel().head.x / 16.0F), (double)(this.getParentModel().head.y / 16.0F), (double)(this.getParentModel().head.z / 16.0F)
      );
      float â˜ƒ = â˜ƒ.getHeadRollAngle(â˜ƒ);
      â˜ƒ.mulPose(Vector3f.ZP.rotation(â˜ƒ));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ));
      if (â˜ƒ.isBaby()) {
         if (â˜ƒ) {
            â˜ƒ.translate(0.4F, 0.26F, 0.15F);
         } else {
            â˜ƒ.translate(0.06F, 0.26F, -0.5);
         }
      } else if (â˜ƒ) {
         â˜ƒ.translate(0.46F, 0.26F, 0.22F);
      } else {
         â˜ƒ.translate(0.06F, 0.27F, -0.5);
      }

      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
      if (â˜ƒ) {
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
      }

      ItemStack â˜ƒ = â˜ƒ.getItemBySlot(EquipmentSlot.MAINHAND);
      Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, ItemTransforms.TransformType.GROUND, false, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }
}
