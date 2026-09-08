package net.minecraft.world.biome;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.provider.BiomeProvider;

public class BiomeCache {
   private final BiomeProvider field_76844_a;
   private final LoadingCache<ChunkPos, BiomeCache.Entry> field_76843_c = CacheBuilder.newBuilder()
      .expireAfterAccess(30000L, TimeUnit.MILLISECONDS)
      .build(new CacheLoader<ChunkPos, BiomeCache.Entry>() {
         public BiomeCache.Entry load(ChunkPos var1) throws Exception {
            return BiomeCache.this.new Entry(☃.field_77276_a, ☃.field_77275_b);
         }
      });

   public BiomeCache(BiomeProvider var1) {
      this.field_76844_a = ☃;
   }

   public BiomeCache.Entry func_76840_a(int var1, int var2) {
      ☃ >>= 4;
      ☃ >>= 4;
      return this.field_76843_c.getUnchecked(new ChunkPos(☃, ☃));
   }

   public Biome func_180284_a(int var1, int var2, Biome var3) {
      Biome ☃ = this.func_76840_a(☃, ☃).func_76885_a(☃, ☃);
      return ☃ == null ? ☃ : ☃;
   }

   public void func_76838_a() {
   }

   public Biome[] func_76839_e(int var1, int var2) {
      return this.func_76840_a(☃, ☃).field_76891_c;
   }

   public class Entry {
      private final Biome[] field_76891_c;

      public Entry(int var2, int var3) {
         this.field_76891_c = BiomeCache.this.field_76844_a.func_201537_a(☃ << 4, ☃ << 4, 16, 16, false);
      }

      public Biome func_76885_a(int var1, int var2) {
         return this.field_76891_c[☃ & 15 | (☃ & 15) << 4];
      }
   }
}
