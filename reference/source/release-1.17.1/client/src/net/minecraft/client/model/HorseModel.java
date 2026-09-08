package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class HorseModel<T extends AbstractHorse> extends AgeableListModel<T> {
   private static final float DEG_125 = 2.1816616F;
   private static final float DEG_60 = (float) (Math.PI / 3);
   private static final float DEG_45 = (float) (Math.PI / 4);
   private static final float DEG_30 = (float) (Math.PI / 6);
   private static final float DEG_15 = (float) (Math.PI / 12);
   protected static final String HEAD_PARTS = "head_parts";
   private static final String LEFT_HIND_BABY_LEG = "left_hind_baby_leg";
   private static final String RIGHT_HIND_BABY_LEG = "right_hind_baby_leg";
   private static final String LEFT_FRONT_BABY_LEG = "left_front_baby_leg";
   private static final String RIGHT_FRONT_BABY_LEG = "right_front_baby_leg";
   private static final String SADDLE = "saddle";
   private static final String LEFT_SADDLE_MOUTH = "left_saddle_mouth";
   private static final String LEFT_SADDLE_LINE = "left_saddle_line";
   private static final String RIGHT_SADDLE_MOUTH = "right_saddle_mouth";
   private static final String RIGHT_SADDLE_LINE = "right_saddle_line";
   private static final String HEAD_SADDLE = "head_saddle";
   private static final String MOUTH_SADDLE_WRAP = "mouth_saddle_wrap";
   protected final ModelPart body;
   protected final ModelPart headParts;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart rightHindBabyLeg;
   private final ModelPart leftHindBabyLeg;
   private final ModelPart rightFrontBabyLeg;
   private final ModelPart leftFrontBabyLeg;
   private final ModelPart tail;
   private final ModelPart[] saddleParts;
   private final ModelPart[] ridingParts;

   public HorseModel(ModelPart var1) {
      super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
      this.body = â˜ƒ.getChild("body");
      this.headParts = â˜ƒ.getChild("head_parts");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
      this.rightHindBabyLeg = â˜ƒ.getChild("right_hind_baby_leg");
      this.leftHindBabyLeg = â˜ƒ.getChild("left_hind_baby_leg");
      this.rightFrontBabyLeg = â˜ƒ.getChild("right_front_baby_leg");
      this.leftFrontBabyLeg = â˜ƒ.getChild("left_front_baby_leg");
      this.tail = this.body.getChild("tail");
      ModelPart â˜ƒ = this.body.getChild("saddle");
      ModelPart â˜ƒx = this.headParts.getChild("left_saddle_mouth");
      ModelPart â˜ƒxx = this.headParts.getChild("right_saddle_mouth");
      ModelPart â˜ƒxxx = this.headParts.getChild("left_saddle_line");
      ModelPart â˜ƒxxxx = this.headParts.getChild("right_saddle_line");
      ModelPart â˜ƒxxxxx = this.headParts.getChild("head_saddle");
      ModelPart â˜ƒxxxxxx = this.headParts.getChild("mouth_saddle_wrap");
      this.saddleParts = new ModelPart[]{â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxx};
      this.ridingParts = new ModelPart[]{â˜ƒxxx, â˜ƒxxxx};
   }

   public static MeshDefinition createBodyMesh(CubeDeformation var0) {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)),
         PartPose.offset(0.0F, 11.0F, 5.0F)
      );
      PartDefinition â˜ƒxxx = â˜ƒx.addOrReplaceChild(
         "head_parts",
         CubeListBuilder.create().texOffs(0, 35).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F),
         PartPose.offsetAndRotation(0.0F, 4.0F, -12.0F, (float) (Math.PI / 6), 0.0F, 0.0F)
      );
      PartDefinition â˜ƒxxxx = â˜ƒxxx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, â˜ƒ), PartPose.ZERO
      );
      â˜ƒxxx.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, â˜ƒ), PartPose.ZERO);
      â˜ƒxxx.addOrReplaceChild("upper_mouth", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, â˜ƒ), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild(
         "left_hind_leg",
         CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, â˜ƒ),
         PartPose.offset(4.0F, 14.0F, 7.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, â˜ƒ), PartPose.offset(-4.0F, 14.0F, 7.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_front_leg",
         CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, â˜ƒ),
         PartPose.offset(4.0F, 14.0F, -12.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_front_leg",
         CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, â˜ƒ),
         PartPose.offset(-4.0F, 14.0F, -12.0F)
      );
      CubeDeformation â˜ƒxxxxx = â˜ƒ.extend(0.0F, 5.5F, 0.0F);
      â˜ƒx.addOrReplaceChild(
         "left_hind_baby_leg",
         CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, â˜ƒxxxxx),
         PartPose.offset(4.0F, 14.0F, 7.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_hind_baby_leg",
         CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, â˜ƒxxxxx),
         PartPose.offset(-4.0F, 14.0F, 7.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_front_baby_leg",
         CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, â˜ƒxxxxx),
         PartPose.offset(4.0F, 14.0F, -12.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_front_baby_leg",
         CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, â˜ƒxxxxx),
         PartPose.offset(-4.0F, 14.0F, -12.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "tail",
         CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, â˜ƒ),
         PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, (float) (Math.PI / 6), 0.0F, 0.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.ZERO
      );
      â˜ƒxxx.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, â˜ƒ), PartPose.ZERO);
      â˜ƒxxx.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, â˜ƒ), PartPose.ZERO);
      â˜ƒxxx.addOrReplaceChild(
         "left_saddle_line",
         CubeListBuilder.create().texOffs(32, 2).addBox(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, â˜ƒ),
         PartPose.rotation((float) (-Math.PI / 6), 0.0F, 0.0F)
      );
      â˜ƒxxx.addOrReplaceChild(
         "right_saddle_line",
         CubeListBuilder.create().texOffs(32, 2).addBox(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, â˜ƒ),
         PartPose.rotation((float) (-Math.PI / 6), 0.0F, 0.0F)
      );
      â˜ƒxxx.addOrReplaceChild(
         "head_saddle", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.ZERO
      );
      â˜ƒxxx.addOrReplaceChild(
         "mouth_saddle_wrap", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.ZERO
      );
      â˜ƒxxxx.addOrReplaceChild(
         "left_ear", CubeListBuilder.create().texOffs(19, 16).addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO
      );
      â˜ƒxxxx.addOrReplaceChild(
         "right_ear", CubeListBuilder.create().texOffs(19, 16).addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO
      );
      return â˜ƒ;
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      boolean â˜ƒ = â˜ƒ.isSaddled();
      boolean â˜ƒx = â˜ƒ.isVehicle();

      for(ModelPart â˜ƒxx : this.saddleParts) {
         â˜ƒxx.visible = â˜ƒ;
      }

      for(ModelPart â˜ƒxx : this.ridingParts) {
         â˜ƒxx.visible = â˜ƒx && â˜ƒ;
      }

      this.body.y = 11.0F;
   }

   @Override
   public Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of(this.headParts);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(
         this.body,
         this.rightHindLeg,
         this.leftHindLeg,
         this.rightFrontLeg,
         this.leftFrontLeg,
         this.rightHindBabyLeg,
         this.leftHindBabyLeg,
         this.rightFrontBabyLeg,
         this.leftFrontBabyLeg
      );
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      super.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = Mth.rotlerp(â˜ƒ.yBodyRotO, â˜ƒ.yBodyRot, â˜ƒ);
      float â˜ƒx = Mth.rotlerp(â˜ƒ.yHeadRotO, â˜ƒ.yHeadRot, â˜ƒ);
      float â˜ƒxx = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
      float â˜ƒxxx = â˜ƒx - â˜ƒ;
      float â˜ƒxxxx = â˜ƒxx * (float) (Math.PI / 180.0);
      if (â˜ƒxxx > 20.0F) {
         â˜ƒxxx = 20.0F;
      }

      if (â˜ƒxxx < -20.0F) {
         â˜ƒxxx = -20.0F;
      }

      if (â˜ƒ > 0.2F) {
         â˜ƒxxxx += Mth.cos(â˜ƒ * 0.4F) * 0.15F * â˜ƒ;
      }

      float â˜ƒ = â˜ƒ.getEatAnim(â˜ƒ);
      float â˜ƒx = â˜ƒ.getStandAnim(â˜ƒ);
      float â˜ƒxx = 1.0F - â˜ƒx;
      float â˜ƒxxx = â˜ƒ.getMouthAnim(â˜ƒ);
      boolean â˜ƒxxxx = â˜ƒ.tailCounter != 0;
      float â˜ƒxxxxx = (float)â˜ƒ.tickCount + â˜ƒ;
      this.headParts.y = 4.0F;
      this.headParts.z = -12.0F;
      this.body.xRot = 0.0F;
      this.headParts.xRot = (float) (Math.PI / 6) + â˜ƒxxxx;
      this.headParts.yRot = â˜ƒxxx * (float) (Math.PI / 180.0);
      float â˜ƒxxxxxx = â˜ƒ.isInWater() ? 0.2F : 1.0F;
      float â˜ƒxxxxxxx = Mth.cos(â˜ƒxxxxxx * â˜ƒ * 0.6662F + (float) Math.PI);
      float â˜ƒxxxxxxxx = â˜ƒxxxxxxx * 0.8F * â˜ƒ;
      float â˜ƒxxxxxxxxx = (1.0F - Math.max(â˜ƒx, â˜ƒ)) * ((float) (Math.PI / 6) + â˜ƒxxxx + â˜ƒxxx * Mth.sin(â˜ƒxxxxx) * 0.05F);
      this.headParts.xRot = â˜ƒx * ((float) (Math.PI / 12) + â˜ƒxxxx) + â˜ƒ * (2.1816616F + Mth.sin(â˜ƒxxxxx) * 0.05F) + â˜ƒxxxxxxxxx;
      this.headParts.yRot = â˜ƒx * â˜ƒxxx * (float) (Math.PI / 180.0) + (1.0F - Math.max(â˜ƒx, â˜ƒ)) * this.headParts.yRot;
      this.headParts.y = â˜ƒx * -4.0F + â˜ƒ * 11.0F + (1.0F - Math.max(â˜ƒx, â˜ƒ)) * this.headParts.y;
      this.headParts.z = â˜ƒx * -4.0F + â˜ƒ * -12.0F + (1.0F - Math.max(â˜ƒx, â˜ƒ)) * this.headParts.z;
      this.body.xRot = â˜ƒx * (float) (-Math.PI / 4) + â˜ƒxx * this.body.xRot;
      float â˜ƒxxxxxxxxxx = (float) (Math.PI / 12) * â˜ƒx;
      float â˜ƒxxxxxxxxxxx = Mth.cos(â˜ƒxxxxx * 0.6F + (float) Math.PI);
      this.leftFrontLeg.y = 2.0F * â˜ƒx + 14.0F * â˜ƒxx;
      this.leftFrontLeg.z = -6.0F * â˜ƒx - 10.0F * â˜ƒxx;
      this.rightFrontLeg.y = this.leftFrontLeg.y;
      this.rightFrontLeg.z = this.leftFrontLeg.z;
      float â˜ƒxxxxxxxxxxxx = ((float) (-Math.PI / 3) + â˜ƒxxxxxxxxxxx) * â˜ƒx + â˜ƒxxxxxxxx * â˜ƒxx;
      float â˜ƒxxxxxxxxxxxxx = ((float) (-Math.PI / 3) - â˜ƒxxxxxxxxxxx) * â˜ƒx - â˜ƒxxxxxxxx * â˜ƒxx;
      this.leftHindLeg.xRot = â˜ƒxxxxxxxxxx - â˜ƒxxxxxxx * 0.5F * â˜ƒ * â˜ƒxx;
      this.rightHindLeg.xRot = â˜ƒxxxxxxxxxx + â˜ƒxxxxxxx * 0.5F * â˜ƒ * â˜ƒxx;
      this.leftFrontLeg.xRot = â˜ƒxxxxxxxxxxxx;
      this.rightFrontLeg.xRot = â˜ƒxxxxxxxxxxxxx;
      this.tail.xRot = (float) (Math.PI / 6) + â˜ƒ * 0.75F;
      this.tail.y = -5.0F + â˜ƒ;
      this.tail.z = 2.0F + â˜ƒ * 2.0F;
      if (â˜ƒxxxx) {
         this.tail.yRot = Mth.cos(â˜ƒxxxxx * 0.7F);
      } else {
         this.tail.yRot = 0.0F;
      }

      this.rightHindBabyLeg.y = this.rightHindLeg.y;
      this.rightHindBabyLeg.z = this.rightHindLeg.z;
      this.rightHindBabyLeg.xRot = this.rightHindLeg.xRot;
      this.leftHindBabyLeg.y = this.leftHindLeg.y;
      this.leftHindBabyLeg.z = this.leftHindLeg.z;
      this.leftHindBabyLeg.xRot = this.leftHindLeg.xRot;
      this.rightFrontBabyLeg.y = this.rightFrontLeg.y;
      this.rightFrontBabyLeg.z = this.rightFrontLeg.z;
      this.rightFrontBabyLeg.xRot = this.rightFrontLeg.xRot;
      this.leftFrontBabyLeg.y = this.leftFrontLeg.y;
      this.leftFrontBabyLeg.z = this.leftFrontLeg.z;
      this.leftFrontBabyLeg.xRot = this.leftFrontLeg.xRot;
      boolean â˜ƒ = â˜ƒ.isBaby();
      this.rightHindLeg.visible = !â˜ƒ;
      this.leftHindLeg.visible = !â˜ƒ;
      this.rightFrontLeg.visible = !â˜ƒ;
      this.leftFrontLeg.visible = !â˜ƒ;
      this.rightHindBabyLeg.visible = â˜ƒ;
      this.leftHindBabyLeg.visible = â˜ƒ;
      this.rightFrontBabyLeg.visible = â˜ƒ;
      this.leftFrontBabyLeg.visible = â˜ƒ;
      this.body.y = â˜ƒ ? 10.8F : 0.0F;
   }
}
