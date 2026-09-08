package net.minecraft.world.gen.feature.structure;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanRuinStructure extends ScatteredStructure<OceanRuinConfig> {
   @Override
   public String func_143025_a() {
      return "Ocean_Ruin";
   }

   @Override
   public int func_202367_b() {
      return 3;
   }

   @Override
   protected int func_204030_a(IChunkGenerator<?> var1) {
      return ☃.func_201496_a_().func_204026_h();
   }

   @Override
   protected int func_211745_b(IChunkGenerator<?> var1) {
      return ☃.func_201496_a_().func_211727_m();
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), null);
      return new OceanRuinStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected int func_202382_c() {
      return 14357621;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         OceanRuinConfig ☃ = (OceanRuinConfig)☃.func_202087_b(☃, Feature.field_204029_o);
         int ☃x = ☃ * 16;
         int ☃xx = ☃ * 16;
         BlockPos ☃xxx = new BlockPos(☃x, 90, ☃xx);
         Rotation ☃xxxx = Rotation.values()[☃.nextInt(Rotation.values().length)];
         TemplateManager ☃xxxxx = ☃.func_72860_G().func_186340_h();
         OceanRuinPieces.func_204041_a(☃xxxxx, ☃xxx, ☃xxxx, this.field_75075_a, ☃, ☃);
         this.func_202500_a(☃);
      }
   }

   public static enum Type {
      WARM,
      COLD;
   }
}
