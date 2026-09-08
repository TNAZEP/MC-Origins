package net.minecraft.entity.passive;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemFood;
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

public class EntityWolf extends EntityTameable {
   private static final DataParameter<Float> field_184759_bz = EntityDataManager.func_187226_a(EntityWolf.class, DataSerializers.field_187193_c);
   private static final DataParameter<Boolean> field_184760_bA = EntityDataManager.func_187226_a(EntityWolf.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184758_bB = EntityDataManager.func_187226_a(EntityWolf.class, DataSerializers.field_187192_b);
   private float field_70926_e;
   private float field_70924_f;
   private boolean field_70925_g;
   private boolean field_70928_h;
   private float field_70929_i;
   private float field_70927_j;

   public EntityWolf(World var1) {
      super(EntityType.field_200724_aC, ☃);
      this.func_70105_a(0.6F, 0.85F);
      this.func_70903_f(false);
   }

   @Override
   protected void func_184651_r() {
      this.field_70911_d = new EntityAISit(this);
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, this.field_70911_d);
      this.field_70714_bg.func_75776_a(3, new EntityWolf.AIAvoidEntity(this, EntityLlama.class, 24.0F, 1.5, 1.5));
      this.field_70714_bg.func_75776_a(4, new EntityAILeapAtTarget(this, 0.4F));
      this.field_70714_bg.func_75776_a(5, new EntityAIAttackMelee(this, 1.0, true));
      this.field_70714_bg.func_75776_a(6, new EntityAIFollowOwner(this, 1.0, 10.0F, 2.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAIMate(this, 1.0));
      this.field_70714_bg.func_75776_a(8, new EntityAIWanderAvoidWater(this, 1.0));
      this.field_70714_bg.func_75776_a(9, new EntityAIBeg(this, 8.0F));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(10, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIOwnerHurtByTarget(this));
      this.field_70715_bh.func_75776_a(2, new EntityAIOwnerHurtTarget(this));
      this.field_70715_bh.func_75776_a(3, new EntityAIHurtByTarget(this, true));
      this.field_70715_bh
         .func_75776_a(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, var0 -> var0 instanceof EntitySheep || var0 instanceof EntityRabbit));
      this.field_70715_bh.func_75776_a(4, new EntityAITargetNonTamed(this, EntityTurtle.class, false, EntityTurtle.field_203029_bx));
      this.field_70715_bh.func_75776_a(5, new EntityAINearestAttackableTarget(this, AbstractSkeleton.class, false));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3F);
      if (this.func_70909_n()) {
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0);
      } else {
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0);
      }

      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0);
   }

   @Override
   public void func_70624_b(@Nullable EntityLivingBase var1) {
      super.func_70624_b(☃);
      if (☃ == null) {
         this.func_70916_h(false);
      } else if (!this.func_70909_n()) {
         this.func_70916_h(true);
      }
   }

   @Override
   protected void func_70619_bc() {
      this.field_70180_af.func_187227_b(field_184759_bz, this.func_110143_aJ());
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184759_bz, this.func_110143_aJ());
      this.field_70180_af.func_187214_a(field_184760_bA, false);
      this.field_70180_af.func_187214_a(field_184758_bB, EnumDyeColor.RED.func_196059_a());
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_187869_gK, 0.15F, 1.0F);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74757_a("Angry", this.func_70919_bu());
      ☃.func_74774_a("CollarColor", (byte)this.func_175546_cu().func_196059_a());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_70916_h(☃.func_74767_n("Angry"));
      if (☃.func_150297_b("CollarColor", 99)) {
         this.func_175547_a(EnumDyeColor.func_196056_a(☃.func_74762_e("CollarColor")));
      }
   }

   @Override
   protected SoundEvent func_184639_G() {
      if (this.func_70919_bu()) {
         return SoundEvents.field_187861_gG;
      } else if (this.field_70146_Z.nextInt(3) == 0) {
         return this.func_70909_n() && this.field_70180_af.func_187225_a(field_184759_bz) < 10.0F ? SoundEvents.field_187871_gL : SoundEvents.field_187865_gI;
      } else {
         return SoundEvents.field_187857_gE;
      }
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187863_gH;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187859_gF;
   }

   @Override
   protected float func_70599_aP() {
      return 0.4F;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186401_I;
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.field_70925_g && !this.field_70928_h && !this.func_70781_l() && this.field_70122_E) {
         this.field_70928_h = true;
         this.field_70929_i = 0.0F;
         this.field_70927_j = 0.0F;
         this.field_70170_p.func_72960_a(this, (byte)8);
      }

      if (!this.field_70170_p.field_72995_K && this.func_70638_az() == null && this.func_70919_bu()) {
         this.func_70916_h(false);
      }
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_70924_f = this.field_70926_e;
      if (this.func_70922_bv()) {
         this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
      } else {
         this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
      }

      if (this.func_203008_ap()) {
         this.field_70925_g = true;
         this.field_70928_h = false;
         this.field_70929_i = 0.0F;
         this.field_70927_j = 0.0F;
      } else if ((this.field_70925_g || this.field_70928_h) && this.field_70928_h) {
         if (this.field_70929_i == 0.0F) {
            this.func_184185_a(
               SoundEvents.field_187867_gJ, this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F
            );
         }

         this.field_70927_j = this.field_70929_i;
         this.field_70929_i += 0.05F;
         if (this.field_70927_j >= 2.0F) {
            this.field_70925_g = false;
            this.field_70928_h = false;
            this.field_70927_j = 0.0F;
            this.field_70929_i = 0.0F;
         }

         if (this.field_70929_i > 0.4F) {
            float ☃ = (float)this.func_174813_aQ().field_72338_b;
            int ☃x = (int)(MathHelper.func_76126_a((this.field_70929_i - 0.4F) * (float) Math.PI) * 7.0F);

            for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
               float ☃xxx = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N * 0.5F;
               float ☃xxxx = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N * 0.5F;
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_197606_Q,
                     this.field_70165_t + (double)☃xxx,
                     (double)(☃ + 0.8F),
                     this.field_70161_v + (double)☃xxxx,
                     this.field_70159_w,
                     this.field_70181_x,
                     this.field_70179_y
                  );
            }
         }
      }
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O * 0.8F;
   }

   @Override
   public int func_70646_bf() {
      return this.func_70906_o() ? 20 : super.func_70646_bf();
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         Entity ☃ = ☃.func_76346_g();
         if (this.field_70911_d != null) {
            this.field_70911_d.func_75270_a(false);
         }

         if (☃ != null && !(☃ instanceof EntityPlayer) && !(☃ instanceof EntityArrow)) {
            ☃ = (☃ + 1.0F) / 2.0F;
         }

         return super.func_70097_a(☃, ☃);
      }
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      boolean ☃ = ☃.func_70097_a(DamageSource.func_76358_a(this), (float)((int)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e()));
      if (☃) {
         this.func_174815_a(this, ☃);
      }

      return ☃;
   }

   @Override
   public void func_70903_f(boolean var1) {
      super.func_70903_f(☃);
      if (☃) {
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0);
      } else {
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0);
      }

      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0);
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      Item ☃x = ☃.func_77973_b();
      if (this.func_70909_n()) {
         if (!☃.func_190926_b()) {
            if (☃x instanceof ItemFood) {
               ItemFood ☃xx = (ItemFood)☃x;
               if (☃xx.func_77845_h() && this.field_70180_af.func_187225_a(field_184759_bz) < 20.0F) {
                  if (!☃.field_71075_bZ.field_75098_d) {
                     ☃.func_190918_g(1);
                  }

                  this.func_70691_i((float)☃xx.func_150905_g(☃));
                  return true;
               }
            } else if (☃x instanceof ItemDye) {
               EnumDyeColor ☃xx = ((ItemDye)☃x).func_195962_g();
               if (☃xx != this.func_175546_cu()) {
                  this.func_175547_a(☃xx);
                  if (!☃.field_71075_bZ.field_75098_d) {
                     ☃.func_190918_g(1);
                  }

                  return true;
               }
            }
         }

         if (this.func_152114_e(☃) && !this.field_70170_p.field_72995_K && !this.func_70877_b(☃)) {
            this.field_70911_d.func_75270_a(!this.func_70906_o());
            this.field_70703_bu = false;
            this.field_70699_by.func_75499_g();
            this.func_70624_b(null);
         }
      } else if (☃x == Items.field_151103_aS && !this.func_70919_bu()) {
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         if (!this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(3) == 0) {
               this.func_193101_c(☃);
               this.field_70699_by.func_75499_g();
               this.func_70624_b(null);
               this.field_70911_d.func_75270_a(true);
               this.func_70606_j(20.0F);
               this.func_70908_e(true);
               this.field_70170_p.func_72960_a(this, (byte)7);
            } else {
               this.func_70908_e(false);
               this.field_70170_p.func_72960_a(this, (byte)6);
            }
         }

         return true;
      }

      return super.func_184645_a(☃, ☃);
   }

   @Override
   public boolean func_70877_b(ItemStack var1) {
      Item ☃ = ☃.func_77973_b();
      return ☃ instanceof ItemFood && ((ItemFood)☃).func_77845_h();
   }

   @Override
   public int func_70641_bl() {
      return 8;
   }

   public boolean func_70919_bu() {
      return (this.field_70180_af.func_187225_a(field_184755_bv) & 2) != 0;
   }

   public void func_70916_h(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184755_bv);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184755_bv, (byte)(☃ | 2));
      } else {
         this.field_70180_af.func_187227_b(field_184755_bv, (byte)(☃ & -3));
      }
   }

   public EnumDyeColor func_175546_cu() {
      return EnumDyeColor.func_196056_a(this.field_70180_af.func_187225_a(field_184758_bB));
   }

   public void func_175547_a(EnumDyeColor var1) {
      this.field_70180_af.func_187227_b(field_184758_bB, ☃.func_196059_a());
   }

   public EntityWolf func_90011_a(EntityAgeable var1) {
      EntityWolf ☃ = new EntityWolf(this.field_70170_p);
      UUID ☃x = this.func_184753_b();
      if (☃x != null) {
         ☃.func_184754_b(☃x);
         ☃.func_70903_f(true);
      }

      return ☃;
   }

   public void func_70918_i(boolean var1) {
      this.field_70180_af.func_187227_b(field_184760_bA, ☃);
   }

   @Override
   public boolean func_70878_b(EntityAnimal var1) {
      if (☃ == this) {
         return false;
      } else if (!this.func_70909_n()) {
         return false;
      } else if (!(☃ instanceof EntityWolf)) {
         return false;
      } else {
         EntityWolf ☃ = (EntityWolf)☃;
         if (!☃.func_70909_n()) {
            return false;
         } else if (☃.func_70906_o()) {
            return false;
         } else {
            return this.func_70880_s() && ☃.func_70880_s();
         }
      }
   }

   public boolean func_70922_bv() {
      return this.field_70180_af.func_187225_a(field_184760_bA);
   }

   @Override
   public boolean func_142018_a(EntityLivingBase var1, EntityLivingBase var2) {
      if (!(☃ instanceof EntityCreeper) && !(☃ instanceof EntityGhast)) {
         if (☃ instanceof EntityWolf) {
            EntityWolf ☃ = (EntityWolf)☃;
            if (☃.func_70909_n() && ☃.func_70902_q() == ☃) {
               return false;
            }
         }

         if (☃ instanceof EntityPlayer && ☃ instanceof EntityPlayer && !((EntityPlayer)☃).func_96122_a((EntityPlayer)☃)) {
            return false;
         } else {
            return !(☃ instanceof AbstractHorse) || !((AbstractHorse)☃).func_110248_bS();
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean func_184652_a(EntityPlayer var1) {
      return !this.func_70919_bu() && super.func_184652_a(☃);
   }

   class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T> {
      private final EntityWolf field_190856_d;

      public AIAvoidEntity(EntityWolf var2, Class<T> var3, float var4, double var5, double var7) {
         super(☃, ☃, ☃, ☃, ☃);
         this.field_190856_d = ☃;
      }

      @Override
      public boolean func_75250_a() {
         if (super.func_75250_a() && this.field_75376_d instanceof EntityLlama) {
            return !this.field_190856_d.func_70909_n() && this.func_190854_a((EntityLlama)this.field_75376_d);
         } else {
            return false;
         }
      }

      private boolean func_190854_a(EntityLlama var1) {
         return ☃.func_190707_dL() >= EntityWolf.this.field_70146_Z.nextInt(5);
      }

      @Override
      public void func_75249_e() {
         EntityWolf.this.func_70624_b(null);
         super.func_75249_e();
      }

      @Override
      public void func_75246_d() {
         EntityWolf.this.func_70624_b(null);
         super.func_75246_d();
      }
   }
}
