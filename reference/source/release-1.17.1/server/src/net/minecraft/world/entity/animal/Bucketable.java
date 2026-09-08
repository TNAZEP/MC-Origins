package net.minecraft.world.entity.animal;

import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public interface Bucketable {
   boolean fromBucket();

   void setFromBucket(boolean var1);

   void saveToBucketTag(ItemStack var1);

   void loadFromBucketTag(CompoundTag var1);

   ItemStack getBucketItemStack();

   SoundEvent getPickupSound();

   @Deprecated
   static void saveDefaultDataToBucketTag(Mob var0, ItemStack var1) {
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      if (â˜ƒ.hasCustomName()) {
         â˜ƒ.setHoverName(â˜ƒ.getCustomName());
      }

      if (â˜ƒ.isNoAi()) {
         â˜ƒ.putBoolean("NoAI", â˜ƒ.isNoAi());
      }

      if (â˜ƒ.isSilent()) {
         â˜ƒ.putBoolean("Silent", â˜ƒ.isSilent());
      }

      if (â˜ƒ.isNoGravity()) {
         â˜ƒ.putBoolean("NoGravity", â˜ƒ.isNoGravity());
      }

      if (â˜ƒ.hasGlowingTag()) {
         â˜ƒ.putBoolean("Glowing", â˜ƒ.hasGlowingTag());
      }

      if (â˜ƒ.isInvulnerable()) {
         â˜ƒ.putBoolean("Invulnerable", â˜ƒ.isInvulnerable());
      }

      â˜ƒ.putFloat("Health", â˜ƒ.getHealth());
   }

   @Deprecated
   static void loadDefaultDataFromBucketTag(Mob var0, CompoundTag var1) {
      if (â˜ƒ.contains("NoAI")) {
         â˜ƒ.setNoAi(â˜ƒ.getBoolean("NoAI"));
      }

      if (â˜ƒ.contains("Silent")) {
         â˜ƒ.setSilent(â˜ƒ.getBoolean("Silent"));
      }

      if (â˜ƒ.contains("NoGravity")) {
         â˜ƒ.setNoGravity(â˜ƒ.getBoolean("NoGravity"));
      }

      if (â˜ƒ.contains("Glowing")) {
         â˜ƒ.setGlowingTag(â˜ƒ.getBoolean("Glowing"));
      }

      if (â˜ƒ.contains("Invulnerable")) {
         â˜ƒ.setInvulnerable(â˜ƒ.getBoolean("Invulnerable"));
      }

      if (â˜ƒ.contains("Health", 99)) {
         â˜ƒ.setHealth(â˜ƒ.getFloat("Health"));
      }
   }

   static <T extends LivingEntity & Bucketable> Optional<InteractionResult> bucketMobPickup(Player var0, InteractionHand var1, T var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.getItem() == Items.WATER_BUCKET && â˜ƒ.isAlive()) {
         â˜ƒ.playSound(â˜ƒ.getPickupSound(), 1.0F, 1.0F);
         ItemStack â˜ƒx = â˜ƒ.getBucketItemStack();
         â˜ƒ.saveToBucketTag(â˜ƒx);
         ItemStack â˜ƒxx = ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, â˜ƒx, false);
         â˜ƒ.setItemInHand(â˜ƒ, â˜ƒxx);
         Level â˜ƒxxx = â˜ƒ.level;
         if (!â˜ƒxxx.isClientSide) {
            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)â˜ƒ, â˜ƒx);
         }

         â˜ƒ.discard();
         return Optional.of(InteractionResult.sidedSuccess(â˜ƒxxx.isClientSide));
      } else {
         return Optional.empty();
      }
   }
}
