package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.init.Biomes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class EndCityStructure extends Structure<EndCityConfig> {
   @Override
   protected ChunkPos func_211744_a(IChunkGenerator<?> var1, Random var2, int var3, int var4, int var5, int var6) {
      int ☃ = ☃.func_201496_a_().func_202178_h();
      int ☃x = ☃.func_201496_a_().func_211728_o();
      int ☃xx = ☃ + ☃ * ☃;
      int ☃xxx = ☃ + ☃ * ☃;
      int ☃xxxx = ☃xx < 0 ? ☃xx - ☃ + 1 : ☃xx;
      int ☃xxxxx = ☃xxx < 0 ? ☃xxx - ☃ + 1 : ☃xxx;
      int ☃xxxxxx = ☃xxxx / ☃;
      int ☃xxxxxxx = ☃xxxxx / ☃;
      ((SharedSeedRandom)☃).func_202427_a(☃.func_202089_c(), ☃xxxxxx, ☃xxxxxxx, 10387313);
      ☃xxxxxx *= ☃;
      ☃xxxxxxx *= ☃;
      ☃xxxxxx += (☃.nextInt(☃ - ☃x) + ☃.nextInt(☃ - ☃x)) / 2;
      ☃xxxxxxx += (☃.nextInt(☃ - ☃x) + ☃.nextInt(☃ - ☃x)) / 2;
      return new ChunkPos(☃xxxxxx, ☃xxxxxxx);
   }

   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      ChunkPos ☃ = this.func_211744_a(☃, ☃, ☃, ☃, 0, 0);
      if (☃ == ☃.field_77276_a && ☃ == ☃.field_77275_b) {
         Biome ☃x = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
         if (!☃.func_202094_a(☃x, Feature.field_202338_p)) {
            return false;
         } else {
            int ☃x = func_191070_b(☃, ☃, ☃);
            return ☃x >= 60;
         }
      } else {
         return false;
      }
   }

   @Override
   protected boolean func_202365_a(IWorld var1) {
      return ☃.func_72912_H().func_76089_r();
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
      return new EndCityStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected String func_143025_a() {
      return "EndCity";
   }

   @Override
   public int func_202367_b() {
      return 9;
   }

   private static int func_191070_b(int var0, int var1, IChunkGenerator<?> var2) {
      Random ☃ = new Random((long)(☃ + ☃ * 10387313));
      Rotation ☃x = Rotation.values()[☃.nextInt(Rotation.values().length)];
      ChunkPrimer ☃xx = new ChunkPrimer(new ChunkPos(☃, ☃), UpgradeData.field_196994_a);
      ☃.func_202088_a(☃xx);
      int ☃xxx = 5;
      int ☃xxxx = 5;
      if (☃x == Rotation.CLOCKWISE_90) {
         ☃xxx = -5;
      } else if (☃x == Rotation.CLOCKWISE_180) {
         ☃xxx = -5;
         ☃xxxx = -5;
      } else if (☃x == Rotation.COUNTERCLOCKWISE_90) {
         ☃xxxx = -5;
      }

      int ☃ = ☃xx.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7, 7);
      int ☃x = ☃xx.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7, 7 + ☃xxxx);
      int ☃xx = ☃xx.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7 + ☃xxx, 7);
      int ☃xxx = ☃xx.func_201576_a(Heightmap.Type.MOTION_BLOCKING, 7 + ☃xxx, 7 + ☃xxxx);
      return Math.min(Math.min(☃, ☃x), Math.min(☃xx, ☃xxx));
   }

   public static class Start extends StructureStart {
      private boolean field_186163_c;

      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         Rotation ☃ = Rotation.values()[☃.nextInt(Rotation.values().length)];
         int ☃x = EndCityStructure.func_191070_b(☃, ☃, ☃);
         if (☃x < 60) {
            this.field_186163_c = false;
         } else {
            BlockPos ☃ = new BlockPos(☃ * 16 + 8, ☃x, ☃ * 16 + 8);
            EndCityPieces.func_191087_a(☃.func_72860_G().func_186340_h(), ☃, ☃, this.field_75075_a, ☃);
            this.func_202500_a(☃);
            this.field_186163_c = true;
         }
      }

      @Override
      public boolean func_75069_d() {
         return this.field_186163_c;
      }
   }
}
