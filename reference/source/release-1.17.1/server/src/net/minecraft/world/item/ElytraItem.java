package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ElytraItem extends Item implements Wearable {
   public ElytraItem(Item.Properties var1) {
      super(â˜ƒ);
      DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
   }

   public static boolean isFlyEnabled(ItemStack var0) {
      return â˜ƒ.getDamageValue() < â˜ƒ.getMaxDamage() - 1;
   }

   @Override
   public boolean isValidRepairItem(ItemStack var1, ItemStack var2) {
      return â˜ƒ.is(Items.PHANTOM_MEMBRANE);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      EquipmentSlot â˜ƒx = Mob.getEquipmentSlotForItem(â˜ƒ);
      ItemStack â˜ƒxx = â˜ƒ.getItemBySlot(â˜ƒx);
      if (â˜ƒxx.isEmpty()) {
         â˜ƒ.setItemSlot(â˜ƒx, â˜ƒ.copy());
         if (!â˜ƒ.isClientSide()) {
            â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         }

         â˜ƒ.setCount(0);
         return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
      } else {
         return InteractionResultHolder.fail(â˜ƒ);
      }
   }

   @Nullable
   @Override
   public SoundEvent getEquipSound() {
      return SoundEvents.ARMOR_EQUIP_ELYTRA;
   }
}
