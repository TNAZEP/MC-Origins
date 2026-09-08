package net.minecraft.entity.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.INameable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;

public abstract class EntityMinecart extends Entity implements INameable {
   private static final DataParameter<Integer> field_184265_a = EntityDataManager.func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184266_b = EntityDataManager.func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Float> field_184267_c = EntityDataManager.func_187226_a(EntityMinecart.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184268_d = EntityDataManager.func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_184269_e = EntityDataManager.func_187226_a(EntityMinecart.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184270_f = EntityDataManager.func_187226_a(EntityMinecart.class, DataSerializers.field_187198_h);
   private boolean field_70499_f;
   private static final int[][][] field_70500_g = new int[][][]{
      {{0, 0, -1}, {0, 0, 1}},
      {{-1, 0, 0}, {1, 0, 0}},
      {{-1, -1, 0}, {1, 0, 0}},
      {{-1, 0, 0}, {1, -1, 0}},
      {{0, 0, -1}, {0, -1, 1}},
      {{0, -1, -1}, {0, 0, 1}},
      {{0, 0, 1}, {1, 0, 0}},
      {{0, 0, 1}, {-1, 0, 0}},
      {{0, 0, -1}, {-1, 0, 0}},
      {{0, 0, -1}, {1, 0, 0}}
   };
   private int field_70510_h;
   private double field_70511_i;
   private double field_70509_j;
   private double field_70514_an;
   private double field_70512_ao;
   private double field_70513_ap;
   private double field_70508_aq;
   private double field_70507_ar;
   private double field_70506_as;

   protected EntityMinecart(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_70156_m = true;
      this.func_70105_a(0.98F, 0.7F);
   }

   protected EntityMinecart(EntityType<?> var1, World var2, double var3, double var5, double var7) {
      this(☃, ☃);
      this.func_70107_b(☃, ☃, ☃);
      this.field_70159_w = 0.0;
      this.field_70181_x = 0.0;
      this.field_70179_y = 0.0;
      this.field_70169_q = ☃;
      this.field_70167_r = ☃;
      this.field_70166_s = ☃;
   }

   public static EntityMinecart func_184263_a(World var0, double var1, double var3, double var5, EntityMinecart.Type var7) {
      switch(☃) {
         case CHEST:
            return new EntityMinecartChest(☃, ☃, ☃, ☃);
         case FURNACE:
            return new EntityMinecartFurnace(☃, ☃, ☃, ☃);
         case TNT:
            return new EntityMinecartTNT(☃, ☃, ☃, ☃);
         case SPAWNER:
            return new EntityMinecartMobSpawner(☃, ☃, ☃, ☃);
         case HOPPER:
            return new EntityMinecartHopper(☃, ☃, ☃, ☃);
         case COMMAND_BLOCK:
            return new EntityMinecartCommandBlock(☃, ☃, ☃, ☃);
         default:
            return new EntityMinecartEmpty(☃, ☃, ☃, ☃);
      }
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184265_a, 0);
      this.field_70180_af.func_187214_a(field_184266_b, 1);
      this.field_70180_af.func_187214_a(field_184267_c, 0.0F);
      this.field_70180_af.func_187214_a(field_184268_d, Block.func_196246_j(Blocks.field_150350_a.func_176223_P()));
      this.field_70180_af.func_187214_a(field_184269_e, 6);
      this.field_70180_af.func_187214_a(field_184270_f, false);
   }

   @Nullable
   @Override
   public AxisAlignedBB func_70114_g(Entity var1) {
      return ☃.func_70104_M() ? ☃.func_174813_aQ() : null;
   }

   @Override
   public boolean func_70104_M() {
      return true;
   }

   @Override
   public double func_70042_X() {
      return 0.0;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.field_70170_p.field_72995_K || this.field_70128_L) {
         return true;
      } else if (this.func_180431_b(☃)) {
         return false;
      } else {
         this.func_70494_i(-this.func_70493_k());
         this.func_70497_h(10);
         this.func_70018_K();
         this.func_70492_c(this.func_70491_i() + ☃ * 10.0F);
         boolean ☃ = ☃.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)☃.func_76346_g()).field_71075_bZ.field_75098_d;
         if (☃ || this.func_70491_i() > 40.0F) {
            this.func_184226_ay();
            if (☃ && !this.func_145818_k_()) {
               this.func_70106_y();
            } else {
               this.func_94095_a(☃);
            }
         }

         return true;
      }
   }

   public void func_94095_a(DamageSource var1) {
      this.func_70106_y();
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         ItemStack ☃ = new ItemStack(Items.field_151143_au);
         if (this.func_145818_k_()) {
            ☃.func_200302_a(this.func_200201_e());
         }

         this.func_199701_a_(☃);
      }
   }

   @Override
   public void func_70057_ab() {
      this.func_70494_i(-this.func_70493_k());
      this.func_70497_h(10);
      this.func_70492_c(this.func_70491_i() + this.func_70491_i() * 10.0F);
   }

   @Override
   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   @Override
   public EnumFacing func_184172_bi() {
      return this.field_70499_f ? this.func_174811_aO().func_176734_d().func_176746_e() : this.func_174811_aO().func_176746_e();
   }

   @Override
   public void func_70071_h_() {
      if (this.func_70496_j() > 0) {
         this.func_70497_h(this.func_70496_j() - 1);
      }

      if (this.func_70491_i() > 0.0F) {
         this.func_70492_c(this.func_70491_i() - 1.0F);
      }

      if (this.field_70163_u < -64.0) {
         this.func_70076_C();
      }

      if (!this.field_70170_p.field_72995_K && this.field_70170_p instanceof WorldServer) {
         this.field_70170_p.field_72984_F.func_76320_a("portal");
         MinecraftServer ☃ = this.field_70170_p.func_73046_m();
         int ☃x = this.func_82145_z();
         if (this.field_71087_bX) {
            if (☃.func_71255_r()) {
               if (!this.func_184218_aH() && this.field_82153_h++ >= ☃x) {
                  this.field_82153_h = ☃x;
                  this.field_71088_bW = this.func_82147_ab();
                  DimensionType ☃xx;
                  if (this.field_70170_p.field_73011_w.func_186058_p() == DimensionType.NETHER) {
                     ☃xx = DimensionType.OVERWORLD;
                  } else {
                     ☃xx = DimensionType.NETHER;
                  }

                  this.func_212321_a(☃xx);
               }

               this.field_71087_bX = false;
            }
         } else {
            if (this.field_82153_h > 0) {
               this.field_82153_h -= 4;
            }

            if (this.field_82153_h < 0) {
               this.field_82153_h = 0;
            }
         }

         if (this.field_71088_bW > 0) {
            --this.field_71088_bW;
         }

         this.field_70170_p.field_72984_F.func_76319_b();
      }

      if (this.field_70170_p.field_72995_K) {
         if (this.field_70510_h > 0) {
            double ☃ = this.field_70165_t + (this.field_70511_i - this.field_70165_t) / (double)this.field_70510_h;
            double ☃x = this.field_70163_u + (this.field_70509_j - this.field_70163_u) / (double)this.field_70510_h;
            double ☃xx = this.field_70161_v + (this.field_70514_an - this.field_70161_v) / (double)this.field_70510_h;
            double ☃xxx = MathHelper.func_76138_g(this.field_70512_ao - (double)this.field_70177_z);
            this.field_70177_z = (float)((double)this.field_70177_z + ☃xxx / (double)this.field_70510_h);
            this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70513_ap - (double)this.field_70125_A) / (double)this.field_70510_h);
            --this.field_70510_h;
            this.func_70107_b(☃, ☃x, ☃xx);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
         } else {
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
         }
      } else {
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.04F;
         }

         int ☃ = MathHelper.func_76128_c(this.field_70165_t);
         int ☃x = MathHelper.func_76128_c(this.field_70163_u);
         int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
         if (this.field_70170_p.func_180495_p(new BlockPos(☃, ☃x - 1, ☃xx)).func_203425_a(BlockTags.field_203437_y)) {
            --☃x;
         }

         BlockPos ☃ = new BlockPos(☃, ☃x, ☃xx);
         IBlockState ☃x = this.field_70170_p.func_180495_p(☃);
         if (☃x.func_203425_a(BlockTags.field_203437_y)) {
            this.func_180460_a(☃, ☃x);
            if (☃x.func_177230_c() == Blocks.field_150408_cc) {
               this.func_96095_a(☃, ☃x, ☃xx, ☃x.func_177229_b(BlockRailPowered.field_176569_M));
            }
         } else {
            this.func_180459_n();
         }

         this.func_145775_I();
         this.field_70125_A = 0.0F;
         double ☃ = this.field_70169_q - this.field_70165_t;
         double ☃x = this.field_70166_s - this.field_70161_v;
         if (☃ * ☃ + ☃x * ☃x > 0.001) {
            this.field_70177_z = (float)(MathHelper.func_181159_b(☃x, ☃) * 180.0 / Math.PI);
            if (this.field_70499_f) {
               this.field_70177_z += 180.0F;
            }
         }

         double ☃ = (double)MathHelper.func_76142_g(this.field_70177_z - this.field_70126_B);
         if (☃ < -170.0 || ☃ >= 170.0) {
            this.field_70177_z += 180.0F;
            this.field_70499_f = !this.field_70499_f;
         }

         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         if (this.func_184264_v() == EntityMinecart.Type.RIDEABLE && this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.01) {
            List<Entity> ☃ = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_72314_b(0.2F, 0.0, 0.2F), EntitySelectors.func_200823_a(this));
            if (!☃.isEmpty()) {
               for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
                  Entity ☃xx = (Entity)☃.get(☃x);
                  if (!(☃xx instanceof EntityPlayer)
                     && !(☃xx instanceof EntityIronGolem)
                     && !(☃xx instanceof EntityMinecart)
                     && !this.func_184207_aI()
                     && !☃xx.func_184218_aH()) {
                     ☃xx.func_184220_m(this);
                  } else {
                     ☃xx.func_70108_f(this);
                  }
               }
            }
         } else {
            for(Entity ☃ : this.field_70170_p.func_72839_b(this, this.func_174813_aQ().func_72314_b(0.2F, 0.0, 0.2F))) {
               if (!this.func_184196_w(☃) && ☃.func_70104_M() && ☃ instanceof EntityMinecart) {
                  ☃.func_70108_f(this);
               }
            }
         }

         this.func_70072_I();
      }
   }

   protected double func_174898_m() {
      return 0.4;
   }

   public void func_96095_a(int var1, int var2, int var3, boolean var4) {
   }

   protected void func_180459_n() {
      double ☃ = this.func_174898_m();
      this.field_70159_w = MathHelper.func_151237_a(this.field_70159_w, -☃, ☃);
      this.field_70179_y = MathHelper.func_151237_a(this.field_70179_y, -☃, ☃);
      if (this.field_70122_E) {
         this.field_70159_w *= 0.5;
         this.field_70181_x *= 0.5;
         this.field_70179_y *= 0.5;
      }

      this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      if (!this.field_70122_E) {
         this.field_70159_w *= 0.95F;
         this.field_70181_x *= 0.95F;
         this.field_70179_y *= 0.95F;
      }
   }

   protected void func_180460_a(BlockPos var1, IBlockState var2) {
      this.field_70143_R = 0.0F;
      Vec3d ☃ = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70163_u = (double)☃.func_177956_o();
      boolean ☃x = false;
      boolean ☃xx = false;
      BlockRailBase ☃xxx = (BlockRailBase)☃.func_177230_c();
      if (☃xxx == Blocks.field_196552_aC) {
         ☃x = ☃.func_177229_b(BlockRailPowered.field_176569_M);
         ☃xx = !☃x;
      }

      double ☃ = 0.0078125;
      RailShape ☃x = ☃.func_177229_b(☃xxx.func_176560_l());
      switch(☃x) {
         case ASCENDING_EAST:
            this.field_70159_w -= 0.0078125;
            ++this.field_70163_u;
            break;
         case ASCENDING_WEST:
            this.field_70159_w += 0.0078125;
            ++this.field_70163_u;
            break;
         case ASCENDING_NORTH:
            this.field_70179_y += 0.0078125;
            ++this.field_70163_u;
            break;
         case ASCENDING_SOUTH:
            this.field_70179_y -= 0.0078125;
            ++this.field_70163_u;
      }

      int[][] ☃ = field_70500_g[☃x.func_208091_a()];
      double ☃x = (double)(☃[1][0] - ☃[0][0]);
      double ☃xx = (double)(☃[1][2] - ☃[0][2]);
      double ☃xxx = Math.sqrt(☃x * ☃x + ☃xx * ☃xx);
      double ☃xxxx = this.field_70159_w * ☃x + this.field_70179_y * ☃xx;
      if (☃xxxx < 0.0) {
         ☃x = -☃x;
         ☃xx = -☃xx;
      }

      double ☃ = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      if (☃ > 2.0) {
         ☃ = 2.0;
      }

      this.field_70159_w = ☃ * ☃x / ☃xxx;
      this.field_70179_y = ☃ * ☃xx / ☃xxx;
      Entity ☃ = this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
      if (☃ instanceof EntityPlayer) {
         double ☃x = (double)((EntityPlayer)☃).field_191988_bg;
         if (☃x > 0.0) {
            double ☃xx = -Math.sin((double)(☃.field_70177_z * (float) (Math.PI / 180.0)));
            double ☃xxx = Math.cos((double)(☃.field_70177_z * (float) (Math.PI / 180.0)));
            double ☃xxxx = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
            if (☃xxxx < 0.01) {
               this.field_70159_w += ☃xx * 0.1;
               this.field_70179_y += ☃xxx * 0.1;
               ☃xx = false;
            }
         }
      }

      if (☃xx) {
         double ☃ = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (☃ < 0.03) {
            this.field_70159_w *= 0.0;
            this.field_70181_x *= 0.0;
            this.field_70179_y *= 0.0;
         } else {
            this.field_70159_w *= 0.5;
            this.field_70181_x *= 0.0;
            this.field_70179_y *= 0.5;
         }
      }

      double ☃x = (double)☃.func_177958_n() + 0.5 + (double)☃[0][0] * 0.5;
      double ☃xx = (double)☃.func_177952_p() + 0.5 + (double)☃[0][2] * 0.5;
      double ☃xxx = (double)☃.func_177958_n() + 0.5 + (double)☃[1][0] * 0.5;
      double ☃xxxx = (double)☃.func_177952_p() + 0.5 + (double)☃[1][2] * 0.5;
      ☃x = ☃xxx - ☃x;
      ☃xx = ☃xxxx - ☃xx;
      double ☃;
      if (☃x == 0.0) {
         this.field_70165_t = (double)☃.func_177958_n() + 0.5;
         ☃ = this.field_70161_v - (double)☃.func_177952_p();
      } else if (☃xx == 0.0) {
         this.field_70161_v = (double)☃.func_177952_p() + 0.5;
         ☃ = this.field_70165_t - (double)☃.func_177958_n();
      } else {
         double ☃ = this.field_70165_t - ☃x;
         double ☃x = this.field_70161_v - ☃xx;
         ☃ = (☃ * ☃x + ☃x * ☃xx) * 2.0;
      }

      this.field_70165_t = ☃x + ☃x * ☃;
      this.field_70161_v = ☃xx + ☃xx * ☃;
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      double ☃ = this.field_70159_w;
      double ☃x = this.field_70179_y;
      if (this.func_184207_aI()) {
         ☃ *= 0.75;
         ☃x *= 0.75;
      }

      double ☃ = this.func_174898_m();
      ☃ = MathHelper.func_151237_a(☃, -☃, ☃);
      ☃x = MathHelper.func_151237_a(☃x, -☃, ☃);
      this.func_70091_d(MoverType.SELF, ☃, 0.0, ☃x);
      if (☃[0][1] != 0
         && MathHelper.func_76128_c(this.field_70165_t) - ☃.func_177958_n() == ☃[0][0]
         && MathHelper.func_76128_c(this.field_70161_v) - ☃.func_177952_p() == ☃[0][2]) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)☃[0][1], this.field_70161_v);
      } else if (☃[1][1] != 0
         && MathHelper.func_76128_c(this.field_70165_t) - ☃.func_177958_n() == ☃[1][0]
         && MathHelper.func_76128_c(this.field_70161_v) - ☃.func_177952_p() == ☃[1][2]) {
         this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)☃[1][1], this.field_70161_v);
      }

      this.func_94101_h();
      Vec3d ☃ = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if (☃ != null && ☃ != null) {
         double ☃x = (☃.field_72448_b - ☃.field_72448_b) * 0.05;
         ☃ = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (☃ > 0.0) {
            this.field_70159_w = this.field_70159_w / ☃ * (☃ + ☃x);
            this.field_70179_y = this.field_70179_y / ☃ * (☃ + ☃x);
         }

         this.func_70107_b(this.field_70165_t, ☃.field_72448_b, this.field_70161_v);
      }

      int ☃ = MathHelper.func_76128_c(this.field_70165_t);
      int ☃x = MathHelper.func_76128_c(this.field_70161_v);
      if (☃ != ☃.func_177958_n() || ☃x != ☃.func_177952_p()) {
         ☃ = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70159_w = ☃ * (double)(☃ - ☃.func_177958_n());
         this.field_70179_y = ☃ * (double)(☃x - ☃.func_177952_p());
      }

      if (☃x) {
         double ☃ = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (☃ > 0.01) {
            double ☃x = 0.06;
            this.field_70159_w += this.field_70159_w / ☃ * 0.06;
            this.field_70179_y += this.field_70179_y / ☃ * 0.06;
         } else if (☃x == RailShape.EAST_WEST) {
            if (this.field_70170_p.func_180495_p(☃.func_177976_e()).func_185915_l()) {
               this.field_70159_w = 0.02;
            } else if (this.field_70170_p.func_180495_p(☃.func_177974_f()).func_185915_l()) {
               this.field_70159_w = -0.02;
            }
         } else if (☃x == RailShape.NORTH_SOUTH) {
            if (this.field_70170_p.func_180495_p(☃.func_177978_c()).func_185915_l()) {
               this.field_70179_y = 0.02;
            } else if (this.field_70170_p.func_180495_p(☃.func_177968_d()).func_185915_l()) {
               this.field_70179_y = -0.02;
            }
         }
      }
   }

   protected void func_94101_h() {
      if (this.func_184207_aI()) {
         this.field_70159_w *= 0.997F;
         this.field_70181_x *= 0.0;
         this.field_70179_y *= 0.997F;
      } else {
         this.field_70159_w *= 0.96F;
         this.field_70181_x *= 0.0;
         this.field_70179_y *= 0.96F;
      }
   }

   @Override
   public void func_70107_b(double var1, double var3, double var5) {
      this.field_70165_t = ☃;
      this.field_70163_u = ☃;
      this.field_70161_v = ☃;
      float ☃ = this.field_70130_N / 2.0F;
      float ☃x = this.field_70131_O;
      this.func_174826_a(new AxisAlignedBB(☃ - (double)☃, ☃, ☃ - (double)☃, ☃ + (double)☃, ☃ + (double)☃x, ☃ + (double)☃));
   }

   @Nullable
   public Vec3d func_70495_a(double var1, double var3, double var5, double var7) {
      int ☃ = MathHelper.func_76128_c(☃);
      int ☃x = MathHelper.func_76128_c(☃);
      int ☃xx = MathHelper.func_76128_c(☃);
      if (this.field_70170_p.func_180495_p(new BlockPos(☃, ☃x - 1, ☃xx)).func_203425_a(BlockTags.field_203437_y)) {
         --☃x;
      }

      IBlockState ☃ = this.field_70170_p.func_180495_p(new BlockPos(☃, ☃x, ☃xx));
      if (☃.func_203425_a(BlockTags.field_203437_y)) {
         RailShape ☃x = ☃.func_177229_b(((BlockRailBase)☃.func_177230_c()).func_176560_l());
         ☃ = (double)☃x;
         if (☃x.func_208092_c()) {
            ☃ = (double)(☃x + 1);
         }

         int[][] ☃x = field_70500_g[☃x.func_208091_a()];
         double ☃xx = (double)(☃x[1][0] - ☃x[0][0]);
         double ☃xxx = (double)(☃x[1][2] - ☃x[0][2]);
         double ☃xxxx = Math.sqrt(☃xx * ☃xx + ☃xxx * ☃xxx);
         ☃xx /= ☃xxxx;
         ☃xxx /= ☃xxxx;
         ☃ += ☃xx * ☃;
         ☃ += ☃xxx * ☃;
         if (☃x[0][1] != 0 && MathHelper.func_76128_c(☃) - ☃ == ☃x[0][0] && MathHelper.func_76128_c(☃) - ☃xx == ☃x[0][2]) {
            ☃ += (double)☃x[0][1];
         } else if (☃x[1][1] != 0 && MathHelper.func_76128_c(☃) - ☃ == ☃x[1][0] && MathHelper.func_76128_c(☃) - ☃xx == ☃x[1][2]) {
            ☃ += (double)☃x[1][1];
         }

         return this.func_70489_a(☃, ☃, ☃);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3d func_70489_a(double var1, double var3, double var5) {
      int ☃ = MathHelper.func_76128_c(☃);
      int ☃x = MathHelper.func_76128_c(☃);
      int ☃xx = MathHelper.func_76128_c(☃);
      if (this.field_70170_p.func_180495_p(new BlockPos(☃, ☃x - 1, ☃xx)).func_203425_a(BlockTags.field_203437_y)) {
         --☃x;
      }

      IBlockState ☃ = this.field_70170_p.func_180495_p(new BlockPos(☃, ☃x, ☃xx));
      if (☃.func_203425_a(BlockTags.field_203437_y)) {
         RailShape ☃xx = ☃.func_177229_b(((BlockRailBase)☃.func_177230_c()).func_176560_l());
         int[][] ☃xxx = field_70500_g[☃xx.func_208091_a()];
         double ☃xxxx = (double)☃ + 0.5 + (double)☃xxx[0][0] * 0.5;
         double ☃xxxxx = (double)☃x + 0.0625 + (double)☃xxx[0][1] * 0.5;
         double ☃xxxxxx = (double)☃xx + 0.5 + (double)☃xxx[0][2] * 0.5;
         double ☃xxxxxxx = (double)☃ + 0.5 + (double)☃xxx[1][0] * 0.5;
         double ☃xxxxxxxx = (double)☃x + 0.0625 + (double)☃xxx[1][1] * 0.5;
         double ☃xxxxxxxxx = (double)☃xx + 0.5 + (double)☃xxx[1][2] * 0.5;
         double ☃xxxxxxxxxx = ☃xxxxxxx - ☃xxxx;
         double ☃xxxxxxxxxxx = (☃xxxxxxxx - ☃xxxxx) * 2.0;
         double ☃xxxxxxxxxxxx = ☃xxxxxxxxx - ☃xxxxxx;
         double ☃x;
         if (☃xxxxxxxxxx == 0.0) {
            ☃x = ☃ - (double)☃xx;
         } else if (☃xxxxxxxxxxxx == 0.0) {
            ☃x = ☃ - (double)☃;
         } else {
            double ☃x = ☃ - ☃xxxx;
            double ☃xx = ☃ - ☃xxxxxx;
            ☃x = (☃x * ☃xxxxxxxxxx + ☃xx * ☃xxxxxxxxxxxx) * 2.0;
         }

         ☃ = ☃xxxx + ☃xxxxxxxxxx * ☃x;
         ☃ = ☃xxxxx + ☃xxxxxxxxxxx * ☃x;
         ☃ = ☃xxxxxx + ☃xxxxxxxxxxxx * ☃x;
         if (☃xxxxxxxxxxx < 0.0) {
            ++☃;
         }

         if (☃xxxxxxxxxxx > 0.0) {
            ☃ += 0.5;
         }

         return new Vec3d(☃, ☃, ☃);
      } else {
         return null;
      }
   }

   @Override
   public AxisAlignedBB func_184177_bl() {
      AxisAlignedBB ☃ = this.func_174813_aQ();
      return this.func_94100_s() ? ☃.func_186662_g((double)Math.abs(this.func_94099_q()) / 16.0) : ☃;
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      if (☃.func_74767_n("CustomDisplayTile")) {
         this.func_174899_a(NBTUtil.func_190008_d(☃.func_74775_l("DisplayState")));
         this.func_94086_l(☃.func_74762_e("DisplayOffset"));
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      if (this.func_94100_s()) {
         ☃.func_74757_a("CustomDisplayTile", true);
         ☃.func_74782_a("DisplayState", NBTUtil.func_190009_a(this.func_174897_t()));
         ☃.func_74768_a("DisplayOffset", this.func_94099_q());
      }
   }

   @Override
   public void func_70108_f(Entity var1) {
      if (!this.field_70170_p.field_72995_K) {
         if (!☃.field_70145_X && !this.field_70145_X) {
            if (!this.func_184196_w(☃)) {
               double ☃ = ☃.field_70165_t - this.field_70165_t;
               double ☃x = ☃.field_70161_v - this.field_70161_v;
               double ☃xx = ☃ * ☃ + ☃x * ☃x;
               if (☃xx >= 1.0E-4F) {
                  ☃xx = (double)MathHelper.func_76133_a(☃xx);
                  ☃ /= ☃xx;
                  ☃x /= ☃xx;
                  double ☃xxx = 1.0 / ☃xx;
                  if (☃xxx > 1.0) {
                     ☃xxx = 1.0;
                  }

                  ☃ *= ☃xxx;
                  ☃x *= ☃xxx;
                  ☃ *= 0.1F;
                  ☃x *= 0.1F;
                  ☃ *= (double)(1.0F - this.field_70144_Y);
                  ☃x *= (double)(1.0F - this.field_70144_Y);
                  ☃ *= 0.5;
                  ☃x *= 0.5;
                  if (☃ instanceof EntityMinecart) {
                     double ☃xxx = ☃.field_70165_t - this.field_70165_t;
                     double ☃xxxx = ☃.field_70161_v - this.field_70161_v;
                     Vec3d ☃xxxxx = new Vec3d(☃xxx, 0.0, ☃xxxx).func_72432_b();
                     Vec3d ☃xxxxxx = new Vec3d(
                           (double)MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)),
                           0.0,
                           (double)MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0))
                        )
                        .func_72432_b();
                     double ☃xxxxxxx = Math.abs(☃xxxxx.func_72430_b(☃xxxxxx));
                     if (☃xxxxxxx < 0.8F) {
                        return;
                     }

                     double ☃xxx = ☃.field_70159_w + this.field_70159_w;
                     double ☃xxxx = ☃.field_70179_y + this.field_70179_y;
                     if (((EntityMinecart)☃).func_184264_v() == EntityMinecart.Type.FURNACE && this.func_184264_v() != EntityMinecart.Type.FURNACE) {
                        this.field_70159_w *= 0.2F;
                        this.field_70179_y *= 0.2F;
                        this.func_70024_g(☃.field_70159_w - ☃, 0.0, ☃.field_70179_y - ☃x);
                        ☃.field_70159_w *= 0.95F;
                        ☃.field_70179_y *= 0.95F;
                     } else if (((EntityMinecart)☃).func_184264_v() != EntityMinecart.Type.FURNACE && this.func_184264_v() == EntityMinecart.Type.FURNACE) {
                        ☃.field_70159_w *= 0.2F;
                        ☃.field_70179_y *= 0.2F;
                        ☃.func_70024_g(this.field_70159_w + ☃, 0.0, this.field_70179_y + ☃x);
                        this.field_70159_w *= 0.95F;
                        this.field_70179_y *= 0.95F;
                     } else {
                        ☃xxx /= 2.0;
                        ☃xxxx /= 2.0;
                        this.field_70159_w *= 0.2F;
                        this.field_70179_y *= 0.2F;
                        this.func_70024_g(☃xxx - ☃, 0.0, ☃xxxx - ☃x);
                        ☃.field_70159_w *= 0.2F;
                        ☃.field_70179_y *= 0.2F;
                        ☃.func_70024_g(☃xxx + ☃, 0.0, ☃xxxx + ☃x);
                     }
                  } else {
                     this.func_70024_g(-☃, 0.0, -☃x);
                     ☃.func_70024_g(☃ / 4.0, 0.0, ☃x / 4.0);
                  }
               }
            }
         }
      }
   }

   @Override
   public void func_180426_a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.field_70511_i = ☃;
      this.field_70509_j = ☃;
      this.field_70514_an = ☃;
      this.field_70512_ao = (double)☃;
      this.field_70513_ap = (double)☃;
      this.field_70510_h = ☃ + 2;
      this.field_70159_w = this.field_70508_aq;
      this.field_70181_x = this.field_70507_ar;
      this.field_70179_y = this.field_70506_as;
   }

   @Override
   public void func_70016_h(double var1, double var3, double var5) {
      this.field_70159_w = ☃;
      this.field_70181_x = ☃;
      this.field_70179_y = ☃;
      this.field_70508_aq = this.field_70159_w;
      this.field_70507_ar = this.field_70181_x;
      this.field_70506_as = this.field_70179_y;
   }

   public void func_70492_c(float var1) {
      this.field_70180_af.func_187227_b(field_184267_c, ☃);
   }

   public float func_70491_i() {
      return this.field_70180_af.func_187225_a(field_184267_c);
   }

   public void func_70497_h(int var1) {
      this.field_70180_af.func_187227_b(field_184265_a, ☃);
   }

   public int func_70496_j() {
      return this.field_70180_af.func_187225_a(field_184265_a);
   }

   public void func_70494_i(int var1) {
      this.field_70180_af.func_187227_b(field_184266_b, ☃);
   }

   public int func_70493_k() {
      return this.field_70180_af.func_187225_a(field_184266_b);
   }

   public abstract EntityMinecart.Type func_184264_v();

   public IBlockState func_174897_t() {
      return !this.func_94100_s() ? this.func_180457_u() : Block.func_196257_b(this.func_184212_Q().func_187225_a(field_184268_d));
   }

   public IBlockState func_180457_u() {
      return Blocks.field_150350_a.func_176223_P();
   }

   public int func_94099_q() {
      return !this.func_94100_s() ? this.func_94085_r() : this.func_184212_Q().func_187225_a(field_184269_e);
   }

   public int func_94085_r() {
      return 6;
   }

   public void func_174899_a(IBlockState var1) {
      this.func_184212_Q().func_187227_b(field_184268_d, Block.func_196246_j(☃));
      this.func_94096_e(true);
   }

   public void func_94086_l(int var1) {
      this.func_184212_Q().func_187227_b(field_184269_e, ☃);
      this.func_94096_e(true);
   }

   public boolean func_94100_s() {
      return this.func_184212_Q().func_187225_a(field_184270_f);
   }

   public void func_94096_e(boolean var1) {
      this.func_184212_Q().func_187227_b(field_184270_f, ☃);
   }

   public static enum Type {
      RIDEABLE(0),
      CHEST(1),
      FURNACE(2),
      TNT(3),
      SPAWNER(4),
      HOPPER(5),
      COMMAND_BLOCK(6);

      private static final EntityMinecart.Type[] field_184965_h = (EntityMinecart.Type[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(EntityMinecart.Type::func_184956_a))
         .toArray(var0 -> new EntityMinecart.Type[var0]);
      private final int field_184966_i;

      private Type(int var3) {
         this.field_184966_i = ☃;
      }

      public int func_184956_a() {
         return this.field_184966_i;
      }

      public static EntityMinecart.Type func_184955_a(int var0) {
         return ☃ >= 0 && ☃ < field_184965_h.length ? field_184965_h[☃] : RIDEABLE;
      }
   }
}
