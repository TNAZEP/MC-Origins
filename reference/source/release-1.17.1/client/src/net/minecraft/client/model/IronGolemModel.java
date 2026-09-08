package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.IronGolem;

public class IronGolemModel<T extends IronGolem> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart rightArm;
   private final ModelPart leftArm;
   private final ModelPart rightLeg;
   private final ModelPart leftLeg;

   public IronGolemModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.rightArm = â˜ƒ.getChild("right_arm");
      this.leftArm = â˜ƒ.getChild("left_arm");
      this.rightLeg = â˜ƒ.getChild("right_leg");
      this.leftLeg = â˜ƒ.getChild("left_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F).texOffs(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F),
         PartPose.offset(0.0F, -7.0F, -2.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 40)
            .addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F)
            .texOffs(0, 70)
            .addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, -7.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), PartPose.offset(0.0F, -7.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), PartPose.offset(0.0F, -7.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), PartPose.offset(-4.0F, 11.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), PartPose.offset(5.0F, 11.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 128, 128);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.rightLeg.xRot = -1.5F * Mth.triangleWave(â˜ƒ, 13.0F) * â˜ƒ;
      this.leftLeg.xRot = 1.5F * Mth.triangleWave(â˜ƒ, 13.0F) * â˜ƒ;
      this.rightLeg.yRot = 0.0F;
      this.leftLeg.yRot = 0.0F;
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      int â˜ƒ = â˜ƒ.getAttackAnimationTick();
      if (â˜ƒ > 0) {
         this.rightArm.xRot = -2.0F + 1.5F * Mth.triangleWave((float)â˜ƒ - â˜ƒ, 10.0F);
         this.leftArm.xRot = -2.0F + 1.5F * Mth.triangleWave((float)â˜ƒ - â˜ƒ, 10.0F);
      } else {
         int â˜ƒ = â˜ƒ.getOfferFlowerTick();
         if (â˜ƒ > 0) {
            this.rightArm.xRot = -0.8F + 0.025F * Mth.triangleWave((float)â˜ƒ, 70.0F);
            this.leftArm.xRot = 0.0F;
         } else {
            this.rightArm.xRot = (-0.2F + 1.5F * Mth.triangleWave(â˜ƒ, 13.0F)) * â˜ƒ;
            this.leftArm.xRot = (-0.2F - 1.5F * Mth.triangleWave(â˜ƒ, 13.0F)) * â˜ƒ;
         }
      }
   }

   public ModelPart getFlowerHoldingArm() {
      return this.rightArm;
   }
}
