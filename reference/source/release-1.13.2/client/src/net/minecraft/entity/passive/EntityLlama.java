package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILlamaFollowCaravan;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityLlama extends AbstractChestHorse implements IRangedAttackMob {
   private static final DataParameter<Integer> field_190720_bG = EntityDataManager.func_187226_a(EntityLlama.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_190721_bH = EntityDataManager.func_187226_a(EntityLlama.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_190722_bI = EntityDataManager.func_187226_a(EntityLlama.class, DataSerializers.field_187192_b);
   private boolean field_190723_bJ;
   @Nullable
   private EntityLlama field_190724_bK;
   @Nullable
   private EntityLlama field_190725_bL;

   public EntityLlama(World var1) {
      super(EntityType.field_200769_I, ☃);
      this.func_70105_a(0.9F, 1.87F);
   }

   private void func_190706_p(int var1) {
      this.field_70180_af.func_187227_b(field_190720_bG, Math.max(1, Math.min(5, ☃)));
   }

   private void func_190705_dT() {
      int ☃ = this.field_70146_Z.nextFloat() < 0.04F ? 5 : 3;
      this.func_190706_p(1 + this.field_70146_Z.nextInt(☃));
   }

   public int func_190707_dL() {
      return this.field_70180_af.func_187225_a(field_190720_bG);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Variant", this.func_190719_dM());
      ☃.func_74768_a("Strength", this.func_190707_dL());
      if (!this.field_110296_bG.func_70301_a(1).func_190926_b()) {
         ☃.func_74782_a("DecorItem", this.field_110296_bG.func_70301_a(1).func_77955_b(new NBTTagCompound()));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.func_190706_p(☃.func_74762_e("Strength"));
      super.func_70037_a(☃);
      this.func_190710_o(☃.func_74762_e("Variant"));
      if (☃.func_150297_b("DecorItem", 10)) {
         this.field_110296_bG.func_70299_a(1, ItemStack.func_199557_a(☃.func_74775_l("DecorItem")));
      }

      this.func_110232_cE();
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
      this.field_70714_bg.func_75776_a(2, new EntityAILlamaFollowCaravan(this, 2.1F));
      this.field_70714_bg.func_75776_a(3, new EntityAIAttackRanged(this, 1.25, 40, 20.0F));
      this.field_70714_bg.func_75776_a(3, new EntityAIPanic(this, 1.2));
      this.field_70714_bg.func_75776_a(4, new EntityAIMate(this, 1.0));
      this.field_70714_bg.func_75776_a(5, new EntityAIFollowParent(this, 1.0));
      this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 0.7));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.field_70715_bh.func_75776_a(1, new EntityLlama.AIHurtByTarget(this));
      this.field_70715_bh.func_75776_a(2, new EntityLlama.AIDefendTarget(this));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_190720_bG, 0);
      this.field_70180_af.func_187214_a(field_190721_bH, -1);
      this.field_70180_af.func_187214_a(field_190722_bI, 0);
   }

   public int func_190719_dM() {
      return MathHelper.func_76125_a(this.field_70180_af.func_187225_a(field_190722_bI), 0, 3);
   }

   public void func_190710_o(int var1) {
      this.field_70180_af.func_187227_b(field_190722_bI, ☃);
   }

   @Override
   protected int func_190686_di() {
      return this.func_190695_dh() ? 2 + 3 * this.func_190696_dl() : super.func_190686_di();
   }

   @Override
   public void func_184232_k(Entity var1) {
      if (this.func_184196_w(☃)) {
         float ☃ = MathHelper.func_76134_b(this.field_70761_aq * (float) (Math.PI / 180.0));
         float ☃x = MathHelper.func_76126_a(this.field_70761_aq * (float) (Math.PI / 180.0));
         float ☃xx = 0.3F;
         ☃.func_70107_b(
            this.field_70165_t + (double)(0.3F * ☃x), this.field_70163_u + this.func_70042_X() + ☃.func_70033_W(), this.field_70161_v - (double)(0.3F * ☃)
         );
      }
   }

   @Override
   public double func_70042_X() {
      return (double)this.field_70131_O * 0.67;
   }

   @Override
   public boolean func_82171_bF() {
      return false;
   }

   @Override
   protected boolean func_190678_b(EntityPlayer var1, ItemStack var2) {
      int ☃ = 0;
      int ☃x = 0;
      float ☃xx = 0.0F;
      boolean ☃xxx = false;
      Item ☃xxxx = ☃.func_77973_b();
      if (☃xxxx == Items.field_151015_O) {
         ☃ = 10;
         ☃x = 3;
         ☃xx = 2.0F;
      } else if (☃xxxx == Blocks.field_150407_cf.func_199767_j()) {
         ☃ = 90;
         ☃x = 6;
         ☃xx = 10.0F;
         if (this.func_110248_bS() && this.func_70874_b() == 0 && this.func_204701_dC()) {
            ☃xxx = true;
            this.func_146082_f(☃);
         }
      }

      if (this.func_110143_aJ() < this.func_110138_aP() && ☃xx > 0.0F) {
         this.func_70691_i(☃xx);
         ☃xxx = true;
      }

      if (this.func_70631_g_() && ☃ > 0) {
         this.field_70170_p
            .func_195594_a(
               Particles.field_197632_y,
               this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               this.field_70163_u + 0.5 + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O),
               this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               0.0,
               0.0,
               0.0
            );
         if (!this.field_70170_p.field_72995_K) {
            this.func_110195_a(☃);
         }

         ☃xxx = true;
      }

      if (☃x > 0 && (☃xxx || !this.func_110248_bS()) && this.func_110252_cg() < this.func_190676_dC()) {
         ☃xxx = true;
         if (!this.field_70170_p.field_72995_K) {
            this.func_110198_t(☃x);
         }
      }

      if (☃xxx && !this.func_174814_R()) {
         this.field_70170_p
            .func_184148_a(
               null,
               this.field_70165_t,
               this.field_70163_u,
               this.field_70161_v,
               SoundEvents.field_191253_dD,
               this.func_184176_by(),
               1.0F,
               1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F
            );
      }

      return ☃xxx;
   }

   @Override
   protected boolean func_70610_aX() {
      return this.func_110143_aJ() <= 0.0F || this.func_110204_cc();
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      this.func_190705_dT();
      int ☃;
      if (☃ instanceof EntityLlama.GroupData) {
         ☃ = ((EntityLlama.GroupData)☃).field_190886_a;
      } else {
         ☃ = this.field_70146_Z.nextInt(4);
         ☃ = new EntityLlama.GroupData(☃);
      }

      this.func_190710_o(☃);
      return ☃;
   }

   public boolean func_190717_dN() {
      return this.func_190704_dO() != null;
   }

   @Override
   protected SoundEvent func_184785_dv() {
      return SoundEvents.field_191250_dA;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_191260_dz;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_191254_dE;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_191252_dC;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_191256_dG, 0.15F, 1.0F);
   }

   @Override
   protected void func_190697_dk() {
      this.func_184185_a(SoundEvents.field_191251_dB, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
   }

   @Override
   public void func_190687_dF() {
      SoundEvent ☃ = this.func_184785_dv();
      if (☃ != null) {
         this.func_184185_a(☃, this.func_70599_aP(), this.func_70647_i());
      }
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191187_aw;
   }

   @Override
   public int func_190696_dl() {
      return this.func_190707_dL();
   }

   @Override
   public boolean func_190677_dK() {
      return true;
   }

   @Override
   public boolean func_190682_f(ItemStack var1) {
      Item ☃ = ☃.func_77973_b();
      return ItemTags.field_200035_e.func_199685_a_(☃);
   }

   @Override
   public boolean func_190685_dA() {
      return false;
   }

   @Override
   public void func_76316_a(IInventory var1) {
      EnumDyeColor ☃ = this.func_190704_dO();
      super.func_76316_a(☃);
      EnumDyeColor ☃x = this.func_190704_dO();
      if (this.field_70173_aa > 20 && ☃x != null && ☃x != ☃) {
         this.func_184185_a(SoundEvents.field_191257_dH, 0.5F, 1.0F);
      }
   }

   @Override
   protected void func_110232_cE() {
      if (!this.field_70170_p.field_72995_K) {
         super.func_110232_cE();
         this.func_190711_a(func_195403_g(this.field_110296_bG.func_70301_a(1)));
      }
   }

   private void func_190711_a(@Nullable EnumDyeColor var1) {
      this.field_70180_af.func_187227_b(field_190721_bH, ☃ == null ? -1 : ☃.func_196059_a());
   }

   @Nullable
   private static EnumDyeColor func_195403_g(ItemStack var0) {
      Block ☃ = Block.func_149634_a(☃.func_77973_b());
      return ☃ instanceof BlockCarpet ? ((BlockCarpet)☃).func_196547_d() : null;
   }

   @Nullable
   public EnumDyeColor func_190704_dO() {
      int ☃ = this.field_70180_af.func_187225_a(field_190721_bH);
      return ☃ == -1 ? null : EnumDyeColor.func_196056_a(☃);
   }

   @Override
   public int func_190676_dC() {
      return 30;
   }

   @Override
   public boolean func_70878_b(EntityAnimal var1) {
      return ☃ != this && ☃ instanceof EntityLlama && this.func_110200_cJ() && ((EntityLlama)☃).func_110200_cJ();
   }

   public EntityLlama func_90011_a(EntityAgeable var1) {
      EntityLlama ☃ = new EntityLlama(this.field_70170_p);
      this.func_190681_a(☃, ☃);
      EntityLlama ☃x = (EntityLlama)☃;
      int ☃xx = this.field_70146_Z.nextInt(Math.max(this.func_190707_dL(), ☃x.func_190707_dL())) + 1;
      if (this.field_70146_Z.nextFloat() < 0.03F) {
         ++☃xx;
      }

      ☃.func_190706_p(☃xx);
      ☃.func_190710_o(this.field_70146_Z.nextBoolean() ? this.func_190719_dM() : ☃x.func_190719_dM());
      return ☃;
   }

   private void func_190713_e(EntityLivingBase var1) {
      EntityLlamaSpit ☃ = new EntityLlamaSpit(this.field_70170_p, this);
      double ☃x = ☃.field_70165_t - this.field_70165_t;
      double ☃xx = ☃.func_174813_aQ().field_72338_b + (double)(☃.field_70131_O / 3.0F) - ☃.field_70163_u;
      double ☃xxx = ☃.field_70161_v - this.field_70161_v;
      float ☃xxxx = MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx) * 0.2F;
      ☃.func_70186_c(☃x, ☃xx + (double)☃xxxx, ☃xxx, 1.5F, 10.0F);
      this.field_70170_p
         .func_184148_a(
            null,
            this.field_70165_t,
            this.field_70163_u,
            this.field_70161_v,
            SoundEvents.field_191255_dF,
            this.func_184176_by(),
            1.0F,
            1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F
         );
      this.field_70170_p.func_72838_d(☃);
      this.field_190723_bJ = true;
   }

   private void func_190714_x(boolean var1) {
      this.field_190723_bJ = ☃;
   }

   @Override
   public void func_180430_e(float var1, float var2) {
      int ☃ = MathHelper.func_76123_f((☃ * 0.5F - 3.0F) * ☃);
      if (☃ > 0) {
         if (☃ >= 6.0F) {
            this.func_70097_a(DamageSource.field_76379_h, (float)☃);
            if (this.func_184207_aI()) {
               for(Entity ☃x : this.func_184182_bu()) {
                  ☃x.func_70097_a(DamageSource.field_76379_h, (float)☃);
               }
            }
         }

         IBlockState ☃x = this.field_70170_p
            .func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u - 0.2 - (double)this.field_70126_B, this.field_70161_v));
         Block ☃xx = ☃x.func_177230_c();
         if (!☃x.func_196958_f() && !this.func_174814_R()) {
            SoundType ☃xxx = ☃xx.func_185467_w();
            this.field_70170_p
               .func_184148_a(
                  null,
                  this.field_70165_t,
                  this.field_70163_u,
                  this.field_70161_v,
                  ☃xxx.func_185844_d(),
                  this.func_184176_by(),
                  ☃xxx.func_185843_a() * 0.5F,
                  ☃xxx.func_185847_b() * 0.75F
               );
         }
      }
   }

   public void func_190709_dP() {
      if (this.field_190724_bK != null) {
         this.field_190724_bK.field_190725_bL = null;
      }

      this.field_190724_bK = null;
   }

   public void func_190715_a(EntityLlama var1) {
      this.field_190724_bK = ☃;
      this.field_190724_bK.field_190725_bL = this;
   }

   public boolean func_190712_dQ() {
      return this.field_190725_bL != null;
   }

   public boolean func_190718_dR() {
      return this.field_190724_bK != null;
   }

   @Nullable
   public EntityLlama func_190716_dS() {
      return this.field_190724_bK;
   }

   @Override
   protected double func_190634_dg() {
      return 2.0;
   }

   @Override
   protected void func_190679_dD() {
      if (!this.func_190718_dR() && this.func_70631_g_()) {
         super.func_190679_dD();
      }
   }

   @Override
   public boolean func_190684_dE() {
      return false;
   }

   @Override
   public void func_82196_d(EntityLivingBase var1, float var2) {
      this.func_190713_e(☃);
   }

   @Override
   public void func_184724_a(boolean var1) {
   }

   static class AIDefendTarget extends EntityAINearestAttackableTarget<EntityWolf> {
      public AIDefendTarget(EntityLlama var1) {
         super(☃, EntityWolf.class, 16, false, true, null);
      }

      @Override
      public boolean func_75250_a() {
         if (super.func_75250_a() && this.field_75309_a != null && !this.field_75309_a.func_70909_n()) {
            return true;
         } else {
            this.field_75299_d.func_70624_b(null);
            return false;
         }
      }

      @Override
      protected double func_111175_f() {
         return super.func_111175_f() * 0.25;
      }
   }

   static class AIHurtByTarget extends EntityAIHurtByTarget {
      public AIHurtByTarget(EntityLlama var1) {
         super(☃, false);
      }

      @Override
      public boolean func_75253_b() {
         if (this.field_75299_d instanceof EntityLlama) {
            EntityLlama ☃ = (EntityLlama)this.field_75299_d;
            if (☃.field_190723_bJ) {
               ☃.func_190714_x(false);
               return false;
            }
         }

         return super.func_75253_b();
      }
   }

   static class GroupData implements IEntityLivingData {
      public int field_190886_a;

      private GroupData(int var1) {
         this.field_190886_a = ☃;
      }
   }
}
