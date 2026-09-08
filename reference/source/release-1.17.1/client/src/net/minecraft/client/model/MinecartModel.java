package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class MinecartModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private static final String CONTENTS = "contents";
   private final ModelPart contents;

   public MinecartModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.contents = â˜ƒ.getChild("contents");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 20;
      int â˜ƒxxx = 8;
      int â˜ƒxxxx = 16;
      int â˜ƒxxxxx = 4;
      â˜ƒx.addOrReplaceChild(
         "bottom",
         CubeListBuilder.create().texOffs(0, 10).addBox(-10.0F, -8.0F, -1.0F, 20.0F, 16.0F, 2.0F),
         PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "front",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F),
         PartPose.offsetAndRotation(-9.0F, 4.0F, 0.0F, 0.0F, (float) (Math.PI * 3.0 / 2.0), 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "back",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F),
         PartPose.offsetAndRotation(9.0F, 4.0F, 0.0F, 0.0F, (float) (Math.PI / 2), 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F),
         PartPose.offsetAndRotation(0.0F, 4.0F, -7.0F, 0.0F, (float) Math.PI, 0.0F)
      );
      â˜ƒx.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), PartPose.offset(0.0F, 4.0F, 7.0F));
      â˜ƒx.addOrReplaceChild(
         "contents",
         CubeListBuilder.create().texOffs(44, 10).addBox(-9.0F, -7.0F, -1.0F, 18.0F, 14.0F, 1.0F),
         PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, (float) (-Math.PI / 2), 0.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.contents.y = 4.0F - â˜ƒ;
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
