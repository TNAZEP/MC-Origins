package net.minecraft.world.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FoodOnAStickItem<T extends Entity & ItemSteerable> extends Item {
   private final EntityType<T> canInteractWith;
   private final int consumeItemDamage;

   public FoodOnAStickItem(Item.Properties var1, EntityType<T> var2, int var3) {
      super(â˜ƒ);
      this.canInteractWith = â˜ƒ;
      this.consumeItemDamage = â˜ƒ;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.isClientSide) {
         return InteractionResultHolder.pass(â˜ƒ);
      } else {
         Entity â˜ƒx = â˜ƒ.getVehicle();
         if (â˜ƒ.isPassenger() && â˜ƒx instanceof ItemSteerable â˜ƒ && â˜ƒx.getType() == this.canInteractWith && â˜ƒ.boost()) {
            â˜ƒ.hurtAndBreak(this.consumeItemDamage, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
            if (â˜ƒ.isEmpty()) {
               ItemStack â˜ƒxx = new ItemStack(Items.FISHING_ROD);
               â˜ƒxx.setTag(â˜ƒ.getTag());
               return InteractionResultHolder.success(â˜ƒxx);
            }

            return InteractionResultHolder.success(â˜ƒ);
         }

         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         return InteractionResultHolder.pass(â˜ƒ);
      }
   }
}
