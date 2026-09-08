package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Ravager;

public class RavagerModel extends HierarchicalModel<Ravager> {
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart mouth;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart neck;

   public RavagerModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.neck = â˜ƒ.getChild("neck");
      this.head = this.neck.getChild("head");
      this.mouth = this.head.getChild("mouth");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 16;
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "neck", CubeListBuilder.create().texOffs(68, 73).addBox(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F), PartPose.offset(0.0F, -7.0F, 5.5F)
      );
      PartDefinition â˜ƒxxxx = â˜ƒxxx.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F).texOffs(0, 0).addBox(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F),
         PartPose.offset(0.0F, 16.0F, -17.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "right_horn",
         CubeListBuilder.create().texOffs(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F),
         PartPose.offsetAndRotation(-10.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "left_horn",
         CubeListBuilder.create().texOffs(74, 55).mirror().addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F),
         PartPose.offsetAndRotation(8.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F)
      );
      â˜ƒxxxx.addOrReplaceChild(
         "mouth", CubeListBuilder.create().texOffs(0, 36).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F), PartPose.offset(0.0F, -2.0F, 2.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 55)
            .addBox(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F)
            .texOffs(0, 91)
            .addBox(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F),
         PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().texOffs(96, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(-8.0F, -13.0F, 18.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().texOffs(96, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(8.0F, -13.0F, 18.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().texOffs(64, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(-8.0F, -13.0F, -5.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), PartPose.offset(8.0F, -13.0F, -5.0F)
      );
      return LayerDefinition.create(â˜ƒ, 128, 128);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(Ravager var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      float â˜ƒ = 0.4F * â˜ƒ;
      this.rightHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * â˜ƒ;
      this.leftHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * â˜ƒ;
      this.rightFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * â˜ƒ;
      this.leftFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * â˜ƒ;
   }

   public void prepareMobModel(Ravager var1, float var2, float var3, float var4) {
      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒ = â˜ƒ.getStunnedTick();
      int â˜ƒx = â˜ƒ.getRoarTick();
      int â˜ƒxx = 20;
      int â˜ƒxxx = â˜ƒ.getAttackTick();
      int â˜ƒxxxx = 10;
      if (â˜ƒxxx > 0) {
         float â˜ƒxxxxx = Mth.triangleWave((float)â˜ƒxxx - â˜ƒ, 10.0F);
         float â˜ƒxxxxxx = (1.0F + â˜ƒxxxxx) * 0.5F;
         float â˜ƒxxxxxxx = â˜ƒxxxxxx * â˜ƒxxxxxx * â˜ƒxxxxxx * 12.0F;
         float â˜ƒxxxxxxxx = â˜ƒxxxxxxx * Mth.sin(this.neck.xRot);
         this.neck.z = -6.5F + â˜ƒxxxxxxx;
         this.neck.y = -7.0F - â˜ƒxxxxxxxx;
         float â˜ƒxxxxxxxxx = Mth.sin(((float)â˜ƒxxx - â˜ƒ) / 10.0F * (float) Math.PI * 0.25F);
         this.mouth.xRot = (float) (Math.PI / 2) * â˜ƒxxxxxxxxx;
         if (â˜ƒxxx > 5) {
            this.mouth.xRot = Mth.sin(((float)(-4 + â˜ƒxxx) - â˜ƒ) / 4.0F) * (float) Math.PI * 0.4F;
         } else {
            this.mouth.xRot = (float) (Math.PI / 20) * Mth.sin((float) Math.PI * ((float)â˜ƒxxx - â˜ƒ) / 10.0F);
         }
      } else {
         float â˜ƒ = -1.0F;
         float â˜ƒx = -1.0F * Mth.sin(this.neck.xRot);
         this.neck.x = 0.0F;
         this.neck.y = -7.0F - â˜ƒx;
         this.neck.z = 5.5F;
         boolean â˜ƒxx = â˜ƒ > 0;
         this.neck.xRot = â˜ƒxx ? 0.21991149F : 0.0F;
         this.mouth.xRot = (float) Math.PI * (â˜ƒxx ? 0.05F : 0.01F);
         if (â˜ƒxx) {
            double â˜ƒxxx = (double)â˜ƒ / 40.0;
            this.neck.x = (float)Math.sin(â˜ƒxxx * 10.0) * 3.0F;
         } else if (â˜ƒx > 0) {
            float â˜ƒ = Mth.sin(((float)(20 - â˜ƒx) - â˜ƒ) / 20.0F * (float) Math.PI * 0.25F);
            this.mouth.xRot = (float) (Math.PI / 2) * â˜ƒ;
         }
      }
   }
}
