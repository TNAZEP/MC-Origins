package net.minecraft.client.renderer.entity.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.Deadmau5EarsLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class PlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public PlayerRenderer(EntityRendererProvider.Context var1, boolean var2) {
      super(â˜ƒ, new PlayerModel<>(â˜ƒ.bakeLayer(â˜ƒ ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), â˜ƒ), 0.5F);
      this.addLayer(
         new HumanoidArmorLayer<>(
            this,
            new HumanoidModel(â˜ƒ.bakeLayer(â˜ƒ ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)),
            new HumanoidModel(â˜ƒ.bakeLayer(â˜ƒ ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR))
         )
      );
      this.addLayer(new PlayerItemInHandLayer<>(this));
      this.addLayer(new ArrowLayer<>(â˜ƒ, this));
      this.addLayer(new Deadmau5EarsLayer(this));
      this.addLayer(new CapeLayer(this));
      this.addLayer(new CustomHeadLayer<>(this, â˜ƒ.getModelSet()));
      this.addLayer(new ElytraLayer<>(this, â˜ƒ.getModelSet()));
      this.addLayer(new ParrotOnShoulderLayer<>(this, â˜ƒ.getModelSet()));
      this.addLayer(new SpinAttackEffectLayer<>(this, â˜ƒ.getModelSet()));
      this.addLayer(new BeeStingerLayer<>(this));
   }

   public void render(AbstractClientPlayer var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      this.setModelProperties(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Vec3 getRenderOffset(AbstractClientPlayer var1, float var2) {
      return â˜ƒ.isCrouching() ? new Vec3(0.0, -0.125, 0.0) : super.getRenderOffset(â˜ƒ, â˜ƒ);
   }

   private void setModelProperties(AbstractClientPlayer var1) {
      PlayerModel<AbstractClientPlayer> â˜ƒ = this.getModel();
      if (â˜ƒ.isSpectator()) {
         â˜ƒ.setAllVisible(false);
         â˜ƒ.head.visible = true;
         â˜ƒ.hat.visible = true;
      } else {
         â˜ƒ.setAllVisible(true);
         â˜ƒ.hat.visible = â˜ƒ.isModelPartShown(PlayerModelPart.HAT);
         â˜ƒ.jacket.visible = â˜ƒ.isModelPartShown(PlayerModelPart.JACKET);
         â˜ƒ.leftPants.visible = â˜ƒ.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
         â˜ƒ.rightPants.visible = â˜ƒ.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
         â˜ƒ.leftSleeve.visible = â˜ƒ.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
         â˜ƒ.rightSleeve.visible = â˜ƒ.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
         â˜ƒ.crouching = â˜ƒ.isCrouching();
         HumanoidModel.ArmPose â˜ƒ = getArmPose(â˜ƒ, InteractionHand.MAIN_HAND);
         HumanoidModel.ArmPose â˜ƒx = getArmPose(â˜ƒ, InteractionHand.OFF_HAND);
         if (â˜ƒ.isTwoHanded()) {
            â˜ƒx = â˜ƒ.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
         }

         if (â˜ƒ.getMainArm() == HumanoidArm.RIGHT) {
            â˜ƒ.rightArmPose = â˜ƒ;
            â˜ƒ.leftArmPose = â˜ƒx;
         } else {
            â˜ƒ.rightArmPose = â˜ƒx;
            â˜ƒ.leftArmPose = â˜ƒ;
         }
      }
   }

   private static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer var0, InteractionHand var1) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         return HumanoidModel.ArmPose.EMPTY;
      } else {
         if (â˜ƒ.getUsedItemHand() == â˜ƒ && â˜ƒ.getUseItemRemainingTicks() > 0) {
            UseAnim â˜ƒ = â˜ƒ.getUseAnimation();
            if (â˜ƒ == UseAnim.BLOCK) {
               return HumanoidModel.ArmPose.BLOCK;
            }

            if (â˜ƒ == UseAnim.BOW) {
               return HumanoidModel.ArmPose.BOW_AND_ARROW;
            }

            if (â˜ƒ == UseAnim.SPEAR) {
               return HumanoidModel.ArmPose.THROW_SPEAR;
            }

            if (â˜ƒ == UseAnim.CROSSBOW && â˜ƒ == â˜ƒ.getUsedItemHand()) {
               return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }

            if (â˜ƒ == UseAnim.SPYGLASS) {
               return HumanoidModel.ArmPose.SPYGLASS;
            }
         } else if (!â˜ƒ.swinging && â˜ƒ.is(Items.CROSSBOW) && CrossbowItem.isCharged(â˜ƒ)) {
            return HumanoidModel.ArmPose.CROSSBOW_HOLD;
         }

         return HumanoidModel.ArmPose.ITEM;
      }
   }

   public ResourceLocation getTextureLocation(AbstractClientPlayer var1) {
      return â˜ƒ.getSkinTextureLocation();
   }

   protected void scale(AbstractClientPlayer var1, PoseStack var2, float var3) {
      float â˜ƒ = 0.9375F;
      â˜ƒ.scale(0.9375F, 0.9375F, 0.9375F);
   }

   protected void renderNameTag(AbstractClientPlayer var1, Component var2, PoseStack var3, MultiBufferSource var4, int var5) {
      double â˜ƒ = this.entityRenderDispatcher.distanceToSqr(â˜ƒ);
      â˜ƒ.pushPose();
      if (â˜ƒ < 100.0) {
         Scoreboard â˜ƒx = â˜ƒ.getScoreboard();
         Objective â˜ƒxx = â˜ƒx.getDisplayObjective(2);
         if (â˜ƒxx != null) {
            Score â˜ƒxxx = â˜ƒx.getOrCreatePlayerScore(â˜ƒ.getScoreboardName(), â˜ƒxx);
            super.renderNameTag(â˜ƒ, new TextComponent(Integer.toString(â˜ƒxxx.getScore())).append(" ").append(â˜ƒxx.getDisplayName()), â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.translate(0.0, (double)(9.0F * 1.15F * 0.025F), 0.0);
         }
      }

      super.renderNameTag(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }

   public void renderRightHand(PoseStack var1, MultiBufferSource var2, int var3, AbstractClientPlayer var4) {
      this.renderHand(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.model.rightArm, this.model.rightSleeve);
   }

   public void renderLeftHand(PoseStack var1, MultiBufferSource var2, int var3, AbstractClientPlayer var4) {
      this.renderHand(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.model.leftArm, this.model.leftSleeve);
   }

   private void renderHand(PoseStack var1, MultiBufferSource var2, int var3, AbstractClientPlayer var4, ModelPart var5, ModelPart var6) {
      PlayerModel<AbstractClientPlayer> â˜ƒ = this.getModel();
      this.setModelProperties(â˜ƒ);
      â˜ƒ.attackTime = 0.0F;
      â˜ƒ.crouching = false;
      â˜ƒ.swimAmount = 0.0F;
      â˜ƒ.setupAnim(â˜ƒ, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      â˜ƒ.xRot = 0.0F;
      â˜ƒ.render(â˜ƒ, â˜ƒ.getBuffer(RenderType.entitySolid(â˜ƒ.getSkinTextureLocation())), â˜ƒ, OverlayTexture.NO_OVERLAY);
      â˜ƒ.xRot = 0.0F;
      â˜ƒ.render(â˜ƒ, â˜ƒ.getBuffer(RenderType.entityTranslucent(â˜ƒ.getSkinTextureLocation())), â˜ƒ, OverlayTexture.NO_OVERLAY);
   }

   protected void setupRotations(AbstractClientPlayer var1, PoseStack var2, float var3, float var4, float var5) {
      float â˜ƒ = â˜ƒ.getSwimAmount(â˜ƒ);
      if (â˜ƒ.isFallFlying()) {
         super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒx = (float)â˜ƒ.getFallFlyingTicks() + â˜ƒ;
         float â˜ƒxx = Mth.clamp(â˜ƒx * â˜ƒx / 100.0F, 0.0F, 1.0F);
         if (!â˜ƒ.isAutoSpinAttack()) {
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxx * (-90.0F - â˜ƒ.getXRot())));
         }

         Vec3 â˜ƒx = â˜ƒ.getViewVector(â˜ƒ);
         Vec3 â˜ƒxx = â˜ƒ.getDeltaMovement();
         double â˜ƒxxx = â˜ƒxx.horizontalDistanceSqr();
         double â˜ƒxxxx = â˜ƒx.horizontalDistanceSqr();
         if (â˜ƒxxx > 0.0 && â˜ƒxxxx > 0.0) {
            double â˜ƒxxxxx = (â˜ƒxx.x * â˜ƒx.x + â˜ƒxx.z * â˜ƒx.z) / Math.sqrt(â˜ƒxxx * â˜ƒxxxx);
            double â˜ƒxxxxxx = â˜ƒxx.x * â˜ƒx.z - â˜ƒxx.z * â˜ƒx.x;
            â˜ƒ.mulPose(Vector3f.YP.rotation((float)(Math.signum(â˜ƒxxxxxx) * Math.acos(â˜ƒxxxxx))));
         }
      } else if (â˜ƒ > 0.0F) {
         super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒ = â˜ƒ.isInWater() ? -90.0F - â˜ƒ.getXRot() : -90.0F;
         float â˜ƒx = Mth.lerp(â˜ƒ, 0.0F, â˜ƒ);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒx));
         if (â˜ƒ.isVisuallySwimming()) {
            â˜ƒ.translate(0.0, -1.0, 0.3F);
         }
      } else {
         super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
