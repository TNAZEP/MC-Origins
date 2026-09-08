package net.minecraft.entity.projectile;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityShulkerBullet extends Entity {
   private EntityLivingBase field_184570_a;
   private Entity field_184571_b;
   @Nullable
   private EnumFacing field_184573_c;
   private int field_184575_d;
   private double field_184577_e;
   private double field_184578_f;
   private double field_184579_g;
   @Nullable
   private UUID field_184580_h;
   private BlockPos field_184572_as;
   @Nullable
   private UUID field_184574_at;
   private BlockPos field_184576_au;

   public EntityShulkerBullet(World var1) {
      super(EntityType.field_200739_ae, ☃);
      this.func_70105_a(0.3125F, 0.3125F);
      this.field_70145_X = true;
   }

   public EntityShulkerBullet(World var1, EntityLivingBase var2, Entity var3, EnumFacing.Axis var4) {
      this(☃);
      this.field_184570_a = ☃;
      BlockPos ☃ = new BlockPos(☃);
      double ☃x = (double)☃.func_177958_n() + 0.5;
      double ☃xx = (double)☃.func_177956_o() + 0.5;
      double ☃xxx = (double)☃.func_177952_p() + 0.5;
      this.func_70012_b(☃x, ☃xx, ☃xxx, this.field_70177_z, this.field_70125_A);
      this.field_184571_b = ☃;
      this.field_184573_c = EnumFacing.UP;
      this.func_184569_a(☃);
   }

   @Override
   public SoundCategory func_184176_by() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      if (this.field_184570_a != null) {
         BlockPos ☃ = new BlockPos(this.field_184570_a);
         NBTTagCompound ☃x = NBTUtil.func_186862_a(this.field_184570_a.func_110124_au());
         ☃x.func_74768_a("X", ☃.func_177958_n());
         ☃x.func_74768_a("Y", ☃.func_177956_o());
         ☃x.func_74768_a("Z", ☃.func_177952_p());
         ☃.func_74782_a("Owner", ☃x);
      }

      if (this.field_184571_b != null) {
         BlockPos ☃ = new BlockPos(this.field_184571_b);
         NBTTagCompound ☃x = NBTUtil.func_186862_a(this.field_184571_b.func_110124_au());
         ☃x.func_74768_a("X", ☃.func_177958_n());
         ☃x.func_74768_a("Y", ☃.func_177956_o());
         ☃x.func_74768_a("Z", ☃.func_177952_p());
         ☃.func_74782_a("Target", ☃x);
      }

      if (this.field_184573_c != null) {
         ☃.func_74768_a("Dir", this.field_184573_c.func_176745_a());
      }

      ☃.func_74768_a("Steps", this.field_184575_d);
      ☃.func_74780_a("TXD", this.field_184577_e);
      ☃.func_74780_a("TYD", this.field_184578_f);
      ☃.func_74780_a("TZD", this.field_184579_g);
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      this.field_184575_d = ☃.func_74762_e("Steps");
      this.field_184577_e = ☃.func_74769_h("TXD");
      this.field_184578_f = ☃.func_74769_h("TYD");
      this.field_184579_g = ☃.func_74769_h("TZD");
      if (☃.func_150297_b("Dir", 99)) {
         this.field_184573_c = EnumFacing.func_82600_a(☃.func_74762_e("Dir"));
      }

      if (☃.func_150297_b("Owner", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("Owner");
         this.field_184580_h = NBTUtil.func_186860_b(☃);
         this.field_184572_as = new BlockPos(☃.func_74762_e("X"), ☃.func_74762_e("Y"), ☃.func_74762_e("Z"));
      }

      if (☃.func_150297_b("Target", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("Target");
         this.field_184574_at = NBTUtil.func_186860_b(☃);
         this.field_184576_au = new BlockPos(☃.func_74762_e("X"), ☃.func_74762_e("Y"), ☃.func_74762_e("Z"));
      }
   }

   @Override
   protected void func_70088_a() {
   }

   private void func_184568_a(@Nullable EnumFacing var1) {
      this.field_184573_c = ☃;
   }

   private void func_184569_a(@Nullable EnumFacing.Axis var1) {
      double ☃x = 0.5;
      BlockPos ☃;
      if (this.field_184571_b == null) {
         ☃ = new BlockPos(this).func_177977_b();
      } else {
         ☃x = (double)this.field_184571_b.field_70131_O * 0.5;
         ☃ = new BlockPos(this.field_184571_b.field_70165_t, this.field_184571_b.field_70163_u + ☃x, this.field_184571_b.field_70161_v);
      }

      double ☃ = (double)☃.func_177958_n() + 0.5;
      double ☃x = (double)☃.func_177956_o() + ☃x;
      double ☃xx = (double)☃.func_177952_p() + 0.5;
      EnumFacing ☃xxx = null;
      if (☃.func_177957_d(this.field_70165_t, this.field_70163_u, this.field_70161_v) >= 4.0) {
         BlockPos ☃xxxx = new BlockPos(this);
         List<EnumFacing> ☃xxxxx = Lists.<EnumFacing>newArrayList();
         if (☃ != EnumFacing.Axis.X) {
            if (☃xxxx.func_177958_n() < ☃.func_177958_n() && this.field_70170_p.func_175623_d(☃xxxx.func_177974_f())) {
               ☃xxxxx.add(EnumFacing.EAST);
            } else if (☃xxxx.func_177958_n() > ☃.func_177958_n() && this.field_70170_p.func_175623_d(☃xxxx.func_177976_e())) {
               ☃xxxxx.add(EnumFacing.WEST);
            }
         }

         if (☃ != EnumFacing.Axis.Y) {
            if (☃xxxx.func_177956_o() < ☃.func_177956_o() && this.field_70170_p.func_175623_d(☃xxxx.func_177984_a())) {
               ☃xxxxx.add(EnumFacing.UP);
            } else if (☃xxxx.func_177956_o() > ☃.func_177956_o() && this.field_70170_p.func_175623_d(☃xxxx.func_177977_b())) {
               ☃xxxxx.add(EnumFacing.DOWN);
            }
         }

         if (☃ != EnumFacing.Axis.Z) {
            if (☃xxxx.func_177952_p() < ☃.func_177952_p() && this.field_70170_p.func_175623_d(☃xxxx.func_177968_d())) {
               ☃xxxxx.add(EnumFacing.SOUTH);
            } else if (☃xxxx.func_177952_p() > ☃.func_177952_p() && this.field_70170_p.func_175623_d(☃xxxx.func_177978_c())) {
               ☃xxxxx.add(EnumFacing.NORTH);
            }
         }

         ☃xxx = EnumFacing.func_176741_a(this.field_70146_Z);
         if (☃xxxxx.isEmpty()) {
            for(int ☃xxxx = 5; !this.field_70170_p.func_175623_d(☃xxxx.func_177972_a(☃xxx)) && ☃xxxx > 0; --☃xxxx) {
               ☃xxx = EnumFacing.func_176741_a(this.field_70146_Z);
            }
         } else {
            ☃xxx = (EnumFacing)☃xxxxx.get(this.field_70146_Z.nextInt(☃xxxxx.size()));
         }

         ☃ = this.field_70165_t + (double)☃xxx.func_82601_c();
         ☃x = this.field_70163_u + (double)☃xxx.func_96559_d();
         ☃xx = this.field_70161_v + (double)☃xxx.func_82599_e();
      }

      this.func_184568_a(☃xxx);
      double ☃ = ☃ - this.field_70165_t;
      double ☃x = ☃x - this.field_70163_u;
      double ☃xx = ☃xx - this.field_70161_v;
      double ☃xxx = (double)MathHelper.func_76133_a(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
      if (☃xxx == 0.0) {
         this.field_184577_e = 0.0;
         this.field_184578_f = 0.0;
         this.field_184579_g = 0.0;
      } else {
         this.field_184577_e = ☃ / ☃xxx * 0.15;
         this.field_184578_f = ☃x / ☃xxx * 0.15;
         this.field_184579_g = ☃xx / ☃xxx * 0.15;
      }

      this.field_70160_al = true;
      this.field_184575_d = 10 + this.field_70146_Z.nextInt(5) * 10;
   }

   @Override
   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) {
         this.func_70106_y();
      } else {
         super.func_70071_h_();
         if (!this.field_70170_p.field_72995_K) {
            if (this.field_184571_b == null && this.field_184574_at != null) {
               for(EntityLivingBase ☃ : this.field_70170_p
                  .func_72872_a(
                     EntityLivingBase.class, new AxisAlignedBB(this.field_184576_au.func_177982_a(-2, -2, -2), this.field_184576_au.func_177982_a(2, 2, 2))
                  )) {
                  if (☃.func_110124_au().equals(this.field_184574_at)) {
                     this.field_184571_b = ☃;
                     break;
                  }
               }

               this.field_184574_at = null;
            }

            if (this.field_184570_a == null && this.field_184580_h != null) {
               for(EntityLivingBase ☃ : this.field_70170_p
                  .func_72872_a(
                     EntityLivingBase.class, new AxisAlignedBB(this.field_184572_as.func_177982_a(-2, -2, -2), this.field_184572_as.func_177982_a(2, 2, 2))
                  )) {
                  if (☃.func_110124_au().equals(this.field_184580_h)) {
                     this.field_184570_a = ☃;
                     break;
                  }
               }

               this.field_184580_h = null;
            }

            if (this.field_184571_b == null
               || !this.field_184571_b.func_70089_S()
               || this.field_184571_b instanceof EntityPlayer && ((EntityPlayer)this.field_184571_b).func_175149_v()) {
               if (!this.func_189652_ae()) {
                  this.field_70181_x -= 0.04;
               }
            } else {
               this.field_184577_e = MathHelper.func_151237_a(this.field_184577_e * 1.025, -1.0, 1.0);
               this.field_184578_f = MathHelper.func_151237_a(this.field_184578_f * 1.025, -1.0, 1.0);
               this.field_184579_g = MathHelper.func_151237_a(this.field_184579_g * 1.025, -1.0, 1.0);
               this.field_70159_w += (this.field_184577_e - this.field_70159_w) * 0.2;
               this.field_70181_x += (this.field_184578_f - this.field_70181_x) * 0.2;
               this.field_70179_y += (this.field_184579_g - this.field_70179_y) * 0.2;
            }

            RayTraceResult ☃ = ProjectileHelper.func_188802_a(this, true, false, this.field_184570_a);
            if (☃ != null) {
               this.func_184567_a(☃);
            }
         }

         this.func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
         ProjectileHelper.func_188803_a(this, 0.5F);
         if (this.field_70170_p.field_72995_K) {
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197624_q,
                  this.field_70165_t - this.field_70159_w,
                  this.field_70163_u - this.field_70181_x + 0.15,
                  this.field_70161_v - this.field_70179_y,
                  0.0,
                  0.0,
                  0.0
               );
         } else if (this.field_184571_b != null && !this.field_184571_b.field_70128_L) {
            if (this.field_184575_d > 0) {
               --this.field_184575_d;
               if (this.field_184575_d == 0) {
                  this.func_184569_a(this.field_184573_c == null ? null : this.field_184573_c.func_176740_k());
               }
            }

            if (this.field_184573_c != null) {
               BlockPos ☃ = new BlockPos(this);
               EnumFacing.Axis ☃x = this.field_184573_c.func_176740_k();
               if (this.field_70170_p.func_195595_w(☃.func_177972_a(this.field_184573_c))) {
                  this.func_184569_a(☃x);
               } else {
                  BlockPos ☃ = new BlockPos(this.field_184571_b);
                  if (☃x == EnumFacing.Axis.X && ☃.func_177958_n() == ☃.func_177958_n()
                     || ☃x == EnumFacing.Axis.Z && ☃.func_177952_p() == ☃.func_177952_p()
                     || ☃x == EnumFacing.Axis.Y && ☃.func_177956_o() == ☃.func_177956_o()) {
                     this.func_184569_a(☃x);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean func_70027_ad() {
      return false;
   }

   @Override
   public float func_70013_c() {
      return 1.0F;
   }

   protected void func_184567_a(RayTraceResult var1) {
      if (☃.field_72308_g == null) {
         ((WorldServer)this.field_70170_p)
            .func_195598_a(Particles.field_197627_t, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2, 0.2, 0.2, 0.2, 0.0);
         this.func_184185_a(SoundEvents.field_187775_eP, 1.0F, 1.0F);
      } else {
         boolean ☃ = ☃.field_72308_g.func_70097_a(DamageSource.func_188403_a(this, this.field_184570_a).func_76349_b(), 4.0F);
         if (☃) {
            this.func_174815_a(this.field_184570_a, ☃.field_72308_g);
            if (☃.field_72308_g instanceof EntityLivingBase) {
               ((EntityLivingBase)☃.field_72308_g).func_195064_c(new PotionEffect(MobEffects.field_188424_y, 200));
            }
         }
      }

      this.func_70106_y();
   }

   @Override
   public boolean func_70067_L() {
      return true;
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (!this.field_70170_p.field_72995_K) {
         this.func_184185_a(SoundEvents.field_187777_eQ, 1.0F, 1.0F);
         ((WorldServer)this.field_70170_p)
            .func_195598_a(Particles.field_197614_g, this.field_70165_t, this.field_70163_u, this.field_70161_v, 15, 0.2, 0.2, 0.2, 0.0);
         this.func_70106_y();
      }

      return true;
   }
}
