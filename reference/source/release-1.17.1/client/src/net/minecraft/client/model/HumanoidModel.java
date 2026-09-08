package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class HumanoidModel<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
   public static final float OVERLAY_SCALE = 0.25F;
   public static final float HAT_OVERLAY_SCALE = 0.5F;
   private static final float SPYGLASS_ARM_ROT_Y = (float) (Math.PI / 12);
   private static final float SPYGLASS_ARM_ROT_X = 1.9198622F;
   private static final float SPYGLASS_ARM_CROUCH_ROT_X = (float) (Math.PI / 12);
   public final ModelPart head;
   public final ModelPart hat;
   public final ModelPart body;
   public final ModelPart rightArm;
   public final ModelPart leftArm;
   public final ModelPart rightLeg;
   public final ModelPart leftLeg;
   public HumanoidModel.ArmPose leftArmPose = HumanoidModel.ArmPose.EMPTY;
   public HumanoidModel.ArmPose rightArmPose = HumanoidModel.ArmPose.EMPTY;
   public boolean crouching;
   public float swimAmount;

   public HumanoidModel(ModelPart var1) {
      this(â˜ƒ, RenderType::entityCutoutNoCull);
   }

   public HumanoidModel(ModelPart var1, Function<ResourceLocation, RenderType> var2) {
      super(â˜ƒ, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
      this.head = â˜ƒ.getChild("head");
      this.hat = â˜ƒ.getChild("hat");
      this.body = â˜ƒ.getChild("body");
      this.rightArm = â˜ƒ.getChild("right_arm");
      this.leftArm = â˜ƒ.getChild("left_arm");
      this.rightLeg = â˜ƒ.getChild("right_leg");
      this.leftLeg = â˜ƒ.getChild("left_leg");
   }

   public static MeshDefinition createMesh(CubeDeformation var0, float var1) {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, â˜ƒ), PartPose.offset(0.0F, 0.0F + â˜ƒ, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "hat",
         CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, â˜ƒ.extend(0.5F)),
         PartPose.offset(0.0F, 0.0F + â˜ƒ, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(0.0F, 0.0F + â˜ƒ, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(-5.0F, 2.0F + â˜ƒ, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ),
         PartPose.offset(5.0F, 2.0F + â˜ƒ, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(-1.9F, 12.0F + â˜ƒ, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ),
         PartPose.offset(1.9F, 12.0F + â˜ƒ, 0.0F)
      );
      return â˜ƒ;
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of(this.head);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.hat);
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      this.swimAmount = â˜ƒ.getSwimAmount(â˜ƒ);
      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      boolean â˜ƒ = â˜ƒ.getFallFlyingTicks() > 4;
      boolean â˜ƒx = â˜ƒ.isVisuallySwimming();
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      if (â˜ƒ) {
         this.head.xRot = (float) (-Math.PI / 4);
      } else if (this.swimAmount > 0.0F) {
         if (â˜ƒx) {
            this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (float) (-Math.PI / 4));
         } else {
            this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, â˜ƒ * (float) (Math.PI / 180.0));
         }
      } else {
         this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      }

      this.body.yRot = 0.0F;
      this.rightArm.z = 0.0F;
      this.rightArm.x = -5.0F;
      this.leftArm.z = 0.0F;
      this.leftArm.x = 5.0F;
      float â˜ƒ = 1.0F;
      if (â˜ƒ) {
         â˜ƒ = (float)â˜ƒ.getDeltaMovement().lengthSqr();
         â˜ƒ /= 0.2F;
         â˜ƒ *= â˜ƒ * â˜ƒ;
      }

      if (â˜ƒ < 1.0F) {
         â˜ƒ = 1.0F;
      }

      this.rightArm.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 2.0F * â˜ƒ * 0.5F / â˜ƒ;
      this.leftArm.xRot = Mth.cos(â˜ƒ * 0.6662F) * 2.0F * â˜ƒ * 0.5F / â˜ƒ;
      this.rightArm.zRot = 0.0F;
      this.leftArm.zRot = 0.0F;
      this.rightLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ / â˜ƒ;
      this.leftLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ / â˜ƒ;
      this.rightLeg.yRot = 0.0F;
      this.leftLeg.yRot = 0.0F;
      this.rightLeg.zRot = 0.0F;
      this.leftLeg.zRot = 0.0F;
      if (this.riding) {
         this.rightArm.xRot += (float) (-Math.PI / 5);
         this.leftArm.xRot += (float) (-Math.PI / 5);
         this.rightLeg.xRot = -1.4137167F;
         this.rightLeg.yRot = (float) (Math.PI / 10);
         this.rightLeg.zRot = 0.07853982F;
         this.leftLeg.xRot = -1.4137167F;
         this.leftLeg.yRot = (float) (-Math.PI / 10);
         this.leftLeg.zRot = -0.07853982F;
      }

      this.rightArm.yRot = 0.0F;
      this.leftArm.yRot = 0.0F;
      boolean â˜ƒ = â˜ƒ.getMainArm() == HumanoidArm.RIGHT;
      if (â˜ƒ.isUsingItem()) {
         boolean â˜ƒx = â˜ƒ.getUsedItemHand() == InteractionHand.MAIN_HAND;
         if (â˜ƒx == â˜ƒ) {
            this.poseRightArm(â˜ƒ);
         } else {
            this.poseLeftArm(â˜ƒ);
         }
      } else {
         boolean â˜ƒ = â˜ƒ ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
         if (â˜ƒ != â˜ƒ) {
            this.poseLeftArm(â˜ƒ);
            this.poseRightArm(â˜ƒ);
         } else {
            this.poseRightArm(â˜ƒ);
            this.poseLeftArm(â˜ƒ);
         }
      }

      this.setupAttackAnimation(â˜ƒ, â˜ƒ);
      if (this.crouching) {
         this.body.xRot = 0.5F;
         this.rightArm.xRot += 0.4F;
         this.leftArm.xRot += 0.4F;
         this.rightLeg.z = 4.0F;
         this.leftLeg.z = 4.0F;
         this.rightLeg.y = 12.2F;
         this.leftLeg.y = 12.2F;
         this.head.y = 4.2F;
         this.body.y = 3.2F;
         this.leftArm.y = 5.2F;
         this.rightArm.y = 5.2F;
      } else {
         this.body.xRot = 0.0F;
         this.rightLeg.z = 0.1F;
         this.leftLeg.z = 0.1F;
         this.rightLeg.y = 12.0F;
         this.leftLeg.y = 12.0F;
         this.head.y = 0.0F;
         this.body.y = 0.0F;
         this.leftArm.y = 2.0F;
         this.rightArm.y = 2.0F;
      }

      if (this.rightArmPose != HumanoidModel.ArmPose.SPYGLASS) {
         AnimationUtils.bobModelPart(this.rightArm, â˜ƒ, 1.0F);
      }

      if (this.leftArmPose != HumanoidModel.ArmPose.SPYGLASS) {
         AnimationUtils.bobModelPart(this.leftArm, â˜ƒ, -1.0F);
      }

      if (this.swimAmount > 0.0F) {
         float â˜ƒ = â˜ƒ % 26.0F;
         HumanoidArm â˜ƒx = this.getAttackArm(â˜ƒ);
         float â˜ƒxx = â˜ƒx == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
         float â˜ƒxxx = â˜ƒx == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
         if (!â˜ƒ.isUsingItem()) {
            if (â˜ƒ < 14.0F) {
               this.leftArm.xRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.xRot, 0.0F);
               this.rightArm.xRot = Mth.lerp(â˜ƒxx, this.rightArm.xRot, 0.0F);
               this.leftArm.yRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.yRot, (float) Math.PI);
               this.rightArm.yRot = Mth.lerp(â˜ƒxx, this.rightArm.yRot, (float) Math.PI);
               this.leftArm.zRot = this.rotlerpRad(
                  â˜ƒxxx, this.leftArm.zRot, (float) Math.PI + 1.8707964F * this.quadraticArmUpdate(â˜ƒ) / this.quadraticArmUpdate(14.0F)
               );
               this.rightArm.zRot = Mth.lerp(
                  â˜ƒxx, this.rightArm.zRot, (float) Math.PI - 1.8707964F * this.quadraticArmUpdate(â˜ƒ) / this.quadraticArmUpdate(14.0F)
               );
            } else if (â˜ƒ >= 14.0F && â˜ƒ < 22.0F) {
               float â˜ƒxxxx = (â˜ƒ - 14.0F) / 8.0F;
               this.leftArm.xRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.xRot, (float) (Math.PI / 2) * â˜ƒxxxx);
               this.rightArm.xRot = Mth.lerp(â˜ƒxx, this.rightArm.xRot, (float) (Math.PI / 2) * â˜ƒxxxx);
               this.leftArm.yRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.yRot, (float) Math.PI);
               this.rightArm.yRot = Mth.lerp(â˜ƒxx, this.rightArm.yRot, (float) Math.PI);
               this.leftArm.zRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.zRot, 5.012389F - 1.8707964F * â˜ƒxxxx);
               this.rightArm.zRot = Mth.lerp(â˜ƒxx, this.rightArm.zRot, 1.2707963F + 1.8707964F * â˜ƒxxxx);
            } else if (â˜ƒ >= 22.0F && â˜ƒ < 26.0F) {
               float â˜ƒxxxx = (â˜ƒ - 22.0F) / 4.0F;
               this.leftArm.xRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.xRot, (float) (Math.PI / 2) - (float) (Math.PI / 2) * â˜ƒxxxx);
               this.rightArm.xRot = Mth.lerp(â˜ƒxx, this.rightArm.xRot, (float) (Math.PI / 2) - (float) (Math.PI / 2) * â˜ƒxxxx);
               this.leftArm.yRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.yRot, (float) Math.PI);
               this.rightArm.yRot = Mth.lerp(â˜ƒxx, this.rightArm.yRot, (float) Math.PI);
               this.leftArm.zRot = this.rotlerpRad(â˜ƒxxx, this.leftArm.zRot, (float) Math.PI);
               this.rightArm.zRot = Mth.lerp(â˜ƒxx, this.rightArm.zRot, (float) Math.PI);
            }
         }

         float â˜ƒ = 0.3F;
         float â˜ƒx = 0.33333334F;
         this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(â˜ƒ * 0.33333334F + (float) Math.PI));
         this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(â˜ƒ * 0.33333334F));
      }

      this.hat.copyFrom(this.head);
   }

   private void poseRightArm(T var1) {
      switch(this.rightArmPose) {
         case EMPTY:
            this.rightArm.yRot = 0.0F;
            break;
         case BLOCK:
            this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
            this.rightArm.yRot = (float) (-Math.PI / 6);
            break;
         case ITEM:
            this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) (Math.PI / 10);
            this.rightArm.yRot = 0.0F;
            break;
         case THROW_SPEAR:
            this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
            this.rightArm.yRot = 0.0F;
            break;
         case BOW_AND_ARROW:
            this.rightArm.yRot = -0.1F + this.head.yRot;
            this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
            this.rightArm.xRot = (float) (-Math.PI / 2) + this.head.xRot;
            this.leftArm.xRot = (float) (-Math.PI / 2) + this.head.xRot;
            break;
         case CROSSBOW_CHARGE:
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, â˜ƒ, true);
            break;
         case CROSSBOW_HOLD:
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
            break;
         case SPYGLASS:
            this.rightArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (â˜ƒ.isCrouching() ? (float) (Math.PI / 12) : 0.0F), -2.4F, 3.3F);
            this.rightArm.yRot = this.head.yRot - (float) (Math.PI / 12);
      }
   }

   private void poseLeftArm(T var1) {
      switch(this.leftArmPose) {
         case EMPTY:
            this.leftArm.yRot = 0.0F;
            break;
         case BLOCK:
            this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
            this.leftArm.yRot = (float) (Math.PI / 6);
            break;
         case ITEM:
            this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) (Math.PI / 10);
            this.leftArm.yRot = 0.0F;
            break;
         case THROW_SPEAR:
            this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
            this.leftArm.yRot = 0.0F;
            break;
         case BOW_AND_ARROW:
            this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
            this.leftArm.yRot = 0.1F + this.head.yRot;
            this.rightArm.xRot = (float) (-Math.PI / 2) + this.head.xRot;
            this.leftArm.xRot = (float) (-Math.PI / 2) + this.head.xRot;
            break;
         case CROSSBOW_CHARGE:
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, â˜ƒ, false);
            break;
         case CROSSBOW_HOLD:
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
            break;
         case SPYGLASS:
            this.leftArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - (â˜ƒ.isCrouching() ? (float) (Math.PI / 12) : 0.0F), -2.4F, 3.3F);
            this.leftArm.yRot = this.head.yRot + (float) (Math.PI / 12);
      }
   }

   protected void setupAttackAnimation(T var1, float var2) {
      if (!(this.attackTime <= 0.0F)) {
         HumanoidArm â˜ƒ = this.getAttackArm(â˜ƒ);
         ModelPart â˜ƒx = this.getArm(â˜ƒ);
         float â˜ƒxx = this.attackTime;
         this.body.yRot = Mth.sin(Mth.sqrt(â˜ƒxx) * (float) (Math.PI * 2)) * 0.2F;
         if (â˜ƒ == HumanoidArm.LEFT) {
            this.body.yRot *= -1.0F;
         }

         this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
         this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
         this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
         this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
         this.rightArm.yRot += this.body.yRot;
         this.leftArm.yRot += this.body.yRot;
         this.leftArm.xRot += this.body.yRot;
         â˜ƒxx = 1.0F - this.attackTime;
         â˜ƒxx *= â˜ƒxx;
         â˜ƒxx *= â˜ƒxx;
         â˜ƒxx = 1.0F - â˜ƒxx;
         float â˜ƒ = Mth.sin(â˜ƒxx * (float) Math.PI);
         float â˜ƒx = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
         â˜ƒx.xRot = (float)((double)â˜ƒx.xRot - ((double)â˜ƒ * 1.2 + (double)â˜ƒx));
         â˜ƒx.yRot += this.body.yRot * 2.0F;
         â˜ƒx.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
      }
   }

   protected float rotlerpRad(float var1, float var2, float var3) {
      float â˜ƒ = (â˜ƒ - â˜ƒ) % (float) (Math.PI * 2);
      if (â˜ƒ < (float) -Math.PI) {
         â˜ƒ += (float) (Math.PI * 2);
      }

      if (â˜ƒ >= (float) Math.PI) {
         â˜ƒ -= (float) (Math.PI * 2);
      }

      return â˜ƒ + â˜ƒ * â˜ƒ;
   }

   private float quadraticArmUpdate(float var1) {
      return -65.0F * â˜ƒ + â˜ƒ * â˜ƒ;
   }

   public void copyPropertiesTo(HumanoidModel<T> var1) {
      super.copyPropertiesTo(â˜ƒ);
      â˜ƒ.leftArmPose = this.leftArmPose;
      â˜ƒ.rightArmPose = this.rightArmPose;
      â˜ƒ.crouching = this.crouching;
      â˜ƒ.head.copyFrom(this.head);
      â˜ƒ.hat.copyFrom(this.hat);
      â˜ƒ.body.copyFrom(this.body);
      â˜ƒ.rightArm.copyFrom(this.rightArm);
      â˜ƒ.leftArm.copyFrom(this.leftArm);
      â˜ƒ.rightLeg.copyFrom(this.rightLeg);
      â˜ƒ.leftLeg.copyFrom(this.leftLeg);
   }

   public void setAllVisible(boolean var1) {
      this.head.visible = â˜ƒ;
      this.hat.visible = â˜ƒ;
      this.body.visible = â˜ƒ;
      this.rightArm.visible = â˜ƒ;
      this.leftArm.visible = â˜ƒ;
      this.rightLeg.visible = â˜ƒ;
      this.leftLeg.visible = â˜ƒ;
   }

   @Override
   public void translateToHand(HumanoidArm var1, PoseStack var2) {
      this.getArm(â˜ƒ).translateAndRotate(â˜ƒ);
   }

   protected ModelPart getArm(HumanoidArm var1) {
      return â˜ƒ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
   }

   @Override
   public ModelPart getHead() {
      return this.head;
   }

   private HumanoidArm getAttackArm(T var1) {
      HumanoidArm â˜ƒ = â˜ƒ.getMainArm();
      return â˜ƒ.swingingArm == InteractionHand.MAIN_HAND ? â˜ƒ : â˜ƒ.getOpposite();
   }

   public static enum ArmPose {
      EMPTY(false),
      ITEM(false),
      BLOCK(false),
      BOW_AND_ARROW(true),
      THROW_SPEAR(false),
      CROSSBOW_CHARGE(true),
      CROSSBOW_HOLD(true),
      SPYGLASS(false);

      private final boolean twoHanded;

      private ArmPose(boolean var3) {
         this.twoHanded = â˜ƒ;
      }

      public boolean isTwoHanded() {
         return this.twoHanded;
      }
   }
}
