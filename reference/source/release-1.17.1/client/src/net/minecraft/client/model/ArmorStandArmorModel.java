package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandArmorModel extends HumanoidModel<ArmorStand> {
   public ArmorStandArmorModel(ModelPart var1) {
      super(â˜ƒ);
   }

   public static LayerDefinition createBodyLayer(CubeDeformation var0) {
      MeshDefinition â˜ƒ = HumanoidModel.createMesh(â˜ƒ, 0.0F);
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, â˜ƒ), PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, â˜ƒ.extend(0.5F)), PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(-1.9F, 11.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(1.9F, 11.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public void setupAnim(ArmorStand var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.xRot = (float) (Math.PI / 180.0) * â˜ƒ.getHeadPose().getX();
      this.head.yRot = (float) (Math.PI / 180.0) * â˜ƒ.getHeadPose().getY();
      this.head.zRot = (float) (Math.PI / 180.0) * â˜ƒ.getHeadPose().getZ();
      this.body.xRot = (float) (Math.PI / 180.0) * â˜ƒ.getBodyPose().getX();
      this.body.yRot = (float) (Math.PI / 180.0) * â˜ƒ.getBodyPose().getY();
      this.body.zRot = (float) (Math.PI / 180.0) * â˜ƒ.getBodyPose().getZ();
      this.leftArm.xRot = (float) (Math.PI / 180.0) * â˜ƒ.getLeftArmPose().getX();
      this.leftArm.yRot = (float) (Math.PI / 180.0) * â˜ƒ.getLeftArmPose().getY();
      this.leftArm.zRot = (float) (Math.PI / 180.0) * â˜ƒ.getLeftArmPose().getZ();
      this.rightArm.xRot = (float) (Math.PI / 180.0) * â˜ƒ.getRightArmPose().getX();
      this.rightArm.yRot = (float) (Math.PI / 180.0) * â˜ƒ.getRightArmPose().getY();
      this.rightArm.zRot = (float) (Math.PI / 180.0) * â˜ƒ.getRightArmPose().getZ();
      this.leftLeg.xRot = (float) (Math.PI / 180.0) * â˜ƒ.getLeftLegPose().getX();
      this.leftLeg.yRot = (float) (Math.PI / 180.0) * â˜ƒ.getLeftLegPose().getY();
      this.leftLeg.zRot = (float) (Math.PI / 180.0) * â˜ƒ.getLeftLegPose().getZ();
      this.rightLeg.xRot = (float) (Math.PI / 180.0) * â˜ƒ.getRightLegPose().getX();
      this.rightLeg.yRot = (float) (Math.PI / 180.0) * â˜ƒ.getRightLegPose().getY();
      this.rightLeg.zRot = (float) (Math.PI / 180.0) * â˜ƒ.getRightLegPose().getZ();
      this.hat.copyFrom(this.head);
   }
}
