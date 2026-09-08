package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPig extends EntityAnimal {
   private static final DataParameter<Boolean> field_184763_bv = EntityDataManager.func_187226_a(EntityPig.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_191520_bx = EntityDataManager.func_187226_a(EntityPig.class, DataSerializers.field_187192_b);
   private static final Ingredient field_184764_bw = Ingredient.func_199804_a(Items.field_151172_bF, Items.field_151174_bG, Items.field_185164_cV);
   private boolean field_184765_bx;
   private int field_184766_bz;
   private int field_184767_bA;

   public EntityPig(World var1) {
      super(EntityType.field_200784_X, ☃);
      this.func_70105_a(0.9F, 0.9F);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.25));
      this.field_70714_bg.func_75776_a(3, new EntityAIMate(this, 1.0));
      this.field_70714_bg.func_75776_a(4, new EntityAITempt(this, 1.2, Ingredient.func_199804_a(Items.field_151146_bM), false));
      this.field_70714_bg.func_75776_a(4, new EntityAITempt(this, 1.2, false, field_184764_bw));
      this.field_70714_bg.func_75776_a(5, new EntityAIFollowParent(this, 1.1));
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25);
   }

   @Nullable
   @Override
   public Entity func_184179_bs() {
      return this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
   }

   @Override
   public boolean func_82171_bF() {
      Entity ☃ = this.func_184179_bs();
      if (!(☃ instanceof EntityPlayer)) {
         return false;
      } else {
         EntityPlayer ☃ = (EntityPlayer)☃;
         return ☃.func_184614_ca().func_77973_b() == Items.field_151146_bM || ☃.func_184592_cb().func_77973_b() == Items.field_151146_bM;
      }
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_191520_bx.equals(☃) && this.field_70170_p.field_72995_K) {
         this.field_184765_bx = true;
         this.field_184766_bz = 0;
         this.field_184767_bA = this.field_70180_af.func_187225_a(field_191520_bx);
      }

      super.func_184206_a(☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184763_bv, false);
      this.field_70180_af.func_187214_a(field_191520_bx, 0);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("Saddle", this.func_70901_n());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_70900_e(☃.func_74767_n("Saddle"));
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187697_dL;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187703_dN;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187700_dM;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_187709_dP, 0.15F, 1.0F);
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      if (!super.func_184645_a(☃, ☃)) {
         ItemStack ☃ = ☃.func_184586_b(☃);
         if (☃.func_77973_b() == Items.field_151057_cb) {
            ☃.func_111282_a(☃, this, ☃);
            return true;
         } else if (this.func_70901_n() && !this.func_184207_aI()) {
            if (!this.field_70170_p.field_72995_K) {
               ☃.func_184220_m(this);
            }

            return true;
         } else if (☃.func_77973_b() == Items.field_151141_av) {
            ☃.func_111282_a(☃, this, ☃);
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      super.func_70645_a(☃);
      if (!this.field_70170_p.field_72995_K) {
         if (this.func_70901_n()) {
            this.func_199703_a(Items.field_151141_av);
         }
      }
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186395_C;
   }

   public boolean func_70901_n() {
      return this.field_70180_af.func_187225_a(field_184763_bv);
   }

   public void func_70900_e(boolean var1) {
      if (☃) {
         this.field_70180_af.func_187227_b(field_184763_bv, true);
      } else {
         this.field_70180_af.func_187227_b(field_184763_bv, false);
      }
   }

   @Override
   public void func_70077_a(EntityLightningBolt var1) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         EntityPigZombie ☃ = new EntityPigZombie(this.field_70170_p);
         ☃.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151010_B));
         ☃.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         ☃.func_94061_f(this.func_175446_cd());
         if (this.func_145818_k_()) {
            ☃.func_200203_b(this.func_200201_e());
            ☃.func_174805_g(this.func_174833_aM());
         }

         this.field_70170_p.func_72838_d(☃);
         this.func_70106_y();
      }
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      Entity ☃ = this.func_184188_bt().isEmpty() ? null : (Entity)this.func_184188_bt().get(0);
      if (this.func_184207_aI() && this.func_82171_bF()) {
         this.field_70177_z = ☃.field_70177_z;
         this.field_70126_B = this.field_70177_z;
         this.field_70125_A = ☃.field_70125_A * 0.5F;
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
         this.field_70761_aq = this.field_70177_z;
         this.field_70759_as = this.field_70177_z;
         this.field_70138_W = 1.0F;
         this.field_70747_aH = this.func_70689_ay() * 0.1F;
         if (this.field_184765_bx && this.field_184766_bz++ > this.field_184767_bA) {
            this.field_184765_bx = false;
         }

         if (this.func_184186_bw()) {
            float ☃x = (float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * 0.225F;
            if (this.field_184765_bx) {
               ☃x += ☃x * 1.15F * MathHelper.func_76126_a((float)this.field_184766_bz / (float)this.field_184767_bA * (float) Math.PI);
            }

            this.func_70659_e(☃x);
            super.func_191986_a(0.0F, 0.0F, 1.0F);
         } else {
            this.field_70159_w = 0.0;
            this.field_70181_x = 0.0;
            this.field_70179_y = 0.0;
         }

         this.field_184618_aE = this.field_70721_aZ;
         double ☃x = this.field_70165_t - this.field_70169_q;
         double ☃xx = this.field_70161_v - this.field_70166_s;
         float ☃xxx = MathHelper.func_76133_a(☃x * ☃x + ☃xx * ☃xx) * 4.0F;
         if (☃xxx > 1.0F) {
            ☃xxx = 1.0F;
         }

         this.field_70721_aZ += (☃xxx - this.field_70721_aZ) * 0.4F;
         this.field_184619_aG += this.field_70721_aZ;
      } else {
         this.field_70138_W = 0.5F;
         this.field_70747_aH = 0.02F;
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   public boolean func_184762_da() {
      if (this.field_184765_bx) {
         return false;
      } else {
         this.field_184765_bx = true;
         this.field_184766_bz = 0;
         this.field_184767_bA = this.func_70681_au().nextInt(841) + 140;
         this.func_184212_Q().func_187227_b(field_191520_bx, this.field_184767_bA);
         return true;
      }
   }

   public EntityPig func_90011_a(EntityAgeable var1) {
      return new EntityPig(this.field_70170_p);
   }

   @Override
   public boolean func_70877_b(ItemStack var1) {
      return field_184764_bw.test(☃);
   }
}
