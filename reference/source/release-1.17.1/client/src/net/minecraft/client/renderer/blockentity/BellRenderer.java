package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BellBlockEntity;

public class BellRenderer implements BlockEntityRenderer<BellBlockEntity> {
   public static final Material BELL_RESOURCE_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/bell/bell_body"));
   private static final String BELL_BODY = "bell_body";
   private final ModelPart bellBody;

   public BellRenderer(BlockEntityRendererProvider.Context var1) {
      ModelPart â˜ƒ = â˜ƒ.bakeLayer(ModelLayers.BELL);
      this.bellBody = â˜ƒ.getChild("bell_body");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "bell_body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F), PartPose.offset(8.0F, 12.0F, 8.0F)
      );
      â˜ƒxx.addOrReplaceChild(
         "bell_base", CubeListBuilder.create().texOffs(0, 13).addBox(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F), PartPose.offset(-8.0F, -12.0F, -8.0F)
      );
      return LayerDefinition.create(â˜ƒ, 32, 32);
   }

   public void render(BellBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      float â˜ƒ = (float)â˜ƒ.ticks + â˜ƒ;
      float â˜ƒx = 0.0F;
      float â˜ƒxx = 0.0F;
      if (â˜ƒ.shaking) {
         float â˜ƒxxx = Mth.sin(â˜ƒ / (float) Math.PI) / (4.0F + â˜ƒ / 3.0F);
         if (â˜ƒ.clickDirection == Direction.NORTH) {
            â˜ƒx = -â˜ƒxxx;
         } else if (â˜ƒ.clickDirection == Direction.SOUTH) {
            â˜ƒx = â˜ƒxxx;
         } else if (â˜ƒ.clickDirection == Direction.EAST) {
            â˜ƒxx = -â˜ƒxxx;
         } else if (â˜ƒ.clickDirection == Direction.WEST) {
            â˜ƒxx = â˜ƒxxx;
         }
      }

      this.bellBody.xRot = â˜ƒx;
      this.bellBody.zRot = â˜ƒxx;
      VertexConsumer â˜ƒ = BELL_RESOURCE_LOCATION.buffer(â˜ƒ, RenderType::entitySolid);
      this.bellBody.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
