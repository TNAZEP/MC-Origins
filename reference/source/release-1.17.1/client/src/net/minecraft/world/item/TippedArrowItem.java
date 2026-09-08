package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class TippedArrowItem extends ArrowItem {
   public TippedArrowItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public ItemStack getDefaultInstance() {
      return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
   }

   @Override
   public void fillItemCategory(CreativeModeTab var1, NonNullList<ItemStack> var2) {
      if (this.allowdedIn(â˜ƒ)) {
         for(Potion â˜ƒ : Registry.POTION) {
            if (!â˜ƒ.getEffects().isEmpty()) {
               â˜ƒ.add(PotionUtils.setPotion(new ItemStack(this), â˜ƒ));
            }
         }
      }
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      PotionUtils.addPotionTooltip(â˜ƒ, â˜ƒ, 0.125F);
   }

   @Override
   public String getDescriptionId(ItemStack var1) {
      return PotionUtils.getPotion(â˜ƒ).getName(this.getDescriptionId() + ".effect.");
   }
}
