package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NetherWorldCarver extends CaveWorldCarver {
   public NetherWorldCarver(Codec<CaveCarverConfiguration> var1) {
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
         Blocks.NETHERRACK,
         Blocks.SOUL_SAND,
         Blocks.SOUL_SOIL,
         Blocks.CRIMSON_NYLIUM,
         Blocks.WARPED_NYLIUM,
         Blocks.NETHER_WART_BLOCK,
         Blocks.WARPED_WART_BLOCK,
         Blocks.BASALT,
         Blocks.BLACKSTONE
      );
      this.liquids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
   }

   @Override
   protected int getCaveBound() {
      return 10;
   }

   @Override
   protected float getThickness(Random var1) {
      return (â˜ƒ.nextFloat() * 2.0F + â˜ƒ.nextFloat()) * 2.0F;
   }

   @Override
   protected double getYScale() {
      return 5.0;
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
      if (this.canReplaceBlock(â˜ƒ.getBlockState(â˜ƒ))) {
         BlockState â˜ƒ;
         if (â˜ƒ.getY() <= â˜ƒ.getMinGenY() + 31) {
            â˜ƒ = LAVA.createLegacyBlock();
         } else {
            â˜ƒ = CAVE_AIR;
         }

         â˜ƒ.setBlockState(â˜ƒ, â˜ƒ, false);
         return true;
      } else {
         return false;
      }
   }
}
