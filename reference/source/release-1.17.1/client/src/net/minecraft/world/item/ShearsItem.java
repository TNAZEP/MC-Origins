package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ShearsItem extends Item {
   public ShearsItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean mineBlock(ItemStack var1, Level var2, BlockState var3, BlockPos var4, LivingEntity var5) {
      if (!â˜ƒ.isClientSide && !â˜ƒ.is(BlockTags.FIRE)) {
         â˜ƒ.hurtAndBreak(1, â˜ƒ, var0 -> var0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
      }

      return !â˜ƒ.is(BlockTags.LEAVES)
            && !â˜ƒ.is(Blocks.COBWEB)
            && !â˜ƒ.is(Blocks.GRASS)
            && !â˜ƒ.is(Blocks.FERN)
            && !â˜ƒ.is(Blocks.DEAD_BUSH)
            && !â˜ƒ.is(Blocks.HANGING_ROOTS)
            && !â˜ƒ.is(Blocks.VINE)
            && !â˜ƒ.is(Blocks.TRIPWIRE)
            && !â˜ƒ.is(BlockTags.WOOL)
         ? super.mineBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         : true;
   }

   @Override
   public boolean isCorrectToolForDrops(BlockState var1) {
      return â˜ƒ.is(Blocks.COBWEB) || â˜ƒ.is(Blocks.REDSTONE_WIRE) || â˜ƒ.is(Blocks.TRIPWIRE);
   }

   @Override
   public float getDestroySpeed(ItemStack var1, BlockState var2) {
      if (â˜ƒ.is(Blocks.COBWEB) || â˜ƒ.is(BlockTags.LEAVES)) {
         return 15.0F;
      } else if (â˜ƒ.is(BlockTags.WOOL)) {
         return 5.0F;
      } else {
         return !â˜ƒ.is(Blocks.VINE) && !â˜ƒ.is(Blocks.GLOW_LICHEN) ? super.getDestroySpeed(â˜ƒ, â˜ƒ) : 2.0F;
      }
   }
}
