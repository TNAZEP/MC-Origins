package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SalmonModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String BODY_FRONT = "body_front";
   private static final String BODY_BACK = "body_back";
   private final ModelPart root;
   private final ModelPart bodyBack;

   public SalmonModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.bodyBack = â˜ƒ.getChild("body_back");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 20;
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F), PartPose.offset(0.0F, 20.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxx = â˜ƒx.addOrReplaceChild(
         "body_back", CubeListBuilder.create().texOffs(0, 13).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F), PartPose.offset(0.0F, 20.0F, 8.0F)
      );
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F), PartPose.offset(0.0F, 20.0F, 0.0F));
      â˜ƒxxxx.addOrReplaceChild(
         "back_fin", CubeListBuilder.create().texOffs(20, 10).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 8.0F)
      );
      â˜ƒxxx.addOrReplaceChild(
         "top_front_fin", CubeListBuilder.create().texOffs(2, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F), PartPose.offset(0.0F, -4.5F, 5.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "top_back_fin", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F), PartPose.offset(0.0F, -4.5F, -1.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_fin",
         CubeListBuilder.create().texOffs(-4, 0).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F),
         PartPose.offsetAndRotation(-1.5F, 21.5F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI / 4))
      );
      â˜ƒx.addOrReplaceChild(
         "left_fin",
         CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F),
         PartPose.offsetAndRotation(1.5F, 21.5F, 0.0F, 0.0F, 0.0F, (float) (Math.PI / 4))
      );
      return LayerDefinition.create(â˜ƒ, 32, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = 1.0F;
      float â˜ƒx = 1.0F;
      if (!â˜ƒ.isInWater()) {
         â˜ƒ = 1.3F;
         â˜ƒx = 1.7F;
      }

      this.bodyBack.yRot = -â˜ƒ * 0.25F * Mth.sin(â˜ƒx * 0.6F * â˜ƒ);
   }
}
