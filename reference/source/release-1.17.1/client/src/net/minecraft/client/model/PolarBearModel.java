package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.PolarBear;

public class PolarBearModel<T extends PolarBear> extends QuadrupedModel<T> {
   public PolarBearModel(ModelPart var1) {
      super(â˜ƒ, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F)
            .texOffs(0, 44)
            .addBox("mouth", -2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F)
            .texOffs(26, 0)
            .addBox("right_ear", -4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F)
            .texOffs(26, 0)
            .mirror()
            .addBox("left_ear", 2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F),
         PartPose.offset(0.0F, 10.0F, -16.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 19)
            .addBox(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F)
            .texOffs(39, 0)
            .addBox(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F),
         PartPose.offsetAndRotation(-2.0F, 9.0F, 12.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      int â˜ƒxx = 10;
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(50, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxxx, PartPose.offset(-4.5F, 14.0F, 6.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxxx, PartPose.offset(4.5F, 14.0F, 6.0F));
      CubeListBuilder â˜ƒxxxx = CubeListBuilder.create().texOffs(50, 40).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F);
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxxxx, PartPose.offset(-3.5F, 14.0F, -8.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxxxx, PartPose.offset(3.5F, 14.0F, -8.0F));
      return LayerDefinition.create(â˜ƒ, 128, 64);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = â˜ƒ - (float)â˜ƒ.tickCount;
      float â˜ƒx = â˜ƒ.getStandingAnimationScale(â˜ƒ);
      â˜ƒx *= â˜ƒx;
      float â˜ƒxx = 1.0F - â˜ƒx;
      this.body.xRot = (float) (Math.PI / 2) - â˜ƒx * (float) Math.PI * 0.35F;
      this.body.y = 9.0F * â˜ƒxx + 11.0F * â˜ƒx;
      this.rightFrontLeg.y = 14.0F * â˜ƒxx - 6.0F * â˜ƒx;
      this.rightFrontLeg.z = -8.0F * â˜ƒxx - 4.0F * â˜ƒx;
      this.rightFrontLeg.xRot -= â˜ƒx * (float) Math.PI * 0.45F;
      this.leftFrontLeg.y = this.rightFrontLeg.y;
      this.leftFrontLeg.z = this.rightFrontLeg.z;
      this.leftFrontLeg.xRot -= â˜ƒx * (float) Math.PI * 0.45F;
      if (this.young) {
         this.head.y = 10.0F * â˜ƒxx - 9.0F * â˜ƒx;
         this.head.z = -16.0F * â˜ƒxx - 7.0F * â˜ƒx;
      } else {
         this.head.y = 10.0F * â˜ƒxx - 14.0F * â˜ƒx;
         this.head.z = -16.0F * â˜ƒxx - 3.0F * â˜ƒx;
      }

      this.head.xRot += â˜ƒx * (float) Math.PI * 0.15F;
   }
}
