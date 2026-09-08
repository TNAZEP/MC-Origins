package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;

public class VillagerModel<T extends Entity> extends HierarchicalModel<T> implements HeadedModel, VillagerHeadModel {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart hat;
   private final ModelPart hatRim;
   private final ModelPart rightLeg;
   private final ModelPart leftLeg;
   protected final ModelPart nose;

   public VillagerModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.hat = this.head.getChild("hat");
      this.hatRim = this.hat.getChild("hat_rim");
      this.nose = this.head.getChild("nose");
      this.rightLeg = â˜ƒ.getChild("right_leg");
      this.leftLeg = â˜ƒ.getChild("left_leg");
   }

   public static MeshDefinition createBodyModel() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      float â˜ƒxx = 0.5F;
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO
      );
      PartDefinition â˜ƒxxxx = â˜ƒxxx.addOrReplaceChild(
         "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO
      );
      â˜ƒxxxx.addOrReplaceChild(
         "hat_rim",
         CubeListBuilder.create().texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F),
         PartPose.rotation((float) (-Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒxxx.addOrReplaceChild(
         "nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxxx = â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.ZERO
      );
      â˜ƒxxxxx.addOrReplaceChild(
         "jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.ZERO
      );
      â˜ƒx.addOrReplaceChild(
         "arms",
         CubeListBuilder.create()
            .texOffs(44, 22)
            .addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F)
            .texOffs(44, 22)
            .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, true)
            .texOffs(40, 38)
            .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
         PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F)
      );
      return â˜ƒ;
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      boolean â˜ƒ = false;
      if (â˜ƒ instanceof AbstractVillager) {
         â˜ƒ = ((AbstractVillager)â˜ƒ).getUnhappyCounter() > 0;
      }

      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      if (â˜ƒ) {
         this.head.zRot = 0.3F * Mth.sin(0.45F * â˜ƒ);
         this.head.xRot = 0.4F;
      } else {
         this.head.zRot = 0.0F;
      }

      this.rightLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ * 0.5F;
      this.leftLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ * 0.5F;
      this.rightLeg.yRot = 0.0F;
      this.leftLeg.yRot = 0.0F;
   }

   @Override
   public ModelPart getHead() {
      return this.head;
   }

   @Override
   public void hatVisible(boolean var1) {
      this.head.visible = â˜ƒ;
      this.hat.visible = â˜ƒ;
      this.hatRim.visible = â˜ƒ;
   }
}
