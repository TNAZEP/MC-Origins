package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;

public class EndCrystalRenderer extends EntityRenderer<EndCrystal> {
   private static final ResourceLocation END_CRYSTAL_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
   private static final float SIN_45 = (float)Math.sin(Math.PI / 4);
   private static final String GLASS = "glass";
   private static final String BASE = "base";
   private final ModelPart cube;
   private final ModelPart glass;
   private final ModelPart base;

   public EndCrystalRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.shadowRadius = 0.5F;
      ModelPart â˜ƒ = â˜ƒ.bakeLayer(ModelLayers.END_CRYSTAL);
      this.glass = â˜ƒ.getChild("glass");
      this.cube = â˜ƒ.getChild("cube");
      this.base = â˜ƒ.getChild("base");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public void render(EndCrystal var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      float â˜ƒ = getY(â˜ƒ, â˜ƒ);
      float â˜ƒx = ((float)â˜ƒ.time + â˜ƒ) * 3.0F;
      VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(RENDER_TYPE);
      â˜ƒ.pushPose();
      â˜ƒ.scale(2.0F, 2.0F, 2.0F);
      â˜ƒ.translate(0.0, -0.5, 0.0);
      int â˜ƒxxx = OverlayTexture.NO_OVERLAY;
      if (â˜ƒ.showsBottom()) {
         this.base.render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx);
      }

      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
      â˜ƒ.translate(0.0, (double)(1.5F + â˜ƒ / 2.0F), 0.0);
      â˜ƒ.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      this.glass.render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx);
      float â˜ƒ = 0.875F;
      â˜ƒ.scale(0.875F, 0.875F, 0.875F);
      â˜ƒ.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
      this.glass.render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx);
      â˜ƒ.scale(0.875F, 0.875F, 0.875F);
      â˜ƒ.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
      this.cube.render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx);
      â˜ƒ.popPose();
      â˜ƒ.popPose();
      BlockPos â˜ƒx = â˜ƒ.getBeamTarget();
      if (â˜ƒx != null) {
         float â˜ƒxx = (float)â˜ƒx.getX() + 0.5F;
         float â˜ƒxxx = (float)â˜ƒx.getY() + 0.5F;
         float â˜ƒxxxx = (float)â˜ƒx.getZ() + 0.5F;
         float â˜ƒxxxxx = (float)((double)â˜ƒxx - â˜ƒ.getX());
         float â˜ƒxxxxxx = (float)((double)â˜ƒxxx - â˜ƒ.getY());
         float â˜ƒxxxxxxx = (float)((double)â˜ƒxxxx - â˜ƒ.getZ());
         â˜ƒ.translate((double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx);
         EnderDragonRenderer.renderCrystalBeams(-â˜ƒxxxxx, -â˜ƒxxxxxx + â˜ƒ, -â˜ƒxxxxxxx, â˜ƒ, â˜ƒ.time, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static float getY(EndCrystal var0, float var1) {
      float â˜ƒ = (float)â˜ƒ.time + â˜ƒ;
      float â˜ƒx = Mth.sin(â˜ƒ * 0.2F) / 2.0F + 0.5F;
      â˜ƒx = (â˜ƒx * â˜ƒx + â˜ƒx) * 0.4F;
      return â˜ƒx - 1.4F;
   }

   public ResourceLocation getTextureLocation(EndCrystal var1) {
      return END_CRYSTAL_LOCATION;
   }

   public boolean shouldRender(EndCrystal var1, Frustum var2, double var3, double var5, double var7) {
      return super.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) || â˜ƒ.getBeamTarget() != null;
   }
}
