package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WitchItemLayer<T extends LivingEntity> extends CrossedArmsItemLayer<T, WitchModel<T>> {
   public WitchItemLayer(RenderLayerParent<T, WitchModel<T>> var1) {
      super(â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ItemStack â˜ƒ = â˜ƒ.getMainHandItem();
      â˜ƒ.pushPose();
      if (â˜ƒ.is(Items.POTION)) {
         this.getParentModel().getHead().translateAndRotate(â˜ƒ);
         this.getParentModel().getNose().translateAndRotate(â˜ƒ);
         â˜ƒ.translate(0.0625, 0.25, 0.0);
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(140.0F));
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(10.0F));
         â˜ƒ.translate(0.0, -0.4F, 0.4F);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }
}
