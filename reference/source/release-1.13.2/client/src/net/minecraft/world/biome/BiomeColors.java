package net.minecraft.world.biome;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class BiomeColors {
   private static final BiomeColors.ColorResolver field_180291_a = Biome::func_180627_b;
   private static final BiomeColors.ColorResolver field_180289_b = Biome::func_180625_c;
   private static final BiomeColors.ColorResolver field_180290_c = (var0, var1) -> var0.func_185361_o();
   private static final BiomeColors.ColorResolver field_204277_d = (var0, var1) -> var0.func_204274_p();

   private static int func_180285_a(IWorldReaderBase var0, BlockPos var1, BiomeColors.ColorResolver var2) {
      int ☃ = 0;
      int ☃x = 0;
      int ☃xx = 0;
      int ☃xxx = Minecraft.func_71410_x().field_71474_y.field_205217_U;
      int ☃xxxx = (☃xxx * 2 + 1) * (☃xxx * 2 + 1);

      for(BlockPos.MutableBlockPos ☃xxxxx : BlockPos.func_191531_b(
         ☃.func_177958_n() - ☃xxx, ☃.func_177956_o(), ☃.func_177952_p() - ☃xxx, ☃.func_177958_n() + ☃xxx, ☃.func_177956_o(), ☃.func_177952_p() + ☃xxx
      )) {
         int ☃xxxxxx = ☃.getColor(☃.func_180494_b(☃xxxxx), ☃xxxxx);
         ☃ += (☃xxxxxx & 0xFF0000) >> 16;
         ☃x += (☃xxxxxx & 0xFF00) >> 8;
         ☃xx += ☃xxxxxx & 0xFF;
      }

      return (☃ / ☃xxxx & 0xFF) << 16 | (☃x / ☃xxxx & 0xFF) << 8 | ☃xx / ☃xxxx & 0xFF;
   }

   public static int func_180286_a(IWorldReaderBase var0, BlockPos var1) {
      return func_180285_a(☃, ☃, field_180291_a);
   }

   public static int func_180287_b(IWorldReaderBase var0, BlockPos var1) {
      return func_180285_a(☃, ☃, field_180289_b);
   }

   public static int func_180288_c(IWorldReaderBase var0, BlockPos var1) {
      return func_180285_a(☃, ☃, field_180290_c);
   }

   interface ColorResolver {
      int getColor(Biome var1, BlockPos var2);
   }
}
