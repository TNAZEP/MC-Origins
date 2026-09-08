package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DropperBlock extends DispenserBlock {
   private static final DispenseItemBehavior DISPENSE_BEHAVIOUR = new DefaultDispenseItemBehavior();

   public DropperBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   protected DispenseItemBehavior getDispenseMethod(ItemStack var1) {
      return DISPENSE_BEHAVIOUR;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new DropperBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void dispenseFrom(ServerLevel var1, BlockPos var2) {
      BlockSourceImpl â˜ƒ = new BlockSourceImpl(â˜ƒ, â˜ƒ);
      DispenserBlockEntity â˜ƒx = â˜ƒ.getEntity();
      int â˜ƒxx = â˜ƒx.getRandomSlot();
      if (â˜ƒxx < 0) {
         â˜ƒ.levelEvent(1001, â˜ƒ, 0);
      } else {
         ItemStack â˜ƒ = â˜ƒx.getItem(â˜ƒxx);
         if (!â˜ƒ.isEmpty()) {
            Direction â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ).getValue(FACING);
            Container â˜ƒxxx = HopperBlockEntity.getContainerAt(â˜ƒ, â˜ƒ.relative(â˜ƒxx));
            ItemStack â˜ƒx;
            if (â˜ƒxxx == null) {
               â˜ƒx = DISPENSE_BEHAVIOUR.dispense(â˜ƒ, â˜ƒ);
            } else {
               â˜ƒx = HopperBlockEntity.addItem(â˜ƒx, â˜ƒxxx, â˜ƒ.copy().split(1), â˜ƒxx.getOpposite());
               if (â˜ƒx.isEmpty()) {
                  â˜ƒx = â˜ƒ.copy();
                  â˜ƒx.shrink(1);
               } else {
                  â˜ƒx = â˜ƒ.copy();
               }
            }

            â˜ƒx.setItem(â˜ƒxx, â˜ƒx);
         }
      }
   }
}
