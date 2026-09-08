package net.minecraft.entity.passive;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityBat extends EntityAmbientCreature {
   private static final DataParameter<Byte> field_184660_a = EntityDataManager.func_187226_a(EntityBat.class, DataSerializers.field_187191_a);
   private BlockPos field_82237_a;

   public EntityBat(World var1) {
      super(EntityType.field_200791_e, ☃);
      this.func_70105_a(0.5F, 0.9F);
      this.func_82236_f(true);
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184660_a, (byte)0);
   }

   @Override
   protected float func_70599_aP() {
      return 0.1F;
   }

   @Override
   protected float func_70647_i() {
      return super.func_70647_i() * 0.95F;
   }

   @Nullable
   @Override
   public SoundEvent func_184639_G() {
      return this.func_82235_h() && this.field_70146_Z.nextInt(4) != 0 ? null : SoundEvents.field_187740_w;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187743_y;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187742_x;
   }

   @Override
   public boolean func_70104_M() {
      return false;
   }

   @Override
   protected void func_82167_n(Entity var1) {
   }

   @Override
   protected void func_85033_bc() {
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(6.0);
   }

   public boolean func_82235_h() {
      return (this.field_70180_af.func_187225_a(field_184660_a) & 1) != 0;
   }

   public void func_82236_f(boolean var1) {
      byte ☃ = this.field_70180_af.func_187225_a(field_184660_a);
      if (☃) {
         this.field_70180_af.func_187227_b(field_184660_a, (byte)(☃ | 1));
      } else {
         this.field_70180_af.func_187227_b(field_184660_a, (byte)(☃ & -2));
      }
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.func_82235_h()) {
         this.field_70159_w = 0.0;
         this.field_70181_x = 0.0;
         this.field_70179_y = 0.0;
         this.field_70163_u = (double)MathHelper.func_76128_c(this.field_70163_u) + 1.0 - (double)this.field_70131_O;
      } else {
         this.field_70181_x *= 0.6F;
      }
   }

   @Override
   protected void func_70619_bc() {
      super.func_70619_bc();
      BlockPos ☃ = new BlockPos(this);
      BlockPos ☃x = ☃.func_177984_a();
      if (this.func_82235_h()) {
         if (this.field_70170_p.func_180495_p(☃x).func_185915_l()) {
            if (this.field_70146_Z.nextInt(200) == 0) {
               this.field_70759_as = (float)this.field_70146_Z.nextInt(360);
            }

            if (this.field_70170_p.func_184136_b(this, 4.0) != null) {
               this.func_82236_f(false);
               this.field_70170_p.func_180498_a(null, 1025, ☃, 0);
            }
         } else {
            this.func_82236_f(false);
            this.field_70170_p.func_180498_a(null, 1025, ☃, 0);
         }
      } else {
         if (this.field_82237_a != null && (!this.field_70170_p.func_175623_d(this.field_82237_a) || this.field_82237_a.func_177956_o() < 1)) {
            this.field_82237_a = null;
         }

         if (this.field_82237_a == null
            || this.field_70146_Z.nextInt(30) == 0
            || this.field_82237_a.func_177954_c((double)((int)this.field_70165_t), (double)((int)this.field_70163_u), (double)((int)this.field_70161_v)) < 4.0) {
            this.field_82237_a = new BlockPos(
               (int)this.field_70165_t + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7),
               (int)this.field_70163_u + this.field_70146_Z.nextInt(6) - 2,
               (int)this.field_70161_v + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7)
            );
         }

         double ☃ = (double)this.field_82237_a.func_177958_n() + 0.5 - this.field_70165_t;
         double ☃x = (double)this.field_82237_a.func_177956_o() + 0.1 - this.field_70163_u;
         double ☃xx = (double)this.field_82237_a.func_177952_p() + 0.5 - this.field_70161_v;
         this.field_70159_w += (Math.signum(☃) * 0.5 - this.field_70159_w) * 0.1F;
         this.field_70181_x += (Math.signum(☃x) * 0.7F - this.field_70181_x) * 0.1F;
         this.field_70179_y += (Math.signum(☃xx) * 0.5 - this.field_70179_y) * 0.1F;
         float ☃xxx = (float)(MathHelper.func_181159_b(this.field_70179_y, this.field_70159_w) * 180.0F / (float)Math.PI) - 90.0F;
         float ☃xxxx = MathHelper.func_76142_g(☃xxx - this.field_70177_z);
         this.field_191988_bg = 0.5F;
         this.field_70177_z += ☃xxxx;
         if (this.field_70146_Z.nextInt(100) == 0 && this.field_70170_p.func_180495_p(☃x).func_185915_l()) {
            this.func_82236_f(true);
         }
      }
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   public void func_180430_e(float var1, float var2) {
   }

   @Override
   protected void func_184231_a(double var1, boolean var3, IBlockState var4, BlockPos var5) {
   }

   @Override
   public boolean func_145773_az() {
      return true;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         if (!this.field_70170_p.field_72995_K && this.func_82235_h()) {
            this.func_82236_f(false);
         }

         return super.func_70097_a(☃, ☃);
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_70180_af.func_187227_b(field_184660_a, ☃.func_74771_c("BatFlags"));
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74774_a("BatFlags", this.field_70180_af.func_187225_a(field_184660_a));
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      BlockPos ☃ = new BlockPos(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v);
      if (☃.func_177956_o() >= ☃.func_181545_F()) {
         return false;
      } else {
         int ☃ = ☃.func_201696_r(☃);
         int ☃x = 4;
         if (this.func_205021_dt()) {
            ☃x = 7;
         } else if (this.field_70146_Z.nextBoolean()) {
            return false;
         }

         return ☃ > this.field_70146_Z.nextInt(☃x) ? false : super.func_205020_a(☃, ☃);
      }
   }

   private boolean func_205021_dt() {
      LocalDate ☃ = LocalDate.now();
      int ☃x = ☃.get(ChronoField.DAY_OF_MONTH);
      int ☃xx = ☃.get(ChronoField.MONTH_OF_YEAR);
      return ☃xx == 10 && ☃x >= 20 || ☃xx == 11 && ☃x <= 3;
   }

   @Override
   public float func_70047_e() {
      return this.field_70131_O / 2.0F;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186377_ab;
   }
}
