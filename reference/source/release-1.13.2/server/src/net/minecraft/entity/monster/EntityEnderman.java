package net.minecraft.entity.monster;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityEnderman extends EntityMob {
   private static final UUID field_110192_bp = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier field_110193_bq = new AttributeModifier(field_110192_bp, "Attacking speed boost", 0.15F, 0).func_111168_a(false);
   private static final DataParameter<Optional<IBlockState>> field_184718_bv = EntityDataManager.func_187226_a(
      EntityEnderman.class, DataSerializers.field_187197_g
   );
   private static final DataParameter<Boolean> field_184719_bw = EntityDataManager.func_187226_a(EntityEnderman.class, DataSerializers.field_187198_h);
   private int field_184720_bx;
   private int field_184721_by;

   public EntityEnderman(World var1) {
      super(EntityType.field_200803_q, ☃);
      this.func_70105_a(0.6F, 2.9F);
      this.field_70138_W = 1.0F;
      this.func_184644_a(PathNodeType.WATER, -1.0F);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIAttackMelee(this, 1.0, false));
      this.field_70714_bg.func_75776_a(7, new EntityAIWanderAvoidWater(this, 1.0, 0.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.field_70714_bg.func_75776_a(10, new EntityEnderman.AIPlaceBlock(this));
      this.field_70714_bg.func_75776_a(11, new EntityEnderman.AITakeBlock(this));
      this.field_70715_bh.func_75776_a(1, new EntityEnderman.AIFindPlayer(this));
      this.field_70715_bh.func_75776_a(2, new EntityAIHurtByTarget(this, false));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, EntityEndermite::func_175495_n));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3F);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0);
   }

   @Override
   public void func_70624_b(@Nullable EntityLivingBase var1) {
      super.func_70624_b(☃);
      IAttributeInstance ☃ = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if (☃ == null) {
         this.field_184721_by = 0;
         this.field_70180_af.func_187227_b(field_184719_bw, false);
         ☃.func_111124_b(field_110193_bq);
      } else {
         this.field_184721_by = this.field_70173_aa;
         this.field_70180_af.func_187227_b(field_184719_bw, true);
         if (!☃.func_180374_a(field_110193_bq)) {
            ☃.func_111121_a(field_110193_bq);
         }
      }
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184718_bv, Optional.empty());
      this.field_70180_af.func_187214_a(field_184719_bw, false);
   }

   public void func_184716_o() {
      if (this.field_70173_aa >= this.field_184720_bx + 400) {
         this.field_184720_bx = this.field_70173_aa;
         if (!this.func_174814_R()) {
            this.field_70170_p
               .func_184134_a(
                  this.field_70165_t,
                  this.field_70163_u + (double)this.func_70047_e(),
                  this.field_70161_v,
                  SoundEvents.field_187533_aW,
                  this.func_184176_by(),
                  2.5F,
                  1.0F,
                  false
               );
         }
      }
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184719_bw.equals(☃) && this.func_70823_r() && this.field_70170_p.field_72995_K) {
         this.func_184716_o();
      }

      super.func_184206_a(☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      IBlockState ☃ = this.func_195405_dq();
      if (☃ != null) {
         ☃.func_74782_a("carriedBlockState", NBTUtil.func_190009_a(☃));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      IBlockState ☃ = null;
      if (☃.func_150297_b("carriedBlockState", 10)) {
         ☃ = NBTUtil.func_190008_d(☃.func_74775_l("carriedBlockState"));
         if (☃.func_196958_f()) {
            ☃ = null;
         }
      }

      this.func_195406_b(☃);
   }

   private boolean func_70821_d(EntityPlayer var1) {
      ItemStack ☃ = ☃.field_71071_by.field_70460_b.get(3);
      if (☃.func_77973_b() == Blocks.field_196625_cS.func_199767_j()) {
         return false;
      } else {
         Vec3d ☃ = ☃.func_70676_i(1.0F).func_72432_b();
         Vec3d ☃x = new Vec3d(
            this.field_70165_t - ☃.field_70165_t,
            this.func_174813_aQ().field_72338_b + (double)this.func_70047_e() - (☃.field_70163_u + (double)☃.func_70047_e()),
            this.field_70161_v - ☃.field_70161_v
         );
         double ☃xx = ☃x.func_72433_c();
         ☃x = ☃x.func_72432_b();
         double ☃xxx = ☃.func_72430_b(☃x);
         return ☃xxx > 1.0 - 0.025 / ☃xx ? ☃.func_70685_l(this) : false;
      }
   }

   @Override
   public float func_70047_e() {
      return 2.55F;
   }

   @Override
   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         for(int ☃ = 0; ☃ < 2; ++☃) {
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197599_J,
                  this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                  this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O - 0.25,
                  this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N,
                  (this.field_70146_Z.nextDouble() - 0.5) * 2.0,
                  -this.field_70146_Z.nextDouble(),
                  (this.field_70146_Z.nextDouble() - 0.5) * 2.0
               );
         }
      }

      this.field_70703_bu = false;
      super.func_70636_d();
   }

   @Override
   protected void func_70619_bc() {
      if (this.func_203008_ap()) {
         this.func_70097_a(DamageSource.field_76369_e, 1.0F);
      }

      if (this.field_70170_p.func_72935_r() && this.field_70173_aa >= this.field_184721_by + 600) {
         float ☃ = this.func_70013_c();
         if (☃ > 0.5F && this.field_70170_p.func_175678_i(new BlockPos(this)) && this.field_70146_Z.nextFloat() * 30.0F < (☃ - 0.4F) * 2.0F) {
            this.func_70624_b(null);
            this.func_70820_n();
         }
      }

      super.func_70619_bc();
   }

   protected boolean func_70820_n() {
      double ☃ = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * 64.0;
      double ☃x = this.field_70163_u + (double)(this.field_70146_Z.nextInt(64) - 32);
      double ☃xx = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * 64.0;
      return this.func_70825_j(☃, ☃x, ☃xx);
   }

   protected boolean func_70816_c(Entity var1) {
      Vec3d ☃ = new Vec3d(
         this.field_70165_t - ☃.field_70165_t,
         this.func_174813_aQ().field_72338_b + (double)(this.field_70131_O / 2.0F) - ☃.field_70163_u + (double)☃.func_70047_e(),
         this.field_70161_v - ☃.field_70161_v
      );
      ☃ = ☃.func_72432_b();
      double ☃x = 16.0;
      double ☃xx = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * 8.0 - ☃.field_72450_a * 16.0;
      double ☃xxx = this.field_70163_u + (double)(this.field_70146_Z.nextInt(16) - 8) - ☃.field_72448_b * 16.0;
      double ☃xxxx = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * 8.0 - ☃.field_72449_c * 16.0;
      return this.func_70825_j(☃xx, ☃xxx, ☃xxxx);
   }

   private boolean func_70825_j(double var1, double var3, double var5) {
      boolean ☃ = this.func_184595_k(☃, ☃, ☃);
      if (☃) {
         this.field_70170_p
            .func_184148_a(null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187534_aX, this.func_184176_by(), 1.0F, 1.0F);
         this.func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
      }

      return ☃;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return this.func_70823_r() ? SoundEvents.field_187532_aV : SoundEvents.field_187529_aS;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187531_aU;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187530_aT;
   }

   @Override
   protected void func_82160_b(boolean var1, int var2) {
      super.func_82160_b(☃, ☃);
      IBlockState ☃ = this.func_195405_dq();
      if (☃ != null) {
         this.func_199703_a(☃.func_177230_c());
      }
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186439_u;
   }

   public void func_195406_b(@Nullable IBlockState var1) {
      this.field_70180_af.func_187227_b(field_184718_bv, Optional.ofNullable(☃));
   }

   @Nullable
   public IBlockState func_195405_dq() {
      return (IBlockState)((Optional)this.field_70180_af.func_187225_a(field_184718_bv)).orElse(null);
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (☃ instanceof EntityDamageSourceIndirect) {
         for(int ☃ = 0; ☃ < 64; ++☃) {
            if (this.func_70820_n()) {
               return true;
            }
         }

         return false;
      } else {
         boolean ☃ = super.func_70097_a(☃, ☃);
         if (☃.func_76363_c() && this.field_70146_Z.nextInt(10) != 0) {
            this.func_70820_n();
         }

         return ☃;
      }
   }

   public boolean func_70823_r() {
      return this.field_70180_af.func_187225_a(field_184719_bw);
   }

   static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
      private final EntityEnderman field_179449_j;
      private EntityPlayer field_179448_g;
      private int field_179450_h;
      private int field_179451_i;

      public AIFindPlayer(EntityEnderman var1) {
         super(☃, EntityPlayer.class, false);
         this.field_179449_j = ☃;
      }

      @Override
      public boolean func_75250_a() {
         double ☃ = this.func_111175_f();
         this.field_179448_g = this.field_179449_j
            .field_70170_p
            .func_184150_a(
               this.field_179449_j.field_70165_t,
               this.field_179449_j.field_70163_u,
               this.field_179449_j.field_70161_v,
               ☃,
               ☃,
               null,
               var1x -> var1x != null && this.field_179449_j.func_70821_d(var1x)
            );
         return this.field_179448_g != null;
      }

      @Override
      public void func_75249_e() {
         this.field_179450_h = 5;
         this.field_179451_i = 0;
      }

      @Override
      public void func_75251_c() {
         this.field_179448_g = null;
         super.func_75251_c();
      }

      @Override
      public boolean func_75253_b() {
         if (this.field_179448_g != null) {
            if (!this.field_179449_j.func_70821_d(this.field_179448_g)) {
               return false;
            } else {
               this.field_179449_j.func_70625_a(this.field_179448_g, 10.0F, 10.0F);
               return true;
            }
         } else {
            return this.field_75309_a != null && this.field_75309_a.func_70089_S() ? true : super.func_75253_b();
         }
      }

      @Override
      public void func_75246_d() {
         if (this.field_179448_g != null) {
            if (--this.field_179450_h <= 0) {
               this.field_75309_a = this.field_179448_g;
               this.field_179448_g = null;
               super.func_75249_e();
            }
         } else {
            if (this.field_75309_a != null) {
               if (this.field_179449_j.func_70821_d(this.field_75309_a)) {
                  if (this.field_75309_a.func_70068_e(this.field_179449_j) < 16.0) {
                     this.field_179449_j.func_70820_n();
                  }

                  this.field_179451_i = 0;
               } else if (this.field_75309_a.func_70068_e(this.field_179449_j) > 256.0
                  && this.field_179451_i++ >= 30
                  && this.field_179449_j.func_70816_c(this.field_75309_a)) {
                  this.field_179451_i = 0;
               }
            }

            super.func_75246_d();
         }
      }
   }

   static class AIPlaceBlock extends EntityAIBase {
      private final EntityEnderman field_179475_a;

      public AIPlaceBlock(EntityEnderman var1) {
         this.field_179475_a = ☃;
      }

      @Override
      public boolean func_75250_a() {
         if (this.field_179475_a.func_195405_dq() == null) {
            return false;
         } else if (!this.field_179475_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         } else {
            return this.field_179475_a.func_70681_au().nextInt(2000) == 0;
         }
      }

      @Override
      public void func_75246_d() {
         Random ☃ = this.field_179475_a.func_70681_au();
         IWorld ☃x = this.field_179475_a.field_70170_p;
         int ☃xx = MathHelper.func_76128_c(this.field_179475_a.field_70165_t - 1.0 + ☃.nextDouble() * 2.0);
         int ☃xxx = MathHelper.func_76128_c(this.field_179475_a.field_70163_u + ☃.nextDouble() * 2.0);
         int ☃xxxx = MathHelper.func_76128_c(this.field_179475_a.field_70161_v - 1.0 + ☃.nextDouble() * 2.0);
         BlockPos ☃xxxxx = new BlockPos(☃xx, ☃xxx, ☃xxxx);
         IBlockState ☃xxxxxx = ☃x.func_180495_p(☃xxxxx);
         IBlockState ☃xxxxxxx = ☃x.func_180495_p(☃xxxxx.func_177977_b());
         IBlockState ☃xxxxxxxx = this.field_179475_a.func_195405_dq();
         if (☃xxxxxxxx != null && this.func_195924_a(☃x, ☃xxxxx, ☃xxxxxxxx, ☃xxxxxx, ☃xxxxxxx)) {
            ☃x.func_180501_a(☃xxxxx, ☃xxxxxxxx, 3);
            this.field_179475_a.func_195406_b(null);
         }
      }

      private boolean func_195924_a(IWorldReaderBase var1, BlockPos var2, IBlockState var3, IBlockState var4, IBlockState var5) {
         return ☃.func_196958_f() && !☃.func_196958_f() && ☃.func_185917_h() && ☃.func_196955_c(☃, ☃);
      }
   }

   static class AITakeBlock extends EntityAIBase {
      private final EntityEnderman field_179473_a;

      public AITakeBlock(EntityEnderman var1) {
         this.field_179473_a = ☃;
      }

      @Override
      public boolean func_75250_a() {
         if (this.field_179473_a.func_195405_dq() != null) {
            return false;
         } else if (!this.field_179473_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         } else {
            return this.field_179473_a.func_70681_au().nextInt(20) == 0;
         }
      }

      @Override
      public void func_75246_d() {
         Random ☃ = this.field_179473_a.func_70681_au();
         World ☃x = this.field_179473_a.field_70170_p;
         int ☃xx = MathHelper.func_76128_c(this.field_179473_a.field_70165_t - 2.0 + ☃.nextDouble() * 4.0);
         int ☃xxx = MathHelper.func_76128_c(this.field_179473_a.field_70163_u + ☃.nextDouble() * 3.0);
         int ☃xxxx = MathHelper.func_76128_c(this.field_179473_a.field_70161_v - 2.0 + ☃.nextDouble() * 4.0);
         BlockPos ☃xxxxx = new BlockPos(☃xx, ☃xxx, ☃xxxx);
         IBlockState ☃xxxxxx = ☃x.func_180495_p(☃xxxxx);
         Block ☃xxxxxxx = ☃xxxxxx.func_177230_c();
         RayTraceResult ☃xxxxxxxx = ☃x.func_200259_a(
            new Vec3d(
               (double)((float)MathHelper.func_76128_c(this.field_179473_a.field_70165_t) + 0.5F),
               (double)((float)☃xxx + 0.5F),
               (double)((float)MathHelper.func_76128_c(this.field_179473_a.field_70161_v) + 0.5F)
            ),
            new Vec3d((double)((float)☃xx + 0.5F), (double)((float)☃xxx + 0.5F), (double)((float)☃xxxx + 0.5F)),
            RayTraceFluidMode.NEVER,
            true,
            false
         );
         boolean ☃xxxxxxxxx = ☃xxxxxxxx != null && ☃xxxxxxxx.func_178782_a().equals(☃xxxxx);
         if (☃xxxxxxx.func_203417_a(BlockTags.field_201151_l) && ☃xxxxxxxxx) {
            this.field_179473_a.func_195406_b(☃xxxxxx);
            ☃x.func_175698_g(☃xxxxx);
         }
      }
   }
}
