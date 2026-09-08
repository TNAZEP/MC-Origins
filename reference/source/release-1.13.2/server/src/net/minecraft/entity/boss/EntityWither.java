package net.minecraft.entity.boss;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWither extends EntityMob implements IRangedAttackMob {
   private static final DataParameter<Integer> field_184741_a = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184742_b = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184743_c = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private static final List<DataParameter<Integer>> field_184745_bv = ImmutableList.of(field_184741_a, field_184742_b, field_184743_c);
   private static final DataParameter<Integer> field_184746_bw = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
   private final float[] field_82220_d = new float[2];
   private final float[] field_82221_e = new float[2];
   private final float[] field_82217_f = new float[2];
   private final float[] field_82218_g = new float[2];
   private final int[] field_82223_h = new int[2];
   private final int[] field_82224_i = new int[2];
   private int field_82222_j;
   private final BossInfoServer field_184744_bE = (BossInfoServer)new BossInfoServer(this.func_145748_c_(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)
      .func_186741_a(true);
   private static final Predicate<Entity> field_82219_bJ = var0 -> var0 instanceof EntityLivingBase
         && ((EntityLivingBase)var0).func_70668_bt() != CreatureAttribute.UNDEAD
         && ((EntityLivingBase)var0).func_190631_cK();

   public EntityWither(World var1) {
      super(EntityType.field_200760_az, ☃);
      this.func_70606_j(this.func_110138_aP());
      this.func_70105_a(0.9F, 3.5F);
      this.field_70178_ae = true;
      ((PathNavigateGround)this.func_70661_as()).func_212239_d(true);
      this.field_70728_aV = 50;
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityWither.AIDoNothing());
      this.field_70714_bg.func_75776_a(2, new EntityAIAttackRanged(this, 1.0, 40, 20.0F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, field_82219_bJ));
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184741_a, 0);
      this.field_70180_af.func_187214_a(field_184742_b, 0);
      this.field_70180_af.func_187214_a(field_184743_c, 0);
      this.field_70180_af.func_187214_a(field_184746_bw, 0);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Invul", this.func_82212_n());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_82215_s(☃.func_74762_e("Invul"));
      if (this.func_145818_k_()) {
         this.field_184744_bE.func_186739_a(this.func_145748_c_());
      }
   }

   @Override
   public void func_200203_b(@Nullable ITextComponent var1) {
      super.func_200203_b(☃);
      this.field_184744_bE.func_186739_a(this.func_145748_c_());
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187925_gy;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187851_gB;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187849_gA;
   }

   @Override
   public void func_70636_d() {
      this.field_70181_x *= 0.6F;
      if (!this.field_70170_p.field_72995_K && this.func_82203_t(0) > 0) {
         Entity ☃ = this.field_70170_p.func_73045_a(this.func_82203_t(0));
         if (☃ != null) {
            if (this.field_70163_u < ☃.field_70163_u || !this.func_82205_o() && this.field_70163_u < ☃.field_70163_u + 5.0) {
               if (this.field_70181_x < 0.0) {
                  this.field_70181_x = 0.0;
               }

               this.field_70181_x += (0.5 - this.field_70181_x) * 0.6F;
            }

            double ☃x = ☃.field_70165_t - this.field_70165_t;
            double ☃xx = ☃.field_70161_v - this.field_70161_v;
            double ☃xxx = ☃x * ☃x + ☃xx * ☃xx;
            if (☃xxx > 9.0) {
               double ☃xxxx = (double)MathHelper.func_76133_a(☃xxx);
               this.field_70159_w += (☃x / ☃xxxx * 0.5 - this.field_70159_w) * 0.6F;
               this.field_70179_y += (☃xx / ☃xxxx * 0.5 - this.field_70179_y) * 0.6F;
            }
         }
      }

      if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.05F) {
         this.field_70177_z = (float)MathHelper.func_181159_b(this.field_70179_y, this.field_70159_w) * (180.0F / (float)Math.PI) - 90.0F;
      }

      super.func_70636_d();

      for(int ☃ = 0; ☃ < 2; ++☃) {
         this.field_82218_g[☃] = this.field_82221_e[☃];
         this.field_82217_f[☃] = this.field_82220_d[☃];
      }

      for(int ☃ = 0; ☃ < 2; ++☃) {
         int ☃x = this.func_82203_t(☃ + 1);
         Entity ☃xx = null;
         if (☃x > 0) {
            ☃xx = this.field_70170_p.func_73045_a(☃x);
         }

         if (☃xx != null) {
            double ☃x = this.func_82214_u(☃ + 1);
            double ☃xx = this.func_82208_v(☃ + 1);
            double ☃xxx = this.func_82213_w(☃ + 1);
            double ☃xxxx = ☃xx.field_70165_t - ☃x;
            double ☃xxxxx = ☃xx.field_70163_u + (double)☃xx.func_70047_e() - ☃xx;
            double ☃xxxxxx = ☃xx.field_70161_v - ☃xxx;
            double ☃xxxxxxx = (double)MathHelper.func_76133_a(☃xxxx * ☃xxxx + ☃xxxxxx * ☃xxxxxx);
            float ☃xxxxxxxx = (float)(MathHelper.func_181159_b(☃xxxxxx, ☃xxxx) * 180.0F / (float)Math.PI) - 90.0F;
            float ☃xxxxxxxxx = (float)(-(MathHelper.func_181159_b(☃xxxxx, ☃xxxxxxx) * 180.0F / (float)Math.PI));
            this.field_82220_d[☃] = this.func_82204_b(this.field_82220_d[☃], ☃xxxxxxxxx, 40.0F);
            this.field_82221_e[☃] = this.func_82204_b(this.field_82221_e[☃], ☃xxxxxxxx, 10.0F);
         } else {
            this.field_82221_e[☃] = this.func_82204_b(this.field_82221_e[☃], this.field_70761_aq, 10.0F);
         }
      }

      boolean ☃ = this.func_82205_o();

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         double ☃xx = this.func_82214_u(☃x);
         double ☃xxx = this.func_82208_v(☃x);
         double ☃xxxx = this.func_82213_w(☃x);
         this.field_70170_p
            .func_195594_a(
               Particles.field_197601_L,
               ☃xx + this.field_70146_Z.nextGaussian() * 0.3F,
               ☃xxx + this.field_70146_Z.nextGaussian() * 0.3F,
               ☃xxxx + this.field_70146_Z.nextGaussian() * 0.3F,
               0.0,
               0.0,
               0.0
            );
         if (☃ && this.field_70170_p.field_73012_v.nextInt(4) == 0) {
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197625_r,
                  ☃xx + this.field_70146_Z.nextGaussian() * 0.3F,
                  ☃xxx + this.field_70146_Z.nextGaussian() * 0.3F,
                  ☃xxxx + this.field_70146_Z.nextGaussian() * 0.3F,
                  0.7F,
                  0.7F,
                  0.5
               );
         }
      }

      if (this.func_82212_n() > 0) {
         for(int ☃x = 0; ☃x < 3; ++☃x) {
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197625_r,
                  this.field_70165_t + this.field_70146_Z.nextGaussian(),
                  this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * 3.3F),
                  this.field_70161_v + this.field_70146_Z.nextGaussian(),
                  0.7F,
                  0.7F,
                  0.9F
               );
         }
      }
   }

   @Override
   protected void func_70619_bc() {
      if (this.func_82212_n() > 0) {
         int ☃ = this.func_82212_n() - 1;
         if (☃ <= 0) {
            this.field_70170_p
               .func_72885_a(
                  this,
                  this.field_70165_t,
                  this.field_70163_u + (double)this.func_70047_e(),
                  this.field_70161_v,
                  7.0F,
                  false,
                  this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")
               );
            this.field_70170_p.func_175669_a(1023, new BlockPos(this), 0);
         }

         this.func_82215_s(☃);
         if (this.field_70173_aa % 10 == 0) {
            this.func_70691_i(10.0F);
         }
      } else {
         super.func_70619_bc();

         for(int ☃ = 1; ☃ < 3; ++☃) {
            if (this.field_70173_aa >= this.field_82223_h[☃ - 1]) {
               this.field_82223_h[☃ - 1] = this.field_70173_aa + 10 + this.field_70146_Z.nextInt(10);
               if ((this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
                  && this.field_82224_i[☃ - 1]++ > 15) {
                  float ☃x = 10.0F;
                  float ☃xx = 5.0F;
                  double ☃xxx = MathHelper.func_82716_a(this.field_70146_Z, this.field_70165_t - 10.0, this.field_70165_t + 10.0);
                  double ☃xxxx = MathHelper.func_82716_a(this.field_70146_Z, this.field_70163_u - 5.0, this.field_70163_u + 5.0);
                  double ☃xxxxx = MathHelper.func_82716_a(this.field_70146_Z, this.field_70161_v - 10.0, this.field_70161_v + 10.0);
                  this.func_82209_a(☃ + 1, ☃xxx, ☃xxxx, ☃xxxxx, true);
                  this.field_82224_i[☃ - 1] = 0;
               }

               int ☃x = this.func_82203_t(☃);
               if (☃x > 0) {
                  Entity ☃xx = this.field_70170_p.func_73045_a(☃x);
                  if (☃xx == null || !☃xx.func_70089_S() || this.func_70068_e(☃xx) > 900.0 || !this.func_70685_l(☃xx)) {
                     this.func_82211_c(☃, 0);
                  } else if (☃xx instanceof EntityPlayer && ((EntityPlayer)☃xx).field_71075_bZ.field_75102_a) {
                     this.func_82211_c(☃, 0);
                  } else {
                     this.func_82216_a(☃ + 1, (EntityLivingBase)☃xx);
                     this.field_82223_h[☃ - 1] = this.field_70173_aa + 40 + this.field_70146_Z.nextInt(20);
                     this.field_82224_i[☃ - 1] = 0;
                  }
               } else {
                  List<EntityLivingBase> ☃x = this.field_70170_p
                     .func_175647_a(
                        EntityLivingBase.class, this.func_174813_aQ().func_72314_b(20.0, 8.0, 20.0), field_82219_bJ.and(EntitySelectors.field_180132_d)
                     );

                  for(int ☃xx = 0; ☃xx < 10 && !☃x.isEmpty(); ++☃xx) {
                     EntityLivingBase ☃xxx = (EntityLivingBase)☃x.get(this.field_70146_Z.nextInt(☃x.size()));
                     if (☃xxx != this && ☃xxx.func_70089_S() && this.func_70685_l(☃xxx)) {
                        if (☃xxx instanceof EntityPlayer) {
                           if (!((EntityPlayer)☃xxx).field_71075_bZ.field_75102_a) {
                              this.func_82211_c(☃, ☃xxx.func_145782_y());
                           }
                        } else {
                           this.func_82211_c(☃, ☃xxx.func_145782_y());
                        }
                        break;
                     }

                     ☃x.remove(☃xxx);
                  }
               }
            }
         }

         if (this.func_70638_az() != null) {
            this.func_82211_c(0, this.func_70638_az().func_145782_y());
         } else {
            this.func_82211_c(0, 0);
         }

         if (this.field_82222_j > 0) {
            --this.field_82222_j;
            if (this.field_82222_j == 0 && this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
               int ☃ = MathHelper.func_76128_c(this.field_70163_u);
               int ☃x = MathHelper.func_76128_c(this.field_70165_t);
               int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
               boolean ☃xxx = false;

               for(int ☃xxxx = -1; ☃xxxx <= 1; ++☃xxxx) {
                  for(int ☃xxxxx = -1; ☃xxxxx <= 1; ++☃xxxxx) {
                     for(int ☃xxxxxx = 0; ☃xxxxxx <= 3; ++☃xxxxxx) {
                        int ☃xxxxxxx = ☃x + ☃xxxx;
                        int ☃xxxxxxxx = ☃ + ☃xxxxxx;
                        int ☃xxxxxxxxx = ☃xx + ☃xxxxx;
                        BlockPos ☃xxxxxxxxxx = new BlockPos(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx);
                        IBlockState ☃xxxxxxxxxxx = this.field_70170_p.func_180495_p(☃xxxxxxxxxx);
                        Block ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx.func_177230_c();
                        if (!☃xxxxxxxxxxx.func_196958_f() && func_181033_a(☃xxxxxxxxxxxx)) {
                           ☃xxx = this.field_70170_p.func_175655_b(☃xxxxxxxxxx, true) || ☃xxx;
                        }
                     }
                  }
               }

               if (☃xxx) {
                  this.field_70170_p.func_180498_a(null, 1022, new BlockPos(this), 0);
               }
            }
         }

         if (this.field_70173_aa % 20 == 0) {
            this.func_70691_i(1.0F);
         }

         this.field_184744_bE.func_186735_a(this.func_110143_aJ() / this.func_110138_aP());
      }
   }

   public static boolean func_181033_a(Block var0) {
      return ☃ != Blocks.field_150357_h
         && ☃ != Blocks.field_150384_bq
         && ☃ != Blocks.field_150378_br
         && ☃ != Blocks.field_150483_bI
         && ☃ != Blocks.field_185776_dc
         && ☃ != Blocks.field_185777_dd
         && ☃ != Blocks.field_180401_cv
         && ☃ != Blocks.field_185779_df
         && ☃ != Blocks.field_189881_dj
         && ☃ != Blocks.field_196603_bb
         && ☃ != Blocks.field_185775_db;
   }

   public void func_82206_m() {
      this.func_82215_s(220);
      this.func_70606_j(this.func_110138_aP() / 3.0F);
   }

   @Override
   public void func_70110_aj() {
   }

   @Override
   public void func_184178_b(EntityPlayerMP var1) {
      super.func_184178_b(☃);
      this.field_184744_bE.func_186760_a(☃);
   }

   @Override
   public void func_184203_c(EntityPlayerMP var1) {
      super.func_184203_c(☃);
      this.field_184744_bE.func_186761_b(☃);
   }

   private double func_82214_u(int var1) {
      if (☃ <= 0) {
         return this.field_70165_t;
      } else {
         float ☃ = (this.field_70761_aq + (float)(180 * (☃ - 1))) * (float) (Math.PI / 180.0);
         float ☃x = MathHelper.func_76134_b(☃);
         return this.field_70165_t + (double)☃x * 1.3;
      }
   }

   private double func_82208_v(int var1) {
      return ☃ <= 0 ? this.field_70163_u + 3.0 : this.field_70163_u + 2.2;
   }

   private double func_82213_w(int var1) {
      if (☃ <= 0) {
         return this.field_70161_v;
      } else {
         float ☃ = (this.field_70761_aq + (float)(180 * (☃ - 1))) * (float) (Math.PI / 180.0);
         float ☃x = MathHelper.func_76126_a(☃);
         return this.field_70161_v + (double)☃x * 1.3;
      }
   }

   private float func_82204_b(float var1, float var2, float var3) {
      float ☃ = MathHelper.func_76142_g(☃ - ☃);
      if (☃ > ☃) {
         ☃ = ☃;
      }

      if (☃ < -☃) {
         ☃ = -☃;
      }

      return ☃ + ☃;
   }

   private void func_82216_a(int var1, EntityLivingBase var2) {
      this.func_82209_a(
         ☃, ☃.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e() * 0.5, ☃.field_70161_v, ☃ == 0 && this.field_70146_Z.nextFloat() < 0.001F
      );
   }

   private void func_82209_a(int var1, double var2, double var4, double var6, boolean var8) {
      this.field_70170_p.func_180498_a(null, 1024, new BlockPos(this), 0);
      double ☃ = this.func_82214_u(☃);
      double ☃x = this.func_82208_v(☃);
      double ☃xx = this.func_82213_w(☃);
      double ☃xxx = ☃ - ☃;
      double ☃xxxx = ☃ - ☃x;
      double ☃xxxxx = ☃ - ☃xx;
      EntityWitherSkull ☃xxxxxx = new EntityWitherSkull(this.field_70170_p, this, ☃xxx, ☃xxxx, ☃xxxxx);
      if (☃) {
         ☃xxxxxx.func_82343_e(true);
      }

      ☃xxxxxx.field_70163_u = ☃x;
      ☃xxxxxx.field_70165_t = ☃;
      ☃xxxxxx.field_70161_v = ☃xx;
      this.field_70170_p.func_72838_d(☃xxxxxx);
   }

   @Override
   public void func_82196_d(EntityLivingBase var1, float var2) {
      this.func_82216_a(0, ☃);
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (☃ == DamageSource.field_76369_e || ☃.func_76346_g() instanceof EntityWither) {
         return false;
      } else if (this.func_82212_n() > 0 && ☃ != DamageSource.field_76380_i) {
         return false;
      } else {
         if (this.func_82205_o()) {
            Entity ☃ = ☃.func_76364_f();
            if (☃ instanceof EntityArrow) {
               return false;
            }
         }

         Entity ☃ = ☃.func_76346_g();
         if (☃ != null && !(☃ instanceof EntityPlayer) && ☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70668_bt() == this.func_70668_bt()) {
            return false;
         } else {
            if (this.field_82222_j <= 0) {
               this.field_82222_j = 20;
            }

            for(int ☃ = 0; ☃ < this.field_82224_i.length; ++☃) {
               this.field_82224_i[☃] += 3;
            }

            return super.func_70097_a(☃, ☃);
         }
      }
   }

   @Override
   protected void func_70628_a(boolean var1, int var2) {
      EntityItem ☃ = this.func_199703_a(Items.field_151156_bN);
      if (☃ != null) {
         ☃.func_174873_u();
      }
   }

   @Override
   protected void func_70623_bb() {
      this.field_70708_bq = 0;
   }

   @Override
   public void func_180430_e(float var1, float var2) {
   }

   @Override
   public boolean func_195064_c(PotionEffect var1) {
      return false;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.6F);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0);
      this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(4.0);
   }

   public int func_82212_n() {
      return this.field_70180_af.func_187225_a(field_184746_bw);
   }

   public void func_82215_s(int var1) {
      this.field_70180_af.func_187227_b(field_184746_bw, ☃);
   }

   public int func_82203_t(int var1) {
      return this.field_70180_af.func_187225_a((DataParameter)field_184745_bv.get(☃));
   }

   public void func_82211_c(int var1, int var2) {
      this.field_70180_af.func_187227_b((DataParameter)field_184745_bv.get(☃), ☃);
   }

   public boolean func_82205_o() {
      return this.func_110143_aJ() <= this.func_110138_aP() / 2.0F;
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.UNDEAD;
   }

   @Override
   protected boolean func_184228_n(Entity var1) {
      return false;
   }

   @Override
   public boolean func_184222_aU() {
      return false;
   }

   @Override
   public void func_184724_a(boolean var1) {
   }

   class AIDoNothing extends EntityAIBase {
      public AIDoNothing() {
         this.func_75248_a(7);
      }

      @Override
      public boolean func_75250_a() {
         return EntityWither.this.func_82212_n() > 0;
      }
   }
}
