package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.SingleBaseStoneSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class WorldCarver<C extends CarverConfiguration> {
   public static final WorldCarver<CaveCarverConfiguration> CAVE = register("cave", new CaveWorldCarver(CaveCarverConfiguration.CODEC));
   public static final WorldCarver<CaveCarverConfiguration> NETHER_CAVE = register("nether_cave", new NetherWorldCarver(CaveCarverConfiguration.CODEC));
   public static final WorldCarver<CanyonCarverConfiguration> CANYON = register("canyon", new CanyonWorldCarver(CanyonCarverConfiguration.CODEC));
   public static final WorldCarver<CanyonCarverConfiguration> UNDERWATER_CANYON = register(
      "underwater_canyon", new UnderwaterCanyonWorldCarver(CanyonCarverConfiguration.CODEC)
   );
   public static final WorldCarver<CaveCarverConfiguration> UNDERWATER_CAVE = register(
      "underwater_cave", new UnderwaterCaveWorldCarver(CaveCarverConfiguration.CODEC)
   );
   protected static final BaseStoneSource STONE_SOURCE = new SingleBaseStoneSource(Blocks.STONE.defaultBlockState());
   protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
   protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();
   protected static final FluidState WATER = Fluids.WATER.defaultFluidState();
   protected static final FluidState LAVA = Fluids.LAVA.defaultFluidState();
   protected Set<Block> replaceableBlocks = ImmutableSet.of(
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
      Blocks.PACKED_ICE,
      Blocks.DEEPSLATE,
      Blocks.TUFF,
      Blocks.GRANITE,
      Blocks.IRON_ORE,
      Blocks.DEEPSLATE_IRON_ORE,
      Blocks.RAW_IRON_BLOCK,
      Blocks.COPPER_ORE,
      Blocks.DEEPSLATE_COPPER_ORE,
      Blocks.RAW_COPPER_BLOCK
   );
   protected Set<Fluid> liquids = ImmutableSet.of(Fluids.WATER);
   private final Codec<ConfiguredWorldCarver<C>> configuredCodec;

   private static <C extends CarverConfiguration, F extends WorldCarver<C>> F register(String var0, F var1) {
      return Registry.register(Registry.CARVER, â˜ƒ, â˜ƒ);
   }

   public WorldCarver(Codec<C> var1) {
      this.configuredCodec = â˜ƒ.fieldOf("config").<ConfiguredWorldCarver<C>>xmap(this::configured, ConfiguredWorldCarver::config).codec();
   }

   public ConfiguredWorldCarver<C> configured(C var1) {
      return new ConfiguredWorldCarver<>(this, â˜ƒ);
   }

   public Codec<ConfiguredWorldCarver<C>> configuredCodec() {
      return this.configuredCodec;
   }

   public int getRange() {
      return 4;
   }

   protected boolean carveEllipsoid(
      CarvingContext var1,
      C var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      long var5,
      Aquifer var7,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      BitSet var18,
      WorldCarver.CarveSkipChecker var19
   ) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      int â˜ƒx = â˜ƒ.x;
      int â˜ƒxx = â˜ƒ.z;
      Random â˜ƒxxx = new Random(â˜ƒ + (long)â˜ƒx + (long)â˜ƒxx);
      double â˜ƒxxxx = (double)â˜ƒ.getMiddleBlockX();
      double â˜ƒxxxxx = (double)â˜ƒ.getMiddleBlockZ();
      double â˜ƒxxxxxx = 16.0 + â˜ƒ * 2.0;
      if (!(Math.abs(â˜ƒ - â˜ƒxxxx) > â˜ƒxxxxxx) && !(Math.abs(â˜ƒ - â˜ƒxxxxx) > â˜ƒxxxxxx)) {
         int â˜ƒxxxxxxx = â˜ƒ.getMinBlockX();
         int â˜ƒxxxxxxxx = â˜ƒ.getMinBlockZ();
         int â˜ƒxxxxxxxxx = Math.max(Mth.floor(â˜ƒ - â˜ƒ) - â˜ƒxxxxxxx - 1, 0);
         int â˜ƒxxxxxxxxxx = Math.min(Mth.floor(â˜ƒ + â˜ƒ) - â˜ƒxxxxxxx, 15);
         int â˜ƒxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒ - â˜ƒ) - 1, â˜ƒ.getMinGenY() + 1);
         int â˜ƒxxxxxxxxxxxx = Math.min(Mth.floor(â˜ƒ + â˜ƒ) + 1, â˜ƒ.getMinGenY() + â˜ƒ.getGenDepth() - 8);
         int â˜ƒxxxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒ - â˜ƒ) - â˜ƒxxxxxxxx - 1, 0);
         int â˜ƒxxxxxxxxxxxxxx = Math.min(Mth.floor(â˜ƒ + â˜ƒ) - â˜ƒxxxxxxxx, 15);
         if (!â˜ƒ.aquifersEnabled
            && this.hasDisallowedLiquid(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx)) {
            return false;
         } else {
            boolean â˜ƒxxxxxxx = false;
            BlockPos.MutableBlockPos â˜ƒxxxxxxxx = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos â˜ƒxxxxxxxxx = new BlockPos.MutableBlockPos();

            for(int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx; â˜ƒxxxxxxxxxx <= â˜ƒxxxxxxxxxx; ++â˜ƒxxxxxxxxxx) {
               int â˜ƒxxxxxxxxxxx = â˜ƒ.getBlockX(â˜ƒxxxxxxxxxx);
               double â˜ƒxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxx + 0.5 - â˜ƒ) / â˜ƒ;

               for(int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxx) {
                  int â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getBlockZ(â˜ƒxxxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxx + 0.5 - â˜ƒ) / â˜ƒ;
                  if (!(â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx >= 1.0)) {
                     MutableBoolean â˜ƒxxxxxxxxxxxxxxxx = new MutableBoolean(false);

                     for(int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxx > â˜ƒxxxxxxxxxxx; --â˜ƒxxxxxxxxxxxxxxxxx) {
                        double â˜ƒxxxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxxxxx - 0.5 - â˜ƒ) / â˜ƒ;
                        if (!â˜ƒ.shouldSkip(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx)) {
                           int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒ.getMinGenY();
                           int â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx | â˜ƒxxxxxxxxxxxxx << 4 | â˜ƒxxxxxxxxxxxxxxxxxxx << 8;
                           if (!â˜ƒ.get(â˜ƒxxxxxxxxxxxxxxxxxxxx) || isDebugEnabled(â˜ƒ)) {
                              â˜ƒ.set(â˜ƒxxxxxxxxxxxxxxxxxxxx);
                              â˜ƒxxxxxxxx.set(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx);
                              â˜ƒxxxxxxx |= this.carveBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }

            return â˜ƒxxxxxxx;
         }
      } else {
         return false;
      }
   }

   protected boolean carveBlock(
      CarvingContext var1,
      C var2,
      ChunkAccess var3,
      Function<BlockPos, Biome> var4,
      BitSet var5,
      Random var6,
      BlockPos.MutableBlockPos var7,
      BlockPos.MutableBlockPos var8,
      Aquifer var9,
      MutableBoolean var10
   ) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.setWithOffset(â˜ƒ, Direction.UP));
      if (â˜ƒ.is(Blocks.GRASS_BLOCK) || â˜ƒ.is(Blocks.MYCELIUM)) {
         â˜ƒ.setTrue();
      }

      if (!this.canReplaceBlock(â˜ƒ, â˜ƒx) && !isDebugEnabled(â˜ƒ)) {
         return false;
      } else {
         BlockState â˜ƒ = this.getCarveState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ == null) {
            return false;
         } else {
            â˜ƒ.setBlockState(â˜ƒ, â˜ƒ, false);
            if (â˜ƒ.isTrue()) {
               â˜ƒ.setWithOffset(â˜ƒ, Direction.DOWN);
               if (â˜ƒ.getBlockState(â˜ƒ).is(Blocks.DIRT)) {
                  â˜ƒ.setBlockState(â˜ƒ, ((Biome)â˜ƒ.apply(â˜ƒ)).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial(), false);
               }
            }

            return true;
         }
      }
   }

   @Nullable
   private BlockState getCarveState(CarvingContext var1, C var2, BlockPos var3, Aquifer var4) {
      if (â˜ƒ.getY() <= â˜ƒ.lavaLevel.resolveY(â˜ƒ)) {
         return LAVA.createLegacyBlock();
      } else if (!â˜ƒ.aquifersEnabled) {
         return isDebugEnabled(â˜ƒ) ? getDebugState(â˜ƒ, AIR) : AIR;
      } else {
         BlockState â˜ƒ = â˜ƒ.computeState(STONE_SOURCE, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), 0.0);
         if (â˜ƒ == Blocks.STONE.defaultBlockState()) {
            return isDebugEnabled(â˜ƒ) ? â˜ƒ.debugSettings.getBarrierState() : null;
         } else {
            return isDebugEnabled(â˜ƒ) ? getDebugState(â˜ƒ, â˜ƒ) : â˜ƒ;
         }
      }
   }

   private static BlockState getDebugState(CarverConfiguration var0, BlockState var1) {
      if (â˜ƒ.is(Blocks.AIR)) {
         return â˜ƒ.debugSettings.getAirState();
      } else if (â˜ƒ.is(Blocks.WATER)) {
         BlockState â˜ƒ = â˜ƒ.debugSettings.getWaterState();
         return â˜ƒ.hasProperty(BlockStateProperties.WATERLOGGED) ? â˜ƒ.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)) : â˜ƒ;
      } else {
         return â˜ƒ.is(Blocks.LAVA) ? â˜ƒ.debugSettings.getLavaState() : â˜ƒ;
      }
   }

   public abstract boolean carve(
      CarvingContext var1, C var2, ChunkAccess var3, Function<BlockPos, Biome> var4, Random var5, Aquifer var6, ChunkPos var7, BitSet var8
   );

   public abstract boolean isStartChunk(C var1, Random var2);

   protected boolean canReplaceBlock(BlockState var1) {
      return this.replaceableBlocks.contains(â˜ƒ.getBlock());
   }

   protected boolean canReplaceBlock(BlockState var1, BlockState var2) {
      return this.canReplaceBlock(â˜ƒ) || (â˜ƒ.is(Blocks.SAND) || â˜ƒ.is(Blocks.GRAVEL)) && !â˜ƒ.getFluidState().is(FluidTags.WATER);
   }

   protected boolean hasDisallowedLiquid(ChunkAccess var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      int â˜ƒx = â˜ƒ.getMinBlockX();
      int â˜ƒxx = â˜ƒ.getMinBlockZ();
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxx = â˜ƒ; â˜ƒxxxx <= â˜ƒ; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = â˜ƒ; â˜ƒxxxxx <= â˜ƒ; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = â˜ƒ - 1; â˜ƒxxxxxx <= â˜ƒ + 1; ++â˜ƒxxxxxx) {
               â˜ƒxxx.set(â˜ƒx + â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxx + â˜ƒxxxxx);
               if (this.liquids.contains(â˜ƒ.getFluidState(â˜ƒxxx).getType())) {
                  return true;
               }

               if (â˜ƒxxxxxx != â˜ƒ + 1 && !isEdge(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
                  â˜ƒxxxxxx = â˜ƒ;
               }
            }
         }
      }

      return false;
   }

   private static boolean isEdge(int var0, int var1, int var2, int var3, int var4, int var5) {
      return â˜ƒ == â˜ƒ || â˜ƒ == â˜ƒ || â˜ƒ == â˜ƒ || â˜ƒ == â˜ƒ;
   }

   protected static boolean canReach(ChunkPos var0, double var1, double var3, int var5, int var6, float var7) {
      double â˜ƒ = (double)â˜ƒ.getMiddleBlockX();
      double â˜ƒx = (double)â˜ƒ.getMiddleBlockZ();
      double â˜ƒxx = â˜ƒ - â˜ƒ;
      double â˜ƒxxx = â˜ƒ - â˜ƒx;
      double â˜ƒxxxx = (double)(â˜ƒ - â˜ƒ);
      double â˜ƒxxxxx = (double)(â˜ƒ + 2.0F + 16.0F);
      return â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx - â˜ƒxxxx * â˜ƒxxxx <= â˜ƒxxxxx * â˜ƒxxxxx;
   }

   private static boolean isDebugEnabled(CarverConfiguration var0) {
      return â˜ƒ.debugSettings.isDebugMode();
   }

   public interface CarveSkipChecker {
      boolean shouldSkip(CarvingContext var1, double var2, double var4, double var6, int var8);
   }
}
