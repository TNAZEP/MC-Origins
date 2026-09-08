package net.minecraft.world.gen.feature.structure;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class ShipwreckStructure extends ScatteredStructure<ShipwreckConfig> {
   @Override
   protected String func_143025_a() {
      return "Shipwreck";
   }

   @Override
   public int func_202367_b() {
      return 3;
   }

   @Override
   protected StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5) {
      Biome ☃ = ☃.func_202090_b().func_180300_a(new BlockPos((☃ << 4) + 9, 0, (☃ << 4) + 9), null);
      return new ShipwreckStructure.Start(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected int func_202382_c() {
      return 165745295;
   }

   @Override
   protected int func_204030_a(IChunkGenerator<?> var1) {
      return ☃.func_201496_a_().func_204748_h();
   }

   @Override
   protected int func_211745_b(IChunkGenerator<?> var1) {
      return ☃.func_201496_a_().func_211730_k();
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5, Biome var6) {
         super(☃, ☃, ☃, ☃, ☃.func_72905_C());
         ShipwreckConfig ☃ = (ShipwreckConfig)☃.func_202087_b(☃, Feature.field_204751_l);
         Rotation ☃x = Rotation.values()[☃.nextInt(Rotation.values().length)];
         BlockPos ☃xx = new BlockPos(☃ * 16, 90, ☃ * 16);
         ShipwreckPieces.func_204760_a(☃.func_72860_G().func_186340_h(), ☃xx, ☃x, this.field_75075_a, ☃, ☃);
         this.func_202500_a(☃);
      }
   }
}
