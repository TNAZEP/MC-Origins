package net.minecraft.entity.passive;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreathAir;
import net.minecraft.entity.ai.EntityAIFindWater;
import net.minecraft.entity.ai.EntityAIFollowBoat;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIJump;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderSwim;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityDolphinHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityDolphin extends EntityWaterMob {
   private static final DataParameter<BlockPos> field_208014_b = EntityDataManager.func_187226_a(EntityDolphin.class, DataSerializers.field_187200_j);
   private static final DataParameter<Boolean> field_208013_bB = EntityDataManager.func_187226_a(EntityDolphin.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_211138_bB = EntityDataManager.func_187226_a(EntityDolphin.class, DataSerializers.field_187192_b);
   public static final Predicate<EntityItem> field_205025_a = var0 -> !var0.func_174874_s() && var0.func_70089_S() && var0.func_70090_H();

   public EntityDolphin(World var1) {
      super(EntityType.field_205137_n, ☃);
      this.func_70105_a(0.9F, 0.6F);
      this.field_70765_h = new EntityDolphin.MoveHelper(this);
      this.field_70749_g = new EntityDolphinHelper(this, 10);
      this.func_98053_h(true);
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      this.func_70050_g(this.func_205010_bg());
      this.field_70125_A = 0.0F;
      return super.func_204210_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_70648_aU() {
      return false;
   }

   @Override
   protected void func_209207_l(int var1) {
   }

   public void func_208012_g(BlockPos var1) {
      this.field_70180_af.func_187227_b(field_208014_b, ☃);
   }

   public BlockPos func_208010_l() {
      return this.field_70180_af.func_187225_a(field_208014_b);
   }

   public boolean func_208011_dD() {
      return this.field_70180_af.func_187225_a(field_208013_bB);
   }

   public void func_208008_s(boolean var1) {
      this.field_70180_af.func_187227_b(field_208013_bB, ☃);
   }

   public int func_211136_dB() {
      return this.field_70180_af.func_187225_a(field_211138_bB);
   }

   public void func_211137_b(int var1) {
      this.field_70180_af.func_187227_b(field_211138_bB, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_208014_b, BlockPos.field_177992_a);
      this.field_70180_af.func_187214_a(field_208013_bB, false);
      this.field_70180_af.func_187214_a(field_211138_bB, 2400);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("TreasurePosX", this.func_208010_l().func_177958_n());
      ☃.func_74768_a("TreasurePosY", this.func_208010_l().func_177956_o());
      ☃.func_74768_a("TreasurePosZ", this.func_208010_l().func_177952_p());
      ☃.func_74757_a("GotFish", this.func_208011_dD());
      ☃.func_74768_a("Moistness", this.func_211136_dB());
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      int ☃ = ☃.func_74762_e("TreasurePosX");
      int ☃x = ☃.func_74762_e("TreasurePosY");
      int ☃xx = ☃.func_74762_e("TreasurePosZ");
      this.func_208012_g(new BlockPos(☃, ☃x, ☃xx));
      super.func_70037_a(☃);
      this.func_208008_s(☃.func_74767_n("GotFish"));
      this.func_211137_b(☃.func_74762_e("Moistness"));
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAIBreathAir(this));
      this.field_70714_bg.func_75776_a(0, new EntityAIFindWater(this));
      this.field_70714_bg.func_75776_a(1, new EntityDolphin.AISwimToTreasure(this));
      this.field_70714_bg.func_75776_a(2, new EntityDolphin.AISwimWithPlayer(this, 4.0));
      this.field_70714_bg.func_75776_a(4, new EntityAIWanderSwim(this, 1.0, 10));
      this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(5, new EntityAIJump(this, 10));
      this.field_70714_bg.func_75776_a(6, new EntityAIAttackMelee(this, 1.2F, true));
      this.field_70714_bg.func_75776_a(8, new EntityDolphin.AIPlayWithItems());
      this.field_70714_bg.func_75776_a(8, new EntityAIFollowBoat(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIAvoidEntity(this, EntityGuardian.class, 8.0F, 1.0, 1.0));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, EntityGuardian.class));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(1.2F);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0);
   }

   @Override
   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateSwimmer(this, ☃);
   }

   @Override
   public boolean func_70652_k(Entity var1) {
      boolean ☃ = ☃.func_70097_a(DamageSource.func_76358_a(this), (float)((int)this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e()));
      if (☃) {
         this.func_174815_a(this, ☃);
         this.func_184185_a(SoundEvents.field_205205_aV, 1.0F, 1.0F);
      }

      return ☃;
   }

   @Override
   public int func_205010_bg() {
      return 4800;
   }

   @Override
   protected int func_207300_l(int var1) {
      return this.func_205010_bg();
   }

   @Override
   public float func_70047_e() {
      return 0.3F;
   }

   @Override
   public int func_70646_bf() {
      return 1;
   }

   @Override
   public int func_184649_cE() {
      return 1;
   }

   @Override
   protected boolean func_184228_n(Entity var1) {
      return true;
   }

   @Override
   protected void func_175445_a(EntityItem var1) {
      if (this.func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b()) {
         ItemStack ☃ = ☃.func_92059_d();
         if (this.func_175448_a(☃)) {
            this.func_184201_a(EntityEquipmentSlot.MAINHAND, ☃);
            this.field_82174_bp[EntityEquipmentSlot.MAINHAND.func_188454_b()] = 2.0F;
            this.func_71001_a(☃, ☃.func_190916_E());
            ☃.func_70106_y();
         }
      }
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.func_175446_cd()) {
         if (this.func_203008_ap()) {
            this.func_211137_b(2400);
         } else {
            this.func_211137_b(this.func_211136_dB() - 1);
            if (this.func_211136_dB() <= 0) {
               this.func_70097_a(DamageSource.field_205132_u, 1.0F);
            }

            if (this.field_70122_E) {
               this.field_70181_x += 0.5;
               this.field_70159_w += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.2F);
               this.field_70179_y += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.2F);
               this.field_70177_z = this.field_70146_Z.nextFloat() * 360.0F;
               this.field_70122_E = false;
               this.field_70160_al = true;
            }
         }

         if (this.field_70170_p.field_72995_K
            && this.func_70090_H()
            && this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y > 0.03) {
            Vec3d ☃ = this.func_70676_i(0.0F);
            float ☃x = MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0)) * 0.3F;
            float ☃xx = MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0)) * 0.3F;
            float ☃xxx = 1.2F - this.field_70146_Z.nextFloat() * 0.7F;

            for(int ☃xxxx = 0; ☃xxxx < 2; ++☃xxxx) {
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_206864_X,
                     this.field_70165_t - ☃.field_72450_a * (double)☃xxx + (double)☃x,
                     this.field_70163_u - ☃.field_72448_b,
                     this.field_70161_v - ☃.field_72449_c * (double)☃xxx + (double)☃xx,
                     0.0,
                     0.0,
                     0.0
                  );
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_206864_X,
                     this.field_70165_t - ☃.field_72450_a * (double)☃xxx - (double)☃x,
                     this.field_70163_u - ☃.field_72448_b,
                     this.field_70161_v - ☃.field_72449_c * (double)☃xxx - (double)☃xx,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }
   }

   @Override
   public void func_70103_a(byte var1) {
      if (☃ == 38) {
         this.func_208401_a(Particles.field_197632_y);
      } else {
         super.func_70103_a(☃);
      }
   }

   private void func_208401_a(IParticleData var1) {
      for(int ☃ = 0; ☃ < 7; ++☃) {
         double ☃x = this.field_70146_Z.nextGaussian() * 0.01;
         double ☃xx = this.field_70146_Z.nextGaussian() * 0.01;
         double ☃xxx = this.field_70146_Z.nextGaussian() * 0.01;
         this.field_70170_p
            .func_195594_a(
               ☃,
               this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               this.field_70163_u + 0.2F + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O),
               this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
               ☃x,
               ☃xx,
               ☃xxx
            );
      }
   }

   @Override
   protected boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (!☃.func_190926_b() && ☃.func_77973_b().func_206844_a(ItemTags.field_206964_G)) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_184185_a(SoundEvents.field_205207_aX, 1.0F, 1.0F);
         }

         this.func_208008_s(true);
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         return true;
      } else {
         return super.func_184645_a(☃, ☃);
      }
   }

   @Nullable
   public EntityItem func_205024_f(ItemStack var1) {
      if (☃.func_190926_b()) {
         return null;
      } else {
         double ☃ = this.field_70163_u - 0.3F + (double)this.func_70047_e();
         EntityItem ☃x = new EntityItem(this.field_70170_p, this.field_70165_t, ☃, this.field_70161_v, ☃);
         ☃x.func_174867_a(40);
         ☃x.func_200216_c(this.func_110124_au());
         float ☃xx = 0.3F;
         ☃x.field_70159_w = (double)(
            -MathHelper.func_76126_a(this.field_70177_z * (float) (Math.PI / 180.0))
               * MathHelper.func_76134_b(this.field_70125_A * (float) (Math.PI / 180.0))
               * ☃xx
         );
         ☃x.field_70181_x = (double)(MathHelper.func_76126_a(this.field_70125_A * (float) (Math.PI / 180.0)) * ☃xx * 1.5F);
         ☃x.field_70179_y = (double)(
            MathHelper.func_76134_b(this.field_70177_z * (float) (Math.PI / 180.0))
               * MathHelper.func_76134_b(this.field_70125_A * (float) (Math.PI / 180.0))
               * ☃xx
         );
         float ☃xxx = this.field_70146_Z.nextFloat() * (float) (Math.PI * 2);
         ☃xx = 0.02F * this.field_70146_Z.nextFloat();
         ☃x.field_70159_w += (double)(MathHelper.func_76134_b(☃xxx) * ☃xx);
         ☃x.field_70179_y += (double)(MathHelper.func_76126_a(☃xxx) * ☃xx);
         this.field_70170_p.func_72838_d(☃x);
         return ☃x;
      }
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return this.field_70163_u > 45.0 && this.field_70163_u < (double)☃.func_181545_F() && ☃.func_180494_b(new BlockPos(this)) != Biomes.field_76771_b
         || ☃.func_180494_b(new BlockPos(this)) != Biomes.field_150575_M && super.func_205020_a(☃, ☃);
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_205208_aY;
   }

   @Nullable
   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_205206_aW;
   }

   @Nullable
   @Override
   protected SoundEvent func_184639_G() {
      return this.func_70090_H() ? SoundEvents.field_205204_aU : SoundEvents.field_205203_aT;
   }

   @Override
   protected SoundEvent func_184181_aa() {
      return SoundEvents.field_205212_bc;
   }

   @Override
   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_205211_bb;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_205214_aN;
   }

   protected boolean func_208006_dE() {
      BlockPos ☃ = this.func_70661_as().func_208485_j();
      if (☃ != null) {
         return this.func_174818_b(☃) < 144.0;
      } else {
         return false;
      }
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      if (this.func_70613_aW() && this.func_70090_H()) {
         this.func_191958_b(☃, ☃, ☃, this.func_70689_ay());
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.9F;
         this.field_70181_x *= 0.9F;
         this.field_70179_y *= 0.9F;
         if (this.func_70638_az() == null) {
            this.field_70181_x -= 0.005;
         }
      } else {
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_184652_a(EntityPlayer var1) {
      return true;
   }

   class AIPlayWithItems extends EntityAIBase {
      private int field_205154_b;

      private AIPlayWithItems() {
      }

      @Override
      public boolean func_75250_a() {
         if (this.field_205154_b > EntityDolphin.this.field_70173_aa) {
            return false;
         } else {
            List<EntityItem> ☃ = EntityDolphin.this.field_70170_p
               .func_175647_a(EntityItem.class, EntityDolphin.this.func_174813_aQ().func_72314_b(8.0, 8.0, 8.0), EntityDolphin.field_205025_a);
            return !☃.isEmpty() || !EntityDolphin.this.func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b();
         }
      }

      @Override
      public void func_75249_e() {
         List<EntityItem> ☃ = EntityDolphin.this.field_70170_p
            .func_175647_a(EntityItem.class, EntityDolphin.this.func_174813_aQ().func_72314_b(8.0, 8.0, 8.0), EntityDolphin.field_205025_a);
         if (!☃.isEmpty()) {
            EntityDolphin.this.func_70661_as().func_75497_a((Entity)☃.get(0), 1.2F);
            EntityDolphin.this.func_184185_a(SoundEvents.field_205210_ba, 1.0F, 1.0F);
         }

         this.field_205154_b = 0;
      }

      @Override
      public void func_75251_c() {
         ItemStack ☃ = EntityDolphin.this.func_184582_a(EntityEquipmentSlot.MAINHAND);
         if (!☃.func_190926_b()) {
            EntityDolphin.this.func_205024_f(☃);
            EntityDolphin.this.func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
            this.field_205154_b = EntityDolphin.this.field_70173_aa + EntityDolphin.this.field_70146_Z.nextInt(100);
         }
      }

      @Override
      public void func_75246_d() {
         List<EntityItem> ☃ = EntityDolphin.this.field_70170_p
            .func_175647_a(EntityItem.class, EntityDolphin.this.func_174813_aQ().func_72314_b(8.0, 8.0, 8.0), EntityDolphin.field_205025_a);
         ItemStack ☃x = EntityDolphin.this.func_184582_a(EntityEquipmentSlot.MAINHAND);
         if (!☃x.func_190926_b()) {
            EntityDolphin.this.func_205024_f(☃x);
            EntityDolphin.this.func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
         } else if (!☃.isEmpty()) {
            EntityDolphin.this.func_70661_as().func_75497_a((Entity)☃.get(0), 1.2F);
         }
      }
   }

   static class AISwimToTreasure extends EntityAIBase {
      private final EntityDolphin field_208057_a;
      private boolean field_208058_b;

      AISwimToTreasure(EntityDolphin var1) {
         this.field_208057_a = ☃;
         this.func_75248_a(3);
      }

      @Override
      public boolean func_75252_g() {
         return false;
      }

      @Override
      public boolean func_75250_a() {
         return this.field_208057_a.func_208011_dD() && this.field_208057_a.func_70086_ai() >= 100;
      }

      @Override
      public boolean func_75253_b() {
         BlockPos ☃ = this.field_208057_a.func_208010_l();
         return this.field_208057_a.func_174818_b(new BlockPos((double)☃.func_177958_n(), this.field_208057_a.field_70163_u, (double)☃.func_177952_p())) > 16.0
            && !this.field_208058_b
            && this.field_208057_a.func_70086_ai() >= 100;
      }

      @Override
      public void func_75249_e() {
         this.field_208058_b = false;
         this.field_208057_a.func_70661_as().func_75499_g();
         World ☃ = this.field_208057_a.field_70170_p;
         BlockPos ☃x = new BlockPos(this.field_208057_a);
         String ☃xx = (double)☃.field_73012_v.nextFloat() >= 0.5 ? "Ocean_Ruin" : "Shipwreck";
         BlockPos ☃xxx = ☃.func_211157_a(☃xx, ☃x, 50, false);
         if (☃xxx == null) {
            BlockPos ☃xxxx = ☃.func_211157_a(☃xx.equals("Ocean_Ruin") ? "Shipwreck" : "Ocean_Ruin", ☃x, 50, false);
            if (☃xxxx == null) {
               this.field_208058_b = true;
               return;
            }

            this.field_208057_a.func_208012_g(☃xxxx);
         } else {
            this.field_208057_a.func_208012_g(☃xxx);
         }

         ☃.func_72960_a(this.field_208057_a, (byte)38);
      }

      @Override
      public void func_75251_c() {
         BlockPos ☃ = this.field_208057_a.func_208010_l();
         if (this.field_208057_a.func_174818_b(new BlockPos((double)☃.func_177958_n(), this.field_208057_a.field_70163_u, (double)☃.func_177952_p())) <= 16.0
            || this.field_208058_b) {
            this.field_208057_a.func_208008_s(false);
         }
      }

      @Override
      public void func_75246_d() {
         BlockPos ☃ = this.field_208057_a.func_208010_l();
         World ☃x = this.field_208057_a.field_70170_p;
         if (this.field_208057_a.func_208006_dE() || this.field_208057_a.func_70661_as().func_75500_f()) {
            Vec3d ☃xx = RandomPositionGenerator.func_203155_a(
               this.field_208057_a, 16, 1, new Vec3d((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p()), (float) (Math.PI / 8)
            );
            if (☃xx == null) {
               ☃xx = RandomPositionGenerator.func_75464_a(
                  this.field_208057_a, 8, 4, new Vec3d((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p())
               );
            }

            if (☃xx != null) {
               BlockPos ☃xx = new BlockPos(☃xx);
               if (!☃x.func_204610_c(☃xx).func_206884_a(FluidTags.field_206959_a) || !☃x.func_180495_p(☃xx).func_196957_g(☃x, ☃xx, PathType.WATER)) {
                  ☃xx = RandomPositionGenerator.func_75464_a(
                     this.field_208057_a, 8, 5, new Vec3d((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p())
                  );
               }
            }

            if (☃xx == null) {
               this.field_208058_b = true;
               return;
            }

            this.field_208057_a
               .func_70671_ap()
               .func_75650_a(
                  ☃xx.field_72450_a,
                  ☃xx.field_72448_b,
                  ☃xx.field_72449_c,
                  (float)(this.field_208057_a.func_184649_cE() + 20),
                  (float)this.field_208057_a.func_70646_bf()
               );
            this.field_208057_a.func_70661_as().func_75492_a(☃xx.field_72450_a, ☃xx.field_72448_b, ☃xx.field_72449_c, 1.3);
            if (☃x.field_73012_v.nextInt(80) == 0) {
               ☃x.func_72960_a(this.field_208057_a, (byte)38);
            }
         }
      }
   }

   static class AISwimWithPlayer extends EntityAIBase {
      private final EntityDolphin field_206834_a;
      private final double field_206835_b;
      private EntityPlayer field_206836_c;

      AISwimWithPlayer(EntityDolphin var1, double var2) {
         this.field_206834_a = ☃;
         this.field_206835_b = ☃;
         this.func_75248_a(3);
      }

      @Override
      public boolean func_75250_a() {
         this.field_206836_c = this.field_206834_a.field_70170_p.func_72890_a(this.field_206834_a, 10.0);
         return this.field_206836_c == null ? false : this.field_206836_c.func_203007_ba();
      }

      @Override
      public boolean func_75253_b() {
         return this.field_206836_c != null && this.field_206836_c.func_203007_ba() && this.field_206834_a.func_70068_e(this.field_206836_c) < 256.0;
      }

      @Override
      public void func_75249_e() {
         this.field_206836_c.func_195064_c(new PotionEffect(MobEffects.field_206827_D, 100));
      }

      @Override
      public void func_75251_c() {
         this.field_206836_c = null;
         this.field_206834_a.func_70661_as().func_75499_g();
      }

      @Override
      public void func_75246_d() {
         this.field_206834_a
            .func_70671_ap()
            .func_75651_a(this.field_206836_c, (float)(this.field_206834_a.func_184649_cE() + 20), (float)this.field_206834_a.func_70646_bf());
         if (this.field_206834_a.func_70068_e(this.field_206836_c) < 6.25) {
            this.field_206834_a.func_70661_as().func_75499_g();
         } else {
            this.field_206834_a.func_70661_as().func_75497_a(this.field_206836_c, this.field_206835_b);
         }

         if (this.field_206836_c.func_203007_ba() && this.field_206836_c.field_70170_p.field_73012_v.nextInt(6) == 0) {
            this.field_206836_c.func_195064_c(new PotionEffect(MobEffects.field_206827_D, 100));
         }
      }
   }

   static class MoveHelper extends EntityMoveHelper {
      private final EntityDolphin field_205138_i;

      public MoveHelper(EntityDolphin var1) {
         super(☃);
         this.field_205138_i = ☃;
      }

      @Override
      public void func_75641_c() {
         if (this.field_205138_i.func_70090_H()) {
            this.field_205138_i.field_70181_x += 0.005;
         }

         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && !this.field_205138_i.func_70661_as().func_75500_f()) {
            double ☃ = this.field_75646_b - this.field_205138_i.field_70165_t;
            double ☃x = this.field_75647_c - this.field_205138_i.field_70163_u;
            double ☃xx = this.field_75644_d - this.field_205138_i.field_70161_v;
            double ☃xxx = ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
            if (☃xxx < 2.5000003E-7F) {
               this.field_75648_a.func_191989_p(0.0F);
            } else {
               float ☃ = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) - 90.0F;
               this.field_205138_i.field_70177_z = this.func_75639_a(this.field_205138_i.field_70177_z, ☃, 10.0F);
               this.field_205138_i.field_70761_aq = this.field_205138_i.field_70177_z;
               this.field_205138_i.field_70759_as = this.field_205138_i.field_70177_z;
               float ☃x = (float)(this.field_75645_e * this.field_205138_i.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
               if (this.field_205138_i.func_70090_H()) {
                  this.field_205138_i.func_70659_e(☃x * 0.02F);
                  float ☃xx = -((float)(MathHelper.func_181159_b(☃x, (double)MathHelper.func_76133_a(☃ * ☃ + ☃xx * ☃xx)) * 180.0F / (float)Math.PI));
                  ☃xx = MathHelper.func_76131_a(MathHelper.func_76142_g(☃xx), -85.0F, 85.0F);
                  this.field_205138_i.field_70125_A = this.func_75639_a(this.field_205138_i.field_70125_A, ☃xx, 5.0F);
                  float ☃xxx = MathHelper.func_76134_b(this.field_205138_i.field_70125_A * (float) (Math.PI / 180.0));
                  float ☃xxxx = MathHelper.func_76126_a(this.field_205138_i.field_70125_A * (float) (Math.PI / 180.0));
                  this.field_205138_i.field_191988_bg = ☃xxx * ☃x;
                  this.field_205138_i.field_70701_bs = -☃xxxx * ☃x;
               } else {
                  this.field_205138_i.func_70659_e(☃x * 0.1F);
               }
            }
         } else {
            this.field_205138_i.func_70659_e(0.0F);
            this.field_205138_i.func_184646_p(0.0F);
            this.field_205138_i.func_70657_f(0.0F);
            this.field_205138_i.func_191989_p(0.0F);
         }
      }
   }
}
