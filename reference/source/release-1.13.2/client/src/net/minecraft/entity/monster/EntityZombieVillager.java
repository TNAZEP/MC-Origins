package net.minecraft.entity.monster;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityZombieVillager extends EntityZombie {
   private static final DataParameter<Boolean> field_184739_bx = EntityDataManager.func_187226_a(EntityZombieVillager.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_190739_c = EntityDataManager.func_187226_a(EntityZombieVillager.class, DataSerializers.field_187192_b);
   private int field_82234_d;
   private UUID field_191992_by;

   public EntityZombieVillager(World var1) {
      super(EntityType.field_200727_aF, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184739_bx, false);
      this.field_70180_af.func_187214_a(field_190739_c, 0);
   }

   public void func_190733_a(int var1) {
      this.field_70180_af.func_187227_b(field_190739_c, ☃);
   }

   public int func_190736_dl() {
      return Math.max(this.field_70180_af.func_187225_a(field_190739_c) % 6, 0);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Profession", this.func_190736_dl());
      ☃.func_74768_a("ConversionTime", this.func_82230_o() ? this.field_82234_d : -1);
      if (this.field_191992_by != null) {
         ☃.func_186854_a("ConversionPlayer", this.field_191992_by);
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_190733_a(☃.func_74762_e("Profession"));
      if (☃.func_150297_b("ConversionTime", 99) && ☃.func_74762_e("ConversionTime") > -1) {
         this.func_191991_a(☃.func_186855_b("ConversionPlayer") ? ☃.func_186857_a("ConversionPlayer") : null, ☃.func_74762_e("ConversionTime"));
      }
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      this.func_190733_a(this.field_70170_p.field_73012_v.nextInt(6));
      return super.func_204210_a(☃, ☃, ☃);
   }

   @Override
   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K && this.func_82230_o()) {
         int ☃ = this.func_190735_dq();
         this.field_82234_d -= ☃;
         if (this.field_82234_d <= 0) {
            this.func_190738_dp();
         }
      }

      super.func_70071_h_();
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151153_ao && this.func_70644_a(MobEffects.field_76437_t)) {
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         if (!this.field_70170_p.field_72995_K) {
            this.func_191991_a(☃.func_110124_au(), this.field_70146_Z.nextInt(2401) + 3600);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected boolean func_204703_dA() {
      return false;
   }

   @Override
   public boolean func_70692_ba() {
      return !this.func_82230_o();
   }

   public boolean func_82230_o() {
      return this.func_184212_Q().func_187225_a(field_184739_bx);
   }

   protected void func_191991_a(@Nullable UUID var1, int var2) {
      this.field_191992_by = ☃;
      this.field_82234_d = ☃;
      this.func_184212_Q().func_187227_b(field_184739_bx, true);
      this.func_195063_d(MobEffects.field_76437_t);
      this.func_195064_c(new PotionEffect(MobEffects.field_76420_g, ☃, Math.min(this.field_70170_p.func_175659_aa().func_151525_a() - 1, 0)));
      this.field_70170_p.func_72960_a(this, (byte)16);
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 16) {
         if (!this.func_174814_R()) {
            this.field_70170_p
               .func_184134_a(
                  this.field_70165_t + 0.5,
                  this.field_70163_u + 0.5,
                  this.field_70161_v + 0.5,
                  SoundEvents.field_187942_hp,
                  this.func_184176_by(),
                  1.0F + this.field_70146_Z.nextFloat(),
                  this.field_70146_Z.nextFloat() * 0.7F + 0.3F,
                  false
               );
         }
      } else {
         super.func_70103_a(☃);
      }
   }

   protected void func_190738_dp() {
      EntityVillager ☃ = new EntityVillager(this.field_70170_p);
      ☃.func_82149_j(this);
      ☃.func_70938_b(this.func_190736_dl());
      ☃.func_190672_a(this.field_70170_p.func_175649_E(new BlockPos(☃)), null, null, false);
      ☃.func_82187_q();
      if (this.func_70631_g_()) {
         ☃.func_70873_a(-24000);
      }

      this.field_70170_p.func_72900_e(this);
      ☃.func_94061_f(this.func_175446_cd());
      if (this.func_145818_k_()) {
         ☃.func_200203_b(this.func_200201_e());
         ☃.func_174805_g(this.func_174833_aM());
      }

      this.field_70170_p.func_72838_d(☃);
      if (this.field_191992_by != null) {
         EntityPlayer ☃ = this.field_70170_p.func_152378_a(this.field_191992_by);
         if (☃ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192137_q.func_192183_a((EntityPlayerMP)☃, this, ☃);
         }
      }

      ☃.func_195064_c(new PotionEffect(MobEffects.field_76431_k, 200, 0));
      this.field_70170_p.func_180498_a(null, 1027, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
   }

   protected int func_190735_dq() {
      int ☃ = 1;
      if (this.field_70146_Z.nextFloat() < 0.01F) {
         int ☃x = 0;
         BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();

         for(int ☃xxx = (int)this.field_70165_t - 4; ☃xxx < (int)this.field_70165_t + 4 && ☃x < 14; ++☃xxx) {
            for(int ☃xxxx = (int)this.field_70163_u - 4; ☃xxxx < (int)this.field_70163_u + 4 && ☃x < 14; ++☃xxxx) {
               for(int ☃xxxxx = (int)this.field_70161_v - 4; ☃xxxxx < (int)this.field_70161_v + 4 && ☃x < 14; ++☃xxxxx) {
                  Block ☃xxxxxx = this.field_70170_p.func_180495_p(☃xx.func_181079_c(☃xxx, ☃xxxx, ☃xxxxx)).func_177230_c();
                  if (☃xxxxxx == Blocks.field_150411_aY || ☃xxxxxx instanceof BlockBed) {
                     if (this.field_70146_Z.nextFloat() < 0.3F) {
                        ++☃;
                     }

                     ++☃x;
                  }
               }
            }
         }
      }

      return ☃;
   }

   @Override
   protected float func_70647_i() {
      return this.func_70631_g_()
         ? (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 2.0F
         : (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F;
   }

   @Override
   public SoundEvent func_184639_G() {
      return SoundEvents.field_187940_hn;
   }

   @Override
   public SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187944_hr;
   }

   @Override
   public SoundEvent func_184615_bR() {
      return SoundEvents.field_187943_hq;
   }

   @Override
   public SoundEvent func_190731_di() {
      return SoundEvents.field_187946_ht;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191183_as;
   }

   @Override
   protected ItemStack func_190732_dj() {
      return ItemStack.field_190927_a;
   }
}
