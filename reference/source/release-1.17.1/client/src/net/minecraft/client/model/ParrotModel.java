package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;

public class ParrotModel extends HierarchicalModel<Parrot> {
   private static final String FEATHER = "feather";
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart tail;
   private final ModelPart leftWing;
   private final ModelPart rightWing;
   private final ModelPart head;
   private final ModelPart feather;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public ParrotModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.body = â˜ƒ.getChild("body");
      this.tail = â˜ƒ.getChild("tail");
      this.leftWing = â˜ƒ.getChild("left_wing");
      this.rightWing = â˜ƒ.getChild("right_wing");
      this.head = â˜ƒ.getChild("head");
      this.feather = this.head.getChild("feather");
      this.leftLeg = â˜ƒ.getChild("left_leg");
      this.rightLeg = â˜ƒ.getChild("right_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("body", CubeListBuilder.create().texOffs(2, 8).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 16.5F, -3.0F));
      â˜ƒx.addOrReplaceChild(
         "tail", CubeListBuilder.create().texOffs(22, 1).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 21.07F, 1.16F)
      );
      â˜ƒx.addOrReplaceChild(
         "left_wing", CubeListBuilder.create().texOffs(19, 8).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F), PartPose.offset(1.5F, 16.94F, -2.76F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_wing", CubeListBuilder.create().texOffs(19, 8).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F), PartPose.offset(-1.5F, 16.94F, -2.76F)
      );
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(2, 2).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F), PartPose.offset(0.0F, 15.69F, -2.76F)
      );
      â˜ƒxx.addOrReplaceChild(
         "head2", CubeListBuilder.create().texOffs(10, 0).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 4.0F), PartPose.offset(0.0F, -2.0F, -1.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "beak1", CubeListBuilder.create().texOffs(11, 7).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(0.0F, -0.5F, -1.5F)
      );
      â˜ƒxx.addOrReplaceChild(
         "beak2", CubeListBuilder.create().texOffs(16, 7).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(0.0F, -1.75F, -2.45F)
      );
      â˜ƒxx.addOrReplaceChild(
         "feather", CubeListBuilder.create().texOffs(2, 18).addBox(0.0F, -4.0F, -2.0F, 0.0F, 5.0F, 4.0F), PartPose.offset(0.0F, -2.15F, 0.15F)
      );
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(14, 18).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      â˜ƒx.addOrReplaceChild("left_leg", â˜ƒxxx, PartPose.offset(1.0F, 22.0F, -1.05F));
      â˜ƒx.addOrReplaceChild("right_leg", â˜ƒxxx, PartPose.offset(-1.0F, 22.0F, -1.05F));
      return LayerDefinition.create(â˜ƒ, 32, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(Parrot var1, float var2, float var3, float var4, float var5, float var6) {
      this.setupAnim(getState(â˜ƒ), â˜ƒ.tickCount, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void prepareMobModel(Parrot var1, float var2, float var3, float var4) {
      this.prepare(getState(â˜ƒ));
   }

   public void renderOnShoulder(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9) {
      this.prepare(ParrotModel.State.ON_SHOULDER);
      this.setupAnim(ParrotModel.State.ON_SHOULDER, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, â˜ƒ, â˜ƒ);
      this.root.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void setupAnim(ParrotModel.State var1, int var2, float var3, float var4, float var5, float var6, float var7) {
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.zRot = 0.0F;
      this.head.x = 0.0F;
      this.body.x = 0.0F;
      this.tail.x = 0.0F;
      this.rightWing.x = -1.5F;
      this.leftWing.x = 1.5F;
      switch(â˜ƒ) {
         case SITTING:
            break;
         case PARTY: {
            float â˜ƒ = Mth.cos((float)â˜ƒ);
            float â˜ƒx = Mth.sin((float)â˜ƒ);
            this.head.x = â˜ƒ;
            this.head.y = 15.69F + â˜ƒx;
            this.head.xRot = 0.0F;
            this.head.yRot = 0.0F;
            this.head.zRot = Mth.sin((float)â˜ƒ) * 0.4F;
            this.body.x = â˜ƒ;
            this.body.y = 16.5F + â˜ƒx;
            this.leftWing.zRot = -0.0873F - â˜ƒ;
            this.leftWing.x = 1.5F + â˜ƒ;
            this.leftWing.y = 16.94F + â˜ƒx;
            this.rightWing.zRot = 0.0873F + â˜ƒ;
            this.rightWing.x = -1.5F + â˜ƒ;
            this.rightWing.y = 16.94F + â˜ƒx;
            this.tail.x = â˜ƒ;
            this.tail.y = 21.07F + â˜ƒx;
            break;
         }
         case STANDING:
            this.leftLeg.xRot += Mth.cos(â˜ƒ * 0.6662F) * 1.4F * â˜ƒ;
            this.rightLeg.xRot += Mth.cos(â˜ƒ * 0.6662F + (float) Math.PI) * 1.4F * â˜ƒ;
         case FLYING:
         case ON_SHOULDER:
         default: {
            float â˜ƒ = â˜ƒ * 0.3F;
            this.head.y = 15.69F + â˜ƒ;
            this.tail.xRot = 1.015F + Mth.cos(â˜ƒ * 0.6662F) * 0.3F * â˜ƒ;
            this.tail.y = 21.07F + â˜ƒ;
            this.body.y = 16.5F + â˜ƒ;
            this.leftWing.zRot = -0.0873F - â˜ƒ;
            this.leftWing.y = 16.94F + â˜ƒ;
            this.rightWing.zRot = 0.0873F + â˜ƒ;
            this.rightWing.y = 16.94F + â˜ƒ;
            this.leftLeg.y = 22.0F + â˜ƒ;
            this.rightLeg.y = 22.0F + â˜ƒ;
         }
      }
   }

   private void prepare(ParrotModel.State var1) {
      this.feather.xRot = -0.2214F;
      this.body.xRot = 0.4937F;
      this.leftWing.xRot = -0.6981F;
      this.leftWing.yRot = (float) -Math.PI;
      this.rightWing.xRot = -0.6981F;
      this.rightWing.yRot = (float) -Math.PI;
      this.leftLeg.xRot = -0.0299F;
      this.rightLeg.xRot = -0.0299F;
      this.leftLeg.y = 22.0F;
      this.rightLeg.y = 22.0F;
      this.leftLeg.zRot = 0.0F;
      this.rightLeg.zRot = 0.0F;
      switch(â˜ƒ) {
         case SITTING:
            float â˜ƒ = 1.9F;
            this.head.y = 17.59F;
            this.tail.xRot = 1.5388988F;
            this.tail.y = 22.97F;
            this.body.y = 18.4F;
            this.leftWing.zRot = -0.0873F;
            this.leftWing.y = 18.84F;
            this.rightWing.zRot = 0.0873F;
            this.rightWing.y = 18.84F;
            ++this.leftLeg.y;
            ++this.rightLeg.y;
            ++this.leftLeg.xRot;
            ++this.rightLeg.xRot;
            break;
         case PARTY:
            this.leftLeg.zRot = (float) (-Math.PI / 9);
            this.rightLeg.zRot = (float) (Math.PI / 9);
         case STANDING:
         case ON_SHOULDER:
         default:
            break;
         case FLYING:
            this.leftLeg.xRot += (float) (Math.PI * 2.0 / 9.0);
            this.rightLeg.xRot += (float) (Math.PI * 2.0 / 9.0);
      }
   }

   private static ParrotModel.State getState(Parrot var0) {
      if (â˜ƒ.isPartyParrot()) {
         return ParrotModel.State.PARTY;
      } else if (â˜ƒ.isInSittingPose()) {
         return ParrotModel.State.SITTING;
      } else {
         return â˜ƒ.isFlying() ? ParrotModel.State.FLYING : ParrotModel.State.STANDING;
      }
   }

   public static enum State {
      FLYING,
      STANDING,
      SITTING,
      PARTY,
      ON_SHOULDER;
   }
}
