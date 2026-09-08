package net.minecraft.world;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityPhantom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.IFluidState;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class PhantomSpawner {
   private int field_203233_a;

   public int func_203232_a(World var1, boolean var2, boolean var3) {
      if (!☃) {
         return 0;
      } else {
         Random ☃ = ☃.field_73012_v;
         --this.field_203233_a;
         if (this.field_203233_a > 0) {
            return 0;
         } else {
            this.field_203233_a += (60 + ☃.nextInt(60)) * 20;
            if (☃.func_175657_ab() < 5 && ☃.field_73011_w.func_191066_m()) {
               return 0;
            } else {
               int ☃ = 0;

               for(EntityPlayer ☃x : ☃.field_73010_i) {
                  if (!☃x.func_175149_v()) {
                     BlockPos ☃xx = new BlockPos(☃x);
                     if (!☃.field_73011_w.func_191066_m() || ☃xx.func_177956_o() >= ☃.func_181545_F() && ☃.func_175678_i(☃xx)) {
                        DifficultyInstance ☃xxx = ☃.func_175649_E(☃xx);
                        if (☃xxx.func_193845_a(☃.nextFloat() * 3.0F)) {
                           StatisticsManagerServer ☃xxxx = ((EntityPlayerMP)☃x).func_147099_x();
                           int ☃xxxxx = MathHelper.func_76125_a(
                              ☃xxxx.func_77444_a(StatList.field_199092_j.func_199076_b(StatList.field_203284_n)), 1, Integer.MAX_VALUE
                           );
                           int ☃xxxxxx = 24000;
                           if (☃.nextInt(☃xxxxx) >= 72000) {
                              BlockPos ☃xxxxxxx = ☃xx.func_177981_b(20 + ☃.nextInt(15)).func_177965_g(-10 + ☃.nextInt(21)).func_177970_e(-10 + ☃.nextInt(21));
                              IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃xxxxxxx);
                              IFluidState ☃xxxxxxxxx = ☃.func_204610_c(☃xxxxxxx);
                              if (WorldEntitySpawner.func_206851_a(☃xxxxxxxx, ☃xxxxxxxxx)) {
                                 IEntityLivingData ☃xxxxxxxxxx = null;
                                 int ☃xxxxxxxxxxx = 1 + ☃.nextInt(☃xxx.func_203095_a().func_151525_a() + 1);

                                 for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < ☃xxxxxxxxxxx; ++☃xxxxxxxxxxxx) {
                                    EntityPhantom ☃xxxxxxxxxxxxx = new EntityPhantom(☃);
                                    ☃xxxxxxxxxxxxx.func_174828_a(☃xxxxxxx, 0.0F, 0.0F);
                                    ☃xxxxxxxxxx = ☃xxxxxxxxxxxxx.func_204210_a(☃xxx, ☃xxxxxxxxxx, null);
                                    ☃.func_72838_d(☃xxxxxxxxxxxxx);
                                 }

                                 ☃ += ☃xxxxxxxxxxx;
                              }
                           }
                        }
                     }
                  }
               }

               return ☃;
            }
         }
      }
   }
}
