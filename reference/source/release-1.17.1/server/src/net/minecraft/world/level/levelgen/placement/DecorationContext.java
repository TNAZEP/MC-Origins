package net.minecraft.world.level.levelgen.placement;

import java.util.BitSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class DecorationContext extends WorldGenerationContext {
   private final WorldGenLevel level;

   public DecorationContext(WorldGenLevel var1, ChunkGenerator var2) {
      super(â˜ƒ, â˜ƒ);
      this.level = â˜ƒ;
   }

   public int getHeight(Heightmap.Types var1, int var2, int var3) {
      return this.level.getHeight(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public BitSet getCarvingMask(ChunkPos var1, GenerationStep.Carving var2) {
      return ((ProtoChunk)this.level.getChunk(â˜ƒ.x, â˜ƒ.z)).getOrCreateCarvingMask(â˜ƒ);
   }

   public BlockState getBlockState(BlockPos var1) {
      return this.level.getBlockState(â˜ƒ);
   }

   public int getMinBuildHeight() {
      return this.level.getMinBuildHeight();
   }

   public WorldGenLevel getLevel() {
      return this.level;
   }
}
