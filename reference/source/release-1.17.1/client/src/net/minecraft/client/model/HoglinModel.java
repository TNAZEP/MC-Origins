package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;

public class HoglinModel<T extends Mob & HoglinBase> extends AgeableListModel<T> {
   private static final float DEFAULT_HEAD_X_ROT = 0.87266463F;
   private static final float ATTACK_HEAD_X_ROT_END = (float) (-Math.PI / 9);
   private final ModelPart head;
   private final ModelPart rightEar;
   private final ModelPart leftEar;
   private final ModelPart body;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart mane;

   public HoglinModel(ModelPart var1) {
      super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
      this.body = â˜ƒ.getChild("body");
      this.mane = this.body.getChild("mane");
      this.head = â˜ƒ.getChild("head");
      this.rightEar = this.head.getChild("right_ear");
      this.leftEar = this.head.getChild("left_ear");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(1, 1).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F), PartPose.offset(0.0F, 7.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "mane",
         CubeListBuilder.create().texOffs(90, 33).addBox(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.001F)),
         PartPose.offset(0.0F, -14.0F, -5.0F)
      );
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F),
         PartPose.offsetAndRotation(0.0F, 2.0F, -12.0F, 0.87266463F, 0.0F, 0.0F)
      );
      â˜ƒxxx.addOrReplaceChild(
         "right_ear",
         CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F),
         PartPose.offsetAndRotation(-6.0F, -2.0F, -3.0F, 0.0F, 0.0F, (float) (-Math.PI * 2.0 / 9.0))
      );
      â˜ƒxxx.addOrReplaceChild(
         "left_ear",
         CubeListBuilder.create().texOffs(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F),
         PartPose.offsetAndRotation(6.0F, -2.0F, -3.0F, 0.0F, 0.0F, (float) (Math.PI * 2.0 / 9.0))
      );
      â˜ƒxxx.addOrReplaceChild(
         "right_horn", CubeListBuilder.create().texOffs(10, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(-7.0F, 2.0F, -12.0F)
      );
      â˜ƒxxx.addOrReplaceChild(
         "left_horn", CubeListBuilder.create().texOffs(1, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(7.0F, 2.0F, -12.0F)
      );
      int â˜ƒxxxx = 14;
      int â˜ƒxxxxx = 11;
      â˜ƒx.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().texOffs(66, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F), PartPose.offset(-4.0F, 10.0F, -8.5F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().texOffs(41, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F), PartPose.offset(4.0F, 10.0F, -8.5F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), PartPose.offset(-5.0F, 13.0F, 10.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().texOffs(0, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), PartPose.offset(5.0F, 13.0F, 10.0F)
      );
      return LayerDefinition.create(â˜ƒ, 128, 64);
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of(this.head);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.body, this.rightFrontLeg, this.leftFrontLeg, this.rightHindLeg, this.leftHindLeg);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.rightEar.zRot = (float) (-Math.PI * 2.0 / 9.0) - â˜ƒ * Mth.sin(â˜ƒ);
      this.leftEar.zRot = (float) (Math.PI * 2.0 / 9.0) + â˜ƒ * Mth.sin(â˜ƒ);
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      int â˜ƒ = â˜ƒ.getAttackAnimationRemainingTicks();
      float â˜ƒx = 1.0F - (float)Mth.abs(10 - 2 * â˜ƒ) / 10.0F;
      this.head.xRot = Mth.lerp(â˜ƒx, 0.87266463F, (float) (-Math.PI / 9));
      if (â˜ƒ.isBaby()) {
         this.head.y = Mth.lerp(â˜ƒx, 2.0F, 5.0F);
         this.mane.z = -3.0F;
      } else {
         this.head.y = 2.0F;
         this.mane.z = -7.0F;
      }

      float â˜ƒ = 1.2F;
      this.rightFrontLeg.xRot = Mth.cos(â˜ƒ) * 1.2F * â˜ƒ;
      this.leftFrontLeg.xRot = Mth.cos(â˜ƒ + (float) Math.PI) * 1.2F * â˜ƒ;
      this.rightHindLeg.xRot = this.leftFrontLeg.xRot;
      this.leftHindLeg.xRot = this.rightFrontLeg.xRot;
   }
}
