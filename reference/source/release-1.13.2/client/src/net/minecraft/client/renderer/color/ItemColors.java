package net.minecraft.client.renderer.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmorDyeable;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.GrassColors;

public class ItemColors {
   private final ObjectIntIdentityMap<IItemColor> field_186732_a = new ObjectIntIdentityMap<>(32);

   public static ItemColors func_186729_a(BlockColors var0) {
      ItemColors ☃ = new ItemColors();
      ☃.func_199877_a(
         (var0x, var1x) -> var1x > 0 ? -1 : ((ItemArmorDyeable)var0x.func_77973_b()).func_200886_f(var0x),
         Items.field_151024_Q,
         Items.field_151027_R,
         Items.field_151026_S,
         Items.field_151021_T
      );
      ☃.func_199877_a((var0x, var1x) -> GrassColors.func_77480_a(0.5, 1.0), Blocks.field_196804_gh, Blocks.field_196805_gi);
      ☃.func_199877_a((var0x, var1x) -> {
         if (var1x != 1) {
            return -1;
         } else {
            NBTTagCompound ☃ = var0x.func_179543_a("Explosion");
            int[] ☃x = ☃ != null && ☃.func_150297_b("Colors", 11) ? ☃.func_74759_k("Colors") : null;
            if (☃x == null) {
               return 9079434;
            } else if (☃x.length == 1) {
               return ☃x[0];
            } else {
               int ☃ = 0;
               int ☃x = 0;
               int ☃xx = 0;

               for(int ☃xxx : ☃x) {
                  ☃ += (☃xxx & 0xFF0000) >> 16;
                  ☃x += (☃xxx & 0xFF00) >> 8;
                  ☃xx += (☃xxx & 0xFF) >> 0;
               }

               ☃ /= ☃x.length;
               ☃x /= ☃x.length;
               ☃xx /= ☃x.length;
               return ☃ << 16 | ☃x << 8 | ☃xx;
            }
         }
      }, Items.field_196153_dF);
      ☃.func_199877_a((var0x, var1x) -> var1x > 0 ? -1 : PotionUtils.func_190932_c(var0x), Items.field_151068_bn, Items.field_185155_bH, Items.field_185156_bI);

      for(ItemSpawnEgg ☃x : ItemSpawnEgg.func_195985_g()) {
         ☃.func_199877_a((var1x, var2) -> ☃.func_195983_a(var2), ☃x);
      }

      ☃.func_199877_a(
         (var1x, var2) -> {
            IBlockState ☃ = ((ItemBlock)var1x.func_77973_b()).func_179223_d().func_176223_P();
            return ☃.func_186724_a(☃, null, null, var2);
         },
         Blocks.field_196658_i,
         Blocks.field_150349_c,
         Blocks.field_196554_aH,
         Blocks.field_150395_bd,
         Blocks.field_196642_W,
         Blocks.field_196645_X,
         Blocks.field_196647_Y,
         Blocks.field_196648_Z,
         Blocks.field_196572_aa,
         Blocks.field_196574_ab,
         Blocks.field_196651_dG
      );
      ☃.func_199877_a((var0x, var1x) -> var1x == 0 ? PotionUtils.func_190932_c(var0x) : -1, Items.field_185167_i);
      ☃.func_199877_a((var0x, var1x) -> var1x == 0 ? -1 : ItemMap.func_190907_h(var0x), Items.field_151098_aY);
      return ☃;
   }

   public int func_186728_a(ItemStack var1, int var2) {
      IItemColor ☃ = this.field_186732_a.func_148745_a(IRegistry.field_212630_s.func_148757_b(☃.func_77973_b()));
      return ☃ == null ? -1 : ☃.getColor(☃, ☃);
   }

   public void func_199877_a(IItemColor var1, IItemProvider... var2) {
      for(IItemProvider ☃ : ☃) {
         this.field_186732_a.func_148746_a(☃, Item.func_150891_b(☃.func_199767_j()));
      }
   }
}
