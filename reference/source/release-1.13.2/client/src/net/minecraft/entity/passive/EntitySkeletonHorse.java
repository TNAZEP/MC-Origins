package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISkeletonRiders;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySkeletonHorse extends AbstractHorse {
   private final EntityAISkeletonRiders field_184792_bN = new EntityAISkeletonRiders(this);
   private boolean field_184793_bU;
   private int field_184794_bV;

   public EntitySkeletonHorse(World var1) {
      super(EntityType.field_200742_ah, ☃);
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(15.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2F);
      this.func_110148_a(field_110271_bv).func_111128_a(this.func_110245_cM());
   }

   @Override
   protected void func_205714_dM() {
   }

   @Override
   protected SoundEvent func_184639_G() {
      super.func_184639_G();
      return this.func_208600_a(FluidTags.field_206959_a) ? SoundEvents.field_206946_il : SoundEvents.field_187858_fe;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      super.func_184615_bR();
      return SoundEvents.field_187860_ff;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      super.func_184601_bQ(☃);
      return SoundEvents.field_187862_fg;
   }

   @Override
   protected SoundEvent func_184184_Z() {
      if (this.field_70122_E) {
         if (!this.func_184207_aI()) {
            return SoundEvents.field_206949_io;
         }

         ++this.field_110285_bP;
         if (this.field_110285_bP > 5 && this.field_110285_bP % 3 == 0) {
            return SoundEvents.field_206947_im;
         }

         if (this.field_110285_bP <= 5) {
            return SoundEvents.field_206949_io;
         }
      }

      return SoundEvents.field_206945_ik;
   }

   @Override
   protected void func_203006_d(float var1) {
      if (this.field_70122_E) {
         super.func_203006_d(0.3F);
      } else {
         super.func_203006_d(Math.min(0.1F, ☃ * 25.0F));
      }
   }

   @Override
   protected void func_205715_ee() {
      if (this.func_70090_H()) {
         this.func_184185_a(SoundEvents.field_206948_in, 0.4F, 1.0F);
      } else {
         super.func_205715_ee();
      }
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.UNDEAD;
   }

   @Override
   public double func_70042_X() {
      return super.func_70042_X() - 0.1875;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186398_F;
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (this.func_190690_dh() && this.field_184794_bV++ >= 18000) {
         this.func_70106_y();
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("SkeletonTrap", this.func_190690_dh());
      ☃.func_74768_a("SkeletonTrapTime", this.field_184794_bV);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_190691_p(☃.func_74767_n("SkeletonTrap"));
      this.field_184794_bV = ☃.func_74762_e("SkeletonTrapTime");
   }

   @Override
   public boolean func_205710_ba() {
      return true;
   }

   @Override
   protected float func_189749_co() {
      return 0.96F;
   }

   public boolean func_190690_dh() {
      return this.field_184793_bU;
   }

   public void func_190691_p(boolean var1) {
      if (☃ != this.field_184793_bU) {
         this.field_184793_bU = ☃;
         if (☃) {
            this.field_70714_bg.func_75776_a(1, this.field_184792_bN);
         } else {
            this.field_70714_bg.func_85156_a(this.field_184792_bN);
         }
      }
   }

   @Nullable
   @Override
   public EntityAgeable func_90011_a(EntityAgeable var1) {
      return new EntitySkeletonHorse(this.field_70170_p);
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() instanceof ItemSpawnEgg) {
         return super.func_184645_a(☃, ☃);
      } else if (!this.func_110248_bS()) {
         return false;
      } else if (this.func_70631_g_()) {
         return super.func_184645_a(☃, ☃);
      } else if (☃.func_70093_af()) {
         this.func_110199_f(☃);
         return true;
      } else if (this.func_184207_aI()) {
         return super.func_184645_a(☃, ☃);
      } else {
         if (!☃.func_190926_b()) {
            if (☃.func_77973_b() == Items.field_151141_av && !this.func_110257_ck()) {
               this.func_110199_f(☃);
               return true;
            }

            if (☃.func_111282_a(☃, this, ☃)) {
               return true;
            }
         }

         this.func_110237_h(☃);
         return true;
      }
   }
}
