package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class CreeperModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private static final int Y_OFFSET = 6;

   public CreeperModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.leftHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.rightHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.leftFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.rightFrontLeg = â˜ƒ.getChild("left_front_leg");
   }

   public static LayerDefinition createBodyLayer(CubeDeformation var0) {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, â˜ƒ), PartPose.offset(0.0F, 6.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(0.0F, 6.0F, 0.0F)
      );
      CubeListBuilder â˜ƒxx = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, â˜ƒ);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxx, PartPose.offset(-2.0F, 18.0F, 4.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxx, PartPose.offset(2.0F, 18.0F, 4.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxx, PartPose.offset(-2.0F, 18.0F, -4.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxx, PartPose.offset(2.0F, 18.0F, -4.0F));
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.rightHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      this.leftHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.rightFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.leftFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
   }
}
