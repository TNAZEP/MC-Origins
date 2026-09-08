package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class MineshaftStructure extends Structure<MineshaftConfig> {
   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      ((SharedSeedRandom)☃).func_202425_c(☃.func_202089_c(), ☃, ☃);
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_180279_ad);
      if (☃.func_202094_a(☃, Feature.field_202329_g)) {
         MineshaftConfig ☃x = (MineshaftConfig)☃.func_202087_b(☃, Feature.field_202329_g);
         double ☃xx = ☃x.field_202439_a;
         return ☃.nextDouble() < ☃xx;
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
      return new MineshaftStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected String func_143025_a() {
      return "Mineshaft";
   }

   @Override
   public int func_202367_b() {
      return 8;
   }

   public static class Start extends StructureStart {
      private MineshaftStructure.Type field_202507_c;

      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         MineshaftConfig ☃ = (MineshaftConfig)☃.func_202087_b(☃, Feature.field_202329_g);
         this.field_202507_c = ☃.field_202440_b;
         MineshaftPieces.Room ☃x = new MineshaftPieces.Room(0, ☃, (☃ << 4) + 2, (☃ << 4) + 2, this.field_202507_c);
         this.field_75075_a.add(☃x);
         ☃x.func_74861_a(☃x, this.field_75075_a, ☃);
         this.func_202500_a(☃);
         if (☃.field_202440_b == MineshaftStructure.Type.MESA) {
            int ☃xx = -5;
            int ☃xxx = ☃.func_181545_F() - this.field_75074_b.field_78894_e + this.field_75074_b.func_78882_c() / 2 - -5;
            this.field_75074_b.func_78886_a(0, ☃xxx, 0);

            for(StructurePiece ☃xxxx : this.field_75075_a) {
               ☃xxxx.func_181138_a(0, ☃xxx, 0);
            }
         } else {
            this.func_75067_a(☃, ☃, 10);
         }
      }
   }

   public static enum Type {
      NORMAL,
      MESA;

      public static MineshaftStructure.Type func_189910_a(int var0) {
         return ☃ >= 0 && ☃ < values().length ? values()[☃] : NORMAL;
      }
   }
}
