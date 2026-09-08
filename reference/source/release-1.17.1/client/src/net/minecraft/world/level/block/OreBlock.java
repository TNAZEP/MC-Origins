package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class OreBlock extends Block {
   private final UniformInt xpRange;

   public OreBlock(BlockBehaviour.Properties var1) {
      this(â˜ƒ, UniformInt.of(0, 0));
   }

   public OreBlock(BlockBehaviour.Properties var1, UniformInt var2) {
      super(â˜ƒ);
      this.xpRange = â˜ƒ;
   }

   @Override
   public void spawnAfterBreak(BlockState var1, ServerLevel var2, BlockPos var3, ItemStack var4) {
      super.spawnAfterBreak(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, â˜ƒ) == 0) {
         int â˜ƒ = this.xpRange.sample(â˜ƒ.random);
         if (â˜ƒ > 0) {
            this.popExperience(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }
}
