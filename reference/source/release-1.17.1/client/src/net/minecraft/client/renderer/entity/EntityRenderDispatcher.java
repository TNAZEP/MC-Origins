package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EntityRenderDispatcher implements ResourceManagerReloadListener {
   private static final RenderType SHADOW_RENDER_TYPE = RenderType.entityShadow(new ResourceLocation("textures/misc/shadow.png"));
   private Map<EntityType<?>, EntityRenderer<?>> renderers = ImmutableMap.of();
   private Map<String, EntityRenderer<? extends Player>> playerRenderers = ImmutableMap.of();
   public final TextureManager textureManager;
   private Level level;
   public Camera camera;
   private Quaternion cameraOrientation;
   public Entity crosshairPickEntity;
   private final ItemRenderer itemRenderer;
   private final Font font;
   public final Options options;
   private final EntityModelSet entityModels;
   private boolean shouldRenderShadow = true;
   private boolean renderHitBoxes;

   public <E extends Entity> int getPackedLightCoords(E var1, float var2) {
      return this.getRenderer(â˜ƒ).getPackedLightCoords(â˜ƒ, â˜ƒ);
   }

   public EntityRenderDispatcher(TextureManager var1, ItemRenderer var2, Font var3, Options var4, EntityModelSet var5) {
      this.textureManager = â˜ƒ;
      this.itemRenderer = â˜ƒ;
      this.font = â˜ƒ;
      this.options = â˜ƒ;
      this.entityModels = â˜ƒ;
   }

   public <T extends Entity> EntityRenderer<? super T> getRenderer(T var1) {
      if (â˜ƒ instanceof AbstractClientPlayer) {
         String â˜ƒ = ((AbstractClientPlayer)â˜ƒ).getModelName();
         EntityRenderer<? extends Player> â˜ƒx = (EntityRenderer)this.playerRenderers.get(â˜ƒ);
         return â˜ƒx != null ? â˜ƒx : (EntityRenderer)this.playerRenderers.get("default");
      } else {
         return (EntityRenderer<? super T>)this.renderers.get(â˜ƒ.getType());
      }
   }

   public void prepare(Level var1, Camera var2, Entity var3) {
      this.level = â˜ƒ;
      this.camera = â˜ƒ;
      this.cameraOrientation = â˜ƒ.rotation();
      this.crosshairPickEntity = â˜ƒ;
   }

   public void overrideCameraOrientation(Quaternion var1) {
      this.cameraOrientation = â˜ƒ;
   }

   public void setRenderShadow(boolean var1) {
      this.shouldRenderShadow = â˜ƒ;
   }

   public void setRenderHitBoxes(boolean var1) {
      this.renderHitBoxes = â˜ƒ;
   }

   public boolean shouldRenderHitBoxes() {
      return this.renderHitBoxes;
   }

   public <E extends Entity> boolean shouldRender(E var1, Frustum var2, double var3, double var5, double var7) {
      EntityRenderer<? super E> â˜ƒ = this.getRenderer(â˜ƒ);
      return â˜ƒ.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public <E extends Entity> void render(
      E var1, double var2, double var4, double var6, float var8, float var9, PoseStack var10, MultiBufferSource var11, int var12
   ) {
      EntityRenderer<? super E> â˜ƒ = this.getRenderer(â˜ƒ);

      try {
         Vec3 â˜ƒx = â˜ƒ.getRenderOffset(â˜ƒ, â˜ƒ);
         double â˜ƒxx = â˜ƒ + â˜ƒx.x();
         double â˜ƒxxx = â˜ƒ + â˜ƒx.y();
         double â˜ƒxxxx = â˜ƒ + â˜ƒx.z();
         â˜ƒ.pushPose();
         â˜ƒ.translate(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.displayFireAnimation()) {
            this.renderFlame(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.translate(-â˜ƒx.x(), -â˜ƒx.y(), -â˜ƒx.z());
         if (this.options.entityShadows && this.shouldRenderShadow && â˜ƒ.shadowRadius > 0.0F && !â˜ƒ.isInvisible()) {
            double â˜ƒx = this.distanceToSqr(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
            float â˜ƒxx = (float)((1.0 - â˜ƒx / 256.0) * (double)â˜ƒ.shadowStrength);
            if (â˜ƒxx > 0.0F) {
               renderShadow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ, this.level, â˜ƒ.shadowRadius);
            }
         }

         if (this.renderHitBoxes && !â˜ƒ.isInvisible() && !Minecraft.getInstance().showOnlyReducedInfo()) {
            renderHitbox(â˜ƒ, â˜ƒ.getBuffer(RenderType.lines()), â˜ƒ, â˜ƒ);
         }

         â˜ƒ.popPose();
      } catch (Throwable var24) {
         CrashReport â˜ƒx = CrashReport.forThrowable(var24, "Rendering entity in world");
         CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Entity being rendered");
         â˜ƒ.fillCrashReportCategory(â˜ƒxx);
         CrashReportCategory â˜ƒxxx = â˜ƒx.addCategory("Renderer details");
         â˜ƒxxx.setDetail("Assigned renderer", â˜ƒ);
         â˜ƒxxx.setDetail("Location", CrashReportCategory.formatLocation(this.level, â˜ƒ, â˜ƒ, â˜ƒ));
         â˜ƒxxx.setDetail("Rotation", â˜ƒ);
         â˜ƒxxx.setDetail("Delta", â˜ƒ);
         throw new ReportedException(â˜ƒx);
      }
   }

   private static void renderHitbox(PoseStack var0, VertexConsumer var1, Entity var2, float var3) {
      AABB â˜ƒ = â˜ƒ.getBoundingBox().move(-â˜ƒ.getX(), -â˜ƒ.getY(), -â˜ƒ.getZ());
      LevelRenderer.renderLineBox(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
      if (â˜ƒ instanceof EnderDragon) {
         double â˜ƒx = -Mth.lerp((double)â˜ƒ, â˜ƒ.xOld, â˜ƒ.getX());
         double â˜ƒxx = -Mth.lerp((double)â˜ƒ, â˜ƒ.yOld, â˜ƒ.getY());
         double â˜ƒxxx = -Mth.lerp((double)â˜ƒ, â˜ƒ.zOld, â˜ƒ.getZ());

         for(EnderDragonPart â˜ƒxxxx : ((EnderDragon)â˜ƒ).getSubEntities()) {
            â˜ƒ.pushPose();
            double â˜ƒxxxxx = â˜ƒx + Mth.lerp((double)â˜ƒ, â˜ƒxxxx.xOld, â˜ƒxxxx.getX());
            double â˜ƒxxxxxx = â˜ƒxx + Mth.lerp((double)â˜ƒ, â˜ƒxxxx.yOld, â˜ƒxxxx.getY());
            double â˜ƒxxxxxxx = â˜ƒxxx + Mth.lerp((double)â˜ƒ, â˜ƒxxxx.zOld, â˜ƒxxxx.getZ());
            â˜ƒ.translate(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
            LevelRenderer.renderLineBox(â˜ƒ, â˜ƒ, â˜ƒxxxx.getBoundingBox().move(-â˜ƒxxxx.getX(), -â˜ƒxxxx.getY(), -â˜ƒxxxx.getZ()), 0.25F, 1.0F, 0.0F, 1.0F);
            â˜ƒ.popPose();
         }
      }

      if (â˜ƒ instanceof LivingEntity) {
         float â˜ƒ = 0.01F;
         LevelRenderer.renderLineBox(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ.minX,
            (double)(â˜ƒ.getEyeHeight() - 0.01F),
            â˜ƒ.minZ,
            â˜ƒ.maxX,
            (double)(â˜ƒ.getEyeHeight() + 0.01F),
            â˜ƒ.maxZ,
            1.0F,
            0.0F,
            0.0F,
            1.0F
         );
      }

      Vec3 â˜ƒ = â˜ƒ.getViewVector(â˜ƒ);
      Matrix4f â˜ƒx = â˜ƒ.last().pose();
      Matrix3f â˜ƒxx = â˜ƒ.last().normal();
      â˜ƒ.vertex(â˜ƒx, 0.0F, â˜ƒ.getEyeHeight(), 0.0F).color(0, 0, 255, 255).normal(â˜ƒxx, (float)â˜ƒ.x, (float)â˜ƒ.y, (float)â˜ƒ.z).endVertex();
      â˜ƒ.vertex(â˜ƒx, (float)(â˜ƒ.x * 2.0), (float)((double)â˜ƒ.getEyeHeight() + â˜ƒ.y * 2.0), (float)(â˜ƒ.z * 2.0))
         .color(0, 0, 255, 255)
         .normal(â˜ƒxx, (float)â˜ƒ.x, (float)â˜ƒ.y, (float)â˜ƒ.z)
         .endVertex();
   }

   private void renderFlame(PoseStack var1, MultiBufferSource var2, Entity var3) {
      TextureAtlasSprite â˜ƒ = ModelBakery.FIRE_0.sprite();
      TextureAtlasSprite â˜ƒx = ModelBakery.FIRE_1.sprite();
      â˜ƒ.pushPose();
      float â˜ƒxx = â˜ƒ.getBbWidth() * 1.4F;
      â˜ƒ.scale(â˜ƒxx, â˜ƒxx, â˜ƒxx);
      float â˜ƒxxx = 0.5F;
      float â˜ƒxxxx = 0.0F;
      float â˜ƒxxxxx = â˜ƒ.getBbHeight() / â˜ƒxx;
      float â˜ƒxxxxxx = 0.0F;
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-this.camera.getYRot()));
      â˜ƒ.translate(0.0, 0.0, (double)(-0.3F + (float)((int)â˜ƒxxxxx) * 0.02F));
      float â˜ƒxxxxxxx = 0.0F;
      int â˜ƒxxxxxxxx = 0;
      VertexConsumer â˜ƒxxxxxxxxx = â˜ƒ.getBuffer(Sheets.cutoutBlockSheet());

      for(PoseStack.Pose â˜ƒxxxxxxxxxx = â˜ƒ.last(); â˜ƒxxxxx > 0.0F; ++â˜ƒxxxxxxxx) {
         TextureAtlasSprite â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx % 2 == 0 ? â˜ƒ : â˜ƒx;
         float â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getU0();
         float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getV0();
         float â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getU1();
         float â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.getV1();
         if (â˜ƒxxxxxxxx / 2 % 2 == 0) {
            float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
         }

         fireVertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxx - 0.0F, 0.0F - â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
         fireVertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx, -â˜ƒxxx - 0.0F, 0.0F - â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
         fireVertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx, -â˜ƒxxx - 0.0F, 1.4F - â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
         fireVertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxx - 0.0F, 1.4F - â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
         â˜ƒxxxxx -= 0.45F;
         â˜ƒxxxxxx -= 0.45F;
         â˜ƒxxx *= 0.9F;
         â˜ƒxxxxxxx += 0.03F;
      }

      â˜ƒ.popPose();
   }

   private static void fireVertex(PoseStack.Pose var0, VertexConsumer var1, float var2, float var3, float var4, float var5, float var6) {
      â˜ƒ.vertex(â˜ƒ.pose(), â˜ƒ, â˜ƒ, â˜ƒ)
         .color(255, 255, 255, 255)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(0, 10)
         .uv2(240)
         .normal(â˜ƒ.normal(), 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   private static void renderShadow(PoseStack var0, MultiBufferSource var1, Entity var2, float var3, float var4, LevelReader var5, float var6) {
      float â˜ƒx = â˜ƒ;
      if (â˜ƒ instanceof Mob â˜ƒ && â˜ƒ.isBaby()) {
         â˜ƒx = â˜ƒ * 0.5F;
      }

      double â˜ƒ = Mth.lerp((double)â˜ƒ, â˜ƒ.xOld, â˜ƒ.getX());
      double â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.yOld, â˜ƒ.getY());
      double â˜ƒxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zOld, â˜ƒ.getZ());
      int â˜ƒxxx = Mth.floor(â˜ƒ - (double)â˜ƒx);
      int â˜ƒxxxx = Mth.floor(â˜ƒ + (double)â˜ƒx);
      int â˜ƒxxxxx = Mth.floor(â˜ƒx - (double)â˜ƒx);
      int â˜ƒxxxxxx = Mth.floor(â˜ƒx);
      int â˜ƒxxxxxxx = Mth.floor(â˜ƒxx - (double)â˜ƒx);
      int â˜ƒxxxxxxxx = Mth.floor(â˜ƒxx + (double)â˜ƒx);
      PoseStack.Pose â˜ƒxxxxxxxxx = â˜ƒ.last();
      VertexConsumer â˜ƒxxxxxxxxxx = â˜ƒ.getBuffer(SHADOW_RENDER_TYPE);

      for(BlockPos â˜ƒxxxxxxxxxxx : BlockPos.betweenClosed(new BlockPos(â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxxxxx), new BlockPos(â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxx))) {
         renderBlockShadow(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxx, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒx, â˜ƒ);
      }
   }

   private static void renderBlockShadow(
      PoseStack.Pose var0, VertexConsumer var1, LevelReader var2, BlockPos var3, double var4, double var6, double var8, float var10, float var11
   ) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.getRenderShape() != RenderShape.INVISIBLE && â˜ƒ.getMaxLocalRawBrightness(â˜ƒ) > 3) {
         if (â˜ƒx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)) {
            VoxelShape â˜ƒxx = â˜ƒx.getShape(â˜ƒ, â˜ƒ.below());
            if (!â˜ƒxx.isEmpty()) {
               float â˜ƒxxx = (float)(((double)â˜ƒ - (â˜ƒ - (double)â˜ƒ.getY()) / 2.0) * 0.5 * (double)â˜ƒ.getBrightness(â˜ƒ));
               if (â˜ƒxxx >= 0.0F) {
                  if (â˜ƒxxx > 1.0F) {
                     â˜ƒxxx = 1.0F;
                  }

                  AABB â˜ƒxxxx = â˜ƒxx.bounds();
                  double â˜ƒxxxxx = (double)â˜ƒ.getX() + â˜ƒxxxx.minX;
                  double â˜ƒxxxxxx = (double)â˜ƒ.getX() + â˜ƒxxxx.maxX;
                  double â˜ƒxxxxxxx = (double)â˜ƒ.getY() + â˜ƒxxxx.minY;
                  double â˜ƒxxxxxxxx = (double)â˜ƒ.getZ() + â˜ƒxxxx.minZ;
                  double â˜ƒxxxxxxxxx = (double)â˜ƒ.getZ() + â˜ƒxxxx.maxZ;
                  float â˜ƒxxxxxxxxxx = (float)(â˜ƒxxxxx - â˜ƒ);
                  float â˜ƒxxxxxxxxxxx = (float)(â˜ƒxxxxxx - â˜ƒ);
                  float â˜ƒxxxxxxxxxxxx = (float)(â˜ƒxxxxxxx - â˜ƒ);
                  float â˜ƒxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxx - â˜ƒ);
                  float â˜ƒxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxx - â˜ƒ);
                  float â˜ƒxxxxxxxxxxxxxxx = -â˜ƒxxxxxxxxxx / 2.0F / â˜ƒ + 0.5F;
                  float â˜ƒxxxxxxxxxxxxxxxx = -â˜ƒxxxxxxxxxxx / 2.0F / â˜ƒ + 0.5F;
                  float â˜ƒxxxxxxxxxxxxxxxxx = -â˜ƒxxxxxxxxxxxxx / 2.0F / â˜ƒ + 0.5F;
                  float â˜ƒxxxxxxxxxxxxxxxxxx = -â˜ƒxxxxxxxxxxxxxx / 2.0F / â˜ƒ + 0.5F;
                  shadowVertex(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx);
                  shadowVertex(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx);
                  shadowVertex(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx);
                  shadowVertex(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }
   }

   private static void shadowVertex(PoseStack.Pose var0, VertexConsumer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      â˜ƒ.vertex(â˜ƒ.pose(), â˜ƒ, â˜ƒ, â˜ƒ)
         .color(1.0F, 1.0F, 1.0F, â˜ƒ)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(15728880)
         .normal(â˜ƒ.normal(), 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   public void setLevel(@Nullable Level var1) {
      this.level = â˜ƒ;
      if (â˜ƒ == null) {
         this.camera = null;
      }
   }

   public double distanceToSqr(Entity var1) {
      return this.camera.getPosition().distanceToSqr(â˜ƒ.position());
   }

   public double distanceToSqr(double var1, double var3, double var5) {
      return this.camera.getPosition().distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Quaternion cameraOrientation() {
      return this.cameraOrientation;
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      EntityRendererProvider.Context â˜ƒ = new EntityRendererProvider.Context(this, this.itemRenderer, â˜ƒ, this.entityModels, this.font);
      this.renderers = EntityRenderers.createEntityRenderers(â˜ƒ);
      this.playerRenderers = EntityRenderers.createPlayerRenderers(â˜ƒ);
   }
}
