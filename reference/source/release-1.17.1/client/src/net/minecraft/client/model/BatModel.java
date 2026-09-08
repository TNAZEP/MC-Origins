package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ambient.Bat;

public class BatModel extends HierarchicalModel<Bat> {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightWing;
   private final ModelPart leftWing;
   private final ModelPart rightWingTip;
   private final ModelPart leftWingTip;

   public BatModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.body = â˜ƒ.getChild("body");
      this.rightWing = this.body.getChild("right_wing");
      this.rightWingTip = this.rightWing.getChild("right_wing_tip");
      this.leftWing = this.body.getChild("left_wing");
      this.leftWingTip = this.leftWing.getChild("left_wing_tip");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
      â˜ƒxx.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), PartPose.ZERO);
      â˜ƒxx.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F), PartPose.ZERO);
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F).texOffs(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10.0F, 6.0F, 1.0F),
         PartPose.ZERO
      );
      PartDefinition â˜ƒxxxx = â˜ƒxxx.addOrReplaceChild(
         "right_wing", CubeListBuilder.create().texOffs(42, 0).addBox(-12.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), PartPose.ZERO
      );
      â˜ƒxxxx.addOrReplaceChild(
         "right_wing_tip", CubeListBuilder.create().texOffs(24, 16).addBox(-8.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), PartPose.offset(-12.0F, 1.0F, 1.5F)
      );
      PartDefinition â˜ƒxxxxx = â˜ƒxxx.addOrReplaceChild(
         "left_wing", CubeListBuilder.create().texOffs(42, 0).mirror().addBox(2.0F, 1.0F, 1.5F, 10.0F, 16.0F, 1.0F), PartPose.ZERO
      );
      â˜ƒxxxxx.addOrReplaceChild(
         "left_wing_tip", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(0.0F, 1.0F, 0.0F, 8.0F, 12.0F, 1.0F), PartPose.offset(12.0F, 1.0F, 1.5F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(Bat var1, float var2, float var3, float var4, float var5, float var6) {
      if (â˜ƒ.isResting()) {
         this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
         this.head.yRot = (float) Math.PI - â˜ƒ * (float) (Math.PI / 180.0);
         this.head.zRot = (float) Math.PI;
         this.head.setPos(0.0F, -2.0F, 0.0F);
         this.rightWing.setPos(-3.0F, 0.0F, 3.0F);
         this.leftWing.setPos(3.0F, 0.0F, 3.0F);
         this.body.xRot = (float) Math.PI;
         this.rightWing.xRot = (float) (-Math.PI / 20);
         this.rightWing.yRot = (float) (-Math.PI * 2.0 / 5.0);
         this.rightWingTip.yRot = -1.7278761F;
         this.leftWing.xRot = this.rightWing.xRot;
         this.leftWing.yRot = -this.rightWing.yRot;
         this.leftWingTip.yRot = -this.rightWingTip.yRot;
      } else {
         this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
         this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
         this.head.zRot = 0.0F;
         this.head.setPos(0.0F, 0.0F, 0.0F);
         this.rightWing.setPos(0.0F, 0.0F, 0.0F);
         this.leftWing.setPos(0.0F, 0.0F, 0.0F);
         this.body.xRot = (float) (Math.PI / 4) + Mth.cos(â˜ƒ * 0.1F) * 0.15F;
         this.body.yRot = 0.0F;
         this.rightWing.yRot = Mth.cos(â˜ƒ * 74.48451F * (float) (Math.PI / 180.0)) * (float) Math.PI * 0.25F;
         this.leftWing.yRot = -this.rightWing.yRot;
         this.rightWingTip.yRot = this.rightWing.yRot * 0.5F;
         this.leftWingTip.yRot = -this.rightWing.yRot * 0.5F;
      }
   }
}
