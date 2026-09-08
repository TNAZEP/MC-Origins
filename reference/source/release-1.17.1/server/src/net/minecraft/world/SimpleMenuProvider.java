package net.minecraft.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;

public final class SimpleMenuProvider implements MenuProvider {
   private final Component title;
   private final MenuConstructor menuConstructor;

   public SimpleMenuProvider(MenuConstructor var1, Component var2) {
      this.menuConstructor = â˜ƒ;
      this.title = â˜ƒ;
   }

   @Override
   public Component getDisplayName() {
      return this.title;
   }

   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
      return this.menuConstructor.createMenu(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
