package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class UnderwaterCaveWorldCarver extends CaveWorldCarver {
   public UnderwaterCaveWorldCarver(Codec<CaveCarverConfiguration> var1) {
      super(â˜ƒ);
      this.replaceableBlocks = ImmutableSet.of(
         Blocks.STONE,
         Blocks.GRANITE,
         Blocks.DIORITE,
         Blocks.ANDESITE,
         Blocks.DIRT,
         Blocks.COARSE_DIRT,
         Blocks.PODZOL,
         Blocks.GRASS_BLOCK,
         Blocks.TERRACOTTA,
         Blocks.WHITE_TERRACOTTA,
         Blocks.ORANGE_TERRACOTTA,
         Blocks.MAGENTA_TERRACOTTA,
         Blocks.LIGHT_BLUE_TERRACOTTA,
         Blocks.YELLOW_TERRACOTTA,
         Blocks.LIME_TERRACOTTA,
         Blocks.PINK_TERRACOTTA,
         Blocks.GRAY_TERRACOTTA,
         Blocks.LIGHT_GRAY_TERRACOTTA,
         Blocks.CYAN_TERRACOTTA,
         Blocks.PURPLE_TERRACOTTA,
         Blocks.BLUE_TERRACOTTA,
         Blocks.BROWN_TERRACOTTA,
         Blocks.GREEN_TERRACOTTA,
         Blocks.RED_TERRACOTTA,
         Blocks.BLACK_TERRACOTTA,
         Blocks.SANDSTONE,
         Blocks.RED_SANDSTONE,
         Blocks.MYCELIUM,
         Blocks.SNOW,
         Blocks.SAND,
         Blocks.GRAVEL,
         Blocks.WATER,
         Blocks.LAVA,
         Blocks.OBSIDIAN,
         Blocks.PACKED_ICE
      );
   }

   @Override
   protected boolean hasDisallowedLiquid(ChunkAccess var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      return false;
   }

   protected boolean carveBlock(
      CarvingContext var1,
      CaveCarverConfiguration var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      BitSet var5,
      Random var6,
      BlockPos.MutableBlockPos var7,
      BlockPos.MutableBlockPos var8,
      Aquifer var9,
      MutableBoolean var10
   ) {
      return carveBlock(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected static boolean carveBlock(
      WorldCarver<?> var0, ChunkAccess var1, Random var2, BlockPos.MutableBlockPos var3, BlockPos.MutableBlockPos var4, Aquifer var5
   ) {
      if (â˜ƒ.computeState(WorldCarver.STONE_SOURCE, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), Double.NEGATIVE_INFINITY).isAir()) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         if (!â˜ƒ.canReplaceBlock(â˜ƒ)) {
            return false;
         } else if (â˜ƒ.getY() == 10) {
            float â˜ƒ = â˜ƒ.nextFloat();
            if ((double)â˜ƒ < 0.25) {
               â˜ƒ.setBlockState(â˜ƒ, Blocks.MAGMA_BLOCK.defaultBlockState(), false);
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, Blocks.MAGMA_BLOCK, 0);
            } else {
               â˜ƒ.setBlockState(â˜ƒ, Blocks.OBSIDIAN.defaultBlockState(), false);
            }

            return true;
         } else if (â˜ƒ.getY() < 10) {
            â˜ƒ.setBlockState(â˜ƒ, Blocks.LAVA.defaultBlockState(), false);
            return false;
         } else {
            â˜ƒ.setBlockState(â˜ƒ, WATER.createLegacyBlock(), false);
            int â˜ƒ = â˜ƒ.getPos().x;
            int â˜ƒx = â˜ƒ.getPos().z;

            for(Direction â˜ƒxx : LiquidBlock.POSSIBLE_FLOW_DIRECTIONS) {
               â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxx);
               if (SectionPos.blockToSectionCoord(â˜ƒ.getX()) != â˜ƒ || SectionPos.blockToSectionCoord(â˜ƒ.getZ()) != â˜ƒx || â˜ƒ.getBlockState(â˜ƒ).isAir()) {
                  â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, WATER.getType(), 0);
                  break;
               }
            }

            return true;
         }
      }
   }
}
