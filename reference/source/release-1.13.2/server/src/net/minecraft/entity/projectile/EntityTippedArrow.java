package net.minecraft.entity.projectile;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class EntityTippedArrow extends EntityArrow {
   private static final DataParameter<Integer> field_184559_f = EntityDataManager.func_187226_a(EntityTippedArrow.class, DataSerializers.field_187192_b);
   private PotionType field_184560_g = PotionTypes.field_185229_a;
   private final Set<PotionEffect> field_184561_h = Sets.<PotionEffect>newHashSet();
   private boolean field_191509_at;

   public EntityTippedArrow(World var1) {
      super(EntityType.field_200790_d, ☃);
   }

   public EntityTippedArrow(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200790_d, ☃, ☃, ☃, ☃);
   }

   public EntityTippedArrow(World var1, EntityLivingBase var2) {
      super(EntityType.field_200790_d, ☃, ☃);
   }

   public void func_184555_a(ItemStack var1) {
      if (☃.func_77973_b() == Items.field_185167_i) {
         this.field_184560_g = PotionUtils.func_185191_c(☃);
         Collection<PotionEffect> ☃ = PotionUtils.func_185190_b(☃);
         if (!☃.isEmpty()) {
            for(PotionEffect ☃x : ☃) {
               this.field_184561_h.add(new PotionEffect(☃x));
            }
         }

         int ☃ = func_191508_b(☃);
         if (☃ == -1) {
            this.func_190548_o();
         } else {
            this.func_191507_d(☃);
         }
      } else if (☃.func_77973_b() == Items.field_151032_g) {
         this.field_184560_g = PotionTypes.field_185229_a;
         this.field_184561_h.clear();
         this.field_70180_af.func_187227_b(field_184559_f, -1);
      }
   }

   public static int func_191508_b(ItemStack var0) {
      NBTTagCompound ☃ = ☃.func_77978_p();
      return ☃ != null && ☃.func_150297_b("CustomPotionColor", 99) ? ☃.func_74762_e("CustomPotionColor") : -1;
   }

   private void func_190548_o() {
      this.field_191509_at = false;
      this.field_70180_af.func_187227_b(field_184559_f, PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.field_184560_g, this.field_184561_h)));
   }

   public void func_184558_a(PotionEffect var1) {
      this.field_184561_h.add(☃);
      this.func_184212_Q().func_187227_b(field_184559_f, PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.field_184560_g, this.field_184561_h)));
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184559_f, -1);
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70170_p.field_72995_K) {
         if (this.field_70254_i) {
            if (this.field_184552_b % 5 == 0) {
               this.func_184556_b(1);
            }
         } else {
            this.func_184556_b(2);
         }
      } else if (this.field_70254_i && this.field_184552_b != 0 && !this.field_184561_h.isEmpty() && this.field_184552_b >= 600) {
         this.field_70170_p.func_72960_a(this, (byte)0);
         this.field_184560_g = PotionTypes.field_185229_a;
         this.field_184561_h.clear();
         this.field_70180_af.func_187227_b(field_184559_f, -1);
      }
   }

   private void func_184556_b(int var1) {
      int ☃ = this.func_184557_n();
      if (☃ != -1 && ☃ > 0) {
         double ☃x = (double)(☃ >> 16 & 0xFF) / 255.0;
         double ☃xx = (double)(☃ >> 8 & 0xFF) / 255.0;
         double ☃xxx = (double)(☃ >> 0 & 0xFF) / 255.0;

         for(int ☃xxxx = 0; ☃xxxx < ☃; ++☃xxxx) {
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197625_r,
                  this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                  this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O,
                  this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                  ☃x,
                  ☃xx,
                  ☃xxx
               );
         }
      }
   }

   public int func_184557_n() {
      return this.field_70180_af.func_187225_a(field_184559_f);
   }

   private void func_191507_d(int var1) {
      this.field_191509_at = true;
      this.field_70180_af.func_187227_b(field_184559_f, ☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      if (this.field_184560_g != PotionTypes.field_185229_a && this.field_184560_g != null) {
         ☃.func_74778_a("Potion", IRegistry.field_212621_j.func_177774_c(this.field_184560_g).toString());
      }

      if (this.field_191509_at) {
         ☃.func_74768_a("Color", this.func_184557_n());
      }

      if (!this.field_184561_h.isEmpty()) {
         NBTTagList ☃ = new NBTTagList();

         for(PotionEffect ☃x : this.field_184561_h) {
            ☃.add((INBTBase)☃x.func_82719_a(new NBTTagCompound()));
         }

         ☃.func_74782_a("CustomPotionEffects", ☃);
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("Potion", 8)) {
         this.field_184560_g = PotionUtils.func_185187_c(☃);
      }

      for(PotionEffect ☃ : PotionUtils.func_185192_b(☃)) {
         this.func_184558_a(☃);
      }

      if (☃.func_150297_b("Color", 99)) {
         this.func_191507_d(☃.func_74762_e("Color"));
      } else {
         this.func_190548_o();
      }
   }

   @Override
   protected void func_184548_a(EntityLivingBase var1) {
      super.func_184548_a(☃);

      for(PotionEffect ☃ : this.field_184560_g.func_185170_a()) {
         ☃.func_195064_c(new PotionEffect(☃.func_188419_a(), Math.max(☃.func_76459_b() / 8, 1), ☃.func_76458_c(), ☃.func_82720_e(), ☃.func_188418_e()));
      }

      if (!this.field_184561_h.isEmpty()) {
         for(PotionEffect ☃ : this.field_184561_h) {
            ☃.func_195064_c(☃);
         }
      }
   }

   @Override
   protected ItemStack func_184550_j() {
      if (this.field_184561_h.isEmpty() && this.field_184560_g == PotionTypes.field_185229_a) {
         return new ItemStack(Items.field_151032_g);
      } else {
         ItemStack ☃ = new ItemStack(Items.field_185167_i);
         PotionUtils.func_185188_a(☃, this.field_184560_g);
         PotionUtils.func_185184_a(☃, this.field_184561_h);
         if (this.field_191509_at) {
            ☃.func_196082_o().func_74768_a("CustomPotionColor", this.func_184557_n());
         }

         return ☃;
      }
   }
}
