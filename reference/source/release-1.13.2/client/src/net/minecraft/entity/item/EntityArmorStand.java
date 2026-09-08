package net.minecraft.entity.item;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityArmorStand extends EntityLivingBase {
   private static final Rotations field_175435_a = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations field_175433_b = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations field_175434_c = new Rotations(-10.0F, 0.0F, -10.0F);
   private static final Rotations field_175431_d = new Rotations(-15.0F, 0.0F, 10.0F);
   private static final Rotations field_175432_e = new Rotations(-1.0F, 0.0F, -1.0F);
   private static final Rotations field_175429_f = new Rotations(1.0F, 0.0F, 1.0F);
   public static final DataParameter<Byte> field_184801_a = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187191_a);
   public static final DataParameter<Rotations> field_184802_b = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184803_c = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184804_d = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184805_e = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184806_f = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   public static final DataParameter<Rotations> field_184807_g = EntityDataManager.func_187226_a(EntityArmorStand.class, DataSerializers.field_187199_i);
   private static final Predicate<Entity> field_184798_bv = var0 -> var0 instanceof EntityMinecart
         && ((EntityMinecart)var0).func_184264_v() == EntityMinecart.Type.RIDEABLE;
   private final NonNullList<ItemStack> field_184799_bw = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
   private final NonNullList<ItemStack> field_184800_bx = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
   private boolean field_175436_h;
   public long field_175437_i;
   private int field_175442_bg;
   private boolean field_181028_bj;
   private Rotations field_175443_bh = field_175435_a;
   private Rotations field_175444_bi = field_175433_b;
   private Rotations field_175438_bj = field_175434_c;
   private Rotations field_175439_bk = field_175431_d;
   private Rotations field_175440_bl = field_175432_e;
   private Rotations field_175441_bm = field_175429_f;

   public EntityArmorStand(World var1) {
      super(EntityType.field_200789_c, ☃);
      this.field_70145_X = this.func_189652_ae();
      this.func_70105_a(0.5F, 1.975F);
      this.field_70138_W = 0.0F;
   }

   public EntityArmorStand(World var1, double var2, double var4, double var6) {
      this(☃);
      this.func_70107_b(☃, ☃, ☃);
   }

   @Override
   protected final void func_70105_a(float var1, float var2) {
      double ☃ = this.field_70165_t;
      double ☃x = this.field_70163_u;
      double ☃xx = this.field_70161_v;
      float ☃xxx = this.func_181026_s() ? 0.0F : (this.func_70631_g_() ? 0.5F : 1.0F);
      super.func_70105_a(☃ * ☃xxx, ☃ * ☃xxx);
      this.func_70107_b(☃, ☃x, ☃xx);
   }

   @Override
   public boolean func_70613_aW() {
      return super.func_70613_aW() && !this.func_189652_ae();
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184801_a, (byte)0);
      this.field_70180_af.func_187214_a(field_184802_b, field_175435_a);
      this.field_70180_af.func_187214_a(field_184803_c, field_175433_b);
      this.field_70180_af.func_187214_a(field_184804_d, field_175434_c);
      this.field_70180_af.func_187214_a(field_184805_e, field_175431_d);
      this.field_70180_af.func_187214_a(field_184806_f, field_175432_e);
      this.field_70180_af.func_187214_a(field_184807_g, field_175429_f);
   }

   @Override
   public Iterable<ItemStack> func_184214_aD() {
      return this.field_184799_bw;
   }

   @Override
   public Iterable<ItemStack> func_184193_aE() {
      return this.field_184800_bx;
   }

   @Override
   public ItemStack func_184582_a(EntityEquipmentSlot var1) {
      switch(☃.func_188453_a()) {
         case HAND:
            return this.field_184799_bw.get(☃.func_188454_b());
         case ARMOR:
            return this.field_184800_bx.get(☃.func_188454_b());
         default:
            return ItemStack.field_190927_a;
      }
   }

   @Override
   public void func_184201_a(EntityEquipmentSlot var1, ItemStack var2) {
      switch(☃.func_188453_a()) {
         case HAND:
            this.func_184606_a_(☃);
            this.field_184799_bw.set(☃.func_188454_b(), ☃);
            break;
         case ARMOR:
            this.func_184606_a_(☃);
            this.field_184800_bx.set(☃.func_188454_b(), ☃);
      }
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

      if (!☃.func_190926_b() && !EntityLiving.func_184648_b(☃, ☃) && ☃ != EntityEquipmentSlot.HEAD) {
         return false;
      } else {
         this.func_184201_a(☃, ☃);
         return true;
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      NBTTagList ☃ = new NBTTagList();

      for(ItemStack ☃x : this.field_184800_bx) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         if (!☃x.func_190926_b()) {
            ☃x.func_77955_b(☃xx);
         }

         ☃.add((INBTBase)☃xx);
      }

      ☃.func_74782_a("ArmorItems", ☃);
      NBTTagList ☃x = new NBTTagList();

      for(ItemStack ☃xx : this.field_184799_bw) {
         NBTTagCompound ☃xxx = new NBTTagCompound();
         if (!☃xx.func_190926_b()) {
            ☃xx.func_77955_b(☃xxx);
         }

         ☃x.add((INBTBase)☃xxx);
      }

      ☃.func_74782_a("HandItems", ☃x);
      ☃.func_74757_a("Invisible", this.func_82150_aj());
      ☃.func_74757_a("Small", this.func_175410_n());
      ☃.func_74757_a("ShowArms", this.func_175402_q());
      ☃.func_74768_a("DisabledSlots", this.field_175442_bg);
      ☃.func_74757_a("NoBasePlate", this.func_175414_r());
      if (this.func_181026_s()) {
         ☃.func_74757_a("Marker", this.func_181026_s());
      }

      ☃.func_74782_a("Pose", this.func_175419_y());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("ArmorItems", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("ArmorItems", 10);

         for(int ☃x = 0; ☃x < this.field_184800_bx.size(); ++☃x) {
            this.field_184800_bx.set(☃x, ItemStack.func_199557_a(☃.func_150305_b(☃x)));
         }
      }

      if (☃.func_150297_b("HandItems", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("HandItems", 10);

         for(int ☃x = 0; ☃x < this.field_184799_bw.size(); ++☃x) {
            this.field_184799_bw.set(☃x, ItemStack.func_199557_a(☃.func_150305_b(☃x)));
         }
      }

      this.func_82142_c(☃.func_74767_n("Invisible"));
      this.func_175420_a(☃.func_74767_n("Small"));
      this.func_175413_k(☃.func_74767_n("ShowArms"));
      this.field_175442_bg = ☃.func_74762_e("DisabledSlots");
      this.func_175426_l(☃.func_74767_n("NoBasePlate"));
      this.func_181027_m(☃.func_74767_n("Marker"));
      this.field_181028_bj = !this.func_181026_s();
      this.field_70145_X = this.func_189652_ae();
      NBTTagCompound ☃ = ☃.func_74775_l("Pose");
      this.func_175416_h(☃);
   }

   private void func_175416_h(NBTTagCompound var1) {
      NBTTagList ☃ = ☃.func_150295_c("Head", 5);
      this.func_175415_a(☃.isEmpty() ? field_175435_a : new Rotations(☃));
      NBTTagList ☃x = ☃.func_150295_c("Body", 5);
      this.func_175424_b(☃x.isEmpty() ? field_175433_b : new Rotations(☃x));
      NBTTagList ☃xx = ☃.func_150295_c("LeftArm", 5);
      this.func_175405_c(☃xx.isEmpty() ? field_175434_c : new Rotations(☃xx));
      NBTTagList ☃xxx = ☃.func_150295_c("RightArm", 5);
      this.func_175428_d(☃xxx.isEmpty() ? field_175431_d : new Rotations(☃xxx));
      NBTTagList ☃xxxx = ☃.func_150295_c("LeftLeg", 5);
      this.func_175417_e(☃xxxx.isEmpty() ? field_175432_e : new Rotations(☃xxxx));
      NBTTagList ☃xxxxx = ☃.func_150295_c("RightLeg", 5);
      this.func_175427_f(☃xxxxx.isEmpty() ? field_175429_f : new Rotations(☃xxxxx));
   }

   private NBTTagCompound func_175419_y() {
      NBTTagCompound ☃ = new NBTTagCompound();
      if (!field_175435_a.equals(this.field_175443_bh)) {
         ☃.func_74782_a("Head", this.field_175443_bh.func_179414_a());
      }

      if (!field_175433_b.equals(this.field_175444_bi)) {
         ☃.func_74782_a("Body", this.field_175444_bi.func_179414_a());
      }

      if (!field_175434_c.equals(this.field_175438_bj)) {
         ☃.func_74782_a("LeftArm", this.field_175438_bj.func_179414_a());
      }

      if (!field_175431_d.equals(this.field_175439_bk)) {
         ☃.func_74782_a("RightArm", this.field_175439_bk.func_179414_a());
      }

      if (!field_175432_e.equals(this.field_175440_bl)) {
         ☃.func_74782_a("LeftLeg", this.field_175440_bl.func_179414_a());
      }

      if (!field_175429_f.equals(this.field_175441_bm)) {
         ☃.func_74782_a("RightLeg", this.field_175441_bm.func_179414_a());
      }

      return ☃;
   }

   @Override
   public boolean func_70104_M() {
      return false;
   }

   @Override
   protected void func_82167_n(Entity var1) {
   }

   @Override
   protected void func_85033_bc() {
      List<Entity> ☃ = this.field_70170_p.func_175674_a(this, this.func_174813_aQ(), field_184798_bv);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         Entity ☃xx = (Entity)☃.get(☃x);
         if (this.func_70068_e(☃xx) <= 0.2) {
            ☃xx.func_70108_f(this);
         }
      }
   }

   @Override
   public EnumActionResult func_184199_a(EntityPlayer var1, Vec3d var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (this.func_181026_s() || ☃.func_77973_b() == Items.field_151057_cb) {
         return EnumActionResult.PASS;
      } else if (!this.field_70170_p.field_72995_K && !☃.func_175149_v()) {
         EntityEquipmentSlot ☃ = EntityLiving.func_184640_d(☃);
         if (☃.func_190926_b()) {
            EntityEquipmentSlot ☃x = this.func_190772_a(☃);
            EntityEquipmentSlot ☃xx = this.func_184796_b(☃x) ? ☃ : ☃x;
            if (this.func_190630_a(☃xx)) {
               this.func_184795_a(☃, ☃xx, ☃, ☃);
            }
         } else {
            if (this.func_184796_b(☃)) {
               return EnumActionResult.FAIL;
            }

            if (☃.func_188453_a() == EntityEquipmentSlot.Type.HAND && !this.func_175402_q()) {
               return EnumActionResult.FAIL;
            }

            this.func_184795_a(☃, ☃, ☃, ☃);
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.SUCCESS;
      }
   }

   protected EntityEquipmentSlot func_190772_a(Vec3d var1) {
      EntityEquipmentSlot ☃ = EntityEquipmentSlot.MAINHAND;
      boolean ☃x = this.func_175410_n();
      double ☃xx = ☃x ? ☃.field_72448_b * 2.0 : ☃.field_72448_b;
      EntityEquipmentSlot ☃xxx = EntityEquipmentSlot.FEET;
      if (☃xx >= 0.1 && ☃xx < 0.1 + (☃x ? 0.8 : 0.45) && this.func_190630_a(☃xxx)) {
         ☃ = EntityEquipmentSlot.FEET;
      } else if (☃xx >= 0.9 + (☃x ? 0.3 : 0.0) && ☃xx < 0.9 + (☃x ? 1.0 : 0.7) && this.func_190630_a(EntityEquipmentSlot.CHEST)) {
         ☃ = EntityEquipmentSlot.CHEST;
      } else if (☃xx >= 0.4 && ☃xx < 0.4 + (☃x ? 1.0 : 0.8) && this.func_190630_a(EntityEquipmentSlot.LEGS)) {
         ☃ = EntityEquipmentSlot.LEGS;
      } else if (☃xx >= 1.6 && this.func_190630_a(EntityEquipmentSlot.HEAD)) {
         ☃ = EntityEquipmentSlot.HEAD;
      } else if (!this.func_190630_a(EntityEquipmentSlot.MAINHAND) && this.func_190630_a(EntityEquipmentSlot.OFFHAND)) {
         ☃ = EntityEquipmentSlot.OFFHAND;
      }

      return ☃;
   }

   public boolean func_184796_b(EntityEquipmentSlot var1) {
      return (this.field_175442_bg & 1 << ☃.func_188452_c()) != 0 || ☃.func_188453_a() == EntityEquipmentSlot.Type.HAND && !this.func_175402_q();
   }

   private void func_184795_a(EntityPlayer var1, EntityEquipmentSlot var2, ItemStack var3, EnumHand var4) {
      ItemStack ☃ = this.func_184582_a(☃);
      if (☃.func_190926_b() || (this.field_175442_bg & 1 << ☃.func_188452_c() + 8) == 0) {
         if (!☃.func_190926_b() || (this.field_175442_bg & 1 << ☃.func_188452_c() + 16) == 0) {
            if (☃.field_71075_bZ.field_75098_d && ☃.func_190926_b() && !☃.func_190926_b()) {
               ItemStack ☃x = ☃.func_77946_l();
               ☃x.func_190920_e(1);
               this.func_184201_a(☃, ☃x);
            } else if (☃.func_190926_b() || ☃.func_190916_E() <= 1) {
               this.func_184201_a(☃, ☃);
               ☃.func_184611_a(☃, ☃);
            } else if (☃.func_190926_b()) {
               ItemStack ☃x = ☃.func_77946_l();
               ☃x.func_190920_e(1);
               this.func_184201_a(☃, ☃x);
               ☃.func_190918_g(1);
            }
         }
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.field_70170_p.field_72995_K || this.field_70128_L) {
         return false;
      } else if (DamageSource.field_76380_i.equals(☃)) {
         this.func_70106_y();
         return false;
      } else if (this.func_180431_b(☃) || this.field_175436_h || this.func_181026_s()) {
         return false;
      } else if (☃.func_94541_c()) {
         this.func_175409_C();
         this.func_70106_y();
         return false;
      } else if (DamageSource.field_76372_a.equals(☃)) {
         if (this.func_70027_ad()) {
            this.func_175406_a(0.15F);
         } else {
            this.func_70015_d(5);
         }

         return false;
      } else if (DamageSource.field_76370_b.equals(☃) && this.func_110143_aJ() > 0.5F) {
         this.func_175406_a(4.0F);
         return false;
      } else {
         boolean ☃ = ☃.func_76364_f() instanceof EntityArrow;
         boolean ☃x = "player".equals(☃.func_76355_l());
         if (!☃x && !☃) {
            return false;
         } else if (☃.func_76346_g() instanceof EntityPlayer && !((EntityPlayer)☃.func_76346_g()).field_71075_bZ.field_75099_e) {
            return false;
         } else if (☃.func_180136_u()) {
            this.func_190773_I();
            this.func_175412_z();
            this.func_70106_y();
            return false;
         } else {
            long ☃ = this.field_70170_p.func_82737_E();
            if (☃ - this.field_175437_i > 5L && !☃) {
               this.field_70170_p.func_72960_a(this, (byte)32);
               this.field_175437_i = ☃;
            } else {
               this.func_175421_A();
               this.func_175412_z();
               this.func_70106_y();
            }

            return true;
         }
      }
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 32) {
         if (this.field_70170_p.field_72995_K) {
            this.field_70170_p
               .func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187707_l, this.func_184176_by(), 0.3F, 1.0F, false);
            this.field_175437_i = this.field_70170_p.func_82737_E();
         }
      } else {
         super.func_70103_a(☃);
      }
   }

   @Override
   public boolean func_70112_a(double var1) {
      double ☃ = this.func_174813_aQ().func_72320_b() * 4.0;
      if (Double.isNaN(☃) || ☃ == 0.0) {
         ☃ = 4.0;
      }

      ☃ *= 64.0;
      return ☃ < ☃ * ☃;
   }

   private void func_175412_z() {
      if (this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p)
            .func_195598_a(
               new BlockParticleData(Particles.field_197611_d, Blocks.field_196662_n.func_176223_P()),
               this.field_70165_t,
               this.field_70163_u + (double)this.field_70131_O / 1.5,
               this.field_70161_v,
               10,
               (double)(this.field_70130_N / 4.0F),
               (double)(this.field_70131_O / 4.0F),
               (double)(this.field_70130_N / 4.0F),
               0.05
            );
      }
   }

   private void func_175406_a(float var1) {
      float ☃ = this.func_110143_aJ();
      ☃ -= ☃;
      if (☃ <= 0.5F) {
         this.func_175409_C();
         this.func_70106_y();
      } else {
         this.func_70606_j(☃);
      }
   }

   private void func_175421_A() {
      Block.func_180635_a(this.field_70170_p, new BlockPos(this), new ItemStack(Items.field_179565_cj));
      this.func_175409_C();
   }

   private void func_175409_C() {
      this.func_190773_I();

      for(int ☃ = 0; ☃ < this.field_184799_bw.size(); ++☃) {
         ItemStack ☃x = this.field_184799_bw.get(☃);
         if (!☃x.func_190926_b()) {
            Block.func_180635_a(this.field_70170_p, new BlockPos(this).func_177984_a(), ☃x);
            this.field_184799_bw.set(☃, ItemStack.field_190927_a);
         }
      }

      for(int ☃ = 0; ☃ < this.field_184800_bx.size(); ++☃) {
         ItemStack ☃x = this.field_184800_bx.get(☃);
         if (!☃x.func_190926_b()) {
            Block.func_180635_a(this.field_70170_p, new BlockPos(this).func_177984_a(), ☃x);
            this.field_184800_bx.set(☃, ItemStack.field_190927_a);
         }
      }
   }

   private void func_190773_I() {
      this.field_70170_p
         .func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187701_j, this.func_184176_by(), 1.0F, 1.0F);
   }

   @Override
   protected float func_110146_f(float var1, float var2) {
      this.field_70760_ar = this.field_70126_B;
      this.field_70761_aq = this.field_70177_z;
      return 0.0F;
   }

   @Override
   public float func_70047_e() {
      return this.func_70631_g_() ? this.field_70131_O * 0.5F : this.field_70131_O * 0.9F;
   }

   @Override
   public double func_70033_W() {
      return this.func_181026_s() ? 0.0 : 0.1F;
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      if (!this.func_189652_ae()) {
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   @Override
   public void func_181013_g(float var1) {
      this.field_70760_ar = this.field_70126_B = ☃;
      this.field_70758_at = this.field_70759_as = ☃;
   }

   @Override
   public void func_70034_d(float var1) {
      this.field_70760_ar = this.field_70126_B = ☃;
      this.field_70758_at = this.field_70759_as = ☃;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      Rotations ☃ = this.field_70180_af.func_187225_a(field_184802_b);
      if (!this.field_175443_bh.equals(☃)) {
         this.func_175415_a(☃);
      }

      Rotations ☃ = this.field_70180_af.func_187225_a(field_184803_c);
      if (!this.field_175444_bi.equals(☃)) {
         this.func_175424_b(☃);
      }

      Rotations ☃ = this.field_70180_af.func_187225_a(field_184804_d);
      if (!this.field_175438_bj.equals(☃)) {
         this.func_175405_c(☃);
      }

      Rotations ☃ = this.field_70180_af.func_187225_a(field_184805_e);
      if (!this.field_175439_bk.equals(☃)) {
         this.func_175428_d(☃);
      }

      Rotations ☃ = this.field_70180_af.func_187225_a(field_184806_f);
      if (!this.field_175440_bl.equals(☃)) {
         this.func_175417_e(☃);
      }

      Rotations ☃ = this.field_70180_af.func_187225_a(field_184807_g);
      if (!this.field_175441_bm.equals(☃)) {
         this.func_175427_f(☃);
      }

      boolean ☃ = this.func_181026_s();
      if (this.field_181028_bj != ☃) {
         this.func_181550_a(☃);
         this.field_70156_m = !☃;
         this.field_181028_bj = ☃;
      }
   }

   private void func_181550_a(boolean var1) {
      if (☃) {
         this.func_70105_a(0.0F, 0.0F);
      } else {
         this.func_70105_a(0.5F, 1.975F);
      }
   }

   @Override
   protected void func_175135_B() {
      this.func_82142_c(this.field_175436_h);
   }

   @Override
   public void func_82142_c(boolean var1) {
      this.field_175436_h = ☃;
      super.func_82142_c(☃);
   }

   @Override
   public boolean func_70631_g_() {
      return this.func_175410_n();
   }

   @Override
   public void func_174812_G() {
      this.func_70106_y();
   }

   @Override
   public boolean func_180427_aV() {
      return this.func_82150_aj();
   }

   @Override
   public EnumPushReaction func_184192_z() {
      return this.func_181026_s() ? EnumPushReaction.IGNORE : super.func_184192_z();
   }

   private void func_175420_a(boolean var1) {
      this.field_70180_af.func_187227_b(field_184801_a, this.func_184797_a(this.field_70180_af.func_187225_a(field_184801_a), 1, ☃));
      this.func_70105_a(0.5F, 1.975F);
   }

   public boolean func_175410_n() {
      return (this.field_70180_af.func_187225_a(field_184801_a) & 1) != 0;
   }

   private void func_175413_k(boolean var1) {
      this.field_70180_af.func_187227_b(field_184801_a, this.func_184797_a(this.field_70180_af.func_187225_a(field_184801_a), 4, ☃));
   }

   public boolean func_175402_q() {
      return (this.field_70180_af.func_187225_a(field_184801_a) & 4) != 0;
   }

   private void func_175426_l(boolean var1) {
      this.field_70180_af.func_187227_b(field_184801_a, this.func_184797_a(this.field_70180_af.func_187225_a(field_184801_a), 8, ☃));
   }

   public boolean func_175414_r() {
      return (this.field_70180_af.func_187225_a(field_184801_a) & 8) != 0;
   }

   private void func_181027_m(boolean var1) {
      this.field_70180_af.func_187227_b(field_184801_a, this.func_184797_a(this.field_70180_af.func_187225_a(field_184801_a), 16, ☃));
      this.func_70105_a(0.5F, 1.975F);
   }

   public boolean func_181026_s() {
      return (this.field_70180_af.func_187225_a(field_184801_a) & 16) != 0;
   }

   private byte func_184797_a(byte var1, int var2, boolean var3) {
      if (☃) {
         ☃ = (byte)(☃ | ☃);
      } else {
         ☃ = (byte)(☃ & ~☃);
      }

      return ☃;
   }

   public void func_175415_a(Rotations var1) {
      this.field_175443_bh = ☃;
      this.field_70180_af.func_187227_b(field_184802_b, ☃);
   }

   public void func_175424_b(Rotations var1) {
      this.field_175444_bi = ☃;
      this.field_70180_af.func_187227_b(field_184803_c, ☃);
   }

   public void func_175405_c(Rotations var1) {
      this.field_175438_bj = ☃;
      this.field_70180_af.func_187227_b(field_184804_d, ☃);
   }

   public void func_175428_d(Rotations var1) {
      this.field_175439_bk = ☃;
      this.field_70180_af.func_187227_b(field_184805_e, ☃);
   }

   public void func_175417_e(Rotations var1) {
      this.field_175440_bl = ☃;
      this.field_70180_af.func_187227_b(field_184806_f, ☃);
   }

   public void func_175427_f(Rotations var1) {
      this.field_175441_bm = ☃;
      this.field_70180_af.func_187227_b(field_184807_g, ☃);
   }

   public Rotations func_175418_s() {
      return this.field_175443_bh;
   }

   public Rotations func_175408_t() {
      return this.field_175444_bi;
   }

   public Rotations func_175404_u() {
      return this.field_175438_bj;
   }

   public Rotations func_175411_v() {
      return this.field_175439_bk;
   }

   public Rotations func_175403_w() {
      return this.field_175440_bl;
   }

   public Rotations func_175407_x() {
      return this.field_175441_bm;
   }

   @Override
   public boolean func_70067_L() {
      return super.func_70067_L() && !this.func_181026_s();
   }

   @Override
   public EnumHandSide func_184591_cq() {
      return EnumHandSide.RIGHT;
   }

   @Override
   protected SoundEvent func_184588_d(int var1) {
      return SoundEvents.field_187704_k;
   }

   @Nullable
   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187707_l;
   }

   @Nullable
   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187701_j;
   }

   @Override
   public void func_70077_a(EntityLightningBolt var1) {
   }

   @Override
   public boolean func_184603_cC() {
      return false;
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184801_a.equals(☃)) {
         this.func_70105_a(0.5F, 1.975F);
      }

      super.func_184206_a(☃);
   }

   @Override
   public boolean func_190631_cK() {
      return false;
   }
}
