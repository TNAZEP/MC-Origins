package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob {
   protected EntityMob(EntityType<?> var1, World var2) {
      super(☃, ☃);
      this.field_70728_aV = 5;
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   @Override
   public void func_70636_d() {
      this.func_82168_bl();
      float ☃ = this.func_70013_c();
      if (☃ > 0.5F) {
         this.field_70708_bq += 2;
      }

      super.func_70636_d();
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
         this.func_70106_y();
      }
   }

   @Override
   protected SoundEvent func_184184_Z() {
      return SoundEvents.field_187593_cC;
   }

   @Override
   protected SoundEvent func_184181_aa() {
      return SoundEvents.field_187591_cB;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      return this.func_180431_b(☃) ? false : super.func_70097_a(☃, ☃);
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187741_cz;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187738_cy;
   }

   @Override
   protected SoundEvent func_184588_d(int var1) {
      return ☃ > 4 ? SoundEvents.field_187735_cx : SoundEvents.field_187589_cA;
   }

   @Override
   public float func_205022_a(BlockPos var1, IWorldReaderBase var2) {
      return 0.5F - ☃.func_205052_D(☃);
   }

   protected boolean func_70814_o() {
      BlockPos ☃ = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
      if (this.field_70170_p.func_175642_b(EnumLightType.SKY, ☃) > this.field_70146_Z.nextInt(32)) {
         return false;
      } else {
         int ☃ = this.field_70170_p.func_72911_I() ? this.field_70170_p.func_205049_d(☃, 10) : this.field_70170_p.func_201696_r(☃);
         return ☃ <= this.field_70146_Z.nextInt(8);
      }
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return ☃.func_175659_aa() != EnumDifficulty.PEACEFUL && this.func_70814_o() && super.func_205020_a(☃, ☃);
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
   }

   @Override
   protected boolean func_146066_aG() {
      return true;
   }

   public boolean func_191990_c(EntityPlayer var1) {
      return true;
   }
}
