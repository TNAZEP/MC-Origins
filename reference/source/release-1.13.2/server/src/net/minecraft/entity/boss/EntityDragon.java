package net.minecraft.entity.boss;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityDragon extends EntityLiving implements IEntityMultiPart, IMob {
   private static final Logger field_184675_bH = LogManager.getLogger();
   public static final DataParameter<Integer> field_184674_a = EntityDataManager.func_187226_a(EntityDragon.class, DataSerializers.field_187192_b);
   public double[][] field_70979_e = new double[64][3];
   public int field_70976_f = -1;
   public MultiPartEntityPart[] field_70977_g;
   public MultiPartEntityPart field_70986_h;
   public MultiPartEntityPart field_184673_bv;
   public MultiPartEntityPart field_70987_i;
   public MultiPartEntityPart field_70985_j;
   public MultiPartEntityPart field_70984_by;
   public MultiPartEntityPart field_70982_bz;
   public MultiPartEntityPart field_70983_bA;
   public MultiPartEntityPart field_70990_bB;
   public float field_70991_bC;
   public float field_70988_bD;
   public boolean field_70994_bF;
   public int field_70995_bG;
   public EntityEnderCrystal field_70992_bH;
   private final DragonFightManager field_184676_bI;
   private final PhaseManager field_184677_bJ;
   private int field_184678_bK = 100;
   private int field_184679_bL;
   private final PathPoint[] field_184680_bM = new PathPoint[24];
   private final int[] field_184681_bN = new int[24];
   private final PathHeap field_184682_bO = new PathHeap();

   public EntityDragon(World var1) {
      super(EntityType.field_200802_p, ☃);
      this.field_70986_h = new MultiPartEntityPart(this, "head", 6.0F, 6.0F);
      this.field_184673_bv = new MultiPartEntityPart(this, "neck", 6.0F, 6.0F);
      this.field_70987_i = new MultiPartEntityPart(this, "body", 8.0F, 8.0F);
      this.field_70985_j = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
      this.field_70984_by = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
      this.field_70982_bz = new MultiPartEntityPart(this, "tail", 4.0F, 4.0F);
      this.field_70983_bA = new MultiPartEntityPart(this, "wing", 4.0F, 4.0F);
      this.field_70990_bB = new MultiPartEntityPart(this, "wing", 4.0F, 4.0F);
      this.field_70977_g = new MultiPartEntityPart[]{
         this.field_70986_h,
         this.field_184673_bv,
         this.field_70987_i,
         this.field_70985_j,
         this.field_70984_by,
         this.field_70982_bz,
         this.field_70983_bA,
         this.field_70990_bB
      };
      this.func_70606_j(this.func_110138_aP());
      this.func_70105_a(16.0F, 8.0F);
      this.field_70145_X = true;
      this.field_70178_ae = true;
      this.field_70158_ak = true;
      if (!☃.field_72995_K && ☃.field_73011_w instanceof EndDimension) {
         this.field_184676_bI = ((EndDimension)☃.field_73011_w).func_186063_s();
      } else {
         this.field_184676_bI = null;
      }

      this.field_184677_bJ = new PhaseManager(this);
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.func_184212_Q().func_187214_a(field_184674_a, PhaseType.field_188751_k.func_188740_b());
   }

   public double[] func_70974_a(int var1, float var2) {
      if (this.func_110143_aJ() <= 0.0F) {
         ☃ = 0.0F;
      }

      ☃ = 1.0F - ☃;
      int ☃ = this.field_70976_f - ☃ & 63;
      int ☃x = this.field_70976_f - ☃ - 1 & 63;
      double[] ☃xx = new double[3];
      double ☃xxx = this.field_70979_e[☃][0];
      double ☃xxxx = MathHelper.func_76138_g(this.field_70979_e[☃x][0] - ☃xxx);
      ☃xx[0] = ☃xxx + ☃xxxx * (double)☃;
      ☃xxx = this.field_70979_e[☃][1];
      ☃xxxx = this.field_70979_e[☃x][1] - ☃xxx;
      ☃xx[1] = ☃xxx + ☃xxxx * (double)☃;
      ☃xx[2] = this.field_70979_e[☃][2] + (this.field_70979_e[☃x][2] - this.field_70979_e[☃][2]) * (double)☃;
      return ☃xx;
   }

   @Override
   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         this.func_70606_j(this.func_110143_aJ());
         if (!this.func_174814_R()) {
            float ☃ = MathHelper.func_76134_b(this.field_70988_bD * (float) (Math.PI * 2));
            float ☃x = MathHelper.func_76134_b(this.field_70991_bC * (float) (Math.PI * 2));
            if (☃x <= -0.3F && ☃ >= -0.3F) {
               this.field_70170_p
                  .func_184134_a(
                     this.field_70165_t,
                     this.field_70163_u,
                     this.field_70161_v,
                     SoundEvents.field_187524_aN,
                     this.func_184176_by(),
                     5.0F,
                     0.8F + this.field_70146_Z.nextFloat() * 0.3F,
                     false
                  );
            }

            if (!this.field_184677_bJ.func_188756_a().func_188654_a() && --this.field_184678_bK < 0) {
               this.field_70170_p
                  .func_184134_a(
                     this.field_70165_t,
                     this.field_70163_u,
                     this.field_70161_v,
                     SoundEvents.field_187525_aO,
                     this.func_184176_by(),
                     2.5F,
                     0.8F + this.field_70146_Z.nextFloat() * 0.3F,
                     false
                  );
               this.field_184678_bK = 200 + this.field_70146_Z.nextInt(200);
            }
         }
      }

      this.field_70991_bC = this.field_70988_bD;
      if (this.func_110143_aJ() <= 0.0F) {
         float ☃ = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         float ☃x = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
         float ☃xx = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         this.field_70170_p
            .func_195594_a(
               Particles.field_197627_t, this.field_70165_t + (double)☃, this.field_70163_u + 2.0 + (double)☃x, this.field_70161_v + (double)☃xx, 0.0, 0.0, 0.0
            );
      } else {
         this.func_70969_j();
         float ☃ = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
         ☃ *= (float)Math.pow(2.0, this.field_70181_x);
         if (this.field_184677_bJ.func_188756_a().func_188654_a()) {
            this.field_70988_bD += 0.1F;
         } else if (this.field_70994_bF) {
            this.field_70988_bD += ☃ * 0.5F;
         } else {
            this.field_70988_bD += ☃;
         }

         this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
         if (this.func_175446_cd()) {
            this.field_70988_bD = 0.5F;
         } else {
            if (this.field_70976_f < 0) {
               for(int ☃ = 0; ☃ < this.field_70979_e.length; ++☃) {
                  this.field_70979_e[☃][0] = (double)this.field_70177_z;
                  this.field_70979_e[☃][1] = this.field_70163_u;
               }
            }

            if (++this.field_70976_f == this.field_70979_e.length) {
               this.field_70976_f = 0;
            }

            this.field_70979_e[this.field_70976_f][0] = (double)this.field_70177_z;
            this.field_70979_e[this.field_70976_f][1] = this.field_70163_u;
            if (this.field_70170_p.field_72995_K) {
               if (this.field_70716_bi > 0) {
                  double ☃ = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / (double)this.field_70716_bi;
                  double ☃x = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / (double)this.field_70716_bi;
                  double ☃xx = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / (double)this.field_70716_bi;
                  double ☃xxx = MathHelper.func_76138_g(this.field_184626_bk - (double)this.field_70177_z);
                  this.field_70177_z = (float)((double)this.field_70177_z + ☃xxx / (double)this.field_70716_bi);
                  this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70709_bj - (double)this.field_70125_A) / (double)this.field_70716_bi);
                  --this.field_70716_bi;
                  this.func_70107_b(☃, ☃x, ☃xx);
                  this.func_70101_b(this.field_70177_z, this.field_70125_A);
               }

               this.field_184677_bJ.func_188756_a().func_188657_b();
            } else {
               IPhase ☃ = this.field_184677_bJ.func_188756_a();
               ☃.func_188659_c();
               if (this.field_184677_bJ.func_188756_a() != ☃) {
                  ☃ = this.field_184677_bJ.func_188756_a();
                  ☃.func_188659_c();
               }

               Vec3d ☃ = ☃.func_188650_g();
               if (☃ != null) {
                  double ☃x = ☃.field_72450_a - this.field_70165_t;
                  double ☃xx = ☃.field_72448_b - this.field_70163_u;
                  double ☃xxx = ☃.field_72449_c - this.field_70161_v;
                  double ☃xxxx = ☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx;
                  float ☃xxxxx = ☃.func_188651_f();
                  ☃xx = MathHelper.func_151237_a(☃xx / (double)MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx), (double)(-☃xxxxx), (double)☃xxxxx);
                  this.field_70181_x += ☃xx * 0.1F;
                  this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
                  double ☃xxxxxx = MathHelper.func_151237_a(
                     MathHelper.func_76138_g(180.0 - MathHelper.func_181159_b(☃x, ☃xxx) * 180.0F / (float)Math.PI - (double)this.field_70177_z), -50.0, 50.0
                  );
                  Vec3d ☃xxxxxxx = new Vec3d(☃.field_72450_a - this.field_70165_t, ☃.field_72448_b - this.field_70163_u, ☃.field_72449_c - this.field_70161_v)
                     .func_72432_b();
                  Vec3d ☃xxxxxxxx = new Vec3d(
                        (double)MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)),
                        this.field_70181_x,
                        (double)(-MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)))
                     )
                     .func_72432_b();
                  float ☃xxxxxxxxx = Math.max(((float)☃xxxxxxxx.func_72430_b(☃xxxxxxx) + 0.5F) / 1.5F, 0.0F);
                  this.field_70704_bt *= 0.8F;
                  this.field_70704_bt = (float)((double)this.field_70704_bt + ☃xxxxxx * (double)☃.func_188653_h());
                  this.field_70177_z += this.field_70704_bt * 0.1F;
                  float ☃xxxxxxxxxx = (float)(2.0 / (☃xxxx + 1.0));
                  float ☃xxxxxxxxxxx = 0.06F;
                  this.func_191958_b(0.0F, 0.0F, -1.0F, 0.06F * (☃xxxxxxxxx * ☃xxxxxxxxxx + (1.0F - ☃xxxxxxxxxx)));
                  if (this.field_70994_bF) {
                     this.func_70091_d(MoverType.SELF, this.field_70159_w * 0.8F, this.field_70181_x * 0.8F, this.field_70179_y * 0.8F);
                  } else {
                     this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                  }

                  Vec3d ☃x = new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72432_b();
                  float ☃xx = ((float)☃x.func_72430_b(☃xxxxxxxx) + 1.0F) / 2.0F;
                  ☃xx = 0.8F + 0.15F * ☃xx;
                  this.field_70159_w *= (double)☃xx;
                  this.field_70179_y *= (double)☃xx;
                  this.field_70181_x *= 0.91F;
               }
            }

            this.field_70761_aq = this.field_70177_z;
            this.field_70986_h.field_70130_N = 1.0F;
            this.field_70986_h.field_70131_O = 1.0F;
            this.field_184673_bv.field_70130_N = 3.0F;
            this.field_184673_bv.field_70131_O = 3.0F;
            this.field_70985_j.field_70130_N = 2.0F;
            this.field_70985_j.field_70131_O = 2.0F;
            this.field_70984_by.field_70130_N = 2.0F;
            this.field_70984_by.field_70131_O = 2.0F;
            this.field_70982_bz.field_70130_N = 2.0F;
            this.field_70982_bz.field_70131_O = 2.0F;
            this.field_70987_i.field_70131_O = 3.0F;
            this.field_70987_i.field_70130_N = 5.0F;
            this.field_70983_bA.field_70131_O = 2.0F;
            this.field_70983_bA.field_70130_N = 4.0F;
            this.field_70990_bB.field_70131_O = 3.0F;
            this.field_70990_bB.field_70130_N = 4.0F;
            Vec3d[] ☃ = new Vec3d[this.field_70977_g.length];

            for(int ☃x = 0; ☃x < this.field_70977_g.length; ++☃x) {
               ☃[☃x] = new Vec3d(this.field_70977_g[☃x].field_70165_t, this.field_70977_g[☃x].field_70163_u, this.field_70977_g[☃x].field_70161_v);
            }

            float ☃x = (float)(this.func_70974_a(5, 1.0F)[1] - this.func_70974_a(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
            float ☃xx = MathHelper.func_76134_b(☃x);
            float ☃xxx = MathHelper.func_76126_a(☃x);
            float ☃xxxx = this.field_70177_z * (float) (Math.PI / 180.0);
            float ☃xxxxx = MathHelper.func_76126_a(☃xxxx);
            float ☃xxxxxx = MathHelper.func_76134_b(☃xxxx);
            this.field_70987_i.func_70071_h_();
            this.field_70987_i
               .func_70012_b(this.field_70165_t + (double)(☃xxxxx * 0.5F), this.field_70163_u, this.field_70161_v - (double)(☃xxxxxx * 0.5F), 0.0F, 0.0F);
            this.field_70983_bA.func_70071_h_();
            this.field_70983_bA
               .func_70012_b(this.field_70165_t + (double)(☃xxxxxx * 4.5F), this.field_70163_u + 2.0, this.field_70161_v + (double)(☃xxxxx * 4.5F), 0.0F, 0.0F);
            this.field_70990_bB.func_70071_h_();
            this.field_70990_bB
               .func_70012_b(this.field_70165_t - (double)(☃xxxxxx * 4.5F), this.field_70163_u + 2.0, this.field_70161_v - (double)(☃xxxxx * 4.5F), 0.0F, 0.0F);
            if (!this.field_70170_p.field_72995_K && this.field_70737_aN == 0) {
               this.func_70970_a(
                  this.field_70170_p.func_72839_b(this, this.field_70983_bA.func_174813_aQ().func_72314_b(4.0, 2.0, 4.0).func_72317_d(0.0, -2.0, 0.0))
               );
               this.func_70970_a(
                  this.field_70170_p.func_72839_b(this, this.field_70990_bB.func_174813_aQ().func_72314_b(4.0, 2.0, 4.0).func_72317_d(0.0, -2.0, 0.0))
               );
               this.func_70971_b(this.field_70170_p.func_72839_b(this, this.field_70986_h.func_174813_aQ().func_186662_g(1.0)));
               this.func_70971_b(this.field_70170_p.func_72839_b(this, this.field_184673_bv.func_174813_aQ().func_186662_g(1.0)));
            }

            double[] ☃x = this.func_70974_a(5, 1.0F);
            float ☃xx = MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0) - this.field_70704_bt * 0.01F);
            float ☃xxx = MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0) - this.field_70704_bt * 0.01F);
            this.field_70986_h.func_70071_h_();
            this.field_184673_bv.func_70071_h_();
            float ☃xxxx = this.func_184662_q(1.0F);
            this.field_70986_h
               .func_70012_b(
                  this.field_70165_t + (double)(☃xx * 6.5F * ☃xx),
                  this.field_70163_u + (double)☃xxxx + (double)(☃xxx * 6.5F),
                  this.field_70161_v - (double)(☃xxx * 6.5F * ☃xx),
                  0.0F,
                  0.0F
               );
            this.field_184673_bv
               .func_70012_b(
                  this.field_70165_t + (double)(☃xx * 5.5F * ☃xx),
                  this.field_70163_u + (double)☃xxxx + (double)(☃xxx * 5.5F),
                  this.field_70161_v - (double)(☃xxx * 5.5F * ☃xx),
                  0.0F,
                  0.0F
               );

            for(int ☃xxxxx = 0; ☃xxxxx < 3; ++☃xxxxx) {
               MultiPartEntityPart ☃xxxxxx = null;
               if (☃xxxxx == 0) {
                  ☃xxxxxx = this.field_70985_j;
               }

               if (☃xxxxx == 1) {
                  ☃xxxxxx = this.field_70984_by;
               }

               if (☃xxxxx == 2) {
                  ☃xxxxxx = this.field_70982_bz;
               }

               double[] ☃xxxxxx = this.func_70974_a(12 + ☃xxxxx * 2, 1.0F);
               float ☃xxxxxxx = this.field_70177_z * (float) (Math.PI / 180.0) + this.func_70973_b(☃xxxxxx[0] - ☃x[0]) * (float) (Math.PI / 180.0);
               float ☃xxxxxxxx = MathHelper.func_76126_a(☃xxxxxxx);
               float ☃xxxxxxxxx = MathHelper.func_76134_b(☃xxxxxxx);
               float ☃xxxxxxxxxx = 1.5F;
               float ☃xxxxxxxxxxx = (float)(☃xxxxx + 1) * 2.0F;
               ☃xxxxxx.func_70071_h_();
               ☃xxxxxx.func_70012_b(
                  this.field_70165_t - (double)((☃xxxxx * 1.5F + ☃xxxxxxxx * ☃xxxxxxxxxxx) * ☃xx),
                  this.field_70163_u + (☃xxxxxx[1] - ☃x[1]) - (double)((☃xxxxxxxxxxx + 1.5F) * ☃xxx) + 1.5,
                  this.field_70161_v + (double)((☃xxxxxx * 1.5F + ☃xxxxxxxxx * ☃xxxxxxxxxxx) * ☃xx),
                  0.0F,
                  0.0F
               );
            }

            if (!this.field_70170_p.field_72995_K) {
               this.field_70994_bF = this.func_70972_a(this.field_70986_h.func_174813_aQ())
                  | this.func_70972_a(this.field_184673_bv.func_174813_aQ())
                  | this.func_70972_a(this.field_70987_i.func_174813_aQ());
               if (this.field_184676_bI != null) {
                  this.field_184676_bI.func_186099_b(this);
               }
            }

            for(int ☃xxxxx = 0; ☃xxxxx < this.field_70977_g.length; ++☃xxxxx) {
               this.field_70977_g[☃xxxxx].field_70169_q = ☃[☃xxxxx].field_72450_a;
               this.field_70977_g[☃xxxxx].field_70167_r = ☃[☃xxxxx].field_72448_b;
               this.field_70977_g[☃xxxxx].field_70166_s = ☃[☃xxxxx].field_72449_c;
            }
         }
      }
   }

   private float func_184662_q(float var1) {
      double ☃;
      if (this.field_184677_bJ.func_188756_a().func_188654_a()) {
         ☃ = -1.0;
      } else {
         double[] ☃ = this.func_70974_a(5, 1.0F);
         double[] ☃x = this.func_70974_a(0, 1.0F);
         ☃ = ☃[1] - ☃x[1];
      }

      return (float)☃;
   }

   private void func_70969_j() {
      if (this.field_70992_bH != null) {
         if (this.field_70992_bH.field_70128_L) {
            this.field_70992_bH = null;
         } else if (this.field_70173_aa % 10 == 0 && this.func_110143_aJ() < this.func_110138_aP()) {
            this.func_70606_j(this.func_110143_aJ() + 1.0F);
         }
      }

      if (this.field_70146_Z.nextInt(10) == 0) {
         List<EntityEnderCrystal> ☃ = this.field_70170_p.func_72872_a(EntityEnderCrystal.class, this.func_174813_aQ().func_186662_g(32.0));
         EntityEnderCrystal ☃x = null;
         double ☃xx = Double.MAX_VALUE;

         for(EntityEnderCrystal ☃xxx : ☃) {
            double ☃xxxx = ☃xxx.func_70068_e(this);
            if (☃xxxx < ☃xx) {
               ☃xx = ☃xxxx;
               ☃x = ☃xxx;
            }
         }

         this.field_70992_bH = ☃x;
      }
   }

   private void func_70970_a(List<Entity> var1) {
      double ☃ = (this.field_70987_i.func_174813_aQ().field_72340_a + this.field_70987_i.func_174813_aQ().field_72336_d) / 2.0;
      double ☃x = (this.field_70987_i.func_174813_aQ().field_72339_c + this.field_70987_i.func_174813_aQ().field_72334_f) / 2.0;

      for(Entity ☃xx : ☃) {
         if (☃xx instanceof EntityLivingBase) {
            double ☃xxx = ☃xx.field_70165_t - ☃;
            double ☃xxxx = ☃xx.field_70161_v - ☃x;
            double ☃xxxxx = ☃xxx * ☃xxx + ☃xxxx * ☃xxxx;
            ☃xx.func_70024_g(☃xxx / ☃xxxxx * 4.0, 0.2F, ☃xxxx / ☃xxxxx * 4.0);
            if (!this.field_184677_bJ.func_188756_a().func_188654_a() && ((EntityLivingBase)☃xx).func_142015_aE() < ☃xx.field_70173_aa - 2) {
               ☃xx.func_70097_a(DamageSource.func_76358_a(this), 5.0F);
               this.func_174815_a(this, ☃xx);
            }
         }
      }
   }

   private void func_70971_b(List<Entity> var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         Entity ☃x = (Entity)☃.get(☃);
         if (☃x instanceof EntityLivingBase) {
            ☃x.func_70097_a(DamageSource.func_76358_a(this), 10.0F);
            this.func_174815_a(this, ☃x);
         }
      }
   }

   private float func_70973_b(double var1) {
      return (float)MathHelper.func_76138_g(☃);
   }

   private boolean func_70972_a(AxisAlignedBB var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃x = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxx = MathHelper.func_76128_c(☃.field_72336_d);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72337_e);
      int ☃xxxxx = MathHelper.func_76128_c(☃.field_72334_f);
      boolean ☃xxxxxx = false;
      boolean ☃xxxxxxx = false;

      for(int ☃xxxxxxxx = ☃; ☃xxxxxxxx <= ☃xxx; ++☃xxxxxxxx) {
         for(int ☃xxxxxxxxx = ☃x; ☃xxxxxxxxx <= ☃xxxx; ++☃xxxxxxxxx) {
            for(int ☃xxxxxxxxxx = ☃xx; ☃xxxxxxxxxx <= ☃xxxxx; ++☃xxxxxxxxxx) {
               BlockPos ☃xxxxxxxxxxx = new BlockPos(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx);
               IBlockState ☃xxxxxxxxxxxx = this.field_70170_p.func_180495_p(☃xxxxxxxxxxx);
               Block ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx.func_177230_c();
               if (!☃xxxxxxxxxxxx.func_196958_f() && ☃xxxxxxxxxxxx.func_185904_a() != Material.field_151581_o) {
                  if (!this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
                     ☃xxxxxx = true;
                  } else if (☃xxxxxxxxxxxxx == Blocks.field_180401_cv
                     || ☃xxxxxxxxxxxxx == Blocks.field_150343_Z
                     || ☃xxxxxxxxxxxxx == Blocks.field_150377_bs
                     || ☃xxxxxxxxxxxxx == Blocks.field_150357_h
                     || ☃xxxxxxxxxxxxx == Blocks.field_150384_bq
                     || ☃xxxxxxxxxxxxx == Blocks.field_150378_br) {
                     ☃xxxxxx = true;
                  } else if (☃xxxxxxxxxxxxx != Blocks.field_150483_bI
                     && ☃xxxxxxxxxxxxx != Blocks.field_185776_dc
                     && ☃xxxxxxxxxxxxx != Blocks.field_185777_dd
                     && ☃xxxxxxxxxxxxx != Blocks.field_150411_aY
                     && ☃xxxxxxxxxxxxx != Blocks.field_185775_db) {
                     ☃xxxxxxx = this.field_70170_p.func_175698_g(☃xxxxxxxxxxx) || ☃xxxxxxx;
                  } else {
                     ☃xxxxxx = true;
                  }
               }
            }
         }
      }

      if (☃xxxxxxx) {
         double ☃xxxxxxxx = ☃.field_72340_a + (☃.field_72336_d - ☃.field_72340_a) * (double)this.field_70146_Z.nextFloat();
         double ☃xxxxxxxxx = ☃.field_72338_b + (☃.field_72337_e - ☃.field_72338_b) * (double)this.field_70146_Z.nextFloat();
         double ☃xxxxxxxxxx = ☃.field_72339_c + (☃.field_72334_f - ☃.field_72339_c) * (double)this.field_70146_Z.nextFloat();
         this.field_70170_p.func_195594_a(Particles.field_197627_t, ☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, 0.0, 0.0, 0.0);
      }

      return ☃xxxxxx;
   }

   @Override
   public boolean func_70965_a(MultiPartEntityPart var1, DamageSource var2, float var3) {
      ☃ = this.field_184677_bJ.func_188756_a().func_188656_a(☃, ☃, ☃);
      if (☃ != this.field_70986_h) {
         ☃ = ☃ / 4.0F + Math.min(☃, 1.0F);
      }

      if (☃ < 0.01F) {
         return false;
      } else {
         if (☃.func_76346_g() instanceof EntityPlayer || ☃.func_94541_c()) {
            float ☃ = this.func_110143_aJ();
            this.func_82195_e(☃, ☃);
            if (this.func_110143_aJ() <= 0.0F && !this.field_184677_bJ.func_188756_a().func_188654_a()) {
               this.func_70606_j(1.0F);
               this.field_184677_bJ.func_188758_a(PhaseType.field_188750_j);
            }

            if (this.field_184677_bJ.func_188756_a().func_188654_a()) {
               this.field_184679_bL = (int)((float)this.field_184679_bL + (☃ - this.func_110143_aJ()));
               if ((float)this.field_184679_bL > 0.25F * this.func_110138_aP()) {
                  this.field_184679_bL = 0;
                  this.field_184677_bJ.func_188758_a(PhaseType.field_188745_e);
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (☃ instanceof EntityDamageSource && ((EntityDamageSource)☃).func_180139_w()) {
         this.func_70965_a(this.field_70987_i, ☃, ☃);
      }

      return false;
   }

   protected boolean func_82195_e(DamageSource var1, float var2) {
      return super.func_70097_a(☃, ☃);
   }

   @Override
   public void func_174812_G() {
      this.func_70106_y();
      if (this.field_184676_bI != null) {
         this.field_184676_bI.func_186099_b(this);
         this.field_184676_bI.func_186096_a(this);
      }
   }

   @Override
   protected void func_70609_aI() {
      if (this.field_184676_bI != null) {
         this.field_184676_bI.func_186099_b(this);
      }

      ++this.field_70995_bG;
      if (this.field_70995_bG >= 180 && this.field_70995_bG <= 200) {
         float ☃ = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         float ☃x = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
         float ☃xx = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
         this.field_70170_p
            .func_195594_a(
               Particles.field_197626_s, this.field_70165_t + (double)☃, this.field_70163_u + 2.0 + (double)☃x, this.field_70161_v + (double)☃xx, 0.0, 0.0, 0.0
            );
      }

      boolean ☃ = this.field_70170_p.func_82736_K().func_82766_b("doMobLoot");
      int ☃x = 500;
      if (this.field_184676_bI != null && !this.field_184676_bI.func_186102_d()) {
         ☃x = 12000;
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70995_bG > 150 && this.field_70995_bG % 5 == 0 && ☃) {
            this.func_184668_a(MathHelper.func_76141_d((float)☃x * 0.08F));
         }

         if (this.field_70995_bG == 1) {
            this.field_70170_p.func_175669_a(1028, new BlockPos(this), 0);
         }
      }

      this.func_70091_d(MoverType.SELF, 0.0, 0.1F, 0.0);
      this.field_70177_z += 20.0F;
      this.field_70761_aq = this.field_70177_z;
      if (this.field_70995_bG == 200 && !this.field_70170_p.field_72995_K) {
         if (☃) {
            this.func_184668_a(MathHelper.func_76141_d((float)☃x * 0.2F));
         }

         if (this.field_184676_bI != null) {
            this.field_184676_bI.func_186096_a(this);
         }

         this.func_70106_y();
      }
   }

   private void func_184668_a(int var1) {
      while(☃ > 0) {
         int ☃ = EntityXPOrb.func_70527_a(☃);
         ☃ -= ☃;
         this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃));
      }
   }

   public int func_184671_o() {
      if (this.field_184680_bM[0] == null) {
         for(int ☃ = 0; ☃ < 24; ++☃) {
            int ☃xxx = 5;
            int ☃x;
            int ☃xx;
            if (☃ < 12) {
               ☃x = (int)(60.0F * MathHelper.func_76134_b(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)☃)));
               ☃xx = (int)(60.0F * MathHelper.func_76126_a(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)☃)));
            } else if (☃ < 20) {
               int var3 = ☃ - 12;
               ☃x = (int)(40.0F * MathHelper.func_76134_b(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               ☃xx = (int)(40.0F * MathHelper.func_76126_a(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               ☃xxx += 10;
            } else {
               int var7 = ☃ - 20;
               ☃x = (int)(20.0F * MathHelper.func_76134_b(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
               ☃xx = (int)(20.0F * MathHelper.func_76126_a(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
            }

            int ☃x = Math.max(
               this.field_70170_p.func_181545_F() + 10,
               this.field_70170_p.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(☃x, 0, ☃xx)).func_177956_o() + ☃xxx
            );
            this.field_184680_bM[☃] = new PathPoint(☃x, ☃x, ☃xx);
         }

         this.field_184681_bN[0] = 6146;
         this.field_184681_bN[1] = 8197;
         this.field_184681_bN[2] = 8202;
         this.field_184681_bN[3] = 16404;
         this.field_184681_bN[4] = 32808;
         this.field_184681_bN[5] = 32848;
         this.field_184681_bN[6] = 65696;
         this.field_184681_bN[7] = 131392;
         this.field_184681_bN[8] = 131712;
         this.field_184681_bN[9] = 263424;
         this.field_184681_bN[10] = 526848;
         this.field_184681_bN[11] = 525313;
         this.field_184681_bN[12] = 1581057;
         this.field_184681_bN[13] = 3166214;
         this.field_184681_bN[14] = 2138120;
         this.field_184681_bN[15] = 6373424;
         this.field_184681_bN[16] = 4358208;
         this.field_184681_bN[17] = 12910976;
         this.field_184681_bN[18] = 9044480;
         this.field_184681_bN[19] = 9706496;
         this.field_184681_bN[20] = 15216640;
         this.field_184681_bN[21] = 13688832;
         this.field_184681_bN[22] = 11763712;
         this.field_184681_bN[23] = 8257536;
      }

      return this.func_184663_l(this.field_70165_t, this.field_70163_u, this.field_70161_v);
   }

   public int func_184663_l(double var1, double var3, double var5) {
      float ☃ = 10000.0F;
      int ☃x = 0;
      PathPoint ☃xx = new PathPoint(MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃), MathHelper.func_76128_c(☃));
      int ☃xxx = 0;
      if (this.field_184676_bI == null || this.field_184676_bI.func_186092_c() == 0) {
         ☃xxx = 12;
      }

      for(int ☃ = ☃xxx; ☃ < 24; ++☃) {
         if (this.field_184680_bM[☃] != null) {
            float ☃x = this.field_184680_bM[☃].func_75832_b(☃xx);
            if (☃x < ☃) {
               ☃ = ☃x;
               ☃x = ☃;
            }
         }
      }

      return ☃x;
   }

   @Nullable
   public Path func_184666_a(int var1, int var2, @Nullable PathPoint var3) {
      for(int ☃ = 0; ☃ < 24; ++☃) {
         PathPoint ☃x = this.field_184680_bM[☃];
         ☃x.field_75842_i = false;
         ☃x.field_75834_g = 0.0F;
         ☃x.field_75836_e = 0.0F;
         ☃x.field_75833_f = 0.0F;
         ☃x.field_75841_h = null;
         ☃x.field_75835_d = -1;
      }

      PathPoint ☃ = this.field_184680_bM[☃];
      PathPoint ☃x = this.field_184680_bM[☃];
      ☃.field_75836_e = 0.0F;
      ☃.field_75833_f = ☃.func_75829_a(☃x);
      ☃.field_75834_g = ☃.field_75833_f;
      this.field_184682_bO.func_75848_a();
      this.field_184682_bO.func_75849_a(☃);
      PathPoint ☃xx = ☃;
      int ☃xxx = 0;
      if (this.field_184676_bI == null || this.field_184676_bI.func_186092_c() == 0) {
         ☃xxx = 12;
      }

      while(!this.field_184682_bO.func_75845_e()) {
         PathPoint ☃ = this.field_184682_bO.func_75844_c();
         if (☃.equals(☃x)) {
            if (☃ != null) {
               ☃.field_75841_h = ☃x;
               ☃x = ☃;
            }

            return this.func_184669_a(☃, ☃x);
         }

         if (☃.func_75829_a(☃x) < ☃xx.func_75829_a(☃x)) {
            ☃xx = ☃;
         }

         ☃.field_75842_i = true;
         int ☃ = 0;

         for(int ☃x = 0; ☃x < 24; ++☃x) {
            if (this.field_184680_bM[☃x] == ☃) {
               ☃ = ☃x;
               break;
            }
         }

         for(int ☃x = ☃xxx; ☃x < 24; ++☃x) {
            if ((this.field_184681_bN[☃] & 1 << ☃x) > 0) {
               PathPoint ☃xx = this.field_184680_bM[☃x];
               if (!☃xx.field_75842_i) {
                  float ☃xxx = ☃.field_75836_e + ☃.func_75829_a(☃xx);
                  if (!☃xx.func_75831_a() || ☃xxx < ☃xx.field_75836_e) {
                     ☃xx.field_75841_h = ☃;
                     ☃xx.field_75836_e = ☃xxx;
                     ☃xx.field_75833_f = ☃xx.func_75829_a(☃x);
                     if (☃xx.func_75831_a()) {
                        this.field_184682_bO.func_75850_a(☃xx, ☃xx.field_75836_e + ☃xx.field_75833_f);
                     } else {
                        ☃xx.field_75834_g = ☃xx.field_75836_e + ☃xx.field_75833_f;
                        this.field_184682_bO.func_75849_a(☃xx);
                     }
                  }
               }
            }
         }
      }

      if (☃xx == ☃) {
         return null;
      } else {
         field_184675_bH.debug("Failed to find path from {} to {}", ☃, ☃);
         if (☃ != null) {
            ☃.field_75841_h = ☃xx;
            ☃xx = ☃;
         }

         return this.func_184669_a(☃, ☃xx);
      }
   }

   private Path func_184669_a(PathPoint var1, PathPoint var2) {
      int ☃ = 1;

      for(PathPoint ☃x = ☃; ☃x.field_75841_h != null; ☃x = ☃x.field_75841_h) {
         ++☃;
      }

      PathPoint[] ☃x = new PathPoint[☃];
      PathPoint var7 = ☃;
      --☃;

      for(☃x[☃] = ☃; var7.field_75841_h != null; ☃x[☃] = var7) {
         var7 = var7.field_75841_h;
         --☃;
      }

      return new Path(☃x);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("DragonPhase", this.field_184677_bJ.func_188756_a().func_188652_i().func_188740_b());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_74764_b("DragonPhase")) {
         this.field_184677_bJ.func_188758_a(PhaseType.func_188738_a(☃.func_74762_e("DragonPhase")));
      }
   }

   @Override
   protected void func_70623_bb() {
   }

   @Override
   public Entity[] func_70021_al() {
      return this.field_70977_g;
   }

   @Override
   public boolean func_70067_L() {
      return false;
   }

   @Override
   public World func_82194_d() {
      return this.field_70170_p;
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187521_aK;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187526_aP;
   }

   @Override
   protected float func_70599_aP() {
      return 5.0F;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191189_ay;
   }

   public Vec3d func_184665_a(float var1) {
      IPhase ☃x = this.field_184677_bJ.func_188756_a();
      PhaseType<? extends IPhase> ☃xx = ☃x.func_188652_i();
      Vec3d ☃;
      if (☃xx == PhaseType.field_188744_d || ☃xx == PhaseType.field_188745_e) {
         BlockPos ☃xxx = this.field_70170_p.func_205770_a(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.field_186139_a);
         float ☃xxxx = Math.max(MathHelper.func_76133_a(this.func_174831_c(☃xxx)) / 4.0F, 1.0F);
         float ☃xxxxx = 6.0F / ☃xxxx;
         float ☃xxxxxx = this.field_70125_A;
         float ☃xxxxxxx = 1.5F;
         this.field_70125_A = -☃xxxxx * 1.5F * 5.0F;
         ☃ = this.func_70676_i(☃);
         this.field_70125_A = ☃xxxxxx;
      } else if (☃x.func_188654_a()) {
         float ☃ = this.field_70125_A;
         float ☃x = 1.5F;
         this.field_70125_A = -45.0F;
         ☃ = this.func_70676_i(☃);
         this.field_70125_A = ☃;
      } else {
         ☃ = this.func_70676_i(☃);
      }

      return ☃;
   }

   public void func_184672_a(EntityEnderCrystal var1, BlockPos var2, DamageSource var3) {
      EntityPlayer ☃;
      if (☃.func_76346_g() instanceof EntityPlayer) {
         ☃ = (EntityPlayer)☃.func_76346_g();
      } else {
         ☃ = this.field_70170_p.func_184139_a(☃, 64.0, 64.0);
      }

      if (☃ == this.field_70992_bH) {
         this.func_70965_a(this.field_70986_h, DamageSource.func_188405_b(☃), 10.0F);
      }

      this.field_184677_bJ.func_188756_a().func_188655_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184674_a.equals(☃) && this.field_70170_p.field_72995_K) {
         this.field_184677_bJ.func_188758_a(PhaseType.func_188738_a(this.func_184212_Q().func_187225_a(field_184674_a)));
      }

      super.func_184206_a(☃);
   }

   public PhaseManager func_184670_cT() {
      return this.field_184677_bJ;
   }

   @Nullable
   public DragonFightManager func_184664_cU() {
      return this.field_184676_bI;
   }

   @Override
   public boolean func_195064_c(PotionEffect var1) {
      return false;
   }

   @Override
   protected boolean func_184228_n(Entity var1) {
      return false;
   }

   @Override
   public boolean func_184222_aU() {
      return false;
   }
}
