package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class DebugRenderer {
   public final PathfindingRenderer pathfindingRenderer = new PathfindingRenderer();
   public final DebugRenderer.SimpleDebugRenderer waterDebugRenderer;
   public final DebugRenderer.SimpleDebugRenderer chunkBorderRenderer;
   public final DebugRenderer.SimpleDebugRenderer heightMapRenderer;
   public final DebugRenderer.SimpleDebugRenderer collisionBoxRenderer;
   public final DebugRenderer.SimpleDebugRenderer neighborsUpdateRenderer;
   public final StructureRenderer structureRenderer;
   public final DebugRenderer.SimpleDebugRenderer lightDebugRenderer;
   public final DebugRenderer.SimpleDebugRenderer worldGenAttemptRenderer;
   public final DebugRenderer.SimpleDebugRenderer solidFaceRenderer;
   public final DebugRenderer.SimpleDebugRenderer chunkRenderer;
   public final BrainDebugRenderer brainDebugRenderer;
   public final VillageSectionsDebugRenderer villageSectionsDebugRenderer;
   public final BeeDebugRenderer beeDebugRenderer;
   public final RaidDebugRenderer raidDebugRenderer;
   public final GoalSelectorDebugRenderer goalSelectorRenderer;
   public final GameTestDebugRenderer gameTestDebugRenderer;
   public final GameEventListenerRenderer gameEventListenerRenderer;
   private boolean renderChunkborder;

   public DebugRenderer(Minecraft var1) {
      this.waterDebugRenderer = new WaterDebugRenderer(â˜ƒ);
      this.chunkBorderRenderer = new ChunkBorderRenderer(â˜ƒ);
      this.heightMapRenderer = new HeightMapRenderer(â˜ƒ);
      this.collisionBoxRenderer = new CollisionBoxRenderer(â˜ƒ);
      this.neighborsUpdateRenderer = new NeighborsUpdateRenderer(â˜ƒ);
      this.structureRenderer = new StructureRenderer(â˜ƒ);
      this.lightDebugRenderer = new LightDebugRenderer(â˜ƒ);
      this.worldGenAttemptRenderer = new WorldGenAttemptRenderer();
      this.solidFaceRenderer = new SolidFaceRenderer(â˜ƒ);
      this.chunkRenderer = new ChunkDebugRenderer(â˜ƒ);
      this.brainDebugRenderer = new BrainDebugRenderer(â˜ƒ);
      this.villageSectionsDebugRenderer = new VillageSectionsDebugRenderer();
      this.beeDebugRenderer = new BeeDebugRenderer(â˜ƒ);
      this.raidDebugRenderer = new RaidDebugRenderer(â˜ƒ);
      this.goalSelectorRenderer = new GoalSelectorDebugRenderer(â˜ƒ);
      this.gameTestDebugRenderer = new GameTestDebugRenderer();
      this.gameEventListenerRenderer = new GameEventListenerRenderer(â˜ƒ);
   }

   public void clear() {
      this.pathfindingRenderer.clear();
      this.waterDebugRenderer.clear();
      this.chunkBorderRenderer.clear();
      this.heightMapRenderer.clear();
      this.collisionBoxRenderer.clear();
      this.neighborsUpdateRenderer.clear();
      this.structureRenderer.clear();
      this.lightDebugRenderer.clear();
      this.worldGenAttemptRenderer.clear();
      this.solidFaceRenderer.clear();
      this.chunkRenderer.clear();
      this.brainDebugRenderer.clear();
      this.villageSectionsDebugRenderer.clear();
      this.beeDebugRenderer.clear();
      this.raidDebugRenderer.clear();
      this.goalSelectorRenderer.clear();
      this.gameTestDebugRenderer.clear();
      this.gameEventListenerRenderer.clear();
   }

   public boolean switchRenderChunkborder() {
      this.renderChunkborder = !this.renderChunkborder;
      return this.renderChunkborder;
   }

   public void render(PoseStack var1, MultiBufferSource.BufferSource var2, double var3, double var5, double var7) {
      if (this.renderChunkborder && !Minecraft.getInstance().showOnlyReducedInfo()) {
         this.chunkBorderRenderer.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      this.gameTestDebugRenderer.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static Optional<Entity> getTargetedEntity(@Nullable Entity var0, int var1) {
      if (â˜ƒ == null) {
         return Optional.empty();
      } else {
         Vec3 â˜ƒ = â˜ƒ.getEyePosition();
         Vec3 â˜ƒx = â˜ƒ.getViewVector(1.0F).scale((double)â˜ƒ);
         Vec3 â˜ƒxx = â˜ƒ.add(â˜ƒx);
         AABB â˜ƒxxx = â˜ƒ.getBoundingBox().expandTowards(â˜ƒx).inflate(1.0);
         int â˜ƒxxxx = â˜ƒ * â˜ƒ;
         Predicate<Entity> â˜ƒxxxxx = var0x -> !var0x.isSpectator() && var0x.isPickable();
         EntityHitResult â˜ƒxxxxxx = ProjectileUtil.getEntityHitResult(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxxx, (double)â˜ƒxxxx);
         if (â˜ƒxxxxxx == null) {
            return Optional.empty();
         } else {
            return â˜ƒ.distanceToSqr(â˜ƒxxxxxx.getLocation()) > (double)â˜ƒxxxx ? Optional.empty() : Optional.of(â˜ƒxxxxxx.getEntity());
         }
      }
   }

   public static void renderFilledBox(BlockPos var0, BlockPos var1, float var2, float var3, float var4, float var5) {
      Camera â˜ƒ = Minecraft.getInstance().gameRenderer.getMainCamera();
      if (â˜ƒ.isInitialized()) {
         Vec3 â˜ƒx = â˜ƒ.getPosition().reverse();
         AABB â˜ƒxx = new AABB(â˜ƒ, â˜ƒ).move(â˜ƒx);
         renderFilledBox(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void renderFilledBox(BlockPos var0, float var1, float var2, float var3, float var4, float var5) {
      Camera â˜ƒ = Minecraft.getInstance().gameRenderer.getMainCamera();
      if (â˜ƒ.isInitialized()) {
         Vec3 â˜ƒx = â˜ƒ.getPosition().reverse();
         AABB â˜ƒxx = new AABB(â˜ƒ).move(â˜ƒx).inflate((double)â˜ƒ);
         renderFilledBox(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void renderFilledBox(AABB var0, float var1, float var2, float var3, float var4) {
      renderFilledBox(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void renderFilledBox(
      double var0, double var2, double var4, double var6, double var8, double var10, float var12, float var13, float var14, float var15
   ) {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      â˜ƒx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
      LevelRenderer.addChainedFilledBoxVertices(â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.end();
   }

   public static void renderFloatingText(String var0, int var1, int var2, int var3, int var4) {
      renderFloatingText(â˜ƒ, (double)â˜ƒ + 0.5, (double)â˜ƒ + 0.5, (double)â˜ƒ + 0.5, â˜ƒ);
   }

   public static void renderFloatingText(String var0, double var1, double var3, double var5, int var7) {
      renderFloatingText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.02F);
   }

   public static void renderFloatingText(String var0, double var1, double var3, double var5, int var7, float var8) {
      renderFloatingText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, 0.0F, false);
   }

   public static void renderFloatingText(String var0, double var1, double var3, double var5, int var7, float var8, boolean var9, float var10, boolean var11) {
      Minecraft â˜ƒ = Minecraft.getInstance();
      Camera â˜ƒx = â˜ƒ.gameRenderer.getMainCamera();
      if (â˜ƒx.isInitialized() && â˜ƒ.getEntityRenderDispatcher().options != null) {
         Font â˜ƒxx = â˜ƒ.font;
         double â˜ƒxxx = â˜ƒx.getPosition().x;
         double â˜ƒxxxx = â˜ƒx.getPosition().y;
         double â˜ƒxxxxx = â˜ƒx.getPosition().z;
         PoseStack â˜ƒxxxxxx = RenderSystem.getModelViewStack();
         â˜ƒxxxxxx.pushPose();
         â˜ƒxxxxxx.translate((double)((float)(â˜ƒ - â˜ƒxxx)), (double)((float)(â˜ƒ - â˜ƒxxxx) + 0.07F), (double)((float)(â˜ƒ - â˜ƒxxxxx)));
         â˜ƒxxxxxx.mulPoseMatrix(new Matrix4f(â˜ƒx.rotation()));
         â˜ƒxxxxxx.scale(â˜ƒ, -â˜ƒ, â˜ƒ);
         RenderSystem.enableTexture();
         if (â˜ƒ) {
            RenderSystem.disableDepthTest();
         } else {
            RenderSystem.enableDepthTest();
         }

         RenderSystem.depthMask(true);
         â˜ƒxxxxxx.scale(-1.0F, 1.0F, 1.0F);
         RenderSystem.applyModelViewMatrix();
         float â˜ƒxx = â˜ƒ ? (float)(-â˜ƒxx.width(â˜ƒ)) / 2.0F : 0.0F;
         â˜ƒxx -= â˜ƒ / â˜ƒ;
         MultiBufferSource.BufferSource â˜ƒxxx = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
         â˜ƒxx.drawInBatch(â˜ƒ, â˜ƒxx, 0.0F, â˜ƒ, false, Transformation.identity().getMatrix(), â˜ƒxxx, â˜ƒ, 0, 15728880);
         â˜ƒxxx.endBatch();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableDepthTest();
         â˜ƒxxxxxx.popPose();
         RenderSystem.applyModelViewMatrix();
      }
   }

   public interface SimpleDebugRenderer {
      void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7);

      default void clear() {
      }
   }
}
