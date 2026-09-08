package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;

public class ConduitRenderer implements BlockEntityRenderer<ConduitBlockEntity> {
   public static final Material SHELL_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/base"));
   public static final Material ACTIVE_SHELL_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/cage"));
   public static final Material WIND_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/wind"));
   public static final Material VERTICAL_WIND_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/wind_vertical"));
   public static final Material OPEN_EYE_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/open_eye"));
   public static final Material CLOSED_EYE_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/closed_eye"));
   private final ModelPart eye;
   private final ModelPart wind;
   private final ModelPart shell;
   private final ModelPart cage;
   private final BlockEntityRenderDispatcher renderer;

   public ConduitRenderer(BlockEntityRendererProvider.Context var1) {
      this.renderer = â˜ƒ.getBlockEntityRenderDispatcher();
      this.eye = â˜ƒ.bakeLayer(ModelLayers.CONDUIT_EYE);
      this.wind = â˜ƒ.bakeLayer(ModelLayers.CONDUIT_WIND);
      this.shell = â˜ƒ.bakeLayer(ModelLayers.CONDUIT_SHELL);
      this.cage = â˜ƒ.bakeLayer(ModelLayers.CONDUIT_CAGE);
   }

   public static LayerDefinition createEyeLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "eye", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.ZERO
      );
      return LayerDefinition.create(â˜ƒ, 16, 16);
   }

   public static LayerDefinition createWindLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("wind", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public static LayerDefinition createShellLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 32, 16);
   }

   public static LayerDefinition createCageLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 32, 16);
   }

   public void render(ConduitBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      float â˜ƒ = (float)â˜ƒ.tickCount + â˜ƒ;
      if (!â˜ƒ.isActive()) {
         float â˜ƒx = â˜ƒ.getActiveRotation(0.0F);
         VertexConsumer â˜ƒxx = SHELL_TEXTURE.buffer(â˜ƒ, RenderType::entitySolid);
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.5, 0.5, 0.5);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
         this.shell.render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      } else {
         float â˜ƒ = â˜ƒ.getActiveRotation(â˜ƒ) * (180.0F / (float)Math.PI);
         float â˜ƒx = Mth.sin(â˜ƒ * 0.1F) / 2.0F + 0.5F;
         â˜ƒx = â˜ƒx * â˜ƒx + â˜ƒx;
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.5, (double)(0.3F + â˜ƒx * 0.2F), 0.5);
         Vector3f â˜ƒxx = new Vector3f(0.5F, 1.0F, 0.5F);
         â˜ƒxx.normalize();
         â˜ƒ.mulPose(â˜ƒxx.rotationDegrees(â˜ƒ));
         this.cage.render(â˜ƒ, ACTIVE_SHELL_TEXTURE.buffer(â˜ƒ, RenderType::entityCutoutNoCull), â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
         int â˜ƒxxx = â˜ƒ.tickCount / 66 % 3;
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.5, 0.5, 0.5);
         if (â˜ƒxxx == 1) {
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
         } else if (â˜ƒxxx == 2) {
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
         }

         VertexConsumer â˜ƒ = (â˜ƒxxx == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).buffer(â˜ƒ, RenderType::entityCutoutNoCull);
         this.wind.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.5, 0.5, 0.5);
         â˜ƒ.scale(0.875F, 0.875F, 0.875F);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(180.0F));
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
         this.wind.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
         Camera â˜ƒx = this.renderer.camera;
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.5, (double)(0.3F + â˜ƒx * 0.2F), 0.5);
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         float â˜ƒxx = -â˜ƒx.getYRot();
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxx));
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒx.getXRot()));
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
         float â˜ƒxxx = 1.3333334F;
         â˜ƒ.scale(1.3333334F, 1.3333334F, 1.3333334F);
         this.eye.render(â˜ƒ, (â˜ƒ.isHunting() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).buffer(â˜ƒ, RenderType::entityCutoutNoCull), â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }
   }
}
