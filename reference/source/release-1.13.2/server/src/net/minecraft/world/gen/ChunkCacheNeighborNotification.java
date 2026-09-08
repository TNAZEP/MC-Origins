package net.minecraft.world.gen;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkCacheNeighborNotification extends Long2ObjectOpenHashMap<Chunk> {
   private static final Logger field_202835_a = LogManager.getLogger();

   public ChunkCacheNeighborNotification(int var1) {
      super(☃);
   }

   public Chunk put(long var1, Chunk var3) {
      Chunk ☃ = super.put(☃, ☃);
      ChunkPos ☃x = new ChunkPos(☃);

      for(int ☃xx = ☃x.field_77276_a - 1; ☃xx <= ☃x.field_77276_a + 1; ++☃xx) {
         for(int ☃xxx = ☃x.field_77275_b - 1; ☃xxx <= ☃x.field_77275_b + 1; ++☃xxx) {
            if (☃xx != ☃x.field_77276_a || ☃xxx != ☃x.field_77275_b) {
               long ☃xxxx = ChunkPos.func_77272_a(☃xx, ☃xxx);
               Chunk ☃xxxxx = this.get(☃xxxx);
               if (☃xxxxx != null) {
                  ☃.func_201605_F();
                  ☃xxxxx.func_201605_F();
               }
            }
         }
      }

      return ☃;
   }

   public Chunk put(Long var1, Chunk var2) {
      return this.put(☃.longValue(), ☃);
   }

   public Chunk remove(long var1) {
      Chunk ☃ = (Chunk)super.remove(☃);
      ChunkPos ☃x = new ChunkPos(☃);

      for(int ☃xx = ☃x.field_77276_a - 1; ☃xx <= ☃x.field_77276_a + 1; ++☃xx) {
         for(int ☃xxx = ☃x.field_77275_b - 1; ☃xxx <= ☃x.field_77275_b + 1; ++☃xxx) {
            if (☃xx != ☃x.field_77276_a || ☃xxx != ☃x.field_77275_b) {
               Chunk ☃xxxx = this.get(ChunkPos.func_77272_a(☃xx, ☃xxx));
               if (☃xxxx != null) {
                  ☃xxxx.func_201611_G();
               }
            }
         }
      }

      return ☃;
   }

   public Chunk remove(Object var1) {
      return this.remove((Long)☃);
   }

   @Override
   public void putAll(Map<? extends Long, ? extends Chunk> var1) {
      throw new RuntimeException("Not yet implemented");
   }

   @Override
   public boolean remove(Object var1, Object var2) {
      throw new RuntimeException("Not yet implemented");
   }
}
