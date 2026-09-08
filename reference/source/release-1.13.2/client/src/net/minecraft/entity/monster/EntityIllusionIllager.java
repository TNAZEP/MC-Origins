package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityIllusionIllager extends EntitySpellcasterIllager implements IRangedAttackMob {
   private int field_193099_c;
   private final Vec3d[][] field_193100_bx;

   public EntityIllusionIllager(World var1) {
      super(EntityType.field_200764_D, ☃);
      this.func_70105_a(0.6F, 1.95F);
      this.field_70728_aV = 5;
      this.field_193100_bx = new Vec3d[2][4];

      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.field_193100_bx[0][☃] = new Vec3d(0.0, 0.0, 0.0);
         this.field_193100_bx[1][☃] = new Vec3d(0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntitySpellcasterIllager.AICastingApell());
      this.field_70714_bg.func_75776_a(4, new EntityIllusionIllager.AIMirriorSpell());
      this.field_70714_bg.func_75776_a(5, new EntityIllusionIllager.AIBlindnessSpell());
      this.field_70714_bg.func_75776_a(6, new EntityAIAttackRangedBow<>(this, 0.5, 20, 15.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWander(this, 0.6));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, EntityIllusionIllager.class));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true).func_190882_b(300));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false).func_190882_b(300));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, false).func_190882_b(300));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(18.0);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(32.0);
   }

   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      this.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151031_f));
      return super.func_204210_a(☃, ☃, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
   }

   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186419_a;
   }

   @Override
   public AxisAlignedBB func_184177_bl() {
      return this.func_174813_aQ().func_72314_b(3.0, 0.0, 3.0);
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (this.field_70170_p.field_72995_K && this.func_82150_aj()) {
         --this.field_193099_c;
         if (this.field_193099_c < 0) {
            this.field_193099_c = 0;
         }

         if (this.field_70737_aN == 1 || this.field_70173_aa % 1200 == 0) {
            this.field_193099_c = 3;
            float ☃ = -6.0F;
            int ☃x = 13;

            for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
               this.field_193100_bx[0][☃xx] = this.field_193100_bx[1][☃xx];
               this.field_193100_bx[1][☃xx] = new Vec3d(
                  (double)(-6.0F + (float)this.field_70146_Z.nextInt(13)) * 0.5,
                  (double)Math.max(0, this.field_70146_Z.nextInt(6) - 4),
                  (double)(-6.0F + (float)this.field_70146_Z.nextInt(13)) * 0.5
               );
            }

            for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_197613_f,
                     this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                     this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O,
                     this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            this.field_70170_p
               .func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_193788_dg, this.func_184176_by(), 1.0F, 1.0F, false);
         } else if (this.field_70737_aN == this.field_70738_aO - 1) {
            this.field_193099_c = 3;

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.field_193100_bx[0][☃] = this.field_193100_bx[1][☃];
               this.field_193100_bx[1][☃] = new Vec3d(0.0, 0.0, 0.0);
            }
         }
      }
   }

   public Vec3d[] func_193098_a(float var1) {
      if (this.field_193099_c <= 0) {
         return this.field_193100_bx[1];
      } else {
         double ☃ = (double)(((float)this.field_193099_c - ☃) / 3.0F);
         ☃ = Math.pow(☃, 0.25);
         Vec3d[] ☃x = new Vec3d[4];

         for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
            ☃x[☃xx] = this.field_193100_bx[1][☃xx].func_186678_a(1.0 - ☃).func_178787_e(this.field_193100_bx[0][☃xx].func_186678_a(☃));
         }

         return ☃x;
      }
   }

   @Override
   public boolean func_184191_r(Entity var1) {
      if (super.func_184191_r(☃)) {
         return true;
      } else if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70668_bt() == CreatureAttribute.ILLAGER) {
         return this.func_96124_cp() == null && ☃.func_96124_cp() == null;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_193783_dc;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_193786_de;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_193787_df;
   }

   @Override
   protected SoundEvent func_193086_dk() {
      return SoundEvents.field_193784_dd;
   }

   @Override
   public void func_82196_d(EntityLivingBase var1, float var2) {
      EntityArrow ☃ = this.func_193097_t(☃);
      double ☃x = ☃.field_70165_t - this.field_70165_t;
      double ☃xx = ☃.func_174813_aQ().field_72338_b + (double)(☃.field_70131_O / 3.0F) - ☃.field_70163_u;
      double ☃xxx = ☃.field_70161_v - this.field_70161_v;
      double ☃xxxx = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx);
      ☃.func_70186_c(☃x, ☃xx + ☃xxxx * 0.2F, ☃xxx, 1.6F, (float)(14 - this.field_70170_p.func_175659_aa().func_151525_a() * 4));
      this.func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(☃);
   }

   protected EntityArrow func_193097_t(float var1) {
      EntityTippedArrow ☃ = new EntityTippedArrow(this.field_70170_p, this);
      ☃.func_190547_a(this, ☃);
      return ☃;
   }

   public boolean func_193096_dj() {
      return this.func_193078_a(1);
   }

   @Override
   public void func_184724_a(boolean var1) {
      this.func_193079_a(1, ☃);
   }

   @Override
   public AbstractIllager.IllagerArmPose func_193077_p() {
      if (this.func_193082_dl()) {
         return AbstractIllager.IllagerArmPose.SPELLCASTING;
      } else {
         return this.func_193096_dj() ? AbstractIllager.IllagerArmPose.BOW_AND_ARROW : AbstractIllager.IllagerArmPose.CROSSED;
      }
   }

   class AIBlindnessSpell extends EntitySpellcasterIllager.AIUseSpell {
      private int field_193325_b;

      private AIBlindnessSpell() {
      }

      @Override
      public boolean func_75250_a() {
         if (!super.func_75250_a()) {
            return false;
         } else if (EntityIllusionIllager.this.func_70638_az() == null) {
            return false;
         } else if (EntityIllusionIllager.this.func_70638_az().func_145782_y() == this.field_193325_b) {
            return false;
         } else {
            return EntityIllusionIllager.this.field_70170_p
               .func_175649_E(new BlockPos(EntityIllusionIllager.this))
               .func_193845_a((float)EnumDifficulty.NORMAL.ordinal());
         }
      }

      @Override
      public void func_75249_e() {
         super.func_75249_e();
         this.field_193325_b = EntityIllusionIllager.this.func_70638_az().func_145782_y();
      }

      @Override
      protected int func_190869_f() {
         return 20;
      }

      @Override
      protected int func_190872_i() {
         return 180;
      }

      @Override
      protected void func_190868_j() {
         EntityIllusionIllager.this.func_70638_az().func_195064_c(new PotionEffect(MobEffects.field_76440_q, 400));
      }

      @Override
      protected SoundEvent func_190871_k() {
         return SoundEvents.field_193789_dh;
      }

      @Override
      protected EntitySpellcasterIllager.SpellType func_193320_l() {
         return EntitySpellcasterIllager.SpellType.BLINDNESS;
      }
   }

   class AIMirriorSpell extends EntitySpellcasterIllager.AIUseSpell {
      private AIMirriorSpell() {
      }

      @Override
      public boolean func_75250_a() {
         if (!super.func_75250_a()) {
            return false;
         } else {
            return !EntityIllusionIllager.this.func_70644_a(MobEffects.field_76441_p);
         }
      }

      @Override
      protected int func_190869_f() {
         return 20;
      }

      @Override
      protected int func_190872_i() {
         return 340;
      }

      @Override
      protected void func_190868_j() {
         EntityIllusionIllager.this.func_195064_c(new PotionEffect(MobEffects.field_76441_p, 1200));
      }

      @Nullable
      @Override
      protected SoundEvent func_190871_k() {
         return SoundEvents.field_193790_di;
      }

      @Override
      protected EntitySpellcasterIllager.SpellType func_193320_l() {
         return EntitySpellcasterIllager.SpellType.DISAPPEAR;
      }
   }
}
