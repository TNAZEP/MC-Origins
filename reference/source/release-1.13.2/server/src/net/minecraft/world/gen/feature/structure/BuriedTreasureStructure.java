package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class BuriedTreasureStructure extends Structure<BuriedTreasureConfig> {
   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), null);
      if (☃.func_202094_a(☃, Feature.field_204292_r)) {
         ((SharedSeedRandom)☃).func_202427_a(☃.func_202089_c(), ☃, ☃, 10387320);
         BuriedTreasureConfig ☃x = (BuriedTreasureConfig)☃.func_202087_b(☃, Feature.field_204292_r);
         return ☃.nextFloat() < ☃x.field_204293_a;
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
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), null);
      return new BuriedTreasureStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected String func_143025_a() {
      return "Buried_Treasure";
   }

   @Override
   public int func_202367_b() {
      return 1;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         int ☃ = ☃ * 16;
         int ☃x = ☃ * 16;
         BlockPos ☃xx = new BlockPos(☃ + 9, 90, ☃x + 9);
         this.field_75075_a.add(new BuriedTreasurePieces.Piece(☃xx));
         this.func_202500_a(☃);
      }

      @Override
      public BlockPos func_204294_a() {
         return new BlockPos((this.field_143024_c << 4) + 9, 0, (this.field_143023_d << 4) + 9);
      }
   }
}
