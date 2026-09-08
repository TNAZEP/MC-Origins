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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SkeletonModel<T extends Mob & RangedAttackMob> extends HumanoidModel<T> {
   public SkeletonModel(ModelPart var1) {
      super(â˜ƒ);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
      this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(InteractionHand.MAIN_HAND);
      if (â˜ƒ.is(Items.BOW) && â˜ƒ.isAggressive()) {
         if (â˜ƒ.getMainArm() == HumanoidArm.RIGHT) {
            this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
         } else {
            this.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
         }
      }

      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      ItemStack â˜ƒ = â˜ƒ.getMainHandItem();
      if (â˜ƒ.isAggressive() && (â˜ƒ.isEmpty() || !â˜ƒ.is(Items.BOW))) {
         float â˜ƒx = Mth.sin(this.attackTime * (float) Math.PI);
         float â˜ƒxx = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
         this.rightArm.zRot = 0.0F;
         this.leftArm.zRot = 0.0F;
         this.rightArm.yRot = -(0.1F - â˜ƒx * 0.6F);
         this.leftArm.yRot = 0.1F - â˜ƒx * 0.6F;
         this.rightArm.xRot = (float) (-Math.PI / 2);
         this.leftArm.xRot = (float) (-Math.PI / 2);
         this.rightArm.xRot -= â˜ƒx * 1.2F - â˜ƒxx * 0.4F;
         this.leftArm.xRot -= â˜ƒx * 1.2F - â˜ƒxx * 0.4F;
         AnimationUtils.bobArms(this.rightArm, this.leftArm, â˜ƒ);
      }
   }

   @Override
   public void translateToHand(HumanoidArm var1, PoseStack var2) {
      float â˜ƒ = â˜ƒ == HumanoidArm.RIGHT ? 1.0F : -1.0F;
      ModelPart â˜ƒx = this.getArm(â˜ƒ);
      â˜ƒx.x += â˜ƒ;
      â˜ƒx.translateAndRotate(â˜ƒ);
      â˜ƒx.x -= â˜ƒ;
   }
}
