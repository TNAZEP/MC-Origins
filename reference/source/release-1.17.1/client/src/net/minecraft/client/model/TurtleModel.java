package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Turtle;

public class TurtleModel<T extends Turtle> extends QuadrupedModel<T> {
   private static final String EGG_BELLY = "egg_belly";
   private final ModelPart eggBelly;

   public TurtleModel(ModelPart var1) {
      super(â˜ƒ, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
      this.eggBelly = â˜ƒ.getChild("egg_belly");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(3, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F), PartPose.offset(0.0F, 19.0F, -10.0F));
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(7, 37)
            .addBox("shell", -9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F)
            .texOffs(31, 1)
            .addBox("belly", -5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F),
         PartPose.offsetAndRotation(0.0F, 11.0F, -10.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "egg_belly",
         CubeListBuilder.create().texOffs(70, 33).addBox(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F),
         PartPose.offsetAndRotation(0.0F, 11.0F, -10.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      int â˜ƒxx = 1;
      â˜ƒx.addOrReplaceChild(
         "right_hind_leg", CubeListBuilder.create().texOffs(1, 23).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F), PartPose.offset(-3.5F, 22.0F, 11.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_hind_leg", CubeListBuilder.create().texOffs(1, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F), PartPose.offset(3.5F, 22.0F, 11.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_front_leg", CubeListBuilder.create().texOffs(27, 30).addBox(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F), PartPose.offset(-5.0F, 21.0F, -4.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_front_leg", CubeListBuilder.create().texOffs(27, 24).addBox(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F), PartPose.offset(5.0F, 21.0F, -4.0F)
      );
      return LayerDefinition.create(â˜ƒ, 128, 64);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return Iterables.concat(super.bodyParts(), ImmutableList.of(this.eggBelly));
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.rightHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F * 0.6F) * 0.5F * â˜ƒ;
      this.leftHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F * 0.6F + (float) Math.PI) * 0.5F * â˜ƒ;
      this.rightFrontLeg.zRot = Mth.cos(â˜ƒ * 0.6662F * 0.6F + (float) Math.PI) * 0.5F * â˜ƒ;
      this.leftFrontLeg.zRot = Mth.cos(â˜ƒ * 0.6662F * 0.6F) * 0.5F * â˜ƒ;
      this.rightFrontLeg.xRot = 0.0F;
      this.leftFrontLeg.xRot = 0.0F;
      this.rightFrontLeg.yRot = 0.0F;
      this.leftFrontLeg.yRot = 0.0F;
      this.rightHindLeg.yRot = 0.0F;
      this.leftHindLeg.yRot = 0.0F;
      if (!â˜ƒ.isInWater() && â˜ƒ.isOnGround()) {
         float â˜ƒ = â˜ƒ.isLayingEgg() ? 4.0F : 1.0F;
         float â˜ƒx = â˜ƒ.isLayingEgg() ? 2.0F : 1.0F;
         float â˜ƒxx = 5.0F;
         this.rightFrontLeg.yRot = Mth.cos(â˜ƒ * â˜ƒ * 5.0F + (float) Math.PI) * 8.0F * â˜ƒ * â˜ƒx;
         this.rightFrontLeg.zRot = 0.0F;
         this.leftFrontLeg.yRot = Mth.cos(â˜ƒ * â˜ƒ * 5.0F) * 8.0F * â˜ƒ * â˜ƒx;
         this.leftFrontLeg.zRot = 0.0F;
         this.rightHindLeg.yRot = Mth.cos(â˜ƒ * 5.0F + (float) Math.PI) * 3.0F * â˜ƒ;
         this.rightHindLeg.xRot = 0.0F;
         this.leftHindLeg.yRot = Mth.cos(â˜ƒ * 5.0F) * 3.0F * â˜ƒ;
         this.leftHindLeg.xRot = 0.0F;
      }

      this.eggBelly.visible = !this.young && â˜ƒ.hasEgg();
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      boolean â˜ƒ = this.eggBelly.visible;
      if (â˜ƒ) {
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, -0.08F, 0.0);
      }

      super.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.popPose();
      }
   }
}
