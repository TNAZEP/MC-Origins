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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class BuriedTreasurePieces {
   public static class BuriedTreasurePiece extends StructurePiece {
      public BuriedTreasurePiece(BlockPos var1) {
         super(StructurePieceType.BURIED_TREASURE_PIECE, 0, new BoundingBox(â˜ƒ));
      }

      public BuriedTreasurePiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.BURIED_TREASURE_PIECE, â˜ƒ);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         int â˜ƒ = â˜ƒ.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.boundingBox.minX(), this.boundingBox.minZ());
         BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos(this.boundingBox.minX(), â˜ƒ, this.boundingBox.minZ());

         while(â˜ƒx.getY() > â˜ƒ.getMinBuildHeight()) {
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx.below());
            if (â˜ƒxxx == Blocks.SANDSTONE.defaultBlockState()
               || â˜ƒxxx == Blocks.STONE.defaultBlockState()
               || â˜ƒxxx == Blocks.ANDESITE.defaultBlockState()
               || â˜ƒxxx == Blocks.GRANITE.defaultBlockState()
               || â˜ƒxxx == Blocks.DIORITE.defaultBlockState()) {
               BlockState â˜ƒxxxx = !â˜ƒxx.isAir() && !this.isLiquid(â˜ƒxx) ? â˜ƒxx : Blocks.SAND.defaultBlockState();

               for(Direction â˜ƒxxxxx : Direction.values()) {
                  BlockPos â˜ƒxxxxxx = â˜ƒx.relative(â˜ƒxxxxx);
                  BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
                  if (â˜ƒxxxxxxx.isAir() || this.isLiquid(â˜ƒxxxxxxx)) {
                     BlockPos â˜ƒxxxxxxxx = â˜ƒxxxxxx.below();
                     BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);
                     if ((â˜ƒxxxxxxxxx.isAir() || this.isLiquid(â˜ƒxxxxxxxxx)) && â˜ƒxxxxx != Direction.UP) {
                        â˜ƒ.setBlock(â˜ƒxxxxxx, â˜ƒxxx, 3);
                     } else {
                        â˜ƒ.setBlock(â˜ƒxxxxxx, â˜ƒxxxx, 3);
                     }
                  }
               }

               this.boundingBox = new BoundingBox(â˜ƒx);
               return this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, BuiltInLootTables.BURIED_TREASURE, null);
            }

            â˜ƒx.move(0, -1, 0);
         }

         return false;
      }

      private boolean isLiquid(BlockState var1) {
         return â˜ƒ == Blocks.WATER.defaultBlockState() || â˜ƒ == Blocks.LAVA.defaultBlockState();
      }
   }
}
