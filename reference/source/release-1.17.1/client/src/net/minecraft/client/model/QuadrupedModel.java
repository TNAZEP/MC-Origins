package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class QuadrupedModel<T extends Entity> extends AgeableListModel<T> {
   protected final ModelPart head;
   protected final ModelPart body;
   protected final ModelPart rightHindLeg;
   protected final ModelPart leftHindLeg;
   protected final ModelPart rightFrontLeg;
   protected final ModelPart leftFrontLeg;

   protected QuadrupedModel(ModelPart var1, boolean var2, float var3, float var4, float var5, float var6, int var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (float)â˜ƒ);
      this.head = â˜ƒ.getChild("head");
      this.body = â˜ƒ.getChild("body");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
   }

   public static MeshDefinition createBodyMesh(int var0, CubeDeformation var1) {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, â˜ƒ), PartPose.offset(0.0F, (float)(18 - â˜ƒ), -6.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(28, 8).addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, â˜ƒ),
         PartPose.offsetAndRotation(0.0F, (float)(17 - â˜ƒ), 2.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      CubeListBuilder â˜ƒxx = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, (float)â˜ƒ, 4.0F, â˜ƒ);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxx, PartPose.offset(-3.0F, (float)(24 - â˜ƒ), 7.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxx, PartPose.offset(3.0F, (float)(24 - â˜ƒ), 7.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxx, PartPose.offset(-3.0F, (float)(24 - â˜ƒ), -5.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxx, PartPose.offset(3.0F, (float)(24 - â˜ƒ), -5.0F));
      return â˜ƒ;
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of(this.head);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.rightHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      this.leftHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.rightFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.leftFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
   }
}
