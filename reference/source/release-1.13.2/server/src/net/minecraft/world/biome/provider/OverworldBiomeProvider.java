package net.minecraft.world.biome.provider;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.storage.WorldInfo;

public class OverworldBiomeProvider extends BiomeProvider {
   private final BiomeCache field_201542_b = new BiomeCache(this);
   private final GenLayer field_201543_c;
   private final GenLayer field_201544_d;
   private final Biome[] field_205007_e = new Biome[]{
      Biomes.field_76771_b,
      Biomes.field_76772_c,
      Biomes.field_76769_d,
      Biomes.field_76770_e,
      Biomes.field_76767_f,
      Biomes.field_76768_g,
      Biomes.field_76780_h,
      Biomes.field_76781_i,
      Biomes.field_76776_l,
      Biomes.field_76777_m,
      Biomes.field_76774_n,
      Biomes.field_76775_o,
      Biomes.field_76789_p,
      Biomes.field_76788_q,
      Biomes.field_76787_r,
      Biomes.field_76786_s,
      Biomes.field_76785_t,
      Biomes.field_76784_u,
      Biomes.field_76783_v,
      Biomes.field_76782_w,
      Biomes.field_76792_x,
      Biomes.field_150574_L,
      Biomes.field_150575_M,
      Biomes.field_150576_N,
      Biomes.field_150577_O,
      Biomes.field_150583_P,
      Biomes.field_150582_Q,
      Biomes.field_150585_R,
      Biomes.field_150584_S,
      Biomes.field_150579_T,
      Biomes.field_150578_U,
      Biomes.field_150581_V,
      Biomes.field_150580_W,
      Biomes.field_150588_X,
      Biomes.field_150587_Y,
      Biomes.field_150589_Z,
      Biomes.field_150607_aa,
      Biomes.field_150608_ab,
      Biomes.field_203614_T,
      Biomes.field_203615_U,
      Biomes.field_203616_V,
      Biomes.field_203617_W,
      Biomes.field_203618_X,
      Biomes.field_203619_Y,
      Biomes.field_203620_Z,
      Biomes.field_185441_Q,
      Biomes.field_185442_R,
      Biomes.field_185443_S,
      Biomes.field_185444_T,
      Biomes.field_150590_f,
      Biomes.field_150599_m,
      Biomes.field_185445_W,
      Biomes.field_185446_X,
      Biomes.field_185447_Y,
      Biomes.field_185448_Z,
      Biomes.field_185429_aa,
      Biomes.field_185430_ab,
      Biomes.field_185431_ac,
      Biomes.field_185432_ad,
      Biomes.field_185433_ae,
      Biomes.field_185434_af,
      Biomes.field_185435_ag,
      Biomes.field_185436_ah,
      Biomes.field_185437_ai,
      Biomes.field_185438_aj,
      Biomes.field_185439_ak
   };

   public OverworldBiomeProvider(OverworldBiomeProviderSettings var1) {
      WorldInfo ☃ = ☃.func_205440_a();
      OverworldGenSettings ☃x = ☃.func_205442_b();
      GenLayer[] ☃xx = LayerUtil.func_202824_a(☃.func_76063_b(), ☃.func_76067_t(), ☃x);
      this.field_201543_c = ☃xx[0];
      this.field_201544_d = ☃xx[1];
   }

   @Nullable
   @Override
   public Biome func_180300_a(BlockPos var1, @Nullable Biome var2) {
      return this.field_201542_b.func_180284_a(☃.func_177958_n(), ☃.func_177952_p(), ☃);
   }

   @Override
   public Biome[] func_201535_a(int var1, int var2, int var3, int var4) {
      return this.field_201543_c.func_202833_a(☃, ☃, ☃, ☃, Biomes.field_180279_ad);
   }

   @Override
   public Biome[] func_201537_a(int var1, int var2, int var3, int var4, boolean var5) {
      return ☃ && ☃ == 16 && ☃ == 16 && (☃ & 15) == 0 && (☃ & 15) == 0
         ? this.field_201542_b.func_76839_e(☃, ☃)
         : this.field_201544_d.func_202833_a(☃, ☃, ☃, ☃, Biomes.field_180279_ad);
   }

   @Override
   public Set<Biome> func_201538_a(int var1, int var2, int var3) {
      int ☃ = ☃ - ☃ >> 2;
      int ☃x = ☃ - ☃ >> 2;
      int ☃xx = ☃ + ☃ >> 2;
      int ☃xxx = ☃ + ☃ >> 2;
      int ☃xxxx = ☃xx - ☃ + 1;
      int ☃xxxxx = ☃xxx - ☃x + 1;
      Set<Biome> ☃xxxxxx = Sets.<Biome>newHashSet();
      Collections.addAll(☃xxxxxx, this.field_201543_c.func_202833_a(☃, ☃x, ☃xxxx, ☃xxxxx, null));
      return ☃xxxxxx;
   }

   @Nullable
   @Override
   public BlockPos func_180630_a(int var1, int var2, int var3, List<Biome> var4, Random var5) {
      int ☃ = ☃ - ☃ >> 2;
      int ☃x = ☃ - ☃ >> 2;
      int ☃xx = ☃ + ☃ >> 2;
      int ☃xxx = ☃ + ☃ >> 2;
      int ☃xxxx = ☃xx - ☃ + 1;
      int ☃xxxxx = ☃xxx - ☃x + 1;
      Biome[] ☃xxxxxx = this.field_201543_c.func_202833_a(☃, ☃x, ☃xxxx, ☃xxxxx, null);
      BlockPos ☃xxxxxxx = null;
      int ☃xxxxxxxx = 0;

      for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxx * ☃xxxxx; ++☃xxxxxxxxx) {
         int ☃xxxxxxxxxx = ☃ + ☃xxxxxxxxx % ☃xxxx << 2;
         int ☃xxxxxxxxxxx = ☃x + ☃xxxxxxxxx / ☃xxxx << 2;
         if (☃.contains(☃xxxxxx[☃xxxxxxxxx])) {
            if (☃xxxxxxx == null || ☃.nextInt(☃xxxxxxxx + 1) == 0) {
               ☃xxxxxxx = new BlockPos(☃xxxxxxxxxx, 0, ☃xxxxxxxxxxx);
            }

            ++☃xxxxxxxx;
         }
      }

      return ☃xxxxxxx;
   }

   @Override
   public boolean func_205004_a(Structure<?> var1) {
      return this.field_205005_a.computeIfAbsent(☃, var1x -> {
         for(Biome ☃ : this.field_205007_e) {
            if (☃.func_201858_a(var1x)) {
               return true;
            }
         }

         return false;
      });
   }

   @Override
   public Set<IBlockState> func_205706_b() {
      if (this.field_205707_b.isEmpty()) {
         for(Biome ☃ : this.field_205007_e) {
            this.field_205707_b.add(☃.func_203944_q().func_204108_a());
         }
      }

      return this.field_205707_b;
   }

   @Override
   public void func_73660_a() {
      this.field_201542_b.func_76838_a();
   }
}
