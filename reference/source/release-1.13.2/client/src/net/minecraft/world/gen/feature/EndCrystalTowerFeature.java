package net.minecraft.world.gen.feature;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndCrystalTowerFeature extends Feature<NoFeatureConfig> {
   private boolean field_186145_a;
   private EndCrystalTowerFeature.EndSpike field_186146_b;
   private BlockPos field_186147_c;

   public void func_186143_a(EndCrystalTowerFeature.EndSpike var1) {
      this.field_186146_b = ☃;
   }

   public void func_186144_a(boolean var1) {
      this.field_186145_a = ☃;
   }

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      if (this.field_186146_b == null) {
         throw new IllegalStateException("Decoration requires priming with a spike");
      } else {
         int ☃ = this.field_186146_b.func_186148_c();

         for(BlockPos.MutableBlockPos ☃x : BlockPos.func_177975_b(
            new BlockPos(☃.func_177958_n() - ☃, 0, ☃.func_177952_p() - ☃),
            new BlockPos(☃.func_177958_n() + ☃, this.field_186146_b.func_186149_d() + 10, ☃.func_177952_p() + ☃)
         )) {
            if (☃x.func_177954_c((double)☃.func_177958_n(), (double)☃x.func_177956_o(), (double)☃.func_177952_p()) <= (double)(☃ * ☃ + 1)
               && ☃x.func_177956_o() < this.field_186146_b.func_186149_d()) {
               this.func_202278_a(☃, ☃x, Blocks.field_150343_Z.func_176223_P());
            } else if (☃x.func_177956_o() > 65) {
               this.func_202278_a(☃, ☃x, Blocks.field_150350_a.func_176223_P());
            }
         }

         if (this.field_186146_b.func_186150_e()) {
            int ☃x = -2;
            int ☃xx = 2;
            int ☃xxx = 3;
            BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxxx = -2; ☃xxxxx <= 2; ++☃xxxxx) {
               for(int ☃xxxxxx = -2; ☃xxxxxx <= 2; ++☃xxxxxx) {
                  for(int ☃xxxxxxx = 0; ☃xxxxxxx <= 3; ++☃xxxxxxx) {
                     boolean ☃xxxxxxxx = MathHelper.func_76130_a(☃xxxxx) == 2;
                     boolean ☃xxxxxxxxx = MathHelper.func_76130_a(☃xxxxxx) == 2;
                     boolean ☃xxxxxxxxxx = ☃xxxxxxx == 3;
                     if (☃xxxxxxxx || ☃xxxxxxxxx || ☃xxxxxxxxxx) {
                        boolean ☃xxxxxxxxxxx = ☃xxxxx == -2 || ☃xxxxx == 2 || ☃xxxxxxxxxx;
                        boolean ☃xxxxxxxxxxxx = ☃xxxxxx == -2 || ☃xxxxxx == 2 || ☃xxxxxxxxxx;
                        IBlockState ☃xxxxxxxxxxxxx = Blocks.field_150411_aY
                           .func_176223_P()
                           .func_206870_a(BlockPane.field_196409_a, Boolean.valueOf(☃xxxxxxxxxxx && ☃xxxxxx != -2))
                           .func_206870_a(BlockPane.field_196413_c, Boolean.valueOf(☃xxxxxxxxxxx && ☃xxxxxx != 2))
                           .func_206870_a(BlockPane.field_196414_y, Boolean.valueOf(☃xxxxxxxxxxxx && ☃xxxxx != -2))
                           .func_206870_a(BlockPane.field_196411_b, Boolean.valueOf(☃xxxxxxxxxxxx && ☃xxxxx != 2));
                        this.func_202278_a(
                           ☃,
                           ☃xxxx.func_181079_c(☃.func_177958_n() + ☃xxxxx, this.field_186146_b.func_186149_d() + ☃xxxxxxx, ☃.func_177952_p() + ☃xxxxxx),
                           ☃xxxxxxxxxxxxx
                        );
                     }
                  }
               }
            }
         }

         EntityEnderCrystal ☃x = new EntityEnderCrystal(☃.func_201672_e());
         ☃x.func_184516_a(this.field_186147_c);
         ☃x.func_184224_h(this.field_186145_a);
         ☃x.func_70012_b(
            (double)((float)☃.func_177958_n() + 0.5F),
            (double)(this.field_186146_b.func_186149_d() + 1),
            (double)((float)☃.func_177952_p() + 0.5F),
            ☃.nextFloat() * 360.0F,
            0.0F
         );
         ☃.func_72838_d(☃x);
         this.func_202278_a(☃, new BlockPos(☃.func_177958_n(), this.field_186146_b.func_186149_d(), ☃.func_177952_p()), Blocks.field_150357_h.func_176223_P());
         return true;
      }
   }

   public void func_186142_a(@Nullable BlockPos var1) {
      this.field_186147_c = ☃;
   }

   public static class EndSpike {
      private final int field_186155_a;
      private final int field_186156_b;
      private final int field_186157_c;
      private final int field_186158_d;
      private final boolean field_186159_e;
      private final AxisAlignedBB field_186160_f;

      public EndSpike(int var1, int var2, int var3, int var4, boolean var5) {
         this.field_186155_a = ☃;
         this.field_186156_b = ☃;
         this.field_186157_c = ☃;
         this.field_186158_d = ☃;
         this.field_186159_e = ☃;
         this.field_186160_f = new AxisAlignedBB((double)(☃ - ☃), 0.0, (double)(☃ - ☃), (double)(☃ + ☃), 256.0, (double)(☃ + ☃));
      }

      public boolean func_186154_a(BlockPos var1) {
         int ☃ = this.field_186155_a - this.field_186157_c;
         int ☃x = this.field_186156_b - this.field_186157_c;
         return ☃.func_177958_n() == (☃ & -16) && ☃.func_177952_p() == (☃x & -16);
      }

      public int func_186151_a() {
         return this.field_186155_a;
      }

      public int func_186152_b() {
         return this.field_186156_b;
      }

      public int func_186148_c() {
         return this.field_186157_c;
      }

      public int func_186149_d() {
         return this.field_186158_d;
      }

      public boolean func_186150_e() {
         return this.field_186159_e;
      }

      public AxisAlignedBB func_186153_f() {
         return this.field_186160_f;
      }
   }
}
