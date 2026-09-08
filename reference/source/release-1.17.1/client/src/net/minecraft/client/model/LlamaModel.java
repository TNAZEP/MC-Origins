package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

public class LlamaModel<T extends AbstractChestedHorse> extends EntityModel<T> {
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart rightChest;
   private final ModelPart leftChest;

   public LlamaModel(ModelPart var1) {
      this.head = â˜ƒ.getChild("head");
      this.body = â˜ƒ.getChild("body");
      this.rightChest = â˜ƒ.getChild("right_chest");
      this.leftChest = â˜ƒ.getChild("left_chest");
      this.rightHindLeg = â˜ƒ.getChild("right_hind_leg");
      this.leftHindLeg = â˜ƒ.getChild("left_hind_leg");
      this.rightFrontLeg = â˜ƒ.getChild("right_front_leg");
      this.leftFrontLeg = â˜ƒ.getChild("left_front_leg");
   }

   public static LayerDefinition createBodyLayer(CubeDeformation var0) {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, â˜ƒ)
            .texOffs(0, 14)
            .addBox("neck", -4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, â˜ƒ)
            .texOffs(17, 0)
            .addBox("ear", -4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, â˜ƒ)
            .texOffs(17, 0)
            .addBox("ear", 1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, â˜ƒ),
         PartPose.offset(0.0F, 7.0F, -6.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(29, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, â˜ƒ),
         PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, (float) (Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_chest",
         CubeListBuilder.create().texOffs(45, 28).addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, â˜ƒ),
         PartPose.offsetAndRotation(-8.5F, 3.0F, 3.0F, 0.0F, (float) (Math.PI / 2), 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_chest",
         CubeListBuilder.create().texOffs(45, 41).addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, â˜ƒ),
         PartPose.offsetAndRotation(5.5F, 3.0F, 3.0F, 0.0F, (float) (Math.PI / 2), 0.0F)
      );
      int â˜ƒxx = 4;
      int â˜ƒxxx = 14;
      CubeListBuilder â˜ƒxxxx = CubeListBuilder.create().texOffs(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, â˜ƒ);
      â˜ƒx.addOrReplaceChild("right_hind_leg", â˜ƒxxxx, PartPose.offset(-3.5F, 10.0F, 6.0F));
      â˜ƒx.addOrReplaceChild("left_hind_leg", â˜ƒxxxx, PartPose.offset(3.5F, 10.0F, 6.0F));
      â˜ƒx.addOrReplaceChild("right_front_leg", â˜ƒxxxx, PartPose.offset(-3.5F, 10.0F, -5.0F));
      â˜ƒx.addOrReplaceChild("left_front_leg", â˜ƒxxxx, PartPose.offset(3.5F, 10.0F, -5.0F));
      return LayerDefinition.create(â˜ƒ, 128, 64);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.rightHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      this.leftHindLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.rightFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
      this.leftFrontLeg.xRot = Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
      boolean â˜ƒ = !â˜ƒ.isBaby() && â˜ƒ.hasChest();
      this.rightChest.visible = â˜ƒ;
      this.leftChest.visible = â˜ƒ;
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      if (this.young) {
         float â˜ƒ = 2.0F;
         â˜ƒ.pushPose();
         float â˜ƒx = 0.7F;
         â˜ƒ.scale(0.71428573F, 0.64935064F, 0.7936508F);
         â˜ƒ.translate(0.0, 1.3125, 0.22F);
         this.head.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
         â˜ƒ.pushPose();
         float â˜ƒxx = 1.1F;
         â˜ƒ.scale(0.625F, 0.45454544F, 0.45454544F);
         â˜ƒ.translate(0.0, 2.0625, 0.0);
         this.body.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
         â˜ƒ.pushPose();
         â˜ƒ.scale(0.45454544F, 0.41322312F, 0.45454544F);
         â˜ƒ.translate(0.0, 2.0625, 0.0);
         ImmutableList.of(this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightChest, this.leftChest)
            .forEach(var8x -> var8x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         â˜ƒ.popPose();
      } else {
         ImmutableList.of(this.head, this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightChest, this.leftChest)
            .forEach(var8x -> var8x.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }
   }
}
