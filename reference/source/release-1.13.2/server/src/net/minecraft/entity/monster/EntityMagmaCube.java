package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.particles.IParticleData;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(World var1) {
      super(EntityType.field_200771_K, ☃);
      this.field_70178_ae = true;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2F);
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      return ☃.func_175659_aa() != EnumDifficulty.PEACEFUL;
   }

   @Override
   public boolean func_205019_a(IWorldReaderBase var1) {
      return ☃.func_195587_c(this, this.func_174813_aQ()) && ☃.func_195586_b(this, this.func_174813_aQ()) && !☃.func_72953_d(this.func_174813_aQ());
   }

   @Override
   protected void func_70799_a(int var1, boolean var2) {
      super.func_70799_a(☃, ☃);
      this.func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a((double)(☃ * 3));
   }

   @Override
   public float func_70013_c() {
      return 1.0F;
   }

   @Override
   protected IParticleData func_195404_m() {
      return Particles.field_197631_x;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return this.func_189101_db() ? LootTableList.field_186419_a : LootTableList.field_186379_ad;
   }

   @Override
   public boolean func_70027_ad() {
      return false;
   }

   @Override
   protected int func_70806_k() {
      return super.func_70806_k() * 4;
   }

   @Override
   protected void func_70808_l() {
      this.field_70813_a *= 0.9F;
   }

   @Override
   protected void func_70664_aZ() {
      this.field_70181_x = (double)(0.42F + (float)this.func_70809_q() * 0.1F);
      this.field_70160_al = true;
   }

   @Override
   protected void func_180466_bG(Tag<Fluid> var1) {
      if (☃ == FluidTags.field_206960_b) {
         this.field_70181_x = (double)(0.22F + (float)this.func_70809_q() * 0.05F);
         this.field_70160_al = true;
      } else {
         super.func_180466_bG(☃);
      }
   }

   @Override
   public void func_180430_e(float var1, float var2) {
   }

   @Override
   protected boolean func_70800_m() {
      return this.func_70613_aW();
   }

   @Override
   protected int func_70805_n() {
      return super.func_70805_n() + 2;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return this.func_189101_db() ? SoundEvents.field_187892_fv : SoundEvents.field_187760_dh;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return this.func_189101_db() ? SoundEvents.field_187890_fu : SoundEvents.field_187758_dg;
   }

   @Override
   protected SoundEvent func_184709_cY() {
      return this.func_189101_db() ? SoundEvents.field_187894_fw : SoundEvents.field_187764_dj;
   }

   @Override
   protected SoundEvent func_184710_cZ() {
      return SoundEvents.field_187762_di;
   }
}
