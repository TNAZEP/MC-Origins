package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Explosion {
   private final boolean field_77286_a;
   private final boolean field_82755_b;
   private final Random field_77290_i = new Random();
   private final World field_77287_j;
   private final double field_77284_b;
   private final double field_77285_c;
   private final double field_77282_d;
   private final Entity field_77283_e;
   private final float field_77280_f;
   private DamageSource field_199593_j;
   private final List<BlockPos> field_77281_g = Lists.<BlockPos>newArrayList();
   private final Map<EntityPlayer, Vec3d> field_77288_k = Maps.<EntityPlayer, Vec3d>newHashMap();

   public Explosion(World var1, @Nullable Entity var2, double var3, double var5, double var7, float var9, boolean var10, boolean var11) {
      this.field_77287_j = ☃;
      this.field_77283_e = ☃;
      this.field_77280_f = ☃;
      this.field_77284_b = ☃;
      this.field_77285_c = ☃;
      this.field_77282_d = ☃;
      this.field_77286_a = ☃;
      this.field_82755_b = ☃;
      this.field_199593_j = DamageSource.func_94539_a(this);
   }

   public void func_77278_a() {
      Set<BlockPos> ☃ = Sets.<BlockPos>newHashSet();
      int ☃x = 16;

      for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
            for(int ☃xxxx = 0; ☃xxxx < 16; ++☃xxxx) {
               if (☃xx == 0 || ☃xx == 15 || ☃xxx == 0 || ☃xxx == 15 || ☃xxxx == 0 || ☃xxxx == 15) {
                  double ☃xxxxx = (double)((float)☃xx / 15.0F * 2.0F - 1.0F);
                  double ☃xxxxxx = (double)((float)☃xxx / 15.0F * 2.0F - 1.0F);
                  double ☃xxxxxxx = (double)((float)☃xxxx / 15.0F * 2.0F - 1.0F);
                  double ☃xxxxxxxx = Math.sqrt(☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx + ☃xxxxxxx * ☃xxxxxxx);
                  ☃xxxxx /= ☃xxxxxxxx;
                  ☃xxxxxx /= ☃xxxxxxxx;
                  ☃xxxxxxx /= ☃xxxxxxxx;
                  float ☃xxxxxxxxx = this.field_77280_f * (0.7F + this.field_77287_j.field_73012_v.nextFloat() * 0.6F);
                  double ☃xxxxxxxxxx = this.field_77284_b;
                  double ☃xxxxxxxxxxx = this.field_77285_c;
                  double ☃xxxxxxxxxxxx = this.field_77282_d;

                  for(float ☃xxxxxxxxxxxxx = 0.3F; ☃xxxxxxxxx > 0.0F; ☃xxxxxxxxx -= 0.22500001F) {
                     BlockPos ☃xxxxxxxxxxxxxx = new BlockPos(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
                     IBlockState ☃xxxxxxxxxxxxxxx = this.field_77287_j.func_180495_p(☃xxxxxxxxxxxxxx);
                     IFluidState ☃xxxxxxxxxxxxxxxx = this.field_77287_j.func_204610_c(☃xxxxxxxxxxxxxx);
                     if (!☃xxxxxxxxxxxxxxx.func_196958_f() || !☃xxxxxxxxxxxxxxxx.func_206888_e()) {
                        float ☃xxxxxxxxxxxxxxxxx = Math.max(☃xxxxxxxxxxxxxxx.func_177230_c().func_149638_a(), ☃xxxxxxxxxxxxxxxx.func_210200_l());
                        if (this.field_77283_e != null) {
                           ☃xxxxxxxxxxxxxxxxx = this.field_77283_e
                              .func_180428_a(this, this.field_77287_j, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx);
                        }

                        ☃xxxxxxxxx -= (☃xxxxxxxxxxxxxxxxx + 0.3F) * 0.3F;
                     }

                     if (☃xxxxxxxxx > 0.0F
                        && (
                           this.field_77283_e == null
                              || this.field_77283_e.func_174816_a(this, this.field_77287_j, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxx)
                        )) {
                        ☃.add(☃xxxxxxxxxxxxxx);
                     }

                     ☃xxxxxxxxxx += ☃xxxxx * 0.3F;
                     ☃xxxxxxxxxxx += ☃xxxxxx * 0.3F;
                     ☃xxxxxxxxxxxx += ☃xxxxxxx * 0.3F;
                  }
               }
            }
         }
      }

      this.field_77281_g.addAll(☃);
      float ☃xx = this.field_77280_f * 2.0F;
      int ☃xxx = MathHelper.func_76128_c(this.field_77284_b - (double)☃xx - 1.0);
      int ☃xxxx = MathHelper.func_76128_c(this.field_77284_b + (double)☃xx + 1.0);
      int ☃xxxxx = MathHelper.func_76128_c(this.field_77285_c - (double)☃xx - 1.0);
      int ☃xxxxxx = MathHelper.func_76128_c(this.field_77285_c + (double)☃xx + 1.0);
      int ☃xxxxxxx = MathHelper.func_76128_c(this.field_77282_d - (double)☃xx - 1.0);
      int ☃xxxxxxxx = MathHelper.func_76128_c(this.field_77282_d + (double)☃xx + 1.0);
      List<Entity> ☃xxxxxxxxx = this.field_77287_j
         .func_72839_b(this.field_77283_e, new AxisAlignedBB((double)☃xxx, (double)☃xxxxx, (double)☃xxxxxxx, (double)☃xxxx, (double)☃xxxxxx, (double)☃xxxxxxxx));
      Vec3d ☃xxxxxxxxxx = new Vec3d(this.field_77284_b, this.field_77285_c, this.field_77282_d);

      for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < ☃xxxxxxxxx.size(); ++☃xxxxxxxxxxx) {
         Entity ☃xxxxxxxxxxxx = (Entity)☃xxxxxxxxx.get(☃xxxxxxxxxxx);
         if (!☃xxxxxxxxxxxx.func_180427_aV()) {
            double ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_70011_f(this.field_77284_b, this.field_77285_c, this.field_77282_d) / (double)☃xx;
            if (☃xxxxxxxxxxxxx <= 1.0) {
               double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxx.field_70165_t - this.field_77284_b;
               double ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx.field_70163_u + (double)☃xxxxxxxxxxxx.func_70047_e() - this.field_77285_c;
               double ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx.field_70161_v - this.field_77282_d;
               double ☃xxxxxxxxxxxxxxxxx = (double)MathHelper.func_76133_a(
                  ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx
               );
               if (☃xxxxxxxxxxxxxxxxx != 0.0) {
                  ☃xxxxxxxxxxxxxx /= ☃xxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxxxx /= ☃xxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxxxxx /= ☃xxxxxxxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxx = (double)this.field_77287_j.func_72842_a(☃xxxxxxxxxx, ☃xxxxxxxxxxxx.func_174813_aQ());
                  double ☃xxxxxxxxxxxxxxxxxxx = (1.0 - ☃xxxxxxxxxxxxx) * ☃xxxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxx.func_70097_a(
                     this.func_199591_b(), (float)((int)((☃xxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxx) / 2.0 * 7.0 * (double)☃xx + 1.0))
                  );
                  double ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx;
                  if (☃xxxxxxxxxxxx instanceof EntityLivingBase) {
                     ☃xxxxxxxxxxxxxxxxxxxx = EnchantmentProtection.func_92092_a((EntityLivingBase)☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx);
                  }

                  ☃xxxxxxxxxxxx.field_70159_w += ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxx.field_70181_x += ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxx.field_70179_y += ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxx;
                  if (☃xxxxxxxxxxxx instanceof EntityPlayer) {
                     EntityPlayer ☃xxxxxxxxxxxxxxxxxx = (EntityPlayer)☃xxxxxxxxxxxx;
                     if (!☃xxxxxxxxxxxxxxxxxx.func_175149_v() && (!☃xxxxxxxxxxxxxxxxxx.func_184812_l_() || !☃xxxxxxxxxxxxxxxxxx.field_71075_bZ.field_75100_b)) {
                        this.field_77288_k
                           .put(
                              ☃xxxxxxxxxxxxxxxxxx,
                              new Vec3d(
                                 ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxx
                              )
                           );
                     }
                  }
               }
            }
         }
      }
   }

   public void func_77279_a(boolean var1) {
      this.field_77287_j
         .func_184148_a(
            null,
            this.field_77284_b,
            this.field_77285_c,
            this.field_77282_d,
            SoundEvents.field_187539_bB,
            SoundCategory.BLOCKS,
            4.0F,
            (1.0F + (this.field_77287_j.field_73012_v.nextFloat() - this.field_77287_j.field_73012_v.nextFloat()) * 0.2F) * 0.7F
         );
      if (!(this.field_77280_f < 2.0F) && this.field_82755_b) {
         this.field_77287_j.func_195594_a(Particles.field_197626_s, this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0, 0.0, 0.0);
      } else {
         this.field_77287_j.func_195594_a(Particles.field_197627_t, this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0, 0.0, 0.0);
      }

      if (this.field_82755_b) {
         for(BlockPos ☃ : this.field_77281_g) {
            IBlockState ☃x = this.field_77287_j.func_180495_p(☃);
            Block ☃xx = ☃x.func_177230_c();
            if (☃) {
               double ☃xxx = (double)((float)☃.func_177958_n() + this.field_77287_j.field_73012_v.nextFloat());
               double ☃xxxx = (double)((float)☃.func_177956_o() + this.field_77287_j.field_73012_v.nextFloat());
               double ☃xxxxx = (double)((float)☃.func_177952_p() + this.field_77287_j.field_73012_v.nextFloat());
               double ☃xxxxxx = ☃xxx - this.field_77284_b;
               double ☃xxxxxxx = ☃xxxx - this.field_77285_c;
               double ☃xxxxxxxx = ☃xxxxx - this.field_77282_d;
               double ☃xxxxxxxxx = (double)MathHelper.func_76133_a(☃xxxxxx * ☃xxxxxx + ☃xxxxxxx * ☃xxxxxxx + ☃xxxxxxxx * ☃xxxxxxxx);
               ☃xxxxxx /= ☃xxxxxxxxx;
               ☃xxxxxxx /= ☃xxxxxxxxx;
               ☃xxxxxxxx /= ☃xxxxxxxxx;
               double ☃xxxxxxxxxx = 0.5 / (☃xxxxxxxxx / (double)this.field_77280_f + 0.1);
               ☃xxxxxxxxxx *= (double)(this.field_77287_j.field_73012_v.nextFloat() * this.field_77287_j.field_73012_v.nextFloat() + 0.3F);
               ☃xxxxxx *= ☃xxxxxxxxxx;
               ☃xxxxxxx *= ☃xxxxxxxxxx;
               ☃xxxxxxxx *= ☃xxxxxxxxxx;
               this.field_77287_j
                  .func_195594_a(
                     Particles.field_197598_I,
                     (☃xxx + this.field_77284_b) / 2.0,
                     (☃xxxx + this.field_77285_c) / 2.0,
                     (☃xxxxx + this.field_77282_d) / 2.0,
                     ☃xxxxxx,
                     ☃xxxxxxx,
                     ☃xxxxxxxx
                  );
               this.field_77287_j.func_195594_a(Particles.field_197601_L, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
            }

            if (!☃x.func_196958_f()) {
               if (☃xx.func_149659_a(this)) {
                  ☃x.func_196941_a(this.field_77287_j, ☃, 1.0F / this.field_77280_f, 0);
               }

               this.field_77287_j.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 3);
               ☃xx.func_180652_a(this.field_77287_j, ☃, this);
            }
         }
      }

      if (this.field_77286_a) {
         for(BlockPos ☃ : this.field_77281_g) {
            if (this.field_77287_j.func_180495_p(☃).func_196958_f()
               && this.field_77287_j.func_180495_p(☃.func_177977_b()).func_200015_d(this.field_77287_j, ☃.func_177977_b())
               && this.field_77290_i.nextInt(3) == 0) {
               this.field_77287_j.func_175656_a(☃, Blocks.field_150480_ab.func_176223_P());
            }
         }
      }
   }

   public DamageSource func_199591_b() {
      return this.field_199593_j;
   }

   public void func_199592_a(DamageSource var1) {
      this.field_199593_j = ☃;
   }

   public Map<EntityPlayer, Vec3d> func_77277_b() {
      return this.field_77288_k;
   }

   @Nullable
   public EntityLivingBase func_94613_c() {
      if (this.field_77283_e == null) {
         return null;
      } else if (this.field_77283_e instanceof EntityTNTPrimed) {
         return ((EntityTNTPrimed)this.field_77283_e).func_94083_c();
      } else {
         return this.field_77283_e instanceof EntityLivingBase ? (EntityLivingBase)this.field_77283_e : null;
      }
   }

   public void func_180342_d() {
      this.field_77281_g.clear();
   }

   public List<BlockPos> func_180343_e() {
      return this.field_77281_g;
   }
}
