package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySquid extends EntityWaterMob {
   public float field_70861_d;
   public float field_70862_e;
   public float field_70859_f;
   public float field_70860_g;
   public float field_70867_h;
   public float field_70868_i;
   public float field_70866_j;
   public float field_70865_by;
   private float field_70863_bz;
   private float field_70864_bA;
   private float field_70871_bB;
   private float field_70872_bC;
   private float field_70869_bD;
   private float field_70870_bE;

   public EntitySquid(World var1) {
      super(EntityType.field_200749_ao, ☃);
      this.func_70105_a(0.8F, 0.8F);
      this.field_70146_Z.setSeed((long)(1 + this.func_145782_y()));
      this.field_70864_bA = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntitySquid.AIMoveRandom(this));
      this.field_70714_bg.func_75776_a(1, new EntitySquid.AIFlee());
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0);
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O * 0.5F;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187829_fQ;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187833_fS;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187831_fR;
   }

   @Override
   protected float func_70599_aP() {
      return 0.4F;
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186381_af;
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      this.field_70862_e = this.field_70861_d;
      this.field_70860_g = this.field_70859_f;
      this.field_70868_i = this.field_70867_h;
      this.field_70865_by = this.field_70866_j;
      this.field_70867_h += this.field_70864_bA;
      if ((double)this.field_70867_h > Math.PI * 2) {
         if (this.field_70170_p.field_72995_K) {
            this.field_70867_h = (float) (Math.PI * 2);
         } else {
            this.field_70867_h = (float)((double)this.field_70867_h - (Math.PI * 2));
            if (this.field_70146_Z.nextInt(10) == 0) {
               this.field_70864_bA = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
            }

            this.field_70170_p.func_72960_a(this, (byte)19);
         }
      }

      if (this.func_203005_aq()) {
         if (this.field_70867_h < (float) Math.PI) {
            float ☃ = this.field_70867_h / (float) Math.PI;
            this.field_70866_j = MathHelper.func_76126_a(☃ * ☃ * (float) Math.PI) * (float) Math.PI * 0.25F;
            if ((double)☃ > 0.75) {
               this.field_70863_bz = 1.0F;
               this.field_70871_bB = 1.0F;
            } else {
               this.field_70871_bB *= 0.8F;
            }
         } else {
            this.field_70866_j = 0.0F;
            this.field_70863_bz *= 0.9F;
            this.field_70871_bB *= 0.99F;
         }

         if (!this.field_70170_p.field_72995_K) {
            this.field_70159_w = (double)(this.field_70872_bC * this.field_70863_bz);
            this.field_70181_x = (double)(this.field_70869_bD * this.field_70863_bz);
            this.field_70179_y = (double)(this.field_70870_bE * this.field_70863_bz);
         }

         float ☃ = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
         this.field_70761_aq += (-((float)MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y)) * (180.0F / (float)Math.PI) - this.field_70761_aq)
            * 0.1F;
         this.field_70177_z = this.field_70761_aq;
         this.field_70859_f = (float)((double)this.field_70859_f + Math.PI * (double)this.field_70871_bB * 1.5);
         this.field_70861_d += (-((float)MathHelper.func_181159_b((double)☃, this.field_70181_x)) * (180.0F / (float)Math.PI) - this.field_70861_d) * 0.1F;
      } else {
         this.field_70866_j = MathHelper.func_76135_e(MathHelper.func_76126_a(this.field_70867_h)) * (float) Math.PI * 0.25F;
         if (!this.field_70170_p.field_72995_K) {
            this.field_70159_w = 0.0;
            this.field_70179_y = 0.0;
            if (this.func_70644_a(MobEffects.field_188424_y)) {
               this.field_70181_x += 0.05 * (double)(this.func_70660_b(MobEffects.field_188424_y).func_76458_c() + 1) - this.field_70181_x;
            } else if (!this.func_189652_ae()) {
               this.field_70181_x -= 0.08;
            }

            this.field_70181_x *= 0.98F;
         }

         this.field_70861_d = (float)((double)this.field_70861_d + (double)(-90.0F - this.field_70861_d) * 0.02);
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (super.func_70097_a(☃, ☃) && this.func_70643_av() != null) {
         this.func_203039_dq();
         return true;
      } else {
         return false;
      }
   }

   private Vec3d func_207400_b(Vec3d var1) {
      Vec3d ☃ = ☃.func_178789_a(this.field_70862_e * (float) (Math.PI / 180.0));
      return ☃.func_178785_b(-this.field_70760_ar * (float) (Math.PI / 180.0));
   }

   private void func_203039_dq() {
      this.func_184185_a(SoundEvents.field_203639_hT, this.func_70599_aP(), this.func_70647_i());
      Vec3d ☃ = this.func_207400_b(new Vec3d(0.0, -1.0, 0.0)).func_72441_c(this.field_70165_t, this.field_70163_u, this.field_70161_v);

      for(int ☃x = 0; ☃x < 30; ++☃x) {
         Vec3d ☃xx = this.func_207400_b(new Vec3d((double)this.field_70146_Z.nextFloat() * 0.6 - 0.3, -1.0, (double)this.field_70146_Z.nextFloat() * 0.6 - 0.3));
         Vec3d ☃xxx = ☃xx.func_186678_a(0.3 + (double)(this.field_70146_Z.nextFloat() * 2.0F));
         ((WorldServer)this.field_70170_p)
            .func_195598_a(
               Particles.field_203219_V,
               ☃.field_72450_a,
               ☃.field_72448_b + 0.5,
               ☃.field_72449_c,
               0,
               ☃xxx.field_72450_a,
               ☃xxx.field_72448_b,
               ☃xxx.field_72449_c,
               0.1F
            );
      }
   }

   @Override
   public void func_191986_a(float var1, float var2, float var3) {
      this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return this.field_70163_u > 45.0 && this.field_70163_u < (double)☃.func_181545_F();
   }

   public void func_175568_b(float var1, float var2, float var3) {
      this.field_70872_bC = ☃;
      this.field_70869_bD = ☃;
      this.field_70870_bE = ☃;
   }

   public boolean func_175567_n() {
      return this.field_70872_bC != 0.0F || this.field_70869_bD != 0.0F || this.field_70870_bE != 0.0F;
   }

   class AIFlee extends EntityAIBase {
      private int field_203125_b;

      private AIFlee() {
      }

      @Override
      public boolean func_75250_a() {
         EntityLivingBase ☃ = EntitySquid.this.func_70643_av();
         if (EntitySquid.this.func_70090_H() && ☃ != null) {
            return EntitySquid.this.func_70068_e(☃) < 100.0;
         } else {
            return false;
         }
      }

      @Override
      public void func_75249_e() {
         this.field_203125_b = 0;
      }

      @Override
      public void func_75246_d() {
         ++this.field_203125_b;
         EntityLivingBase ☃ = EntitySquid.this.func_70643_av();
         if (☃ != null) {
            Vec3d ☃x = new Vec3d(
               EntitySquid.this.field_70165_t - ☃.field_70165_t,
               EntitySquid.this.field_70163_u - ☃.field_70163_u,
               EntitySquid.this.field_70161_v - ☃.field_70161_v
            );
            IBlockState ☃xx = EntitySquid.this.field_70170_p
               .func_180495_p(
                  new BlockPos(
                     EntitySquid.this.field_70165_t + ☃x.field_72450_a,
                     EntitySquid.this.field_70163_u + ☃x.field_72448_b,
                     EntitySquid.this.field_70161_v + ☃x.field_72449_c
                  )
               );
            IFluidState ☃xxx = EntitySquid.this.field_70170_p
               .func_204610_c(
                  new BlockPos(
                     EntitySquid.this.field_70165_t + ☃x.field_72450_a,
                     EntitySquid.this.field_70163_u + ☃x.field_72448_b,
                     EntitySquid.this.field_70161_v + ☃x.field_72449_c
                  )
               );
            if (☃xxx.func_206884_a(FluidTags.field_206959_a) || ☃xx.func_196958_f()) {
               double ☃xxxx = ☃x.func_72433_c();
               if (☃xxxx > 0.0) {
                  ☃x.func_72432_b();
                  float ☃xxxxx = 3.0F;
                  if (☃xxxx > 5.0) {
                     ☃xxxxx = (float)((double)☃xxxxx - (☃xxxx - 5.0) / 5.0);
                  }

                  if (☃xxxxx > 0.0F) {
                     ☃x = ☃x.func_186678_a((double)☃xxxxx);
                  }
               }

               if (☃xx.func_196958_f()) {
                  ☃x = ☃x.func_178786_a(0.0, ☃x.field_72448_b, 0.0);
               }

               EntitySquid.this.func_175568_b((float)☃x.field_72450_a / 20.0F, (float)☃x.field_72448_b / 20.0F, (float)☃x.field_72449_c / 20.0F);
            }

            if (this.field_203125_b % 10 == 5) {
               EntitySquid.this.field_70170_p
                  .func_195594_a(
                     Particles.field_197612_e, EntitySquid.this.field_70165_t, EntitySquid.this.field_70163_u, EntitySquid.this.field_70161_v, 0.0, 0.0, 0.0
                  );
            }
         }
      }
   }

   class AIMoveRandom extends EntityAIBase {
      private final EntitySquid field_179476_a;

      public AIMoveRandom(EntitySquid var2) {
         this.field_179476_a = ☃;
      }

      @Override
      public boolean func_75250_a() {
         return true;
      }

      @Override
      public void func_75246_d() {
         int ☃ = this.field_179476_a.func_70654_ax();
         if (☃ > 100) {
            this.field_179476_a.func_175568_b(0.0F, 0.0F, 0.0F);
         } else if (this.field_179476_a.func_70681_au().nextInt(50) == 0 || !this.field_179476_a.field_70171_ac || !this.field_179476_a.func_175567_n()) {
            float ☃ = this.field_179476_a.func_70681_au().nextFloat() * (float) (Math.PI * 2);
            float ☃x = MathHelper.func_76134_b(☃) * 0.2F;
            float ☃xx = -0.1F + this.field_179476_a.func_70681_au().nextFloat() * 0.2F;
            float ☃xxx = MathHelper.func_76126_a(☃) * 0.2F;
            this.field_179476_a.func_175568_b(☃x, ☃xx, ☃xxx);
         }
      }
   }
}
