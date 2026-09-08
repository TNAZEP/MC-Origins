package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.Sheep;

public class SheepFurModel<T extends Sheep> extends QuadrupedModel<T> {
   private float headXRot;

   public SheepFurModel(ModelPart var1) {
      super(â˜ƒ, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
   }

   public static LayerDefinition createFurLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.6F)),
         PartPose.offset(0.0F, 6.0F, -8.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.75F)),
         PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      CubeListBuilder â˜ƒxx = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F));
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxx, PartPose.offset(-3.0F, 12.0F, 7.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxx, PartPose.offset(3.0F, 12.0F, 7.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxx, PartPose.offset(-3.0F, 12.0F, -5.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxx, PartPose.offset(3.0F, 12.0F, -5.0F));
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.head.y = 6.0F + â˜ƒ.getHeadEatPositionScale(â˜ƒ) * 9.0F;
      this.headXRot = â˜ƒ.getHeadEatAngleScale(â˜ƒ);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.head.xRot = this.headXRot;
   }
}
