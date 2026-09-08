package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HoeItem extends DiggerItem {
   protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES = Maps.<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>>newHashMap(
      ImmutableMap.of(
         Blocks.GRASS_BLOCK,
         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())),
         Blocks.DIRT_PATH,
         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())),
         Blocks.DIRT,
         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())),
         Blocks.COARSE_DIRT,
         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.DIRT.defaultBlockState())),
         Blocks.ROOTED_DIRT,
         Pair.of((Predicate)var0 -> true, changeIntoStateAndDropItem(Blocks.DIRT.defaultBlockState(), Items.HANGING_ROOTS))
      )
   );

   protected HoeItem(Tier var1, int var2, float var3, Item.Properties var4) {
      super((float)â˜ƒ, â˜ƒ, â˜ƒ, BlockTags.MINEABLE_WITH_HOE, â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> â˜ƒxx = (Pair)TILLABLES.get(â˜ƒ.getBlockState(â˜ƒx).getBlock());
      if (â˜ƒxx == null) {
         return InteractionResult.PASS;
      } else {
         Predicate<UseOnContext> â˜ƒ = (Predicate)â˜ƒxx.getFirst();
         Consumer<UseOnContext> â˜ƒx = (Consumer)â˜ƒxx.getSecond();
         if (â˜ƒ.test(â˜ƒ)) {
            Player â˜ƒxx = â˜ƒ.getPlayer();
            â˜ƒ.playSound(â˜ƒxx, â˜ƒx, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!â˜ƒ.isClientSide) {
               â˜ƒx.accept(â˜ƒ);
               if (â˜ƒxx != null) {
                  â˜ƒ.getItemInHand().hurtAndBreak(1, â˜ƒxx, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getHand()));
               }
            }

            return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   public static Consumer<UseOnContext> changeIntoState(BlockState var0) {
      return var1 -> var1.getLevel().setBlock(var1.getClickedPos(), â˜ƒ, 11);
   }

   public static Consumer<UseOnContext> changeIntoStateAndDropItem(BlockState var0, ItemLike var1) {
      return var2 -> {
         var2.getLevel().setBlock(var2.getClickedPos(), â˜ƒ, 11);
         Block.popResourceFromFace(var2.getLevel(), var2.getClickedPos(), var2.getClickedFace(), new ItemStack(â˜ƒ));
      };
   }

   public static boolean onlyIfAirAbove(UseOnContext var0) {
      return â˜ƒ.getClickedFace() != Direction.DOWN && â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().above()).isAir();
   }
}
