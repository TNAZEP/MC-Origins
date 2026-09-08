package net.minecraft.world.item;

import java.util.stream.Stream;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ItemUtils {
   public static InteractionResultHolder<ItemStack> startUsingInstantly(Level var0, Player var1, InteractionHand var2) {
      â˜ƒ.startUsingItem(â˜ƒ);
      return InteractionResultHolder.consume(â˜ƒ.getItemInHand(â˜ƒ));
   }

   public static ItemStack createFilledResult(ItemStack var0, Player var1, ItemStack var2, boolean var3) {
      boolean â˜ƒ = â˜ƒ.getAbilities().instabuild;
      if (â˜ƒ && â˜ƒ) {
         if (!â˜ƒ.getInventory().contains(â˜ƒ)) {
            â˜ƒ.getInventory().add(â˜ƒ);
         }

         return â˜ƒ;
      } else {
         if (!â˜ƒ) {
            â˜ƒ.shrink(1);
         }

         if (â˜ƒ.isEmpty()) {
            return â˜ƒ;
         } else {
            if (!â˜ƒ.getInventory().add(â˜ƒ)) {
               â˜ƒ.drop(â˜ƒ, false);
            }

            return â˜ƒ;
         }
      }
   }

   public static ItemStack createFilledResult(ItemStack var0, Player var1, ItemStack var2) {
      return createFilledResult(â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public static void onContainerDestroyed(ItemEntity var0, Stream<ItemStack> var1) {
      Level â˜ƒ = â˜ƒ.level;
      if (!â˜ƒ.isClientSide) {
         â˜ƒ.forEach(var2x -> â˜ƒ.addFreshEntity(new ItemEntity(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), var2x)));
      }
   }
}
