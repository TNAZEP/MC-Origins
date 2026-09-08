package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WritableBookItem extends Item {
   public WritableBookItem(Item.Properties var1) {
      super(â˜ƒ);
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

   public static boolean makeSureTagIsValid(@Nullable CompoundTag var0) {
      if (â˜ƒ == null) {
         return false;
      } else if (!â˜ƒ.contains("pages", 9)) {
         return false;
      } else {
         ListTag â˜ƒ = â˜ƒ.getList("pages", 8);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            String â˜ƒxx = â˜ƒ.getString(â˜ƒx);
            if (â˜ƒxx.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}
