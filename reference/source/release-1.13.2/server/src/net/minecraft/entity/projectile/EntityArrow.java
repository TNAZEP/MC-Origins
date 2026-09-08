package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityArrow extends Entity implements IProjectile {
   private static final Predicate<Entity> field_184553_f = EntitySelectors.field_180132_d.and(EntitySelectors.field_94557_a.and(Entity::func_70067_L));
   private static final DataParameter<Byte> field_184554_g = EntityDataManager.func_187226_a(EntityArrow.class, DataSerializers.field_187191_a);
   protected static final DataParameter<Optional<UUID>> field_212362_a = EntityDataManager.func_187226_a(EntityArrow.class, DataSerializers.field_187203_m);
   private int field_145791_d = -1;
   private int field_145792_e = -1;
   private int field_145789_f = -1;
   @Nullable
   private IBlockState field_195056_av;
   protected boolean field_70254_i;
   protected int field_184552_b;
   public EntityArrow.PickupStatus field_70251_a = EntityArrow.PickupStatus.DISALLOWED;
   public int field_70249_b;
   public UUID field_70250_c;
   private int field_70252_j;
   private int field_70257_an;
   private double field_70255_ao = 2.0;
   private int field_70256_ap;

   protected EntityArrow(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(0.5F, 0.5F);
   }

   protected EntityArrow(EntityType<?> var1, double var2, double var4, double var6, World var8) {
      this(☃, ☃);
      this.func_70107_b(☃, ☃, ☃);
   }

   protected EntityArrow(EntityType<?> var1, EntityLivingBase var2, World var3) {
      this(☃, ☃.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e() - 0.1F, ☃.field_70161_v, ☃);
      this.func_212361_a(☃);
      if (☃ instanceof EntityPlayer) {
         this.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
      }
   }

   @Override
   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184554_g, (byte)0);
      this.field_70180_af.func_187214_a(field_212362_a, Optional.empty());
   }

   public void func_184547_a(Entity var1, float var2, float var3, float var4, float var5, float var6) {
      float ☃ = -MathHelper.func_76126_a(☃ * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0));
      float ☃x = -MathHelper.func_76126_a(☃ * (float) (Math.PI / 180.0));
      float ☃xx = MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0));
      this.func_70186_c((double)☃, (double)☃x, (double)☃xx, ☃, ☃);
      this.field_70159_w += ☃.field_70159_w;
      this.field_70179_y += ☃.field_70179_y;
      if (!☃.field_70122_E) {
         this.field_70181_x += ☃.field_70181_x;
      }
   }

   @Override
   public void func_70186_c(double var1, double var3, double var5, float var7, float var8) {
      float ☃ = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃ + ☃ * ☃);
      ☃ /= (double)☃;
      ☃ /= (double)☃;
      ☃ /= (double)☃;
      ☃ += this.field_70146_Z.nextGaussian() * 0.0075F * (double)☃;
      ☃ += this.field_70146_Z.nextGaussian() * 0.0075F * (double)☃;
      ☃ += this.field_70146_Z.nextGaussian() * 0.0075F * (double)☃;
      ☃ *= (double)☃;
      ☃ *= (double)☃;
      ☃ *= (double)☃;
      this.field_70159_w = ☃;
      this.field_70181_x = ☃;
      this.field_70179_y = ☃;
      float ☃x = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃);
      this.field_70177_z = (float)(MathHelper.func_181159_b(☃, ☃) * 180.0F / (float)Math.PI);
      this.field_70125_A = (float)(MathHelper.func_181159_b(☃, (double)☃x) * 180.0F / (float)Math.PI);
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      this.field_70252_j = 0;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      boolean ☃ = this.func_203047_q();
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float ☃x = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 180.0F / (float)Math.PI);
         this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)☃x) * 180.0F / (float)Math.PI);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }

      BlockPos ☃ = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
      IBlockState ☃x = this.field_70170_p.func_180495_p(☃);
      if (!☃x.func_196958_f() && !☃) {
         VoxelShape ☃xx = ☃x.func_196952_d(this.field_70170_p, ☃);
         if (!☃xx.func_197766_b()) {
            for(AxisAlignedBB ☃xxx : ☃xx.func_197756_d()) {
               if (☃xxx.func_186670_a(☃).func_72318_a(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
                  this.field_70254_i = true;
                  break;
               }
            }
         }
      }

      if (this.field_70249_b > 0) {
         --this.field_70249_b;
      }

      if (this.func_70026_G()) {
         this.func_70066_B();
      }

      if (this.field_70254_i && !☃) {
         if (this.field_195056_av != ☃x && this.field_70170_p.func_195586_b(null, this.func_174813_aQ().func_186662_g(0.05))) {
            this.field_70254_i = false;
            this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70252_j = 0;
            this.field_70257_an = 0;
         } else {
            this.func_203048_f();
         }

         ++this.field_184552_b;
      } else {
         this.field_184552_b = 0;
         ++this.field_70257_an;
         Vec3d ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         Vec3d ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         RayTraceResult ☃xx = this.field_70170_p.func_200259_a(☃, ☃x, RayTraceFluidMode.NEVER, true, false);
         ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         if (☃xx != null) {
            ☃x = new Vec3d(☃xx.field_72307_f.field_72450_a, ☃xx.field_72307_f.field_72448_b, ☃xx.field_72307_f.field_72449_c);
         }

         Entity ☃ = this.func_184551_a(☃, ☃x);
         if (☃ != null) {
            ☃xx = new RayTraceResult(☃);
         }

         if (☃xx != null && ☃xx.field_72308_g instanceof EntityPlayer) {
            EntityPlayer ☃ = (EntityPlayer)☃xx.field_72308_g;
            Entity ☃x = this.func_212360_k();
            if (☃x instanceof EntityPlayer && !((EntityPlayer)☃x).func_96122_a(☃)) {
               ☃xx = null;
            }
         }

         if (☃xx != null && !☃) {
            this.func_184549_a(☃xx);
            this.field_70160_al = true;
         }

         if (this.func_70241_g()) {
            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_197614_g,
                     this.field_70165_t + this.field_70159_w * (double)☃ / 4.0,
                     this.field_70163_u + this.field_70181_x * (double)☃ / 4.0,
                     this.field_70161_v + this.field_70179_y * (double)☃ / 4.0,
                     -this.field_70159_w,
                     -this.field_70181_x + 0.2,
                     -this.field_70179_y
                  );
            }
         }

         this.field_70165_t += this.field_70159_w;
         this.field_70163_u += this.field_70181_x;
         this.field_70161_v += this.field_70179_y;
         float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         if (☃) {
            this.field_70177_z = (float)(MathHelper.func_181159_b(-this.field_70159_w, -this.field_70179_y) * 180.0F / (float)Math.PI);
         } else {
            this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 180.0F / (float)Math.PI);
         }

         this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)☃) * 180.0F / (float)Math.PI);

         while(this.field_70125_A - this.field_70127_C < -180.0F) {
            this.field_70127_C -= 360.0F;
         }

         while(this.field_70125_A - this.field_70127_C >= 180.0F) {
            this.field_70127_C += 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B < -180.0F) {
            this.field_70126_B -= 360.0F;
         }

         while(this.field_70177_z - this.field_70126_B >= 180.0F) {
            this.field_70126_B += 360.0F;
         }

         this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
         this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
         float ☃ = 0.99F;
         float ☃x = 0.05F;
         if (this.func_70090_H()) {
            for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
               float ☃xxx = 0.25F;
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_197612_e,
                     this.field_70165_t - this.field_70159_w * 0.25,
                     this.field_70163_u - this.field_70181_x * 0.25,
                     this.field_70161_v - this.field_70179_y * 0.25,
                     this.field_70159_w,
                     this.field_70181_x,
                     this.field_70179_y
                  );
            }

            ☃ = this.func_203044_p();
         }

         this.field_70159_w *= (double)☃;
         this.field_70181_x *= (double)☃;
         this.field_70179_y *= (double)☃;
         if (!this.func_189652_ae() && !☃) {
            this.field_70181_x -= 0.05F;
         }

         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
         this.func_145775_I();
      }
   }

   protected void func_203048_f() {
      ++this.field_70252_j;
      if (this.field_70252_j >= 1200) {
         this.func_70106_y();
      }
   }

   protected void func_184549_a(RayTraceResult var1) {
      if (☃.field_72308_g != null) {
         this.func_203046_b(☃);
      } else {
         BlockPos ☃ = ☃.func_178782_a();
         this.field_145791_d = ☃.func_177958_n();
         this.field_145792_e = ☃.func_177956_o();
         this.field_145789_f = ☃.func_177952_p();
         IBlockState ☃x = this.field_70170_p.func_180495_p(☃);
         this.field_195056_av = ☃x;
         this.field_70159_w = (double)((float)(☃.field_72307_f.field_72450_a - this.field_70165_t));
         this.field_70181_x = (double)((float)(☃.field_72307_f.field_72448_b - this.field_70163_u));
         this.field_70179_y = (double)((float)(☃.field_72307_f.field_72449_c - this.field_70161_v));
         float ☃xx = MathHelper.func_76133_a(
               this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y
            )
            * 20.0F;
         this.field_70165_t -= this.field_70159_w / (double)☃xx;
         this.field_70163_u -= this.field_70181_x / (double)☃xx;
         this.field_70161_v -= this.field_70179_y / (double)☃xx;
         this.func_184185_a(this.func_203050_i(), 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
         this.field_70254_i = true;
         this.field_70249_b = 7;
         this.func_70243_d(false);
         if (!☃x.func_196958_f()) {
            this.field_195056_av.func_196950_a(this.field_70170_p, ☃, this);
         }
      }
   }

   protected void func_203046_b(RayTraceResult var1) {
      Entity ☃ = ☃.field_72308_g;
      float ☃x = MathHelper.func_76133_a(
         this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y
      );
      int ☃xx = MathHelper.func_76143_f((double)☃x * this.field_70255_ao);
      if (this.func_70241_g()) {
         ☃xx += this.field_70146_Z.nextInt(☃xx / 2 + 2);
      }

      Entity ☃x = this.func_212360_k();
      DamageSource ☃;
      if (☃x == null) {
         ☃ = DamageSource.func_76353_a(this, this);
      } else {
         ☃ = DamageSource.func_76353_a(this, ☃x);
      }

      if (this.func_70027_ad() && !(☃ instanceof EntityEnderman)) {
         ☃.func_70015_d(5);
      }

      if (☃.func_70097_a(☃, (float)☃xx)) {
         if (☃ instanceof EntityLivingBase) {
            EntityLivingBase ☃ = (EntityLivingBase)☃;
            if (!this.field_70170_p.field_72995_K) {
               ☃.func_85034_r(☃.func_85035_bI() + 1);
            }

            if (this.field_70256_ap > 0) {
               float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
               if (☃ > 0.0F) {
                  ☃.func_70024_g(
                     this.field_70159_w * (double)this.field_70256_ap * 0.6F / (double)☃,
                     0.1,
                     this.field_70179_y * (double)this.field_70256_ap * 0.6F / (double)☃
                  );
               }
            }

            if (☃x instanceof EntityLivingBase) {
               EnchantmentHelper.func_151384_a(☃, ☃x);
               EnchantmentHelper.func_151385_b((EntityLivingBase)☃x, ☃);
            }

            this.func_184548_a(☃);
            if (☃x != null && ☃ != ☃x && ☃ instanceof EntityPlayer && ☃x instanceof EntityPlayerMP) {
               ((EntityPlayerMP)☃x).field_71135_a.func_147359_a(new SPacketChangeGameState(6, 0.0F));
            }
         }

         this.func_184185_a(SoundEvents.field_187731_t, 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
         if (!(☃ instanceof EntityEnderman)) {
            this.func_70106_y();
         }
      } else {
         this.field_70159_w *= -0.1F;
         this.field_70181_x *= -0.1F;
         this.field_70179_y *= -0.1F;
         this.field_70177_z += 180.0F;
         this.field_70126_B += 180.0F;
         this.field_70257_an = 0;
         if (!this.field_70170_p.field_72995_K
            && this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y < 0.001F) {
            if (this.field_70251_a == EntityArrow.PickupStatus.ALLOWED) {
               this.func_70099_a(this.func_184550_j(), 0.1F);
            }

            this.func_70106_y();
         }
      }
   }

   protected SoundEvent func_203050_i() {
      return SoundEvents.field_187731_t;
   }

   @Override
   public void func_70091_d(MoverType var1, double var2, double var4, double var6) {
      super.func_70091_d(☃, ☃, ☃, ☃);
      if (this.field_70254_i) {
         this.field_145791_d = MathHelper.func_76128_c(this.field_70165_t);
         this.field_145792_e = MathHelper.func_76128_c(this.field_70163_u);
         this.field_145789_f = MathHelper.func_76128_c(this.field_70161_v);
      }
   }

   protected void func_184548_a(EntityLivingBase var1) {
   }

   @Nullable
   protected Entity func_184551_a(Vec3d var1, Vec3d var2) {
      Entity ☃ = null;
      List<Entity> ☃x = this.field_70170_p
         .func_175674_a(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0), field_184553_f);
      double ☃xx = 0.0;

      for(int ☃xxx = 0; ☃xxx < ☃x.size(); ++☃xxx) {
         Entity ☃xxxx = (Entity)☃x.get(☃xxx);
         if (☃xxxx != this.func_212360_k() || this.field_70257_an >= 5) {
            AxisAlignedBB ☃xxxxx = ☃xxxx.func_174813_aQ().func_186662_g(0.3F);
            RayTraceResult ☃xxxxxx = ☃xxxxx.func_72327_a(☃, ☃);
            if (☃xxxxxx != null) {
               double ☃xxxxxxx = ☃.func_72436_e(☃xxxxxx.field_72307_f);
               if (☃xxxxxxx < ☃xx || ☃xx == 0.0) {
                  ☃ = ☃xxxx;
                  ☃xx = ☃xxxxxxx;
               }
            }
         }
      }

      return ☃;
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74768_a("xTile", this.field_145791_d);
      ☃.func_74768_a("yTile", this.field_145792_e);
      ☃.func_74768_a("zTile", this.field_145789_f);
      ☃.func_74777_a("life", (short)this.field_70252_j);
      if (this.field_195056_av != null) {
         ☃.func_74782_a("inBlockState", NBTUtil.func_190009_a(this.field_195056_av));
      }

      ☃.func_74774_a("shake", (byte)this.field_70249_b);
      ☃.func_74774_a("inGround", (byte)(this.field_70254_i ? 1 : 0));
      ☃.func_74774_a("pickup", (byte)this.field_70251_a.ordinal());
      ☃.func_74780_a("damage", this.field_70255_ao);
      ☃.func_74757_a("crit", this.func_70241_g());
      if (this.field_70250_c != null) {
         ☃.func_186854_a("OwnerUUID", this.field_70250_c);
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.field_145791_d = ☃.func_74762_e("xTile");
      this.field_145792_e = ☃.func_74762_e("yTile");
      this.field_145789_f = ☃.func_74762_e("zTile");
      this.field_70252_j = ☃.func_74765_d("life");
      if (☃.func_150297_b("inBlockState", 10)) {
         this.field_195056_av = NBTUtil.func_190008_d(☃.func_74775_l("inBlockState"));
      }

      this.field_70249_b = ☃.func_74771_c("shake") & 255;
      this.field_70254_i = ☃.func_74771_c("inGround") == 1;
      if (☃.func_150297_b("damage", 99)) {
         this.field_70255_ao = ☃.func_74769_h("damage");
      }

      if (☃.func_150297_b("pickup", 99)) {
         this.field_70251_a = EntityArrow.PickupStatus.func_188795_a(☃.func_74771_c("pickup"));
      } else if (☃.func_150297_b("player", 99)) {
         this.field_70251_a = ☃.func_74767_n("player") ? EntityArrow.PickupStatus.ALLOWED : EntityArrow.PickupStatus.DISALLOWED;
      }

      this.func_70243_d(☃.func_74767_n("crit"));
      if (☃.func_186855_b("OwnerUUID")) {
         this.field_70250_c = ☃.func_186857_a("OwnerUUID");
      }
   }

   public void func_212361_a(@Nullable Entity var1) {
      this.field_70250_c = ☃ == null ? null : ☃.func_110124_au();
   }

   @Nullable
   public Entity func_212360_k() {
      return this.field_70250_c != null && this.field_70170_p instanceof WorldServer
         ? ((WorldServer)this.field_70170_p).func_175733_a(this.field_70250_c)
         : null;
   }

   @Override
   public void func_70100_b_(EntityPlayer var1) {
      if (!this.field_70170_p.field_72995_K && (this.field_70254_i || this.func_203047_q()) && this.field_70249_b <= 0) {
         boolean ☃ = this.field_70251_a == EntityArrow.PickupStatus.ALLOWED
            || this.field_70251_a == EntityArrow.PickupStatus.CREATIVE_ONLY && ☃.field_71075_bZ.field_75098_d
            || this.func_203047_q() && this.func_212360_k().func_110124_au() == ☃.func_110124_au();
         if (this.field_70251_a == EntityArrow.PickupStatus.ALLOWED && !☃.field_71071_by.func_70441_a(this.func_184550_j())) {
            ☃ = false;
         }

         if (☃) {
            ☃.func_71001_a(this, 1);
            this.func_70106_y();
         }
      }
   }

   protected abstract ItemStack func_184550_j();

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   public void func_70239_b(double var1) {
      this.field_70255_ao = ☃;
   }

   public double func_70242_d() {
      return this.field_70255_ao;
   }

   public void func_70240_a(int var1) {
      this.field_70256_ap = ☃;
   }

   @Override
   public boolean func_70075_an() {
      return false;
   }

   @Override
   public float func_70047_e() {
      return 0.0F;
   }

   public void func_70243_d(boolean var1) {
      this.func_203049_a(1, ☃);
   }

   private void func_203049_a(int var1, boolean var2) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184554_g);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184554_g, (byte)(☃ | ☃));
      } else {
         this.field_70180_af.func_187227_b(field_184554_g, (byte)(☃ & ~☃));
      }
   }

   public boolean func_70241_g() {
      byte ☃ = this.field_70180_af.func_187225_a(field_184554_g);
      return (☃ & 1) != 0;
   }

   public void func_190547_a(EntityLivingBase var1, float var2) {
      int ☃ = EnchantmentHelper.func_185284_a(Enchantments.field_185309_u, ☃);
      int ☃x = EnchantmentHelper.func_185284_a(Enchantments.field_185310_v, ☃);
      this.func_70239_b(
         (double)(☃ * 2.0F) + this.field_70146_Z.nextGaussian() * 0.25 + (double)((float)this.field_70170_p.func_175659_aa().func_151525_a() * 0.11F)
      );
      if (☃ > 0) {
         this.func_70239_b(this.func_70242_d() + (double)☃ * 0.5 + 0.5);
      }

      if (☃x > 0) {
         this.func_70240_a(☃x);
      }

      if (EnchantmentHelper.func_185284_a(Enchantments.field_185311_w, ☃) > 0) {
         this.func_70015_d(100);
      }
   }

   protected float func_203044_p() {
      return 0.6F;
   }

   public void func_203045_n(boolean var1) {
      this.field_70145_X = ☃;
      this.func_203049_a(2, ☃);
   }

   public boolean func_203047_q() {
      if (!this.field_70170_p.field_72995_K) {
         return this.field_70145_X;
      } else {
         return (this.field_70180_af.func_187225_a(field_184554_g) & 2) != 0;
      }
   }

   public static enum PickupStatus {
      DISALLOWED,
      ALLOWED,
      CREATIVE_ONLY;

      public static EntityArrow.PickupStatus func_188795_a(int var0) {
         if (☃ < 0 || ☃ > values().length) {
            ☃ = 0;
         }

         return values()[☃];
      }
   }
}
