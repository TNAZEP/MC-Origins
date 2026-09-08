package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class ItemPotion extends Item {
   public ItemPotion(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ItemStack func_77654_b(ItemStack var1, World var2, EntityLivingBase var3) {
      EntityPlayer ☃ = ☃ instanceof EntityPlayer ? (EntityPlayer)☃ : null;
      if (☃ == null || !☃.field_71075_bZ.field_75098_d) {
         ☃.func_190918_g(1);
      }

      if (☃ instanceof EntityPlayerMP) {
         CriteriaTriggers.field_193138_y.func_193148_a((EntityPlayerMP)☃, ☃);
      }

      if (!☃.field_72995_K) {
         for(PotionEffect ☃ : PotionUtils.func_185189_a(☃)) {
            if (☃.func_188419_a().func_76403_b()) {
               ☃.func_188419_a().func_180793_a(☃, ☃, ☃, ☃.func_76458_c(), 1.0);
            } else {
               ☃.func_195064_c(new PotionEffect(☃));
            }
         }
      }

      if (☃ != null) {
         ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      }

      if (☃ == null || !☃.field_71075_bZ.field_75098_d) {
         if (☃.func_190926_b()) {
            return new ItemStack(Items.field_151069_bo);
         }

         if (☃ != null) {
            ☃.field_71071_by.func_70441_a(new ItemStack(Items.field_151069_bo));
         }
      }

      return ☃;
   }

   @Override
   public int func_77626_a(ItemStack var1) {
      return 32;
   }

   @Override
   public EnumAction func_77661_b(ItemStack var1) {
      return EnumAction.DRINK;
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ☃.func_184598_c(☃);
      return new ActionResult<>(EnumActionResult.SUCCESS, ☃.func_184586_b(☃));
   }

   @Override
   public String func_77667_c(ItemStack var1) {
      return PotionUtils.func_185191_c(☃).func_185174_b(this.func_77658_a() + ".effect.");
   }

   @Override
   public void func_150895_a(ItemGroup var1, NonNullList<ItemStack> var2) {
      if (this.func_194125_a(☃)) {
         for(PotionType ☃ : IRegistry.field_212621_j) {
            if (☃ != PotionTypes.field_185229_a) {
               ☃.add(PotionUtils.func_185188_a(new ItemStack(this), ☃));
            }
         }
      }
   }
}
