package net.minecraft.world.gen.tasks;

import java.util.Map;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.IChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ChunkTask {
   private static final Logger field_202841_a = LogManager.getLogger();

   protected ChunkPrimer[] func_202838_a(ChunkStatus var1, int var2, int var3, Map<ChunkPos, ChunkPrimer> var4) {
      int ☃ = ☃.func_202128_c();
      ChunkPrimer[] ☃x = new ChunkPrimer[(1 + 2 * ☃) * (1 + 2 * ☃)];
      int ☃xx = 0;

      for(int ☃xxx = -☃; ☃xxx <= ☃; ++☃xxx) {
         for(int ☃xxxx = -☃; ☃xxxx <= ☃; ++☃xxxx) {
            ChunkPrimer ☃xxxxx = (ChunkPrimer)☃.get(new ChunkPos(☃ + ☃xxxx, ☃ + ☃xxx));
            ☃xxxxx.func_207739_b(☃.func_207794_f());
            ☃x[☃xx++] = ☃xxxxx;
         }
      }

      return ☃x;
   }

   public ChunkPrimer func_202839_a(ChunkStatus var1, World var2, IChunkGenerator<?> var3, Map<ChunkPos, ChunkPrimer> var4, int var5, int var6) {
      ChunkPrimer[] ☃ = this.func_202838_a(☃, ☃, ☃, ☃);
      return this.func_202840_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected abstract ChunkPrimer func_202840_a(ChunkStatus var1, World var2, IChunkGenerator<?> var3, ChunkPrimer[] var4, int var5, int var6);
}
