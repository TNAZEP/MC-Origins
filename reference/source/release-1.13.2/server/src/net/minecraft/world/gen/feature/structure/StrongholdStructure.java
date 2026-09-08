package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class StrongholdStructure extends Structure<StrongholdConfig> {
   private boolean field_75056_f;
   private ChunkPos[] field_75057_g;
   private long field_202387_av;

   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      if (this.field_202387_av != ☃.func_202089_c()) {
         this.func_202386_c();
      }

      if (!this.field_75056_f) {
         this.func_202385_a(☃);
         this.field_75056_f = true;
      }

      for(ChunkPos ☃ : this.field_75057_g) {
         if (☃ == ☃.field_77276_a && ☃ == ☃.field_77275_b) {
            return true;
         }
      }

      return false;
   }

   private void func_202386_c() {
      this.field_75056_f = false;
      this.field_75057_g = null;
   }

   @Override
   protected boolean func_202365_a(IWorld var1) {
      return ☃.func_72912_H().func_76089_r();
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
      int ☃x = 0;
      StrongholdStructure.Start ☃xx = new StrongholdStructure.Start(☃, ☃, ☃, ☃, ☃, ☃x++);

      while(☃xx.func_186161_c().isEmpty() || ((StrongholdPieces.Stairs2)☃xx.func_186161_c().get(0)).field_75025_b == null) {
         ☃xx = new StrongholdStructure.Start(☃, ☃, ☃, ☃, ☃, ☃x++);
      }

      return ☃xx;
   }

   @Override
   protected String func_143025_a() {
      return "Stronghold";
   }

   @Override
   public int func_202367_b() {
      return 8;
   }

   @Nullable
   @Override
   public BlockPos func_211405_a(World var1, IChunkGenerator<? extends IChunkGenSettings> var2, BlockPos var3, int var4, boolean var5) {
      if (!☃.func_202090_b().func_205004_a(this)) {
         return null;
      } else {
         if (this.field_202387_av != ☃.func_72905_C()) {
            this.func_202386_c();
         }

         if (!this.field_75056_f) {
            this.func_202385_a(☃);
            this.field_75056_f = true;
         }

         BlockPos ☃ = null;
         BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos(0, 0, 0);
         double ☃xx = Double.MAX_VALUE;

         for(ChunkPos ☃xxx : this.field_75057_g) {
            ☃x.func_181079_c((☃xxx.field_77276_a << 4) + 8, 32, (☃xxx.field_77275_b << 4) + 8);
            double ☃xxxx = ☃x.func_177951_i(☃);
            if (☃ == null) {
               ☃ = new BlockPos(☃x);
               ☃xx = ☃xxxx;
            } else if (☃xxxx < ☃xx) {
               ☃ = new BlockPos(☃x);
               ☃xx = ☃xxxx;
            }
         }

         return ☃;
      }
   }

   private void func_202385_a(IChunkGenerator<?> var1) {
      this.field_202387_av = ☃.func_202089_c();
      List<Biome> ☃ = Lists.<Biome>newArrayList();

      for(Biome ☃x : IRegistry.field_212624_m) {
         if (☃x != null && ☃.func_202094_a(☃x, Feature.field_202335_m)) {
            ☃.add(☃x);
         }
      }

      int ☃x = ☃.func_201496_a_().func_202172_d();
      int ☃xx = ☃.func_201496_a_().func_202176_e();
      int ☃xxx = ☃.func_201496_a_().func_202175_f();
      this.field_75057_g = new ChunkPos[☃xx];
      int ☃xxxx = 0;
      Long2ObjectMap<StructureStart> ☃xxxxx = ☃.func_203224_a(this);
      synchronized(☃xxxxx) {
         for(StructureStart ☃xxxxxx : ☃xxxxx.values()) {
            if (☃xxxx < this.field_75057_g.length) {
               this.field_75057_g[☃xxxx++] = new ChunkPos(☃xxxxxx.func_143019_e(), ☃xxxxxx.func_143018_f());
            }
         }
      }

      Random ☃x = new Random();
      ☃x.setSeed(☃.func_202089_c());
      double ☃xx = ☃x.nextDouble() * Math.PI * 2.0;
      int ☃xxx = ☃xxxxx.size();
      if (☃xxx < this.field_75057_g.length) {
         int ☃xxxx = 0;
         int ☃xxxxx = 0;

         for(int ☃xxxxxx = 0; ☃xxxxxx < this.field_75057_g.length; ++☃xxxxxx) {
            double ☃xxxxxxx = (double)(4 * ☃x + ☃x * ☃xxxxx * 6) + (☃x.nextDouble() - 0.5) * (double)☃x * 2.5;
            int ☃xxxxxxxx = (int)Math.round(Math.cos(☃xx) * ☃xxxxxxx);
            int ☃xxxxxxxxx = (int)Math.round(Math.sin(☃xx) * ☃xxxxxxx);
            BlockPos ☃xxxxxxxxxx = ☃.func_202090_b().func_180630_a((☃xxxxxxxx << 4) + 8, (☃xxxxxxxxx << 4) + 8, 112, ☃, ☃x);
            if (☃xxxxxxxxxx != null) {
               ☃xxxxxxxx = ☃xxxxxxxxxx.func_177958_n() >> 4;
               ☃xxxxxxxxx = ☃xxxxxxxxxx.func_177952_p() >> 4;
            }

            if (☃xxxxxx >= ☃xxx) {
               this.field_75057_g[☃xxxxxx] = new ChunkPos(☃xxxxxxxx, ☃xxxxxxxxx);
            }

            ☃xx += (Math.PI * 2) / (double)☃xxx;
            if (++☃xxxx == ☃xxx) {
               ++☃xxxxx;
               ☃xxxx = 0;
               ☃xxx += 2 * ☃xxx / (☃xxxxx + 1);
               ☃xxx = Math.min(☃xxx, this.field_75057_g.length - ☃xxxxxx);
               ☃xx += ☃x.nextDouble() * Math.PI * 2.0;
            }
         }
      }
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, SharedSeedRandom var2, int var3, int var4, Biome var5, int var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C() + (long)☃);
         StrongholdPieces.func_75198_a();
         StrongholdPieces.Stairs2 ☃ = new StrongholdPieces.Stairs2(0, ☃, (☃ << 4) + 2, (☃ << 4) + 2);
         this.field_75075_a.add(☃);
         ☃.func_74861_a(☃, this.field_75075_a, ☃);
         List<StructurePiece> ☃x = ☃.field_75026_c;

         while(!☃x.isEmpty()) {
            int ☃xx = ☃.nextInt(☃x.size());
            StructurePiece ☃xxx = (StructurePiece)☃x.remove(☃xx);
            ☃xxx.func_74861_a(☃, this.field_75075_a, ☃);
         }

         this.func_202500_a(☃);
         this.func_75067_a(☃, ☃, 10);
      }
   }
}
