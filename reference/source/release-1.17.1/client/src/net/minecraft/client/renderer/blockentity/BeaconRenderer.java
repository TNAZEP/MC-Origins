package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;

public class BeaconRenderer implements BlockEntityRenderer<BeaconBlockEntity> {
   public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");
   public static final int MAX_RENDER_Y = 1024;

   public BeaconRenderer(BlockEntityRendererProvider.Context var1) {
   }

   public void render(BeaconBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      long â˜ƒ = â˜ƒ.getLevel().getGameTime();
      List<BeaconBlockEntity.BeaconBeamSection> â˜ƒx = â˜ƒ.getBeamSections();
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.size(); ++â˜ƒxxx) {
         BeaconBlockEntity.BeaconBeamSection â˜ƒxxxx = (BeaconBlockEntity.BeaconBeamSection)â˜ƒx.get(â˜ƒxxx);
         renderBeaconBeam(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx == â˜ƒx.size() - 1 ? 1024 : â˜ƒxxxx.getHeight(), â˜ƒxxxx.getColor());
         â˜ƒxx += â˜ƒxxxx.getHeight();
      }
   }

   private static void renderBeaconBeam(PoseStack var0, MultiBufferSource var1, float var2, long var3, int var5, int var6, float[] var7) {
      renderBeaconBeam(â˜ƒ, â˜ƒ, BEAM_LOCATION, â˜ƒ, 1.0F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.2F, 0.25F);
   }

   public static void renderBeaconBeam(
      PoseStack var0,
      MultiBufferSource var1,
      ResourceLocation var2,
      float var3,
      float var4,
      long var5,
      int var7,
      int var8,
      float[] var9,
      float var10,
      float var11
   ) {
      int â˜ƒ = â˜ƒ + â˜ƒ;
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.5, 0.0, 0.5);
      float â˜ƒx = (float)Math.floorMod(â˜ƒ, 40) + â˜ƒ;
      float â˜ƒxx = â˜ƒ < 0 ? â˜ƒx : -â˜ƒx;
      float â˜ƒxxx = Mth.frac(â˜ƒxx * 0.2F - (float)Mth.floor(â˜ƒxx * 0.1F));
      float â˜ƒxxxx = â˜ƒ[0];
      float â˜ƒxxxxx = â˜ƒ[1];
      float â˜ƒxxxxxx = â˜ƒ[2];
      â˜ƒ.pushPose();
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx * 2.25F - 45.0F));
      float â˜ƒxxxxxxx = 0.0F;
      float â˜ƒxxxxxxxx = 0.0F;
      float â˜ƒxxxxxxxxx = -â˜ƒ;
      float â˜ƒxxxxxxxxxx = 0.0F;
      float â˜ƒxxxxxxxxxxx = 0.0F;
      float â˜ƒxxxxxxxxxxxx = -â˜ƒ;
      float â˜ƒxxxxxxxxxxxxx = 0.0F;
      float â˜ƒxxxxxxxxxxxxxx = 1.0F;
      float â˜ƒxxxxxxxxxxxxxxx = -1.0F + â˜ƒxxx;
      float â˜ƒxxxxxxxxxxxxxxxx = (float)â˜ƒ * â˜ƒ * (0.5F / â˜ƒ) + â˜ƒxxxxxxxxxxxxxxx;
      renderPart(
         â˜ƒ,
         â˜ƒ.getBuffer(RenderType.beaconBeam(â˜ƒ, false)),
         â˜ƒxxxx,
         â˜ƒxxxxx,
         â˜ƒxxxxxx,
         1.0F,
         â˜ƒ,
         â˜ƒ,
         0.0F,
         â˜ƒ,
         â˜ƒ,
         0.0F,
         â˜ƒxxxxxxxxx,
         0.0F,
         0.0F,
         â˜ƒxxxxxxxxxxxx,
         0.0F,
         1.0F,
         â˜ƒxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxx
      );
      â˜ƒ.popPose();
      â˜ƒxxxxxxx = -â˜ƒ;
      float â˜ƒxxxxxxxxxxxxxxxxx = -â˜ƒ;
      â˜ƒxxxxxxxx = -â˜ƒ;
      â˜ƒxxxxxxxxx = -â˜ƒ;
      â˜ƒxxxxxxxxxxxxx = 0.0F;
      â˜ƒxxxxxxxxxxxxxx = 1.0F;
      â˜ƒxxxxxxxxxxxxxxx = -1.0F + â˜ƒxxx;
      â˜ƒxxxxxxxxxxxxxxxx = (float)â˜ƒ * â˜ƒ + â˜ƒxxxxxxxxxxxxxxx;
      renderPart(
         â˜ƒ,
         â˜ƒ.getBuffer(RenderType.beaconBeam(â˜ƒ, true)),
         â˜ƒxxxx,
         â˜ƒxxxxx,
         â˜ƒxxxxxx,
         0.125F,
         â˜ƒ,
         â˜ƒ,
         â˜ƒxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxx,
         â˜ƒ,
         â˜ƒxxxxxxxx,
         â˜ƒxxxxxxxxx,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         0.0F,
         1.0F,
         â˜ƒxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxx
      );
      â˜ƒ.popPose();
   }

   private static void renderPart(
      PoseStack var0,
      VertexConsumer var1,
      float var2,
      float var3,
      float var4,
      float var5,
      int var6,
      int var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19
   ) {
      PoseStack.Pose â˜ƒ = â˜ƒ.last();
      Matrix4f â˜ƒx = â˜ƒ.pose();
      Matrix3f â˜ƒxx = â˜ƒ.normal();
      renderQuad(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      renderQuad(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      renderQuad(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      renderQuad(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void renderQuad(
      Matrix4f var0,
      Matrix3f var1,
      VertexConsumer var2,
      float var3,
      float var4,
      float var5,
      float var6,
      int var7,
      int var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16
   ) {
      addVertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      addVertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      addVertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      addVertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void addVertex(
      Matrix4f var0,
      Matrix3f var1,
      VertexConsumer var2,
      float var3,
      float var4,
      float var5,
      float var6,
      int var7,
      float var8,
      float var9,
      float var10,
      float var11
   ) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, (float)â˜ƒ, â˜ƒ)
         .color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(15728880)
         .normal(â˜ƒ, 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   public boolean shouldRenderOffScreen(BeaconBlockEntity var1) {
      return true;
   }

   @Override
   public int getViewDistance() {
      return 256;
   }

   public boolean shouldRender(BeaconBlockEntity var1, Vec3 var2) {
      return Vec3.atCenterOf(â˜ƒ.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(â˜ƒ.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
   }
}
