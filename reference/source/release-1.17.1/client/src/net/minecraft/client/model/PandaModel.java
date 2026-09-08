package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;

public class PandaModel<T extends Panda> extends QuadrupedModel<T> {
   private float sitAmount;
   private float lieOnBackAmount;
   private float rollAmount;

   public PandaModel(ModelPart var1) {
      super(â˜ƒ, true, 23.0F, 4.8F, 2.7F, 3.0F, 49);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 6)
            .addBox(-6.5F, -5.0F, -4.0F, 13.0F, 10.0F, 9.0F)
            .texOffs(45, 16)
            .addBox("nose", -3.5F, 0.0F, -6.0F, 7.0F, 5.0F, 2.0F)
            .texOffs(52, 25)
            .addBox("left_ear", 3.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F)
            .texOffs(52, 25)
            .addBox("right_ear", -8.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F),
         PartPose.offset(0.0F, 11.5F, -17.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 25).addBox(-9.5F, -13.0F, -6.5F, 19.0F, 26.0F, 13.0F),
         PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      int â˜ƒxx = 9;
      int â˜ƒxxx = 6;
      CubeListBuilder â˜ƒxxxx = CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxxxx, PartPose.offset(-5.5F, 15.0F, 9.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxxxx, PartPose.offset(5.5F, 15.0F, 9.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxxxx, PartPose.offset(-5.5F, 15.0F, -9.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxxxx, PartPose.offset(5.5F, 15.0F, -9.0F));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.sitAmount = â˜ƒ.getSitAmount(â˜ƒ);
      this.lieOnBackAmount = â˜ƒ.getLieOnBackAmount(â˜ƒ);
      this.rollAmount = â˜ƒ.isBaby() ? 0.0F : â˜ƒ.getRollAmount(â˜ƒ);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      boolean â˜ƒ = â˜ƒ.getUnhappyCounter() > 0;
      boolean â˜ƒx = â˜ƒ.isSneezing();
      int â˜ƒxx = â˜ƒ.getSneezeCounter();
      boolean â˜ƒxxx = â˜ƒ.isEating();
      boolean â˜ƒxxxx = â˜ƒ.isScared();
      if (â˜ƒ) {
         this.head.yRot = 0.35F * Mth.sin(0.6F * â˜ƒ);
         this.head.zRot = 0.35F * Mth.sin(0.6F * â˜ƒ);
         this.rightFrontLeg.xRot = -0.75F * Mth.sin(0.3F * â˜ƒ);
         this.leftFrontLeg.xRot = 0.75F * Mth.sin(0.3F * â˜ƒ);
      } else {
         this.head.zRot = 0.0F;
      }

      if (â˜ƒx) {
         if (â˜ƒxx < 15) {
            this.head.xRot = (float) (-Math.PI / 4) * (float)â˜ƒxx / 14.0F;
         } else if (â˜ƒxx < 20) {
            float â˜ƒ = (float)((â˜ƒxx - 15) / 5);
            this.head.xRot = (float) (-Math.PI / 4) + (float) (Math.PI / 4) * â˜ƒ;
         }
      }

      if (this.sitAmount > 0.0F) {
         this.body.xRot = ModelUtils.rotlerpRad(this.body.xRot, 1.7407963F, this.sitAmount);
         this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, (float) (Math.PI / 2), this.sitAmount);
         this.rightFrontLeg.zRot = -0.27079642F;
         this.leftFrontLeg.zRot = 0.27079642F;
         this.rightHindLeg.zRot = 0.5707964F;
         this.leftHindLeg.zRot = -0.5707964F;
         if (â˜ƒxxx) {
            this.head.xRot = (float) (Math.PI / 2) + 0.2F * Mth.sin(â˜ƒ * 0.6F);
            this.rightFrontLeg.xRot = -0.4F - 0.2F * Mth.sin(â˜ƒ * 0.6F);
            this.leftFrontLeg.xRot = -0.4F - 0.2F * Mth.sin(â˜ƒ * 0.6F);
         }

         if (â˜ƒxxxx) {
            this.head.xRot = 2.1707964F;
            this.rightFrontLeg.xRot = -0.9F;
            this.leftFrontLeg.xRot = -0.9F;
         }
      } else {
         this.rightHindLeg.zRot = 0.0F;
         this.leftHindLeg.zRot = 0.0F;
         this.rightFrontLeg.zRot = 0.0F;
         this.leftFrontLeg.zRot = 0.0F;
      }

      if (this.lieOnBackAmount > 0.0F) {
         this.rightHindLeg.xRot = -0.6F * Mth.sin(â˜ƒ * 0.15F);
         this.leftHindLeg.xRot = 0.6F * Mth.sin(â˜ƒ * 0.15F);
         this.rightFrontLeg.xRot = 0.3F * Mth.sin(â˜ƒ * 0.25F);
         this.leftFrontLeg.xRot = -0.3F * Mth.sin(â˜ƒ * 0.25F);
         this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, (float) (Math.PI / 2), this.lieOnBackAmount);
      }

      if (this.rollAmount > 0.0F) {
         this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, 2.0561945F, this.rollAmount);
         this.rightHindLeg.xRot = -0.5F * Mth.sin(â˜ƒ * 0.5F);
         this.leftHindLeg.xRot = 0.5F * Mth.sin(â˜ƒ * 0.5F);
         this.rightFrontLeg.xRot = 0.5F * Mth.sin(â˜ƒ * 0.5F);
         this.leftFrontLeg.xRot = -0.5F * Mth.sin(â˜ƒ * 0.5F);
      }
   }
}
