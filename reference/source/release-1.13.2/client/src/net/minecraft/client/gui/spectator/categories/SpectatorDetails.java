package net.minecraft.client.gui.spectator.categories;

import com.google.common.base.MoreObjects;
import java.util.List;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

public class SpectatorDetails {
   private final ISpectatorMenuView field_178684_a;
   private final List<ISpectatorMenuObject> field_178682_b;
   private final int field_178683_c;

   public SpectatorDetails(ISpectatorMenuView var1, List<ISpectatorMenuObject> var2, int var3) {
      this.field_178684_a = ☃;
      this.field_178682_b = ☃;
      this.field_178683_c = ☃;
   }

   public ISpectatorMenuObject func_178680_a(int var1) {
      return ☃ >= 0 && ☃ < this.field_178682_b.size()
         ? MoreObjects.firstNonNull((ISpectatorMenuObject)this.field_178682_b.get(☃), SpectatorMenu.field_178657_a)
         : SpectatorMenu.field_178657_a;
   }

   public int func_178681_b() {
      return this.field_178683_c;
   }
}
