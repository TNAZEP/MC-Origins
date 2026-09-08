package net.minecraft.world.item.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

public class FrostWalkerEnchantment extends Enchantment {
   public FrostWalkerEnchantment(Enchantment.Rarity var1, EquipmentSlot... var2) {
      super(â˜ƒ, EnchantmentCategory.ARMOR_FEET, â˜ƒ);
   }

   @Override
   public int getMinCost(int var1) {
      return â˜ƒ * 10;
   }

   @Override
   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 15;
   }

   @Override
   public boolean isTreasureOnly() {
      return true;
   }

   @Override
   public int getMaxLevel() {
      return 2;
   }

   public static void onEntityMoved(LivingEntity var0, Level var1, BlockPos var2, int var3) {
      if (â˜ƒ.isOnGround()) {
         BlockState â˜ƒ = Blocks.FROSTED_ICE.defaultBlockState();
         float â˜ƒx = (float)Math.min(16, 2 + â˜ƒ);
         BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();

         for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(â˜ƒ.offset((double)(-â˜ƒx), -1.0, (double)(-â˜ƒx)), â˜ƒ.offset((double)â˜ƒx, -1.0, (double)â˜ƒx))) {
            if (â˜ƒxxx.closerThan(â˜ƒ.position(), (double)â˜ƒx)) {
               â˜ƒxx.set(â˜ƒxxx.getX(), â˜ƒxxx.getY() + 1, â˜ƒxxx.getZ());
               BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxx);
               if (â˜ƒxxxx.isAir()) {
                  BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
                  if (â˜ƒxxxxx.getMaterial() == Material.WATER
                     && â˜ƒxxxxx.getValue(LiquidBlock.LEVEL) == 0
                     && â˜ƒ.canSurvive(â˜ƒ, â˜ƒxxx)
                     && â˜ƒ.isUnobstructed(â˜ƒ, â˜ƒxxx, CollisionContext.empty())) {
                     â˜ƒ.setBlockAndUpdate(â˜ƒxxx, â˜ƒ);
                     â˜ƒ.getBlockTicks().scheduleTick(â˜ƒxxx, Blocks.FROSTED_ICE, Mth.nextInt(â˜ƒ.getRandom(), 60, 120));
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean checkCompatibility(Enchantment var1) {
      return super.checkCompatibility(â˜ƒ) && â˜ƒ != Enchantments.DEPTH_STRIDER;
   }
}
