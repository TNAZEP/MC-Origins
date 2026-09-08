package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class FireworkStarItem extends Item {
   public FireworkStarItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      CompoundTag â˜ƒ = â˜ƒ.getTagElement("Explosion");
      if (â˜ƒ != null) {
         appendHoverText(â˜ƒ, â˜ƒ);
      }
   }

   public static void appendHoverText(CompoundTag var0, List<Component> var1) {
      FireworkRocketItem.Shape â˜ƒ = FireworkRocketItem.Shape.byId(â˜ƒ.getByte("Type"));
      â˜ƒ.add(new TranslatableComponent("item.minecraft.firework_star.shape." + â˜ƒ.getName()).withStyle(ChatFormatting.GRAY));
      int[] â˜ƒx = â˜ƒ.getIntArray("Colors");
      if (â˜ƒx.length > 0) {
         â˜ƒ.add(appendColors(new TextComponent("").withStyle(ChatFormatting.GRAY), â˜ƒx));
      }

      int[] â˜ƒ = â˜ƒ.getIntArray("FadeColors");
      if (â˜ƒ.length > 0) {
         â˜ƒ.add(appendColors(new TranslatableComponent("item.minecraft.firework_star.fade_to").append(" ").withStyle(ChatFormatting.GRAY), â˜ƒ));
      }

      if (â˜ƒ.getBoolean("Trail")) {
         â˜ƒ.add(new TranslatableComponent("item.minecraft.firework_star.trail").withStyle(ChatFormatting.GRAY));
      }

      if (â˜ƒ.getBoolean("Flicker")) {
         â˜ƒ.add(new TranslatableComponent("item.minecraft.firework_star.flicker").withStyle(ChatFormatting.GRAY));
      }
   }

   private static Component appendColors(MutableComponent var0, int[] var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
         if (â˜ƒ > 0) {
            â˜ƒ.append(", ");
         }

         â˜ƒ.append(getColorName(â˜ƒ[â˜ƒ]));
      }

      return â˜ƒ;
   }

   private static Component getColorName(int var0) {
      DyeColor â˜ƒ = DyeColor.byFireworkColor(â˜ƒ);
      return â˜ƒ == null
         ? new TranslatableComponent("item.minecraft.firework_star.custom_color")
         : new TranslatableComponent("item.minecraft.firework_star." + â˜ƒ.getName());
   }
}
