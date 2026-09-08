package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemElytra extends Item {
   public ItemElytra(Item.Properties var1) {
      super(☃);
      this.func_185043_a(new ResourceLocation("broken"), (var0, var1x, var2) -> func_185069_d(var0) ? 0.0F : 1.0F);
      BlockDispenser.func_199774_a(this, ItemArmor.field_96605_cw);
   }

   public static boolean func_185069_d(ItemStack var0) {
      return ☃.func_77952_i() < ☃.func_77958_k() - 1;
   }

   @Override
   public boolean func_82789_a(ItemStack var1, ItemStack var2) {
      return ☃.func_77973_b() == Items.field_204840_eX;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      EntityEquipmentSlot ☃x = EntityLiving.func_184640_d(☃);
      ItemStack ☃xx = ☃.func_184582_a(☃x);
      if (☃xx.func_190926_b()) {
         ☃.func_184201_a(☃x, ☃.func_77946_l());
         ☃.func_190920_e(0);
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      } else {
         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      }
   }
}
