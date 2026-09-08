package net.minecraft.world.lighting;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;

public class SkyLightEngine extends BaseLightEngine {
   public static final EnumFacing[] field_202676_b = new EnumFacing[]{EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH};

   @Override
   public EnumLightType func_202657_a() {
      return EnumLightType.SKY;
   }

   public void func_202675_a(WorldGenRegion var1, IChunk var2) {
      int ☃ = ☃.func_76632_l().func_180334_c();
      int ☃x = ☃.func_76632_l().func_180333_d();

      try (
         BlockPos.PooledMutableBlockPos ☃xx = BlockPos.PooledMutableBlockPos.func_185346_s();
         BlockPos.PooledMutableBlockPos ☃xxx = BlockPos.PooledMutableBlockPos.func_185346_s();
      ) {
         for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
            for(int ☃xxxxx = 0; ☃xxxxx < 16; ++☃xxxxx) {
               int ☃xxxxxx = ☃.func_201576_a(Heightmap.Type.LIGHT_BLOCKING, ☃xxxx, ☃xxxxx) + 1;
               int ☃xxxxxxx = ☃xxxx + ☃;
               int ☃xxxxxxxx = ☃xxxxx + ☃x;

               for(int ☃xxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxx < ☃.func_76587_i().length * 16 - 1; ++☃xxxxxxxxx) {
                  ☃xx.func_181079_c(☃xxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxx);
                  this.func_202667_a(☃, ☃xx, 15);
               }

               this.func_202669_a(☃.func_76632_l(), ☃xxxxxxx, ☃xxxxxx, ☃xxxxxxxx, 15);

               for(EnumFacing ☃xxxxxxxxx : field_202676_b) {
                  int ☃xxxxxxxxxx = ☃.func_201676_a(Heightmap.Type.LIGHT_BLOCKING, ☃xxxxxxx + ☃xxxxxxxxx.func_82601_c(), ☃xxxxxxxx + ☃xxxxxxxxx.func_82599_e());
                  if (☃xxxxxxxxxx - ☃xxxxxx >= 2) {
                     for(int ☃xxxxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxxxx <= ☃xxxxxxxxxx; ++☃xxxxxxxxxxx) {
                        ☃xxx.func_181079_c(☃xxxxxxx + ☃xxxxxxxxx.func_82601_c(), ☃xxxxxxxxxxx, ☃xxxxxxxx + ☃xxxxxxxxx.func_82599_e());
                        int ☃xxxxxxxxxxxx = ☃.func_180495_p(☃xxx).func_200016_a(☃, ☃xxx);
                        if (☃xxxxxxxxxxxx != ☃.func_201572_C()) {
                           this.func_202667_a(☃, ☃xxx, 15 - ☃xxxxxxxxxxxx - 1);
                           this.func_202659_a(☃.func_76632_l(), ☃xxx, 15 - ☃xxxxxxxxxxxx - 1);
                        }
                     }
                  }
               }
            }
         }

         this.func_202664_a(☃, ☃.func_76632_l());
      }
   }
}
