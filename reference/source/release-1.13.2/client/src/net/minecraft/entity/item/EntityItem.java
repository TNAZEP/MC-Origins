package net.minecraft.entity.item;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class EntityItem extends Entity {
   private static final DataParameter<ItemStack> field_184533_c = EntityDataManager.func_187226_a(EntityItem.class, DataSerializers.field_187196_f);
   private int field_70292_b;
   private int field_145804_b;
   private int field_70291_e = 5;
   private UUID field_145801_f;
   private UUID field_145802_g;
   public float field_70290_d = (float)(Math.random() * Math.PI * 2.0);

   public EntityItem(World var1) {
      super(EntityType.field_200765_E, ☃);
      this.func_70105_a(0.25F, 0.25F);
   }

   public EntityItem(World var1, double var2, double var4, double var6) {
      this(☃);
      this.func_70107_b(☃, ☃, ☃);
      this.field_70177_z = (float)(Math.random() * 360.0);
      this.field_70159_w = (double)((float)(Math.random() * 0.2F - 0.1F));
      this.field_70181_x = 0.2F;
      this.field_70179_y = (double)((float)(Math.random() * 0.2F - 0.1F));
   }

   public EntityItem(World var1, double var2, double var4, double var6, ItemStack var8) {
      this(☃, ☃, ☃, ☃);
      this.func_92058_a(☃);
   }

   @Override
   protected boolean func_70041_e_() {
      return false;
   }

   @Override
   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184533_c, ItemStack.field_190927_a);
   }

   @Override
   public void func_70071_h_() {
      if (this.func_92059_d().func_190926_b()) {
         this.func_70106_y();
      } else {
         super.func_70071_h_();
         if (this.field_145804_b > 0 && this.field_145804_b != 32767) {
            --this.field_145804_b;
         }

         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         double ☃ = this.field_70159_w;
         double ☃x = this.field_70181_x;
         double ☃xx = this.field_70179_y;
         if (this.func_208600_a(FluidTags.field_206959_a)) {
            this.func_203043_v();
         } else if (!this.func_189652_ae()) {
            this.field_70181_x -= 0.04F;
         }

         if (this.field_70170_p.field_72995_K) {
            this.field_70145_X = false;
         } else {
            this.field_70145_X = this.func_145771_j(
               this.field_70165_t, (this.func_174813_aQ().field_72338_b + this.func_174813_aQ().field_72337_e) / 2.0, this.field_70161_v
            );
         }

         this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
         boolean ☃ = (int)this.field_70169_q != (int)this.field_70165_t
            || (int)this.field_70167_r != (int)this.field_70163_u
            || (int)this.field_70166_s != (int)this.field_70161_v;
         if (☃ || this.field_70173_aa % 25 == 0) {
            if (this.field_70170_p.func_204610_c(new BlockPos(this)).func_206884_a(FluidTags.field_206960_b)) {
               this.field_70181_x = 0.2F;
               this.field_70159_w = (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
               this.field_70179_y = (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
               this.func_184185_a(SoundEvents.field_187658_bx, 0.4F, 2.0F + this.field_70146_Z.nextFloat() * 0.4F);
            }

            if (!this.field_70170_p.field_72995_K) {
               this.func_85054_d();
            }
         }

         float ☃ = 0.98F;
         if (this.field_70122_E) {
            ☃ = this.field_70170_p
                  .func_180495_p(
                     new BlockPos(
                        MathHelper.func_76128_c(this.field_70165_t),
                        MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b) - 1,
                        MathHelper.func_76128_c(this.field_70161_v)
                     )
                  )
                  .func_177230_c()
                  .func_208618_m()
               * 0.98F;
         }

         this.field_70159_w *= (double)☃;
         this.field_70181_x *= 0.98F;
         this.field_70179_y *= (double)☃;
         if (this.field_70122_E) {
            this.field_70181_x *= -0.5;
         }

         if (this.field_70292_b != -32768) {
            ++this.field_70292_b;
         }

         this.field_70160_al |= this.func_70072_I();
         if (!this.field_70170_p.field_72995_K) {
            double ☃ = this.field_70159_w - ☃;
            double ☃x = this.field_70181_x - ☃x;
            double ☃xx = this.field_70179_y - ☃xx;
            double ☃xxx = ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
            if (☃xxx > 0.01) {
               this.field_70160_al = true;
            }
         }

         if (!this.field_70170_p.field_72995_K && this.field_70292_b >= 6000) {
            this.func_70106_y();
         }
      }
   }

   private void func_203043_v() {
      if (this.field_70181_x < 0.06F) {
         this.field_70181_x += 5.0E-4F;
      }

      this.field_70159_w *= 0.99F;
      this.field_70179_y *= 0.99F;
   }

   private void func_85054_d() {
      for(EntityItem ☃ : this.field_70170_p.func_72872_a(EntityItem.class, this.func_174813_aQ().func_72314_b(0.5, 0.0, 0.5))) {
         this.func_70289_a(☃);
      }
   }

   private boolean func_70289_a(EntityItem var1) {
      if (☃ == this) {
         return false;
      } else if (☃.func_70089_S() && this.func_70089_S()) {
         ItemStack ☃ = this.func_92059_d();
         ItemStack ☃x = ☃.func_92059_d().func_77946_l();
         if (this.field_145804_b == 32767 || ☃.field_145804_b == 32767) {
            return false;
         } else if (this.field_70292_b != -32768 && ☃.field_70292_b != -32768) {
            if (☃x.func_77973_b() != ☃.func_77973_b()) {
               return false;
            } else if (☃x.func_77942_o() ^ ☃.func_77942_o()) {
               return false;
            } else if (☃x.func_77942_o() && !☃x.func_77978_p().equals(☃.func_77978_p())) {
               return false;
            } else if (☃x.func_77973_b() == null) {
               return false;
            } else if (☃x.func_190916_E() < ☃.func_190916_E()) {
               return ☃.func_70289_a(this);
            } else if (☃x.func_190916_E() + ☃.func_190916_E() > ☃x.func_77976_d()) {
               return false;
            } else {
               ☃x.func_190917_f(☃.func_190916_E());
               ☃.field_145804_b = Math.max(☃.field_145804_b, this.field_145804_b);
               ☃.field_70292_b = Math.min(☃.field_70292_b, this.field_70292_b);
               ☃.func_92058_a(☃x);
               this.func_70106_y();
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void func_70288_d() {
      this.field_70292_b = 4800;
   }

   @Override
   protected void func_70081_e(int var1) {
      this.func_70097_a(DamageSource.field_76372_a, (float)☃);
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else if (!this.func_92059_d().func_190926_b() && this.func_92059_d().func_77973_b() == Items.field_151156_bN && ☃.func_94541_c()) {
         return false;
      } else {
         this.func_70018_K();
         this.field_70291_e = (int)((float)this.field_70291_e - ☃);
         if (this.field_70291_e <= 0) {
            this.func_70106_y();
         }

         return false;
      }
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74777_a("Health", (short)this.field_70291_e);
      ☃.func_74777_a("Age", (short)this.field_70292_b);
      ☃.func_74777_a("PickupDelay", (short)this.field_145804_b);
      if (this.func_200214_m() != null) {
         ☃.func_74782_a("Thrower", NBTUtil.func_186862_a(this.func_200214_m()));
      }

      if (this.func_200215_l() != null) {
         ☃.func_74782_a("Owner", NBTUtil.func_186862_a(this.func_200215_l()));
      }

      if (!this.func_92059_d().func_190926_b()) {
         ☃.func_74782_a("Item", this.func_92059_d().func_77955_b(new NBTTagCompound()));
      }
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.field_70291_e = ☃.func_74765_d("Health");
      this.field_70292_b = ☃.func_74765_d("Age");
      if (☃.func_74764_b("PickupDelay")) {
         this.field_145804_b = ☃.func_74765_d("PickupDelay");
      }

      if (☃.func_150297_b("Owner", 10)) {
         this.field_145802_g = NBTUtil.func_186860_b(☃.func_74775_l("Owner"));
      }

      if (☃.func_150297_b("Thrower", 10)) {
         this.field_145801_f = NBTUtil.func_186860_b(☃.func_74775_l("Thrower"));
      }

      NBTTagCompound ☃ = ☃.func_74775_l("Item");
      this.func_92058_a(ItemStack.func_199557_a(☃));
      if (this.func_92059_d().func_190926_b()) {
         this.func_70106_y();
      }
   }

   @Override
   public void func_70100_b_(EntityPlayer var1) {
      if (!this.field_70170_p.field_72995_K) {
         ItemStack ☃ = this.func_92059_d();
         Item ☃x = ☃.func_77973_b();
         int ☃xx = ☃.func_190916_E();
         if (this.field_145804_b == 0
            && (this.field_145802_g == null || 6000 - this.field_70292_b <= 200 || this.field_145802_g.equals(☃.func_110124_au()))
            && ☃.field_71071_by.func_70441_a(☃)) {
            ☃.func_71001_a(this, ☃xx);
            if (☃.func_190926_b()) {
               this.func_70106_y();
               ☃.func_190920_e(☃xx);
            }

            ☃.func_71064_a(StatList.field_199089_f.func_199076_b(☃x), ☃xx);
         }
      }
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      return (ITextComponent)(☃ != null ? ☃ : new TextComponentTranslation(this.func_92059_d().func_77977_a()));
   }

   @Override
   public boolean func_70075_an() {
      return false;
   }

   @Nullable
   @Override
   public Entity func_212321_a(DimensionType var1) {
      Entity ☃ = super.func_212321_a(☃);
      if (!this.field_70170_p.field_72995_K && ☃ instanceof EntityItem) {
         ((EntityItem)☃).func_85054_d();
      }

      return ☃;
   }

   public ItemStack func_92059_d() {
      return this.func_184212_Q().func_187225_a(field_184533_c);
   }

   public void func_92058_a(ItemStack var1) {
      this.func_184212_Q().func_187227_b(field_184533_c, ☃);
   }

   @Nullable
   public UUID func_200215_l() {
      return this.field_145802_g;
   }

   public void func_200217_b(@Nullable UUID var1) {
      this.field_145802_g = ☃;
   }

   @Nullable
   public UUID func_200214_m() {
      return this.field_145801_f;
   }

   public void func_200216_c(@Nullable UUID var1) {
      this.field_145801_f = ☃;
   }

   public int func_174872_o() {
      return this.field_70292_b;
   }

   public void func_174869_p() {
      this.field_145804_b = 10;
   }

   public void func_174868_q() {
      this.field_145804_b = 0;
   }

   public void func_174871_r() {
      this.field_145804_b = 32767;
   }

   public void func_174867_a(int var1) {
      this.field_145804_b = ☃;
   }

   public boolean func_174874_s() {
      return this.field_145804_b > 0;
   }

   public void func_174873_u() {
      this.field_70292_b = -6000;
   }

   public void func_174870_v() {
      this.func_174871_r();
      this.field_70292_b = 5999;
   }
}
