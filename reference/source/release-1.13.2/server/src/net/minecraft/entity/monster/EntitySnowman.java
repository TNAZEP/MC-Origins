package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
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

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
   private static final DataParameter<Byte> field_184749_a = EntityDataManager.func_187226_a(EntitySnowman.class, DataSerializers.field_187191_a);

   public EntitySnowman(World var1) {
      super(EntityType.field_200745_ak, ☃);
      this.func_70105_a(0.7F, 1.9F);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntityAIAttackRanged(this, 1.25, 20, 10.0F));
      this.field_70714_bg.func_75776_a(2, new EntityAIWanderAvoidWater(this, 1.0, 1.0000001E-5F));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.field_82192_a));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2F);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184749_a, (byte)16);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("Pumpkin", this.func_184748_o());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_74764_b("Pumpkin")) {
         this.func_184747_a(☃.func_74767_n("Pumpkin"));
      }
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         int ☃ = MathHelper.func_76128_c(this.field_70165_t);
         int ☃x = MathHelper.func_76128_c(this.field_70163_u);
         int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
         if (this.func_203008_ap()) {
            this.func_70097_a(DamageSource.field_76369_e, 1.0F);
         }

         if (this.field_70170_p.func_180494_b(new BlockPos(☃, 0, ☃xx)).func_180626_a(new BlockPos(☃, ☃x, ☃xx)) > 1.0F) {
            this.func_70097_a(DamageSource.field_76370_b, 1.0F);
         }

         if (!this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return;
         }

         IBlockState ☃ = Blocks.field_150433_aE.func_176223_P();

         for(int ☃x = 0; ☃x < 4; ++☃x) {
            ☃ = MathHelper.func_76128_c(this.field_70165_t + (double)((float)(☃x % 2 * 2 - 1) * 0.25F));
            ☃x = MathHelper.func_76128_c(this.field_70163_u);
            ☃xx = MathHelper.func_76128_c(this.field_70161_v + (double)((float)(☃x / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos ☃xx = new BlockPos(☃, ☃x, ☃xx);
            if (this.field_70170_p.func_180495_p(☃xx).func_196958_f()
               && this.field_70170_p.func_180494_b(☃xx).func_180626_a(☃xx) < 0.8F
               && ☃.func_196955_c(this.field_70170_p, ☃xx)) {
               this.field_70170_p.func_175656_a(☃xx, ☃);
            }
         }
      }
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186444_z;
   }

   @Override
   public void func_82196_d(EntityLivingBase var1, float var2) {
      EntitySnowball ☃ = new EntitySnowball(this.field_70170_p, this);
      double ☃x = ☃.field_70163_u + (double)☃.func_70047_e() - 1.1F;
      double ☃xx = ☃.field_70165_t - this.field_70165_t;
      double ☃xxx = ☃x - ☃.field_70163_u;
      double ☃xxxx = ☃.field_70161_v - this.field_70161_v;
      float ☃xxxxx = MathHelper.func_76133_a(☃xx * ☃xx + ☃xxxx * ☃xxxx) * 0.2F;
      ☃.func_70186_c(☃xx, ☃xxx + (double)☃xxxxx, ☃xxxx, 1.6F, 12.0F);
      this.func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(☃);
   }

   @Override
   public float func_70047_e() {
      return 1.7F;
   }

   @Override
   protected boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_77973_b() == Items.field_151097_aZ && this.func_184748_o() && !this.field_70170_p.field_72995_K) {
         this.func_184747_a(false);
         ☃.func_77972_a(1, ☃);
      }

      return super.func_184645_a(☃, ☃);
   }

   public boolean func_184748_o() {
      return (this.field_70180_af.func_187225_a(field_184749_a) & 16) != 0;
   }

   public void func_184747_a(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184749_a);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184749_a, (byte)(☃ | 16));
      } else {
         this.field_70180_af.func_187227_b(field_184749_a, (byte)(☃ & -17));
      }
   }

   @Nullable
   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187799_fB;
   }

   @Nullable
   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187803_fD;
   }

   @Nullable
   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187801_fC;
   }

   @Override
   public void func_184724_a(boolean var1) {
   }
}
