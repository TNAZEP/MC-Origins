package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WrittenBookItem extends Item {
   public static final int TITLE_LENGTH = 16;
   public static final int TITLE_MAX_LENGTH = 32;
   public static final int PAGE_EDIT_LENGTH = 1024;
   public static final int PAGE_LENGTH = 32767;
   public static final int MAX_PAGES = 100;
   public static final int MAX_GENERATION = 2;
   public static final String TAG_TITLE = "title";
   public static final String TAG_FILTERED_TITLE = "filtered_title";
   public static final String TAG_AUTHOR = "author";
   public static final String TAG_PAGES = "pages";
   public static final String TAG_FILTERED_PAGES = "filtered_pages";
   public static final String TAG_GENERATION = "generation";
   public static final String TAG_RESOLVED = "resolved";

   public WrittenBookItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public static boolean makeSureTagIsValid(@Nullable CompoundTag var0) {
      if (!WritableBookItem.makeSureTagIsValid(â˜ƒ)) {
         return false;
      } else if (!â˜ƒ.contains("title", 8)) {
         return false;
      } else {
         String â˜ƒ = â˜ƒ.getString("title");
         return â˜ƒ.length() > 32 ? false : â˜ƒ.contains("author", 8);
      }
   }

   public static int getGeneration(ItemStack var0) {
      return â˜ƒ.getTag().getInt("generation");
   }

   public static int getPageCount(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      return â˜ƒ != null ? â˜ƒ.getList("pages", 8).size() : 0;
   }

   @Override
   public Component getName(ItemStack var1) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null) {
         String â˜ƒx = â˜ƒ.getString("title");
         if (!StringUtil.isNullOrEmpty(â˜ƒx)) {
            return new TextComponent(â˜ƒx);
         }
      }

      return super.getName(â˜ƒ);
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      if (â˜ƒ.hasTag()) {
         CompoundTag â˜ƒ = â˜ƒ.getTag();
         String â˜ƒx = â˜ƒ.getString("author");
         if (!StringUtil.isNullOrEmpty(â˜ƒx)) {
            â˜ƒ.add(new TranslatableComponent("book.byAuthor", â˜ƒx).withStyle(ChatFormatting.GRAY));
         }

         â˜ƒ.add(new TranslatableComponent("book.generation." + â˜ƒ.getInt("generation")).withStyle(ChatFormatting.GRAY));
      }
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (â˜ƒxx.is(Blocks.LECTERN)) {
         return LecternBlock.tryPlaceBook(â˜ƒ.getPlayer(), â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ.getItemInHand())
            ? InteractionResult.sidedSuccess(â˜ƒ.isClientSide)
            : InteractionResult.PASS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      â˜ƒ.openItemGui(â˜ƒ, â˜ƒ);
      â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
   }

   public static boolean resolveBookComponents(ItemStack var0, @Nullable CommandSourceStack var1, @Nullable Player var2) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      if (â˜ƒ != null && !â˜ƒ.getBoolean("resolved")) {
         â˜ƒ.putBoolean("resolved", true);
         if (!makeSureTagIsValid(â˜ƒ)) {
            return false;
         } else {
            ListTag â˜ƒx = â˜ƒ.getList("pages", 8);

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
               â˜ƒx.set(â˜ƒxx, (Tag)StringTag.valueOf(resolvePage(â˜ƒ, â˜ƒ, â˜ƒx.getString(â˜ƒxx))));
            }

            if (â˜ƒ.contains("filtered_pages", 10)) {
               CompoundTag â˜ƒxx = â˜ƒ.getCompound("filtered_pages");

               for(String â˜ƒxxx : â˜ƒxx.getAllKeys()) {
                  â˜ƒxx.putString(â˜ƒxxx, resolvePage(â˜ƒ, â˜ƒ, â˜ƒxx.getString(â˜ƒxxx)));
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static String resolvePage(@Nullable CommandSourceStack var0, @Nullable Player var1, String var2) {
      Component â˜ƒ;
      try {
         â˜ƒ = Component.Serializer.fromJsonLenient(â˜ƒ);
         â˜ƒ = ComponentUtils.updateForEntity(â˜ƒ, â˜ƒ, â˜ƒ, 0);
      } catch (Exception var5) {
         â˜ƒ = new TextComponent(â˜ƒ);
      }

      return Component.Serializer.toJson(â˜ƒ);
   }

   @Override
   public boolean isFoil(ItemStack var1) {
      return true;
   }
}
