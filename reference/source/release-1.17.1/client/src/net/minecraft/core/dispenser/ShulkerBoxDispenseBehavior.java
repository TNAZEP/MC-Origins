package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.DispenserBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShulkerBoxDispenseBehavior extends OptionalDispenseItemBehavior {
   private static final Logger LOGGER = LogManager.getLogger();

   @Override
   protected ItemStack execute(BlockSource var1, ItemStack var2) {
      this.setSuccess(false);
      Item â˜ƒ = â˜ƒ.getItem();
      if (â˜ƒ instanceof BlockItem) {
         Direction â˜ƒx = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
         BlockPos â˜ƒxx = â˜ƒ.getPos().relative(â˜ƒx);
         Direction â˜ƒxxx = â˜ƒ.getLevel().isEmptyBlock(â˜ƒxx.below()) ? â˜ƒx : Direction.UP;

         try {
            this.setSuccess(((BlockItem)â˜ƒ).place(new DirectionalPlaceContext(â˜ƒ.getLevel(), â˜ƒxx, â˜ƒx, â˜ƒ, â˜ƒxxx)).consumesAction());
         } catch (Exception var8) {
            LOGGER.error("Error trying to place shulker box at {}", â˜ƒxx, var8);
         }
      }

      return â˜ƒ;
   }
}
