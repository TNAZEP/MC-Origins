package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class AxolotlModel<T extends Axolotl & LerpingModel> extends AgeableListModel<T> {
   public static final float SWIMMING_LEG_XROT = 1.8849558F;
   private final ModelPart tail;
   private final ModelPart leftHindLeg;
   private final ModelPart rightHindLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart topGills;
   private final ModelPart leftGills;
   private final ModelPart rightGills;

   public AxolotlModel(ModelPart var1) {
      super(true, 8.0F, 3.35F);
      this.body = â˜ƒ.getChild("body");
      this.head = this.body.getChild("head");
      this.rightHindLeg = this.body.getChild("right_hind_leg");
      this.leftHindLeg = this.body.getChild("left_hind_leg");
      this.rightFrontLeg = this.body.getChild("right_front_leg");
      this.leftFrontLeg = this.body.getChild("left_front_leg");
      this.tail = this.body.getChild("tail");
      this.topGills = this.head.getChild("top_gills");
      this.leftGills = this.head.getChild("left_gills");
      this.rightGills = this.head.getChild("right_gills");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 10.0F).texOffs(2, 17).addBox(0.0F, -3.0F, -8.0F, 0.0F, 5.0F, 9.0F),
         PartPose.offset(0.0F, 20.0F, 5.0F)
      );
      CubeDeformation â˜ƒxxx = new CubeDeformation(0.001F);
      PartDefinition â˜ƒxxxx = â˜ƒxx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 1).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 5.0F, 5.0F, â˜ƒxxx), PartPose.offset(0.0F, 0.0F, -9.0F)
      );
      CubeListBuilder â˜ƒxxxxx = CubeListBuilder.create().texOffs(3, 37).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 3.0F, 0.0F, â˜ƒxxx);
      CubeListBuilder â˜ƒxxxxxx = CubeListBuilder.create().texOffs(0, 40).addBox(-3.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, â˜ƒxxx);
      CubeListBuilder â˜ƒxxxxxxx = CubeListBuilder.create().texOffs(11, 40).addBox(0.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, â˜ƒxxx);
      â˜ƒxxxx.addOrReplaceChild("top_gills", â˜ƒxxxxx, PartPose.offset(0.0F, -3.0F, -1.0F));
      â˜ƒxxxx.addOrReplaceChild("left_gills", â˜ƒxxxxxx, PartPose.offset(-4.0F, 0.0F, -1.0F));
      â˜ƒxxxx.addOrReplaceChild("right_gills", â˜ƒxxxxxxx, PartPose.offset(4.0F, 0.0F, -1.0F));
      CubeListBuilder â˜ƒxxxxxxxx = CubeListBuilder.create().texOffs(2, 13).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, â˜ƒxxx);
      CubeListBuilder â˜ƒxxxxxxxxx = CubeListBuilder.create().texOffs(2, 13).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, â˜ƒxxx);
      â˜ƒxx.addOrReplaceChild("right_hind_leg", â˜ƒxxxxxxxxx, PartPose.offset(-3.5F, 1.0F, -1.0F));
      â˜ƒxx.addOrReplaceChild("left_hind_leg", â˜ƒxxxxxxxx, PartPose.offset(3.5F, 1.0F, -1.0F));
      â˜ƒxx.addOrReplaceChild("right_front_leg", â˜ƒxxxxxxxxx, PartPose.offset(-3.5F, 1.0F, -8.0F));
      â˜ƒxx.addOrReplaceChild("left_front_leg", â˜ƒxxxxxxxx, PartPose.offset(3.5F, 1.0F, -8.0F));
      â˜ƒxx.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(2, 19).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 12.0F), PartPose.offset(0.0F, 0.0F, 1.0F));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of();
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.body);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.setupInitialAnimationValues(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPlayingDead()) {
         this.setupPlayDeadAnimation(â˜ƒ);
         this.saveAnimationValues(â˜ƒ);
      } else {
         boolean â˜ƒ = â˜ƒ.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7
            || â˜ƒ.getXRot() != â˜ƒ.xRotO
            || â˜ƒ.getYRot() != â˜ƒ.yRotO
            || â˜ƒ.xOld != â˜ƒ.getX()
            || â˜ƒ.zOld != â˜ƒ.getZ();
         if (â˜ƒ.isInWaterOrBubble()) {
            if (â˜ƒ) {
               this.setupSwimmingAnimation(â˜ƒ, â˜ƒ);
            } else {
               this.setupWaterHoveringAnimation(â˜ƒ);
            }

            this.saveAnimationValues(â˜ƒ);
         } else {
            if (â˜ƒ.isOnGround()) {
               if (â˜ƒ) {
                  this.setupGroundCrawlingAnimation(â˜ƒ, â˜ƒ);
               } else {
                  this.setupLayStillOnGroundAnimation(â˜ƒ, â˜ƒ);
               }
            }

            this.saveAnimationValues(â˜ƒ);
         }
      }
   }

   private void saveAnimationValues(T var1) {
      Map<String, Vector3f> â˜ƒ = â˜ƒ.getModelRotationValues();
      â˜ƒ.put("body", this.getRotationVector(this.body));
      â˜ƒ.put("head", this.getRotationVector(this.head));
      â˜ƒ.put("right_hind_leg", this.getRotationVector(this.rightHindLeg));
      â˜ƒ.put("left_hind_leg", this.getRotationVector(this.leftHindLeg));
      â˜ƒ.put("right_front_leg", this.getRotationVector(this.rightFrontLeg));
      â˜ƒ.put("left_front_leg", this.getRotationVector(this.leftFrontLeg));
      â˜ƒ.put("tail", this.getRotationVector(this.tail));
      â˜ƒ.put("top_gills", this.getRotationVector(this.topGills));
      â˜ƒ.put("left_gills", this.getRotationVector(this.leftGills));
      â˜ƒ.put("right_gills", this.getRotationVector(this.rightGills));
   }

   private Vector3f getRotationVector(ModelPart var1) {
      return new Vector3f(â˜ƒ.xRot, â˜ƒ.yRot, â˜ƒ.zRot);
   }

   private void setRotationFromVector(ModelPart var1, Vector3f var2) {
      â˜ƒ.setRotation(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }

   private void setupInitialAnimationValues(T var1, float var2, float var3) {
      this.body.x = 0.0F;
      this.head.y = 0.0F;
      this.body.y = 20.0F;
      Map<String, Vector3f> â˜ƒ = â˜ƒ.getModelRotationValues();
      if (â˜ƒ.isEmpty()) {
         this.body.setRotation(â˜ƒ * (float) (Math.PI / 180.0), â˜ƒ * (float) (Math.PI / 180.0), 0.0F);
         this.head.setRotation(0.0F, 0.0F, 0.0F);
         this.leftHindLeg.setRotation(0.0F, 0.0F, 0.0F);
         this.rightHindLeg.setRotation(0.0F, 0.0F, 0.0F);
         this.leftFrontLeg.setRotation(0.0F, 0.0F, 0.0F);
         this.rightFrontLeg.setRotation(0.0F, 0.0F, 0.0F);
         this.leftGills.setRotation(0.0F, 0.0F, 0.0F);
         this.rightGills.setRotation(0.0F, 0.0F, 0.0F);
         this.topGills.setRotation(0.0F, 0.0F, 0.0F);
         this.tail.setRotation(0.0F, 0.0F, 0.0F);
      } else {
         this.setRotationFromVector(this.body, (Vector3f)â˜ƒ.get("body"));
         this.setRotationFromVector(this.head, (Vector3f)â˜ƒ.get("head"));
         this.setRotationFromVector(this.leftHindLeg, (Vector3f)â˜ƒ.get("left_hind_leg"));
         this.setRotationFromVector(this.rightHindLeg, (Vector3f)â˜ƒ.get("right_hind_leg"));
         this.setRotationFromVector(this.leftFrontLeg, (Vector3f)â˜ƒ.get("left_front_leg"));
         this.setRotationFromVector(this.rightFrontLeg, (Vector3f)â˜ƒ.get("right_front_leg"));
         this.setRotationFromVector(this.leftGills, (Vector3f)â˜ƒ.get("left_gills"));
         this.setRotationFromVector(this.rightGills, (Vector3f)â˜ƒ.get("right_gills"));
         this.setRotationFromVector(this.topGills, (Vector3f)â˜ƒ.get("top_gills"));
         this.setRotationFromVector(this.tail, (Vector3f)â˜ƒ.get("tail"));
      }
   }

   private float lerpTo(float var1, float var2) {
      return this.lerpTo(0.05F, â˜ƒ, â˜ƒ);
   }

   private float lerpTo(float var1, float var2, float var3) {
      return Mth.rotLerp(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void lerpPart(ModelPart var1, float var2, float var3, float var4) {
      â˜ƒ.setRotation(this.lerpTo(â˜ƒ.xRot, â˜ƒ), this.lerpTo(â˜ƒ.yRot, â˜ƒ), this.lerpTo(â˜ƒ.zRot, â˜ƒ));
   }

   private void setupLayStillOnGroundAnimation(float var1, float var2) {
      float â˜ƒ = â˜ƒ * 0.09F;
      float â˜ƒx = Mth.sin(â˜ƒ);
      float â˜ƒxx = Mth.cos(â˜ƒ);
      float â˜ƒxxx = â˜ƒx * â˜ƒx - 2.0F * â˜ƒx;
      float â˜ƒxxxx = â˜ƒxx * â˜ƒxx - 3.0F * â˜ƒx;
      this.head.xRot = this.lerpTo(this.head.xRot, -0.09F * â˜ƒxxx);
      this.head.yRot = this.lerpTo(this.head.yRot, 0.0F);
      this.head.zRot = this.lerpTo(this.head.zRot, -0.2F);
      this.tail.yRot = this.lerpTo(this.tail.yRot, -0.1F + 0.1F * â˜ƒxxx);
      this.topGills.xRot = this.lerpTo(this.topGills.xRot, 0.6F + 0.05F * â˜ƒxxxx);
      this.leftGills.yRot = this.lerpTo(this.leftGills.yRot, -this.topGills.xRot);
      this.rightGills.yRot = this.lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
      this.lerpPart(this.leftHindLeg, 1.1F, 1.0F, 0.0F);
      this.lerpPart(this.leftFrontLeg, 0.8F, 2.3F, -0.5F);
      this.applyMirrorLegRotations();
      this.body.xRot = this.lerpTo(0.2F, this.body.xRot, 0.0F);
      this.body.yRot = this.lerpTo(this.body.yRot, â˜ƒ * (float) (Math.PI / 180.0));
      this.body.zRot = this.lerpTo(this.body.zRot, 0.0F);
   }

   private void setupGroundCrawlingAnimation(float var1, float var2) {
      float â˜ƒ = â˜ƒ * 0.11F;
      float â˜ƒx = Mth.cos(â˜ƒ);
      float â˜ƒxx = (â˜ƒx * â˜ƒx - 2.0F * â˜ƒx) / 5.0F;
      float â˜ƒxxx = 0.7F * â˜ƒx;
      this.head.xRot = this.lerpTo(this.head.xRot, 0.0F);
      this.head.yRot = this.lerpTo(this.head.yRot, 0.09F * â˜ƒx);
      this.head.zRot = this.lerpTo(this.head.zRot, 0.0F);
      this.tail.yRot = this.lerpTo(this.tail.yRot, this.head.yRot);
      this.topGills.xRot = this.lerpTo(this.topGills.xRot, 0.6F - 0.08F * (â˜ƒx * â˜ƒx + 2.0F * Mth.sin(â˜ƒ)));
      this.leftGills.yRot = this.lerpTo(this.leftGills.yRot, -this.topGills.xRot);
      this.rightGills.yRot = this.lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
      this.lerpPart(this.leftHindLeg, 0.9424779F, 1.5F - â˜ƒxx, -0.1F);
      this.lerpPart(this.leftFrontLeg, 1.0995574F, (float) (Math.PI / 2) - â˜ƒxxx, 0.0F);
      this.lerpPart(this.rightHindLeg, this.leftHindLeg.xRot, -1.0F - â˜ƒxx, 0.0F);
      this.lerpPart(this.rightFrontLeg, this.leftFrontLeg.xRot, (float) (-Math.PI / 2) - â˜ƒxxx, 0.0F);
      this.body.xRot = this.lerpTo(0.2F, this.body.xRot, 0.0F);
      this.body.yRot = this.lerpTo(this.body.yRot, â˜ƒ * (float) (Math.PI / 180.0));
      this.body.zRot = this.lerpTo(this.body.zRot, 0.0F);
   }

   private void setupWaterHoveringAnimation(float var1) {
      float â˜ƒ = â˜ƒ * 0.075F;
      float â˜ƒx = Mth.cos(â˜ƒ);
      float â˜ƒxx = Mth.sin(â˜ƒ) * 0.15F;
      this.body.xRot = this.lerpTo(this.body.xRot, -0.15F + 0.075F * â˜ƒx);
      this.body.y -= â˜ƒxx;
      this.head.xRot = this.lerpTo(this.head.xRot, -this.body.xRot);
      this.topGills.xRot = this.lerpTo(this.topGills.xRot, 0.2F * â˜ƒx);
      this.leftGills.yRot = this.lerpTo(this.leftGills.yRot, -0.3F * â˜ƒx - 0.19F);
      this.rightGills.yRot = this.lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
      this.lerpPart(this.leftHindLeg, (float) (Math.PI * 3.0 / 4.0) - â˜ƒx * 0.11F, 0.47123894F, 1.7278761F);
      this.lerpPart(this.leftFrontLeg, (float) (Math.PI / 4) - â˜ƒx * 0.2F, 2.042035F, 0.0F);
      this.applyMirrorLegRotations();
      this.tail.yRot = this.lerpTo(this.tail.yRot, 0.5F * â˜ƒx);
      this.head.yRot = this.lerpTo(this.head.yRot, 0.0F);
      this.head.zRot = this.lerpTo(this.head.zRot, 0.0F);
   }

   private void setupSwimmingAnimation(float var1, float var2) {
      float â˜ƒ = â˜ƒ * 0.33F;
      float â˜ƒx = Mth.sin(â˜ƒ);
      float â˜ƒxx = Mth.cos(â˜ƒ);
      float â˜ƒxxx = 0.13F * â˜ƒx;
      this.body.xRot = this.lerpTo(0.1F, this.body.xRot, â˜ƒ * (float) (Math.PI / 180.0) + â˜ƒxxx);
      this.head.xRot = -â˜ƒxxx * 1.8F;
      this.body.y -= 0.45F * â˜ƒxx;
      this.topGills.xRot = this.lerpTo(this.topGills.xRot, -0.5F * â˜ƒx - 0.8F);
      this.leftGills.yRot = this.lerpTo(this.leftGills.yRot, 0.3F * â˜ƒx + 0.9F);
      this.rightGills.yRot = this.lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
      this.tail.yRot = this.lerpTo(this.tail.yRot, 0.3F * Mth.cos(â˜ƒ * 0.9F));
      this.lerpPart(this.leftHindLeg, 1.8849558F, -0.4F * â˜ƒx, (float) (Math.PI / 2));
      this.lerpPart(this.leftFrontLeg, 1.8849558F, -0.2F * â˜ƒxx - 0.1F, (float) (Math.PI / 2));
      this.applyMirrorLegRotations();
      this.head.yRot = this.lerpTo(this.head.yRot, 0.0F);
      this.head.zRot = this.lerpTo(this.head.zRot, 0.0F);
   }

   private void setupPlayDeadAnimation(float var1) {
      this.lerpPart(this.leftHindLeg, 1.4137167F, 1.0995574F, (float) (Math.PI / 4));
      this.lerpPart(this.leftFrontLeg, (float) (Math.PI / 4), 2.042035F, 0.0F);
      this.body.xRot = this.lerpTo(this.body.xRot, -0.15F);
      this.body.zRot = this.lerpTo(this.body.zRot, 0.35F);
      this.applyMirrorLegRotations();
      this.body.yRot = this.lerpTo(this.body.yRot, â˜ƒ * (float) (Math.PI / 180.0));
      this.head.xRot = this.lerpTo(this.head.xRot, 0.0F);
      this.head.yRot = this.lerpTo(this.head.yRot, 0.0F);
      this.head.zRot = this.lerpTo(this.head.zRot, 0.0F);
      this.tail.yRot = this.lerpTo(this.tail.yRot, 0.0F);
      this.lerpPart(this.topGills, 0.0F, 0.0F, 0.0F);
      this.lerpPart(this.leftGills, 0.0F, 0.0F, 0.0F);
      this.lerpPart(this.rightGills, 0.0F, 0.0F, 0.0F);
   }

   private void applyMirrorLegRotations() {
      this.lerpPart(this.rightHindLeg, this.leftHindLeg.xRot, -this.leftHindLeg.yRot, -this.leftHindLeg.zRot);
      this.lerpPart(this.rightFrontLeg, this.leftFrontLeg.xRot, -this.leftFrontLeg.yRot, -this.leftFrontLeg.zRot);
   }
}
