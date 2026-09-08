package net.minecraft.world.gen;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.util.ExpiringMap;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarverWrapper;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;

public abstract class AbstractChunkGenerator<C extends IChunkGenSettings> implements IChunkGenerator<C> {
   protected final IWorld field_202095_a;
   protected final long field_202096_b;
   protected final BiomeProvider field_202097_c;
   protected final Map<Structure<? extends IFeatureConfig>, Long2ObjectMap<StructureStart>> field_203227_d = Maps.<Structure<? extends IFeatureConfig>, Long2ObjectMap<StructureStart>>newHashMap(
      
   );
   protected final Map<Structure<? extends IFeatureConfig>, Long2ObjectMap<LongSet>> field_203228_e = Maps.<Structure<? extends IFeatureConfig>, Long2ObjectMap<LongSet>>newHashMap(
      
   );

   public AbstractChunkGenerator(IWorld var1, BiomeProvider var2) {
      this.field_202095_a = ☃;
      this.field_202096_b = ☃.func_72905_C();
      this.field_202097_c = ☃;
   }

   @Override
   public void func_202091_a(WorldGenRegion var1, GenerationStage.Carving var2) {
      SharedSeedRandom ☃ = new SharedSeedRandom(this.field_202096_b);
      int ☃x = 8;
      int ☃xx = ☃.func_201679_a();
      int ☃xxx = ☃.func_201680_b();
      BitSet ☃xxxx = ☃.func_72964_e(☃xx, ☃xxx).func_205749_a(☃);

      for(int ☃xxxxx = ☃xx - 8; ☃xxxxx <= ☃xx + 8; ++☃xxxxx) {
         for(int ☃xxxxxx = ☃xxx - 8; ☃xxxxxx <= ☃xxx + 8; ++☃xxxxxx) {
            List<WorldCarverWrapper<?>> ☃xxxxxxx = ☃.func_72863_F()
               .func_201711_g()
               .func_202090_b()
               .func_180300_a(new BlockPos(☃xxxxx * 16, 0, ☃xxxxxx * 16), null)
               .func_203603_a(☃);
            ListIterator<WorldCarverWrapper<?>> ☃xxxxxxxx = ☃xxxxxxx.listIterator();

            while(☃xxxxxxxx.hasNext()) {
               int ☃xxxxxxxxx = ☃xxxxxxxx.nextIndex();
               WorldCarverWrapper<?> ☃xxxxxxxxxx = (WorldCarverWrapper)☃xxxxxxxx.next();
               ☃.func_202425_c(☃.func_201672_e().func_72905_C() + (long)☃xxxxxxxxx, ☃xxxxx, ☃xxxxxx);
               if (☃xxxxxxxxxx.func_212246_a(☃, ☃, ☃xxxxx, ☃xxxxxx, IFeatureConfig.field_202429_e)) {
                  ☃xxxxxxxxxx.func_202522_a(☃, ☃, ☃xxxxx, ☃xxxxxx, ☃xx, ☃xxx, ☃xxxx, IFeatureConfig.field_202429_e);
               }
            }
         }
      }
   }

   @Nullable
   @Override
   public BlockPos func_211403_a(World var1, String var2, BlockPos var3, int var4, boolean var5) {
      Structure<?> ☃ = (Structure)Feature.field_202300_at.get(☃.toLowerCase(Locale.ROOT));
      return ☃ != null ? ☃.func_211405_a(☃, this, ☃, ☃, ☃) : null;
   }

   protected void func_205472_a(IChunk var1, Random var2) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();
      int ☃x = ☃.func_76632_l().func_180334_c();
      int ☃xx = ☃.func_76632_l().func_180333_d();

      for(BlockPos ☃xxx : BlockPos.func_191532_a(☃x, 0, ☃xx, ☃x + 16, 0, ☃xx + 16)) {
         for(int ☃xxxx = 4; ☃xxxx >= 0; --☃xxxx) {
            if (☃xxxx <= ☃.nextInt(5)) {
               ☃.func_177436_a(☃.func_181079_c(☃xxx.func_177958_n(), ☃xxxx, ☃xxx.func_177952_p()), Blocks.field_150357_h.func_176223_P(), false);
            }
         }
      }
   }

   @Override
   public void func_202092_b(WorldGenRegion var1) {
      BlockFalling.field_149832_M = true;
      int ☃ = ☃.func_201679_a();
      int ☃x = ☃.func_201680_b();
      int ☃xx = ☃ * 16;
      int ☃xxx = ☃x * 16;
      BlockPos ☃xxxx = new BlockPos(☃xx, 0, ☃xxx);
      Biome ☃xxxxx = ☃.func_72964_e(☃ + 1, ☃x + 1).func_201590_e()[0];
      SharedSeedRandom ☃xxxxxx = new SharedSeedRandom();
      long ☃xxxxxxx = ☃xxxxxx.func_202424_a(☃.func_72905_C(), ☃xx, ☃xxx);

      for(GenerationStage.Decoration ☃xxxxxxxx : GenerationStage.Decoration.values()) {
         ☃xxxxx.func_203608_a(☃xxxxxxxx, this, ☃, ☃xxxxxxx, ☃xxxxxx, ☃xxxx);
      }

      BlockFalling.field_149832_M = false;
   }

   public void func_205471_a(IChunk var1, Biome[] var2, SharedSeedRandom var3, int var4) {
      double ☃ = 0.03125;
      ChunkPos ☃x = ☃.func_76632_l();
      int ☃xx = ☃x.func_180334_c();
      int ☃xxx = ☃x.func_180333_d();
      double[] ☃xxxx = this.func_205473_a(☃x.field_77276_a, ☃x.field_77275_b);

      for(int ☃xxxxx = 0; ☃xxxxx < 16; ++☃xxxxx) {
         for(int ☃xxxxxx = 0; ☃xxxxxx < 16; ++☃xxxxxx) {
            int ☃xxxxxxx = ☃xx + ☃xxxxx;
            int ☃xxxxxxxx = ☃xxx + ☃xxxxxx;
            int ☃xxxxxxxxx = ☃.func_201576_a(Heightmap.Type.WORLD_SURFACE_WG, ☃xxxxx, ☃xxxxxx) + 1;
            ☃[☃xxxxxx * 16 + ☃xxxxx]
               .func_206854_a(
                  ☃,
                  ☃,
                  ☃xxxxxxx,
                  ☃xxxxxxxx,
                  ☃xxxxxxxxx,
                  ☃xxxx[☃xxxxxx * 16 + ☃xxxxx],
                  this.func_201496_a_().func_205532_l(),
                  this.func_201496_a_().func_205533_m(),
                  ☃,
                  this.field_202095_a.func_72905_C()
               );
         }
      }
   }

   @Override
   public abstract C func_201496_a_();

   public abstract double[] func_205473_a(int var1, int var2);

   @Override
   public boolean func_202094_a(Biome var1, Structure<? extends IFeatureConfig> var2) {
      return ☃.func_201858_a(☃);
   }

   @Nullable
   @Override
   public IFeatureConfig func_202087_b(Biome var1, Structure<? extends IFeatureConfig> var2) {
      return ☃.func_201857_b(☃);
   }

   @Override
   public BiomeProvider func_202090_b() {
      return this.field_202097_c;
   }

   @Override
   public long func_202089_c() {
      return this.field_202096_b;
   }

   @Override
   public Long2ObjectMap<StructureStart> func_203224_a(Structure<? extends IFeatureConfig> var1) {
      return (Long2ObjectMap<StructureStart>)this.field_203227_d.computeIfAbsent(☃, var0 -> Long2ObjectMaps.synchronize(new ExpiringMap(8192, 10000)));
   }

   @Override
   public Long2ObjectMap<LongSet> func_203223_b(Structure<? extends IFeatureConfig> var1) {
      return (Long2ObjectMap<LongSet>)this.field_203228_e.computeIfAbsent(☃, var0 -> Long2ObjectMaps.synchronize(new ExpiringMap(8192, 10000)));
   }

   @Override
   public int func_207511_e() {
      return 256;
   }
}
