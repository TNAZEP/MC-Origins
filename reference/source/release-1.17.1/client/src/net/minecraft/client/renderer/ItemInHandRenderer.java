package net.minecraft.client.renderer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class ItemInHandRenderer {
   private static final RenderType MAP_BACKGROUND = RenderType.text(new ResourceLocation("textures/map/map_background.png"));
   private static final RenderType MAP_BACKGROUND_CHECKERBOARD = RenderType.text(new ResourceLocation("textures/map/map_background_checkerboard.png"));
   private static final float ITEM_SWING_X_POS_SCALE = -0.4F;
   private static final float ITEM_SWING_Y_POS_SCALE = 0.2F;
   private static final float ITEM_SWING_Z_POS_SCALE = -0.2F;
   private static final float ITEM_HEIGHT_SCALE = -0.6F;
   private static final float ITEM_POS_X = 0.56F;
   private static final float ITEM_POS_Y = -0.52F;
   private static final float ITEM_POS_Z = -0.72F;
   private static final float ITEM_PRESWING_ROT_Y = 45.0F;
   private static final float ITEM_SWING_X_ROT_AMOUNT = -80.0F;
   private static final float ITEM_SWING_Y_ROT_AMOUNT = -20.0F;
   private static final float ITEM_SWING_Z_ROT_AMOUNT = -20.0F;
   private static final float EAT_JIGGLE_X_ROT_AMOUNT = 10.0F;
   private static final float EAT_JIGGLE_Y_ROT_AMOUNT = 90.0F;
   private static final float EAT_JIGGLE_Z_ROT_AMOUNT = 30.0F;
   private static final float EAT_JIGGLE_X_POS_SCALE = 0.6F;
   private static final float EAT_JIGGLE_Y_POS_SCALE = -0.5F;
   private static final float EAT_JIGGLE_Z_POS_SCALE = 0.0F;
   private static final double EAT_JIGGLE_EXPONENT = 27.0;
   private static final float EAT_EXTRA_JIGGLE_CUTOFF = 0.8F;
   private static final float EAT_EXTRA_JIGGLE_SCALE = 0.1F;
   private static final float ARM_SWING_X_POS_SCALE = -0.3F;
   private static final float ARM_SWING_Y_POS_SCALE = 0.4F;
   private static final float ARM_SWING_Z_POS_SCALE = -0.4F;
   private static final float ARM_SWING_Y_ROT_AMOUNT = 70.0F;
   private static final float ARM_SWING_Z_ROT_AMOUNT = -20.0F;
   private static final float ARM_HEIGHT_SCALE = -0.6F;
   private static final float ARM_POS_SCALE = 0.8F;
   private static final float ARM_POS_X = 0.8F;
   private static final float ARM_POS_Y = -0.75F;
   private static final float ARM_POS_Z = -0.9F;
   private static final float ARM_PRESWING_ROT_Y = 45.0F;
   private static final float ARM_PREROTATION_X_OFFSET = -1.0F;
   private static final float ARM_PREROTATION_Y_OFFSET = 3.6F;
   private static final float ARM_PREROTATION_Z_OFFSET = 3.5F;
   private static final float ARM_POSTROTATION_X_OFFSET = 5.6F;
   private static final int ARM_ROT_X = 200;
   private static final int ARM_ROT_Y = -135;
   private static final int ARM_ROT_Z = 120;
   private static final float MAP_SWING_X_POS_SCALE = -0.4F;
   private static final float MAP_SWING_Z_POS_SCALE = -0.2F;
   private static final float MAP_HANDS_POS_X = 0.0F;
   private static final float MAP_HANDS_POS_Y = 0.04F;
   private static final float MAP_HANDS_POS_Z = -0.72F;
   private static final float MAP_HANDS_HEIGHT_SCALE = -1.2F;
   private static final float MAP_HANDS_TILT_SCALE = -0.5F;
   private static final float MAP_PLAYER_PITCH_SCALE = 45.0F;
   private static final float MAP_HANDS_Z_ROT_AMOUNT = -85.0F;
   private static final float MAPHAND_X_ROT_AMOUNT = 45.0F;
   private static final float MAPHAND_Y_ROT_AMOUNT = 92.0F;
   private static final float MAPHAND_Z_ROT_AMOUNT = -41.0F;
   private static final float MAP_HAND_X_POS = 0.3F;
   private static final float MAP_HAND_Y_POS = -1.1F;
   private static final float MAP_HAND_Z_POS = 0.45F;
   private static final float MAP_SWING_X_ROT_AMOUNT = 20.0F;
   private static final float MAP_PRE_ROT_SCALE = 0.38F;
   private static final float MAP_GLOBAL_X_POS = -0.5F;
   private static final float MAP_GLOBAL_Y_POS = -0.5F;
   private static final float MAP_GLOBAL_Z_POS = 0.0F;
   private static final float MAP_FINAL_SCALE = 0.0078125F;
   private static final int MAP_BORDER = 7;
   private static final int MAP_HEIGHT = 128;
   private static final int MAP_WIDTH = 128;
   private static final float BOW_CHARGE_X_POS_SCALE = 0.0F;
   private static final float BOW_CHARGE_Y_POS_SCALE = 0.0F;
   private static final float BOW_CHARGE_Z_POS_SCALE = 0.04F;
   private static final float BOW_CHARGE_SHAKE_X_SCALE = 0.0F;
   private static final float BOW_CHARGE_SHAKE_Y_SCALE = 0.004F;
   private static final float BOW_CHARGE_SHAKE_Z_SCALE = 0.0F;
   private static final float BOW_CHARGE_Z_SCALE = 0.2F;
   private static final float BOW_MIN_SHAKE_CHARGE = 0.1F;
   private final Minecraft minecraft;
   private ItemStack mainHandItem = ItemStack.EMPTY;
   private ItemStack offHandItem = ItemStack.EMPTY;
   private float mainHandHeight;
   private float oMainHandHeight;
   private float offHandHeight;
   private float oOffHandHeight;
   private final EntityRenderDispatcher entityRenderDispatcher;
   private final ItemRenderer itemRenderer;

   public ItemInHandRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
      this.entityRenderDispatcher = â˜ƒ.getEntityRenderDispatcher();
      this.itemRenderer = â˜ƒ.getItemRenderer();
   }

   public void renderItem(LivingEntity var1, ItemStack var2, ItemTransforms.TransformType var3, boolean var4, PoseStack var5, MultiBufferSource var6, int var7) {
      if (!â˜ƒ.isEmpty()) {
         this.itemRenderer.renderStatic(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.level, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒ.getId() + â˜ƒ.ordinal());
      }
   }

   private float calculateMapTilt(float var1) {
      float â˜ƒ = 1.0F - â˜ƒ / 45.0F + 0.1F;
      â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
      return -Mth.cos(â˜ƒ * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void renderMapHand(PoseStack var1, MultiBufferSource var2, int var3, HumanoidArm var4) {
      RenderSystem.setShaderTexture(0, this.minecraft.player.getSkinTextureLocation());
      PlayerRenderer â˜ƒ = (PlayerRenderer)this.entityRenderDispatcher.<AbstractClientPlayer>getRenderer(this.minecraft.player);
      â˜ƒ.pushPose();
      float â˜ƒx = â˜ƒ == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(92.0F));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(45.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒx * -41.0F));
      â˜ƒ.translate((double)(â˜ƒx * 0.3F), -1.1F, 0.45F);
      if (â˜ƒ == HumanoidArm.RIGHT) {
         â˜ƒ.renderRightHand(â˜ƒ, â˜ƒ, â˜ƒ, this.minecraft.player);
      } else {
         â˜ƒ.renderLeftHand(â˜ƒ, â˜ƒ, â˜ƒ, this.minecraft.player);
      }

      â˜ƒ.popPose();
   }

   private void renderOneHandedMap(PoseStack var1, MultiBufferSource var2, int var3, float var4, HumanoidArm var5, float var6, ItemStack var7) {
      float â˜ƒ = â˜ƒ == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      â˜ƒ.translate((double)(â˜ƒ * 0.125F), -0.125, 0.0);
      if (!this.minecraft.player.isInvisible()) {
         â˜ƒ.pushPose();
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒ * 10.0F));
         this.renderPlayerArm(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }

      â˜ƒ.pushPose();
      â˜ƒ.translate((double)(â˜ƒ * 0.51F), (double)(-0.08F + â˜ƒ * -1.2F), -0.75);
      float â˜ƒ = Mth.sqrt(â˜ƒ);
      float â˜ƒx = Mth.sin(â˜ƒ * (float) Math.PI);
      float â˜ƒxx = -0.5F * â˜ƒx;
      float â˜ƒxxx = 0.4F * Mth.sin(â˜ƒ * (float) (Math.PI * 2));
      float â˜ƒxxxx = -0.3F * Mth.sin(â˜ƒ * (float) Math.PI);
      â˜ƒ.translate((double)(â˜ƒ * â˜ƒxx), (double)(â˜ƒxxx - 0.3F * â˜ƒx), (double)â˜ƒxxxx);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒx * -45.0F));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ * â˜ƒx * -30.0F));
      this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }

   private void renderTwoHandedMap(PoseStack var1, MultiBufferSource var2, int var3, float var4, float var5, float var6) {
      float â˜ƒ = Mth.sqrt(â˜ƒ);
      float â˜ƒx = -0.2F * Mth.sin(â˜ƒ * (float) Math.PI);
      float â˜ƒxx = -0.4F * Mth.sin(â˜ƒ * (float) Math.PI);
      â˜ƒ.translate(0.0, (double)(-â˜ƒx / 2.0F), (double)â˜ƒxx);
      float â˜ƒxxx = this.calculateMapTilt(â˜ƒ);
      â˜ƒ.translate(0.0, (double)(0.04F + â˜ƒ * -1.2F + â˜ƒxxx * -0.5F), -0.72F);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxxx * -85.0F));
      if (!this.minecraft.player.isInvisible()) {
         â˜ƒ.pushPose();
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(90.0F));
         this.renderMapHand(â˜ƒ, â˜ƒ, â˜ƒ, HumanoidArm.RIGHT);
         this.renderMapHand(â˜ƒ, â˜ƒ, â˜ƒ, HumanoidArm.LEFT);
         â˜ƒ.popPose();
      }

      float â˜ƒ = Mth.sin(â˜ƒ * (float) Math.PI);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ * 20.0F));
      â˜ƒ.scale(2.0F, 2.0F, 2.0F);
      this.renderMap(â˜ƒ, â˜ƒ, â˜ƒ, this.mainHandItem);
   }

   private void renderMap(PoseStack var1, MultiBufferSource var2, int var3, ItemStack var4) {
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
      â˜ƒ.scale(0.38F, 0.38F, 0.38F);
      â˜ƒ.translate(-0.5, -0.5, 0.0);
      â˜ƒ.scale(0.0078125F, 0.0078125F, 0.0078125F);
      Integer â˜ƒ = MapItem.getMapId(â˜ƒ);
      MapItemSavedData â˜ƒx = MapItem.getSavedData(â˜ƒ, this.minecraft.level);
      VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(â˜ƒx == null ? MAP_BACKGROUND : MAP_BACKGROUND_CHECKERBOARD);
      Matrix4f â˜ƒxxx = â˜ƒ.last().pose();
      â˜ƒxx.vertex(â˜ƒxxx, -7.0F, 135.0F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(â˜ƒ).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx, 135.0F, 135.0F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(â˜ƒ).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx, 135.0F, -7.0F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(â˜ƒ).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx, -7.0F, -7.0F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(â˜ƒ).endVertex();
      if (â˜ƒx != null) {
         this.minecraft.gameRenderer.getMapRenderer().render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, false, â˜ƒ);
      }
   }

   private void renderPlayerArm(PoseStack var1, MultiBufferSource var2, int var3, float var4, float var5, HumanoidArm var6) {
      boolean â˜ƒ = â˜ƒ != HumanoidArm.LEFT;
      float â˜ƒx = â˜ƒ ? 1.0F : -1.0F;
      float â˜ƒxx = Mth.sqrt(â˜ƒ);
      float â˜ƒxxx = -0.3F * Mth.sin(â˜ƒxx * (float) Math.PI);
      float â˜ƒxxxx = 0.4F * Mth.sin(â˜ƒxx * (float) (Math.PI * 2));
      float â˜ƒxxxxx = -0.4F * Mth.sin(â˜ƒ * (float) Math.PI);
      â˜ƒ.translate((double)(â˜ƒx * (â˜ƒxxx + 0.64000005F)), (double)(â˜ƒxxxx + -0.6F + â˜ƒ * -0.6F), (double)(â˜ƒxxxxx + -0.71999997F));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx * 45.0F));
      float â˜ƒxxxxxx = Mth.sin(â˜ƒ * â˜ƒ * (float) Math.PI);
      float â˜ƒxxxxxxx = Mth.sin(â˜ƒxx * (float) Math.PI);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx * â˜ƒxxxxxxx * 70.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒx * â˜ƒxxxxxx * -20.0F));
      AbstractClientPlayer â˜ƒxxxxxxxx = this.minecraft.player;
      RenderSystem.setShaderTexture(0, â˜ƒxxxxxxxx.getSkinTextureLocation());
      â˜ƒ.translate((double)(â˜ƒx * -1.0F), 3.6F, 3.5);
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒx * 120.0F));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(200.0F));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx * -135.0F));
      â˜ƒ.translate((double)(â˜ƒx * 5.6F), 0.0, 0.0);
      PlayerRenderer â˜ƒxxxxxxxxx = (PlayerRenderer)this.entityRenderDispatcher.<AbstractClientPlayer>getRenderer(â˜ƒxxxxxxxx);
      if (â˜ƒ) {
         â˜ƒxxxxxxxxx.renderRightHand(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxx);
      } else {
         â˜ƒxxxxxxxxx.renderLeftHand(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxx);
      }
   }

   private void applyEatTransform(PoseStack var1, float var2, HumanoidArm var3, ItemStack var4) {
      float â˜ƒ = (float)this.minecraft.player.getUseItemRemainingTicks() - â˜ƒ + 1.0F;
      float â˜ƒx = â˜ƒ / (float)â˜ƒ.getUseDuration();
      if (â˜ƒx < 0.8F) {
         float â˜ƒxx = Mth.abs(Mth.cos(â˜ƒ / 4.0F * (float) Math.PI) * 0.1F);
         â˜ƒ.translate(0.0, (double)â˜ƒxx, 0.0);
      }

      float â˜ƒ = 1.0F - (float)Math.pow((double)â˜ƒx, 27.0);
      int â˜ƒx = â˜ƒ == HumanoidArm.RIGHT ? 1 : -1;
      â˜ƒ.translate((double)(â˜ƒ * 0.6F * (float)â˜ƒx), (double)(â˜ƒ * -0.5F), (double)(â˜ƒ * 0.0F));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒx * â˜ƒ * 90.0F));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ * 10.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒx * â˜ƒ * 30.0F));
   }

   private void applyItemArmAttackTransform(PoseStack var1, HumanoidArm var2, float var3) {
      int â˜ƒ = â˜ƒ == HumanoidArm.RIGHT ? 1 : -1;
      float â˜ƒx = Mth.sin(â˜ƒ * â˜ƒ * (float) Math.PI);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒ * (45.0F + â˜ƒx * -20.0F)));
      float â˜ƒxx = Mth.sin(Mth.sqrt(â˜ƒ) * (float) Math.PI);
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒ * â˜ƒxx * -20.0F));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxx * -80.0F));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒ * -45.0F));
   }

   private void applyItemArmTransform(PoseStack var1, HumanoidArm var2, float var3) {
      int â˜ƒ = â˜ƒ == HumanoidArm.RIGHT ? 1 : -1;
      â˜ƒ.translate((double)((float)â˜ƒ * 0.56F), (double)(-0.52F + â˜ƒ * -0.6F), -0.72F);
   }

   public void renderHandsWithItems(float var1, PoseStack var2, MultiBufferSource.BufferSource var3, LocalPlayer var4, int var5) {
      float â˜ƒ = â˜ƒ.getAttackAnim(â˜ƒ);
      InteractionHand â˜ƒx = MoreObjects.firstNonNull(â˜ƒ.swingingArm, InteractionHand.MAIN_HAND);
      float â˜ƒxx = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
      ItemInHandRenderer.HandRenderSelection â˜ƒxxx = evaluateWhichHandsToRender(â˜ƒ);
      float â˜ƒxxxx = Mth.lerp(â˜ƒ, â˜ƒ.xBobO, â˜ƒ.xBob);
      float â˜ƒxxxxx = Mth.lerp(â˜ƒ, â˜ƒ.yBobO, â˜ƒ.yBob);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees((â˜ƒ.getViewXRot(â˜ƒ) - â˜ƒxxxx) * 0.1F));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((â˜ƒ.getViewYRot(â˜ƒ) - â˜ƒxxxxx) * 0.1F));
      if (â˜ƒxxx.renderMainHand) {
         float â˜ƒxxxxxx = â˜ƒx == InteractionHand.MAIN_HAND ? â˜ƒ : 0.0F;
         float â˜ƒxxxxxxx = 1.0F - Mth.lerp(â˜ƒ, this.oMainHandHeight, this.mainHandHeight);
         this.renderArmWithItem(â˜ƒ, â˜ƒ, â˜ƒxx, InteractionHand.MAIN_HAND, â˜ƒxxxxxx, this.mainHandItem, â˜ƒxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      if (â˜ƒxxx.renderOffHand) {
         float â˜ƒ = â˜ƒx == InteractionHand.OFF_HAND ? â˜ƒ : 0.0F;
         float â˜ƒx = 1.0F - Mth.lerp(â˜ƒ, this.oOffHandHeight, this.offHandHeight);
         this.renderArmWithItem(â˜ƒ, â˜ƒ, â˜ƒxx, InteractionHand.OFF_HAND, â˜ƒ, this.offHandItem, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      â˜ƒ.endBatch();
   }

   @VisibleForTesting
   static ItemInHandRenderer.HandRenderSelection evaluateWhichHandsToRender(LocalPlayer var0) {
      ItemStack â˜ƒ = â˜ƒ.getMainHandItem();
      ItemStack â˜ƒx = â˜ƒ.getOffhandItem();
      boolean â˜ƒxx = â˜ƒ.is(Items.BOW) || â˜ƒx.is(Items.BOW);
      boolean â˜ƒxxx = â˜ƒ.is(Items.CROSSBOW) || â˜ƒx.is(Items.CROSSBOW);
      if (!â˜ƒxx && !â˜ƒxxx) {
         return ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS;
      } else if (â˜ƒ.isUsingItem()) {
         return selectionUsingItemWhileHoldingBowLike(â˜ƒ);
      } else {
         return isChargedCrossbow(â˜ƒ)
            ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY
            : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS;
      }
   }

   private static ItemInHandRenderer.HandRenderSelection selectionUsingItemWhileHoldingBowLike(LocalPlayer var0) {
      ItemStack â˜ƒ = â˜ƒ.getUseItem();
      InteractionHand â˜ƒx = â˜ƒ.getUsedItemHand();
      if (!â˜ƒ.is(Items.BOW) && !â˜ƒ.is(Items.CROSSBOW)) {
         return â˜ƒx == InteractionHand.MAIN_HAND && isChargedCrossbow(â˜ƒ.getOffhandItem())
            ? ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY
            : ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS;
      } else {
         return ItemInHandRenderer.HandRenderSelection.onlyForHand(â˜ƒx);
      }
   }

   private static boolean isChargedCrossbow(ItemStack var0) {
      return â˜ƒ.is(Items.CROSSBOW) && CrossbowItem.isCharged(â˜ƒ);
   }

   private void renderArmWithItem(
      AbstractClientPlayer var1,
      float var2,
      float var3,
      InteractionHand var4,
      float var5,
      ItemStack var6,
      float var7,
      PoseStack var8,
      MultiBufferSource var9,
      int var10
   ) {
      if (!â˜ƒ.isScoping()) {
         boolean â˜ƒ = â˜ƒ == InteractionHand.MAIN_HAND;
         HumanoidArm â˜ƒx = â˜ƒ ? â˜ƒ.getMainArm() : â˜ƒ.getMainArm().getOpposite();
         â˜ƒ.pushPose();
         if (â˜ƒ.isEmpty()) {
            if (â˜ƒ && !â˜ƒ.isInvisible()) {
               this.renderPlayerArm(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            }
         } else if (â˜ƒ.is(Items.FILLED_MAP)) {
            if (â˜ƒ && this.offHandItem.isEmpty()) {
               this.renderTwoHandedMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            } else {
               this.renderOneHandedMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
            }
         } else if (â˜ƒ.is(Items.CROSSBOW)) {
            boolean â˜ƒ = CrossbowItem.isCharged(â˜ƒ);
            boolean â˜ƒx = â˜ƒx == HumanoidArm.RIGHT;
            int â˜ƒxx = â˜ƒx ? 1 : -1;
            if (â˜ƒ.isUsingItem() && â˜ƒ.getUseItemRemainingTicks() > 0 && â˜ƒ.getUsedItemHand() == â˜ƒ) {
               this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
               â˜ƒ.translate((double)((float)â˜ƒxx * -0.4785682F), -0.094387F, 0.05731531F);
               â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-11.935F));
               â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒxx * 65.3F));
               â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒxx * -9.785F));
               float â˜ƒxxx = (float)â˜ƒ.getUseDuration() - ((float)this.minecraft.player.getUseItemRemainingTicks() - â˜ƒ + 1.0F);
               float â˜ƒxxxx = â˜ƒxxx / (float)CrossbowItem.getChargeDuration(â˜ƒ);
               if (â˜ƒxxxx > 1.0F) {
                  â˜ƒxxxx = 1.0F;
               }

               if (â˜ƒxxxx > 0.1F) {
                  float â˜ƒxxx = Mth.sin((â˜ƒxxx - 0.1F) * 1.3F);
                  float â˜ƒxxxx = â˜ƒxxxx - 0.1F;
                  float â˜ƒxxxxx = â˜ƒxxx * â˜ƒxxxx;
                  â˜ƒ.translate((double)(â˜ƒxxxxx * 0.0F), (double)(â˜ƒxxxxx * 0.004F), (double)(â˜ƒxxxxx * 0.0F));
               }

               â˜ƒ.translate((double)(â˜ƒxxxx * 0.0F), (double)(â˜ƒxxxx * 0.0F), (double)(â˜ƒxxxx * 0.04F));
               â˜ƒ.scale(1.0F, 1.0F, 1.0F + â˜ƒxxxx * 0.2F);
               â˜ƒ.mulPose(Vector3f.YN.rotationDegrees((float)â˜ƒxx * 45.0F));
            } else {
               float â˜ƒ = -0.4F * Mth.sin(Mth.sqrt(â˜ƒ) * (float) Math.PI);
               float â˜ƒx = 0.2F * Mth.sin(Mth.sqrt(â˜ƒ) * (float) (Math.PI * 2));
               float â˜ƒxx = -0.2F * Mth.sin(â˜ƒ * (float) Math.PI);
               â˜ƒ.translate((double)((float)â˜ƒxx * â˜ƒ), (double)â˜ƒx, (double)â˜ƒxx);
               this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
               this.applyItemArmAttackTransform(â˜ƒ, â˜ƒx, â˜ƒ);
               if (â˜ƒ && â˜ƒ < 0.001F && â˜ƒ) {
                  â˜ƒ.translate((double)((float)â˜ƒxx * -0.641864F), 0.0, 0.0);
                  â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒxx * 10.0F));
               }
            }

            this.renderItem(
               â˜ƒ,
               â˜ƒ,
               â˜ƒx ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND,
               !â˜ƒx,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ
            );
         } else {
            boolean â˜ƒ = â˜ƒx == HumanoidArm.RIGHT;
            if (â˜ƒ.isUsingItem() && â˜ƒ.getUseItemRemainingTicks() > 0 && â˜ƒ.getUsedItemHand() == â˜ƒ) {
               int â˜ƒx = â˜ƒ ? 1 : -1;
               switch(â˜ƒ.getUseAnimation()) {
                  case NONE:
                     this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
                     break;
                  case EAT:
                  case DRINK:
                     this.applyEatTransform(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
                     this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
                     break;
                  case BLOCK:
                     this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
                     break;
                  case BOW:
                     this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
                     â˜ƒ.translate((double)((float)â˜ƒx * -0.2785682F), 0.18344387F, 0.15731531F);
                     â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-13.935F));
                     â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒx * 35.3F));
                     â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒx * -9.785F));
                     float â˜ƒxx = (float)â˜ƒ.getUseDuration() - ((float)this.minecraft.player.getUseItemRemainingTicks() - â˜ƒ + 1.0F);
                     float â˜ƒxxx = â˜ƒxx / 20.0F;
                     â˜ƒxxx = (â˜ƒxxx * â˜ƒxxx + â˜ƒxxx * 2.0F) / 3.0F;
                     if (â˜ƒxxx > 1.0F) {
                        â˜ƒxxx = 1.0F;
                     }

                     if (â˜ƒxxx > 0.1F) {
                        float â˜ƒxx = Mth.sin((â˜ƒxx - 0.1F) * 1.3F);
                        float â˜ƒxxx = â˜ƒxxx - 0.1F;
                        float â˜ƒxxxx = â˜ƒxx * â˜ƒxxx;
                        â˜ƒ.translate((double)(â˜ƒxxxx * 0.0F), (double)(â˜ƒxxxx * 0.004F), (double)(â˜ƒxxxx * 0.0F));
                     }

                     â˜ƒ.translate((double)(â˜ƒxxx * 0.0F), (double)(â˜ƒxxx * 0.0F), (double)(â˜ƒxxx * 0.04F));
                     â˜ƒ.scale(1.0F, 1.0F, 1.0F + â˜ƒxxx * 0.2F);
                     â˜ƒ.mulPose(Vector3f.YN.rotationDegrees((float)â˜ƒx * 45.0F));
                     break;
                  case SPEAR:
                     this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
                     â˜ƒ.translate((double)((float)â˜ƒx * -0.5F), 0.7F, 0.1F);
                     â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-55.0F));
                     â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒx * 35.3F));
                     â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒx * -9.785F));
                     float â˜ƒxx = (float)â˜ƒ.getUseDuration() - ((float)this.minecraft.player.getUseItemRemainingTicks() - â˜ƒ + 1.0F);
                     float â˜ƒxxx = â˜ƒxx / 10.0F;
                     if (â˜ƒxxx > 1.0F) {
                        â˜ƒxxx = 1.0F;
                     }

                     if (â˜ƒxxx > 0.1F) {
                        float â˜ƒxx = Mth.sin((â˜ƒxx - 0.1F) * 1.3F);
                        float â˜ƒxxx = â˜ƒxxx - 0.1F;
                        float â˜ƒxxxx = â˜ƒxx * â˜ƒxxx;
                        â˜ƒ.translate((double)(â˜ƒxxxx * 0.0F), (double)(â˜ƒxxxx * 0.004F), (double)(â˜ƒxxxx * 0.0F));
                     }

                     â˜ƒ.translate(0.0, 0.0, (double)(â˜ƒxxx * 0.2F));
                     â˜ƒ.scale(1.0F, 1.0F, 1.0F + â˜ƒxxx * 0.2F);
                     â˜ƒ.mulPose(Vector3f.YN.rotationDegrees((float)â˜ƒx * 45.0F));
               }
            } else if (â˜ƒ.isAutoSpinAttack()) {
               this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
               int â˜ƒ = â˜ƒ ? 1 : -1;
               â˜ƒ.translate((double)((float)â˜ƒ * -0.4F), 0.8F, 0.3F);
               â˜ƒ.mulPose(Vector3f.YP.rotationDegrees((float)â˜ƒ * 65.0F));
               â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)â˜ƒ * -85.0F));
            } else {
               float â˜ƒ = -0.4F * Mth.sin(Mth.sqrt(â˜ƒ) * (float) Math.PI);
               float â˜ƒx = 0.2F * Mth.sin(Mth.sqrt(â˜ƒ) * (float) (Math.PI * 2));
               float â˜ƒxx = -0.2F * Mth.sin(â˜ƒ * (float) Math.PI);
               int â˜ƒxxx = â˜ƒ ? 1 : -1;
               â˜ƒ.translate((double)((float)â˜ƒxxx * â˜ƒ), (double)â˜ƒx, (double)â˜ƒxx);
               this.applyItemArmTransform(â˜ƒ, â˜ƒx, â˜ƒ);
               this.applyItemArmAttackTransform(â˜ƒ, â˜ƒx, â˜ƒ);
            }

            this.renderItem(
               â˜ƒ, â˜ƒ, â˜ƒ ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ
            );
         }

         â˜ƒ.popPose();
      }
   }

   public void tick() {
      this.oMainHandHeight = this.mainHandHeight;
      this.oOffHandHeight = this.offHandHeight;
      LocalPlayer â˜ƒ = this.minecraft.player;
      ItemStack â˜ƒx = â˜ƒ.getMainHandItem();
      ItemStack â˜ƒxx = â˜ƒ.getOffhandItem();
      if (ItemStack.matches(this.mainHandItem, â˜ƒx)) {
         this.mainHandItem = â˜ƒx;
      }

      if (ItemStack.matches(this.offHandItem, â˜ƒxx)) {
         this.offHandItem = â˜ƒxx;
      }

      if (â˜ƒ.isHandsBusy()) {
         this.mainHandHeight = Mth.clamp(this.mainHandHeight - 0.4F, 0.0F, 1.0F);
         this.offHandHeight = Mth.clamp(this.offHandHeight - 0.4F, 0.0F, 1.0F);
      } else {
         float â˜ƒ = â˜ƒ.getAttackStrengthScale(1.0F);
         this.mainHandHeight += Mth.clamp((this.mainHandItem == â˜ƒx ? â˜ƒ * â˜ƒ * â˜ƒ : 0.0F) - this.mainHandHeight, -0.4F, 0.4F);
         this.offHandHeight += Mth.clamp((float)(this.offHandItem == â˜ƒxx ? 1 : 0) - this.offHandHeight, -0.4F, 0.4F);
      }

      if (this.mainHandHeight < 0.1F) {
         this.mainHandItem = â˜ƒx;
      }

      if (this.offHandHeight < 0.1F) {
         this.offHandItem = â˜ƒxx;
      }
   }

   public void itemUsed(InteractionHand var1) {
      if (â˜ƒ == InteractionHand.MAIN_HAND) {
         this.mainHandHeight = 0.0F;
      } else {
         this.offHandHeight = 0.0F;
      }
   }

   @VisibleForTesting
   static enum HandRenderSelection {
      RENDER_BOTH_HANDS(true, true),
      RENDER_MAIN_HAND_ONLY(true, false),
      RENDER_OFF_HAND_ONLY(false, true);

      final boolean renderMainHand;
      final boolean renderOffHand;

      private HandRenderSelection(boolean var3, boolean var4) {
         this.renderMainHand = â˜ƒ;
         this.renderOffHand = â˜ƒ;
      }

      public static ItemInHandRenderer.HandRenderSelection onlyForHand(InteractionHand var0) {
         return â˜ƒ == InteractionHand.MAIN_HAND ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
      }
   }
}
