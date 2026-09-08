package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ChickenModel<T extends Entity> extends AgeableListModel<T> {
   public static final String RED_THING = "red_thing";
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightLeg;
   private final ModelPart leftLeg;
   private final ModelPart rightWing;
   private final ModelPart leftWing;
   private final ModelPart beak;
   private final ModelPart redThing;

   public ChickenModel(ModelPart var1) {
      this.head = â˜ƒ.getChild("head");
      this.beak = â˜ƒ.getChild("beak");
      this.redThing = â˜ƒ.getChild("red_thing");
      this.body = â˜ƒ.getChild("body");
      this.rightLeg = â˜ƒ.getChild("right_leg");
      this.leftLeg = â˜ƒ.getChild("left_leg");
      this.rightWing = â˜ƒ.getChild("right_wing");
      this.leftWing = â˜ƒ.getChild("left_wing");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = 16;
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
      â˜ƒx.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
      â˜ƒx.addOrReplaceChild(
         "red_thing", CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 15.0F, -4.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F),
         PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
      â˜ƒx.addOrReplaceChild("right_leg", â˜ƒxxx, PartPose.offset(-2.0F, 19.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("left_leg", â˜ƒxxx, PartPose.offset(1.0F, 19.0F, 1.0F));
      â˜ƒx.addOrReplaceChild(
         "right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(-4.0F, 13.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), PartPose.offset(4.0F, 13.0F, 0.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of(this.head, this.beak, this.redThing);
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.beak.xRot = this.head.xRot;
      this.beak.yRot = this.head.yRot;
      this.redThing.xRot = this.head.xRot;
      this.redThing.yRot = this.head.yRot;
      this.rightLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      this.leftLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.rightWing.zRot = â˜ƒ;
      this.leftWing.zRot = -â˜ƒ;
   }
}
