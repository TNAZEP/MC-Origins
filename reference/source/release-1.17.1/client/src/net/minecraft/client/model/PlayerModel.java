package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Random;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class PlayerModel<T extends LivingEntity> extends HumanoidModel<T> {
   private static final String EAR = "ear";
   private static final String CLOAK = "cloak";
   private static final String LEFT_SLEEVE = "left_sleeve";
   private static final String RIGHT_SLEEVE = "right_sleeve";
   private static final String LEFT_PANTS = "left_pants";
   private static final String RIGHT_PANTS = "right_pants";
   private final List<ModelPart> parts;
   public final ModelPart leftSleeve;
   public final ModelPart rightSleeve;
   public final ModelPart leftPants;
   public final ModelPart rightPants;
   public final ModelPart jacket;
   private final ModelPart cloak;
   private final ModelPart ear;
   private final boolean slim;

   public PlayerModel(ModelPart var1, boolean var2) {
      super(â˜ƒ, RenderType::entityTranslucent);
      this.slim = â˜ƒ;
      this.ear = â˜ƒ.getChild("ear");
      this.cloak = â˜ƒ.getChild("cloak");
      this.leftSleeve = â˜ƒ.getChild("left_sleeve");
      this.rightSleeve = â˜ƒ.getChild("right_sleeve");
      this.leftPants = â˜ƒ.getChild("left_pants");
      this.rightPants = â˜ƒ.getChild("right_pants");
      this.jacket = â˜ƒ.getChild("jacket");
      this.parts = (List)â˜ƒ.getAllParts().filter(var0 -> !var0.isEmpty()).collect(ImmutableList.toImmutableList());
   }

   public static MeshDefinition createMesh(CubeDeformation var0, boolean var1) {
      MeshDefinition â˜ƒ = HumanoidModel.createMesh(â˜ƒ, 0.0F);
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, â˜ƒ), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild(
         "cloak", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, â˜ƒ, 1.0F, 0.5F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      float â˜ƒxx = 0.25F;
      if (â˜ƒ) {
         â˜ƒx.addOrReplaceChild(
            "left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(5.0F, 2.5F, 0.0F)
         );
         â˜ƒx.addOrReplaceChild(
            "right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(-5.0F, 2.5F, 0.0F)
         );
         â˜ƒx.addOrReplaceChild(
            "left_sleeve",
            CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)),
            PartPose.offset(5.0F, 2.5F, 0.0F)
         );
         â˜ƒx.addOrReplaceChild(
            "right_sleeve",
            CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)),
            PartPose.offset(-5.0F, 2.5F, 0.0F)
         );
      } else {
         â˜ƒx.addOrReplaceChild(
            "left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(5.0F, 2.0F, 0.0F)
         );
         â˜ƒx.addOrReplaceChild(
            "left_sleeve",
            CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)),
            PartPose.offset(5.0F, 2.0F, 0.0F)
         );
         â˜ƒx.addOrReplaceChild(
            "right_sleeve",
            CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)),
            PartPose.offset(-5.0F, 2.0F, 0.0F)
         );
      }

      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ), PartPose.offset(1.9F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_pants",
         CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)),
         PartPose.offset(1.9F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_pants",
         CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)),
         PartPose.offset(-1.9F, 12.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, â˜ƒ.extend(0.25F)), PartPose.ZERO);
      return â˜ƒ;
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return Iterables.concat(super.bodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.jacket));
   }

   public void renderEars(PoseStack var1, VertexConsumer var2, int var3, int var4) {
      this.ear.copyFrom(this.head);
      this.ear.x = 0.0F;
      this.ear.y = 0.0F;
      this.ear.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void renderCloak(PoseStack var1, VertexConsumer var2, int var3, int var4) {
      this.cloak.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.leftPants.copyFrom(this.leftLeg);
      this.rightPants.copyFrom(this.rightLeg);
      this.leftSleeve.copyFrom(this.leftArm);
      this.rightSleeve.copyFrom(this.rightArm);
      this.jacket.copyFrom(this.body);
      if (â˜ƒ.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
         if (â˜ƒ.isCrouching()) {
            this.cloak.z = 1.4F;
            this.cloak.y = 1.85F;
         } else {
            this.cloak.z = 0.0F;
            this.cloak.y = 0.0F;
         }
      } else if (â˜ƒ.isCrouching()) {
         this.cloak.z = 0.3F;
         this.cloak.y = 0.8F;
      } else {
         this.cloak.z = -1.1F;
         this.cloak.y = -0.85F;
      }
   }

   @Override
   public void setAllVisible(boolean var1) {
      super.setAllVisible(â˜ƒ);
      this.leftSleeve.visible = â˜ƒ;
      this.rightSleeve.visible = â˜ƒ;
      this.leftPants.visible = â˜ƒ;
      this.rightPants.visible = â˜ƒ;
      this.jacket.visible = â˜ƒ;
      this.cloak.visible = â˜ƒ;
      this.ear.visible = â˜ƒ;
   }

   @Override
   public void translateToHand(HumanoidArm var1, PoseStack var2) {
      ModelPart â˜ƒ = this.getArm(â˜ƒ);
      if (this.slim) {
         float â˜ƒx = 0.5F * (float)(â˜ƒ == HumanoidArm.RIGHT ? 1 : -1);
         â˜ƒ.x += â˜ƒx;
         â˜ƒ.translateAndRotate(â˜ƒ);
         â˜ƒ.x -= â˜ƒx;
      } else {
         â˜ƒ.translateAndRotate(â˜ƒ);
      }
   }

   public ModelPart getRandomModelPart(Random var1) {
      return (ModelPart)this.parts.get(â˜ƒ.nextInt(this.parts.size()));
   }
}
