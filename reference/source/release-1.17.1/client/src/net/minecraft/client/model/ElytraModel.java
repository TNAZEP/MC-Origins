package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ElytraModel<T extends LivingEntity> extends AgeableListModel<T> {
   private final ModelPart rightWing;
   private final ModelPart leftWing;

   public ElytraModel(ModelPart var1) {
      this.leftWing = â˜ƒ.getChild("left_wing");
      this.rightWing = â˜ƒ.getChild("right_wing");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      CubeDeformation â˜ƒxx = new CubeDeformation(1.0F);
      â˜ƒx.addOrReplaceChild(
         "left_wing",
         CubeListBuilder.create().texOffs(22, 0).addBox(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, â˜ƒxx),
         PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, (float) (Math.PI / 12), 0.0F, (float) (-Math.PI / 12))
      );
      â˜ƒx.addOrReplaceChild(
         "right_wing",
         CubeListBuilder.create().texOffs(22, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, â˜ƒxx),
         PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, (float) (Math.PI / 12), 0.0F, (float) (Math.PI / 12))
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   protected Iterable<ModelPart> headParts() {
      return ImmutableList.<ModelPart>of();
   }

   @Override
   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.<ModelPart>of(this.leftWing, this.rightWing);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = (float) (Math.PI / 12);
      float â˜ƒx = (float) (-Math.PI / 12);
      float â˜ƒxx = 0.0F;
      float â˜ƒxxx = 0.0F;
      if (â˜ƒ.isFallFlying()) {
         float â˜ƒxxxx = 1.0F;
         Vec3 â˜ƒxxxxx = â˜ƒ.getDeltaMovement();
         if (â˜ƒxxxxx.y < 0.0) {
            Vec3 â˜ƒxxxxxx = â˜ƒxxxxx.normalize();
            â˜ƒxxxx = 1.0F - (float)Math.pow(-â˜ƒxxxxxx.y, 1.5);
         }

         â˜ƒ = â˜ƒxxxx * (float) (Math.PI / 9) + (1.0F - â˜ƒxxxx) * â˜ƒ;
         â˜ƒx = â˜ƒxxxx * (float) (-Math.PI / 2) + (1.0F - â˜ƒxxxx) * â˜ƒx;
      } else if (â˜ƒ.isCrouching()) {
         â˜ƒ = (float) (Math.PI * 2.0 / 9.0);
         â˜ƒx = (float) (-Math.PI / 4);
         â˜ƒxx = 3.0F;
         â˜ƒxxx = 0.08726646F;
      }

      this.leftWing.y = â˜ƒxx;
      if (â˜ƒ instanceof AbstractClientPlayer â˜ƒ) {
         â˜ƒ.elytraRotX = (float)((double)â˜ƒ.elytraRotX + (double)(â˜ƒ - â˜ƒ.elytraRotX) * 0.1);
         â˜ƒ.elytraRotY = (float)((double)â˜ƒ.elytraRotY + (double)(â˜ƒxxx - â˜ƒ.elytraRotY) * 0.1);
         â˜ƒ.elytraRotZ = (float)((double)â˜ƒ.elytraRotZ + (double)(â˜ƒx - â˜ƒ.elytraRotZ) * 0.1);
         this.leftWing.xRot = â˜ƒ.elytraRotX;
         this.leftWing.yRot = â˜ƒ.elytraRotY;
         this.leftWing.zRot = â˜ƒ.elytraRotZ;
      } else {
         this.leftWing.xRot = â˜ƒ;
         this.leftWing.zRot = â˜ƒx;
         this.leftWing.yRot = â˜ƒxxx;
      }

      this.rightWing.yRot = -this.leftWing.yRot;
      this.rightWing.y = this.leftWing.y;
      this.rightWing.xRot = this.leftWing.xRot;
      this.rightWing.zRot = -this.leftWing.zRot;
   }
}
