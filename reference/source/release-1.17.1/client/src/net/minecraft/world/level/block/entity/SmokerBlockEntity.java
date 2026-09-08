package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class SmokerBlockEntity extends AbstractFurnaceBlockEntity {
   public SmokerBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.SMOKER, â˜ƒ, â˜ƒ, RecipeType.SMOKING);
   }

   @Override
   protected Component getDefaultName() {
      return new TranslatableComponent("container.smoker");
   }

   @Override
   protected int getBurnDuration(ItemStack var1) {
      return super.getBurnDuration(â˜ƒ) / 2;
   }

   @Override
   protected AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return new SmokerMenu(â˜ƒ, â˜ƒ, this, this.dataAccess);
   }
}
