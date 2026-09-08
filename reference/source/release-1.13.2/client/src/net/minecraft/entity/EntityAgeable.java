package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {
   private static final DataParameter<Boolean> field_184751_bv = EntityDataManager.func_187226_a(EntityAgeable.class, DataSerializers.field_187198_h);
   protected int field_175504_a;
   protected int field_175502_b;
   protected int field_175503_c;
   private float field_98056_d = -1.0F;
   private float field_98057_e;

   protected EntityAgeable(EntityType<?> var1, World var2) {
      super(☃, ☃);
   }

   @Nullable
   public abstract EntityAgeable func_90011_a(EntityAgeable var1);

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      Item ☃x = ☃.func_77973_b();
      if (☃x instanceof ItemSpawnEgg && ((ItemSpawnEgg)☃x).func_208077_a(☃.func_77978_p(), this.func_200600_R())) {
         if (!this.field_70170_p.field_72995_K) {
            EntityAgeable ☃xx = this.func_90011_a(this);
            if (☃xx != null) {
               ☃xx.func_70873_a(-24000);
               ☃xx.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0F, 0.0F);
               this.field_70170_p.func_72838_d(☃xx);
               if (☃.func_82837_s()) {
                  ☃xx.func_200203_b(☃.func_200301_q());
               }

               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184751_bv, false);
   }

   public int func_70874_b() {
      if (this.field_70170_p.field_72995_K) {
         return this.field_70180_af.func_187225_a(field_184751_bv) ? -1 : 1;
      } else {
         return this.field_175504_a;
      }
   }

   public void func_175501_a(int var1, boolean var2) {
      int ☃ = this.func_70874_b();
      ☃ += ☃ * 20;
      if (☃ > 0) {
         ☃ = 0;
         if (☃ < 0) {
            this.func_175500_n();
         }
      }

      int ☃ = ☃ - ☃;
      this.func_70873_a(☃);
      if (☃) {
         this.field_175502_b += ☃;
         if (this.field_175503_c == 0) {
            this.field_175503_c = 40;
         }
      }

      if (this.func_70874_b() == 0) {
         this.func_70873_a(this.field_175502_b);
      }
   }

   public void func_110195_a(int var1) {
      this.func_175501_a(☃, false);
   }

   public void func_70873_a(int var1) {
      this.field_70180_af.func_187227_b(field_184751_bv, ☃ < 0);
      this.field_175504_a = ☃;
      this.func_98054_a(this.func_70631_g_());
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Age", this.func_70874_b());
      ☃.func_74768_a("ForcedAge", this.field_175502_b);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_70873_a(☃.func_74762_e("Age"));
      this.field_175502_b = ☃.func_74762_e("ForcedAge");
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184751_bv.equals(☃)) {
         this.func_98054_a(this.func_70631_g_());
      }

      super.func_184206_a(☃);
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (this.field_70170_p.field_72995_K) {
         if (this.field_175503_c > 0) {
            if (this.field_175503_c % 4 == 0) {
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
            }

            --this.field_175503_c;
         }
      } else {
         int ☃ = this.func_70874_b();
         if (☃ < 0) {
            this.func_70873_a(++☃);
            if (☃ == 0) {
               this.func_175500_n();
            }
         } else if (☃ > 0) {
            this.func_70873_a(--☃);
         }
      }
   }

   protected void func_175500_n() {
   }

   @Override
   public boolean func_70631_g_() {
      return this.func_70874_b() < 0;
   }

   public void func_98054_a(boolean var1) {
      this.func_98055_j(☃ ? 0.5F : 1.0F);
   }

   @Override
   protected final void func_70105_a(float var1, float var2) {
      this.field_98056_d = ☃;
      this.field_98057_e = ☃;
      this.func_98055_j(1.0F);
   }

   protected final void func_98055_j(float var1) {
      super.func_70105_a(this.field_98056_d * ☃, this.field_98057_e * ☃);
   }
}
