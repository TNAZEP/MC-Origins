package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ModelBlockRenderer {
   private static final int FACE_CUBIC = 0;
   private static final int FACE_PARTIAL = 1;
   static final Direction[] DIRECTIONS = Direction.values();
   private final BlockColors blockColors;
   private static final int CACHE_SIZE = 100;
   static final ThreadLocal<ModelBlockRenderer.Cache> CACHE = ThreadLocal.withInitial(ModelBlockRenderer.Cache::new);

   public ModelBlockRenderer(BlockColors var1) {
      this.blockColors = â˜ƒ;
   }

   public boolean tesselateBlock(
      BlockAndTintGetter var1,
      BakedModel var2,
      BlockState var3,
      BlockPos var4,
      PoseStack var5,
      VertexConsumer var6,
      boolean var7,
      Random var8,
      long var9,
      int var11
   ) {
      boolean â˜ƒ = Minecraft.useAmbientOcclusion() && â˜ƒ.getLightEmission() == 0 && â˜ƒ.useAmbientOcclusion();
      Vec3 â˜ƒx = â˜ƒ.getOffset(â˜ƒ, â˜ƒ);
      â˜ƒ.translate(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z);

      try {
         return â˜ƒ
            ? this.tesselateWithAO(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
            : this.tesselateWithoutAO(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (Throwable var17) {
         CrashReport â˜ƒxx = CrashReport.forThrowable(var17, "Tesselating block model");
         CrashReportCategory â˜ƒxxx = â˜ƒxx.addCategory("Block model being tesselated");
         CrashReportCategory.populateBlockDetails(â˜ƒxxx, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒxxx.setDetail("Using AO", â˜ƒ);
         throw new ReportedException(â˜ƒxx);
      }
   }

   public boolean tesselateWithAO(
      BlockAndTintGetter var1,
      BakedModel var2,
      BlockState var3,
      BlockPos var4,
      PoseStack var5,
      VertexConsumer var6,
      boolean var7,
      Random var8,
      long var9,
      int var11
   ) {
      boolean â˜ƒ = false;
      float[] â˜ƒx = new float[DIRECTIONS.length * 2];
      BitSet â˜ƒxx = new BitSet(3);
      ModelBlockRenderer.AmbientOcclusionFace â˜ƒxxx = new ModelBlockRenderer.AmbientOcclusionFace();
      BlockPos.MutableBlockPos â˜ƒxxxx = â˜ƒ.mutable();

      for(Direction â˜ƒxxxxx : DIRECTIONS) {
         â˜ƒ.setSeed(â˜ƒ);
         List<BakedQuad> â˜ƒxxxxxx = â˜ƒ.getQuads(â˜ƒ, â˜ƒxxxxx, â˜ƒ);
         if (!â˜ƒxxxxxx.isEmpty()) {
            â˜ƒxxxx.setWithOffset(â˜ƒ, â˜ƒxxxxx);
            if (!â˜ƒ || Block.shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒxxxx)) {
               this.renderModelFaceAO(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ);
               â˜ƒ = true;
            }
         }
      }

      â˜ƒ.setSeed(â˜ƒ);
      List<BakedQuad> â˜ƒxxxxx = â˜ƒ.getQuads(â˜ƒ, null, â˜ƒ);
      if (!â˜ƒxxxxx.isEmpty()) {
         this.renderModelFaceAO(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ);
         â˜ƒ = true;
      }

      return â˜ƒ;
   }

   public boolean tesselateWithoutAO(
      BlockAndTintGetter var1,
      BakedModel var2,
      BlockState var3,
      BlockPos var4,
      PoseStack var5,
      VertexConsumer var6,
      boolean var7,
      Random var8,
      long var9,
      int var11
   ) {
      boolean â˜ƒ = false;
      BitSet â˜ƒx = new BitSet(3);
      BlockPos.MutableBlockPos â˜ƒxx = â˜ƒ.mutable();

      for(Direction â˜ƒxxx : DIRECTIONS) {
         â˜ƒ.setSeed(â˜ƒ);
         List<BakedQuad> â˜ƒxxxx = â˜ƒ.getQuads(â˜ƒ, â˜ƒxxx, â˜ƒ);
         if (!â˜ƒxxxx.isEmpty()) {
            â˜ƒxx.setWithOffset(â˜ƒ, â˜ƒxxx);
            if (!â˜ƒ || Block.shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx)) {
               int â˜ƒxxxxx = LevelRenderer.getLightColor(â˜ƒ, â˜ƒ, â˜ƒxx);
               this.renderModelFaceFlat(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒx);
               â˜ƒ = true;
            }
         }
      }

      â˜ƒ.setSeed(â˜ƒ);
      List<BakedQuad> â˜ƒxxx = â˜ƒ.getQuads(â˜ƒ, null, â˜ƒ);
      if (!â˜ƒxxx.isEmpty()) {
         this.renderModelFaceFlat(â˜ƒ, â˜ƒ, â˜ƒ, -1, â˜ƒ, true, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒx);
         â˜ƒ = true;
      }

      return â˜ƒ;
   }

   private void renderModelFaceAO(
      BlockAndTintGetter var1,
      BlockState var2,
      BlockPos var3,
      PoseStack var4,
      VertexConsumer var5,
      List<BakedQuad> var6,
      float[] var7,
      BitSet var8,
      ModelBlockRenderer.AmbientOcclusionFace var9,
      int var10
   ) {
      for(BakedQuad â˜ƒ : â˜ƒ) {
         this.calculateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getVertices(), â˜ƒ.getDirection(), â˜ƒ, â˜ƒ);
         â˜ƒ.calculate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getDirection(), â˜ƒ, â˜ƒ, â˜ƒ.isShade());
         this.putQuadData(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ.last(),
            â˜ƒ,
            â˜ƒ.brightness[0],
            â˜ƒ.brightness[1],
            â˜ƒ.brightness[2],
            â˜ƒ.brightness[3],
            â˜ƒ.lightmap[0],
            â˜ƒ.lightmap[1],
            â˜ƒ.lightmap[2],
            â˜ƒ.lightmap[3],
            â˜ƒ
         );
      }
   }

   private void putQuadData(
      BlockAndTintGetter var1,
      BlockState var2,
      BlockPos var3,
      VertexConsumer var4,
      PoseStack.Pose var5,
      BakedQuad var6,
      float var7,
      float var8,
      float var9,
      float var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15
   ) {
      float â˜ƒ;
      float â˜ƒx;
      float â˜ƒxx;
      if (â˜ƒ.isTinted()) {
         int â˜ƒxxx = this.blockColors.getColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getTintIndex());
         â˜ƒ = (float)(â˜ƒxxx >> 16 & 0xFF) / 255.0F;
         â˜ƒx = (float)(â˜ƒxxx >> 8 & 0xFF) / 255.0F;
         â˜ƒxx = (float)(â˜ƒxxx & 0xFF) / 255.0F;
      } else {
         â˜ƒ = 1.0F;
         â˜ƒx = 1.0F;
         â˜ƒxx = 1.0F;
      }

      â˜ƒ.putBulkData(â˜ƒ, â˜ƒ, new float[]{â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ}, â˜ƒ, â˜ƒx, â˜ƒxx, new int[]{â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ}, â˜ƒ, true);
   }

   private void calculateShape(BlockAndTintGetter var1, BlockState var2, BlockPos var3, int[] var4, Direction var5, @Nullable float[] var6, BitSet var7) {
      float â˜ƒ = 32.0F;
      float â˜ƒx = 32.0F;
      float â˜ƒxx = 32.0F;
      float â˜ƒxxx = -32.0F;
      float â˜ƒxxxx = -32.0F;
      float â˜ƒxxxxx = -32.0F;

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 4; ++â˜ƒxxxxxx) {
         float â˜ƒxxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxxxxx * 8]);
         float â˜ƒxxxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxxxxx * 8 + 1]);
         float â˜ƒxxxxxxxxx = Float.intBitsToFloat(â˜ƒ[â˜ƒxxxxxx * 8 + 2]);
         â˜ƒ = Math.min(â˜ƒ, â˜ƒxxxxxxx);
         â˜ƒx = Math.min(â˜ƒx, â˜ƒxxxxxxxx);
         â˜ƒxx = Math.min(â˜ƒxx, â˜ƒxxxxxxxxx);
         â˜ƒxxx = Math.max(â˜ƒxxx, â˜ƒxxxxxxx);
         â˜ƒxxxx = Math.max(â˜ƒxxxx, â˜ƒxxxxxxxx);
         â˜ƒxxxxx = Math.max(â˜ƒxxxxx, â˜ƒxxxxxxxxx);
      }

      if (â˜ƒ != null) {
         â˜ƒ[Direction.WEST.get3DDataValue()] = â˜ƒ;
         â˜ƒ[Direction.EAST.get3DDataValue()] = â˜ƒxxx;
         â˜ƒ[Direction.DOWN.get3DDataValue()] = â˜ƒx;
         â˜ƒ[Direction.UP.get3DDataValue()] = â˜ƒxxxx;
         â˜ƒ[Direction.NORTH.get3DDataValue()] = â˜ƒxx;
         â˜ƒ[Direction.SOUTH.get3DDataValue()] = â˜ƒxxxxx;
         int â˜ƒxxxxxx = DIRECTIONS.length;
         â˜ƒ[Direction.WEST.get3DDataValue() + â˜ƒxxxxxx] = 1.0F - â˜ƒ;
         â˜ƒ[Direction.EAST.get3DDataValue() + â˜ƒxxxxxx] = 1.0F - â˜ƒxxx;
         â˜ƒ[Direction.DOWN.get3DDataValue() + â˜ƒxxxxxx] = 1.0F - â˜ƒx;
         â˜ƒ[Direction.UP.get3DDataValue() + â˜ƒxxxxxx] = 1.0F - â˜ƒxxxx;
         â˜ƒ[Direction.NORTH.get3DDataValue() + â˜ƒxxxxxx] = 1.0F - â˜ƒxx;
         â˜ƒ[Direction.SOUTH.get3DDataValue() + â˜ƒxxxxxx] = 1.0F - â˜ƒxxxxx;
      }

      float â˜ƒxxxxxx = 1.0E-4F;
      float â˜ƒxxxxxxx = 0.9999F;
      switch(â˜ƒ) {
         case DOWN:
            â˜ƒ.set(1, â˜ƒ >= 1.0E-4F || â˜ƒxx >= 1.0E-4F || â˜ƒxxx <= 0.9999F || â˜ƒxxxxx <= 0.9999F);
            â˜ƒ.set(0, â˜ƒx == â˜ƒxxxx && (â˜ƒx < 1.0E-4F || â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)));
            break;
         case UP:
            â˜ƒ.set(1, â˜ƒ >= 1.0E-4F || â˜ƒxx >= 1.0E-4F || â˜ƒxxx <= 0.9999F || â˜ƒxxxxx <= 0.9999F);
            â˜ƒ.set(0, â˜ƒx == â˜ƒxxxx && (â˜ƒxxxx > 0.9999F || â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)));
            break;
         case NORTH:
            â˜ƒ.set(1, â˜ƒ >= 1.0E-4F || â˜ƒx >= 1.0E-4F || â˜ƒxxx <= 0.9999F || â˜ƒxxxx <= 0.9999F);
            â˜ƒ.set(0, â˜ƒxx == â˜ƒxxxxx && (â˜ƒxx < 1.0E-4F || â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)));
            break;
         case SOUTH:
            â˜ƒ.set(1, â˜ƒ >= 1.0E-4F || â˜ƒx >= 1.0E-4F || â˜ƒxxx <= 0.9999F || â˜ƒxxxx <= 0.9999F);
            â˜ƒ.set(0, â˜ƒxx == â˜ƒxxxxx && (â˜ƒxxxxx > 0.9999F || â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)));
            break;
         case WEST:
            â˜ƒ.set(1, â˜ƒx >= 1.0E-4F || â˜ƒxx >= 1.0E-4F || â˜ƒxxxx <= 0.9999F || â˜ƒxxxxx <= 0.9999F);
            â˜ƒ.set(0, â˜ƒ == â˜ƒxxx && (â˜ƒ < 1.0E-4F || â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)));
            break;
         case EAST:
            â˜ƒ.set(1, â˜ƒx >= 1.0E-4F || â˜ƒxx >= 1.0E-4F || â˜ƒxxxx <= 0.9999F || â˜ƒxxxxx <= 0.9999F);
            â˜ƒ.set(0, â˜ƒ == â˜ƒxxx && (â˜ƒxxx > 0.9999F || â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)));
      }
   }

   private void renderModelFaceFlat(
      BlockAndTintGetter var1,
      BlockState var2,
      BlockPos var3,
      int var4,
      int var5,
      boolean var6,
      PoseStack var7,
      VertexConsumer var8,
      List<BakedQuad> var9,
      BitSet var10
   ) {
      for(BakedQuad â˜ƒ : â˜ƒ) {
         if (â˜ƒ) {
            this.calculateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getVertices(), â˜ƒ.getDirection(), null, â˜ƒ);
            BlockPos â˜ƒx = â˜ƒ.get(0) ? â˜ƒ.relative(â˜ƒ.getDirection()) : â˜ƒ;
            â˜ƒ = LevelRenderer.getLightColor(â˜ƒ, â˜ƒ, â˜ƒx);
         }

         float â˜ƒx = â˜ƒ.getShade(â˜ƒ.getDirection(), â˜ƒ.isShade());
         this.putQuadData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.last(), â˜ƒ, â˜ƒx, â˜ƒx, â˜ƒx, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void renderModel(
      PoseStack.Pose var1, VertexConsumer var2, @Nullable BlockState var3, BakedModel var4, float var5, float var6, float var7, int var8, int var9
   ) {
      Random â˜ƒ = new Random();
      long â˜ƒx = 42L;

      for(Direction â˜ƒxx : DIRECTIONS) {
         â˜ƒ.setSeed(42L);
         renderQuadList(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getQuads(â˜ƒ, â˜ƒxx, â˜ƒ), â˜ƒ, â˜ƒ);
      }

      â˜ƒ.setSeed(42L);
      renderQuadList(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getQuads(â˜ƒ, null, â˜ƒ), â˜ƒ, â˜ƒ);
   }

   private static void renderQuadList(PoseStack.Pose var0, VertexConsumer var1, float var2, float var3, float var4, List<BakedQuad> var5, int var6, int var7) {
      for(BakedQuad â˜ƒ : â˜ƒ) {
         float â˜ƒx;
         float â˜ƒxx;
         float â˜ƒxxx;
         if (â˜ƒ.isTinted()) {
            â˜ƒx = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
            â˜ƒxx = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
            â˜ƒxxx = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
         } else {
            â˜ƒx = 1.0F;
            â˜ƒxx = 1.0F;
            â˜ƒxxx = 1.0F;
         }

         â˜ƒ.putBulkData(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ);
      }
   }

   public static void enableCaching() {
      ((ModelBlockRenderer.Cache)CACHE.get()).enable();
   }

   public static void clearCache() {
      ((ModelBlockRenderer.Cache)CACHE.get()).disable();
   }

   protected static enum AdjacencyInfo {
      DOWN(
         new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH},
         0.5F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      ),
      UP(
         new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH},
         1.0F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      ),
      NORTH(
         new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST},
         0.8F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST
         }
      ),
      SOUTH(
         new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP},
         0.8F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.WEST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_WEST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.WEST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.WEST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.EAST
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_EAST,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.EAST,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.EAST
         }
      ),
      WEST(
         new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH},
         0.6F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      ),
      EAST(
         new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH},
         0.6F,
         true,
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.SOUTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.DOWN,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.NORTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_NORTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.NORTH
         },
         new ModelBlockRenderer.SizeInfo[]{
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.SOUTH,
            ModelBlockRenderer.SizeInfo.FLIP_UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.FLIP_SOUTH,
            ModelBlockRenderer.SizeInfo.UP,
            ModelBlockRenderer.SizeInfo.SOUTH
         }
      );

      final Direction[] corners;
      final boolean doNonCubicWeight;
      final ModelBlockRenderer.SizeInfo[] vert0Weights;
      final ModelBlockRenderer.SizeInfo[] vert1Weights;
      final ModelBlockRenderer.SizeInfo[] vert2Weights;
      final ModelBlockRenderer.SizeInfo[] vert3Weights;
      private static final ModelBlockRenderer.AdjacencyInfo[] BY_FACING = Util.make(new ModelBlockRenderer.AdjacencyInfo[6], var0 -> {
         var0[Direction.DOWN.get3DDataValue()] = DOWN;
         var0[Direction.UP.get3DDataValue()] = UP;
         var0[Direction.NORTH.get3DDataValue()] = NORTH;
         var0[Direction.SOUTH.get3DDataValue()] = SOUTH;
         var0[Direction.WEST.get3DDataValue()] = WEST;
         var0[Direction.EAST.get3DDataValue()] = EAST;
      });

      private AdjacencyInfo(
         Direction[] var3,
         float var4,
         boolean var5,
         ModelBlockRenderer.SizeInfo[] var6,
         ModelBlockRenderer.SizeInfo[] var7,
         ModelBlockRenderer.SizeInfo[] var8,
         ModelBlockRenderer.SizeInfo[] var9
      ) {
         this.corners = â˜ƒ;
         this.doNonCubicWeight = â˜ƒ;
         this.vert0Weights = â˜ƒ;
         this.vert1Weights = â˜ƒ;
         this.vert2Weights = â˜ƒ;
         this.vert3Weights = â˜ƒ;
      }

      public static ModelBlockRenderer.AdjacencyInfo fromFacing(Direction var0) {
         return BY_FACING[â˜ƒ.get3DDataValue()];
      }
   }

   class AmbientOcclusionFace {
      final float[] brightness = new float[4];
      final int[] lightmap = new int[4];

      public AmbientOcclusionFace() {
      }

      public void calculate(BlockAndTintGetter var1, BlockState var2, BlockPos var3, Direction var4, float[] var5, BitSet var6, boolean var7) {
         BlockPos â˜ƒxx = â˜ƒ.get(0) ? â˜ƒ.relative(â˜ƒ) : â˜ƒ;
         ModelBlockRenderer.AdjacencyInfo â˜ƒxxx = ModelBlockRenderer.AdjacencyInfo.fromFacing(â˜ƒ);
         BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();
         ModelBlockRenderer.Cache â˜ƒxxxxx = (ModelBlockRenderer.Cache)ModelBlockRenderer.CACHE.get();
         â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[0]);
         BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
         int â˜ƒxxxxxxx = â˜ƒxxxxx.getLightColor(â˜ƒxxxxxx, â˜ƒ, â˜ƒxxxx);
         float â˜ƒxxxxxxxx = â˜ƒxxxxx.getShadeBrightness(â˜ƒxxxxxx, â˜ƒ, â˜ƒxxxx);
         â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[1]);
         BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
         int â˜ƒxxxxxxxxxx = â˜ƒxxxxx.getLightColor(â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒxxxx);
         float â˜ƒxxxxxxxxxxx = â˜ƒxxxxx.getShadeBrightness(â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒxxxx);
         â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[2]);
         BlockState â˜ƒxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
         int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxx.getLightColor(â˜ƒxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxx);
         float â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxx.getShadeBrightness(â˜ƒxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxx);
         â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[3]);
         BlockState â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
         int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxx.getLightColor(â˜ƒxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxx);
         float â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxx.getShadeBrightness(â˜ƒxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxx);
         BlockState â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[0]).move(â˜ƒ));
         boolean â˜ƒxxxxxxxxxxxxxxxxxxx = !â˜ƒxxxxxxxxxxxxxxxxxx.isViewBlocking(â˜ƒ, â˜ƒxxxx) || â˜ƒxxxxxxxxxxxxxxxxxx.getLightBlock(â˜ƒ, â˜ƒxxxx) == 0;
         BlockState â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[1]).move(â˜ƒ));
         boolean â˜ƒxxxxxxxxxxxxxxxxxxxxx = !â˜ƒxxxxxxxxxxxxxxxxxxxx.isViewBlocking(â˜ƒ, â˜ƒxxxx) || â˜ƒxxxxxxxxxxxxxxxxxxxx.getLightBlock(â˜ƒ, â˜ƒxxxx) == 0;
         BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[2]).move(â˜ƒ));
         boolean â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = !â˜ƒxxxxxxxxxxxxxxxxxxxxxx.isViewBlocking(â˜ƒ, â˜ƒxxxx)
            || â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getLightBlock(â˜ƒ, â˜ƒxxxx) == 0;
         BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[3]).move(â˜ƒ));
         boolean â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = !â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.isViewBlocking(â˜ƒ, â˜ƒxxxx)
            || â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.getLightBlock(â˜ƒ, â˜ƒxxxx) == 0;
         float â˜ƒ;
         int â˜ƒx;
         if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxx && !â˜ƒxxxxxxxxxxxxxxxxxxx) {
            â˜ƒ = â˜ƒxxxxxxxx;
            â˜ƒx = â˜ƒxxxxxxx;
         } else {
            â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[0]).move(â˜ƒxxx.corners[2]);
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒxxxx);
            â˜ƒ = â˜ƒxxxxx.getShadeBrightness(â˜ƒ, â˜ƒ, â˜ƒxxxx);
            â˜ƒx = â˜ƒxxxxx.getLightColor(â˜ƒ, â˜ƒ, â˜ƒxxxx);
         }

         float â˜ƒ;
         int â˜ƒx;
         if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx && !â˜ƒxxxxxxxxxxxxxxxxxxx) {
            â˜ƒ = â˜ƒxxxxxxxx;
            â˜ƒx = â˜ƒxxxxxxx;
         } else {
            â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[0]).move(â˜ƒxxx.corners[3]);
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒxxxx);
            â˜ƒ = â˜ƒxxxxx.getShadeBrightness(â˜ƒ, â˜ƒ, â˜ƒxxxx);
            â˜ƒx = â˜ƒxxxxx.getLightColor(â˜ƒ, â˜ƒ, â˜ƒxxxx);
         }

         float â˜ƒ;
         int â˜ƒx;
         if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxx && !â˜ƒxxxxxxxxxxxxxxxxxxxxx) {
            â˜ƒ = â˜ƒxxxxxxxx;
            â˜ƒx = â˜ƒxxxxxxx;
         } else {
            â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[1]).move(â˜ƒxxx.corners[2]);
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒxxxx);
            â˜ƒ = â˜ƒxxxxx.getShadeBrightness(â˜ƒ, â˜ƒ, â˜ƒxxxx);
            â˜ƒx = â˜ƒxxxxx.getLightColor(â˜ƒ, â˜ƒ, â˜ƒxxxx);
         }

         float â˜ƒ;
         int â˜ƒx;
         if (!â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx && !â˜ƒxxxxxxxxxxxxxxxxxxxxx) {
            â˜ƒ = â˜ƒxxxxxxxx;
            â˜ƒx = â˜ƒxxxxxxx;
         } else {
            â˜ƒxxxx.setWithOffset(â˜ƒxx, â˜ƒxxx.corners[1]).move(â˜ƒxxx.corners[3]);
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒxxxx);
            â˜ƒ = â˜ƒxxxxx.getShadeBrightness(â˜ƒ, â˜ƒ, â˜ƒxxxx);
            â˜ƒx = â˜ƒxxxxx.getLightColor(â˜ƒ, â˜ƒ, â˜ƒxxxx);
         }

         int â˜ƒ = â˜ƒxxxxx.getLightColor(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒxxxx.setWithOffset(â˜ƒ, â˜ƒ);
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒxxxx);
         if (â˜ƒ.get(0) || !â˜ƒx.isSolidRender(â˜ƒ, â˜ƒxxxx)) {
            â˜ƒ = â˜ƒxxxxx.getLightColor(â˜ƒx, â˜ƒ, â˜ƒxxxx);
         }

         float â˜ƒ = â˜ƒ.get(0)
            ? â˜ƒxxxxx.getShadeBrightness(â˜ƒ.getBlockState(â˜ƒxx), â˜ƒ, â˜ƒxx)
            : â˜ƒxxxxx.getShadeBrightness(â˜ƒ.getBlockState(â˜ƒ), â˜ƒ, â˜ƒ);
         ModelBlockRenderer.AmbientVertexRemap â˜ƒx = ModelBlockRenderer.AmbientVertexRemap.fromFacing(â˜ƒ);
         if (â˜ƒ.get(1) && â˜ƒxxx.doNonCubicWeight) {
            float â˜ƒxx = (â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒxxx = (â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒxxxx = (â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒxxxxx = (â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒxxxxxx = â˜ƒ[â˜ƒxxx.vert0Weights[0].shape] * â˜ƒ[â˜ƒxxx.vert0Weights[1].shape];
            float â˜ƒxxxxxxx = â˜ƒ[â˜ƒxxx.vert0Weights[2].shape] * â˜ƒ[â˜ƒxxx.vert0Weights[3].shape];
            float â˜ƒxxxxxxxx = â˜ƒ[â˜ƒxxx.vert0Weights[4].shape] * â˜ƒ[â˜ƒxxx.vert0Weights[5].shape];
            float â˜ƒxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert0Weights[6].shape] * â˜ƒ[â˜ƒxxx.vert0Weights[7].shape];
            float â˜ƒxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert1Weights[0].shape] * â˜ƒ[â˜ƒxxx.vert1Weights[1].shape];
            float â˜ƒxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert1Weights[2].shape] * â˜ƒ[â˜ƒxxx.vert1Weights[3].shape];
            float â˜ƒxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert1Weights[4].shape] * â˜ƒ[â˜ƒxxx.vert1Weights[5].shape];
            float â˜ƒxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert1Weights[6].shape] * â˜ƒ[â˜ƒxxx.vert1Weights[7].shape];
            float â˜ƒxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert2Weights[0].shape] * â˜ƒ[â˜ƒxxx.vert2Weights[1].shape];
            float â˜ƒxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert2Weights[2].shape] * â˜ƒ[â˜ƒxxx.vert2Weights[3].shape];
            float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert2Weights[4].shape] * â˜ƒ[â˜ƒxxx.vert2Weights[5].shape];
            float â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert2Weights[6].shape] * â˜ƒ[â˜ƒxxx.vert2Weights[7].shape];
            float â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert3Weights[0].shape] * â˜ƒ[â˜ƒxxx.vert3Weights[1].shape];
            float â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert3Weights[2].shape] * â˜ƒ[â˜ƒxxx.vert3Weights[3].shape];
            float â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert3Weights[4].shape] * â˜ƒ[â˜ƒxxx.vert3Weights[5].shape];
            float â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒ[â˜ƒxxx.vert3Weights[6].shape] * â˜ƒ[â˜ƒxxx.vert3Weights[7].shape];
            this.brightness[â˜ƒx.vert0] = â˜ƒxx * â˜ƒxxxxxx + â˜ƒxxx * â˜ƒxxxxxxx + â˜ƒxxxx * â˜ƒxxxxxxxx + â˜ƒxxxxx * â˜ƒxxxxxxxxx;
            this.brightness[â˜ƒx.vert1] = â˜ƒxx * â˜ƒxxxxxxxxxx + â˜ƒxxx * â˜ƒxxxxxxxxxxx + â˜ƒxxxx * â˜ƒxxxxxxxxxxxx + â˜ƒxxxxx * â˜ƒxxxxxxxxxxxxx;
            this.brightness[â˜ƒx.vert2] = â˜ƒxx * â˜ƒxxxxxxxxxxxxxx
               + â˜ƒxxx * â˜ƒxxxxxxxxxxxxxxx
               + â˜ƒxxxx * â˜ƒxxxxxxxxxxxxxxxx
               + â˜ƒxxxxx * â˜ƒxxxxxxxxxxxxxxxxx;
            this.brightness[â˜ƒx.vert3] = â˜ƒxx * â˜ƒxxxxxxxxxxxxxxxxxx
               + â˜ƒxxx * â˜ƒxxxxxxxxxxxxxxxxxxx
               + â˜ƒxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx
               + â˜ƒxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxx;
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = this.blend(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒ);
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = this.blend(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒ);
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = this.blend(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒx, â˜ƒ);
            int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = this.blend(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒx, â˜ƒ);
            this.lightmap[â˜ƒx.vert0] = this.blend(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxx,
               â˜ƒxxxxxxxxx
            );
            this.lightmap[â˜ƒx.vert1] = this.blend(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxx
            );
            this.lightmap[â˜ƒx.vert2] = this.blend(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx
            );
            this.lightmap[â˜ƒx.vert3] = this.blend(
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx
            );
         } else {
            float â˜ƒ = (â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒx = (â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒxx = (â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            float â˜ƒxxx = (â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxx + â˜ƒ + â˜ƒ) * 0.25F;
            this.lightmap[â˜ƒx.vert0] = this.blend(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒ);
            this.lightmap[â˜ƒx.vert1] = this.blend(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒ);
            this.lightmap[â˜ƒx.vert2] = this.blend(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒx, â˜ƒ);
            this.lightmap[â˜ƒx.vert3] = this.blend(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒx, â˜ƒ);
            this.brightness[â˜ƒx.vert0] = â˜ƒ;
            this.brightness[â˜ƒx.vert1] = â˜ƒx;
            this.brightness[â˜ƒx.vert2] = â˜ƒxx;
            this.brightness[â˜ƒx.vert3] = â˜ƒxxx;
         }

         float â˜ƒ = â˜ƒ.getShade(â˜ƒ, â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < this.brightness.length; ++â˜ƒx) {
            this.brightness[â˜ƒx] *= â˜ƒ;
         }
      }

      private int blend(int var1, int var2, int var3, int var4) {
         if (â˜ƒ == 0) {
            â˜ƒ = â˜ƒ;
         }

         if (â˜ƒ == 0) {
            â˜ƒ = â˜ƒ;
         }

         if (â˜ƒ == 0) {
            â˜ƒ = â˜ƒ;
         }

         return â˜ƒ + â˜ƒ + â˜ƒ + â˜ƒ >> 2 & 16711935;
      }

      private int blend(int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         int â˜ƒ = (int)((float)(â˜ƒ >> 16 & 0xFF) * â˜ƒ + (float)(â˜ƒ >> 16 & 0xFF) * â˜ƒ + (float)(â˜ƒ >> 16 & 0xFF) * â˜ƒ + (float)(â˜ƒ >> 16 & 0xFF) * â˜ƒ)
            & 0xFF;
         int â˜ƒx = (int)((float)(â˜ƒ & 0xFF) * â˜ƒ + (float)(â˜ƒ & 0xFF) * â˜ƒ + (float)(â˜ƒ & 0xFF) * â˜ƒ + (float)(â˜ƒ & 0xFF) * â˜ƒ) & 0xFF;
         return â˜ƒ << 16 | â˜ƒx;
      }
   }

   static enum AmbientVertexRemap {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      final int vert0;
      final int vert1;
      final int vert2;
      final int vert3;
      private static final ModelBlockRenderer.AmbientVertexRemap[] BY_FACING = Util.make(new ModelBlockRenderer.AmbientVertexRemap[6], var0 -> {
         var0[Direction.DOWN.get3DDataValue()] = DOWN;
         var0[Direction.UP.get3DDataValue()] = UP;
         var0[Direction.NORTH.get3DDataValue()] = NORTH;
         var0[Direction.SOUTH.get3DDataValue()] = SOUTH;
         var0[Direction.WEST.get3DDataValue()] = WEST;
         var0[Direction.EAST.get3DDataValue()] = EAST;
      });

      private AmbientVertexRemap(int var3, int var4, int var5, int var6) {
         this.vert0 = â˜ƒ;
         this.vert1 = â˜ƒ;
         this.vert2 = â˜ƒ;
         this.vert3 = â˜ƒ;
      }

      public static ModelBlockRenderer.AmbientVertexRemap fromFacing(Direction var0) {
         return BY_FACING[â˜ƒ.get3DDataValue()];
      }
   }

   static class Cache {
      private boolean enabled;
      private final Long2IntLinkedOpenHashMap colorCache = Util.make(() -> {
         Long2IntLinkedOpenHashMap â˜ƒ = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            @Override
            protected void rehash(int var1) {
            }
         };
         â˜ƒ.defaultReturnValue(Integer.MAX_VALUE);
         return â˜ƒ;
      });
      private final Long2FloatLinkedOpenHashMap brightnessCache = Util.make(() -> {
         Long2FloatLinkedOpenHashMap â˜ƒ = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            @Override
            protected void rehash(int var1) {
            }
         };
         â˜ƒ.defaultReturnValue(Float.NaN);
         return â˜ƒ;
      });

      private Cache() {
      }

      public void enable() {
         this.enabled = true;
      }

      public void disable() {
         this.enabled = false;
         this.colorCache.clear();
         this.brightnessCache.clear();
      }

      public int getLightColor(BlockState var1, BlockAndTintGetter var2, BlockPos var3) {
         long â˜ƒ = â˜ƒ.asLong();
         if (this.enabled) {
            int â˜ƒx = this.colorCache.get(â˜ƒ);
            if (â˜ƒx != Integer.MAX_VALUE) {
               return â˜ƒx;
            }
         }

         int â˜ƒ = LevelRenderer.getLightColor(â˜ƒ, â˜ƒ, â˜ƒ);
         if (this.enabled) {
            if (this.colorCache.size() == 100) {
               this.colorCache.removeFirstInt();
            }

            this.colorCache.put(â˜ƒ, â˜ƒ);
         }

         return â˜ƒ;
      }

      public float getShadeBrightness(BlockState var1, BlockAndTintGetter var2, BlockPos var3) {
         long â˜ƒ = â˜ƒ.asLong();
         if (this.enabled) {
            float â˜ƒx = this.brightnessCache.get(â˜ƒ);
            if (!Float.isNaN(â˜ƒx)) {
               return â˜ƒx;
            }
         }

         float â˜ƒ = â˜ƒ.getShadeBrightness(â˜ƒ, â˜ƒ);
         if (this.enabled) {
            if (this.brightnessCache.size() == 100) {
               this.brightnessCache.removeFirstFloat();
            }

            this.brightnessCache.put(â˜ƒ, â˜ƒ);
         }

         return â˜ƒ;
      }
   }

   protected static enum SizeInfo {
      DOWN(Direction.DOWN, false),
      UP(Direction.UP, false),
      NORTH(Direction.NORTH, false),
      SOUTH(Direction.SOUTH, false),
      WEST(Direction.WEST, false),
      EAST(Direction.EAST, false),
      FLIP_DOWN(Direction.DOWN, true),
      FLIP_UP(Direction.UP, true),
      FLIP_NORTH(Direction.NORTH, true),
      FLIP_SOUTH(Direction.SOUTH, true),
      FLIP_WEST(Direction.WEST, true),
      FLIP_EAST(Direction.EAST, true);

      final int shape;

      private SizeInfo(Direction var3, boolean var4) {
         this.shape = â˜ƒ.get3DDataValue() + (â˜ƒ ? ModelBlockRenderer.DIRECTIONS.length : 0);
      }
   }
}
