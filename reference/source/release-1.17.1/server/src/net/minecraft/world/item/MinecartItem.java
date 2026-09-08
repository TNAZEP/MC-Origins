package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;

public class MinecartItem extends Item {
   private static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
      private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

      @Override
      public ItemStack execute(BlockSource var1, ItemStack var2) {
         Direction â˜ƒx = â˜ƒ.getBlockState().getValue(DispenserBlock.FACING);
         Level â˜ƒxx = â˜ƒ.getLevel();
         double â˜ƒxxx = â˜ƒ.x() + (double)â˜ƒx.getStepX() * 1.125;
         double â˜ƒxxxx = Math.floor(â˜ƒ.y()) + (double)â˜ƒx.getStepY();
         double â˜ƒxxxxx = â˜ƒ.z() + (double)â˜ƒx.getStepZ() * 1.125;
         BlockPos â˜ƒxxxxxx = â˜ƒ.getPos().relative(â˜ƒx);
         BlockState â˜ƒxxxxxxx = â˜ƒxx.getBlockState(â˜ƒxxxxxx);
         RailShape â˜ƒxxxxxxxx = â˜ƒxxxxxxx.getBlock() instanceof BaseRailBlock
            ? â˜ƒxxxxxxx.getValue(((BaseRailBlock)â˜ƒxxxxxxx.getBlock()).getShapeProperty())
            : RailShape.NORTH_SOUTH;
         double â˜ƒ;
         if (â˜ƒxxxxxxx.is(BlockTags.RAILS)) {
            if (â˜ƒxxxxxxxx.isAscending()) {
               â˜ƒ = 0.6;
            } else {
               â˜ƒ = 0.1;
            }
         } else {
            if (!â˜ƒxxxxxxx.isAir() || !â˜ƒxx.getBlockState(â˜ƒxxxxxx.below()).is(BlockTags.RAILS)) {
               return this.defaultDispenseItemBehavior.dispense(â˜ƒ, â˜ƒ);
            }

            BlockState â˜ƒ = â˜ƒxx.getBlockState(â˜ƒxxxxxx.below());
            RailShape â˜ƒx = â˜ƒ.getBlock() instanceof BaseRailBlock ? â˜ƒ.getValue(((BaseRailBlock)â˜ƒ.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            if (â˜ƒx != Direction.DOWN && â˜ƒx.isAscending()) {
               â˜ƒ = -0.4;
            } else {
               â˜ƒ = -0.9;
            }
         }

         AbstractMinecart â˜ƒ = AbstractMinecart.createMinecart(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx + â˜ƒ, â˜ƒxxxxx, ((MinecartItem)â˜ƒ.getItem()).type);
         if (â˜ƒ.hasCustomHoverName()) {
            â˜ƒ.setCustomName(â˜ƒ.getHoverName());
         }

         â˜ƒxx.addFreshEntity(â˜ƒ);
         â˜ƒ.shrink(1);
         return â˜ƒ;
      }

      @Override
      protected void playSound(BlockSource var1) {
         â˜ƒ.getLevel().levelEvent(1000, â˜ƒ.getPos(), 0);
      }
   };
   final AbstractMinecart.Type type;

   public MinecartItem(AbstractMinecart.Type var1, Item.Properties var2) {
      super(â˜ƒ);
      this.type = â˜ƒ;
      DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (!â˜ƒxx.is(BlockTags.RAILS)) {
         return InteractionResult.FAIL;
      } else {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand();
         if (!â˜ƒ.isClientSide) {
            RailShape â˜ƒx = â˜ƒxx.getBlock() instanceof BaseRailBlock
               ? â˜ƒxx.getValue(((BaseRailBlock)â˜ƒxx.getBlock()).getShapeProperty())
               : RailShape.NORTH_SOUTH;
            double â˜ƒxx = 0.0;
            if (â˜ƒx.isAscending()) {
               â˜ƒxx = 0.5;
            }

            AbstractMinecart â˜ƒx = AbstractMinecart.createMinecart(
               â˜ƒ, (double)â˜ƒx.getX() + 0.5, (double)â˜ƒx.getY() + 0.0625 + â˜ƒxx, (double)â˜ƒx.getZ() + 0.5, this.type
            );
            if (â˜ƒ.hasCustomHoverName()) {
               â˜ƒx.setCustomName(â˜ƒ.getHoverName());
            }

            â˜ƒ.addFreshEntity(â˜ƒx);
            â˜ƒ.gameEvent(â˜ƒ.getPlayer(), GameEvent.ENTITY_PLACE, â˜ƒx);
         }

         â˜ƒ.shrink(1);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }
}
