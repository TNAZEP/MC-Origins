package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class ShulkerBulletModel<T extends Entity> extends HierarchicalModel<T> {
   private static final String MAIN = "main";
   private final ModelPart root;
   private final ModelPart main;

   public ShulkerBulletModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.main = â˜ƒ.getChild("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "main",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F)
            .texOffs(0, 10)
            .addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F)
            .texOffs(20, 0)
            .addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F),
         PartPose.ZERO
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.main.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.main.xRot = â˜ƒ * (float) (Math.PI / 180.0);
   }
}
