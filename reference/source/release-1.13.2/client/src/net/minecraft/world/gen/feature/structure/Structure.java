package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Structure<C extends IFeatureConfig> extends Feature<C> {
   private static final Logger field_208204_b = LogManager.getLogger();
   public static final StructureStart field_202376_c = new StructureStart() {
      @Override
      public boolean func_75069_d() {
         return false;
      }
   };

   @Override
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, C var5) {
      if (!this.func_202365_a(☃)) {
         return false;
      } else {
         int ☃ = this.func_202367_b();
         int ☃x = ☃.func_177958_n() >> 4;
         int ☃xx = ☃.func_177952_p() >> 4;
         int ☃xxx = ☃x << 4;
         int ☃xxxx = ☃xx << 4;
         long ☃xxxxx = ChunkPos.func_77272_a(☃x, ☃xx);
         boolean ☃xxxxxx = false;

         for(int ☃xxxxxxx = ☃x - ☃; ☃xxxxxxx <= ☃x + ☃; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = ☃xx - ☃; ☃xxxxxxxx <= ☃xx + ☃; ++☃xxxxxxxx) {
               long ☃xxxxxxxxx = ChunkPos.func_77272_a(☃xxxxxxx, ☃xxxxxxxx);
               StructureStart ☃xxxxxxxxxx = this.func_202373_a(☃, ☃, (SharedSeedRandom)☃, ☃xxxxxxxxx);
               if (☃xxxxxxxxxx != field_202376_c && ☃xxxxxxxxxx.func_75071_a().func_78885_a(☃xxx, ☃xxxx, ☃xxx + 15, ☃xxxx + 15)) {
                  ☃.func_203223_b(this).computeIfAbsent(☃xxxxx, var0 -> new LongOpenHashSet()).add(☃xxxxxxxxx);
                  ☃.func_72863_F().func_201713_d(☃x, ☃xx, true).func_201583_a(this.func_143025_a(), ☃xxxxxxxxx);
                  ☃xxxxxxxxxx.func_75068_a(☃, ☃, new MutableBoundingBox(☃xxx, ☃xxxx, ☃xxx + 15, ☃xxxx + 15), new ChunkPos(☃x, ☃xx));
                  ☃xxxxxxxxxx.func_175787_b(new ChunkPos(☃x, ☃xx));
                  ☃xxxxxx = true;
               }
            }
         }

         return ☃xxxxxx;
      }
   }

   protected StructureStart func_202364_a(IWorld var1, BlockPos var2) {
      for(StructureStart ☃ : this.func_202371_a(☃, ☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4)) {
         if (☃.func_75069_d() && ☃.func_75071_a().func_175898_b(☃)) {
            for(StructurePiece ☃x : ☃.func_186161_c()) {
               if (☃x.func_74874_b().func_175898_b(☃)) {
                  return ☃;
               }
            }
         }
      }

      return field_202376_c;
   }

   public boolean func_175796_a(IWorld var1, BlockPos var2) {
      for(StructureStart ☃ : this.func_202371_a(☃, ☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4)) {
         if (☃.func_75069_d() && ☃.func_75071_a().func_175898_b(☃)) {
            return true;
         }
      }

      return false;
   }

   public boolean func_202366_b(IWorld var1, BlockPos var2) {
      return this.func_202364_a(☃, ☃).func_75069_d();
   }

   @Nullable
   public BlockPos func_211405_a(World var1, IChunkGenerator<? extends IChunkGenSettings> var2, BlockPos var3, int var4, boolean var5) {
      if (!☃.func_202090_b().func_205004_a(this)) {
         return null;
      } else {
         int ☃ = ☃.func_177958_n() >> 4;
         int ☃x = ☃.func_177952_p() >> 4;
         int ☃xx = 0;

         for(SharedSeedRandom ☃xxx = new SharedSeedRandom(); ☃xx <= ☃; ++☃xx) {
            for(int ☃xxxx = -☃xx; ☃xxxx <= ☃xx; ++☃xxxx) {
               boolean ☃xxxxx = ☃xxxx == -☃xx || ☃xxxx == ☃xx;

               for(int ☃xxxxxx = -☃xx; ☃xxxxxx <= ☃xx; ++☃xxxxxx) {
                  boolean ☃xxxxxxx = ☃xxxxxx == -☃xx || ☃xxxxxx == ☃xx;
                  if (☃xxxxx || ☃xxxxxxx) {
                     ChunkPos ☃xxxxxxxx = this.func_211744_a(☃, ☃xxx, ☃, ☃x, ☃xxxx, ☃xxxxxx);
                     StructureStart ☃xxxxxxxxx = this.func_202373_a(☃, ☃, ☃xxx, ☃xxxxxxxx.func_201841_a());
                     if (☃xxxxxxxxx != field_202376_c) {
                        if (☃ && ☃xxxxxxxxx.func_212687_g()) {
                           ☃xxxxxxxxx.func_212685_h();
                           return ☃xxxxxxxxx.func_204294_a();
                        }

                        if (!☃) {
                           return ☃xxxxxxxxx.func_204294_a();
                        }
                     }

                     if (☃xx == 0) {
                        break;
                     }
                  }
               }

               if (☃xx == 0) {
                  break;
               }
            }
         }

         return null;
      }
   }

   private List<StructureStart> func_202371_a(IWorld var1, int var2, int var3) {
      List<StructureStart> ☃ = Lists.<StructureStart>newArrayList();
      Long2ObjectMap<StructureStart> ☃x = ☃.func_72863_F().func_201711_g().func_203224_a(this);
      Long2ObjectMap<LongSet> ☃xx = ☃.func_72863_F().func_201711_g().func_203223_b(this);
      long ☃xxx = ChunkPos.func_77272_a(☃, ☃);
      LongSet ☃xxxx = ☃xx.get(☃xxx);
      if (☃xxxx == null) {
         ☃xxxx = ☃.func_72863_F().func_201713_d(☃, ☃, true).func_201578_b(this.func_143025_a());
         ☃xx.put(☃xxx, ☃xxxx);
      }

      LongIterator var10 = ☃xxxx.iterator();

      while(var10.hasNext()) {
         Long ☃ = (Long)var10.next();
         StructureStart ☃x = ☃x.get(☃);
         if (☃x != null) {
            ☃.add(☃x);
         } else {
            ChunkPos ☃ = new ChunkPos(☃);
            IChunk ☃x = ☃.func_72863_F().func_201713_d(☃.field_77276_a, ☃.field_77275_b, true);
            ☃x = ☃x.func_201585_a(this.func_143025_a());
            if (☃x != null) {
               ☃x.put(☃, ☃x);
               ☃.add(☃x);
            }
         }
      }

      return ☃;
   }

   private StructureStart func_202373_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, SharedSeedRandom var3, long var4) {
      if (!☃.func_202090_b().func_205004_a(this)) {
         return field_202376_c;
      } else {
         Long2ObjectMap<StructureStart> ☃ = ☃.func_203224_a(this);
         StructureStart ☃x = ☃.get(☃);
         if (☃x != null) {
            return ☃x;
         } else {
            ChunkPos ☃ = new ChunkPos(☃);
            IChunk ☃x = ☃.func_72863_F().func_201713_d(☃.field_77276_a, ☃.field_77275_b, false);
            if (☃x != null) {
               ☃x = ☃x.func_201585_a(this.func_143025_a());
               if (☃x != null) {
                  ☃.put(☃, ☃x);
                  return ☃x;
               }
            }

            if (this.func_202372_a(☃, ☃, ☃.field_77276_a, ☃.field_77275_b)) {
               StructureStart ☃ = this.func_202369_a(☃, ☃, ☃, ☃.field_77276_a, ☃.field_77275_b);
               ☃x = ☃.func_75069_d() ? ☃ : field_202376_c;
            } else {
               ☃x = field_202376_c;
            }

            if (☃x.func_75069_d()) {
               ☃.func_72863_F().func_201713_d(☃.field_77276_a, ☃.field_77275_b, true).func_201584_a(this.func_143025_a(), ☃x);
            }

            ☃.put(☃, ☃x);
            return ☃x;
         }
      }
   }

   protected ChunkPos func_211744_a(IChunkGenerator<?> var1, Random var2, int var3, int var4, int var5, int var6) {
      return new ChunkPos(☃ + ☃, ☃ + ☃);
   }

   protected abstract boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4);

   protected abstract boolean func_202365_a(IWorld var1);

   protected abstract StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5);

   protected abstract String func_143025_a();

   public abstract int func_202367_b();
}
