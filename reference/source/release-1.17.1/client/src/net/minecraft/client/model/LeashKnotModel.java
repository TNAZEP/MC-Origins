package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class LeashKnotModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String KNOT = "knot";
   private final ModelPart root;
   private final ModelPart knot;

   public LeashKnotModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.knot = â˜ƒ.getChild("knot");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("knot", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 32, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.knot.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.knot.xRot = â˜ƒ * (float) (Math.PI / 180.0);
   }
}
