package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class SwampSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   public SwampSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
      super(â˜ƒ);
   }

   public void apply(
      Random var1,
      ChunkAccess var2,
      Biome var3,
      int var4,
      int var5,
      int var6,
      double var7,
      BlockState var9,
      BlockState var10,
      int var11,
      int var12,
      long var13,
      SurfaceBuilderBaseConfiguration var15
   ) {
      double â˜ƒ = Biome.BIOME_INFO_NOISE.getValue((double)â˜ƒ * 0.25, (double)â˜ƒ * 0.25, false);
      if (â˜ƒ > 0.0) {
         int â˜ƒx = â˜ƒ & 15;
         int â˜ƒxx = â˜ƒ & 15;
         BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxx = â˜ƒ; â˜ƒxxxx >= â˜ƒ; --â˜ƒxxxx) {
            â˜ƒxxx.set(â˜ƒx, â˜ƒxxxx, â˜ƒxx);
            if (!â˜ƒ.getBlockState(â˜ƒxxx).isAir()) {
               if (â˜ƒxxxx == 62 && !â˜ƒ.getBlockState(â˜ƒxxx).is(â˜ƒ.getBlock())) {
                  â˜ƒ.setBlockState(â˜ƒxxx, â˜ƒ, false);
               }
               break;
            }
         }
      }

      SurfaceBuilder.DEFAULT.apply(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
