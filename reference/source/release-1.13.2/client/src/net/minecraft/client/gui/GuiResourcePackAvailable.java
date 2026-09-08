package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiResourcePackAvailable extends GuiResourcePackList {
   public GuiResourcePackAvailable(Minecraft var1, int var2, int var3) {
      super(☃, ☃, ☃);
   }

   @Override
   protected String func_148202_k() {
      return I18n.func_135052_a("resourcePack.available.title");
   }
}
