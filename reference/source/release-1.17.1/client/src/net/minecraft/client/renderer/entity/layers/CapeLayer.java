package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CapeLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public CapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> var1) {
      super(â˜ƒ);
   }

   public void render(
      PoseStack var1, MultiBufferSource var2, int var3, AbstractClientPlayer var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      if (â˜ƒ.isCapeLoaded() && !â˜ƒ.isInvisible() && â˜ƒ.isModelPartShown(PlayerModelPart.CAPE) && â˜ƒ.getCloakTextureLocation() != null) {
         ItemStack â˜ƒ = â˜ƒ.getItemBySlot(EquipmentSlot.CHEST);
         if (!â˜ƒ.is(Items.ELYTRA)) {
            â˜ƒ.pushPose();
            â˜ƒ.translate(0.0, 0.0, 0.125);
            double â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.xCloakO, â˜ƒ.xCloak) - Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX());
            double â˜ƒxx = Mth.lerp((double)â˜ƒ, â˜ƒ.yCloakO, â˜ƒ.yCloak) - Mth.lerp((double)â˜ƒ, â˜ƒ.yo, â˜ƒ.getY());
            double â˜ƒxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zCloakO, â˜ƒ.zCloak) - Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ());
            float â˜ƒxxxx = â˜ƒ.yBodyRotO + (â˜ƒ.yBodyRot - â˜ƒ.yBodyRotO);
            double â˜ƒxxxxx = (double)Mth.sin(â˜ƒxxxx * (float) (Math.PI / 180.0));
            double â˜ƒxxxxxx = (double)(-Mth.cos(â˜ƒxxxx * (float) (Math.PI / 180.0)));
            float â˜ƒxxxxxxx = (float)â˜ƒxx * 10.0F;
            â˜ƒxxxxxxx = Mth.clamp(â˜ƒxxxxxxx, -6.0F, 32.0F);
            float â˜ƒxxxxxxxx = (float)(â˜ƒx * â˜ƒxxxxx + â˜ƒxxx * â˜ƒxxxxxx) * 100.0F;
            â˜ƒxxxxxxxx = Mth.clamp(â˜ƒxxxxxxxx, 0.0F, 150.0F);
            float â˜ƒxxxxxxxxx = (float)(â˜ƒx * â˜ƒxxxxxx - â˜ƒxxx * â˜ƒxxxxx) * 100.0F;
            â˜ƒxxxxxxxxx = Mth.clamp(â˜ƒxxxxxxxxx, -20.0F, 20.0F);
            if (â˜ƒxxxxxxxx < 0.0F) {
               â˜ƒxxxxxxxx = 0.0F;
            }

            float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.oBob, â˜ƒ.bob);
            â˜ƒxxxxxxx += Mth.sin(Mth.lerp(â˜ƒ, â˜ƒ.walkDistO, â˜ƒ.walkDist) * 6.0F) * 32.0F * â˜ƒx;
            if (â˜ƒ.isCrouching()) {
               â˜ƒxxxxxxx += 25.0F;
            }

            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(6.0F + â˜ƒxxxxxxxx / 2.0F + â˜ƒxxxxxxx));
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒxxxxxxxxx / 2.0F));
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒxxxxxxxxx / 2.0F));
            VertexConsumer â˜ƒx = â˜ƒ.getBuffer(RenderType.entitySolid(â˜ƒ.getCloakTextureLocation()));
            this.getParentModel().renderCloak(â˜ƒ, â˜ƒx, â˜ƒ, OverlayTexture.NO_OVERLAY);
            â˜ƒ.popPose();
         }
      }
   }
}
