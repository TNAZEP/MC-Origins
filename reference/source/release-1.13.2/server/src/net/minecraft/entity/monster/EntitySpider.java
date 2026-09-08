package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySpider extends EntityMob {
   private static final DataParameter<Byte> field_184729_a = EntityDataManager.func_187226_a(EntitySpider.class, DataSerializers.field_187191_a);

   protected EntitySpider(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.func_70105_a(1.4F, 0.9F);
   }

   public EntitySpider(World var1) {
      this(EntityType.field_200748_an, ☃);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(3, new EntityAILeapAtTarget(this, 0.4F));
      this.field_70714_bg.func_75776_a(4, new EntitySpider.AISpiderAttack(this));
      this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 0.8));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(2, new EntitySpider.AISpiderTarget(this, EntityPlayer.class));
      this.field_70715_bh.func_75776_a(3, new EntitySpider.AISpiderTarget(this, EntityIronGolem.class));
   }

   @Override
   public double func_70042_X() {
      return (double)(this.field_70131_O * 0.5F);
   }

   @Override
   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateClimber(this, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184729_a, (byte)0);
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K) {
         this.func_70839_e(this.field_70123_F);
      }
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(16.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3F);
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187817_fK;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187821_fM;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187819_fL;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F);
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186435_q;
   }

   @Override
   public boolean func_70617_f_() {
      return this.func_70841_p();
   }

   @Override
   public void func_70110_aj() {
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.ARTHROPOD;
   }

   @Override
   public boolean func_70687_e(PotionEffect var1) {
      return ☃.func_188419_a() == MobEffects.field_76436_u ? false : super.func_70687_e(☃);
   }

   public boolean func_70841_p() {
      return (this.field_70180_af.func_187225_a(field_184729_a) & 1) != 0;
   }

   public void func_70839_e(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184729_a);
      if (☃) {
         ☃ = (byte)(☃ | 1);
      } else {
         ☃ = (byte)(☃ & -2);
      }

      this.field_70180_af.func_187227_b(field_184729_a, ☃);
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      if (this.field_70170_p.field_73012_v.nextInt(100) == 0) {
         EntitySkeleton ☃ = new EntitySkeleton(this.field_70170_p);
         ☃.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
         ☃.func_204210_a(☃, null, null);
         this.field_70170_p.func_72838_d(☃);
         ☃.func_184220_m(this);
      }

      if (☃ == null) {
         ☃ = new EntitySpider.GroupData();
         if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70170_p.field_73012_v.nextFloat() < 0.1F * ☃.func_180170_c()) {
            ((EntitySpider.GroupData)☃).func_111104_a(this.field_70170_p.field_73012_v);
         }
      }

      if (☃ instanceof EntitySpider.GroupData) {
         Potion ☃ = ((EntitySpider.GroupData)☃).field_188478_a;
         if (☃ != null) {
            this.func_195064_c(new PotionEffect(☃, Integer.MAX_VALUE));
         }
      }

      return ☃;
   }

   @Override
   public float func_70047_e() {
      return 0.65F;
   }

   static class AISpiderAttack extends EntityAIAttackMelee {
      public AISpiderAttack(EntitySpider var1) {
         super(☃, 1.0, true);
      }

      @Override
      public boolean func_75253_b() {
         float ☃ = this.field_75441_b.func_70013_c();
         if (☃ >= 0.5F && this.field_75441_b.func_70681_au().nextInt(100) == 0) {
            this.field_75441_b.func_70624_b(null);
            return false;
         } else {
            return super.func_75253_b();
         }
      }

      @Override
      protected double func_179512_a(EntityLivingBase var1) {
         return (double)(4.0F + ☃.field_70130_N);
      }
   }

   static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
      public AISpiderTarget(EntitySpider var1, Class<T> var2) {
         super(☃, ☃, true);
      }

      @Override
      public boolean func_75250_a() {
         float ☃ = this.field_75299_d.func_70013_c();
         return ☃ >= 0.5F ? false : super.func_75250_a();
      }
   }

   public static class GroupData implements IEntityLivingData {
      public Potion field_188478_a;

      public void func_111104_a(Random var1) {
         int ☃ = ☃.nextInt(5);
         if (☃ <= 1) {
            this.field_188478_a = MobEffects.field_76424_c;
         } else if (☃ <= 2) {
            this.field_188478_a = MobEffects.field_76420_g;
         } else if (☃ <= 3) {
            this.field_188478_a = MobEffects.field_76428_l;
         } else if (☃ <= 4) {
            this.field_188478_a = MobEffects.field_76441_p;
         }
      }
   }
}
