package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndGatewayFeature extends Feature<EndGatewayConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, EndGatewayConfig var5) {
      for(BlockPos.MutableBlockPos ☃ : BlockPos.func_177975_b(☃.func_177982_a(-1, -2, -1), ☃.func_177982_a(1, 2, 1))) {
         boolean ☃x = ☃.func_177958_n() == ☃.func_177958_n();
         boolean ☃xx = ☃.func_177956_o() == ☃.func_177956_o();
         boolean ☃xxx = ☃.func_177952_p() == ☃.func_177952_p();
         boolean ☃xxxx = Math.abs(☃.func_177956_o() - ☃.func_177956_o()) == 2;
         if (☃x && ☃xx && ☃xxx) {
            BlockPos ☃xxxxx = ☃.func_185334_h();
            this.func_202278_a(☃, ☃xxxxx, Blocks.field_185775_db.func_176223_P());
            if (☃.func_209959_a()) {
               TileEntity ☃xxxxxx = ☃.func_175625_s(☃xxxxx);
               if (☃xxxxxx instanceof TileEntityEndGateway) {
                  TileEntityEndGateway ☃xxxxxxx = (TileEntityEndGateway)☃xxxxxx;
                  ☃xxxxxxx.func_195489_b(EndDimension.field_209958_g);
               }
            }
         } else if (☃xx) {
            this.func_202278_a(☃, ☃, Blocks.field_150350_a.func_176223_P());
         } else if (☃xxxx && ☃x && ☃xxx) {
            this.func_202278_a(☃, ☃, Blocks.field_150357_h.func_176223_P());
         } else if ((☃x || ☃xxx) && !☃xxxx) {
            this.func_202278_a(☃, ☃, Blocks.field_150357_h.func_176223_P());
         } else {
            this.func_202278_a(☃, ☃, Blocks.field_150350_a.func_176223_P());
         }
      }

      return true;
   }
}
