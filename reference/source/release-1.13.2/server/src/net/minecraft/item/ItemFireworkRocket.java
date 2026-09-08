package net.minecraft.item;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFireworkRocket extends Item {
   public ItemFireworkRocket(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      if (!☃.field_72995_K) {
         BlockPos ☃x = ☃.func_195995_a();
         ItemStack ☃xx = ☃.func_195996_i();
         EntityFireworkRocket ☃xxx = new EntityFireworkRocket(
            ☃,
            (double)((float)☃x.func_177958_n() + ☃.func_195997_m()),
            (double)((float)☃x.func_177956_o() + ☃.func_195993_n()),
            (double)((float)☃x.func_177952_p() + ☃.func_195994_o()),
            ☃xx
         );
         ☃.func_72838_d(☃xxx);
         ☃xx.func_190918_g(1);
      }

      return EnumActionResult.SUCCESS;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      if (☃.func_184613_cA()) {
         ItemStack ☃ = ☃.func_184586_b(☃);
         if (!☃.field_72995_K) {
            EntityFireworkRocket ☃x = new EntityFireworkRocket(☃, ☃, ☃);
            ☃.func_72838_d(☃x);
            if (!☃.field_71075_bZ.field_75098_d) {
               ☃.func_190918_g(1);
            }
         }

         return new ActionResult<>(EnumActionResult.SUCCESS, ☃.func_184586_b(☃));
      } else {
         return new ActionResult<>(EnumActionResult.PASS, ☃.func_184586_b(☃));
      }
   }

   public static enum Shape {
      SMALL_BALL(0, "small_ball"),
      LARGE_BALL(1, "large_ball"),
      STAR(2, "star"),
      CREEPER(3, "creeper"),
      BURST(4, "burst");

      private static final ItemFireworkRocket.Shape[] field_196077_f = (ItemFireworkRocket.Shape[])Arrays.stream(values())
         .sorted(Comparator.comparingInt(var0 -> var0.field_196078_g))
         .toArray(var0 -> new ItemFireworkRocket.Shape[var0]);
      private final int field_196078_g;
      private final String field_196079_h;

      private Shape(int var3, String var4) {
         this.field_196078_g = ☃;
         this.field_196079_h = ☃;
      }

      public int func_196071_a() {
         return this.field_196078_g;
      }
   }
}
