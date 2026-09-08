package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Strider;

public class StriderModel<T extends Strider> extends HierarchicalModel<T> {
   private static final String RIGHT_BOTTOM_BRISTLE = "right_bottom_bristle";
   private static final String RIGHT_MIDDLE_BRISTLE = "right_middle_bristle";
   private static final String RIGHT_TOP_BRISTLE = "right_top_bristle";
   private static final String LEFT_TOP_BRISTLE = "left_top_bristle";
   private static final String LEFT_MIDDLE_BRISTLE = "left_middle_bristle";
   private static final String LEFT_BOTTOM_BRISTLE = "left_bottom_bristle";
   private final ModelPart root;
   private final ModelPart rightLeg;
   private final ModelPart leftLeg;
   private final ModelPart body;
   private final ModelPart rightBottomBristle;
   private final ModelPart rightMiddleBristle;
   private final ModelPart rightTopBristle;
   private final ModelPart leftTopBristle;
   private final ModelPart leftMiddleBristle;
   private final ModelPart leftBottomBristle;

   public StriderModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.rightLeg = â˜ƒ.getChild("right_leg");
      this.leftLeg = â˜ƒ.getChild("left_leg");
      this.body = â˜ƒ.getChild("body");
      this.rightBottomBristle = this.body.getChild("right_bottom_bristle");
      this.rightMiddleBristle = this.body.getChild("right_middle_bristle");
      this.rightTopBristle = this.body.getChild("right_top_bristle");
      this.leftTopBristle = this.body.getChild("left_top_bristle");
      this.leftMiddleBristle = this.body.getChild("left_middle_bristle");
      this.leftBottomBristle = this.body.getChild("left_bottom_bristle");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F), PartPose.offset(-4.0F, 8.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 55).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F), PartPose.offset(4.0F, 8.0F, 0.0F)
      );
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F), PartPose.offset(0.0F, 1.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "right_bottom_bristle",
         CubeListBuilder.create().texOffs(16, 65).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true),
         PartPose.offsetAndRotation(-8.0F, 4.0F, -8.0F, 0.0F, 0.0F, -1.2217305F)
      );
      â˜ƒxx.addOrReplaceChild(
         "right_middle_bristle",
         CubeListBuilder.create().texOffs(16, 49).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true),
         PartPose.offsetAndRotation(-8.0F, -1.0F, -8.0F, 0.0F, 0.0F, -1.134464F)
      );
      â˜ƒxx.addOrReplaceChild(
         "right_top_bristle",
         CubeListBuilder.create().texOffs(16, 33).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true),
         PartPose.offsetAndRotation(-8.0F, -5.0F, -8.0F, 0.0F, 0.0F, -0.87266463F)
      );
      â˜ƒxx.addOrReplaceChild(
         "left_top_bristle",
         CubeListBuilder.create().texOffs(16, 33).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F),
         PartPose.offsetAndRotation(8.0F, -6.0F, -8.0F, 0.0F, 0.0F, 0.87266463F)
      );
      â˜ƒxx.addOrReplaceChild(
         "left_middle_bristle",
         CubeListBuilder.create().texOffs(16, 49).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F),
         PartPose.offsetAndRotation(8.0F, -2.0F, -8.0F, 0.0F, 0.0F, 1.134464F)
      );
      â˜ƒxx.addOrReplaceChild(
         "left_bottom_bristle",
         CubeListBuilder.create().texOffs(16, 65).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F),
         PartPose.offsetAndRotation(8.0F, 3.0F, -8.0F, 0.0F, 0.0F, 1.2217305F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 128);
   }

   public void setupAnim(Strider var1, float var2, float var3, float var4, float var5, float var6) {
      â˜ƒ = Math.min(0.25F, â˜ƒ);
      if (!â˜ƒ.isVehicle()) {
         this.body.xRot = â˜ƒ * (float) (Math.PI / 180.0);
         this.body.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      } else {
         this.body.xRot = 0.0F;
         this.body.yRot = 0.0F;
      }

      float â˜ƒ = 1.5F;
      this.body.zRot = 0.1F * Mth.sin(â˜ƒ * 1.5F) * 4.0F * â˜ƒ;
      this.body.y = 2.0F;
      this.body.y -= 2.0F * Mth.cos(â˜ƒ * 1.5F) * 2.0F * â˜ƒ;
      this.leftLeg.xRot = Mth.sin(â˜ƒ * 1.5F * 0.5F) * 2.0F * â˜ƒ;
      this.rightLeg.xRot = Mth.sin(â˜ƒ * 1.5F * 0.5F + (float) Math.PI) * 2.0F * â˜ƒ;
      this.leftLeg.zRot = (float) (Math.PI / 18) * Mth.cos(â˜ƒ * 1.5F * 0.5F) * â˜ƒ;
      this.rightLeg.zRot = (float) (Math.PI / 18) * Mth.cos(â˜ƒ * 1.5F * 0.5F + (float) Math.PI) * â˜ƒ;
      this.leftLeg.y = 8.0F + 2.0F * Mth.sin(â˜ƒ * 1.5F * 0.5F + (float) Math.PI) * 2.0F * â˜ƒ;
      this.rightLeg.y = 8.0F + 2.0F * Mth.sin(â˜ƒ * 1.5F * 0.5F) * 2.0F * â˜ƒ;
      this.rightBottomBristle.zRot = -1.2217305F;
      this.rightMiddleBristle.zRot = -1.134464F;
      this.rightTopBristle.zRot = -0.87266463F;
      this.leftTopBristle.zRot = 0.87266463F;
      this.leftMiddleBristle.zRot = 1.134464F;
      this.leftBottomBristle.zRot = 1.2217305F;
      float â˜ƒx = Mth.cos(â˜ƒ * 1.5F + (float) Math.PI) * â˜ƒ;
      this.rightBottomBristle.zRot += â˜ƒx * 1.3F;
      this.rightMiddleBristle.zRot += â˜ƒx * 1.2F;
      this.rightTopBristle.zRot += â˜ƒx * 0.6F;
      this.leftTopBristle.zRot += â˜ƒx * 0.6F;
      this.leftMiddleBristle.zRot += â˜ƒx * 1.2F;
      this.leftBottomBristle.zRot += â˜ƒx * 1.3F;
      float â˜ƒxx = 1.0F;
      float â˜ƒxxx = 1.0F;
      this.rightBottomBristle.zRot += 0.05F * Mth.sin(â˜ƒ * 1.0F * -0.4F);
      this.rightMiddleBristle.zRot += 0.1F * Mth.sin(â˜ƒ * 1.0F * 0.2F);
      this.rightTopBristle.zRot += 0.1F * Mth.sin(â˜ƒ * 1.0F * 0.4F);
      this.leftTopBristle.zRot += 0.1F * Mth.sin(â˜ƒ * 1.0F * 0.4F);
      this.leftMiddleBristle.zRot += 0.1F * Mth.sin(â˜ƒ * 1.0F * 0.2F);
      this.leftBottomBristle.zRot += 0.05F * Mth.sin(â˜ƒ * 1.0F * -0.4F);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
