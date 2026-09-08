package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySlime extends EntityLiving implements IMob {
   private static final DataParameter<Integer> field_184711_bt = EntityDataManager.func_187226_a(EntitySlime.class, DataSerializers.field_187192_b);
   public float field_70813_a;
   public float field_70811_b;
   public float field_70812_c;
   private boolean field_175452_bi;

   protected EntitySlime(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_70765_h = new EntitySlime.SlimeMoveHelper(this);
   }

   public EntitySlime(World var1) {
      this(EntityType.field_200743_ai, ☃);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(1, new EntitySlime.AISlimeFloat(this));
      this.field_70714_bg.func_75776_a(2, new EntitySlime.AISlimeAttack(this));
      this.field_70714_bg.func_75776_a(3, new EntitySlime.AISlimeFaceRandom(this));
      this.field_70714_bg.func_75776_a(5, new EntitySlime.AISlimeHop(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIFindEntityNearestPlayer(this));
      this.field_70715_bh.func_75776_a(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184711_bt, 1);
   }

   protected void func_70799_a(int var1, boolean var2) {
      this.field_70180_af.func_187227_b(field_184711_bt, ☃);
      this.func_70105_a(0.51000005F * (float)☃, 0.51000005F * (float)☃);
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)(☃ * ☃));
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a((double)(0.2F + 0.1F * (float)☃));
      if (☃) {
         this.func_70606_j(this.func_110138_aP());
      }

      this.field_70728_aV = ☃;
   }

   public int func_70809_q() {
      return this.field_70180_af.func_187225_a(field_184711_bt);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Size", this.func_70809_q() - 1);
      ☃.func_74757_a("wasOnGround", this.field_175452_bi);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      int ☃ = ☃.func_74762_e("Size");
      if (☃ < 0) {
         ☃ = 0;
      }

      this.func_70799_a(☃ + 1, false);
      this.field_175452_bi = ☃.func_74767_n("wasOnGround");
   }

   public boolean func_189101_db() {
      return this.func_70809_q() <= 1;
   }

   protected IParticleData func_195404_m() {
      return Particles.field_197592_C;
   }

   @Override
   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL && this.func_70809_q() > 0) {
         this.field_70128_L = true;
      }

      this.field_70811_b += (this.field_70813_a - this.field_70811_b) * 0.5F;
      this.field_70812_c = this.field_70811_b;
      super.func_70071_h_();
      if (this.field_70122_E && !this.field_175452_bi) {
         int ☃ = this.func_70809_q();

         for(int ☃x = 0; ☃x < ☃ * 8; ++☃x) {
            float ☃xx = this.field_70146_Z.nextFloat() * (float) (Math.PI * 2);
            float ☃xxx = this.field_70146_Z.nextFloat() * 0.5F + 0.5F;
            float ☃xxxx = MathHelper.func_76126_a(☃xx) * (float)☃ * 0.5F * ☃xxx;
            float ☃xxxxx = MathHelper.func_76134_b(☃xx) * (float)☃ * 0.5F * ☃xxx;
            World var10000 = this.field_70170_p;
            IParticleData var10001 = this.func_195404_m();
            double var10002 = this.field_70165_t + (double)☃xxxx;
            double var10004 = this.field_70161_v + (double)☃xxxxx;
            var10000.func_195594_a(var10001, var10002, this.func_174813_aQ().field_72338_b, var10004, 0.0, 0.0, 0.0);
         }

         this.func_184185_a(
            this.func_184709_cY(), this.func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / 0.8F
         );
         this.field_70813_a = -0.5F;
      } else if (!this.field_70122_E && this.field_175452_bi) {
         this.field_70813_a = 1.0F;
      }

      this.field_175452_bi = this.field_70122_E;
      this.func_70808_l();
   }

   protected void func_70808_l() {
      this.field_70813_a *= 0.6F;
   }

   protected int func_70806_k() {
      return this.field_70146_Z.nextInt(20) + 10;
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184711_bt.equals(☃)) {
         int ☃ = this.func_70809_q();
         this.func_70105_a(0.51000005F * (float)☃, 0.51000005F * (float)☃);
         this.field_70177_z = this.field_70759_as;
         this.field_70761_aq = this.field_70759_as;
         if (this.func_70090_H() && this.field_70146_Z.nextInt(20) == 0) {
            this.func_71061_d_();
         }
      }

      super.func_184206_a(☃);
   }

   @Override
   public EntityType<? extends EntitySlime> func_200600_R() {
      return super.func_200600_R();
   }

   @Override
   public void func_70106_y() {
      int ☃ = this.func_70809_q();
      if (!this.field_70170_p.field_72995_K && ☃ > 1 && this.func_110143_aJ() <= 0.0F) {
         int ☃x = 2 + this.field_70146_Z.nextInt(3);

         for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
            float ☃xxx = ((float)(☃xx % 2) - 0.5F) * (float)☃ / 4.0F;
            float ☃xxxx = ((float)(☃xx / 2) - 0.5F) * (float)☃ / 4.0F;
            EntitySlime ☃xxxxx = this.func_200600_R().func_200721_a(this.field_70170_p);
            if (this.func_145818_k_()) {
               ☃xxxxx.func_200203_b(this.func_200201_e());
            }

            if (this.func_104002_bU()) {
               ☃xxxxx.func_110163_bv();
            }

            ☃xxxxx.func_70799_a(☃ / 2, true);
            ☃xxxxx.func_70012_b(
               this.field_70165_t + (double)☃xxx, this.field_70163_u + 0.5, this.field_70161_v + (double)☃xxxx, this.field_70146_Z.nextFloat() * 360.0F, 0.0F
            );
            this.field_70170_p.func_72838_d(☃xxxxx);
         }
      }

      super.func_70106_y();
   }

   @Override
   public void func_70108_f(Entity var1) {
      super.func_70108_f(☃);
      if (☃ instanceof EntityIronGolem && this.func_70800_m()) {
         this.func_175451_e((EntityLivingBase)☃);
      }
   }

   @Override
   public void func_70100_b_(EntityPlayer var1) {
      if (this.func_70800_m()) {
         this.func_175451_e(☃);
      }
   }

   protected void func_175451_e(EntityLivingBase var1) {
      int ☃ = this.func_70809_q();
      if (this.func_70685_l(☃)
         && this.func_70068_e(☃) < 0.6 * (double)☃ * 0.6 * (double)☃
         && ☃.func_70097_a(DamageSource.func_76358_a(this), (float)this.func_70805_n())) {
         this.func_184185_a(SoundEvents.field_187870_fk, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         this.func_174815_a(this, ☃);
      }
   }

   @Override
   public float func_70047_e() {
      return 0.625F * this.field_70131_O;
   }

   protected boolean func_70800_m() {
      return !this.func_189101_db() && this.func_70613_aW();
   }

   protected int func_70805_n() {
      return this.func_70809_q();
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return this.func_189101_db() ? SoundEvents.field_187898_fy : SoundEvents.field_187880_fp;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return this.func_189101_db() ? SoundEvents.field_187896_fx : SoundEvents.field_187874_fm;
   }

   protected SoundEvent func_184709_cY() {
      return this.func_189101_db() ? SoundEvents.field_187900_fz : SoundEvents.field_187886_fs;
   }

   @Override
   protected Item func_146068_u() {
      return this.func_70809_q() == 1 ? Items.field_151123_aH : null;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return this.func_70809_q() == 1 ? LootTableList.field_186378_ac : LootTableList.field_186419_a;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      BlockPos ☃ = new BlockPos(MathHelper.func_76128_c(this.field_70165_t), 0, MathHelper.func_76128_c(this.field_70161_v));
      if (☃.func_72912_H().func_76067_t() == WorldType.field_77138_c && this.field_70146_Z.nextInt(4) != 1) {
         return false;
      } else {
         if (☃.func_175659_aa() != EnumDifficulty.PEACEFUL) {
            Biome ☃ = ☃.func_180494_b(☃);
            if (☃ == Biomes.field_76780_h
               && this.field_70163_u > 50.0
               && this.field_70163_u < 70.0
               && this.field_70146_Z.nextFloat() < 0.5F
               && this.field_70146_Z.nextFloat() < ☃.func_130001_d()
               && ☃.func_201696_r(new BlockPos(this)) <= this.field_70146_Z.nextInt(8)) {
               return super.func_205020_a(☃, ☃);
            }

            ChunkPos ☃ = new ChunkPos(☃);
            boolean ☃x = SharedSeedRandom.func_205190_a(☃.field_77276_a, ☃.field_77275_b, ☃.func_72905_C(), 987234911L).nextInt(10) == 0;
            if (this.field_70146_Z.nextInt(10) == 0 && ☃x && this.field_70163_u < 40.0) {
               return super.func_205020_a(☃, ☃);
            }
         }

         return false;
      }
   }

   @Override
   protected float func_70599_aP() {
      return 0.4F * (float)this.func_70809_q();
   }

   @Override
   public int func_70646_bf() {
      return 0;
   }

   protected boolean func_70807_r() {
      return this.func_70809_q() > 0;
   }

   @Override
   protected void func_70664_aZ() {
      this.field_70181_x = 0.42F;
      this.field_70160_al = true;
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      int ☃ = this.field_70146_Z.nextInt(3);
      if (☃ < 2 && this.field_70146_Z.nextFloat() < 0.5F * ☃.func_180170_c()) {
         ++☃;
      }

      int ☃ = 1 << ☃;
      this.func_70799_a(☃, true);
      return super.func_204210_a(☃, ☃, ☃);
   }

   protected SoundEvent func_184710_cZ() {
      return this.func_189101_db() ? SoundEvents.field_189110_fE : SoundEvents.field_187882_fq;
   }

   static class AISlimeAttack extends EntityAIBase {
      private final EntitySlime field_179466_a;
      private int field_179465_b;

      public AISlimeAttack(EntitySlime var1) {
         this.field_179466_a = ☃;
         this.func_75248_a(2);
      }

      @Override
      public boolean func_75250_a() {
         EntityLivingBase ☃ = this.field_179466_a.func_70638_az();
         if (☃ == null) {
            return false;
         } else if (!☃.func_70089_S()) {
            return false;
         } else {
            return !(☃ instanceof EntityPlayer) || !((EntityPlayer)☃).field_71075_bZ.field_75102_a;
         }
      }

      @Override
      public void func_75249_e() {
         this.field_179465_b = 300;
         super.func_75249_e();
      }

      @Override
      public boolean func_75253_b() {
         EntityLivingBase ☃ = this.field_179466_a.func_70638_az();
         if (☃ == null) {
            return false;
         } else if (!☃.func_70089_S()) {
            return false;
         } else if (☃ instanceof EntityPlayer && ((EntityPlayer)☃).field_71075_bZ.field_75102_a) {
            return false;
         } else {
            return --this.field_179465_b > 0;
         }
      }

      @Override
      public void func_75246_d() {
         this.field_179466_a.func_70625_a(this.field_179466_a.func_70638_az(), 10.0F, 10.0F);
         ((EntitySlime.SlimeMoveHelper)this.field_179466_a.func_70605_aq())
            .func_179920_a(this.field_179466_a.field_70177_z, this.field_179466_a.func_70800_m());
      }
   }

   static class AISlimeFaceRandom extends EntityAIBase {
      private final EntitySlime field_179461_a;
      private float field_179459_b;
      private int field_179460_c;

      public AISlimeFaceRandom(EntitySlime var1) {
         this.field_179461_a = ☃;
         this.func_75248_a(2);
      }

      @Override
      public boolean func_75250_a() {
         return this.field_179461_a.func_70638_az() == null
            && (
               this.field_179461_a.field_70122_E
                  || this.field_179461_a.func_70090_H()
                  || this.field_179461_a.func_180799_ab()
                  || this.field_179461_a.func_70644_a(MobEffects.field_188424_y)
            );
      }

      @Override
      public void func_75246_d() {
         if (--this.field_179460_c <= 0) {
            this.field_179460_c = 40 + this.field_179461_a.func_70681_au().nextInt(60);
            this.field_179459_b = (float)this.field_179461_a.func_70681_au().nextInt(360);
         }

         ((EntitySlime.SlimeMoveHelper)this.field_179461_a.func_70605_aq()).func_179920_a(this.field_179459_b, false);
      }
   }

   static class AISlimeFloat extends EntityAIBase {
      private final EntitySlime field_179457_a;

      public AISlimeFloat(EntitySlime var1) {
         this.field_179457_a = ☃;
         this.func_75248_a(5);
         ((PathNavigateGround)☃.func_70661_as()).func_212239_d(true);
      }

      @Override
      public boolean func_75250_a() {
         return this.field_179457_a.func_70090_H() || this.field_179457_a.func_180799_ab();
      }

      @Override
      public void func_75246_d() {
         if (this.field_179457_a.func_70681_au().nextFloat() < 0.8F) {
            this.field_179457_a.func_70683_ar().func_75660_a();
         }

         ((EntitySlime.SlimeMoveHelper)this.field_179457_a.func_70605_aq()).func_179921_a(1.2);
      }
   }

   static class AISlimeHop extends EntityAIBase {
      private final EntitySlime field_179458_a;

      public AISlimeHop(EntitySlime var1) {
         this.field_179458_a = ☃;
         this.func_75248_a(5);
      }

      @Override
      public boolean func_75250_a() {
         return true;
      }

      @Override
      public void func_75246_d() {
         ((EntitySlime.SlimeMoveHelper)this.field_179458_a.func_70605_aq()).func_179921_a(1.0);
      }
   }

   static class SlimeMoveHelper extends EntityMoveHelper {
      private float field_179922_g;
      private int field_179924_h;
      private final EntitySlime field_179925_i;
      private boolean field_179923_j;

      public SlimeMoveHelper(EntitySlime var1) {
         super(☃);
         this.field_179925_i = ☃;
         this.field_179922_g = 180.0F * ☃.field_70177_z / (float) Math.PI;
      }

      public void func_179920_a(float var1, boolean var2) {
         this.field_179922_g = ☃;
         this.field_179923_j = ☃;
      }

      public void func_179921_a(double var1) {
         this.field_75645_e = ☃;
         this.field_188491_h = EntityMoveHelper.Action.MOVE_TO;
      }

      @Override
      public void func_75641_c() {
         this.field_75648_a.field_70177_z = this.func_75639_a(this.field_75648_a.field_70177_z, this.field_179922_g, 90.0F);
         this.field_75648_a.field_70759_as = this.field_75648_a.field_70177_z;
         this.field_75648_a.field_70761_aq = this.field_75648_a.field_70177_z;
         if (this.field_188491_h != EntityMoveHelper.Action.MOVE_TO) {
            this.field_75648_a.func_191989_p(0.0F);
         } else {
            this.field_188491_h = EntityMoveHelper.Action.WAIT;
            if (this.field_75648_a.field_70122_E) {
               this.field_75648_a
                  .func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
               if (this.field_179924_h-- <= 0) {
                  this.field_179924_h = this.field_179925_i.func_70806_k();
                  if (this.field_179923_j) {
                     this.field_179924_h /= 3;
                  }

                  this.field_179925_i.func_70683_ar().func_75660_a();
                  if (this.field_179925_i.func_70807_r()) {
                     this.field_179925_i
                        .func_184185_a(
                           this.field_179925_i.func_184710_cZ(),
                           this.field_179925_i.func_70599_aP(),
                           ((this.field_179925_i.func_70681_au().nextFloat() - this.field_179925_i.func_70681_au().nextFloat()) * 0.2F + 1.0F) * 0.8F
                        );
                  }
               } else {
                  this.field_179925_i.field_70702_br = 0.0F;
                  this.field_179925_i.field_191988_bg = 0.0F;
                  this.field_75648_a.func_70659_e(0.0F);
               }
            } else {
               this.field_75648_a
                  .func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
            }
         }
      }
   }
}
