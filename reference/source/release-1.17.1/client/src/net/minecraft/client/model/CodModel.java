package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class CodModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart tailFin;

   public CodModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.tailFin = â˜ƒ.getChild("tail_fin");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 22;
      â˜ƒx.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 7.0F), PartPose.offset(0.0F, 22.0F, 0.0F));
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(11, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F), PartPose.offset(0.0F, 22.0F, 0.0F));
      â˜ƒx.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 22.0F, -3.0F));
      â˜ƒx.addOrReplaceChild(
         "right_fin",
         CubeListBuilder.create().texOffs(22, 1).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F),
         PartPose.offsetAndRotation(-1.0F, 23.0F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI / 4))
      );
      â˜ƒx.addOrReplaceChild(
         "left_fin",
         CubeListBuilder.create().texOffs(22, 4).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F),
         PartPose.offsetAndRotation(1.0F, 23.0F, 0.0F, 0.0F, 0.0F, (float) (Math.PI / 4))
      );
      â˜ƒx.addOrReplaceChild(
         "tail_fin", CubeListBuilder.create().texOffs(22, 3).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 22.0F, 7.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "top_fin", CubeListBuilder.create().texOffs(20, -6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 20.0F, 0.0F)
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
      if (!â˜ƒ.isInWater()) {
         â˜ƒ = 1.5F;
      }

      this.tailFin.yRot = -â˜ƒ * 0.45F * Mth.sin(0.6F * â˜ƒ);
   }
}
