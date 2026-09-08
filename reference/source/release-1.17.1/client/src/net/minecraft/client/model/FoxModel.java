package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;

public class FoxModel<T extends Fox> extends AgeableListModel<T> {
   public final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart tail;
   private static final int LEG_SIZE = 6;
   private static final float HEAD_HEIGHT = 16.5F;
   private static final float LEG_POS = 17.5F;
   private float legMotionPos;

   public FoxModel(ModelPart var1) {
      super(true, 8.0F, 3.35F);
      this.head = â˜ƒ.getChild("head");
      this.body = â˜ƒ.getChild("body");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
      this.tail = this.body.getChild("tail");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(1, 5).addBox(-3.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F), PartPose.offset(-1.0F, 16.5F, -3.0F)
      );
      â˜ƒxx.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(8, 1).addBox(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F), PartPose.ZERO);
      â˜ƒxx.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(15, 1).addBox(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F), PartPose.ZERO);
      â˜ƒxx.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(6, 18).addBox(-1.0F, 2.01F, -8.0F, 4.0F, 2.0F, 3.0F), PartPose.ZERO);
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(24, 15).addBox(-3.0F, 3.999F, -3.5F, 6.0F, 11.0F, 6.0F),
         PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      CubeDeformation â˜ƒxxxx = new CubeDeformation(0.001F);
      CubeListBuilder â˜ƒxxxxx = CubeListBuilder.create().texOffs(4, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, â˜ƒxxxx);
      CubeListBuilder â˜ƒxxxxxx = CubeListBuilder.create().texOffs(13, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, â˜ƒxxxx);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxxxxxx, PartPose.offset(-5.0F, 17.5F, 7.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxxxxx, PartPose.offset(-1.0F, 17.5F, 7.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxxxxxx, PartPose.offset(-5.0F, 17.5F, 0.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxxxxx, PartPose.offset(-1.0F, 17.5F, 0.0F));
      â˜ƒxxx.addOrReplaceChild(
         "tail",
         CubeListBuilder.create().texOffs(30, 0).addBox(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F),
         PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, -0.05235988F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 48, 32);
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      this.body.xRot = (float) (Math.PI / 2);
      this.tail.xRot = -0.05235988F;
      this.rightHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      this.leftHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.rightFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.leftFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      this.head.setPos(-1.0F, 16.5F, -3.0F);
      this.head.yRot = 0.0F;
      this.head.zRot = â˜ƒ.getHeadRollAngle(â˜ƒ);
      this.rightHindLeg.visible = true;
      this.leftHindLeg.visible = true;
      this.rightFrontLeg.visible = true;
      this.leftFrontLeg.visible = true;
      this.body.setPos(0.0F, 16.0F, -6.0F);
      this.body.zRot = 0.0F;
      this.rightHindLeg.setPos(-5.0F, 17.5F, 7.0F);
      this.leftHindLeg.setPos(-1.0F, 17.5F, 7.0F);
      if (â˜ƒ.isCrouching()) {
         this.body.xRot = 1.6755161F;
         float â˜ƒ = â˜ƒ.getCrouchAmount(â˜ƒ);
         this.body.setPos(0.0F, 16.0F + â˜ƒ.getCrouchAmount(â˜ƒ), -6.0F);
         this.head.setPos(-1.0F, 16.5F + â˜ƒ, -3.0F);
         this.head.yRot = 0.0F;
      } else if (â˜ƒ.isSleeping()) {
         this.body.zRot = (float) (-Math.PI / 2);
         this.body.setPos(0.0F, 21.0F, -6.0F);
         this.tail.xRot = (float) (-Math.PI * 5.0 / 6.0);
         if (this.young) {
            this.tail.xRot = -2.1816616F;
            this.body.setPos(0.0F, 21.0F, -2.0F);
         }

         this.head.setPos(1.0F, 19.49F, -3.0F);
         this.head.xRot = 0.0F;
         this.head.yRot = (float) (-Math.PI * 2.0 / 3.0);
         this.head.zRot = 0.0F;
         this.rightHindLeg.visible = false;
         this.leftHindLeg.visible = false;
         this.rightFrontLeg.visible = false;
         this.leftFrontLeg.visible = false;
      } else if (â˜ƒ.isSitting()) {
         this.body.xRot = (float) (Math.PI / 6);
         this.body.setPos(0.0F, 9.0F, -3.0F);
         this.tail.xRot = (float) (Math.PI / 4);
         this.tail.setPos(-4.0F, 15.0F, -2.0F);
         this.head.setPos(-1.0F, 10.0F, -0.25F);
         this.head.xRot = 0.0F;
         this.head.yRot = 0.0F;
         if (this.young) {
            this.head.setPos(-1.0F, 13.0F, -3.75F);
         }

         this.rightHindLeg.xRot = (float) (-Math.PI * 5.0 / 12.0);
         this.rightHindLeg.setPos(-5.0F, 21.5F, 6.75F);
         this.leftHindLeg.xRot = (float) (-Math.PI * 5.0 / 12.0);
         this.leftHindLeg.setPos(-1.0F, 21.5F, 6.75F);
         this.rightFrontLeg.xRot = (float) (-Math.PI / 12);
         this.leftFrontLeg.xRot = (float) (-Math.PI / 12);
      }
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of(this.head);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      if (!â˜ƒ.isSleeping() && !â˜ƒ.isFaceplanted() && !â˜ƒ.isCrouching()) {
         this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
         this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      }

      if (â˜ƒ.isSleeping()) {
         this.head.xRot = 0.0F;
         this.head.yRot = (float) (-Math.PI * 2.0 / 3.0);
         this.head.zRot = Mth.cos(â˜ƒ * 0.027F) / 22.0F;
      }

      if (â˜ƒ.isCrouching()) {
         float â˜ƒ = Mth.cos(â˜ƒ) * 0.01F;
         this.body.yRot = â˜ƒ;
         this.rightHindLeg.zRot = â˜ƒ;
         this.leftHindLeg.zRot = â˜ƒ;
         this.rightFrontLeg.zRot = â˜ƒ / 2.0F;
         this.leftFrontLeg.zRot = â˜ƒ / 2.0F;
      }

      if (â˜ƒ.isFaceplanted()) {
         float â˜ƒ = 0.1F;
         this.legMotionPos += 0.67F;
         this.rightHindLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F) * 0.1F;
         this.leftHindLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F + (float) Math.PI) * 0.1F;
         this.rightFrontLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F + (float) Math.PI) * 0.1F;
         this.leftFrontLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F) * 0.1F;
      }
   }
}
