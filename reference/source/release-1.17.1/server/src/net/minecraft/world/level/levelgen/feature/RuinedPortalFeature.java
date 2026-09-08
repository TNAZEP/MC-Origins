package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.RuinedPortalConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class RuinedPortalFeature extends StructureFeature<RuinedPortalConfiguration> {
   static final String[] STRUCTURE_LOCATION_PORTALS = new String[]{
      "ruined_portal/portal_1",
      "ruined_portal/portal_2",
      "ruined_portal/portal_3",
      "ruined_portal/portal_4",
      "ruined_portal/portal_5",
      "ruined_portal/portal_6",
      "ruined_portal/portal_7",
      "ruined_portal/portal_8",
      "ruined_portal/portal_9",
      "ruined_portal/portal_10"
   };
   static final String[] STRUCTURE_LOCATION_GIANT_PORTALS = new String[]{
      "ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"
   };
   private static final float PROBABILITY_OF_GIANT_PORTAL = 0.05F;
   private static final float PROBABILITY_OF_AIR_POCKET = 0.5F;
   private static final float PROBABILITY_OF_UNDERGROUND = 0.5F;
   private static final float UNDERWATER_MOSSINESS = 0.8F;
   private static final float JUNGLE_MOSSINESS = 0.8F;
   private static final float SWAMP_MOSSINESS = 0.5F;
   private static final int MIN_Y = 15;

   public RuinedPortalFeature(Codec<RuinedPortalConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public StructureFeature.StructureStartFactory<RuinedPortalConfiguration> getStartFactory() {
      return RuinedPortalFeature.FeatureStart::new;
   }

   static boolean isCold(BlockPos var0, Biome var1) {
      return â˜ƒ.getTemperature(â˜ƒ) < 0.15F;
   }

   static int findSuitableY(
      Random var0, ChunkGenerator var1, RuinedPortalPiece.VerticalPlacement var2, boolean var3, int var4, int var5, BoundingBox var6, LevelHeightAccessor var7
   ) {
      int â˜ƒ;
      if (â˜ƒ == RuinedPortalPiece.VerticalPlacement.IN_NETHER) {
         if (â˜ƒ) {
            â˜ƒ = Mth.randomBetweenInclusive(â˜ƒ, 32, 100);
         } else if (â˜ƒ.nextFloat() < 0.5F) {
            â˜ƒ = Mth.randomBetweenInclusive(â˜ƒ, 27, 29);
         } else {
            â˜ƒ = Mth.randomBetweenInclusive(â˜ƒ, 29, 100);
         }
      } else if (â˜ƒ == RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN) {
         int â˜ƒ = â˜ƒ - â˜ƒ;
         â˜ƒ = getRandomWithinInterval(â˜ƒ, 70, â˜ƒ);
      } else if (â˜ƒ == RuinedPortalPiece.VerticalPlacement.UNDERGROUND) {
         int â˜ƒ = â˜ƒ - â˜ƒ;
         â˜ƒ = getRandomWithinInterval(â˜ƒ, 15, â˜ƒ);
      } else if (â˜ƒ == RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED) {
         â˜ƒ = â˜ƒ - â˜ƒ + Mth.randomBetweenInclusive(â˜ƒ, 2, 8);
      } else {
         â˜ƒ = â˜ƒ;
      }

      List<BlockPos> â˜ƒ = ImmutableList.of(
         new BlockPos(â˜ƒ.minX(), 0, â˜ƒ.minZ()),
         new BlockPos(â˜ƒ.maxX(), 0, â˜ƒ.minZ()),
         new BlockPos(â˜ƒ.minX(), 0, â˜ƒ.maxZ()),
         new BlockPos(â˜ƒ.maxX(), 0, â˜ƒ.maxZ())
      );
      List<NoiseColumn> â˜ƒx = (List)â˜ƒ.stream().map(var2x -> â˜ƒ.getBaseColumn(var2x.getX(), var2x.getZ(), â˜ƒ)).collect(Collectors.toList());
      Heightmap.Types â˜ƒxx = â˜ƒ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      int â˜ƒ;
      for(â˜ƒ = â˜ƒ; â˜ƒ > 15; --â˜ƒ) {
         int â˜ƒxxxx = 0;
         â˜ƒxxx.set(0, â˜ƒ, 0);

         for(NoiseColumn â˜ƒxxxxx : â˜ƒx) {
            BlockState â˜ƒxxxxxx = â˜ƒxxxxx.getBlockState(â˜ƒxxx);
            if (â˜ƒxx.isOpaque().test(â˜ƒxxxxxx)) {
               if (++â˜ƒxxxx == 3) {
                  return â˜ƒ;
               }
            }
         }
      }

      return â˜ƒ;
   }

   private static int getRandomWithinInterval(Random var0, int var1, int var2) {
      return â˜ƒ < â˜ƒ ? Mth.randomBetweenInclusive(â˜ƒ, â˜ƒ, â˜ƒ) : â˜ƒ;
   }

   public static class FeatureStart extends StructureStart<RuinedPortalConfiguration> {
      protected FeatureStart(StructureFeature<RuinedPortalConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, RuinedPortalConfiguration var6, LevelHeightAccessor var7
      ) {
         RuinedPortalPiece.Properties â˜ƒx = new RuinedPortalPiece.Properties();
         RuinedPortalPiece.VerticalPlacement â˜ƒ;
         if (â˜ƒ.portalType == RuinedPortalFeature.Type.DESERT) {
            â˜ƒ = RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED;
            â˜ƒx.airPocket = false;
            â˜ƒx.mossiness = 0.0F;
         } else if (â˜ƒ.portalType == RuinedPortalFeature.Type.JUNGLE) {
            â˜ƒ = RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE;
            â˜ƒx.airPocket = this.random.nextFloat() < 0.5F;
            â˜ƒx.mossiness = 0.8F;
            â˜ƒx.overgrown = true;
            â˜ƒx.vines = true;
         } else if (â˜ƒ.portalType == RuinedPortalFeature.Type.SWAMP) {
            â˜ƒ = RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR;
            â˜ƒx.airPocket = false;
            â˜ƒx.mossiness = 0.5F;
            â˜ƒx.vines = true;
         } else if (â˜ƒ.portalType == RuinedPortalFeature.Type.MOUNTAIN) {
            boolean â˜ƒ = this.random.nextFloat() < 0.5F;
            â˜ƒ = â˜ƒ ? RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN : RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE;
            â˜ƒx.airPocket = â˜ƒ || this.random.nextFloat() < 0.5F;
         } else if (â˜ƒ.portalType == RuinedPortalFeature.Type.OCEAN) {
            â˜ƒ = RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR;
            â˜ƒx.airPocket = false;
            â˜ƒx.mossiness = 0.8F;
         } else if (â˜ƒ.portalType == RuinedPortalFeature.Type.NETHER) {
            â˜ƒ = RuinedPortalPiece.VerticalPlacement.IN_NETHER;
            â˜ƒx.airPocket = this.random.nextFloat() < 0.5F;
            â˜ƒx.mossiness = 0.0F;
            â˜ƒx.replaceWithBlackstone = true;
         } else {
            boolean â˜ƒ = this.random.nextFloat() < 0.5F;
            â˜ƒ = â˜ƒ ? RuinedPortalPiece.VerticalPlacement.UNDERGROUND : RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE;
            â˜ƒx.airPocket = â˜ƒ || this.random.nextFloat() < 0.5F;
         }

         ResourceLocation â˜ƒ;
         if (this.random.nextFloat() < 0.05F) {
            â˜ƒ = new ResourceLocation(
               RuinedPortalFeature.STRUCTURE_LOCATION_GIANT_PORTALS[this.random.nextInt(RuinedPortalFeature.STRUCTURE_LOCATION_GIANT_PORTALS.length)]
            );
         } else {
            â˜ƒ = new ResourceLocation(
               RuinedPortalFeature.STRUCTURE_LOCATION_PORTALS[this.random.nextInt(RuinedPortalFeature.STRUCTURE_LOCATION_PORTALS.length)]
            );
         }

         StructureTemplate â˜ƒ = â˜ƒ.getOrCreate(â˜ƒ);
         Rotation â˜ƒx = Util.getRandom((Rotation[])Rotation.values(), this.random);
         Mirror â˜ƒxx = this.random.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
         BlockPos â˜ƒxxx = new BlockPos(â˜ƒ.getSize().getX() / 2, 0, â˜ƒ.getSize().getZ() / 2);
         BlockPos â˜ƒxxxx = â˜ƒ.getWorldPosition();
         BoundingBox â˜ƒxxxxx = â˜ƒ.getBoundingBox(â˜ƒxxxx, â˜ƒx, â˜ƒxxx, â˜ƒxx);
         BlockPos â˜ƒxxxxxx = â˜ƒxxxxx.getCenter();
         int â˜ƒxxxxxxx = â˜ƒxxxxxx.getX();
         int â˜ƒxxxxxxxx = â˜ƒxxxxxx.getZ();
         int â˜ƒxxxxxxxxx = â˜ƒ.getBaseHeight(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, RuinedPortalPiece.getHeightMapType(â˜ƒ), â˜ƒ) - 1;
         int â˜ƒxxxxxxxxxx = RuinedPortalFeature.findSuitableY(this.random, â˜ƒ, â˜ƒ, â˜ƒx.airPocket, â˜ƒxxxxxxxxx, â˜ƒxxxxx.getYSpan(), â˜ƒxxxxx, â˜ƒ);
         BlockPos â˜ƒxxxxxxxxxxx = new BlockPos(â˜ƒxxxx.getX(), â˜ƒxxxxxxxxxx, â˜ƒxxxx.getZ());
         if (â˜ƒ.portalType == RuinedPortalFeature.Type.MOUNTAIN
            || â˜ƒ.portalType == RuinedPortalFeature.Type.OCEAN
            || â˜ƒ.portalType == RuinedPortalFeature.Type.STANDARD) {
            â˜ƒx.cold = RuinedPortalFeature.isCold(â˜ƒxxxxxxxxxxx, â˜ƒ);
         }

         this.addPiece(new RuinedPortalPiece(â˜ƒ, â˜ƒxxxxxxxxxxx, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx));
      }
   }

   public static enum Type implements StringRepresentable {
      STANDARD("standard"),
      DESERT("desert"),
      JUNGLE("jungle"),
      SWAMP("swamp"),
      MOUNTAIN("mountain"),
      OCEAN("ocean"),
      NETHER("nether");

      public static final Codec<RuinedPortalFeature.Type> CODEC = StringRepresentable.fromEnum(
         RuinedPortalFeature.Type::values, RuinedPortalFeature.Type::byName
      );
      private static final Map<String, RuinedPortalFeature.Type> BY_NAME = (Map<String, RuinedPortalFeature.Type>)Arrays.stream(values())
         .collect(Collectors.toMap(RuinedPortalFeature.Type::getName, var0 -> var0));
      private final String name;

      private Type(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static RuinedPortalFeature.Type byName(String var0) {
         return (RuinedPortalFeature.Type)BY_NAME.get(â˜ƒ);
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }
   }
}
