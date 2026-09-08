package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityEvokerFangs extends Entity {
   private int field_190553_a;
   private boolean field_190554_b;
   private int field_190555_c = 22;
   private boolean field_190556_d;
   private EntityLivingBase field_190557_e;
   private UUID field_190558_f;

   public EntityEvokerFangs(World var1) {
      super(EntityType.field_200805_s, ☃);
      this.func_70105_a(0.5F, 0.8F);
   }

   public EntityEvokerFangs(World var1, double var2, double var4, double var6, float var8, int var9, EntityLivingBase var10) {
      this(☃);
      this.field_190553_a = ☃;
      this.func_190549_a(☃);
      this.field_70177_z = ☃ * (180.0F / (float)Math.PI);
      this.func_70107_b(☃, ☃, ☃);
   }

   @Override
   protected void func_70088_a() {
   }

   public void func_190549_a(@Nullable EntityLivingBase var1) {
      this.field_190557_e = ☃;
      this.field_190558_f = ☃ == null ? null : ☃.func_110124_au();
   }

   @Nullable
   public EntityLivingBase func_190552_j() {
      if (this.field_190557_e == null && this.field_190558_f != null && this.field_70170_p instanceof WorldServer) {
         Entity ☃ = ((WorldServer)this.field_70170_p).func_175733_a(this.field_190558_f);
         if (☃ instanceof EntityLivingBase) {
            this.field_190557_e = (EntityLivingBase)☃;
         }
      }

      return this.field_190557_e;
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      this.field_190553_a = ☃.func_74762_e("Warmup");
      if (☃.func_186855_b("OwnerUUID")) {
         this.field_190558_f = ☃.func_186857_a("OwnerUUID");
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      ☃.func_74768_a("Warmup", this.field_190553_a);
      if (this.field_190558_f != null) {
         ☃.func_186854_a("OwnerUUID", this.field_190558_f);
      }
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         if (this.field_190556_d) {
            --this.field_190555_c;
            if (this.field_190555_c == 14) {
               for(int ☃ = 0; ☃ < 12; ++☃) {
                  double ☃x = this.field_70165_t + (this.field_70146_Z.nextDouble() * 2.0 - 1.0) * (double)this.field_70130_N * 0.5;
                  double ☃xx = this.field_70163_u + 0.05 + this.field_70146_Z.nextDouble();
                  double ☃xxx = this.field_70161_v + (this.field_70146_Z.nextDouble() * 2.0 - 1.0) * (double)this.field_70130_N * 0.5;
                  double ☃xxxx = (this.field_70146_Z.nextDouble() * 2.0 - 1.0) * 0.3;
                  double ☃xxxxx = 0.3 + this.field_70146_Z.nextDouble() * 0.3;
                  double ☃xxxxxx = (this.field_70146_Z.nextDouble() * 2.0 - 1.0) * 0.3;
                  this.field_70170_p.func_195594_a(Particles.field_197614_g, ☃x, ☃xx + 1.0, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx);
               }
            }
         }
      } else if (--this.field_190553_a < 0) {
         if (this.field_190553_a == -8) {
            for(EntityLivingBase ☃ : this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ().func_72314_b(0.2, 0.0, 0.2))) {
               this.func_190551_c(☃);
            }
         }

         if (!this.field_190554_b) {
            this.field_70170_p.func_72960_a(this, (byte)4);
            this.field_190554_b = true;
         }

         if (--this.field_190555_c < 0) {
            this.func_70106_y();
         }
      }
   }

   private void func_190551_c(EntityLivingBase var1) {
      EntityLivingBase ☃ = this.func_190552_j();
      if (☃.func_70089_S() && !☃.func_190530_aW() && ☃ != ☃) {
         if (☃ == null) {
            ☃.func_70097_a(DamageSource.field_76376_m, 6.0F);
         } else {
            if (☃.func_184191_r(☃)) {
               return;
            }

            ☃.func_70097_a(DamageSource.func_76354_b(this, ☃), 6.0F);
         }
      }
   }

   @Override
   public void func_70103_a(byte var1) {
      super.func_70103_a(☃);
      if (☃ == 4) {
         this.field_190556_d = true;
         if (!this.func_174814_R()) {
            this.field_70170_p
               .func_184134_a(
                  this.field_70165_t,
                  this.field_70163_u,
                  this.field_70161_v,
                  SoundEvents.field_191242_bl,
                  this.func_184176_by(),
                  1.0F,
                  this.field_70146_Z.nextFloat() * 0.2F + 0.85F,
                  false
               );
         }
      }
   }

   public float func_190550_a(float var1) {
      if (!this.field_190556_d) {
         return 0.0F;
      } else {
         int ☃ = this.field_190555_c - 2;
         return ☃ <= 0 ? 1.0F : 1.0F - ((float)☃ - ☃) / 20.0F;
      }
   }
}
