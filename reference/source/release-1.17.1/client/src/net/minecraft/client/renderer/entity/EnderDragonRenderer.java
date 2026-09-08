package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

public class EnderDragonRenderer extends EntityRenderer<EnderDragon> {
   public static final ResourceLocation CRYSTAL_BEAM_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
   private static final ResourceLocation DRAGON_EXPLODING_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation DRAGON_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   private static final ResourceLocation DRAGON_EYES_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(DRAGON_LOCATION);
   private static final RenderType DECAL = RenderType.entityDecal(DRAGON_LOCATION);
   private static final RenderType EYES = RenderType.eyes(DRAGON_EYES_LOCATION);
   private static final RenderType BEAM = RenderType.entitySmoothCutout(CRYSTAL_BEAM_LOCATION);
   private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0) / 2.0);
   private final EnderDragonRenderer.DragonModel model;

   public EnderDragonRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.shadowRadius = 0.5F;
      this.model = new EnderDragonRenderer.DragonModel(â˜ƒ.bakeLayer(ModelLayers.ENDER_DRAGON));
   }

   public void render(EnderDragon var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      float â˜ƒ = (float)â˜ƒ.getLatencyPos(7, â˜ƒ)[0];
      float â˜ƒx = (float)(â˜ƒ.getLatencyPos(5, â˜ƒ)[1] - â˜ƒ.getLatencyPos(10, â˜ƒ)[1]);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-â˜ƒ));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒx * 10.0F));
      â˜ƒ.translate(0.0, 0.0, 1.0);
      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      â˜ƒ.translate(0.0, -1.501F, 0.0);
      boolean â˜ƒxx = â˜ƒ.hurtTime > 0;
      this.model.prepareMobModel(â˜ƒ, 0.0F, 0.0F, â˜ƒ);
      if (â˜ƒ.dragonDeathTime > 0) {
         float â˜ƒxxx = (float)â˜ƒ.dragonDeathTime / 200.0F;
         VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(RenderType.dragonExplosionAlpha(DRAGON_EXPLODING_LOCATION));
         this.model.renderToBuffer(â˜ƒ, â˜ƒxxxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, â˜ƒxxx);
         VertexConsumer â˜ƒxxxxx = â˜ƒ.getBuffer(DECAL);
         this.model.renderToBuffer(â˜ƒ, â˜ƒxxxxx, â˜ƒ, OverlayTexture.pack(0.0F, â˜ƒxx), 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RENDER_TYPE);
         this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.pack(0.0F, â˜ƒxx), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(EYES);
      this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      if (â˜ƒ.dragonDeathTime > 0) {
         float â˜ƒx = ((float)â˜ƒ.dragonDeathTime + â˜ƒ) / 200.0F;
         float â˜ƒxx = Math.min(â˜ƒx > 0.8F ? (â˜ƒx - 0.8F) / 0.2F : 0.0F, 1.0F);
         Random â˜ƒxxx = new Random(432L);
         VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(RenderType.lightning());
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, -1.0, -2.0);

         for(int â˜ƒxxxxx = 0; (float)â˜ƒxxxxx < (â˜ƒx + â˜ƒx * â˜ƒx) / 2.0F * 60.0F; ++â˜ƒxxxxx) {
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxxx.nextFloat() * 360.0F));
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxxx.nextFloat() * 360.0F));
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒxxx.nextFloat() * 360.0F));
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxxx.nextFloat() * 360.0F));
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxxx.nextFloat() * 360.0F));
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒxxx.nextFloat() * 360.0F + â˜ƒx * 90.0F));
            float â˜ƒxxxxxx = â˜ƒxxx.nextFloat() * 20.0F + 5.0F + â˜ƒxx * 10.0F;
            float â˜ƒxxxxxxx = â˜ƒxxx.nextFloat() * 2.0F + 1.0F + â˜ƒxx * 2.0F;
            Matrix4f â˜ƒxxxxxxxx = â˜ƒ.last().pose();
            int â˜ƒxxxxxxxxx = (int)(255.0F * (1.0F - â˜ƒxx));
            vertex01(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            vertex2(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            vertex3(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            vertex01(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            vertex3(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            vertex4(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            vertex01(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            vertex4(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            vertex2(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
         }

         â˜ƒ.popPose();
      }

      â˜ƒ.popPose();
      if (â˜ƒ.nearestCrystal != null) {
         â˜ƒ.pushPose();
         float â˜ƒ = (float)(â˜ƒ.nearestCrystal.getX() - Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX()));
         float â˜ƒx = (float)(â˜ƒ.nearestCrystal.getY() - Mth.lerp((double)â˜ƒ, â˜ƒ.yo, â˜ƒ.getY()));
         float â˜ƒxx = (float)(â˜ƒ.nearestCrystal.getZ() - Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ()));
         renderCrystalBeams(â˜ƒ, â˜ƒx + EndCrystalRenderer.getY(â˜ƒ.nearestCrystal, â˜ƒ), â˜ƒxx, â˜ƒ, â˜ƒ.tickCount, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void vertex01(VertexConsumer var0, Matrix4f var1, int var2) {
      â˜ƒ.vertex(â˜ƒ, 0.0F, 0.0F, 0.0F).color(255, 255, 255, â˜ƒ).endVertex();
   }

   private static void vertex2(VertexConsumer var0, Matrix4f var1, float var2, float var3) {
      â˜ƒ.vertex(â˜ƒ, -HALF_SQRT_3 * â˜ƒ, â˜ƒ, -0.5F * â˜ƒ).color(255, 0, 255, 0).endVertex();
   }

   private static void vertex3(VertexConsumer var0, Matrix4f var1, float var2, float var3) {
      â˜ƒ.vertex(â˜ƒ, HALF_SQRT_3 * â˜ƒ, â˜ƒ, -0.5F * â˜ƒ).color(255, 0, 255, 0).endVertex();
   }

   private static void vertex4(VertexConsumer var0, Matrix4f var1, float var2, float var3) {
      â˜ƒ.vertex(â˜ƒ, 0.0F, â˜ƒ, 1.0F * â˜ƒ).color(255, 0, 255, 0).endVertex();
   }

   public static void renderCrystalBeams(float var0, float var1, float var2, float var3, int var4, PoseStack var5, MultiBufferSource var6, int var7) {
      float â˜ƒ = Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
      float â˜ƒx = Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 2.0, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotation((float)(-Math.atan2((double)â˜ƒ, (double)â˜ƒ)) - (float) (Math.PI / 2)));
      â˜ƒ.mulPose(Vector3f.XP.rotation((float)(-Math.atan2((double)â˜ƒ, (double)â˜ƒ)) - (float) (Math.PI / 2)));
      VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(BEAM);
      float â˜ƒxxx = 0.0F - ((float)â˜ƒ + â˜ƒ) * 0.01F;
      float â˜ƒxxxx = Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ) / 32.0F - ((float)â˜ƒ + â˜ƒ) * 0.01F;
      int â˜ƒxxxxx = 8;
      float â˜ƒxxxxxx = 0.0F;
      float â˜ƒxxxxxxx = 0.75F;
      float â˜ƒxxxxxxxx = 0.0F;
      PoseStack.Pose â˜ƒxxxxxxxxx = â˜ƒ.last();
      Matrix4f â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.pose();
      Matrix3f â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx.normal();

      for(int â˜ƒxxxxxxxxxxxx = 1; â˜ƒxxxxxxxxxxxx <= 8; ++â˜ƒxxxxxxxxxxxx) {
         float â˜ƒxxxxxxxxxxxxx = Mth.sin((float)â˜ƒxxxxxxxxxxxx * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float â˜ƒxxxxxxxxxxxxxx = Mth.cos((float)â˜ƒxxxxxxxxxxxx * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float â˜ƒxxxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxxxx / 8.0F;
         â˜ƒxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxx * 0.2F, â˜ƒxxxxxxx * 0.2F, 0.0F)
            .color(0, 0, 0, 255)
            .uv(â˜ƒxxxxxxxx, â˜ƒxxx)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(â˜ƒ)
            .normal(â˜ƒxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒx)
            .color(255, 255, 255, 255)
            .uv(â˜ƒxxxxxxxx, â˜ƒxxxx)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(â˜ƒ)
            .normal(â˜ƒxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒx)
            .color(255, 255, 255, 255)
            .uv(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxx)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(â˜ƒ)
            .normal(â˜ƒxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .endVertex();
         â˜ƒxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx * 0.2F, â˜ƒxxxxxxxxxxxxxx * 0.2F, 0.0F)
            .color(0, 0, 0, 255)
            .uv(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxx)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(â˜ƒ)
            .normal(â˜ƒxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .endVertex();
         â˜ƒxxxxxx = â˜ƒxxxxxxxxxxxxx;
         â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
         â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx;
      }

      â˜ƒ.popPose();
   }

   public ResourceLocation getTextureLocation(EnderDragon var1) {
      return DRAGON_LOCATION;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      float â˜ƒxx = -16.0F;
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .addBox("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44)
            .addBox("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30)
            .mirror()
            .addBox("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
            .addBox("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0)
            .mirror()
            .addBox("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
            .addBox("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0),
         PartPose.ZERO
      );
      â˜ƒxxx.addOrReplaceChild("jaw", CubeListBuilder.create().addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 176, 65), PartPose.offset(0.0F, 4.0F, -8.0F));
      â˜ƒx.addOrReplaceChild(
         "neck",
         CubeListBuilder.create().addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 192, 104).addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 48, 0),
         PartPose.ZERO
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0, 0)
            .addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 220, 53)
            .addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 220, 53)
            .addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 220, 53),
         PartPose.offset(0.0F, 4.0F, 8.0F)
      );
      PartDefinition â˜ƒxxxx = â˜ƒx.addOrReplaceChild(
         "left_wing",
         CubeListBuilder.create().mirror().addBox("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).addBox("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88),
         PartPose.offset(12.0F, 5.0F, 2.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "left_wing_tip",
         CubeListBuilder.create().mirror().addBox("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).addBox("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144),
         PartPose.offset(56.0F, 0.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxxx = â˜ƒx.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), PartPose.offset(12.0F, 20.0F, 2.0F)
      );
      PartDefinition â˜ƒxxxxxx = â˜ƒxxxxx.addOrReplaceChild(
         "left_front_leg_tip", CubeListBuilder.create().addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), PartPose.offset(0.0F, 20.0F, -1.0F)
      );
      â˜ƒxxxxxx.addOrReplaceChild(
         "left_front_foot", CubeListBuilder.create().addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), PartPose.offset(0.0F, 23.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxxxxx = â˜ƒx.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), PartPose.offset(16.0F, 16.0F, 42.0F)
      );
      PartDefinition â˜ƒxxxxxxxx = â˜ƒxxxxxxx.addOrReplaceChild(
         "left_hind_leg_tip", CubeListBuilder.create().addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), PartPose.offset(0.0F, 32.0F, -4.0F)
      );
      â˜ƒxxxxxxxx.addOrReplaceChild(
         "left_hind_foot", CubeListBuilder.create().addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), PartPose.offset(0.0F, 31.0F, 4.0F)
      );
      PartDefinition â˜ƒxxxxxxxxx = â˜ƒx.addOrReplaceChild(
         "right_wing",
         CubeListBuilder.create().addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88),
         PartPose.offset(-12.0F, 5.0F, 2.0F)
      );
      â˜ƒxxxxxxxxx.addOrReplaceChild(
         "right_wing_tip",
         CubeListBuilder.create().addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144),
         PartPose.offset(-56.0F, 0.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxxxxxxxx = â˜ƒx.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), PartPose.offset(-12.0F, 20.0F, 2.0F)
      );
      PartDefinition â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.addOrReplaceChild(
         "right_front_leg_tip", CubeListBuilder.create().addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), PartPose.offset(0.0F, 20.0F, -1.0F)
      );
      â˜ƒxxxxxxxxxxx.addOrReplaceChild(
         "right_front_foot", CubeListBuilder.create().addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), PartPose.offset(0.0F, 23.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxxxxxxxxxx = â˜ƒx.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), PartPose.offset(-16.0F, 16.0F, 42.0F)
      );
      PartDefinition â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.addOrReplaceChild(
         "right_hind_leg_tip", CubeListBuilder.create().addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), PartPose.offset(0.0F, 32.0F, -4.0F)
      );
      â˜ƒxxxxxxxxxxxxx.addOrReplaceChild(
         "right_hind_foot", CubeListBuilder.create().addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), PartPose.offset(0.0F, 31.0F, 4.0F)
      );
      return LayerDefinition.create(â˜ƒ, 256, 256);
   }

   public static class DragonModel extends EntityModel<EnderDragon> {
      private final ModelPart head;
      private final ModelPart neck;
      private final ModelPart jaw;
      private final ModelPart body;
      private final ModelPart leftWing;
      private final ModelPart leftWingTip;
      private final ModelPart leftFrontLeg;
      private final ModelPart leftFrontLegTip;
      private final ModelPart leftFrontFoot;
      private final ModelPart leftRearLeg;
      private final ModelPart leftRearLegTip;
      private final ModelPart leftRearFoot;
      private final ModelPart rightWing;
      private final ModelPart rightWingTip;
      private final ModelPart rightFrontLeg;
      private final ModelPart rightFrontLegTip;
      private final ModelPart rightFrontFoot;
      private final ModelPart rightRearLeg;
      private final ModelPart rightRearLegTip;
      private final ModelPart rightRearFoot;
      @Nullable
      private EnderDragon entity;
      private float a;

      public DragonModel(ModelPart var1) {
         this.head = â˜ƒ.getChild("head");
         this.jaw = this.head.getChild("jaw");
         this.neck = â˜ƒ.getChild("neck");
         this.body = â˜ƒ.getChild("body");
         this.leftWing = â˜ƒ.getChild("left_wing");
         this.leftWingTip = this.leftWing.getChild("left_wing_tip");
         this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
         this.leftFrontLegTip = this.leftFrontLeg.getChild("left_front_leg_tip");
         this.leftFrontFoot = this.leftFrontLegTip.getChild("left_front_foot");
         this.leftRearLeg = â˜ƒ.getChild("left_hind_leg");
         this.leftRearLegTip = this.leftRearLeg.getChild("left_hind_leg_tip");
         this.leftRearFoot = this.leftRearLegTip.getChild("left_hind_foot");
         this.rightWing = â˜ƒ.getChild("right_wing");
         this.rightWingTip = this.rightWing.getChild("right_wing_tip");
         this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
         this.rightFrontLegTip = this.rightFrontLeg.getChild("right_front_leg_tip");
         this.rightFrontFoot = this.rightFrontLegTip.getChild("right_front_foot");
         this.rightRearLeg = â˜ƒ.getChild("right_hind_leg");
         this.rightRearLegTip = this.rightRearLeg.getChild("right_hind_leg_tip");
         this.rightRearFoot = this.rightRearLegTip.getChild("right_hind_foot");
      }

      public void prepareMobModel(EnderDragon var1, float var2, float var3, float var4) {
         this.entity = â˜ƒ;
         this.a = â˜ƒ;
      }

      public void setupAnim(EnderDragon var1, float var2, float var3, float var4, float var5, float var6) {
      }

      @Override
      public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         â˜ƒ.pushPose();
         float â˜ƒ = Mth.lerp(this.a, this.entity.oFlapTime, this.entity.flapTime);
         this.jaw.xRot = (float)(Math.sin((double)(â˜ƒ * (float) (Math.PI * 2))) + 1.0) * 0.2F;
         float â˜ƒx = (float)(Math.sin((double)(â˜ƒ * (float) (Math.PI * 2) - 1.0F)) + 1.0);
         â˜ƒx = (â˜ƒx * â˜ƒx + â˜ƒx * 2.0F) * 0.05F;
         â˜ƒ.translate(0.0, (double)(â˜ƒx - 2.0F), -3.0);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒx * 2.0F));
         float â˜ƒxx = 0.0F;
         float â˜ƒxxx = 20.0F;
         float â˜ƒxxxx = -12.0F;
         float â˜ƒxxxxx = 1.5F;
         double[] â˜ƒxxxxxx = this.entity.getLatencyPos(6, this.a);
         float â˜ƒxxxxxxx = Mth.rotWrap(this.entity.getLatencyPos(5, this.a)[0] - this.entity.getLatencyPos(10, this.a)[0]);
         float â˜ƒxxxxxxxx = Mth.rotWrap(this.entity.getLatencyPos(5, this.a)[0] + (double)(â˜ƒxxxxxxx / 2.0F));
         float â˜ƒxxxxxxxxx = â˜ƒ * (float) (Math.PI * 2);

         for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < 5; ++â˜ƒxxxxxxxxxx) {
            double[] â˜ƒxxxxxxxxxxx = this.entity.getLatencyPos(5 - â˜ƒxxxxxxxxxx, this.a);
            float â˜ƒxxxxxxxxxxxx = (float)Math.cos((double)((float)â˜ƒxxxxxxxxxx * 0.45F + â˜ƒxxxxxxxxx)) * 0.15F;
            this.neck.yRot = Mth.rotWrap(â˜ƒxxxxxxxxxxx[0] - â˜ƒxxxxxx[0]) * (float) (Math.PI / 180.0) * 1.5F;
            this.neck.xRot = â˜ƒxxxxxxxxxxxx
               + this.entity.getHeadPartYOffset(â˜ƒxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.neck.zRot = -Mth.rotWrap(â˜ƒxxxxxxxxxxx[0] - (double)â˜ƒxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
            this.neck.y = â˜ƒxxx;
            this.neck.z = â˜ƒxxxx;
            this.neck.x = â˜ƒxx;
            â˜ƒxxx = (float)((double)â˜ƒxxx + Math.sin((double)this.neck.xRot) * 10.0);
            â˜ƒxxxx = (float)((double)â˜ƒxxxx - Math.cos((double)this.neck.yRot) * Math.cos((double)this.neck.xRot) * 10.0);
            â˜ƒxx = (float)((double)â˜ƒxx - Math.sin((double)this.neck.yRot) * Math.cos((double)this.neck.xRot) * 10.0);
            this.neck.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
         }

         this.head.y = â˜ƒxxx;
         this.head.z = â˜ƒxxxx;
         this.head.x = â˜ƒxx;
         double[] â˜ƒxxxxxxxxxx = this.entity.getLatencyPos(0, this.a);
         this.head.yRot = Mth.rotWrap(â˜ƒxxxxxxxxxx[0] - â˜ƒxxxxxx[0]) * (float) (Math.PI / 180.0);
         this.head.xRot = Mth.rotWrap((double)this.entity.getHeadPartYOffset(6, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx)) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
         this.head.zRot = -Mth.rotWrap(â˜ƒxxxxxxxxxx[0] - (double)â˜ƒxxxxxxxx) * (float) (Math.PI / 180.0);
         this.head.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, 1.0, 0.0);
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(-â˜ƒxxxxxxx * 1.5F));
         â˜ƒ.translate(0.0, -1.0, 0.0);
         this.body.zRot = 0.0F;
         this.body.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
         float â˜ƒxxxxxxxxxxx = â˜ƒ * (float) (Math.PI * 2);
         this.leftWing.xRot = 0.125F - (float)Math.cos((double)â˜ƒxxxxxxxxxxx) * 0.2F;
         this.leftWing.yRot = -0.25F;
         this.leftWing.zRot = -((float)(Math.sin((double)â˜ƒxxxxxxxxxxx) + 0.125)) * 0.8F;
         this.leftWingTip.zRot = (float)(Math.sin((double)(â˜ƒxxxxxxxxxxx + 2.0F)) + 0.5) * 0.75F;
         this.rightWing.xRot = this.leftWing.xRot;
         this.rightWing.yRot = -this.leftWing.yRot;
         this.rightWing.zRot = -this.leftWing.zRot;
         this.rightWingTip.zRot = -this.leftWingTip.zRot;
         this.renderSide(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒx,
            this.leftWing,
            this.leftFrontLeg,
            this.leftFrontLegTip,
            this.leftFrontFoot,
            this.leftRearLeg,
            this.leftRearLegTip,
            this.leftRearFoot,
            â˜ƒ
         );
         this.renderSide(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒx,
            this.rightWing,
            this.rightFrontLeg,
            this.rightFrontLegTip,
            this.rightFrontFoot,
            this.rightRearLeg,
            this.rightRearLegTip,
            this.rightRearFoot,
            â˜ƒ
         );
         â˜ƒ.popPose();
         float â˜ƒxxxxxxxxxxxx = -((float)Math.sin((double)(â˜ƒ * (float) (Math.PI * 2)))) * 0.0F;
         â˜ƒxxxxxxxxx = â˜ƒ * (float) (Math.PI * 2);
         â˜ƒxxx = 10.0F;
         â˜ƒxxxx = 60.0F;
         â˜ƒxx = 0.0F;
         â˜ƒxxxxxx = this.entity.getLatencyPos(11, this.a);

         for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx < 12; ++â˜ƒxxxxxxxxxxxxx) {
            â˜ƒxxxxxxxxxx = this.entity.getLatencyPos(12 + â˜ƒxxxxxxxxxxxxx, this.a);
            â˜ƒxxxxxxxxxxxx = (float)((double)â˜ƒxxxxxxxxxxxx + Math.sin((double)((float)â˜ƒxxxxxxxxxxxxx * 0.45F + â˜ƒxxxxxxxxx)) * 0.05F);
            this.neck.yRot = (Mth.rotWrap(â˜ƒxxxxxxxxxx[0] - â˜ƒxxxxxx[0]) * 1.5F + 180.0F) * (float) (Math.PI / 180.0);
            this.neck.xRot = â˜ƒxxxxxxxxxxxx + (float)(â˜ƒxxxxxxxxxx[1] - â˜ƒxxxxxx[1]) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.neck.zRot = Mth.rotWrap(â˜ƒxxxxxxxxxx[0] - (double)â˜ƒxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
            this.neck.y = â˜ƒxxx;
            this.neck.z = â˜ƒxxxx;
            this.neck.x = â˜ƒxx;
            â˜ƒxxx = (float)((double)â˜ƒxxx + Math.sin((double)this.neck.xRot) * 10.0);
            â˜ƒxxxx = (float)((double)â˜ƒxxxx - Math.cos((double)this.neck.yRot) * Math.cos((double)this.neck.xRot) * 10.0);
            â˜ƒxx = (float)((double)â˜ƒxx - Math.sin((double)this.neck.yRot) * Math.cos((double)this.neck.xRot) * 10.0);
            this.neck.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
         }

         â˜ƒ.popPose();
      }

      private void renderSide(
         PoseStack var1,
         VertexConsumer var2,
         int var3,
         int var4,
         float var5,
         ModelPart var6,
         ModelPart var7,
         ModelPart var8,
         ModelPart var9,
         ModelPart var10,
         ModelPart var11,
         ModelPart var12,
         float var13
      ) {
         â˜ƒ.xRot = 1.0F + â˜ƒ * 0.1F;
         â˜ƒ.xRot = 0.5F + â˜ƒ * 0.1F;
         â˜ƒ.xRot = 0.75F + â˜ƒ * 0.1F;
         â˜ƒ.xRot = 1.3F + â˜ƒ * 0.1F;
         â˜ƒ.xRot = -0.5F - â˜ƒ * 0.1F;
         â˜ƒ.xRot = 0.75F + â˜ƒ * 0.1F;
         â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
         â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
         â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, â˜ƒ);
      }
   }
}
