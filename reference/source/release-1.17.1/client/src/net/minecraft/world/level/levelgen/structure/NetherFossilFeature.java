package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class NetherFossilFeature extends StructureFeature<RangeDecoratorConfiguration> {
   public NetherFossilFeature(Codec<RangeDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public StructureFeature.StructureStartFactory<RangeDecoratorConfiguration> getStartFactory() {
      return NetherFossilFeature.FeatureStart::new;
   }

   public static class FeatureStart extends NoiseAffectingStructureStart<RangeDecoratorConfiguration> {
      public FeatureStart(StructureFeature<RangeDecoratorConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, RangeDecoratorConfiguration var6, LevelHeightAccessor var7
      ) {
         int â˜ƒ = â˜ƒ.getMinBlockX() + this.random.nextInt(16);
         int â˜ƒx = â˜ƒ.getMinBlockZ() + this.random.nextInt(16);
         int â˜ƒxx = â˜ƒ.getSeaLevel();
         WorldGenerationContext â˜ƒxxx = new WorldGenerationContext(â˜ƒ, â˜ƒ);
         int â˜ƒxxxx = â˜ƒ.height.sample(this.random, â˜ƒxxx);
         NoiseColumn â˜ƒxxxxx = â˜ƒ.getBaseColumn(â˜ƒ, â˜ƒx, â˜ƒ);

         for(BlockPos.MutableBlockPos â˜ƒxxxxxx = new BlockPos.MutableBlockPos(â˜ƒ, â˜ƒxxxx, â˜ƒx); â˜ƒxxxx > â˜ƒxx; --â˜ƒxxxx) {
            BlockState â˜ƒxxxxxxx = â˜ƒxxxxx.getBlockState(â˜ƒxxxxxx);
            â˜ƒxxxxxx.move(Direction.DOWN);
            BlockState â˜ƒxxxxxxxx = â˜ƒxxxxx.getBlockState(â˜ƒxxxxxx);
            if (â˜ƒxxxxxxx.isAir() && (â˜ƒxxxxxxxx.is(Blocks.SOUL_SAND) || â˜ƒxxxxxxxx.isFaceSturdy(EmptyBlockGetter.INSTANCE, â˜ƒxxxxxx, Direction.UP))) {
               break;
            }
         }

         if (â˜ƒxxxx > â˜ƒxx) {
            NetherFossilPieces.addPieces(â˜ƒ, this, this.random, new BlockPos(â˜ƒ, â˜ƒxxxx, â˜ƒx));
         }
      }
   }
}
