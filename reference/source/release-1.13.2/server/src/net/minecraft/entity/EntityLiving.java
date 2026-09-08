package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockAbstractSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public abstract class EntityLiving extends EntityLivingBase {
   private static final DataParameter<Byte> field_184654_a = EntityDataManager.func_187226_a(EntityLiving.class, DataSerializers.field_187191_a);
   public int field_70757_a;
   protected int field_70728_aV;
   protected EntityLookHelper field_70749_g;
   protected EntityMoveHelper field_70765_h;
   protected EntityJumpHelper field_70767_i;
   private final EntityBodyHelper field_70762_j;
   protected PathNavigate field_70699_by;
   protected final EntityAITasks field_70714_bg;
   protected final EntityAITasks field_70715_bh;
   private EntityLivingBase field_70696_bz;
   private final EntitySenses field_70723_bA;
   private final NonNullList<ItemStack> field_184656_bv = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
   protected float[] field_82174_bp = new float[2];
   private final NonNullList<ItemStack> field_184657_bw = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
   protected float[] field_184655_bs = new float[4];
   private boolean field_82172_bs;
   private boolean field_82179_bU;
   private final Map<PathNodeType, Float> field_184658_bz = Maps.newEnumMap(PathNodeType.class);
   private ResourceLocation field_184659_bA;
   private long field_184653_bB;
   private boolean field_110169_bv;
   private Entity field_110168_bw;
   private NBTTagCompound field_110170_bx;

   protected EntityLiving(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_70714_bg = new EntityAITasks(☃ != null && ☃.field_72984_F != null ? ☃.field_72984_F : null);
      this.field_70715_bh = new EntityAITasks(☃ != null && ☃.field_72984_F != null ? ☃.field_72984_F : null);
      this.field_70749_g = new EntityLookHelper(this);
      this.field_70765_h = new EntityMoveHelper(this);
      this.field_70767_i = new EntityJumpHelper(this);
      this.field_70762_j = this.func_184650_s();
      this.field_70699_by = this.func_175447_b(☃);
      this.field_70723_bA = new EntitySenses(this);
      Arrays.fill(this.field_184655_bs, 0.085F);
      Arrays.fill(this.field_82174_bp, 0.085F);
      if (☃ != null && !☃.field_72995_K) {
         this.func_184651_r();
      }
   }

   protected void func_184651_r() {
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0);
   }

   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateGround(this, ☃);
   }

   public float func_184643_a(PathNodeType var1) {
      Float ☃ = (Float)this.field_184658_bz.get(☃);
      return ☃ == null ? ☃.func_186289_a() : ☃;
   }

   public void func_184644_a(PathNodeType var1, float var2) {
      this.field_184658_bz.put(☃, ☃);
   }

   protected EntityBodyHelper func_184650_s() {
      return new EntityBodyHelper(this);
   }

   public EntityLookHelper func_70671_ap() {
      return this.field_70749_g;
   }

   public EntityMoveHelper func_70605_aq() {
      return this.field_70765_h;
   }

   public EntityJumpHelper func_70683_ar() {
      return this.field_70767_i;
   }

   public PathNavigate func_70661_as() {
      return this.field_70699_by;
   }

   public EntitySenses func_70635_at() {
      return this.field_70723_bA;
   }

   @Nullable
   public EntityLivingBase func_70638_az() {
      return this.field_70696_bz;
   }

   public void func_70624_b(@Nullable EntityLivingBase var1) {
      this.field_70696_bz = ☃;
   }

   public boolean func_70686_a(Class<? extends EntityLivingBase> var1) {
      return ☃ != EntityGhast.class;
   }

   public void func_70615_aA() {
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184654_a, (byte)0);
   }

   public int func_70627_aG() {
      return 80;
   }

   public void func_70642_aH() {
      SoundEvent ☃ = this.func_184639_G();
      if (☃ != null) {
         this.func_184185_a(☃, this.func_70599_aP(), this.func_70647_i());
      }
   }

   @Override
   public void func_70030_z() {
      super.func_70030_z();
      this.field_70170_p.field_72984_F.func_76320_a("mobBaseTick");
      if (this.func_70089_S() && this.field_70146_Z.nextInt(1000) < this.field_70757_a++) {
         this.func_175456_n();
         this.func_70642_aH();
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   @Override
   protected void func_184581_c(DamageSource var1) {
      this.func_175456_n();
      super.func_184581_c(☃);
   }

   private void func_175456_n() {
      this.field_70757_a = -this.func_70627_aG();
   }

   @Override
   protected int func_70693_a(EntityPlayer var1) {
      if (this.field_70728_aV > 0) {
         int ☃ = this.field_70728_aV;

         for(int ☃x = 0; ☃x < this.field_184657_bw.size(); ++☃x) {
            if (!this.field_184657_bw.get(☃x).func_190926_b() && this.field_184655_bs[☃x] <= 1.0F) {
               ☃ += 1 + this.field_70146_Z.nextInt(3);
            }
         }

         for(int ☃x = 0; ☃x < this.field_184656_bv.size(); ++☃x) {
            if (!this.field_184656_bv.get(☃x).func_190926_b() && this.field_82174_bp[☃x] <= 1.0F) {
               ☃ += 1 + this.field_70146_Z.nextInt(3);
            }
         }

         return ☃;
      } else {
         return this.field_70728_aV;
      }
   }

   public void func_70656_aK() {
      if (this.field_70170_p.field_72995_K) {
         for(int ☃ = 0; ☃ < 20; ++☃) {
            double ☃x = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃xx = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃xxx = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃xxxx = 10.0;
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197598_I,
                  this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - ☃x * 10.0,
                  this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O) - ☃xx * 10.0,
                  this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - ☃xxx * 10.0,
                  ☃x,
                  ☃xx,
                  ☃xxx
               );
         }
      } else {
         this.field_70170_p.func_72960_a(this, (byte)20);
      }
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         this.func_110159_bB();
         if (this.field_70173_aa % 5 == 0) {
            boolean ☃ = !(this.func_184179_bs() instanceof EntityLiving);
            boolean ☃x = !(this.func_184187_bx() instanceof EntityBoat);
            this.field_70714_bg.func_188527_a(1, ☃);
            this.field_70714_bg.func_188527_a(4, ☃ && ☃x);
            this.field_70714_bg.func_188527_a(2, ☃);
         }
      }
   }

   @Override
   protected float func_110146_f(float var1, float var2) {
      this.field_70762_j.func_75664_a();
      return ☃;
   }

   @Nullable
   protected SoundEvent func_184639_G() {
      return null;
   }

   @Nullable
   protected Item func_146068_u() {
      return null;
   }

   @Override
   protected void func_70628_a(boolean var1, int var2) {
      Item ☃ = this.func_146068_u();
      if (☃ != null) {
         int ☃x = this.field_70146_Z.nextInt(3);
         if (☃ > 0) {
            ☃x += this.field_70146_Z.nextInt(☃ + 1);
         }

         for(int ☃x = 0; ☃x < ☃x; ++☃x) {
            this.func_199703_a(☃);
         }
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("CanPickUpLoot", this.func_98052_bS());
      ☃.func_74757_a("PersistenceRequired", this.field_82179_bU);
      NBTTagList ☃ = new NBTTagList();

      for(ItemStack ☃x : this.field_184657_bw) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         if (!☃x.func_190926_b()) {
            ☃x.func_77955_b(☃xx);
         }

         ☃.add((INBTBase)☃xx);
      }

      ☃.func_74782_a("ArmorItems", ☃);
      NBTTagList ☃x = new NBTTagList();

      for(ItemStack ☃xx : this.field_184656_bv) {
         NBTTagCompound ☃xxx = new NBTTagCompound();
         if (!☃xx.func_190926_b()) {
            ☃xx.func_77955_b(☃xxx);
         }

         ☃x.add((INBTBase)☃xxx);
      }

      ☃.func_74782_a("HandItems", ☃x);
      NBTTagList ☃xx = new NBTTagList();

      for(float ☃xxx : this.field_184655_bs) {
         ☃xx.add((INBTBase)(new NBTTagFloat(☃xxx)));
      }

      ☃.func_74782_a("ArmorDropChances", ☃xx);
      NBTTagList ☃xxx = new NBTTagList();

      for(float ☃xxxx : this.field_82174_bp) {
         ☃xxx.add((INBTBase)(new NBTTagFloat(☃xxxx)));
      }

      ☃.func_74782_a("HandDropChances", ☃xxx);
      ☃.func_74757_a("Leashed", this.field_110169_bv);
      if (this.field_110168_bw != null) {
         NBTTagCompound ☃xxxx = new NBTTagCompound();
         if (this.field_110168_bw instanceof EntityLivingBase) {
            UUID ☃xxxxx = this.field_110168_bw.func_110124_au();
            ☃xxxx.func_186854_a("UUID", ☃xxxxx);
         } else if (this.field_110168_bw instanceof EntityHanging) {
            BlockPos ☃xxxx = ((EntityHanging)this.field_110168_bw).func_174857_n();
            ☃xxxx.func_74768_a("X", ☃xxxx.func_177958_n());
            ☃xxxx.func_74768_a("Y", ☃xxxx.func_177956_o());
            ☃xxxx.func_74768_a("Z", ☃xxxx.func_177952_p());
         }

         ☃.func_74782_a("Leash", ☃xxxx);
      }

      ☃.func_74757_a("LeftHanded", this.func_184638_cS());
      if (this.field_184659_bA != null) {
         ☃.func_74778_a("DeathLootTable", this.field_184659_bA.toString());
         if (this.field_184653_bB != 0L) {
            ☃.func_74772_a("DeathLootTableSeed", this.field_184653_bB);
         }
      }

      if (this.func_175446_cd()) {
         ☃.func_74757_a("NoAI", this.func_175446_cd());
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("CanPickUpLoot", 1)) {
         this.func_98053_h(☃.func_74767_n("CanPickUpLoot"));
      }

      this.field_82179_bU = ☃.func_74767_n("PersistenceRequired");
      if (☃.func_150297_b("ArmorItems", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("ArmorItems", 10);

         for(int ☃x = 0; ☃x < this.field_184657_bw.size(); ++☃x) {
            this.field_184657_bw.set(☃x, ItemStack.func_199557_a(☃.func_150305_b(☃x)));
         }
      }

      if (☃.func_150297_b("HandItems", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("HandItems", 10);

         for(int ☃x = 0; ☃x < this.field_184656_bv.size(); ++☃x) {
            this.field_184656_bv.set(☃x, ItemStack.func_199557_a(☃.func_150305_b(☃x)));
         }
      }

      if (☃.func_150297_b("ArmorDropChances", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("ArmorDropChances", 5);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_184655_bs[☃x] = ☃.func_150308_e(☃x);
         }
      }

      if (☃.func_150297_b("HandDropChances", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("HandDropChances", 5);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_82174_bp[☃x] = ☃.func_150308_e(☃x);
         }
      }

      this.field_110169_bv = ☃.func_74767_n("Leashed");
      if (this.field_110169_bv && ☃.func_150297_b("Leash", 10)) {
         this.field_110170_bx = ☃.func_74775_l("Leash");
      }

      this.func_184641_n(☃.func_74767_n("LeftHanded"));
      if (☃.func_150297_b("DeathLootTable", 8)) {
         this.field_184659_bA = new ResourceLocation(☃.func_74779_i("DeathLootTable"));
         this.field_184653_bB = ☃.func_74763_f("DeathLootTableSeed");
      }

      this.func_94061_f(☃.func_74767_n("NoAI"));
   }

   @Nullable
   protected ResourceLocation func_184647_J() {
      return null;
   }

   @Override
   protected void func_184610_a(boolean var1, int var2, DamageSource var3) {
      ResourceLocation ☃ = this.field_184659_bA;
      if (☃ == null) {
         ☃ = this.func_184647_J();
      }

      if (☃ != null) {
         LootTable ☃ = this.field_70170_p.func_73046_m().func_200249_aQ().func_186521_a(☃);
         this.field_184659_bA = null;
         LootContext.Builder ☃x = new LootContext.Builder((WorldServer)this.field_70170_p)
            .func_186472_a(this)
            .func_186473_a(☃)
            .func_204313_a(new BlockPos(this));
         if (☃ && this.field_70717_bb != null) {
            ☃x = ☃x.func_186470_a(this.field_70717_bb).func_186469_a(this.field_70717_bb.func_184817_da());
         }

         for(ItemStack ☃ : ☃.func_186462_a(this.field_184653_bB == 0L ? this.field_70146_Z : new Random(this.field_184653_bB), ☃x.func_186471_a())) {
            this.func_199701_a_(☃);
         }

         this.func_82160_b(☃, ☃);
      } else {
         super.func_184610_a(☃, ☃, ☃);
      }
   }

   public void func_191989_p(float var1) {
      this.field_191988_bg = ☃;
   }

   public void func_70657_f(float var1) {
      this.field_70701_bs = ☃;
   }

   public void func_184646_p(float var1) {
      this.field_70702_br = ☃;
   }

   @Override
   public void func_70659_e(float var1) {
      super.func_70659_e(☃);
      this.func_191989_p(☃);
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      this.field_70170_p.field_72984_F.func_76320_a("looting");
      if (!this.field_70170_p.field_72995_K && this.func_98052_bS() && !this.field_70729_aU && this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
         for(EntityItem ☃ : this.field_70170_p.func_72872_a(EntityItem.class, this.func_174813_aQ().func_72314_b(1.0, 0.0, 1.0))) {
            if (!☃.field_70128_L && !☃.func_92059_d().func_190926_b() && !☃.func_174874_s()) {
               this.func_175445_a(☃);
            }
         }
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_175445_a(EntityItem var1) {
      ItemStack ☃ = ☃.func_92059_d();
      EntityEquipmentSlot ☃x = func_184640_d(☃);
      ItemStack ☃xx = this.func_184582_a(☃x);
      boolean ☃xxx = this.func_208003_a(☃, ☃xx, ☃x);
      if (☃xxx && this.func_175448_a(☃)) {
         double ☃xxxx = (double)this.func_205712_c(☃x);
         if (!☃xx.func_190926_b() && (double)(this.field_70146_Z.nextFloat() - 0.1F) < ☃xxxx) {
            this.func_199701_a_(☃xx);
         }

         this.func_184201_a(☃x, ☃);
         switch(☃x.func_188453_a()) {
            case HAND:
               this.field_82174_bp[☃x.func_188454_b()] = 2.0F;
               break;
            case ARMOR:
               this.field_184655_bs[☃x.func_188454_b()] = 2.0F;
         }

         this.field_82179_bU = true;
         this.func_71001_a(☃, ☃.func_190916_E());
         ☃.func_70106_y();
      }
   }

   protected boolean func_208003_a(ItemStack var1, ItemStack var2, EntityEquipmentSlot var3) {
      boolean ☃ = true;
      if (!☃.func_190926_b()) {
         if (☃.func_188453_a() == EntityEquipmentSlot.Type.HAND) {
            if (☃.func_77973_b() instanceof ItemSword && !(☃.func_77973_b() instanceof ItemSword)) {
               ☃ = true;
            } else if (☃.func_77973_b() instanceof ItemSword && ☃.func_77973_b() instanceof ItemSword) {
               ItemSword ☃x = (ItemSword)☃.func_77973_b();
               ItemSword ☃xx = (ItemSword)☃.func_77973_b();
               if (☃x.func_200894_d() == ☃xx.func_200894_d()) {
                  ☃ = ☃.func_77952_i() < ☃.func_77952_i() || ☃.func_77942_o() && !☃.func_77942_o();
               } else {
                  ☃ = ☃x.func_200894_d() > ☃xx.func_200894_d();
               }
            } else if (☃.func_77973_b() instanceof ItemBow && ☃.func_77973_b() instanceof ItemBow) {
               ☃ = ☃.func_77942_o() && !☃.func_77942_o();
            } else {
               ☃ = false;
            }
         } else if (☃.func_77973_b() instanceof ItemArmor && !(☃.func_77973_b() instanceof ItemArmor)) {
            ☃ = true;
         } else if (☃.func_77973_b() instanceof ItemArmor && ☃.func_77973_b() instanceof ItemArmor && !EnchantmentHelper.func_190938_b(☃)) {
            ItemArmor ☃x = (ItemArmor)☃.func_77973_b();
            ItemArmor ☃xx = (ItemArmor)☃.func_77973_b();
            if (☃x.func_200881_e() == ☃xx.func_200881_e()) {
               ☃ = ☃.func_77952_i() < ☃.func_77952_i() || ☃.func_77942_o() && !☃.func_77942_o();
            } else {
               ☃ = ☃x.func_200881_e() > ☃xx.func_200881_e();
            }
         } else {
            ☃ = false;
         }
      }

      return ☃;
   }

   protected boolean func_175448_a(ItemStack var1) {
      return true;
   }

   public boolean func_70692_ba() {
      return true;
   }

   protected void func_70623_bb() {
      if (this.field_82179_bU) {
         this.field_70708_bq = 0;
      } else {
         Entity ☃ = this.field_70170_p.func_72890_a(this, -1.0);
         if (☃ != null) {
            double ☃x = ☃.field_70165_t - this.field_70165_t;
            double ☃xx = ☃.field_70163_u - this.field_70163_u;
            double ☃xxx = ☃.field_70161_v - this.field_70161_v;
            double ☃xxxx = ☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx;
            if (this.func_70692_ba() && ☃xxxx > 16384.0) {
               this.func_70106_y();
            }

            if (this.field_70708_bq > 600 && this.field_70146_Z.nextInt(800) == 0 && ☃xxxx > 1024.0 && this.func_70692_ba()) {
               this.func_70106_y();
            } else if (☃xxxx < 1024.0) {
               this.field_70708_bq = 0;
            }
         }
      }
   }

   @Override
   protected final void func_70626_be() {
      ++this.field_70708_bq;
      this.field_70170_p.field_72984_F.func_76320_a("checkDespawn");
      this.func_70623_bb();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("sensing");
      this.field_70723_bA.func_75523_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("targetSelector");
      this.field_70715_bh.func_75774_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("goalSelector");
      this.field_70714_bg.func_75774_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("navigation");
      this.field_70699_by.func_75501_e();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("mob tick");
      this.func_70619_bc();
      this.field_70170_p.field_72984_F.func_76319_b();
      if (this.func_184218_aH() && this.func_184187_bx() instanceof EntityLiving) {
         EntityLiving ☃ = (EntityLiving)this.func_184187_bx();
         ☃.func_70661_as().func_75484_a(this.func_70661_as().func_75505_d(), 1.5);
         ☃.func_70605_aq().func_188487_a(this.func_70605_aq());
      }

      this.field_70170_p.field_72984_F.func_76320_a("controls");
      this.field_70170_p.field_72984_F.func_76320_a("move");
      this.field_70765_h.func_75641_c();
      this.field_70170_p.field_72984_F.func_76318_c("look");
      this.field_70749_g.func_75649_a();
      this.field_70170_p.field_72984_F.func_76318_c("jump");
      this.field_70767_i.func_75661_b();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_70619_bc() {
   }

   public int func_70646_bf() {
      return 40;
   }

   public int func_184649_cE() {
      return 10;
   }

   public void func_70625_a(Entity var1, float var2, float var3) {
      double ☃x = ☃.field_70165_t - this.field_70165_t;
      double ☃xx = ☃.field_70161_v - this.field_70161_v;
      double ☃;
      if (☃ instanceof EntityLivingBase) {
         EntityLivingBase ☃xxx = (EntityLivingBase)☃;
         ☃ = ☃xxx.field_70163_u + (double)☃xxx.func_70047_e() - (this.field_70163_u + (double)this.func_70047_e());
      } else {
         ☃ = (☃.func_174813_aQ().field_72338_b + ☃.func_174813_aQ().field_72337_e) / 2.0 - (this.field_70163_u + (double)this.func_70047_e());
      }

      double ☃ = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xx * ☃xx);
      float ☃x = (float)(MathHelper.func_181159_b(☃xx, ☃x) * 180.0F / (float)Math.PI) - 90.0F;
      float ☃xx = (float)(-(MathHelper.func_181159_b(☃, ☃) * 180.0F / (float)Math.PI));
      this.field_70125_A = this.func_70663_b(this.field_70125_A, ☃xx, ☃);
      this.field_70177_z = this.func_70663_b(this.field_70177_z, ☃x, ☃);
   }

   private float func_70663_b(float var1, float var2, float var3) {
      float ☃ = MathHelper.func_76142_g(☃ - ☃);
      if (☃ > ☃) {
         ☃ = ☃;
      }

      if (☃ < -☃) {
         ☃ = -☃;
      }

      return ☃ + ☃;
   }

   public boolean func_205020_a(IWorld var1, boolean var2) {
      IBlockState ☃ = ☃.func_180495_p(new BlockPos(this).func_177977_b());
      return ☃.func_189884_a(this);
   }

   public final boolean func_70058_J() {
      return this.func_205019_a(this.field_70170_p);
   }

   public boolean func_205019_a(IWorldReaderBase var1) {
      return !☃.func_72953_d(this.func_174813_aQ()) && ☃.func_195586_b(this, this.func_174813_aQ()) && ☃.func_195587_c(this, this.func_174813_aQ());
   }

   public int func_70641_bl() {
      return 4;
   }

   public boolean func_204209_c(int var1) {
      return false;
   }

   @Override
   public int func_82143_as() {
      if (this.func_70638_az() == null) {
         return 3;
      } else {
         int ☃ = (int)(this.func_110143_aJ() - this.func_110138_aP() * 0.33F);
         ☃ -= (3 - this.field_70170_p.func_175659_aa().func_151525_a()) * 4;
         if (☃ < 0) {
            ☃ = 0;
         }

         return ☃ + 3;
      }
   }

   @Override
   public Iterable<ItemStack> func_184214_aD() {
      return this.field_184656_bv;
   }

   @Override
   public Iterable<ItemStack> func_184193_aE() {
      return this.field_184657_bw;
   }

   @Override
   public ItemStack func_184582_a(EntityEquipmentSlot var1) {
      switch(☃.func_188453_a()) {
         case HAND:
            return this.field_184656_bv.get(☃.func_188454_b());
         case ARMOR:
            return this.field_184657_bw.get(☃.func_188454_b());
         default:
            return ItemStack.field_190927_a;
      }
   }

   @Override
   public void func_184201_a(EntityEquipmentSlot var1, ItemStack var2) {
      switch(☃.func_188453_a()) {
         case HAND:
            this.field_184656_bv.set(☃.func_188454_b(), ☃);
            break;
         case ARMOR:
            this.field_184657_bw.set(☃.func_188454_b(), ☃);
      }
   }

   @Override
   protected void func_82160_b(boolean var1, int var2) {
      for(EntityEquipmentSlot ☃ : EntityEquipmentSlot.values()) {
         ItemStack ☃x = this.func_184582_a(☃);
         float ☃xx = this.func_205712_c(☃);
         boolean ☃xxx = ☃xx > 1.0F;
         if (!☃x.func_190926_b() && !EnchantmentHelper.func_190939_c(☃x) && (☃ || ☃xxx) && this.field_70146_Z.nextFloat() - (float)☃ * 0.01F < ☃xx) {
            if (!☃xxx && ☃x.func_77984_f()) {
               ☃x.func_196085_b(☃x.func_77958_k() - this.field_70146_Z.nextInt(1 + this.field_70146_Z.nextInt(Math.max(☃x.func_77958_k() - 3, 1))));
            }

            this.func_199701_a_(☃x);
         }
      }
   }

   protected float func_205712_c(EntityEquipmentSlot var1) {
      float ☃;
      switch(☃.func_188453_a()) {
         case HAND:
            ☃ = this.field_82174_bp[☃.func_188454_b()];
            break;
         case ARMOR:
            ☃ = this.field_184655_bs[☃.func_188454_b()];
            break;
         default:
            ☃ = 0.0F;
      }

      return ☃;
   }

   protected void func_180481_a(DifficultyInstance var1) {
      if (this.field_70146_Z.nextFloat() < 0.15F * ☃.func_180170_c()) {
         int ☃ = this.field_70146_Z.nextInt(2);
         float ☃x = this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD ? 0.1F : 0.25F;
         if (this.field_70146_Z.nextFloat() < 0.095F) {
            ++☃;
         }

         if (this.field_70146_Z.nextFloat() < 0.095F) {
            ++☃;
         }

         if (this.field_70146_Z.nextFloat() < 0.095F) {
            ++☃;
         }

         boolean ☃ = true;

         for(EntityEquipmentSlot ☃x : EntityEquipmentSlot.values()) {
            if (☃x.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
               ItemStack ☃xx = this.func_184582_a(☃x);
               if (!☃ && this.field_70146_Z.nextFloat() < ☃x) {
                  break;
               }

               ☃ = false;
               if (☃xx.func_190926_b()) {
                  Item ☃xx = func_184636_a(☃x, ☃);
                  if (☃xx != null) {
                     this.func_184201_a(☃x, new ItemStack(☃xx));
                  }
               }
            }
         }
      }
   }

   public static EntityEquipmentSlot func_184640_d(ItemStack var0) {
      Item ☃ = ☃.func_77973_b();
      if (☃ != Blocks.field_196625_cS.func_199767_j() && (!(☃ instanceof ItemBlock) || !(((ItemBlock)☃).func_179223_d() instanceof BlockAbstractSkull))) {
         if (☃ instanceof ItemArmor) {
            return ((ItemArmor)☃).func_185083_B_();
         } else if (☃ == Items.field_185160_cR) {
            return EntityEquipmentSlot.CHEST;
         } else {
            return ☃ == Items.field_185159_cQ ? EntityEquipmentSlot.OFFHAND : EntityEquipmentSlot.MAINHAND;
         }
      } else {
         return EntityEquipmentSlot.HEAD;
      }
   }

   @Nullable
   public static Item func_184636_a(EntityEquipmentSlot var0, int var1) {
      switch(☃) {
         case HEAD:
            if (☃ == 0) {
               return Items.field_151024_Q;
            } else if (☃ == 1) {
               return Items.field_151169_ag;
            } else if (☃ == 2) {
               return Items.field_151020_U;
            } else if (☃ == 3) {
               return Items.field_151028_Y;
            } else if (☃ == 4) {
               return Items.field_151161_ac;
            }
         case CHEST:
            if (☃ == 0) {
               return Items.field_151027_R;
            } else if (☃ == 1) {
               return Items.field_151171_ah;
            } else if (☃ == 2) {
               return Items.field_151023_V;
            } else if (☃ == 3) {
               return Items.field_151030_Z;
            } else if (☃ == 4) {
               return Items.field_151163_ad;
            }
         case LEGS:
            if (☃ == 0) {
               return Items.field_151026_S;
            } else if (☃ == 1) {
               return Items.field_151149_ai;
            } else if (☃ == 2) {
               return Items.field_151022_W;
            } else if (☃ == 3) {
               return Items.field_151165_aa;
            } else if (☃ == 4) {
               return Items.field_151173_ae;
            }
         case FEET:
            if (☃ == 0) {
               return Items.field_151021_T;
            } else if (☃ == 1) {
               return Items.field_151151_aj;
            } else if (☃ == 2) {
               return Items.field_151029_X;
            } else if (☃ == 3) {
               return Items.field_151167_ab;
            } else if (☃ == 4) {
               return Items.field_151175_af;
            }
         default:
            return null;
      }
   }

   protected void func_180483_b(DifficultyInstance var1) {
      float ☃ = ☃.func_180170_c();
      if (!this.func_184614_ca().func_190926_b() && this.field_70146_Z.nextFloat() < 0.25F * ☃) {
         this.func_184201_a(
            EntityEquipmentSlot.MAINHAND,
            EnchantmentHelper.func_77504_a(this.field_70146_Z, this.func_184614_ca(), (int)(5.0F + ☃ * (float)this.field_70146_Z.nextInt(18)), false)
         );
      }

      for(EntityEquipmentSlot ☃ : EntityEquipmentSlot.values()) {
         if (☃.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
            ItemStack ☃x = this.func_184582_a(☃);
            if (!☃x.func_190926_b() && this.field_70146_Z.nextFloat() < 0.5F * ☃) {
               this.func_184201_a(☃, EnchantmentHelper.func_77504_a(this.field_70146_Z, ☃x, (int)(5.0F + ☃ * (float)this.field_70146_Z.nextInt(18)), false));
            }
         }
      }
   }

   @Nullable
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      this.func_110148_a(SharedMonsterAttributes.field_111265_b)
         .func_111121_a(new AttributeModifier("Random spawn bonus", this.field_70146_Z.nextGaussian() * 0.05, 1));
      if (this.field_70146_Z.nextFloat() < 0.05F) {
         this.func_184641_n(true);
      } else {
         this.func_184641_n(false);
      }

      return ☃;
   }

   public boolean func_82171_bF() {
      return false;
   }

   public void func_110163_bv() {
      this.field_82179_bU = true;
   }

   public void func_184642_a(EntityEquipmentSlot var1, float var2) {
      switch(☃.func_188453_a()) {
         case HAND:
            this.field_82174_bp[☃.func_188454_b()] = ☃;
            break;
         case ARMOR:
            this.field_184655_bs[☃.func_188454_b()] = ☃;
      }
   }

   public boolean func_98052_bS() {
      return this.field_82172_bs;
   }

   public void func_98053_h(boolean var1) {
      this.field_82172_bs = ☃;
   }

   public boolean func_104002_bU() {
      return this.field_82179_bU;
   }

   @Override
   public final boolean func_184230_a(EntityPlayer var1, EnumHand var2) {
      if (this.func_110167_bD() && this.func_110166_bE() == ☃) {
         this.func_110160_i(true, !☃.field_71075_bZ.field_75098_d);
         return true;
      } else {
         ItemStack ☃ = ☃.func_184586_b(☃);
         if (☃.func_77973_b() == Items.field_151058_ca && this.func_184652_a(☃)) {
            this.func_110162_b(☃, true);
            ☃.func_190918_g(1);
            return true;
         } else {
            return this.func_184645_a(☃, ☃) ? true : super.func_184230_a(☃, ☃);
         }
      }
   }

   protected boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      return false;
   }

   protected void func_110159_bB() {
      if (this.field_110170_bx != null) {
         this.func_110165_bF();
      }

      if (this.field_110169_bv) {
         if (!this.func_70089_S()) {
            this.func_110160_i(true, true);
         }

         if (this.field_110168_bw == null || this.field_110168_bw.field_70128_L) {
            this.func_110160_i(true, true);
         }
      }
   }

   public void func_110160_i(boolean var1, boolean var2) {
      if (this.field_110169_bv) {
         this.field_110169_bv = false;
         this.field_110168_bw = null;
         if (!this.field_70170_p.field_72995_K && ☃) {
            this.func_199703_a(Items.field_151058_ca);
         }

         if (!this.field_70170_p.field_72995_K && ☃ && this.field_70170_p instanceof WorldServer) {
            ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketEntityAttach(this, null));
         }
      }
   }

   public boolean func_184652_a(EntityPlayer var1) {
      return !this.func_110167_bD() && !(this instanceof IMob);
   }

   public boolean func_110167_bD() {
      return this.field_110169_bv;
   }

   public Entity func_110166_bE() {
      return this.field_110168_bw;
   }

   public void func_110162_b(Entity var1, boolean var2) {
      this.field_110169_bv = true;
      this.field_110168_bw = ☃;
      if (!this.field_70170_p.field_72995_K && ☃ && this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p).func_73039_n().func_151247_a(this, new SPacketEntityAttach(this, this.field_110168_bw));
      }

      if (this.func_184218_aH()) {
         this.func_184210_p();
      }
   }

   @Override
   public boolean func_184205_a(Entity var1, boolean var2) {
      boolean ☃ = super.func_184205_a(☃, ☃);
      if (☃ && this.func_110167_bD()) {
         this.func_110160_i(true, true);
      }

      return ☃;
   }

   private void func_110165_bF() {
      if (this.field_110169_bv && this.field_110170_bx != null) {
         if (this.field_110170_bx.func_186855_b("UUID")) {
            UUID ☃ = this.field_110170_bx.func_186857_a("UUID");

            for(EntityLivingBase ☃x : this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_186662_g(10.0))) {
               if (☃x.func_110124_au().equals(☃)) {
                  this.func_110162_b(☃x, true);
                  break;
               }
            }
         } else if (this.field_110170_bx.func_150297_b("X", 99) && this.field_110170_bx.func_150297_b("Y", 99) && this.field_110170_bx.func_150297_b("Z", 99)) {
            BlockPos ☃ = new BlockPos(this.field_110170_bx.func_74762_e("X"), this.field_110170_bx.func_74762_e("Y"), this.field_110170_bx.func_74762_e("Z"));
            EntityLeashKnot ☃x = EntityLeashKnot.func_174863_b(this.field_70170_p, ☃);
            if (☃x == null) {
               ☃x = EntityLeashKnot.func_174862_a(this.field_70170_p, ☃);
            }

            this.func_110162_b(☃x, true);
         } else {
            this.func_110160_i(false, true);
         }
      }

      this.field_110170_bx = null;
   }

   @Override
   public boolean func_174820_d(int var1, ItemStack var2) {
      EntityEquipmentSlot ☃;
      if (☃ == 98) {
         ☃ = EntityEquipmentSlot.MAINHAND;
      } else if (☃ == 99) {
         ☃ = EntityEquipmentSlot.OFFHAND;
      } else if (☃ == 100 + EntityEquipmentSlot.HEAD.func_188454_b()) {
         ☃ = EntityEquipmentSlot.HEAD;
      } else if (☃ == 100 + EntityEquipmentSlot.CHEST.func_188454_b()) {
         ☃ = EntityEquipmentSlot.CHEST;
      } else if (☃ == 100 + EntityEquipmentSlot.LEGS.func_188454_b()) {
         ☃ = EntityEquipmentSlot.LEGS;
      } else {
         if (☃ != 100 + EntityEquipmentSlot.FEET.func_188454_b()) {
            return false;
         }

         ☃ = EntityEquipmentSlot.FEET;
      }

      if (!☃.func_190926_b() && !func_184648_b(☃, ☃) && ☃ != EntityEquipmentSlot.HEAD) {
         return false;
      } else {
         this.func_184201_a(☃, ☃);
         return true;
      }
   }

   @Override
   public boolean func_184186_bw() {
      return this.func_82171_bF() && super.func_184186_bw();
   }

   public static boolean func_184648_b(EntityEquipmentSlot var0, ItemStack var1) {
      EntityEquipmentSlot ☃ = func_184640_d(☃);
      return ☃ == ☃
         || ☃ == EntityEquipmentSlot.MAINHAND && ☃ == EntityEquipmentSlot.OFFHAND
         || ☃ == EntityEquipmentSlot.OFFHAND && ☃ == EntityEquipmentSlot.MAINHAND;
   }

   @Override
   public boolean func_70613_aW() {
      return super.func_70613_aW() && !this.func_175446_cd();
   }

   public void func_94061_f(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184654_a);
      this.field_70180_af.func_187227_b(field_184654_a, ☃ ? (byte)(☃ | 1) : (byte)(☃ & -2));
   }

   public void func_184641_n(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184654_a);
      this.field_70180_af.func_187227_b(field_184654_a, ☃ ? (byte)(☃ | 2) : (byte)(☃ & -3));
   }

   public boolean func_175446_cd() {
      return (this.field_70180_af.func_187225_a(field_184654_a) & 1) != 0;
   }

   public boolean func_184638_cS() {
      return (this.field_70180_af.func_187225_a(field_184654_a) & 2) != 0;
   }

   @Override
   public EnumHandSide func_184591_cq() {
      return this.func_184638_cS() ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      float ☃ = (float)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
      int ☃x = 0;
      if (☃ instanceof EntityLivingBase) {
         ☃ += EnchantmentHelper.func_152377_a(this.func_184614_ca(), ((EntityLivingBase)☃).func_70668_bt());
         ☃x += EnchantmentHelper.func_77501_a(this);
      }

      boolean ☃ = ☃.func_70097_a(DamageSource.func_76358_a(this), ☃);
      if (☃) {
         if (☃x > 0 && ☃ instanceof EntityLivingBase) {
            ((EntityLivingBase)☃)
               .func_70653_a(
                  this,
                  (float)☃x * 0.5F,
                  (double)MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)),
                  (double)(-MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)))
               );
            this.field_70159_w *= 0.6;
            this.field_70179_y *= 0.6;
         }

         int ☃x = EnchantmentHelper.func_90036_a(this);
         if (☃x > 0) {
            ☃.func_70015_d(☃x * 4);
         }

         if (☃ instanceof EntityPlayer) {
            EntityPlayer ☃x = (EntityPlayer)☃;
            ItemStack ☃xx = this.func_184614_ca();
            ItemStack ☃xxx = ☃x.func_184587_cr() ? ☃x.func_184607_cu() : ItemStack.field_190927_a;
            if (!☃xx.func_190926_b() && !☃xxx.func_190926_b() && ☃xx.func_77973_b() instanceof ItemAxe && ☃xxx.func_77973_b() == Items.field_185159_cQ) {
               float ☃xxxx = 0.25F + (float)EnchantmentHelper.func_185293_e(this) * 0.05F;
               if (this.field_70146_Z.nextFloat() < ☃xxxx) {
                  ☃x.func_184811_cZ().func_185145_a(Items.field_185159_cQ, 100);
                  this.field_70170_p.func_72960_a(☃x, (byte)30);
               }
            }
         }

         this.func_174815_a(this, ☃);
      }

      return ☃;
   }

   protected boolean func_204609_dp() {
      if (this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K) {
         float ☃ = this.func_70013_c();
         BlockPos ☃x = this.func_184187_bx() instanceof EntityBoat
            ? new BlockPos(this.field_70165_t, (double)Math.round(this.field_70163_u), this.field_70161_v).func_177984_a()
            : new BlockPos(this.field_70165_t, (double)Math.round(this.field_70163_u), this.field_70161_v);
         if (☃ > 0.5F && this.field_70146_Z.nextFloat() * 30.0F < (☃ - 0.4F) * 2.0F && this.field_70170_p.func_175678_i(☃x)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void func_180466_bG(Tag<Fluid> var1) {
      if (this.func_70661_as().func_212238_t()) {
         super.func_180466_bG(☃);
      } else {
         this.field_70181_x += 0.3F;
      }
   }
}
