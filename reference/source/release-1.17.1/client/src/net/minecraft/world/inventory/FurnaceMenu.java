package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeType;

public class FurnaceMenu extends AbstractFurnaceMenu {
   public FurnaceMenu(int var1, Inventory var2) {
      super(MenuType.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, â˜ƒ, â˜ƒ);
   }

   public FurnaceMenu(int var1, Inventory var2, Container var3, ContainerData var4) {
      super(MenuType.FURNACE, RecipeType.SMELTING, RecipeBookType.FURNACE, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
