package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;

public class IllagerModel<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart hat;
   private final ModelPart arms;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;
   private final ModelPart rightArm;
   private final ModelPart leftArm;

   public IllagerModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.hat = this.head.getChild("hat");
      this.hat.visible = false;
      this.arms = â˜ƒ.getChild("arms");
      this.leftLeg = â˜ƒ.getChild("left_leg");
      this.rightLeg = â˜ƒ.getChild("right_leg");
      this.leftArm = â˜ƒ.getChild("left_arm");
      this.rightArm = â˜ƒ.getChild("right_arm");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO
      );
      â˜ƒxx.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(16, 20)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F)
            .texOffs(0, 38)
            .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "arms",
         CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
         PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F)
      );
      â˜ƒxxx.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      if (this.riding) {
         this.rightArm.xRot = (float) (-Math.PI / 5);
         this.rightArm.yRot = 0.0F;
         this.rightArm.zRot = 0.0F;
         this.leftArm.xRot = (float) (-Math.PI / 5);
         this.leftArm.yRot = 0.0F;
         this.leftArm.zRot = 0.0F;
         this.rightLeg.xRot = -1.4137167F;
         this.rightLeg.yRot = (float) (Math.PI / 10);
         this.rightLeg.zRot = 0.07853982F;
         this.leftLeg.xRot = -1.4137167F;
         this.leftLeg.yRot = (float) (-Math.PI / 10);
         this.leftLeg.zRot = -0.07853982F;
      } else {
         this.rightArm.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 2.0F * â˜ƒ * 0.5F;
         this.rightArm.yRot = 0.0F;
         this.rightArm.zRot = 0.0F;
         this.leftArm.xRot = Mth.cos(â˜ƒ * 0.6662F) * 2.0F * â˜ƒ * 0.5F;
         this.leftArm.yRot = 0.0F;
         this.leftArm.zRot = 0.0F;
         this.rightLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ * 0.5F;
         this.rightLeg.yRot = 0.0F;
         this.rightLeg.zRot = 0.0F;
         this.leftLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ * 0.5F;
         this.leftLeg.yRot = 0.0F;
         this.leftLeg.zRot = 0.0F;
      }

      AbstractIllager.IllagerArmPose â˜ƒ = â˜ƒ.getArmPose();
      if (â˜ƒ == AbstractIllager.IllagerArmPose.ATTACKING) {
         if (â˜ƒ.getMainHandItem().isEmpty()) {
            AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, â˜ƒ);
         } else {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, â˜ƒ, this.attackTime, â˜ƒ);
         }
      } else if (â˜ƒ == AbstractIllager.IllagerArmPose.SPELLCASTING) {
         this.rightArm.z = 0.0F;
         this.rightArm.x = -5.0F;
         this.leftArm.z = 0.0F;
         this.leftArm.x = 5.0F;
         this.rightArm.xRot = Mth.cos(â˜ƒ * 0.6662F) * 0.25F;
         this.leftArm.xRot = Mth.cos(â˜ƒ * 0.6662F) * 0.25F;
         this.rightArm.zRot = (float) (Math.PI * 3.0 / 4.0);
         this.leftArm.zRot = (float) (-Math.PI * 3.0 / 4.0);
         this.rightArm.yRot = 0.0F;
         this.leftArm.yRot = 0.0F;
      } else if (â˜ƒ == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
         this.rightArm.yRot = -0.1F + this.head.yRot;
         this.rightArm.xRot = (float) (-Math.PI / 2) + this.head.xRot;
         this.leftArm.xRot = -0.9424779F + this.head.xRot;
         this.leftArm.yRot = this.head.yRot - 0.4F;
         this.leftArm.zRot = (float) (Math.PI / 2);
      } else if (â˜ƒ == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
         AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
      } else if (â˜ƒ == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
         AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, â˜ƒ, true);
      } else if (â˜ƒ == AbstractIllager.IllagerArmPose.CELEBRATING) {
         this.rightArm.z = 0.0F;
         this.rightArm.x = -5.0F;
         this.rightArm.xRot = Mth.cos(â˜ƒ * 0.6662F) * 0.05F;
         this.rightArm.zRot = 2.670354F;
         this.rightArm.yRot = 0.0F;
         this.leftArm.z = 0.0F;
         this.leftArm.x = 5.0F;
         this.leftArm.xRot = Mth.cos(â˜ƒ * 0.6662F) * 0.05F;
         this.leftArm.zRot = (float) (-Math.PI * 3.0 / 4.0);
         this.leftArm.yRot = 0.0F;
      }

      boolean â˜ƒ = â˜ƒ == AbstractIllager.IllagerArmPose.CROSSED;
      this.arms.visible = â˜ƒ;
      this.leftArm.visible = !â˜ƒ;
      this.rightArm.visible = !â˜ƒ;
   }

   private ModelPart getArm(HumanoidArm var1) {
      return â˜ƒ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
   }

   public ModelPart getHat() {
      return this.hat;
   }

   @Override
   public ModelPart getHead() {
      return this.head;
   }

   @Override
   public void translateToHand(HumanoidArm var1, PoseStack var2) {
      this.getArm(â˜ƒ).translateAndRotate(â˜ƒ);
   }
}
