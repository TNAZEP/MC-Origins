package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
   private static final DataParameter<Boolean> field_184275_c = EntityDataManager.func_187226_a(EntityMinecartFurnace.class, DataSerializers.field_187198_h);
   private int field_94110_c;
   public double field_94111_a;
   public double field_94109_b;
   private static final Ingredient field_195407_e = Ingredient.func_199804_a(Items.field_151044_h, Items.field_196155_l);

   public EntityMinecartFurnace(World var1) {
      super(EntityType.field_200775_O, ☃);
   }

   public EntityMinecartFurnace(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200775_O, ☃, ☃, ☃, ☃);
   }

   @Override
   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.FURNACE;
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184275_c, false);
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_94110_c > 0) {
         --this.field_94110_c;
      }

      if (this.field_94110_c <= 0) {
         this.field_94111_a = 0.0;
         this.field_94109_b = 0.0;
      }

      this.func_94107_f(this.field_94110_c > 0);
      if (this.func_94108_c() && this.field_70146_Z.nextInt(4) == 0) {
         this.field_70170_p.func_195594_a(Particles.field_197594_E, this.field_70165_t, this.field_70163_u + 0.8, this.field_70161_v, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected double func_174898_m() {
      return 0.2;
   }

   @Override
   public void func_94095_a(DamageSource var1) {
      super.func_94095_a(☃);
      if (!☃.func_94541_c() && this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         this.func_199703_a(Blocks.field_150460_al);
      }
   }

   @Override
   protected void func_180460_a(BlockPos var1, IBlockState var2) {
      super.func_180460_a(☃, ☃);
      double ☃ = this.field_94111_a * this.field_94111_a + this.field_94109_b * this.field_94109_b;
      if (☃ > 1.0E-4 && this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.001) {
         ☃ = (double)MathHelper.func_76133_a(☃);
         this.field_94111_a /= ☃;
         this.field_94109_b /= ☃;
         if (this.field_94111_a * this.field_70159_w + this.field_94109_b * this.field_70179_y < 0.0) {
            this.field_94111_a = 0.0;
            this.field_94109_b = 0.0;
         } else {
            double ☃x = ☃ / this.func_174898_m();
            this.field_94111_a *= ☃x;
            this.field_94109_b *= ☃x;
         }
      }
   }

   @Override
   protected void func_94101_h() {
      double ☃ = this.field_94111_a * this.field_94111_a + this.field_94109_b * this.field_94109_b;
      if (☃ > 1.0E-4) {
         ☃ = (double)MathHelper.func_76133_a(☃);
         this.field_94111_a /= ☃;
         this.field_94109_b /= ☃;
         double ☃x = 1.0;
         this.field_70159_w *= 0.8F;
         this.field_70181_x *= 0.0;
         this.field_70179_y *= 0.8F;
         this.field_70159_w += this.field_94111_a * 1.0;
         this.field_70179_y += this.field_94109_b * 1.0;
      } else {
         this.field_70159_w *= 0.98F;
         this.field_70181_x *= 0.0;
         this.field_70179_y *= 0.98F;
      }

      super.func_94101_h();
   }

   @Override
   public boolean func_184230_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (field_195407_e.test(☃) && this.field_94110_c + 3600 <= 32000) {
         if (!☃.field_71075_bZ.field_75098_d) {
            ☃.func_190918_g(1);
         }

         this.field_94110_c += 3600;
      }

      this.field_94111_a = this.field_70165_t - ☃.field_70165_t;
      this.field_94109_b = this.field_70161_v - ☃.field_70161_v;
      return true;
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74780_a("PushX", this.field_94111_a);
      ☃.func_74780_a("PushZ", this.field_94109_b);
      ☃.func_74777_a("Fuel", (short)this.field_94110_c);
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_94111_a = ☃.func_74769_h("PushX");
      this.field_94109_b = ☃.func_74769_h("PushZ");
      this.field_94110_c = ☃.func_74765_d("Fuel");
   }

   protected boolean func_94108_c() {
      return this.field_70180_af.func_187225_a(field_184275_c);
   }

   protected void func_94107_f(boolean var1) {
      this.field_70180_af.func_187227_b(field_184275_c, ☃);
   }

   @Override
   public IBlockState func_180457_u() {
      return Blocks.field_150460_al
         .func_176223_P()
         .func_206870_a(BlockFurnace.field_176447_a, EnumFacing.NORTH)
         .func_206870_a(BlockFurnace.field_196325_b, Boolean.valueOf(this.func_94108_c()));
   }
}
