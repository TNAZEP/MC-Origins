package net.minecraft.entity;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.PotionUtils;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.CombatRules;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EntityLivingBase extends Entity {
   private static final Logger field_190632_a = LogManager.getLogger();
   private static final UUID field_110156_b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier field_110157_c = new AttributeModifier(field_110156_b, "Sprinting speed boost", 0.3F, 2).func_111168_a(false);
   protected static final DataParameter<Byte> field_184621_as = EntityDataManager.func_187226_a(EntityLivingBase.class, DataSerializers.field_187191_a);
   private static final DataParameter<Float> field_184632_c = EntityDataManager.func_187226_a(EntityLivingBase.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184633_f = EntityDataManager.func_187226_a(EntityLivingBase.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184634_g = EntityDataManager.func_187226_a(EntityLivingBase.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184635_h = EntityDataManager.func_187226_a(EntityLivingBase.class, DataSerializers.field_187192_b);
   private AbstractAttributeMap field_110155_d;
   private final CombatTracker field_94063_bt = new CombatTracker(this);
   private final Map<Potion, PotionEffect> field_70713_bf = Maps.<Potion, PotionEffect>newHashMap();
   private final NonNullList<ItemStack> field_184630_bs = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
   private final NonNullList<ItemStack> field_184631_bt = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
   public boolean field_82175_bq;
   public EnumHand field_184622_au;
   public int field_110158_av;
   public int field_70720_be;
   public int field_70737_aN;
   public int field_70738_aO;
   public float field_70739_aP;
   public int field_70725_aQ;
   public float field_70732_aI;
   public float field_70733_aJ;
   protected int field_184617_aD;
   public float field_184618_aE;
   public float field_70721_aZ;
   public float field_184619_aG;
   public int field_70771_an = 20;
   public float field_70727_aS;
   public float field_70726_aT;
   public float field_70769_ao;
   public float field_70770_ap;
   public float field_70761_aq;
   public float field_70760_ar;
   public float field_70759_as;
   public float field_70758_at;
   public float field_70747_aH = 0.02F;
   protected EntityPlayer field_70717_bb;
   protected int field_70718_bc;
   protected boolean field_70729_aU;
   protected int field_70708_bq;
   protected float field_70768_au;
   protected float field_110154_aX;
   protected float field_70764_aw;
   protected float field_70763_ax;
   protected float field_70741_aB;
   protected int field_70744_aE;
   protected float field_110153_bc;
   protected boolean field_70703_bu;
   public float field_70702_br;
   public float field_70701_bs;
   public float field_191988_bg;
   public float field_70704_bt;
   protected int field_70716_bi;
   protected double field_184623_bh;
   protected double field_184624_bi;
   protected double field_184625_bj;
   protected double field_184626_bk;
   protected double field_70709_bj;
   protected double field_208001_bq;
   protected int field_208002_br;
   private boolean field_70752_e = true;
   private EntityLivingBase field_70755_b;
   private int field_70756_c;
   private EntityLivingBase field_110150_bn;
   private int field_142016_bo;
   private float field_70746_aG;
   private int field_70773_bE;
   private float field_110151_bq;
   protected ItemStack field_184627_bm = ItemStack.field_190927_a;
   protected int field_184628_bn;
   protected int field_184629_bo;
   private BlockPos field_184620_bC;
   private DamageSource field_189750_bF;
   private long field_189751_bG;
   protected int field_204807_bs;
   private float field_205017_bL;
   private float field_205018_bM;

   protected EntityLivingBase(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_110147_ax();
      this.func_70606_j(this.func_110138_aP());
      this.field_70156_m = true;
      this.field_70770_ap = (float)((Math.random() + 1.0) * 0.01F);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70769_ao = (float)Math.random() * 12398.0F;
      this.field_70177_z = (float)(Math.random() * (float) (Math.PI * 2));
      this.field_70759_as = this.field_70177_z;
      this.field_70138_W = 0.6F;
   }

   @Override
   public void func_174812_G() {
      this.func_70097_a(DamageSource.field_76380_i, Float.MAX_VALUE);
   }

   @Override
   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184621_as, (byte)0);
      this.field_70180_af.func_187214_a(field_184633_f, 0);
      this.field_70180_af.func_187214_a(field_184634_g, false);
      this.field_70180_af.func_187214_a(field_184635_h, 0);
      this.field_70180_af.func_187214_a(field_184632_c, 1.0F);
   }

   protected void func_110147_ax() {
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111267_a);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111266_c);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111263_d);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188791_g);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_189429_h);
   }

   @Override
   protected void func_184231_a(double var1, boolean var3, IBlockState var4, BlockPos var5) {
      if (!this.func_70090_H()) {
         this.func_70072_I();
      }

      if (!this.field_70170_p.field_72995_K && this.field_70143_R > 3.0F && ☃) {
         float ☃ = (float)MathHelper.func_76123_f(this.field_70143_R - 3.0F);
         if (!☃.func_196958_f()) {
            double ☃x = Math.min((double)(0.2F + ☃ / 15.0F), 2.5);
            int ☃xx = (int)(150.0 * ☃x);
            ((WorldServer)this.field_70170_p)
               .func_195598_a(
                  new BlockParticleData(Particles.field_197611_d, ☃), this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃xx, 0.0, 0.0, 0.0, 0.15F
               );
         }
      }

      super.func_184231_a(☃, ☃, ☃, ☃);
   }

   public boolean func_70648_aU() {
      return this.func_70668_bt() == CreatureAttribute.UNDEAD;
   }

   @Override
   public void func_70030_z() {
      this.field_70732_aI = this.field_70733_aJ;
      super.func_70030_z();
      this.field_70170_p.field_72984_F.func_76320_a("livingEntityBaseTick");
      boolean ☃ = this instanceof EntityPlayer;
      if (this.func_70089_S()) {
         if (this.func_70094_T()) {
            this.func_70097_a(DamageSource.field_76368_d, 1.0F);
         } else if (☃ && !this.field_70170_p.func_175723_af().func_177743_a(this.func_174813_aQ())) {
            double ☃x = this.field_70170_p.func_175723_af().func_177745_a(this) + this.field_70170_p.func_175723_af().func_177742_m();
            if (☃x < 0.0) {
               double ☃xx = this.field_70170_p.func_175723_af().func_177727_n();
               if (☃xx > 0.0) {
                  this.func_70097_a(DamageSource.field_76368_d, (float)Math.max(1, MathHelper.func_76128_c(-☃x * ☃xx)));
               }
            }
         }
      }

      if (this.func_70045_F() || this.field_70170_p.field_72995_K) {
         this.func_70066_B();
      }

      boolean ☃ = ☃ && ((EntityPlayer)this).field_71075_bZ.field_75102_a;
      if (this.func_70089_S()) {
         if (this.func_208600_a(FluidTags.field_206959_a)
            && this.field_70170_p
                  .func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v))
                  .func_177230_c()
               != Blocks.field_203203_C) {
            if (!this.func_70648_aU() && !PotionUtil.func_205133_c(this) && !☃) {
               this.func_70050_g(this.func_70682_h(this.func_70086_ai()));
               if (this.func_70086_ai() == -20) {
                  this.func_70050_g(0);

                  for(int ☃x = 0; ☃x < 8; ++☃x) {
                     float ☃xx = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                     float ☃xxx = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                     float ☃xxxx = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                     this.field_70170_p
                        .func_195594_a(
                           Particles.field_197612_e,
                           this.field_70165_t + (double)☃xx,
                           this.field_70163_u + (double)☃xxx,
                           this.field_70161_v + (double)☃xxxx,
                           this.field_70159_w,
                           this.field_70181_x,
                           this.field_70179_y
                        );
                  }

                  this.func_70097_a(DamageSource.field_76369_e, 2.0F);
               }
            }

            if (!this.field_70170_p.field_72995_K && this.func_184218_aH() && this.func_184187_bx() != null && !this.func_184187_bx().func_205710_ba()) {
               this.func_184210_p();
            }
         } else if (this.func_70086_ai() < this.func_205010_bg()) {
            this.func_70050_g(this.func_207300_l(this.func_70086_ai()));
         }

         if (!this.field_70170_p.field_72995_K) {
            BlockPos ☃x = new BlockPos(this);
            if (!Objects.equal(this.field_184620_bC, ☃x)) {
               this.field_184620_bC = ☃x;
               this.func_184594_b(☃x);
            }
         }
      }

      if (this.func_70089_S() && this.func_203008_ap()) {
         this.func_70066_B();
      }

      this.field_70727_aS = this.field_70726_aT;
      if (this.field_70737_aN > 0) {
         --this.field_70737_aN;
      }

      if (this.field_70172_ad > 0 && !(this instanceof EntityPlayerMP)) {
         --this.field_70172_ad;
      }

      if (this.func_110143_aJ() <= 0.0F) {
         this.func_70609_aI();
      }

      if (this.field_70718_bc > 0) {
         --this.field_70718_bc;
      } else {
         this.field_70717_bb = null;
      }

      if (this.field_110150_bn != null && !this.field_110150_bn.func_70089_S()) {
         this.field_110150_bn = null;
      }

      if (this.field_70755_b != null) {
         if (!this.field_70755_b.func_70089_S()) {
            this.func_70604_c(null);
         } else if (this.field_70173_aa - this.field_70756_c > 100) {
            this.func_70604_c(null);
         }
      }

      this.func_70679_bo();
      this.field_70763_ax = this.field_70764_aw;
      this.field_70760_ar = this.field_70761_aq;
      this.field_70758_at = this.field_70759_as;
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_184594_b(BlockPos var1) {
      int ☃ = EnchantmentHelper.func_185284_a(Enchantments.field_185301_j, this);
      if (☃ > 0) {
         EnchantmentFrostWalker.func_185266_a(this, this.field_70170_p, ☃, ☃);
      }
   }

   public boolean func_70631_g_() {
      return false;
   }

   @Override
   public boolean func_205710_ba() {
      return false;
   }

   protected void func_70609_aI() {
      ++this.field_70725_aQ;
      if (this.field_70725_aQ == 20) {
         if (!this.field_70170_p.field_72995_K
            && (this.func_70684_aJ() || this.field_70718_bc > 0 && this.func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot"))) {
            int ☃ = this.func_70693_a(this.field_70717_bb);

            while(☃ > 0) {
               int ☃x = EntityXPOrb.func_70527_a(☃);
               ☃ -= ☃x;
               this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃x));
            }
         }

         this.func_70106_y();

         for(int ☃ = 0; ☃ < 20; ++☃) {
            double ☃x = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃xx = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃xxx = this.field_70146_Z.nextGaussian() * 0.02;
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197598_I,
                  this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
                  this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O),
                  this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
                  ☃x,
                  ☃xx,
                  ☃xxx
               );
         }
      }
   }

   protected boolean func_146066_aG() {
      return !this.func_70631_g_();
   }

   protected int func_70682_h(int var1) {
      int ☃ = EnchantmentHelper.func_185292_c(this);
      return ☃ > 0 && this.field_70146_Z.nextInt(☃ + 1) > 0 ? ☃ : ☃ - 1;
   }

   protected int func_207300_l(int var1) {
      return Math.min(☃ + 4, this.func_205010_bg());
   }

   protected int func_70693_a(EntityPlayer var1) {
      return 0;
   }

   protected boolean func_70684_aJ() {
      return false;
   }

   public Random func_70681_au() {
      return this.field_70146_Z;
   }

   @Nullable
   public EntityLivingBase func_70643_av() {
      return this.field_70755_b;
   }

   public int func_142015_aE() {
      return this.field_70756_c;
   }

   public void func_70604_c(@Nullable EntityLivingBase var1) {
      this.field_70755_b = ☃;
      this.field_70756_c = this.field_70173_aa;
   }

   public EntityLivingBase func_110144_aD() {
      return this.field_110150_bn;
   }

   public int func_142013_aG() {
      return this.field_142016_bo;
   }

   public void func_130011_c(Entity var1) {
      if (☃ instanceof EntityLivingBase) {
         this.field_110150_bn = (EntityLivingBase)☃;
      } else {
         this.field_110150_bn = null;
      }

      this.field_142016_bo = this.field_70173_aa;
   }

   public int func_70654_ax() {
      return this.field_70708_bq;
   }

   protected void func_184606_a_(ItemStack var1) {
      if (!☃.func_190926_b()) {
         SoundEvent ☃ = SoundEvents.field_187719_p;
         Item ☃x = ☃.func_77973_b();
         if (☃x instanceof ItemArmor) {
            ☃ = ((ItemArmor)☃x).func_200880_d().func_200899_b();
         } else if (☃x == Items.field_185160_cR) {
            ☃ = SoundEvents.field_191258_p;
         }

         this.func_184185_a(☃, 1.0F, 1.0F);
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74776_a("Health", this.func_110143_aJ());
      ☃.func_74777_a("HurtTime", (short)this.field_70737_aN);
      ☃.func_74768_a("HurtByTimestamp", this.field_70756_c);
      ☃.func_74777_a("DeathTime", (short)this.field_70725_aQ);
      ☃.func_74776_a("AbsorptionAmount", this.func_110139_bj());

      for(EntityEquipmentSlot ☃ : EntityEquipmentSlot.values()) {
         ItemStack ☃x = this.func_184582_a(☃);
         if (!☃x.func_190926_b()) {
            this.func_110140_aT().func_111148_a(☃x.func_111283_C(☃));
         }
      }

      ☃.func_74782_a("Attributes", SharedMonsterAttributes.func_111257_a(this.func_110140_aT()));

      for(EntityEquipmentSlot ☃ : EntityEquipmentSlot.values()) {
         ItemStack ☃x = this.func_184582_a(☃);
         if (!☃x.func_190926_b()) {
            this.func_110140_aT().func_111147_b(☃x.func_111283_C(☃));
         }
      }

      if (!this.field_70713_bf.isEmpty()) {
         NBTTagList ☃ = new NBTTagList();

         for(PotionEffect ☃x : this.field_70713_bf.values()) {
            ☃.add((INBTBase)☃x.func_82719_a(new NBTTagCompound()));
         }

         ☃.func_74782_a("ActiveEffects", ☃);
      }

      ☃.func_74757_a("FallFlying", this.func_184613_cA());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.func_110149_m(☃.func_74760_g("AbsorptionAmount"));
      if (☃.func_150297_b("Attributes", 9) && this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         SharedMonsterAttributes.func_151475_a(this.func_110140_aT(), ☃.func_150295_c("Attributes", 10));
      }

      if (☃.func_150297_b("ActiveEffects", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("ActiveEffects", 10);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
            PotionEffect ☃xxx = PotionEffect.func_82722_b(☃xx);
            if (☃xxx != null) {
               this.field_70713_bf.put(☃xxx.func_188419_a(), ☃xxx);
            }
         }
      }

      if (☃.func_150297_b("Health", 99)) {
         this.func_70606_j(☃.func_74760_g("Health"));
      }

      this.field_70737_aN = ☃.func_74765_d("HurtTime");
      this.field_70725_aQ = ☃.func_74765_d("DeathTime");
      this.field_70756_c = ☃.func_74762_e("HurtByTimestamp");
      if (☃.func_150297_b("Team", 8)) {
         String ☃ = ☃.func_74779_i("Team");
         ScorePlayerTeam ☃x = this.field_70170_p.func_96441_U().func_96508_e(☃);
         boolean ☃xx = ☃x != null && this.field_70170_p.func_96441_U().func_197901_a(this.func_189512_bd(), ☃x);
         if (!☃xx) {
            field_190632_a.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", ☃);
         }
      }

      if (☃.func_74767_n("FallFlying")) {
         this.func_70052_a(7, true);
      }
   }

   protected void func_70679_bo() {
      Iterator<Potion> ☃ = this.field_70713_bf.keySet().iterator();

      try {
         while(☃.hasNext()) {
            Potion ☃x = (Potion)☃.next();
            PotionEffect ☃xx = (PotionEffect)this.field_70713_bf.get(☃x);
            if (!☃xx.func_76455_a(this)) {
               if (!this.field_70170_p.field_72995_K) {
                  ☃.remove();
                  this.func_70688_c(☃xx);
               }
            } else if (☃xx.func_76459_b() % 600 == 0) {
               this.func_70695_b(☃xx, false);
            }
         }
      } catch (ConcurrentModificationException var11) {
      }

      if (this.field_70752_e) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_175135_B();
         }

         this.field_70752_e = false;
      }

      int ☃x = this.field_70180_af.func_187225_a(field_184633_f);
      boolean ☃xx = this.field_70180_af.func_187225_a(field_184634_g);
      if (☃x > 0) {
         boolean ☃xxx;
         if (this.func_82150_aj()) {
            ☃xxx = this.field_70146_Z.nextInt(15) == 0;
         } else {
            ☃xxx = this.field_70146_Z.nextBoolean();
         }

         if (☃xx) {
            ☃xxx &= this.field_70146_Z.nextInt(5) == 0;
         }

         if (☃xxx && ☃x > 0) {
            double ☃xxx = (double)(☃x >> 16 & 0xFF) / 255.0;
            double ☃xxxx = (double)(☃x >> 8 & 0xFF) / 255.0;
            double ☃xxxxx = (double)(☃x >> 0 & 0xFF) / 255.0;
            this.field_70170_p
               .func_195594_a(
                  ☃xx ? Particles.field_197608_a : Particles.field_197625_r,
                  this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                  this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O,
                  this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                  ☃xxx,
                  ☃xxxx,
                  ☃xxxxx
               );
         }
      }
   }

   protected void func_175135_B() {
      if (this.field_70713_bf.isEmpty()) {
         this.func_175133_bi();
         this.func_82142_c(false);
      } else {
         Collection<PotionEffect> ☃ = this.field_70713_bf.values();
         this.field_70180_af.func_187227_b(field_184634_g, func_184593_a(☃));
         this.field_70180_af.func_187227_b(field_184633_f, PotionUtils.func_185181_a(☃));
         this.func_82142_c(this.func_70644_a(MobEffects.field_76441_p));
      }
   }

   public static boolean func_184593_a(Collection<PotionEffect> var0) {
      for(PotionEffect ☃ : ☃) {
         if (!☃.func_82720_e()) {
            return false;
         }
      }

      return true;
   }

   protected void func_175133_bi() {
      this.field_70180_af.func_187227_b(field_184634_g, false);
      this.field_70180_af.func_187227_b(field_184633_f, 0);
   }

   public boolean func_195061_cb() {
      if (this.field_70170_p.field_72995_K) {
         return false;
      } else {
         Iterator<PotionEffect> ☃ = this.field_70713_bf.values().iterator();

         boolean ☃;
         for(☃ = false; ☃.hasNext(); ☃ = true) {
            this.func_70688_c((PotionEffect)☃.next());
            ☃.remove();
         }

         return ☃;
      }
   }

   public Collection<PotionEffect> func_70651_bq() {
      return this.field_70713_bf.values();
   }

   public Map<Potion, PotionEffect> func_193076_bZ() {
      return this.field_70713_bf;
   }

   public boolean func_70644_a(Potion var1) {
      return this.field_70713_bf.containsKey(☃);
   }

   @Nullable
   public PotionEffect func_70660_b(Potion var1) {
      return (PotionEffect)this.field_70713_bf.get(☃);
   }

   public boolean func_195064_c(PotionEffect var1) {
      if (!this.func_70687_e(☃)) {
         return false;
      } else {
         PotionEffect ☃ = (PotionEffect)this.field_70713_bf.get(☃.func_188419_a());
         if (☃ == null) {
            this.field_70713_bf.put(☃.func_188419_a(), ☃);
            this.func_70670_a(☃);
            return true;
         } else if (☃.func_199308_a(☃)) {
            this.func_70695_b(☃, true);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean func_70687_e(PotionEffect var1) {
      if (this.func_70668_bt() == CreatureAttribute.UNDEAD) {
         Potion ☃ = ☃.func_188419_a();
         if (☃ == MobEffects.field_76428_l || ☃ == MobEffects.field_76436_u) {
            return false;
         }
      }

      return true;
   }

   public boolean func_70662_br() {
      return this.func_70668_bt() == CreatureAttribute.UNDEAD;
   }

   @Nullable
   public PotionEffect func_184596_c(@Nullable Potion var1) {
      return (PotionEffect)this.field_70713_bf.remove(☃);
   }

   public boolean func_195063_d(Potion var1) {
      PotionEffect ☃ = this.func_184596_c(☃);
      if (☃ != null) {
         this.func_70688_c(☃);
         return true;
      } else {
         return false;
      }
   }

   protected void func_70670_a(PotionEffect var1) {
      this.field_70752_e = true;
      if (!this.field_70170_p.field_72995_K) {
         ☃.func_188419_a().func_111185_a(this, this.func_110140_aT(), ☃.func_76458_c());
      }
   }

   protected void func_70695_b(PotionEffect var1, boolean var2) {
      this.field_70752_e = true;
      if (☃ && !this.field_70170_p.field_72995_K) {
         Potion ☃ = ☃.func_188419_a();
         ☃.func_111187_a(this, this.func_110140_aT(), ☃.func_76458_c());
         ☃.func_111185_a(this, this.func_110140_aT(), ☃.func_76458_c());
      }
   }

   protected void func_70688_c(PotionEffect var1) {
      this.field_70752_e = true;
      if (!this.field_70170_p.field_72995_K) {
         ☃.func_188419_a().func_111187_a(this, this.func_110140_aT(), ☃.func_76458_c());
      }
   }

   public void func_70691_i(float var1) {
      float ☃ = this.func_110143_aJ();
      if (☃ > 0.0F) {
         this.func_70606_j(☃ + ☃);
      }
   }

   public float func_110143_aJ() {
      return this.field_70180_af.func_187225_a(field_184632_c);
   }

   public void func_70606_j(float var1) {
      this.field_70180_af.func_187227_b(field_184632_c, MathHelper.func_76131_a(☃, 0.0F, this.func_110138_aP()));
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (this.field_70170_p.field_72995_K) {
         return false;
      } else if (this.func_110143_aJ() <= 0.0F) {
         return false;
      } else if (☃.func_76347_k() && this.func_70644_a(MobEffects.field_76426_n)) {
         return false;
      } else {
         this.field_70708_bq = 0;
         float ☃ = ☃;
         if ((☃ == DamageSource.field_82728_o || ☃ == DamageSource.field_82729_p) && !this.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
            this.func_184582_a(EntityEquipmentSlot.HEAD).func_77972_a((int)(☃ * 4.0F + this.field_70146_Z.nextFloat() * ☃ * 2.0F), this);
            ☃ *= 0.75F;
         }

         boolean ☃ = false;
         float ☃x = 0.0F;
         if (☃ > 0.0F && this.func_184583_d(☃)) {
            this.func_184590_k(☃);
            ☃x = ☃;
            ☃ = 0.0F;
            if (!☃.func_76352_a()) {
               Entity ☃xx = ☃.func_76364_f();
               if (☃xx instanceof EntityLivingBase) {
                  this.func_190629_c((EntityLivingBase)☃xx);
               }
            }

            ☃ = true;
         }

         this.field_70721_aZ = 1.5F;
         boolean ☃ = true;
         if ((float)this.field_70172_ad > (float)this.field_70771_an / 2.0F) {
            if (☃ <= this.field_110153_bc) {
               return false;
            }

            this.func_70665_d(☃, ☃ - this.field_110153_bc);
            this.field_110153_bc = ☃;
            ☃ = false;
         } else {
            this.field_110153_bc = ☃;
            this.field_70172_ad = this.field_70771_an;
            this.func_70665_d(☃, ☃);
            this.field_70738_aO = 10;
            this.field_70737_aN = this.field_70738_aO;
         }

         this.field_70739_aP = 0.0F;
         Entity ☃ = ☃.func_76346_g();
         if (☃ != null) {
            if (☃ instanceof EntityLivingBase) {
               this.func_70604_c((EntityLivingBase)☃);
            }

            if (☃ instanceof EntityPlayer) {
               this.field_70718_bc = 100;
               this.field_70717_bb = (EntityPlayer)☃;
            } else if (☃ instanceof EntityWolf) {
               EntityWolf ☃x = (EntityWolf)☃;
               if (☃x.func_70909_n()) {
                  this.field_70718_bc = 100;
                  this.field_70717_bb = null;
               }
            }
         }

         if (☃) {
            if (☃) {
               this.field_70170_p.func_72960_a(this, (byte)29);
            } else if (☃ instanceof EntityDamageSource && ((EntityDamageSource)☃).func_180139_w()) {
               this.field_70170_p.func_72960_a(this, (byte)33);
            } else {
               byte ☃;
               if (☃ == DamageSource.field_76369_e) {
                  ☃ = 36;
               } else if (☃.func_76347_k()) {
                  ☃ = 37;
               } else {
                  ☃ = 2;
               }

               this.field_70170_p.func_72960_a(this, ☃);
            }

            if (☃ != DamageSource.field_76369_e && (!☃ || ☃ > 0.0F)) {
               this.func_70018_K();
            }

            if (☃ != null) {
               double ☃ = ☃.field_70165_t - this.field_70165_t;

               double ☃;
               for(☃ = ☃.field_70161_v - this.field_70161_v; ☃ * ☃ + ☃ * ☃ < 1.0E-4; ☃ = (Math.random() - Math.random()) * 0.01) {
                  ☃ = (Math.random() - Math.random()) * 0.01;
               }

               this.field_70739_aP = (float)(MathHelper.func_181159_b(☃, ☃) * 180.0F / (float)Math.PI - (double)this.field_70177_z);
               this.func_70653_a(☃, 0.4F, ☃, ☃);
            } else {
               this.field_70739_aP = (float)((int)(Math.random() * 2.0) * 180);
            }
         }

         if (this.func_110143_aJ() <= 0.0F) {
            if (!this.func_190628_d(☃)) {
               SoundEvent ☃ = this.func_184615_bR();
               if (☃ && ☃ != null) {
                  this.func_184185_a(☃, this.func_70599_aP(), this.func_70647_i());
               }

               this.func_70645_a(☃);
            }
         } else if (☃) {
            this.func_184581_c(☃);
         }

         boolean ☃ = !☃ || ☃ > 0.0F;
         if (☃) {
            this.field_189750_bF = ☃;
            this.field_189751_bG = this.field_70170_p.func_82737_E();
         }

         if (this instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192128_h.func_192200_a((EntityPlayerMP)this, ☃, ☃, ☃, ☃);
            if (☃x > 0.0F && ☃x < 3.4028235E37F) {
               ((EntityPlayerMP)this).func_195067_a(StatList.field_212737_I, Math.round(☃x * 10.0F));
            }
         }

         if (☃ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192127_g.func_192220_a((EntityPlayerMP)☃, this, ☃, ☃, ☃, ☃);
         }

         return ☃;
      }
   }

   protected void func_190629_c(EntityLivingBase var1) {
      ☃.func_70653_a(this, 0.5F, this.field_70165_t - ☃.field_70165_t, this.field_70161_v - ☃.field_70161_v);
   }

   private boolean func_190628_d(DamageSource var1) {
      if (☃.func_76357_e()) {
         return false;
      } else {
         ItemStack ☃ = null;

         for(EnumHand ☃x : EnumHand.values()) {
            ItemStack ☃xx = this.func_184586_b(☃x);
            if (☃xx.func_77973_b() == Items.field_190929_cY) {
               ☃ = ☃xx.func_77946_l();
               ☃xx.func_190918_g(1);
               break;
            }
         }

         if (☃ != null) {
            if (this instanceof EntityPlayerMP) {
               EntityPlayerMP ☃x = (EntityPlayerMP)this;
               ☃x.func_71029_a(StatList.field_75929_E.func_199076_b(Items.field_190929_cY));
               CriteriaTriggers.field_193130_A.func_193187_a(☃x, ☃);
            }

            this.func_70606_j(1.0F);
            this.func_195061_cb();
            this.func_195064_c(new PotionEffect(MobEffects.field_76428_l, 900, 1));
            this.func_195064_c(new PotionEffect(MobEffects.field_76444_x, 100, 1));
            this.field_70170_p.func_72960_a(this, (byte)35);
         }

         return ☃ != null;
      }
   }

   @Nullable
   public DamageSource func_189748_bU() {
      if (this.field_70170_p.func_82737_E() - this.field_189751_bG > 40L) {
         this.field_189750_bF = null;
      }

      return this.field_189750_bF;
   }

   protected void func_184581_c(DamageSource var1) {
      SoundEvent ☃ = this.func_184601_bQ(☃);
      if (☃ != null) {
         this.func_184185_a(☃, this.func_70599_aP(), this.func_70647_i());
      }
   }

   private boolean func_184583_d(DamageSource var1) {
      if (!☃.func_76363_c() && this.func_184585_cz()) {
         Vec3d ☃ = ☃.func_188404_v();
         if (☃ != null) {
            Vec3d ☃x = this.func_70676_i(1.0F);
            Vec3d ☃xx = ☃.func_72444_a(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v)).func_72432_b();
            ☃xx = new Vec3d(☃xx.field_72450_a, 0.0, ☃xx.field_72449_c);
            if (☃xx.func_72430_b(☃x) < 0.0) {
               return true;
            }
         }
      }

      return false;
   }

   public void func_70669_a(ItemStack var1) {
      super.func_184185_a(SoundEvents.field_187635_cQ, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
      this.func_195062_a(☃, 5);
   }

   public void func_70645_a(DamageSource var1) {
      if (!this.field_70729_aU) {
         Entity ☃ = ☃.func_76346_g();
         EntityLivingBase ☃x = this.func_94060_bK();
         if (this.field_70744_aE >= 0 && ☃x != null) {
            ☃x.func_191956_a(this, this.field_70744_aE, ☃);
         }

         if (☃ != null) {
            ☃.func_70074_a(this);
         }

         this.field_70729_aU = true;
         this.func_110142_aN().func_94549_h();
         if (!this.field_70170_p.field_72995_K) {
            int ☃ = 0;
            if (☃ instanceof EntityPlayer) {
               ☃ = EnchantmentHelper.func_185283_h((EntityLivingBase)☃);
            }

            if (this.func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
               boolean ☃ = this.field_70718_bc > 0;
               this.func_184610_a(☃, ☃, ☃);
            }
         }

         this.field_70170_p.func_72960_a(this, (byte)3);
      }
   }

   protected void func_184610_a(boolean var1, int var2, DamageSource var3) {
      this.func_70628_a(☃, ☃);
      this.func_82160_b(☃, ☃);
   }

   protected void func_82160_b(boolean var1, int var2) {
   }

   public void func_70653_a(Entity var1, float var2, double var3, double var5) {
      if (!(this.field_70146_Z.nextDouble() < this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e())) {
         this.field_70160_al = true;
         float ☃ = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃);
         this.field_70159_w /= 2.0;
         this.field_70179_y /= 2.0;
         this.field_70159_w -= ☃ / (double)☃ * (double)☃;
         this.field_70179_y -= ☃ / (double)☃ * (double)☃;
         if (this.field_70122_E) {
            this.field_70181_x /= 2.0;
            this.field_70181_x += (double)☃;
            if (this.field_70181_x > 0.4F) {
               this.field_70181_x = 0.4F;
            }
         }
      }
   }

   @Nullable
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187543_bD;
   }

   @Nullable
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187661_by;
   }

   protected SoundEvent func_184588_d(int var1) {
      return ☃ > 4 ? SoundEvents.field_187655_bw : SoundEvents.field_187545_bE;
   }

   protected void func_70628_a(boolean var1, int var2) {
   }

   public boolean func_70617_f_() {
      int ☃ = MathHelper.func_76128_c(this.field_70165_t);
      int ☃x = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
      int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
      if (this instanceof EntityPlayer && ((EntityPlayer)this).func_175149_v()) {
         return false;
      } else {
         BlockPos ☃ = new BlockPos(☃, ☃x, ☃xx);
         IBlockState ☃x = this.field_70170_p.func_180495_p(☃);
         Block ☃xx = ☃x.func_177230_c();
         if (☃xx != Blocks.field_150468_ap && ☃xx != Blocks.field_150395_bd) {
            return ☃xx instanceof BlockTrapDoor && this.func_184604_a(☃, ☃x);
         } else {
            return true;
         }
      }
   }

   private boolean func_184604_a(BlockPos var1, IBlockState var2) {
      if (☃.func_177229_b(BlockTrapDoor.field_176283_b)) {
         IBlockState ☃ = this.field_70170_p.func_180495_p(☃.func_177977_b());
         if (☃.func_177230_c() == Blocks.field_150468_ap && ☃.func_177229_b(BlockLadder.field_176382_a) == ☃.func_177229_b(BlockTrapDoor.field_185512_D)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean func_70089_S() {
      return !this.field_70128_L && this.func_110143_aJ() > 0.0F;
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      super.func_180430_e(☃, ☃);
      PotionEffect ☃ = this.func_70660_b(MobEffects.field_76430_j);
      float ☃x = ☃ == null ? 0.0F : (float)(☃.func_76458_c() + 1);
      int ☃xx = MathHelper.func_76123_f((☃ - 3.0F - ☃x) * ☃);
      if (☃xx > 0) {
         this.func_184185_a(this.func_184588_d(☃xx), 1.0F, 1.0F);
         this.func_70097_a(DamageSource.field_76379_h, (float)☃xx);
         int ☃xxx = MathHelper.func_76128_c(this.field_70165_t);
         int ☃xxxx = MathHelper.func_76128_c(this.field_70163_u - 0.2F);
         int ☃xxxxx = MathHelper.func_76128_c(this.field_70161_v);
         IBlockState ☃xxxxxx = this.field_70170_p.func_180495_p(new BlockPos(☃xxx, ☃xxxx, ☃xxxxx));
         if (!☃xxxxxx.func_196958_f()) {
            SoundType ☃xxxxxxx = ☃xxxxxx.func_177230_c().func_185467_w();
            this.func_184185_a(☃xxxxxxx.func_185842_g(), ☃xxxxxxx.func_185843_a() * 0.5F, ☃xxxxxxx.func_185847_b() * 0.75F);
         }
      }
   }

   public int func_70658_aO() {
      IAttributeInstance ☃ = this.func_110148_a(SharedMonsterAttributes.field_188791_g);
      return MathHelper.func_76128_c(☃.func_111126_e());
   }

   protected void func_70675_k(float var1) {
   }

   protected void func_184590_k(float var1) {
   }

   protected float func_70655_b(DamageSource var1, float var2) {
      if (!☃.func_76363_c()) {
         this.func_70675_k(☃);
         ☃ = CombatRules.func_189427_a(☃, (float)this.func_70658_aO(), (float)this.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
      }

      return ☃;
   }

   protected float func_70672_c(DamageSource var1, float var2) {
      if (☃.func_151517_h()) {
         return ☃;
      } else {
         if (this.func_70644_a(MobEffects.field_76429_m) && ☃ != DamageSource.field_76380_i) {
            int ☃ = (this.func_70660_b(MobEffects.field_76429_m).func_76458_c() + 1) * 5;
            int ☃x = 25 - ☃;
            float ☃xx = ☃ * (float)☃x;
            float ☃xxx = ☃;
            ☃ = Math.max(☃xx / 25.0F, 0.0F);
            float ☃xxxx = ☃xxx - ☃;
            if (☃xxxx > 0.0F && ☃xxxx < 3.4028235E37F) {
               if (this instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)this).func_195067_a(StatList.field_212739_K, Math.round(☃xxxx * 10.0F));
               } else if (☃.func_76346_g() instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)☃.func_76346_g()).func_195067_a(StatList.field_212736_G, Math.round(☃xxxx * 10.0F));
               }
            }
         }

         if (☃ <= 0.0F) {
            return 0.0F;
         } else {
            int ☃ = EnchantmentHelper.func_77508_a(this.func_184193_aE(), ☃);
            if (☃ > 0) {
               ☃ = CombatRules.func_188401_b(☃, (float)☃);
            }

            return ☃;
         }
      }
   }

   protected void func_70665_d(DamageSource var1, float var2) {
      if (!this.func_180431_b(☃)) {
         ☃ = this.func_70655_b(☃, ☃);
         ☃ = this.func_70672_c(☃, ☃);
         float var8 = Math.max(☃ - this.func_110139_bj(), 0.0F);
         this.func_110149_m(this.func_110139_bj() - (☃ - var8));
         float ☃ = ☃ - var8;
         if (☃ > 0.0F && ☃ < 3.4028235E37F && ☃.func_76346_g() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)☃.func_76346_g()).func_195067_a(StatList.field_212735_F, Math.round(☃ * 10.0F));
         }

         if (var8 != 0.0F) {
            float ☃ = this.func_110143_aJ();
            this.func_70606_j(☃ - var8);
            this.func_110142_aN().func_94547_a(☃, ☃, var8);
            this.func_110149_m(this.func_110139_bj() - var8);
         }
      }
   }

   public CombatTracker func_110142_aN() {
      return this.field_94063_bt;
   }

   @Nullable
   public EntityLivingBase func_94060_bK() {
      if (this.field_94063_bt.func_94550_c() != null) {
         return this.field_94063_bt.func_94550_c();
      } else if (this.field_70717_bb != null) {
         return this.field_70717_bb;
      } else {
         return this.field_70755_b != null ? this.field_70755_b : null;
      }
   }

   public final float func_110138_aP() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
   }

   public final int func_85035_bI() {
      return this.field_70180_af.func_187225_a(field_184635_h);
   }

   public final void func_85034_r(int var1) {
      this.field_70180_af.func_187227_b(field_184635_h, ☃);
   }

   private int func_82166_i() {
      if (PotionUtil.func_205135_a(this)) {
         return 6 - (1 + PotionUtil.func_205134_b(this));
      } else {
         return this.func_70644_a(MobEffects.field_76419_f) ? 6 + (1 + this.func_70660_b(MobEffects.field_76419_f).func_76458_c()) * 2 : 6;
      }
   }

   public void func_184609_a(EnumHand var1) {
      if (!this.field_82175_bq || this.field_110158_av >= this.func_82166_i() / 2 || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.field_82175_bq = true;
         this.field_184622_au = ☃;
         if (this.field_70170_p instanceof WorldServer) {
            ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketAnimation(this, ☃ == EnumHand.MAIN_HAND ? 0 : 3));
         }
      }
   }

   @Override
   protected void func_70076_C() {
      this.func_70097_a(DamageSource.field_76380_i, 4.0F);
   }

   protected void func_82168_bl() {
      int ☃ = this.func_82166_i();
      if (this.field_82175_bq) {
         ++this.field_110158_av;
         if (this.field_110158_av >= ☃) {
            this.field_110158_av = 0;
            this.field_82175_bq = false;
         }
      } else {
         this.field_110158_av = 0;
      }

      this.field_70733_aJ = (float)this.field_110158_av / (float)☃;
   }

   public IAttributeInstance func_110148_a(IAttribute var1) {
      return this.func_110140_aT().func_111151_a(☃);
   }

   public AbstractAttributeMap func_110140_aT() {
      if (this.field_110155_d == null) {
         this.field_110155_d = new AttributeMap();
      }

      return this.field_110155_d;
   }

   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.UNDEFINED;
   }

   public ItemStack func_184614_ca() {
      return this.func_184582_a(EntityEquipmentSlot.MAINHAND);
   }

   public ItemStack func_184592_cb() {
      return this.func_184582_a(EntityEquipmentSlot.OFFHAND);
   }

   public ItemStack func_184586_b(EnumHand var1) {
      if (☃ == EnumHand.MAIN_HAND) {
         return this.func_184582_a(EntityEquipmentSlot.MAINHAND);
      } else if (☃ == EnumHand.OFF_HAND) {
         return this.func_184582_a(EntityEquipmentSlot.OFFHAND);
      } else {
         throw new IllegalArgumentException("Invalid hand " + ☃);
      }
   }

   public void func_184611_a(EnumHand var1, ItemStack var2) {
      if (☃ == EnumHand.MAIN_HAND) {
         this.func_184201_a(EntityEquipmentSlot.MAINHAND, ☃);
      } else {
         if (☃ != EnumHand.OFF_HAND) {
            throw new IllegalArgumentException("Invalid hand " + ☃);
         }

         this.func_184201_a(EntityEquipmentSlot.OFFHAND, ☃);
      }
   }

   public boolean func_190630_a(EntityEquipmentSlot var1) {
      return !this.func_184582_a(☃).func_190926_b();
   }

   @Override
   public abstract Iterable<ItemStack> func_184193_aE();

   public abstract ItemStack func_184582_a(EntityEquipmentSlot var1);

   @Override
   public abstract void func_184201_a(EntityEquipmentSlot var1, ItemStack var2);

   @Override
   public void func_70031_b(boolean var1) {
      super.func_70031_b(☃);
      IAttributeInstance ☃ = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (☃.func_111127_a(field_110156_b) != null) {
         ☃.func_111124_b(field_110157_c);
      }

      if (☃) {
         ☃.func_111121_a(field_110157_c);
      }
   }

   protected float func_70599_aP() {
      return 1.0F;
   }

   protected float func_70647_i() {
      return this.func_70631_g_()
         ? (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.5F
         : (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean func_70610_aX() {
      return this.func_110143_aJ() <= 0.0F;
   }

   public void func_110145_l(Entity var1) {
      if (!(☃ instanceof EntityBoat) && !(☃ instanceof AbstractHorse)) {
         double ☃ = ☃.field_70165_t;
         double ☃x = ☃.func_174813_aQ().field_72338_b + (double)☃.field_70131_O;
         double ☃xx = ☃.field_70161_v;
         EnumFacing ☃xxx = ☃.func_184172_bi();
         if (☃xxx != null) {
            EnumFacing ☃xxxx = ☃xxx.func_176746_e();
            int[][] ☃xxxxx = new int[][]{{0, 1}, {0, -1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}, {-1, 0}, {1, 0}, {0, 1}};
            double ☃xxxxxx = Math.floor(this.field_70165_t) + 0.5;
            double ☃xxxxxxx = Math.floor(this.field_70161_v) + 0.5;
            double ☃xxxxxxxx = this.func_174813_aQ().field_72336_d - this.func_174813_aQ().field_72340_a;
            double ☃xxxxxxxxx = this.func_174813_aQ().field_72334_f - this.func_174813_aQ().field_72339_c;
            AxisAlignedBB ☃xxxxxxxxxx = new AxisAlignedBB(
               ☃xxxxxx - ☃xxxxxxxx / 2.0,
               ☃.func_174813_aQ().field_72338_b,
               ☃xxxxxxx - ☃xxxxxxxxx / 2.0,
               ☃xxxxxx + ☃xxxxxxxx / 2.0,
               Math.floor(☃.func_174813_aQ().field_72338_b) + (double)this.field_70131_O,
               ☃xxxxxxx + ☃xxxxxxxxx / 2.0
            );

            for(int[] ☃xxxxxxxxxxx : ☃xxxxx) {
               double ☃xxxxxxxxxxxx = (double)(☃xxx.func_82601_c() * ☃xxxxxxxxxxx[0] + ☃xxxx.func_82601_c() * ☃xxxxxxxxxxx[1]);
               double ☃xxxxxxxxxxxxx = (double)(☃xxx.func_82599_e() * ☃xxxxxxxxxxx[0] + ☃xxxx.func_82599_e() * ☃xxxxxxxxxxx[1]);
               double ☃xxxxxxxxxxxxxx = ☃xxxxxx + ☃xxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxx = ☃xxxxxxx + ☃xxxxxxxxxxxxx;
               AxisAlignedBB ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxx.func_72317_d(☃xxxxxxxxxxxx, 0.0, ☃xxxxxxxxxxxxx);
               if (this.field_70170_p.func_195586_b(this, ☃xxxxxxxxxxxxxxxx)) {
                  if (this.field_70170_p.func_180495_p(new BlockPos(☃xxxxxxxxxxxxxx, this.field_70163_u, ☃xxxxxxxxxxxxxxx)).func_185896_q()) {
                     this.func_70634_a(☃xxxxxxxxxxxxxx, this.field_70163_u + 1.0, ☃xxxxxxxxxxxxxxx);
                     return;
                  }

                  BlockPos ☃xxxxxxxxxxxxxxxxx = new BlockPos(☃xxxxxxxxxxxxxx, this.field_70163_u - 1.0, ☃xxxxxxxxxxxxxxx);
                  if (this.field_70170_p.func_180495_p(☃xxxxxxxxxxxxxxxxx).func_185896_q()
                     || this.field_70170_p.func_204610_c(☃xxxxxxxxxxxxxxxxx).func_206884_a(FluidTags.field_206959_a)) {
                     ☃ = ☃xxxxxxxxxxxxxx;
                     ☃x = this.field_70163_u + 1.0;
                     ☃xx = ☃xxxxxxxxxxxxxxx;
                  }
               } else if (this.field_70170_p.func_195586_b(this, ☃xxxxxxxxxxxxxxxx.func_72317_d(0.0, 1.0, 0.0))
                  && this.field_70170_p.func_180495_p(new BlockPos(☃xxxxxxxxxxxxxx, this.field_70163_u + 1.0, ☃xxxxxxxxxxxxxxx)).func_185896_q()) {
                  ☃ = ☃xxxxxxxxxxxxxx;
                  ☃x = this.field_70163_u + 2.0;
                  ☃xx = ☃xxxxxxxxxxxxxxx;
               }
            }
         }

         this.func_70634_a(☃, ☃x, ☃xx);
      } else {
         double ☃x = (double)(this.field_70130_N / 2.0F + ☃.field_70130_N / 2.0F) + 0.4;
         float ☃;
         if (☃ instanceof EntityBoat) {
            ☃ = 0.0F;
         } else {
            ☃ = (float) (Math.PI / 2) * (float)(this.func_184591_cq() == EnumHandSide.RIGHT ? -1 : 1);
         }

         float ☃ = -MathHelper.func_76126_a(-this.field_70177_z * (float) (Math.PI / 180.0) - (float) Math.PI + ☃);
         float ☃x = -MathHelper.func_76134_b(-this.field_70177_z * (float) (Math.PI / 180.0) - (float) Math.PI + ☃);
         double ☃xx = Math.abs(☃) > Math.abs(☃x) ? ☃x / (double)Math.abs(☃) : ☃x / (double)Math.abs(☃x);
         double ☃xxx = this.field_70165_t + (double)☃ * ☃xx;
         double ☃xxxx = this.field_70161_v + (double)☃x * ☃xx;
         this.func_70107_b(☃xxx, ☃.field_70163_u + (double)☃.field_70131_O + 0.001, ☃xxxx);
         if (!this.field_70170_p.func_195586_b(this, this.func_174813_aQ().func_111270_a(☃.func_174813_aQ()))) {
            this.func_70107_b(☃xxx, ☃.field_70163_u + (double)☃.field_70131_O + 1.001, ☃xxxx);
            if (!this.field_70170_p.func_195586_b(this, this.func_174813_aQ().func_111270_a(☃.func_174813_aQ()))) {
               this.func_70107_b(☃.field_70165_t, ☃.field_70163_u + (double)this.field_70131_O + 0.001, ☃.field_70161_v);
            }
         }
      }
   }

   protected float func_175134_bD() {
      return 0.42F;
   }

   protected void func_70664_aZ() {
      this.field_70181_x = (double)this.func_175134_bD();
      if (this.func_70644_a(MobEffects.field_76430_j)) {
         this.field_70181_x += (double)((float)(this.func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F);
      }

      if (this.func_70051_ag()) {
         float ☃ = this.field_70177_z * (float) (Math.PI / 180.0);
         this.field_70159_w -= (double)(MathHelper.func_76126_a(☃) * 0.2F);
         this.field_70179_y += (double)(MathHelper.func_76134_b(☃) * 0.2F);
      }

      this.field_70160_al = true;
   }

   protected void func_180466_bG(Tag<Fluid> var1) {
      this.field_70181_x += 0.04F;
   }

   protected float func_189749_co() {
      return 0.8F;
   }

   public void func_191986_a(float var1, float var2, float var3) {
      if (this.func_70613_aW() || this.func_184186_bw()) {
         double ☃ = 0.08;
         if (this.field_70181_x <= 0.0 && this.func_70644_a(MobEffects.field_204839_B)) {
            ☃ = 0.01;
            this.field_70143_R = 0.0F;
         }

         if (!this.func_70090_H() || this instanceof EntityPlayer && ((EntityPlayer)this).field_71075_bZ.field_75100_b) {
            if (!this.func_180799_ab() || this instanceof EntityPlayer && ((EntityPlayer)this).field_71075_bZ.field_75100_b) {
               if (this.func_184613_cA()) {
                  if (this.field_70181_x > -0.5) {
                     this.field_70143_R = 1.0F;
                  }

                  Vec3d ☃ = this.func_70040_Z();
                  float ☃x = this.field_70125_A * (float) (Math.PI / 180.0);
                  double ☃xx = Math.sqrt(☃.field_72450_a * ☃.field_72450_a + ☃.field_72449_c * ☃.field_72449_c);
                  double ☃xxx = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                  double ☃xxxx = ☃.func_72433_c();
                  float ☃xxxxx = MathHelper.func_76134_b(☃x);
                  ☃xxxxx = (float)((double)☃xxxxx * (double)☃xxxxx * Math.min(1.0, ☃xxxx / 0.4));
                  this.field_70181_x += ☃ * (-1.0 + (double)☃xxxxx * 0.75);
                  if (this.field_70181_x < 0.0 && ☃xx > 0.0) {
                     double ☃xxxxxx = this.field_70181_x * -0.1 * (double)☃xxxxx;
                     this.field_70181_x += ☃xxxxxx;
                     this.field_70159_w += ☃.field_72450_a * ☃xxxxxx / ☃xx;
                     this.field_70179_y += ☃.field_72449_c * ☃xxxxxx / ☃xx;
                  }

                  if (☃x < 0.0F && ☃xx > 0.0) {
                     double ☃ = ☃xxx * (double)(-MathHelper.func_76126_a(☃x)) * 0.04;
                     this.field_70181_x += ☃ * 3.2;
                     this.field_70159_w -= ☃.field_72450_a * ☃ / ☃xx;
                     this.field_70179_y -= ☃.field_72449_c * ☃ / ☃xx;
                  }

                  if (☃xx > 0.0) {
                     this.field_70159_w += (☃.field_72450_a / ☃xx * ☃xxx - this.field_70159_w) * 0.1;
                     this.field_70179_y += (☃.field_72449_c / ☃xx * ☃xxx - this.field_70179_y) * 0.1;
                  }

                  this.field_70159_w *= 0.99F;
                  this.field_70181_x *= 0.98F;
                  this.field_70179_y *= 0.99F;
                  this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                  if (this.field_70123_F && !this.field_70170_p.field_72995_K) {
                     double ☃ = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                     double ☃x = ☃xxx - ☃;
                     float ☃xx = (float)(☃x * 10.0 - 3.0);
                     if (☃xx > 0.0F) {
                        this.func_184185_a(this.func_184588_d((int)☃xx), 1.0F, 1.0F);
                        this.func_70097_a(DamageSource.field_188406_j, ☃xx);
                     }
                  }

                  if (this.field_70122_E && !this.field_70170_p.field_72995_K) {
                     this.func_70052_a(7, false);
                  }
               } else {
                  float ☃ = 0.91F;

                  try (BlockPos.PooledMutableBlockPos ☃x = BlockPos.PooledMutableBlockPos.func_185345_c(
                        this.field_70165_t, this.func_174813_aQ().field_72338_b - 1.0, this.field_70161_v
                     )) {
                     if (this.field_70122_E) {
                        ☃ = this.field_70170_p.func_180495_p(☃x).func_177230_c().func_208618_m() * 0.91F;
                     }

                     float ☃xxx = 0.16277137F / (☃ * ☃ * ☃);
                     float ☃xx;
                     if (this.field_70122_E) {
                        ☃xx = this.func_70689_ay() * ☃xxx;
                     } else {
                        ☃xx = this.field_70747_aH;
                     }

                     this.func_191958_b(☃, ☃, ☃, ☃xx);
                     ☃ = 0.91F;
                     if (this.field_70122_E) {
                        ☃ = this.field_70170_p
                              .func_180495_p(☃x.func_189532_c(this.field_70165_t, this.func_174813_aQ().field_72338_b - 1.0, this.field_70161_v))
                              .func_177230_c()
                              .func_208618_m()
                           * 0.91F;
                     }

                     if (this.func_70617_f_()) {
                        float ☃xx = 0.15F;
                        this.field_70159_w = MathHelper.func_151237_a(this.field_70159_w, -0.15F, 0.15F);
                        this.field_70179_y = MathHelper.func_151237_a(this.field_70179_y, -0.15F, 0.15F);
                        this.field_70143_R = 0.0F;
                        if (this.field_70181_x < -0.15) {
                           this.field_70181_x = -0.15;
                        }

                        boolean ☃xx = this.func_70093_af() && this instanceof EntityPlayer;
                        if (☃xx && this.field_70181_x < 0.0) {
                           this.field_70181_x = 0.0;
                        }
                     }

                     this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                     if (this.field_70123_F && this.func_70617_f_()) {
                        this.field_70181_x = 0.2;
                     }

                     if (this.func_70644_a(MobEffects.field_188424_y)) {
                        this.field_70181_x += (0.05 * (double)(this.func_70660_b(MobEffects.field_188424_y).func_76458_c() + 1) - this.field_70181_x) * 0.2;
                        this.field_70143_R = 0.0F;
                     } else {
                        ☃x.func_189532_c(this.field_70165_t, 0.0, this.field_70161_v);
                        if (!this.field_70170_p.field_72995_K || this.field_70170_p.func_175667_e(☃x) && this.field_70170_p.func_175726_f(☃x).func_177410_o()) {
                           if (!this.func_189652_ae()) {
                              this.field_70181_x -= ☃;
                           }
                        } else if (this.field_70163_u > 0.0) {
                           this.field_70181_x = -0.1;
                        } else {
                           this.field_70181_x = 0.0;
                        }
                     }

                     this.field_70181_x *= 0.98F;
                     this.field_70159_w *= (double)☃;
                     this.field_70179_y *= (double)☃;
                  }
               }
            } else {
               double ☃ = this.field_70163_u;
               this.func_191958_b(☃, ☃, ☃, 0.02F);
               this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
               this.field_70159_w *= 0.5;
               this.field_70181_x *= 0.5;
               this.field_70179_y *= 0.5;
               if (!this.func_189652_ae()) {
                  this.field_70181_x -= ☃ / 4.0;
               }

               if (this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6F - this.field_70163_u + ☃, this.field_70179_y)) {
                  this.field_70181_x = 0.3F;
               }
            }
         } else {
            double ☃ = this.field_70163_u;
            float ☃x = this.func_70051_ag() ? 0.9F : this.func_189749_co();
            float ☃xx = 0.02F;
            float ☃xxx = (float)EnchantmentHelper.func_185294_d(this);
            if (☃xxx > 3.0F) {
               ☃xxx = 3.0F;
            }

            if (!this.field_70122_E) {
               ☃xxx *= 0.5F;
            }

            if (☃xxx > 0.0F) {
               ☃x += (0.54600006F - ☃x) * ☃xxx / 3.0F;
               ☃xx += (this.func_70689_ay() - ☃xx) * ☃xxx / 3.0F;
            }

            if (this.func_70644_a(MobEffects.field_206827_D)) {
               ☃x = 0.96F;
            }

            this.func_191958_b(☃, ☃, ☃, ☃xx);
            this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= (double)☃x;
            this.field_70181_x *= 0.8F;
            this.field_70179_y *= (double)☃x;
            if (!this.func_189652_ae() && !this.func_70051_ag()) {
               if (this.field_70181_x <= 0.0 && Math.abs(this.field_70181_x - 0.005) >= 0.003 && Math.abs(this.field_70181_x - ☃ / 16.0) < 0.003) {
                  this.field_70181_x = -0.003;
               } else {
                  this.field_70181_x -= ☃ / 16.0;
               }
            }

            if (this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6F - this.field_70163_u + ☃, this.field_70179_y)) {
               this.field_70181_x = 0.3F;
            }
         }
      }

      this.field_184618_aE = this.field_70721_aZ;
      double ☃ = this.field_70165_t - this.field_70169_q;
      double ☃x = this.field_70161_v - this.field_70166_s;
      double ☃xx = this instanceof IFlyingAnimal ? this.field_70163_u - this.field_70167_r : 0.0;
      float ☃xxx = MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx + ☃x * ☃x) * 4.0F;
      if (☃xxx > 1.0F) {
         ☃xxx = 1.0F;
      }

      this.field_70721_aZ += (☃xxx - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
   }

   public float func_70689_ay() {
      return this.field_70746_aG;
   }

   public void func_70659_e(float var1) {
      this.field_70746_aG = ☃;
   }

   public boolean func_70652_k(Entity var1) {
      this.func_130011_c(☃);
      return false;
   }

   public boolean func_70608_bn() {
      return false;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      this.func_184608_ct();
      this.func_205014_p();
      if (!this.field_70170_p.field_72995_K) {
         int ☃ = this.func_85035_bI();
         if (☃ > 0) {
            if (this.field_70720_be <= 0) {
               this.field_70720_be = 20 * (30 - ☃);
            }

            --this.field_70720_be;
            if (this.field_70720_be <= 0) {
               this.func_85034_r(☃ - 1);
            }
         }

         for(EntityEquipmentSlot ☃ : EntityEquipmentSlot.values()) {
            ItemStack ☃x;
            switch(☃.func_188453_a()) {
               case HAND:
                  ☃x = this.field_184630_bs.get(☃.func_188454_b());
                  break;
               case ARMOR:
                  ☃x = this.field_184631_bt.get(☃.func_188454_b());
                  break;
               default:
                  continue;
            }

            ItemStack ☃x = this.func_184582_a(☃);
            if (!ItemStack.func_77989_b(☃x, ☃x)) {
               ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketEntityEquipment(this.func_145782_y(), ☃, ☃x));
               if (!☃x.func_190926_b()) {
                  this.func_110140_aT().func_111148_a(☃x.func_111283_C(☃));
               }

               if (!☃x.func_190926_b()) {
                  this.func_110140_aT().func_111147_b(☃x.func_111283_C(☃));
               }

               switch(☃.func_188453_a()) {
                  case HAND:
                     this.field_184630_bs.set(☃.func_188454_b(), ☃x.func_190926_b() ? ItemStack.field_190927_a : ☃x.func_77946_l());
                     break;
                  case ARMOR:
                     this.field_184631_bt.set(☃.func_188454_b(), ☃x.func_190926_b() ? ItemStack.field_190927_a : ☃x.func_77946_l());
               }
            }
         }

         if (this.field_70173_aa % 20 == 0) {
            this.func_110142_aN().func_94549_h();
         }

         if (!this.field_184238_ar) {
            boolean ☃ = this.func_70644_a(MobEffects.field_188423_x);
            if (this.func_70083_f(6) != ☃) {
               this.func_70052_a(6, ☃);
            }
         }
      }

      this.func_70636_d();
      double ☃ = this.field_70165_t - this.field_70169_q;
      double ☃x = this.field_70161_v - this.field_70166_s;
      float ☃xx = (float)(☃ * ☃ + ☃x * ☃x);
      float ☃xxx = this.field_70761_aq;
      float ☃xxxx = 0.0F;
      this.field_70768_au = this.field_110154_aX;
      float ☃xxxxx = 0.0F;
      if (☃xx > 0.0025000002F) {
         ☃xxxxx = 1.0F;
         ☃xxxx = (float)Math.sqrt((double)☃xx) * 3.0F;
         float ☃xxxxxx = (float)MathHelper.func_181159_b(☃x, ☃) * (180.0F / (float)Math.PI) - 90.0F;
         float ☃xxxxxxx = MathHelper.func_76135_e(MathHelper.func_76142_g(this.field_70177_z) - ☃xxxxxx);
         if (95.0F < ☃xxxxxxx && ☃xxxxxxx < 265.0F) {
            ☃xxx = ☃xxxxxx - 180.0F;
         } else {
            ☃xxx = ☃xxxxxx;
         }
      }

      if (this.field_70733_aJ > 0.0F) {
         ☃xxx = this.field_70177_z;
      }

      if (!this.field_70122_E) {
         ☃xxxxx = 0.0F;
      }

      this.field_110154_aX += (☃xxxxx - this.field_110154_aX) * 0.3F;
      this.field_70170_p.field_72984_F.func_76320_a("headTurn");
      ☃xxxx = this.func_110146_f(☃xxx, ☃xxxx);
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("rangeChecks");

      while(this.field_70177_z - this.field_70126_B < -180.0F) {
         this.field_70126_B -= 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B >= 180.0F) {
         this.field_70126_B += 360.0F;
      }

      while(this.field_70761_aq - this.field_70760_ar < -180.0F) {
         this.field_70760_ar -= 360.0F;
      }

      while(this.field_70761_aq - this.field_70760_ar >= 180.0F) {
         this.field_70760_ar += 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C < -180.0F) {
         this.field_70127_C -= 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C >= 180.0F) {
         this.field_70127_C += 360.0F;
      }

      while(this.field_70759_as - this.field_70758_at < -180.0F) {
         this.field_70758_at -= 360.0F;
      }

      while(this.field_70759_as - this.field_70758_at >= 180.0F) {
         this.field_70758_at += 360.0F;
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70764_aw += ☃xxxx;
      if (this.func_184613_cA()) {
         ++this.field_184629_bo;
      } else {
         this.field_184629_bo = 0;
      }
   }

   protected float func_110146_f(float var1, float var2) {
      float ☃ = MathHelper.func_76142_g(☃ - this.field_70761_aq);
      this.field_70761_aq += ☃ * 0.3F;
      float ☃x = MathHelper.func_76142_g(this.field_70177_z - this.field_70761_aq);
      boolean ☃xx = ☃x < -90.0F || ☃x >= 90.0F;
      if (☃x < -75.0F) {
         ☃x = -75.0F;
      }

      if (☃x >= 75.0F) {
         ☃x = 75.0F;
      }

      this.field_70761_aq = this.field_70177_z - ☃x;
      if (☃x * ☃x > 2500.0F) {
         this.field_70761_aq += ☃x * 0.2F;
      }

      if (☃xx) {
         ☃ *= -1.0F;
      }

      return ☃;
   }

   public void func_70636_d() {
      if (this.field_70773_bE > 0) {
         --this.field_70773_bE;
      }

      if (this.field_70716_bi > 0 && !this.func_184186_bw()) {
         double ☃ = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / (double)this.field_70716_bi;
         double ☃x = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / (double)this.field_70716_bi;
         double ☃xx = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / (double)this.field_70716_bi;
         double ☃xxx = MathHelper.func_76138_g(this.field_184626_bk - (double)this.field_70177_z);
         this.field_70177_z = (float)((double)this.field_70177_z + ☃xxx / (double)this.field_70716_bi);
         this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70709_bj - (double)this.field_70125_A) / (double)this.field_70716_bi);
         --this.field_70716_bi;
         this.func_70107_b(☃, ☃x, ☃xx);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
      } else if (!this.func_70613_aW()) {
         this.field_70159_w *= 0.98;
         this.field_70181_x *= 0.98;
         this.field_70179_y *= 0.98;
      }

      if (this.field_208002_br > 0) {
         this.field_70759_as = (float)(
            (double)this.field_70759_as + MathHelper.func_76138_g(this.field_208001_bq - (double)this.field_70759_as) / (double)this.field_208002_br
         );
         --this.field_208002_br;
      }

      if (Math.abs(this.field_70159_w) < 0.003) {
         this.field_70159_w = 0.0;
      }

      if (Math.abs(this.field_70181_x) < 0.003) {
         this.field_70181_x = 0.0;
      }

      if (Math.abs(this.field_70179_y) < 0.003) {
         this.field_70179_y = 0.0;
      }

      this.field_70170_p.field_72984_F.func_76320_a("ai");
      if (this.func_70610_aX()) {
         this.field_70703_bu = false;
         this.field_70702_br = 0.0F;
         this.field_191988_bg = 0.0F;
         this.field_70704_bt = 0.0F;
      } else if (this.func_70613_aW()) {
         this.field_70170_p.field_72984_F.func_76320_a("newAi");
         this.func_70626_be();
         this.field_70170_p.field_72984_F.func_76319_b();
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("jump");
      if (this.field_70703_bu) {
         if (!(this.field_211517_W > 0.0) || this.field_70122_E && !(this.field_211517_W > 0.4)) {
            if (this.func_180799_ab()) {
               this.func_180466_bG(FluidTags.field_206960_b);
            } else if ((this.field_70122_E || this.field_211517_W > 0.0 && this.field_211517_W <= 0.4) && this.field_70773_bE == 0) {
               this.func_70664_aZ();
               this.field_70773_bE = 10;
            }
         } else {
            this.func_180466_bG(FluidTags.field_206959_a);
         }
      } else {
         this.field_70773_bE = 0;
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("travel");
      this.field_70702_br *= 0.98F;
      this.field_191988_bg *= 0.98F;
      this.field_70704_bt *= 0.9F;
      this.func_184616_r();
      AxisAlignedBB ☃ = this.func_174813_aQ();
      this.func_191986_a(this.field_70702_br, this.field_70701_bs, this.field_191988_bg);
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("push");
      if (this.field_204807_bs > 0) {
         --this.field_204807_bs;
         this.func_204801_a(☃, this.func_174813_aQ());
      }

      this.func_85033_bc();
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   private void func_184616_r() {
      boolean ☃ = this.func_70083_f(7);
      if (☃ && !this.field_70122_E && !this.func_184218_aH()) {
         ItemStack ☃x = this.func_184582_a(EntityEquipmentSlot.CHEST);
         if (☃x.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(☃x)) {
            ☃ = true;
            if (!this.field_70170_p.field_72995_K && (this.field_184629_bo + 1) % 20 == 0) {
               ☃x.func_77972_a(1, this);
            }
         } else {
            ☃ = false;
         }
      } else {
         ☃ = false;
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70052_a(7, ☃);
      }
   }

   protected void func_70626_be() {
   }

   protected void func_85033_bc() {
      List<Entity> ☃ = this.field_70170_p.func_175674_a(this, this.func_174813_aQ(), EntitySelectors.func_200823_a(this));
      if (!☃.isEmpty()) {
         int ☃x = this.field_70170_p.func_82736_K().func_180263_c("maxEntityCramming");
         if (☃x > 0 && ☃.size() > ☃x - 1 && this.field_70146_Z.nextInt(4) == 0) {
            int ☃xx = 0;

            for(int ☃xxx = 0; ☃xxx < ☃.size(); ++☃xxx) {
               if (!((Entity)☃.get(☃xxx)).func_184218_aH()) {
                  ++☃xx;
               }
            }

            if (☃xx > ☃x - 1) {
               this.func_70097_a(DamageSource.field_191291_g, 6.0F);
            }
         }

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            Entity ☃xx = (Entity)☃.get(☃x);
            this.func_82167_n(☃xx);
         }
      }
   }

   protected void func_204801_a(AxisAlignedBB var1, AxisAlignedBB var2) {
      AxisAlignedBB ☃ = ☃.func_111270_a(☃);
      List<Entity> ☃x = this.field_70170_p.func_72839_b(this, ☃);
      if (!☃x.isEmpty()) {
         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            Entity ☃xxx = (Entity)☃x.get(☃xx);
            if (☃xxx instanceof EntityLivingBase) {
               this.func_204804_d((EntityLivingBase)☃xxx);
               this.field_204807_bs = 0;
               this.field_70159_w *= -0.2;
               this.field_70181_x *= -0.2;
               this.field_70179_y *= -0.2;
               break;
            }
         }
      } else if (this.field_70123_F) {
         this.field_204807_bs = 0;
      }

      if (!this.field_70170_p.field_72995_K && this.field_204807_bs <= 0) {
         this.func_204802_c(4, false);
      }
   }

   protected void func_82167_n(Entity var1) {
      ☃.func_70108_f(this);
   }

   protected void func_204804_d(EntityLivingBase var1) {
   }

   public void func_204803_n(int var1) {
      this.field_204807_bs = ☃;
      if (!this.field_70170_p.field_72995_K) {
         this.func_204802_c(4, true);
      }
   }

   public boolean func_204805_cN() {
      return (this.field_70180_af.func_187225_a(field_184621_as) & 4) != 0;
   }

   @Override
   public void func_184210_p() {
      Entity ☃ = this.func_184187_bx();
      super.func_184210_p();
      if (☃ != null && ☃ != this.func_184187_bx() && !this.field_70170_p.field_72995_K) {
         this.func_110145_l(☃);
      }
   }

   @Override
   public void func_70098_U() {
      super.func_70098_U();
      this.field_70768_au = this.field_110154_aX;
      this.field_110154_aX = 0.0F;
      this.field_70143_R = 0.0F;
   }

   public void func_70637_d(boolean var1) {
      this.field_70703_bu = ☃;
   }

   public void func_71001_a(Entity var1, int var2) {
      if (!☃.field_70128_L && !this.field_70170_p.field_72995_K) {
         EntityTracker ☃ = ((WorldServer)this.field_70170_p).func_73039_n();
         if (☃ instanceof EntityItem || ☃ instanceof EntityArrow || ☃ instanceof EntityXPOrb) {
            ☃.func_151247_a(☃, new SPacketCollectItem(☃.func_145782_y(), this.func_145782_y(), ☃));
         }
      }
   }

   public boolean func_70685_l(Entity var1) {
      return this.field_70170_p
            .func_200259_a(
               new Vec3d(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v),
               new Vec3d(☃.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e(), ☃.field_70161_v),
               RayTraceFluidMode.NEVER,
               true,
               false
            )
         == null;
   }

   @Override
   public float func_195046_g(float var1) {
      return ☃ == 1.0F ? this.field_70759_as : this.field_70758_at + (this.field_70759_as - this.field_70758_at) * ☃;
   }

   public boolean func_70613_aW() {
      return !this.field_70170_p.field_72995_K;
   }

   @Override
   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   @Override
   public boolean func_70104_M() {
      return this.func_70089_S() && !this.func_70617_f_();
   }

   @Override
   protected void func_70018_K() {
      this.field_70133_I = this.field_70146_Z.nextDouble() >= this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e();
   }

   @Override
   public float func_70079_am() {
      return this.field_70759_as;
   }

   @Override
   public void func_70034_d(float var1) {
      this.field_70759_as = ☃;
   }

   @Override
   public void func_181013_g(float var1) {
      this.field_70761_aq = ☃;
   }

   public float func_110139_bj() {
      return this.field_110151_bq;
   }

   public void func_110149_m(float var1) {
      if (☃ < 0.0F) {
         ☃ = 0.0F;
      }

      this.field_110151_bq = ☃;
   }

   public void func_152111_bt() {
   }

   public void func_152112_bu() {
   }

   protected void func_175136_bO() {
      this.field_70752_e = true;
   }

   public abstract EnumHandSide func_184591_cq();

   public boolean func_184587_cr() {
      return (this.field_70180_af.func_187225_a(field_184621_as) & 1) > 0;
   }

   public EnumHand func_184600_cs() {
      return (this.field_70180_af.func_187225_a(field_184621_as) & 2) > 0 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
   }

   protected void func_184608_ct() {
      if (this.func_184587_cr()) {
         if (this.func_184586_b(this.func_184600_cs()) == this.field_184627_bm) {
            if (this.func_184605_cv() <= 25 && this.func_184605_cv() % 4 == 0) {
               this.func_184584_a(this.field_184627_bm, 5);
            }

            if (--this.field_184628_bn == 0 && !this.field_70170_p.field_72995_K) {
               this.func_71036_o();
            }
         } else {
            this.func_184602_cy();
         }
      }
   }

   private void func_205014_p() {
      this.field_205018_bM = this.field_205017_bL;
      if (this.func_203007_ba()) {
         this.field_205017_bL = Math.min(1.0F, this.field_205017_bL + 0.09F);
      } else {
         this.field_205017_bL = Math.max(0.0F, this.field_205017_bL - 0.09F);
      }
   }

   protected void func_204802_c(int var1, boolean var2) {
      int ☃ = this.field_70180_af.func_187225_a(field_184621_as);
      if (☃) {
         ☃ |= ☃;
      } else {
         ☃ &= ~☃;
      }

      this.field_70180_af.func_187227_b(field_184621_as, (byte)☃);
   }

   public void func_184598_c(EnumHand var1) {
      ItemStack ☃ = this.func_184586_b(☃);
      if (!☃.func_190926_b() && !this.func_184587_cr()) {
         this.field_184627_bm = ☃;
         this.field_184628_bn = ☃.func_77988_m();
         if (!this.field_70170_p.field_72995_K) {
            this.func_204802_c(1, true);
            this.func_204802_c(2, ☃ == EnumHand.OFF_HAND);
         }
      }
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      super.func_184206_a(☃);
      if (field_184621_as.equals(☃) && this.field_70170_p.field_72995_K) {
         if (this.func_184587_cr() && this.field_184627_bm.func_190926_b()) {
            this.field_184627_bm = this.func_184586_b(this.func_184600_cs());
            if (!this.field_184627_bm.func_190926_b()) {
               this.field_184628_bn = this.field_184627_bm.func_77988_m();
            }
         } else if (!this.func_184587_cr() && !this.field_184627_bm.func_190926_b()) {
            this.field_184627_bm = ItemStack.field_190927_a;
            this.field_184628_bn = 0;
         }
      }
   }

   @Override
   public void func_200602_a(EntityAnchorArgument.Type var1, Vec3d var2) {
      super.func_200602_a(☃, ☃);
      this.field_70758_at = this.field_70759_as;
      this.field_70761_aq = this.field_70759_as;
      this.field_70760_ar = this.field_70761_aq;
   }

   protected void func_184584_a(ItemStack var1, int var2) {
      if (!☃.func_190926_b() && this.func_184587_cr()) {
         if (☃.func_77975_n() == EnumAction.DRINK) {
            this.func_184185_a(SoundEvents.field_187664_bz, 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
         }

         if (☃.func_77975_n() == EnumAction.EAT) {
            this.func_195062_a(☃, ☃);
            this.func_184185_a(
               SoundEvents.field_187537_bA,
               0.5F + 0.5F * (float)this.field_70146_Z.nextInt(2),
               (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F
            );
         }
      }
   }

   private void func_195062_a(ItemStack var1, int var2) {
      for(int ☃ = 0; ☃ < ☃; ++☃) {
         Vec3d ☃x = new Vec3d(((double)this.field_70146_Z.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         ☃x = ☃x.func_178789_a(-this.field_70125_A * (float) (Math.PI / 180.0));
         ☃x = ☃x.func_178785_b(-this.field_70177_z * (float) (Math.PI / 180.0));
         double ☃xx = (double)(-this.field_70146_Z.nextFloat()) * 0.6 - 0.3;
         Vec3d ☃xxx = new Vec3d(((double)this.field_70146_Z.nextFloat() - 0.5) * 0.3, ☃xx, 0.6);
         ☃xxx = ☃xxx.func_178789_a(-this.field_70125_A * (float) (Math.PI / 180.0));
         ☃xxx = ☃xxx.func_178785_b(-this.field_70177_z * (float) (Math.PI / 180.0));
         ☃xxx = ☃xxx.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
         this.field_70170_p
            .func_195594_a(
               new ItemParticleData(Particles.field_197591_B, ☃),
               ☃xxx.field_72450_a,
               ☃xxx.field_72448_b,
               ☃xxx.field_72449_c,
               ☃x.field_72450_a,
               ☃x.field_72448_b + 0.05,
               ☃x.field_72449_c
            );
      }
   }

   protected void func_71036_o() {
      if (!this.field_184627_bm.func_190926_b() && this.func_184587_cr()) {
         this.func_184584_a(this.field_184627_bm, 16);
         this.func_184611_a(this.func_184600_cs(), this.field_184627_bm.func_77950_b(this.field_70170_p, this));
         this.func_184602_cy();
      }
   }

   public ItemStack func_184607_cu() {
      return this.field_184627_bm;
   }

   public int func_184605_cv() {
      return this.field_184628_bn;
   }

   public int func_184612_cw() {
      return this.func_184587_cr() ? this.field_184627_bm.func_77988_m() - this.func_184605_cv() : 0;
   }

   public void func_184597_cx() {
      if (!this.field_184627_bm.func_190926_b()) {
         this.field_184627_bm.func_77974_b(this.field_70170_p, this, this.func_184605_cv());
      }

      this.func_184602_cy();
   }

   public void func_184602_cy() {
      if (!this.field_70170_p.field_72995_K) {
         this.func_204802_c(1, false);
      }

      this.field_184627_bm = ItemStack.field_190927_a;
      this.field_184628_bn = 0;
   }

   public boolean func_184585_cz() {
      if (this.func_184587_cr() && !this.field_184627_bm.func_190926_b()) {
         Item ☃ = this.field_184627_bm.func_77973_b();
         if (☃.func_77661_b(this.field_184627_bm) != EnumAction.BLOCK) {
            return false;
         } else {
            return ☃.func_77626_a(this.field_184627_bm) - this.field_184628_bn >= 5;
         }
      } else {
         return false;
      }
   }

   public boolean func_184613_cA() {
      return this.func_70083_f(7);
   }

   public boolean func_184595_k(double var1, double var3, double var5) {
      double ☃ = this.field_70165_t;
      double ☃x = this.field_70163_u;
      double ☃xx = this.field_70161_v;
      this.field_70165_t = ☃;
      this.field_70163_u = ☃;
      this.field_70161_v = ☃;
      boolean ☃xxx = false;
      BlockPos ☃xxxx = new BlockPos(this);
      IWorld ☃xxxxx = this.field_70170_p;
      Random ☃xxxxxx = this.func_70681_au();
      if (☃xxxxx.func_175667_e(☃xxxx)) {
         boolean ☃xxxxxxx = false;

         while(!☃xxxxxxx && ☃xxxx.func_177956_o() > 0) {
            BlockPos ☃xxxxxxxx = ☃xxxx.func_177977_b();
            IBlockState ☃xxxxxxxxx = ☃xxxxx.func_180495_p(☃xxxxxxxx);
            if (☃xxxxxxxxx.func_185904_a().func_76230_c()) {
               ☃xxxxxxx = true;
            } else {
               --this.field_70163_u;
               ☃xxxx = ☃xxxxxxxx;
            }
         }

         if (☃xxxxxxx) {
            this.func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            if (☃xxxxx.func_195586_b(this, this.func_174813_aQ()) && !☃xxxxx.func_72953_d(this.func_174813_aQ())) {
               ☃xxx = true;
            }
         }
      }

      if (!☃xxx) {
         this.func_70634_a(☃, ☃x, ☃xx);
         return false;
      } else {
         int ☃ = 128;

         for(int ☃x = 0; ☃x < 128; ++☃x) {
            double ☃xx = (double)☃x / 127.0;
            float ☃xxx = (☃xxxxxx.nextFloat() - 0.5F) * 0.2F;
            float ☃xxxx = (☃xxxxxx.nextFloat() - 0.5F) * 0.2F;
            float ☃xxxxx = (☃xxxxxx.nextFloat() - 0.5F) * 0.2F;
            double ☃xxxxxx = ☃ + (this.field_70165_t - ☃) * ☃xx + (☃xxxxxx.nextDouble() - 0.5) * (double)this.field_70130_N * 2.0;
            double ☃xxxxxxx = ☃x + (this.field_70163_u - ☃x) * ☃xx + ☃xxxxxx.nextDouble() * (double)this.field_70131_O;
            double ☃xxxxxxxx = ☃xx + (this.field_70161_v - ☃xx) * ☃xx + (☃xxxxxx.nextDouble() - 0.5) * (double)this.field_70130_N * 2.0;
            ☃xxxxx.func_195594_a(Particles.field_197599_J, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, (double)☃xxx, (double)☃xxxx, (double)☃xxxxx);
         }

         if (this instanceof EntityCreature) {
            ((EntityCreature)this).func_70661_as().func_75499_g();
         }

         return true;
      }
   }

   public boolean func_184603_cC() {
      return true;
   }

   public boolean func_190631_cK() {
      return true;
   }
}
