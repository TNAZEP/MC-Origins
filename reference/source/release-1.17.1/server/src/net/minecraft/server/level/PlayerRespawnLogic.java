package net.minecraft.server.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class PlayerRespawnLogic {
   @Nullable
   protected static BlockPos getOverworldRespawnPos(ServerLevel var0, int var1, int var2, boolean var3) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos(â˜ƒ, â˜ƒ.getMinBuildHeight(), â˜ƒ);
      Biome â˜ƒx = â˜ƒ.getBiome(â˜ƒ);
      boolean â˜ƒxx = â˜ƒ.dimensionType().hasCeiling();
      BlockState â˜ƒxxx = â˜ƒx.getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
      if (â˜ƒ && !â˜ƒxxx.is(BlockTags.VALID_SPAWN)) {
         return null;
      } else {
         LevelChunk â˜ƒ = â˜ƒ.getChunk(SectionPos.blockToSectionCoord(â˜ƒ), SectionPos.blockToSectionCoord(â˜ƒ));
         int â˜ƒx = â˜ƒxx ? â˜ƒ.getChunkSource().getGenerator().getSpawnHeight(â˜ƒ) : â˜ƒ.getHeight(Heightmap.Types.MOTION_BLOCKING, â˜ƒ & 15, â˜ƒ & 15);
         if (â˜ƒx < â˜ƒ.getMinBuildHeight()) {
            return null;
         } else {
            int â˜ƒ = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒ & 15, â˜ƒ & 15);
            if (â˜ƒ <= â˜ƒx && â˜ƒ > â˜ƒ.getHeight(Heightmap.Types.OCEAN_FLOOR, â˜ƒ & 15, â˜ƒ & 15)) {
               return null;
            } else {
               for(int â˜ƒ = â˜ƒx + 1; â˜ƒ >= â˜ƒ.getMinBuildHeight(); --â˜ƒ) {
                  â˜ƒ.set(â˜ƒ, â˜ƒ, â˜ƒ);
                  BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
                  if (!â˜ƒx.getFluidState().isEmpty()) {
                     break;
                  }

                  if (â˜ƒx.equals(â˜ƒxxx)) {
                     return â˜ƒ.above().immutable();
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   public static BlockPos getSpawnPosInChunk(ServerLevel var0, ChunkPos var1, boolean var2) {
      for(int â˜ƒ = â˜ƒ.getMinBlockX(); â˜ƒ <= â˜ƒ.getMaxBlockX(); ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ.getMinBlockZ(); â˜ƒx <= â˜ƒ.getMaxBlockZ(); ++â˜ƒx) {
            BlockPos â˜ƒxx = getOverworldRespawnPos(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
            if (â˜ƒxx != null) {
               return â˜ƒxx;
            }
         }
      }

      return null;
   }
}
