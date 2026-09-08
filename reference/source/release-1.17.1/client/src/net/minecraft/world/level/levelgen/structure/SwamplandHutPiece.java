package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

public class SwamplandHutPiece extends ScatteredFeaturePiece {
   private boolean spawnedWitch;
   private boolean spawnedCat;

   public SwamplandHutPiece(Random var1, int var2, int var3) {
      super(StructurePieceType.SWAMPLAND_HUT, â˜ƒ, 64, â˜ƒ, 7, 7, 9, getRandomHorizontalDirection(â˜ƒ));
   }

   public SwamplandHutPiece(ServerLevel var1, CompoundTag var2) {
      super(StructurePieceType.SWAMPLAND_HUT, â˜ƒ);
      this.spawnedWitch = â˜ƒ.getBoolean("Witch");
      this.spawnedCat = â˜ƒ.getBoolean("Cat");
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
      â˜ƒ.putBoolean("Witch", this.spawnedWitch);
      â˜ƒ.putBoolean("Cat", this.spawnedCat);
   }

   @Override
   public boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   ) {
      if (!this.updateAverageGroundHeight(â˜ƒ, â˜ƒ, 0)) {
         return false;
      } else {
         this.generateBox(â˜ƒ, â˜ƒ, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
         this.generateBox(â˜ƒ, â˜ƒ, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
         this.placeBlock(â˜ƒ, Blocks.OAK_FENCE.defaultBlockState(), 2, 3, 2, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.OAK_FENCE.defaultBlockState(), 3, 3, 7, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 1, 3, 4, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 5, 3, 4, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 5, 3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.POTTED_RED_MUSHROOM.defaultBlockState(), 1, 3, 5, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CRAFTING_TABLE.defaultBlockState(), 3, 2, 6, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.CAULDRON.defaultBlockState(), 4, 2, 6, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.OAK_FENCE.defaultBlockState(), 1, 2, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, Blocks.OAK_FENCE.defaultBlockState(), 5, 2, 1, â˜ƒ);
         BlockState â˜ƒ = Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
         BlockState â˜ƒx = Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
         BlockState â˜ƒxx = Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
         BlockState â˜ƒxxx = Blocks.SPRUCE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 4, 1, 6, 4, 1, â˜ƒ, â˜ƒ, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 4, 2, 0, 4, 7, â˜ƒx, â˜ƒx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 6, 4, 2, 6, 4, 7, â˜ƒxx, â˜ƒxx, false);
         this.generateBox(â˜ƒ, â˜ƒ, 0, 4, 8, 6, 4, 8, â˜ƒxxx, â˜ƒxxx, false);
         this.placeBlock(â˜ƒ, â˜ƒ.setValue(StairBlock.SHAPE, StairsShape.OUTER_RIGHT), 0, 4, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒ.setValue(StairBlock.SHAPE, StairsShape.OUTER_LEFT), 6, 4, 1, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx.setValue(StairBlock.SHAPE, StairsShape.OUTER_LEFT), 0, 4, 8, â˜ƒ);
         this.placeBlock(â˜ƒ, â˜ƒxxx.setValue(StairBlock.SHAPE, StairsShape.OUTER_RIGHT), 6, 4, 8, â˜ƒ);

         for(int â˜ƒxxxx = 2; â˜ƒxxxx <= 7; â˜ƒxxxx += 5) {
            for(int â˜ƒxxxxx = 1; â˜ƒxxxxx <= 5; â˜ƒxxxxx += 4) {
               this.fillColumnDown(â˜ƒ, Blocks.OAK_LOG.defaultBlockState(), â˜ƒxxxxx, -1, â˜ƒxxxx, â˜ƒ);
            }
         }

         if (!this.spawnedWitch) {
            BlockPos â˜ƒxxxx = this.getWorldPos(2, 2, 5);
            if (â˜ƒ.isInside(â˜ƒxxxx)) {
               this.spawnedWitch = true;
               Witch â˜ƒxxxxx = EntityType.WITCH.create(â˜ƒ.getLevel());
               â˜ƒxxxxx.setPersistenceRequired();
               â˜ƒxxxxx.moveTo((double)â˜ƒxxxx.getX() + 0.5, (double)â˜ƒxxxx.getY(), (double)â˜ƒxxxx.getZ() + 0.5, 0.0F, 0.0F);
               â˜ƒxxxxx.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒxxxx), MobSpawnType.STRUCTURE, null, null);
               â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxxxx);
            }
         }

         this.spawnCat(â˜ƒ, â˜ƒ);
         return true;
      }
   }

   private void spawnCat(ServerLevelAccessor var1, BoundingBox var2) {
      if (!this.spawnedCat) {
         BlockPos â˜ƒ = this.getWorldPos(2, 2, 5);
         if (â˜ƒ.isInside(â˜ƒ)) {
            this.spawnedCat = true;
            Cat â˜ƒx = EntityType.CAT.create(â˜ƒ.getLevel());
            â˜ƒx.setPersistenceRequired();
            â˜ƒx.moveTo((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, 0.0F, 0.0F);
            â˜ƒx.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ), MobSpawnType.STRUCTURE, null, null);
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒx);
         }
      }
   }
}
