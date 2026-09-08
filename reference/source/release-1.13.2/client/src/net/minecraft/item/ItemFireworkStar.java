package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFireworkStar extends Item {
   public ItemFireworkStar(Item.Properties var1) {
      super(☃);
   }

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      NBTTagCompound ☃ = ☃.func_179543_a("Explosion");
      if (☃ != null) {
         func_195967_a(☃, ☃);
      }
   }

   public static void func_195967_a(NBTTagCompound var0, List<ITextComponent> var1) {
      ItemFireworkRocket.Shape ☃ = ItemFireworkRocket.Shape.func_196070_a(☃.func_74771_c("Type"));
      ☃.add(new TextComponentTranslation("item.minecraft.firework_star.shape." + ☃.func_196068_b()).func_211708_a(TextFormatting.GRAY));
      int[] ☃x = ☃.func_74759_k("Colors");
      if (☃x.length > 0) {
         ☃.add(func_200298_a(new TextComponentString("").func_211708_a(TextFormatting.GRAY), ☃x));
      }

      int[] ☃ = ☃.func_74759_k("FadeColors");
      if (☃.length > 0) {
         ☃.add(func_200298_a(new TextComponentTranslation("item.minecraft.firework_star.fade_to").func_150258_a(" ").func_211708_a(TextFormatting.GRAY), ☃));
      }

      if (☃.func_74767_n("Trail")) {
         ☃.add(new TextComponentTranslation("item.minecraft.firework_star.trail").func_211708_a(TextFormatting.GRAY));
      }

      if (☃.func_74767_n("Flicker")) {
         ☃.add(new TextComponentTranslation("item.minecraft.firework_star.flicker").func_211708_a(TextFormatting.GRAY));
      }
   }

   private static ITextComponent func_200298_a(ITextComponent var0, int[] var1) {
      for(int ☃ = 0; ☃ < ☃.length; ++☃) {
         if (☃ > 0) {
            ☃.func_150258_a(", ");
         }

         ☃.func_150257_a(func_200297_a(☃[☃]));
      }

      return ☃;
   }

   private static ITextComponent func_200297_a(int var0) {
      EnumDyeColor ☃ = EnumDyeColor.func_196058_b(☃);
      return ☃ == null
         ? new TextComponentTranslation("item.minecraft.firework_star.custom_color")
         : new TextComponentTranslation("item.minecraft.firework_star." + ☃.func_176762_d());
   }
}
