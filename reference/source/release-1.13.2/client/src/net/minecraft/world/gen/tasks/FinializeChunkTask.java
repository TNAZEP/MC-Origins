package net.minecraft.world.gen.tasks;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;

public class FinializeChunkTask extends ChunkTask {
   @Override
   protected ChunkPrimer func_202840_a(ChunkStatus var1, World var2, IChunkGenerator<?> var3, ChunkPrimer[] var4, int var5, int var6) {
      ChunkPrimer ☃ = ☃[☃.length / 2];
      ☃.func_201574_a(ChunkStatus.FINALIZED);
      ☃.func_201588_a(
         Heightmap.Type.MOTION_BLOCKING,
         Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
         Heightmap.Type.LIGHT_BLOCKING,
         Heightmap.Type.OCEAN_FLOOR,
         Heightmap.Type.WORLD_SURFACE
      );
      return ☃;
   }
}
