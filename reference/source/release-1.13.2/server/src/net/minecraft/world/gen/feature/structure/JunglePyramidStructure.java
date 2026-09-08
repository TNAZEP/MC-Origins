package net.minecraft.world.gen.feature.structure;

import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;

public class JunglePyramidStructure extends ScatteredStructure<JunglePyramidConfig> {
   @Override
   protected String func_143025_a() {
      return "Jungle_Pyramid";
   }

   @Override
   public int func_202367_b() {
      return 3;
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), Biomes.field_76772_c);
      return new JunglePyramidStructure.Start(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected int func_202382_c() {
      return 14357619;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, SharedSeedRandom var2, int var3, int var4, Biome var5) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         JunglePyramidPiece ☃ = new JunglePyramidPiece(☃, ☃ * 16, ☃ * 16);
         this.field_75075_a.add(☃);
         this.func_202500_a(☃);
      }
   }
}
