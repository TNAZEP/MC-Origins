package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemUseContext extends ItemUseContext {
   private final BlockPos field_196014_j;
   protected boolean field_196013_a = true;

   public BlockItemUseContext(ItemUseContext var1) {
      this(
         ☃.func_195991_k(), ☃.func_195999_j(), ☃.func_195996_i(), ☃.func_195995_a(), ☃.func_196000_l(), ☃.func_195997_m(), ☃.func_195993_n(), ☃.func_195994_o()
      );
   }

   protected BlockItemUseContext(World var1, @Nullable EntityPlayer var2, ItemStack var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_196014_j = this.field_196008_i.func_177972_a(this.field_196005_f);
      this.field_196013_a = this.func_195991_k().func_180495_p(this.field_196008_i).func_196953_a(this);
   }

   @Override
   public BlockPos func_195995_a() {
      return this.field_196013_a ? this.field_196008_i : this.field_196014_j;
   }

   public boolean func_196011_b() {
      return this.field_196013_a || this.func_195991_k().func_180495_p(this.func_195995_a()).func_196953_a(this);
   }

   public boolean func_196012_c() {
      return this.field_196013_a;
   }

   public EnumFacing func_196010_d() {
      return EnumFacing.func_196054_a(this.field_196001_b)[0];
   }

   public EnumFacing[] func_196009_e() {
      EnumFacing[] ☃ = EnumFacing.func_196054_a(this.field_196001_b);
      if (this.field_196013_a) {
         return ☃;
      } else {
         int ☃ = 0;

         while(☃ < ☃.length && ☃[☃] != this.field_196005_f.func_176734_d()) {
            ++☃;
         }

         if (☃ > 0) {
            System.arraycopy(☃, 0, ☃, 1, ☃);
            ☃[0] = this.field_196005_f.func_176734_d();
         }

         return ☃;
      }
   }
}
