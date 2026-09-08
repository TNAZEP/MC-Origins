package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeType;

public class SmokerMenu extends AbstractFurnaceMenu {
   public SmokerMenu(int var1, Inventory var2) {
      super(MenuType.SMOKER, RecipeType.SMOKING, RecipeBookType.SMOKER, â˜ƒ, â˜ƒ);
   }

   public SmokerMenu(int var1, Inventory var2, Container var3, ContainerData var4) {
      super(MenuType.SMOKER, RecipeType.SMOKING, RecipeBookType.SMOKER, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
