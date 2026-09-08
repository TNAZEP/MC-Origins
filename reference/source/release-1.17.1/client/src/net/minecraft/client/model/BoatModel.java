package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class BoatModel extends ListModel<Boat> {
   private static final String LEFT_PADDLE = "left_paddle";
   private static final String RIGHT_PADDLE = "right_paddle";
   private static final String WATER_PATCH = "water_patch";
   private static final String BOTTOM = "bottom";
   private static final String BACK = "back";
   private static final String FRONT = "front";
   private static final String RIGHT = "right";
   private static final String LEFT = "left";
   private final ModelPart leftPaddle;
   private final ModelPart rightPaddle;
   private final ModelPart waterPatch;
   private final ImmutableList<ModelPart> parts;

   public BoatModel(ModelPart var1) {
      this.leftPaddle = â˜ƒ.getChild("left_paddle");
      this.rightPaddle = â˜ƒ.getChild("right_paddle");
      this.waterPatch = â˜ƒ.getChild("water_patch");
      this.parts = ImmutableList.of(
         â˜ƒ.getChild("bottom"), â˜ƒ.getChild("back"), â˜ƒ.getChild("front"), â˜ƒ.getChild("right"), â˜ƒ.getChild("left"), this.leftPaddle, this.rightPaddle
      );
   }

   public static LayerDefinition createBodyModel() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 32;
      int â˜ƒxxx = 6;
      int â˜ƒxxxx = 20;
      int â˜ƒxxxxx = 4;
      int â˜ƒxxxxxx = 28;
      â˜ƒx.addOrReplaceChild(
         "bottom",
         CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F),
         PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "back",
         CubeListBuilder.create().texOffs(0, 19).addBox(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F),
         PartPose.offsetAndRotation(-15.0F, 4.0F, 4.0F, 0.0F, (float) (Math.PI * 3.0 / 2.0), 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "front",
         CubeListBuilder.create().texOffs(0, 27).addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F),
         PartPose.offsetAndRotation(15.0F, 4.0F, 0.0F, 0.0F, (float) (Math.PI / 2), 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right",
         CubeListBuilder.create().texOffs(0, 35).addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F),
         PartPose.offsetAndRotation(0.0F, 4.0F, -9.0F, 0.0F, (float) Math.PI, 0.0F)
      );
      â˜ƒx.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 43).addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), PartPose.offset(0.0F, 4.0F, 9.0F));
      int â˜ƒxxxxxxx = 20;
      int â˜ƒxxxxxxxx = 7;
      int â˜ƒxxxxxxxxx = 6;
      float â˜ƒxxxxxxxxxx = -5.0F;
      â˜ƒx.addOrReplaceChild(
         "left_paddle",
         CubeListBuilder.create().texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F),
         PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, (float) (Math.PI / 16))
      );
      â˜ƒx.addOrReplaceChild(
         "right_paddle",
         CubeListBuilder.create().texOffs(62, 20).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F),
         PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, (float) Math.PI, (float) (Math.PI / 16))
      );
      â˜ƒx.addOrReplaceChild(
         "water_patch",
         CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F),
         PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 128, 64);
   }

   public void setupAnim(Boat var1, float var2, float var3, float var4, float var5, float var6) {
      animatePaddle(â˜ƒ, 0, this.leftPaddle, â˜ƒ);
      animatePaddle(â˜ƒ, 1, this.rightPaddle, â˜ƒ);
   }

   public ImmutableList<ModelPart> parts() {
      return this.parts;
   }

   public ModelPart waterPatch() {
      return this.waterPatch;
   }

   private static void animatePaddle(Boat var0, int var1, ModelPart var2, float var3) {
      float â˜ƒ = â˜ƒ.getRowingTime(â˜ƒ, â˜ƒ);
      â˜ƒ.xRot = Mth.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (Mth.sin(-â˜ƒ) + 1.0F) / 2.0F);
      â˜ƒ.yRot = Mth.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (Mth.sin(-â˜ƒ + 1.0F) + 1.0F) / 2.0F);
      if (â˜ƒ == 1) {
         â˜ƒ.yRot = (float) Math.PI - â˜ƒ.yRot;
      }
   }
}
