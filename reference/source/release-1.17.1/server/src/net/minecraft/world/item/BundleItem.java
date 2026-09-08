package net.minecraft.world.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.Level;

public class BundleItem extends Item {
   private static final String TAG_ITEMS = "Items";
   public static final int MAX_WEIGHT = 64;
   private static final int BUNDLE_IN_BUNDLE_WEIGHT = 4;
   private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

   public BundleItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public static float getFullnessDisplay(ItemStack var0) {
      return (float)getContentWeight(â˜ƒ) / 64.0F;
   }

   @Override
   public boolean overrideStackedOnOther(ItemStack var1, Slot var2, ClickAction var3, Player var4) {
      if (â˜ƒ != ClickAction.SECONDARY) {
         return false;
      } else {
         ItemStack â˜ƒ = â˜ƒ.getItem();
         if (â˜ƒ.isEmpty()) {
            removeOne(â˜ƒ).ifPresent(var2x -> add(â˜ƒ, â˜ƒ.safeInsert(var2x)));
         } else if (â˜ƒ.getItem().canFitInsideContainerItems()) {
            int â˜ƒ = (64 - getContentWeight(â˜ƒ)) / getWeight(â˜ƒ);
            add(â˜ƒ, â˜ƒ.safeTake(â˜ƒ.getCount(), â˜ƒ, â˜ƒ));
         }

         return true;
      }
   }

   @Override
   public boolean overrideOtherStackedOnMe(ItemStack var1, ItemStack var2, Slot var3, ClickAction var4, Player var5, SlotAccess var6) {
      if (â˜ƒ == ClickAction.SECONDARY && â˜ƒ.allowModification(â˜ƒ)) {
         if (â˜ƒ.isEmpty()) {
            removeOne(â˜ƒ).ifPresent(â˜ƒ::set);
         } else {
            â˜ƒ.shrink(add(â˜ƒ, â˜ƒ));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (dropContents(â˜ƒ, â˜ƒ)) {
         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
      } else {
         return InteractionResultHolder.fail(â˜ƒ);
      }
   }

   @Override
   public boolean isBarVisible(ItemStack var1) {
      return getContentWeight(â˜ƒ) > 0;
   }

   @Override
   public int getBarWidth(ItemStack var1) {
      return Math.min(1 + 12 * getContentWeight(â˜ƒ) / 64, 13);
   }

   @Override
   public int getBarColor(ItemStack var1) {
      return BAR_COLOR;
   }

   private static int add(ItemStack var0, ItemStack var1) {
      if (!â˜ƒ.isEmpty() && â˜ƒ.getItem().canFitInsideContainerItems()) {
         CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
         if (!â˜ƒ.contains("Items")) {
            â˜ƒ.put("Items", new ListTag());
         }

         int â˜ƒ = getContentWeight(â˜ƒ);
         int â˜ƒx = getWeight(â˜ƒ);
         int â˜ƒxx = Math.min(â˜ƒ.getCount(), (64 - â˜ƒ) / â˜ƒx);
         if (â˜ƒxx == 0) {
            return 0;
         } else {
            ListTag â˜ƒ = â˜ƒ.getList("Items", 10);
            Optional<CompoundTag> â˜ƒx = getMatchingItem(â˜ƒ, â˜ƒ);
            if (â˜ƒx.isPresent()) {
               CompoundTag â˜ƒxx = (CompoundTag)â˜ƒx.get();
               ItemStack â˜ƒxxx = ItemStack.of(â˜ƒxx);
               â˜ƒxxx.grow(â˜ƒxx);
               â˜ƒxxx.save(â˜ƒxx);
               â˜ƒ.remove(â˜ƒxx);
               â˜ƒ.add(0, â˜ƒxx);
            } else {
               ItemStack â˜ƒ = â˜ƒ.copy();
               â˜ƒ.setCount(â˜ƒxx);
               CompoundTag â˜ƒx = new CompoundTag();
               â˜ƒ.save(â˜ƒx);
               â˜ƒ.add(0, â˜ƒx);
            }

            return â˜ƒxx;
         }
      } else {
         return 0;
      }
   }

   private static Optional<CompoundTag> getMatchingItem(ItemStack var0, ListTag var1) {
      return â˜ƒ.is(Items.BUNDLE)
         ? Optional.empty()
         : â˜ƒ.stream()
            .filter(CompoundTag.class::isInstance)
            .map(CompoundTag.class::cast)
            .filter(var1x -> ItemStack.isSameItemSameTags(ItemStack.of(var1x), â˜ƒ))
            .findFirst();
   }

   private static int getWeight(ItemStack var0) {
      if (â˜ƒ.is(Items.BUNDLE)) {
         return 4 + getContentWeight(â˜ƒ);
      } else {
         if ((â˜ƒ.is(Items.BEEHIVE) || â˜ƒ.is(Items.BEE_NEST)) && â˜ƒ.hasTag()) {
            CompoundTag â˜ƒ = â˜ƒ.getTagElement("BlockEntityTag");
            if (â˜ƒ != null && !â˜ƒ.getList("Bees", 10).isEmpty()) {
               return 64;
            }
         }

         return 64 / â˜ƒ.getMaxStackSize();
      }
   }

   private static int getContentWeight(ItemStack var0) {
      return getContents(â˜ƒ).mapToInt(var0x -> getWeight(var0x) * var0x.getCount()).sum();
   }

   private static Optional<ItemStack> removeOne(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      if (!â˜ƒ.contains("Items")) {
         return Optional.empty();
      } else {
         ListTag â˜ƒ = â˜ƒ.getList("Items", 10);
         if (â˜ƒ.isEmpty()) {
            return Optional.empty();
         } else {
            int â˜ƒ = 0;
            CompoundTag â˜ƒx = â˜ƒ.getCompound(0);
            ItemStack â˜ƒxx = ItemStack.of(â˜ƒx);
            â˜ƒ.remove(0);
            if (â˜ƒ.isEmpty()) {
               â˜ƒ.removeTagKey("Items");
            }

            return Optional.of(â˜ƒxx);
         }
      }
   }

   private static boolean dropContents(ItemStack var0, Player var1) {
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      if (!â˜ƒ.contains("Items")) {
         return false;
      } else {
         if (â˜ƒ instanceof ServerPlayer) {
            ListTag â˜ƒ = â˜ƒ.getList("Items", 10);

            for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
               CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
               ItemStack â˜ƒxxx = ItemStack.of(â˜ƒxx);
               â˜ƒ.drop(â˜ƒxxx, true);
            }
         }

         â˜ƒ.removeTagKey("Items");
         return true;
      }
   }

   private static Stream<ItemStack> getContents(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ == null) {
         return Stream.empty();
      } else {
         ListTag â˜ƒ = â˜ƒ.getList("Items", 10);
         return â˜ƒ.stream().map(CompoundTag.class::cast).map(ItemStack::of);
      }
   }

   @Override
   public Optional<TooltipComponent> getTooltipImage(ItemStack var1) {
      NonNullList<ItemStack> â˜ƒ = NonNullList.create();
      getContents(â˜ƒ).forEach(â˜ƒ::add);
      return Optional.of(new BundleTooltip(â˜ƒ, getContentWeight(â˜ƒ)));
   }

   @Override
   public void appendHoverText(ItemStack var1, Level var2, List<Component> var3, TooltipFlag var4) {
      â˜ƒ.add(new TranslatableComponent("item.minecraft.bundle.fullness", getContentWeight(â˜ƒ), 64).withStyle(ChatFormatting.GRAY));
   }

   @Override
   public void onDestroyed(ItemEntity var1) {
      ItemUtils.onContainerDestroyed(â˜ƒ, getContents(â˜ƒ.getItem()));
   }
}
