package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.goat.Goat;

public class GoatModel<T extends Goat> extends QuadrupedModel<T> {
   public GoatModel(ModelPart var1) {
      super(â˜ƒ, true, 19.0F, 1.0F, 2.5F, 2.0F, 24);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(2, 61)
            .addBox("right ear", -6.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F)
            .texOffs(2, 61)
            .mirror()
            .addBox("left ear", 2.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F)
            .texOffs(23, 52)
            .addBox("goatee", -0.5F, -3.0F, -14.0F, 0.0F, 7.0F, 5.0F),
         PartPose.offset(1.0F, 14.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "left_horn", CubeListBuilder.create().texOffs(12, 55).addBox(-0.01F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "right_horn", CubeListBuilder.create().texOffs(12, 55).addBox(-2.99F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "nose",
         CubeListBuilder.create().texOffs(34, 46).addBox(-3.0F, -4.0F, -8.0F, 5.0F, 7.0F, 10.0F),
         PartPose.offsetAndRotation(0.0F, -8.0F, -8.0F, 0.9599F, 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(1, 1)
            .addBox(-4.0F, -17.0F, -7.0F, 9.0F, 11.0F, 16.0F)
            .texOffs(0, 28)
            .addBox(-5.0F, -18.0F, -8.0F, 11.0F, 14.0F, 11.0F),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().texOffs(36, 29).addBox(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), PartPose.offset(1.0F, 14.0F, 4.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().texOffs(49, 29).addBox(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), PartPose.offset(-3.0F, 14.0F, 4.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().texOffs(49, 2).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), PartPose.offset(1.0F, 14.0F, -6.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().texOffs(35, 2).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), PartPose.offset(-3.0F, 14.0F, -6.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.getChild("left_horn").visible = !â˜ƒ.isBaby();
      this.head.getChild("right_horn").visible = !â˜ƒ.isBaby();
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = â˜ƒ.getRammingXHeadRot();
      if (â˜ƒ != 0.0F) {
         this.head.xRot = â˜ƒ;
      }
   }
}
