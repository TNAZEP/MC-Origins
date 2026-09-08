package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBubbleColumn;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtil;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityPlayer extends EntityLivingBase {
   private static final DataParameter<Float> field_184829_a = EntityDataManager.func_187226_a(EntityPlayer.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184830_b = EntityDataManager.func_187226_a(EntityPlayer.class, DataSerializers.field_187192_b);
   protected static final DataParameter<Byte> field_184827_bp = EntityDataManager.func_187226_a(EntityPlayer.class, DataSerializers.field_187191_a);
   protected static final DataParameter<Byte> field_184828_bq = EntityDataManager.func_187226_a(EntityPlayer.class, DataSerializers.field_187191_a);
   protected static final DataParameter<NBTTagCompound> field_192032_bt = EntityDataManager.func_187226_a(EntityPlayer.class, DataSerializers.field_192734_n);
   protected static final DataParameter<NBTTagCompound> field_192033_bu = EntityDataManager.func_187226_a(EntityPlayer.class, DataSerializers.field_192734_n);
   public InventoryPlayer field_71071_by = new InventoryPlayer(this);
   protected InventoryEnderChest field_71078_a = new InventoryEnderChest();
   public Container field_71069_bz;
   public Container field_71070_bA;
   protected FoodStats field_71100_bB = new FoodStats();
   protected int field_71101_bC;
   public float field_71107_bF;
   public float field_71109_bG;
   public int field_71090_bL;
   public double field_71091_bM;
   public double field_71096_bN;
   public double field_71097_bO;
   public double field_71094_bP;
   public double field_71095_bQ;
   public double field_71085_bR;
   protected boolean field_71083_bS;
   public BlockPos field_71081_bT;
   private int field_71076_b;
   public float field_71079_bU;
   public float field_71082_cx;
   public float field_71089_bV;
   private boolean field_203042_d;
   protected boolean field_204230_bP;
   private BlockPos field_71077_c;
   private boolean field_82248_d;
   public PlayerCapabilities field_71075_bZ = new PlayerCapabilities();
   public int field_71068_ca;
   public int field_71067_cb;
   public float field_71106_cc;
   protected int field_175152_f;
   protected float field_71102_ce = 0.02F;
   private int field_82249_h;
   private final GameProfile field_146106_i;
   private boolean field_175153_bG;
   private ItemStack field_184831_bT = ItemStack.field_190927_a;
   private final CooldownTracker field_184832_bU = this.func_184815_l();
   @Nullable
   public EntityFishHook field_71104_cf;

   public EntityPlayer(World var1, GameProfile var2) {
      super(EntityType.field_200729_aH, ☃);
      this.func_184221_a(func_146094_a(☃));
      this.field_146106_i = ☃;
      this.field_71069_bz = new ContainerPlayer(this.field_71071_by, !☃.field_72995_K, this);
      this.field_71070_bA = this.field_71069_bz;
      BlockPos ☃ = ☃.func_175694_M();
      this.func_70012_b((double)☃.func_177958_n() + 0.5, (double)(☃.func_177956_o() + 1), (double)☃.func_177952_p() + 0.5, 0.0F, 0.0F);
      this.field_70741_aB = 180.0F;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.1F);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188790_f);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188792_h);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184829_a, 0.0F);
      this.field_70180_af.func_187214_a(field_184830_b, 0);
      this.field_70180_af.func_187214_a(field_184827_bp, (byte)0);
      this.field_70180_af.func_187214_a(field_184828_bq, (byte)1);
      this.field_70180_af.func_187214_a(field_192032_bt, new NBTTagCompound());
      this.field_70180_af.func_187214_a(field_192033_bu, new NBTTagCompound());
   }

   @Override
   public void func_70071_h_() {
      this.field_70145_X = this.func_175149_v();
      if (this.func_175149_v()) {
         this.field_70122_E = false;
      }

      if (this.field_71090_bL > 0) {
         --this.field_71090_bL;
      }

      if (this.func_70608_bn()) {
         ++this.field_71076_b;
         if (this.field_71076_b > 100) {
            this.field_71076_b = 100;
         }

         if (!this.field_70170_p.field_72995_K) {
            if (!this.func_175143_p()) {
               this.func_70999_a(true, true, false);
            } else if (this.field_70170_p.func_72935_r()) {
               this.func_70999_a(false, true, true);
            }
         }
      } else if (this.field_71076_b > 0) {
         ++this.field_71076_b;
         if (this.field_71076_b >= 110) {
            this.field_71076_b = 0;
         }
      }

      this.func_203040_o();
      this.func_204229_de();
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && this.field_71070_bA != null && !this.field_71070_bA.func_75145_c(this)) {
         this.func_71053_j();
         this.field_71070_bA = this.field_71069_bz;
      }

      if (this.func_70027_ad() && this.field_71075_bZ.field_75102_a) {
         this.func_70066_B();
      }

      this.func_184820_o();
      if (!this.field_70170_p.field_72995_K) {
         this.field_71100_bB.func_75118_a(this);
         this.func_195066_a(StatList.field_188097_g);
         if (this.func_70089_S()) {
            this.func_195066_a(StatList.field_188098_h);
         }

         if (this.func_70093_af()) {
            this.func_195066_a(StatList.field_188099_i);
         }

         if (!this.func_70608_bn()) {
            this.func_195066_a(StatList.field_203284_n);
         }
      }

      int ☃ = 29999999;
      double ☃x = MathHelper.func_151237_a(this.field_70165_t, -2.9999999E7, 2.9999999E7);
      double ☃xx = MathHelper.func_151237_a(this.field_70161_v, -2.9999999E7, 2.9999999E7);
      if (☃x != this.field_70165_t || ☃xx != this.field_70161_v) {
         this.func_70107_b(☃x, this.field_70163_u, ☃xx);
      }

      ++this.field_184617_aD;
      ItemStack ☃ = this.func_184614_ca();
      if (!ItemStack.func_77989_b(this.field_184831_bT, ☃)) {
         if (!ItemStack.func_185132_d(this.field_184831_bT, ☃)) {
            this.func_184821_cY();
         }

         this.field_184831_bT = ☃.func_190926_b() ? ItemStack.field_190927_a : ☃.func_77946_l();
      }

      this.func_203041_m();
      this.field_184832_bU.func_185144_a();
      this.func_184808_cD();
   }

   protected boolean func_204229_de() {
      this.field_204230_bP = this.func_208600_a(FluidTags.field_206959_a);
      return this.field_204230_bP;
   }

   private void func_203041_m() {
      ItemStack ☃ = this.func_184582_a(EntityEquipmentSlot.HEAD);
      if (☃.func_77973_b() == Items.field_203179_ao && !this.func_208600_a(FluidTags.field_206959_a)) {
         this.func_195064_c(new PotionEffect(MobEffects.field_76427_o, 200, 0, false, false, true));
      }
   }

   protected CooldownTracker func_184815_l() {
      return new CooldownTracker();
   }

   private void func_203040_o() {
      IBlockState ☃ = this.field_70170_p.func_203067_a(this.func_174813_aQ().func_72314_b(0.0, -0.4F, 0.0).func_186664_h(0.001), Blocks.field_203203_C);
      if (☃ != null) {
         if (!this.field_203042_d && !this.field_70148_d && ☃.func_177230_c() == Blocks.field_203203_C && !this.func_175149_v()) {
            boolean ☃x = ☃.func_177229_b(BlockBubbleColumn.field_203160_a);
            if (☃x) {
               this.field_70170_p
                  .func_184134_a(
                     this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_203283_jd, this.func_184176_by(), 1.0F, 1.0F, false
                  );
            } else {
               this.field_70170_p
                  .func_184134_a(
                     this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_203252_T, this.func_184176_by(), 1.0F, 1.0F, false
                  );
            }
         }

         this.field_203042_d = true;
      } else {
         this.field_203042_d = false;
      }
   }

   private void func_184820_o() {
      this.field_71091_bM = this.field_71094_bP;
      this.field_71096_bN = this.field_71095_bQ;
      this.field_71097_bO = this.field_71085_bR;
      double ☃ = this.field_70165_t - this.field_71094_bP;
      double ☃x = this.field_70163_u - this.field_71095_bQ;
      double ☃xx = this.field_70161_v - this.field_71085_bR;
      double ☃xxx = 10.0;
      if (☃ > 10.0) {
         this.field_71094_bP = this.field_70165_t;
         this.field_71091_bM = this.field_71094_bP;
      }

      if (☃xx > 10.0) {
         this.field_71085_bR = this.field_70161_v;
         this.field_71097_bO = this.field_71085_bR;
      }

      if (☃x > 10.0) {
         this.field_71095_bQ = this.field_70163_u;
         this.field_71096_bN = this.field_71095_bQ;
      }

      if (☃ < -10.0) {
         this.field_71094_bP = this.field_70165_t;
         this.field_71091_bM = this.field_71094_bP;
      }

      if (☃xx < -10.0) {
         this.field_71085_bR = this.field_70161_v;
         this.field_71097_bO = this.field_71085_bR;
      }

      if (☃x < -10.0) {
         this.field_71095_bQ = this.field_70163_u;
         this.field_71096_bN = this.field_71095_bQ;
      }

      this.field_71094_bP += ☃ * 0.25;
      this.field_71085_bR += ☃xx * 0.25;
      this.field_71095_bQ += ☃x * 0.25;
   }

   protected void func_184808_cD() {
      float ☃;
      float ☃x;
      if (this.func_184613_cA()) {
         ☃ = 0.6F;
         ☃x = 0.6F;
      } else if (this.func_70608_bn()) {
         ☃ = 0.2F;
         ☃x = 0.2F;
      } else if (this.func_203007_ba() || this.func_204805_cN()) {
         ☃ = 0.6F;
         ☃x = 0.6F;
      } else if (this.func_70093_af()) {
         ☃ = 0.6F;
         ☃x = 1.65F;
      } else {
         ☃ = 0.6F;
         ☃x = 1.8F;
      }

      if (☃ != this.field_70130_N || ☃x != this.field_70131_O) {
         AxisAlignedBB ☃ = this.func_174813_aQ();
         ☃ = new AxisAlignedBB(
            ☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72340_a + (double)☃, ☃.field_72338_b + (double)☃x, ☃.field_72339_c + (double)☃
         );
         if (this.field_70170_p.func_195586_b(null, ☃)) {
            this.func_70105_a(☃, ☃x);
         }
      }
   }

   @Override
   public int func_82145_z() {
      return this.field_71075_bZ.field_75102_a ? 1 : 80;
   }

   @Override
   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_187808_ef;
   }

   @Override
   protected SoundEvent func_184181_aa() {
      return SoundEvents.field_187806_ee;
   }

   @Override
   protected SoundEvent func_204208_ah() {
      return SoundEvents.field_204328_gh;
   }

   @Override
   public int func_82147_ab() {
      return 10;
   }

   @Override
   public void func_184185_a(SoundEvent var1, float var2, float var3) {
      this.field_70170_p.func_184148_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃, this.func_184176_by(), ☃, ☃);
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.PLAYERS;
   }

   @Override
   protected int func_190531_bD() {
      return 20;
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 9) {
         this.func_71036_o();
      } else if (☃ == 23) {
         this.field_175153_bG = false;
      } else if (☃ == 22) {
         this.field_175153_bG = true;
      } else {
         super.func_70103_a(☃);
      }
   }

   @Override
   protected boolean func_70610_aX() {
      return this.func_110143_aJ() <= 0.0F || this.func_70608_bn();
   }

   protected void func_71053_j() {
      this.field_71070_bA = this.field_71069_bz;
   }

   @Override
   public void func_70098_U() {
      if (!this.field_70170_p.field_72995_K && this.func_70093_af() && this.func_184218_aH()) {
         this.func_184210_p();
         this.func_70095_a(false);
      } else {
         double ☃ = this.field_70165_t;
         double ☃x = this.field_70163_u;
         double ☃xx = this.field_70161_v;
         float ☃xxx = this.field_70177_z;
         float ☃xxxx = this.field_70125_A;
         super.func_70098_U();
         this.field_71107_bF = this.field_71109_bG;
         this.field_71109_bG = 0.0F;
         this.func_71015_k(this.field_70165_t - ☃, this.field_70163_u - ☃x, this.field_70161_v - ☃xx);
         if (this.func_184187_bx() instanceof EntityPig) {
            this.field_70125_A = ☃xxxx;
            this.field_70177_z = ☃xxx;
            this.field_70761_aq = ((EntityPig)this.func_184187_bx()).field_70761_aq;
         }
      }
   }

   @Override
   public void func_70065_x() {
      this.func_70105_a(0.6F, 1.8F);
      super.func_70065_x();
      this.func_70606_j(this.func_110138_aP());
      this.field_70725_aQ = 0;
   }

   @Override
   protected void func_70626_be() {
      super.func_70626_be();
      this.func_82168_bl();
      this.field_70759_as = this.field_70177_z;
   }

   @Override
   public void func_70636_d() {
      if (this.field_71101_bC > 0) {
         --this.field_71101_bC;
      }

      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL && this.field_70170_p.func_82736_K().func_82766_b("naturalRegeneration")) {
         if (this.func_110143_aJ() < this.func_110138_aP() && this.field_70173_aa % 20 == 0) {
            this.func_70691_i(1.0F);
         }

         if (this.field_71100_bB.func_75121_c() && this.field_70173_aa % 10 == 0) {
            this.field_71100_bB.func_75114_a(this.field_71100_bB.func_75116_a() + 1);
         }
      }

      this.field_71071_by.func_70429_k();
      this.field_71107_bF = this.field_71109_bG;
      super.func_70636_d();
      IAttributeInstance ☃ = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (!this.field_70170_p.field_72995_K) {
         ☃.func_111128_a((double)this.field_71075_bZ.func_75094_b());
      }

      this.field_70747_aH = this.field_71102_ce;
      if (this.func_70051_ag()) {
         this.field_70747_aH = (float)((double)this.field_70747_aH + (double)this.field_71102_ce * 0.3);
      }

      this.func_70659_e((float)☃.func_111126_e());
      float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      float ☃x = (float)(Math.atan(-this.field_70181_x * 0.2F) * 15.0);
      if (☃ > 0.1F) {
         ☃ = 0.1F;
      }

      if (!this.field_70122_E || this.func_110143_aJ() <= 0.0F || this.func_203007_ba()) {
         ☃ = 0.0F;
      }

      if (this.field_70122_E || this.func_110143_aJ() <= 0.0F) {
         ☃x = 0.0F;
      }

      this.field_71109_bG += (☃ - this.field_71109_bG) * 0.4F;
      this.field_70726_aT += (☃x - this.field_70726_aT) * 0.8F;
      if (this.func_110143_aJ() > 0.0F && !this.func_175149_v()) {
         AxisAlignedBB ☃;
         if (this.func_184218_aH() && !this.func_184187_bx().field_70128_L) {
            ☃ = this.func_174813_aQ().func_111270_a(this.func_184187_bx().func_174813_aQ()).func_72314_b(1.0, 0.0, 1.0);
         } else {
            ☃ = this.func_174813_aQ().func_72314_b(1.0, 0.5, 1.0);
         }

         List<Entity> ☃ = this.field_70170_p.func_72839_b(this, ☃);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            Entity ☃xx = (Entity)☃.get(☃x);
            if (!☃xx.field_70128_L) {
               this.func_71044_o(☃xx);
            }
         }
      }

      this.func_192028_j(this.func_192023_dk());
      this.func_192028_j(this.func_192025_dl());
      if (!this.field_70170_p.field_72995_K && (this.field_70143_R > 0.5F || this.func_70090_H() || this.func_184218_aH()) || this.field_71075_bZ.field_75100_b
         )
       {
         this.func_192030_dh();
      }
   }

   private void func_192028_j(@Nullable NBTTagCompound var1) {
      if (☃ != null && !☃.func_74764_b("Silent") || !☃.func_74767_n("Silent")) {
         String ☃ = ☃.func_74779_i("id");
         if (EntityType.func_200713_a(☃) == EntityType.field_200783_W) {
            EntityParrot.func_192005_a(this.field_70170_p, this);
         }
      }
   }

   private void func_71044_o(Entity var1) {
      ☃.func_70100_b_(this);
   }

   public int func_71037_bA() {
      return this.field_70180_af.func_187225_a(field_184830_b);
   }

   public void func_85040_s(int var1) {
      this.field_70180_af.func_187227_b(field_184830_b, ☃);
   }

   public void func_85039_t(int var1) {
      int ☃ = this.func_71037_bA();
      this.field_70180_af.func_187227_b(field_184830_b, ☃ + ☃);
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      this.func_70105_a(0.2F, 0.2F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70181_x = 0.1F;
      if ("Notch".equals(this.func_200200_C_().getString())) {
         this.func_146097_a(new ItemStack(Items.field_151034_e), true, false);
      }

      if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory") && !this.func_175149_v()) {
         this.func_190776_cN();
         this.field_71071_by.func_70436_m();
      }

      if (☃ != null) {
         this.field_70159_w = (double)(-MathHelper.func_76134_b((this.field_70739_aP + this.field_70177_z) * (float) (Math.PI / 180.0)) * 0.1F);
         this.field_70179_y = (double)(-MathHelper.func_76126_a((this.field_70739_aP + this.field_70177_z) * (float) (Math.PI / 180.0)) * 0.1F);
      } else {
         this.field_70159_w = 0.0;
         this.field_70179_y = 0.0;
      }

      this.func_195066_a(StatList.field_188069_A);
      this.func_175145_a(StatList.field_199092_j.func_199076_b(StatList.field_188098_h));
      this.func_175145_a(StatList.field_199092_j.func_199076_b(StatList.field_203284_n));
      this.func_70066_B();
      this.func_70052_a(0, false);
   }

   protected void func_190776_cN() {
      for(int ☃ = 0; ☃ < this.field_71071_by.func_70302_i_(); ++☃) {
         ItemStack ☃x = this.field_71071_by.func_70301_a(☃);
         if (!☃x.func_190926_b() && EnchantmentHelper.func_190939_c(☃x)) {
            this.field_71071_by.func_70304_b(☃);
         }
      }
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      if (☃ == DamageSource.field_76370_b) {
         return SoundEvents.field_193806_fH;
      } else {
         return ☃ == DamageSource.field_76369_e ? SoundEvents.field_193805_fG : SoundEvents.field_187800_eb;
      }
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187798_ea;
   }

   @Nullable
   public EntityItem func_71040_bB(boolean var1) {
      return this.func_146097_a(
         this.field_71071_by
            .func_70298_a(
               this.field_71071_by.field_70461_c,
               ☃ && !this.field_71071_by.func_70448_g().func_190926_b() ? this.field_71071_by.func_70448_g().func_190916_E() : 1
            ),
         false,
         true
      );
   }

   @Nullable
   public EntityItem func_71019_a(ItemStack var1, boolean var2) {
      return this.func_146097_a(☃, false, ☃);
   }

   @Nullable
   public EntityItem func_146097_a(ItemStack var1, boolean var2, boolean var3) {
      if (☃.func_190926_b()) {
         return null;
      } else {
         double ☃ = this.field_70163_u - 0.3F + (double)this.func_70047_e();
         EntityItem ☃x = new EntityItem(this.field_70170_p, this.field_70165_t, ☃, this.field_70161_v, ☃);
         ☃x.func_174867_a(40);
         if (☃) {
            ☃x.func_200216_c(this.func_110124_au());
         }

         if (☃) {
            float ☃ = this.field_70146_Z.nextFloat() * 0.5F;
            float ☃x = this.field_70146_Z.nextFloat() * (float) (Math.PI * 2);
            ☃x.field_70159_w = (double)(-MathHelper.func_76126_a(☃x) * ☃);
            ☃x.field_70179_y = (double)(MathHelper.func_76134_b(☃x) * ☃);
            ☃x.field_70181_x = 0.2F;
         } else {
            float ☃ = 0.3F;
            ☃x.field_70159_w = (double)(
               -MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0))
                  * MathHelper.func_76134_b(this.field_70125_A * (float) (Math.PI / 180.0))
                  * ☃
            );
            ☃x.field_70179_y = (double)(
               MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0))
                  * MathHelper.func_76134_b(this.field_70125_A * (float) (Math.PI / 180.0))
                  * ☃
            );
            ☃x.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A * (float) (Math.PI / 180.0)) * ☃ + 0.1F);
            float ☃x = this.field_70146_Z.nextFloat() * (float) (Math.PI * 2);
            ☃ = 0.02F * this.field_70146_Z.nextFloat();
            ☃x.field_70159_w += Math.cos((double)☃x) * (double)☃;
            ☃x.field_70181_x += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
            ☃x.field_70179_y += Math.sin((double)☃x) * (double)☃;
         }

         ItemStack ☃ = this.func_184816_a(☃x);
         if (☃) {
            if (!☃.func_190926_b()) {
               this.func_71064_a(StatList.field_188068_aj.func_199076_b(☃.func_77973_b()), ☃.func_190916_E());
            }

            this.func_195066_a(StatList.field_75952_v);
         }

         return ☃x;
      }
   }

   protected ItemStack func_184816_a(EntityItem var1) {
      this.field_70170_p.func_72838_d(☃);
      return ☃.func_92059_d();
   }

   public float func_184813_a(IBlockState var1) {
      float ☃ = this.field_71071_by.func_184438_a(☃);
      if (☃ > 1.0F) {
         int ☃x = EnchantmentHelper.func_185293_e(this);
         ItemStack ☃xx = this.func_184614_ca();
         if (☃x > 0 && !☃xx.func_190926_b()) {
            ☃ += (float)(☃x * ☃x + 1);
         }
      }

      if (PotionUtil.func_205135_a(this)) {
         ☃ *= 1.0F + (float)(PotionUtil.func_205134_b(this) + 1) * 0.2F;
      }

      if (this.func_70644_a(MobEffects.field_76419_f)) {
         float ☃;
         switch(this.func_70660_b(MobEffects.field_76419_f).func_76458_c()) {
            case 0:
               ☃ = 0.3F;
               break;
            case 1:
               ☃ = 0.09F;
               break;
            case 2:
               ☃ = 0.0027F;
               break;
            case 3:
            default:
               ☃ = 8.1E-4F;
         }

         ☃ *= ☃;
      }

      if (this.func_208600_a(FluidTags.field_206959_a) && !EnchantmentHelper.func_185287_i(this)) {
         ☃ /= 5.0F;
      }

      if (!this.field_70122_E) {
         ☃ /= 5.0F;
      }

      return ☃;
   }

   public boolean func_184823_b(IBlockState var1) {
      return ☃.func_185904_a().func_76229_l() || this.field_71071_by.func_184432_b(☃);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_184221_a(func_146094_a(this.field_146106_i));
      NBTTagList ☃ = ☃.func_150295_c("Inventory", 10);
      this.field_71071_by.func_70443_b(☃);
      this.field_71071_by.field_70461_c = ☃.func_74762_e("SelectedItemSlot");
      this.field_71083_bS = ☃.func_74767_n("Sleeping");
      this.field_71076_b = ☃.func_74765_d("SleepTimer");
      this.field_71106_cc = ☃.func_74760_g("XpP");
      this.field_71068_ca = ☃.func_74762_e("XpLevel");
      this.field_71067_cb = ☃.func_74762_e("XpTotal");
      this.field_175152_f = ☃.func_74762_e("XpSeed");
      if (this.field_175152_f == 0) {
         this.field_175152_f = this.field_70146_Z.nextInt();
      }

      this.func_85040_s(☃.func_74762_e("Score"));
      if (this.field_71083_bS) {
         this.field_71081_bT = new BlockPos(this);
         this.func_70999_a(true, true, false);
      }

      if (☃.func_150297_b("SpawnX", 99) && ☃.func_150297_b("SpawnY", 99) && ☃.func_150297_b("SpawnZ", 99)) {
         this.field_71077_c = new BlockPos(☃.func_74762_e("SpawnX"), ☃.func_74762_e("SpawnY"), ☃.func_74762_e("SpawnZ"));
         this.field_82248_d = ☃.func_74767_n("SpawnForced");
      }

      this.field_71100_bB.func_75112_a(☃);
      this.field_71075_bZ.func_75095_b(☃);
      if (☃.func_150297_b("EnderItems", 9)) {
         this.field_71078_a.func_70486_a(☃.func_150295_c("EnderItems", 10));
      }

      if (☃.func_150297_b("ShoulderEntityLeft", 10)) {
         this.func_192029_h(☃.func_74775_l("ShoulderEntityLeft"));
      }

      if (☃.func_150297_b("ShoulderEntityRight", 10)) {
         this.func_192031_i(☃.func_74775_l("ShoulderEntityRight"));
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("DataVersion", 1631);
      ☃.func_74782_a("Inventory", this.field_71071_by.func_70442_a(new NBTTagList()));
      ☃.func_74768_a("SelectedItemSlot", this.field_71071_by.field_70461_c);
      ☃.func_74757_a("Sleeping", this.field_71083_bS);
      ☃.func_74777_a("SleepTimer", (short)this.field_71076_b);
      ☃.func_74776_a("XpP", this.field_71106_cc);
      ☃.func_74768_a("XpLevel", this.field_71068_ca);
      ☃.func_74768_a("XpTotal", this.field_71067_cb);
      ☃.func_74768_a("XpSeed", this.field_175152_f);
      ☃.func_74768_a("Score", this.func_71037_bA());
      if (this.field_71077_c != null) {
         ☃.func_74768_a("SpawnX", this.field_71077_c.func_177958_n());
         ☃.func_74768_a("SpawnY", this.field_71077_c.func_177956_o());
         ☃.func_74768_a("SpawnZ", this.field_71077_c.func_177952_p());
         ☃.func_74757_a("SpawnForced", this.field_82248_d);
      }

      this.field_71100_bB.func_75117_b(☃);
      this.field_71075_bZ.func_75091_a(☃);
      ☃.func_74782_a("EnderItems", this.field_71078_a.func_70487_g());
      if (!this.func_192023_dk().isEmpty()) {
         ☃.func_74782_a("ShoulderEntityLeft", this.func_192023_dk());
      }

      if (!this.func_192025_dl().isEmpty()) {
         ☃.func_74782_a("ShoulderEntityRight", this.func_192025_dl());
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (this.field_71075_bZ.field_75102_a && !☃.func_76357_e()) {
         return false;
      } else {
         this.field_70708_bq = 0;
         if (this.func_110143_aJ() <= 0.0F) {
            return false;
         } else {
            if (this.func_70608_bn() && !this.field_70170_p.field_72995_K) {
               this.func_70999_a(true, true, false);
            }

            this.func_192030_dh();
            if (☃.func_76350_n()) {
               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
                  ☃ = 0.0F;
               }

               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY) {
                  ☃ = Math.min(☃ / 2.0F + 1.0F, ☃);
               }

               if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
                  ☃ = ☃ * 3.0F / 2.0F;
               }
            }

            return ☃ == 0.0F ? false : super.func_70097_a(☃, ☃);
         }
      }
   }

   @Override
   protected void func_190629_c(EntityLivingBase var1) {
      super.func_190629_c(☃);
      if (☃.func_184614_ca().func_77973_b() instanceof ItemAxe) {
         this.func_190777_m(true);
      }
   }

   public boolean func_96122_a(EntityPlayer var1) {
      Team ☃ = this.func_96124_cp();
      Team ☃x = ☃.func_96124_cp();
      if (☃ == null) {
         return true;
      } else {
         return !☃.func_142054_a(☃x) ? true : ☃.func_96665_g();
      }
   }

   @Override
   protected void func_70675_k(float var1) {
      this.field_71071_by.func_70449_g(☃);
   }

   @Override
   protected void func_184590_k(float var1) {
      if (☃ >= 3.0F && this.field_184627_bm.func_77973_b() == Items.field_185159_cQ) {
         int ☃ = 1 + MathHelper.func_76141_d(☃);
         this.field_184627_bm.func_77972_a(☃, this);
         if (this.field_184627_bm.func_190926_b()) {
            EnumHand ☃x = this.func_184600_cs();
            if (☃x == EnumHand.MAIN_HAND) {
               this.func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
            } else {
               this.func_184201_a(EntityEquipmentSlot.OFFHAND, ItemStack.field_190927_a);
            }

            this.field_184627_bm = ItemStack.field_190927_a;
            this.func_184185_a(SoundEvents.field_187769_eM, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
         }
      }
   }

   public float func_82243_bO() {
      int ☃ = 0;

      for(ItemStack ☃x : this.field_71071_by.field_70460_b) {
         if (!☃x.func_190926_b()) {
            ++☃;
         }
      }

      return (float)☃ / (float)this.field_71071_by.field_70460_b.size();
   }

   @Override
   protected void func_70665_d(DamageSource var1, float var2) {
      if (!this.func_180431_b(☃)) {
         ☃ = this.func_70655_b(☃, ☃);
         ☃ = this.func_70672_c(☃, ☃);
         float var8 = Math.max(☃ - this.func_110139_bj(), 0.0F);
         this.func_110149_m(this.func_110139_bj() - (☃ - var8));
         float ☃ = ☃ - var8;
         if (☃ > 0.0F && ☃ < 3.4028235E37F) {
            this.func_195067_a(StatList.field_212738_J, Math.round(☃ * 10.0F));
         }

         if (var8 != 0.0F) {
            this.func_71020_j(☃.func_76345_d());
            float ☃ = this.func_110143_aJ();
            this.func_70606_j(this.func_110143_aJ() - var8);
            this.func_110142_aN().func_94547_a(☃, ☃, var8);
            if (var8 < 3.4028235E37F) {
               this.func_195067_a(StatList.field_188112_z, Math.round(var8 * 10.0F));
            }
         }
      }
   }

   public void func_175141_a(TileEntitySign var1) {
   }

   public void func_184809_a(CommandBlockBaseLogic var1) {
   }

   public void func_184824_a(TileEntityCommandBlock var1) {
   }

   public void func_189807_a(TileEntityStructure var1) {
   }

   public void func_180472_a(IMerchant var1) {
   }

   public void func_71007_a(IInventory var1) {
   }

   public void func_184826_a(AbstractHorse var1, IInventory var2) {
   }

   public void func_180468_a(IInteractionObject var1) {
   }

   public void func_184814_a(ItemStack var1, EnumHand var2) {
   }

   public EnumActionResult func_190775_a(Entity var1, EnumHand var2) {
      if (this.func_175149_v()) {
         if (☃ instanceof IInventory) {
            this.func_71007_a((IInventory)☃);
         }

         return EnumActionResult.PASS;
      } else {
         ItemStack ☃ = this.func_184586_b(☃);
         ItemStack ☃x = ☃.func_190926_b() ? ItemStack.field_190927_a : ☃.func_77946_l();
         if (☃.func_184230_a(this, ☃)) {
            if (this.field_71075_bZ.field_75098_d && ☃ == this.func_184586_b(☃) && ☃.func_190916_E() < ☃x.func_190916_E()) {
               ☃.func_190920_e(☃x.func_190916_E());
            }

            return EnumActionResult.SUCCESS;
         } else {
            if (!☃.func_190926_b() && ☃ instanceof EntityLivingBase) {
               if (this.field_71075_bZ.field_75098_d) {
                  ☃ = ☃x;
               }

               if (☃.func_111282_a(this, (EntityLivingBase)☃, ☃)) {
                  if (☃.func_190926_b() && !this.field_71075_bZ.field_75098_d) {
                     this.func_184611_a(☃, ItemStack.field_190927_a);
                  }

                  return EnumActionResult.SUCCESS;
               }
            }

            return EnumActionResult.PASS;
         }
      }
   }

   @Override
   public double func_70033_W() {
      return -0.35;
   }

   @Override
   public void func_184210_p() {
      super.func_184210_p();
      this.field_184245_j = 0;
   }

   public void func_71059_n(Entity var1) {
      if (☃.func_70075_an()) {
         if (!☃.func_85031_j(this)) {
            float ☃x = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            float ☃;
            if (☃ instanceof EntityLivingBase) {
               ☃ = EnchantmentHelper.func_152377_a(this.func_184614_ca(), ((EntityLivingBase)☃).func_70668_bt());
            } else {
               ☃ = EnchantmentHelper.func_152377_a(this.func_184614_ca(), CreatureAttribute.UNDEFINED);
            }

            float ☃ = this.func_184825_o(0.5F);
            ☃x *= 0.2F + ☃ * ☃ * 0.8F;
            ☃ *= ☃;
            this.func_184821_cY();
            if (☃x > 0.0F || ☃ > 0.0F) {
               boolean ☃x = ☃ > 0.9F;
               boolean ☃xx = false;
               int ☃xxx = 0;
               ☃xxx += EnchantmentHelper.func_77501_a(this);
               if (this.func_70051_ag() && ☃x) {
                  this.field_70170_p
                     .func_184148_a(
                        null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187721_dT, this.func_184176_by(), 1.0F, 1.0F
                     );
                  ++☃xxx;
                  ☃xx = true;
               }

               boolean ☃x = ☃x
                  && this.field_70143_R > 0.0F
                  && !this.field_70122_E
                  && !this.func_70617_f_()
                  && !this.func_70090_H()
                  && !this.func_70644_a(MobEffects.field_76440_q)
                  && !this.func_184218_aH()
                  && ☃ instanceof EntityLivingBase;
               ☃x = ☃x && !this.func_70051_ag();
               if (☃x) {
                  ☃x *= 1.5F;
               }

               ☃x += ☃;
               boolean ☃x = false;
               double ☃xx = (double)(this.field_70140_Q - this.field_70141_P);
               if (☃x && !☃x && !☃xx && this.field_70122_E && ☃xx < (double)this.func_70689_ay()) {
                  ItemStack ☃xxx = this.func_184586_b(EnumHand.MAIN_HAND);
                  if (☃xxx.func_77973_b() instanceof ItemSword) {
                     ☃x = true;
                  }
               }

               float ☃x = 0.0F;
               boolean ☃xx = false;
               int ☃xxx = EnchantmentHelper.func_90036_a(this);
               if (☃ instanceof EntityLivingBase) {
                  ☃x = ((EntityLivingBase)☃).func_110143_aJ();
                  if (☃xxx > 0 && !☃.func_70027_ad()) {
                     ☃xx = true;
                     ☃.func_70015_d(1);
                  }
               }

               double ☃x = ☃.field_70159_w;
               double ☃xx = ☃.field_70181_x;
               double ☃xxx = ☃.field_70179_y;
               boolean ☃xxxx = ☃.func_70097_a(DamageSource.func_76365_a(this), ☃x);
               if (☃xxxx) {
                  if (☃xxx > 0) {
                     if (☃ instanceof EntityLivingBase) {
                        ((EntityLivingBase)☃)
                           .func_70653_a(
                              this,
                              (float)☃xxx * 0.5F,
                              (double)MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)),
                              (double)(-MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)))
                           );
                     } else {
                        ☃.func_70024_g(
                           (double)(-MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)) * (float)☃xxx * 0.5F),
                           0.1,
                           (double)(MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)) * (float)☃xxx * 0.5F)
                        );
                     }

                     this.field_70159_w *= 0.6;
                     this.field_70179_y *= 0.6;
                     this.func_70031_b(false);
                  }

                  if (☃x) {
                     float ☃xxxxx = 1.0F + EnchantmentHelper.func_191527_a(this) * ☃x;

                     for(EntityLivingBase ☃xxxxxx : this.field_70170_p.func_72872_a(EntityLivingBase.class, ☃.func_174813_aQ().func_72314_b(1.0, 0.25, 1.0))) {
                        if (☃xxxxxx != this
                           && ☃xxxxxx != ☃
                           && !this.func_184191_r(☃xxxxxx)
                           && (!(☃xxxxxx instanceof EntityArmorStand) || !((EntityArmorStand)☃xxxxxx).func_181026_s())
                           && this.func_70068_e(☃xxxxxx) < 9.0) {
                           ☃xxxxxx.func_70653_a(
                              this,
                              0.4F,
                              (double)MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)),
                              (double)(-MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)))
                           );
                           ☃xxxxxx.func_70097_a(DamageSource.func_76365_a(this), ☃xxxxx);
                        }
                     }

                     this.field_70170_p
                        .func_184148_a(
                           null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187730_dW, this.func_184176_by(), 1.0F, 1.0F
                        );
                     this.func_184810_cG();
                  }

                  if (☃ instanceof EntityPlayerMP && ☃.field_70133_I) {
                     ((EntityPlayerMP)☃).field_71135_a.func_147359_a(new SPacketEntityVelocity(☃));
                     ☃.field_70133_I = false;
                     ☃.field_70159_w = ☃x;
                     ☃.field_70181_x = ☃xx;
                     ☃.field_70179_y = ☃xxx;
                  }

                  if (☃x) {
                     this.field_70170_p
                        .func_184148_a(
                           null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187718_dS, this.func_184176_by(), 1.0F, 1.0F
                        );
                     this.func_71009_b(☃);
                  }

                  if (!☃x && !☃x) {
                     if (☃x) {
                        this.field_70170_p
                           .func_184148_a(
                              null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187727_dV, this.func_184176_by(), 1.0F, 1.0F
                           );
                     } else {
                        this.field_70170_p
                           .func_184148_a(
                              null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187733_dX, this.func_184176_by(), 1.0F, 1.0F
                           );
                     }
                  }

                  if (☃ > 0.0F) {
                     this.func_71047_c(☃);
                  }

                  this.func_130011_c(☃);
                  if (☃ instanceof EntityLivingBase) {
                     EnchantmentHelper.func_151384_a((EntityLivingBase)☃, this);
                  }

                  EnchantmentHelper.func_151385_b(this, ☃);
                  ItemStack ☃xxxxx = this.func_184614_ca();
                  Entity ☃xxxxxx = ☃;
                  if (☃ instanceof MultiPartEntityPart) {
                     IEntityMultiPart ☃xxxxxxx = ((MultiPartEntityPart)☃).field_70259_a;
                     if (☃xxxxxxx instanceof EntityLivingBase) {
                        ☃xxxxxx = (EntityLivingBase)☃xxxxxxx;
                     }
                  }

                  if (!☃xxxxx.func_190926_b() && ☃xxxxxx instanceof EntityLivingBase) {
                     ☃xxxxx.func_77961_a((EntityLivingBase)☃xxxxxx, this);
                     if (☃xxxxx.func_190926_b()) {
                        this.func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
                     }
                  }

                  if (☃ instanceof EntityLivingBase) {
                     float ☃xxxxx = ☃x - ((EntityLivingBase)☃).func_110143_aJ();
                     this.func_195067_a(StatList.field_188111_y, Math.round(☃xxxxx * 10.0F));
                     if (☃xxx > 0) {
                        ☃.func_70015_d(☃xxx * 4);
                     }

                     if (this.field_70170_p instanceof WorldServer && ☃xxxxx > 2.0F) {
                        int ☃xxxxx = (int)((double)☃xxxxx * 0.5);
                        ((WorldServer)this.field_70170_p)
                           .func_195598_a(
                              Particles.field_197615_h,
                              ☃.field_70165_t,
                              ☃.field_70163_u + (double)(☃.field_70131_O * 0.5F),
                              ☃.field_70161_v,
                              ☃xxxxx,
                              0.1,
                              0.0,
                              0.1,
                              0.2
                           );
                     }
                  }

                  this.func_71020_j(0.1F);
               } else {
                  this.field_70170_p
                     .func_184148_a(
                        null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187724_dU, this.func_184176_by(), 1.0F, 1.0F
                     );
                  if (☃xx) {
                     ☃.func_70066_B();
                  }
               }
            }
         }
      }
   }

   @Override
   protected void func_204804_d(EntityLivingBase var1) {
      this.func_71059_n(☃);
   }

   public void func_190777_m(boolean var1) {
      float ☃ = 0.25F + (float)EnchantmentHelper.func_185293_e(this) * 0.05F;
      if (☃) {
         ☃ += 0.75F;
      }

      if (this.field_70146_Z.nextFloat() < ☃) {
         this.func_184811_cZ().func_185145_a(Items.field_185159_cQ, 100);
         this.func_184602_cy();
         this.field_70170_p.func_72960_a(this, (byte)30);
      }
   }

   public void func_71009_b(Entity var1) {
   }

   public void func_71047_c(Entity var1) {
   }

   public void func_184810_cG() {
      double ☃ = (double)(-MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)));
      double ☃x = (double)MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0));
      if (this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p)
            .func_195598_a(
               Particles.field_197603_N,
               this.field_70165_t + ☃,
               this.field_70163_u + (double)this.field_70131_O * 0.5,
               this.field_70161_v + ☃x,
               0,
               ☃,
               0.0,
               ☃x,
               0.0
            );
      }
   }

   public void func_71004_bE() {
   }

   @Override
   public void func_70106_y() {
      super.func_70106_y();
      this.field_71069_bz.func_75134_a(this);
      if (this.field_71070_bA != null) {
         this.field_71070_bA.func_75134_a(this);
      }
   }

   @Override
   public boolean func_70094_T() {
      return !this.field_71083_bS && super.func_70094_T();
   }

   public boolean func_175144_cb() {
      return false;
   }

   public GameProfile func_146103_bH() {
      return this.field_146106_i;
   }

   public EntityPlayer.SleepResult func_180469_a(BlockPos var1) {
      EnumFacing ☃ = this.field_70170_p.func_180495_p(☃).func_177229_b(BlockHorizontal.field_185512_D);
      if (!this.field_70170_p.field_72995_K) {
         if (this.func_70608_bn() || !this.func_70089_S()) {
            return EntityPlayer.SleepResult.OTHER_PROBLEM;
         }

         if (!this.field_70170_p.field_73011_w.func_76569_d()) {
            return EntityPlayer.SleepResult.NOT_POSSIBLE_HERE;
         }

         if (this.field_70170_p.func_72935_r()) {
            return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW;
         }

         if (!this.func_190774_a(☃, ☃)) {
            return EntityPlayer.SleepResult.TOO_FAR_AWAY;
         }

         if (!this.func_184812_l_()) {
            double ☃x = 8.0;
            double ☃xx = 5.0;
            List<EntityMob> ☃xxx = this.field_70170_p
               .func_175647_a(
                  EntityMob.class,
                  new AxisAlignedBB(
                     (double)☃.func_177958_n() - 8.0,
                     (double)☃.func_177956_o() - 5.0,
                     (double)☃.func_177952_p() - 8.0,
                     (double)☃.func_177958_n() + 8.0,
                     (double)☃.func_177956_o() + 5.0,
                     (double)☃.func_177952_p() + 8.0
                  ),
                  new EntityPlayer.SleepEnemyPredicate(this)
               );
            if (!☃xxx.isEmpty()) {
               return EntityPlayer.SleepResult.NOT_SAFE;
            }
         }
      }

      if (this.func_184218_aH()) {
         this.func_184210_p();
      }

      this.func_192030_dh();
      this.func_175145_a(StatList.field_199092_j.func_199076_b(StatList.field_203284_n));
      this.func_70105_a(0.2F, 0.2F);
      if (this.field_70170_p.func_175667_e(☃)) {
         float ☃ = 0.5F + (float)☃.func_82601_c() * 0.4F;
         float ☃x = 0.5F + (float)☃.func_82599_e() * 0.4F;
         this.func_175139_a(☃);
         this.func_70107_b((double)((float)☃.func_177958_n() + ☃), (double)((float)☃.func_177956_o() + 0.6875F), (double)((float)☃.func_177952_p() + ☃x));
      } else {
         this.func_70107_b((double)((float)☃.func_177958_n() + 0.5F), (double)((float)☃.func_177956_o() + 0.6875F), (double)((float)☃.func_177952_p() + 0.5F));
      }

      this.field_71083_bS = true;
      this.field_71076_b = 0;
      this.field_71081_bT = ☃;
      this.field_70159_w = 0.0;
      this.field_70181_x = 0.0;
      this.field_70179_y = 0.0;
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72854_c();
      }

      return EntityPlayer.SleepResult.OK;
   }

   private boolean func_190774_a(BlockPos var1, EnumFacing var2) {
      if (Math.abs(this.field_70165_t - (double)☃.func_177958_n()) <= 3.0
         && Math.abs(this.field_70163_u - (double)☃.func_177956_o()) <= 2.0
         && Math.abs(this.field_70161_v - (double)☃.func_177952_p()) <= 3.0) {
         return true;
      } else {
         BlockPos ☃ = ☃.func_177972_a(☃.func_176734_d());
         return Math.abs(this.field_70165_t - (double)☃.func_177958_n()) <= 3.0
            && Math.abs(this.field_70163_u - (double)☃.func_177956_o()) <= 2.0
            && Math.abs(this.field_70161_v - (double)☃.func_177952_p()) <= 3.0;
      }
   }

   private void func_175139_a(EnumFacing var1) {
      this.field_71079_bU = -1.8F * (float)☃.func_82601_c();
      this.field_71089_bV = -1.8F * (float)☃.func_82599_e();
   }

   public void func_70999_a(boolean var1, boolean var2, boolean var3) {
      this.func_70105_a(0.6F, 1.8F);
      IBlockState ☃ = this.field_70170_p.func_180495_p(this.field_71081_bT);
      if (this.field_71081_bT != null && ☃.func_177230_c() instanceof BlockBed) {
         this.field_70170_p.func_180501_a(this.field_71081_bT, ☃.func_206870_a(BlockBed.field_176471_b, Boolean.valueOf(false)), 4);
         BlockPos ☃x = BlockBed.func_176468_a(this.field_70170_p, this.field_71081_bT, 0);
         if (☃x == null) {
            ☃x = this.field_71081_bT.func_177984_a();
         }

         this.func_70107_b((double)((float)☃x.func_177958_n() + 0.5F), (double)((float)☃x.func_177956_o() + 0.1F), (double)((float)☃x.func_177952_p() + 0.5F));
      }

      this.field_71083_bS = false;
      if (!this.field_70170_p.field_72995_K && ☃) {
         this.field_70170_p.func_72854_c();
      }

      this.field_71076_b = ☃ ? 0 : 100;
      if (☃) {
         this.func_180473_a(this.field_71081_bT, false);
      }
   }

   private boolean func_175143_p() {
      return this.field_70170_p.func_180495_p(this.field_71081_bT).func_177230_c() instanceof BlockBed;
   }

   @Nullable
   public static BlockPos func_180467_a(IBlockReader var0, BlockPos var1, boolean var2) {
      Block ☃ = ☃.func_180495_p(☃).func_177230_c();
      if (!(☃ instanceof BlockBed)) {
         if (!☃) {
            return null;
         } else {
            boolean ☃x = ☃.func_181623_g();
            boolean ☃xx = ☃.func_180495_p(☃.func_177984_a()).func_177230_c().func_181623_g();
            return ☃x && ☃xx ? ☃ : null;
         }
      } else {
         return BlockBed.func_176468_a(☃, ☃, 0);
      }
   }

   public float func_71051_bG() {
      if (this.field_71081_bT != null) {
         EnumFacing ☃ = this.field_70170_p.func_180495_p(this.field_71081_bT).func_177229_b(BlockHorizontal.field_185512_D);
         switch(☃) {
            case SOUTH:
               return 90.0F;
            case WEST:
               return 0.0F;
            case NORTH:
               return 270.0F;
            case EAST:
               return 180.0F;
         }
      }

      return 0.0F;
   }

   @Override
   public boolean func_70608_bn() {
      return this.field_71083_bS;
   }

   public boolean func_71026_bH() {
      return this.field_71083_bS && this.field_71076_b >= 100;
   }

   public int func_71060_bI() {
      return this.field_71076_b;
   }

   public void func_146105_b(ITextComponent var1, boolean var2) {
   }

   public BlockPos func_180470_cg() {
      return this.field_71077_c;
   }

   public boolean func_82245_bX() {
      return this.field_82248_d;
   }

   public void func_180473_a(BlockPos var1, boolean var2) {
      if (☃ != null) {
         this.field_71077_c = ☃;
         this.field_82248_d = ☃;
      } else {
         this.field_71077_c = null;
         this.field_82248_d = false;
      }
   }

   public void func_195066_a(ResourceLocation var1) {
      this.func_71029_a(StatList.field_199092_j.func_199076_b(☃));
   }

   public void func_195067_a(ResourceLocation var1, int var2) {
      this.func_71064_a(StatList.field_199092_j.func_199076_b(☃), ☃);
   }

   public void func_71029_a(Stat<?> var1) {
      this.func_71064_a(☃, 1);
   }

   public void func_71064_a(Stat<?> var1, int var2) {
   }

   public void func_175145_a(Stat<?> var1) {
   }

   public int func_195065_a(Collection<IRecipe> var1) {
      return 0;
   }

   public void func_193102_a(ResourceLocation[] var1) {
   }

   public int func_195069_b(Collection<IRecipe> var1) {
      return 0;
   }

   @Override
   public void func_70664_aZ() {
      super.func_70664_aZ();
      this.func_195066_a(StatList.field_75953_u);
      if (this.func_70051_ag()) {
         this.func_71020_j(0.2F);
      } else {
         this.func_71020_j(0.05F);
      }
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      double ☃ = this.field_70165_t;
      double ☃x = this.field_70163_u;
      double ☃xx = this.field_70161_v;
      if (this.func_203007_ba() && !this.func_184218_aH()) {
         double ☃xxx = this.func_70040_Z().field_72448_b;
         double ☃xxxx = ☃xxx < -0.2 ? 0.085 : 0.06;
         if (☃xxx <= 0.0
            || this.field_70703_bu
            || !this.field_70170_p
               .func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u + 1.0 - 0.1, this.field_70161_v))
               .func_204520_s()
               .func_206888_e()) {
            this.field_70181_x += (☃xxx - this.field_70181_x) * ☃xxxx;
         }
      }

      if (this.field_71075_bZ.field_75100_b && !this.func_184218_aH()) {
         double ☃ = this.field_70181_x;
         float ☃x = this.field_70747_aH;
         this.field_70747_aH = this.field_71075_bZ.func_75093_a() * (float)(this.func_70051_ag() ? 2 : 1);
         super.func_191986_a(☃, ☃, ☃);
         this.field_70181_x = ☃ * 0.6;
         this.field_70747_aH = ☃x;
         this.field_70143_R = 0.0F;
         this.func_70052_a(7, false);
      } else {
         super.func_191986_a(☃, ☃, ☃);
      }

      this.func_71000_j(this.field_70165_t - ☃, this.field_70163_u - ☃x, this.field_70161_v - ☃xx);
   }

   @Override
   public void func_205343_av() {
      if (this.field_71075_bZ.field_75100_b) {
         this.func_204711_a(false);
      } else {
         super.func_205343_av();
      }
   }

   protected boolean func_207402_f(BlockPos var1) {
      return this.func_207401_g(☃) && !this.field_70170_p.func_180495_p(☃.func_177984_a()).func_185915_l();
   }

   protected boolean func_207401_g(BlockPos var1) {
      return !this.field_70170_p.func_180495_p(☃).func_185915_l();
   }

   @Override
   public float func_70689_ay() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
   }

   public void func_71000_j(double var1, double var3, double var5) {
      if (!this.func_184218_aH()) {
         if (this.func_203007_ba()) {
            int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃) * 100.0F);
            if (☃ > 0) {
               this.func_195067_a(StatList.field_75946_m, ☃);
               this.func_71020_j(0.01F * (float)☃ * 0.01F);
            }
         } else if (this.func_208600_a(FluidTags.field_206959_a)) {
            int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃) * 100.0F);
            if (☃ > 0) {
               this.func_195067_a(StatList.field_211756_w, ☃);
               this.func_71020_j(0.01F * (float)☃ * 0.01F);
            }
         } else if (this.func_70090_H()) {
            int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃) * 100.0F);
            if (☃ > 0) {
               this.func_195067_a(StatList.field_211755_s, ☃);
               this.func_71020_j(0.01F * (float)☃ * 0.01F);
            }
         } else if (this.func_70617_f_()) {
            if (☃ > 0.0) {
               this.func_195067_a(StatList.field_188103_o, (int)Math.round(☃ * 100.0));
            }
         } else if (this.field_70122_E) {
            int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃) * 100.0F);
            if (☃ > 0) {
               if (this.func_70051_ag()) {
                  this.func_195067_a(StatList.field_188102_l, ☃);
                  this.func_71020_j(0.1F * (float)☃ * 0.01F);
               } else if (this.func_70093_af()) {
                  this.func_195067_a(StatList.field_188101_k, ☃);
                  this.func_71020_j(0.0F * (float)☃ * 0.01F);
               } else {
                  this.func_195067_a(StatList.field_188100_j, ☃);
                  this.func_71020_j(0.0F * (float)☃ * 0.01F);
               }
            }
         } else if (this.func_184613_cA()) {
            int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃) * 100.0F);
            this.func_195067_a(StatList.field_188110_v, ☃);
         } else {
            int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃) * 100.0F);
            if (☃ > 25) {
               this.func_195067_a(StatList.field_188104_p, ☃);
            }
         }
      }
   }

   private void func_71015_k(double var1, double var3, double var5) {
      if (this.func_184218_aH()) {
         int ☃ = Math.round(MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃) * 100.0F);
         if (☃ > 0) {
            if (this.func_184187_bx() instanceof EntityMinecart) {
               this.func_195067_a(StatList.field_188106_r, ☃);
            } else if (this.func_184187_bx() instanceof EntityBoat) {
               this.func_195067_a(StatList.field_188107_s, ☃);
            } else if (this.func_184187_bx() instanceof EntityPig) {
               this.func_195067_a(StatList.field_188108_t, ☃);
            } else if (this.func_184187_bx() instanceof AbstractHorse) {
               this.func_195067_a(StatList.field_188109_u, ☃);
            }
         }
      }
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      if (!this.field_71075_bZ.field_75101_c) {
         if (☃ >= 2.0F) {
            this.func_195067_a(StatList.field_75943_n, (int)Math.round((double)☃ * 100.0));
         }

         super.func_180430_e(☃, ☃);
      }
   }

   @Override
   protected void func_71061_d_() {
      if (!this.func_175149_v()) {
         super.func_71061_d_();
      }
   }

   @Override
   protected SoundEvent func_184588_d(int var1) {
      return ☃ > 4 ? SoundEvents.field_187736_dY : SoundEvents.field_187804_ed;
   }

   @Override
   public void func_70074_a(EntityLivingBase var1) {
      this.func_71029_a(StatList.field_199090_h.func_199076_b(☃.func_200600_R()));
   }

   @Override
   public void func_70110_aj() {
      if (!this.field_71075_bZ.field_75100_b) {
         super.func_70110_aj();
      }
   }

   public void func_195068_e(int var1) {
      this.func_85039_t(☃);
      this.field_71106_cc += (float)☃ / (float)this.func_71050_bK();
      this.field_71067_cb = MathHelper.func_76125_a(this.field_71067_cb + ☃, 0, Integer.MAX_VALUE);

      while(this.field_71106_cc < 0.0F) {
         float ☃ = this.field_71106_cc * (float)this.func_71050_bK();
         if (this.field_71068_ca > 0) {
            this.func_82242_a(-1);
            this.field_71106_cc = 1.0F + ☃ / (float)this.func_71050_bK();
         } else {
            this.func_82242_a(-1);
            this.field_71106_cc = 0.0F;
         }
      }

      while(this.field_71106_cc >= 1.0F) {
         this.field_71106_cc = (this.field_71106_cc - 1.0F) * (float)this.func_71050_bK();
         this.func_82242_a(1);
         this.field_71106_cc /= (float)this.func_71050_bK();
      }
   }

   public int func_175138_ci() {
      return this.field_175152_f;
   }

   public void func_192024_a(ItemStack var1, int var2) {
      this.field_71068_ca -= ☃;
      if (this.field_71068_ca < 0) {
         this.field_71068_ca = 0;
         this.field_71106_cc = 0.0F;
         this.field_71067_cb = 0;
      }

      this.field_175152_f = this.field_70146_Z.nextInt();
   }

   public void func_82242_a(int var1) {
      this.field_71068_ca += ☃;
      if (this.field_71068_ca < 0) {
         this.field_71068_ca = 0;
         this.field_71106_cc = 0.0F;
         this.field_71067_cb = 0;
      }

      if (☃ > 0 && this.field_71068_ca % 5 == 0 && (float)this.field_82249_h < (float)this.field_70173_aa - 100.0F) {
         float ☃ = this.field_71068_ca > 30 ? 1.0F : (float)this.field_71068_ca / 30.0F;
         this.field_70170_p
            .func_184148_a(
               null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187802_ec, this.func_184176_by(), ☃ * 0.75F, 1.0F
            );
         this.field_82249_h = this.field_70173_aa;
      }
   }

   public int func_71050_bK() {
      if (this.field_71068_ca >= 30) {
         return 112 + (this.field_71068_ca - 30) * 9;
      } else {
         return this.field_71068_ca >= 15 ? 37 + (this.field_71068_ca - 15) * 5 : 7 + this.field_71068_ca * 2;
      }
   }

   public void func_71020_j(float var1) {
      if (!this.field_71075_bZ.field_75102_a) {
         if (!this.field_70170_p.field_72995_K) {
            this.field_71100_bB.func_75113_a(☃);
         }
      }
   }

   public FoodStats func_71024_bL() {
      return this.field_71100_bB;
   }

   public boolean func_71043_e(boolean var1) {
      return !this.field_71075_bZ.field_75102_a && (☃ || this.field_71100_bB.func_75121_c());
   }

   public boolean func_70996_bM() {
      return this.func_110143_aJ() > 0.0F && this.func_110143_aJ() < this.func_110138_aP();
   }

   public boolean func_175142_cm() {
      return this.field_71075_bZ.field_75099_e;
   }

   public boolean func_175151_a(BlockPos var1, EnumFacing var2, ItemStack var3) {
      if (this.field_71075_bZ.field_75099_e) {
         return true;
      } else {
         BlockPos ☃ = ☃.func_177972_a(☃.func_176734_d());
         BlockWorldState ☃x = new BlockWorldState(this.field_70170_p, ☃, false);
         return ☃.func_206847_b(this.field_70170_p.func_205772_D(), ☃x);
      }
   }

   @Override
   protected int func_70693_a(EntityPlayer var1) {
      if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory") && !this.func_175149_v()) {
         int ☃ = this.field_71068_ca * 7;
         return ☃ > 100 ? 100 : ☃;
      } else {
         return 0;
      }
   }

   @Override
   protected boolean func_70684_aJ() {
      return true;
   }

   @Override
   public boolean func_94059_bO() {
      return true;
   }

   @Override
   protected boolean func_70041_e_() {
      return !this.field_71075_bZ.field_75100_b;
   }

   public void func_71016_p() {
   }

   public void func_71033_a(GameType var1) {
   }

   @Override
   public ITextComponent func_200200_C_() {
      return new TextComponentString(this.field_146106_i.getName());
   }

   public InventoryEnderChest func_71005_bN() {
      return this.field_71078_a;
   }

   @Override
   public ItemStack func_184582_a(EntityEquipmentSlot var1) {
      if (☃ == EntityEquipmentSlot.MAINHAND) {
         return this.field_71071_by.func_70448_g();
      } else if (☃ == EntityEquipmentSlot.OFFHAND) {
         return this.field_71071_by.field_184439_c.get(0);
      } else {
         return ☃.func_188453_a() == EntityEquipmentSlot.Type.ARMOR ? this.field_71071_by.field_70460_b.get(☃.func_188454_b()) : ItemStack.field_190927_a;
      }
   }

   @Override
   public void func_184201_a(EntityEquipmentSlot var1, ItemStack var2) {
      if (☃ == EntityEquipmentSlot.MAINHAND) {
         this.func_184606_a_(☃);
         this.field_71071_by.field_70462_a.set(this.field_71071_by.field_70461_c, ☃);
      } else if (☃ == EntityEquipmentSlot.OFFHAND) {
         this.func_184606_a_(☃);
         this.field_71071_by.field_184439_c.set(0, ☃);
      } else if (☃.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
         this.func_184606_a_(☃);
         this.field_71071_by.field_70460_b.set(☃.func_188454_b(), ☃);
      }
   }

   public boolean func_191521_c(ItemStack var1) {
      this.func_184606_a_(☃);
      return this.field_71071_by.func_70441_a(☃);
   }

   @Override
   public Iterable<ItemStack> func_184214_aD() {
      return Lists.<ItemStack>newArrayList(this.func_184614_ca(), this.func_184592_cb());
   }

   @Override
   public Iterable<ItemStack> func_184193_aE() {
      return this.field_71071_by.field_70460_b;
   }

   public boolean func_192027_g(NBTTagCompound var1) {
      if (this.func_184218_aH() || !this.field_70122_E || this.func_70090_H()) {
         return false;
      } else if (this.func_192023_dk().isEmpty()) {
         this.func_192029_h(☃);
         return true;
      } else if (this.func_192025_dl().isEmpty()) {
         this.func_192031_i(☃);
         return true;
      } else {
         return false;
      }
   }

   protected void func_192030_dh() {
      this.func_192026_k(this.func_192023_dk());
      this.func_192029_h(new NBTTagCompound());
      this.func_192026_k(this.func_192025_dl());
      this.func_192031_i(new NBTTagCompound());
   }

   private void func_192026_k(@Nullable NBTTagCompound var1) {
      if (!this.field_70170_p.field_72995_K && !☃.isEmpty()) {
         Entity ☃ = EntityType.func_200716_a(☃, this.field_70170_p);
         if (☃ instanceof EntityTameable) {
            ((EntityTameable)☃).func_184754_b(this.field_96093_i);
         }

         ☃.func_70107_b(this.field_70165_t, this.field_70163_u + 0.7F, this.field_70161_v);
         this.field_70170_p.func_72838_d(☃);
      }
   }

   @Override
   public boolean func_98034_c(EntityPlayer var1) {
      if (!this.func_82150_aj()) {
         return false;
      } else if (☃.func_175149_v()) {
         return false;
      } else {
         Team ☃ = this.func_96124_cp();
         return ☃ == null || ☃ == null || ☃.func_96124_cp() != ☃ || !☃.func_98297_h();
      }
   }

   public abstract boolean func_175149_v();

   @Override
   public boolean func_203007_ba() {
      return !this.field_71075_bZ.field_75100_b && !this.func_175149_v() && super.func_203007_ba();
   }

   public abstract boolean func_184812_l_();

   @Override
   public boolean func_96092_aw() {
      return !this.field_71075_bZ.field_75100_b;
   }

   public Scoreboard func_96123_co() {
      return this.field_70170_p.func_96441_U();
   }

   @Override
   public ITextComponent func_145748_c_() {
      ITextComponent ☃ = ScorePlayerTeam.func_200541_a(this.func_96124_cp(), this.func_200200_C_());
      return this.func_208016_c(☃);
   }

   public ITextComponent func_208017_dF() {
      return new TextComponentString("")
         .func_150257_a(this.func_200200_C_())
         .func_150258_a(" (")
         .func_150258_a(this.field_146106_i.getId().toString())
         .func_150258_a(")");
   }

   private ITextComponent func_208016_c(ITextComponent var1) {
      String ☃ = this.func_146103_bH().getName();
      return ☃.func_211710_a(
         var2x -> var2x.func_150241_a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + ☃ + " "))
               .func_150209_a(this.func_174823_aP())
               .func_179989_a(☃)
      );
   }

   @Override
   public String func_195047_I_() {
      return this.func_146103_bH().getName();
   }

   @Override
   public float func_70047_e() {
      float ☃ = 1.62F;
      if (this.func_70608_bn()) {
         ☃ = 0.2F;
      } else if (this.func_203007_ba() || this.func_184613_cA() || this.field_70131_O == 0.6F) {
         ☃ = 0.4F;
      } else if (this.func_70093_af() || this.field_70131_O == 1.65F) {
         ☃ -= 0.08F;
      }

      return ☃;
   }

   @Override
   public void func_110149_m(float var1) {
      if (☃ < 0.0F) {
         ☃ = 0.0F;
      }

      this.func_184212_Q().func_187227_b(field_184829_a, ☃);
   }

   @Override
   public float func_110139_bj() {
      return this.func_184212_Q().func_187225_a(field_184829_a);
   }

   public static UUID func_146094_a(GameProfile var0) {
      UUID ☃ = ☃.getId();
      if (☃ == null) {
         ☃ = func_175147_b(☃.getName());
      }

      return ☃;
   }

   public static UUID func_175147_b(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + ☃).getBytes(StandardCharsets.UTF_8));
   }

   public boolean func_175146_a(LockCode var1) {
      if (☃.func_180160_a()) {
         return true;
      } else {
         ItemStack ☃ = this.func_184614_ca();
         return !☃.func_190926_b() && ☃.func_82837_s() ? ☃.func_200301_q().getString().equals(☃.func_180159_b()) : false;
      }
   }

   public boolean func_175148_a(EnumPlayerModelParts var1) {
      return (this.func_184212_Q().func_187225_a(field_184827_bp) & ☃.func_179327_a()) == ☃.func_179327_a();
   }

   @Override
   public boolean func_174820_d(int var1, ItemStack var2) {
      if (☃ >= 0 && ☃ < this.field_71071_by.field_70462_a.size()) {
         this.field_71071_by.func_70299_a(☃, ☃);
         return true;
      } else {
         EntityEquipmentSlot ☃;
         if (☃ == 100 + EntityEquipmentSlot.HEAD.func_188454_b()) {
            ☃ = EntityEquipmentSlot.HEAD;
         } else if (☃ == 100 + EntityEquipmentSlot.CHEST.func_188454_b()) {
            ☃ = EntityEquipmentSlot.CHEST;
         } else if (☃ == 100 + EntityEquipmentSlot.LEGS.func_188454_b()) {
            ☃ = EntityEquipmentSlot.LEGS;
         } else if (☃ == 100 + EntityEquipmentSlot.FEET.func_188454_b()) {
            ☃ = EntityEquipmentSlot.FEET;
         } else {
            ☃ = null;
         }

         if (☃ == 98) {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, ☃);
            return true;
         } else if (☃ == 99) {
            this.func_184201_a(EntityEquipmentSlot.OFFHAND, ☃);
            return true;
         } else if (☃ == null) {
            int ☃ = ☃ - 200;
            if (☃ >= 0 && ☃ < this.field_71078_a.func_70302_i_()) {
               this.field_71078_a.func_70299_a(☃, ☃);
               return true;
            } else {
               return false;
            }
         } else {
            if (!☃.func_190926_b()) {
               if (!(☃.func_77973_b() instanceof ItemArmor) && !(☃.func_77973_b() instanceof ItemElytra)) {
                  if (☃ != EntityEquipmentSlot.HEAD) {
                     return false;
                  }
               } else if (EntityLiving.func_184640_d(☃) != ☃) {
                  return false;
               }
            }

            this.field_71071_by.func_70299_a(☃.func_188454_b() + this.field_71071_by.field_70462_a.size(), ☃);
            return true;
         }
      }
   }

   public boolean func_175140_cp() {
      return this.field_175153_bG;
   }

   public void func_175150_k(boolean var1) {
      this.field_175153_bG = ☃;
   }

   @Override
   public EnumHandSide func_184591_cq() {
      return this.field_70180_af.func_187225_a(field_184828_bq) == 0 ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
   }

   public void func_184819_a(EnumHandSide var1) {
      this.field_70180_af.func_187227_b(field_184828_bq, (byte)(☃ == EnumHandSide.LEFT ? 0 : 1));
   }

   public NBTTagCompound func_192023_dk() {
      return this.field_70180_af.func_187225_a(field_192032_bt);
   }

   protected void func_192029_h(NBTTagCompound var1) {
      this.field_70180_af.func_187227_b(field_192032_bt, ☃);
   }

   public NBTTagCompound func_192025_dl() {
      return this.field_70180_af.func_187225_a(field_192033_bu);
   }

   protected void func_192031_i(NBTTagCompound var1) {
      this.field_70180_af.func_187227_b(field_192033_bu, ☃);
   }

   public float func_184818_cX() {
      return (float)(1.0 / this.func_110148_a(SharedMonsterAttributes.field_188790_f).func_111126_e() * 20.0);
   }

   public float func_184825_o(float var1) {
      return MathHelper.func_76131_a(((float)this.field_184617_aD + ☃) / this.func_184818_cX(), 0.0F, 1.0F);
   }

   public void func_184821_cY() {
      this.field_184617_aD = 0;
   }

   public CooldownTracker func_184811_cZ() {
      return this.field_184832_bU;
   }

   @Override
   public void func_70108_f(Entity var1) {
      if (!this.func_70608_bn()) {
         super.func_70108_f(☃);
      }
   }

   public float func_184817_da() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_188792_h).func_111126_e();
   }

   public boolean func_195070_dx() {
      return this.field_71075_bZ.field_75098_d && this.func_184840_I() >= 2;
   }

   public static enum EnumChatVisibility {
      FULL(0, "options.chat.visibility.full"),
      SYSTEM(1, "options.chat.visibility.system"),
      HIDDEN(2, "options.chat.visibility.hidden");

      private static final EntityPlayer.EnumChatVisibility[] field_151432_d = (EntityPlayer.EnumChatVisibility[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(EntityPlayer.EnumChatVisibility::func_151428_a))
         .toArray(var0 -> new EntityPlayer.EnumChatVisibility[var0]);
      private final int field_151433_e;
      private final String field_151430_f;

      private EnumChatVisibility(int var3, String var4) {
         this.field_151433_e = ☃;
         this.field_151430_f = ☃;
      }

      public int func_151428_a() {
         return this.field_151433_e;
      }

      public static EntityPlayer.EnumChatVisibility func_151426_a(int var0) {
         return field_151432_d[☃ % field_151432_d.length];
      }

      public String func_151429_b() {
         return this.field_151430_f;
      }
   }

   static class SleepEnemyPredicate implements Predicate<EntityMob> {
      private final EntityPlayer field_192387_a;

      private SleepEnemyPredicate(EntityPlayer var1) {
         this.field_192387_a = ☃;
      }

      public boolean test(@Nullable EntityMob var1) {
         return ☃.func_191990_c(this.field_192387_a);
      }
   }

   public static enum SleepResult {
      OK,
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW,
      TOO_FAR_AWAY,
      OTHER_PROBLEM,
      NOT_SAFE;
   }
}
