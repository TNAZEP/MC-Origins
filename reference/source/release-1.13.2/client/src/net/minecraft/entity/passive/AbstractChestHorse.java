package net.minecraft.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class AbstractChestHorse extends AbstractHorse {
   private static final DataParameter<Boolean> field_190698_bG = EntityDataManager.func_187226_a(AbstractChestHorse.class, DataSerializers.field_187198_h);

   protected AbstractChestHorse(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_190688_bE = false;
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_190698_bG, false);
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.func_110267_cL());
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.175F);
      this.func_110148_a(field_110271_bv).func_111128_a(0.5);
   }

   public boolean func_190695_dh() {
      return this.field_70180_af.func_187225_a(field_190698_bG);
   }

   public void func_110207_m(boolean var1) {
      this.field_70180_af.func_187227_b(field_190698_bG, ☃);
   }

   @Override
   protected int func_190686_di() {
      return this.func_190695_dh() ? 17 : super.func_190686_di();
   }

   @Override
   public double func_70042_X() {
      return super.func_70042_X() - 0.25;
   }

   @Override
   protected SoundEvent func_184785_dv() {
      super.func_184785_dv();
      return SoundEvents.field_187582_aw;
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      if (this.func_190695_dh()) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_199703_a(Blocks.field_150486_ae);
         }

         this.func_110207_m(false);
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("ChestedHorse", this.func_190695_dh());
      if (this.func_190695_dh()) {
         NBTTagList ☃ = new NBTTagList();

         for(int ☃x = 2; ☃x < this.field_110296_bG.func_70302_i_(); ++☃x) {
            ItemStack ☃xx = this.field_110296_bG.func_70301_a(☃x);
            if (!☃xx.func_190926_b()) {
               NBTTagCompound ☃xxx = new NBTTagCompound();
               ☃xxx.func_74774_a("Slot", (byte)☃x);
               ☃xx.func_77955_b(☃xxx);
               ☃.add((INBTBase)☃xxx);
            }
         }

         ☃.func_74782_a("Items", ☃);
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_110207_m(☃.func_74767_n("ChestedHorse"));
      if (this.func_190695_dh()) {
         NBTTagList ☃ = ☃.func_150295_c("Items", 10);
         this.func_110226_cD();

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            NBTTagCompound ☃xx = ☃.func_150305_b(☃x);
            int ☃xxx = ☃xx.func_74771_c("Slot") & 255;
            if (☃xxx >= 2 && ☃xxx < this.field_110296_bG.func_70302_i_()) {
               this.field_110296_bG.func_70299_a(☃xxx, ItemStack.func_199557_a(☃xx));
            }
         }
      }

      this.func_110232_cE();
   }

   @Override
   public boolean func_174820_d(int var1, ItemStack var2) {
      if (☃ == 499) {
         if (this.func_190695_dh() && ☃.func_190926_b()) {
            this.func_110207_m(false);
            this.func_110226_cD();
            return true;
         }

         if (!this.func_190695_dh() && ☃.func_77973_b() == Blocks.field_150486_ae.func_199767_j()) {
            this.func_110207_m(true);
            this.func_110226_cD();
            return true;
         }
      }

      return super.func_174820_d(☃, ☃);
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() instanceof ItemSpawnEgg) {
         return super.func_184645_a(☃, ☃);
      } else {
         if (!this.func_70631_g_()) {
            if (this.func_110248_bS() && ☃.func_70093_af()) {
               this.func_110199_f(☃);
               return true;
            }

            if (this.func_184207_aI()) {
               return super.func_184645_a(☃, ☃);
            }
         }

         if (!☃.func_190926_b()) {
            boolean ☃ = this.func_190678_b(☃, ☃);
            if (!☃) {
               if (!this.func_110248_bS() || ☃.func_77973_b() == Items.field_151057_cb) {
                  if (☃.func_111282_a(☃, this, ☃)) {
                     return true;
                  } else {
                     this.func_190687_dF();
                     return true;
                  }
               }

               if (!this.func_190695_dh() && ☃.func_77973_b() == Blocks.field_150486_ae.func_199767_j()) {
                  this.func_110207_m(true);
                  this.func_190697_dk();
                  ☃ = true;
                  this.func_110226_cD();
               }

               if (!this.func_70631_g_() && !this.func_110257_ck() && ☃.func_77973_b() == Items.field_151141_av) {
                  this.func_110199_f(☃);
                  return true;
               }
            }

            if (☃) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
               }

               return true;
            }
         }

         if (this.func_70631_g_()) {
            return super.func_184645_a(☃, ☃);
         } else {
            this.func_110237_h(☃);
            return true;
         }
      }
   }

   protected void func_190697_dk() {
      this.func_184185_a(SoundEvents.field_187584_ax, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
   }

   public int func_190696_dl() {
      return 5;
   }
}
