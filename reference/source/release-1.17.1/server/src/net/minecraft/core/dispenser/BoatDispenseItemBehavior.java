package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class BoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
   private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
   private final Boat.Type type;

   public BoatDispenseItemBehavior(Boat.Type var1) {
      this.type = â˜ƒ;
   }

   @Override
   public ItemStack execute(BlockSource var1, ItemStack var2) {
      Direction â˜ƒx = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
      Level â˜ƒxx = â˜ƒ.getLevel();
      double â˜ƒxxx = â˜ƒ.x() + (double)((float)â˜ƒx.getStepX() * 1.125F);
      double â˜ƒxxxx = â˜ƒ.y() + (double)((float)â˜ƒx.getStepY() * 1.125F);
      double â˜ƒxxxxx = â˜ƒ.z() + (double)((float)â˜ƒx.getStepZ() * 1.125F);
      BlockPos â˜ƒxxxxxx = â˜ƒ.getPos().relative(â˜ƒx);
      double â˜ƒ;
      if (â˜ƒxx.getFluidState(â˜ƒxxxxxx).is(FluidTags.WATER)) {
         â˜ƒ = 1.0;
      } else {
         if (!â˜ƒxx.getBlockState(â˜ƒxxxxxx).isAir() || !â˜ƒxx.getFluidState(â˜ƒxxxxxx.below()).is(FluidTags.WATER)) {
            return this.defaultDispenseItemBehavior.dispense(â˜ƒ, â˜ƒ);
         }

         â˜ƒ = 0.0;
      }

      Boat â˜ƒ = new Boat(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx + â˜ƒ, â˜ƒxxxxx);
      â˜ƒ.setType(this.type);
      â˜ƒ.setYRot(â˜ƒx.toYRot());
      â˜ƒxx.addFreshEntity(â˜ƒ);
      â˜ƒ.shrink(1);
      return â˜ƒ;
   }

   @Override
   protected void playSound(BlockSource var1) {
      â˜ƒ.getLevel().levelEvent(1000, â˜ƒ.getPos(), 0);
   }
}
