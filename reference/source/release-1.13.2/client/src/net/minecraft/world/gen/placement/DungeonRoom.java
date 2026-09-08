package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class DungeonRoom extends BasePlacement<DungeonRoomConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, DungeonRoomConfig var5, Feature<C> var6, C var7
   ) {
      int ☃ = ☃.field_202487_a;

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         int ☃xx = ☃.nextInt(16);
         int ☃xxx = ☃.nextInt(☃.func_207511_e());
         int ☃xxxx = ☃.nextInt(16);
         ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃xx, ☃xxx, ☃xxxx), ☃);
      }

      return true;
   }
}
