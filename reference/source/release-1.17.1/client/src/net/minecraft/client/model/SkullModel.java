package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SkullModel extends SkullModelBase {
   private final ModelPart root;
   protected final ModelPart head;

   public SkullModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
   }

   public static MeshDefinition createHeadModel() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
      return â˜ƒ;
   }

   public static LayerDefinition createHumanoidHeadLayer() {
      MeshDefinition â˜ƒ = createHeadModel();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.getChild("head")
         .addOrReplaceChild(
            "hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.ZERO
         );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public static LayerDefinition createMobHeadLayer() {
      MeshDefinition â˜ƒ = createHeadModel();
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public void setupAnim(float var1, float var2, float var3) {
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.root.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
