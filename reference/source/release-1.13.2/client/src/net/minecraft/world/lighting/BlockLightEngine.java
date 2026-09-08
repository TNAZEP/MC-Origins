package net.minecraft.world.lighting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.WorldGenRegion;

public class BlockLightEngine extends BaseLightEngine {
   @Override
   public EnumLightType func_202657_a() {
      return EnumLightType.BLOCK;
   }

   public void func_202677_a(WorldGenRegion var1, IChunk var2) {
      for(BlockPos ☃ : ☃.func_201582_h()) {
         this.func_202667_a(☃, ☃, this.func_202670_c(☃, ☃));
         this.func_202659_a(☃.func_76632_l(), ☃, this.func_202666_a(☃, ☃));
      }

      this.func_202664_a(☃, ☃.func_76632_l());
   }
}
