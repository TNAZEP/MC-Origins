package net.minecraft.core.cauldron;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public interface CauldronInteraction {
   Map<Item, CauldronInteraction> EMPTY = newInteractionMap();
   Map<Item, CauldronInteraction> WATER = newInteractionMap();
   Map<Item, CauldronInteraction> LAVA = newInteractionMap();
   Map<Item, CauldronInteraction> POWDER_SNOW = newInteractionMap();
   CauldronInteraction FILL_WATER = (var0, var1, var2, var3, var4, var5) -> emptyBucket(
         var1,
         var2,
         var3,
         var4,
         var5,
         Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, Integer.valueOf(3)),
         SoundEvents.BUCKET_EMPTY
      );
   CauldronInteraction FILL_LAVA = (var0, var1, var2, var3, var4, var5) -> emptyBucket(
         var1, var2, var3, var4, var5, Blocks.LAVA_CAULDRON.defaultBlockState(), SoundEvents.BUCKET_EMPTY_LAVA
      );
   CauldronInteraction FILL_POWDER_SNOW = (var0, var1, var2, var3, var4, var5) -> emptyBucket(
         var1,
         var2,
         var3,
         var4,
         var5,
         Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, Integer.valueOf(3)),
         SoundEvents.BUCKET_EMPTY_POWDER_SNOW
      );
   CauldronInteraction SHULKER_BOX = (var0, var1, var2, var3, var4, var5) -> {
      Block â˜ƒ = Block.byItem(var5.getItem());
      if (!(â˜ƒ instanceof ShulkerBoxBlock)) {
         return InteractionResult.PASS;
      } else {
         if (!var1.isClientSide) {
            ItemStack â˜ƒ = new ItemStack(Blocks.SHULKER_BOX);
            if (var5.hasTag()) {
               â˜ƒ.setTag(var5.getTag().copy());
            }

            var3.setItemInHand(var4, â˜ƒ);
            var3.awardStat(Stats.CLEAN_SHULKER_BOX);
            LayeredCauldronBlock.lowerFillLevel(var0, var1, var2);
         }

         return InteractionResult.sidedSuccess(var1.isClientSide);
      }
   };
   CauldronInteraction BANNER = (var0, var1, var2, var3, var4, var5) -> {
      if (BannerBlockEntity.getPatternCount(var5) <= 0) {
         return InteractionResult.PASS;
      } else {
         if (!var1.isClientSide) {
            ItemStack â˜ƒ = var5.copy();
            â˜ƒ.setCount(1);
            BannerBlockEntity.removeLastPattern(â˜ƒ);
            if (!var3.getAbilities().instabuild) {
               var5.shrink(1);
            }

            if (var5.isEmpty()) {
               var3.setItemInHand(var4, â˜ƒ);
            } else if (var3.getInventory().add(â˜ƒ)) {
               var3.inventoryMenu.sendAllDataToRemote();
            } else {
               var3.drop(â˜ƒ, false);
            }

            var3.awardStat(Stats.CLEAN_BANNER);
            LayeredCauldronBlock.lowerFillLevel(var0, var1, var2);
         }

         return InteractionResult.sidedSuccess(var1.isClientSide);
      }
   };
   CauldronInteraction DYED_ITEM = (var0, var1, var2, var3, var4, var5) -> {
      Item â˜ƒ = var5.getItem();
      if (!(â˜ƒ instanceof DyeableLeatherItem)) {
         return InteractionResult.PASS;
      } else {
         DyeableLeatherItem â˜ƒ = (DyeableLeatherItem)â˜ƒ;
         if (!â˜ƒ.hasCustomColor(var5)) {
            return InteractionResult.PASS;
         } else {
            if (!var1.isClientSide) {
               â˜ƒ.clearColor(var5);
               var3.awardStat(Stats.CLEAN_ARMOR);
               LayeredCauldronBlock.lowerFillLevel(var0, var1, var2);
            }

            return InteractionResult.sidedSuccess(var1.isClientSide);
         }
      }
   };

   static Object2ObjectOpenHashMap<Item, CauldronInteraction> newInteractionMap() {
      return Util.make(new Object2ObjectOpenHashMap<>(), var0 -> var0.defaultReturnValue((var0x, var1, var2, var3, var4, var5) -> InteractionResult.PASS));
   }

   InteractionResult interact(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, ItemStack var6);

   static void bootStrap() {
      addDefaultInteractions(EMPTY);
      EMPTY.put(Items.POTION, (CauldronInteraction)(var0, var1, var2, var3, var4, var5) -> {
         if (PotionUtils.getPotion(var5) != Potions.WATER) {
            return InteractionResult.PASS;
         } else {
            if (!var1.isClientSide) {
               Item â˜ƒ = var5.getItem();
               var3.setItemInHand(var4, ItemUtils.createFilledResult(var5, var3, new ItemStack(Items.GLASS_BOTTLE)));
               var3.awardStat(Stats.USE_CAULDRON);
               var3.awardStat(Stats.ITEM_USED.get(â˜ƒ));
               var1.setBlockAndUpdate(var2, Blocks.WATER_CAULDRON.defaultBlockState());
               var1.playSound(null, var2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
               var1.gameEvent(null, GameEvent.FLUID_PLACE, var2);
            }

            return InteractionResult.sidedSuccess(var1.isClientSide);
         }
      });
      addDefaultInteractions(WATER);
      WATER.put(
         Items.BUCKET,
         (CauldronInteraction)(var0, var1, var2, var3, var4, var5) -> fillBucket(
               var0,
               var1,
               var2,
               var3,
               var4,
               var5,
               new ItemStack(Items.WATER_BUCKET),
               var0x -> var0x.getValue(LayeredCauldronBlock.LEVEL) == 3,
               SoundEvents.BUCKET_FILL
            )
      );
      WATER.put(Items.GLASS_BOTTLE, (CauldronInteraction)(var0, var1, var2, var3, var4, var5) -> {
         if (!var1.isClientSide) {
            Item â˜ƒ = var5.getItem();
            var3.setItemInHand(var4, ItemUtils.createFilledResult(var5, var3, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)));
            var3.awardStat(Stats.USE_CAULDRON);
            var3.awardStat(Stats.ITEM_USED.get(â˜ƒ));
            LayeredCauldronBlock.lowerFillLevel(var0, var1, var2);
            var1.playSound(null, var2, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            var1.gameEvent(null, GameEvent.FLUID_PICKUP, var2);
         }

         return InteractionResult.sidedSuccess(var1.isClientSide);
      });
      WATER.put(Items.POTION, (CauldronInteraction)(var0, var1, var2, var3, var4, var5) -> {
         if (var0.getValue(LayeredCauldronBlock.LEVEL) != 3 && PotionUtils.getPotion(var5) == Potions.WATER) {
            if (!var1.isClientSide) {
               var3.setItemInHand(var4, ItemUtils.createFilledResult(var5, var3, new ItemStack(Items.GLASS_BOTTLE)));
               var3.awardStat(Stats.USE_CAULDRON);
               var3.awardStat(Stats.ITEM_USED.get(var5.getItem()));
               var1.setBlockAndUpdate(var2, var0.cycle(LayeredCauldronBlock.LEVEL));
               var1.playSound(null, var2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
               var1.gameEvent(null, GameEvent.FLUID_PLACE, var2);
            }

            return InteractionResult.sidedSuccess(var1.isClientSide);
         } else {
            return InteractionResult.PASS;
         }
      });
      WATER.put(Items.LEATHER_BOOTS, DYED_ITEM);
      WATER.put(Items.LEATHER_LEGGINGS, DYED_ITEM);
      WATER.put(Items.LEATHER_CHESTPLATE, DYED_ITEM);
      WATER.put(Items.LEATHER_HELMET, DYED_ITEM);
      WATER.put(Items.LEATHER_HORSE_ARMOR, DYED_ITEM);
      WATER.put(Items.WHITE_BANNER, BANNER);
      WATER.put(Items.GRAY_BANNER, BANNER);
      WATER.put(Items.BLACK_BANNER, BANNER);
      WATER.put(Items.BLUE_BANNER, BANNER);
      WATER.put(Items.BROWN_BANNER, BANNER);
      WATER.put(Items.CYAN_BANNER, BANNER);
      WATER.put(Items.GREEN_BANNER, BANNER);
      WATER.put(Items.LIGHT_BLUE_BANNER, BANNER);
      WATER.put(Items.LIGHT_GRAY_BANNER, BANNER);
      WATER.put(Items.LIME_BANNER, BANNER);
      WATER.put(Items.MAGENTA_BANNER, BANNER);
      WATER.put(Items.ORANGE_BANNER, BANNER);
      WATER.put(Items.PINK_BANNER, BANNER);
      WATER.put(Items.PURPLE_BANNER, BANNER);
      WATER.put(Items.RED_BANNER, BANNER);
      WATER.put(Items.YELLOW_BANNER, BANNER);
      WATER.put(Items.WHITE_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.GRAY_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.BLACK_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.BLUE_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.BROWN_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.CYAN_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.GREEN_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.LIGHT_BLUE_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.LIGHT_GRAY_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.LIME_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.MAGENTA_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.ORANGE_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.PINK_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.PURPLE_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.RED_SHULKER_BOX, SHULKER_BOX);
      WATER.put(Items.YELLOW_SHULKER_BOX, SHULKER_BOX);
      LAVA.put(
         Items.BUCKET,
         (CauldronInteraction)(var0, var1, var2, var3, var4, var5) -> fillBucket(
               var0, var1, var2, var3, var4, var5, new ItemStack(Items.LAVA_BUCKET), var0x -> true, SoundEvents.BUCKET_FILL_LAVA
            )
      );
      addDefaultInteractions(LAVA);
      POWDER_SNOW.put(
         Items.BUCKET,
         (CauldronInteraction)(var0, var1, var2, var3, var4, var5) -> fillBucket(
               var0,
               var1,
               var2,
               var3,
               var4,
               var5,
               new ItemStack(Items.POWDER_SNOW_BUCKET),
               var0x -> var0x.getValue(LayeredCauldronBlock.LEVEL) == 3,
               SoundEvents.BUCKET_FILL_POWDER_SNOW
            )
      );
      addDefaultInteractions(POWDER_SNOW);
   }

   static void addDefaultInteractions(Map<Item, CauldronInteraction> var0) {
      â˜ƒ.put(Items.LAVA_BUCKET, FILL_LAVA);
      â˜ƒ.put(Items.WATER_BUCKET, FILL_WATER);
      â˜ƒ.put(Items.POWDER_SNOW_BUCKET, FILL_POWDER_SNOW);
   }

   static InteractionResult fillBucket(
      BlockState var0,
      Level var1,
      BlockPos var2,
      Player var3,
      InteractionHand var4,
      ItemStack var5,
      ItemStack var6,
      Predicate<BlockState> var7,
      SoundEvent var8
   ) {
      if (!â˜ƒ.test(â˜ƒ)) {
         return InteractionResult.PASS;
      } else {
         if (!â˜ƒ.isClientSide) {
            Item â˜ƒ = â˜ƒ.getItem();
            â˜ƒ.setItemInHand(â˜ƒ, ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, â˜ƒ));
            â˜ƒ.awardStat(Stats.USE_CAULDRON);
            â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ));
            â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.CAULDRON.defaultBlockState());
            â˜ƒ.playSound(null, â˜ƒ, â˜ƒ, SoundSource.BLOCKS, 1.0F, 1.0F);
            â˜ƒ.gameEvent(null, GameEvent.FLUID_PICKUP, â˜ƒ);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   static InteractionResult emptyBucket(Level var0, BlockPos var1, Player var2, InteractionHand var3, ItemStack var4, BlockState var5, SoundEvent var6) {
      if (!â˜ƒ.isClientSide) {
         Item â˜ƒ = â˜ƒ.getItem();
         â˜ƒ.setItemInHand(â˜ƒ, ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, new ItemStack(Items.BUCKET)));
         â˜ƒ.awardStat(Stats.FILL_CAULDRON);
         â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒ));
         â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒ);
         â˜ƒ.playSound(null, â˜ƒ, â˜ƒ, SoundSource.BLOCKS, 1.0F, 1.0F);
         â˜ƒ.gameEvent(null, GameEvent.FLUID_PLACE, â˜ƒ);
      }

      return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
   }
}
