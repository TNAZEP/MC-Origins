package net.minecraft.world.item;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class AxeItem extends DiggerItem {
   protected static final Map<Block, Block> STRIPPABLES = new Builder<Block, Block>()
      .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
      .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
      .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
      .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
      .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
      .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
      .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
      .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
      .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
      .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
      .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
      .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
      .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
      .put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
      .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
      .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
      .build();

   protected AxeItem(Tier var1, float var2, float var3, Item.Properties var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, BlockTags.MINEABLE_WITH_AXE, â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      Player â˜ƒxx = â˜ƒ.getPlayer();
      BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
      Optional<BlockState> â˜ƒxxxx = this.getStripped(â˜ƒxxx);
      Optional<BlockState> â˜ƒxxxxx = WeatheringCopper.getPrevious(â˜ƒxxx);
      Optional<BlockState> â˜ƒxxxxxx = Optional.ofNullable((Block)((BiMap)HoneycombItem.WAX_OFF_BY_BLOCK.get()).get(â˜ƒxxx.getBlock()))
         .map(var1x -> var1x.withPropertiesOf(â˜ƒ));
      ItemStack â˜ƒxxxxxxx = â˜ƒ.getItemInHand();
      Optional<BlockState> â˜ƒxxxxxxxx = Optional.empty();
      if (â˜ƒxxxx.isPresent()) {
         â˜ƒ.playSound(â˜ƒxx, â˜ƒx, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
         â˜ƒxxxxxxxx = â˜ƒxxxx;
      } else if (â˜ƒxxxxx.isPresent()) {
         â˜ƒ.playSound(â˜ƒxx, â˜ƒx, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
         â˜ƒ.levelEvent(â˜ƒxx, 3005, â˜ƒx, 0);
         â˜ƒxxxxxxxx = â˜ƒxxxxx;
      } else if (â˜ƒxxxxxx.isPresent()) {
         â˜ƒ.playSound(â˜ƒxx, â˜ƒx, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
         â˜ƒ.levelEvent(â˜ƒxx, 3004, â˜ƒx, 0);
         â˜ƒxxxxxxxx = â˜ƒxxxxxx;
      }

      if (â˜ƒxxxxxxxx.isPresent()) {
         if (â˜ƒxx instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)â˜ƒxx, â˜ƒx, â˜ƒxxxxxxx);
         }

         â˜ƒ.setBlock(â˜ƒx, (BlockState)â˜ƒxxxxxxxx.get(), 11);
         if (â˜ƒxx != null) {
            â˜ƒxxxxxxx.hurtAndBreak(1, â˜ƒxx, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getHand()));
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   private Optional<BlockState> getStripped(BlockState var1) {
      return Optional.ofNullable((Block)STRIPPABLES.get(â˜ƒ.getBlock()))
         .map(var1x -> var1x.defaultBlockState().setValue(RotatedPillarBlock.AXIS, (Direction.Axis)â˜ƒ.getValue(RotatedPillarBlock.AXIS)));
   }
}
