package net.minecraft.entity.monster;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGuardian extends EntityMob {
   private static final DataParameter<Boolean> field_190766_bz = EntityDataManager.func_187226_a(EntityGuardian.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_184723_b = EntityDataManager.func_187226_a(EntityGuardian.class, DataSerializers.field_187192_b);
   protected float field_175482_b;
   protected float field_175484_c;
   protected float field_175483_bk;
   protected float field_175485_bl;
   protected float field_175486_bm;
   private EntityLivingBase field_175478_bn;
   private int field_175479_bo;
   private boolean field_175480_bp;
   protected EntityAIWander field_175481_bq;

   protected EntityGuardian(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_70728_aV = 10;
      this.func_70105_a(0.85F, 0.85F);
      this.field_70765_h = new EntityGuardian.GuardianMoveHelper(this);
      this.field_175482_b = this.field_70146_Z.nextFloat();
      this.field_175484_c = this.field_175482_b;
   }

   public EntityGuardian(World var1) {
      this(EntityType.field_200761_A, ☃);
   }

   @Override
   protected void func_184651_r() {
      EntityAIMoveTowardsRestriction ☃ = new EntityAIMoveTowardsRestriction(this, 1.0);
      this.field_175481_bq = new EntityAIWander(this, 1.0, 80);
      this.field_70714_bg.func_75776_a(4, new EntityGuardian.AIGuardianAttack(this));
      this.field_70714_bg.func_75776_a(5, ☃);
      this.field_70714_bg.func_75776_a(7, this.field_175481_bq);
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.field_175481_bq.func_75248_a(3);
      ☃.func_75248_a(3);
      this.field_70715_bh
         .func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new EntityGuardian.GuardianTargetSelector(this)));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0);
   }

   @Override
   protected PathNavigate func_175447_b(World var1) {
      return new PathNavigateSwimmer(this, ☃);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_190766_bz, false);
      this.field_70180_af.func_187214_a(field_184723_b, 0);
   }

   @Override
   public boolean func_70648_aU() {
      return true;
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.field_203100_e;
   }

   public boolean func_175472_n() {
      return this.field_70180_af.func_187225_a(field_190766_bz);
   }

   private void func_175476_l(boolean var1) {
      this.field_70180_af.func_187227_b(field_190766_bz, ☃);
   }

   public int func_175464_ck() {
      return 80;
   }

   private void func_175463_b(int var1) {
      this.field_70180_af.func_187227_b(field_184723_b, ☃);
   }

   public boolean func_175474_cn() {
      return this.field_70180_af.func_187225_a(field_184723_b) != 0;
   }

   @Nullable
   public EntityLivingBase func_175466_co() {
      if (!this.func_175474_cn()) {
         return null;
      } else if (this.field_70170_p.field_72995_K) {
         if (this.field_175478_bn != null) {
            return this.field_175478_bn;
         } else {
            Entity ☃ = this.field_70170_p.func_73045_a(this.field_70180_af.func_187225_a(field_184723_b));
            if (☃ instanceof EntityLivingBase) {
               this.field_175478_bn = (EntityLivingBase)☃;
               return this.field_175478_bn;
            } else {
               return null;
            }
         }
      } else {
         return this.func_70638_az();
      }
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      super.func_184206_a(☃);
      if (field_184723_b.equals(☃)) {
         this.field_175479_bo = 0;
         this.field_175478_bn = null;
      }
   }

   @Override
   public int func_70627_aG() {
      return 160;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return this.func_203005_aq() ? SoundEvents.field_187670_cb : SoundEvents.field_187672_cc;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return this.func_203005_aq() ? SoundEvents.field_187687_ch : SoundEvents.field_187690_ci;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return this.func_203005_aq() ? SoundEvents.field_187678_ce : SoundEvents.field_187681_cf;
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O * 0.5F;
   }

   @Override
   public float func_205022_a(BlockPos var1, IWorldReaderBase var2) {
      return ☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a) ? 10.0F + ☃.func_205052_D(☃) - 0.5F : super.func_205022_a(☃, ☃);
   }

   @Override
   public void func_70636_d() {
      if (this.field_70170_p.field_72995_K) {
         this.field_175484_c = this.field_175482_b;
         if (!this.func_70090_H()) {
            this.field_175483_bk = 2.0F;
            if (this.field_70181_x > 0.0 && this.field_175480_bp && !this.func_174814_R()) {
               this.field_70170_p
                  .func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.func_190765_dj(), this.func_184176_by(), 1.0F, 1.0F, false);
            }

            this.field_175480_bp = this.field_70181_x < 0.0 && this.field_70170_p.func_195595_w(new BlockPos(this).func_177977_b());
         } else if (this.func_175472_n()) {
            if (this.field_175483_bk < 0.5F) {
               this.field_175483_bk = 4.0F;
            } else {
               this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
            }
         } else {
            this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
         }

         this.field_175482_b += this.field_175483_bk;
         this.field_175486_bm = this.field_175485_bl;
         if (!this.func_203005_aq()) {
            this.field_175485_bl = this.field_70146_Z.nextFloat();
         } else if (this.func_175472_n()) {
            this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
         } else {
            this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
         }

         if (this.func_175472_n() && this.func_70090_H()) {
            Vec3d ☃ = this.func_70676_i(0.0F);

            for(int ☃x = 0; ☃x < 2; ++☃x) {
               this.field_70170_p
                  .func_195594_a(
                     Particles.field_197612_e,
                     this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N - ☃.field_72450_a * 1.5,
                     this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O - ☃.field_72448_b * 1.5,
                     this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5) * (double)this.field_70130_N - ☃.field_72449_c * 1.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.func_175474_cn()) {
            if (this.field_175479_bo < this.func_175464_ck()) {
               ++this.field_175479_bo;
            }

            EntityLivingBase ☃ = this.func_175466_co();
            if (☃ != null) {
               this.func_70671_ap().func_75651_a(☃, 90.0F, 90.0F);
               this.func_70671_ap().func_75649_a();
               double ☃x = (double)this.func_175477_p(0.0F);
               double ☃xx = ☃.field_70165_t - this.field_70165_t;
               double ☃xxx = ☃.field_70163_u + (double)(☃.field_70131_O * 0.5F) - (this.field_70163_u + (double)this.func_70047_e());
               double ☃xxxx = ☃.field_70161_v - this.field_70161_v;
               double ☃xxxxx = Math.sqrt(☃xx * ☃xx + ☃xxx * ☃xxx + ☃xxxx * ☃xxxx);
               ☃xx /= ☃xxxxx;
               ☃xxx /= ☃xxxxx;
               ☃xxxx /= ☃xxxxx;
               double ☃xxxxxx = this.field_70146_Z.nextDouble();

               while(☃xxxxxx < ☃xxxxx) {
                  ☃xxxxxx += 1.8 - ☃x + this.field_70146_Z.nextDouble() * (1.7 - ☃x);
                  this.field_70170_p
                     .func_195594_a(
                        Particles.field_197612_e,
                        this.field_70165_t + ☃xx * ☃xxxxxx,
                        this.field_70163_u + ☃xxx * ☃xxxxxx + (double)this.func_70047_e(),
                        this.field_70161_v + ☃xxxx * ☃xxxxxx,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }
         }
      }

      if (this.func_203005_aq()) {
         this.func_70050_g(300);
      } else if (this.field_70122_E) {
         this.field_70181_x += 0.5;
         this.field_70159_w += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.field_70179_y += (double)((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.4F);
         this.field_70177_z = this.field_70146_Z.nextFloat() * 360.0F;
         this.field_70122_E = false;
         this.field_70160_al = true;
      }

      if (this.func_175474_cn()) {
         this.field_70177_z = this.field_70759_as;
      }

      super.func_70636_d();
   }

   protected SoundEvent func_190765_dj() {
      return SoundEvents.field_187684_cg;
   }

   public float func_175471_a(float var1) {
      return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * ☃;
   }

   public float func_175469_o(float var1) {
      return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * ☃;
   }

   public float func_175477_p(float var1) {
      return ((float)this.field_175479_bo + ☃) / (float)this.func_175464_ck();
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186440_v;
   }

   @Override
   protected boolean func_70814_o() {
      return true;
   }

   @Override
   public boolean func_205019_a(IWorldReaderBase var1) {
      return ☃.func_195587_c(this, this.func_174813_aQ()) && ☃.func_195586_b(this, this.func_174813_aQ());
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return (this.field_70146_Z.nextInt(20) == 0 || !☃.func_175710_j(new BlockPos(this))) && super.func_205020_a(☃, ☃);
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (!this.func_175472_n() && !☃.func_82725_o() && ☃.func_76364_f() instanceof EntityLivingBase) {
         EntityLivingBase ☃ = (EntityLivingBase)☃.func_76364_f();
         if (!☃.func_94541_c()) {
            ☃.func_70097_a(DamageSource.func_92087_a(this), 2.0F);
         }
      }

      if (this.field_175481_bq != null) {
         this.field_175481_bq.func_179480_f();
      }

      return super.func_70097_a(☃, ☃);
   }

   @Override
   public int func_70646_bf() {
      return 180;
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      if (this.func_70613_aW() && this.func_70090_H()) {
         this.func_191958_b(☃, ☃, ☃, 0.1F);
         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.9F;
         this.field_70181_x *= 0.9F;
         this.field_70179_y *= 0.9F;
         if (!this.func_175472_n() && this.func_70638_az() == null) {
            this.field_70181_x -= 0.005;
         }
      } else {
         super.func_191986_a(☃, ☃, ☃);
      }
   }

   static class AIGuardianAttack extends EntityAIBase {
      private final EntityGuardian field_179456_a;
      private int field_179455_b;
      private final boolean field_190881_c;

      public AIGuardianAttack(EntityGuardian var1) {
         this.field_179456_a = ☃;
         this.field_190881_c = ☃ instanceof EntityElderGuardian;
         this.func_75248_a(3);
      }

      @Override
      public boolean func_75250_a() {
         EntityLivingBase ☃ = this.field_179456_a.func_70638_az();
         return ☃ != null && ☃.func_70089_S();
      }

      @Override
      public boolean func_75253_b() {
         return super.func_75253_b() && (this.field_190881_c || this.field_179456_a.func_70068_e(this.field_179456_a.func_70638_az()) > 9.0);
      }

      @Override
      public void func_75249_e() {
         this.field_179455_b = -10;
         this.field_179456_a.func_70661_as().func_75499_g();
         this.field_179456_a.func_70671_ap().func_75651_a(this.field_179456_a.func_70638_az(), 90.0F, 90.0F);
         this.field_179456_a.field_70160_al = true;
      }

      @Override
      public void func_75251_c() {
         this.field_179456_a.func_175463_b(0);
         this.field_179456_a.func_70624_b(null);
         this.field_179456_a.field_175481_bq.func_179480_f();
      }

      @Override
      public void func_75246_d() {
         EntityLivingBase ☃ = this.field_179456_a.func_70638_az();
         this.field_179456_a.func_70661_as().func_75499_g();
         this.field_179456_a.func_70671_ap().func_75651_a(☃, 90.0F, 90.0F);
         if (!this.field_179456_a.func_70685_l(☃)) {
            this.field_179456_a.func_70624_b(null);
         } else {
            ++this.field_179455_b;
            if (this.field_179455_b == 0) {
               this.field_179456_a.func_175463_b(this.field_179456_a.func_70638_az().func_145782_y());
               this.field_179456_a.field_70170_p.func_72960_a(this.field_179456_a, (byte)21);
            } else if (this.field_179455_b >= this.field_179456_a.func_175464_ck()) {
               float ☃ = 1.0F;
               if (this.field_179456_a.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
                  ☃ += 2.0F;
               }

               if (this.field_190881_c) {
                  ☃ += 2.0F;
               }

               ☃.func_70097_a(DamageSource.func_76354_b(this.field_179456_a, this.field_179456_a), ☃);
               ☃.func_70097_a(
                  DamageSource.func_76358_a(this.field_179456_a),
                  (float)this.field_179456_a.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e()
               );
               this.field_179456_a.func_70624_b(null);
            }

            super.func_75246_d();
         }
      }
   }

   static class GuardianMoveHelper extends EntityMoveHelper {
      private final EntityGuardian field_179930_g;

      public GuardianMoveHelper(EntityGuardian var1) {
         super(☃);
         this.field_179930_g = ☃;
      }

      @Override
      public void func_75641_c() {
         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && !this.field_179930_g.func_70661_as().func_75500_f()) {
            double ☃ = this.field_75646_b - this.field_179930_g.field_70165_t;
            double ☃x = this.field_75647_c - this.field_179930_g.field_70163_u;
            double ☃xx = this.field_75644_d - this.field_179930_g.field_70161_v;
            double ☃xxx = (double)MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
            ☃x /= ☃xxx;
            float ☃xxxx = (float)(MathHelper.func_181159_b(☃xx, ☃) * 180.0F / (float)Math.PI) - 90.0F;
            this.field_179930_g.field_70177_z = this.func_75639_a(this.field_179930_g.field_70177_z, ☃xxxx, 90.0F);
            this.field_179930_g.field_70761_aq = this.field_179930_g.field_70177_z;
            float ☃xxxxx = (float)(this.field_75645_e * this.field_179930_g.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            this.field_179930_g.func_70659_e(this.field_179930_g.func_70689_ay() + (☃xxxxx - this.field_179930_g.func_70689_ay()) * 0.125F);
            double ☃xxxxxx = Math.sin((double)(this.field_179930_g.field_70173_aa + this.field_179930_g.func_145782_y()) * 0.5) * 0.05;
            double ☃xxxxxxx = Math.cos((double)(this.field_179930_g.field_70177_z * (float) (Math.PI / 180.0)));
            double ☃xxxxxxxx = Math.sin((double)(this.field_179930_g.field_70177_z * (float) (Math.PI / 180.0)));
            this.field_179930_g.field_70159_w += ☃xxxxxx * ☃xxxxxxx;
            this.field_179930_g.field_70179_y += ☃xxxxxx * ☃xxxxxxxx;
            ☃xxxxxx = Math.sin((double)(this.field_179930_g.field_70173_aa + this.field_179930_g.func_145782_y()) * 0.75) * 0.05;
            this.field_179930_g.field_70181_x += ☃xxxxxx * (☃xxxxxxxx + ☃xxxxxxx) * 0.25;
            this.field_179930_g.field_70181_x += (double)this.field_179930_g.func_70689_ay() * ☃x * 0.1;
            EntityLookHelper ☃xxxxxxxxx = this.field_179930_g.func_70671_ap();
            double ☃xxxxxxxxxx = this.field_179930_g.field_70165_t + ☃ / ☃xxx * 2.0;
            double ☃xxxxxxxxxxx = (double)this.field_179930_g.func_70047_e() + this.field_179930_g.field_70163_u + ☃x / ☃xxx;
            double ☃xxxxxxxxxxxx = this.field_179930_g.field_70161_v + ☃xx / ☃xxx * 2.0;
            double ☃xxxxxxxxxxxxx = ☃xxxxxxxxx.func_180423_e();
            double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxx.func_180422_f();
            double ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxx.func_180421_g();
            if (!☃xxxxxxxxx.func_180424_b()) {
               ☃xxxxxxxxxxxxx = ☃xxxxxxxxxx;
               ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxx;
               ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx;
            }

            this.field_179930_g
               .func_70671_ap()
               .func_75650_a(
                  ☃xxxxxxxxxxxxx + (☃xxxxxxxxxx - ☃xxxxxxxxxxxxx) * 0.125,
                  ☃xxxxxxxxxxxxxx + (☃xxxxxxxxxxx - ☃xxxxxxxxxxxxxx) * 0.125,
                  ☃xxxxxxxxxxxxxxx + (☃xxxxxxxxxxxx - ☃xxxxxxxxxxxxxxx) * 0.125,
                  10.0F,
                  40.0F
               );
            this.field_179930_g.func_175476_l(true);
         } else {
            this.field_179930_g.func_70659_e(0.0F);
            this.field_179930_g.func_175476_l(false);
         }
      }
   }

   static class GuardianTargetSelector implements Predicate<EntityLivingBase> {
      private final EntityGuardian field_179916_a;

      public GuardianTargetSelector(EntityGuardian var1) {
         this.field_179916_a = ☃;
      }

      public boolean test(@Nullable EntityLivingBase var1) {
         return (☃ instanceof EntityPlayer || ☃ instanceof EntitySquid) && ☃.func_70068_e(this.field_179916_a) > 9.0;
      }
   }
}
