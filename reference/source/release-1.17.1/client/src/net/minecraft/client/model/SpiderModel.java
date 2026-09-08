package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SpiderModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String BODY_0 = "body0";
   private static final String BODY_1 = "body1";
   private static final String RIGHT_MIDDLE_FRONT_LEG = "right_middle_front_leg";
   private static final String LEFT_MIDDLE_FRONT_LEG = "left_middle_front_leg";
   private static final String RIGHT_MIDDLE_HIND_LEG = "right_middle_hind_leg";
   private static final String LEFT_MIDDLE_HIND_LEG = "left_middle_hind_leg";
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightMiddleHindLeg;
   private final ModelPart leftMiddleHindLeg;
   private final ModelPart rightMiddleFrontLeg;
   private final ModelPart leftMiddleFrontLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;

   public SpiderModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.rightMiddleHindLeg = â˜ƒ.getChild("right_middle_hind_leg");
      this.leftMiddleHindLeg = â˜ƒ.getChild("left_middle_hind_leg");
      this.rightMiddleFrontLeg = â˜ƒ.getChild("right_middle_front_leg");
      this.leftMiddleFrontLeg = â˜ƒ.getChild("left_middle_front_leg");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
   }

   public static LayerDefinition createSpiderBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 15;
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 15.0F, -3.0F));
      â˜ƒx.addOrReplaceChild("body0", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 15.0F, 0.0F));
      â˜ƒx.addOrReplaceChild(
         "body1", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), PartPose.offset(0.0F, 15.0F, 9.0F)
      );
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
      CubeListBuilder â˜ƒxxxx = CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxxx, PartPose.offset(-4.0F, 15.0F, 2.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxxxx, PartPose.offset(4.0F, 15.0F, 2.0F));
      â˜ƒx.addOrReplaceChild("right_middle_hind_leg", â˜ƒxxx, PartPose.offset(-4.0F, 15.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("left_middle_hind_leg", â˜ƒxxxx, PartPose.offset(4.0F, 15.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("right_middle_front_leg", â˜ƒxxx, PartPose.offset(-4.0F, 15.0F, 0.0F));
      â˜ƒx.addOrReplaceChild("left_middle_front_leg", â˜ƒxxxx, PartPose.offset(4.0F, 15.0F, 0.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxxx, PartPose.offset(-4.0F, 15.0F, -1.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxxxx, PartPose.offset(4.0F, 15.0F, -1.0F));
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      float â˜ƒ = (float) (Math.PI / 4);
      this.rightHindLeg.zRot = (float) (-Math.PI / 4);
      this.leftHindLeg.zRot = (float) (Math.PI / 4);
      this.rightMiddleHindLeg.zRot = -0.58119464F;
      this.leftMiddleHindLeg.zRot = 0.58119464F;
      this.rightMiddleFrontLeg.zRot = -0.58119464F;
      this.leftMiddleFrontLeg.zRot = 0.58119464F;
      this.rightFrontLeg.zRot = (float) (-Math.PI / 4);
      this.leftFrontLeg.zRot = (float) (Math.PI / 4);
      float â˜ƒx = -0.0F;
      float â˜ƒxx = (float) (Math.PI / 8);
      this.rightHindLeg.yRot = (float) (Math.PI / 4);
      this.leftHindLeg.yRot = (float) (-Math.PI / 4);
      this.rightMiddleHindLeg.yRot = (float) (Math.PI / 8);
      this.leftMiddleHindLeg.yRot = (float) (-Math.PI / 8);
      this.rightMiddleFrontLeg.yRot = (float) (-Math.PI / 8);
      this.leftMiddleFrontLeg.yRot = (float) (Math.PI / 8);
      this.rightFrontLeg.yRot = (float) (-Math.PI / 4);
      this.leftFrontLeg.yRot = (float) (Math.PI / 4);
      float â˜ƒxxx = -(Mth.cos(â˜ƒ * 0.6662F * 2.0F + 0.0F) * 0.4F) * â˜ƒ;
      float â˜ƒxxxx = -(Mth.cos(â˜ƒ * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * â˜ƒ;
      float â˜ƒxxxxx = -(Mth.cos(â˜ƒ * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * â˜ƒ;
      float â˜ƒxxxxxx = -(Mth.cos(â˜ƒ * 0.6662F * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * â˜ƒ;
      float â˜ƒxxxxxxx = Math.abs(Mth.sin(â˜ƒ * 0.6662F + 0.0F) * 0.4F) * â˜ƒ;
      float â˜ƒxxxxxxxx = Math.abs(Mth.sin(â˜ƒ * 0.6662F + (float) Math.PI) * 0.4F) * â˜ƒ;
      float â˜ƒxxxxxxxxx = Math.abs(Mth.sin(â˜ƒ * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * â˜ƒ;
      float â˜ƒxxxxxxxxxx = Math.abs(Mth.sin(â˜ƒ * 0.6662F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * â˜ƒ;
      this.rightHindLeg.yRot += â˜ƒxxx;
      this.leftHindLeg.yRot += -â˜ƒxxx;
      this.rightMiddleHindLeg.yRot += â˜ƒxxxx;
      this.leftMiddleHindLeg.yRot += -â˜ƒxxxx;
      this.rightMiddleFrontLeg.yRot += â˜ƒxxxxx;
      this.leftMiddleFrontLeg.yRot += -â˜ƒxxxxx;
      this.rightFrontLeg.yRot += â˜ƒxxxxxx;
      this.leftFrontLeg.yRot += -â˜ƒxxxxxx;
      this.rightHindLeg.zRot += â˜ƒxxxxxxx;
      this.leftHindLeg.zRot += -â˜ƒxxxxxxx;
      this.rightMiddleHindLeg.zRot += â˜ƒxxxxxxxx;
      this.leftMiddleHindLeg.zRot += -â˜ƒxxxxxxxx;
      this.rightMiddleFrontLeg.zRot += â˜ƒxxxxxxxxx;
      this.leftMiddleFrontLeg.zRot += -â˜ƒxxxxxxxxx;
      this.rightFrontLeg.zRot += â˜ƒxxxxxxxxxx;
      this.leftFrontLeg.zRot += -â˜ƒxxxxxxxxxx;
   }
}
