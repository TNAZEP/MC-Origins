package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySilverfish extends EntityMob {
   private EntitySilverfish.AISummonSilverfish field_175460_b;

   public EntitySilverfish(World var1) {
      super(EntityType.field_200740_af, ☃);
      this.func_70105_a(0.4F, 0.3F);
   }

   @Override
   protected void func_184651_r() {
      this.field_175460_b = new EntitySilverfish.AISummonSilverfish(this);
      this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(3, this.field_175460_b);
      this.field_70714_bg.func_75776_a(4, new EntityAIAttackMelee(this, 1.0, false));
      this.field_70714_bg.func_75776_a(5, new EntitySilverfish.AIHideInStone(this));
      this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, true));
      this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
   }

   @Override
   public double func_70033_W() {
      return 0.1;
   }

   @Override
   public float func_70047_e() {
      return 0.1F;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0);
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return SoundEvents.field_187793_eY;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187850_fa;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187795_eZ;
   }

   @Override
   protected void func_180429_a(BlockPos var1, IBlockState var2) {
      this.func_184185_a(SoundEvents.field_187852_fb, 0.15F, 1.0F);
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         if ((☃ instanceof EntityDamageSource || ☃ == DamageSource.field_76376_m) && this.field_175460_b != null) {
            this.field_175460_b.func_179462_f();
         }

         return super.func_70097_a(☃, ☃);
      }
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186438_t;
   }

   @Override
   public void func_70071_h_() {
      this.field_70761_aq = this.field_70177_z;
      super.func_70071_h_();
   }

   @Override
   public void func_181013_g(float var1) {
      this.field_70177_z = ☃;
      super.func_181013_g(☃);
   }

   @Override
   public float func_205022_a(BlockPos var1, IWorldReaderBase var2) {
      return BlockSilverfish.func_196466_i(☃.func_180495_p(☃.func_177977_b())) ? 10.0F : super.func_205022_a(☃, ☃);
   }

   @Override
   protected boolean func_70814_o() {
      return true;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      if (super.func_205020_a(☃, ☃)) {
         EntityPlayer ☃ = ☃.func_184136_b(this, 5.0);
         return ☃ == null;
      } else {
         return false;
      }
   }

   @Override
   public CreatureAttribute func_70668_bt() {
      return CreatureAttribute.ARTHROPOD;
   }

   static class AIHideInStone extends EntityAIWander {
      private EnumFacing field_179483_b;
      private boolean field_179484_c;

      public AIHideInStone(EntitySilverfish var1) {
         super(☃, 1.0, 10);
         this.func_75248_a(1);
      }

      @Override
      public boolean func_75250_a() {
         if (this.field_75457_a.func_70638_az() != null) {
            return false;
         } else if (!this.field_75457_a.func_70661_as().func_75500_f()) {
            return false;
         } else {
            Random ☃ = this.field_75457_a.func_70681_au();
            if (this.field_75457_a.field_70170_p.func_82736_K().func_82766_b("mobGriefing") && ☃.nextInt(10) == 0) {
               this.field_179483_b = EnumFacing.func_176741_a(☃);
               BlockPos ☃x = new BlockPos(this.field_75457_a.field_70165_t, this.field_75457_a.field_70163_u + 0.5, this.field_75457_a.field_70161_v)
                  .func_177972_a(this.field_179483_b);
               IBlockState ☃xx = this.field_75457_a.field_70170_p.func_180495_p(☃x);
               if (BlockSilverfish.func_196466_i(☃xx)) {
                  this.field_179484_c = true;
                  return true;
               }
            }

            this.field_179484_c = false;
            return super.func_75250_a();
         }
      }

      @Override
      public boolean func_75253_b() {
         return this.field_179484_c ? false : super.func_75253_b();
      }

      @Override
      public void func_75249_e() {
         if (!this.field_179484_c) {
            super.func_75249_e();
         } else {
            IWorld ☃ = this.field_75457_a.field_70170_p;
            BlockPos ☃x = new BlockPos(this.field_75457_a.field_70165_t, this.field_75457_a.field_70163_u + 0.5, this.field_75457_a.field_70161_v)
               .func_177972_a(this.field_179483_b);
            IBlockState ☃xx = ☃.func_180495_p(☃x);
            if (BlockSilverfish.func_196466_i(☃xx)) {
               ☃.func_180501_a(☃x, BlockSilverfish.func_196467_h(☃xx.func_177230_c()), 3);
               this.field_75457_a.func_70656_aK();
               this.field_75457_a.func_70106_y();
            }
         }
      }
   }

   static class AISummonSilverfish extends EntityAIBase {
      private final EntitySilverfish field_179464_a;
      private int field_179463_b;

      public AISummonSilverfish(EntitySilverfish var1) {
         this.field_179464_a = ☃;
      }

      public void func_179462_f() {
         if (this.field_179463_b == 0) {
            this.field_179463_b = 20;
         }
      }

      @Override
      public boolean func_75250_a() {
         return this.field_179463_b > 0;
      }

      @Override
      public void func_75246_d() {
         --this.field_179463_b;
         if (this.field_179463_b <= 0) {
            World ☃ = this.field_179464_a.field_70170_p;
            Random ☃x = this.field_179464_a.func_70681_au();
            BlockPos ☃xx = new BlockPos(this.field_179464_a);

            for(int ☃xxx = 0; ☃xxx <= 5 && ☃xxx >= -5; ☃xxx = (☃xxx <= 0 ? 1 : 0) - ☃xxx) {
               for(int ☃xxxx = 0; ☃xxxx <= 10 && ☃xxxx >= -10; ☃xxxx = (☃xxxx <= 0 ? 1 : 0) - ☃xxxx) {
                  for(int ☃xxxxx = 0; ☃xxxxx <= 10 && ☃xxxxx >= -10; ☃xxxxx = (☃xxxxx <= 0 ? 1 : 0) - ☃xxxxx) {
                     BlockPos ☃xxxxxx = ☃xx.func_177982_a(☃xxxx, ☃xxx, ☃xxxxx);
                     IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxxxx);
                     Block ☃xxxxxxxx = ☃xxxxxxx.func_177230_c();
                     if (☃xxxxxxxx instanceof BlockSilverfish) {
                        if (☃.func_82736_K().func_82766_b("mobGriefing")) {
                           ☃.func_175655_b(☃xxxxxx, true);
                        } else {
                           ☃.func_180501_a(☃xxxxxx, ((BlockSilverfish)☃xxxxxxxx).func_196468_d().func_176223_P(), 3);
                        }

                        if (☃x.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
