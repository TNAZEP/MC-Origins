package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class EvokerFangsModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String BASE = "base";
   private static final String UPPER_JAW = "upper_jaw";
   private static final String LOWER_JAW = "lower_jaw";
   private final ModelPart root;
   private final ModelPart base;
   private final ModelPart upperJaw;
   private final ModelPart lowerJaw;

   public EvokerFangsModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.base = â˜ƒ.getChild("base");
      this.upperJaw = â˜ƒ.getChild("upper_jaw");
      this.lowerJaw = â˜ƒ.getChild("lower_jaw");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F), PartPose.offset(-5.0F, 24.0F, -5.0F));
      CubeListBuilder â˜ƒxx = CubeListBuilder.create().texOffs(40, 0).addBox(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
      â˜ƒx.addOrReplaceChild("upper_jaw", â˜ƒxx, PartPose.offset(1.5F, 24.0F, -4.0F));
      â˜ƒx.addOrReplaceChild("lower_jaw", â˜ƒxx, PartPose.offsetAndRotation(-1.5F, 24.0F, 4.0F, 0.0F, (float) Math.PI, 0.0F));
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = â˜ƒ * 2.0F;
      if (â˜ƒ > 1.0F) {
         â˜ƒ = 1.0F;
      }

      â˜ƒ = 1.0F - â˜ƒ * â˜ƒ * â˜ƒ;
      this.upperJaw.zRot = (float) Math.PI - â˜ƒ * 0.35F * (float) Math.PI;
      this.lowerJaw.zRot = (float) Math.PI + â˜ƒ * 0.35F * (float) Math.PI;
      float â˜ƒ = (â˜ƒ + Mth.sin(â˜ƒ * 2.7F)) * 0.6F * 12.0F;
      this.upperJaw.y = 24.0F - â˜ƒ;
      this.lowerJaw.y = this.upperJaw.y;
      this.base.y = this.upperJaw.y;
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
