package net.minecraft.entity.projectile;

import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityFishHook extends Entity {
   private static final DataParameter<Integer> field_184528_c = EntityDataManager.func_187226_a(EntityFishHook.class, DataSerializers.field_187192_b);
   private boolean field_146051_au;
   private int field_146049_av;
   private EntityPlayer field_146042_b;
   private int field_146047_aw;
   private int field_146045_ax;
   private int field_146040_ay;
   private int field_146038_az;
   private float field_146054_aA;
   public Entity field_146043_c;
   private EntityFishHook.State field_190627_av = EntityFishHook.State.FLYING;
   private int field_191518_aw;
   private int field_191519_ax;

   private EntityFishHook(World var1) {
      super(EntityType.field_200730_aI, ☃);
   }

   public EntityFishHook(World var1, EntityPlayer var2) {
      this(☃);
      this.func_190626_a(☃);
      this.func_190620_n();
   }

   private void func_190626_a(EntityPlayer var1) {
      this.func_70105_a(0.25F, 0.25F);
      this.field_70158_ak = true;
      this.field_146042_b = ☃;
      this.field_146042_b.field_71104_cf = this;
   }

   public void func_191516_a(int var1) {
      this.field_191519_ax = ☃;
   }

   public void func_191517_b(int var1) {
      this.field_191518_aw = ☃;
   }

   private void func_190620_n() {
      float ☃ = this.field_146042_b.field_70125_A;
      float ☃x = this.field_146042_b.field_70177_z;
      float ☃xx = MathHelper.func_76134_b(-☃x * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃xxx = MathHelper.func_76126_a(-☃x * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃xxxx = -MathHelper.func_76134_b(-☃ * (float) (Math.PI / 180.0));
      float ☃xxxxx = MathHelper.func_76126_a(-☃ * (float) (Math.PI / 180.0));
      double ☃xxxxxx = this.field_146042_b.field_70165_t - (double)☃xxx * 0.3;
      double ☃xxxxxxx = this.field_146042_b.field_70163_u + (double)this.field_146042_b.func_70047_e();
      double ☃xxxxxxxx = this.field_146042_b.field_70161_v - (double)☃xx * 0.3;
      this.func_70012_b(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, ☃x, ☃);
      this.field_70159_w = (double)(-☃xxx);
      this.field_70181_x = (double)MathHelper.func_76131_a(-(☃xxxxx / ☃xxxx), -5.0F, 5.0F);
      this.field_70179_y = (double)(-☃xx);
      float ☃xxxxxxxxx = MathHelper.func_76133_a(
         this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y
      );
      this.field_70159_w *= 0.6 / (double)☃xxxxxxxxx + 0.5 + this.field_70146_Z.nextGaussian() * 0.0045;
      this.field_70181_x *= 0.6 / (double)☃xxxxxxxxx + 0.5 + this.field_70146_Z.nextGaussian() * 0.0045;
      this.field_70179_y *= 0.6 / (double)☃xxxxxxxxx + 0.5 + this.field_70146_Z.nextGaussian() * 0.0045;
      float ☃xxxxxxxxxx = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 180.0F / (float)Math.PI);
      this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, (double)☃xxxxxxxxxx) * 180.0F / (float)Math.PI);
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
   }

   @Override
   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184528_c, 0);
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184528_c.equals(☃)) {
         int ☃ = this.func_184212_Q().func_187225_a(field_184528_c);
         this.field_146043_c = ☃ > 0 ? this.field_70170_p.func_73045_a(☃ - 1) : null;
      }

      super.func_184206_a(☃);
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_146042_b == null) {
         this.func_70106_y();
      } else if (this.field_70170_p.field_72995_K || !this.func_190625_o()) {
         if (this.field_146051_au) {
            ++this.field_146049_av;
            if (this.field_146049_av >= 1200) {
               this.func_70106_y();
               return;
            }
         }

         float ☃ = 0.0F;
         BlockPos ☃x = new BlockPos(this);
         IFluidState ☃xx = this.field_70170_p.func_204610_c(☃x);
         if (☃xx.func_206884_a(FluidTags.field_206959_a)) {
            ☃ = ☃xx.func_206885_f();
         }

         if (this.field_190627_av == EntityFishHook.State.FLYING) {
            if (this.field_146043_c != null) {
               this.field_70159_w = 0.0;
               this.field_70181_x = 0.0;
               this.field_70179_y = 0.0;
               this.field_190627_av = EntityFishHook.State.HOOKED_IN_ENTITY;
               return;
            }

            if (☃ > 0.0F) {
               this.field_70159_w *= 0.3;
               this.field_70181_x *= 0.2;
               this.field_70179_y *= 0.3;
               this.field_190627_av = EntityFishHook.State.BOBBING;
               return;
            }

            if (!this.field_70170_p.field_72995_K) {
               this.func_190624_r();
            }

            if (!this.field_146051_au && !this.field_70122_E && !this.field_70123_F) {
               ++this.field_146047_aw;
            } else {
               this.field_146047_aw = 0;
               this.field_70159_w = 0.0;
               this.field_70181_x = 0.0;
               this.field_70179_y = 0.0;
            }
         } else {
            if (this.field_190627_av == EntityFishHook.State.HOOKED_IN_ENTITY) {
               if (this.field_146043_c != null) {
                  if (this.field_146043_c.field_70128_L) {
                     this.field_146043_c = null;
                     this.field_190627_av = EntityFishHook.State.FLYING;
                  } else {
                     this.field_70165_t = this.field_146043_c.field_70165_t;
                     double var10002 = (double)this.field_146043_c.field_70131_O;
                     this.field_70163_u = this.field_146043_c.func_174813_aQ().field_72338_b + var10002 * 0.8;
                     this.field_70161_v = this.field_146043_c.field_70161_v;
                     this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                  }
               }

               return;
            }

            if (this.field_190627_av == EntityFishHook.State.BOBBING) {
               this.field_70159_w *= 0.9;
               this.field_70179_y *= 0.9;
               double ☃ = this.field_70163_u + this.field_70181_x - (double)☃x.func_177956_o() - (double)☃;
               if (Math.abs(☃) < 0.01) {
                  ☃ += Math.signum(☃) * 0.1;
               }

               this.field_70181_x -= ☃ * (double)this.field_70146_Z.nextFloat() * 0.2;
               if (!this.field_70170_p.field_72995_K && ☃ > 0.0F) {
                  this.func_190621_a(☃x);
               }
            }
         }

         if (!☃xx.func_206884_a(FluidTags.field_206959_a)) {
            this.field_70181_x -= 0.03;
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.func_190623_q();
         double ☃ = 0.92;
         this.field_70159_w *= 0.92;
         this.field_70181_x *= 0.92;
         this.field_70179_y *= 0.92;
         this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      }
   }

   private boolean func_190625_o() {
      ItemStack ☃ = this.field_146042_b.func_184614_ca();
      ItemStack ☃x = this.field_146042_b.func_184592_cb();
      boolean ☃xx = ☃.func_77973_b() == Items.field_151112_aM;
      boolean ☃xxx = ☃x.func_77973_b() == Items.field_151112_aM;
      if (!this.field_146042_b.field_70128_L && this.field_146042_b.func_70089_S() && (☃xx || ☃xxx) && !(this.func_70068_e(this.field_146042_b) > 1024.0)) {
         return false;
      } else {
         this.func_70106_y();
         return true;
      }
   }

   private void func_190623_q() {
      float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70177_z = (float)(MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y) * 180.0F / (float)Math.PI);
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
   }

   private void func_190624_r() {
      Vec3d ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      RayTraceResult ☃xx = this.field_70170_p.func_200259_a(☃, ☃x, RayTraceFluidMode.NEVER, true, false);
      ☃ = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      ☃x = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (☃xx != null) {
         ☃x = new Vec3d(☃xx.field_72307_f.field_72450_a, ☃xx.field_72307_f.field_72448_b, ☃xx.field_72307_f.field_72449_c);
      }

      Entity ☃ = null;
      List<Entity> ☃x = this.field_70170_p
         .func_72839_b(this, this.func_174813_aQ().func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_186662_g(1.0));
      double ☃xx = 0.0;

      for(Entity ☃xxx : ☃x) {
         if (this.func_189739_a(☃xxx) && (☃xxx != this.field_146042_b || this.field_146047_aw >= 5)) {
            AxisAlignedBB ☃xxxx = ☃xxx.func_174813_aQ().func_186662_g(0.3F);
            RayTraceResult ☃xxxxx = ☃xxxx.func_72327_a(☃, ☃x);
            if (☃xxxxx != null) {
               double ☃xxxxxx = ☃.func_72436_e(☃xxxxx.field_72307_f);
               if (☃xxxxxx < ☃xx || ☃xx == 0.0) {
                  ☃ = ☃xxx;
                  ☃xx = ☃xxxxxx;
               }
            }
         }
      }

      if (☃ != null) {
         ☃xx = new RayTraceResult(☃);
      }

      if (☃xx != null && ☃xx.field_72313_a != RayTraceResult.Type.MISS) {
         if (☃xx.field_72313_a == RayTraceResult.Type.ENTITY) {
            this.field_146043_c = ☃xx.field_72308_g;
            this.func_190622_s();
         } else {
            this.field_146051_au = true;
         }
      }
   }

   private void func_190622_s() {
      this.func_184212_Q().func_187227_b(field_184528_c, this.field_146043_c.func_145782_y() + 1);
   }

   private void func_190621_a(BlockPos var1) {
      WorldServer ☃ = (WorldServer)this.field_70170_p;
      int ☃x = 1;
      BlockPos ☃xx = ☃.func_177984_a();
      if (this.field_70146_Z.nextFloat() < 0.25F && this.field_70170_p.func_175727_C(☃xx)) {
         ++☃x;
      }

      if (this.field_70146_Z.nextFloat() < 0.5F && !this.field_70170_p.func_175678_i(☃xx)) {
         --☃x;
      }

      if (this.field_146045_ax > 0) {
         --this.field_146045_ax;
         if (this.field_146045_ax <= 0) {
            this.field_146040_ay = 0;
            this.field_146038_az = 0;
         } else {
            this.field_70181_x -= 0.2 * (double)this.field_70146_Z.nextFloat() * (double)this.field_70146_Z.nextFloat();
         }
      } else if (this.field_146038_az > 0) {
         this.field_146038_az -= ☃x;
         if (this.field_146038_az > 0) {
            this.field_146054_aA = (float)((double)this.field_146054_aA + this.field_70146_Z.nextGaussian() * 4.0);
            float ☃ = this.field_146054_aA * (float) (Math.PI / 180.0);
            float ☃x = MathHelper.func_76126_a(☃);
            float ☃xx = MathHelper.func_76134_b(☃);
            double ☃xxx = this.field_70165_t + (double)(☃x * (float)this.field_146038_az * 0.1F);
            double ☃xxxx = (double)((float)MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) + 1.0F);
            double ☃xxxxx = this.field_70161_v + (double)(☃xx * (float)this.field_146038_az * 0.1F);
            Block ☃xxxxxx = ☃.func_180495_p(new BlockPos(☃xxx, ☃xxxx - 1.0, ☃xxxxx)).func_177230_c();
            if (☃xxxxxx == Blocks.field_150355_j) {
               if (this.field_70146_Z.nextFloat() < 0.15F) {
                  ☃.func_195598_a(Particles.field_197612_e, ☃xxx, ☃xxxx - 0.1F, ☃xxxxx, 1, (double)☃x, 0.1, (double)☃xx, 0.0);
               }

               float ☃xxxxxxx = ☃x * 0.04F;
               float ☃xxxxxxxx = ☃xx * 0.04F;
               ☃.func_195598_a(Particles.field_197630_w, ☃xxx, ☃xxxx, ☃xxxxx, 0, (double)☃xxxxxxxx, 0.01, (double)(-☃xxxxxxx), 1.0);
               ☃.func_195598_a(Particles.field_197630_w, ☃xxx, ☃xxxx, ☃xxxxx, 0, (double)(-☃xxxxxxxx), 0.01, (double)☃xxxxxxx, 1.0);
            }
         } else {
            this.field_70181_x = (double)(-0.4F * MathHelper.func_151240_a(this.field_70146_Z, 0.6F, 1.0F));
            this.func_184185_a(SoundEvents.field_187609_F, 0.25F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
            double ☃ = this.func_174813_aQ().field_72338_b + 0.5;
            ☃.func_195598_a(
               Particles.field_197612_e,
               this.field_70165_t,
               ☃,
               this.field_70161_v,
               (int)(1.0F + this.field_70130_N * 20.0F),
               (double)this.field_70130_N,
               0.0,
               (double)this.field_70130_N,
               0.2F
            );
            ☃.func_195598_a(
               Particles.field_197630_w,
               this.field_70165_t,
               ☃,
               this.field_70161_v,
               (int)(1.0F + this.field_70130_N * 20.0F),
               (double)this.field_70130_N,
               0.0,
               (double)this.field_70130_N,
               0.2F
            );
            this.field_146045_ax = MathHelper.func_76136_a(this.field_70146_Z, 20, 40);
         }
      } else if (this.field_146040_ay > 0) {
         this.field_146040_ay -= ☃x;
         float ☃ = 0.15F;
         if (this.field_146040_ay < 20) {
            ☃ = (float)((double)☃ + (double)(20 - this.field_146040_ay) * 0.05);
         } else if (this.field_146040_ay < 40) {
            ☃ = (float)((double)☃ + (double)(40 - this.field_146040_ay) * 0.02);
         } else if (this.field_146040_ay < 60) {
            ☃ = (float)((double)☃ + (double)(60 - this.field_146040_ay) * 0.01);
         }

         if (this.field_70146_Z.nextFloat() < ☃) {
            float ☃ = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
            float ☃x = MathHelper.func_151240_a(this.field_70146_Z, 25.0F, 60.0F);
            double ☃xx = this.field_70165_t + (double)(MathHelper.func_76126_a(☃) * ☃x * 0.1F);
            double ☃xxx = (double)((float)MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) + 1.0F);
            double ☃xxxx = this.field_70161_v + (double)(MathHelper.func_76134_b(☃) * ☃x * 0.1F);
            Block ☃xxxxx = ☃.func_180495_p(new BlockPos((int)☃xx, (int)☃xxx - 1, (int)☃xxxx)).func_177230_c();
            if (☃xxxxx == Blocks.field_150355_j) {
               ☃.func_195598_a(Particles.field_197606_Q, ☃xx, ☃xxx, ☃xxxx, 2 + this.field_70146_Z.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
            }
         }

         if (this.field_146040_ay <= 0) {
            this.field_146054_aA = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 360.0F);
            this.field_146038_az = MathHelper.func_76136_a(this.field_70146_Z, 20, 80);
         }
      } else {
         this.field_146040_ay = MathHelper.func_76136_a(this.field_70146_Z, 100, 600);
         this.field_146040_ay -= this.field_191519_ax * 20 * 5;
      }
   }

   protected boolean func_189739_a(Entity var1) {
      return ☃.func_70067_L() || ☃ instanceof EntityItem;
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
   }

   public int func_146034_e(ItemStack var1) {
      if (!this.field_70170_p.field_72995_K && this.field_146042_b != null) {
         int ☃ = 0;
         if (this.field_146043_c != null) {
            this.func_184527_k();
            CriteriaTriggers.field_204811_D.func_204820_a((EntityPlayerMP)this.field_146042_b, ☃, this, Collections.emptyList());
            this.field_70170_p.func_72960_a(this, (byte)31);
            ☃ = this.field_146043_c instanceof EntityItem ? 3 : 5;
         } else if (this.field_146045_ax > 0) {
            LootContext.Builder ☃ = new LootContext.Builder((WorldServer)this.field_70170_p).func_204313_a(new BlockPos(this));
            ☃.func_186469_a((float)this.field_191518_aw + this.field_146042_b.func_184817_da());
            List<ItemStack> ☃x = this.field_70170_p
               .func_73046_m()
               .func_200249_aQ()
               .func_186521_a(LootTableList.field_186387_al)
               .func_186462_a(this.field_70146_Z, ☃.func_186471_a());
            CriteriaTriggers.field_204811_D.func_204820_a((EntityPlayerMP)this.field_146042_b, ☃, this, ☃x);

            for(ItemStack ☃xx : ☃x) {
               EntityItem ☃xxx = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, ☃xx);
               double ☃xxxx = this.field_146042_b.field_70165_t - this.field_70165_t;
               double ☃xxxxx = this.field_146042_b.field_70163_u - this.field_70163_u;
               double ☃xxxxxx = this.field_146042_b.field_70161_v - this.field_70161_v;
               double ☃xxxxxxx = (double)MathHelper.func_76133_a(☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx);
               double ☃xxxxxxxx = 0.1;
               ☃xxx.field_70159_w = ☃xxxx * 0.1;
               ☃xxx.field_70181_x = ☃xxxxx * 0.1 + (double)MathHelper.func_76133_a(☃xxxxxxx) * 0.08;
               ☃xxx.field_70179_y = ☃xxxxxx * 0.1;
               this.field_70170_p.func_72838_d(☃xxx);
               this.field_146042_b
                  .field_70170_p
                  .func_72838_d(
                     new EntityXPOrb(
                        this.field_146042_b.field_70170_p,
                        this.field_146042_b.field_70165_t,
                        this.field_146042_b.field_70163_u + 0.5,
                        this.field_146042_b.field_70161_v + 0.5,
                        this.field_70146_Z.nextInt(6) + 1
                     )
                  );
               if (☃xx.func_77973_b().func_206844_a(ItemTags.field_206964_G)) {
                  this.field_146042_b.func_195067_a(StatList.field_188071_E, 1);
               }
            }

            ☃ = 1;
         }

         if (this.field_146051_au) {
            ☃ = 2;
         }

         this.func_70106_y();
         return ☃;
      } else {
         return 0;
      }
   }

   protected void func_184527_k() {
      if (this.field_146042_b != null) {
         double ☃ = this.field_146042_b.field_70165_t - this.field_70165_t;
         double ☃x = this.field_146042_b.field_70163_u - this.field_70163_u;
         double ☃xx = this.field_146042_b.field_70161_v - this.field_70161_v;
         double ☃xxx = 0.1;
         this.field_146043_c.field_70159_w += ☃ * 0.1;
         this.field_146043_c.field_70181_x += ☃x * 0.1;
         this.field_146043_c.field_70179_y += ☃xx * 0.1;
      }
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   public void func_70106_y() {
      super.func_70106_y();
      if (this.field_146042_b != null) {
         this.field_146042_b.field_71104_cf = null;
      }
   }

   public EntityPlayer func_190619_l() {
      return this.field_146042_b;
   }

   @Override
   public boolean func_184222_aU() {
      return false;
   }

   static enum State {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING;
   }
}
