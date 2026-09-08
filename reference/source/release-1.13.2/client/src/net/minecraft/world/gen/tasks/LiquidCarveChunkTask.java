package net.minecraft.world.gen.tasks;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;

public class LiquidCarveChunkTask extends ChunkTask {
   @Override
   protected ChunkPrimer func_202840_a(ChunkStatus var1, World var2, IChunkGenerator<?> var3, ChunkPrimer[] var4, int var5, int var6) {
      ☃.func_202091_a(new WorldGenRegion(☃, ☃.func_202128_c() * 2 + 1, ☃.func_202128_c() * 2 + 1, ☃, ☃, ☃), GenerationStage.Carving.LIQUID);
      ChunkPrimer ☃ = ☃[☃.length / 2];
      ☃.func_201588_a(Heightmap.Type.OCEAN_FLOOR_WG, Heightmap.Type.WORLD_SURFACE_WG);
      ☃.func_201574_a(ChunkStatus.LIQUID_CARVED);
      return ☃;
   }
}
