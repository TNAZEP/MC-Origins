package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class DesertPyramidPiece extends ScatteredFeaturePiece {
   private final boolean[] hasPlacedChest = new boolean[4];

   public DesertPyramidPiece(Random var1, int var2, int var3) {
      super(StructurePieceType.DESERT_PYRAMID_PIECE, â˜ƒ, 64, â˜ƒ, 21, 15, 21, getRandomHorizontalDirection(â˜ƒ));
   }

   public DesertPyramidPiece(ServerLevel var1, CompoundTag var2) {
      super(StructurePieceType.DESERT_PYRAMID_PIECE, â˜ƒ);
      this.hasPlacedChest[0] = â˜ƒ.getBoolean("hasPlacedChest0");
      this.hasPlacedChest[1] = â˜ƒ.getBoolean("hasPlacedChest1");
      this.hasPlacedChest[2] = â˜ƒ.getBoolean("hasPlacedChest2");
      this.hasPlacedChest[3] = â˜ƒ.getBoolean("hasPlacedChest3");
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
      â˜ƒ.putBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
      â˜ƒ.putBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
      â˜ƒ.putBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
      â˜ƒ.putBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
   }

   @Override
   public boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   ) {
      this.generateBox(â˜ƒ, â˜ƒ, 0, -4, 0, this.width - 1, 0, this.depth - 1, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);

      for(int â˜ƒ = 1; â˜ƒ <= 9; ++â˜ƒ) {
         this.generateBox(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            this.width - 1 - â˜ƒ,
            â˜ƒ,
            this.depth - 1 - â˜ƒ,
            Blocks.SANDSTONE.defaultBlockState(),
            Blocks.SANDSTONE.defaultBlockState(),
            false
         );
         this.generateBox(
            â˜ƒ,
            â˜ƒ,
            â˜ƒ + 1,
            â˜ƒ,
            â˜ƒ + 1,
            this.width - 2 - â˜ƒ,
            â˜ƒ,
            this.depth - 2 - â˜ƒ,
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            false
         );
      }

      for(int â˜ƒ = 0; â˜ƒ < this.width; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < this.depth; ++â˜ƒx) {
            int â˜ƒxx = -5;
            this.fillColumnDown(â˜ƒ, Blocks.SANDSTONE.defaultBlockState(), â˜ƒ, -5, â˜ƒx, â˜ƒ);
         }
      }

      BlockState â˜ƒ = Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
      BlockState â˜ƒx = Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
      BlockState â˜ƒxx = Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
      BlockState â˜ƒxxx = Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
      this.generateBox(â˜ƒ, â˜ƒ, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, â˜ƒ, 2, 10, 0, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒx, 2, 10, 4, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒxx, 0, 10, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒxxx, 4, 10, 2, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 5, 0, 0, this.width - 1, 9, 4, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(
         â˜ƒ, â˜ƒ, this.width - 4, 10, 1, this.width - 2, 10, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false
      );
      this.placeBlock(â˜ƒ, â˜ƒ, this.width - 3, 10, 0, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒx, this.width - 3, 10, 4, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒxx, this.width - 5, 10, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒxxx, this.width - 1, 10, 2, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 9, 1, 0, 11, 3, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 9, 1, 1, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 9, 2, 1, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 9, 3, 1, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 10, 3, 1, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 11, 3, 1, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 11, 2, 1, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 11, 1, 1, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 4, 1, 2, 8, 2, 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 12, 1, 2, 16, 2, 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 5, 4, 5, this.width - 6, 4, this.depth - 6, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 9, 4, 9, 11, 4, 11, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 8, 1, 8, 8, 3, 8, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 12, 1, 8, 12, 3, 8, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 8, 1, 12, 8, 3, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 12, 1, 12, 12, 3, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 5, 1, 5, this.width - 2, 4, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 7, 7, 9, this.width - 7, 7, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 5, 5, 9, 5, 7, 11, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(
         â˜ƒ, â˜ƒ, this.width - 6, 5, 9, this.width - 6, 7, 11, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false
      );
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 5, 5, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 5, 6, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 6, 6, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), this.width - 6, 5, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), this.width - 6, 6, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), this.width - 7, 6, 10, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 2, 4, 4, 2, 6, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 3, 4, 4, this.width - 3, 6, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, â˜ƒ, 2, 4, 5, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒ, 2, 3, 4, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒ, this.width - 3, 4, 5, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒ, this.width - 3, 3, 4, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 3, 1, 3, this.width - 2, 2, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, Blocks.SANDSTONE.defaultBlockState(), 1, 1, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.SANDSTONE.defaultBlockState(), this.width - 2, 1, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.SANDSTONE_SLAB.defaultBlockState(), 1, 2, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.SANDSTONE_SLAB.defaultBlockState(), this.width - 2, 2, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒxxx, 2, 1, 2, â˜ƒ);
      this.placeBlock(â˜ƒ, â˜ƒxx, this.width - 3, 1, 2, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 4, 3, 5, 4, 3, 17, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 5, 3, 5, this.width - 5, 3, 17, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 3, 1, 5, 4, 2, 16, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, this.width - 6, 1, 5, this.width - 5, 2, 16, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);

      for(int â˜ƒxxxx = 5; â˜ƒxxxx <= 17; â˜ƒxxxx += 2) {
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 4, 1, â˜ƒxxxx, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 4, 2, â˜ƒxxxx, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), this.width - 5, 1, â˜ƒxxxx, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), this.width - 5, 2, â˜ƒxxxx, â˜ƒ);
      }

      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 7, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 8, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 0, 9, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 0, 9, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 8, 0, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 12, 0, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 7, 0, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 13, 0, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 0, 11, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 0, 11, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 12, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 13, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.BLUE_TERRACOTTA.defaultBlockState(), 10, 0, 10, â˜ƒ);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx <= this.width - 1; â˜ƒxxxx += this.width - 1) {
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 2, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 2, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 2, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 3, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 3, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 3, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 4, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 4, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 4, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 5, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 5, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 5, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 6, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 6, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 6, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 7, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 7, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 7, 3, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 8, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 8, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 8, 3, â˜ƒ);
      }

      for(int â˜ƒxxxx = 2; â˜ƒxxxx <= this.width - 3; â˜ƒxxxx += this.width - 3 - 2) {
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx - 1, 2, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 2, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx + 1, 2, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx - 1, 3, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 3, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx + 1, 3, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx - 1, 4, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 4, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx + 1, 4, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx - 1, 5, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 5, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx + 1, 5, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx - 1, 6, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 6, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx + 1, 6, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx - 1, 7, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx, 7, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), â˜ƒxxxx + 1, 7, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx - 1, 8, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx, 8, 0, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), â˜ƒxxxx + 1, 8, 0, â˜ƒ);
      }

      this.generateBox(â˜ƒ, â˜ƒ, 8, 4, 0, 12, 6, 0, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 8, 6, 0, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 12, 6, 0, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 5, 0, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 10, 5, 0, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 5, 0, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 8, -14, 8, 12, -11, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 8, -10, 8, 12, -10, 12, Blocks.CHISELED_SANDSTONE.defaultBlockState(), Blocks.CHISELED_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 8, -9, 8, 12, -9, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
      this.generateBox(â˜ƒ, â˜ƒ, 9, -11, 9, 11, -1, 11, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), 10, -11, 10, â˜ƒ);
      this.generateBox(â˜ƒ, â˜ƒ, 9, -13, 9, 11, -13, 11, Blocks.TNT.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 8, -11, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 8, -10, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 7, -10, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 7, -11, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 12, -11, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 12, -10, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 13, -10, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 13, -11, 10, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 10, -11, 8, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 10, -10, 8, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 10, -10, 7, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 10, -11, 7, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 10, -11, 12, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 10, -10, 12, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 10, -10, 13, â˜ƒ);
      this.placeBlock(â˜ƒ, Blocks.CUT_SANDSTONE.defaultBlockState(), 10, -11, 13, â˜ƒ);

      for(Direction â˜ƒxxxx : Direction.Plane.HORIZONTAL) {
         if (!this.hasPlacedChest[â˜ƒxxxx.get2DDataValue()]) {
            int â˜ƒxxxxx = â˜ƒxxxx.getStepX() * 2;
            int â˜ƒxxxxxx = â˜ƒxxxx.getStepZ() * 2;
            this.hasPlacedChest[â˜ƒxxxx.get2DDataValue()] = this.createChest(
               â˜ƒ, â˜ƒ, â˜ƒ, 10 + â˜ƒxxxxx, -11, 10 + â˜ƒxxxxxx, BuiltInLootTables.DESERT_PYRAMID
            );
         }
      }

      return true;
   }
}
