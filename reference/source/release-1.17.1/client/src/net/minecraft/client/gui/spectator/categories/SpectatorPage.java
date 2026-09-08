package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.MoreObjects;
import java.util.List;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;

public class SpectatorPage {
   public static final int NO_SELECTION = -1;
   private final List<SpectatorMenuItem> items;
   private final int selection;

   public SpectatorPage(List<SpectatorMenuItem> var1, int var2) {
      this.items = â˜ƒ;
      this.selection = â˜ƒ;
   }

   public SpectatorMenuItem getItem(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.items.size()
         ? MoreObjects.firstNonNull((SpectatorMenuItem)this.items.get(â˜ƒ), SpectatorMenu.EMPTY_SLOT)
         : SpectatorMenu.EMPTY_SLOT;
   }

   public int getSelectedSlot() {
      return this.selection;
   }
}
