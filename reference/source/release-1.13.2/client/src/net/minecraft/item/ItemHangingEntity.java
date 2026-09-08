package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {
   private final Class<? extends EntityHanging> field_82811_a;

   public ItemHangingEntity(Class<? extends EntityHanging> var1, Item.Properties var2) {
      super(☃);
      this.field_82811_a = ☃;
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      BlockPos ☃ = ☃.func_195995_a();
      EnumFacing ☃x = ☃.func_196000_l();
      BlockPos ☃xx = ☃.func_177972_a(☃x);
      EntityPlayer ☃xxx = ☃.func_195999_j();
      if (☃xxx != null && !this.func_200127_a(☃xxx, ☃x, ☃.func_195996_i(), ☃xx)) {
         return EnumActionResult.FAIL;
      } else {
         World ☃ = ☃.func_195991_k();
         EntityHanging ☃x = this.func_179233_a(☃, ☃xx, ☃x);
         if (☃x != null && ☃x.func_70518_d()) {
            if (!☃.field_72995_K) {
               ☃x.func_184523_o();
               ☃.func_72838_d(☃x);
            }

            ☃.func_195996_i().func_190918_g(1);
         }

         return EnumActionResult.SUCCESS;
      }
   }

   protected boolean func_200127_a(EntityPlayer var1, EnumFacing var2, ItemStack var3, BlockPos var4) {
      return !☃.func_176740_k().func_200128_b() && ☃.func_175151_a(☃, ☃, ☃);
   }

   @Nullable
   private EntityHanging func_179233_a(World var1, BlockPos var2, EnumFacing var3) {
      if (this.field_82811_a == EntityPainting.class) {
         return new EntityPainting(☃, ☃, ☃);
      } else {
         return this.field_82811_a == EntityItemFrame.class ? new EntityItemFrame(☃, ☃, ☃) : null;
      }
   }
}
