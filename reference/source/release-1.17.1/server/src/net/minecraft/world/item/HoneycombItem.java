package net.minecraft.world.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HoneycombItem extends Item {
   public static final Supplier<BiMap<Block, Block>> WAXABLES = Suppliers.memoize(
      () -> ImmutableBiMap.<Block, Block>builder()
            .put(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK)
            .put(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER)
            .put(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER)
            .put(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER)
            .put(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER)
            .put(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER)
            .put(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER)
            .put(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER)
            .put(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB)
            .put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)
            .put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)
            .put(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)
            .put(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS)
            .put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS)
            .put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS)
            .put(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
            .build()
   );
   public static final Supplier<BiMap<Block, Block>> WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> ((BiMap)WAXABLES.get()).inverse());

   public HoneycombItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      return (InteractionResult)getWaxed(â˜ƒxx).map(var3x -> {
         Player â˜ƒ = â˜ƒ.getPlayer();
         ItemStack â˜ƒx = â˜ƒ.getItemInHand();
         if (â˜ƒ instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)â˜ƒ, â˜ƒ, â˜ƒx);
         }

         â˜ƒx.shrink(1);
         â˜ƒ.setBlock(â˜ƒ, var3x, 11);
         â˜ƒ.levelEvent(â˜ƒ, 3003, â˜ƒ, 0);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }).orElse(InteractionResult.PASS);
   }

   public static Optional<BlockState> getWaxed(BlockState var0) {
      return Optional.ofNullable((Block)((BiMap)WAXABLES.get()).get(â˜ƒ.getBlock())).map(var1 -> var1.withPropertiesOf(â˜ƒ));
   }
}
