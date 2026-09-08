package net.minecraft.entity.monster;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityEvoker extends EntitySpellcasterIllager {
   private EntitySheep field_190763_bw;

   public EntityEvoker(World var1) {
      super(EntityType.field_200806_t, ☃);
      this.func_70105_a(0.6F, 1.95F);
      this.field_70728_aV = 10;
   }

   @Override
   protected void func_184651_r() {
      super.func_184651_r();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityEvoker.AICastingSpell());
      this.field_70714_bg.func_75776_a(2, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 0.6, 1.0));
      this.field_70714_bg.func_75776_a(4, new EntityEvoker.AISummonSpell());
      this.field_70714_bg.func_75776_a(5, new EntityEvoker.AIAttackSpell());
      this.field_70714_bg.func_75776_a(6, new EntityEvoker.AIWololoSpell());
      this.field_70714_bg.func_75776_a(8, new EntityAIWander(this, 0.6));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true, EntityEvoker.class));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true).func_190882_b(300));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false).func_190882_b(300));
      this.field_70715_bh.func_75776_a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, false));
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(12.0);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
   }

   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191185_au;
   }

   @Override
   protected void func_70619_bc() {
      super.func_70619_bc();
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
   }

   @Override
   public boolean func_184191_r(Entity var1) {
      if (☃ == null) {
         return false;
      } else if (☃ == this) {
         return true;
      } else if (super.func_184191_r(☃)) {
         return true;
      } else if (☃ instanceof EntityVex) {
         return this.func_184191_r(((EntityVex)☃).func_190645_o());
      } else if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70668_bt() == CreatureAttribute.ILLAGER) {
         return this.func_96124_cp() == null && ☃.func_96124_cp() == null;
      } else {
         return false;
      }
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_191243_bm;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_191245_bo;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_191246_bp;
   }

   private void func_190748_a(@Nullable EntitySheep var1) {
      this.field_190763_bw = ☃;
   }

   @Nullable
   private EntitySheep func_190751_dj() {
      return this.field_190763_bw;
   }

   @Override
   protected SoundEvent func_193086_dk() {
      return SoundEvents.field_191244_bn;
   }

   class AIAttackSpell extends EntitySpellcasterIllager.AIUseSpell {
      private AIAttackSpell() {
      }

      @Override
      protected int func_190869_f() {
         return 40;
      }

      @Override
      protected int func_190872_i() {
         return 100;
      }

      @Override
      protected void func_190868_j() {
         EntityLivingBase ☃ = EntityEvoker.this.func_70638_az();
         double ☃x = Math.min(☃.field_70163_u, EntityEvoker.this.field_70163_u);
         double ☃xx = Math.max(☃.field_70163_u, EntityEvoker.this.field_70163_u) + 1.0;
         float ☃xxx = (float)MathHelper.func_181159_b(☃.field_70161_v - EntityEvoker.this.field_70161_v, ☃.field_70165_t - EntityEvoker.this.field_70165_t);
         if (EntityEvoker.this.func_70068_e(☃) < 9.0) {
            for(int ☃xxxx = 0; ☃xxxx < 5; ++☃xxxx) {
               float ☃xxxxx = ☃xxx + (float)☃xxxx * (float) Math.PI * 0.4F;
               this.func_190876_a(
                  EntityEvoker.this.field_70165_t + (double)MathHelper.func_76134_b(☃xxxxx) * 1.5,
                  EntityEvoker.this.field_70161_v + (double)MathHelper.func_76126_a(☃xxxxx) * 1.5,
                  ☃x,
                  ☃xx,
                  ☃xxxxx,
                  0
               );
            }

            for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
               float ☃xxxxx = ☃xxx + (float)☃xxxx * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.func_190876_a(
                  EntityEvoker.this.field_70165_t + (double)MathHelper.func_76134_b(☃xxxxx) * 2.5,
                  EntityEvoker.this.field_70161_v + (double)MathHelper.func_76126_a(☃xxxxx) * 2.5,
                  ☃x,
                  ☃xx,
                  ☃xxxxx,
                  3
               );
            }
         } else {
            for(int ☃ = 0; ☃ < 16; ++☃) {
               double ☃x = 1.25 * (double)(☃ + 1);
               int ☃xx = 1 * ☃;
               this.func_190876_a(
                  EntityEvoker.this.field_70165_t + (double)MathHelper.func_76134_b(☃xxx) * ☃x,
                  EntityEvoker.this.field_70161_v + (double)MathHelper.func_76126_a(☃xxx) * ☃x,
                  ☃x,
                  ☃xx,
                  ☃xxx,
                  ☃xx
               );
            }
         }
      }

      private void func_190876_a(double var1, double var3, double var5, double var7, float var9, int var10) {
         BlockPos ☃ = new BlockPos(☃, ☃, ☃);
         boolean ☃x = false;
         double ☃xx = 0.0;

         do {
            if (!EntityEvoker.this.field_70170_p.func_195595_w(☃) && EntityEvoker.this.field_70170_p.func_195595_w(☃.func_177977_b())) {
               if (!EntityEvoker.this.field_70170_p.func_175623_d(☃)) {
                  IBlockState ☃xxx = EntityEvoker.this.field_70170_p.func_180495_p(☃);
                  VoxelShape ☃xxxx = ☃xxx.func_196952_d(EntityEvoker.this.field_70170_p, ☃);
                  if (!☃xxxx.func_197766_b()) {
                     ☃xx = ☃xxxx.func_197758_c(EnumFacing.Axis.Y);
                  }
               }

               ☃x = true;
               break;
            }

            ☃ = ☃.func_177977_b();
         } while(☃.func_177956_o() >= MathHelper.func_76128_c(☃) - 1);

         if (☃x) {
            EntityEvokerFangs ☃xxx = new EntityEvokerFangs(EntityEvoker.this.field_70170_p, ☃, (double)☃.func_177956_o() + ☃xx, ☃, ☃, ☃, EntityEvoker.this);
            EntityEvoker.this.field_70170_p.func_72838_d(☃xxx);
         }
      }

      @Override
      protected SoundEvent func_190871_k() {
         return SoundEvents.field_191247_bq;
      }

      @Override
      protected EntitySpellcasterIllager.SpellType func_193320_l() {
         return EntitySpellcasterIllager.SpellType.FANGS;
      }
   }

   class AICastingSpell extends EntitySpellcasterIllager.AICastingApell {
      private AICastingSpell() {
      }

      @Override
      public void func_75246_d() {
         if (EntityEvoker.this.func_70638_az() != null) {
            EntityEvoker.this.func_70671_ap()
               .func_75651_a(EntityEvoker.this.func_70638_az(), (float)EntityEvoker.this.func_184649_cE(), (float)EntityEvoker.this.func_70646_bf());
         } else if (EntityEvoker.this.func_190751_dj() != null) {
            EntityEvoker.this.func_70671_ap()
               .func_75651_a(EntityEvoker.this.func_190751_dj(), (float)EntityEvoker.this.func_184649_cE(), (float)EntityEvoker.this.func_70646_bf());
         }
      }
   }

   class AISummonSpell extends EntitySpellcasterIllager.AIUseSpell {
      private AISummonSpell() {
      }

      @Override
      public boolean func_75250_a() {
         if (!super.func_75250_a()) {
            return false;
         } else {
            int ☃ = EntityEvoker.this.field_70170_p.func_72872_a(EntityVex.class, EntityEvoker.this.func_174813_aQ().func_186662_g(16.0)).size();
            return EntityEvoker.this.field_70146_Z.nextInt(8) + 1 > ☃;
         }
      }

      @Override
      protected int func_190869_f() {
         return 100;
      }

      @Override
      protected int func_190872_i() {
         return 340;
      }

      @Override
      protected void func_190868_j() {
         for(int ☃ = 0; ☃ < 3; ++☃) {
            BlockPos ☃x = new BlockPos(EntityEvoker.this)
               .func_177982_a(-2 + EntityEvoker.this.field_70146_Z.nextInt(5), 1, -2 + EntityEvoker.this.field_70146_Z.nextInt(5));
            EntityVex ☃xx = new EntityVex(EntityEvoker.this.field_70170_p);
            ☃xx.func_174828_a(☃x, 0.0F, 0.0F);
            ☃xx.func_204210_a(EntityEvoker.this.field_70170_p.func_175649_E(☃x), null, null);
            ☃xx.func_190658_a(EntityEvoker.this);
            ☃xx.func_190651_g(☃x);
            ☃xx.func_190653_a(20 * (30 + EntityEvoker.this.field_70146_Z.nextInt(90)));
            EntityEvoker.this.field_70170_p.func_72838_d(☃xx);
         }
      }

      @Override
      protected SoundEvent func_190871_k() {
         return SoundEvents.field_191248_br;
      }

      @Override
      protected EntitySpellcasterIllager.SpellType func_193320_l() {
         return EntitySpellcasterIllager.SpellType.SUMMON_VEX;
      }
   }

   public class AIWololoSpell extends EntitySpellcasterIllager.AIUseSpell {
      private final Predicate<EntitySheep> field_190879_a = var0 -> var0.func_175509_cj() == EnumDyeColor.BLUE;

      @Override
      public boolean func_75250_a() {
         if (EntityEvoker.this.func_70638_az() != null) {
            return false;
         } else if (EntityEvoker.this.func_193082_dl()) {
            return false;
         } else if (EntityEvoker.this.field_70173_aa < this.field_193322_d) {
            return false;
         } else if (!EntityEvoker.this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
            return false;
         } else {
            List<EntitySheep> ☃ = EntityEvoker.this.field_70170_p
               .func_175647_a(EntitySheep.class, EntityEvoker.this.func_174813_aQ().func_72314_b(16.0, 4.0, 16.0), this.field_190879_a);
            if (☃.isEmpty()) {
               return false;
            } else {
               EntityEvoker.this.func_190748_a((EntitySheep)☃.get(EntityEvoker.this.field_70146_Z.nextInt(☃.size())));
               return true;
            }
         }
      }

      @Override
      public boolean func_75253_b() {
         return EntityEvoker.this.func_190751_dj() != null && this.field_193321_c > 0;
      }

      @Override
      public void func_75251_c() {
         super.func_75251_c();
         EntityEvoker.this.func_190748_a(null);
      }

      @Override
      protected void func_190868_j() {
         EntitySheep ☃ = EntityEvoker.this.func_190751_dj();
         if (☃ != null && ☃.func_70089_S()) {
            ☃.func_175512_b(EnumDyeColor.RED);
         }
      }

      @Override
      protected int func_190867_m() {
         return 40;
      }

      @Override
      protected int func_190869_f() {
         return 60;
      }

      @Override
      protected int func_190872_i() {
         return 140;
      }

      @Override
      protected SoundEvent func_190871_k() {
         return SoundEvents.field_191249_bs;
      }

      @Override
      protected EntitySpellcasterIllager.SpellType func_193320_l() {
         return EntitySpellcasterIllager.SpellType.WOLOLO;
      }
   }
}
