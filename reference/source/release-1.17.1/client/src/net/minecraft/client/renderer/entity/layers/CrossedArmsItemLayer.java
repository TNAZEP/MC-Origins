package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CrossedArmsItemLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public CrossedArmsItemLayer(RenderLayerParent<T, M> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.4F, -0.4F);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(180.0F));
      ItemStack â˜ƒ = â˜ƒ.getItemBySlot(EquipmentSlot.MAINHAND);
      Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, ItemTransforms.TransformType.GROUND, false, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }
}
