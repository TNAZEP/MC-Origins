package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PlayerItemInHandLayer<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> {
   private static final float X_ROT_MIN = (float) (-Math.PI / 6);
   private static final float X_ROT_MAX = (float) (Math.PI / 2);

   public PlayerItemInHandLayer(RenderLayerParent<T, M> var1) {
      super(â˜ƒ);
   }

   @Override
   protected void renderArmWithItem(
      LivingEntity var1, ItemStack var2, ItemTransforms.TransformType var3, HumanoidArm var4, PoseStack var5, MultiBufferSource var6, int var7
   ) {
      if (â˜ƒ.is(Items.SPYGLASS) && â˜ƒ.getUseItem() == â˜ƒ && â˜ƒ.swingTime == 0) {
         this.renderArmWithSpyglass(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         super.renderArmWithItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void renderArmWithSpyglass(LivingEntity var1, ItemStack var2, HumanoidArm var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      ModelPart â˜ƒ = this.getParentModel().getHead();
      float â˜ƒx = â˜ƒ.xRot;
      â˜ƒ.xRot = Mth.clamp(â˜ƒ.xRot, (float) (-Math.PI / 6), (float) (Math.PI / 2));
      â˜ƒ.translateAndRotate(â˜ƒ);
      â˜ƒ.xRot = â˜ƒx;
      CustomHeadLayer.translateToHead(â˜ƒ, false);
      boolean â˜ƒxx = â˜ƒ == HumanoidArm.LEFT;
      â˜ƒ.translate((double)((â˜ƒxx ? -2.5F : 2.5F) / 16.0F), -0.0625, 0.0);
      Minecraft.getInstance().getItemInHandRenderer().renderItem(â˜ƒ, â˜ƒ, ItemTransforms.TransformType.HEAD, false, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }
}
