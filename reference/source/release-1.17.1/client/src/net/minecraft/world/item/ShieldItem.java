package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ShieldItem extends Item {
   public static final int EFFECTIVE_BLOCK_DELAY = 5;
   public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;
   public static final String TAG_BASE_COLOR = "Base";

   public ShieldItem(Item.Properties var1) {
      super(â˜ƒ);
      DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
   }

   @Override
   public String getDescriptionId(ItemStack var1) {
      return â˜ƒ.getTagElement("BlockEntityTag") != null ? this.getDescriptionId() + "." + getColor(â˜ƒ).getName() : super.getDescriptionId(â˜ƒ);
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      BannerItem.appendHoverTextFromBannerBlockEntityTag(â˜ƒ, â˜ƒ);
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.BLOCK;
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 72000;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      â˜ƒ.startUsingItem(â˜ƒ);
      return InteractionResultHolder.consume(â˜ƒ);
   }

   @Override
   public boolean isValidRepairItem(ItemStack var1, ItemStack var2) {
      return â˜ƒ.is(ItemTags.PLANKS) || super.isValidRepairItem(â˜ƒ, â˜ƒ);
   }

   public static DyeColor getColor(ItemStack var0) {
      return DyeColor.byId(â˜ƒ.getOrCreateTagElement("BlockEntityTag").getInt("Base"));
   }
}
