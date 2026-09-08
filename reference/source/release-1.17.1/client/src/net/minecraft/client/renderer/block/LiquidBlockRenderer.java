package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LiquidBlockRenderer {
   private static final float MAX_FLUID_HEIGHT = 0.8888889F;
   private final TextureAtlasSprite[] lavaIcons = new TextureAtlasSprite[2];
   private final TextureAtlasSprite[] waterIcons = new TextureAtlasSprite[2];
   private TextureAtlasSprite waterOverlay;

   protected void setupSprites() {
      this.lavaIcons[0] = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(Blocks.LAVA.defaultBlockState()).getParticleIcon();
      this.lavaIcons[1] = ModelBakery.LAVA_FLOW.sprite();
      this.waterIcons[0] = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(Blocks.WATER.defaultBlockState()).getParticleIcon();
      this.waterIcons[1] = ModelBakery.WATER_FLOW.sprite();
      this.waterOverlay = ModelBakery.WATER_OVERLAY.sprite();
   }

   private static boolean isNeighborSameFluid(BlockGetter var0, BlockPos var1, Direction var2, FluidState var3) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
      FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ);
      return â˜ƒx.getType().isSame(â˜ƒ.getType());
   }

   private static boolean isFaceOccludedByState(BlockGetter var0, Direction var1, float var2, BlockPos var3, BlockState var4) {
      if (â˜ƒ.canOcclude()) {
         VoxelShape â˜ƒ = Shapes.box(0.0, 0.0, 0.0, 1.0, (double)â˜ƒ, 1.0);
         VoxelShape â˜ƒx = â˜ƒ.getOcclusionShape(â˜ƒ, â˜ƒ);
         return Shapes.blockOccudes(â˜ƒ, â˜ƒx, â˜ƒ);
      } else {
         return false;
      }
   }

   private static boolean isFaceOccludedByNeighbor(BlockGetter var0, BlockPos var1, Direction var2, float var3) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return isFaceOccludedByState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
   }

   private static boolean isFaceOccludedBySelf(BlockGetter var0, BlockPos var1, BlockState var2, Direction var3) {
      return isFaceOccludedByState(â˜ƒ, â˜ƒ.getOpposite(), 1.0F, â˜ƒ, â˜ƒ);
   }

   public static boolean shouldRenderFace(BlockAndTintGetter var0, BlockPos var1, FluidState var2, BlockState var3, Direction var4) {
      return !isFaceOccludedBySelf(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) && !isNeighborSameFluid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean tesselate(BlockAndTintGetter var1, BlockPos var2, VertexConsumer var3, FluidState var4) {
      boolean â˜ƒ = â˜ƒ.is(FluidTags.LAVA);
      TextureAtlasSprite[] â˜ƒx = â˜ƒ ? this.lavaIcons : this.waterIcons;
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
      int â˜ƒxxx = â˜ƒ ? 16777215 : BiomeColors.getAverageWaterColor(â˜ƒ, â˜ƒ);
      float â˜ƒxxxx = (float)(â˜ƒxxx >> 16 & 0xFF) / 255.0F;
      float â˜ƒxxxxx = (float)(â˜ƒxxx >> 8 & 0xFF) / 255.0F;
      float â˜ƒxxxxxx = (float)(â˜ƒxxx & 0xFF) / 255.0F;
      boolean â˜ƒxxxxxxx = !isNeighborSameFluid(â˜ƒ, â˜ƒ, Direction.UP, â˜ƒ);
      boolean â˜ƒxxxxxxxx = shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, Direction.DOWN) && !isFaceOccludedByNeighbor(â˜ƒ, â˜ƒ, Direction.DOWN, 0.8888889F);
      boolean â˜ƒxxxxxxxxx = shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, Direction.NORTH);
      boolean â˜ƒxxxxxxxxxx = shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, Direction.SOUTH);
      boolean â˜ƒxxxxxxxxxxx = shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, Direction.WEST);
      boolean â˜ƒxxxxxxxxxxxx = shouldRenderFace(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, Direction.EAST);
      if (!â˜ƒxxxxxxx && !â˜ƒxxxxxxxx && !â˜ƒxxxxxxxxxxxx && !â˜ƒxxxxxxxxxxx && !â˜ƒxxxxxxxxx && !â˜ƒxxxxxxxxxx) {
         return false;
      } else {
         boolean â˜ƒ = false;
         float â˜ƒx = â˜ƒ.getShade(Direction.DOWN, true);
         float â˜ƒxx = â˜ƒ.getShade(Direction.UP, true);
         float â˜ƒxxx = â˜ƒ.getShade(Direction.NORTH, true);
         float â˜ƒxxxx = â˜ƒ.getShade(Direction.WEST, true);
         float â˜ƒxxxxx = this.getWaterHeight(â˜ƒ, â˜ƒ, â˜ƒ.getType());
         float â˜ƒxxxxxx = this.getWaterHeight(â˜ƒ, â˜ƒ.south(), â˜ƒ.getType());
         float â˜ƒxxxxxxx = this.getWaterHeight(â˜ƒ, â˜ƒ.east().south(), â˜ƒ.getType());
         float â˜ƒxxxxxxxx = this.getWaterHeight(â˜ƒ, â˜ƒ.east(), â˜ƒ.getType());
         double â˜ƒxxxxxxxxx = (double)(â˜ƒ.getX() & 15);
         double â˜ƒxxxxxxxxxx = (double)(â˜ƒ.getY() & 15);
         double â˜ƒxxxxxxxxxxx = (double)(â˜ƒ.getZ() & 15);
         float â˜ƒxxxxxxxxxxxx = 0.001F;
         float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxx ? 0.001F : 0.0F;
         if (â˜ƒxxxxxxx && !isFaceOccludedByNeighbor(â˜ƒ, â˜ƒ, Direction.UP, Math.min(Math.min(â˜ƒxxxxx, â˜ƒxxxxxx), Math.min(â˜ƒxxxxxxx, â˜ƒxxxxxxxx)))) {
            â˜ƒ = true;
            â˜ƒxxxxx -= 0.001F;
            â˜ƒxxxxxx -= 0.001F;
            â˜ƒxxxxxxx -= 0.001F;
            â˜ƒxxxxxxxx -= 0.001F;
            Vec3 â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getFlow(â˜ƒ, â˜ƒ);
            float â˜ƒxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxxxxxx;
            if (â˜ƒxxxxxxxxxxxxxxxxxxxxxx.x == 0.0 && â˜ƒxxxxxxxxxxxxxxxxxxxxxx.z == 0.0) {
               TextureAtlasSprite â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒx[0];
               â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.getU(0.0);
               â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.getV(0.0);
               â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.getV(16.0);
               â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.getU(16.0);
               â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx;
            } else {
               TextureAtlasSprite â˜ƒxxxxxxxxxxxxxx = â˜ƒx[1];
               float â˜ƒxxxxxxxxxxxxxxx = (float)Mth.atan2(â˜ƒxxxxxxxxxxxxxxxxxxxxxx.z, â˜ƒxxxxxxxxxxxxxxxxxxxxxx.x) - (float) (Math.PI / 2);
               float â˜ƒxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxxxxx) * 0.25F;
               float â˜ƒxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxxxxx) * 0.25F;
               float â˜ƒxxxxxxxxxxxxxxxxxx = 8.0F;
               â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getU((double)(8.0F + (-â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getV((double)(8.0F + (-â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getU((double)(8.0F + (-â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getV((double)(8.0F + (â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getU((double)(8.0F + (â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getV((double)(8.0F + (â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getU((double)(8.0F + (â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
               â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.getV((double)(8.0F + (-â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxx) * 16.0F));
            }

            float â˜ƒxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxx) / 4.0F;
            float â˜ƒxxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxx) / 4.0F;
            float â˜ƒxxxxxxxxxxxxxxxx = (float)â˜ƒx[0].getWidth() / (â˜ƒx[0].getU1() - â˜ƒx[0].getU0());
            float â˜ƒxxxxxxxxxxxxxxxxx = (float)â˜ƒx[0].getHeight() / (â˜ƒx[0].getV1() - â˜ƒx[0].getV0());
            float â˜ƒxxxxxxxxxxxxxxxxxx = 4.0F / Math.max(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxxxxxxxx = this.getLightColor(â˜ƒ, â˜ƒ);
            float â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxx * â˜ƒxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxx * â˜ƒxxxxx;
            float â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxx * â˜ƒxxxxxx;
            this.vertex(
               â˜ƒ,
               â˜ƒxxxxxxxxx + 0.0,
               â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxx,
               â˜ƒxxxxxxxxxxx + 0.0,
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒ,
               â˜ƒxxxxxxxxx + 0.0,
               â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxx,
               â˜ƒxxxxxxxxxxx + 1.0,
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒ,
               â˜ƒxxxxxxxxx + 1.0,
               â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxx,
               â˜ƒxxxxxxxxxxx + 1.0,
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxx
            );
            this.vertex(
               â˜ƒ,
               â˜ƒxxxxxxxxx + 1.0,
               â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxx,
               â˜ƒxxxxxxxxxxx + 0.0,
               â˜ƒxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxxxxxxxxx
            );
            if (â˜ƒ.shouldRenderBackwardUpFace(â˜ƒ, â˜ƒ.above())) {
               this.vertex(
                  â˜ƒ,
                  â˜ƒxxxxxxxxx + 0.0,
                  â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxx,
                  â˜ƒxxxxxxxxxxx + 0.0,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  â˜ƒ,
                  â˜ƒxxxxxxxxx + 1.0,
                  â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxx,
                  â˜ƒxxxxxxxxxxx + 0.0,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  â˜ƒ,
                  â˜ƒxxxxxxxxx + 1.0,
                  â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxx,
                  â˜ƒxxxxxxxxxxx + 1.0,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxx
               );
               this.vertex(
                  â˜ƒ,
                  â˜ƒxxxxxxxxx + 0.0,
                  â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxx,
                  â˜ƒxxxxxxxxxxx + 1.0,
                  â˜ƒxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxxx
               );
            }
         }

         if (â˜ƒxxxxxxxx) {
            float â˜ƒ = â˜ƒx[0].getU0();
            float â˜ƒx = â˜ƒx[0].getU1();
            float â˜ƒxx = â˜ƒx[0].getV0();
            float â˜ƒxxx = â˜ƒx[0].getV1();
            int â˜ƒxxxx = this.getLightColor(â˜ƒ, â˜ƒ.below());
            float â˜ƒxxxxx = â˜ƒx * â˜ƒxxxx;
            float â˜ƒxxxxxx = â˜ƒx * â˜ƒxxxxx;
            float â˜ƒxxxxxxx = â˜ƒx * â˜ƒxxxxxx;
            this.vertex(
               â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx + 1.0, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒ, â˜ƒxxx, â˜ƒxxxx
            );
            this.vertex(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒ, â˜ƒxx, â˜ƒxxxx);
            this.vertex(
               â˜ƒ, â˜ƒxxxxxxxxx + 1.0, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxxx
            );
            this.vertex(
               â˜ƒ, â˜ƒxxxxxxxxx + 1.0, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx + 1.0, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒx, â˜ƒxxx, â˜ƒxxxx
            );
            â˜ƒ = true;
         }

         int â˜ƒ = this.getLightColor(â˜ƒ, â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
            float â˜ƒxx;
            float â˜ƒxxx;
            double â˜ƒxxxx;
            double â˜ƒxxxxx;
            double â˜ƒxxxxxx;
            double â˜ƒxxxxxxx;
            Direction â˜ƒxxxxxxxx;
            boolean â˜ƒxxxxxxxxx;
            if (â˜ƒx == 0) {
               â˜ƒxx = â˜ƒxxxxx;
               â˜ƒxxx = â˜ƒxxxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxx;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxx + 1.0;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxx + 0.001F;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxx + 0.001F;
               â˜ƒxxxxxxxx = Direction.NORTH;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx;
            } else if (â˜ƒx == 1) {
               â˜ƒxx = â˜ƒxxxxxxx;
               â˜ƒxxx = â˜ƒxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxx + 1.0;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxx + 1.0 - 0.001F;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxx + 1.0 - 0.001F;
               â˜ƒxxxxxxxx = Direction.SOUTH;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxx;
            } else if (â˜ƒx == 2) {
               â˜ƒxx = â˜ƒxxxxxx;
               â˜ƒxxx = â˜ƒxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxx + 0.001F;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxx + 0.001F;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxx + 1.0;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxx;
               â˜ƒxxxxxxxx = Direction.WEST;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxx;
            } else {
               â˜ƒxx = â˜ƒxxxxxxxx;
               â˜ƒxxx = â˜ƒxxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxx + 1.0 - 0.001F;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxx + 1.0 - 0.001F;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxx;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxx + 1.0;
               â˜ƒxxxxxxxx = Direction.EAST;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxx;
            }

            if (â˜ƒxxxxxxxxx && !isFaceOccludedByNeighbor(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxx, Math.max(â˜ƒxx, â˜ƒxxx))) {
               â˜ƒ = true;
               BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒxxxxxxxx);
               TextureAtlasSprite â˜ƒxxx = â˜ƒx[1];
               if (!â˜ƒ) {
                  Block â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxx).getBlock();
                  if (â˜ƒxxxx instanceof HalfTransparentBlock || â˜ƒxxxx instanceof LeavesBlock) {
                     â˜ƒxxx = this.waterOverlay;
                  }
               }

               float â˜ƒxx = â˜ƒxxx.getU(0.0);
               float â˜ƒxxx = â˜ƒxxx.getU(8.0);
               float â˜ƒxxxx = â˜ƒxxx.getV((double)((1.0F - â˜ƒxx) * 16.0F * 0.5F));
               float â˜ƒxxxxx = â˜ƒxxx.getV((double)((1.0F - â˜ƒxxx) * 16.0F * 0.5F));
               float â˜ƒxxxxxx = â˜ƒxxx.getV(8.0);
               float â˜ƒxxxxxxx = â˜ƒx < 2 ? â˜ƒxxx : â˜ƒxxxx;
               float â˜ƒxxxxxxxx = â˜ƒxx * â˜ƒxxxxxxx * â˜ƒxxxx;
               float â˜ƒxxxxxxxxx = â˜ƒxx * â˜ƒxxxxxxx * â˜ƒxxxxx;
               float â˜ƒxxxxxxxxxx = â˜ƒxx * â˜ƒxxxxxxx * â˜ƒxxxxxx;
               this.vertex(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxx, â˜ƒxxxx, â˜ƒ);
               this.vertex(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxxx, â˜ƒ);
               this.vertex(
                  â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxx, â˜ƒ
               );
               this.vertex(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxx, â˜ƒxxxxxx, â˜ƒ);
               if (â˜ƒxxx != this.waterOverlay) {
                  this.vertex(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxx, â˜ƒxxxxxx, â˜ƒ);
                  this.vertex(
                     â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxx, â˜ƒ
                  );
                  this.vertex(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxxx, â˜ƒ);
                  this.vertex(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxxx + (double)â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxx, â˜ƒxxxx, â˜ƒ);
               }
            }
         }

         return â˜ƒ;
      }
   }

   private void vertex(VertexConsumer var1, double var2, double var4, double var6, float var8, float var9, float var10, float var11, float var12, int var13) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F).uv(â˜ƒ, â˜ƒ).uv2(â˜ƒ).normal(0.0F, 1.0F, 0.0F).endVertex();
   }

   private int getLightColor(BlockAndTintGetter var1, BlockPos var2) {
      int â˜ƒ = LevelRenderer.getLightColor(â˜ƒ, â˜ƒ);
      int â˜ƒx = LevelRenderer.getLightColor(â˜ƒ, â˜ƒ.above());
      int â˜ƒxx = â˜ƒ & 0xFF;
      int â˜ƒxxx = â˜ƒx & 0xFF;
      int â˜ƒxxxx = â˜ƒ >> 16 & 0xFF;
      int â˜ƒxxxxx = â˜ƒx >> 16 & 0xFF;
      return (â˜ƒxx > â˜ƒxxx ? â˜ƒxx : â˜ƒxxx) | (â˜ƒxxxx > â˜ƒxxxxx ? â˜ƒxxxx : â˜ƒxxxxx) << 16;
   }

   private float getWaterHeight(BlockGetter var1, BlockPos var2, Fluid var3) {
      int â˜ƒ = 0;
      float â˜ƒx = 0.0F;

      for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
         BlockPos â˜ƒxxx = â˜ƒ.offset(-(â˜ƒxx & 1), 0, -(â˜ƒxx >> 1 & 1));
         if (â˜ƒ.getFluidState(â˜ƒxxx.above()).getType().isSame(â˜ƒ)) {
            return 1.0F;
         }

         FluidState â˜ƒxxx = â˜ƒ.getFluidState(â˜ƒxxx);
         if (â˜ƒxxx.getType().isSame(â˜ƒ)) {
            float â˜ƒxxxx = â˜ƒxxx.getHeight(â˜ƒ, â˜ƒxxx);
            if (â˜ƒxxxx >= 0.8F) {
               â˜ƒx += â˜ƒxxxx * 10.0F;
               â˜ƒ += 10;
            } else {
               â˜ƒx += â˜ƒxxxx;
               ++â˜ƒ;
            }
         } else if (!â˜ƒ.getBlockState(â˜ƒxxx).getMaterial().isSolid()) {
            ++â˜ƒ;
         }
      }

      return â˜ƒx / (float)â˜ƒ;
   }
}
