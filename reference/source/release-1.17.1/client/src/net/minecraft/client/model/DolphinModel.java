package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class DolphinModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart tail;
   private final ModelPart tailFin;

   public DolphinModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.body = â˜ƒ.getChild("body");
      this.tail = this.body.getChild("tail");
      this.tailFin = this.tail.getChild("tail_fin");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      float â˜ƒxx = 18.0F;
      float â˜ƒxxx = -8.0F;
      PartDefinition â˜ƒxxxx = â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(22, 0).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F), PartPose.offset(0.0F, 22.0F, -5.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "back_fin", CubeListBuilder.create().texOffs(51, 0).addBox(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F), PartPose.rotation((float) (Math.PI / 3), 0.0F, 0.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "left_fin",
         CubeListBuilder.create().texOffs(48, 20).mirror().addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F),
         PartPose.offsetAndRotation(2.0F, -2.0F, 4.0F, (float) (Math.PI / 3), 0.0F, (float) (Math.PI * 2.0 / 3.0))
      );
      â˜ƒxxxx.addOrReplaceChild(
         "right_fin",
         CubeListBuilder.create().texOffs(48, 20).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F),
         PartPose.offsetAndRotation(-2.0F, -2.0F, 4.0F, (float) (Math.PI / 3), 0.0F, (float) (-Math.PI * 2.0 / 3.0))
      );
      PartDefinition â˜ƒxxxxx = â˜ƒxxxx.addOrReplaceChild(
         "tail",
         CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F),
         PartPose.offsetAndRotation(0.0F, -2.5F, 11.0F, -0.10471976F, 0.0F, 0.0F)
      );
      â˜ƒxxxxx.addOrReplaceChild(
         "tail_fin", CubeListBuilder.create().texOffs(19, 20).addBox(-5.0F, -0.5F, 0.0F, 10.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 9.0F)
      );
      PartDefinition â˜ƒxxxxxx = â˜ƒxxxx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F), PartPose.offset(0.0F, -4.0F, -3.0F)
      );
      â˜ƒxxxxxx.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.body.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.body.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      if (â˜ƒ.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7) {
         this.body.xRot += -0.05F - 0.05F * Mth.cos(â˜ƒ * 0.3F);
         this.tail.xRot = -0.1F * Mth.cos(â˜ƒ * 0.3F);
         this.tailFin.xRot = -0.2F * Mth.cos(â˜ƒ * 0.3F);
      }
   }
}
