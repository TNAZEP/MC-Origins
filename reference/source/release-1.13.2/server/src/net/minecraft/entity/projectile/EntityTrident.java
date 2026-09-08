package net.minecraft.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityTrident extends EntityArrow {
   private static final DataParameter<Byte> field_203053_g = EntityDataManager.func_187226_a(EntityTrident.class, DataSerializers.field_187191_a);
   private ItemStack field_203054_h = new ItemStack(Items.field_203184_eO);
   private boolean field_203051_au;
   public int field_203052_f;

   public EntityTrident(World var1) {
      super(EntityType.field_203098_aL, ☃);
   }

   public EntityTrident(World var1, EntityLivingBase var2, ItemStack var3) {
      super(EntityType.field_203098_aL, ☃, ☃);
      this.field_203054_h = ☃.func_77946_l();
      this.field_70180_af.func_187227_b(field_203053_g, (byte)EnchantmentHelper.func_203191_f(☃));
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_203053_g, (byte)0);
   }

   @Override
   public void func_70071_h_() {
      if (this.field_184552_b > 4) {
         this.field_203051_au = true;
      }

      Entity ☃ = this.func_212360_k();
      if ((this.field_203051_au || this.func_203047_q()) && ☃ != null) {
         int ☃x = this.field_70180_af.func_187225_a(field_203053_g);
         if (☃x > 0 && !this.func_207403_q()) {
            if (!this.field_70170_p.field_72995_K && this.field_70251_a == EntityArrow.PickupStatus.ALLOWED) {
               this.func_70099_a(this.func_184550_j(), 0.1F);
            }

            this.func_70106_y();
         } else if (☃x > 0) {
            this.func_203045_n(true);
            Vec3d ☃x = new Vec3d(
               ☃.field_70165_t - this.field_70165_t, ☃.field_70163_u + (double)☃.func_70047_e() - this.field_70163_u, ☃.field_70161_v - this.field_70161_v
            );
            this.field_70163_u += ☃x.field_72448_b * 0.015 * (double)☃x;
            if (this.field_70170_p.field_72995_K) {
               this.field_70137_T = this.field_70163_u;
            }

            ☃x = ☃x.func_72432_b();
            double ☃x = 0.05 * (double)☃x;
            this.field_70159_w += ☃x.field_72450_a * ☃x - this.field_70159_w * 0.05;
            this.field_70181_x += ☃x.field_72448_b * ☃x - this.field_70181_x * 0.05;
            this.field_70179_y += ☃x.field_72449_c * ☃x - this.field_70179_y * 0.05;
            if (this.field_203052_f == 0) {
               this.func_184185_a(SoundEvents.field_203270_il, 10.0F, 1.0F);
            }

            ++this.field_203052_f;
         }
      }

      super.func_70071_h_();
   }

   private boolean func_207403_q() {
      Entity ☃ = this.func_212360_k();
      if (☃ == null || !☃.func_70089_S()) {
         return false;
      } else {
         return !(☃ instanceof EntityPlayerMP) || !((EntityPlayerMP)☃).func_175149_v();
      }
   }

   @Override
   protected ItemStack func_184550_j() {
      return this.field_203054_h.func_77946_l();
   }

   @Nullable
   @Override
   protected Entity func_184551_a(Vec3d var1, Vec3d var2) {
      return this.field_203051_au ? null : super.func_184551_a(☃, ☃);
   }

   @Override
   protected void func_203046_b(RayTraceResult var1) {
      Entity ☃ = ☃.field_72308_g;
      float ☃x = 8.0F;
      if (☃ instanceof EntityLivingBase) {
         EntityLivingBase ☃xx = (EntityLivingBase)☃;
         ☃x += EnchantmentHelper.func_152377_a(this.field_203054_h, ☃xx.func_70668_bt());
      }

      Entity ☃ = this.func_212360_k();
      DamageSource ☃x = DamageSource.func_203096_a(this, (Entity)(☃ == null ? this : ☃));
      this.field_203051_au = true;
      SoundEvent ☃xx = SoundEvents.field_203268_ij;
      if (☃.func_70097_a(☃x, ☃x) && ☃ instanceof EntityLivingBase) {
         EntityLivingBase ☃xxx = (EntityLivingBase)☃;
         if (☃ instanceof EntityLivingBase) {
            EnchantmentHelper.func_151384_a(☃xxx, ☃);
            EnchantmentHelper.func_151385_b((EntityLivingBase)☃, ☃xxx);
         }

         this.func_184548_a(☃xxx);
      }

      this.field_70159_w *= -0.01F;
      this.field_70181_x *= -0.1F;
      this.field_70179_y *= -0.01F;
      float ☃ = 1.0F;
      if (this.field_70170_p.func_72911_I() && EnchantmentHelper.func_203192_h(this.field_203054_h)) {
         BlockPos ☃x = ☃.func_180425_c();
         if (this.field_70170_p.func_175678_i(☃x)) {
            EntityLightningBolt ☃xx = new EntityLightningBolt(
               this.field_70170_p, (double)☃x.func_177958_n() + 0.5, (double)☃x.func_177956_o(), (double)☃x.func_177952_p() + 0.5, false
            );
            ☃xx.func_204809_d(☃ instanceof EntityPlayerMP ? (EntityPlayerMP)☃ : null);
            this.field_70170_p.func_72942_c(☃xx);
            ☃xx = SoundEvents.field_203275_iq;
            ☃ = 5.0F;
         }
      }

      this.func_184185_a(☃xx, ☃, 1.0F);
   }

   @Override
   protected SoundEvent func_203050_i() {
      return SoundEvents.field_203269_ik;
   }

   @Override
   public void func_70100_b_(EntityPlayer var1) {
      Entity ☃ = this.func_212360_k();
      if (☃ == null || ☃.func_110124_au() == ☃.func_110124_au()) {
         super.func_70100_b_(☃);
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      if (☃.func_150297_b("Trident", 10)) {
         this.field_203054_h = ItemStack.func_199557_a(☃.func_74775_l("Trident"));
      }

      this.field_203051_au = ☃.func_74767_n("DealtDamage");
      this.field_70180_af.func_187227_b(field_203053_g, (byte)EnchantmentHelper.func_203191_f(this.field_203054_h));
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74782_a("Trident", this.field_203054_h.func_77955_b(new NBTTagCompound()));
      ☃.func_74757_a("DealtDamage", this.field_203051_au);
   }

   @Override
   protected void func_203048_f() {
      int ☃ = this.field_70180_af.func_187225_a(field_203053_g);
      if (this.field_70251_a != EntityArrow.PickupStatus.ALLOWED || ☃ <= 0) {
         super.func_203048_f();
      }
   }

   @Override
   protected float func_203044_p() {
      return 0.99F;
   }
}
