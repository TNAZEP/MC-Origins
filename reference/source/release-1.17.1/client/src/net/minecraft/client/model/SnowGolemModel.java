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

public class SnowGolemModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String UPPER_BODY = "upper_body";
   private final ModelPart root;
   private final ModelPart upperBody;
   private final ModelPart head;
   private final ModelPart leftArm;
   private final ModelPart rightArm;

   public SnowGolemModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.leftArm = â˜ƒ.getChild("left_arm");
      this.rightArm = â˜ƒ.getChild("right_arm");
      this.upperBody = â˜ƒ.getChild("upper_body");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      float â˜ƒxx = 4.0F;
      CubeDeformation â˜ƒxxx = new CubeDeformation(-0.5F);
      â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, â˜ƒxxx), PartPose.offset(0.0F, 4.0F, 0.0F)
      );
      CubeListBuilder â˜ƒxxxx = CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, â˜ƒxxx);
      â˜ƒx.addOrReplaceChild("left_arm", â˜ƒxxxx, PartPose.offsetAndRotation(5.0F, 6.0F, 1.0F, 0.0F, 0.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("right_arm", â˜ƒxxxx, PartPose.offsetAndRotation(-5.0F, 6.0F, -1.0F, 0.0F, (float) Math.PI, -1.0F));
      â˜ƒx.addOrReplaceChild(
         "upper_body", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, â˜ƒxxx), PartPose.offset(0.0F, 13.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "lower_body", CubeListBuilder.create().texOffs(0, 36).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, â˜ƒxxx), PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.upperBody.yRot = â˜ƒ * (float) (Math.PI / 180.0) * 0.25F;
      float â˜ƒ = Mth.sin(this.upperBody.yRot);
      float â˜ƒx = Mth.cos(this.upperBody.yRot);
      this.leftArm.yRot = this.upperBody.yRot;
      this.rightArm.yRot = this.upperBody.yRot + (float) Math.PI;
      this.leftArm.x = â˜ƒx * 5.0F;
      this.leftArm.z = -â˜ƒ * 5.0F;
      this.rightArm.x = -â˜ƒx * 5.0F;
      this.rightArm.z = â˜ƒ * 5.0F;
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public ModelPart getHead() {
      return this.head;
   }
}
