package net.minecraft.world.gen.tasks;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.lighting.BlockLightEngine;
import net.minecraft.world.lighting.SkyLightEngine;

public class LightChunkTask extends ChunkTask {
   @Override
   protected ChunkPrimer func_202840_a(ChunkStatus var1, World var2, IChunkGenerator<?> var3, ChunkPrimer[] var4, int var5, int var6) {
      ChunkPrimer ☃ = ☃[☃.length / 2];
      WorldGenRegion ☃x = new WorldGenRegion(☃, ☃.func_202128_c() * 2 + 1, ☃.func_202128_c() * 2 + 1, ☃, ☃, ☃);
      ☃.func_201588_a(Heightmap.Type.LIGHT_BLOCKING);
      if (☃x.func_201675_m().func_191066_m()) {
         new SkyLightEngine().func_202675_a(☃x, ☃);
      }

      new BlockLightEngine().func_202677_a(☃x, ☃);
      ☃.func_201574_a(ChunkStatus.LIGHTED);
      return ☃;
   }
}
