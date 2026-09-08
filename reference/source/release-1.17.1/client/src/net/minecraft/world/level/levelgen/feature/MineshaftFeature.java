package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.MineShaftPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class MineshaftFeature extends StructureFeature<MineshaftConfiguration> {
   public MineshaftFeature(Codec<MineshaftConfiguration> var1) {
      super(â˜ƒ);
   }

   protected boolean isFeatureChunk(
      ChunkGenerator var1,
      BiomeSource var2,
      long var3,
      WorldgenRandom var5,
      ChunkPos var6,
      Biome var7,
      ChunkPos var8,
      MineshaftConfiguration var9,
      LevelHeightAccessor var10
   ) {
      â˜ƒ.setLargeFeatureSeed(â˜ƒ, â˜ƒ.x, â˜ƒ.z);
      double â˜ƒ = (double)â˜ƒ.probability;
      return â˜ƒ.nextDouble() < â˜ƒ;
   }

   @Override
   public StructureFeature.StructureStartFactory<MineshaftConfiguration> getStartFactory() {
      return MineshaftFeature.MineShaftStart::new;
   }

   public static class MineShaftStart extends StructureStart<MineshaftConfiguration> {
      public MineShaftStart(StructureFeature<MineshaftConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, MineshaftConfiguration var6, LevelHeightAccessor var7
      ) {
         MineShaftPieces.MineShaftRoom â˜ƒ = new MineShaftPieces.MineShaftRoom(0, this.random, â˜ƒ.getBlockX(2), â˜ƒ.getBlockZ(2), â˜ƒ.type);
         this.addPiece(â˜ƒ);
         â˜ƒ.addChildren(â˜ƒ, this, this.random);
         if (â˜ƒ.type == MineshaftFeature.Type.MESA) {
            int â˜ƒx = -5;
            BoundingBox â˜ƒxx = this.getBoundingBox();
            int â˜ƒxxx = â˜ƒ.getSeaLevel() - â˜ƒxx.maxY() + â˜ƒxx.getYSpan() / 2 - -5;
            this.offsetPiecesVertically(â˜ƒxxx);
         } else {
            this.moveBelowSeaLevel(â˜ƒ.getSeaLevel(), â˜ƒ.getMinY(), this.random, 10);
         }
      }
   }

   public static enum Type implements StringRepresentable {
      NORMAL("normal", Blocks.OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_FENCE),
      MESA("mesa", Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE);

      public static final Codec<MineshaftFeature.Type> CODEC = StringRepresentable.fromEnum(MineshaftFeature.Type::values, MineshaftFeature.Type::byName);
      private static final Map<String, MineshaftFeature.Type> BY_NAME = (Map<String, MineshaftFeature.Type>)Arrays.stream(values())
         .collect(Collectors.toMap(MineshaftFeature.Type::getName, var0 -> var0));
      private final String name;
      private final BlockState woodState;
      private final BlockState planksState;
      private final BlockState fenceState;

      private Type(String var3, Block var4, Block var5, Block var6) {
         this.name = â˜ƒ;
         this.woodState = â˜ƒ.defaultBlockState();
         this.planksState = â˜ƒ.defaultBlockState();
         this.fenceState = â˜ƒ.defaultBlockState();
      }

      public String getName() {
         return this.name;
      }

      private static MineshaftFeature.Type byName(String var0) {
         return (MineshaftFeature.Type)BY_NAME.get(â˜ƒ);
      }

      public static MineshaftFeature.Type byId(int var0) {
         return â˜ƒ >= 0 && â˜ƒ < values().length ? values()[â˜ƒ] : NORMAL;
      }

      public BlockState getWoodState() {
         return this.woodState;
      }

      public BlockState getPlanksState() {
         return this.planksState;
      }

      public BlockState getFenceState() {
         return this.fenceState;
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }
   }
}
