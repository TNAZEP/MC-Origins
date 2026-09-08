package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class WitchModel<T extends Entity> extends VillagerModel<T> {
   private boolean holdingItem;

   public WitchModel(ModelPart var1) {
      super(â˜ƒ);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = VillagerModel.createBodyModel();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO
      );
      PartDefinition â˜ƒxxx = â˜ƒxx.addOrReplaceChild(
         "hat", CubeListBuilder.create().texOffs(0, 64).addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F), PartPose.offset(-5.0F, -10.03125F, -5.0F)
      );
      PartDefinition â˜ƒxxxx = â˜ƒxxx.addOrReplaceChild(
         "hat2",
         CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F),
         PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.05235988F, 0.0F, 0.02617994F)
      );
      PartDefinition â˜ƒxxxxx = â˜ƒxxxx.addOrReplaceChild(
         "hat3",
         CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F),
         PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.10471976F, 0.0F, 0.05235988F)
      );
      â˜ƒxxxxx.addOrReplaceChild(
         "hat4",
         CubeListBuilder.create().texOffs(0, 95).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)),
         PartPose.offsetAndRotation(1.75F, -2.0F, 2.0F, (float) (-Math.PI / 15), 0.0F, 0.10471976F)
      );
      PartDefinition â˜ƒxxxxxx = â˜ƒxx.getChild("nose");
      â˜ƒxxxxxx.addOrReplaceChild(
         "mole",
         CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)),
         PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 128);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.nose.setPos(0.0F, -2.0F, 0.0F);
      float â˜ƒ = 0.01F * (float)(â˜ƒ.getId() % 10);
      this.nose.xRot = Mth.sin((float)â˜ƒ.tickCount * â˜ƒ) * 4.5F * (float) (Math.PI / 180.0);
      this.nose.yRot = 0.0F;
      this.nose.zRot = Mth.cos((float)â˜ƒ.tickCount * â˜ƒ) * 2.5F * (float) (Math.PI / 180.0);
      if (this.holdingItem) {
         this.nose.setPos(0.0F, 1.0F, -1.5F);
         this.nose.xRot = -0.9F;
      }
   }

   public ModelPart getNose() {
      return this.nose;
   }

   public void setHoldingItem(boolean var1) {
      this.holdingItem = â˜ƒ;
   }
}
