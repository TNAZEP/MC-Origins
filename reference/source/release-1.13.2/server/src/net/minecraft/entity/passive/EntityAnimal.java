package net.minecraft.entity.passive;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimal {
   protected Block field_175506_bl = Blocks.field_196658_i;
   private int field_70881_d;
   private UUID field_146084_br;

   protected EntityAnimal(EntityType<?> var1, World var2) {
      super(☃, ☃);
   }

   @Override
   protected void func_70619_bc() {
      if (this.func_70874_b() != 0) {
         this.field_70881_d = 0;
      }

      super.func_70619_bc();
   }

   @Override
   public void func_70636_d() {
      super.func_70636_d();
      if (this.func_70874_b() != 0) {
         this.field_70881_d = 0;
      }

      if (this.field_70881_d > 0) {
         --this.field_70881_d;
         if (this.field_70881_d % 10 == 0) {
            double ☃ = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃x = this.field_70146_Z.nextGaussian() * 0.02;
            double ☃xx = this.field_70146_Z.nextGaussian() * 0.02;
            this.field_70170_p
               .func_195594_a(
                  Particles.field_197633_z,
                  this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
                  this.field_70163_u + 0.5 + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O),
                  this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N,
                  ☃,
                  ☃x,
                  ☃xx
               );
         }
      }
   }

   @Override
   public boolean func_70097_a(DamageSource var1, float var2) {
      if (this.func_180431_b(☃)) {
         return false;
      } else {
         this.field_70881_d = 0;
         return super.func_70097_a(☃, ☃);
      }
   }

   @Override
   public float func_205022_a(BlockPos var1, IWorldReaderBase var2) {
      return ☃.func_180495_p(☃.func_177977_b()).func_177230_c() == this.field_175506_bl ? 10.0F : ☃.func_205052_D(☃) - 0.5F;
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("InLove", this.field_70881_d);
      if (this.field_146084_br != null) {
         ☃.func_186854_a("LoveCause", this.field_146084_br);
      }
   }

   @Override
   public double func_70033_W() {
      return 0.14;
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_70881_d = ☃.func_74762_e("InLove");
      this.field_146084_br = ☃.func_186855_b("LoveCause") ? ☃.func_186857_a("LoveCause") : null;
   }

   @Override
   public boolean func_205020_a(IWorld var1, boolean var2) {
      int ☃ = MathHelper.func_76128_c(this.field_70165_t);
      int ☃x = MathHelper.func_76128_c(this.func_174813_aQ().field_72338_b);
      int ☃xx = MathHelper.func_76128_c(this.field_70161_v);
      BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
      return ☃.func_180495_p(☃xxx.func_177977_b()).func_177230_c() == this.field_175506_bl && ☃.func_201669_a(☃xxx, 0) > 8 && super.func_205020_a(☃, ☃);
   }

   @Override
   public int func_70627_aG() {
      return 120;
   }

   @Override
   public boolean func_70692_ba() {
      return false;
   }

   @Override
   protected int func_70693_a(EntityPlayer var1) {
      return 1 + this.field_70170_p.field_73012_v.nextInt(3);
   }

   public boolean func_70877_b(ItemStack var1) {
      return ☃.func_77973_b() == Items.field_151015_O;
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (this.func_70877_b(☃)) {
         if (this.func_70874_b() == 0 && this.func_204701_dC()) {
            this.func_175505_a(☃, ☃);
            this.func_146082_f(☃);
            return true;
         }

         if (this.func_70631_g_()) {
            this.func_175505_a(☃, ☃);
            this.func_175501_a((int)((float)(-this.func_70874_b() / 20) * 0.1F), true);
            return true;
         }
      }

      return super.func_184645_a(☃, ☃);
   }

   protected void func_175505_a(EntityPlayer var1, ItemStack var2) {
      if (!☃.field_71075_bZ.field_75098_d) {
         ☃.func_190918_g(1);
      }
   }

   public boolean func_204701_dC() {
      return this.field_70881_d <= 0;
   }

   public void func_146082_f(@Nullable EntityPlayer var1) {
      this.field_70881_d = 600;
      if (☃ != null) {
         this.field_146084_br = ☃.func_110124_au();
      }

      this.field_70170_p.func_72960_a(this, (byte)18);
   }

   public void func_204700_e(int var1) {
      this.field_70881_d = ☃;
   }

   @Nullable
   public EntityPlayerMP func_191993_do() {
      if (this.field_146084_br == null) {
         return null;
      } else {
         EntityPlayer ☃ = this.field_70170_p.func_152378_a(this.field_146084_br);
         return ☃ instanceof EntityPlayerMP ? (EntityPlayerMP)☃ : null;
      }
   }

   public boolean func_70880_s() {
      return this.field_70881_d > 0;
   }

   public void func_70875_t() {
      this.field_70881_d = 0;
   }

   public boolean func_70878_b(EntityAnimal var1) {
      if (☃ == this) {
         return false;
      } else if (☃.getClass() != this.getClass()) {
         return false;
      } else {
         return this.func_70880_s() && ☃.func_70880_s();
      }
   }
}
