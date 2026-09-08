package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockTorchWall;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndPodiumFeature extends Feature<NoFeatureConfig> {
   public static final BlockPos field_186139_a = BlockPos.field_177992_a;
   private final boolean field_186141_c;

   public EndPodiumFeature(boolean var1) {
      this.field_186141_c = ☃;
   }

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      for(BlockPos.MutableBlockPos ☃ : BlockPos.func_177975_b(
         new BlockPos(☃.func_177958_n() - 4, ☃.func_177956_o() - 1, ☃.func_177952_p() - 4),
         new BlockPos(☃.func_177958_n() + 4, ☃.func_177956_o() + 32, ☃.func_177952_p() + 4)
      )) {
         double ☃x = ☃.func_185332_f(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
         if (☃x <= 3.5) {
            if (☃.func_177956_o() < ☃.func_177956_o()) {
               if (☃x <= 2.5) {
                  this.func_202278_a(☃, ☃, Blocks.field_150357_h.func_176223_P());
               } else if (☃.func_177956_o() < ☃.func_177956_o()) {
                  this.func_202278_a(☃, ☃, Blocks.field_150377_bs.func_176223_P());
               }
            } else if (☃.func_177956_o() > ☃.func_177956_o()) {
               this.func_202278_a(☃, ☃, Blocks.field_150350_a.func_176223_P());
            } else if (☃x > 2.5) {
               this.func_202278_a(☃, ☃, Blocks.field_150357_h.func_176223_P());
            } else if (this.field_186141_c) {
               this.func_202278_a(☃, new BlockPos(☃), Blocks.field_150384_bq.func_176223_P());
            } else {
               this.func_202278_a(☃, new BlockPos(☃), Blocks.field_150350_a.func_176223_P());
            }
         }
      }

      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.func_202278_a(☃, ☃.func_177981_b(☃), Blocks.field_150357_h.func_176223_P());
      }

      BlockPos ☃ = ☃.func_177981_b(2);

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         this.func_202278_a(☃, ☃.func_177972_a(☃x), Blocks.field_196591_bQ.func_176223_P().func_206870_a(BlockTorchWall.field_196532_a, ☃x));
      }

      return true;
   }
}
