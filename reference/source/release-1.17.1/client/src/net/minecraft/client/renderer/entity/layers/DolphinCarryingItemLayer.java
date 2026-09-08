package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.item.ItemStack;

public class DolphinCarryingItemLayer extends RenderLayer<Dolphin, DolphinModel<Dolphin>> {
   public DolphinCarryingItemLayer(RenderLayerParent<Dolphin, DolphinModel<Dolphin>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Dolphin var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      boolean â˜ƒ = â˜ƒ.getMainArm() == HumanoidArm.RIGHT;
      â˜ƒ.pushPose();
      float â˜ƒx = 1.0F;
      float â˜ƒxx = -1.0F;
      float â˜ƒxxx = Mth.abs(â˜ƒ.getXRot()) / 60.0F;
      if (â˜ƒ.getXRot() < 0.0F) {
         â˜ƒ.translate(0.0, (double)(1.0F - â˜ƒxxx * 0.5F), (double)(-1.0F + â˜ƒxxx * 0.5F));
      } else {
         â˜ƒ.translate(0.0, (double)(1.0F + â˜ƒxxx * 0.8F), (double)(-1.0F + â˜ƒxxx * 0.2F));
      }

      ItemStack â˜ƒ = â˜ƒ ? â˜ƒ.getMainHandItem() : â˜ƒ.getOffhandItem();
      Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, ItemTransforms.TransformType.GROUND, false, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }
}
