package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGhast extends EntityFlying implements IMob {
   private static final DataParameter<Boolean> field_184683_a = EntityDataManager.func_187226_a(EntityGhast.class, DataSerializers.field_187198_h);
   private int field_92014_j = 1;

   public EntityGhast(World var1) {
      super(EntityType.field_200811_y, ☃);
      this.func_70105_a(4.0F, 4.0F);
      this.field_70178_ae = true;
      this.field_70728_aV = 5;
      this.field_70765_h = new EntityGhast.GhastMoveHelper(this);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(5, new EntityGhast.AIRandomFly(this));
      this.field_70714_bg.func_75776_a(7, new EntityGhast.AILookAround(this));
      this.field_70714_bg.func_75776_a(7, new EntityGhast.AIFireballAttack(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIFindEntityNearestPlayer(this));
   }

   public boolean func_110182_bF() {
      return this.field_70180_af.func_187225_a(field_184683_a);
   }

   public void func_175454_a(boolean var1) {
      this.field_70180_af.func_187227_b(field_184683_a, ☃);
   }

   public int func_175453_cd() {
      return this.field_92014_j;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
         this.func_70106_y();
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (☃.func_76364_f() instanceof EntityLargeFireball && ☃.func_76346_g() instanceof EntityPlayer) {
         super.func_70097_a(☃, 1000.0F);
         return true;
      } else {
         return super.func_70097_a(☃, ☃);
      }
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184683_a, false);
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100.0);
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187551_bH;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187555_bJ;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187553_bI;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186380_ae;
   }

   @Override
   protected float func_70599_aP() {
      return 10.0F;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return this.field_70146_Z.nextInt(20) == 0 && super.func_205020_a(☃, ☃) && ☃.func_175659_aa() != EnumDifficulty.PEACEFUL;
   }

   @Override
   public int func_70641_bl() {
      return 1;
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("ExplosionPower", this.field_92014_j);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("ExplosionPower", 99)) {
         this.field_92014_j = ☃.func_74762_e("ExplosionPower");
      }
   }

   @Override
   public float func_70047_e() {
      return 2.6F;
   }

   static class AIFireballAttack extends EntityAIBase {
      private final EntityGhast field_179470_b;
      public int field_179471_a;

      public AIFireballAttack(EntityGhast var1) {
         this.field_179470_b = ☃;
      }

      @Override
      public boolean func_75250_a() {
         return this.field_179470_b.func_70638_az() != null;
      }

      @Override
      public void func_75249_e() {
         this.field_179471_a = 0;
      }

      @Override
      public void func_75251_c() {
         this.field_179470_b.func_175454_a(false);
      }

      @Override
      public void func_75246_d() {
         EntityLivingBase ☃ = this.field_179470_b.func_70638_az();
         double ☃x = 64.0;
         if (☃.func_70068_e(this.field_179470_b) < 4096.0 && this.field_179470_b.func_70685_l(☃)) {
            World ☃xx = this.field_179470_b.field_70170_p;
            ++this.field_179471_a;
            if (this.field_179471_a == 10) {
               ☃xx.func_180498_a(null, 1015, new BlockPos(this.field_179470_b), 0);
            }

            if (this.field_179471_a == 20) {
               double ☃xx = 4.0;
               Vec3d ☃xxx = this.field_179470_b.func_70676_i(1.0F);
               double ☃xxxx = ☃.field_70165_t - (this.field_179470_b.field_70165_t + ☃xxx.field_72450_a * 4.0);
               double ☃xxxxx = ☃.func_174813_aQ().field_72338_b
                  + (double)(☃.field_70131_O / 2.0F)
                  - (0.5 + this.field_179470_b.field_70163_u + (double)(this.field_179470_b.field_70131_O / 2.0F));
               double ☃xxxxxx = ☃.field_70161_v - (this.field_179470_b.field_70161_v + ☃xxx.field_72449_c * 4.0);
               ☃xx.func_180498_a(null, 1016, new BlockPos(this.field_179470_b), 0);
               EntityLargeFireball ☃xxxxxxx = new EntityLargeFireball(☃xx, this.field_179470_b, ☃xxxx, ☃xxxxx, ☃xxxxxx);
               ☃xxxxxxx.field_92057_e = this.field_179470_b.func_175453_cd();
               ☃xxxxxxx.field_70165_t = this.field_179470_b.field_70165_t + ☃xxx.field_72450_a * 4.0;
               ☃xxxxxxx.field_70163_u = this.field_179470_b.field_70163_u + (double)(this.field_179470_b.field_70131_O / 2.0F) + 0.5;
               ☃xxxxxxx.field_70161_v = this.field_179470_b.field_70161_v + ☃xxx.field_72449_c * 4.0;
               ☃xx.func_72838_d(☃xxxxxxx);
               this.field_179471_a = -40;
            }
         } else if (this.field_179471_a > 0) {
            --this.field_179471_a;
         }

         this.field_179470_b.func_175454_a(this.field_179471_a > 10);
      }
   }

   static class AILookAround extends EntityAIBase {
      private final EntityGhast field_179472_a;

      public AILookAround(EntityGhast var1) {
         this.field_179472_a = ☃;
         this.func_75248_a(2);
      }

      @Override
      public boolean func_75250_a() {
         return true;
      }

      @Override
      public void func_75246_d() {
         if (this.field_179472_a.func_70638_az() == null) {
            this.field_179472_a.field_70177_z = -((float)MathHelper.func_181159_b(this.field_179472_a.field_70159_w, this.field_179472_a.field_70179_y))
               * (180.0F / (float)Math.PI);
            this.field_179472_a.field_70761_aq = this.field_179472_a.field_70177_z;
         } else {
            EntityLivingBase ☃ = this.field_179472_a.func_70638_az();
            double ☃x = 64.0;
            if (☃.func_70068_e(this.field_179472_a) < 4096.0) {
               double ☃xx = ☃.field_70165_t - this.field_179472_a.field_70165_t;
               double ☃xxx = ☃.field_70161_v - this.field_179472_a.field_70161_v;
               this.field_179472_a.field_70177_z = -((float)MathHelper.func_181159_b(☃xx, ☃xxx)) * (180.0F / (float)Math.PI);
               this.field_179472_a.field_70761_aq = this.field_179472_a.field_70177_z;
            }
         }
      }
   }

   static class AIRandomFly extends EntityAIBase {
      private final EntityGhast field_179454_a;

      public AIRandomFly(EntityGhast var1) {
         this.field_179454_a = ☃;
         this.func_75248_a(1);
      }

      @Override
      public boolean func_75250_a() {
         EntityMoveHelper ☃ = this.field_179454_a.func_70605_aq();
         if (!☃.func_75640_a()) {
            return true;
         } else {
            double ☃ = ☃.func_179917_d() - this.field_179454_a.field_70165_t;
            double ☃x = ☃.func_179919_e() - this.field_179454_a.field_70163_u;
            double ☃xx = ☃.func_179918_f() - this.field_179454_a.field_70161_v;
            double ☃xxx = ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
            return ☃xxx < 1.0 || ☃xxx > 3600.0;
         }
      }

      @Override
      public boolean func_75253_b() {
         return false;
      }

      @Override
      public void func_75249_e() {
         Random ☃ = this.field_179454_a.func_70681_au();
         double ☃x = this.field_179454_a.field_70165_t + (double)((☃.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double ☃xx = this.field_179454_a.field_70163_u + (double)((☃.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double ☃xxx = this.field_179454_a.field_70161_v + (double)((☃.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.field_179454_a.func_70605_aq().func_75642_a(☃x, ☃xx, ☃xxx, 1.0);
      }
   }

   static class GhastMoveHelper extends EntityMoveHelper {
      private final EntityGhast field_179927_g;
      private int field_179928_h;

      public GhastMoveHelper(EntityGhast var1) {
         super(☃);
         this.field_179927_g = ☃;
      }

      @Override
      public void func_75641_c() {
         if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO) {
            double ☃ = this.field_75646_b - this.field_179927_g.field_70165_t;
            double ☃x = this.field_75647_c - this.field_179927_g.field_70163_u;
            double ☃xx = this.field_75644_d - this.field_179927_g.field_70161_v;
            double ☃xxx = ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
            if (this.field_179928_h-- <= 0) {
               this.field_179928_h += this.field_179927_g.func_70681_au().nextInt(5) + 2;
               ☃xxx = (double)MathHelper.func_76133_a(☃xxx);
               if (this.func_179926_b(this.field_75646_b, this.field_75647_c, this.field_75644_d, ☃xxx)) {
                  this.field_179927_g.field_70159_w += ☃ / ☃xxx * 0.1;
                  this.field_179927_g.field_70181_x += ☃x / ☃xxx * 0.1;
                  this.field_179927_g.field_70179_y += ☃xx / ☃xxx * 0.1;
               } else {
                  this.field_188491_h = EntityMoveHelper.Action.WAIT;
               }
            }
         }
      }

      private boolean func_179926_b(double var1, double var3, double var5, double var7) {
         double ☃ = (☃ - this.field_179927_g.field_70165_t) / ☃;
         double ☃x = (☃ - this.field_179927_g.field_70163_u) / ☃;
         double ☃xx = (☃ - this.field_179927_g.field_70161_v) / ☃;
         AxisAlignedBB ☃xxx = this.field_179927_g.func_174813_aQ();

         for(int ☃xxxx = 1; (double)☃xxxx < ☃; ++☃xxxx) {
            ☃xxx = ☃xxx.func_72317_d(☃, ☃x, ☃xx);
            if (!this.field_179927_g.field_70170_p.func_195586_b(this.field_179927_g, ☃xxx)) {
               return false;
            }
         }

         return true;
      }
   }
}
