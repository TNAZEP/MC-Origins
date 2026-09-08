package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ShieldModel extends Model {
   private static final String PLATE = "plate";
   private static final String HANDLE = "handle";
   private static final int SHIELD_WIDTH = 10;
   private static final int SHIELD_HEIGHT = 20;
   private final ModelPart root;
   private final ModelPart plate;
   private final ModelPart handle;

   public ShieldModel(ModelPart var1) {
      super(RenderType::entitySolid);
      this.root = â˜ƒ;
      this.plate = â˜ƒ.getChild("plate");
      this.handle = â˜ƒ.getChild("handle");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public ModelPart plate() {
      return this.plate;
   }

   public ModelPart handle() {
      return this.handle;
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.root.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
