package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class DefaultSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   public DefaultSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
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
      this.apply(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getTopMaterial(), â˜ƒ.getUnderMaterial(), â˜ƒ.getUnderwaterMaterial(), â˜ƒ, â˜ƒ);
   }

   protected void apply(
      Random var1,
      ChunkAccess var2,
      Biome var3,
      int var4,
      int var5,
      int var6,
      double var7,
      BlockState var9,
      BlockState var10,
      BlockState var11,
      BlockState var12,
      BlockState var13,
      int var14,
      int var15
   ) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      int â˜ƒx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      if (â˜ƒx == 0) {
         boolean â˜ƒxx = false;

         for(int â˜ƒxxx = â˜ƒ; â˜ƒxxx >= â˜ƒ; --â˜ƒxxx) {
            â˜ƒ.set(â˜ƒ, â˜ƒxxx, â˜ƒ);
            BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒxxxx.isAir()) {
               â˜ƒxx = false;
            } else if (â˜ƒxxxx.is(â˜ƒ.getBlock())) {
               if (!â˜ƒxx) {
                  BlockState â˜ƒxxxx;
                  if (â˜ƒxxx >= â˜ƒ) {
                     â˜ƒxxxx = Blocks.AIR.defaultBlockState();
                  } else if (â˜ƒxxx == â˜ƒ - 1) {
                     â˜ƒxxxx = â˜ƒ.getTemperature(â˜ƒ) < 0.15F ? Blocks.ICE.defaultBlockState() : â˜ƒ;
                  } else if (â˜ƒxxx >= â˜ƒ - (7 + â˜ƒx)) {
                     â˜ƒxxxx = â˜ƒ;
                  } else {
                     â˜ƒxxxx = â˜ƒ;
                  }

                  â˜ƒ.setBlockState(â˜ƒ, â˜ƒxxxx, false);
               }

               â˜ƒxx = true;
            }
         }
      } else {
         BlockState â˜ƒ = â˜ƒ;
         int â˜ƒx = -1;

         for(int â˜ƒxx = â˜ƒ; â˜ƒxx >= â˜ƒ; --â˜ƒxx) {
            â˜ƒ.set(â˜ƒ, â˜ƒxx, â˜ƒ);
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒxxx.isAir()) {
               â˜ƒx = -1;
            } else if (â˜ƒxxx.is(â˜ƒ.getBlock())) {
               if (â˜ƒx == -1) {
                  â˜ƒx = â˜ƒx;
                  BlockState â˜ƒxxx;
                  if (â˜ƒxx >= â˜ƒ + 2) {
                     â˜ƒxxx = â˜ƒ;
                  } else if (â˜ƒxx >= â˜ƒ - 1) {
                     â˜ƒ = â˜ƒ;
                     â˜ƒxxx = â˜ƒ;
                  } else if (â˜ƒxx >= â˜ƒ - 4) {
                     â˜ƒ = â˜ƒ;
                     â˜ƒxxx = â˜ƒ;
                  } else if (â˜ƒxx >= â˜ƒ - (7 + â˜ƒx)) {
                     â˜ƒxxx = â˜ƒ;
                  } else {
                     â˜ƒ = â˜ƒ;
                     â˜ƒxxx = â˜ƒ;
                  }

                  â˜ƒ.setBlockState(â˜ƒ, â˜ƒxxx, false);
               } else if (â˜ƒx > 0) {
                  --â˜ƒx;
                  â˜ƒ.setBlockState(â˜ƒ, â˜ƒ, false);
                  if (â˜ƒx == 0 && â˜ƒ.is(Blocks.SAND) && â˜ƒx > 1) {
                     â˜ƒx = â˜ƒ.nextInt(4) + Math.max(0, â˜ƒxx - â˜ƒ);
                     â˜ƒ = â˜ƒ.is(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.defaultBlockState() : Blocks.SANDSTONE.defaultBlockState();
                  }
               }
            }
         }
      }
   }
}
