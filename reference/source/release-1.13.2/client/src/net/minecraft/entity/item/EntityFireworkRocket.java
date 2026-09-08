package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {
   private static final DataParameter<ItemStack> field_184566_a = EntityDataManager.func_187226_a(EntityFireworkRocket.class, DataSerializers.field_187196_f);
   private static final DataParameter<Integer> field_191512_b = EntityDataManager.func_187226_a(EntityFireworkRocket.class, DataSerializers.field_187192_b);
   private int field_92056_a;
   private int field_92055_b;
   private EntityLivingBase field_191513_e;

   public EntityFireworkRocket(World var1) {
      super(EntityType.field_200810_x, ☃);
      this.func_70105_a(0.25F, 0.25F);
   }

   @Override
   protected void func_70088_a() {
      this.field_70180_af.func_187214_a(field_184566_a, ItemStack.field_190927_a);
      this.field_70180_af.func_187214_a(field_191512_b, 0);
   }

   @Override
   public boolean func_70112_a(double var1) {
      return ☃ < 4096.0 && !this.func_191511_j();
   }

   @Override
   public boolean func_145770_h(double var1, double var3, double var5) {
      return super.func_145770_h(☃, ☃, ☃) && !this.func_191511_j();
   }

   public EntityFireworkRocket(World var1, double var2, double var4, double var6, ItemStack var8) {
      super(EntityType.field_200810_x, ☃);
      this.field_92056_a = 0;
      this.func_70105_a(0.25F, 0.25F);
      this.func_70107_b(☃, ☃, ☃);
      int ☃ = 1;
      if (!☃.func_190926_b() && ☃.func_77942_o()) {
         this.field_70180_af.func_187227_b(field_184566_a, ☃.func_77946_l());
         ☃ += ☃.func_190925_c("Fireworks").func_74771_c("Flight");
      }

      this.field_70159_w = this.field_70146_Z.nextGaussian() * 0.001;
      this.field_70179_y = this.field_70146_Z.nextGaussian() * 0.001;
      this.field_70181_x = 0.05;
      this.field_92055_b = 10 * ☃ + this.field_70146_Z.nextInt(6) + this.field_70146_Z.nextInt(7);
   }

   public EntityFireworkRocket(World var1, ItemStack var2, EntityLivingBase var3) {
      this(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃);
      this.field_70180_af.func_187227_b(field_191512_b, ☃.func_145782_y());
      this.field_191513_e = ☃;
   }

   @Override
   public void func_70016_h(double var1, double var3, double var5) {
      this.field_70159_w = ☃;
      this.field_70181_x = ☃;
      this.field_70179_y = ☃;
      if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
         float ☃ = MathHelper.func_76133_a(☃ * ☃ + ☃ * ☃);
         this.field_70177_z = (float)(MathHelper.func_181159_b(☃, ☃) * 180.0F / (float)Math.PI);
         this.field_70125_A = (float)(MathHelper.func_181159_b(☃, (double)☃) * 180.0F / (float)Math.PI);
         this.field_70126_B = this.field_70177_z;
         this.field_70127_C = this.field_70125_A;
      }
   }

   @Override
   public void func_70071_h_() {
      this.field_70142_S = this.field_70165_t;
      this.field_70137_T = this.field_70163_u;
      this.field_70136_U = this.field_70161_v;
      super.func_70071_h_();
      if (this.func_191511_j()) {
         if (this.field_191513_e == null) {
            Entity ☃ = this.field_70170_p.func_73045_a(this.field_70180_af.func_187225_a(field_191512_b));
            if (☃ instanceof EntityLivingBase) {
               this.field_191513_e = (EntityLivingBase)☃;
            }
         }

         if (this.field_191513_e != null) {
            if (this.field_191513_e.func_184613_cA()) {
               Vec3d ☃ = this.field_191513_e.func_70040_Z();
               double ☃x = 1.5;
               double ☃xx = 0.1;
               this.field_191513_e.field_70159_w += ☃.field_72450_a * 0.1 + (☃.field_72450_a * 1.5 - this.field_191513_e.field_70159_w) * 0.5;
               this.field_191513_e.field_70181_x += ☃.field_72448_b * 0.1 + (☃.field_72448_b * 1.5 - this.field_191513_e.field_70181_x) * 0.5;
               this.field_191513_e.field_70179_y += ☃.field_72449_c * 0.1 + (☃.field_72449_c * 1.5 - this.field_191513_e.field_70179_y) * 0.5;
            }

            this.func_70107_b(this.field_191513_e.field_70165_t, this.field_191513_e.field_70163_u, this.field_191513_e.field_70161_v);
            this.field_70159_w = this.field_191513_e.field_70159_w;
            this.field_70181_x = this.field_191513_e.field_70181_x;
            this.field_70179_y = this.field_191513_e.field_70179_y;
         }
      } else {
         this.field_70159_w *= 1.15;
         this.field_70179_y *= 1.15;
         this.field_70181_x += 0.04;
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      }

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
      if (this.field_92056_a == 0 && !this.func_174814_R()) {
         this.field_70170_p
            .func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187631_bo, SoundCategory.AMBIENT, 3.0F, 1.0F);
      }

      ++this.field_92056_a;
      if (this.field_70170_p.field_72995_K && this.field_92056_a % 2 < 2) {
         this.field_70170_p
            .func_195594_a(
               Particles.field_197629_v,
               this.field_70165_t,
               this.field_70163_u - 0.3,
               this.field_70161_v,
               this.field_70146_Z.nextGaussian() * 0.05,
               -this.field_70181_x * 0.5,
               this.field_70146_Z.nextGaussian() * 0.05
            );
      }

      if (!this.field_70170_p.field_72995_K && this.field_92056_a > this.field_92055_b) {
         this.field_70170_p.func_72960_a(this, (byte)17);
         this.func_191510_k();
         this.func_70106_y();
      }
   }

   private void func_191510_k() {
      float ☃ = 0.0F;
      ItemStack ☃x = this.field_70180_af.func_187225_a(field_184566_a);
      NBTTagCompound ☃xx = ☃x.func_190926_b() ? null : ☃x.func_179543_a("Fireworks");
      NBTTagList ☃xxx = ☃xx != null ? ☃xx.func_150295_c("Explosions", 10) : null;
      if (☃xxx != null && !☃xxx.isEmpty()) {
         ☃ = (float)(5 + ☃xxx.size() * 2);
      }

      if (☃ > 0.0F) {
         if (this.field_191513_e != null) {
            this.field_191513_e.func_70097_a(DamageSource.field_191552_t, (float)(5 + ☃xxx.size() * 2));
         }

         double ☃ = 5.0;
         Vec3d ☃x = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);

         for(EntityLivingBase ☃xx : this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_186662_g(5.0))) {
            if (☃xx != this.field_191513_e && !(this.func_70068_e(☃xx) > 25.0)) {
               boolean ☃xxx = false;

               for(int ☃xxxx = 0; ☃xxxx < 2; ++☃xxxx) {
                  RayTraceResult ☃xxxxx = this.field_70170_p
                     .func_200259_a(
                        ☃x,
                        new Vec3d(☃xx.field_70165_t, ☃xx.field_70163_u + (double)☃xx.field_70131_O * 0.5 * (double)☃xxxx, ☃xx.field_70161_v),
                        RayTraceFluidMode.NEVER,
                        true,
                        false
                     );
                  if (☃xxxxx == null || ☃xxxxx.field_72313_a == RayTraceResult.Type.MISS) {
                     ☃xxx = true;
                     break;
                  }
               }

               if (☃xxx) {
                  float ☃xxxx = ☃ * (float)Math.sqrt((5.0 - (double)this.func_70032_d(☃xx)) / 5.0);
                  ☃xx.func_70097_a(DamageSource.field_191552_t, ☃xxxx);
               }
            }
         }
      }
   }

   public boolean func_191511_j() {
      return this.field_70180_af.func_187225_a(field_191512_b) > 0;
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 17 && this.field_70170_p.field_72995_K) {
         ItemStack ☃ = this.field_70180_af.func_187225_a(field_184566_a);
         NBTTagCompound ☃x = ☃.func_190926_b() ? null : ☃.func_179543_a("Fireworks");
         this.field_70170_p
            .func_92088_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, ☃x);
      }

      super.func_70103_a(☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74768_a("Life", this.field_92056_a);
      ☃.func_74768_a("LifeTime", this.field_92055_b);
      ItemStack ☃ = this.field_70180_af.func_187225_a(field_184566_a);
      if (!☃.func_190926_b()) {
         ☃.func_74782_a("FireworksItem", ☃.func_77955_b(new NBTTagCompound()));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.field_92056_a = ☃.func_74762_e("Life");
      this.field_92055_b = ☃.func_74762_e("LifeTime");
      ItemStack ☃ = ItemStack.func_199557_a(☃.func_74775_l("FireworksItem"));
      if (!☃.func_190926_b()) {
         this.field_70180_af.func_187227_b(field_184566_a, ☃);
      }
   }

   @Override
   public boolean func_70075_an() {
      return false;
   }
}
