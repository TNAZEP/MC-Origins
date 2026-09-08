package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemShield extends Item {
   public ItemShield(Item.Properties var1) {
      super(☃);
      this.func_185043_a(
         new ResourceLocation("blocking"), (var0, var1x, var2) -> var2 != null && var2.func_184587_cr() && var2.func_184607_cu() == var0 ? 1.0F : 0.0F
      );
      BlockDispenser.func_199774_a(this, ItemArmor.field_96605_cw);
   }

   @Override
   public String func_77667_c(ItemStack var1) {
      return ☃.func_179543_a("BlockEntityTag") != null ? this.func_77658_a() + '.' + func_195979_f(☃).func_176762_d() : super.func_77667_c(☃);
   }

   @Override
   public EnumAction func_77661_b(ItemStack var1) {
      return EnumAction.BLOCK;
   }

   @Override
   public int func_77626_a(ItemStack var1) {
      return 72000;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      ☃.func_184598_c(☃);
      return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
   }

   @Override
   public boolean func_82789_a(ItemStack var1, ItemStack var2) {
      return ItemTags.field_199905_b.func_199685_a_(☃.func_77973_b()) || super.func_82789_a(☃, ☃);
   }

   public static EnumDyeColor func_195979_f(ItemStack var0) {
      return EnumDyeColor.func_196056_a(☃.func_190925_c("BlockEntityTag").func_74762_e("Base"));
   }
}
