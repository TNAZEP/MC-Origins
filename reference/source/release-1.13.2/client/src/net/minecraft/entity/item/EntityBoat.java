package net.minecraft.entity.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

public class EntityBoat extends Entity {
   private static final DataParameter<Integer> field_184460_a = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184462_b = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private static final DataParameter<Float> field_184464_c = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184466_d = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_199704_e = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_199705_f = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_203064_g = EntityDataManager.func_187226_a(EntityBoat.class, DataSerializers.field_187192_b);
   private final float[] field_184470_f = new float[2];
   private float field_184472_g;
   private float field_184474_h;
   private float field_184475_as;
   private int field_184476_at;
   private double field_70281_h;
   private double field_184477_av;
   private double field_184478_aw;
   private double field_70273_g;
   private double field_184479_ay;
   private boolean field_184480_az;
   private boolean field_184459_aA;
   private boolean field_184461_aB;
   private boolean field_184463_aC;
   private double field_184465_aD;
   private float field_184467_aE;
   private EntityBoat.Status field_184469_aF;
   private EntityBoat.Status field_184471_aG;
   private double field_184473_aH;
   private boolean field_203059_aM;
   private boolean field_203060_aN;
   private float field_203061_aO;
   private float field_203062_aP;
   private float field_203063_aQ;

   public EntityBoat(World var1) {
      super(EntityType.field_200793_g, ☃);
      this.field_70156_m = true;
      this.func_70105_a(1.375F, 0.5625F);
   }

   public EntityBoat(World var1, double var2, double var4, double var6) {
      this(☃);
      this.func_70107_b(☃, ☃, ☃);
      this.field_70159_w = 0.0;
      this.field_70181_x = 0.0;
      this.field_70179_y = 0.0;
      this.field_70169_q = ☃;
      this.field_70167_r = ☃;
      this.field_70166_s = ☃;
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184460_a, 0);
      this.field_70180_af.func_187214_a(field_184462_b, 1);
      this.field_70180_af.func_187214_a(field_184464_c, 0.0F);
      this.field_70180_af.func_187214_a(field_184466_d, EntityBoat.Type.OAK.ordinal());
      this.field_70180_af.func_187214_a(field_199704_e, false);
      this.field_70180_af.func_187214_a(field_199705_f, false);
      this.field_70180_af.func_187214_a(field_203064_g, 0);
   }

   @Nullable
   @Override
   public AxisAlignedBB func_70114_g(Entity var1) {
      return ☃.func_70104_M() ? ☃.func_174813_aQ() : null;
   }

   @Nullable
   @Override
   public AxisAlignedBB func_70046_E() {
      return this.func_174813_aQ();
   }

   @Override
   public boolean func_70104_M() {
      return true;
   }

   @Override
   public double func_70042_X() {
      return -0.1;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (this.field_70170_p.field_72995_K || this.field_70128_L) {
         return true;
      } else if (☃ instanceof EntityDamageSourceIndirect && ☃.func_76346_g() != null && this.func_184196_w(☃.func_76346_g())) {
         return false;
      } else {
         this.func_70269_c(-this.func_70267_i());
         this.func_70265_b(10);
         this.func_70266_a(this.func_70271_g() + ☃ * 10.0F);
         this.func_70018_K();
         boolean ☃ = ☃.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)☃.func_76346_g()).field_71075_bZ.field_75098_d;
         if (☃ || this.func_70271_g() > 40.0F) {
            if (!☃ && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
               this.func_199703_a(this.func_184455_j());
            }

            this.func_70106_y();
         }

         return true;
      }
   }

   @Override
   public void func_203002_i(boolean var1) {
      if (!this.field_70170_p.field_72995_K) {
         this.field_203059_aM = true;
         this.field_203060_aN = ☃;
         if (this.func_203058_B() == 0) {
            this.func_203055_e(60);
         }
      }

      this.field_70170_p
         .func_195594_a(
            Particles.field_197606_Q,
            this.field_70165_t + (double)this.field_70146_Z.nextFloat(),
            this.field_70163_u + 0.7,
            this.field_70161_v + (double)this.field_70146_Z.nextFloat(),
            0.0,
            0.0,
            0.0
         );
      if (this.field_70146_Z.nextInt(20) == 0) {
         this.field_70170_p
            .func_184134_a(
               this.field_70165_t,
               this.field_70163_u,
               this.field_70161_v,
               this.func_184181_aa(),
               this.func_184176_by(),
               1.0F,
               0.8F + 0.4F * this.field_70146_Z.nextFloat(),
               false
            );
      }
   }

   @Override
   public void func_70108_f(Entity var1) {
      if (☃ instanceof EntityBoat) {
         if (☃.func_174813_aQ().field_72338_b < this.func_174813_aQ().field_72337_e) {
            super.func_70108_f(☃);
         }
      } else if (☃.func_174813_aQ().field_72338_b <= this.func_174813_aQ().field_72338_b) {
         super.func_70108_f(☃);
      }
   }

   public Item func_184455_j() {
      switch(this.func_184453_r()) {
         case OAK:
         default:
            return Items.field_151124_az;
         case SPRUCE:
            return Items.field_185150_aH;
         case BIRCH:
            return Items.field_185151_aI;
         case JUNGLE:
            return Items.field_185152_aJ;
         case ACACIA:
            return Items.field_185153_aK;
         case DARK_OAK:
            return Items.field_185154_aL;
      }
   }

   @Override
   public void func_70057_ab() {
      this.func_70269_c(-this.func_70267_i());
      this.func_70265_b(10);
      this.func_70266_a(this.func_70271_g() * 11.0F);
   }

   @Override
   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   @Override
   public void func_180426_a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.field_70281_h = ☃;
      this.field_184477_av = ☃;
      this.field_184478_aw = ☃;
      this.field_70273_g = (double)☃;
      this.field_184479_ay = (double)☃;
      this.field_184476_at = 10;
   }

   @Override
   public EnumFacing func_184172_bi() {
      return this.func_174811_aO().func_176746_e();
   }

   @Override
   public void func_70071_h_() {
      this.field_184471_aG = this.field_184469_aF;
      this.field_184469_aF = this.func_184449_t();
      if (this.field_184469_aF != EntityBoat.Status.UNDER_WATER && this.field_184469_aF != EntityBoat.Status.UNDER_FLOWING_WATER) {
         this.field_184474_h = 0.0F;
      } else {
         ++this.field_184474_h;
      }

      if (!this.field_70170_p.field_72995_K && this.field_184474_h >= 60.0F) {
         this.func_184226_ay();
      }

      if (this.func_70268_h() > 0) {
         this.func_70265_b(this.func_70268_h() - 1);
      }

      if (this.func_70271_g() > 0.0F) {
         this.func_70266_a(this.func_70271_g() - 1.0F);
      }

      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      super.func_70071_h_();
      this.func_184447_s();
      if (this.func_184186_bw()) {
         if (this.func_184188_bt().isEmpty() || !(this.func_184188_bt().get(0) instanceof EntityPlayer)) {
            this.func_184445_a(false, false);
         }

         this.func_184450_w();
         if (this.field_70170_p.field_72995_K) {
            this.func_184443_x();
            this.field_70170_p.func_184135_a(new CPacketSteerBoat(this.func_184457_a(0), this.func_184457_a(1)));
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      } else {
         this.field_70159_w = 0.0;
         this.field_70181_x = 0.0;
         this.field_70179_y = 0.0;
      }

      this.func_203057_r();

      for(int ☃ = 0; ☃ <= 1; ++☃) {
         if (this.func_184457_a(☃)) {
            if (!this.func_174814_R()
               && (double)(this.field_184470_f[☃] % (float) (Math.PI * 2)) <= (float) (Math.PI / 4)
               && ((double)this.field_184470_f[☃] + (float) (Math.PI / 8)) % (float) (Math.PI * 2) >= (float) (Math.PI / 4)) {
               SoundEvent ☃x = this.func_193047_k();
               if (☃x != null) {
                  Vec3d ☃xx = this.func_70676_i(1.0F);
                  double ☃xxx = ☃ == 1 ? -☃xx.field_72449_c : ☃xx.field_72449_c;
                  double ☃xxxx = ☃ == 1 ? ☃xx.field_72450_a : -☃xx.field_72450_a;
                  this.field_70170_p
                     .func_184148_a(
                        null,
                        this.field_70165_t + ☃xxx,
                        this.field_70163_u,
                        this.field_70161_v + ☃xxxx,
                        ☃x,
                        this.func_184176_by(),
                        1.0F,
                        0.8F + 0.4F * this.field_70146_Z.nextFloat()
                     );
               }
            }

            this.field_184470_f[☃] = (float)((double)this.field_184470_f[☃] + (float) (Math.PI / 8));
         } else {
            this.field_184470_f[☃] = 0.0F;
         }
      }

      this.func_145775_I();
      List<Entity> ☃ = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_72314_b(0.2F, -0.01F, 0.2F), EntitySelectors.func_200823_a(this));
      if (!☃.isEmpty()) {
         boolean ☃x = !this.field_70170_p.field_72995_K && !(this.func_184179_bs() instanceof EntityPlayer);

         for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
            Entity ☃xxx = (Entity)☃.get(☃xx);
            if (!☃xxx.func_184196_w(this)) {
               if (☃x
                  && this.func_184188_bt().size() < 2
                  && !☃xxx.func_184218_aH()
                  && ☃xxx.field_70130_N < this.field_70130_N
                  && ☃xxx instanceof EntityLivingBase
                  && !(☃xxx instanceof EntityWaterMob)
                  && !(☃xxx instanceof EntityPlayer)) {
                  ☃xxx.func_184220_m(this);
               } else {
                  this.func_70108_f(☃xxx);
               }
            }
         }
      }
   }

   private void func_203057_r() {
      if (this.field_70170_p.field_72995_K) {
         int ☃ = this.func_203058_B();
         if (☃ > 0) {
            this.field_203061_aO += 0.05F;
         } else {
            this.field_203061_aO -= 0.1F;
         }

         this.field_203061_aO = MathHelper.func_76131_a(this.field_203061_aO, 0.0F, 1.0F);
         this.field_203063_aQ = this.field_203062_aP;
         this.field_203062_aP = 10.0F * (float)Math.sin((double)(0.5F * (float)this.field_70170_p.func_82737_E())) * this.field_203061_aO;
      } else {
         if (!this.field_203059_aM) {
            this.func_203055_e(0);
         }

         int ☃ = this.func_203058_B();
         if (☃ > 0) {
            this.func_203055_e(--☃);
            int ☃x = 60 - ☃ - 1;
            if (☃x > 0 && ☃ == 0) {
               this.func_203055_e(0);
               if (this.field_203060_aN) {
                  this.field_70181_x -= 0.7;
                  this.func_184226_ay();
               } else {
                  this.field_70181_x = this.func_205708_a(EntityPlayer.class) ? 2.7 : 0.6;
               }
            }

            this.field_203059_aM = false;
         }
      }
   }

   @Nullable
   protected SoundEvent func_193047_k() {
      switch(this.func_184449_t()) {
         case IN_WATER:
         case UNDER_WATER:
         case UNDER_FLOWING_WATER:
            return SoundEvents.field_193779_I;
         case ON_LAND:
            return SoundEvents.field_193778_H;
         case IN_AIR:
         default:
            return null;
      }
   }

   private void func_184447_s() {
      if (this.field_184476_at > 0 && !this.func_184186_bw()) {
         double ☃ = this.field_70165_t + (this.field_70281_h - this.field_70165_t) / (double)this.field_184476_at;
         double ☃x = this.field_70163_u + (this.field_184477_av - this.field_70163_u) / (double)this.field_184476_at;
         double ☃xx = this.field_70161_v + (this.field_184478_aw - this.field_70161_v) / (double)this.field_184476_at;
         double ☃xxx = MathHelper.func_76138_g(this.field_70273_g - (double)this.field_70177_z);
         this.field_70177_z = (float)((double)this.field_70177_z + ☃xxx / (double)this.field_184476_at);
         this.field_70125_A = (float)((double)this.field_70125_A + (this.field_184479_ay - (double)this.field_70125_A) / (double)this.field_184476_at);
         --this.field_184476_at;
         this.func_70107_b(☃, ☃x, ☃xx);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
      }
   }

   public void func_184445_a(boolean var1, boolean var2) {
      this.field_70180_af.func_187227_b(field_199704_e, ☃);
      this.field_70180_af.func_187227_b(field_199705_f, ☃);
   }

   public float func_184448_a(int var1, float var2) {
      return this.func_184457_a(☃)
         ? (float)MathHelper.func_151238_b((double)this.field_184470_f[☃] - (float) (Math.PI / 8), (double)this.field_184470_f[☃], (double)☃)
         : 0.0F;
   }

   private EntityBoat.Status func_184449_t() {
      EntityBoat.Status ☃ = this.func_184444_v();
      if (☃ != null) {
         this.field_184465_aD = this.func_174813_aQ().field_72337_e;
         return ☃;
      } else if (this.func_184446_u()) {
         return EntityBoat.Status.IN_WATER;
      } else {
         float ☃ = this.func_184441_l();
         if (☃ > 0.0F) {
            this.field_184467_aE = ☃;
            return EntityBoat.Status.ON_LAND;
         } else {
            return EntityBoat.Status.IN_AIR;
         }
      }
   }

   public float func_184451_k() {
      AxisAlignedBB ☃ = this.func_174813_aQ();
      int ☃x = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃xx = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xxx = MathHelper.func_76128_c(☃.field_72337_e);
      int ☃xxxx = MathHelper.func_76143_f(☃.field_72337_e - this.field_184473_aH);
      int ☃xxxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxxx = MathHelper.func_76143_f(☃.field_72334_f);

      try (BlockPos.PooledMutableBlockPos ☃xxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         label136:
         for(int ☃xxxxxxxx = ☃xxx; ☃xxxxxxxx < ☃xxxx; ++☃xxxxxxxx) {
            float ☃xxxxxxxxx = 0.0F;
            int ☃xxxxxxxxxx = ☃x;

            while(true) {
               if (☃xxxxxxxxxx < ☃xx) {
                  for(int ☃xxxxxxxxxxx = ☃xxxxx; ☃xxxxxxxxxxx < ☃xxxxxx; ++☃xxxxxxxxxxx) {
                     ☃xxxxxxx.func_181079_c(☃xxxxxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxxx);
                     IFluidState ☃xxxxxxxxxxxx = this.field_70170_p.func_204610_c(☃xxxxxxx);
                     if (☃xxxxxxxxxxxx.func_206884_a(FluidTags.field_206959_a)) {
                        ☃xxxxxxxxx = Math.max(☃xxxxxxxxx, (float)☃xxxxxxxx + ☃xxxxxxxxxxxx.func_206885_f());
                     }

                     if (☃xxxxxxxxx >= 1.0F) {
                        continue label136;
                     }
                  }

                  ++☃xxxxxxxxxx;
               } else {
                  if (☃xxxxxxxxx < 1.0F) {
                     return (float)☃xxxxxxx.func_177956_o() + ☃xxxxxxxxx;
                  }
                  break;
               }
            }
         }

         return (float)(☃xxxx + 1);
      }
   }

   public float func_184441_l() {
      AxisAlignedBB ☃ = this.func_174813_aQ();
      AxisAlignedBB ☃x = new AxisAlignedBB(☃.field_72340_a, ☃.field_72338_b - 0.001, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72338_b, ☃.field_72334_f);
      int ☃xx = MathHelper.func_76128_c(☃x.field_72340_a) - 1;
      int ☃xxx = MathHelper.func_76143_f(☃x.field_72336_d) + 1;
      int ☃xxxx = MathHelper.func_76128_c(☃x.field_72338_b) - 1;
      int ☃xxxxx = MathHelper.func_76143_f(☃x.field_72337_e) + 1;
      int ☃xxxxxx = MathHelper.func_76128_c(☃x.field_72339_c) - 1;
      int ☃xxxxxxx = MathHelper.func_76143_f(☃x.field_72334_f) + 1;
      VoxelShape ☃xxxxxxxx = VoxelShapes.func_197881_a(☃x);
      float ☃xxxxxxxxx = 0.0F;
      int ☃xxxxxxxxxx = 0;

      try (BlockPos.PooledMutableBlockPos ☃xxxxxxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(int ☃xxxxxxxxxxxx = ☃xx; ☃xxxxxxxxxxxx < ☃xxx; ++☃xxxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxxxxxx < ☃xxxxxxx; ++☃xxxxxxxxxxxxx) {
               int ☃xxxxxxxxxxxxxx = (☃xxxxxxxxxxxx != ☃xx && ☃xxxxxxxxxxxx != ☃xxx - 1 ? 0 : 1)
                  + (☃xxxxxxxxxxxxx != ☃xxxxxx && ☃xxxxxxxxxxxxx != ☃xxxxxxx - 1 ? 0 : 1);
               if (☃xxxxxxxxxxxxxx != 2) {
                  for(int ☃xxxxxxxxxxxxxxx = ☃xxxx; ☃xxxxxxxxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxxxxxxxx) {
                     if (☃xxxxxxxxxxxxxx <= 0 || ☃xxxxxxxxxxxxxxx != ☃xxxx && ☃xxxxxxxxxxxxxxx != ☃xxxxx - 1) {
                        ☃xxxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx);
                        IBlockState ☃xxxxxxxxxxxxxxxx = this.field_70170_p.func_180495_p(☃xxxxxxxxxxx);
                        if (!(☃xxxxxxxxxxxxxxxx.func_177230_c() instanceof BlockLilyPad)
                           && VoxelShapes.func_197879_c(
                              ☃xxxxxxxxxxxxxxxx.func_196952_d(this.field_70170_p, ☃xxxxxxxxxxx)
                                 .func_197751_a((double)☃xxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxx),
                              ☃xxxxxxxx,
                              IBooleanFunction.AND
                           )) {
                           ☃xxxxxxxxx += ☃xxxxxxxxxxxxxxxx.func_177230_c().func_208618_m();
                           ++☃xxxxxxxxxx;
                        }
                     }
                  }
               }
            }
         }
      }

      return ☃xxxxxxxxx / (float)☃xxxxxxxxxx;
   }

   private boolean func_184446_u() {
      AxisAlignedBB ☃ = this.func_174813_aQ();
      int ☃x = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃xx = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xxx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxxx = MathHelper.func_76143_f(☃.field_72338_b + 0.001);
      int ☃xxxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxxx = MathHelper.func_76143_f(☃.field_72334_f);
      boolean ☃xxxxxxx = false;
      this.field_184465_aD = Double.MIN_VALUE;

      try (BlockPos.PooledMutableBlockPos ☃xxxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(int ☃xxxxxxxxx = ☃x; ☃xxxxxxxxx < ☃xx; ++☃xxxxxxxxx) {
            for(int ☃xxxxxxxxxx = ☃xxx; ☃xxxxxxxxxx < ☃xxxx; ++☃xxxxxxxxxx) {
               for(int ☃xxxxxxxxxxx = ☃xxxxx; ☃xxxxxxxxxxx < ☃xxxxxx; ++☃xxxxxxxxxxx) {
                  ☃xxxxxxxx.func_181079_c(☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx);
                  IFluidState ☃xxxxxxxxxxxx = this.field_70170_p.func_204610_c(☃xxxxxxxx);
                  if (☃xxxxxxxxxxxx.func_206884_a(FluidTags.field_206959_a)) {
                     float ☃xxxxxxxxxxxxx = (float)☃xxxxxxxxxx + ☃xxxxxxxxxxxx.func_206885_f();
                     this.field_184465_aD = Math.max((double)☃xxxxxxxxxxxxx, this.field_184465_aD);
                     ☃xxxxxxx |= ☃.field_72338_b < (double)☃xxxxxxxxxxxxx;
                  }
               }
            }
         }
      }

      return ☃xxxxxxx;
   }

   @Nullable
   private EntityBoat.Status func_184444_v() {
      AxisAlignedBB ☃ = this.func_174813_aQ();
      double ☃x = ☃.field_72337_e + 0.001;
      int ☃xx = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃xxx = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72337_e);
      int ☃xxxxx = MathHelper.func_76143_f(☃x);
      int ☃xxxxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxxxx = MathHelper.func_76143_f(☃.field_72334_f);
      boolean ☃xxxxxxxx = false;

      try (BlockPos.PooledMutableBlockPos ☃xxxxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(int ☃xxxxxxxxxx = ☃xx; ☃xxxxxxxxxx < ☃xxx; ++☃xxxxxxxxxx) {
            for(int ☃xxxxxxxxxxx = ☃xxxx; ☃xxxxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxxxxx < ☃xxxxxxx; ++☃xxxxxxxxxxxx) {
                  ☃xxxxxxxxx.func_181079_c(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
                  IFluidState ☃xxxxxxxxxxxxx = this.field_70170_p.func_204610_c(☃xxxxxxxxx);
                  if (☃xxxxxxxxxxxxx.func_206884_a(FluidTags.field_206959_a)
                     && ☃x < (double)((float)☃xxxxxxxxx.func_177956_o() + ☃xxxxxxxxxxxxx.func_206885_f())) {
                     if (!☃xxxxxxxxxxxxx.func_206889_d()) {
                        return EntityBoat.Status.UNDER_FLOWING_WATER;
                     }

                     ☃xxxxxxxx = true;
                  }
               }
            }
         }
      }

      return ☃xxxxxxxx ? EntityBoat.Status.UNDER_WATER : null;
   }

   private void func_184450_w() {
      double ☃ = -0.04F;
      double ☃x = this.func_189652_ae() ? 0.0 : -0.04F;
      double ☃xx = 0.0;
      this.field_184472_g = 0.05F;
      if (this.field_184471_aG == EntityBoat.Status.IN_AIR
         && this.field_184469_aF != EntityBoat.Status.IN_AIR
         && this.field_184469_aF != EntityBoat.Status.ON_LAND) {
         this.field_184465_aD = this.func_174813_aQ().field_72338_b + (double)this.field_70131_O;
         this.func_70107_b(this.field_70165_t, (double)(this.func_184451_k() - this.field_70131_O) + 0.101, this.field_70161_v);
         this.field_70181_x = 0.0;
         this.field_184473_aH = 0.0;
         this.field_184469_aF = EntityBoat.Status.IN_WATER;
      } else {
         if (this.field_184469_aF == EntityBoat.Status.IN_WATER) {
            ☃xx = (this.field_184465_aD - this.func_174813_aQ().field_72338_b) / (double)this.field_70131_O;
            this.field_184472_g = 0.9F;
         } else if (this.field_184469_aF == EntityBoat.Status.UNDER_FLOWING_WATER) {
            ☃x = -7.0E-4;
            this.field_184472_g = 0.9F;
         } else if (this.field_184469_aF == EntityBoat.Status.UNDER_WATER) {
            ☃xx = 0.01F;
            this.field_184472_g = 0.45F;
         } else if (this.field_184469_aF == EntityBoat.Status.IN_AIR) {
            this.field_184472_g = 0.9F;
         } else if (this.field_184469_aF == EntityBoat.Status.ON_LAND) {
            this.field_184472_g = this.field_184467_aE;
            if (this.func_184179_bs() instanceof EntityPlayer) {
               this.field_184467_aE /= 2.0F;
            }
         }

         this.field_70159_w *= (double)this.field_184472_g;
         this.field_70179_y *= (double)this.field_184472_g;
         this.field_184475_as *= this.field_184472_g;
         this.field_70181_x += ☃x;
         if (☃xx > 0.0) {
            double ☃ = 0.65;
            this.field_70181_x += ☃xx * 0.06153846016296973;
            double ☃x = 0.75;
            this.field_70181_x *= 0.75;
         }
      }
   }

   private void func_184443_x() {
      if (this.func_184207_aI()) {
         float ☃ = 0.0F;
         if (this.field_184480_az) {
            this.field_184475_as += -1.0F;
         }

         if (this.field_184459_aA) {
            ++this.field_184475_as;
         }

         if (this.field_184459_aA != this.field_184480_az && !this.field_184461_aB && !this.field_184463_aC) {
            ☃ += 0.005F;
         }

         this.field_70177_z += this.field_184475_as;
         if (this.field_184461_aB) {
            ☃ += 0.04F;
         }

         if (this.field_184463_aC) {
            ☃ -= 0.005F;
         }

         this.field_70159_w += (double)(MathHelper.func_76126_a(-this.field_70177_z * (float) (Math.PI / 180.0)) * ☃);
         this.field_70179_y += (double)(MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)) * ☃);
         this.func_184445_a(
            this.field_184459_aA && !this.field_184480_az || this.field_184461_aB, this.field_184480_az && !this.field_184459_aA || this.field_184461_aB
         );
      }
   }

   @Override
   public void func_184232_k(Entity var1) {
      if (this.func_184196_w(☃)) {
         float ☃ = 0.0F;
         float ☃x = (float)((this.field_70128_L ? 0.01F : this.func_70042_X()) + ☃.func_70033_W());
         if (this.func_184188_bt().size() > 1) {
            int ☃xx = this.func_184188_bt().indexOf(☃);
            if (☃xx == 0) {
               ☃ = 0.2F;
            } else {
               ☃ = -0.6F;
            }

            if (☃ instanceof EntityAnimal) {
               ☃ = (float)((double)☃ + 0.2);
            }
         }

         Vec3d ☃ = new Vec3d((double)☃, 0.0, 0.0).func_178785_b(-this.field_70177_z * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
         ☃.func_70107_b(this.field_70165_t + ☃.field_72450_a, this.field_70163_u + (double)☃x, this.field_70161_v + ☃.field_72449_c);
         ☃.field_70177_z += this.field_184475_as;
         ☃.func_70034_d(☃.func_70079_am() + this.field_184475_as);
         this.func_184454_a(☃);
         if (☃ instanceof EntityAnimal && this.func_184188_bt().size() > 1) {
            int ☃x = ☃.func_145782_y() % 2 == 0 ? 90 : 270;
            ☃.func_181013_g(((EntityAnimal)☃).field_70761_aq + (float)☃x);
            ☃.func_70034_d(☃.func_70079_am() + (float)☃x);
         }
      }
   }

   protected void func_184454_a(Entity var1) {
      ☃.func_181013_g(this.field_70177_z);
      float ☃ = MathHelper.func_76142_g(☃.field_70177_z - this.field_70177_z);
      float ☃x = MathHelper.func_76131_a(☃, -105.0F, 105.0F);
      ☃.field_70126_B += ☃x - ☃;
      ☃.field_70177_z += ☃x - ☃;
      ☃.func_70034_d(☃.field_70177_z);
   }

   @Override
   public void func_184190_l(Entity var1) {
      this.func_184454_a(☃);
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      ☃.func_74778_a("Type", this.func_184453_r().func_184980_a());
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      if (☃.func_150297_b("Type", 8)) {
         this.func_184458_a(EntityBoat.Type.func_184981_a(☃.func_74779_i("Type")));
      }
   }

   @Override
   public boolean func_184230_a(EntityPlayer var1, EnumHand var2) {
      if (☃.func_70093_af()) {
         return false;
      } else {
         if (!this.field_70170_p.field_72995_K && this.field_184474_h < 60.0F) {
            ☃.func_184220_m(this);
         }

         return true;
      }
   }

   @Override
   protected void func_184231_a(double var1, boolean var3, IBlockState var4, BlockPos var5) {
      this.field_184473_aH = this.field_70181_x;
      if (!this.func_184218_aH()) {
         if (☃) {
            if (this.field_70143_R > 3.0F) {
               if (this.field_184469_aF != EntityBoat.Status.ON_LAND) {
                  this.field_70143_R = 0.0F;
                  return;
               }

               this.func_180430_e(this.field_70143_R, 1.0F);
               if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
                  this.func_70106_y();
                  if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
                     for(int ☃ = 0; ☃ < 3; ++☃) {
                        this.func_199703_a(this.func_184453_r().func_195933_b());
                     }

                     for(int ☃ = 0; ☃ < 2; ++☃) {
                        this.func_199703_a(Items.field_151055_y);
                     }
                  }
               }
            }

            this.field_70143_R = 0.0F;
         } else if (!this.field_70170_p.func_204610_c(new BlockPos(this).func_177977_b()).func_206884_a(FluidTags.field_206959_a) && ☃ < 0.0) {
            this.field_70143_R = (float)((double)this.field_70143_R - ☃);
         }
      }
   }

   public boolean func_184457_a(int var1) {
      return this.field_70180_af.func_187225_a(☃ == 0 ? field_199704_e : field_199705_f) && this.func_184179_bs() != null;
   }

   public void func_70266_a(float var1) {
      this.field_70180_af.func_187227_b(field_184464_c, ☃);
   }

   public float func_70271_g() {
      return this.field_70180_af.func_187225_a(field_184464_c);
   }

   public void func_70265_b(int var1) {
      this.field_70180_af.func_187227_b(field_184460_a, ☃);
   }

   public int func_70268_h() {
      return this.field_70180_af.func_187225_a(field_184460_a);
   }

   private void func_203055_e(int var1) {
      this.field_70180_af.func_187227_b(field_203064_g, ☃);
   }

   private int func_203058_B() {
      return this.field_70180_af.func_187225_a(field_203064_g);
   }

   public float func_203056_b(float var1) {
      return this.field_203063_aQ + (this.field_203062_aP - this.field_203063_aQ) * ☃;
   }

   public void func_70269_c(int var1) {
      this.field_70180_af.func_187227_b(field_184462_b, ☃);
   }

   public int func_70267_i() {
      return this.field_70180_af.func_187225_a(field_184462_b);
   }

   public void func_184458_a(EntityBoat.Type var1) {
      this.field_70180_af.func_187227_b(field_184466_d, ☃.ordinal());
   }

   public EntityBoat.Type func_184453_r() {
      return EntityBoat.Type.func_184979_a(this.field_70180_af.func_187225_a(field_184466_d));
   }

   @Override
   protected boolean func_184219_q(Entity var1) {
      return this.func_184188_bt().size() < 2 && !this.func_208600_a(FluidTags.field_206959_a);
   }

   @Nullable
   @Override
   public Entity func_184179_bs() {
      List<Entity> ☃ = this.func_184188_bt();
      return ☃.isEmpty() ? null : (Entity)☃.get(0);
   }

   public void func_184442_a(boolean var1, boolean var2, boolean var3, boolean var4) {
      this.field_184480_az = ☃;
      this.field_184459_aA = ☃;
      this.field_184461_aB = ☃;
      this.field_184463_aC = ☃;
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;
   }

   public static enum Type {
      OAK(Blocks.field_196662_n, "oak"),
      SPRUCE(Blocks.field_196664_o, "spruce"),
      BIRCH(Blocks.field_196666_p, "birch"),
      JUNGLE(Blocks.field_196668_q, "jungle"),
      ACACIA(Blocks.field_196670_r, "acacia"),
      DARK_OAK(Blocks.field_196672_s, "dark_oak");

      private final String field_184990_g;
      private final Block field_195934_h;

      private Type(Block var3, String var4) {
         this.field_184990_g = ☃;
         this.field_195934_h = ☃;
      }

      public String func_184980_a() {
         return this.field_184990_g;
      }

      public Block func_195933_b() {
         return this.field_195934_h;
      }

      public String toString() {
         return this.field_184990_g;
      }

      public static EntityBoat.Type func_184979_a(int var0) {
         EntityBoat.Type[] ☃ = values();
         if (☃ < 0 || ☃ >= ☃.length) {
            ☃ = 0;
         }

         return ☃[☃];
      }

      public static EntityBoat.Type func_184981_a(String var0) {
         EntityBoat.Type[] ☃ = values();

         for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
            if (☃[☃x].func_184980_a().equals(☃)) {
               return ☃[☃x];
            }
         }

         return ☃[0];
      }
   }
}
