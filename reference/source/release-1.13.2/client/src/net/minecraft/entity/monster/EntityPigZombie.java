package net.minecraft.entity.monster;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPigZombie extends EntityZombie {
   private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final AttributeModifier field_110190_br = new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.05, 0).func_111168_a(false);
   private int field_70837_d;
   private int field_70838_e;
   private UUID field_175459_bn;

   public EntityPigZombie(World var1) {
      super(EntityType.field_200785_Y, ☃);
      this.field_70178_ae = true;
   }

   @Override
   public void func_70604_c(@Nullable EntityLivingBase var1) {
      super.func_70604_c(☃);
      if (☃ != null) {
         this.field_175459_bn = ☃.func_110124_au();
      }
   }

   @Override
   protected void func_175456_n() {
      this.field_70714_bg.func_75776_a(2, new EntityAIZombieAttack(this, 1.0, false));
      this.field_70714_bg.func_75776_a(7, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70715_bh.func_75776_a(1, new EntityPigZombie.AIHurtByAggressor(this));
      this.field_70715_bh.func_75776_a(2, new EntityPigZombie.AITargetAggressor(this));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(field_110186_bp).func_111128_a(0.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23F);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0);
   }

   @Override
   protected boolean func_204703_dA() {
      return false;
   }

   @Override
   protected void func_70619_bc() {
      IAttributeInstance ☃ = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (this.func_175457_ck()) {
         if (!this.func_70631_g_() && !☃.func_180374_a(field_110190_br)) {
            ☃.func_111121_a(field_110190_br);
         }

         --this.field_70837_d;
      } else if (☃.func_180374_a(field_110190_br)) {
         ☃.func_111124_b(field_110190_br);
      }

      if (this.field_70838_e > 0 && --this.field_70838_e == 0) {
         this.func_184185_a(
            SoundEvents.field_187936_hj, this.func_70599_aP() * 2.0F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) * 1.8F
         );
      }

      if (this.field_70837_d > 0 && this.field_175459_bn != null && this.func_70643_av() == null) {
         EntityPlayer ☃ = this.field_70170_p.func_152378_a(this.field_175459_bn);
         this.func_70604_c(☃);
         this.field_70717_bb = ☃;
         this.field_70718_bc = this.func_142015_aE();
      }

      super.func_70619_bc();
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return ☃.func_175659_aa() != EnumDifficulty.PEACEFUL;
   }

   @Override
   public boolean func_205019_a(IWorldReaderBase var1) {
      return ☃.func_195587_c(this, this.func_174813_aQ()) && ☃.func_195586_b(this, this.func_174813_aQ()) && !☃.func_72953_d(this.func_174813_aQ());
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74777_a("Anger", (short)this.field_70837_d);
      if (this.field_175459_bn != null) {
         ☃.func_74778_a("HurtBy", this.field_175459_bn.toString());
      } else {
         ☃.func_74778_a("HurtBy", "");
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_70837_d = ☃.func_74765_d("Anger");
      String ☃ = ☃.func_74779_i("HurtBy");
      if (!☃.isEmpty()) {
         this.field_175459_bn = UUID.fromString(☃);
         EntityPlayer ☃x = this.field_70170_p.func_152378_a(this.field_175459_bn);
         this.func_70604_c(☃x);
         if (☃x != null) {
            this.field_70717_bb = ☃x;
            this.field_70718_bc = this.func_142015_aE();
         }
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         Entity ☃ = ☃.func_76346_g();
         if (☃ instanceof EntityPlayer && !((EntityPlayer)☃).func_184812_l_()) {
            this.func_70835_c(☃);
         }

         return super.func_70097_a(☃, ☃);
      }
   }

   private void func_70835_c(Entity var1) {
      this.field_70837_d = 400 + this.field_70146_Z.nextInt(400);
      this.field_70838_e = this.field_70146_Z.nextInt(40);
      if (☃ instanceof EntityLivingBase) {
         this.func_70604_c((EntityLivingBase)☃);
      }
   }

   public boolean func_175457_ck() {
      return this.field_70837_d > 0;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187935_hi;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187938_hl;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187937_hk;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186384_ai;
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      return false;
   }

   @Override
   protected void func_180481_a(DifficultyInstance var1) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151010_B));
   }

   @Override
   protected ItemStack func_190732_dj() {
      return ItemStack.field_190927_a;
   }

   @Override
   public boolean func_191990_c(EntityPlayer var1) {
      return this.func_175457_ck();
   }

   static class AIHurtByAggressor extends EntityAIHurtByTarget {
      public AIHurtByAggressor(EntityPigZombie var1) {
         super(☃, true);
      }

      @Override
      protected void func_179446_a(EntityCreature var1, EntityLivingBase var2) {
         super.func_179446_a(☃, ☃);
         if (☃ instanceof EntityPigZombie) {
            ((EntityPigZombie)☃).func_70835_c(☃);
         }
      }
   }

   static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer> {
      public AITargetAggressor(EntityPigZombie var1) {
         super(☃, EntityPlayer.class, true);
      }

      @Override
      public boolean func_75250_a() {
         return ((EntityPigZombie)this.field_75299_d).func_175457_ck() && super.func_75250_a();
      }
   }
}
