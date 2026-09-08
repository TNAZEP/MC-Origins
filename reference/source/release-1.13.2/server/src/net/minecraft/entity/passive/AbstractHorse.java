package net.minecraft.entity.passive;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public abstract class AbstractHorse extends EntityAnimal implements IInventoryChangedListener, IJumpingMount {
   private static final Predicate<Entity> field_110276_bu = var0 -> var0 instanceof AbstractHorse && ((AbstractHorse)var0).func_110205_ce();
   protected static final IAttribute field_110271_bv = new RangedAttribute(null, "horse.jumpStrength", 0.7, 0.0, 2.0)
      .func_111117_a("Jump Strength")
      .func_111112_a(true);
   private static final DataParameter<Byte> field_184787_bE = EntityDataManager.func_187226_a(AbstractHorse.class, DataSerializers.field_187191_a);
   private static final DataParameter<Optional<UUID>> field_184790_bH = EntityDataManager.func_187226_a(AbstractHorse.class, DataSerializers.field_187203_m);
   private int field_190689_bJ;
   private int field_110290_bE;
   private int field_110295_bF;
   public int field_110278_bp;
   public int field_110279_bq;
   protected boolean field_110275_br;
   protected ContainerHorseChest field_110296_bG;
   protected int field_110274_bs;
   protected float field_110277_bt;
   private boolean field_110294_bI;
   private float field_110283_bJ;
   private float field_110284_bK;
   private float field_110281_bL;
   private float field_110282_bM;
   private float field_110287_bN;
   private float field_110288_bO;
   protected boolean field_190688_bE = true;
   protected int field_110285_bP;

   protected AbstractHorse(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(1.3964844F, 1.6F);
      this.field_70138_W = 1.0F;
      this.func_110226_cD();
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.2));
      this.field_70714_bg.func_75776_a(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0, AbstractHorse.class));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.0));
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 0.7));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.func_205714_dM();
   }

   protected void func_205714_dM() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184787_bE, (byte)0);
      this.field_70180_af.func_187214_a(field_184790_bH, Optional.empty());
   }

   protected boolean func_110233_w(int var1) {
      return (this.field_70180_af.func_187225_a(field_184787_bE) & ☃) != 0;
   }

   protected void func_110208_b(int var1, boolean var2) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184787_bE);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184787_bE, (byte)(☃ | ☃));
      } else {
         this.field_70180_af.func_187227_b(field_184787_bE, (byte)(☃ & ~☃));
      }
   }

   public boolean func_110248_bS() {
      return this.func_110233_w(2);
   }

   @Nullable
   public UUID func_184780_dh() {
      return (UUID)((Optional)this.field_70180_af.func_187225_a(field_184790_bH)).orElse(null);
   }

   public void func_184779_b(@Nullable UUID var1) {
      this.field_70180_af.func_187227_b(field_184790_bH, Optional.ofNullable(☃));
   }

   public float func_110254_bY() {
      return 0.5F;
   }

   @Override
   public void func_98054_a(boolean var1) {
      this.func_98055_j(☃ ? this.func_110254_bY() : 1.0F);
   }

   public boolean func_110246_bZ() {
      return this.field_110275_br;
   }

   public void func_110234_j(boolean var1) {
      this.func_110208_b(2, ☃);
   }

   public void func_110255_k(boolean var1) {
      this.field_110275_br = ☃;
   }

   @Override
   public boolean func_184652_a(EntityPlayer var1) {
      return super.func_184652_a(☃) && this.func_70668_bt() != CreatureAttribute.UNDEAD;
   }

   @Override
   protected void func_142017_o(float var1) {
      if (☃ > 6.0F && this.func_110204_cc()) {
         this.func_110227_p(false);
      }
   }

   public boolean func_110204_cc() {
      return this.func_110233_w(16);
   }

   public boolean func_110209_cd() {
      return this.func_110233_w(32);
   }

   public boolean func_110205_ce() {
      return this.func_110233_w(8);
   }

   public void func_110242_l(boolean var1) {
      this.func_110208_b(8, ☃);
   }

   public void func_110251_o(boolean var1) {
      this.func_110208_b(4, ☃);
   }

   public int func_110252_cg() {
      return this.field_110274_bs;
   }

   public void func_110238_s(int var1) {
      this.field_110274_bs = ☃;
   }

   public int func_110198_t(int var1) {
      int ☃ = MathHelper.func_76125_a(this.func_110252_cg() + ☃, 0, this.func_190676_dC());
      this.func_110238_s(☃);
      return ☃;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      Entity ☃ = ☃.func_76346_g();
      return this.func_184207_aI() && ☃ != null && this.func_184215_y(☃) ? false : super.func_70097_a(☃, ☃);
   }

   @Override
   public boolean func_70104_M() {
      return !this.func_184207_aI();
   }

   private void func_110266_cB() {
      this.func_110249_cI();
      if (!this.func_174814_R()) {
         this.field_70170_p
            .func_184148_a(
               null,
               this.field_70165_t,
               this.field_70163_u,
               this.field_70161_v,
               SoundEvents.field_187711_cp,
               this.func_184176_by(),
               1.0F,
               1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F
            );
      }
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      if (☃ > 1.0F) {
         this.func_184185_a(SoundEvents.field_187723_ct, 0.4F, 1.0F);
      }

      int ☃ = MathHelper.func_76123_f((☃ * 0.5F - 3.0F) * ☃);
      if (☃ > 0) {
         this.func_70097_a(DamageSource.field_76379_h, (float)☃);
         if (this.func_184207_aI()) {
            for(Entity ☃x : this.func_184182_bu()) {
               ☃x.func_70097_a(DamageSource.field_76379_h, (float)☃);
            }
         }

         IBlockState ☃x = this.field_70170_p
            .func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u - 0.2 - (double)this.field_70126_B, this.field_70161_v));
         Block ☃xx = ☃x.func_177230_c();
         if (!☃x.func_196958_f() && !this.func_174814_R()) {
            SoundType ☃xxx = ☃xx.func_185467_w();
            this.field_70170_p
               .func_184148_a(
                  null,
                  this.field_70165_t,
                  this.field_70163_u,
                  this.field_70161_v,
                  ☃xxx.func_185844_d(),
                  this.func_184176_by(),
                  ☃xxx.func_185843_a() * 0.5F,
                  ☃xxx.func_185847_b() * 0.75F
               );
         }
      }
   }

   protected int func_190686_di() {
      return 2;
   }

   protected void func_110226_cD() {
      ContainerHorseChest ☃ = this.field_110296_bG;
      this.field_110296_bG = new ContainerHorseChest(this.func_200200_C_(), this.func_190686_di());
      this.field_110296_bG.func_200228_a(this.func_200201_e());
      if (☃ != null) {
         ☃.func_110132_b(this);
         int ☃x = Math.min(☃.func_70302_i_(), this.field_110296_bG.func_70302_i_());

         for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
            ItemStack ☃xxx = ☃.func_70301_a(☃xx);
            if (!☃xxx.func_190926_b()) {
               this.field_110296_bG.func_70299_a(☃xx, ☃xxx.func_77946_l());
            }
         }
      }

      this.field_110296_bG.func_110134_a(this);
      this.func_110232_cE();
   }

   protected void func_110232_cE() {
      if (!this.field_70170_p.field_72995_K) {
         this.func_110251_o(!this.field_110296_bG.func_70301_a(0).func_190926_b() && this.func_190685_dA());
      }
   }

   @Override
   public void func_76316_a(IInventory var1) {
      boolean ☃ = this.func_110257_ck();
      this.func_110232_cE();
      if (this.field_70173_aa > 20 && !☃ && this.func_110257_ck()) {
         this.func_184185_a(SoundEvents.field_187726_cu, 0.5F, 1.0F);
      }
   }

   @Nullable
   protected AbstractHorse func_110250_a(Entity var1, double var2) {
      double ☃ = Double.MAX_VALUE;
      Entity ☃x = null;

      for(Entity ☃xx : this.field_70170_p.func_175674_a(☃, ☃.func_174813_aQ().func_72321_a(☃, ☃, ☃), field_110276_bu)) {
         double ☃xxx = ☃xx.func_70092_e(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
         if (☃xxx < ☃) {
            ☃x = ☃xx;
            ☃ = ☃xxx;
         }
      }

      return (AbstractHorse)☃x;
   }

   public double func_110215_cj() {
      return this.func_110148_a(field_110271_bv).func_111126_e();
   }

   @Nullable
   @Override
   protected SoundEvent func_184615_bR() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_110220_cK();
      }

      return null;
   }

   @Nullable
   @Override
   protected SoundEvent func_184639_G() {
      if (this.field_70146_Z.nextInt(10) == 0 && !this.func_70610_aX()) {
         this.func_110220_cK();
      }

      return null;
   }

   public boolean func_190685_dA() {
      return true;
   }

   public boolean func_110257_ck() {
      return this.func_110233_w(4);
   }

   @Nullable
   protected SoundEvent func_184785_dv() {
      this.func_110220_cK();
      return null;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      if (!☃.func_185904_a().func_76224_d()) {
         SoundType ☃ = ☃.func_177230_c().func_185467_w();
         if (this.field_70170_p.func_180495_p(☃.func_177984_a()).func_177230_c() == Blocks.field_150433_aE) {
            ☃ = Blocks.field_150433_aE.func_185467_w();
         }

         if (this.func_184207_aI() && this.field_190688_bE) {
            ++this.field_110285_bP;
            if (this.field_110285_bP > 5 && this.field_110285_bP % 3 == 0) {
               this.func_190680_a(☃);
            } else if (this.field_110285_bP <= 5) {
               this.func_184185_a(SoundEvents.field_187732_cw, ☃.func_185843_a() * 0.15F, ☃.func_185847_b());
            }
         } else if (☃ == SoundType.field_185848_a) {
            this.func_184185_a(SoundEvents.field_187732_cw, ☃.func_185843_a() * 0.15F, ☃.func_185847_b());
         } else {
            this.func_184185_a(SoundEvents.field_187729_cv, ☃.func_185843_a() * 0.15F, ☃.func_185847_b());
         }
      }
   }

   protected void func_190680_a(SoundType var1) {
      this.func_184185_a(SoundEvents.field_187714_cq, ☃.func_185843_a() * 0.15F, ☃.func_185847_b());
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(field_110271_bv);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(53.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.225F);
   }

   @Override
   public int func_70641_bl() {
      return 6;
   }

   public int func_190676_dC() {
      return 100;
   }

   @Override
   protected float func_70599_aP() {
      return 0.8F;
   }

   @Override
   public int func_70627_aG() {
      return 400;
   }

   public void func_110199_f(EntityPlayer var1) {
      if (!this.field_70170_p.field_72995_K && (!this.func_184207_aI() || this.func_184196_w(☃)) && this.func_110248_bS()) {
         this.field_110296_bG.func_200228_a(this.func_200201_e());
         ☃.func_184826_a(this, this.field_110296_bG);
      }
   }

   protected boolean func_190678_b(EntityPlayer var1, ItemStack var2) {
      boolean ☃ = false;
      float ☃x = 0.0F;
      int ☃xx = 0;
      int ☃xxx = 0;
      Item ☃xxxx = ☃.func_77973_b();
      if (☃xxxx == Items.field_151015_O) {
         ☃x = 2.0F;
         ☃xx = 20;
         ☃xxx = 3;
      } else if (☃xxxx == Items.field_151102_aT) {
         ☃x = 1.0F;
         ☃xx = 30;
         ☃xxx = 3;
      } else if (☃xxxx == Blocks.field_150407_cf.func_199767_j()) {
         ☃x = 20.0F;
         ☃xx = 180;
      } else if (☃xxxx == Items.field_151034_e) {
         ☃x = 3.0F;
         ☃xx = 60;
         ☃xxx = 3;
      } else if (☃xxxx == Items.field_151150_bK) {
         ☃x = 4.0F;
         ☃xx = 60;
         ☃xxx = 5;
         if (this.func_110248_bS() && this.func_70874_b() == 0 && !this.func_70880_s()) {
            ☃ = true;
            this.func_146082_f(☃);
         }
      } else if (☃xxxx == Items.field_151153_ao || ☃xxxx == Items.field_196100_at) {
         ☃x = 10.0F;
         ☃xx = 240;
         ☃xxx = 10;
         if (this.func_110248_bS() && this.func_70874_b() == 0 && !this.func_70880_s()) {
            ☃ = true;
            this.func_146082_f(☃);
         }
      }

      if (this.func_110143_aJ() < this.func_110138_aP() && ☃x > 0.0F) {
         this.func_70691_i(☃x);
         ☃ = true;
      }

      if (this.func_70631_g_() && ☃xx > 0) {
         this.field_70170_p
            .func_195594_a(
               Particles.field_197632_y,
               this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               this.field_70163_u + 0.5 + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O),
               this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               0.0,
               0.0,
               0.0
            );
         if (!this.field_70170_p.field_72995_K) {
            this.func_110195_a(☃xx);
         }

         ☃ = true;
      }

      if (☃xxx > 0 && (☃ || !this.func_110248_bS()) && this.func_110252_cg() < this.func_190676_dC()) {
         ☃ = true;
         if (!this.field_70170_p.field_72995_K) {
            this.func_110198_t(☃xxx);
         }
      }

      if (☃) {
         this.func_110266_cB();
      }

      return ☃;
   }

   protected void func_110237_h(EntityPlayer var1) {
      this.func_110227_p(false);
      this.func_110219_q(false);
      if (!this.field_70170_p.field_72995_K) {
         ☃.field_70177_z = this.field_70177_z;
         ☃.field_70125_A = this.field_70125_A;
         ☃.func_184220_m(this);
      }
   }

   @Override
   protected boolean func_70610_aX() {
      return super.func_70610_aX() && this.func_184207_aI() && this.func_110257_ck() || this.func_110204_cc() || this.func_110209_cd();
   }

   @Override
   public boolean func_70877_b(ItemStack var1) {
      return false;
   }

   private void func_110210_cH() {
      this.field_110278_bp = 1;
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      if (!this.field_70170_p.field_72995_K && this.field_110296_bG != null) {
         for(int ☃ = 0; ☃ < this.field_110296_bG.func_70302_i_(); ++☃) {
            ItemStack ☃x = this.field_110296_bG.func_70301_a(☃);
            if (!☃x.func_190926_b()) {
               this.func_199701_a_(☃x);
            }
         }
      }
   }

   @Override
   public void func_70636_d() {
      if (this.field_70146_Z.nextInt(200) == 0) {
         this.func_110210_cH();
      }

      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70146_Z.nextInt(900) == 0 && this.field_70725_aQ == 0) {
            this.func_70691_i(1.0F);
         }

         if (this.func_190684_dE()) {
            if (!this.func_110204_cc()
               && !this.func_184207_aI()
               && this.field_70146_Z.nextInt(300) == 0
               && this.field_70170_p
                     .func_180495_p(
                        new BlockPos(
                           MathHelper.func_76128_c(this.field_70165_t),
                           MathHelper.func_76128_c(this.field_70163_u) - 1,
                           MathHelper.func_76128_c(this.field_70161_v)
                        )
                     )
                     .func_177230_c()
                  == Blocks.field_196658_i) {
               this.func_110227_p(true);
            }

            if (this.func_110204_cc() && ++this.field_190689_bJ > 50) {
               this.field_190689_bJ = 0;
               this.func_110227_p(false);
            }
         }

         this.func_190679_dD();
      }
   }

   protected void func_190679_dD() {
      if (this.func_110205_ce() && this.func_70631_g_() && !this.func_110204_cc()) {
         AbstractHorse ☃ = this.func_110250_a(this, 16.0);
         if (☃ != null && this.func_70068_e(☃) > 4.0) {
            this.field_70699_by.func_75494_a(☃);
         }
      }
   }

   public boolean func_190684_dE() {
      return true;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_110290_bE > 0 && ++this.field_110290_bE > 30) {
         this.field_110290_bE = 0;
         this.func_110208_b(64, false);
      }

      if ((this.func_184186_bw() || this.func_70613_aW()) && this.field_110295_bF > 0 && ++this.field_110295_bF > 20) {
         this.field_110295_bF = 0;
         this.func_110219_q(false);
      }

      if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
         this.field_110278_bp = 0;
      }

      if (this.field_110279_bq > 0) {
         ++this.field_110279_bq;
         if (this.field_110279_bq > 300) {
            this.field_110279_bq = 0;
         }
      }

      this.field_110284_bK = this.field_110283_bJ;
      if (this.func_110204_cc()) {
         this.field_110283_bJ += (1.0F - this.field_110283_bJ) * 0.4F + 0.05F;
         if (this.field_110283_bJ > 1.0F) {
            this.field_110283_bJ = 1.0F;
         }
      } else {
         this.field_110283_bJ += (0.0F - this.field_110283_bJ) * 0.4F - 0.05F;
         if (this.field_110283_bJ < 0.0F) {
            this.field_110283_bJ = 0.0F;
         }
      }

      this.field_110282_bM = this.field_110281_bL;
      if (this.func_110209_cd()) {
         this.field_110283_bJ = 0.0F;
         this.field_110284_bK = this.field_110283_bJ;
         this.field_110281_bL += (1.0F - this.field_110281_bL) * 0.4F + 0.05F;
         if (this.field_110281_bL > 1.0F) {
            this.field_110281_bL = 1.0F;
         }
      } else {
         this.field_110294_bI = false;
         this.field_110281_bL += (0.8F * this.field_110281_bL * this.field_110281_bL * this.field_110281_bL - this.field_110281_bL) * 0.6F - 0.05F;
         if (this.field_110281_bL < 0.0F) {
            this.field_110281_bL = 0.0F;
         }
      }

      this.field_110288_bO = this.field_110287_bN;
      if (this.func_110233_w(64)) {
         this.field_110287_bN += (1.0F - this.field_110287_bN) * 0.7F + 0.05F;
         if (this.field_110287_bN > 1.0F) {
            this.field_110287_bN = 1.0F;
         }
      } else {
         this.field_110287_bN += (0.0F - this.field_110287_bN) * 0.7F - 0.05F;
         if (this.field_110287_bN < 0.0F) {
            this.field_110287_bN = 0.0F;
         }
      }
   }

   private void func_110249_cI() {
      if (!this.field_70170_p.field_72995_K) {
         this.field_110290_bE = 1;
         this.func_110208_b(64, true);
      }
   }

   public void func_110227_p(boolean var1) {
      this.func_110208_b(16, ☃);
   }

   public void func_110219_q(boolean var1) {
      if (☃) {
         this.func_110227_p(false);
      }

      this.func_110208_b(32, ☃);
   }

   private void func_110220_cK() {
      if (this.func_184186_bw() || this.func_70613_aW()) {
         this.field_110295_bF = 1;
         this.func_110219_q(true);
      }
   }

   public void func_190687_dF() {
      this.func_110220_cK();
      SoundEvent ☃ = this.func_184785_dv();
      if (☃ != null) {
         this.func_184185_a(☃, this.func_70599_aP(), this.func_70647_i());
      }
   }

   public boolean func_110263_g(EntityPlayer var1) {
      this.func_184779_b(☃.func_110124_au());
      this.func_110234_j(true);
      if (☃ instanceof EntityPlayerMP) {
         CriteriaTriggers.field_193136_w.func_193178_a((EntityPlayerMP)☃, this);
      }

      this.field_70170_p.func_72960_a(this, (byte)7);
      return true;
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      if (this.func_184207_aI() && this.func_82171_bF() && this.func_110257_ck()) {
         EntityLivingBase ☃ = (EntityLivingBase)this.func_184179_bs();
         this.field_70177_z = ☃.field_70177_z;
         this.field_70126_B = this.field_70177_z;
         this.field_70125_A = ☃.field_70125_A * 0.5F;
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         this.field_70761_aq = this.field_70177_z;
         this.field_70759_as = this.field_70761_aq;
         ☃ = ☃.field_70702_br * 0.5F;
         ☃ = ☃.field_191988_bg;
         if (☃ <= 0.0F) {
            ☃ *= 0.25F;
            this.field_110285_bP = 0;
         }

         if (this.field_70122_E && this.field_110277_bt == 0.0F && this.func_110209_cd() && !this.field_110294_bI) {
            ☃ = 0.0F;
            ☃ = 0.0F;
         }

         if (this.field_110277_bt > 0.0F && !this.func_110246_bZ() && this.field_70122_E) {
            this.field_70181_x = this.func_110215_cj() * (double)this.field_110277_bt;
            if (this.func_70644_a(MobEffects.field_76430_j)) {
               this.field_70181_x += (double)((float)(this.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
            }

            this.func_110255_k(true);
            this.field_70160_al = true;
            if (☃ > 0.0F) {
               float ☃ = MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0));
               float ☃x = MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0));
               this.field_70159_w += (double)(-0.4F * ☃ * this.field_110277_bt);
               this.field_70179_y += (double)(0.4F * ☃x * this.field_110277_bt);
               this.func_205715_ee();
            }

            this.field_110277_bt = 0.0F;
         }

         this.field_70747_aH = this.func_70689_ay() * 0.1F;
         if (this.func_184186_bw()) {
            this.func_70659_e((float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            super.func_191986_a(☃, ☃, ☃);
         } else if (☃ instanceof EntityPlayer) {
            this.field_70159_w = 0.0;
            this.field_70181_x = 0.0;
            this.field_70179_y = 0.0;
         }

         if (this.field_70122_E) {
            this.field_110277_bt = 0.0F;
            this.func_110255_k(false);
         }

         this.field_184618_aE = this.field_70721_aZ;
         double ☃ = this.field_70165_t - this.field_70169_q;
         double ☃x = this.field_70161_v - this.field_70166_s;
         float ☃xx = MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x) * 4.0F;
         if (☃xx > 1.0F) {
            ☃xx = 1.0F;
         }

         this.field_70721_aZ += (☃xx - this.field_70721_aZ) * 0.4F;
         this.field_184619_aG += this.field_70721_aZ;
      } else {
         this.field_70747_aH = 0.02F;
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   protected void func_205715_ee() {
      this.func_184185_a(SoundEvents.field_187720_cs, 0.4F, 1.0F);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("EatingHaystack", this.func_110204_cc());
      ☃.func_74757_a("Bred", this.func_110205_ce());
      ☃.func_74768_a("Temper", this.func_110252_cg());
      ☃.func_74757_a("Tame", this.func_110248_bS());
      if (this.func_184780_dh() != null) {
         ☃.func_74778_a("OwnerUUID", this.func_184780_dh().toString());
      }

      if (!this.field_110296_bG.func_70301_a(0).func_190926_b()) {
         ☃.func_74782_a("SaddleItem", this.field_110296_bG.func_70301_a(0).func_77955_b(new NBTTagCompound()));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_110227_p(☃.func_74767_n("EatingHaystack"));
      this.func_110242_l(☃.func_74767_n("Bred"));
      this.func_110238_s(☃.func_74762_e("Temper"));
      this.func_110234_j(☃.func_74767_n("Tame"));
      String ☃;
      if (☃.func_150297_b("OwnerUUID", 8)) {
         ☃ = ☃.func_74779_i("OwnerUUID");
      } else {
         String ☃ = ☃.func_74779_i("Owner");
         ☃ = PreYggdrasilConverter.func_187473_a(this.func_184102_h(), ☃);
      }

      if (!☃.isEmpty()) {
         this.func_184779_b(UUID.fromString(☃));
      }

      IAttributeInstance ☃ = this.func_110140_aT().func_111152_a("Speed");
      if (☃ != null) {
         this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(☃.func_111125_b() * 0.25);
      }

      if (☃.func_150297_b("SaddleItem", 10)) {
         ItemStack ☃ = ItemStack.func_199557_a(☃.func_74775_l("SaddleItem"));
         if (☃.func_77973_b() == Items.field_151141_av) {
            this.field_110296_bG.func_70299_a(0, ☃);
         }
      }

      this.func_110232_cE();
   }

   @Override
   public boolean func_70878_b(EntityAnimal var1) {
      return false;
   }

   protected boolean func_110200_cJ() {
      return !this.func_184207_aI()
         && !this.func_184218_aH()
         && this.func_110248_bS()
         && !this.func_70631_g_()
         && this.func_110143_aJ() >= this.func_110138_aP()
         && this.func_70880_s();
   }

   @Nullable
   @Override
   public EntityAgeable func_90011_a(EntityAgeable var1) {
      return null;
   }

   protected void func_190681_a(EntityAgeable var1, AbstractHorse var2) {
      double ☃ = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b()
         + ☃.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b()
         + (double)this.func_110267_cL();
      ☃.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(☃ / 3.0);
      double ☃x = this.func_110148_a(field_110271_bv).func_111125_b() + ☃.func_110148_a(field_110271_bv).func_111125_b() + this.func_110245_cM();
      ☃.func_110148_a(field_110271_bv).func_111128_a(☃x / 3.0);
      double ☃xx = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b()
         + ☃.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b()
         + this.func_110203_cN();
      ☃.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(☃xx / 3.0);
   }

   @Override
   public boolean func_82171_bF() {
      return this.func_184179_bs() instanceof EntityLivingBase;
   }

   @Override
   public boolean func_184776_b() {
      return this.func_110257_ck();
   }

   @Override
   public void func_184775_b(int var1) {
      this.field_110294_bI = true;
      this.func_110220_cK();
   }

   @Override
   public void func_184777_r_() {
   }

   @Override
   public void func_184232_k(Entity var1) {
      super.func_184232_k(☃);
      if (☃ instanceof EntityLiving) {
         EntityLiving ☃ = (EntityLiving)☃;
         this.field_70761_aq = ☃.field_70761_aq;
      }

      if (this.field_110282_bM > 0.0F) {
         float ☃ = MathHelper.func_76126_a(this.field_70761_aq * (float) (Math.PI / 180.0));
         float ☃x = MathHelper.func_76134_b(this.field_70761_aq * (float) (Math.PI / 180.0));
         float ☃xx = 0.7F * this.field_110282_bM;
         float ☃xxx = 0.15F * this.field_110282_bM;
         ☃.func_70107_b(
            this.field_70165_t + (double)(☃xx * ☃),
            this.field_70163_u + this.func_70042_X() + ☃.func_70033_W() + (double)☃xxx,
            this.field_70161_v - (double)(☃xx * ☃x)
         );
         if (☃ instanceof EntityLivingBase) {
            ((EntityLivingBase)☃).field_70761_aq = this.field_70761_aq;
         }
      }
   }

   protected float func_110267_cL() {
      return 15.0F + (float)this.field_70146_Z.nextInt(8) + (float)this.field_70146_Z.nextInt(9);
   }

   protected double func_110245_cM() {
      return 0.4F + this.field_70146_Z.nextDouble() * 0.2 + this.field_70146_Z.nextDouble() * 0.2 + this.field_70146_Z.nextDouble() * 0.2;
   }

   protected double func_110203_cN() {
      return (0.45F + this.field_70146_Z.nextDouble() * 0.3 + this.field_70146_Z.nextDouble() * 0.3 + this.field_70146_Z.nextDouble() * 0.3) * 0.25;
   }

   @Override
   public boolean func_70617_f_() {
      return false;
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O;
   }

   public boolean func_190677_dK() {
      return false;
   }

   public boolean func_190682_f(ItemStack var1) {
      return false;
   }

   @Override
   public boolean func_174820_d(int var1, ItemStack var2) {
      int ☃ = ☃ - 400;
      if (☃ >= 0 && ☃ < 2 && ☃ < this.field_110296_bG.func_70302_i_()) {
         if (☃ == 0 && ☃.func_77973_b() != Items.field_151141_av) {
            return false;
         } else if (☃ != 1 || this.func_190677_dK() && this.func_190682_f(☃)) {
            this.field_110296_bG.func_70299_a(☃, ☃);
            this.func_110232_cE();
            return true;
         } else {
            return false;
         }
      } else {
         int ☃ = ☃ - 500 + 2;
         if (☃ >= 2 && ☃ < this.field_110296_bG.func_70302_i_()) {
            this.field_110296_bG.func_70299_a(☃, ☃);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public Entity func_184179_bs() {
      return this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_70873_a(-24000);
      }

      return ☃;
   }
}
