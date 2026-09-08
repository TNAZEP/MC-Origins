package net.minecraft.client.renderer.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;

public class RenderChunkRegion implements BlockAndTintGetter {
   protected final int centerX;
   protected final int centerZ;
   protected final BlockPos start;
   protected final int xLength;
   protected final int yLength;
   protected final int zLength;
   protected final LevelChunk[][] chunks;
   protected final BlockState[] blockStates;
   protected final Level level;

   @Nullable
   public static RenderChunkRegion createIfNotEmpty(Level var0, BlockPos var1, BlockPos var2, int var3) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX() - â˜ƒ);
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ() - â˜ƒ);
      int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ.getX() + â˜ƒ);
      int â˜ƒxxx = SectionPos.blockToSectionCoord(â˜ƒ.getZ() + â˜ƒ);
      LevelChunk[][] â˜ƒxxxx = new LevelChunk[â˜ƒxx - â˜ƒ + 1][â˜ƒxxx - â˜ƒx + 1];

      for(int â˜ƒxxxxx = â˜ƒ; â˜ƒxxxxx <= â˜ƒxx; ++â˜ƒxxxxx) {
         for(int â˜ƒxxxxxx = â˜ƒx; â˜ƒxxxxxx <= â˜ƒxxx; ++â˜ƒxxxxxx) {
            â˜ƒxxxx[â˜ƒxxxxx - â˜ƒ][â˜ƒxxxxxx - â˜ƒx] = â˜ƒ.getChunk(â˜ƒxxxxx, â˜ƒxxxxxx);
         }
      }

      if (isAllEmpty(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxxx)) {
         return null;
      } else {
         int â˜ƒxxxxx = 1;
         BlockPos â˜ƒxxxxxx = â˜ƒ.offset(-1, -1, -1);
         BlockPos â˜ƒxxxxxxx = â˜ƒ.offset(1, 1, 1);
         return new RenderChunkRegion(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
      }
   }

   public static boolean isAllEmpty(BlockPos var0, BlockPos var1, int var2, int var3, LevelChunk[][] var4) {
      for(int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX()); â˜ƒ <= SectionPos.blockToSectionCoord(â˜ƒ.getX()); ++â˜ƒ) {
         for(int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ()); â˜ƒx <= SectionPos.blockToSectionCoord(â˜ƒ.getZ()); ++â˜ƒx) {
            LevelChunk â˜ƒxx = â˜ƒ[â˜ƒ - â˜ƒ][â˜ƒx - â˜ƒ];
            if (!â˜ƒxx.isYSpaceEmpty(â˜ƒ.getY(), â˜ƒ.getY())) {
               return false;
            }
         }
      }

      return true;
   }

   public RenderChunkRegion(Level var1, int var2, int var3, LevelChunk[][] var4, BlockPos var5, BlockPos var6) {
      this.level = â˜ƒ;
      this.centerX = â˜ƒ;
      this.centerZ = â˜ƒ;
      this.chunks = â˜ƒ;
      this.start = â˜ƒ;
      this.xLength = â˜ƒ.getX() - â˜ƒ.getX() + 1;
      this.yLength = â˜ƒ.getY() - â˜ƒ.getY() + 1;
      this.zLength = â˜ƒ.getZ() - â˜ƒ.getZ() + 1;
      this.blockStates = new BlockState[this.xLength * this.yLength * this.zLength];

      for(BlockPos â˜ƒ : BlockPos.betweenClosed(â˜ƒ, â˜ƒ)) {
         int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getX()) - â˜ƒ;
         int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ.getZ()) - â˜ƒ;
         LevelChunk â˜ƒxxx = â˜ƒ[â˜ƒx][â˜ƒxx];
         int â˜ƒxxxx = this.index(â˜ƒ);
         this.blockStates[â˜ƒxxxx] = â˜ƒxxx.getBlockState(â˜ƒ);
      }
   }

   protected final int index(BlockPos var1) {
      return this.index(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   protected int index(int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ - this.start.getX();
      int â˜ƒx = â˜ƒ - this.start.getY();
      int â˜ƒxx = â˜ƒ - this.start.getZ();
      return â˜ƒxx * this.xLength * this.yLength + â˜ƒx * this.xLength + â˜ƒ;
   }

   @Override
   public BlockState getBlockState(BlockPos var1) {
      return this.blockStates[this.index(â˜ƒ)];
   }

   @Override
   public FluidState getFluidState(BlockPos var1) {
      return this.blockStates[this.index(â˜ƒ)].getFluidState();
   }

   @Override
   public float getShade(Direction var1, boolean var2) {
      return this.level.getShade(â˜ƒ, â˜ƒ);
   }

   @Override
   public LevelLightEngine getLightEngine() {
      return this.level.getLightEngine();
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos var1) {
      return this.getBlockEntity(â˜ƒ, LevelChunk.EntityCreationType.IMMEDIATE);
   }

   @Nullable
   public BlockEntity getBlockEntity(BlockPos var1, LevelChunk.EntityCreationType var2) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX()) - this.centerX;
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ()) - this.centerZ;
      return this.chunks[â˜ƒ][â˜ƒx].getBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getBlockTint(BlockPos var1, ColorResolver var2) {
      return this.level.getBlockTint(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getMinBuildHeight() {
      return this.level.getMinBuildHeight();
   }

   @Override
   public int getHeight() {
      return this.level.getHeight();
   }
}
