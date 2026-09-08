package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
   public ItemInHandLayer(RenderLayerParent<T, M> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      boolean â˜ƒ = â˜ƒ.getMainArm() == HumanoidArm.RIGHT;
      ItemStack â˜ƒx = â˜ƒ ? â˜ƒ.getOffhandItem() : â˜ƒ.getMainHandItem();
      ItemStack â˜ƒxx = â˜ƒ ? â˜ƒ.getMainHandItem() : â˜ƒ.getOffhandItem();
      if (!â˜ƒx.isEmpty() || !â˜ƒxx.isEmpty()) {
         â˜ƒ.pushPose();
         if (this.getParentModel().young) {
            float â˜ƒxxx = 0.5F;
            â˜ƒ.translate(0.0, 0.75, 0.0);
            â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         }

         this.renderArmWithItem(â˜ƒ, â˜ƒxx, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, â˜ƒ, â˜ƒ, â˜ƒ);
         this.renderArmWithItem(â˜ƒ, â˜ƒx, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }
   }

   protected void renderArmWithItem(
      LivingEntity var1, ItemStack var2, ItemTransforms.TransformType var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7
   ) {
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.pushPose();
         this.getParentModel().translateToHand(â˜ƒ, â˜ƒ);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         boolean â˜ƒ = â˜ƒ == HumanoidArm.LEFT;
         â˜ƒ.translate((double)((float)(â˜ƒ ? -1 : 1) / 16.0F), 0.125, -0.625);
         Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }
   }
}
