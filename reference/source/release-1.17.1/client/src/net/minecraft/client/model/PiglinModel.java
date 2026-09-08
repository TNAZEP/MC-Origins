package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;

public class PiglinModel<T extends Mob> extends PlayerModel<T> {
   public final ModelPart rightEar = this.head.getChild("right_ear");
   private final ModelPart leftEar = this.head.getChild("left_ear");
   private final PartPose bodyDefault = this.body.storePose();
   private final PartPose headDefault = this.head.storePose();
   private final PartPose leftArmDefault = this.leftArm.storePose();
   private final PartPose rightArmDefault = this.rightArm.storePose();

   public PiglinModel(ModelPart var1) {
      super(â˜ƒ, false);
   }

   public static MeshDefinition createMesh(CubeDeformation var0) {
      MeshDefinition â˜ƒ = PlayerModel.createMesh(â˜ƒ, false);
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, â˜ƒ), PartPose.ZERO);
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, â˜ƒ)
            .texOffs(31, 1)
            .addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, â˜ƒ)
            .texOffs(2, 4)
            .addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, â˜ƒ)
            .texOffs(2, 0)
            .addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, â˜ƒ),
         PartPose.ZERO
      );
      â˜ƒxx.addOrReplaceChild(
         "left_ear",
         CubeListBuilder.create().texOffs(51, 6).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, â˜ƒ),
         PartPose.offsetAndRotation(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI / 6))
      );
      â˜ƒxx.addOrReplaceChild(
         "right_ear",
         CubeListBuilder.create().texOffs(39, 6).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, â˜ƒ),
         PartPose.offsetAndRotation(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, (float) (Math.PI / 6))
      );
      â˜ƒx.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      return â˜ƒ;
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.body.loadPose(this.bodyDefault);
      this.head.loadPose(this.headDefault);
      this.leftArm.loadPose(this.leftArmDefault);
      this.rightArm.loadPose(this.rightArmDefault);
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒx = (float) (Math.PI / 6);
      float â˜ƒxx = â˜ƒ * 0.1F + â˜ƒ * 0.5F;
      float â˜ƒxxx = 0.08F + â˜ƒ * 0.4F;
      this.leftEar.zRot = (float) (-Math.PI / 6) - Mth.cos(â˜ƒxx * 1.2F) * â˜ƒxxx;
      this.rightEar.zRot = (float) (Math.PI / 6) + Mth.cos(â˜ƒxx) * â˜ƒxxx;
      if (â˜ƒ instanceof AbstractPiglin â˜ƒ) {
         PiglinArmPose â˜ƒxxxx = â˜ƒ.getArmPose();
         if (â˜ƒxxxx == PiglinArmPose.DANCING) {
            float â˜ƒxxxxx = â˜ƒ / 60.0F;
            this.rightEar.zRot = (float) (Math.PI / 6) + (float) (Math.PI / 180.0) * Mth.sin(â˜ƒxxxxx * 30.0F) * 10.0F;
            this.leftEar.zRot = (float) (-Math.PI / 6) - (float) (Math.PI / 180.0) * Mth.cos(â˜ƒxxxxx * 30.0F) * 10.0F;
            this.head.x = Mth.sin(â˜ƒxxxxx * 10.0F);
            this.head.y = Mth.sin(â˜ƒxxxxx * 40.0F) + 0.4F;
            this.rightArm.zRot = (float) (Math.PI / 180.0) * (70.0F + Mth.cos(â˜ƒxxxxx * 40.0F) * 10.0F);
            this.leftArm.zRot = this.rightArm.zRot * -1.0F;
            this.rightArm.y = Mth.sin(â˜ƒxxxxx * 40.0F) * 0.5F + 1.5F;
            this.leftArm.y = Mth.sin(â˜ƒxxxxx * 40.0F) * 0.5F + 1.5F;
            this.body.y = Mth.sin(â˜ƒxxxxx * 40.0F) * 0.35F;
         } else if (â˜ƒxxxx == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON && this.attackTime == 0.0F) {
            this.holdWeaponHigh(â˜ƒ);
         } else if (â˜ƒxxxx == PiglinArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, !â˜ƒ.isLeftHanded());
         } else if (â˜ƒxxxx == PiglinArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, â˜ƒ, !â˜ƒ.isLeftHanded());
         } else if (â˜ƒxxxx == PiglinArmPose.ADMIRING_ITEM) {
            this.head.xRot = 0.5F;
            this.head.yRot = 0.0F;
            if (â˜ƒ.isLeftHanded()) {
               this.rightArm.yRot = -0.5F;
               this.rightArm.xRot = -0.9F;
            } else {
               this.leftArm.yRot = 0.5F;
               this.leftArm.xRot = -0.9F;
            }
         }
      } else if (â˜ƒ.getType() == EntityType.ZOMBIFIED_PIGLIN) {
         AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, â˜ƒ.isAggressive(), this.attackTime, â˜ƒ);
      }

      this.leftPants.copyFrom(this.leftLeg);
      this.rightPants.copyFrom(this.rightLeg);
      this.leftSleeve.copyFrom(this.leftArm);
      this.rightSleeve.copyFrom(this.rightArm);
      this.jacket.copyFrom(this.body);
      this.hat.copyFrom(this.head);
   }

   protected void setupAttackAnimation(T var1, float var2) {
      if (this.attackTime > 0.0F && â˜ƒ instanceof Piglin && ((Piglin)â˜ƒ).getArmPose() == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON) {
         AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, â˜ƒ, this.attackTime, â˜ƒ);
      } else {
         super.setupAttackAnimation(â˜ƒ, â˜ƒ);
      }
   }

   private void holdWeaponHigh(T var1) {
      if (â˜ƒ.isLeftHanded()) {
         this.leftArm.xRot = -1.8F;
      } else {
         this.rightArm.xRot = -1.8F;
      }
   }
}
