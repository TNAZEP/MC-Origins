package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;

public class IceBlock extends HalfTransparentBlock {
   public IceBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void playerDestroy(Level var1, Player var2, BlockPos var3, BlockState var4, @Nullable BlockEntity var5, ItemStack var6) {
      super.playerDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, â˜ƒ) == 0) {
         if (â˜ƒ.dimensionType().ultraWarm()) {
            â˜ƒ.removeBlock(â˜ƒ, false);
            return;
         }

         Material â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below()).getMaterial();
         if (â˜ƒ.blocksMotion() || â˜ƒ.isLiquid()) {
            â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.WATER.defaultBlockState());
         }
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getBrightness(LightLayer.BLOCK, â˜ƒ) > 11 - â˜ƒ.getLightBlock(â˜ƒ, â˜ƒ)) {
         this.melt(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected void melt(BlockState var1, Level var2, BlockPos var3) {
      if (â˜ƒ.dimensionType().ultraWarm()) {
         â˜ƒ.removeBlock(â˜ƒ, false);
      } else {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.WATER.defaultBlockState());
         â˜ƒ.neighborChanged(â˜ƒ, Blocks.WATER, â˜ƒ);
      }
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.NORMAL;
   }
}
