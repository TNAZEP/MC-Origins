package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.WitherSkull;

public class WitherSkullRenderer extends EntityRenderer<WitherSkull> {
   private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");
   private final SkullModel model;

   public WitherSkullRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.model = new SkullModel(â˜ƒ.bakeLayer(ModelLayers.WITHER_SKULL));
   }

   public static LayerDefinition createSkullLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   protected int getBlockLightLevel(WitherSkull var1, BlockPos var2) {
      return 15;
   }

   public void render(WitherSkull var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      float â˜ƒ = Mth.rotlerp(â˜ƒ.yRotO, â˜ƒ.getYRot(), â˜ƒ);
      float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
      VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(this.model.renderType(this.getTextureLocation(â˜ƒ)));
      this.model.setupAnim(0.0F, â˜ƒ, â˜ƒx);
      this.model.renderToBuffer(â˜ƒ, â˜ƒxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(WitherSkull var1) {
      return â˜ƒ.isDangerous() ? WITHER_INVULNERABLE_LOCATION : WITHER_LOCATION;
   }
}
