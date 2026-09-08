package net.minecraft.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTurtle;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public abstract class AbstractSkeleton extends EntityMob implements IRangedAttackMob {
   private static final DataParameter<Boolean> field_184728_b = EntityDataManager.func_187226_a(AbstractSkeleton.class, DataSerializers.field_187198_h);
   private final EntityAIAttackRangedBow<AbstractSkeleton> field_85037_d = new EntityAIAttackRangedBow<>(this, 1.0, 20, 15.0F);
   private final EntityAIAttackMelee field_85038_e = new EntityAIAttackMelee(this, 1.2, false) {
      @Override
      public void func_75251_c() {
         super.func_75251_c();
         AbstractSkeleton.this.func_184724_a(false);
      }

      @Override
      public void func_75249_e() {
         super.func_75249_e();
         AbstractSkeleton.this.func_184724_a(true);
      }
   };

   protected AbstractSkeleton(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(0.6F, 1.99F);
      this.func_85036_m();
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(2, new EntityAIRestrictSun(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIFleeSun(this, 1.0));
      this.field_70714_bg.func_75776_a(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0, 1.2));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityTurtle.class, 10, true, false, EntityTurtle.field_203029_bx));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184728_b, false);
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(this.func_190727_o(), 0.15F, 1.0F);
   }

   abstract SoundEvent func_190727_o();

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.UNDEAD;
   }

   @Override
   public void func_70636_d() {
      boolean ☃ = this.func_204609_dp();
      if (☃) {
         ItemStack ☃x = this.func_184582_a(EntityEquipmentSlot.HEAD);
         if (!☃x.func_190926_b()) {
            if (☃x.func_77984_f()) {
               ☃x.func_196085_b(☃x.func_77952_i() + this.field_70146_Z.nextInt(2));
               if (☃x.func_77952_i() >= ☃x.func_77958_k()) {
                  this.func_70669_a(☃x);
                  this.func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
               }
            }

            ☃ = false;
         }

         if (☃) {
            this.func_70015_d(8);
         }
      }

      super.func_70636_d();
   }

   @Override
   public void func_70098_U() {
      super.func_70098_U();
      if (this.func_184187_bx() instanceof EntityCreature) {
         EntityCreature ☃ = (EntityCreature)this.func_184187_bx();
         this.field_70761_aq = ☃.field_70761_aq;
      }
   }

   @Override
   protected void func_180481_a(DifficultyInstance var1) {
      super.func_180481_a(☃);
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151031_f));
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      this.func_180481_a(☃);
      this.func_180483_b(☃);
      this.func_85036_m();
      this.func_98053_h(this.field_70146_Z.nextFloat() < 0.55F * ☃.func_180170_c());
      if (this.func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
         LocalDate ☃ = LocalDate.now();
         int ☃x = ☃.get(ChronoField.DAY_OF_MONTH);
         int ☃xx = ☃.get(ChronoField.MONTH_OF_YEAR);
         if (☃xx == 10 && ☃x == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
            this.func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack(this.field_70146_Z.nextFloat() < 0.1F ? Blocks.field_196628_cT : Blocks.field_196625_cS));
            this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
         }
      }

      return ☃;
   }

   public void func_85036_m() {
      if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         this.field_70714_bg.func_85156_a(this.field_85038_e);
         this.field_70714_bg.func_85156_a(this.field_85037_d);
         ItemStack ☃ = this.func_184614_ca();
         if (☃.func_77973_b() == Items.field_151031_f) {
            int ☃x = 20;
            if (this.field_70170_p.func_175659_aa() != EnumDifficulty.HARD) {
               ☃x = 40;
            }

            this.field_85037_d.func_189428_b(☃x);
            this.field_70714_bg.func_75776_a(4, this.field_85037_d);
         } else {
            this.field_70714_bg.func_75776_a(4, this.field_85038_e);
         }
      }
   }

   @Override
   public void func_82196_d(EntityLivingBase var1, float var2) {
      EntityArrow ☃ = this.func_190726_a(☃);
      double ☃x = ☃.field_70165_t - this.field_70165_t;
      double ☃xx = ☃.func_174813_aQ().field_72338_b + (double)(☃.field_70131_O / 3.0F) - ☃.field_70163_u;
      double ☃xxx = ☃.field_70161_v - this.field_70161_v;
      double ☃xxxx = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx);
      ☃.func_70186_c(☃x, ☃xx + ☃xxxx * 0.2F, ☃xxx, 1.6F, (float)(14 - this.field_70170_p.func_175659_aa().func_151525_a() * 4));
      this.func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(☃);
   }

   protected EntityArrow func_190726_a(float var1) {
      EntityTippedArrow ☃ = new EntityTippedArrow(this.field_70170_p, this);
      ☃.func_190547_a(this, ☃);
      return ☃;
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_85036_m();
   }

   @Override
   public void func_184201_a(EntityEquipmentSlot var1, ItemStack var2) {
      super.func_184201_a(☃, ☃);
      if (!this.field_70170_p.field_72995_K && ☃ == EntityEquipmentSlot.MAINHAND) {
         this.func_85036_m();
      }
   }

   @Override
   public float func_70047_e() {
      return 1.74F;
   }

   @Override
   public double func_70033_W() {
      return -0.6;
   }

   @Override
   public void func_184724_a(boolean var1) {
      this.field_70180_af.func_187227_b(field_184728_b, ☃);
   }
}
