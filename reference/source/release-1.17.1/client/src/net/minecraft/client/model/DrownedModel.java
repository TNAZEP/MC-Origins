package net.minecraft.client.model;

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
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DrownedModel<T extends Zombie> extends ZombieModel<T> {
   public DrownedModel(ModelPart var1) {
      super(â˜ƒ);
   }

   public static LayerDefinition createBodyLayer(CubeDeformation var0) {
      MeshDefinition â˜ƒ = HumanoidModel.createMesh(â˜ƒ, 0.0F);
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(5.0F, 2.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(1.9F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
      this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(InteractionHand.MAIN_HAND);
      if (â˜ƒ.is(Items.TRIDENT) && â˜ƒ.isAggressive()) {
         if (â˜ƒ.getMainArm() == HumanoidArm.RIGHT) {
            this.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
         } else {
            this.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
         }
      }

      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.leftArmPose == HumanoidModel.ArmPose.THROW_SPEAR) {
         this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
         this.leftArm.yRot = 0.0F;
      }

      if (this.rightArmPose == HumanoidModel.ArmPose.THROW_SPEAR) {
         this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
         this.rightArm.yRot = 0.0F;
      }

      if (this.swimAmount > 0.0F) {
         this.rightArm.xRot = this.rotlerpRad(this.swimAmount, this.rightArm.xRot, (float) (-Math.PI * 4.0 / 5.0))
            + this.swimAmount * 0.35F * Mth.sin(0.1F * â˜ƒ);
         this.leftArm.xRot = this.rotlerpRad(this.swimAmount, this.leftArm.xRot, (float) (-Math.PI * 4.0 / 5.0))
            - this.swimAmount * 0.35F * Mth.sin(0.1F * â˜ƒ);
         this.rightArm.zRot = this.rotlerpRad(this.swimAmount, this.rightArm.zRot, -0.15F);
         this.leftArm.zRot = this.rotlerpRad(this.swimAmount, this.leftArm.zRot, 0.15F);
         this.leftLeg.xRot -= this.swimAmount * 0.55F * Mth.sin(0.1F * â˜ƒ);
         this.rightLeg.xRot += this.swimAmount * 0.55F * Mth.sin(0.1F * â˜ƒ);
         this.head.xRot = 0.0F;
      }
   }
}
