package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMinecart extends Item {
   private static final IBehaviorDispenseItem field_96602_b = new BehaviorDefaultDispenseItem() {
      private final BehaviorDefaultDispenseItem field_96465_b = new BehaviorDefaultDispenseItem();

      @Override
      public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
         EnumFacing ☃x = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
         World ☃xx = ☃.func_197524_h();
         double ☃xxx = ☃.func_82615_a() + (double)☃x.func_82601_c() * 1.125;
         double ☃xxxx = Math.floor(☃.func_82617_b()) + (double)☃x.func_96559_d();
         double ☃xxxxx = ☃.func_82616_c() + (double)☃x.func_82599_e() * 1.125;
         BlockPos ☃xxxxxx = ☃.func_180699_d().func_177972_a(☃x);
         IBlockState ☃xxxxxxx = ☃xx.func_180495_p(☃xxxxxx);
         RailShape ☃xxxxxxxx = ☃xxxxxxx.func_177230_c() instanceof BlockRailBase
            ? ☃xxxxxxx.func_177229_b(((BlockRailBase)☃xxxxxxx.func_177230_c()).func_176560_l())
            : RailShape.NORTH_SOUTH;
         double ☃;
         if (☃xxxxxxx.func_203425_a(BlockTags.field_203437_y)) {
            if (☃xxxxxxxx.func_208092_c()) {
               ☃ = 0.6;
            } else {
               ☃ = 0.1;
            }
         } else {
            if (!☃xxxxxxx.func_196958_f() || !☃xx.func_180495_p(☃xxxxxx.func_177977_b()).func_203425_a(BlockTags.field_203437_y)) {
               return this.field_96465_b.dispense(☃, ☃);
            }

            IBlockState ☃ = ☃xx.func_180495_p(☃xxxxxx.func_177977_b());
            RailShape ☃x = ☃.func_177230_c() instanceof BlockRailBase
               ? ☃.func_177229_b(((BlockRailBase)☃.func_177230_c()).func_176560_l())
               : RailShape.NORTH_SOUTH;
            if (☃x != EnumFacing.DOWN && ☃x.func_208092_c()) {
               ☃ = -0.4;
            } else {
               ☃ = -0.9;
            }
         }

         EntityMinecart ☃ = EntityMinecart.func_184263_a(☃xx, ☃xxx, ☃xxxx + ☃, ☃xxxxx, ((ItemMinecart)☃.func_77973_b()).field_77841_a);
         if (☃.func_82837_s()) {
            ☃.func_200203_b(☃.func_200301_q());
         }

         ☃xx.func_72838_d(☃);
         ☃.func_190918_g(1);
         return ☃;
      }

      @Override
      protected void func_82485_a(IBlockSource var1) {
         ☃.func_197524_h().func_175718_b(1000, ☃.func_180699_d(), 0);
      }
   };
   private final EntityMinecart.Type field_77841_a;

   public ItemMinecart(EntityMinecart.Type var1, Item.Properties var2) {
      super(☃);
      this.field_77841_a = ☃;
      BlockDispenser.func_199774_a(this, field_96602_b);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      if (!☃xx.func_203425_a(BlockTags.field_203437_y)) {
         return EnumActionResult.FAIL;
      } else {
         ItemStack ☃ = ☃.func_195996_i();
         if (!☃.field_72995_K) {
            RailShape ☃x = ☃xx.func_177230_c() instanceof BlockRailBase
               ? ☃xx.func_177229_b(((BlockRailBase)☃xx.func_177230_c()).func_176560_l())
               : RailShape.NORTH_SOUTH;
            double ☃xx = 0.0;
            if (☃x.func_208092_c()) {
               ☃xx = 0.5;
            }

            EntityMinecart ☃x = EntityMinecart.func_184263_a(
               ☃, (double)☃x.func_177958_n() + 0.5, (double)☃x.func_177956_o() + 0.0625 + ☃xx, (double)☃x.func_177952_p() + 0.5, this.field_77841_a
            );
            if (☃.func_82837_s()) {
               ☃x.func_200203_b(☃.func_200301_q());
            }

            ☃.func_72838_d(☃x);
         }

         ☃.func_190918_g(1);
         return EnumActionResult.SUCCESS;
      }
   }
}
