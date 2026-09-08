package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndGateway extends BasePlacement<NoPlacementConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoPlacementConfig var5, Feature<C> var6, C var7
   ) {
      boolean ☃ = false;
      if (☃.nextInt(700) == 0) {
         int ☃x = ☃.nextInt(16);
         int ☃xx = ☃.nextInt(16);
         int ☃xxx = ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃x, 0, ☃xx)).func_177956_o();
         if (☃xxx > 0) {
            int ☃xxxx = ☃xxx + 3 + ☃.nextInt(7);
            BlockPos ☃xxxxx = ☃.func_177982_a(☃x, ☃xxxx, ☃xx);
            ☃.func_212245_a(☃, ☃, ☃, ☃xxxxx, ☃);
            TileEntity ☃xxxxxx = ☃.func_175625_s(☃);
            if (☃xxxxxx instanceof TileEntityEndGateway) {
               TileEntityEndGateway ☃xxxxxxx = (TileEntityEndGateway)☃xxxxxx;
               ☃xxxxxxx.func_195489_b(((ChunkGeneratorEnd)☃).func_202112_d());
            }
         }
      }

      return false;
   }
}
