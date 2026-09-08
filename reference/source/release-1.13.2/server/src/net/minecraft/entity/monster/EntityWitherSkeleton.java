package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityWitherSkeleton extends AbstractSkeleton {
   public EntityWitherSkeleton(World var1) {
      super(EntityType.field_200722_aA, ☃);
      this.func_70105_a(0.7F, 2.4F);
      this.field_70178_ae = true;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186386_ak;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_190036_ha;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_190038_hc;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_190037_hb;
   }

   @Override
   SoundEvent func_190727_o() {
      return SoundEvents.field_190039_hd;
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      if (☃.func_76346_g() instanceof EntityCreeper) {
         EntityCreeper ☃ = (EntityCreeper)☃.func_76346_g();
         if (☃.func_70830_n() && ☃.func_70650_aV()) {
            ☃.func_175493_co();
            this.func_199703_a(Items.field_196183_dw);
         }
      }
   }

   @Override
   protected void func_180481_a(DifficultyInstance var1) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151052_q));
   }

   @Override
   protected void func_180483_b(DifficultyInstance var1) {
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      IEntityLivingData ☃ = super.func_204210_a(☃, ☃, ☃);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0);
      this.func_85036_m();
      return ☃;
   }

   @Override
   public float func_70047_e() {
      return 2.1F;
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      if (!super.func_70652_k(☃)) {
         return false;
      } else {
         if (☃ instanceof EntityLivingBase) {
            ((EntityLivingBase)☃).func_195064_c(new PotionEffect(MobEffects.field_82731_v, 200));
         }

         return true;
      }
   }

   @Override
   protected EntityArrow func_190726_a(float var1) {
      EntityArrow ☃ = super.func_190726_a(☃);
      ☃.func_70015_d(100);
      return ☃;
   }
}
