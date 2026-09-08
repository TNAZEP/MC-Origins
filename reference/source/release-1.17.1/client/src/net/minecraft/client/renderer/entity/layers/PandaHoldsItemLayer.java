package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.item.ItemStack;

public class PandaHoldsItemLayer extends RenderLayer<Panda, PandaModel<Panda>> {
   public PandaHoldsItemLayer(RenderLayerParent<Panda, PandaModel<Panda>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Panda var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ItemStack â˜ƒ = â˜ƒ.getItemBySlot(EquipmentSlot.MAINHAND);
      if (â˜ƒ.isSitting() && !â˜ƒ.isScared()) {
         float â˜ƒx = -0.6F;
         float â˜ƒxx = 1.4F;
         if (â˜ƒ.isEating()) {
            â˜ƒx -= 0.2F * Mth.sin(â˜ƒ * 0.6F) + 0.2F;
            â˜ƒxx -= 0.09F * Mth.sin(â˜ƒ * 0.6F);
         }

         â˜ƒ.pushPose();
         â˜ƒ.translate(0.1F, (double)â˜ƒxx, (double)â˜ƒx);
         Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, ItemTransforms.TransformType.GROUND, false, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }
   }
}
